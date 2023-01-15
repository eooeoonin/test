var _pages;
$(function () {

	var srhData = {
			"pageNo" : "1",
			"pageSize" : "20"
//			"business" : "CHECK_ACCOUNT"
		};
		tableFun("/capital/checkAccountRecordList", srhData);
		myPage();
		var _table = $('#table');
		_table.bootstrapTable();
	
    laydate({elem: "#date1", format: "YYYY/MM/DD", istoday: false});


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
            url: "/capital/check",
            data: $("#checkForm").serialize(),
            success: function (data) {
                if (data != null && data != "") {
                    if (data.resCode == 0) {
                        var _data = data.data;
                        bootbox.alert(_data.resultCodeMsg);
                    } else {
                        bootbox.alert(data.msg);
                    }
                }
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
							    	d_checkDate = v.checkDate,
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
							    	tableBodyHtml += '<td>' + d_checkDate+ '</td>';
							    	if ( null == d_responseCode || "" == d_responseCode){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseCode+ '</td>';
							    	}
									if ( null == d_responseMessage || "" == d_responseMessage){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseMessage+ '</td>';
							    	}
									if ( null == d_responseTime || "" == d_responseTime){
							    		tableBodyHtml += '<td>——</td>';
							    	} else {
							    		tableBodyHtml += '<td>' + d_responseTime+ '</td>';
							    	}
							    	tableBodyHtml += '<td>' + d_transStatus+ '</td>';
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

};

var myPage = function(){
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {  
			var pageData = {
			"pageNo" : p,
			"pageSize" : "20"
//			"business" : "CHECK_ACCOUNT"
			};
		tableFun("/capital/checkAccountRecordList", pageData);		  
	  }
	});
};