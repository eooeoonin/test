var templateId = "";
var _pages = 1;
$(function() {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#modal_form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
		var srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
		};
		tableFun("/basedata_mgt/templateManager/getTemplateList", srhData);
		myPage();
	
});

$("#addBtn").click(function(){
	location.href = "/basedata_mgt/templateManager_add.html";
});

$("#srhBtn").click(function(){
	var name = $("#name").val();
	var srhData = {};
	if(name != ""){
		 srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"name" : name
		};
	}else{
		 srhData = {
					"pageNo" : "1",
					"pageSize" : "10",
				};
	}
	tableFun("/basedata_mgt/templateManager/getTemplateList", srhData);
	myPage();
});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			if (data.resCode == 0) {
				var _table = $('#table'), tableBodyHtml = '';
				if(data.data.total > 10){
					_pages = data.data.total / 10 + 1;
				}else{
					_pages = 1;
				}
				$.each(data.data.list, function(k, v) {
					// 获取数据
					var id=v.id,name=v.name,type=v.type,eventCode=v.eventCode,createTime=v.createTime;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + name + '</td>';
					tableBodyHtml += '<td>' + type + '</td>';
					tableBodyHtml += '<td>' + eventCode	 + '</td>';
					tableBodyHtml += '<td>' + createTime + '</td>';
					tableBodyHtml += '<td><a onclick="edit(\''+id+'\')">编辑</a>&nbsp;|<a onclick="del(\''+id+'\')">删除</a></td>';
					tableBodyHtml += '</tr>';
				});
				$("#tcdPageCode").show();
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="7" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPageCode").hide();
			}
		},
		async : false
	});
};

var myPage = function() {
	var name = $("#name").val();
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {};
			if(name != ""){
				 srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"name" : name
				};
			}else{
				 srhData = {
							"pageNo" : p,
							"pageSize" : "10",
						};
			}
			// 点击分页事件
			tableFun("/basedata_mgt/templateManager/getTemplateList", srhData);
		}
	});
};

function edit(id){
	location.href = "/basedata_mgt/templateManager_add.html?id="+id;
};
function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/basedata_mgt/templateManager/delTemplate",
				data : {'id':id},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('删除成功',function(){
			        		window.location.reload();
			        	});
					} else {
						bootbox.alert("操作失败");
					}
					;
				}
			});
		} else {
			return;
		}
	});
};