package lb.com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by libing on 2016/3/21.
 */
public class DateUtil {

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static String getFormatDate(Date date, String format) {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat(format==null?YYYYMMDDHHMMSS:format);
            return f.format(date);
        } else {
            return null;
        }
    }

    public static Date parseDate(String dateSt, String format) throws ParseException {
        if (dateSt != null) {
            SimpleDateFormat f = new SimpleDateFormat(format==null?YYYYMMDDHHMMSS:format);
            return f.parse(dateSt);
        } else {
            return null;
        }
    }
}
