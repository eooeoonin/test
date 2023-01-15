/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}
var Request = {};
Request = GetRequest();

$(function () {
	$(document).on('ready', function () {
	    $.ajax({
	      type: "POST",
	      url: "/pay/transactionlog/getLogById",
	      data:{id:Request.Id},
	      success: function (data) {
	        if (data != null && data != "") {
	          if (data.resCode == 0) {
	        	  var d_business = "";
				  switch(data.data.business){
						case "NetBank":
							d_business="网银业务";
							break;
						case "Quick":
							d_business="快捷业务";
							break;
						case "POS":
							d_business="POS刷卡";
							break;
						case "Withdraw":
							d_business="提现";
							break;
						case "RealName":
							d_business="实名认证";
							break;
						case "BindCard":
							d_business="绑卡认证";
							break;
						default:
							d_business = data.data.business;
						break;
					
				};
				var d_orderStatus = data.data.orderStatus;
				switch(data.data.orderStatus){
					case 0:
						d_orderStatus="未开始";
						break;
					case 2:
						d_orderStatus="处理中";
						break;
					case 1:
						d_orderStatus="交易成功";
						break;
					case -1:
						d_orderStatus="交易失败";
						break;
					case -2:
						d_orderStatus="已撤销";
						break;
					case -3:
						d_orderStatus="已过期";
						break;
					case -4:
						d_orderStatus="订单不存在";
						break;
					case -5:
						d_orderStatus="重复订单号";
						break;
					case -6:
						d_orderStatus="订单未支付";
						break;
					case -7:
						d_orderStatus="已退款";
						break;
					default:
						d_orderStatus = data.data.orderStatus;
					break;
				
				};
				var d_transaction = "";
				switch(data.data.transaction){
					case "Pay":
						d_transaction="支付";
						break;
					case "BindCard":
						d_transaction="绑卡";
						break;
					case "RealName":
						d_transaction="实名认证";
						break;
					case "Order":
						d_transaction="新订单";
						break;
					default:
						d_transaction = data.data.transaction;
					break;
				
				};
				var d_action = "";
				switch(data.data.action){
					case "Apply":
						d_action="申请";
						break;
					case "SMS":
						d_action="发送短信";
						break;
					case "Submit":
						d_action="提交";
						break;
					case "Confirm":
						d_action="确认";
						break;
					case "CallBack":
						d_action="回调通知";
						break;
					case "Query":
						d_action="查询";
						break;
					case "Revise":
						d_action="补单";
						break;
					case "Cancel":
						d_action="取消";
						break;
					default:
						d_action = "默认";
					break;
			    };
			    
			    var d_transCur = "";
				switch(data.data.transCur){
					case "CNY":
						d_transCur="人民币";
						break;
					default:
						d_transCur = data.data.transCur;
					break;
			    };
			    
			    var d_cardType = "";
				switch(data.data.cardType){
					case "DebitCard":
						d_cardType="借记卡";
						break;
					case "CreditCard":
						d_cardType="信用卡";
						break;
					case "HybridCard":
						d_cardType="混合卡";
						break;
					case "Company":
						d_cardType="企业账户";
						break;
					default:
						d_cardType = data.data.cardType;
					break;
			    };
	            $("#id").text(data.data.id);
	            $("#merchant").text(data.data.merchant);
	            $("#channel").text(data.data.channel);
	            $("#business").text(d_business);
	            $("#transaction").text(d_transaction);
	            $("#action").text(d_action);
	            $("#from").text(data.data.from);
	            $("#requestId").text(data.data.requestId);
	            $("#sequenceId").text(data.data.sequenceId);
	            $("#flowDirection").text(data.data.flowDirction);
	            $("#transCur").text(d_transCur);
	            $("#payerId").text(data.data.payerId);
	            $("#payerUserName").text(data.data.payerUsername);
	            $("#amount").text(data.data.amount.amount);
	            $("#bankMobile").text(data.data.bankMobile);
	            $("#bankName").text(data.data.bankName);
	            $("#bankCode").text(data.data.bankCode);
	            $("#cardNo").text(data.data.cardNo);
	            $("#cardType").text(d_cardType);
	            $("#payeeId").text(data.data.payeeId);
	            $("#payeeUserName").text(data.data.payeeUsername);
	            $("#requestTime").text(data.data.requestTime);
	            $("#sendTime").text(data.data.sendTime);
	            $("#useRedPacket").text(data.data.useRedPacket);
	            $("#transStatus").text(data.data.transStatus);
	            $("#responseCode").text(data.data.responseCode);
	            $("#responseMessage").text(data.data.responseMessage);
	            $("#orderStatus").text(d_orderStatus);
	         
	          }
	          else {
	            bootbox.alert(data.msg);
	          }
	        }
	      },
	      async: false
	    });
	});
});