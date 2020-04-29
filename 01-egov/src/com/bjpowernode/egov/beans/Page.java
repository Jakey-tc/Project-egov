package com.bjpowernode.egov.beans;
/*
 * 封装页码，总页数，总信息条数，每一页显示的信息条数，当前页用户信息对象到这个Page类
 */
import java.util.ArrayList;
import java.util.List;

public class Page<T>{
	
	//当前页码
	private Integer pageno;
	//总页码数
	private Integer pagecount;
	//总记录条数
	private Integer totalsize;
	//当前页最多显示记录条数
	private Integer pagesize;
	
	//存储用户信息对象的集合
	private List<T> userList;
	
	//存储企业信息对象的集合
	private List<T> enList;
	
	public List<T> getEnList() {
		return enList;
	}

	public void setEnList(List<T> enList) {
		this.enList = enList;
	}

	//定义构造方法
	public Page(String pageno) {
		
		if(pageno != null) {
			
			this.pageno = Integer.parseInt(pageno);
		}else {
			this.pageno = 1;
		}
		//创建存储用户对象的集合
		userList = new ArrayList<T>();
		//定义好每一页最多显示的记录条数
		this.pagesize = 3;
		
		enList = new ArrayList<T>();
	}
	
	//定义一个获取用户业务sql语句的方法
	public String getSql(String sql) {
		
		return "select t.usercode,t.username,t.userpsw,t.orgtype from(" + sql + ")as t limit " + (pageno-1) * pagesize + "," + pagesize;
	}
	
	//定义一个获取投资人业务sql语句的方法
	public String getSql2(String sql) {
		
		return "select t.investcode,t.investname,t.regdate,t.country,t.username from(" + sql + ")as t limit " + (pageno-1) * pagesize + "," + pagesize;
	}
	
	//定义一个获取企业业务sql语句的方法
	public String getSql3(String sql) {
		
		return "select t.orgcode,t.chName,t.regdate,t.usercode from("+ sql +") as t limit " + (pageno-1) * pagesize + "," + pagesize;
	}
	
	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	public Integer getPagecount() {
		return this.totalsize % pagesize == 0 ? this.totalsize / this.pagesize : this.totalsize / this.pagesize + 1;
	}
	
	//这个设置pagecount属性的方法没有必要，可以不要
	public void setPagecount(Integer pagecount) {
		this.pagecount = pagecount;
	}

	public Integer getTotalsize() {
		return totalsize;
	}

	public void setTotalsize(Integer totalsize) {
		this.totalsize = totalsize;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public List<T> getUserList() {
		return userList;
	}

	public void setUserList(List<T> userList) {
		this.userList = userList;
	}
	
	

}
