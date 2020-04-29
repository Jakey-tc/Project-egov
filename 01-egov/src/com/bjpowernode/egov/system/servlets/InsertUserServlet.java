package com.bjpowernode.egov.system.servlets;
/*
 * 
 * 
 * 添加用户的Servlet
 * 已经改为MVC架构模式
 * 本Servlet就是Control层
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
		//创建User对象
		User user = new User();
		//把提交上来的数据保存到User对象中
		WebUtil.makeRequestToObject(request, user);
		
		//创建秘书Service层对象，然后调用Service层的业务方法。
		IUserService userService = new UserServiceImpl();
		//执行Service层的保存用户数据的方法。返回一个int类型数字，用来判断此业务是否执行成功。
		count = userService.save(user);
		
		//如果count为1，表示保存用户数据的业务执行成功，此时重定向到分页查询页面
		if(count == 1) {
			//用户信息如果成功存入数据库,那么重定向到分页查询的Servlet,再由分页查询的Servlet跳转到user.jsp.
			//如果直接跳转到user.jsp会出现空指针异常,因为此时没有userList集合对象.
			response.sendRedirect("pageQueryUserServlet3");
		}
		else {
			//如果信息保存失败（可能是由于usercode重复了，因为usercode字段是主键，不能重复），重定向到添加用户界面重新输入。
			//把错误信息保存到request属性域中。
			String errorMsg = "用户代码已经存在，请重新输入";
			request.setAttribute("errorMsg", errorMsg);
			request.getRequestDispatcher("system/userAdd.jsp").forward(request,response);
			System.out.println("执行阿斯顿撒旦撒");
		}
	}

}
