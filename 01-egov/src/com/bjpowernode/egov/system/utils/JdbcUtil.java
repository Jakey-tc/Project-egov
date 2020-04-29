package com.bjpowernode.egov.system.utils;
/*
 * 连接mysql数据库的工具类
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {
	
	private static Connection conn;
	
	static {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
	
		String mysql = "jdbc:mysql://localhost:3306/egov?serverTimezone=UTC";
		String user = "root";
		String password = "351997";
		conn = DriverManager.getConnection(mysql,user,password);
		
		return conn;
	}
	
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException {
		
		if(conn!=null&&!conn.isClosed()) {
			
			conn.close();
		}
		
		if(ps!=null&&!ps.isClosed()) {
			
			ps.close();
		}
		
		if(rs!=null&&!rs.isClosed()) {
			rs.close();
		}
	}
	
	
}
