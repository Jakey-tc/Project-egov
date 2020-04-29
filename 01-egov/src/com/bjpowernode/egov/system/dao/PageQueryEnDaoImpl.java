package com.bjpowernode.egov.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bjpowernode.egov.beans.Enterprise;
import com.bjpowernode.egov.beans.Page;
import com.bjpowernode.egov.system.utils.JdbcUtil2;
import com.bjpowernode.egov.system.utils.StringUtil;

public class PageQueryEnDaoImpl implements IPageQueryEnDao {

	@Override
	public Page query(Map<String,String> selectInfo) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Page page = new Page(selectInfo.get("pageno"));
		
		try {
			
			conn = JdbcUtil2.getConnection();
			
			StringBuffer sql = new StringBuffer("select e.orgcode,e.chName,e.regdate,u.usercode from t_enterprise as e join t_user as u on e.manager=u.usercode where 1=1");
			StringBuffer sql2 = new StringBuffer("select count(*) as totalsize from t_enterprise as e join t_user as u on e.manager=u.usercode where 1=1");
			
			List list = new ArrayList();
			
			if(StringUtil.isNotEmpty(selectInfo.get("orgcode"))) {
				
				sql.append(" and e.orgcode = ?");
				sql2.append(" and e.orgcode = ?");
				
				list.add(selectInfo.get("orgcode"));
			}
			
			if(StringUtil.isNotEmpty(selectInfo.get("chName"))) {
				
				sql.append(" and e.chName = ?");
				sql2.append(" and e.chName = ?");
				
				list.add(selectInfo.get("chName"));
			}
			
			if(StringUtil.isNotEmpty(selectInfo.get("startDate"))) {
				
				sql.append(" and e.regdate >= ?");
				sql2.append(" and e.regdate >= ?");				
				list.add(selectInfo.get("startDate"));
			}
			
			if(StringUtil.isNotEmpty(selectInfo.get("endDate"))) {
				
				sql.append(" and e.regdate <= ?");
				sql2.append(" and e.regdate <= ?");
				list.add(selectInfo.get("endDate"));
			}
			
			
			sql.append(" order by e.regdate desc");
			
			String pageSql = page.getSql3(sql.toString());
			
			//记得重新赋给ps
			ps = conn.prepareStatement(pageSql);
			
			for(int i=0;i<list.size();i++) {
				
				ps.setString(i+1,(String)list.get(i));
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				
				Enterprise en = new Enterprise();
				en.setOrgcode(rs.getString("orgcode"));
				en.setChName(rs.getString("chName"));
				en.setRegdate(rs.getString("regdate"));
				en.setManager(rs.getString("usercode"));
				
				page.getEnList().add(en);
				
			}
			
			String totalsizesql = sql2.toString();
			ps = conn.prepareStatement(totalsizesql);
			for(int i=0;i<list.size();i++) {
				
				ps.setString(i+1,(String)list.get(i));
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				
				page.setTotalsize(rs.getInt("totalsize"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("分页查询失败");
		}
		return page;
	}

}
