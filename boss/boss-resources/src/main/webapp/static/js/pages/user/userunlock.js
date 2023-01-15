
var _unlock = $("#unlock"), _unlockform = $("#unlockform");
_unlockform.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 2000
	});
_unlock
		.click(function() {
			if (!_unlockform.validationEngine('validate')) {
			    return false;
			}else{
				var thData = {
						"mobile":$("#mobile").val()
				};
				$.ajax({
					type : "POST",
					url : "/user/userunlock/smsUnBindCard",
					data : thData,
					dataType : "json",
					success : function(data) {
						alert(data.result);
					},
					error : function(data) {
						alert("解锁失败");
					}
				});
			}		
		});