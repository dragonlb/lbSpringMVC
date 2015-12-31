package lbts.com.util.log;

import lb.com.util.log.LogSnapshotBO;
import org.junit.Test;

/**
 * Created by libing on 2015/12/31.
 */
public class TsLogSnapshort {

    @Test
    public void testMoreSub(){
        System.out.println(".... 1 ....");
        LogSnapshotBO rootBo = new LogSnapshotBO("root").start();
        rootBo.setAliveMode(LogSnapshotBO.ALIVE);
        LogSnapshotBO tempBo = null;
        for(int i=0;i<200;i++){
            LogSnapshotBO subBo;
            if(tempBo==null){
                subBo = rootBo.startSub("sub_"+i);
            }
            else{
                subBo = tempBo.startSub("sub_"+i);
            }
            for(int k=0;k<200;k++) {
                subBo.log("my sub[{}] log ", k);
            }
            subBo.commit();
            tempBo =subBo;
        }
        rootBo.commit();
        System.out.println(rootBo.toLogString());
    }


}
