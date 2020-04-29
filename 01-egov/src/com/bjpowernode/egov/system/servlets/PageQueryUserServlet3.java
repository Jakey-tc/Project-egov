package com.bjpowernode.egov.system.servlets;
/*
 * 使用封装Page对象的方法进行物理分页查询
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

import com.bjpowernode.egov.beans.Page;
import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.JdbcUtil;


public class PageQueryUserServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//创建一个Page对象,把当前页码当前参数传入。
		Page<User> page = new Page<User>(request.getParameter("pageno"));
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from t_user order by regdate desc";
			//通过Page类中的getSql(sql)方法获取业务sql语句
			String pageSql = page.getSql(sql);
			ps = conn.prepareStatement(pageSql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setUsercode(rs.getString("usercode"));
			    user.setUsername(rs.getString("username"));
				user.setUserpsw(rs.getString("userpsw"));
				user.setOrgtype(rs.getString("orgtype"));
				
				page.getUserList().add(user);
			}
			
			//执行查询总记录条数的sql语句
			pageSql = "select count(*) as totalsize from t_user";
			ps = conn.prepareStatement(pageSql);
			rs = ps.executeQuery();
			if(rs.next()) {
				page.setTotalsize(rs.getInt("totalsize"));
				
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				JdbcUtil.close(conn, ps, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//把page对象存储到request属性域中
		request.setAttribute("page",page);
			
		request.getRequestDispatcher("system/user.jsp").forward(request,response);
		
	
		

	}

}
