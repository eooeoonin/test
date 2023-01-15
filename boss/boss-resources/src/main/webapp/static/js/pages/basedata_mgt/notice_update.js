

//修改
function submitNoticTwo() {
	
	var formData=$("#form").serialize();

	if( document.getElementById("availableTimeAfter").value==""|| document.getElementById("availableTimeBefore").value==""){
		alert("有效时间不能为空!!!请注意!");
		return false;
	}
//	alert(document.getElementById("availableTimeAfter").value);
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/notice_list/notice/update",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/notice_list.html";
		},error: function(){
			alert("修改失败");
			location = "../basedata_mgt/notice_list.html";
		}
			
	});
}


