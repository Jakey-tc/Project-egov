<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script>
	
	//ajax显示详细的核准件信息
	var XRequest;
	
	function selectAuth(){
		
		
		var authnoTag = document.getElementById("authno");
		var authno = authnoTag.value;
		
		

		if(window.XMLHttpRequest){
			
			XRequest = new XMLHttpRequest();
			
		}else if(window.ActiveXObject){
			
			XRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		XRequest.onreadystatechange = callback;
		
		XRequest.open("GET","/01-egov/authViewServlet?authno="+authno,true);
		
		XRequest.send();
		
	
		
		
	}
	
	function callback(){
		
		
		if(XRequest.readyState == 4){
			
			if(XRequest.status == 200){
				
				var authView = document.getElementById("authView");
				authView.innerHTML = XRequest.responseText;	
			
				
			}else if(XRequest.status == 404){
				
				alert("404-Not Found");
			}else if(XRequest.status == 500){
				alert("505-Server Inner Error");
			}else{
				
				alert("未知错误");
			}
					
		}
	}
</script>
</head>
<body>
	
	<div style="width:100%;height:20px;background-color:green;"></div>

	核准件编号：<input type="text" id="authno" name="authno"/>
	<input type="button" value="查询"  onclick="selectAuth()"/>
	
	<br/><br/>
	<div id="authView"></div>		
		
	
</body>
</html>