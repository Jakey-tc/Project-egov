package com.bjpowernode.egov.system.utils;
import java.lang.reflect.Method;
import java.util.Enumeration;

/*
 * ͨ��������ưѱ��ύ�����ݱ��浽һ��javabean������
 * ʹ�ô˹�������makeRequestToObject������������
 *						*���õ�javabean����������һ��Ҫ��String.class���͵�
 *						*���뱣֤javabean�е��������ƺ��ύ���е�nameֵһ�� 	
 *
 */
import javax.servlet.http.HttpServletRequest;

import com.bjpowernode.egov.beans.Investor;

public class WebUtil {
	
	//��������ѱ��ύ��request�е����ݱ��浽javebean��Ķ�����ȥ��
	public static void makeRequestToObject(HttpServletRequest request,Object obj) {
		
		//ͨ�������ȡjavabean�������
		Class c = obj.getClass();
		
		//��ȡ���еı��ύ�����ı�ǩ��nameֵ
		Enumeration<String> fieldNames = request.getParameterNames();
		//ѭ��ʹ�÷�����Ƶ���javabean���е�set������javabean�����е����Ը�ֵ
		while(fieldNames.hasMoreElements()) {
			
			//��ȡÿһ���������������ȡ���Ǳ��ύ�����ı�ǩ��nameֵ
			String fieldName = fieldNames.nextElement();
			
			//ͨ�������ȡ��ÿһ������������ϳ�javabean�и����Ը�ֵ�ķ���������setter����������
			//���Ҫ����е�nameֵ��javabean�е�������һ�¡�
			String methodName = "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
									
			try {
				//��ȡ������
				Method setMethod = c.getDeclaredMethod(methodName,String.class);
				//���øոյõ��ķ���������ֵ��ֵ��
				setMethod.invoke(obj, request.getParameter(fieldName));
			} catch (Exception e) {
				//������ܷ������������ڵ��쳣�������������������checkpsw�ı�ǩ�У����setCheckpsw�����ǲ�������
				//User�����еģ����Կ��Բ����쳣ԭ���ӡ���������������д�����Բ�Ҫ��
				//ע��try...catch�����쳣ʱ��Ҫ��whileѭ�������ˣ���Ϊ����쳣������ԭ����ĳ�����õķ���������
				//��ʱ����Ҫ����ִ��whileѭ����������ķ�����
				e.printStackTrace();
			}						
			
		}
		
	}
}
