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
	
	location = "../basedata_mgt/TAGAdministration.html";
	
}

function submitedit() {
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/TAG/TAGInsert",
		data :formData,
		dataType : "json",
		success : function(data) {
			bootbox.alert("添加成功",function(){
				location = "../basedata_mgt/TAGAdministration.html";
			});
	},error: function(){
			bootbox.alert("添加失败",function(){
				location = "../basedata_mgt/TAGAdministration.html";
			});
	   }
	});
	  	}
}
