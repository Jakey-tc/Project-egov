package com.bjpowernode.egov.system.servlets;
/*
 * 检验企业的组织机构代码是否已经存在的Servlet
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

import com.bjpowernode.egov.system.utils.JdbcUtil;


public class CheckOrgcodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String orgcode = request.getParameter("orgcode");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null ;
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from t_enterprise where orgcode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,orgcode);
			rs = ps.executeQuery();
			if(rs.next()) {
				request.setAttribute("errorMsg","该组织机构代码已经存在，请重新填写。");
				request.getRequestDispatcher("foreignExchange/newInputOrg.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("foreignExchange/inputOrgInfo.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
