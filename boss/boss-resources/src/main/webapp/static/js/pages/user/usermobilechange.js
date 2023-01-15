
var _change = $("#change"), _mobilechange = $("#mobilechange");
_mobilechange.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 2000
	});
_change
		.click(function() {
			if (!_mobilechange.validationEngine('validate')) {
			    return false;
			}else{
//				if($("#newMobile").val() != $("#newMobile2").val()) {
//					alert("新手机号两次输入不一致");
//				}
			
					//var formData = $("form").serialize();
					var formData = {
							"orginMobile":$("#orginMobile").val(),
							"newMobile":$("#newMobile").val()
				};
					$.ajax({
						type:"POST",
						url:"/user/userunlock/changeMobile",
						data:formData,
						dataType:"json",
						async : false,
						success:function(data){
							alert(data.result);
							$('#mobilechange')[0].reset();
						},
						error:function(data){
							alert("手机号变更失败");
						}
						
					});
			}		
		});