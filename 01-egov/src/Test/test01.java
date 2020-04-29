package Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test01 {
	
	public static void main(String[] args) {
		String x = new String("abc");
		String y = new String("abc");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String a  = sdf.format(date);
		
	
		
		System.out.println(x);
		System.out.println(y);
		System.out.println(x.equals(y));
		
		System.out.println(x==y);


	}
}
