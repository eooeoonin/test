
function _getParamsObject(){
	var thisURL = document.URL;    
	var getval =thisURL.split('?')[1];
	var paramsArray = getval.split("&");
	var paramsObject={};
	for(var i=0; i< paramsArray.length; i++){
		var keyAndValue = paramsArray[i].split("=");
		paramsObject[keyAndValue[0]] = keyAndValue[1];
	}
	return paramsObject;
}


$(function() {
	_modalFm1 =  $('#groupForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});

	if( _getParamsObject().type=="edit"){
		var systemConfigId =  _getParamsObject().systemConfigId;
		$.ajax({
			type : "GET",
			url : "/user/group/" + systemConfigId,
			data : {id : systemConfigId},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						var _data = data.data;
						$("#codeKey").val(_data.codeKey);
						$("#codeValue").val(_data.codeValue);
						$("#systemConfigId").val(_data.id);
                        if(_data.available==1){
                            $("#open").attr("checked",true);
                        }else if(_data.available==0){
                            $("#close").attr("checked",true);
                        }
					}
				}else bootbox.alert(data.msg);
			},
			async : false
		});
	}
});



$("#groupSubmit").click(function() {
	  if (!$("#groupForm").validationEngine('validate')) {
		    return false;
		  }
	  
	$.ajax({
		type : "POST",
		url : "/user/group/add",
		data : $("#groupForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "group_list.html";
					});
				}
				else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
});
