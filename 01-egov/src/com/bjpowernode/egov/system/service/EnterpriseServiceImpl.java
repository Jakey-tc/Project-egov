package com.bjpowernode.egov.system.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.bjpowernode.egov.beans.Enterprise;
import com.bjpowernode.egov.system.dao.EnterpriseDaoImpl;
import com.bjpowernode.egov.system.dao.IEnterpriseDao;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil2;

public class EnterpriseServiceImpl implements IEnterpriseService{

		
	private IEnterpriseDao enDao = new EnterpriseDaoImpl();
	/*
	 * 保存企业信息的Service方法
	 */
	@Override
	public int save(Enterprise En, List EnInvList) {
		
		
		int count = 0;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = JdbcUtil2.getConnection();
			conn.setAutoCommit(false);
			
			count = enDao.insert(En, EnInvList);
			
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

	/*
	 * 通过组织机构代码orgcode查找企业的详细信息的Service方法
	 */
	@Override
	public Enterprise selectEnByOrgcode(String orgcode) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Enterprise en = null;
		
		try {
			conn = JdbcUtil2.getConnection();
			conn.setAutoCommit(false);
		    en = enDao.selectEnByOrgcode(orgcode);
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
				JdbcUtil2.close(conn, null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return en;
	}

}
