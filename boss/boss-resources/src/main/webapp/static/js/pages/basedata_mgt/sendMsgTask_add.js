
var url;
$(function() {
	init();
	$("#WINXName").hide();
	$("#templateName").hide();
	_modalFm1 = $('#addForm');
	_modalFm1.validationEngine('attach', {
		maxErrorsPerField : 1,
		autoHidePrompt : true,
		autoHideDelay : 2000
	});

	$("#ischeck").click(function() {
		if ($("input[type='checkbox']").is(':checked')) {
			$("#SMSName").hide();
			$("#SMSNei").hide();
			$("#messageName").val("");
			$("#messageContent").val("");
			$("#templateName").show();
		}else{
			$("#SMSName").show();
			$("#SMSNei").show();
			$("#templateName").hide();
			$("#templateId").val("");
		}
	});
	$("input[name='sendTypeString']").click(function() {
		$("input[name='sendTypeString']").each(function(i) {
			if (this.checked) {
				var values = $("input[name='sendTypeString']")[i].value;
				if (values == "SMS") {
					$("#WINXName").hide();
					$("#SMSName").show();
					$("#SMSNei").show();
					$("#wxTemplateId").val("");
				} else if (values == "WEIX") {
					$("#WINXName").show();
					$("#ischeck").removeAttr("checked");
					$("#templateName").hide();
					$("#SMSName").hide();
					$("#templateID").hide();
					$("#SMSNei").hide();
					$("#messageName").val("");
					$("#messageContent").val("");
				} else if (values == "PUSH") {
					$("#WINXName").hide();
					$("#SMSName").show();
					$("#SMSNei").show();
					$("#wxTemplateId").val("");
				} else if (values == "EMAIL") {
					$("#wxTemplateId").val("");
					$("#WINXName").hide();
					$("#SMSName").show();
					$("#SMSNei").show();
				} else if (values == "PERSONAL") {
					$("#wxTemplateId").val("");
					$("#WINXName").hide();
					$("#SMSName").show();
					$("#SMSNei").show();
				} else if (values == "SYSTEM") {
					$("#WINXName").hide();
					$("#wxTemplateId").val("");
					$("#SMSName").show();
					$("#SMSNei").show();
				} else {
					bootbox.alert("数据加载失败!");
				}
			}
		})
	});

});

$("#addSubmit").click(function() {
	if ($("input[type='checkbox']").is(':checked')) {
		if($("#templateId").val() == null || $("#templateId").val() == ""){
			bootbox.alert('请选择模板');
			return false;
		}
	}
	var data = serializeFormat($("#addForm").serialize());
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/sendMsgTask/createMsgTaskcardChe",
		data : data,
		scriptCharset: 'utf-8',
		success : function(data) {
			if(data.resCode == 0){
				bootbox.alert('保存成功',function(){
					location = "../basedata_mgt/sendMsgTask.html";
	        	});
			}else{
				bootbox.alert("操作失败");
				}
			},
			async : false
		});

});
function serializeFormat(data){
	return data.split("&").filter(function(str){
		return !str.endsWith("=")
	}).join("&");
}
function init(){
	//tag
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/sendMsgTask/getAllTagData",
		data : {},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$.each(data.data, function(k, v) {
					$("#groupId").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
				});
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
	//template
	//tag
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/sendMsgTask/getAllTemplate",
		data : {},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#templateId").append("<option value=''>无</option>"); // 为Select追加一个Option(下拉项)
				$.each(data.data, function(k, v) {
					$("#templateId").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
				});
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
}