<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.bjpowernode.egov.beans.Enterprise" %>
<%@ page import="com.bjpowernode.egov.system.utils.StringUtil" %>
<!DOCTYPE html>
<%
	Enterprise en = (Enterprise)request.getAttribute("en");
	String orgcode = null;
    String chName =null;
    String currency = null;
    Integer capital = null;
	if(en != null){
		
		
		orgcode = en.getOrgcode();
		chName = en.getChName();
		currency = en.getCurrency();
		capital = en.getCapital();
	}
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script>
	function saveAuth(){
		
 		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="saveAuthServlet" method="POST" enctype="multipart/form-data">
		<div style="width:100%;height:30px;background:gray;">资本金账户开户核准-录入</div>
		<table width="100%" border="1" cellspacing="0"> 
			<tr style="height:20px;">
				<td style="background:green;text-align:center;" colspan="4">资本金账户开户核准信息</td>
			</tr>
			
			<tr>
				<td style="text-align:right;">组织机构代码:</td>
				<td><input type="text" name="orgcode"  readonly="readonly" value="<%=orgcode%>"/></td>
				<td style="text-align:right;">企业中文名称:</td>
				<td><input type="text" name="chName"  readonly="readonly" value="<%=chName%>"/></td> 
			</tr>
			
			<tr>
				<td style="text-align:right;">限额币种:</td>
				<td><input type="text" name="currency" readonly="readonly" value="<%=StringUtil.getCountryByCode("currency." + currency)%>"/></td>
				<td style="text-align:right;">账户限额:</td>
				<td><input type="text" name="capital" readonly="readonly" value="<%=capital%>"/></td>
			</tr>
			
			<tr style="height:20px;">
				<td style="background:green;text-align:center;" colspan="4">核准件其他信息</td>
			</tr>
			
			<tr>
				<td style="text-align:right;" >联系人:</td>
				<td><input type="text" name="contact" /></td>
				<td style="text-align:right;">联系电话:</td>
				<td><input type="text" name="contactPhone"/></td>
			</tr>
			
			<tr>
				<td style="text-align:right;">备注：</td>
				<td colspan="3"><textarea rows="3" cols="50" name="remark" style="overflow-y:scroll"></textarea></td>
				
			</tr>
			
			<tr style="height:20px;">
				<td style="background:green;text-align:center;" colspan="4">文件信息</td>
			</tr>
			
			<tr >
				<td style="text-align:right;">验资文件:</td>
				<td colspan="3"><input type="file" name="capitalDoc"/></td>
				
			</tr>
			
			<tr>
				<td style="text-align:right;">备注:</td>
				<td colspan="3"><textarea rows="3" cols="50"  name="docRemark" style="overflow-y:scroll"></textarea></td>
				
			</tr>
			
			<tr>
				<td style="text-align:right;" colspan="2"><input type="button" value="确定" onclick="saveAuth();"/></td>
				
				<td style="text-align:left;" colspan="2"><input type="button" value="返回"/></td>
			</tr>
					
		
		</table>
	</form>
</body>
</html>