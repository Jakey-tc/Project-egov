package com.bjpowernode.egov.system.servlets;
/*
 * 
 * 
 * ����û���Servlet
 * �Ѿ���ΪMVC�ܹ�ģʽ
 * ��Servlet����Control��
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.service.IUserService;
import com.bjpowernode.egov.system.service.UserServiceImpl;
import com.bjpowernode.egov.system.utils.Const;
import com.bjpowernode.egov.system.utils.DateUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.WebUtil;


public class InsertUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
    public InsertUserServlet() {
       
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int count = 0;
		//����User����
		User user = new User();
		//���ύ���������ݱ��浽User������
		WebUtil.makeRequestToObject(request, user);
		
		//��������Service�����Ȼ�����Service���ҵ�񷽷���
		IUserService userService = new UserServiceImpl();
		//ִ��Service��ı����û����ݵķ���������һ��int�������֣������жϴ�ҵ���Ƿ�ִ�гɹ���
		count = userService.save(user);
		
		//���countΪ1����ʾ�����û����ݵ�ҵ��ִ�гɹ�����ʱ�ض��򵽷�ҳ��ѯҳ��
		if(count == 1) {
			//�û���Ϣ����ɹ��������ݿ�,��ô�ض��򵽷�ҳ��ѯ��Servlet,���ɷ�ҳ��ѯ��Servlet��ת��user.jsp.
			//���ֱ����ת��user.jsp����ֿ�ָ���쳣,��Ϊ��ʱû��userList���϶���.
			response.sendRedirect("pageQueryUserServlet3");
		}
		else {
			//�����Ϣ����ʧ�ܣ�����������usercode�ظ��ˣ���Ϊusercode�ֶ��������������ظ������ض�������û������������롣
			//�Ѵ�����Ϣ���浽request�������С�
			String errorMsg = "�û������Ѿ����ڣ�����������";
			request.setAttribute("errorMsg", errorMsg);
			request.getRequestDispatcher("system/userAdd.jsp").forward(request,response);
			System.out.println("ִ�а�˹��������");
		}
	}

}
