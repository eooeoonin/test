/**
 * 合同详情记录
 */
var _pages;
$(function() {
	var srhData1 = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/signet/contract_details_record/listOrderProtocol", srhData1);
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
						var d_id = v.id, // id
						 	d_orderId = v.orderId, // 合同id
						 	d_borrowCode = v.borrowCode, // 借款编号
						 	d_savePath = v.savePath, // 保存路径
						 	d_protocolNo = v.protocolNo, // 协议编号
						 	d_borrowerSealId = v.borrowerSealId, // 借款人印章/印模id
						 	d_leanderSealImgId = v.leanderSealImgId, // 出款人印模id
						 	d_guarantorSealId = v.guarantorSealId, // 担保人印章/印模id
						 	d_systemSealId = v.systemSealId, // 平台印章/印模id
						 	d_status = v.status, // 状态
						 	d_preserve = v.preserve, // 是否保全
						 	d_evidenceId = v.evidenceId, // 存证id
						 	d_batchId = v.batchId; // 批次id
	
						// 输出HTML元素
						tableBodyHtml += '<tr>';
						tableBodyHtml += '<td>'+ d_orderId + '</td>';
						tableBodyHtml += '<td>'+ d_borrowCode + '</td>';
						tableBodyHtml += '<td>'+ d_savePath + '</td>';
						tableBodyHtml += '<td>'+ d_protocolNo + '</td>';
						tableBodyHtml += '<td>'+ d_borrowerSealId + '</td>';
						tableBodyHtml += '<td>'+ d_leanderSealImgId + '</td>';
						tableBodyHtml += '<td>'+ d_guarantorSealId + '</td>';
						tableBodyHtml += '<td>'+ d_systemSealId + '</td>';
						tableBodyHtml += '<td>'+ d_batchId + '</td>';
						tableBodyHtml += '<td>'+ d_evidenceId + '</td>';
						if ("SUCCESS" == d_status &&(null == d_preserve || "FAIL" == d_preserve)) {
							tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="evidence(\''+d_orderId+'\',\''+d_batchId+'\')">存证</td>'; //操作
						}else{
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
				 html +='<td colspan="9" align="center">没有找到匹配的记录</td>';
				 html += '</tr>';
	    		 $("#table").find("tbody").html(html);
	    		 $("#tcdPageCode").hide();
			}
		},error : function(){
        	alert("获取合同详情失败");
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
			var _orderId = $("#orderId").val();
			var srhData2 = {
				"pageNo" : q,
				"pageSize" : "20",
				"orderId" : _orderId
			};
			tableFun("/signet/contract_details_record/listOrderProtocol", srhData2);
		}
	});
}

var _srhBtn = $("#srhBtn");
_srhBtn.click(function() {
	var _orderId = $("#orderId").val();
	var srhData3 = {
		"pageNo" : "1",
		"pageSize" : "20",
		"orderId" : _orderId
	};
	tableFun("/signet/contract_details_record/listOrderProtocol", srhData3);
	myPage();
});

//补签
function evidence(orderId,batchId)
{
    $.ajax({
            type : "POST",
            url : "/signet/contract_details_record/evidencePreserve",
            data : 
            {
            	"orderId" : orderId,
            	"batchId":batchId,
            	"status":"SUCCESS"
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
