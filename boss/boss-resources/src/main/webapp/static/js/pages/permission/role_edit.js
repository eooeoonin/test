
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
	_modalFm1 =  $('#roleForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	$("input[type='text']").val("");

	//编辑角色
	if( _getParamsObject().type=="edit"){
	
	   var roleId =  _getParamsObject().roleId;
		$.ajax({
			type : "POST",
			url : "/permission/role/get/" + roleId,
			data : {id : roleId},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						$("#roleName").val(data.data.roleName);
						$("#remark").val(data.data.remark);
						$("#roleId").val(data.data.id);
						 
						//得到当前登录人的权限，并展示
						_getMenusLogined();
						 //得到编辑的目标用户的权限,并将目标用户的权限选中
						_checkedAccordRoleId();
						
					}
				}
			},
			async : false
		});
		
	}else{
		//新增
		_getMenusLogined();
		
	}
	
});



$("#roleSubmit").click(function() {
	  if (!$("#roleForm").validationEngine('validate')) {
		    return false;
		  }
	  
	$.ajax({
		type : "POST",
		url : "/permission/role/add",
		data : $("#roleForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "role.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
});


function _showPermissionMenus(menus){
	var _permissions = $("#permissions");
	var str = "";
	for(var i=0; i<menus.length; i++){
		if(menus[i].text == "首页"){
			_permissions.append("<input type='hidden' name='permissionIds' value="+menus[i].id+ ">");
		}
		str += "<ul class='clr'><li class='roles_tit'>"+menus[i].text+"</li>";
		var children = menus[i].children;
		for(var k=0; k<children.length; k++){
			str += "<li><label class=\"checkbox-inline i-checks\">";
			str += "<input type=\"checkbox\" title = " + children[k].id + " name='permissionIds' value= "+children[k].id +">" + children[k].text +"</label></li>";
		}
		str += "</ul>";
	}
	
	_permissions.append(str);
	
//	_permissions.find("li").each(function(){
//		if($(this).text() == "统计页"){
//			$(this).find("div").addClass("disabled");
////			$(this).find("input").attr("checked",true).attr("disabled",true);
//			$(this).find("input").attr("checked",true);
//		}
//	});
	
	  $(".i-checks").iCheck({
	    checkboxClass: "icheckbox_square-green"
	  });
	
	
}


var _getMenusLogined = function(){
	//新增角色时只加载该登录用户拥有的权限
	$.ajax({
		type : "POST",
		url : "/permission/role/getMenusLogineds",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					_showPermissionMenus(data.data)
					
				}
			}
		},
		async : false
	});
	
}

var _checkedAccordRoleId = function(){
	var roleId =  _getParamsObject().roleId;
	$.ajax({
		type : "GET",
		url : "/permission/role/resources/"+ roleId,
		data : {"roleId" : roleId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					$.each(data.data,function(k,v){
						var _permissions = $("#permissions");
						_permissions.find("li").each(function(){
							var _pmsIpt = $(this).find("input");
							if(_pmsIpt.attr("title") == (v.menuId)){
								$(this).find("div").addClass("checked");
								_pmsIpt.attr("checked",true);
							}
						});
					});
					
				}
			}
		},
		async : false
	});
}
