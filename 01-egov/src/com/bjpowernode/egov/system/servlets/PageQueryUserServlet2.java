package com.bjpowernode.egov.system.servlets;
/*
 * �����ѯ��Servlet,һ��ʹ�����ֲ�ѯ��ʽ
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.JdbcUtil;

public class PageQueryUserServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ��ǰҳ��pageno
		int pageno = request.getParameter("pageno") == null ? 1 : Integer.parseInt(request.getParameter("pageno"));
		
		//����ÿһҳ��ʾ����Ϣ����Ϊ3
		int pagesize = 3;
		
		//��������Ϣ�����ı���
		int totalsize = 0;
		
		//������ҳ���ı���
		int pagecount = 0;
		
		//2.����userList�������ڴ��ĳһҳ���û���Ϣ����
		List<User> userList = new ArrayList<User>();
		
		//3.ִ��Mysql���е�limit��sql�������ҳ����
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			//�����ڽ���������û���Ϣ���ҳ�����sql���
			String sql = "select usercode,username,orgtype,regdate from t_user order by regdate desc";
			
			//ͨ�������Ѿ��������ڽ������еı�����ѯ��ǰҳ��Ҫ����Ϣ������һ���̶�д��.���Բ����׸Ķ�.
			//ע����ϱ�sql�����ҳ����ı��һ������.
			String pageSql = "select s.usercode,s.username,s.orgtype,s.regdate from (" + sql + ") s limit " + (pageno-1) * pagesize + "," + pagesize;
			ps = conn.prepareStatement(pageSql);
			rs = ps.executeQuery();
			while(rs.next()) {
				
				User user = new User();
				user.setOrgtype(rs.getString("orgtype"));
				user.setUsercode(rs.getString("usercode"));
				user.setUsername(rs.getString("username"));
				user.setRegdate(rs.getString("regdate"));
				
				userList.add(user);
			}
			
			//�ٴ�ִ��sql��䣬���������û���¼��������
			pageSql = "select count(*) as totalsize from t_user";
			ps = conn.prepareStatement(pageSql);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				totalsize = rs.getInt("totalsize");
			}
			
			//������ҳ��
			pagecount = totalsize % pagesize == 0 ? (totalsize / pagesize) : (totalsize / pagesize +1);
			
			//�ѵ�ǰҳ�룬��ҳ��������Ϣ������ÿһҳ��ʾ��������Ϣ����request�������Կռ���
			request.setAttribute("pageno",pageno);
			request.setAttribute("totalsize",totalsize);
			request.setAttribute("pagecount",pagecount);
			request.setAttribute("pagesize", pagesize);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					JdbcUtil.close(conn,ps,rs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			
		}
		
		//4.����userList��request��
		request.setAttribute("userList",userList);
		//5.����ת��,����Ҫ��user.jsp��ʹ�ñ�Servlet��request���еĲ���,����ʹ������ת��.����user.jsp�е�request������������ͬһ��
		request.getRequestDispatcher("/system/user.jsp").forward(request,response);
		
		
		
	}

}
