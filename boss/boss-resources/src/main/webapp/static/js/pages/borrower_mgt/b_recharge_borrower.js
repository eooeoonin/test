
$(function () {
	
	$("#rechargeSubmit").click(function() {
			$.ajax({
				type : "POST",
				url : "/borrower/recharge/submit",
				data : $("#rechargeForm").serialize(),
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							bootbox.alert("操作成功", function(){
								window.location.href = "borrower/borrowerlist.html";
							});
						}else{
							bootbox.alert(data.msg);
						}
					}
				},
				async : false
			});
		  
		
	});

});