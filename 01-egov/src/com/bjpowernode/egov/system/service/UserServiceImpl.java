package com.bjpowernode.egov.system.service;
/*
 * MVC�ܹ��е�Model��֮һ��Service�㣺Servlet��Model�㣩������֮һ��
 * ���ܣ�ִ�б����û����ݵ�ҵ���߼�����
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
			//ʹ���µ�JDBC�������ȡConnection�������������ҵ���һ�λ�ȡConnection����
			//������Dao�л�ȡ��Connection��������Connection������ͬһ����
			conn = JdbcUtil2.getConnection();
			
			//���������ֶ��ύ
			conn.setAutoCommit(false);
			
			//����Service����������Dao����
			IUserDao userDao = new UserDaoImpl();
			
			//��ȡusercode�û�����
			String usercode = user.getUsercode();
		
			//����Dao��ķ��������û���Ϣ������һ��User�����������Ϊnull����ʾ�����ݿ���û���ҵ����User����
			User selectUser = userDao.selectByUsercode(usercode);
		
			//����ϱ߷��ص�User�����ǿյģ���������û������������ݿ��У����Կ�����������û���
			if(selectUser==null) {
				
				//ִ�в������ݿ���Ϣ�ķ�����ʵ���û����ݲ��뵽���ݿ���
				count = userDao.insert(user);
			}
			
			//��ҵ������������ݿ⽻����������ɺ��ύ����
			conn.commit();
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				//��������쳣���ع�����
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			
			try {
				//���������Զ��ύ
				conn.setAutoCommit(true);
				//�ر�Connection����
				JdbcUtil.close(conn,null,null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		   
			
	
		return count;
	}

}
