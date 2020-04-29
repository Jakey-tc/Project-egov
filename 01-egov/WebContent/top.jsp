<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.bjpowernode.egov.system.utils.StringUtil"%>
<%@ page import="com.bjpowernode.egov.beans.User"%>
<!DOCTYPE html>
<%
	User user = (User)session.getAttribute("user");
	String usercode = user.getUsercode();
	
	String orgtype = user.getOrgtype();
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>

	//实现用户退出功能的方法之一，此函数绑定一个按钮，实现退出功能，但是没有销毁session对象。
	/* function getOut(){
		
		//在main这个frame框架中打开login.jsp这个页面
		window.open('login.jsp','main','',false); 
	} */
</script>

</head>
<body>
	<span style="font-size: 40px;">外汇业务信息系统</span> 用户代码:<%=usercode %>
	机构:<%=StringUtil.getTextByCode(orgtype) %>

	<!--这是我自己实现退出功能时，使用的按钮，绑定js函数实现退出功能，没有执行Servlet去销毁session对象。  -->
	<!-- <input type="button" value="退出" style="position:absolute;right:5px;bottom:5px;" onclick="getOut();" /> -->

	<a href="getOutServlet"
		style="position: absolute; right: 5px; bottom: 5px;" target="_top">退出</a>

</body>
</html>