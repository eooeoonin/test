$(function () {
	var tdUrl = "../platformManager/account/getPlatformAccount";
	var tbData = {"userId":$("#user_id").val()};
	tableFun(tdUrl,tbData);
	document.getElementById('extend').style.display='none';
})


　$('#user_id').change(function(){  
	var tdUrl = "../platformManager/account/getPlatformAccount";
	var tbData = {"userId":$("#user_id").val()};
	tableFun(tdUrl,tbData);
	document.getElementById('extend').style.display='none';
　　　　})  

   //账户余额
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
		  if(data.resCode == 0){
			  $("#balance").text(data.data.balance.amount+"元");
		  }else{
			  alert(data.msg);
		  }
		},error : function(data){
			alert(data);
		}
	});


}
	
	
	var _srhBtn = $("#srhBtn");
	_srhBtn.click(function () {
	$("#selected").validationEngine('attach',{
		inlineValidation: false,
		maxErrorsPerField:1,
		autoHidePrompt: true,
		autoHideDelay: 2000
	});
	if (!$("#selected").validationEngine('validate')) {
		$("#selected").validationEngine('detach');
		 return false;
	} 
	accountRecharge();
	});
	

	function accountRecharge(){
		var _rechargeMoney = $("#rechargeMoney").val();
		var _userId = $("#user_id").val();
		$.ajax({
			type : "POST",
			url : "../platformManager/account/recharge",
			data : {
					   "amount":_rechargeMoney,
					   "userId":_userId
					  },
			dataType : "json",
			success : function(data) {
                if (data != null && data != "") {
                    if (data.resCode == 0) {
                        var _sendUrl = data.data.sendUrl;
                        var _data = data.data.sendForm;
                        document.all("extend").setAttribute("action",_sendUrl);
                        document.all("extend").setAttribute("target",'_blank');
                        document.getElementById("keySerial").value = _data.keySerial;
                        document.getElementById("platformNo").value = _data.platformNo;
                        document.getElementById("reqData").value = _data.reqData;
                        document.getElementById("serviceName").value = _data.serviceName;
                        document.getElementById("sign").value = _data.sign;

                        document.getElementById("extend").submit();
                    }else{
                        alert(data.msg);
					}
                }else{
                	alert("接口请求失败");
				}


			},error : function(data){
				alert("接口请求失败");
			}
		});
	}
	
	
	