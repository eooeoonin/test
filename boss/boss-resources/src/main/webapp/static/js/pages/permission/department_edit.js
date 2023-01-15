
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
	_modalFm1 =  $('#departmentForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});

	if( _getParamsObject().type=="edit"){
		var departmentId =  _getParamsObject().departmentId;
		$.ajax({
			type : "GET",
			url : "/permission/department/get/" + departmentId,
			data : {id : departmentId},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						var _data = data.data;
						$("#departmentName").val(_data.departmentName);
						$("#departmentCode").val(_data.departmentCode);
						$("#departmentType").val(_data.departmentType);
						$("#departmentId").val(_data.id);
					}
				}
			},
			async : false
		});
	}
});



$("#departmentSubmit").click(function() {
	  if (!$("#departmentForm").validationEngine('validate')) {
		    return false;
		  }
	  
	$.ajax({
		type : "POST",
		url : "/permission/department/add",
		data : $("#departmentForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "department.html";
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
