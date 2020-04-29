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
		
		//获取得到的数据并封装到Map集合中
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
		
		//执行Service中的方法获取page对象,然后把page对象放入request属性空间中
		Page page = pageQueryEnService.query(selectInfo);
		
		request.setAttribute("page", page);
		
		
		//使用Page对象的信息跳转到指定的分页查询页面
		request.getRequestDispatcher("/auth/orgcodeSelect.jsp").forward(request,response);
		
		
		
		
	}

}
