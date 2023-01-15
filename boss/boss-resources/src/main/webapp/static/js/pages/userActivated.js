function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};
var staffName = getUrlParam("staffName");

$(function () {
	
	$.ajax({
		type : "POST",
		url : "/permission/staff/userActivated",
		data : {
			staffName : staffName
		},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("激活成功", function(){
						window.location.href = "/login.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
	
});



