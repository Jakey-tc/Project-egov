package com.bjpowernode.egov.system.servlets;
/*
 * 删除被选复选框的用户信息的Servlet。获取被选中的用户部分信息，通过这些用户的部分信息再数据库中删除整个用户信息。
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.system.utils.JdbcUtil;


public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//这里只获取了复选框的第一个被选中的数据。没有解决多个数据的获取。
		String[] usercode = request.getParameterValues("selects");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		
		
		try {
			conn = JdbcUtil.getConnection();
			//设置事务提交为手动。
			conn.setAutoCommit(false);
			String sql = "delete from t_user where usercode = ?";
			ps = conn.prepareStatement(sql);
			
			for(int i=0;i<usercode.length;i++) {
				
				ps.setString(1, usercode[i]);
			    count += ps.executeUpdate();
			}
			//sql语句执行成功完后事务提交
			conn.commit();
	
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(conn != null) {
				
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}finally {
			
			try {
				//事务执行成功后设置提交方式为原来的自动提交。因为有些业务需要执行的sql语句不需要使用事务机制，一执行就提交就可以。
				conn.setAutoCommit(true);
				JdbcUtil.close(conn, ps, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//如果sql语句执行成功，重定向到分页查询页面。
		if(count == usercode.length) {
			
			response.sendRedirect("pageQueryUserServlet3");
			
			return;
		}
	}

}
