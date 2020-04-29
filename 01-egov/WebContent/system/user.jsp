<!--用户信息显示的jsp页面  -->
<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.bjpowernode.egov.beans.User"%>
<%@ page import="com.bjpowernode.egov.system.utils.*"%>
<%@ page import="com.bjpowernode.egov.beans.Page"%>

<%
	//首先使用的是PageQueryUserServlet2分页查询（不封装page对象时数据的获取方法，我全部注释了起来）
	/*获取保存当前页面的User对象的List集合  */
	//List<User> userList = (List<User>)request.getAttribute("userList");
	
	/*获取当前页面的一些信息。  */
	//Integer pagecount = (Integer)request.getAttribute("pagecount");
	//Integer pagesize = (Integer)request.getAttribute("pagesize");
	//Integer pageno = (Integer)request.getAttribute("pageno");
	//Integer totalsize = (Integer)request.getAttribute("totalsize");
	
	//下面是使用了PageQueryUserServlet3（封装page对象）时的数据获取方法
	Page<User> pages = (Page<User>)request.getAttribute("page");
	

	List<User> userList = pages.getUserList();
	
	
	Integer pagecount = pages.getPagecount();
	Integer pagesize = pages.getPagesize();
	Integer pageno = pages.getPageno();
	Integer totalsize = pages.getTotalsize();
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#table1 {
	width: 100%;
}

#table1 tr {
	height: 50px;
}

#table1 tr td {
	text-align: center;
}
</style>
<script type="text/javascript">
	
	
	//实现点击按钮页码跳转功能
	function changePage(pageno){
		
		document.location="/01-egov/pageQueryUserServlet3?pageno=" + pageno;
	}
	
	//实现输入页码后点击跳转按钮跳转功能
	function jumpPage(){
		
		//通过id获取text标签对象
		var jumpnumberTag = document.getElementById("jumptext");
		//获取这个text标签对象的value值，即用户输入的页码
		//注意value是字符串，需要转为int类型
		var number = parseInt(jumpnumberTag.value);
		
		//非空校验
		if(jumpnumberTag.value == ""){
			alert("不能为空");
			jumpnumberTag.focus();
			return;
			
		}
		//数字校验
		if(isNaN(number)){
			
			alert("请输入数字");
			jumpnumberTag.focus();
			return;
		}
		//范围校验
		if(number < 1 || number > <%=pagecount%>){
			
			alert("输入的页码不存在，请重新输入");
			jumpnumberTag.focus();
			return;
		}
		
		//跳转到用户输入的页码
		changePage(number);
	}
	
	
	//控制“删除”和“修改”这两个按钮是否能被点击。
	function controlDeleteAndUpdate(){
		
		//设置一个变量用于计算被需选择的复选框数量
		var count = 0;
		
		//获取所有复选框标签对象的数组
		var selects = document.getElementsByName("selects");	
		
		//获取修改按钮的标签对象
		var update = document.getElementById("update");
		
		//获取删除按钮的标签对象
		var deletes = document.getElementById("delete");
		
		//循环检验每一个复选框是否被选中，如果被选中，count变量增加。
		for(var i=0;i<selects.length;i++){
			if(selects[i].checked){
				
				count++;
				
			}
		}
		
		//注意：还可以在下面各个条件中使用js修改这些按钮的背景图片。效果更佳。
		//如果被选中的复选框是0个，删除和修改按钮都不能被点击。
		if(count == 0){
			
			deletes.disabled = true;
			update.disabled = true;
		}
		
		//如果被选中的复选框是1个，删除和修改按钮都能被点击。
		if(count == 1){
			
			
			deletes.disabled = false;
			update.disabled = false;
		}
		
		//如果被选中的复选框大于1个，删除按钮能被点击，而修改按钮不能被点击。
		if(count > 1){
			
			
			deletes.disabled = false;
			update.disabled = true;
		}
			
	}
	
	//全选按钮功能函数
	//定义一个变量表示是否所有选项已经被选择。一开始默认全部没有被选中
	var isSelected = false;
	function selectAll(){
		//每执行一次这个功能函数，表示“被选择的”变量就会取它的相反值。
		isSelected = !isSelected;
		var selects = document.getElementsByName("selects");
		
			
			for(var i = 0;i<selects.length;i++){
				//实现全选或全不选
				selects[i].checked = isSelected;	
						
			}
	
	}
	
	
	//反选按钮功能函数
	function selectOppsite(){
		
		var selects = document.getElementsByName("selects");
		for(var i = 0;i<selects.length;i++){
			//如果循环到的复选框被选择了，就取消他的选择属性，否则如果他的复选框没被选择，就给予它选择属性。
			//注意一定要使用if + else if ,不能使用两个if。因为两个if一定都会执行。
			if(selects[i].checked){
				selects[i].checked = false;
			}else if(!selects[i].checked){
				
				selects[i].checked = true;
			}
		}
		
	}
	
	//提交要修改的用户信息到SelectUserServlet
	function goUpdate(){
		
		//更改userForm表单的action属性值，即改变这个表单的提交路径
		//这是由于修改用户和删除用户都要用到这个表单的数据，而这两个功能的Servlet是不同的，所以需要改变表单提交路径
		document.forms["userForm"].action="SelectUserServlet";
		
		document.forms["userForm"].submit();
		
	}
	
	//选中一条信息后点击删除按钮删除这条信息。（不能使用js把这个标签删除，而是在数据库中删除，彻底的删除。）
	function deleteUser(){
		
		var ok = window.confirm("确定要删除吗");
		if(ok){
			
			//更改userForm表单的action属性值，即改变这个表单的提交路径
			//这是由于修改用户和删除用户都要用到这个表单的数据，而这两个功能的Servlet是不同的，所以需要改变表单提交路径
			document.forms["userForm"].action="deleteUserServlet";
			
			document.forms["userForm"].submit();	
		}
		
	}
	
	
</script>
</head>
<!-- 在body页面加载时就执行controlDeleteAndUpdate()。因为此时没有复选框被选择，修改和删除按钮要变得不能被点击。 -->
<body onload=" controlDeleteAndUpdate()">
	<input type="button" value="新增" id="create"
		onclick="document.location='/01-egov/system/userAdd.jsp'" />
	<input type="button" value="更改" id="update" onclick="goUpdate();" />
	<input type="button" value="删除" id="delete" onclick="deleteUser();" />
	<input type="button" value="全选" id="selectall" onclick="selectAll();" />
	<input type="button" value="反选" id="selectoppsite"
		onclick="selectOppsite();">



	<form action="SelectUserServlet" method="POST" name="userForm">
		<!-- 设置隐藏域，把页码pageno放入其中，点击修改按钮提交表单时会把这个隐藏域的信息提交上去到SelectUserServlet中 -->
		<input type="hidden" name="pageno" value="<%=pageno%>">

		<table border="1" cellspacing="0" id="table1">
			<tr>
				<td>选择</td>
				<td>序号</td>
				<td>用户代码</td>
				<td>用户姓名</td>
				<td>机构类型</td>
			</tr>
			<%
				int i = 0;
				for(User user:userList){
			%>
			<tr>
				<td><input type="checkbox" value="<%=user.getUsercode() %>"
					name="selects" onclick="controlDeleteAndUpdate()" /></td>
				<td><%= ++i %></td>
				<td><%=user.getUsercode() %></td>
				<td><%=user.getUsername() %></td>
				<td><%=StringUtil.getTextByCode(user.getOrgtype()) %></td>
				<!--这里遇见一个暂时未解决的问题,如果在上边这个td中的内容使用< %=StringUtil.getTextByCode(user.getOrgtype)%>
							就会出现异常,我认为原因是系统不能解析user.getOrgtype这个方法而获取其中的值.因为没有直接写在< %=%>里面.
							所以我使用了第一种方法即三目运算符解决机构类型的显示问题.2019-11-22
							
							现在问题已解决，原因不是上边的问题，而是我配置文件中没有关于“管理人员”这个value值的配置。而数据库中有这个信息。所以报错。
						  -->

			</tr>
			<%
				}
			%>

		</table>
	</form>


	<table border="0px" cellspacing="0" style="width: 100%">
		<%
			/*定义两个boolean类型的变量判断是否当前页时首页以及当前页是末页  */
			boolean isFirstPage = (pageno == 1);
			boolean isLastPage = (pageno == pagecount);
		%>

		<tr>

			<td width="700px"><span>第<%=pageno %> / <%=pagecount %>
					页，当前页最多显示记录 <%=pagesize %> 条，总记录 <%=totalsize %> 条
			</span></td>
			<td><input type="button" value="首页"
				<%= isFirstPage ? "" : "style='cursor:pointer' onclick='changePage(1)'" %> /></td>
			<td><input type="button" value="上一页"
				<%= isFirstPage ? "" : "style='cursor:pointer' onclick='changePage(" + (pageno-1) + ")'" %> /></td>
			<td><input type="button" value="下一页"
				<%= isLastPage ? "" : "style='cursor:pointer' onclick='changePage(" + (pageno+1) + ")'" %> /></td>
			<td><input type="button" value="末页"
				<%= isLastPage ? "" : "style='cursor:pointer' onclick='changePage(" + pagecount + ")'" %> /></td>
			<td><span>跳转到第<input type="text" id="jumptext"
					style="width: 50px" />页
			</span></td>
			<td><span><input type="button" value="跳转"
					onclick="jumpPage();" /></span></td>
		</tr>
	</table>
</body>
</html>