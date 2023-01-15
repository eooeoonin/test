var formType;
var totalBalance;
$(function () {
	$.ajax({
		type : "POST",
		url : "../platformManager/transfer/init",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					$("#totalBalance").text(data.data[0].balance.amount.toFixed(2));
					totalBalance = data.data[0].balance.amount; 
					$("#targetBalance").text(data.data[1].balance.amount.toFixed(2));
					$("#amount").attr("class","form-control validate[required,custom[onlyNumberdouble]],max["+data.data[0].balance.amount+"],validate[custom[onlyNumberTwo]],validate[custom[gtZero]]");
				}else{
					bootbox.alert("数据加载异常");
				}
			}
		},
		async : false
	});
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#addProForm');
	_modalFm1.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 3000
	});
  /***
   *功能说明：复选框、单选框美化
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
    $(".i-checks").iCheck({
      checkboxClass: "icheckbox_square-green",
      radioClass: "iradio_square-green"
    });
});

$("#addSubmit").click(function() {
	
	if (!$("#addProForm").validationEngine('validate')) {
		return false;
	}	
	redpacketsFun();

});

$("#subTran").click(function() {
	var userIdIn = $('#tradeState option:selected')[0].value;
	var amount = $('#amount')[0].value;
	var pdata ={'userIdIn':userIdIn,'amount':amount};
	$.ajax({
		type : "POST",
		url : "/platformManager/transfer/subTran",
		data : pdata,
		dataType : "json",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					$('#myModal').modal('hide');
					bootbox.alert("转账成功", function(result) {
						 $("#tradeState").trigger("change");
						 $("#amount")[0].value = "";
						 $("#afterBalance")[0].innerHTML = "";
						 window.location.reload();
					});
				}else{
					$('#myModal').modal('hide');
					bootbox.alert("请求失败");
				};
			};
		},
		async : false
	});
});

$("#amount").blur(function(){
	var a = this.value;
	if(a <= 0){
		$("#afterBalance").text(0);
	}else if(a > totalBalance){
		$("#afterBalance").text(0);
	}else{
		var b = $("#targetBalance")[0].innerHTML;
		var amount = a*1 + b*1;
		$("#afterBalance").text(amount.toFixed(2));
	};
}); 
$("#tradeState").change(function(){
	var userId = $(this).children('option:selected').val();
	$.ajax({
		type : "POST",
		url : "/platformManager/transfer/balance",
		data : {"userId":userId},
		dataType : "json",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					$("#targetBalance").text(data.data.businessObject.balance.amount);
				}else{
					bootbox.alert("数据获取异常");
				};
			};
		},
		async : false
	});
});
function getUrlParam(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	   var r = window.location.search.substr(1).match(reg); //匹配目标参数
	   if (r != null) return unescape(r[2]); return null; //返回参数值
	  };
function redpacketsFun() {
	$('#mbzh').text($('#tradeState option:selected')[0].text);
	$('#zzje').text($('#amount')[0].value);
	$('#zhje').text($('#targetBalance')[0].textContent);
	$('#zham').text($('#afterBalance')[0].textContent);
	$('#myModal').modal('show');
	};	  
	  