

//修改
function submitedit() {
	
	var formData=$("#form").serialize();
	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/message_system/message/edit",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/message_system.html";
		},error: function(){
			alert("修改成功");
			location = "../basedata_mgt/message_system.html";
		}
			
	});
}


