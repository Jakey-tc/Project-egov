<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.bjpowernode.egov.beans.*"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bjpowernode.egov.system.utils.StringUtil"%>
<!DOCTYPE html>
<!-- 投资人分页查询页面 -->
<%
	Page<Investor> pages = (Page<Investor>)request.getAttribute("page");
	
	List<Investor> lists = null;
	Integer pageno = 0;
	Integer pagesize = 0;
	Integer totalsize = 0;
	Integer pagecount = 0;
	
	if(pages != null){
		
		 lists = pages.getUserList();
		 
		 pageno = pages.getPageno();
		 pagesize = pages.getPagesize();
		 totalsize = pages.getTotalsize();
		 pagecount = pages.getPagecount();
	
	}
		
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script>

	function Inquire(){
		
		document.forms["inquireForm"].submit();		
	}
	
	//跳页函数
	function changePage(pageno){
		//把表单的action加上页码参数传递进去，注意要使用POST方法
		document.forms["inquireForm"].action="/01-egov/pageQueryInvestServlet?pageno=" + pageno;
		document.forms["inquireForm"].submit();
	}
</script>

</head>
<body>
	<font size="5">企业投资人信息登记列表</font>
	</br>
	</br>
	<form action="/01-egov/pageQueryInvestServlet" name="inquireForm"
		method="POST">
		<!--设置一个隐藏域，用于保存本资源的访问地址  -->
		<input type="hidden" value="/basicinfo/exoticOrgList.jsp" name="Path"/>
		投资人登记编号：<input type="text" name="investCode"
			value="<%=request.getParameter("investCode") == null ? "" : request.getParameter("investCode") %>" />
		投资人名称：<input type="text" name="investName" style="width: 100px"
			value="<%=request.getParameter("investName") == null ? "" : request.getParameter("investName") %>" />
		登记日期：<input type="text" name="startDate"
			value="<%=request.getParameter("startDate") == null ? "" : request.getParameter("startDate") %>" />
		~ <input type="text" name="endDate"
			value="<%=request.getParameter("endDate") == null ? "" : request.getParameter("endDate") %>" />
		<input type="button" value="查询" onclick="Inquire();"> </br> <input
			type="button" value="新增"
			onclick="document.location='/01-egov/basicinfo/exoticOrgAdd.jsp'" />

	</form>

	<table width="100%" cellspacing="0" border="1">
		<tr>
			<td>序号</td>
			<td>投资人登记编号</td>
			<td>投资人名称</td>
			<td>登记日期</td>
			<td>国别</td>
			<td>经办人</td>

		</tr>

		<%
			if(lists != null){
				
				int count = 0;
				for(Investor userlist:lists){
				
				
		%>
		<tr>
			<td><%=count++%></td>
			<td style="cursor: pointer"
				onclick="document.location='viewInvestServlet?investcode=<%=userlist.getInvestcode()%>'"><%=userlist.getInvestcode()%></td>
			<td><%=userlist.getInvestname()%></td>
			<td><%=userlist.getRegdate()%></td>
			<td><%=StringUtil.getCountryByCode(userlist.getCountry())%></td>
			<td><%=userlist.getManager()%></td>
		</tr>
		<%		
			     }
			}
		%>

	</table>
	<%
		boolean isFirstPage = (pageno == 1);
		boolean isLastPage = (pageno == pagecount);
	%>
	总记录<%=totalsize %>条, 当前<%=pageno %>页,共<%=pagecount %>页
	<span style="position: absolute; right: 80px;"><input
		type="button" value="上一页"
		<%= isFirstPage ? "" : "style='cursor:pointer' onclick='changePage(" + (pageno-1) + ")'" %> /></span>
	<span style="position: absolute; right: 10px;"><input
		type="button" value="下一页"
		<%= isLastPage ? "" : "style='cursor:pointer' onclick='changePage(" + (pageno+1) + ")'" %> /></span>
</body>
</html>