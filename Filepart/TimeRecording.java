package Filepart;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeRecording {
    public static String nowTime(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String nowtime=formatter.format(date);
        return nowtime;
    }
    public static String nowTimeFileName(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date(System.currentTimeMillis());
        String nowtimefilename=formatter.format(date);
        return nowtimefilename;
    }
}
