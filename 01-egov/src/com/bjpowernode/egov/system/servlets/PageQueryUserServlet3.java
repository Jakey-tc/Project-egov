package com.bjpowernode.egov.system.servlets;
/*
 * ʹ�÷�װPage����ķ������������ҳ��ѯ
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
		
		//����һ��Page����,�ѵ�ǰҳ�뵱ǰ�������롣
		Page<User> page = new Page<User>(request.getParameter("pageno"));
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from t_user order by regdate desc";
			//ͨ��Page���е�getSql(sql)������ȡҵ��sql���
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
			
			//ִ�в�ѯ�ܼ�¼������sql���
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
		
		//��page����洢��request��������
		request.setAttribute("page",page);
			
		request.getRequestDispatcher("system/user.jsp").forward(request,response);
		
	
		

	}

}
