var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/auth_record/authRecordList", srhData);
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
							    	d_userId = v.userId,
							    	d_platformUserId = v.platformUserId,
							    	d_requestId = v.requestId,
							    	d_requestTime = v.requestTime,
							    	d_sequenceId = v.sequenceId,
							    	d_responseCode = v.responseCode,
							    	d_responseMessage = v.responseMessage,
							    	d_responseTime = v.responseTime,
							    	d_actionStatus = v.actionStatus,
							    	d_transStatus = v.transStatus,
							    	d_userType = v.userType,
							    	d_authStatus = v.authStatus,
							    	d_authTime = v.authTime;
							    
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_business+ '</td>';
									tableBodyHtml += '<td>' + d_action+ '</td>';
									tableBodyHtml += '<td>' + d_userId+ '</td>';
									tableBodyHtml += '<td>' + d_requestId+ '</td>';
									tableBodyHtml += '<td>' + d_requestTime+ '</td>';
									tableBodyHtml += '<td>' + d_sequenceId+ '</td>';
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
									tableBodyHtml += '<td>' + d_actionStatus+ '</td>';
									tableBodyHtml += '<td>' + d_transStatus+ '</td>';
									if ( null == d_userType){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_userType+ '</td>';
							    	}
									if ( null == d_authStatus){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_authStatus+ '</td>';
							    	}
									if ( null == d_authTime){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_authTime+ '</td>';
							    	}
									if(d_transStatus=="PROCESSING")
							        {
							            tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\')">补单</button></td>'; //操作
							        }
									else
							        {
							            tableBodyHtml += '<td>——</td>'; //操作
							        }
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
						$("#tcdPageCode").show();
					}else{
						 var html = ""; 
						 html +='<tr class="no-records-found">';
						 html +='<td colspan="9" align="center">没有找到匹配的记录</td>';
						 html += '</tr>';
			    		 $("#table").find("tbody").html(html);
			    		 $("#tcdPageCode").hide();
			    	}
				},error : function(){
		        	alert("获取认证记录失败");
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
		tableFun("/gateway/auth_record/authRecordList", pageData);		  
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
		tableFun("/gateway/auth_record/authRecordList",pageData);
		myPage();
});

/**
 * 补单
 */
function revise(requestId)
{
    $.ajax({
            type : "POST",
            url : "/gateway/auth_record/revise",
            data : 
            {
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
