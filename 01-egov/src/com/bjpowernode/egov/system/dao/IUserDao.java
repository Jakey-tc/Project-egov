package com.bjpowernode.egov.system.dao;

import com.bjpowernode.egov.beans.User;

public interface IUserDao {
	
	
	
	int insert(User user); 
	
	User selectByUsercode(String usercode);
}
