var JSESSIONID = $.cookie("JSESSIONID");

$(".J_tabExit").click(function() {
	$.get("/admin/logout", function(data, state) {
		if (data.resCode == 0) {
			$.removeCookie("username", {path : '/'});
			location.href = "/login.html";
		}
	}, "json")
});


$.ajax({
	type : "POST",
	url : "/permission/role/getMenusLogined",
	data : {},
	success : function(data) {
		if (data != null && data != "") {
			if (data.resCode == 0) {
				_showMenus(data.data)
				
			}
		}
	},
	async : false
});


function _showMenus(data){
	var str = "";
	for(var i=0; i<data.length; i++){
		var children =  data[i].children;
		 	str += "<li>";
			str += "<a href='"+data[i].url+"'>";
		    str += " <i class='fa fa-tachometer'></i>";
		    str += "<span class='nav-label'>"+data[i].text+"</span>"
			if(children.length > 0){
		    str += " <span class='fa arrow'></span>";
			}
		    str += "</a>";
		if(children.length > 0){
	    str += "<ul class='nav nav-second-level'>";
		}
		for(var k=0; k<children.length; k++){
			str += "<li><a class='J_menuItem' href='"+children[k].url+"?JSESSIONID="+JSESSIONID+"'>"+children[k].text+"</a></li>"
		}
		if(children.length > 0){
		str+= "</ul>";
		}
		str+= "</li>";
	}
	
	var _menus = $("#side-menu");
	_menus.append(str);
	
}

//得到当前登录人信息
$.ajax({
	type : "POST",
	url : "/admin/getCurrentUser",
	data : {},
	success : function(data) {
		if (data != null && data != "") {
			if (data.resCode == 0) {
				$("#currentUser").html(data.data.staffName);
			}
		}
	},
	async : false
});