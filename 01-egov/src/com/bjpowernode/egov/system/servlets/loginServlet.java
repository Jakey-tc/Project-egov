package com.bjpowernode.egov.system.servlets;
/*
 * ��֤�˺�����ʵ���˺�������ȷ���ض����û�����
 * �˺�����������ض��򵽵�¼ҳ�档
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
import javax.servlet.http.HttpSession;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.JdbcUtil;



public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public loginServlet() {
  
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1��ȡ�û���������
		String usercode = request.getParameter("usercode");
		String userpsw = request.getParameter("userpsw");
		String orgtype = request.getParameter("orgtype");
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//�趨һ��������ʾ��¼�Ƿ�ɹ�
		boolean loginSuccess = false;
		
		try {
			 conn = JdbcUtil.getConnection();
			 String sql = "select * from t_user where usercode = ? and userpsw = ? and orgtype = ?";
			 ps = conn.prepareStatement(sql);
			 ps.setString(1, usercode);
			 ps.setString(2, userpsw);
			 ps.setString(3, orgtype);
			 rs = ps.executeQuery();
			 
			 if(rs.next()) {
				 
				 //����User���󣬰��û���Ϣ�洢����ߡ�Ȼ�������û��������session�������У��������ҳ�治�ر�ʱ���ܹ����κ���ҳ���ʡ�
				 User user = new User();
				 user.setUsercode(rs.getString("usercode"));
				 user.setUserpsw(rs.getString("userpsw"));
				 user.setOrgtype(rs.getString("orgtype"));
				 
				 HttpSession session = request.getSession();
				 session.setAttribute("user",user);
				 
				 //���ñ�����ʾ��¼�ɹ�
				 loginSuccess = true;
				 
				
			 }
		} catch (ClassNotFoundException | SQLException e) {
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
		
		if(loginSuccess) {
			
			 //������ҵ�����Ӧ����Ϣ��loginSuccessΪtrue�����ض�����ҳ�棬Ȼ��ǵ�return�� 
			 response.sendRedirect("main.html");
			
		}else {
			
			 //���������ݿ��в��ҷ����û�������˺�������Ϣ�����û���ҵ���loginSuccessΪfalse��
			 //�Ѵ�����Ϣ�洢��request��Χ�У�Ȼ������ת������¼ҳ�����û��������롣
			 String errorMsg = "�����֤ʧ�ܣ������µ�¼";
			 request.setAttribute("errorMsg", errorMsg);
			 //����ת���ĵ�ַд��/��ʾ��ת�����õĻ�ӭ���棬Ҳ����login.jsp
			 request.getRequestDispatcher("/").forward(request,response);
		}
		
	
		
		
	}

}
