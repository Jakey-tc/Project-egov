package com.bjpowernode.egov.system.utils;
import java.lang.reflect.Method;
import java.util.Enumeration;

/*
 * 通过反射机制把表单提交的数据保存到一个javabean对象中
 * 使用此工具类中makeRequestToObject方法的条件：
 *						*设置的javabean的属性类型一定要是String.class类型的
 *						*必须保证javabean中的属性名称和提交表单中的name值一样 	
 *
 */
import javax.servlet.http.HttpServletRequest;

import com.bjpowernode.egov.beans.Investor;

public class WebUtil {
	
	//这个方法把表单提交到request中的内容保存到javebean类的对象中去。
	public static void makeRequestToObject(HttpServletRequest request,Object obj) {
		
		//通过反射获取javabean对象的类
		Class c = obj.getClass();
		
		//获取所有的表单提交上来的标签的name值
		Enumeration<String> fieldNames = request.getParameterNames();
		//循环使用反射机制调用javabean类中的set方法给javabean对象中的属性赋值
		while(fieldNames.hasMoreElements()) {
			
			//获取每一个属性名，这里获取的是表单提交上来的标签的name值
			String fieldName = fieldNames.nextElement();
			
			//通过上面获取的每一个属性名，组合成javabean中给属性赋值的方法名，即setter方法的名字
			//这就要求表单中的name值和javabean中的属性名一致。
			String methodName = "set" + fieldName.toUpperCase().charAt(0) + fieldName.substring(1);
									
			try {
				//获取方法名
				Method setMethod = c.getDeclaredMethod(methodName,String.class);
				//调用刚刚得到的方法给属性值赋值。
				setMethod.invoke(obj, request.getParameter(fieldName));
			} catch (Exception e) {
				//这里可能发生方法不存在的异常，是正常情况，比如在checkpsw的标签中，这个setCheckpsw方法是不存在于
				//User对象中的，所以可以不把异常原因打印出来，即下面这行代码可以不要。
				//注意try...catch处理异常时不要把while循环包括了，因为如果异常发生，原因是某个调用的方法不存在
				//此时还需要继续执行while循环调用下面的方法。
				e.printStackTrace();
			}						
			
		}
		
	}
}
