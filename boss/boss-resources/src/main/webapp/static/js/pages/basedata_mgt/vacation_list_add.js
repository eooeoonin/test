
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
	
	  laydate({elem: "#date", format: "YYYY/MM/DD "});

	
	_modalFm1 =  $('#departmentForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});

	if( _getParamsObject().type=="edit"){
		
		var departmentId =  _getParamsObject().departmentId;
		$.ajax({
			type : "POST",
			url : "/basedata_mgt/vacation_list/byId",
			data : {id : departmentId},
			success : function(data) {
				if (data != null && data != "") {
				
						var _data = data;
						$("#date").val(_data.date.substring(0,10));
						
						$("#isHodiday").val(_data.isHodiday);
						$("#remark").val(_data.remark);
						$("#id").val(_data.id);
						$("#date").attr("disabled","true");
					
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
		url : "/basedata_mgt/vacation_list/add",
		data : $("#departmentForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if(data.resultCode==1){
					bootbox.alert("操作成功", function(){
						location = "vacation_list.html";
					});
			}else{
				bootbox.alert(data.resultCodeMsg);
			}
				
			}
		},
		async : false
	});
});
