/**
 * 合同签署批次记录
 */
var _pages;
$(function() {
	var srhData1 = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/signet/contract_signing_batch_record/listSignatureBatch", srhData1);
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
			if (  "" != data.data.list) {
				var _table = $('#table'), tableBodyHtml = '';
				_pages = data.data.pages;
				$.each(
					data.data.list,function(k, v) {
					/**
					 * 获取数据信息
					 */
						var d_id = v.id, // 批次id
						 	d_borrowId = v.borrowId, // 借款id
						 	d_releaseId = v.releaseId, // 放款id
						 	d_platformCode = v.platformCode, // 平台id
						 	d_borrowerCode = v.borrowerCode, // 借款人id
						 	d_guarantorCode = v.guarantorCode, // 担保人id
						 	d_templetId = v.templetId, // 合同模板id
						 	d_createTime = v.createTime, // 创建时间
						 	d_status = v.status; // 状态
	
						// 输出HTML元素
						tableBodyHtml += '<tr>';
						tableBodyHtml += '<td>'+ d_id + '</td>';
						tableBodyHtml += '<td>'+ d_borrowId + '</td>';
						tableBodyHtml += '<td>'+ d_releaseId + '</td>';
						tableBodyHtml += '<td>'+ d_borrowerCode + '</td>';
						tableBodyHtml += '<td>'+ d_guarantorCode + '</td>';
						tableBodyHtml += '<td>'+ d_templetId + '</td>';
						tableBodyHtml += '<td>'+ d_createTime + '</td>';
						tableBodyHtml += '<td>'+ d_status + '</td>';
						if("FAIL" == d_status){
							tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="retry(\''+d_borrowId+'\',\''+d_id+'\',\''+d_releaseId+'\')">补签</button><button class="btn btn-primary" type="button" onclick="mock(\''+d_borrowId+'\',\''+d_id+'\',\''+d_releaseId+'\')">MOCK</button></td>'; //操作
						}else if("MOCK_SUCCESS" == d_status){
							tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="retry(\''+d_borrowId+'\',\''+d_id+'\',\''+d_releaseId+'\')">补签</td>'; //操作
						}else if("SUCCESS" == d_status){
							tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="cancel(\''+d_borrowId+'\',\''+d_id+'\',\''+d_releaseId+'\')">撤销</button></td>'; //操作
						}else {
							tableBodyHtml += '<td>——</td>';
						}
						tableBodyHtml += '</tr>';
					});
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
				$("#tcdPageCode").show();
			} else {
				var html = ""; 
				 html +='<tr class="no-records-found">';
				 html +='<td colspan="10" align="center">没有找到匹配的记录</td>';
				 html += '</tr>';
	    		 $("#table").find("tbody").html(html);
	    		 $("#tcdPageCode").hide();
			}
		},error : function(){
        	alert("获取合同签署批次记录失败");
        	$("#tcdPageCode").hide();
        },
		async : false
	});
}
function myPage() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(q) {
			var _borrowerCode = $("#borrowerCode").val();
			var srhData2 = {
				"pageNo" : q,
				"pageSize" : "20",
				"borrowerCode" : _borrowerCode
			};
			tableFun("/signet/contract_signing_batch_record/listSignatureBatch", srhData2);
		}
	});
}

var _srhBtn = $("#srhBtn");
_srhBtn.click(function() {
	var _borrowerCode = $("#borrowerCode").val();
	var srhData3 = {
		"pageNo" : "1",
		"pageSize" : "20",
		"borrowerCode" : _borrowerCode
	};
	tableFun("/signet/contract_signing_batch_record/listSignatureBatch", srhData3);
	myPage();
});

//补签
function retry(borrowId,batchId,releaseId)
{
    $.ajax({
            type : "POST",
            url : "/signet/contract_signing_batch_record/signatureRetry",
            data : 
            {
            	"borrowId" : borrowId,
            	"batchId":batchId,
            	"releaseId":releaseId
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
//MOCK
function mock(borrowId,batchId,releaseId)
{
	$.ajax({
		type : "POST",
		url : "/signet/contract_signing_batch_record/signatureMock",
		data : 
		{
			"borrowId" : borrowId,
			"batchId":batchId,
			"releaseId":releaseId
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
//撤销
function cancel(borrowId,batchId,releaseId)
{
	$.ajax({
		type : "POST",
		url : "/signet/contract_signing_batch_record/signatureCancel",
		data : 
		{
			"borrowId" : borrowId,
			"batchId":batchId,
			"releaseId":releaseId
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