<!--  修改用户信息的jsp页面 -->
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.bjpowernode.egov.beans.User"%>

<% 
	User user = (User)request.getAttribute("user");	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 引入外部的js文件，这个js文件是我定义的用于检验非空以及是否一致性的类和方法。还有表单项的类 -->
<script src="/01-egov/js/egov.js"></script>
<script>
	
	//检验修改用户信息是否符合规定，符合规定就提交表单，即修改。
	function doUpdate(){
		
		var ok = validateForm();
		if(ok){
			
			document.forms["userForm"].submit();
		}
	}
	
	
	//检验修改的用户信息是否符合非空规范，以及密码和确认密码是否一致。使用面向对象的js.
	function validateForm(){
		
		//创建三个表单项对象。
		var formItem1 = new FormItem("用户姓名","username");
		var formItem2 = new FormItem("用户密码","userpsw");
		var formItem3 = new FormItem("确认密码","checkpsw");
		
		//把三个表单项对象放入数组中
		var formItemArray = [formItem1,formItem2,formItem3];
		
		//利用封装好的类和方法对这三个表单项对象进行检验。
		//这里使用&&符号，也就是短路与符号，是为了不让多个alert连续执行。
		return $.isNotEmpty(formItemArray) && $.isSame(formItem2,formItem3);
		
		
	}
	
</script>
</head>
<body>
	<form action="updateUserServlet" method="POST" name="userForm">
		<!-- 设置隐藏域，把从user.jsp提交上来的隐藏域中的信息pageno再放到这个隐藏域中提交到UpdateUserServlet中 -->
		<input type="hidden" name="pageno"
			value="<%=request.getParameter("pageno")%>"> 用户代码：<%=user.getUsercode()%><input
			type="hidden" name="hiddenUsercode" id="hiddenUsercode"
			value="<%=user.getUsercode()%>"><br /> 用户姓名：<input
			type="text" value="<%=user.getUsername() %>" name="username"
			id="username" /><br /> 用户密码：<input type="password"
			value="<%=user.getUserpsw() %>" name="userpsw" id="userpsw" /><br />
		确认密码：<input type="password" value="<%=user.getUserpsw() %>"
			name="checkpsw" id="checkpsw" /><br /> 机构类型： <select name="orgtype"
			id="orgtype">
			<option value="x"
				<%="x".equals(user.getOrgtype()) ? "selected" : "" %>>管理人员</option>
			<option value="0"
				<%="0".equals(user.getOrgtype()) ? "selected" : "" %>>外汇局管理人员</option>
			<option value="1"
				<%="1".equals(user.getOrgtype()) ? "selected" : "" %>>银行管理人员</option>
		</select> <input type="button" value="修改" onclick="doUpdate();" />

	</form>

</body>
</html>