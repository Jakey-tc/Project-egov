<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
	
	//点击查询按钮打开子页面分页查询企业信息
	function openQuery(){
		
		window.open("orgcodeSelect.jsp","newWindow","width=400,height=500");
	}
	
	function selectEnDetail(){
		
		
		var orgcode = document.getElementById("orgcode");
		if(orgcode.value == ""){
			
			alert("组织机构代码不能为空，请查询");
			return;
		}
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<div id="top" style="width:100%;height:20px;background:green;"></div>
	<form action="/01-egov/selectEnDetailServlet" method="POST">
		<!-- 下面的id属性用于在子页面操作父页面的此标签:window.opener.document.all.orgcode.value= -->
		组织机构代码:<input type="text" id="orgcode" name="orgcode" readonly="readonly"/>
		<input type="button" value="查询" onclick="openQuery();"/>
		<input type="button" value="确认" onclick="selectEnDetail();"/>
	</form>
</body>
</html>