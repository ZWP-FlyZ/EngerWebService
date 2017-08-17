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
	
	private final static SimpleDateFormat sdfTenMu = 
			new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static String getNow(){
		return sdf.format(new Date());
	}
	
	public static String getNowYM(){
		return sdfYM.format(new Date());
	}	
	public static String[] sqlitTimeRange(String timeRange){
		if(timeRange==null) return null;
		if(timeRange.contains("&")) return timeRange.split("&");
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
	
	
	public  static  String getTenMinute(String s){
		if(s==null) return null;
		
		return s.substring(0, s.length()-4)+"0";
	}
	
	public static List<String> getTenMuList(String timeRange){
		List<String> tms = new ArrayList<>();
		
		try {
			String[] ss = sqlitTimeRange(timeRange);
			Date ds = sdfTenMu.parse(getTenMinute(ss[0]));
			Date de = sdfTenMu.parse(getTenMinute(ss[1]));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ds);
			while(calendar.getTime().before(de)||
						calendar.getTime().equals(de)){
				tms.add(sdfTenMu.format(calendar.getTime()));
				calendar.add(Calendar.MINUTE, 10);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tms;
	}
	
	
	public static int getMaxDayOfMonth(String YM){
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdfYM.parse(YM));
			return c.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
			return 28;
		}
	}
	
	
	
	
	
}
