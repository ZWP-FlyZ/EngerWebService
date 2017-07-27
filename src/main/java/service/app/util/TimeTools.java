package service.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeTools {
	private final static SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfYM = 
						new SimpleDateFormat("yyyy-MM");
	
	
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
	public static List<String> getYMlist(String timeRange){
		List<String> yms = new ArrayList<>();
		 String[] ss = sqlitTimeRange(timeRange);
		try {
			Date ds = sdfYM.parse(ss[0]);
			Date de = sdfYM.parse(ss[1]);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ds);
			while(calendar.getTime().before(de)||
						calendar.getTime().equals(de)){
				yms.add(sdfYM.format(calendar.getTime()));
				calendar.add(Calendar.MONTH, 1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return yms;
	}
	
}
