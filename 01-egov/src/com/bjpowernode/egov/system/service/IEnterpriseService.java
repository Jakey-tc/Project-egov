package com.bjpowernode.egov.system.service;

import java.util.List;

import com.bjpowernode.egov.beans.Enterprise;
import com.bjpowernode.egov.system.dao.EnterpriseDaoImpl;
import com.bjpowernode.egov.system.dao.IEnterpriseDao;

public interface IEnterpriseService {
	
	/*
	 * 保存企业信息的Service方法
	 */
	public int save(Enterprise En,List EnInvList);
	
	/*
	 * 通过组织机构代码orgcode查找企业的详细信息的Service方法
	 */
	public Enterprise selectEnByOrgcode(String orgcode);
	
	
}
