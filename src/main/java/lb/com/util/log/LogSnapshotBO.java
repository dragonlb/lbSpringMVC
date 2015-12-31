package lb.com.util.log;

import java.util.*;

/**
 * 日志对象--快照
 * Created by libing on 2015/11/4.
 */
public class LogSnapshotBO {

    private static final int MSG_SIZE = 10;
    private static final int MAX_SUB_SIZE = 3;
    public static final String ALIVE = "ALIVE";

    //存活模式  ALIVE: 活动模式    DEAD: 死亡模式
    private String aliveMode;

    //父级快照
    private LogSnapshotBO preBo;

    //快照主键
    private String key;

    //快照源主键--只有且仅可人为指定
    private String oriKey;

    //层级
    private int level;

    //启动时间
    private Long startNano;

    //结束时间
    private Long commitNano;

    //快照耗时
    private Long stepNano;

    //日志内容
    private List<LogLineBO> msgList = new ArrayList<LogLineBO>();

    //日志溢出数量
    private Long oom_msgs = 0L;

    //
    private Long oom_subs = 0L;

    //子日志快照
    private Map<String, LogSnapshotBO> subBoMap = new HashMap<String, LogSnapshotBO>();

    public LogSnapshotBO() {
        this(null);
    }

    public LogSnapshotBO(String key) {
        if(key!=null && key.length()>0) {
            this.oriKey = key;
        }
        else{
            key = UUID.randomUUID().toString();
        }
        this.key = key;
    }

    /**
     * 启动快照操作
     * @return
     */
    public LogSnapshotBO start(){
        if(this.startNano!=null)    return this;
        this.startNano = System.nanoTime();
        return this;
    }

    /**
     * 结束快照操作
     * @return
     */
    public LogSnapshotBO commit(){
        if(this.startNano == null)   this.start();
        this.commitNano = System.nanoTime();
        this.stepNano = this.commitNano - this.startNano;
        return this;
    }

    /**
     * 启动子快照
     * @param subKey
     * @return
     */
    public LogSnapshotBO startSub(String subKey){
        LogSnapshotBO subBo = null;
        if(subKey!=null && subKey.length()>0){
            String genKey;
            for(int i=0;;i++){
                genKey = subKey+"."+i;
                if(!this.getSubBoMap().containsKey(genKey)) {
                    subBo = new LogSnapshotBO(genKey);
                    subBo.setOriKey(subKey);
                    break;
                }
            }
        }
        else{
            subKey = UUID.randomUUID().toString();
            subBo = new LogSnapshotBO(subKey);
        }
        subBo.setPreBo(this);
        subBo.setLevel(this.getLevel() + 1);
        subBo.setAliveMode(this.getAliveMode());
        LogSnapshotBO releaseBo = null;
        LogSnapshotBO rootBo = this.findRootBo();
        while(rootBo.sizeOfSubs()>MAX_SUB_SIZE){//尝试释放
            releaseBo = rootBo.releaseSub();
        }
        if(releaseBo!=null && releaseBo.getKey().equals(this.getKey())){//如果移除的是当前BO
            LogSnapshotBO preBo = this.getPreBo();
            subBo.setPreBo(preBo);
            preBo.getSubBoMap().put(subBo.getKey(), subBo);
            preBo.setOom_subs(preBo.getOom_subs()+this.getOom_subs());
        }
        else {
            this.subBoMap.put(subBo.getKey(), subBo);
        }
        return subBo.start();
    }

    /**
     * 结束子快照
     * @param subKey
     * @return
     */
    public LogSnapshotBO commitSub(String subKey){
        LogSnapshotBO subBo = this.findSub(subKey);
        if(subBo==null) return null;
        return subBo.commit();
    }

    public boolean isAlive(){
        return ALIVE.equalsIgnoreCase(this.getAliveMode());
    }

    /**
     * 判断当前快照是否处于事务状态
     * @return  true:处于事务状态 false:非事务状态
     */
    public boolean isRunning(){
        if(this.getStartNano()!=null && this.getCommitNano()==null) return true;
        return false;
    }

    /**
     * 记录日志
     * @param msg       日志内容--可以包含参数占位符{}
     * @param args      内容参数
     * @return
     */
    public String log(String msg, Object... args){
        if(this.startNano==null) this.start();
        Long now = System.nanoTime();
        now = now - this.startNano;
        LogLineBO logLineBO = new LogLineBO(now, msg, args);
        if(!this.isAlive() || msg==null)   return logLineBO.getMsgLine();
        if(this.msgList==null)  this.msgList = new ArrayList<LogLineBO>();
        LogSnapshotBO rootBo = this.findRootBo();
        long size = rootBo.sizeOfMsg();
        if(size>MSG_SIZE) {   //总空间已满，需要释放
            do{
                rootBo.releaseMsg();
            }while(rootBo.sizeOfMsg()>MSG_SIZE);
        }
        this.msgList.add(logLineBO);
        return logLineBO.getMsgLine();
    }

    public LogSnapshotBO findRootBo(){
        if(this.getPreBo()==null || this.getPreBo().getKey().equals(this.getKey())){
            return this;
        }
        return this.getPreBo().findRootBo();
    }

    private long releaseMsg(){
        long releaseSize = 0;
        if(this.getSubBoMap()!=null && !this.getSubBoMap().isEmpty()) {
            for (LogSnapshotBO subBo : this.getSubBoMap().values()) {
                releaseSize = subBo.releaseMsg();
                if (releaseSize > 0) {
                    break;
                }
            }
        }
        if(releaseSize>0)   return releaseSize;
        if(this.getMsgList()!=null && this.getMsgList().size()>0){
            int beforeSize = this.getMsgList().size();
            this.getMsgList().remove(0);
            releaseSize = beforeSize - this.getMsgList().size();
            this.oom_msgs += releaseSize;
        }
        return releaseSize;
    }

    public long sizeOfMsg(){
        long retSize = 0;
        if(this.getMsgList()!=null) retSize += this.getMsgList().size();
        if(this.getSubBoMap()!=null && !this.getSubBoMap().isEmpty()){
            for(LogSnapshotBO subBo: this.getSubBoMap().values()){
                retSize += subBo.sizeOfMsg();
            }
        }
        return retSize;
    }

    /**
     * 子快照数量（含自己）
     * @return
     */
    public long sizeOfSubs(){
        long retSize = 1;//含自己
        if(this.getSubBoMap()!=null && !this.getSubBoMap().isEmpty()){
            for(LogSnapshotBO subBo: this.getSubBoMap().values()){
                retSize += subBo.sizeOfSubs();
            }
        }
        return retSize;
    }

    public LogSnapshotBO releaseSub(){
        LogSnapshotBO retBo = null;
        if(this.getSubBoMap()==null || this.getSubBoMap().isEmpty()){
            return null;
        }
        for(LogSnapshotBO subBo: this.getSubBoMap().values()){
            if(subBo.isLeafBo()){
                this.setOom_subs(this.getOom_subs() + 1 + subBo.getOom_subs());
                this.getSubBoMap().remove(subBo.getKey());
                retBo = subBo;
            }
            else {
                retBo = subBo.releaseSub();
            }
            if(retBo!=null){
                break;
            }
        }
        return retBo;
    }

    private boolean isLeafBo(){
        if(this.getSubBoMap()!=null && this.getSubBoMap().values().size()>0){
            return false;
        }
        return true;
    }

    /**
     * 查找子快照
     * @param subKey
     * @return
     */
    public LogSnapshotBO findSub(String subKey){
        if(subKey==null)    return null;
        return this.getSubBoMap().get(subKey);
    }

    public String toLogString(){
        StringBuffer retBuf = new StringBuffer();
        String levelSpan = "";
        for(int i=0;i<this.getLevel()*4;i++){
            levelSpan += "-";
        }
        retBuf.append((this.getLevel()>0?"|":"")+levelSpan+"# "+this.getKey()+" ("+this.getStepNano()/1000000+"){\r\n");
        String tempSt = "";
        if(this.getOom_subs()>0){
            tempSt += "SUBS ["+this.getOom_subs()+"]  ";
        }
        if(this.getOom_msgs()>0){
            tempSt += "MESSAGES ["+this.getOom_msgs()+"]";
        }
        if(tempSt.length()>0){
            retBuf.append("|"+levelSpan+"---- ... ...RELEASED "+tempSt+" .\r\n");
        }
        for(LogLineBO logLineBO: this.getMsgList()){
            retBuf.append("|"+levelSpan+"---- "+logLineBO.getProcessNano()/1000000+"  "+logLineBO.getMsgLine()+"\r\n");
        }
        if(this.getSubBoMap()!=null && !this.getSubBoMap().isEmpty()){
            for(LogSnapshotBO subBo: this.getSubBoMap().values()){
                retBuf.append(subBo.toLogString());
            }
        }
        retBuf.append("|"+levelSpan+"}\r\n");
        return retBuf.toString();
    }

    public String toShortJSON(){
        StringBuffer retBuf = new StringBuffer();
        retBuf.append("\"key\":\""+this.getKey()+"\"");
        if(this.getStepNano()!=null)	retBuf.append(",\"stepMillisecond\":"+this.getStepNano()/1000000);
        if(this.getOom_msgs()!=null && this.getOom_msgs().longValue()>0)	retBuf.append(",\"oom_count\":"+this.getOom_msgs());
        if(getMsgList()!=null && getMsgList().size()>0){
            StringBuffer tempBuf = new StringBuffer();
            for(LogLineBO logLineBO: getMsgList()){
                tempBuf.append((tempBuf.length()<=0?"":",")+logLineBO.toShortJSON());
            }
            retBuf.append(",\"msgList\":["+tempBuf.toString()+"]");
        }
        else{
            retBuf.append(",\"msgList\":[]");
        }
        if(this.getSubBoMap()!=null && !this.getSubBoMap().isEmpty()){
            StringBuffer tempBuf = new StringBuffer();
            for(LogSnapshotBO subBo: this.getSubBoMap().values()){
                tempBuf.append((tempBuf.length()<=0?"":",")+subBo.toShortJSON());
            }
            retBuf.append(",\"subList\":["+tempBuf.toString()+"]");
        }
        else{
            retBuf.append(",\"subList\":[]");
        }
        if(retBuf.toString().startsWith(","))	retBuf.replace(0, 1, "");
        return "{"+retBuf.toString()+"}";
    }

    public String getAliveMode() {
        return aliveMode;
    }

    public void setAliveMode(String aliveMode) {
        this.aliveMode = aliveMode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOriKey() {
        return oriKey;
    }

    public void setOriKey(String oriKey) {
        this.oriKey = oriKey;
    }

    public Long getStepNano() {
        return stepNano;
    }

    public void setStepNano(Long stepNano) {
        this.stepNano = stepNano;
    }

    public Long getStartNano() {
        return startNano;
    }

    public void setStartNano(Long startNano) {
        this.startNano = startNano;
    }

    public Long getCommitNano() {
        return commitNano;
    }

    public void setCommitNano(Long commitNano) {
        this.commitNano = commitNano;
    }

    public Map<String, LogSnapshotBO> getSubBoMap() {
        return subBoMap;
    }

    public void setSubBoMap(Map<String, LogSnapshotBO> subBoMap) {
        this.subBoMap = subBoMap;
    }

    public List<LogLineBO> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<LogLineBO> msgList) {
        this.msgList = msgList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LogSnapshotBO getPreBo() {
        return preBo;
    }

    public void setPreBo(LogSnapshotBO preBo) {
        this.preBo = preBo;
    }

    public Long getOom_msgs() {
        return oom_msgs;
    }

    public void setOom_msgs(Long oom_msgs) {
        this.oom_msgs = oom_msgs;
    }

    public Long getOom_subs() {
        return oom_subs;
    }

    public void setOom_subs(Long oom_subs) {
        this.oom_subs = oom_subs;
    }

    private class LogLineBO{

        //最大字数限制
        private static final int MAX_SIZE = 1024;

        //执行时间点--相对 LogSnapshotBO.startNano 的值
        private Long processNano;

        //一行日志内容
        private String msgLine;

        public LogLineBO(Long processNano, String msgLine) {
            this.processNano = processNano;
            this.msgLine = msgLine;
        }

        public LogLineBO(Long processNano, String msgLine, Object... args) {
            this.processNano = processNano;
            String retSt = null;
            if(msgLine==null)   return;
            for(int i=0;i<args.length;i++){
                msgLine = msgLine.replaceFirst("\\{\\}", args[i]==null?"":args[i].toString());
                if(msgLine.indexOf("{}")<0){
                    break;
                }
            }
            if(msgLine.length()>MAX_SIZE){
                msgLine = msgLine.substring(0, MAX_SIZE);
            }
            this.msgLine = msgLine;
        }

        public String toShortJSON(){
            try {
                Long key = this.getProcessNano()==null?null:(this.getProcessNano()/1000000);
                return "{\"" + key + "\": \"" + this.getMsgLine() + "\"}";
            }catch(Exception ex){
                return "{}";
            }
        }

        public Long getProcessNano() {
            return processNano;
        }

        public void setProcessNano(Long processNano) {
            this.processNano = processNano;
        }

        public String getMsgLine() {
            return msgLine;
        }

        public void setMsgLine(String msgLine) {
            this.msgLine = msgLine;
        }
    }
}
