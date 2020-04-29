<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!--输入企业的组织结构代码，确认进行登记  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>新增组织机构代码</title>

<script type="text/javascript">
	
	//检验输入的组织机构代码不能为空，以及提交
	function checkOrgcode(){
		
		var orgcode = document.getElementById("orgcode");
		if(orgcode.value == ""){
			alert("组织机构代码不能为空");
			orgcode.focus();
			return;
		}
		document.forms["enForm"].submit();
	}
	
	//组织机构代码如果已经存在弹出提示框
	function pageLoad(){
		
		<%
			Object errorMsg = request.getAttribute("errorMsg");
			if(errorMsg != null){
				
		%>
				alert("<%=errorMsg%>");
		<%
			}
		%>
		
	}
</script>
</head>
<body onload="pageLoad();">
	<div id="top" style="height:20px;width:100%;background-color:green;"></div>
	<form name="enForm" action="/01-egov/checkOrgcodeServlet" method="POST">
		组织机构代码：<input type="text" id="orgcode" name="orgcode">
		<input type="button" value="确定" onclick="checkOrgcode();"/>
	</form>
</body>
</html>