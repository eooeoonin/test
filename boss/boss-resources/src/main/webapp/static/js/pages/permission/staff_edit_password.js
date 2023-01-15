
function _getParamsObject(){
	var thisURL = document.URL;    
	var getval =thisURL.split('?')[1];
	var paramsArray = getval.split("&");
	var paramsObject={};
	for(var i=0; i< paramsArray.length; i++){
		var keyAndValue = paramsArray[i].split("=");
		paramsObject[keyAndValue[0]] = keyAndValue[1];
	}
	return paramsObject;
}




$(function() {
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#editStaffPasswordForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
});



$("#editStaffPasswrodSubmit").click(function() {
	 $('.er_prm').html("");
	  if (!$("#editStaffPasswordForm").validationEngine('validate')) {
		    return false;
		  }
	  if($("#password").val() != $("#passwordConfirm").val()){
		  $('.er_prm').html("新密码两次输入不一致");
		  return false;
	  }
	  var pswStrong = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-._]).{8,}$/;
	  var newPwdVal = $("#password").val();		  
	  if(!pswStrong.test(newPwdVal)){
		  $('.er_prm').html("密码强度不够，必须大于8位、包含大写字母、小写字母、数字、特殊字符");
		  return false;
	  }
	  
	  var staffId =  _getParamsObject().staffId;	  
	$.ajax({
		type : "POST",
		url : "/permission/staff/changePassword",
		data : {
			staffId : staffId,
			newPassword : $("#password").val()
		},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "staff.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
});
