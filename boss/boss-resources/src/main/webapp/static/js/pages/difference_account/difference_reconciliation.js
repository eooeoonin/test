
var _unlock = $("#unlock"), _unlockform = $("#unlockform");

var _unlock2 = $("#unlock2"), _unlockform2 = $("#unlockform2");




$(function(){

		  laydate({elem: "#clearDate", format: "YYYY/MM/DD"});
		  laydate({elem: "#tradeCheckDate", format: "YYYY/MM/DD"});
		  laydate({elem: "#accountCheckDate", format: "YYYY/MM/DD"});
})
//删除
_unlock
		.click(function() {
			
			_unlockform.validationEngine('attach', {
				  maxErrorsPerField:1,
				  autoHidePrompt: true,
				  autoHideDelay: 2000
				});
			if (!_unlockform.validationEngine('validate')) {
			    return false;
			}else{
				var thData = {
						"clearDate":$("#clearDate").val()
				};
				$.ajax({
					type : "POST",
					url : "/difference_account/difference_reconciliation/delete",
					data : thData,
					dataType : "json",
					success : function(data) {
						alert(data);
					},
					error : function(data) {
						alert("删除失败");
					}
				});
			}		
		});


//对账
_unlock2
.click(function() {
	
	_unlockform2.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	if (!_unlockform2.validationEngine('validate')) {
	    return false;
	  
	}else{
//		  $("#unlock2").attr("disabled", "true");
		//单独
		if($("#checkBiz").val()!=1){
		var thData = {
				"checkBiz":$("#checkBiz").val(),
				"tradeCheckDate":$("#tradeCheckDate").val(),
				"accountCheckDate":$("#accountCheckDate").val()
		};
		$.ajax({
			type : "POST",
			url : "/difference_account/difference_reconciliation/check",
			data : thData,
			dataType : "json",
			success : function(data) {
//				$("#unlock2").removeAttr("disabled");
				alert("正在处理中");
			},
			error : function(data) {
				alert("对账失败");
			}
		});
		//全部
		}else {
			var thData = {
					"tradeCheckDate":$("#tradeCheckDate").val(),
					"accountCheckDate":$("#accountCheckDate").val()
			};
			$.ajax({
				type : "POST",
				url : "/difference_account/difference_reconciliation/checkAll",
				data : thData,
				dataType : "json",
				success : function(data) {
//					$("#unlock2").removeAttr("disabled");
					alert("正在处理中");
				},
				error : function(data) {
					alert("对账失败");
				}
			});
		}
	}		
});