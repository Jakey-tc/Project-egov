package com.bjpowernode.egov.beans;
/*
 * ��װҳ�룬��ҳ��������Ϣ������ÿһҳ��ʾ����Ϣ��������ǰҳ�û���Ϣ�������Page��
 */
import java.util.ArrayList;
import java.util.List;

public class Page<T>{
	
	//��ǰҳ��
	private Integer pageno;
	//��ҳ����
	private Integer pagecount;
	//�ܼ�¼����
	private Integer totalsize;
	//��ǰҳ�����ʾ��¼����
	private Integer pagesize;
	
	//�洢�û���Ϣ����ļ���
	private List<T> userList;
	
	//�洢��ҵ��Ϣ����ļ���
	private List<T> enList;
	
	public List<T> getEnList() {
		return enList;
	}

	public void setEnList(List<T> enList) {
		this.enList = enList;
	}

	//���幹�췽��
	public Page(String pageno) {
		
		if(pageno != null) {
			
			this.pageno = Integer.parseInt(pageno);
		}else {
			this.pageno = 1;
		}
		//�����洢�û�����ļ���
		userList = new ArrayList<T>();
		//�����ÿһҳ�����ʾ�ļ�¼����
		this.pagesize = 3;
		
		enList = new ArrayList<T>();
	}
	
	//����һ����ȡ�û�ҵ��sql���ķ���
	public String getSql(String sql) {
		
		return "select t.usercode,t.username,t.userpsw,t.orgtype from(" + sql + ")as t limit " + (pageno-1) * pagesize + "," + pagesize;
	}
	
	//����һ����ȡͶ����ҵ��sql���ķ���
	public String getSql2(String sql) {
		
		return "select t.investcode,t.investname,t.regdate,t.country,t.username from(" + sql + ")as t limit " + (pageno-1) * pagesize + "," + pagesize;
	}
	
	//����һ����ȡ��ҵҵ��sql���ķ���
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
	
	//�������pagecount���Եķ���û�б�Ҫ�����Բ�Ҫ
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
