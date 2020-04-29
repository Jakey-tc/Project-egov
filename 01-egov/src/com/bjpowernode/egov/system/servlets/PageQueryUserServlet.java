package com.bjpowernode.egov.system.servlets;
/*
 * �߼���ѯ��Servlet��һ�㲻ʹ�����ֲ�ѯ��ʽ
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
import javax.servlet.http.HttpSession;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.JdbcUtil;


public class PageQueryUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			//1.��ȡҳ�롢
			int pageno = request.getParameter("pageno") == null ? 1 :Integer.parseInt(request.getParameter("pageno"));
			//2.��ȡsession
			HttpSession session = request.getSession();
			//3.��session�л�ȡ��List����
			List<User> bigList = (List<User>)session.getAttribute("bigList");
			//4.������صĴ�List����Ϊnull���������ݿ�
			if(bigList == null) {
				
				bigList = new ArrayList<User>();
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				
				try {
					conn = JdbcUtil.getConnection();
					String sql = "select usercode,username,orgtype from t_user order by regdate desc";
					ps = conn.prepareStatement(sql);
					//5.ִ�в�ѯ��䷵�ؽ����
					rs = ps.executeQuery();
					while(rs.next()) {
						//6.�����������װ����
						User user = new User();
						user.setUsercode(rs.getString("usercode"));
						user.setUsername(rs.getString("username"));
						user.setOrgtype(rs.getString("orgtype"));
						
						bigList.add(user);
					}
					//7.����List���ϴ洢��session�����С���һ���͵������Ƕ�Ӧ�ġ���һ�δ������ʱ����������ȡ����bigList��null��
					//�����߲�ִ�к�bigList�Ѿ����洢��session���ˡ�ֻҪ���ر���������ٴη������Servlet���������ͻ�session��
					//��ȡ�����bigList���Ӷ�����Ҫ�ٴ��������ݿ��ˡ�
					session.setAttribute("bigList",bigList);
					
					
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
				
			}
			
		
			//8.����ҳ��Ӵ�List������ȡ��СList����
			List<User> userList = new ArrayList<User>();
			//����ÿһҳ�����ʾ�����û����ݡ�
			int pagesize = 3;
			//����bigListѭ��ʱ�Ŀ�ʼ�������뵱ǰҳ��ҳ���ҳ����ʾ��������ݸ����й�
			int beginIndex = (pageno-1) * pagesize;
			//����bigListѭ��ʱ����������Ҳ�뵱ǰҳ��ҳ���ҳ����ʾ��������ݸ����йء��Ҳ��ܳ���bigList�����������
			int bigIndex = pageno * pagesize > bigList.size() ? bigList.size() : pageno * pagesize;
			for(int i = beginIndex;i<bigIndex;i++ ) {
				
				//����bigList���ѷ�Χ�����ڵ����ݴ洢��userList�С�
				userList.add(bigList.get(i));
			}
			
			//9.��СList���ϴ洢��request��Χ��
			request.setAttribute("userList",userList);
			//10.ת��
			request.getRequestDispatcher("/system/user.jsp").forward(request,response);
	
	}
	
	

}
