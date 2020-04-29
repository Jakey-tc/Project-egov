package com.bjpowernode.egov.system.utils;


/*
 * MVC�ܹ�����mysql���ݿ�Ĺ�����
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil2 {
	
	private static Connection conn;
	
	//����һ��ThreadLocal���͵Ķ��������ѵ�ǰ�̺߳�Connection�������һ��ע����static��̬�ģ���Ϊֻ��Ҫһ��
	//ThreadLocal������ͬһ���̵߳Ĳ�ͬ������ʹ��Connection����ʱҪ�����Connection������ͬһ��������ThreadLocalҲ
	//Ҫ��ͬһ����
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	static {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
	
		String mysql = "jdbc:mysql://localhost:3306/egov";
		String user = "root";
		String password = "351997";
		
		//������ͨ��ThreadLocal�����ȡ�󶨵�Connection��������������û�б��󶨵�ThreadLocal�У������´�
		//��Ŀ��һ��ִ�����ҵ��ʱ��������Ҫ�������ķ�����ȡConnection����Ȼ������ǰ�����������������ҵ�����ٴ�
		//ʹ�����Connection����
		conn = threadLocal.get();
		if(conn == null) {
			
			conn = DriverManager.getConnection(mysql,user,password);
			//��Connection����ThreadLocal�����Map������
			threadLocal.set(conn);
		}
	
		
		return conn;
	}
	
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException {
		
		if(conn!=null&&!conn.isClosed()) {
			
			conn.close();
			//�����ǰ�߳���Connection���Ӷ���İ󶨡��������ÿһ��ҵ��ִ����Ϻ�conn����رպ�ִ�С�
			//�����������ӣ���ô��Connection����close���������ʵ�ʱ������ˣ����ǻ�����ThreadLocal������
			//�´�ʹ��ʱ����ʹ�����Connection���󣬵��³���
			threadLocal.remove();
		}
		
		if(ps!=null&&!ps.isClosed()) {
			
			ps.close();
		}
		
		if(rs!=null&&!rs.isClosed()) {
			rs.close();
		}
	}
	
	
}
