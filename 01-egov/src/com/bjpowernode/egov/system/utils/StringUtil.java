package com.bjpowernode.egov.system.utils;

import java.util.ResourceBundle;

public class StringUtil {
	
	//�����ʱ�󶨻������������ļ���Դ
	private static ResourceBundle bundle = ResourceBundle.getBundle("com.bjpowernode.egov.resource.Message_CN");
	
	private StringUtil() {
		
		
	}
	//��Message_CN.properties�������������ļ��ķ���
	public static String getTextByCode(String code) {
		
		
		return bundle.getString(code);
	}
	
	//��Country_CN.properties����ͱ��������ļ��ķ���
	public static String getCountryByCode(String code) {
		
		ResourceBundle bundle2 = ResourceBundle.getBundle("com.bjpowernode.egov.resource.Country_CN");
		return bundle2.getString(code);
	}
	
	public static String getCountryCode(String a,String b) {
		
		ResourceBundle bundle2 = ResourceBundle.getBundle("com.bjpowernode.egov.resource.Country_CN");
		return bundle2.getString(a + b);
	}
	
	
	//��֤�ַ��Ƿǿյķ���
	public static boolean isNotEmpty(String s) {
		
		return s.trim().length() != 0 && s != null;
	}

}
