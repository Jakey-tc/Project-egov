package com.bjpowernode.egov.system.servlets;
/*
 * 物理查询的Servlet,一般使用这种查询方式
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
		//1.获取当前页码pageno
		int pageno = request.getParameter("pageno") == null ? 1 : Integer.parseInt(request.getParameter("pageno"));
		
		//设置每一页显示的信息条数为3
		int pagesize = 3;
		
		//定义总信息条数的变量
		int totalsize = 0;
		
		//定义总页数的变量
		int pagecount = 0;
		
		//2.创建userList集合用于存放某一页的用户信息对象。
		List<User> userList = new ArrayList<User>();
		
		//3.执行Mysql特有的limit的sql语句来分页查找
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			//按日期降序把所有用户信息查找出来的sql语句
			String sql = "select usercode,username,orgtype,regdate from t_user order by regdate desc";
			
			//通过上面已经按照日期降序排列的表，来查询当前页需要的信息。这是一个固定写法.可以不轻易改动.
			//注意给上边sql语句查找出来的表给一个别名.
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
			
			//再次执行sql语句，查找所有用户记录的条数。
			pageSql = "select count(*) as totalsize from t_user";
			ps = conn.prepareStatement(pageSql);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				totalsize = rs.getInt("totalsize");
			}
			
			//计算总页数
			pagecount = totalsize % pagesize == 0 ? (totalsize / pagesize) : (totalsize / pagesize +1);
			
			//把当前页码，总页数，总信息条数，每一页显示的数量信息放入request的域属性空间中
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
		
		//4.保存userList到request中
		request.setAttribute("userList",userList);
		//5.请求转发,由于要在user.jsp中使用本Servlet中request域中的参数,所以使用请求转发.这样user.jsp中的request对象和这里的是同一个
		request.getRequestDispatcher("/system/user.jsp").forward(request,response);
		
		
		
	}

}
