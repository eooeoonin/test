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
				$("#name").val(data.data.result.name);
				if("" != data.data.tempName && null != data.data.tempName){
					$("#SName").hide();
					$("#SMSNei").hide();
					$("#templateID").show();
					$("#templateName").val(data.data.tempName);
					$("#ischeck").attr("checked", 'checked');
				}else{
					$("#SName").show();
					$("#SMSNei").show();
					$("#templateID").hide();
					$("#templateN").hide();
					$("#SMSName").val(data.data.result.messageName);
					$("#content").val(data.data.result.messageContent);
					$("#ischeck").attr("checked",false);
				}
				if("" != data.data.result.wxTemplateId && null != data.data.result.wxTemplateId){
					$("#WINXTemplate").val(data.data.result.templateId);
					$("#SName").hide();
					$("#SMSNei").hide();
					$("#WINXName").show();
					$("#templateID").hide();
					$("#templateN").hide();
				}
				
				if(data.data.result.sendType=="SMS"){
					$("input:radio[value='SMS']").attr('checked','true');
				}else if(data.data.result.sendType=="WEIX"){
					$("input:radio[value='WEIX']").attr('checked','true');
				}else if(data.data.result.sendType=="PUSH"){
					$("input:radio[value='PUSH']").attr('checked','true');
				}else if(data.data.result.sendType=="EMAIL"){
					$("input:radio[value='EMAIL']").attr('checked','true');
				}else if(data.data.result.sendType=="PERSONAL"){
					$("input:radio[value='PERSONAL']").attr('checked','true');
				}else if(data.data.result.sendType=="SYSTEM"){
					$("input:radio[value='SYSTEM']").attr('checked','true');
				}else{
					bootbox.alert("数据异常,请重新登录后重试!");
				}
			}
		}
	});
}


















