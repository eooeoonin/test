var _pages;
var channelId;
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
	_modalFm2 =  $('#modal_form1');
	_modalFm2.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	_modalFm3 =  $('#modal_form2');
	_modalFm3.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	$.ajax({
		type : "POST",
		url : "/activity/channelManage/getAllChannelType",
		data : {},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$.each(data.data, function(k, v) {
					$("#ctype").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
					$("#ectype").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
				});
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
	};
	tableFun("/activity/channelManage/getAll", srhData);
	myPage();
});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			var a = data.data.total;
			if (a != 0) {
				var _table = $('#table'), tableBodyHtml = '';
				_pages = data.data.pages;
				var _data = data.data;
				$.each(_data.list, function(k, v) {
					// 获取数据
					var id=v.id,code=v.code,name=v.name,channelType=v.channelType,modifyTime=v.modifyTime,editedBy=v.editedBy;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + id + '</td>';
					tableBodyHtml += '<td>' + code + '</td>';
					tableBodyHtml += '<td>' + name + '</td>';
					tableBodyHtml += '<td>' + channelType + '</td>';
					tableBodyHtml += '<td>' + modifyTime + '</td>';
					tableBodyHtml += '<td>' + editedBy + '</td>';
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
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var srhData = {};
			if($("#name").val() != "" && undefined != $("#name").val() && null != $("#name").val()){
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"name":$("#name").val()
				};
			}else{
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
				};
			}
			tableFun("/activity/channelManage/getAll", srhData);
		}
	});
};
$("#addChannelType").click(function() {
	$('#ChannelTypeModal').modal('show');
});
$("#addChannel").click(function() {
	$('#ChannelModal').modal('show');
});

$("#actBtn").click(function() {
	if (!$("#modal_form").validationEngine('validate')) {
		return false;
	};
	var s = $('#channelType').val();
	$.ajax({
		type : "POST",
		url : "/activity/channelManage/addChannelType",
		data : {'name':s},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#channelType")[0].value = "";
				$('#ChannelTypeModal').modal('hide');
				bootbox.alert('保存成功',function(){
	        		window.location.reload();
	        	});
			}else{
				bootbox.alert("操作失败");
			}
		},
		async : false
	});
});
$("#subTran").click(function() {
	if (!$("#modal_form1").validationEngine('validate')) {
		return false;
	};	
	var channelTypeId = $("#ctype option:selected")[0].value;
	var channelType = $("#ctype option:selected")[0].text;
	var channelName = $("#channelName").val();
	var channelCode = $("#channelCode").val();
	$.ajax({
		type : "POST",
		url : "/activity/channelManage/addChannel",
		data : {'name':channelName,'code':channelCode,'channelTypeId':channelTypeId,'channelType':channelType},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#channelName")[0].value = "";
				$("#channelCode")[0].value = "";
				$('#ChannelModal').modal('hide');
				bootbox.alert('保存成功',function(){
	        		window.location.reload();
	        	});
			}else{
				bootbox.alert("操作失败");
			}
		},
		async : false
	});
});
$("#srhBtn").click(function() {
	var name = $("#name").val();
	var srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"name":name
		};
		tableFun("/activity/channelManage/getAll", srhData);
		myPage();
});
$("#editBtn").click(function() {
	var channelTypeId = $("#ectype option:selected")[0].value;
	var channelType = $("#ectype option:selected")[0].text;
	var channelName = $("#echannelName").val();
	var channelCode = $("#echannelCode").val();
	var srhData = {
			'id':channelId,'name':channelName,'code':channelCode,'channelTypeId':channelTypeId,'channelType':channelType
		};
		$.ajax({
			type : "POST",
			url : "/activity/channelManage/editChannel",
			data : srhData,
			dataType : "json",
			success : function(data) {
				if(data.resCode == 0){
					$('#editChannelModal').modal('hide');
					bootbox.alert('修改成功',function(){
		        		window.location.reload();
		        	});
				}else{
					bootbox.alert("数据加载异常");
				}
			},
			async : false
		});
});
function edit(id){
	channelId = id;
	$.ajax({
		type : "POST",
		url : "/activity/channelManage/getById",
		data : {'id':id},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#echannelName")[0].value = data.data.name;
				$("#echannelCode")[0].value = data.data.code;
				$("#ectype").val(data.data.channelTypeId);
				$('#editChannelModal').modal('show');
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
};
function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/activity/channelManage/delChannel",
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