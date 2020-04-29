<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!-- 登录页面 -->
<html>
<head>

<!-- 设置base标签，当作以路径名称开头的相对路径的参考路径 -->
<base href="<%=request.getScheme() %>://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/">

<meta charset="UTF-8">
<title>欢迎登录外汇业务管理系统</title>

	<style type="text/css">
		body{
		
			margin:0px;
		}
		#top{
			background-color:rgb(3,86,81);
			width:100%;
			height:350px;
			position:relative;
			
		
		}
	 	#bottom{
			background-color:rgb(31,125,123);
			width:100%;
			height:350px;
			position:relative;
			
		} 
		#head{
			position:absolute;
			left:600px;
			bottom:0px;
		}
		
		#form{
			position:absolute;
			left:600px;
			margin:0px;
			
		}
		#register{
			position:absolute;
			left:650px;
			top:70px;
			background-color:rgb(241,241,241);
			text-decoration:none;
			color:black;
		}
	</style>
	
	<script>
	
	//检验是否登录失败，如果有登录失败信息，弹出提示框。
	function checkErrorMsg(){
		
		<%
			String errorMsg = (String)request.getAttribute("errorMsg");
			if(errorMsg != null){
		%>
				alert("<%=errorMsg%>");
		<%
				
			}
		%>
					
	}
	
	</script>
</head>
<body onload="checkErrorMsg()">
	
	<div id="top">
		<div id="head">
			<img src="images/登录图标.png" width="70px" height="80px"/>
			<span style="color:white;font-size:40px">外汇业务管理平台</span>
		
		</div><p align="left">
	
	</p></div>
		
	<div id="bottom">
		<form id="form" action="/01-egov/loginServlet" method="POST" ">
			&emsp;&emsp;&ensp;&nbsp;
			<select name="orgtype">
				<option value="x">管理人员</option>
				<option value="0">外汇局管理人员</option>
				<option value="1">银行管理人员</option>
			</select>
			<br/>
			账号：<input type="text" name="usercode" id="usercode"/><br/>
			密码：<input type="password" name="userpsw" id="userpsw"/><br/>
			<input type="submit" value="登录"/>
		</form>
		
	</div>
</body>
</html>