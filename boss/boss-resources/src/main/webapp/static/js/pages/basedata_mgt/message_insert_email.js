

//插入
$(function(){
	var ue = UE.getEditor('miaoshu');
	
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

function fanhui(){
	
	location = "../basedata_mgt/message.html";
	
}


function submitedit() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/message/messageInsert",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("插入成功");
	      location = "../basedata_mgt/message.html";
		},error: function(){
			alert("插入成功成功");
			location = "../basedata_mgt/message.html";
		}
			
	});
	  }
}

function checkKey(){
	if(document.getElementById("key").value.length !=""){
		document.getElementById("label_key").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#key").val("").focus();
		document.getElementById("label_key").innerHTML="<font color='red'>值不能为空</font>";
		 return false;
	}
}
function checkMer(){
	if(document.getElementById("merchant").value.length !=""){
		document.getElementById("label_merchant").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#merchant").val("").focus();
		document.getElementById("label_merchant").innerHTML="<font color='red'>merchant不能为空</font>";
		 return false;
	}
}

function checkSub(){
	if(document.getElementById("subject").value.length !=""){
		document.getElementById("label_subject").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#subject").val("").focus();
		document.getElementById("label_subject").innerHTML="<font color='red'>消息名称不能为空</font>";
		 return false;
	}
}

function checkContent(){
	if(document.getElementById("miaoshu").value.length !=""){
		document.getElementById("label_miaoshu").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#miaoshu").val("").focus();
		document.getElementById("label_miaoshu").innerHTML="<font color='red'>短信内容不能为空</font>";
		 return false;
	}
}

function checkType(){
	if(document.getElementById("type").value.length !=""){
		document.getElementById("label_type").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#type").val("").focus();
		document.getElementById("label_type").innerHTML="<font color='red'>消息类型不能为空</font>";
		 return false;
	}
}
function select(){
	
	if(document.getElementById("type").value=="INNER_MESSAGE"){
		   location = "message_insert_inner.html";
	}else if(document.getElementById("type").value=="EMAIL"){
		   location = "message_insert_email.html";
	}else if(document.getElementById("type").value=="PUSH"){
		   location = "message_insert_push.html";	
	}else if(document.getElementById("type").value=="SMS"){
		   location = "message_insert_sms.html";
	}	
	
}
	

