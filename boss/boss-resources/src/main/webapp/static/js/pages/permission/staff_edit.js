
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
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#staffForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	  $(".i-checks").iCheck({
	    checkboxClass: "icheckbox_square-green",
	    radioClass: "iradio_square-green"
	  });
	  laydate({elem: "#date1", format: "YYYY/MM/DD"});
	
	$("input[type='text']").val("");
	//部门列表下拉框
	_loadDepartmentSelect();
	//员工角色下拉框
	_loadRolesSelect();
	if( _getParamsObject().type=="edit"){
		
		var staffId =  _getParamsObject().staffId;
		$.ajax({
			type : "GET",
			url : "/permission/staff/get/" + staffId,
			data : {id : staffId},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						var _data = data.data;
						$("#staffName").val(_data.staffName);
//						$("#password").hide();
						var sex= _data.sex; //姓别，0男 1女
						$("input:radio").parent().removeClass("checked");
						$("input[type='radio'][name='sex'][value="+sex+"]").attr("checked", "checked").parent().addClass("checked");
						$("#idCard").val(_data.idCard);
						$("#staffName").val(_data.staffName);
						$("#realName").val(_data.realName);
						$("#email").val(_data.email);
						$("#departmentId").val(_data.departmentId);
						$("#roleId").val(_data.roleId);
						$("#mobile").val(_data.mobile);
						$("#qq").val(_data.qq);
						$("#phone").val(_data.phone);
						$("#birthday").val(_data.birthday);
						$("#departmentIdSelect").val(_data.departmentId);
						$("#roleIdSelect").val(_data.roleId);
						$("#staffId").val(_data.id);
						$("#date1").val(_data.birthday);
						
					}
				}
			},
			async : false
		});
	}
	
});



$("#staffSubmit").click(function() {
	  if (!$("#staffForm").validationEngine('validate')) {
		    return false;
		  }
	  
	$.ajax({
		type : "POST",
		url : "/permission/staff/add",
		data : $("#staffForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "staff.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
});


var _loadDepartmentSelect = function(){
	
	$.ajax({
		type : "GET",
		url : "/permission/department/departments",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var str = "<option value=''>--请选择--</option>";
					for(var key = 0; key < data.data.length; key++){
						var departmentId = data.data[key].id;
						var departmentName = data.data[key].departmentName;
						str += "<option value= '"+departmentId+"'>" +departmentName+"</option>";
					}
					$("#departmentIdSelect").html(str);
				}
			}
		},
		async : false
	});
	
}

var _loadRolesSelect = function(){
	
	$.ajax({
		type : "GET",
		url : "/permission/role/roles",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var str = "<option value=''>--请选择--</option>";
					for(var key = 0; key < data.data.length; key++){
						var departmentId = data.data[key].id;
						var roleName = data.data[key].roleName;
						str += "<option value= '"+departmentId+"'>" +roleName+"</option>";
					}
					$("#roleIdSelect").html(str);
				}
			}
		},
		async : false
	});

}