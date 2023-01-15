
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
function submitedit1() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
		  var formData=$("#form").serialize();
		  var tdUrl = "/pay/channelConfig/insertChannelConfig";
		$.ajax({    
                type: "POST",
                url:tdUrl,
                data:formData,
                dataType : "json",
                success: function(data) {
                	  alert("保存成功");
                	  location = "../pay/channelConfiglist.html";
                },error: function(data){
        			alert("保存失败");
        		}
		 });
}