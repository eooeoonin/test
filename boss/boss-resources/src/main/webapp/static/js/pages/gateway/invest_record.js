var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/invest_record/newInvestRecordList", srhData);
	myPage();
	var _table = $('#table');
	_table.bootstrapTable();
});

function tableFun(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if (data.list !== "") {
						var _table = $('#table'), tableBodyHtml = '';
						_pages = data.pages;
						$.each(data.list,
							function(k, v) {
								var 
								d_business = v.business, 
								d_action = v.action,
								d_investUserId = v.userId, 
								d_loanId = v.loanId, 
								d_amount = v.amount.amount, 
								d_redMoney = v.redMoney.amount, 
								d_requestId = v.requestId, 
								d_requestTime = v.requestTime, 
								d_sequenceId = v.sequenceId, 
								d_responseCode = v.responseCode, 
								d_responseMessage = v.responseMessage, 
								d_responseTime = v.responseTime, 
								d_actionStatus = v.actionStatus, 
								d_transStatus = v.transStatus;
								
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>'
										+ d_business + '</td>';
									tableBodyHtml += '<td>' + d_action
									+ '</td>';
									tableBodyHtml += '<td>'
										+ d_investUserId + '</td>';
									tableBodyHtml += '<td>' + d_loanId
									+ '</td>';
									tableBodyHtml += '<td>' + d_amount
									+ '</td>';
									tableBodyHtml += '<td>'
										+ d_redMoney + '</td>';
									tableBodyHtml += '<td>'
										+ d_requestId + '</td>';
									tableBodyHtml += '<td>'
										+ d_requestTime + '</td>';
									tableBodyHtml += '<td>'
										+ d_sequenceId + '</td>';
									if ( null == d_responseCode){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseCode+ '</td>';
							    	}
									if ( null == d_responseMessage){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseMessage+ '</td>';
							    	}
									if ( null == d_responseTime){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseTime+ '</td>';
							    	}
									tableBodyHtml += '<td>'
										+ d_actionStatus + '</td>';
									tableBodyHtml += '<td>'
										+ d_transStatus + '</td>';
									if(d_business == "INVEST" || d_business == "AUTO_INVEST"){
										if (d_transStatus == "PROCESSING") {
											tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\',\''+d_investUserId+'\')">补单</button></td>'; //补单操作
										} else if (d_transStatus == "SUCCESS"){
											tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="repeal(\''+d_requestId+'\')">撤销</button></td>'; //撤销操作
										} else {
											tableBodyHtml += '<td>——</td>'; // 操作
										}
									}else{
										if (d_transStatus == "PROCESSING") {
											tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="killrevise(\''+d_requestId+'\',\''+d_userId+'\')">补单</button></td>'; //操作
										} else if (d_transStatus == "FAIL") {
											tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="killretry(\''+d_requestId+'\',\''+d_userId+'\',\''+d_preRequestId+'\',\''+d_amount+'\')">重试</button></td>'; //操作
										} else {
											tableBodyHtml += '<td>——</td>'; // 操作
										}
									}
									tableBodyHtml += '</tr>';
							});
						_table.find("tbody").html(tableBodyHtml);
					} else {
						var html = ""; 
						 html +='<tr class="no-records-found">';
						 html +='<td colspan="9" align="center">没有找到匹配的记录</td>';
						 html += '</tr>';
			    		 $("#table").find("tbody").html(html);
			    		 $("#tcdPageCode").hide();
			    	}
				},error : function(){
		        	alert("获取投标记录失败");
		        	$("#tcdPageCode").hide();
		        },
				async : false
			});

}

var myPage = function() {
	var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(p) {
				var v_userId = $("#userId").val(),
				v_loanId = $("#loanId").val(), 
				v_requestId = $("#requestId").val(), 
				v_sequenceId = $("#sequenceId").val();
				v_business = $("#business").val();
				var pageData = {
					"pageNo" : p,
					"pageSize" : "20",
					"userId" : v_userId,
					"loanId" : v_loanId,
					"requestId" : v_requestId,
					"sequenceId" : v_sequenceId,
					"business" : v_business
				};
				tableFun("/gateway/invest_record/newInvestRecordList",
						pageData);
			}
			});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function() {
		var v_userId = $("#userId").val(),
		v_loanId = $("#loanId").val(), 
		v_requestId = $("#requestId").val(), 
		v_sequenceId = $("#sequenceId").val();
		v_business = $("#business").val();
		var pageData = {
			"pageNo" : "1",
			"pageSize" : "20",
			"userId" : v_userId,
			"loanId" : v_loanId,
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId,
			"business" : v_business
		};
		tableFun("/gateway/invest_record/newInvestRecordList", pageData);
		myPage();
	});

/**
 * 补单
 * @param requestId
 * @param investUserId
 */
function revise(requestId,investUserId)
{
	$.ajax({
        type : "POST",
        url : "/gateway/invest_record/revise",
        data : {
			"userId" : investUserId,
        	"requestId" : requestId
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
/**
 * 撤销出借
 * @param requestId
 */
function repeal(requestId){
	bootbox.confirm("确定要撤销吗?", function(result){
	if(result){
		$.ajax({
			type : "POST",
			url : "/gateway/invest_record/repeal",
			data : {
				"investId" : requestId
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
	});
}

/**
 * 补单
 * @param requestId
 * @param userId
 */
function killrevise(requestId,userId)
{
	$.ajax({
        type : "POST",
        url : "/gateway/invest_record/killrevise",
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
 * 重试流标
 * @param requestId
 * @param userId
 * @param loanId
 * @param amount
 */
function killretry(requestId,userId,preRequestId,amount)
{
	$.ajax({
		type : "POST",
		url : "/gateway/invest_record/killretry",
		data : {
			"userId" : userId,
			"requestId" : requestId,
			"preRequestId" : preRequestId,
			"Amount" : amount
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