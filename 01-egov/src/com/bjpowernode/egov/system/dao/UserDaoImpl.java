package com.bjpowernode.egov.system.dao;
/*
 * MVC架构中的Dao层：Service层的秘书。
 * 功能：拥有两个方法，查询用户数据并返回此用户对象，插入用户信息到数据库中
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.utils.Const;
import com.bjpowernode.egov.system.utils.DateUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil2;

public class UserDaoImpl implements IUserDao {

	@Override
	//插入用户数据到数据库中
	public int insert(User user) {
		
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String regTime = DateUtil.format(new Date(), Const.DATE_FORMAT_ALL);
		
		try {
		    conn = JdbcUtil2.getConnection();
			
			
			String sql = "insert into t_user(usercode,username,userpsw,orgtype,regdate) values(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsercode());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getUserpsw());
			ps.setString(4, user.getOrgtype());
			ps.setString(5, regTime);
			count = ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//这里是把Dao中的异常往Service中抛出。原因是当这个业务用事务控制多个数据库交互操作时，如果
			//这个发生了异常没有抛出到Service中，而是被这个Dao捕获了，那么在Service中的控制事务的回滚就不会发生。
			//所以需要抛出这里的异常来提高安全性。
			throw new RuntimeException("出现异常");
		}finally {
			
			try {
				//这里不能关闭Connection对象，因为执行完这个方法后，一个业务（事务）可能还没有完成。
				//在执行其他数据库交互的方法时会用到这个Connection对象。
				//在Service层中也会用到这个Connection对象来操作事务。
				JdbcUtil2.close(null,ps,rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
		//返回count，判断插入用户信息是否成功
		return count;
	}

	/**
	 *根据用户代码usercode查找用户并返回这个用户对象
	 */
	@Override
	//这个方法是查询用户数据并返回用户对象
	public User selectByUsercode(String usercode) {
		
		//创建一个用户对象 
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {			
			conn = JdbcUtil2.getConnection();
			
			//执行查找是否此usercode字段的用户已经存在于数据库中的sql语句
			String sql = "select username,userpsw,regdate,orgtype from t_user where usercode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, usercode);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				user.setOrgtype(rs.getString("orgtype"));
				user.setUsercode(rs.getString("usercode"));
				user.setUsername(rs.getString("username"));
				user.setUserpsw(rs.getString("userpsw"));
				user.setRegdate(rs.getString("regdate"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//这里是把Dao中的异常往Service中抛出。原因是当这个业务用事务控制多个数据库交互操作时，如果
			//这个发生了异常没有抛出到Service中，而是被这个Dao捕获了，那么在Service中的控制事务的回滚就不会发生。
			//所以需要抛出这里的异常来提高安全性。
			throw new RuntimeException("出现异常");
		}finally {
			
			try {
				//同样这里也不能关闭Connection对象。
				JdbcUtil2.close(null, ps, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		}
		
		//返回User用户对象，判断是否查询到了某一个用户的信息。
		return user;
	}

}
