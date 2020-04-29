package com.bjpowernode.egov.system.servlets;
/*
 * 保存企业信息，以及保存企业和投资人关系表的信息。
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.EnInv;
import com.bjpowernode.egov.beans.Enterprise;
import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.service.EnterpriseServiceImpl;
import com.bjpowernode.egov.system.service.IEnterpriseService;
import com.bjpowernode.egov.system.utils.DateUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.WebUtil;


public class InsertEnvServlet extends HttpServlet {
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IEnterpriseService enService = new EnterpriseServiceImpl();
		
		int count = 0;
		
		Enterprise en = new Enterprise();
		WebUtil.makeRequestToObject(request, en);
		//把经办人放入企业对象中
		en.setManager(((User)request.getSession(false).getAttribute("user")).getUsercode());
		
		//获取多个投资人的投资代码的数组
		String[] investcode  =  request.getParameterValues("investcode");
		
		//获取多个投资人的投资资金的数组
		String[] regcapital = request.getParameterValues("regcapital");
		
		//获取多个投资人的利润比例的数组
		String[] profitRatio = request.getParameterValues("profitRatio");
		
		List EnInvList = new ArrayList();
		for(int i=0;i<investcode.length;i++) {
			
			EnInv eninv = new EnInv();
			eninv.setInvestcode(investcode[i]);
			eninv.setOrgcode(en.getOrgcode());
			eninv.setProfitRatio(profitRatio[i]);
			eninv.setRegcapital(regcapital[i]);
			
			EnInvList.add(eninv);
				
		}
		
	    count = enService.save(en, EnInvList);
		
		
		
		//如果成功执行了企业信息的保存和企业投资人信息的保存，那么这个事务执行成功。
		if(count == 1 + regcapital.length) {
			
			response.sendRedirect("/01-egov/foreignExchange/newInputOrg.jsp");
		}
		
	}

}
