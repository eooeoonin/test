/**
 * 银行错误码展示页
 */
var _pages;
$(function () {
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"20"
			};	
	tableFun("/gateway/change_code/listChangeCode", srhData1);	
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
    		d_channel = v.channel,//平台编码
    		d_business = v.business,//业务类型
    		d_codeName = v.codeName,//转码名称
    		d_outerCode = v.outerCode,//外部编码
    		d_outerInfo = v.outerInfo,//外部编码说明
    		d_innerCode = v.innerCode,//内部编码
    		d_innerInfo = v.innerInfo,//内部编码说明
    		d_direction = v.direction,//转码方向
    		d_available = v.available,//是否可用
    		d_status = v.status,//状态预留
    		d_editedBy = v.editedBy,//最后编辑人
    		d_createTime = v.createTime,// 创建时间
    		d_modifyTime = v.modifyTime;//最后编辑时间
    		
    	
      //输出HTML元素
      tableBodyHtml += '<tr>';
      tableBodyHtml += '<td>' + d_createTime + '</td>';
      tableBodyHtml += '<td>' + d_channel + '</td>';
      tableBodyHtml += '<td>' + d_business + '</td>';
      tableBodyHtml += '<td>' + d_codeName + '</td>';
      tableBodyHtml += '<td>' + d_outerCode + '</td>';
      tableBodyHtml += '<td>' + d_outerInfo + '</td>';
      tableBodyHtml += '<td>' + d_innerCode + '</td>';
      tableBodyHtml += '<td>' + d_innerInfo + '</td>';
      tableBodyHtml += '<td>' + d_direction + '</td>';
      tableBodyHtml += '<td>' + d_available + '</td>';
      tableBodyHtml += '<td>' + d_editedBy + '</td>';
      tableBodyHtml += '<td><a href="../gateway/change_code_update.html?id='+d_id+'" style="margin-left:10px;">编辑</a><a  name='+d_id+' href="javascript:" style="margin-left:10px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
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
				var _channel1 = $("#channel1").val();
		  	    var _bus1 = $("#business1").val();			
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "20",
					};
					tableFun("/gateway/change_code/listChangeCode", srhData4);	
			}
		});
}

function deleteIsystemConfig(sid){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/gateway/change_code/deleteChangeCode",
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