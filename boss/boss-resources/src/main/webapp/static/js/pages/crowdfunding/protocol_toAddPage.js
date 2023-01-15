	
$(function () {
	
	_modalFm1 =  $('#addForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
	var ue = UE.getEditor('content');
});


$("#save").click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
  
	var ue = UE.getEditor('content');
	var bodyContent = ue.getContentLength(true)
	if(bodyContent < 1){
		alert("内容不能为空");
		return false;
	}
	if(bodyContent > 5000){
		alert("内容长度不能超过5000");
		return false;
	}
	
	$.ajax({
		type : "POST",
		url : "/crowdfunding/protocol/protocolAdd",
		data : $("#addForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "/crowdfunding/protocol.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
});

$("#reSet").click(function(){
	_modalFm1[0].reset();
	var ue = UE.getEditor('content');
	ue.execCommand("cleardoc");
});