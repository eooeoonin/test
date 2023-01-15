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

//添加
function submitBankInfo() {
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	  }
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/b_bank_info_list/BankInfo/add",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("添加成功");
	      location = "../basedata_mgt/b_bank_info_list.html";
		},error: function(){
			alert("添加失败");
			location = "../basedata_mgt/b_bank_info_list.html";
		}
			
	});
}


