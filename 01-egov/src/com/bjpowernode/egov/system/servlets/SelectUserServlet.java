package com.bjpowernode.egov.system.servlets;

/*
 * 在修改被选中的用户信息时，通过这个Servlet把提交上来的这条用户的部分信息的所有信息通过数据库查找出来放入request范围中，
 * 并请求转发到userUpdate的jsp页面进行修改信息。
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

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.JdbcUtil;

public class SelectUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SelectUserServlet() {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usercode = request.getParameter("selects");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		// 先定义一个User对象，如果在if里面创建User对象，那么在setAttribute("user",user);时会出错
		User user = null;

		try {
			conn = JdbcUtil.getConnection();
			String sql = "select username,userpsw,orgtype from t_user where usercode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, usercode);
			rs = ps.executeQuery();
			if (rs.next()) {

				user = new User();
				user.setOrgtype(rs.getString("orgtype"));
				user.setUsername(rs.getString("username"));
				user.setUserpsw(rs.getString("userpsw"));
				user.setUsercode(usercode);

			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				JdbcUtil.close(conn, ps, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("user", user);

		request.getRequestDispatcher("system/userUpdate.jsp").forward(request, response);
	}

}
