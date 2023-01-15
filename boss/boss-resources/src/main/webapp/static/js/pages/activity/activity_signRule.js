var _pages = 0;
var aid = getUrlParam("id");
var acode =  getUrlParam("code");
$(function() {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#tableForm');
	_modalFm1.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 3000
	});

	if (aid != null && aid != undefined && aid != "") {
		$.ajax({
			type : "POST",
			url : "/activity/activityManager/getActivityById",
			data : {
				'id' : aid
			},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						$("#aname").val(data.data[0].name);
						$("#astartTime").val(data.data[0].startTime);
						$("#aendTime").val(data.data[0].endTime);
					} else {
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});
	}
	tableFun("/activity/activityManager/getAllSignRule");
});

function tableFun(tdUrl) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : {"id":aid},
		success : function(data) {
			if (data.resCode == 0) {
				var _table = $('#table'), tableBodyHtml = '';
				var _data = data.data;
				$.each(_data, function(k, v) {
					// 获取数据
					var id = v.id,
					circleNo = v.circleNo,
					ruleName = v.ruleName,
					ruleRegular = v.ruleRegular,
					editedBy = v.editedBy,
					createTime = v.createTime;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td><input type="text" disabled class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="'+id+'" value="'+id+'"></td>';
					tableBodyHtml += '<td><input type="text" disabled class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="'+id+'circleNo" value="'+circleNo+'"></td>';
					tableBodyHtml += '<td><input type="text" disabled class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="'+id+'ruleName" value="'+ruleName+'"></td>';
					tableBodyHtml += '<td><input type="text" disabled class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="'+id+'ruleRegular" value="'+ruleRegular+'"></td>';
					tableBodyHtml += '<td><input type="text" disabled class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="'+id+'editedBy" value="'+editedBy+'"></td>';
					tableBodyHtml += '<td><input type="text" disabled class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="'+id+'createTime" value="'+createTime+'"></td>';
					tableBodyHtml += '<td><a onclick="edit(\''+id+'\')">编辑</a>&nbsp;|&nbsp;<a onclick="del(\''+id+'\')">删除&nbsp;|&nbsp;<a id="'+id+'save" onclick="save(\''+id+'\')" hidden>保存</a></td>';
					tableBodyHtml += '</tr>';
				});
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			}
		},
		async : false
	});
};
function edit(id){
	$("#"+id+"circleNo").attr("disabled",false);
	$("#"+id+"ruleName").attr("disabled",false);
	$("#"+id+"ruleRegular").attr("disabled",false);
	$("#"+id+"save").show();
}
function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/activity/activityManager/delSignRule",
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
function save(id){
	if (!$("#tableForm").validationEngine('validate')) {
		return false;
	};

	var circleNo = $("#"+id+"circleNo").val();
	var ruleName = $("#"+id+"ruleName").val();
	var ruleRegular = $("#"+id+"ruleRegular").val();
	var udata = {
			'id':id,
			'circleNo':circleNo,
			'ruleName':ruleName,
			'ruleRegular':ruleRegular
	};
	$.ajax({
		type : "POST",
		url : "/activity/activityManager/editSignRule",
		data : udata,
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert('操作成功',function(){
						window.location.reload();
		        	});
				} else {
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
	$("#"+id+"save").hide();
}
function addBtnClick(){
	var tableBodyHtml;
	tableBodyHtml += '<tr>';
	tableBodyHtml += '<td><input type="text" disabled class="form-control"></td>';
	tableBodyHtml += '<td><input type="text"  class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="addcircleNo"></td>';
	tableBodyHtml += '<td><input type="text"  class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="addruleName"></td>';
	tableBodyHtml += '<td><input type="text"  class="form-control validate[required]" data-errormessage-value-missing="不能为空" id="addruleRegular"></td>';
	tableBodyHtml += '<td><input type="text" disabled class="form-control"></td>';
	tableBodyHtml += '<td><input type="text" disabled class="form-control"></td>';
	tableBodyHtml += '<td><a onclick="insert()">保存</a></td>';
	tableBodyHtml += '</tr>';
	 $("#table tbody").append(tableBodyHtml);
	 $("#addBtn").attr("disabled",true);
	 
}
function insert(){
	if (!$("#tableForm").validationEngine('validate')) {
		return false;
	};
	var circleNo = $("#addcircleNo").val();
	var ruleName = $("#addruleName").val();
	var ruleRegular = $("#addruleRegular").val();
	var udata = {
			'activityCode':acode,
			'activityId':aid,
			'circleNo':circleNo,
			'ruleName':ruleName,
			'ruleRegular':ruleRegular
	};
	$.ajax({
		type : "POST",
		url : "/activity/activityManager/addSignRule",
		data : udata,
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert('操作成功',function(){
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
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};