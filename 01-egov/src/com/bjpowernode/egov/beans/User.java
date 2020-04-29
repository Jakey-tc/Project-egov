package com.bjpowernode.egov.beans;

public class User {
	
	String usercode;
	String username;
	String orgtype;
	String userpsw;
	String regdate;
	
	public User(){
		
		
	}
	
	
	public User(String usercode, String username, String orgtype, String userpsw, String regdate) {
		super();
		this.usercode = usercode;
		this.username = username;
		this.orgtype = orgtype;
		this.userpsw = userpsw;
		this.regdate = regdate;
	}


	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getUserpsw() {
		return userpsw;
	}
	public void setUserpsw(String userpsw) {
		this.userpsw = userpsw;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
}
