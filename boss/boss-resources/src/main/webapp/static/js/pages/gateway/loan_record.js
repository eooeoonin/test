var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/loan_record/loanRecordList", srhData);
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
							d_userId = v.userId, 
							d_loanId = v.loanId, 
							d_loanName = v.loanName, 
							d_loanAmount = v.loanAmount.amount, 
							d_loanPeriod = v.loanPeriod, 
							d_loanStatus = v.loanStatus, 
							d_loanRate = v.loanRate, 
							d_requestId = v.requestId, 
							d_requestTime = v.requestTime, 
							d_sequenceId = v.sequenceId, 
							d_transStatus = v.transStatus,
							d_entrustUserId = v.entrustUserId,
							d_entrustStatus = v.entrustStatus,
							d_responseTime = v.responseTime; 
							
								
							tableBodyHtml += '<tr>';
							tableBodyHtml += '<td>' + d_business + '</td>';
							if ( null == d_userId){
					    		tableBodyHtml += '<td>——</td>';
					    	} else {
					    		tableBodyHtml += '<td>' + d_userId+ '</td>';
					    	}
							tableBodyHtml += '<td>' + d_requestId + '</td>';
							tableBodyHtml += '<td>' + d_requestTime + '</td>';
							tableBodyHtml += '<td>' + d_sequenceId + '</td>';
							tableBodyHtml += '<td>' + d_loanId + '</td>';
							if ( null == d_loanName){
					    		tableBodyHtml += '<td>——</td>';
					    	} else {
					    		tableBodyHtml += '<td>' + d_loanName+ '</td>';
					    	}
							if ( null == d_loanStatus){
					    		tableBodyHtml += '<td>——</td>';
					    	} else {
					    		tableBodyHtml += '<td>' + d_loanStatus+ '</td>';
					    	}
							tableBodyHtml += '<td>' + d_transStatus + '</td>';
							if ( null == d_entrustUserId){
					    		tableBodyHtml += '<td>——</td>';
					    	} else {
							tableBodyHtml += '<td>' + d_entrustUserId + '</td>';
					    	}
							if ( null == d_entrustStatus){
					    		tableBodyHtml += '<td>——</td>';
					    	} else {
							tableBodyHtml += '<td>' + d_entrustStatus + '</td>';
					    	}
							if ( null == d_responseTime){
					    		tableBodyHtml += '<td>——</td>';
					    	} else {
					    		tableBodyHtml += '<td>' + d_responseTime+ '</td>';
					    	}
							if (d_transStatus == "PROCESSING"){
								tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\')">补单</button></td>'; //操作
							} else if(d_transStatus == "SUCCESS"){
								if(d_entrustStatus != "SUCCESS" && null == d_entrustUserId){
									tableBodyHtml += '<td>——</td>';
								}else if (d_entrustStatus != "SUCCESS" && null != d_entrustUserId){
									tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="dispose(\''+d_requestId+'\')">委托授权</button></td>';
								}else{
									tableBodyHtml += '<td>——</td>'; // 操作
								}
							} else {
								tableBodyHtml += '<td>——</td>'; // 操作
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
		        	alert("获取标的记录失败");
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
				var pageData = {
					"pageNo" : p,
					"pageSize" : "20",
					"userId" : v_userId,
					"loanId" : v_loanId,
					"requestId" : v_requestId,
					"sequenceId" : v_sequenceId
				};
				tableFun("/gateway/loan_record/loanRecordList",
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
		var pageData = {
			"pageNo" : "1",
			"pageSize" : "20",
			"userId" : v_userId,
			"loanId" : v_loanId,
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId
		};
		tableFun("/gateway/loan_record/loanRecordList", pageData);
		myPage();
	});

/**
 * 补单
 * @param requestId
 * @param investUserId
 */
function revise(requestId)
{
	$.ajax({
        type : "POST",
        url : "/gateway/loan_record/loanRecordRevise",
        data : {
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
 * 委托处理
 * @param requestId
 */
function dispose(requestId)
{
	$.ajax({
        type : "POST",
        url : "/gateway/loan_record/authEntrustPay",
        data : {
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