package com.bjpowernode.egov.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.bjpowernode.egov.beans.Auth;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil2;

public class AuthDaoImpl implements IAuthDao {

	@Override
	public int saveAuth(Map<String, String> authMap) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		
		try {
			conn = JdbcUtil2.getConnection();
			String sql = "insert into t_auth(orgcode,remark,contact,contactPhone,capitalDoc,"
					+ "docRemark,manager,feedback) values(?,?,?,?,?,?,?,'0')";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, authMap.get("orgcode"));
			ps.setString(2, authMap.get("remark"));
			ps.setString(3, authMap.get("contact"));
			ps.setString(4, authMap.get("contactPhone"));
			ps.setString(5, authMap.get("capitalDoc"));
			ps.setString(6, authMap.get("docRemark"));
			ps.setString(7, authMap.get("manager"));
			
			count = ps.executeUpdate();
			
			 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public Auth getAuthByAuthno(String authno) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Auth auth = null;
		
		try {
			conn  = JdbcUtil2.getConnection();
			String sql = "select a.contact,a.contactPhone,e.capital,e.currency,e.regdate from t_auth as a join t_enterprise as e on a.orgcode = e.orgcode where a.approvalCode = ?";
			
		
			ps = conn.prepareStatement(sql);
			ps.setString(1, authno);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				auth = new Auth();
				auth.setCapital(rs.getInt("capital"));
				auth.setContact(rs.getString("contact"));
				auth.setContactPhone(rs.getString("contactPhone"));
				auth.setCurrency(rs.getString("currency"));
				auth.setRegdate(rs.getString("regdate"));
				auth.setApprovalCode(authno);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				JdbcUtil2.close(null,ps,rs);
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = JdbcUtil2.getConnection();
			String sql = "update t_auth set feedback='1' where approvalCode = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,Integer.parseInt(authno));
			count = ps.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}

}
