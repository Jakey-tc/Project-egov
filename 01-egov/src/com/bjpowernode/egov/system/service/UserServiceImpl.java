package com.bjpowernode.egov.system.service;
/*
 * MVC架构中的Model层之一：Service层：Servlet（Model层）的秘书之一。
 * 功能：执行保存用户数据的业务逻辑代码
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.dao.IUserDao;
import com.bjpowernode.egov.system.dao.UserDaoImpl;
import com.bjpowernode.egov.system.utils.Const;
import com.bjpowernode.egov.system.utils.DateUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil2;

public class UserServiceImpl implements IUserService {


	@Override
	public int save(User user) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			//使用新的JDBC工具类获取Connection对象，这里是这个业务第一次获取Connection对象，
			//后面在Dao中获取的Connection对象和这个Connection对象是同一个。
			conn = JdbcUtil2.getConnection();
			
			//设置事务手动提交
			conn.setAutoCommit(false);
			
			//创建Service层的秘书对象：Dao对象
			IUserDao userDao = new UserDaoImpl();
			
			//获取usercode用户代码
			String usercode = user.getUsercode();
		
			//调用Dao层的方法查找用户信息，返回一个User对象，如果返回为null，表示在数据库中没有找到这个User对象。
			User selectUser = userDao.selectByUsercode(usercode);
		
			//如果上边返回的User对象是空的，表面这个用户不存在于数据库中，所以可以新增这个用户。
			if(selectUser==null) {
				
				//执行插入数据库信息的方法，实现用户数据插入到数据库中
				count = userDao.insert(user);
			}
			
			//在业务的所有与数据库交互的任务完成后，提交事务
			conn.commit();
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				//如果发生异常，回滚事务
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			
			try {
				//设置事务自动提交
				conn.setAutoCommit(true);
				//关闭Connection对象
				JdbcUtil.close(conn,null,null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		   
			
	
		return count;
	}

}
