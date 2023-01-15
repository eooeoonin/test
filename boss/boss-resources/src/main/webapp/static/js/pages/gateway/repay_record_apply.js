var _pages;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/repay_record/repayRecordApplyList", srhData);
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
							    var d_borrowerUserId = v.borrowerUserId,//借款人用户编号
							    	d_repayId = v.repayId,//主单单号
							    	d_loanId = v.loanId,//项目编号
							    	d_requestId = v.requestId,//交易请求单号
							    	d_transAmount = v.transAmount.amount,//交易金额
							    	d_business = v.business,//业务类型
							    	d_action = v.action,//事物类型
							    	d_tradeTime = v.tradeTime,//交易时间
							    	d_sequenceId = v.sequenceId,//存管请求单号
							    	d_depoeitSendTime = v.depoeitSendTime,//发送存管时间
							    	d_depoeitSendData = v.depoeitSendData,//上送存管报文
							    	d_depoeitResponseCode = v.depoeitResponseCode,//存管响应编码
							    	d_depoeitResponseMsg = v.depoeitResponseMsg,//存管响应信息
							    	d_depoeitResponseData = v.depoeitResponseData,//存管响应报文
							    	d_depoeitResponseTime = v.depoeitResponseTime,//存管响应时间
							    	d_transStatus = v.transStatus,//存管状态
							    	d_actionStatus = v.actionStatus,//操作状态
							    	d_modifyTime = v.modifyTime;//修改时间
									
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_business+ '</td>';
									tableBodyHtml += '<td>' + d_action+ '</td>';
									tableBodyHtml += '<td>' + d_borrowerUserId+ '</td>';
									tableBodyHtml += '<td>' + d_requestId+ '</td>';
									tableBodyHtml += '<td>' + d_transAmount+ '</td>';
									tableBodyHtml += '<td>' + d_tradeTime+ '</td>';
									tableBodyHtml += '<td>' + d_loanId+ '</td>';
									if ( null == d_repayId){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_repayId+ '</td>';
							    	}
									tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
									if ( null == d_depoeitResponseCode){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_depoeitResponseCode+ '</td>';
							    	}
									if ( null == d_depoeitResponseMsg){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_depoeitResponseMsg+ '</td>';
							    	}
									if ( null == d_depoeitResponseTime){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_depoeitResponseTime+ '</td>';
							    	}
									tableBodyHtml += '<td>' + d_actionStatus+ '</td>';
									tableBodyHtml += '<td>' + d_transStatus+ '</td>';
									if("PROCESSING" == d_transStatus)
							        {
							            tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\')">补单</button></td>'; //补单操作
							        }
									else if ("SUCCESS" == d_transStatus){
										tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="repeal(\''+d_requestId+'\',\''+d_loanId+'\',\''+d_transAmount+'\')">撤销</button></td>'; //撤销操作
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
		        	alert("获取记录失败");
		        	$("#tcdPageCode").hide();
		        },
				async : false
			});

}


var myPage = function(){
	var $tcdPage = $(".tcdPageCode");
	var v_loanId = $("#loanId").val(),
	v_borrowerUserId = $("#borrowerUserId").val(),
	v_requestId = $("#requestId").val();
	
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {  
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20",
			"loanId" : v_loanId,
			"borrowerUserId" : v_borrowerUserId,
			"requestId" : v_requestId
			};
		tableFun("/gateway/repay_record/repayRecordApplyList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
		var v_loanId = $("#loanId").val(),
		v_borrowerUserId = $("#borrowerUserId").val(),
		v_requestId = $("#requestId").val();
		var pageData = {
		"pageNo" : "1",
		"pageSize" : "20",
		"loanId" : v_loanId,
		"borrowerUserId" : v_borrowerUserId,
		"requestId" : v_requestId
		};
		tableFun("/gateway/repay_record/repayRecordApplyList",pageData);
		myPage();
});

/**
 * 补单
 */
function revise(requestId)
{
    $.ajax({
            type : "POST",
            url : "/gateway/repay_record/repayRecordApplyRevise",
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
 * 撤销
 * @param requestId
 */

function repeal(requestId,loanId,transAmount){
	bootbox.confirm("确定要撤销吗?", function(result){
	if(result){
		$.ajax({
			type : "POST",
			url : "/gateway/repay_record/repayRecordApplyRepeal",
			data : {
				"preRequestId" : requestId,
				"loanId" : loanId,
				"principalAmount" : transAmount
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