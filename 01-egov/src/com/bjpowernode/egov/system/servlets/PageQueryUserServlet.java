package com.bjpowernode.egov.system.servlets;
/*
 * 逻辑查询的Servlet，一般不使用这种查询方式
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
		
			//1.获取页码、
			int pageno = request.getParameter("pageno") == null ? 1 :Integer.parseInt(request.getParameter("pageno"));
			//2.获取session
			HttpSession session = request.getSession();
			//3.从session中获取大List集合
			List<User> bigList = (List<User>)session.getAttribute("bigList");
			//4.如果返回的大List集合为null则连接数据库
			if(bigList == null) {
				
				bigList = new ArrayList<User>();
				Connection conn = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				
				try {
					conn = JdbcUtil.getConnection();
					String sql = "select usercode,username,orgtype from t_user order by regdate desc";
					ps = conn.prepareStatement(sql);
					//5.执行查询语句返回结果集
					rs = ps.executeQuery();
					while(rs.next()) {
						//6.遍历结果集封装对象
						User user = new User();
						user.setUsercode(rs.getString("usercode"));
						user.setUsername(rs.getString("username"));
						user.setOrgtype(rs.getString("orgtype"));
						
						bigList.add(user);
					}
					//7.将大List集合存储到session对象中。这一步和第三步是对应的。第一次打开浏览器时，第三步获取到的bigList是null。
					//而第七步执行后，bigList已经被存储在session中了。只要不关闭浏览器，再次访问这个Servlet。第三步就会session中
					//获取到这个bigList。从而不需要再次连接数据库了。
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
			
		
			//8.根据页码从大List集合中取出小List集合
			List<User> userList = new ArrayList<User>();
			//定义每一页最多显示三个用户数据。
			int pagesize = 3;
			//定义bigList循环时的开始索引，与当前页的页码和页面显示的最多数据个数有关
			int beginIndex = (pageno-1) * pagesize;
			//定义bigList循环时最后的索引。也与当前页的页码和页面显示的最多数据个数有关。且不能超过bigList的最大索引。
			int bigIndex = pageno * pagesize > bigList.size() ? bigList.size() : pageno * pagesize;
			for(int i = beginIndex;i<bigIndex;i++ ) {
				
				//遍历bigList并把范围索引内的数据存储到userList中。
				userList.add(bigList.get(i));
			}
			
			//9.将小List集合存储到request范围中
			request.setAttribute("userList",userList);
			//10.转发
			request.getRequestDispatcher("/system/user.jsp").forward(request,response);
	
	}
	
	

}
