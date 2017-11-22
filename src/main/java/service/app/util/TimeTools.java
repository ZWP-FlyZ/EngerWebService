package service.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeTools {	
	public static String getNow(){
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getNowYM(){
		 SimpleDateFormat sdfYM = 
					new SimpleDateFormat("yyyy-MM");
		return sdfYM.format(new Date());
	}
	
	public static String[] getNowBeforeYM(){
		 SimpleDateFormat sdfYM = 
					new SimpleDateFormat("yyyy-MM");
		 SimpleDateFormat sdfYMD = 
					new SimpleDateFormat("yyyy-MM-dd");
		String [] ss = new String[2];
		Date nd = new Date();
		ss[0] = sdfYMD.format(nd);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nd);
		calendar.add(Calendar.MONTH, -1);
		ss[1] = sdfYM.format(calendar.getTime());
		return ss;
	}
	
	
	public static int compareYMD(String time1,String time2){
		 SimpleDateFormat sdfYMD = 
					new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = sdfYMD.parse(time1);
			Date d2 = sdfYMD.parse(time2);

			if(d1.before(d2)) return -1;
			else if(d1.after(d2)) return 1;
			else return 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return -2;
		
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
		 SimpleDateFormat sdfYM = 
					new SimpleDateFormat("yyyy-MM");
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
		 SimpleDateFormat sdfTenMu = 
					new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
	
	
	public final static int getMaxDayOfMonth(String YM){
		 SimpleDateFormat sdfYM = 
					new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdfYM.parse(YM));
			return c.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
			return 28;
		}
	}
	
	public final static List<String> getPreLoadYMList(){
		List<String> yms = new ArrayList<String>();
		 SimpleDateFormat sdfYM = 
					new SimpleDateFormat("yyyy-MM");
		Date de = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(de);
		c.add(Calendar.MONTH, -2);
		de = c.getTime();
		c.add(Calendar.YEAR, -1);
		while(c.getTime().before(de)||c.getTime().equals(de)){
			yms.add(sdfYM.format(c.getTime()));
			c.add(Calendar.MONTH, 1);
		}
		return yms;
	}
	
	
	
}
