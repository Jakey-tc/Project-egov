package com.bjpowernode.egov.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.bjpowernode.egov.beans.EnInv;
import com.bjpowernode.egov.beans.Enterprise;
import com.bjpowernode.egov.system.utils.DateUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil;
import com.bjpowernode.egov.system.utils.JdbcUtil2;

public class EnterpriseDaoImpl implements IEnterpriseDao {

	/*
	 *  ������ҵ��Ϣ��Dao����
	 */
	@Override
	public int insert(Enterprise en, List<EnInv> EnInvList) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//�����������ִ�гɹ���sql�������
		int count = 0;
		
		
		try {
			conn = JdbcUtil2.getConnection();
			
			//������ҵ��Ϣ
			String sql = "insert into t_enterprise(orgcode,foreignNumber,chName,EnName,contactName,contactPhone,capital,foreignCapital,currency,manager,regdate) values(?,?,?,?,?,?,?,?,?,?,?)";			
			 
			ps = conn.prepareStatement(sql);
			ps.setString(1, en.getOrgcode());
			ps.setString(2, en.getForeignNumber());
			ps.setString(3, en.getChName());
			ps.setString(4, en.getEnName());
			ps.setString(5, en.getContactName());
			ps.setString(6, en.getContactPhone());
			ps.setInt(7, en.getCapital());
			ps.setInt(8, en.getForeignCapital());
			ps.setString(9, en.getCurrency());
			ps.setString(10, en.getManager());
			ps.setString(11, DateUtil.format(new Date(), "yyyy-MM-dd"));
			
			count = ps.executeUpdate();
			
			//����Ͷ���˺���ҵ�Ĺ�ϵ��Ϣ
			String sql2 = "insert into t_en_inv(orgcode,investcode,regcapital,profitRatio) values(?,?,?,?)";
			ps = conn.prepareStatement(sql2);
			//�����ж��Ͷ����Ͷ��һ����ҵ�������ж���Ͷ������Ϣ����Ҫѭ��ִ��sql��䡣
			for(EnInv eninv:EnInvList) {
				
				ps.setString(1, eninv.getOrgcode());
				ps.setString(2, eninv.getInvestcode());
				ps.setString(3, eninv.getRegcapital());
				ps.setString(4, eninv.getProfitRatio());
				count += ps.executeUpdate();
			}
		
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("������ҵ�쳣");
			
		}finally {
			
			
			try {
		
				JdbcUtil.close(null,ps,null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}
	
	/*
	 * ͨ����֯��������orgcode������ҵ����ϸ��Ϣ��Dao����
	 */
	@Override
	public Enterprise selectEnByOrgcode(String orgcode) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Enterprise en = new Enterprise();
		
		try {
			conn = JdbcUtil2.getConnection();
			String sql = "select chName,currency,capital from t_enterprise where orgcode = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, orgcode);
			rs = ps.executeQuery();
			if(rs.next()) {
				en.setOrgcode(orgcode);
				en.setChName(rs.getString("chName"));
				en.setCurrency(rs.getString("currency"));
				en.setCapital(rs.getInt("capital"));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("��ҵ��ϸ��Ϣ��ѯʧ��");
		}finally {
			
			try {
				JdbcUtil.close(null,ps,rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return en;
	}

}
