package com.bjpowernode.egov.system.servlets;
/*
 * ����Ͷ������Ϣ��Servlet
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.Investor;
import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.WebUtil;


public class SavaInvestInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("UTF-8"); 
		
		//��ȡ���ύ��Ҫ�����Ͷ������Ϣ��
		//�������ʹ��WebUtil�������еķ������������Щ���ύ����Ϣ���浽һ��investor�����С���߿���Ч��
		/*
		String investname = request.getParameter("investname");
		String country = request.getParameter("country");
		String orgcode = request.getParameter("orgcode");
		String contact = request.getParameter("contact");
		String contactPhone = request.getParameter("contactPhone");
		String email= request.getParameter("email");
		String remark = request.getParameter("remark");
		*/
		
		//����Investor����Ȼ���request�еĴӱ��л�ȡ������ͨ��WebUtil�еķ�����������ƣ�����Invstor������
		Investor inv = new Investor();
		WebUtil.makeRequestToObject(request,inv);
		
		
		
		
		String manager = ((User)request.getSession().getAttribute("user")).getUsercode();
		
		Date nowTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String regdate = sdf.format(nowTime);
	
		
		Connection conn = null; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "insert into t_invest(investname,country,orgcode,contact,contactPhone,email,remark,manager,regdate) "
					+ "values(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, inv.getInvestname());
			ps.setString(2, inv.getCountry());
			ps.setString(3, inv.getOrgcode());
			ps.setString(4, inv.getContact());
			ps.setString(5, inv.getContactPhone());
			ps.setString(6, inv.getEmail()); 
			ps.setString(7, inv.getRemark());
			
			ps.setString(8, manager);
			ps.setString(9, regdate); 
	
		    count = ps.executeUpdate();
		    
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				JdbcUtil.close(conn,ps,rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(count == 1) {
			response.sendRedirect("/01-egov/basicinfo/exoticOrgList.jsp");
			
		}
		
	}

}
