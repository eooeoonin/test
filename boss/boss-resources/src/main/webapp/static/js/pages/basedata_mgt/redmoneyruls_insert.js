
$(function() {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	$("#merchantName1").val("P2P");
	$("#minAmount1").val("0");
	$("#maxAmount1").val("0");
});
function selecttype(){
	var cc = $("#type1").val();
	if(cc=="random_invest"){
		$(".oicd").show();
	}else{
		$(".oicd").hide();
	}
}
function submitedit1() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
		  var formData=$("#form").serialize();
		  var tdUrl = "/basedata_mgt/redmoneyrules/redmoneyRuleadd";
		$.ajax({    
                type: "POST",
                url:tdUrl,
                data:formData,
                dataType : "json",
                success: function(data) {
                	  alert("保存成功");
                	  location = "../basedata_mgt/redmoneyrules_list.html";
                },error: function(data){
        			alert("保存失败");
        		}
		 });
}