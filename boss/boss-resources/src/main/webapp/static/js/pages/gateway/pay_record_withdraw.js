var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/pay_record_withdraw/payRecordWithdrawList", srhData);
	myPage();
	var _table = $('#table');
	_table.bootstrapTable();
});

function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if(data.list !== ""){
						var _table = $('#table'), tableBodyHtml = '';
						_pages = data.pages;
						$.each(data.list,function(k, v) {
							    var d_business = v.business,
							    	d_action = v.action,
							    	d_platformUserId = v.platformUserId,
							    	d_userId = v.userId,
							    	d_requestTime = v.requestTime,
							    	d_requestId = v.requestId,
							    	d_sequenceId = v.sequenceId,
							    	d_preRequestId = v.preRequestId,
							    	d_preSequenceId = v.preSequenceId,
							    	d_payType = v.payType,
							    	d_flowDirction = v.flowDirction,
							    	d_payerId = v.payerId,
							    	d_payeeId = v.payeeId,
							    	d_amount = v.amount.amount,
							    	d_responseCode = v.responseCode,
							    	d_responseMessage = v.responseMessage,
							    	d_responseTime = v.responseTime,
							    	d_outOrderId = v.outOrderId,
							    	d_orderStatus = v.orderStatus,
							    	d_actionStatus = v.actionStatus,
							    	d_transStatus = v.transStatus,
							    	d_modifyTime = v.modifyTime
									;
							    
							    	tableBodyHtml += '<tr>';
//							    	tableBodyHtml += '<td>' + d_business+ '</td>';
							    	tableBodyHtml += '<td>' + d_action+ '</td>';
							    	tableBodyHtml += '<td>' + d_userId+ '</td>';
							    	tableBodyHtml += '<td>' + d_requestId+ '</td>';
							    	tableBodyHtml += '<td>' + d_requestTime+ '</td>';
							    	tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
							    	if ( null == d_preRequestId){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_preRequestId+ '</td>';
							    	}
							    	if ( null == d_preSequenceId){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_preSequenceId+ '</td>';
							    	}
							    	tableBodyHtml += '<td>' + d_amount+ '</td>';
							    	if ( null == d_responseCode || "" == d_responseCode){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseCode+ '</td>';
							    	}
									if ( null == d_responseMessage || "" == d_responseMessage){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseMessage+ '</td>';
							    	}
									if ( null == d_responseTime || "" == d_responseTime){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseTime+ '</td>';
							    	}
									if (d_orderStatus == -1 ) {
										tableBodyHtml += '<td>交易失败</td>';
									} else if (d_orderStatus== 0 ) {
										tableBodyHtml += '<td>未开始</td>';
									} else if (d_orderStatus == 1 ) {
										tableBodyHtml += '<td>交易成功</td>';
									} else if (d_orderStatus == 2 ) {
										tableBodyHtml += '<td>处理中</td>';
									} else if (d_orderStatus == 3 ) {
										tableBodyHtml += '<td>待确认</td>';
									} else if (d_orderStatus == 4 ) {
										tableBodyHtml += '<td>出款中</td>';
									} else if (d_orderStatus == -2 ) {
										tableBodyHtml += '<td>已撤销</td>';
									} else if (d_orderStatus == -3 ) {
										tableBodyHtml += '<td>已过期</td>';
									} else if (d_orderStatus == -4 ) {
										tableBodyHtml += '<td>订单不存在</td>';
									} else if (d_orderStatus == -5 ) {
										tableBodyHtml += '<td>重复订单号</td>';
									} else if (d_orderStatus == -6 ) {
										tableBodyHtml += '<td>订单未支付</td>';
									} else if (d_orderStatus == -7 ) {
										tableBodyHtml += '<td>已退款</td>';
									} else {
										tableBodyHtml += '<td>' + d_orderStatus+ '</td>';
									}
							    	tableBodyHtml += '<td>' + d_actionStatus+ '</td>';
							    	tableBodyHtml += '<td>' + d_transStatus+ '</td>';
							    	if(d_transStatus=="PROCESSING")
							    	{
							    		tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\',\''+d_userId+'\')">补单</button></td>'; //操作
							    	} else if ("CONFIRM" == d_action && 2 == d_orderStatus) {
						    			tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="intercept(\''+d_requestId+'\',\''+d_userId+'\',\''+d_preRequestId+'\')">拦截</button></td>'; //操作
						    		} else {
							    		tableBodyHtml += '<td>——</td>'; //操作
							    	}
							    	tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
					}else{
						var html = ""; 
						 html +='<tr class="no-records-found">';
						 html +='<td colspan="9" align="center">没有找到匹配的记录</td>';
						 html += '</tr>';
			    		 $("#table").find("tbody").html(html);
			    		 $("#tcdPageCode").hide();
			    	}
				},error : function(){
		        	alert("获取提现记录失败");
		        	$("#tcdPageCode").hide();
		        },
				async : false
			});

}


var myPage = function(){
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {  
		  var v_userId = $("#userId").val(),
			v_requestId = $("#requestId").val(),
			v_sequenceId = $("#sequenceId").val(),
		  	v_orderStatus = $("#orderStatus").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20",
			"userId" : v_userId,
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId,
			"orderStatus" : v_orderStatus
			};
		tableFun("/gateway/pay_record_withdraw/payRecordWithdrawList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
		var v_userId = $("#userId").val(),
		v_requestId = $("#requestId").val(),
		v_sequenceId = $("#sequenceId").val(),
	  	v_orderStatus = $("#orderStatus").val();
		var pageData = {
			"pageNo" : "1",
			"pageSize" : "20",
			"userId" : v_userId,
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId,
			"orderStatus" : v_orderStatus
		};
		tableFun("/gateway/pay_record_withdraw/payRecordWithdrawList",pageData);
		myPage();
});

/**
 * 补单
 */
function revise(requestId,userId)
{
    $.ajax({
            type : "POST",
            url : "/gateway/pay_record_withdraw/withdrawRevise",
            data : {
    			"userId" : userId,
            	"requestId" : requestId
            	},
            dataType : "json",
            success : function(data) {
                if (data != null && data != "") {
                    if (data.resCode == 0) {
                    	alert("code="+data.resCode+"|msg="+data.msg);
                    	document.location.reload();
                    }else{
                        alert(data.msg);
                        document.location.reload();
                    }
                }
            },error : function(data){
	        	alert(data.msg);
	        },
	        async : false
        });
}
/**
 * 拦截
 */
function intercept(requestId,userId,preRequestId)
{
	$.ajax({
		type : "POST",
		url : "/gateway/pay_record_withdraw/withdrawIntercept",
		data : {
			"userId" : userId,
			"requestId" : requestId,
			"preRequestId" : preRequestId
			
		},
		dataType : "json",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					alert(data.msg);
					document.location.reload();
				}else{
					alert(data.msg);
					document.location.reload();
				}
			}
		},error : function(data){
			alert(data.msg);
		},
		async : false
	});
}