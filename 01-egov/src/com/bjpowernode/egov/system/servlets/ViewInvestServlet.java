package com.bjpowernode.egov.system.servlets;
/*
 * 显示详细投资者信息的Servlet。
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.Investor;
import com.bjpowernode.egov.system.utils.JdbcUtil;

public class ViewInvestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("UTF-8");
		//获取表单提交的investcode标签的value值
		String investcode = request.getParameter("investcode");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Investor investor = null;
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select investname,country,orgcode,contact,contactPhone,email,remark,manager,regdate from t_invest where investcode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,investcode);
			rs = ps.executeQuery();
			if(rs.next()) {
			    investor = new Investor();
				investor.setCountry(rs.getString("country"));
				investor.setInvestname(rs.getString("investname"));
				investor.setOrgcode(rs.getString("orgcode"));
				investor.setContact(rs.getString("contact"));
				investor.setContactPhone(rs.getString("contactPhone"));
				investor.setEmail(rs.getString("email"));
				investor.setRemark(rs.getString("remark"));
				investor.setManager(rs.getString("manager"));
				investor.setRegdate(rs.getString("regdate"));
				
			}
			//把投资者对象存储到request范围中
			request.setAttribute("investor", investor);
			//请求转发到投资人显示页面
			request.getRequestDispatcher("/basicinfo/exoticOrgView.jsp").forward(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
