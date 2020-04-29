package com.bjpowernode.egov.system.servlets;
/*
 * 验证账号密码实现账号密码正确则重定向到用户界面
 * 账号密码错误则重定向到登录页面。
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
import javax.servlet.http.HttpSession;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.JdbcUtil;



public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public loginServlet() {
  
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取用户名和密码
		String usercode = request.getParameter("usercode");
		String userpsw = request.getParameter("userpsw");
		String orgtype = request.getParameter("orgtype");
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//设定一个变量表示登录是否成功
		boolean loginSuccess = false;
		
		try {
			 conn = JdbcUtil.getConnection();
			 String sql = "select * from t_user where usercode = ? and userpsw = ? and orgtype = ?";
			 ps = conn.prepareStatement(sql);
			 ps.setString(1, usercode);
			 ps.setString(2, userpsw);
			 ps.setString(3, orgtype);
			 rs = ps.executeQuery();
			 
			 if(rs.next()) {
				 
				 //创建User对象，把用户信息存储在里边。然后把这个用户对象放在session属性域中，这个域在页面不关闭时都能够在任何网页访问。
				 User user = new User();
				 user.setUsercode(rs.getString("usercode"));
				 user.setUserpsw(rs.getString("userpsw"));
				 user.setOrgtype(rs.getString("orgtype"));
				 
				 HttpSession session = request.getSession();
				 session.setAttribute("user",user);
				 
				 //设置变量表示登录成功
				 loginSuccess = true;
				 
				
			 }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				JdbcUtil.close(conn,ps,rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(loginSuccess) {
			
			 //如果查找到了相应的信息，loginSuccess为true，就重定向到主页面，然后记得return。 
			 response.sendRedirect("main.html");
			
		}else {
			
			 //上面在数据库中查找符合用户输入的账号密码信息，如果没有找到，loginSuccess为false。
			 //把错误信息存储到request范围中，然后请求转发到登录页面让用户重新输入。
			 String errorMsg = "身份验证失败，请重新登录";
			 request.setAttribute("errorMsg", errorMsg);
			 //请求转发的地址写成/表示跳转到设置的欢迎界面，也就是login.jsp
			 request.getRequestDispatcher("/").forward(request,response);
		}
		
	
		
		
	}

}
