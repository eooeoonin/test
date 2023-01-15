$(function(){
	
	_modalFm1 =  $('#changePasswordForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	$("input[type='passowrd']").val("");
	var _changePasswordFm = $("#changePasswordForm");

	$("#changePassword").click(function() {
		 $('.er_prm').html("");
		  if (!_changePasswordFm.validationEngine('validate')) {
			    return false;
			  }
		  
		  if($("#newPassword").val() != $("#newPasswordConfirm").val()){
			  $('.er_prm').html("新密码两次输入不一致");
			  return false;
		  }
		  var pswStrong = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-._]).{8,}$/;
		  var newPwdVal = $("#newPassword").val();		  
		  if(!pswStrong.test(newPwdVal)){
			  $('.er_prm').html("密码强度不够，必须大于8位、包含大写字母、小写字母、数字、特殊字符");
			  return false;
		  }
		  
		  
		$.ajax({
			type : "POST",
			url : "/admin/changePassword",
			data : {
				originPassword : $("#originPassword").val(),
				newPassword : $("#newPassword").val()
			},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("密码修改成功", function(){
							window.location.href = "/index.html?random="+ Math.random()*1000;
						});
					}
					else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});
	});
	
	
});
