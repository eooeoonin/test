
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
});
function submitedit() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
		  var formData=$("#form").serialize();
		  var tdUrl = "/pay/returnCode/insertReturnCode";
		$.ajax({    
                type: "POST",
                url:tdUrl,
                data:formData,
                dataType : "json",
                success: function(data) {
                	  alert("保存成功");
                	  location = "../pay/returnCodelist.html";
                },error: function(data){
        			alert("保存失败");
        		}
		 });
}