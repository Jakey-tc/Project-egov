package com.bjpowernode.egov.system.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.Enterprise;
import com.bjpowernode.egov.system.service.EnterpriseServiceImpl;
import com.bjpowernode.egov.system.service.IEnterpriseService;


public class SelectEnDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IEnterpriseService enService = new EnterpriseServiceImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String orgcode = request.getParameter("orgcode");
		
		Enterprise en = enService.selectEnByOrgcode(orgcode);
		
		request.setAttribute("en", en);
		
		request.getRequestDispatcher("/auth/openAccountAuthDetail.jsp").forward(request,response);
		
		
	}

}
