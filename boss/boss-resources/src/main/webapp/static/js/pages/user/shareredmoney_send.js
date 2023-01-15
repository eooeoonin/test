
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

					var formData = {
							"userId":$("#userId").val(),
							"earningAmount":$("#earningAmount").val(),
							"redmoneyNum":$("#redmoneyNum").val(),
							"maxRedMoneyAmt":$("#maxRedMoneyAmt").val()
				};
					var formData1 = {
							"userId":$("#userId").val()
				};
					
					
					
					$.ajax({
						type:"POST",
						url:"/user/user_list/getById",
						data:formData1,
						dataType:"json",
						async : false,
						success:function(data){
							if(data.id!=null){
							$.ajax({
								type:"POST",
								url:"/user/user_list/createBigRedMoney",
								data:formData,
								dataType:"json",
								async : false,
								success:function(data1){
									if (data1 != null && data1 != "") {
										if (data1.msg == "success") {
											bootbox.alert("发放分享大红包成功", function(result){
												window.location.reload();
											});
										} else {
											bootbox.alert(data1.msg);
										}
									}
								},
								async : false
							});
						}else{
							bootbox.alert("没有找到用户");
						}
							
						}
						
					});
					
					
					
			}		
		});