var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/send_redmoney_record/sendRedmoneyRecordList", srhData);
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
							    	d_mainRequestId = v.mainRequestId,
							    	d_userId = v.userId,
							    	d_requestTime = v.requestTime,
							    	d_sequenceId = v.sequenceId,
							    	d_platformRequestId = v.platformRequestId,
							    	d_amount = v.amount.amount,
							    	d_actionStatus = v.actionStatus,
							    	d_transStatus = v.transStatus,
							    	d_responseTime = v.responseTime
									;
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_business+ '</td>';
									tableBodyHtml += '<td>' + d_action+ '</td>';
									tableBodyHtml += '<td>' + d_mainRequestId+ '</td>';
									tableBodyHtml += '<td>' + d_requestTime+ '</td>';
									tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
									tableBodyHtml += '<td>' + d_platformRequestId+ '</td>';
									tableBodyHtml += '<td>' + d_amount+ '</td>';
									tableBodyHtml += '<td>' + d_actionStatus+ '</td>';
									tableBodyHtml += '<td>' + d_transStatus+ '</td>';
									tableBodyHtml += '<td>' + d_responseTime+ '</td>';
									if (d_transStatus == "PROCESSING") {
										tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_mainRequestId+'\')">补单</button></td>'; //操作
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
		        	alert("获取营销款记录失败");
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
		  var v_mainRequestId = $("#mainRequestId").val(),
			v_sequenceId = $("#sequenceId").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20",
			"mainRequestId" : v_mainRequestId,
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/send_redmoney_record/sendRedmoneyRecordList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	var v_mainRequestId = $("#mainRequestId").val(),
	v_sequenceId = $("#sequenceId").val();
	var pageData = {
		"pageNo" : "1",
		"pageSize" : "20",
		"mainRequestId" : v_mainRequestId,
		"sequenceId" : v_sequenceId
		};
		tableFun("/gateway/send_redmoney_record/sendRedmoneyRecordList",pageData);
		myPage();
});

/**
 * 补单
 */
function revise(requestId)
{
    $.ajax({
            type : "POST",
            url : "/gateway/send_redmoney_record/revise",
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
