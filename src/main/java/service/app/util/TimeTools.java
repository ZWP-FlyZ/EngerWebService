package service.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTools {
	private final static SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getNow(){
		return sdf.format(new Date());
	}
	
	public static String[] sqlitTimeRange(String timeRange){
		return timeRange.split(":");
	}
	public static String getYearMonth(String date){
		if(date==null|| date.length()<7) 
			return null;
		return date.substring(0, 7);
	}
}
