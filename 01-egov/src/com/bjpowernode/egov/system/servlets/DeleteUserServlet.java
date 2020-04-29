package com.bjpowernode.egov.system.servlets;
/*
 * ɾ����ѡ��ѡ����û���Ϣ��Servlet����ȡ��ѡ�е��û�������Ϣ��ͨ����Щ�û��Ĳ�����Ϣ�����ݿ���ɾ�������û���Ϣ��
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


public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//����ֻ��ȡ�˸�ѡ��ĵ�һ����ѡ�е����ݡ�û�н��������ݵĻ�ȡ��
		String[] usercode = request.getParameterValues("selects");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		
		
		try {
			conn = JdbcUtil.getConnection();
			//���������ύΪ�ֶ���
			conn.setAutoCommit(false);
			String sql = "delete from t_user where usercode = ?";
			ps = conn.prepareStatement(sql);
			
			for(int i=0;i<usercode.length;i++) {
				
				ps.setString(1, usercode[i]);
			    count += ps.executeUpdate();
			}
			//sql���ִ�гɹ���������ύ
			conn.commit();
	
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(conn != null) {
				
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}finally {
			
			try {
				//����ִ�гɹ��������ύ��ʽΪԭ�����Զ��ύ����Ϊ��Щҵ����Ҫִ�е�sql��䲻��Ҫʹ��������ƣ�һִ�о��ύ�Ϳ��ԡ�
				conn.setAutoCommit(true);
				JdbcUtil.close(conn, ps, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//���sql���ִ�гɹ����ض��򵽷�ҳ��ѯҳ�档
		if(count == usercode.length) {
			
			response.sendRedirect("pageQueryUserServlet3");
			
			return;
		}
	}

}
