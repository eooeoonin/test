var _mobilechange = $("#mobilechange");
_mobilechange.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 2000
});
$("#addSubmit").click(function(){
	if (!_mobilechange.validationEngine('validate')) {
	    return false;
	}
	$.ajax({
		type : "POST",
		url : "/user/user_list/createBatchRedMoney",
		dataType : "json",
		data : {'amount':$("#redmoney").val()},
		success : function(data) {
			if(data.resCode == 0){
				if (data.msg == "success") {
					bootbox.alert("发放成功", function(result){
						window.location.reload();
					});
				} else if(data.msg == "literror"){
					bootbox.alert("发放部分失败");
				}else if(data.msg == "lockerror"){
					bootbox.alert("正在发送请不要重复提交申请");
				}else{
					bootbox.alert("发放失败");
				}
			}else{
				bootbox.alert("发放失败");
			}
		},
		async : false,
		error : function(){
			bootbox.alert("发放失败");
		}
	});
});