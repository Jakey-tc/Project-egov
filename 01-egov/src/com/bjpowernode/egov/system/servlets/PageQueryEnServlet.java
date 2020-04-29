package com.bjpowernode.egov.system.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.Page;
import com.bjpowernode.egov.system.service.IPageQueryEnService;
import com.bjpowernode.egov.system.service.PageQueryEnServiceImpl;


public class PageQueryEnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IPageQueryEnService pageQueryEnService = new PageQueryEnServiceImpl();
		
		//��ȡ�õ������ݲ���װ��Map������
		String orgcode = request.getParameter("orgcode");
		String cnName = request.getParameter("cnName");
		String regdate = request.getParameter("regdate");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String pageno = request.getParameter("pageno");
		
		Map<String,String> selectInfo = new HashMap<String,String>();
		selectInfo.put("orgcode",orgcode);
		selectInfo.put("chName",cnName);
		selectInfo.put("regdate",regdate);
		selectInfo.put("startDate",startDate);
		selectInfo.put("endDate",endDate);
		selectInfo.put("pageno",pageno);
		
		//ִ��Service�еķ�����ȡpage����,Ȼ���page�������request���Կռ���
		Page page = pageQueryEnService.query(selectInfo);
		
		request.setAttribute("page", page);
		
		
		//ʹ��Page�������Ϣ��ת��ָ���ķ�ҳ��ѯҳ��
		request.getRequestDispatcher("/auth/orgcodeSelect.jsp").forward(request,response);
		
		
		
		
	}

}
