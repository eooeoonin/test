var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/kill_invest_record/killInvestRecordList", srhData);
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
							    var d_action = v.action,
							        d_business = v.business,
							    	d_requestId = v.requestId,
							    	d_userId = v.userId,
							    	d_loanId = v.loanId,
							    	d_preRequestId = v.preRequestId,
							    	d_preSequenceId = v.preSequenceId,
							    	d_requestTime = v.requestTime,
							    	d_amount = v.amount.amount,
							    	d_redMoney = v.redMoney.amount,
							    	d_sequenceId = v.sequenceId,
							    	d_responseCode = v.responseCode,
							    	d_responseMessage = v.responseMessage,
							    	d_responseTime = v.responseTime,
							    	d_actionStatus = v.actionStatus,
							    	d_transStatus = v.transStatus,
							    	d_modifyTime = v.modifyTime
									;
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_action+ '</td>';
//									tableBodyHtml += '<td>' + d_business+ '</td>';
//									tableBodyHtml += '<td>' + d_loanId+ '</td>';
									tableBodyHtml += '<td>' + d_requestId+ '</td>';
//									tableBodyHtml += '<td>' + d_requestTime+ '</td>';
									tableBodyHtml += '<td>' + d_amount+ '</td>';
									tableBodyHtml += '<td>' + d_redMoney+ '</td>';
									tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
									tableBodyHtml += '<td>' + d_preRequestId+ '</td>';
									tableBodyHtml += '<td>' + d_preSequenceId+ '</td>';
									tableBodyHtml += '<td>' + d_responseCode+ '</td>';
									tableBodyHtml += '<td>' + d_responseMessage+ '</td>';
									tableBodyHtml += '<td>' + d_responseTime+ '</td>';
									tableBodyHtml += '<td>' + d_actionStatus+ '</td>';
									tableBodyHtml += '<td>' + d_transStatus+ '</td>';
//									tableBodyHtml += '<td>重试|补单</td>';
									if (d_transStatus == "PROCESSING") {
										tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\',\''+d_userId+'\')">补单</button></td>'; //操作
									} else if (d_transStatus == "FAIL") {
										tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="retry(\''+d_requestId+'\',\''+d_userId+'\',\''+d_preRequestId+'\',\''+d_amount+'\')">重试</button></td>'; //操作
									} else {
										tableBodyHtml += '<td>——</td>'; // 操作
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
		        	alert("获取流标记录失败");
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
		  var v_requestId = $("#requestId").val(),
			v_sequenceId = $("#sequenceId").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20",
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/kill_invest_record/killInvestRecordList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	var v_requestId = $("#requestId").val(),
	v_sequenceId = $("#sequenceId").val();
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "20",
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/kill_invest_record/killInvestRecordList",pageData);
		myPage();
});

/**
 * 补单
 * @param requestId
 * @param userId
 */
function revise(requestId,userId)
{
	$.ajax({
        type : "POST",
        url : "/gateway/kill_invest_record/revise",
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
function retry(requestId,userId,preRequestId,amount)
{
	$.ajax({
		type : "POST",
		url : "/gateway/kill_invest_record/retry",
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

