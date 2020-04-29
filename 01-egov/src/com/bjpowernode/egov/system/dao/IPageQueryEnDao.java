package com.bjpowernode.egov.system.dao;

import java.util.Map;

import com.bjpowernode.egov.beans.Page;

public interface IPageQueryEnDao {
	
	Page query(Map<String,String> selectInfo);
}
