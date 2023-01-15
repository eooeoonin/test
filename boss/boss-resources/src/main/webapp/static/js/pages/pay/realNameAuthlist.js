/**
 * 
 */
var _pages;
$(function () {
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"50"
			};	
	tableFun("/pay/realNameAuth/listRealNameAuth", srhData1);	
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
    		var d_id = v.requestId,//请求id
    		d_from = v.from,//来源
    		d_userId = v.userId,//用户id
    		d_username = v.username,//用户名 
    		d_realName = v.realName,//真实姓名
    		d_certType = v.certType,//证件类型
    		d_certNumber = v.certNumber,//证件号码
    		d_authStatus = v.authStatus,//认证状态：0=未知  1=一致  -1=不一致
    		d_transStatus = v.transState,//交易状态（0=处理中 1=成功  -1=失败 -2=取消）
    		//SELF_SUCCESS自成功（未发送，使用已有数据）
    	    //PROCESSING 处理中 SUCCESS(1,"成功"),FAILURE(-1,"失败"),SEND_FAIL(-2,"取消"),INIT(0,"未发送");
    		d_requestTime = v.requestTime,//请求时间
    		d_authTime = v.authTime,//认证时间
    		d_responseCode = v.responseCode,//应答编码
    		d_responseMessage = v.responseMessage,//应答信息
    		d_gender = v.gender,//性别
    		d_Birthday = v.Birthday;//生日
    	
		var dauthat;//认证
		switch(d_authStatus){
		case 0:
			dauthat="未知";
			break;
		case 1:
			dauthat="一致";
			break;
		case -1:
			dauthat="不一致";
			break;
		default:
			dauthat="——";
		break;
		};
		var transState;//交易
		switch(d_transStatus){
		case 0:
			transState="处理中";
			break;
		case 1:
			transState="成功";
			break;
		case -1:
			transState="失败";
			break;
		case -2:
			transState="取消";
			break;	
		default:
			transState="——";
		break;
		};
    	
      //输出HTML元素
      tableBodyHtml += '<tr>';
      tableBodyHtml += '<td>' + d_requestTime + '</td>';
      tableBodyHtml += '<td>' + d_authTime + '</td>';
      tableBodyHtml += '<td>' + d_username + '</td>';
      tableBodyHtml += '<td>' + d_realName + '</td>';
      tableBodyHtml += '<td>' + d_certNumber + '</td>';
      tableBodyHtml += '<td>' + dauthat + '</td>';
      tableBodyHtml += '<td>' + transState + '</td>';
      tableBodyHtml += '<td>' + d_responseCode + '</td>';
      tableBodyHtml += '<td>' + d_responseMessage + '</td>';

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
		  	   var _realName1 = $("#realName1").val();
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "50",
							"realName" : _realName1
					};
					tableFun("/pay/realNameAuth/listRealNameAuth", srhData4);	
			}
		});
}
var _insertBtn = $("#insertBtn");
_insertBtn.click(function(){
	location = "../pay/channelConfigInsert.html";
});
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	var _realName1 = $("#realName1").val();
		  var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "50",
					"realName" : _realName1
			};
			tableFun("/pay/realNameAuth/listRealNameAuth",srhData2);
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