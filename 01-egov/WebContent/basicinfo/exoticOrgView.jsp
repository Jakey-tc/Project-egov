<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.bjpowernode.egov.beans.Investor"%>
<!DOCTYPE html>
<%
	Investor investor = (Investor)request.getAttribute("investor");
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table border="1" cellspacing="0" width="100%">

		<tr>
			<td width="150px">投资人名称</td>
			<td><%=investor.getInvestname()==null ? "" : investor.getInvestname()%></td>

		</tr>

		<tr>
			<td>所属国家/地区</td>
			<td><%=investor.getCountry()==null ? "" : investor.getCountry() %></td>

		</tr>

		<tr>
			<td>组织机构代码</td>
			<td><%=investor.getOrgcode()==null ? "" : investor.getOrgcode()%></td>

		</tr>

		<tr>
			<td>联系人</td>
			<td><%=investor.getContact()==null ? "" : investor.getContact() %></td>

		</tr>

		<tr>
			<td>联系电话</td>
			<td><%=investor.getContactPhone()==null ? "" : investor.getContactPhone() %></td>

		</tr>
		<tr>
			<td>电子信箱</td>
			<td><%=investor.getEmail()==null ? "" : investor.getEmail() %></td>

		</tr>
		<tr>
			<td>备注</td>
			<td><%=investor.getRemark()==null ? "" :investor.getRemark() %></td>

		</tr>
	</table>
	<input type="button" value="返回"
		onclick="document.location='basicinfo/exoticOrgList.jsp'" />
</body>
</html>