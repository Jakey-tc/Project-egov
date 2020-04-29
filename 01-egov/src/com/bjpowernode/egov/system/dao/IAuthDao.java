package com.bjpowernode.egov.system.dao;

import java.util.Map;

import com.bjpowernode.egov.beans.Auth;

public interface IAuthDao {
	
	public int saveAuth(Map<String,String> authMap);
	
	public Auth getAuthByAuthno(String authno);

	public int feedbackAuth(String authno);
}
