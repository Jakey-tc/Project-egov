package com.bjpowernode.egov.system.dao;

import java.util.List;

import com.bjpowernode.egov.beans.EnInv;
import com.bjpowernode.egov.beans.Enterprise;

public interface IEnterpriseDao {
	
	/*
	 * 保存企业信息的Dao方法
	 */
	public int insert(Enterprise en,List<EnInv> EnInvList);

	/*
	 * 通过组织机构代码orgcode查找企业的详细信息的Dao方法
	 */
	public Enterprise selectEnByOrgcode(String orgcode);
}
