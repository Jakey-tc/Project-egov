package com.bjpowernode.egov.system.dao;
/*
 * MVC�ܹ��е�Dao�㣺Service������顣
 * ���ܣ�ӵ��������������ѯ�û����ݲ����ش��û����󣬲����û���Ϣ�����ݿ���
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
	//�����û����ݵ����ݿ���
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
			//�����ǰ�Dao�е��쳣��Service���׳���ԭ���ǵ����ҵ����������ƶ�����ݿ⽻������ʱ�����
			//����������쳣û���׳���Service�У����Ǳ����Dao�����ˣ���ô��Service�еĿ�������Ļع��Ͳ��ᷢ����
			//������Ҫ�׳�������쳣����߰�ȫ�ԡ�
			throw new RuntimeException("�����쳣");
		}finally {
			
			try {
				//���ﲻ�ܹر�Connection������Ϊִ�������������һ��ҵ�����񣩿��ܻ�û����ɡ�
				//��ִ���������ݿ⽻���ķ���ʱ���õ����Connection����
				//��Service����Ҳ���õ����Connection��������������
				JdbcUtil2.close(null,ps,rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
		//����count���жϲ����û���Ϣ�Ƿ�ɹ�
		return count;
	}

	/**
	 *�����û�����usercode�����û�����������û�����
	 */
	@Override
	//��������ǲ�ѯ�û����ݲ������û�����
	public User selectByUsercode(String usercode) {
		
		//����һ���û����� 
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {			
			conn = JdbcUtil2.getConnection();
			
			//ִ�в����Ƿ��usercode�ֶε��û��Ѿ����������ݿ��е�sql���
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
			//�����ǰ�Dao�е��쳣��Service���׳���ԭ���ǵ����ҵ����������ƶ�����ݿ⽻������ʱ�����
			//����������쳣û���׳���Service�У����Ǳ����Dao�����ˣ���ô��Service�еĿ�������Ļع��Ͳ��ᷢ����
			//������Ҫ�׳�������쳣����߰�ȫ�ԡ�
			throw new RuntimeException("�����쳣");
		}finally {
			
			try {
				//ͬ������Ҳ���ܹر�Connection����
				JdbcUtil2.close(null, ps, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		}
		
		//����User�û������ж��Ƿ��ѯ����ĳһ���û�����Ϣ��
		return user;
	}

}
