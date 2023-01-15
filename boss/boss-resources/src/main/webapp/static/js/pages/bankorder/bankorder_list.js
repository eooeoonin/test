/***
 *** URL
 ***/
function GetRequest() {
    var url = location.search; //
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



var _pages;var _transSta;
$(function () {
	
	var _table = $('#table');
	_table.bootstrapTable();
	
	var _transStatus = Request.transStatus;
	if(_transStatus == null){
		var srhData = {"pageNo":"1","pageSize":"10"};
	}else{
		var srhData = {"transStatus":_transStatus,"pageNo":"1","pageSize":"10"};
		$("#transStatus").val(_transStatus);
	}
	
	_transSta = $("#transStatus").val();
	
	tableFun("../bankorder/bankorder_list/list", srhData);	
	myPage();
	
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
        var   u_id = v.id,
        	  u_createTime = v.createTime,
        	  u_tradeTime = v.tradeTime,
              u_indexOrderId = v.indexOrderId,
              u_payAccountName = v.payAccountName,
              u_payAccountNo = v.payAccountNo,
              u_amount = v.amount.amount,
              u_transStatus = v.transStatus
              
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + u_createTime + '</td>';
          tableBodyHtml += '<td>' + u_tradeTime + '</td>';
          tableBodyHtml += '<td>' + u_indexOrderId + '</td>';
          tableBodyHtml += '<td>' + u_payAccountName + '</td>';
          tableBodyHtml += '<td>' + u_payAccountNo + '</td>';
          tableBodyHtml += '<td>' + v.payBranchBank + '</td>';
          tableBodyHtml += '<td>' + v.payBankAddress + '</td>';
          tableBodyHtml += '<td>' + v.transMessage + '</td>';
          tableBodyHtml += '<td>' + u_amount + '</td>';
          if(u_transStatus == 'INIT'){																																	 
        	  tableBodyHtml += '<td><a href="bankorder_list_details.html?id='+u_id+'&transStatus='+_transSta+'">详情</a>  <a href="#" title='+u_indexOrderId+' style="margin-left:15px;" onclick="offlinePay(this)">补单</a></td>';
          }if(u_transStatus == 'MATCHED'){
				tableBodyHtml += '<td><a href="bankorder_list_details.html?id='+u_id+'&transStatus='+_transSta+'">详情</a></td>';
		  }if(u_transStatus == 'REFUND'){
				tableBodyHtml += '<td><a href="bankorder_list_details.html?id='+u_id+'&transStatus='+_transSta+'">详情</a></td>';
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
			var _userId = $("#userId").val();
			var _startTime = $("#startTime").val();
			var _endTime = $("#endTime").val();
			var _indexOrderId = $("#indexOrderId").val();
			var _transStatus = $("#transStatus").val();
				  _transSta = $("#transStatus").val();
			if(_startTime!= "" && _endTime != ""){
				srhData = {
					  "userId":_userId,
					  "indexOrderId":_indexOrderId,
					  "transStatus":_transStatus,
					  "startTime":_startTime,
					  "endTime":_endTime,
					  "pageNo":p,
					  "pageSize":"10"
				};
			}
			else{
				srhData = {
						"userId":_userId,
						  "indexOrderId":_indexOrderId,
						  "transStatus":_transStatus,
						  "pageNo":p,
						  "pageSize":"10"
				};
			}	
			tableFun("../bankorder/bankorder_list/list", srhData);
		}
	});
};
		

	$("#srhBtn").click(function(){
			var _userId = $("#userId").val();
			var _startTime = $("#startTime").val();
			var _endTime = $("#endTime").val();
			var _indexOrderId = $("#indexOrderId").val();
			var _transStatus = $("#transStatus").val();
				  _transSta = $("#transStatus").val();
			if(_startTime!= "" && _endTime != ""){
				srhData = {
					  "userId":_userId,
					  "indexOrderId":_indexOrderId,
					  "transStatus":_transStatus,
					  "startTime":_startTime,
					  "endTime":_endTime,
					  "pageNo":"1",
					  "pageSize":"10"
				};
			}
			else{
				srhData = {
						"userId":_userId,
						  "indexOrderId":_indexOrderId,
						  "transStatus":_transStatus,
						  "pageNo":"1",
						  "pageSize":"10"
				};
			}	
		
		tableFun("../bankorder/bankorder_list/list", srhData);	
		myPage();	
	})

	
	function offlinePay(indexOrderId){
		 $.ajax({
		        type : "POST",
		        url : "../bankorder/refundrecord_list/reviseOfflin",
		        data : {"indexOrderId":indexOrderId.title},
		        dataType : "json",
		        success : function(data) {
		        	if (data != null && data != "") {
		        			alert(data);
							window.location.reload();
					}
		        },error : function(data){
		        	alert("补单失败");
		        },
		        async : false
		    });
	}
	
