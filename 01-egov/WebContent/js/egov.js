//定义表单项类型
//类名：FormItem,属性包括两个：lable(表单项的名字),id（用户提交标签的id）
FormItem = function(label,id){
	
	this.label = label;
	this.id = id;
} 

//工具类
EGOV = function(){
	
	//非空检验函数（用于所有用户必填项的检验）
	this.isNotEmpty = function(formItemArray){
		
		var domObj;
		for(var i = 0;i<formItemArray.length;i++){
			domObj = document.getElementById(formItemArray[i].id);
			if(domObj.value == ""){
				
				alert(formItemArray[i].label + "不能为空，请重新输入");
				domObj.focus();
				return false;
			}
					
		}
		return true;
		
	}
	
	//一致性检验函数（一般用于密码和确认密码的检验）
	this.isSame = function(formItem1,formItem2){
		
		var id1 = formItem1.id;
		var label1 = formItem1.label;
		var domObj1 = document.getElementById(id1);
		
		var id2 = formItem2.id;
		var label2 = formItem2.label;
		var domObj2 = document.getElementById(id2);
		
		if(domObj1.value != domObj2.value){
			
			alert(label1 + "和" + label2 + "不一致，请重新输入");
			
			domObj1.value = "";
			domObj2.value = "";
			domObj1.focus();
			return false;
		}
		return true;
	}
}

var $ = new EGOV();