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
	
$(function () {
	
	_modalFm1 =  $('#addForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
	var ue = UE.getEditor('content');
	
	  $(document).on('ready', function () {
			$.ajax({
				type : "POST",
				url : "/crowdfunding/protocol/getById",
				data : {"id" : Request.protocolId},
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							var _data = data.data;
							$("#id").val(_data.id);
							$("#name").val(_data.name);
							$("#type").val(_data.type);
							$("#content").val(_data.template);
							
						}else{
							bootbox.alert("取得协议数据异常");
						}
					}
				},
				async : false
			});
	  	});
});


$("#save").click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
  
	var ue = UE.getEditor('content');
	var bodyContent = ue.getContentLength(true);
	if(bodyContent < 1){
		alert("内容不能为空");
		return false;
	}
	if(bodyContent > 5000){
		alert("内容长度不能超过5000");
		return false;
	}
	
	$.ajax({
		type : "POST",
		url : "/crowdfunding/protocol/protocolEdit",
		data : $("#addForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "/crowdfunding/protocol.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
});
