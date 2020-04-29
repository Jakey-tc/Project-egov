package com.bjpowernode.egov.system.servlets;
/*
 * 保存投资者信息的Servlet
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
		
		//获取表单提交的要保存的投资者信息。
		//这里可以使用WebUtil工具类中的反射机制来把这些表单提交的信息保存到一个investor对象中。提高开发效率
		/*
		String investname = request.getParameter("investname");
		String country = request.getParameter("country");
		String orgcode = request.getParameter("orgcode");
		String contact = request.getParameter("contact");
		String contactPhone = request.getParameter("contactPhone");
		String email= request.getParameter("email");
		String remark = request.getParameter("remark");
		*/
		
		//创建Investor对象，然后把request中的从表单中获取的数据通过WebUtil中的方法（反射机制）传入Invstor对象中
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
