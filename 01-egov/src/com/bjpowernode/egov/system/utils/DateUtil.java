package com.bjpowernode.egov.system.utils;
/*
 * ���ڹ����࣬
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	//��ȡ��ǰ�����ں�ʱ��
	public static String format(Date date,String pattern) {
		
		return new SimpleDateFormat(pattern).format(date);
	}
}
