/**
 * 
 */
var _pages;
$(function () {
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"50"
			};	
	tableFun("/pay/bindCard/listBindCard", srhData1);	
	myPage2();	  
   	var _table = $('#table');
	  _table.bootstrapTable();
});	 
/**
 * 
 */
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
    		var d_id = v.id,//请求id
    		d_merchant = v.merchant,//商户
    		d_channel = v.channel,//渠道
    		d_from = v.from,//来源
    		d_username = v.username,//用户名
    		d_sequenceId = v.sequenceId,//流水id
    		d_requestTime = v.requestTime,//请求时间
    		d_cardNo = v.cardNo,//银行卡号
    		d_bankName = v.bankName,//银行名称
    		d_certNumber = v.certNumber,//身份证号    
    		d_realName = v.realName,//姓名
    		d_mobile = v.mobile,//银行预留手机号
    		d_action = v.action,//动作
    		d_transState = v.transState,//交易状态
    		d_bindStatus = v.bindStatus,//绑定状态
    		d_responseMessage = v.responseMessage,//通道响应消息
    		d_remark = v.remark;//备注
      //输出HTML元素
      tableBodyHtml += '<tr>';
      tableBodyHtml += '<td>' + d_merchant + '</td>';
      tableBodyHtml += '<td>' + d_channel + '</td>';
      tableBodyHtml += '<td>' + d_from + '</td>';
      tableBodyHtml += '<td>' + d_id + '</td>';
      tableBodyHtml += '<td>' + d_sequenceId + '</td>';
      tableBodyHtml += '<td>' + d_username + '</td>';
      tableBodyHtml += '<td>' + d_realName + '</td>';
      tableBodyHtml += '<td>' + d_mobile + '</td>';
      tableBodyHtml += '<td>' + d_certNumber + '</td>';
      tableBodyHtml += '<td>' + d_cardNo + '</td>';
      tableBodyHtml += '<td>' + d_bankName + '</td>';
      tableBodyHtml += '<td>' + d_action + '</td>';
      tableBodyHtml += '<td>' + d_transState + '</td>';
      tableBodyHtml += '<td>' + d_bindStatus + '</td>';
      tableBodyHtml += '<td>' + d_responseMessage + '</td>';
      tableBodyHtml += '<td>' + d_remark + '</td>';
      //tableBodyHtml += '<td><a href="../pay/channelConfigUpdate.html?id='+d_id+'" style="margin-left:10px;">编辑</a><a  name='+d_id+' href="javascript:" style="margin-left:10px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
      tableBodyHtml += '</tr>';
    });
    _table.find("tbody").html(tableBodyHtml);
    replaceFun(_table);
  },
async : false
});
}
function myPage2(){
	  var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(q) {	 
		  	   var mobile = $("#mobile").val();
		  	 var certNumber = $("#certNumber").val();
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "50",
							"mobile" : mobile,
							"certNumber" : certNumber
								
								
					};
					tableFun("/pay/bindCard/listBindCard", srhData4);	
			}
		});
}
var _insertBtn = $("#insertBtn");
_insertBtn.click(function(){
	location = "../pay/channelConfigInsert.html";
});
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	   var mobile = $("#mobile").val();
	  	 var certNumber = $("#certNumber").val();
		  var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "50",
					"mobile" : mobile,
					"certNumber" : certNumber
			};
			tableFun("/pay/bindCard/listBindCard",srhData2);
			myPage2();
});  

function deleteIsystemConfig(sid){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/pay/channelConfig/deleteChannelConfig",
					data : {
						"id" : sid.name 
						},
					dataType: "json",
					async:false,
					success : function(data) {
						if (data != null && data != "") {
							if (data == 1) {
								bootbox.alert("删除成功", function(result){
									window.location.reload();
								});
							} else {
								bootbox.alert(data.msg);
							}
						}
					},
					async : false
				});
			}
		});
}