package com.bjpowernode.egov.system.servlets;
/*
 * ������ҵ��Ϣ���Լ�������ҵ��Ͷ���˹�ϵ�����Ϣ��
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
		//�Ѿ����˷�����ҵ������
		en.setManager(((User)request.getSession(false).getAttribute("user")).getUsercode());
		
		//��ȡ���Ͷ���˵�Ͷ�ʴ��������
		String[] investcode  =  request.getParameterValues("investcode");
		
		//��ȡ���Ͷ���˵�Ͷ���ʽ������
		String[] regcapital = request.getParameterValues("regcapital");
		
		//��ȡ���Ͷ���˵��������������
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
		
		
		
		//����ɹ�ִ������ҵ��Ϣ�ı������ҵͶ������Ϣ�ı��棬��ô�������ִ�гɹ���
		if(count == 1 + regcapital.length) {
			
			response.sendRedirect("/01-egov/foreignExchange/newInputOrg.jsp");
		}
		
	}

}
