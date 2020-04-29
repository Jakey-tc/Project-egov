package com.bjpowernode.egov.system.utils;

import java.util.ResourceBundle;

public class StringUtil {
	
	//类加载时绑定机构类型配置文件资源
	private static ResourceBundle bundle = ResourceBundle.getBundle("com.bjpowernode.egov.resource.Message_CN");
	
	private StringUtil() {
		
		
	}
	//绑定Message_CN.properties机构类型配置文件的方法
	public static String getTextByCode(String code) {
		
		
		return bundle.getString(code);
	}
	
	//绑定Country_CN.properties国别和币种配置文件的方法
	public static String getCountryByCode(String code) {
		
		ResourceBundle bundle2 = ResourceBundle.getBundle("com.bjpowernode.egov.resource.Country_CN");
		return bundle2.getString(code);
	}
	
	public static String getCountryCode(String a,String b) {
		
		ResourceBundle bundle2 = ResourceBundle.getBundle("com.bjpowernode.egov.resource.Country_CN");
		return bundle2.getString(a + b);
	}
	
	
	//验证字符是非空的方法
	public static boolean isNotEmpty(String s) {
		
		return s.trim().length() != 0 && s != null;
	}

}
