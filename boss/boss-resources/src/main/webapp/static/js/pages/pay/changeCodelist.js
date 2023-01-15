/**
 * 
 */
var _pages;
$(function () {
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"20"
			};	
	tableFun("/pay/changeCode/listChangeCode", srhData1);	
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
    		d_business = v.business,//业务类型
    		d_transaction = v.transaction,//交易事务类型：Pay=支付、BindCard=绑卡、RealName=实名认证
    		d_codeName = v.codeName,//转码名称
    		d_outerCode = v.outerCode,//外部编码
    		d_outerInfo = v.outerInfo,//外部编码说明
    		d_innerCode = v.innerCode,//内部编码
    		d_innerInfo = v.innerInfo,//内部编码说明
    		d_direction = v.direction,//转码方向
    		//ToInner("ToSys","外码到内码"),ToOuter("ToBank","内码到外码"),ToAll("ToAll","双向"), 		
    		d_extend = v.extend,//扩展信息
    		d_available = v.available,//是否可用
    		d_createTime = v.createTime,// 创建时间
    		d_modifyTime = v.modifyTime;//最后编辑时间
    		var direct;
    		switch(d_direction){
			case "ToInner":
				direct="外码到内码";
				break;
			case "ToOuter":
				direct="内码到外码";
				break;
			case "ToAll":
				direct="双向";
				break;
			default:
				direct="未知";
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
    		var jiaoyitype;
    		if(d_transaction=="Pay"){
    			jiaoyitype="支付";
    		}else if(d_transaction=="BindCard"){
    			jiaoyitype="绑卡";
    		}else if(d_transaction=="RealName"){
    			jiaoyitype="实名认证";
    		}else{
    			jiaoyitype="——";
    		}
      //输出HTML元素
      tableBodyHtml += '<tr>';
      tableBodyHtml += '<td>' + d_createTime + '</td>';
      tableBodyHtml += '<td>' + d_channelCode + '</td>';
      tableBodyHtml += '<td>' + d_codeName + '</td>';
      tableBodyHtml += '<td>' + busine + '</td>';
      tableBodyHtml += '<td>' + jiaoyitype + '</td>';
      tableBodyHtml += '<td>' + d_outerCode + '</td>';
      tableBodyHtml += '<td>' + d_outerInfo + '</td>';
      tableBodyHtml += '<td>' + d_innerCode + '</td>';
      tableBodyHtml += '<td>' + d_innerInfo + '</td>';
      tableBodyHtml += '<td>' + direct + '</td>';;
      tableBodyHtml += '<td>' + result1 + '</td>';
      tableBodyHtml += '<td><a href="../pay/changeCodeUpdate.html?id='+d_id+'" style="margin-left:10px;">编辑</a><a  name='+d_id+' href="javascript:" style="margin-left:10px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
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
		  	    var _channelName1 = $("#channelCode1").val();	
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "20",
							"channelCode" : _channelName1
					};
					tableFun("/pay/changeCode/listChangeCode", srhData4);	
			}
		});
}
var _insertBtn = $("#insertBtn");
_insertBtn.click(function(){
	location = "../pay/changeCodeInsert.html";
});
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	  var _channelName1 = $("#channelCode1").val();
		  var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "20",
					"channelCode" : _channelName1
			};
			tableFun("/pay/changeCode/listChangeCode",srhData2);
			myPage2();
});  

function deleteIsystemConfig(sid){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/pay/changeCode/deleteChangeCode",
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