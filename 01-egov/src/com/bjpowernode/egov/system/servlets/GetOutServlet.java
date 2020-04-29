package com.bjpowernode.egov.system.servlets;
/*
 * ʵ���˳����ܵ�Servlet����ȡsession���������Ϊ�գ��������session��Ȼ���ض���login.jsp��¼ҳ��
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
		
		//��ȡsession���󣬲�����false��ʾ���û��session���󣬲��ᴴ��һ���µ�session
		HttpSession session = request.getSession(false);
		
		//���session����Ϊ��
		if(session != null) {
			//����session����Ȼ���ض��򵽵�¼ҳ�档
			session.invalidate();
			response.sendRedirect("login.jsp");
		}
	}

}
