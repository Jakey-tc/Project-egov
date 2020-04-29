<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
#topModel {
	width: 100%;
	background-color: green;
	height: 20px;
}
</style>

<script src="/01-egov/js/egov.js"></script>

<script>
	
	function doSave(){
		
		var ok = validateForm();
		if(ok){
			
			document.forms["investForm"].submit();
		}
	}
	
	function validateForm(){
		
		var itemObj1 = new FormItem("投资人名称","investname");
		var itemObj2 = new FormItem("所属国家/地区","country");
		var array = [itemObj1,itemObj2];
		
		return $.isNotEmpty(array);
		
	}
	
</script>
</head>
<body>
	<div id="topModel"></div>
	<form name="investForm" action="/01-egov/savaInvestInfoServlet"
		method="POST">
		<table width="100%" border="1px">
			<tr>
				<td width="300px" style="text-align: right">投资人名称：</td>
				<td><input type="text" name="investname" id="investname"><font
					color="red">*</font></td>
			</tr>

			<tr>
				<td width="300px" style="text-align: right">所属国家/地区：</td>
				<td><select name="country" id="country">
						<option value="000" selected>中国</option>
						<option value="001">美国</option>
						<option value="002">英国</option>
						<option value="003">日本</option>

				</select> <font color="red">*</font></td>
			</tr>

			<tr>
				<td width="300px" style="text-align: right">组织机构代码：</td>
				<td><input type="text" name="orgcode" id="orgcode"></td>
			</tr>

			<tr>
				<td width="300px" style="text-align: right">联系人</td>
				<td><input type="text" name="contact" id="contact"></td>
			</tr>

			<tr>
				<td width="300px" style="text-align: right">联系电话</td>
				<td><input type="text" name="contactPhone" id="contactPhone"></td>

			</tr>

			<tr>
				<td width="300px" style="text-align: right">电子信箱</td>
				<td><input type="text" name="email" id="email" /></td>
			</tr>

			<tr>
				<td width="300px" style="text-align: right">备注</td>
				<td><textarea cols="70" rows="5" name="remark" id="remark"></textarea></td>
			</tr>

			<tr>
				<td width="300px" style="text-align: right; border: none"></td>
				<td width="50px" style="border: none"><input type="button"
					value="保存" onclick="doSave();"> <input type="button"
					value="清除" onclick="document.forms[0].reset();" /> <input
					type="button" value="返回"
					onclick="document.location='/01-egov/basicinfo/exoticOrgList.jsp'" />
				</td>

			</tr>
		</table>
	</form>
</body>
</html>