package com.bjpowernode.egov.system.dao;

import java.util.List;

import com.bjpowernode.egov.beans.EnInv;
import com.bjpowernode.egov.beans.Enterprise;

public interface IEnterpriseDao {
	
	/*
	 * ������ҵ��Ϣ��Dao����
	 */
	public int insert(Enterprise en,List<EnInv> EnInvList);

	/*
	 * ͨ����֯��������orgcode������ҵ����ϸ��Ϣ��Dao����
	 */
	public Enterprise selectEnByOrgcode(String orgcode);
}
