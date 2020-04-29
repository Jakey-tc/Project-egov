package com.bjpowernode.egov.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.Auth;
import com.bjpowernode.egov.system.service.AuthServiceImpl;
import com.bjpowernode.egov.system.service.IAuthService;

public class AuthViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		IAuthService authservice = new AuthServiceImpl();
		String authno = request.getParameter("authno");
		
		Auth auth = authservice.getAuthByAuthno(authno);
		
		request.setAttribute("auth", auth);
		
		request.getRequestDispatcher("authresponse/authResponseView.jsp").forward(request,response);
		
		
	}

}
