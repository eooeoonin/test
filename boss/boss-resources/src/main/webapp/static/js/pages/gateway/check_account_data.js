var _pages;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/check_account_data/checkAccountDataList", srhData);
	myPage();
	var _table = $('#table');
	_table.bootstrapTable();
	
	_modalFm =  $('#modal_form');
	_modalFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
});

function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if("" != data.list){
						var _table = $('#table'), tableBodyHtml = '';
						_pages = data.pages;
						$.each(data.list,function(k, v) {
							    var d_id = v.id,//id
							    	d_business = v.business,
							    	d_accountType = v.accountType,//账务类型
							    	d_checkDate = v.checkDate,//对账日期
							    	d_batch = v.batch,//批次
							    	d_amount = v.amount.amount,//金额
							    	d_sequenceId = v.sequenceId,//付款方id
							    	d_payerId = v.payerId,//付款方id
							    	d_payeeId = v.payeeId,//收款方id
							    	d_transTime = v.transTime,//业务时间
							    	d_orderStatus = v.orderStatus,//订单状态
							    	d_outOrderId = v.outOrderId,//外部流水号
							    	d_checkStatus = v.checkStatus,//轧账状态
							    	d_clearStatus = v.clearStatus,//平账状态
							    	d_message = v.message, //消息说明
							    	d_transStatus = v.transStatus //业务状态
									;
							    
							    	tableBodyHtml += '<tr>';
							    	tableBodyHtml += '<td>' + d_business+ '</td>';
							    	if("RECHARGE"==d_accountType){
							    		tableBodyHtml += '<td>' +"充值"+ '</td>';
							    	}else if("WITHDRAW"==d_accountType){
							    		tableBodyHtml += '<td>' +"提现"+ '</td>';
							    	}else if("BACKROLL_RECHARGE"==d_accountType){
							    		tableBodyHtml += '<td>' +"资金回充"+ '</td>';
							    	}else if("TRANSACTION"==d_accountType){
							    		tableBodyHtml += '<td>' +"交易"+ '</td>';
							    	}else if("COMMISSION"==d_accountType){
							    		tableBodyHtml += '<td>' +"佣金"+ '</td>';
							    	}else if("ALLBALANCE"==d_accountType){
							    		tableBodyHtml += '<td>' +"日终余额"+ '</td>';
							    	}else if("USER"==d_accountType){
							    		tableBodyHtml += '<td>' +"会员"+ '</td>';
							    	}else{
							    		tableBodyHtml += '<td>——</td>';
							    	}
							    	tableBodyHtml += '<td>' + d_checkDate+ '</td>';
							    	tableBodyHtml += '<td>' + d_batch+ '</td>';
							    	tableBodyHtml += '<td>' + d_amount+ '</td>';
							    	if(""!=d_sequenceId && null!=d_sequenceId){
							    		tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
							    	}else{
							    		tableBodyHtml += '<td>——</td>';
							    	}
							    	if(""!=d_payerId && null!=d_payerId){
							    		tableBodyHtml += '<td>' + d_payerId+ '</td>';
							    	}else{
							    		tableBodyHtml += '<td>——</td>';
							    	}
							    	if(""!=d_payeeId && null!=d_payeeId){
							    		tableBodyHtml += '<td>' + d_payeeId+ '</td>';
							    	}else{
							    		tableBodyHtml += '<td>——</td>';
							    	}
							    	if(""!=d_transTime && null!=d_transTime){
							    		tableBodyHtml += '<td>' + d_transTime+ '</td>';
							    	}else{
							    		tableBodyHtml += '<td>——</td>';
							    	}
							    	if(""!=d_outOrderId && null!=d_outOrderId){
							    		tableBodyHtml += '<td>' + d_outOrderId+ '</td>';
							    	}else{
							    		tableBodyHtml += '<td>——</td>';
							    	}
							    	tableBodyHtml += '<td>' + d_orderStatus+ '</td>';
							    	tableBodyHtml += '<td>' + d_checkStatus+ '</td>';
							    	tableBodyHtml += '<td>' + d_message+ '</td>';
							    	tableBodyHtml += '<td>' + d_clearStatus+ '</td>';
							    	tableBodyHtml += '<td>' + d_transStatus+ '</td>';
							    	if(d_transStatus=="FAIL")
							    	{
							    		tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="popup(\''+d_id+'\')">平账</button></td>'; //操作
							    	}
							    	else
							    	{
							    		tableBodyHtml += '<td>——</td>'; //操作
							    	}
							    	tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
					}else{
						var html = ""; 
						 html +='<tr class="no-records-found">';
						 html +='<td colspan="15" align="center">没有找到匹配的记录</td>';
						 html += '</tr>';
			    		 $("#table").find("tbody").html(html);
			    		 $("#tcdPageCode").hide();
			    	}
				},error : function(){
		        	alert("获取对账数据失败");
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
		  v_sequenceId = $("#sequenceId").val();
			v_batch = $("#batch").val();
			v_accountType = $("#accountType").val();
			v_transStatus = $("#transStatus").val();
			var pageData = {
					"pageNo" : p,
					"pageSize" : "20",
					"sequenceId" : v_sequenceId,
					"batch" : v_batch ,
					"accountType" : v_accountType,
					"transStatus" : v_transStatus
					};
		tableFun("/gateway/check_account_data/checkAccountDataList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	v_sequenceId = $("#sequenceId").val();
	v_batch = $("#batch").val();
	v_accountType = $("#accountType").val();
	v_transStatus = $("#transStatus").val();
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "20",
			"sequenceId" : v_sequenceId,
			"batch" : v_batch ,
			"accountType" : v_accountType,
			"transStatus" : v_transStatus
			};
		tableFun("/gateway/check_account_data/checkAccountDataList",pageData);
		myPage();
});

/**
 * 弹窗
 */
popup = function(id) {
	$("#id").val(id);
	$('#myModal').modal('show');
}
/**
 * 平账
 */
_modalSave = $("#modal_save");
  _modalSave.click(function() {
		if (!_modalFm.validationEngine('validate')) {
		    return false;
		}else{
			var id = $("#id").val(), 
			remark = $('#remark').val();
			var tData = {
				"id" : id,
				"remark" : remark
			};
			$.ajax({
				type : "POST",
				url : "/gateway/check_account_data/clearAccount",
				data : tData,
				dataType : "json",
				async : false,
				success : function(data) {
					if(data.resCode == 0){
						alert(data.msg);
						$('#myModal').modal('hide');
						window.location.reload();
					}else{
						alert(data.msg);
						$('#myModal').modal('hide');
						window.location.reload();
					}
				},
				error : function() {
					alert("平账失败");
					$('#myModal').modal('hide');
					window.location.reload();
				}
			});
		}		
	});