var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/trans_record/transRecordList", srhData);
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
							    	d_requestId = v.requestId,
							    	d_requestTime = v.requestTime,
							    	d_sequenceId = v.sequenceId,
							    	d_responseCode = v.responseCode,
							    	d_responseMessage = v.responseMessage,
							    	d_responseTime = v.responseTime,
							    	d_actionStatus = v.actionStatus,
							    	d_transStatus = v.transStatus,
							    	d_modifyTime = v.modifyTime
									;
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_business+ '</td>';
									tableBodyHtml += '<td>' + d_action+ '</td>';
//									tableBodyHtml += '<td>' + d_platformUserId+ '</td>';
									tableBodyHtml += '<td>' + d_userId+ '</td>';
									tableBodyHtml += '<td>' + d_requestId+ '</td>';
									tableBodyHtml += '<td>' + d_requestTime+ '</td>';
									tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
//									tableBodyHtml += '<td>' + d_responseCode+ '</td>';
//									tableBodyHtml += '<td>' + d_responseMessage+ '</td>';
//									tableBodyHtml += '<td>' + d_responseTime+ '</td>';
									tableBodyHtml += '<td>' + d_actionStatus+ '</td>';
									tableBodyHtml += '<td>' + d_transStatus+ '</td>';
									tableBodyHtml += '<td>' + d_modifyTime+ '</td>';
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
		        	alert("获取通用记录失败");
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
			v_sequenceId = $("#sequenceId").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20",
			"userId" : v_userId,
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/trans_record/transRecordList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	var v_userId = $("#userId").val(),
	v_requestId = $("#requestId").val(),
	v_sequenceId = $("#sequenceId").val();
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "20",
			"userId" : v_userId,
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/trans_record/transRecordList",pageData);
		myPage();
});

