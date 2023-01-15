var id = getUrlParam("id");
var url;
$(function () {
	_modalFm1 =  $('#addForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	initEven();
	initPara();
	if(undefined != id && null != id && "" != id ){
		url = "/basedata_mgt/templateManager/editTemplate";
		$.ajax({
			type : "POST",
			url : "/basedata_mgt/templateManager/getById",
			data : {'id':id},
			dataType : "json",
			success : function(data) {
				if(data.resCode == 0){
					$("#eventCode").val(data.data.eventCode);
					$("#name").val(data.data.name);
					$("#content").val(data.data.content);
					if(data.data.type == "微信"){
						$("input:radio[value='WEIX']").attr('checked','true');
					}else if(data.data.type == "PUSH"){
						$("input:radio[value='PUSH']").attr('checked','true');
					}else if(data.data.type == "短信"){
						$("input:radio[value='SMS']").attr('checked','true');
					}else if(data.data.type == "邮件"){
						$("input:radio[value='EMAIL']").attr('checked','true');
					}else if(data.data.type == "个人消息"){
						$("input:radio[value='PER']").attr('checked','true');
					}else if(data.data.type == "系统消息"){
						$("input:radio[value='SYS']").attr('checked','true');
					}
				}else{
					bootbox.alert("数据加载异常");
				}
			},
			async : false
		});
	}else{
		url = "/basedata_mgt/templateManager/addTemplate";
	}
});
function initPara(){
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/templateManager/getAllPara",
		data : {},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				var _table = $('#paraTable'), tableBodyHtml = '';
				$.each(data.data, function(k, v) {
					var name=v.name,code=v.code;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + name + '</td>';
					tableBodyHtml += '<td>' + code + '</td>';
					tableBodyHtml += '</tr>';
				});
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			}else{
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="2" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#paraTable").find("tbody").html(html);
			}
		},
		async : false
	});
};
function initEven(){
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/templateManager/getAllEven",
		data : {},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$.each(data.data, function(k, v) {
					$("#eventCode").append("<option value='" + v.code + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
				});
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
};
$("#addSubmit").click(function() {
	if (!$("#addForm").validationEngine('validate')) {
		return false;
	};
	var formData;
	if(undefined != id && null != id && "" != id ){
		formData={"id":id,"type":$('input:radio:checked').val(),"eventCode":$("#eventCode").val(),"name":$("#name").val(),"content":$("#content").val()};
	}else{
		formData={"type":$('input:radio:checked').val(),"eventCode":$("#eventCode").val(),"name":$("#name").val(),"content":$("#content").val()};
	}
	$.ajax({
		type : "POST",
		url : url,
		data : formData,
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				bootbox.alert('操作成功',function(){
					location.href = "/basedata_mgt/templateManager_list.html";
	        	});
			}else{
				bootbox.alert("操作失败");
			}
		},
		async : false,
		error:function(da){
			console.log(da);
		}
	});
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};