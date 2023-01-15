var _dateOne;
var _pages;
var _pageSize = 5;
$(function () {
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：LSC
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();

  var srhData = {"pageNo":"1","pageSize":_pageSize};
  tableFun("/web_mgt/w_app_banner_list/banner/page", srhData);	
  myPage();
});

/***
 *功能说明：表格数据
 *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
 *创建人：LSC
 *时间：2016-08-01
 ***/
function tableFun(tdUrl, tbData) {
	$.ajax({
		type: "POST",
		url: tdUrl,
		data: tbData,
		dataType: "json",
		async : false,
		success: function (data) {
			if(data.resCode  == 0){
			_pages  = data.data.pages;
			var _data = data.data.list;
			var _table = $('#table'),
			tableBodyHtml = '';
				
			
			$.each(_data, function (k, v) {
				//获取数据
				var d_ID = v.id,
				d_titile = v.titile, //名称
				d_body = v.body; //内容
				d_time = v.modifyTime;//调整时间             
				d_isShow=v.isShow;//
				d_weight=v.weight;
				
				var date = new Date(d_time);
				var dateStr = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();  
				
				//输出HTML元素
				tableBodyHtml += '<tr>';
				tableBodyHtml += '<td>' + d_titile + '</td>';
				tableBodyHtml += '<td>' + d_body + '</td>';
				tableBodyHtml += '<td>' + dateStr + '</td>';
				tableBodyHtml += '<td class="w_available">' + (v.isShow == '1' ? '开启' : '关闭') + '</td>>';
				tableBodyHtml += '<td><a href="w_app_banner_list_edit.html?id='+d_ID+'">编辑</a><a  title='+v.id +' href="#" style="margin-left:5px;" onclick="deleteiportol(\''+ v.id  +'\')">删除</a>' + 
									'<a href="javascript:" id="'+ v.id +'" style="margin-left:5px;" class="changeStatus">' + (v.isShow == 0 ? "开启" : "关闭") + '</a>';
				tableBodyHtml += '<td><button class="btn btn-primary" type="button" title='+d_ID+' onclick="orderDown(this)"><i class="fa fa-arrow-down"></i></button><br/><button class="btn btn-primary " type="button" title='+d_ID+' onclick="orderUp(this)"><i class="fa fa-arrow-up"></i></button>';
				
				tableBodyHtml += '</tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
			_table.find("tr").find(".changeStatus").each(function(){
				$(this).click(function(){
					var _id = $(this).attr("id");
					var changeText = "";
					var changeText1 = $(this).html();
					$.ajax({
						type : "POST",
						url : "/web_mgt/w_app_banner_list/banner/changeStatus",
						data : {
							"id" : _id
						},
						dataType: "json",
						async:false,
						success : function(data) {
							if(data.flag > 0){
								alert("操作成功！");
								if(changeText1 == "开启"){
									changeText = "关闭";
								}else{
									changeText = "开启";
								}
								$("#"+_id).html(changeText);
								$("#"+_id).parent().parent().find(".w_available").html(changeText1);
							} else {
								alert("操作失败！");
							}
						},
						async : false
					});
				})
			})
		}
		}
	});
}

function orderDown(bannerDown) {
	$.ajax({
		type: "POST",
		url: "/web_mgt/w_app_banner_list/banner/orderDown",  //下调
		dataType: "json",
		data: {"id": bannerDown.title},
		success: function (data) {
			if (data == 0) {
				return;
			};
			location=location
		}, error: function () {
			alert("调整失败");
			location=location
		}
	});
}

var myPage = function(){
	//分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount: _pages,
		current: 1,
		backFn: function (p) {
			var pageData = {
				"pageNo" : p,
				"pageSize" : _pageSize
			};
			tableFun("/web_mgt/w_app_banner_list/banner/page", pageData);
		}
	});
}

function orderUp(bannerDown) {
	$.ajax({
		type: "POST",
		url: "/web_mgt/w_app_banner_list/banner/orderUp",  //下调
		dataType: "json",
		data: {"id": bannerDown.title},
		success: function (data) {
			if (data == 0) {
				return;
			};
			location=location
		}, error: function () {
			alert("调整失败");
			location=location
		}
	});
}

function deleteiportol(pid){
	bootbox.confirm("确定要删除吗?", function(result){
		if(result){
			$.ajax({
				type : "POST",
				url : "/web_mgt/w_app_banner_list/banner/delete",
				data : {
					"id" : pid
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
