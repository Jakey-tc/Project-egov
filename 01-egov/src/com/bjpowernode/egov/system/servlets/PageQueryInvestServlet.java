package com.bjpowernode.egov.system.servlets;
/*
 * �û������ѯ��Ϣ������ѯ��ť������Servlet�������ѯͶ������Ϣ�����з�ҳ�����Servlet
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

import com.bjpowernode.egov.beans.Investor;
import com.bjpowernode.egov.beans.Page;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.StringUtil;


public class PageQueryInvestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//���������������
		//request.setCharacterEncoding("UTF-8");
		//��ȡ�û����в�ѯ��������Ϣ
		String investcode = request.getParameter("investCode");
		String investname = request.getParameter("investName");
		String begindate = request.getParameter("startDate");
		String enddate = request.getParameter("endDate");
		
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//������ҳ���󣬴���ҳ�����
		Page page = new Page(request.getParameter("pageno"));
		
		
		try {
			conn = JdbcUtil.getConnection();
			
			//ʹ��StringBuffer����������һ��Sql��䣬�������ű�����Ӳ�ѯ��Ҫ��ʾ�����е�Ͷ���˵���Ϣ��
			StringBuffer sql = new StringBuffer("select i.investname,i.investcode,i.regdate,i.country,u.username from t_invest as i join t_user as u on i.manager = u.usercode where 1=1");
			
			//ʹ��StringBuffer���󴴽���ѯ��¼��������sql��䡣
			StringBuffer totalsizeSql = new StringBuffer("select count(*) as totalsize from t_invest as i join t_user as u on i.manager = u.usercode where 1=1");
			List<String> paramList = new ArrayList<String>();
			
			//�����ȡ��investcode��Ϊ�գ�����������ѯ�������ϱߵ�sql����У�ע��andǰ����Ͽո�
			if(StringUtil.isNotEmpty(investcode)) {
				
				sql.append(" and investcode = ?");
				totalsizeSql.append(" and investcode = ?");
				//�������������뵽��sql����where�ؼ����У��Ͱ�������һ��List�����С��������ʹ��ps.setString();�����á������е�ֵ
				paramList.add(investcode);
			} 
			//ͬ�ϣ�����ʹ��ģ����ѯ�ؼ��֡�like��
			if(StringUtil.isNotEmpty(investname)) {
						
				sql.append(" and investname like ?");
				totalsizeSql.append(" and investname like ?");
				paramList.add("%" + investname + "%");
			}
			//ͬ�ϣ�ע������ʹ�õ���regdate���ڵ����û��������ʼ���ڡ�
			if(StringUtil.isNotEmpty(begindate)) {
				
				sql.append(" and i.regdate >= ?");
				totalsizeSql.append(" and i.regdate >= ?");
				paramList.add(begindate);
			}
			//ͬ�ϣ�ע������ʹ�õ���regdateС�ڵ����û�����Ľ������ڡ�
			if(StringUtil.isNotEmpty(enddate)) {
				
				sql.append(" and i.regdate <= ?");
				totalsizeSql.append(" and i.regdate <= ?");
				paramList.add(enddate);
			}
			
			
			//Ԥ�����ѯ�ܼ�¼������sql���
			//�����ִ��������ѯ�ܼ�¼������sql��䣬��Ϊ�ں�ߵ�sql�����
			//��Ҫ�õ�page�����е�totalsize���ܼ�¼������������ǰҳ��ʾ��¼���������ֵ��
			
			ps = conn.prepareStatement(totalsizeSql.toString());
			//����List���ϸ�"?"��ֵ
			for(int i=0;i<paramList.size();i++) {
				
				ps.setString(i + 1, paramList.get(i));
			}
			//ִ��sql��䣬�����ѯ����������ѻ�ȡ�����ܼ�¼�����洢��Page�����totalsize��ȥ��
			rs = ps.executeQuery();
			if(rs.next()) {
				
				page.setTotalsize(rs.getInt("totalsize"));
			}
			
					
			
			//ƴ��sql��䣬��Ϊ��ҳ��ѯ��sql��䡣ֻ��ʾ��ǰҳ��ʾ��Ͷ���˵���Ϣ��
			String querySql = page.getSql2(sql.toString());
			ps = conn.prepareStatement(querySql);
			//ѭ������������ֵ������List����
			for(int i=0;i<paramList.size();i++) {
				
				ps.setString(i + 1, paramList.get(i));
				
				//���������Ϊ������û�����Ĳ�ѯ��Ϣ��������֤�û�����Ĳ�ѯ��Ϣ�Ƿ񱻻�ȡ����
				//System.out.println(paramList.get(i));
			}
			//ִ��sql��䣬�����ѯ�������������Ͷ����Investor���󣬰���Ҫ��ʾ����Ϣ���浽�����У�Ȼ���Ͷ���˶������Page�����List�����С�
			rs = ps.executeQuery();
			while(rs.next()) {
				
				Investor investor = new Investor();
				investor.setCountry(rs.getString("country"));
				investor.setInvestname(rs.getString("investname"));
				investor.setInvestcode(rs.getString("Investcode"));
				investor.setRegdate(rs.getString("regdate"));
				investor.setManager(rs.getString("username"));
				
				page.getUserList().add(investor);
			}
			
	
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
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
		//��Page�������request��Χ��ȥ��Ȼ������ת������ҳ��ѯͶ������Ϣ��ҳ�档
		request.setAttribute("page", page);
		
		String goPath = request.getParameter("Path");
		request.getRequestDispatcher(goPath).forward(request,response);
	}

}
