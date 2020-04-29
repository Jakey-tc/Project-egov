package com.bjpowernode.egov.system.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.bjpowernode.egov.beans.Page;
import com.bjpowernode.egov.system.dao.IPageQueryEnDao;
import com.bjpowernode.egov.system.dao.PageQueryEnDaoImpl;
import com.bjpowernode.egov.system.utils.JdbcUtil2;

public class PageQueryEnServiceImpl implements IPageQueryEnService {

	@Override
	public Page query(Map<String,String> selectInfo) {
		
		IPageQueryEnDao pageQueryEnDao = new PageQueryEnDaoImpl();
		Page page = null;
		
		Connection conn = null;
		try {
			conn = JdbcUtil2.getConnection();
			conn.setAutoCommit(false);
			
		    page = pageQueryEnDao.query(selectInfo);
			
			conn.commit();
		} catch (Exception e) {
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
		
		
		return page;
	}

}
