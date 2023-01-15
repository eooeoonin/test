/***
 *** 获取URL参数
 ***/
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
var Request = {};
Request = GetRequest();


var _pages;var _id;
$(function () {
	var _table = $('#table');
	_table.bootstrapTable();

    //URL参数
    _id = Request.id;
    
  //时间段引用方式
    var start = {
      elem: "#startTime", format: "YYYY/MM/DD", istime: true, istoday: false, choose: function (datas) {
        end.min = datas;
        end.start = datas
      }
    };
    var end = {
      elem: "#endTime", format: "YYYY/MM/DD", istime: true, istoday: false, choose: function (datas) {
        start.max = datas
      }
    };
    laydate(start);
    laydate(end);

	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("../bankorder/refundrecord_list/transLogList", srhData);	
	myPage();
 });
	

function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';

        _pages = data.pages;
        
        $.each(data.list, function (k, v) {
          //获取数据
        var u_id = v.id,
        	  u_indexOrderId = v.indexOrderId,
        	  u_sequenceId = v.sequenceId,
        	  u_payAccountNo = v.payAccountNo,
        	  u_payAccountName = v.payAccountName,
        	  u_bankAccountNo = v.bankAccountNo,
        	  u_amount = v.amount.amount,
              u_orderStatus = v.orderStatus,
              u_requestTime = v.requestTime,
              u_responseMessage = v.responseMessage;
              
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + u_indexOrderId + '</td>';
          tableBodyHtml += '<td>' + u_sequenceId + '</td>';
          tableBodyHtml += '<td>' + u_payAccountNo + '</td>';
          tableBodyHtml += '<td>' + u_payAccountName + '</td>';
          tableBodyHtml += '<td>' + u_bankAccountNo + '</td>';
          tableBodyHtml += '<td>' + u_amount + '</td>';
          tableBodyHtml += '<td>' + v.editedBy + '</td>';
          tableBodyHtml += '<td>' + u_requestTime + '</td>';
          tableBodyHtml += '<td>' + u_responseMessage + '</td>';
          if(v.transStatus == 'REFUND_APPLY_FAIL'){
        	  tableBodyHtml += '<td><a onclick="opera(\''+u_id+'\')">操作</a></td>';
          }else{
        	  tableBodyHtml += '<td></td>';
          }
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
      },
      async : false
    });
  }


   // 分页
  var myPage = function(){
  var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var _startTime = $("#startTime").val();
			var _endTime = $("#endTime").val();
			var _indexOrderId = $("#indexOrderId").val();
			var _orderStatus = $("#orderStatus").val();
			if(_startTime!= "" && _endTime != ""){
				srhData = {
					  "indexOrderId":_indexOrderId,
					  "orderStatus":_orderStatus,
					  "startTime":_startTime,
					  "endTime":_endTime,
					  "pageNo":p,
					  "pageSize":"10"
				};
			}
			else{
				srhData = {
						  "indexOrderId":_indexOrderId,
						  "orderStatus":_orderStatus,
						  "pageNo":p,
						  "pageSize":"10"
				};
			}	
			tableFun("../bankorder/refundrecord_list/transLogList", srhData);
		}
	});
};
		

	$("#srhBtn").click(function(){
		var _startTime = $("#startTime").val();
		var _endTime = $("#endTime").val();
		var _indexOrderId = $("#indexOrderId").val();
		var _orderStatus = $("#orderStatus").val();
		if(_startTime!= "" && _endTime != ""){
			srhData = {
				  "indexOrderId":_indexOrderId,
				  "orderStatus":_orderStatus,
				  "startTime":_startTime,
				  "endTime":_endTime,
				  "pageNo":"1",
				  "pageSize":"10"
			};
		}
		else{
			srhData = {
					  "indexOrderId":_indexOrderId,
					  "orderStatus":_orderStatus,
					  "pageNo":"1",
					  "pageSize":"10"
			};
		}	
		tableFun("../bankorder/refundrecord_list/transLogList", srhData);	
		myPage();
	})
function opera(u_id){
		location.href = "/bankorder/refundrecord_list_retry.html?id="+u_id;
}
