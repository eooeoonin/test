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
	
	location = "../basedata_mgt/eventManagement.html";
	
}

function submitedit() {
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/event/eventInsert",
		data :formData,
		dataType : "json",
		success : function(data) {
			bootbox.alert("新增事件成功",function(){
				location = "../basedata_mgt/eventManagement.html";
			});
	     // location = "../basedata_mgt/eventManagement.html";
	      
	      

			//
	      
		},error: function(){
			alert("新增事件失败");
			location = "../basedata_mgt/eventManagement.html";
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
		document.getElementById("label_name").innerHTML="<font color='red'>事件名称不能为空</font>";
		 return false;
	}
}

function checkContent(){
	if(document.getElementById("event").value.length !=""){
		document.getElementById("label_event").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#event").val("").focus();
		document.getElementById("label_event").innerHTML="<font color='red'>事件码不能为空</font>";
		 return false;
	}
}