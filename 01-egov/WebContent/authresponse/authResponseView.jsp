<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="myFunction" uri="http://www.myElFunction.com/jsp/el" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script>
	
	
</script>
</head>
<body>
	
	<table width="100%" border="1" cellspacing="0">
		<tr>
			<td>核准件编号：</td>
			<td>${requestScope.auth.approvalCode}</td>
			<td>登记日期：</td>
			<td>${requestScope.auth.regdate}</td>
			
		</tr>
		
		<tr>
			
			<td>限额币种：</td>
			<!--使用自定义的EL获取币种信息  -->
			<td>${myFunction:getCountryCode("currency.",auth.currency)}</td>
			<td>账户限额：</td>
			<td>${requestScope.auth.capital}</td>
		</tr>
		
		<tr>
			<td>联系人：</td>
			<td>${requestScope.auth.contact}</td>
			<td>联系电话：</td>
			<td>${requestScope.auth.contactPhone}</td>
		</tr>
		<tr>
			<td><input type="button" value="反馈" onclick="if(window.confirm('确定审核完毕?')){
					document.location='/01-egov/feedbackServlet?authno=${requestScope.auth.approvalCode}'}"/></td>
		</tr>
		
	
	</table>
</body>
</html>