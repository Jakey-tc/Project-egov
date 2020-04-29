package com.bjpowernode.egov.system.service;

import java.util.List;

import com.bjpowernode.egov.beans.Enterprise;
import com.bjpowernode.egov.system.dao.EnterpriseDaoImpl;
import com.bjpowernode.egov.system.dao.IEnterpriseDao;

public interface IEnterpriseService {
	
	/*
	 * ������ҵ��Ϣ��Service����
	 */
	public int save(Enterprise En,List EnInvList);
	
	/*
	 * ͨ����֯��������orgcode������ҵ����ϸ��Ϣ��Service����
	 */
	public Enterprise selectEnByOrgcode(String orgcode);
	
	
}
