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


var _pages;var _cleardate;var _inBusType;var _inStatus;var _inCheckCount;
$(function(){
	
		  laydate({elem: "#clearDate", format: "YYYY/MM/DD"});
		  
		  _cleardate = Request.clearDate;
		  _type_select = Request.type_select;
		  _inStatus = Request.inStatus;
		  
		  $("#clearDate").val(_cleardate);
		  $("#type_select").val(_type_select);
		  $("#inStatus").val(_inStatus);
		  
		  if(_inStatus == '1'){
			  _inStatus2 = '0,2,3';
		  }if(_inStatus == '2'){
			  _inStatus2 = '4,5';
		  }
		  
	var srhData = {
						  "clearDate":_cleardate,
						  "inStatus":_inStatus2
						 };
		  tableFun("../difference_account/difference/tradeRecordStatis", srhData);	
		  
		  if(_inStatus == '1'){
			  _inCheckCount = '0,2';
		  }if(_inStatus == '2'){
			  _inCheckCount = '4,5';
		  } 
		  var srhData = {
				  "clearDate":_cleardate,
				  "inCheckCount":_inCheckCount
				 };
  tableFun2("../difference_account/difference/accountRecordStatis", srhData);	
		  
})

var _recharge = 0;var _withdraw1 = 0;var _withdraw2 = 0;var _withdraw3 = 0;var _invest1 = 0;var _invest2 = 0;var _killloan = 0;var _release = 0;var _repay = 0;
var _inviteprofit = 0;var _adjustment1 = 0;var _adjustment2 = 0;
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data = data.data;
					if(_data.length != 0){
						$.each(_data, function (k, v) {
							if(v.busType == 'RECHARGE'){
								_recharge = v.count
							}
							if(v.busType == 'WITHDRAW_SUCCESS'){
								_withdraw1 = v.count;
							}
							if(v.busType == 'WITHDRAW_FAIL'){
								_withdraw2 = v.count;
							}
							if(v.busType == 'WITHDRAW_APPLY'){
								_withdraw3 = v.count;
							}
							if(v.busType == 'INVEST_SUCCESS'){
								_invest1 = v.count;
							}
							if(v.busType == 'INVEST_FAIL'){
								_invest2 = v.count;
							}
							if(v.busType == 'KILL_LOAN'){
								_killloan = v.count;
							}
							if(v.busType == 'RELEASE'){
								_release = v.count;
							}
							if(v.busType == 'REPAY'){
								_repay = v.count;
							}
							if(v.busType == 'INVITE_PROFIT'){
								_inviteprofit = v.count;
							}
							if(v.busType == 'ADJUSTMENT_ADD'){
								_adjustment1 = v.count;
							}
							if(v.busType == 'ADJUSTMENT_SUB'){
								_adjustment2 = v.count;
							}
						})
					}
					
					
					
					$("#recharge").text(_recharge);
					$("#withdraw").text(_withdraw1 + _withdraw2 + _withdraw3);
					$("#invest").text(_invest1 + _invest2);
					$("#killloan").text(_killloan);
					$("#release").text(_release);
					$("#repay").text(_repay);
					$("#inviteprofit").text(_inviteprofit);
					$("#adjustment").text(_adjustment1 + _adjustment2);
				}else{
					bootbox.alert("交易统计异常");
				
				}
			}else{
				bootbox.alert(data.msg);
			}
		},
		async : false
	});
}



var _accountingWater = 0;var _accountingWater2 = 0;var _accountingWater3 = 0;var _accountingWater4 = 0;var _accountingWater5 = 0;var _accountingWater6 = 0;var _accountingWater7 = 0;var _accountingWater8 = 0;var _accountingFreeze = 0;
var _accountingFreeze2 = 0;var _redWater = 0;var _redWater2 = 0;var _redFreeze = 0;var _redFreeze2 = 0;
function tableFun2(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			
				
					var _data = data;
					if(_data.length != 0){
						$.each(_data, function (k, v) {
							if(v.busType == 'CASH_OUT'){
								_accountingWater = v.count;
							}
							if(v.busType == 'CASH_IN'){
								_accountingWater2 = v.count;
							}
							if(v.busType == 'PRINCIPAL_CASH_OUT'){
								_accountingWater3 = v.count;
							}
							if(v.busType == 'PRINCIPAL_CASH_IN'){
								_accountingWater4 = v.count;
							}
							if(v.busType == 'INTEREST_CASH_OUT'){
								_accountingWater5 = v.count;
							}
							if(v.busType == 'INTEREST_CASH_IN'){
								_accountingWater6 = v.count;
							}
							if(v.busType == 'PENALTY_CASH_OUT'){
								_accountingWater7 = v.count;
							}
							if(v.busType == 'PENALTY_CASH_IN'){
								_accountingWater8 = v.count;
							}
							if(v.busType == 'FREEZE_CASH'){
								_accountingFreeze= v.count;
							}
							if(v.busType == 'UNFREEZE_CASH'){
								_accountingFreeze2 = v.count;
							}
							if(v.busType == 'REDMONEY_OUT'){
								_redWater = v.count;
							}
							if(v.busType == 'REDMONEY_IN'){
								_redWater2 = v.count;
							}
							
							if(v.busType == 'FREEZE_REDMONEY'){
								_redFreeze = v.count;
							}
							if(v.busType == 'UNFREEZE_REDMONEY'){
								_redFreeze2 = v.count;
							
							}
						})
					}
					
					
					
					$("#accountingWater").text(_accountingWater+_accountingWater2+_accountingWater3+_accountingWater4+_accountingWater5+_accountingWater6+_accountingWater7+_accountingWater8);
					$("#accountingFreeze").text(_accountingFreeze +_accountingFreeze2);
					$("#redWater").text(_redWater + _redWater2);
					$("#redFreeze").text(_redFreeze+_redFreeze2);
			
		},
		async : false
	});
}


$("#inStatus").change(function(){
	search();
});

function search(){
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
		var _type_select = document.getElementById("type_select").value;
		var _clearDate = document.getElementById("clearDate").value;
		var _inStatus = document.getElementById("inStatus").value;
		if(_type_select == 0){
			window.location.href="difference_summary.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
		}if(_type_select == 1){
			if(_inStatus == 1){
				window.location.href="difference_recharge_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}if(_inStatus == 2){
				window.location.href="difference_recharge_handle_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}
		}if(_type_select == 2){
			if(_inStatus == 1){//未处理
				window.location.href="difference_accountingwater_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}if(_inStatus == 2){//已处理
				window.location.href="difference_accountingwater_handle_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}
			
		}
	};