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
		tableFun("/activity/template/getAlltemplate", srhData);
		myPage();
	
});

$("#addBtn").click(function(){
	templateId = "";
	$("#name")[0].value = "";
	$("#fileName")[0].value = "";
	$("#imgNum")[0].value = "";
	$('#ChannelTypeModal').modal('show');
});

$("#subTran").click(function(){
	if (!$("#modal_form").validationEngine('validate')) {
		return false;
	};	
	var type = $("#type option:selected")[0].value;
	var name = $("#name").val();
	var fileName = $("#fileName").val();
	var imgNum = $("#imgNum").val();
	var url = "";
	if(templateId != null && templateId != "" && undefined != templateId){
		url = "/activity/template/updateTemplate";
		data = {'id':templateId,'name':name,'type':type,'fileName':fileName,'imgNum':imgNum};
	}else{
		url = "/activity/template/addtemplate";
		data ={'name':name,'type':type,'fileName':fileName,'imgNum':imgNum};
	}
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#name")[0].value = "";
				$("#fileName")[0].value = "";
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
					var id=v.id,editedBy=v.editedBy,name=v.name,type=v.type,modifyTime=v.modifyTime;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + id + '</td>';
					tableBodyHtml += '<td>' + name + '</td>';
					if(type == 0){
						tableBodyHtml += '<td>礼包类</td>';
					}else if(type == 1){
						tableBodyHtml += '<td>抽奖类</td>';
					}else if(type == 2){
						tableBodyHtml += '<td>邀请减免类</td>';
					}else if(type == 3){
                        tableBodyHtml += '<td>集采类</td>';
                    }else if(type == 4){
                        tableBodyHtml += '<td>扫码活动</td>';
                    }else if(type == 5){
                        tableBodyHtml += '<td>砸蛋活动</td>';
                    }
					tableBodyHtml += '<td>' + editedBy	 + '</td>';
					tableBodyHtml += '<td>' + modifyTime + '</td>';
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
			var srhData = {
					"pageNo" : p,
					"pageSize" : "10",
				};
			tableFun("/activity/template/getAlltemplate", srhData);
		}
	});
};

function edit(id){
	templateId = id;
	$.ajax({
		type : "POST",
		url : "/activity/template/getTemplateById",
		data : {'id':id},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#name")[0].value = data.data.name;
				$("#fileName")[0].value = data.data.fileName;
				$("#type").val(data.data.type);
				$("#imgNum").val(data.data.imgNum);
				$('#ChannelTypeModal').modal('show');
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
				url : "/activity/template/delTemplate",
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