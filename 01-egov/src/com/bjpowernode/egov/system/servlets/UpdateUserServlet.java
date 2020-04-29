package com.bjpowernode.egov.system.servlets;
/*
 * ��ȡ�޸ĵ��û���Ϣ�������ݿ���ʵ���޸ġ�Ȼ���ض��򵽷�ҳ��ѯ��Servlet����Ҫ�ص��ո��޸Ĺ����û�����һҳ��
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.system.utils.JdbcUtil;


public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//�����ַ���ʽ���������
		//request.setCharacterEncoding("UTF-8");
		
		//��ȡ���ύ���û���Ϣ
		String usercode = request.getParameter("hiddenUsercode");
		String username= request.getParameter("username");
		String userpsw= request.getParameter("userpsw");
		String orgtype = request.getParameter("orgtype");
		
		//��ȡuserUpdate.jsp���������е���Ϣ����ҳ��pageno��
		String pageno = request.getParameter("pageno");
		
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		
		try {
	
			conn = JdbcUtil.getConnection();
			String sql = "update t_user set username = ?, userpsw = ?, orgtype = ? where usercode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, userpsw);
			ps.setString(3, orgtype);
			ps.setString(4, usercode);
			
			count = ps.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				JdbcUtil.close(conn, ps, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//���sql���ִ�гɹ������ݳɹ����ģ��ض��򵽷ִ�ҳ��ѯҳ�棬ͬʱ��һ��pagenoҳ��Ĳ�����
		//��ҳ���ܹ���ת���ո��޸���Ϣ���û����ڵ�ҳ��ҳ�档
		if(count == 1) {
			
			response.sendRedirect("pageQueryUserServlet3?pageno=" + pageno);
		}
		
	
		
		
		
	}

}
