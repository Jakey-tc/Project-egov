package com.bjpowernode.egov.system.servlets;
/*
 * 使用ajax的get方法检验usercode是否已经存在于数据库中的Servlet
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.system.utils.JdbcUtil;

public class CheckUsercodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取模拟get方法获取得到的usercode属性值
		String usercode = request.getParameter("usercode");
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//定义一个变量。表示usercode是否重复
        boolean hasUsercode = false;
        
        //解决中文乱码。
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from t_user where usercode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, usercode);
			rs = ps.executeQuery();
			//如果查找到了数据，表示重复
			hasUsercode = rs.next();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//如果重复了，输出提示内容，否则没重复，输出提示内容
		if(hasUsercode) {
			
			out.print("<font color='red'>用户代码已经存在，请重新输入</font>");
			
			
		}else{
			
			out.print("<font color='green'>用户代码可以使用</font>");
			
		}
	}

}
