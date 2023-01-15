/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _lgBtn = $("#loginBtn"), _lgFm =  $('#loginForm');
//表单元素验证
_lgFm.validationEngine('attach', {
  maxErrorsPerField:1,
  autoHidePrompt: true,
  autoHideDelay: 2000
});
//表单提交
_lgBtn.click(function () {
  if (!_lgFm.validationEngine('validate')) {
    return false;
  }
	  $.ajax({
		type : "POST",
		url : "/admin/login",
		data : {
			staffName : $("#username").val(),
			password : $("#password").val()
		},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					if ("change" == data.data){
//						window.location.href = "/index.html?random="+ Math.random()*1000;
						alert("为了您的密码安全，建议更改密码");
						window.location.href = "/change_pwd.html?random="+ Math.random()*1000;
						$.cookie("username", $('#username').val(), {path : "/"});
					} else {
						$.cookie("username", $('#username').val(), {path : "/"});
						window.location.href = "/index.html?random="+ Math.random()*1000;
					}
				} else {
					$('.er_prm').html(data.msg);
					return false;
				}
			}
		},
		async : false
	});
	  
});

//回车提交
$(document).keyup(function(event){
	if(event.keyCode ==13){
		_lgBtn.trigger("click");
	}
});

//修改初始密码
function changePSW(staffId){
	  bootbox.confirm("确定要修改初始密吗?", function(result){
			if(result){
				$.ajax({
					type : "DELETE",
					url : "/permission/staff/delete/"+ staffId,
					data : {
						"staffId" : staffId
					},
					success : function(data) {
						if (data != null && data != "") {
							if (data.resCode == 0) {
								bootbox.alert("员工删除成功", function(result){
									window.location.reload();
								});
							} else {
								bootbox.alert(data.msg);
							}
						}
					},
					async : false
				});
			}

		});
}