/**
 * 
 */

var addErrorCode = function(){
	//var formData=$("form").serialize();
	var 
	systemCode =  $("#systemCode").val().substring(0,3),
	systemName =  $("#systemName").val(),
	code =  $("#code").val(),
	message =  $("#message").val(),
	level =  $("#level").val(),
	result =  $("#result").val(),
	extend =  $("#extend").val(),
	version =  $("#version").val(),
	status =  $("#status").val(),
	editedBy =  $("#editedBy").val();
	var formData = {
			"systemCode":systemCode,
			"systemName":systemName,
			"code":code,
			"message":message,
			"level":level,
			"result":result,
			"extend":extend,
			"version":version,
			"status":status,
			"editedBy":editedBy
	};
	$
	.ajax({
		type : "POST",
		url : "/basedata_mgt/errorcode_manage/addErrorCode",
		data : formData,
		dataType : "json",
		success : function(data) {
			alert(data.result);
			//location = "../basedata_mgt/errorcode_manage.html";
		}
	});
};

$(function() {
	
	var thisURL = document.URL;    
	var getval =thisURL.split('?')[1];  
	var systemCode= getval.split("=")[1].split("_",2)[0];
	var systemName= getval.split("=")[1].split("_",2)[1];
	showvalf();
	function  showvalf(){ 
	   $("#systemCode").val(systemCode);
	   $("#systemName").val(systemName);
	};
	
	_errFm =  $('#form');
	_errFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
});


$("#addErrorCode").click(function(){
	if (!_errFm.validationEngine('validate')) {
	    return false;
	  }
	addErrorCode();
});


