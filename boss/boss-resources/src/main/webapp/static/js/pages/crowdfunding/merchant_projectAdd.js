function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  url = decodeURI(url);
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
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#addForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
 
	$("#city_2").citySelect({
		nodata:"none"
	});
	
	$("#companyNameInput").val(Request.companyName);
	$("#companyName").text(Request.companyName);
	$("#companyId").val(Request.companyId);
	
});


$("#save").click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
	var queryManager = false;
	$.ajax({
		type : "POST",
		url : "/crowdfunding/merchant/queryManager",
		data : $("#addForm").serialize(),
		success : function(data) {
			if("success"==data){
                $("#form1").submit();
            }else{
            	queryManager = true;
                alert("管理员已存在");
                return false;
            }
		},
		async : false
	});
	if(!queryManager) {
		$.ajax({
			type : "POST",
			url : "/crowdfunding/merchant/projectAdd",
			data : $("#addForm").serialize(),
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("操作成功", function(){
							window.location.href = "/crowdfunding/merchant.html";
						});
					}else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});
	}
	
});

$("#reSet").click(function(){
	_modalFm1[0].reset();
	$("#city").attr("style", "display:none");
});