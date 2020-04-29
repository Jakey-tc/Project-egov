package com.bjpowernode.egov.system.servlets;
/*
 * 实现退出功能的Servlet：获取session对象，如果不为空，销毁这个session，然后重定向到login.jsp登录页面
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GetOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取session对象，参数是false表示如果没有session对象，不会创建一个新的session
		HttpSession session = request.getSession(false);
		
		//如果session对象不为空
		if(session != null) {
			//销毁session对象，然后重定向到登录页面。
			session.invalidate();
			response.sendRedirect("login.jsp");
		}
	}

}
