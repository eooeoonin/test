
$(function() {
	  $(".i-checks").iCheck({
		    checkboxClass: "icheckbox_square-green"
		  });
	  
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
//发短信
	function submitBannerInfoTwo(){
		if($("#sendAll").parent(".checked").length > 0){
			var content=$("#content").val().trim().replace(/[\n\r]/gi,"").replace(/[ ]/g,"");
			if($("#content").val().length <=0 ){
				alert("给平台所有用户发短信内容不能为空");
				return false;
			}
			if("1"== $("#sendAll").val()){
				$("#save").attr("disabled","disabled").addClass("btn-white");
				
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/shortmessage_list/batch",  //发短信
					dataType : "json",
					data :{
						   "content":content
					},
					success : function(data) {
						if (data != null && data != "") {
							if (data.resCode == 0) {
								bootbox.alert("给平台所有用户发短信成功", function(){
									$("#save").removeAttr("disabled").removeClass("btn-white");
									window.location.reload();
								});
							}else{
								$("#save").removeAttr("disabled").removeClass("btn-white");
								bootbox.alert(data.msg);
							}
						}
				     
					},error: function(){	
					  alert("发送失败");
					}
					
				});
			}
			
		}else{
			var phoneOrUserId = $("#phoneOrUserId").val().trim().replace(/[\n\r]/gi,"").replace(/[ ]/g,"");
			var content=$("#content").val().trim().replace(/[\n\r]/gi,"").replace(/[ ]/g,"");
			
			//判断是否为空
			if (!_modalFm1.validationEngine('validate')) {
			    return false;
			}
			
		$.ajax({
				type : "POST",
				url : "/basedata_mgt/shortmessage_list/short",  //发短信
				dataType : "json",
				data :{"phoneOrUserId":phoneOrUserId,
					   "content":content,
					   "typeSelect":$("#type_select").val()
				},

				success : function(data) {
					
			      alert("发送成功");
			     
				},error: function(){	
				  alert("发送失败");
					
				}
				
			});
		}
		
	}
