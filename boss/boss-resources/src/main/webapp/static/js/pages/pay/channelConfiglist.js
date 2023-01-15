/**
 * 
 */
var _pages;
$(function () {
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"10"
			};	
	tableFun("/pay/channelConfig/listChannelConfig", srhData1);	
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
    		d_channelName = v.channelName,// 渠道名称
    		d_channelCode = v.channelCode,//渠道编号
    		d_business = v.business,//业务类型  
    		d_transaction = v.transaction,//事务类型：绑卡、支付
    		d_merchant = v.merchant,//商户
    		d_merchantCode = v.merchantCode,//商户编号
    		d_needSms = v.needSms,//是否需要发送短验：System=系统发送、Channel=通道发送
    		d_available = v.available,//是否可用
    		d_singleQuota = v.singleQuota,//单笔限额
    		d_dayQuota = v.dayQuota,//单日限额
    		d_monthQuota = v.monthQuota,//单月限额
    		d_quotaDesc = v.quotaDesc,//限额文字描述
    		d_configFile = v.configFile,// 配置文件
    		d_extension = v.extension;//扩展信息
    		var dneed;
    		switch(d_needSms){
    		case "System":
    			dneed="系统发送";
    			break;
    		case "Channel":
    			dneed="通道发送";
    			break;
    		default:
    			dneed="——";
    		break;
    		};
    	
    		var dtran;
    		switch(d_transaction){
			case "Pay":
				dtran="支付";
				break;
			case "BindCard":
				dtran="绑卡";
				break;
			default:
				dtran="未知";
			break;
    		};
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
		var result1;
		if(d_available==1){
			result1="可用";
		}else if(d_available==0){
			result1="不可用";
		}
    		var need;
    		if(d_needSms=="System"){
    			need="系统发送";
    		}else if(d_needSms=="Channel"){
    			need="通道发送";
    		}else{
    			need="未知";
    		}
      //输出HTML元素
      tableBodyHtml += '<tr>';
      tableBodyHtml += '<td>' + d_channelName + '</td>';
      tableBodyHtml += '<td>' + d_channelCode + '</td>';
      tableBodyHtml += '<td>' + busine + '</td>';
      tableBodyHtml += '<td>' + d_merchant + '</td>';
      tableBodyHtml += '<td>' + d_merchantCode + '</td>';
      tableBodyHtml += '<td>' + d_singleQuota + '</td>';
      tableBodyHtml += '<td>' + d_dayQuota + '</td>';
      tableBodyHtml += '<td>' + d_monthQuota + '</td>';
      tableBodyHtml += '<td>' + dneed + '</td>';
      tableBodyHtml += '<td>' + result1 + '</td>';
      tableBodyHtml += '<td><a href="../pay/channelConfigUpdate.html?id='+d_id+'" style="margin-left:10px;">编辑</a><a  name='+d_id+' href="javascript:" style="margin-left:10px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
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
		  	   // var _channelName1 = $("#channelCode1").val();	
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "10"
					};
					tableFun("/pay/channelConfig/listChannelConfig", srhData4);	
			}
		});
}
var _insertBtn = $("#insertBtn");
_insertBtn.click(function(){
	location = "../pay/channelConfigInsert.html";
});
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	  //var _channelName1 = $("#channelCode1").val();
		  var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "10"
			};
			tableFun("/pay/channelConfig/listChannelConfig",srhData2);
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