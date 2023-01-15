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


//修改
function submitedit() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/message/message/edit",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/message.html";
		},error: function(){
			alert("修改成功");
			location = "../basedata_mgt/message.html";
		}
			
	});
	  }
}


