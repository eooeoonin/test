/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}  
var Request = {};
Request = GetRequest();


$(function() {
	init();
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#addForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	
	//URL参数
	var id = Request.id;
	$("#id").val(id);
	$("#WINXName").hide();


	
	var tdUrl = "/basedata_mgt/sendMsgTask/ById";
	var tbData = {
		"id":id
	};
	tableFun(tdUrl,tbData);
	
});


function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			console.log(data);
			if(data.resCode == 0){
				var type = data.data.result.sendType;
				$("#TAGCode").val(data.data.result.groupId);
				$("#TAGCode").val(data.data.result.groupId);
				$("#name").val(data.data.result.name);
				if("" == data.data.result.messageName || null == data.data.result.messageName){
					$("#SMSName").hide();
					$("#SMSNei").hide();
					$("#messageName").val("");
					$("#messageContent").val("");
					$("#templateName").show();
					$("#templateID").show();
					$("#templateId").val(data.data.result.templateId);
					$("#ischeck").attr("checked", 'checked');
				}else{
					$("#SMSName").show();
					$("#SMSNei").show();
					$("#templateID").hide();
					$("#templateName").hide();
					$("#messageName").val(data.data.result.messageName);
					$("#messageContent").val(data.data.result.messageContent);
				}
				if("" != data.data.result.wxTemplateId && null != data.data.result.wxTemplateId){
					$("#wxTemplateId").val(data.data.result.templateId);
					$("#WINXName").show();
					$("#ischeck").removeAttr("checked");
					$("#templateName").hide();
					$("#SMSName").hide();
					$("#templateID").hide();
					$("#SMSNei").hide();
					$("#messageName").val("");
					$("#messageContent").val("");
				}
				
				if(data.data.result.sendType=="SMS"){
					$("input:radio[value='SMS']").attr('checked','true');
				}else if(data.data.result.sendType=="WEIX"){
					$("input:radio[value='WEIX']").attr('checked','true');
				}else if(data.data.result.sendType=="PUSH"){
					$("input:radio[value='PUSH']").attr('checked','true');
				}else if(data.data.result.sendType=="EMAIL"){
					$("input:radio[value='EMAIL']").attr('checked','true');
				}else if(data.data.sendType=="PERSONAL"){
					$("input:radio[value='PERSONAL']").attr('checked','true');
				}else if(data.data.sendType=="SYSTEM"){
					$("input:radio[value='SYSTEM']").attr('checked','true');
				}else{
					bootbox.alert("数据异常,请重新登录后重试!");
				}
			}
		}
	});
}

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

$("#submitEdit").click(function(){
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#addForm").serialize();
	if ($("input[type='checkbox']").is(':checked')) {
		if($("#templateId").val() == null || $("#templateId").val() == ""){
			bootbox.alert('请选择模板');
			return false;
		}
	}
	var data = serializeFormat($("#addForm").serialize());
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/sendMsgTask/updateTask",
		data : data,
		scriptCharset: 'utf-8',
		dataType : "json",
		success : function(data) {
			bootbox.alert("修改成功");
	      location = "../basedata_mgt/sendMsgTask.html";
		},
		async : false,
		error : function(da){
			console.log(da);
		}
			
	});
	  }
})



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










