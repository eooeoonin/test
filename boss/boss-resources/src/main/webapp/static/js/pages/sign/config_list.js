var _pages = 0;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
	};
	tableFun("/sign/config/getAll", srhData);
	myPage();

});
$("#addBtn").click(function() {
	location.href = "/sign/config_add.html";
});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			if (data.resCode == 0) {
				var a = data.data.total;
				if (a != 0) {
					var _table = $('#table'), tableBodyHtml = '';
					_pages = data.data.pages;
					var _data = data.data;
					$.each(_data.list, function(k, v) {
						var name = v.name,
						desc = v.desc,
						additional = v.additional,
						modifyTime = v.modifyTime,
						status = v.status
						// 获取数据
						tableBodyHtml += '<tr>';
						tableBodyHtml += '<td>' + name + '</td>';
						tableBodyHtml += '<td>' + desc + '</td>';
						tableBodyHtml += '<td>' + additional + '</td>';
						tableBodyHtml += '<td>' + modifyTime + '</td>';
						if(status == 0){
							tableBodyHtml += '<td>下线</td>';
							tableBodyHtml += '<td><a onclick="edit(\'' + v.id + '\')">编辑</a>&nbsp;|&nbsp;<a onclick="del(\'' + v.id + '\')">删除</a>&nbsp;|&nbsp;<a onclick="upLine(\'' + v.id + '\')">上线</a></td>';
						}else{
							tableBodyHtml += '<td>上线</td>';
							tableBodyHtml += '<td><a onclick="edit(\'' + v.id + '\')">编辑</a>&nbsp;|&nbsp;<a onclick="del(\'' + v.id + '\')">删除</a>&nbsp;|&nbsp;<a onclick="downLine(\'' + v.id + '\')">下线</a></td>';
						}
					});
					$("#tcdPageCode").show();
					_table.find("tbody").html(tableBodyHtml);
					replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="6" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPageCode").hide();
			}
			}else{
				bootbox.alert("查询异常");
			}
		},
		async : false
	});
};

var myPage = function() {
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var srhData = {};
			if ($("#additional").val() != "" && undefined != $("#additional").val() && null != $("#additional").val()) {
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"additional" : $("#additional").val()
				};
			} else {
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
				};
			}
			tableFun("/sign/config/getAll", srhData);
		}
	});
};

$("#srhBtn").click(function() {
	var srhData;
	if ($("#additional").val() != "" && undefined != $("#additional").val() && null != $("#additional").val()) {
		srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"additional" : $("#additional").val()
		};
	} else {
		srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
		};
	}
	tableFun("/sign/config/getAll", srhData);
	myPage();

});
function edit(id) {
	location.href = "/sign/config_add.html?id=" + id;
}
function upLine(id) {
	bootbox.confirm("你确定要上线吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/sign/config/upLine",
				data : {
					'id' : id,
					'status' : 1
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('成功', function() {
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
}
function downLine(id) {
	bootbox.confirm("你确定要下线吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/sign/config/downLine",
				data : {
					'id' : id,
					'status' : 0
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('成功', function() {
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
}
function del(id) {
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/sign/config/del",
				data : {
					'id' : id
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('删除成功', function() {
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
}
