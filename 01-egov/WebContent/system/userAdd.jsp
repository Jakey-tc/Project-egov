<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- 添加用户的表单页面 -->
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	
	//在html页面加载完毕后执行的函数。
	//来显示当用户代码重复时的提醒信息，但是从新增按钮进入此页面时不显示错误信息。
	function doOnLoad(){
		
		<%
			//没明白，如果从新增按钮进入此页面时，为什么不报空指针异常
			//明白了，如果errorMsg是空，但是没有调用它的内容是不会报空指针异常的。
			Object errorMsg = request.getAttribute("errorMsg");
			if(errorMsg != null){
				
				
		
		%>
			alert("<%=errorMsg%>");
		<%
			}
		%>
	}
	
	/*保存表单的数据  */
	function saveForm(){
		
		var ok = checkIsNull();
		if(ok){
			document.forms[0].submit();
		}
	}
	
	//检验表单是否符合规定
	function checkIsNull(){
		var userCode = document.getElementById("usercode");	
		if(userCode.value==""){
			alert("用户代码不能为空");
			return false;
		}

		var userName  = document.getElementById("username");
		if(userName.value==""){
			alert("用户名称不能为空");
			return false;
		}

        var userPsw  = document.getElementById("userpsw");
		if(userPsw.value==""){
			alert("用户密码不能为空");
			return false;
		}
        var userPsw2 = document.getElementById("userpsw2");
		if(userPsw2.value!=userPsw.value|userPsw2==""){
			alert("密码不一致或不能为空");
			return false;
		}
		document.forms[0].submit();
	
	}
	
	//返回上一页
	function returns(){
		
		document.location="/01-egov/pageQueryUserServlet3";
	}
	
	
	//定义XMLHttpRequest对象的变量
	var XRequest;
	//使用ajax的get方法检验输入的usercode是否跟数据库中的数据重复
	function checkUsercode(usercode){

		//创建XMLHttpRequest对象
		if(window.XMLHttpRequest){
			
			XRequest = new XMLHttpRequest();
			
		}else if(window.ActiveXObject){
			
			XRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		//注册回调函数
		XRequest.onreadystatechange = callback;
		
		//开启通道
		XRequest.open("GET","/01-egov/checkUsercodeServlet?_=" + new Date().getTime() + "&usercode=" + usercode,true);
		
		//发送请求
		XRequest.send();
		
	}
	
	//回调函数
	function callback(){
		
		if(XRequest.readyState == 4){
			
			//如果状态码是200，把Servlet中的输出内容或显示内容显示到id为tipUsername的span标签体中
			if(XRequest.status == 200){
				
				var tipUsername = document.getElementById("tipUsername");
				tipUsername.innerHTML = XRequest.responseText;
				
			}else if(XRequest.status == 400){
				
				alert("400-Not Found");
			}else if(XRequest.status == 500){
				
				alert("500-Server Inner Error");
			}else{
				
				alert(XRequest.status + "-未知错误");
			}
		}
	}

	
</script>
</head>
<body onload="doOnLoad();">
	<input type="button" value="返回" onclick="returns();" />
	<br />
	<form action="/01-egov/insertUserServlet" method="POST">

		<table style="width: 100%; border: solid gray 2px">

			<tr>
				<td height="20px" width="100%" style="background-color: green;"></td>
			</tr>

			<tr>
				<td style="border: solid green 1px"><div
						style="position: relative; left: 500px">
						用户代码：<input type="text" name="usercode" id="usercode"
							onblur="checkUsercode(this.value);" /><span id="tipUsername"></span>
					</div></td>
			</tr>

			<tr>
				<td style="border: solid green 1px"><div
						style="position: relative; left: 500px">
						用户姓名：<input type="text" name="username" id="username" />
					</div></td>
			</tr>

			<tr>

				<td style="border: solid green 1px;"><div
						style="position: relative; left: 500px">
						用户密码：<input type="password" name="userpsw" id="userpsw" />
					</div></td>
			</tr>

			<tr>

				<td style="border: solid green 1px"><div
						style="position: relative; left: 500px">
						确认密码：<input type="password" name="userpsw2" id="userpsw2" />
					</div></td>
			</tr>

			<tr>

				<td style="border: solid green 1px;"><div
						style="position: relative; left: 500px">
						机构类型： <select name="orgtype" id="orgtype">
							<option value="x">管理人员</option>
							<option value="0">外汇局业务人员</option>
							<option value="1">银行业务人员</option>
						</select>
					</div></td>
			</tr>

			<tr>
				<td style="border: solid green 1px">
					<div style="position: relative; left: 500px">
						<input type="button" value="保存" onclick="saveForm();" />
						<!-- 这里保存使用button而不使用submit。因为要检验用户输入的信息是否符合标准。
							不符合不能够提交。如果用submit，即使不符合也会提交。而使用button，再使用js
							代码检验用户信息，如果没有问题。最后通过js中的document.forms[表单索引].submit()方法提交表单。 -->
						<input type="button" value="清除"
							style="position: relative; left: 5px"
							onclick="new function(){
								document.forms[0].reset();
								<!-- 点击清除按钮实现清除表单数据的功能。 -->
							}" />
					</div>
				</td>

			</tr>
		</table>
	</form>
</body>
</html>