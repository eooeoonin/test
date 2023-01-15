

/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  		url = decodeURIComponent(url);
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
    }
  }
  return theRequest;
}  

var Request = {};
Request = GetRequest();


$(function() {
	/**
	 * 数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	//URL参数
	var id = Request.id;
	var tdUrl = "/pay/channelConfig/seletByIdChannelConfig";
	var tbData = {
		"id":id
	};
	tableFun(tdUrl,tbData);

//编辑回显
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			$("#id1").val(data.id);
			$("#channelName").val(data.channelName);
			$("#channelCode").val(data.channelCode);
			$("#business").val(data.business);
			$("#transaction").val(data.transaction);
			$("#merchant").val(data.merchant);
			$("#needSms").val(data.needSms);
			$("#singleQuota").val(data.singleQuota);
			$("#dayQuota").val(data.dayQuota);
			$("#monthQuota").val(data.monthQuota);
			$("#quotaDesc").val(data.quotaDesc);
			$("#configFile").val(data.configFile);
			if(true == data.available){
				$("#open").attr("checked",true);
			}else if(false == data.available){
				$("#close").attr("checked",true);
			}
		}
	});
}	
});
function submitedit1(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
		  var formData=$("#form").serialize();
		  var tdUrl = "/pay/channelConfig/updateChannelConfig";
		$.ajax({    
                type: "POST",
                url:tdUrl,
                data:formData,
                dataType : "json",
                success: function(data) {
                	  alert("修改成功");
                	  location = "../pay/channelConfiglist.html";
                },error: function(data){
        			alert("修改失败");
        		}
		 });
	  }
}