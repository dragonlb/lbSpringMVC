package lbts.com.file;

import lb.com.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alber.lb on 2016/6/17 0017.
 */
public class AccountDiffTest {

    public List<ScheduleInfo> readYueke(){
        File yueke = new File("d:\\Lb\\wk2016\\buf\\task\\siff\\dbbk\\场次信息(2).csv");
        List<ScheduleInfo> retList = new ArrayList<ScheduleInfo>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(yueke));
            int count = 0;
            String lineSt = null;
            while ((lineSt = reader.readLine()) != null){
                ScheduleInfo info = new ScheduleInfo();
                count++;
                if (count==1)   continue;
                lineSt = lineSt.replace("\"", "");
                String[] lineFields = lineSt.split(",");
                info.setId(lineFields[0]);                              //排期ID
                info.setShowId(lineFields[1]);                          //影片ID
                info.setShowName(lineFields[2]);                        //片名
                info.setShowDate(lineFields[3]+" "+lineFields[4]);      //放映时间
                info.setCinemaId(lineFields[5]);                        //影院ID
                info.setCinemaName(lineFields[6]);                      //影院名
                retList.add(info);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retList;
    }

    public List<ScheduleInfo> readTaobao(){
        List<ScheduleInfo> retList = new ArrayList<ScheduleInfo>();
        String[] taobaoNames = {"查询结果.txt","查询结果 (1).txt","查询结果 (2).txt","查询结果 (3).txt","查询结果 (4).txt","查询结果 (5).txt","查询结果 (6).txt"};
        for (String oneName : taobaoNames) {
            retList.addAll(readTaobao(oneName));
        }
        return retList;
    }

    public List<ScheduleInfo> readTaobao(String fileName){
        File taobao = new File("C:\\Users\\alber.lb\\Downloads\\"+fileName);
        List<ScheduleInfo> retList = new ArrayList<ScheduleInfo>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(taobao));
            int count = 0;
            String lineSt = null;
            while ((lineSt = reader.readLine()) != null){
                ScheduleInfo info = new ScheduleInfo();
                count++;
                if (count==1)   continue;
                lineSt = lineSt.replace("\"", "");
                String[] lineFields = lineSt.split(",");
                info.setId(lineFields[0]);
                info.setShowDate(lineFields[1]);
                info.setShowId(lineFields[2]);
                info.setCinemaId(lineFields[3]);
                retList.add(info);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retList;
    }

    public static List<ScheduleInfo> findErrorList(List<ScheduleInfo> oriList, List<ScheduleInfo> paramList) {
        List<ScheduleInfo> errorList = new ArrayList<ScheduleInfo>();
        boolean isError = false;
        for (ScheduleInfo oriInfo : oriList) {
            isError = true;
            for (ScheduleInfo taobaoInfo : paramList) {
                if(taobaoInfo.getId().equals(oriInfo.getId())
                   && taobaoInfo.getShowDate().equals(oriInfo.getShowDate())
                        ){
                    isError = false;
                    break;
                }
            }
            if (isError) {
                errorList.add(oriInfo);
            }
        }
        return errorList;
    }

    public static ScheduleInfo findInfoById(List<ScheduleInfo> aimList, String id) {
        for (ScheduleInfo info: aimList) {
            if (id.equals(info.getId())){
                return info;
            }
        }
        return null;
    }

    public static void main(String[] args){
        AccountDiffTest adt = new AccountDiffTest();
        List<ScheduleInfo> yuekeList = adt.readYueke();
        List<ScheduleInfo> taobaoList = adt.readTaobao();
        List<ScheduleInfo> errorYuekeList = findErrorList(yuekeList, taobaoList);
        List<ScheduleInfo> errorTaobaoList = findErrorList(taobaoList, yuekeList);
        int count = 0;
        Date dateLine = null;
        try {
            dateLine = DateUtil.parseDate("2016-06-17 00:00:00", null);
        } catch (ParseException e) {
            e.printStackTrace();
            dateLine = new Date();
        }
        String errorIds = "";
        for (ScheduleInfo info : errorYuekeList){
//            if(info.getShowDateTime().after(dateLine)){
//                continue;
//            }
            ScheduleInfo opInfo = findInfoById(taobaoList, info.getId());
            errorIds += "'"+info.getId() + "',";
            System.out.println(count+++"\tyueke error: " + info.toString());
            if (opInfo != null) {
                System.out.println("\t\ttaobao info : "+(opInfo == null ? "" : opInfo.toString()));
            }
        }
        System.out.println("yueke error ids : "+ errorIds);
        count = 0;
        errorIds = "";
        for (ScheduleInfo info : errorTaobaoList){
//            if(info.getShowDateTime().before(dateLine)){
//                continue;
//            }
            ScheduleInfo opInfo = findInfoById(yuekeList, info.getId());
            errorIds += "'"+info.getId() + "',";
            System.out.println(count+++"taobao error: " + info.toString());
            if (opInfo != null) {
                System.out.println("\t\tyueke info : " + (opInfo == null ? "" : opInfo.toString()));
            }
        }
        System.out.println("taobao error ids : "+ errorIds);
    }


    public class ScheduleInfo {
        private String id;
        private String showId;
        private String showName;
        private String showDate;
        private String cinemaId;
        private String cinemaName;
        private Date showDateTime;

        @Override
        public String toString() {
            return "ScheduleInfo{" + "id='" + (id == null ? "" : id) + '\'' + ", showId='" + (showId == null ? "" : showId) + '\'' + ", showName='" + (showName == null ? "" : showName) + '\''
                   + ", showDate='" + (showDate == null ? "" : showDate) + '\'' + ", cinemaId='" + (cinemaId == null ? "" : cinemaId) + '\'' + ", cinemaName='" + (cinemaName == null ? "" : cinemaName)
                   + '\'' + '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShowId() {
            return showId;
        }

        public void setShowId(String showId) {
            this.showId = showId;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public String getShowDate() {
            return showDate;
        }

        public void setShowDate(String showDate) {
            this.showDate = showDate;
            try {
                this.showDateTime = DateUtil.parseDate(showDate, null);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public Date getShowDateTime() {
            return showDateTime;
        }

        public void setShowDateTime(Date showDateTime) {
            this.showDateTime = showDateTime;
        }

        public String getCinemaId() {
            return cinemaId;
        }

        public void setCinemaId(String cinemaId) {
            this.cinemaId = cinemaId;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }
    }

}
