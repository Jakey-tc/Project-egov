package com.bjpowernode.egov.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.system.service.AuthServiceImpl;
import com.bjpowernode.egov.system.service.IAuthService;


public class FeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String authno = request.getParameter("authno");
		
		IAuthService authservice = new AuthServiceImpl();
		
		int count = authservice.feedbackAuth(authno);
		
		if(count == 1) {
			response.sendRedirect("/01-egov/authresponse/authResponseList.jsp");
		}
	}

}
