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

	$("#projectId").val(Request.projectId);
	
	$(document).on('ready', function () {
		$.ajax({
			type : "POST",
			url : "/crowdfunding/merchant/projectGetById",
			data : {"projectId" : Request.projectId},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						var _data = data.data;
						
						var province,city;
			            province=_data.proLocationInfo.privince;
			            city=_data.proLocationInfo.city+"_"+_data.proLocationInfo.localCode;
			            if(province&&city){
			                $("#city_2").citySelect({
			                    prov:province,city:city
			                });
			            }else{
			                $("#city_2").citySelect({
			                    nodata:"none"
			                });
			            }
						$("#name").val(_data.name);
						$("#companyName").val(_data.companyName);
						$("#description").val(_data.description);
						$("#localCode").val(_data.proLocationInfo.localCode);
						$("#username").val(_data.estateUser.username);
						$("#usernameHidden").val(_data.estateUser.username);
						
						$("#id").val(_data.id);
						$("#companyId").val(_data.companyId);
						$("#editflag").val(_data.editFlag);
						if("0" == _data.editFlag){
							$("#name").attr("disabled","disabled");
							$("#prov").attr("disabled", "disabled");
							$("#city").attr("disabled", "disabled");
							$("#localCode").attr("disabled", "disabled");
							$("#username").attr("disabled", "disabled");
							//$("#floatRate").removeAttr("disabled");
						}	
					}else{
						bootbox.alert("取得商户异常");
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
			url : "/crowdfunding/merchant/projectEdit",
			data : $("#addForm").serialize(),
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("操作成功", function(){
							window.location.href = "/crowdfunding/merchant_projectList.html?companyId=" + $("#companyId").val();
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
