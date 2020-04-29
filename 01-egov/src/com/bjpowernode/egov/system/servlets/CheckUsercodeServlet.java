package com.bjpowernode.egov.system.servlets;
/*
 * ʹ��ajax��get��������usercode�Ƿ��Ѿ����������ݿ��е�Servlet
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
		
		//��ȡģ��get������ȡ�õ���usercode����ֵ
		String usercode = request.getParameter("usercode");
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//����һ����������ʾusercode�Ƿ��ظ�
        boolean hasUsercode = false;
        
        //����������롣
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
		try {
			conn = JdbcUtil.getConnection();
			String sql = "select * from t_user where usercode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, usercode);
			rs = ps.executeQuery();
			//������ҵ������ݣ���ʾ�ظ�
			hasUsercode = rs.next();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//����ظ��ˣ������ʾ���ݣ�����û�ظ��������ʾ����
		if(hasUsercode) {
			
			out.print("<font color='red'>�û������Ѿ����ڣ�����������</font>");
			
			
		}else{
			
			out.print("<font color='green'>�û��������ʹ��</font>");
			
		}
	}

}
