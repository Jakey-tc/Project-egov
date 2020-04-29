<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.bjpowernode.egov.beans.*"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<%

	Page<Enterprise> pageObj = (Page<Enterprise>)request.getAttribute("page");

	Integer pageno = 0;
	Integer pagesize = 0;
	Integer totalsize = 0;
	Integer pagecount = 0;
	
	List<Enterprise> list = null;
	
	if(pageObj != null){
		pageno = pageObj.getPageno();
		pagesize = pageObj.getPagesize();
		totalsize = pageObj.getTotalsize();
		pagecount  = pageObj.getPagecount();
		
		list = (List<Enterprise>)pageObj.getEnList();
		
	}
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>外商投资企业列表</title><base>
<script>
	function query(){
		
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<font size="5">外商投资企业列表</font>
	<br/>
	<form action="/01-egov/pageQueryEnServlet" method="POST">
		组织机构代码：<input type="text" name="orgcode"/>
		企业中文名称：<input type="text" name="cnName"/>
		登记日期：<input type="text" name="startDate"/>~<input type="text" name="endDate"/>
		
		<input type="button" value="查询" onclick="query();"/>
		<input type="button" value="清除" onclick="new function(){document.forms[0].reset()};"/>
			
	</form>
	<br/>
	<table width="100%" border="1" cellspacing="0">
	
		<tr>
			<td>序号</td>
			<td>组织机构代码</td>
			<td>企业中文名称</td>
			<td>登记日期</td>
			<td>经办人</td>
			<td>投资比例</td>
		</tr>
		
		<%
		
			int count=0;
			if(list != null){
				
				for(Enterprise enlist:list){	
						
			
		%>
		
		
			<tr>
				<td><%=count++ %></td>
				<td style="cursor:pointer;" onclick="window.opener.document.all.orgcode.value='<%=enlist.getOrgcode() %>'"><%=enlist.getOrgcode() %></td>
				<td><%=enlist.getChName() %></td>
				<td><%=enlist.getRegdate() %></td>
				<td><%=enlist.getManager() %></td>
				<td></td>
			</tr>
		<%
				}
			}
		%>
		
	</table>
	
</body>
</html>