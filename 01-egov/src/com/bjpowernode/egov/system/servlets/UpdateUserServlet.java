package com.bjpowernode.egov.system.servlets;
/*
 * 获取修改的用户信息，在数据库中实现修改。然后重定向到分页查询的Servlet，且要回到刚刚修改过的用户的那一页。
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.system.utils.JdbcUtil;


public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//设置字符格式，解决乱码
		//request.setCharacterEncoding("UTF-8");
		
		//获取表单提交的用户信息
		String usercode = request.getParameter("hiddenUsercode");
		String username= request.getParameter("username");
		String userpsw= request.getParameter("userpsw");
		String orgtype = request.getParameter("orgtype");
		
		//获取userUpdate.jsp中隐藏域中的信息。即页码pageno。
		String pageno = request.getParameter("pageno");
		
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		
		try {
	
			conn = JdbcUtil.getConnection();
			String sql = "update t_user set username = ?, userpsw = ?, orgtype = ? where usercode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, userpsw);
			ps.setString(3, orgtype);
			ps.setString(4, usercode);
			
			count = ps.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				JdbcUtil.close(conn, ps, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//如果sql语句执行成功，数据成功更改，重定向到分传页查询页面，同时递一个pageno页码的参数，
		//让页面能够跳转到刚刚修改信息的用户所在的页码页面。
		if(count == 1) {
			
			response.sendRedirect("pageQueryUserServlet3?pageno=" + pageno);
		}
		
	
		
		
		
	}

}
