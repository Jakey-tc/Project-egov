package com.bjpowernode.egov.system.servlets;
/*
 * 用户输入查询信息后点击查询按钮后进入的Servlet，处理查询投资人信息并进行分页处理的Servlet
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
		
		//解决中文乱码问题
		//request.setCharacterEncoding("UTF-8");
		//获取用户进行查询的输入信息
		String investcode = request.getParameter("investCode");
		String investname = request.getParameter("investName");
		String begindate = request.getParameter("startDate");
		String enddate = request.getParameter("endDate");
		
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//创建分页对象，传入页码参数
		Page page = new Page(request.getParameter("pageno"));
		
		
		try {
			conn = JdbcUtil.getConnection();
			
			//使用StringBuffer对象来创建一个Sql语句，进行两张表的连接查询需要显示的所有的投资人的信息。
			StringBuffer sql = new StringBuffer("select i.investname,i.investcode,i.regdate,i.country,u.username from t_invest as i join t_user as u on i.manager = u.usercode where 1=1");
			
			//使用StringBuffer对象创建查询记录总条数的sql语句。
			StringBuffer totalsizeSql = new StringBuffer("select count(*) as totalsize from t_invest as i join t_user as u on i.manager = u.usercode where 1=1");
			List<String> paramList = new ArrayList<String>();
			
			//如果获取的investcode不为空，则加入这个查询条件到上边的sql语句中，注意and前面加上空格。
			if(StringUtil.isNotEmpty(investcode)) {
				
				sql.append(" and investcode = ?");
				totalsizeSql.append(" and investcode = ?");
				//如果这个条件加入到了sql语句的where关键词中，就把它放入一个List集合中。方便后面使用ps.setString();来设置“？”中的值
				paramList.add(investcode);
			} 
			//同上，这里使用模糊查询关键字“like”
			if(StringUtil.isNotEmpty(investname)) {
						
				sql.append(" and investname like ?");
				totalsizeSql.append(" and investname like ?");
				paramList.add("%" + investname + "%");
			}
			//同上，注意这里使用的是regdate大于等于用户输入的起始日期。
			if(StringUtil.isNotEmpty(begindate)) {
				
				sql.append(" and i.regdate >= ?");
				totalsizeSql.append(" and i.regdate >= ?");
				paramList.add(begindate);
			}
			//同上，注意这里使用的是regdate小于等于用户输入的截至日期。
			if(StringUtil.isNotEmpty(enddate)) {
				
				sql.append(" and i.regdate <= ?");
				totalsizeSql.append(" and i.regdate <= ?");
				paramList.add(enddate);
			}
			
			
			//预编译查询总记录条数的sql语句
			//最好先执行这条查询总记录条数的sql语句，因为在后边的sql语句中
			//我要用到page对象中的totalsize即总记录条数，当作当前页显示记录条数的最大值。
			
			ps = conn.prepareStatement(totalsizeSql.toString());
			//借助List集合给"?"赋值
			for(int i=0;i<paramList.size();i++) {
				
				ps.setString(i + 1, paramList.get(i));
			}
			//执行sql语句，如果查询到结果集，把获取到的总记录条数存储到Page对象的totalsize中去。
			rs = ps.executeQuery();
			if(rs.next()) {
				
				page.setTotalsize(rs.getInt("totalsize"));
			}
			
					
			
			//拼接sql语句，成为分页查询的sql语句。只显示当前页显示的投资人的信息。
			String querySql = page.getSql2(sql.toString());
			ps = conn.prepareStatement(querySql);
			//循环给“？”赋值，借助List集合
			for(int i=0;i<paramList.size();i++) {
				
				ps.setString(i + 1, paramList.get(i));
				
				//下面这句是为了输出用户输入的查询信息，用于验证用户输入的查询信息是否被获取到了
				//System.out.println(paramList.get(i));
			}
			//执行sql语句，如果查询到结果集，创建投资人Investor对象，把需要显示的信息保存到对象中，然后把投资人对象放入Page对象的List集合中。
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
		//把Page对象放入request范围中去，然后请求转发到分页查询投资人信息的页面。
		request.setAttribute("page", page);
		
		String goPath = request.getParameter("Path");
		request.getRequestDispatcher(goPath).forward(request,response);
	}

}
