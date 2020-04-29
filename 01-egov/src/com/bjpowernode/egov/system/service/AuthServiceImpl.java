package com.bjpowernode.egov.system.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.bjpowernode.egov.beans.Auth;
import com.bjpowernode.egov.system.dao.AuthDaoImpl;
import com.bjpowernode.egov.system.dao.IAuthDao;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil2;

public class AuthServiceImpl implements IAuthService {
	
	private IAuthDao authDao = new AuthDaoImpl();
	@Override
	public int saveAuth(Map<String, String> authMap) {
		
		Connection conn = null;
		int count = 0;
		try {
			conn = JdbcUtil2.getConnection();
			conn.setAutoCommit(false);
			
		    count = authDao.saveAuth(authMap);
			
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			
			try {
				conn.setAutoCommit(true);
				JdbcUtil2.close(conn, null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return count;
	}
	
	@Override
	public Auth getAuthByAuthno(String authno) {
		
		Connection conn = null;
		Auth auth = null;
		try {
			conn = JdbcUtil2.getConnection();
			conn.setAutoCommit(false);
			auth = authDao.getAuthByAuthno(authno);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}finally {
			
			try {
				conn.setAutoCommit(false);
				JdbcUtil2.close(conn, null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return auth;
	}

	@Override
	public int feedbackAuth(String authno) {
		
		Connection conn = null;
		int count = 0;
		try {
			conn = JdbcUtil2.getConnection();
			conn.setAutoCommit(false);
			count = authDao.feedbackAuth(authno);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}finally {
			
			try {
				conn.setAutoCommit(true);
				JdbcUtil2.close(conn,null,null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return count;
	}

}
