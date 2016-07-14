package lbts.com.web;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by alber.lb on 2016/5/27 0027.
 */
public class DateTs {

    public static void treeSetSortTest(){
        Set<String> retSet = new TreeSet<String>();
        for (int i=10;i>=0;i-=2) {
            retSet.add("T_"+i);
        }
        for (int i=1;i<10;i+=2) {
            retSet.add("T_"+i);
        }
        for (String s: retSet) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
//        Date start = new Date();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(new Date().getTime()-start.getTime());
        treeSetSortTest();
    }
}
