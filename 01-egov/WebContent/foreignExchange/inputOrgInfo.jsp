<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!--进行详细的企业登记页面，包括企业的信息和添加投资人-->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>企业信息以及投资人信息录入</title>
<script>
	
	//点击查询按钮打开投资人查询页面的函数
	function openOrgcodeSelect(){
		//打开一个子网页，设置网页的大小，位置，是否有菜单栏，工具栏，等等。
		window.open("/01-egov/foreignExchange/orgcodeSelect.jsp","newwindow","height=400,width=1100,top=100,left=100,toolbar=no,menubar=no, scrollbars=no,resizable=no,location=no,status=no");
	} 
	
	//删除由子页面添加的表单中指定某一行（执行这个函数的按钮所在的行）的函数
	function deleteRow(s){
		
		//获取当前行号，由于传入了按钮标签的参数，所以s就是按钮标签，可以通过它找到当前的行标签，再用rowIndex获取行号
		var rowIndex = s.parentNode.parentNode.rowIndex;
		//使用按钮标签获取table表格标签，然后使用deleteRow(行号)删除按钮标签所在的行。
		s.parentNode.parentNode.parentNode.deleteRow(rowIndex);
	}
	
	function calculate(){
		
		//获取投资资本的所有标签
		var regcapital = document.getElementsByName("regcapital");
		
		//获取注册资本标签（其中的value是总投资量，会自动计算出来）
		var capital = document.getElementsByName("capital");
		
		//获取国家类型的标签
		var country = document.getElementsByName("country");
		//获取外国资本标签
		var foreignCapital = document.getElementById("foreignCapital");
		//获取外方资本比例的标签
		var foreignRatio = document.getElementById("foreignRatio");
		
		//先零外方资本比例为0
		foreignRatio.value = 0;
		
		
	
		//定义一个变量进行总资本累加
		var ss = 0;
		//定义一个变量进行外方资本累加
		var aa = 0;
	
		
		for(var i=0;i<regcapital.length;i++){
			
			//判断只有当用户输入了资本后才会对这个资本进行累加，否则不会加，因为如果没有输入数字，那么累加后的数据类型是NaN。  
			if(regcapital[i].value != ""){
				
				ss += parseInt(regcapital[i].value);	
			}
			//如果国别不是中国，对外方资本的变量进行累加
			if(regcapital[i].value != "" && country[i].value != "中国"){
				
				aa += parseInt(regcapital[i].value);
			}
			
		}
		//将累加的结果赋值给总注册资本标签的value。完成注册资本的自动计算
		capital[0].value = ss;
		//将累加的结果赋值给外方资本标签的value。
		foreignCapital.value = aa;
		
		//进行外方资本比例的计算。只保留小数点后边两位
		foreignRatio.value = (aa * 100 / ss ).toFixed(2)+ "%";
		
		
		 
	}
	
	
</script>
</head>
<body>
	<form action="/01-egov/insertEnvServlet" method="POST">
		<table id="enreg" width="100%" border="1" cellspacing="0">
			<tr>
				<td colspan="5" width="100%" style="text-align:center;background-color:rgb(14,111,104)">企业基本信息</td>
			</tr>
			
			<tr>
				<td style="text-align:right;">组织机构代码：</td>
				<td><input type="text"  style="border:none" readonly="readonly" name="orgcode" value="<%=request.getParameter("orgcode") %>"></td>
				<td style="text-align:right;">外汇登记证号：</td>
				<td colspan="2"><input type="text" name="foreignNumber"/><font color="red">*</font></td>
				
			</tr>
			
			<tr>
				<td style="text-align:right;">企业中文名称：</td>
				<td><input type="text" name="chName"/><font color="red">*</font></td>
				<td style="text-align:right;">企业英文名称：</td>
				<td colspan="2"><input type="text" name="EnName"/><font color="red">*</font></td>
			</tr>
			
				
			<tr>
				<td style="text-align:right;">联系人：</td>
				<td><input type="text" id="contactName" name="contactName"/><font color="red">*</font></td>
				<td style="text-align:right;">联系电话：</td>
				<td colspan="2"><input type="text" name="contactPhone"/><font color="red">*</font></td>
			</tr>
			
			<tr>
				<td colspan="5" width="100%" style="text-align:center;background-color:rgb(14,111,104)">企业资金情况</td>
			</tr>
			
			<tr>
				<td style="text-align:right;">注册资本：</td>
				<td><input type="text" name="capital" disabled="disabled"/><font color="red">*</font></td>
				<td style="text-align:right;">注册币种：</td>
				<td colspan="2">
					<select name="currency">
						<option value="000">人民币</option>
						<option value="001">美元</option>
						<option value="002">欧元</option>
						<option value="003">英镑</option>
						<option value="004">日元</option>
						<option value="005">韩元</option>
					</select><font color="red">*</font>
				
				</td>
			</tr>
			
				<tr>
				<td style="text-align:right;">外方注册资本：</td>
				<td><input type="text" id="foreignCapital" readonly="readonly"/><font color="red">*</font></td>
				<td style="text-align:right;">外方出资比例：</td>
				<td colspan="2"><input type="text" id="foreignRatio" readonly="readonly"/></td>
			</tr>
			
			<tr>
				<td colspan="5" width="100%" style="text-align:center;background-color:rgb(14,111,104)">投资者资金及利润分配</td>
			</tr>
			 
			 
			<tr>
				<td style="text-align:center;">投资者名称</td>
				<td style="text-align:center;">国别</td>
				
				<td style="text-align:center;">注册资本出资额</td>
				<td style="text-align:center;">利润分配比例</td>	
				<td style="text-align:center"><input type="button" value="查询" style="cursor:pointer;" onclick="openOrgcodeSelect();"/></td>
			</tr>
		</table>
		<input type="submit" value="确定"/>
	</form>
</body>
</html>