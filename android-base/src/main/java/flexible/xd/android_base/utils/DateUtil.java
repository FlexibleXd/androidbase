package flexible.xd.android_base.utils;

import java.text.SimpleDateFormat;

public class DateUtil {
	public static String getCurTime(){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Long time = Long.valueOf( getCurTimeStamp() + "000" );
	    String d = format.format(time);
	    return d;
	}
	public static String getCurTimeStamp(){
		return String.valueOf( System.currentTimeMillis() ).substring( 0, 10 );
	}
	public static String getTimeFromTimeStamp(String l ){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Long time = Long.valueOf( l + "000" );
	    String d = format.format(time);
	    return d;
	}
}
