var _pages;
var _pageSize = 10;
$(function() {
	var start = {
			elem : "#startTime",
			format : "YYYY/MM/DD hh:mm",
			istime : true,
			istoday : true,
			choose : function(datas) {
				end.min = datas;
				end.start = datas;
			}
		};
	var end = {
		elem : "#endTime",
		format : "YYYY/MM/DD hh:mm",
		istime : true,
		istoday : true,
		choose : function(datas) {
			start.max = datas;
		}
	};
	laydate(start);
	laydate(end);
	
	tData = {
		"pageNo":1,
		"pageSize":_pageSize
	}
	tableFun("/web_mgt/w_app_news_list/newsList",tData);
	myPage();
});

$("#srhBtn").click(function() {  
	var _titleKeyWord = $("#titleKeyWord").val();
	var _startTime = $("#startTime").val();
	var _endTime = $("#endTime").val();
	var _isShow = $("#isShow").val();
	var srhData = {
			"pageNo" : "1",
			"pageSize" : _pageSize,
			"titleKeyWord" : _titleKeyWord, 
			"startTime" : _startTime, 
			"endTime" : _endTime, 
			"isShow" : _isShow
	};
	tableFun("/web_mgt/w_app_news_list/listByFilter", srhData);
	myPage();
});

var tableFun = function(url,tData){
	$.ajax({
		type:"POST",
		url:url,
		data:tData,
		dataType:"json",
		success:function(data){
			_pages = data.pages;
			var _table = $('#table'), tableBodyHtml = '';
			var newslnk = mktUrl + "html/a_";
			$.each(data.list,function(k,v){
				tableBodyHtml += '<tr>';
				tableBodyHtml += '<td>' + v.createTime + '</td>';
				tableBodyHtml += '<td>' + v.titile + '</a></td>>';
				tableBodyHtml += '<td class="newslink">' + newslnk + v.id + '.html</a><input type="hidden" id="nlikhd" value="' + newslnk + v.id + '.html"></td>>';
				tableBodyHtml += '<td>' + v.preview + '</a></td>>';
				tableBodyHtml += '<td>' + v.wxBody + '</a></td>>';
				tableBodyHtml += '<td class="w_available">' + (v.isShow == '1' ? '上线' : '下线') + '</a></td>>';
                tableBodyHtml += '<td>' + v.weight + '</a></td>>';
				tableBodyHtml += '<td style="position:relative"><a href="w_app_news_list_edit.html?id='+ v.id + '" style="margin-left:5px;">编辑</a><a  title='+v.id +' href="#" style="margin-left:5px;" onclick="deleteiportol(\''+ v.id  +'\')">删除</a><a href="javascript:" style="margin-left:5px;" class="copylink">复制链接</a>' + 
									'<a href="javascript:" id="'+ v.id +'" style="margin-left:5px;" class="changeStatus">' + (v.isShow == 0 ? "上线" : "下线") + '</a>';
				tableBodyHtml += '</td></tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
			//链接复制
			_table.find("tr").each(function(){
				//$(this).find(".copylink").click(function(){
				var nlikhd = $(this).find("#nlikhd").val();  //获取链接值
				$(this).find(".copylink").zclip({
					path: "/static/js/plugins/zclip/ZeroClipboard.swf",
					copy: function () {
						return nlikhd;
					},
					afterCopy: function () {/* 复制成功后的操作 */
						alert("复制成功！");
					}
				});
				//});
			});
			_table.find("tr").find(".changeStatus").each(function(){
				$(this).click(function(){
					var _id = $(this).attr("id");
					var changeText = "";
					var changeText1 = $(this).html();
					$.ajax({
						type : "POST",
						url : "/web_mgt/w_app_news_list/changeStatus",
						data : {
							"id" : _id
						},
						dataType: "json",
						async:false,
						success : function(data) {
							if(data.flag > 0){
								alert("操作成功！");
								if(changeText1 == "上线"){
									changeText = "下线";
								}else{
									changeText = "上线";
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
		},
		async : false
	});
};
function deleteiportol(pid){
	bootbox.confirm("确定要删除吗?", function(result){
		if(result){
			$.ajax({
				type : "POST",
				url : "/web_mgt/w_app_news_list/newsDelete",
				data : {
					"id" : pid
				},
				dataType: "json",
				async:false,
				success : function(data) {

					if (data != null && data != "") {
						if (data == 1) {
							bootbox.alert("新闻删除成功", function(result){
								var pageData = {
										"pageNo" : 1,
										"pageSize" : _pageSize,
										"titleKeyWord" : $("#titleKeyWord").val(), 
										"startTime" : $("#startTime").val(), 
										"endTime" : $("#endTime").val(), 
										"isShow" : $("#isShow").val()
									};
									tableFun("/web_mgt/w_app_news_list/listByFilter", pageData);		
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
var myPage = function(){
	//分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount: _pages,
		current: 1,
		backFn: function (p) {
			var pageData = {
				"pageNo" : p,
				"pageSize" : _pageSize,
				"titleKeyWord" : $("#titleKeyWord").val(), 
				"startTime" : $("#startTime").val(), 
				"endTime" : $("#endTime").val(), 
				"isShow" : $("#isShow").val()
			};
			tableFun("/web_mgt/w_app_news_list/listByFilter", pageData);			
		}
	});
}

$("#addButton").click(function(){
	window.location.href = "../web_mgt/w_app_news_list_add.html";
});
