package lbts.com.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by libing on 2015/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-config.xml"})
public class StringToolTs {


    private static final Log LOG = LogFactory.getLog(StringToolTs.class);

    @Test
    public void logTest(){
        LOG.debug("Hello LOG of debug.....");
        System.out.println("Hello sysout....");
    }

    @Test
    public void stringTest(){
        String[] ss = {"a", "b", "c", "d"};
        List<String> stList = new ArrayList<String>();
        stList.add("a");
        stList.add("b");
        stList.add("c");
        stList.add("d");
        System.out.println(ss);
        System.out.println(stList);
    }

    @Test
    public void stringToIntTest(){
        String s = "138.00";
        Integer iVal = Double.valueOf(s).intValue();
        System.out.println("iVal="+iVal);
    }

    @Test
    public void charArray(){
        String ts = "0123456789abcdefghijklmnopqrstuvwxyz";
        char[] cs = ts.toCharArray();
        char[] cs2 = ts.toUpperCase().toCharArray();
        char[] aimCs = new char[4];
        Random random = new Random();
        for(int i=0;i<aimCs.length;i++){
            if(random.nextBoolean()){
                aimCs[i] = cs2[random.nextInt(cs.length)];
            }
            else{
                aimCs[i] = cs[random.nextInt(cs.length)];
            }
        }
        System.out.println(new String(aimCs));
    }

    public static void main(String[] args) {
        String s = "138.00";
//        Integer iVal = Double.valueOf(s).intValue();
//        iVal = Integer.valueOf(s);
//        System.out.println("iVal="+iVal);
//        Date start = new Date();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(new Date().getTime()-start.getTime());
    }
}
