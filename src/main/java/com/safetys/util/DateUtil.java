package com.safetys.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期操作
 * @author zhaozhi3758
 */
public class DateUtil {
	public static final String LONG_MODEL = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_MODEL = "yyyy-MM-dd";
	
	/**
	 * 一小时毫秒
	 */
	public static final Long HOUR_MILLIS = 1000L*60*60;
	/**
	 * 一天毫秒
	 */
	public static final Long DAY_MILLIS = HOUR_MILLIS*24;
	
	/**
	 * 时间格式化
	 * @param d
	 * @return
	 */
	public static String dateFormat(Date d){
		SimpleDateFormat sdf=new SimpleDateFormat(LONG_MODEL);
		return sdf.format(d);
	}
	
	/**
	 * 时间格式化
	 * @param d
	 * @return
	 */
	public static String dateFormat(Date d,String model){
		SimpleDateFormat sdf=new SimpleDateFormat(model);
		return sdf.format(d);
	}
	
	/**
	 * 字符串转日期，支持两种格式 1."日期 时间" 2."纯日期"
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToDate(String s) throws ParseException{
		
		DateFormat sdf = new SimpleDateFormat(LONG_MODEL);
		try {
			return sdf.parse(s);
		} catch (ParseException e) {
			DateFormat sdf2 = new SimpleDateFormat(SHORT_MODEL);
			try {
				return sdf2.parse(s);
			} catch (ParseException e2) {}
		}
		return null;
	}
	

	/**
	 * 计算 h个小时后的时间 "以当前时间为基准"
	 * @param h
	 * @return
	 */
	public static Date calculateDate(int h){
		
		return calculateDate(new Date(),h,0);
	}
	
	/**
	 * 计算 h个小时，m分钟后的时间 "以当前时间为基准"
	 * @param h
	 * @param m
	 * @return
	 */
	public static Date calculateDate(int h,int m){
		
		return calculateDate(new Date(),h,m);
	}
	
	/**
	 * 计算 h个小时后的时间 "以fromdate为基准"
	 * @param fromdate
	 * @param h
	 * @return
	 */
	public static Date calculateDate(Date fromdate, int h){
		
		return calculateDate(fromdate,h,0);
	}
	
	/**
	 * 计算 h个小时，m分钟后的时间 "以fromdate为基准"
	 * @param fromdate
	 * @param h
	 * @param m
	 * @return
	 */
	public static Date calculateDate(Date fromdate, int h, int m){
		
		Date date = null; 
		Calendar cal = Calendar.getInstance();  
		cal.setTime(fromdate); 
		cal.add(Calendar.HOUR_OF_DAY, h);
		cal.add(Calendar.MINUTE, m);
		date = cal.getTime(); 
		return date;
	}
	
	/**
	 * 两个时间相差的分钟数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long dateDiff(Date d1,Date d2){
		
		return dateCompare(d1,d2) / 1000 / 60;
	}
	
	/**
	 * 时间大小的比较，返回相差的毫秒数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long dateCompare(Date d1,Date d2){
		Calendar cal = Calendar.getInstance(); 
		Calendar ca2 = Calendar.getInstance(); 
		cal.setTime(d1);
		ca2.setTime(d2);
		long l1 = cal.getTimeInMillis();
		long l2 = ca2.getTimeInMillis();
		return l1 - l2;
	}
	/**
	 * 毫秒转换小时
	 * @param millis
	 * @return
	 */
	public static Integer toHour(Long millis){
		return Long.valueOf(millis/HOUR_MILLIS).intValue();
	}
	/**
	 * 转换成天
	 * @param millis
	 * @return
	 */
	public static Integer toDay(Long millis){
		return Long.valueOf(millis/DAY_MILLIS).intValue();
	}
	
}
