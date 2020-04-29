package com.bjpowernode.egov.system.utils;
/*
 * 日期工具类，
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	//获取当前的日期和时间
	public static String format(Date date,String pattern) {
		
		return new SimpleDateFormat(pattern).format(date);
	}
}
