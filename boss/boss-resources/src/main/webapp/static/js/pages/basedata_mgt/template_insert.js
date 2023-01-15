$(function (){
	
	
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	
	

});

function quxiao(){
	
	location = "../basedata_mgt/TemplatePara.html";
	
}

function submitedit() {
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/TemplatePara/templateParaInsert",
		data :formData,
		dataType : "json",
		success : function(data) {
			bootbox.alert('插入成功',function(){
				 location = "../basedata_mgt/TemplatePara.html";})
		},error: function(){
			bootbox.alert('插入失败',function(){
				 location = "../basedata_mgt/TemplatePara.html";})
		}
			
	});
	  	}
}



function checkName(){
	if(document.getElementById("name").value.length !=""){
		document.getElementById("label_name").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#name").val("").focus();
		document.getElementById("label_name").innerHTML="<font color='red'>变量名称不能为空</font>";
		 return false;
	}
}

function checkCode(){
	if(document.getElementById("code").value.length !=""){
		document.getElementById("label_code").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#code").val("").focus();
		document.getElementById("label_code").innerHTML="<font color='red'>变量码不能为空</font>";
		 return false;
	}
}

function checkContent(){
	if(document.getElementById("content").value.length !=""){
		document.getElementById("label_content").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#content").val("").focus();
		document.getElementById("label_content").innerHTML="<font color='red'>变量内容不能为空</font>";
		 return false;
	}
}