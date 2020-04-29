package com.bjpowernode.egov.system.utils;


/*
 * MVC架构连接mysql数据库的工具类
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil2 {
	
	private static Connection conn;
	
	//创建一个ThreadLocal类型的对象，用来把当前线程和Connection对象绑定在一起。注意是static静态的，因为只需要一个
	//ThreadLocal对象，在同一个线程的不同对象中使用Connection对象时要求这个Connection对象是同一个，所以ThreadLocal也
	//要是同一个。
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
		
		//在这里通过ThreadLocal对象获取绑定的Connection对象，如果这个对象没有被绑定到ThreadLocal中（即重新打开
		//项目第一次执行这个业务时）。就需要用正常的方法获取Connection对象，然后把他们绑定起来，方便在整个业务中再次
		//使用这个Connection对象。
		conn = threadLocal.get();
		if(conn == null) {
			
			conn = DriverManager.getConnection(mysql,user,password);
			//绑定Connection对象到ThreadLocal对象的Map集合中
			threadLocal.set(conn);
		}
	
		
		return conn;
	}
	
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException {
		
		if(conn!=null&&!conn.isClosed()) {
			
			conn.close();
			//解除当前线程与Connection连接对象的绑定。这个会在每一次业务执行完毕后即conn对象关闭后执行。
			//如果不解除连接，那么在Connection对象被close后，这个对象实际被销毁了，但是还绑定在ThreadLocal对象中
			//下次使用时还会使用这个Connection对象，导致出错
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
