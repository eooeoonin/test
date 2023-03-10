var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/release_record/releaseRecordList", srhData);
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
							    	d_requestTime = v.requestTime,
							    	d_requestId = v.requestId,
							    	d_sequenceId = v.sequenceId,
							    	d_loanId = v.loanId,
							    	d_userId = v.userId,
							    	d_amount = v.amount.amount,
							    	d_borrCustId = v.borrowUserId,
							    	d_responseCode = v.responseCode,
							    	d_responseMessage = v.responseMessage,
							    	d_responseTime = v.responseTime,
							    	d_actionStatus = v.actionStatus,
							    	d_transStatus = v.transStatus,
							    	d_modifyTime = v.modifyTime
									;
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_action+ '</td>';
									tableBodyHtml += '<td>' + d_requestTime+ '</td>';
									tableBodyHtml += '<td>' + d_requestId+ '</td>';
									tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
									tableBodyHtml += '<td>' + d_loanId+ '</td>';
									tableBodyHtml += '<td>' + d_amount+ '</td>';
									tableBodyHtml += '<td>' + d_borrCustId+ '</td>';
									tableBodyHtml += '<td>' + d_responseCode+ '</td>';
									tableBodyHtml += '<td>' + d_responseMessage+ '</td>';
									tableBodyHtml += '<td>' + d_responseTime+ '</td>';
									tableBodyHtml += '<td>' + d_actionStatus+ '</td>';
									tableBodyHtml += '<td>' + d_transStatus+ '</td>';
//									tableBodyHtml += '<td>' + d_modifyTime+ '</td>';
									if("PROCESSING"==d_transStatus)
							        {
							            tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\',\''+d_userId+'\')">??????</button></td>'; //??????
							        }
									else
							        {
							            tableBodyHtml += '<td>??????</td>'; //??????
							        }
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
					}else{
						var html = ""; 
						 html +='<tr class="no-records-found">';
						 html +='<td colspan="9" align="center">???????????????????????????</td>';
						 html += '</tr>';
			    		 $("#table").find("tbody").html(html);
			    		 $("#tcdPageCode").hide();
			    	}
				},error : function(){
		        	alert("????????????????????????");
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
		  var v_loanId = $("#loanId").val(),
			v_requestId = $("#requestId").val(),
			v_sequenceId = $("#sequenceId").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20",
			"loanId" : v_loanId,
			"requestId" : v_requestId,
			"sequenceId" : v_sequenceId
			};
		tableFun("/gateway/release_record/releaseRecordList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
		var v_loanId = $("#loanId").val(),
		v_requestId = $("#requestId").val(),
		v_sequenceId = $("#sequenceId").val();
		var pageData = {
		"pageNo" : "1",
		"pageSize" : "20",
		"loanId" : v_loanId,
		"requestId" : v_requestId,
		"sequenceId" : v_sequenceId
		};
		tableFun("/gateway/release_record/releaseRecordList",pageData);
		myPage();
});

/**
 * ??????
 */
function revise(requestId,d_userId)
{
    $.ajax({
            type : "POST",
            url : "/gateway/release_record/revise",
            data : {
            	"requestId" : requestId,
            	"d_userId" : d_userId
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