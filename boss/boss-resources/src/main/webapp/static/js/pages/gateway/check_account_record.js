var _pages;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/check_account_record/checkAccountRecordList", srhData);
	myPage();
	var _table = $('#table');
	_table.bootstrapTable();
	
    laydate({elem: "#date1", format: "YYYYMMDD", istoday: false});


    $("#checkButton").click(function () {
        $("#checkForm").validationEngine('attach', {
            inlineValidation: false,
            maxErrorsPerField: 1,
            autoHidePrompt: true,
            autoHideDelay: 2000
        });

        if (!$("#checkForm").validationEngine('validate')) {
            $("#checkForm").validationEngine('detach');
            return false;
        }
        
        $.ajax({
            type: "POST",
            url: "/gateway/check_account_record/applyCheckAccount",
            data: $("#checkForm").serialize(),
            success: function (data) {
                if (data != null && data != "") {
                        bootbox.alert(data);
                }
            },error : function(){
            	bootbox.alert("对账失败");
	        },
            async: false
        });
    });
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
							    var d_id = v.id,
							    	d_business = v.business,
							    	d_action = v.action,
							    	d_sequenceId = v.sequenceId,
							    	d_batch = v.batch,
							    	d_checkDate = v.checkDate,
							    	d_accountType = v.accountType,
							    	d_checkFile = v.checkFile,
							    	d_responseCode = v.responseCode,
							    	d_responseMessage = v.responseMessage,
							    	d_responseTime = v.responseTime,
							    	d_transStatus = v.transStatus
									;
							    
							    	tableBodyHtml += '<tr>';
							    	if ( null == d_business || "" == d_business){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_business+ '</td>';
							    	}
							    	tableBodyHtml += '<td>' + d_action+ '</td>';
							    	if ( null == d_sequenceId || "" == d_sequenceId){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
							    	}
							    	if ( null == d_batch || "" == d_batch){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_batch+ '</td>';
							    	}
							    	tableBodyHtml += '<td>' + d_checkDate+ '</td>';
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
									if ( null == d_responseMessage || "" == d_responseMessage){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseMessage+ '</td>';
							    	}
									if ( null == d_responseCode || "" == d_responseCode){
										tableBodyHtml += '<td>——</td>';
									} else {
										tableBodyHtml += '<td>' + d_responseCode+ '</td>';
									}
									if ( null == d_responseTime || "" == d_responseTime){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseTime+ '</td>';
							    	}
							    	tableBodyHtml += '<td>' + d_transStatus+ '</td>';
							    	if(d_business=="CHECK_ACCOUNT" && d_action=="APPLY")
							    	{
							    		if(d_transStatus=="FAIL" ||  d_transStatus=="AUDIT"){
							    		tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="confirm(\''+d_batch+'\')">对账确认</button></td>'; //操作
										}else{
											tableBodyHtml += '<td>——</td>'; //操作
										}
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
						 html +='<td colspan="9" align="center">没有找到匹配的记录</td>';
						 html += '</tr>';
			    		 $("#table").find("tbody").html(html);
			    		 $("#tcdPageCode").hide();
			    	}
				},error : function(){
		        	alert("获取对账记录失败");
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
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20",
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/check_account_record/checkAccountRecordList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	v_sequenceId = $("#sequenceId").val();
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "20",
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/check_account_record/checkAccountRecordList",pageData);
		myPage();
});


/**
 * 对账确认
 */
function confirm(batch) {
		$.ajax({
			type : "POST",
			url : "/gateway/check_account_record/confirmCheckAccount",
			data : {
				"batch" : batch
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if(data != null && data != ""){
					bootbox.alert(data);
				}
			},
			error : function() {
				bootbox.alert("对账确认失败");
//				window.location.reload();
			}
		});
};