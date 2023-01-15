/**
 * 
 */
var _pages;
$(function () {
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"20"
			};	
	tableFun("/pay/returnCode/listReturnCode", srhData1);	
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
      /**
       * 获取数据信息
       * */
    	
    	var d_id = v.id,//id
    		d_channelCode = v.channelCode,//通道编码
    		d_channelName = v.channelName,//通道名称
    		d_business = v.business,//业务类型
    		d_transaction = v.transaction,//交易事务类型：Pay=支付、BindCard=绑卡、RealName=实名认证
    		d_outerCode = v.outerCode,//外部编码
    		d_outerInfo = v.outerInfo,//外部编码说明
    		d_innerCode = v.innerCode,//内部编码
    		d_innerInfo = v.innerInfo,//内部编码说明
    		d_result = v.result,//结果：-1=失败 2=处理中、 1=成功
    		d_useState = v.useState,//使用状态：1=启用  0=未知，待配置 -1=不启用
    		d_extend = v.extend,//扩展信息
    		d_status = v.status,//状态预留
    		d_editedBy = v.editedBy,//最后编辑人
    		d_createTime = v.createTime,// 创建时间
    		d_modifyTime = v.modifyTime;//最后编辑时间
    		var busine;
    		switch(d_business){
			case "NetBank":
				busine="网银业务";
				break;
			case "Quick":
				busine="快捷业务";
				break;
			case "POS":
				busine="POS刷卡";
				break;
			case "Withdraw":
				busine="提现";
				break;
			case "RealName":
				busine="实名认证";
				break;
			case "BindCard":
				busine="绑卡认证";
				break;
			default:
				busine="未知";
			break;
		
		};
    		var useState1;
    		if(d_useState==1){
    			useState1="启用";
    		}else if(d_useState==0){
    			useState1="待配置";
    		}else if(d_useState==-1){
    			useState1="不启用";
    		}
    		var jiaoyitype;
    		if(d_transaction=="Pay"){
    			jiaoyitype="支付";
    		}else if(d_transaction=="Info"){
    			jiaoyitype="信息";
    		}else if(d_transaction=="Auth"){
    			jiaoyitype="认证";
    		}else{
    			jiaoyitype="——";
    		}
    		
    	
      //输出HTML元素
      tableBodyHtml += '<tr>';
      tableBodyHtml += '<td>' + d_createTime + '</td>';
      tableBodyHtml += '<td>' + d_channelCode + '</td>';
      tableBodyHtml += '<td>' + d_channelName + '</td>';
      tableBodyHtml += '<td>' + busine + '</td>';
      tableBodyHtml += '<td>' + jiaoyitype + '</td>';
      tableBodyHtml += '<td>' + d_outerCode + '</td>';
      tableBodyHtml += '<td>' + d_outerInfo + '</td>';
      tableBodyHtml += '<td>' + d_innerCode + '</td>';
      tableBodyHtml += '<td>' + d_innerInfo + '</td>';
      tableBodyHtml += '<td>' + d_editedBy + '</td>';
      tableBodyHtml += '<td>' + useState1 + '</td>';
      tableBodyHtml += '<td>' + d_result + '</td>';
      tableBodyHtml += '<td><a href="../pay/returnCodeUpdate.html?id='+d_id+'" style="margin-left:10px;">编辑</a><a  name='+d_id+' href="javascript:" style="margin-left:10px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
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
				var _channelCode1 = $("#channelCode1").val();
		  	    var _channelName1 = $("#channelName1").val();
		  	    var _bus1 = $("#business1").val();			
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "20",
							"channelCode" : _channelCode1,
							"channelName" : _channelName1,
							"business" : _bus1
					};
					tableFun("/pay/returnCode/listReturnCode", srhData4);	
			}
		});
}
var _insertBtn = $("#insertBtn");
_insertBtn.click(function(){
	location = "../pay/returnCodeInsert.html";
});
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	  var _channelCode1 = $("#channelCode1").val();
	  var _channelName1 = $("#channelName1").val();
	  var _bus1 = $("#business1").val();
		  var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "20",
					"channelCode" : _channelCode1,
					"channelName" : _channelName1,
					"business" : _bus1
			};
			tableFun("/pay/returnCode/listReturnCode",srhData2);
			myPage2();
});  

function deleteIsystemConfig(sid){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/pay/returnCode/deleteReturnCode",
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