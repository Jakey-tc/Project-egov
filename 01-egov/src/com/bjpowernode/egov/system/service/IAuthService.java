package com.bjpowernode.egov.system.service;

import java.util.Map;

import com.bjpowernode.egov.beans.Auth;

public interface IAuthService {
	
	public int saveAuth(Map<String,String> authMap);
	
	public Auth getAuthByAuthno(String authno);
	
	public int feedbackAuth(String authno);
}
