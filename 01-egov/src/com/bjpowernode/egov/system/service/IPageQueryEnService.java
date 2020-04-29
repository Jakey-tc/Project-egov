package com.bjpowernode.egov.system.service;

import java.util.Map;

import com.bjpowernode.egov.beans.Page;

public interface IPageQueryEnService {

	Page query(Map<String,String> selectInfo);
}
