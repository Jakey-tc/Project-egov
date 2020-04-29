<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.bjpowernode.egov.beans.*"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bjpowernode.egov.system.utils.StringUtil"%>
<!DOCTYPE html>
<!-- 在给企业添加投资人时的投资人分页查询页面 -->
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
<title>投资人信息查询</title>

<script>
	/*点击查询按钮提交查询信息的表单*/
	function Inquire(){
		
		document.forms["inquireForm"].submit();		
	}
	
	/*跳页函数  */
	function changePage(pageno){
		//把表单的action加上页码参数传递进去，注意要使用POST方法
		document.forms["inquireForm"].action="/01-egov/pageQueryInvestServlet?pageno=" + pageno;
		document.forms["inquireForm"].submit();
	}
	
	/*父子窗口通信  */
	function addToParent(s){
		
		//获取投资人代码
		var investcode = s.parentNode.parentNode.children[1].innerHTML;
		//获取此页面上的investName投资人名称信息
		var investName = s.parentNode.parentNode.children[2].innerHTML;
		//获取此页面上的country国别信息
		var country = s.parentNode.parentNode.children[4].innerHTML;
		
		var investcode = s.parentNode.parentNode.children[1].innerHTML;
		
		//获取父页面上的指定table表格
	    var parentTable = window.opener.document.getElementById("enreg");
	    //获取table中有几个tr（有几行）
	    var parentTableRowLength = parentTable.rows.length;
	   	
	 	//在父页面的指定table的最后一行插入一行
	    var Row1 = parentTable.insertRow(parentTableRowLength);
	 	//再在这一行插入第一个单元格
	    var Col1 = Row1.insertCell(0);
	    Col1.style.textAlign = "center";
	    Col1.innerHTML = "<input type='hidden' name='investcode' value='"+ investcode + "' ><font color='red'>" + investName + "</font><input type='hidden' name='investname' value='"+investcode+"'/>";
	    
	  //再在这一行插入第二个单元格并在单元格内赋值
	    var Col2 = Row1.insertCell(1);
	    Col2.style.textAlign = "center";
	    Col2.innerHTML =  "<font color='red'><input name='country' type='text' value='"+ country + "'/></font>";
	    
	  //再在这一行插入第三个单元格并在单元格内赋值
	    var Col3 = Row1.insertCell(2);
	    Col3.style.textAlign = "center";
	    Col3.innerHTML = "<input type='text' name='regcapital' onblur='calculate();'/>";
	    
	  //再在这一行插入第四个单元格并在单元格内赋值
	    var Col4 = Row1.insertCell(3);
	    Col4.style.textAlign = "center";
	    Col4.innerHTML = "<input type='text' name='profitRatio'/>";
	    
	  //再在这一行插入第五个单元格并在单元格内赋值
	    var Col5 = Row1.insertCell(4);
	    Col5.style.textAlign = "center";
	  //这里在单元格赋值的是一个按钮标签，点击这个按钮会删除此行，删除此行的函数在父页面中编写。
	    Col5.innerHTML = "<input type='button' value='删除'  onclick='deleteRow(this);calculate();'/>";
	
	  


	}
</script>

</head>
<body>
	<font size="5">企业投资人信息登记列表</font>
	
	<form action="/01-egov/pageQueryInvestServlet" name="inquireForm"
		method="POST">
		<!--设置一个隐藏域，用于保存本资源的访问地址  -->
		<input type="hidden" value="/foreignExchange/orgcodeSelect.jsp" name="Path">
		投资人登记编号：<input type="text" name="investCode"
			value="<%=request.getParameter("investCode") == null ? "" : request.getParameter("investCode") %>" />
		投资人名称：<input type="text" id="investName" name="investName" style="width: 100px"
			value="<%=request.getParameter("investName") == null ? "" : request.getParameter("investName") %>" />
		登记日期：<input type="text" name="startDate"
			value="<%=request.getParameter("startDate") == null ? "" : request.getParameter("startDate") %>" />
		~ <input type="text" name="endDate"
			value="<%=request.getParameter("endDate") == null ? "" : request.getParameter("endDate") %>" />
		<input type="button" value="查询" onclick="Inquire();"> 

	</form>
	
	
	<table width="100%" cellspacing="0" border="1">
		<tr>
			<td>序号</td>
			<td>投资人登记编号</td>
			<td>投资人名称</td>
			<td>登记日期</td>
			<td>国别</td>
			<td>经办人</td>
			<td><input type="button" value="添加" /></td>
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
			<td><input type="button" value="添加" onclick="addToParent(this);"/></td>
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