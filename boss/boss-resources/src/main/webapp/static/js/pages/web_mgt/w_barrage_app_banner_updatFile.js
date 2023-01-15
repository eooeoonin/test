/*//上传微信缩略图
function UploadWXFile() {
	
	var formData = new FormData();
	
	formData.append('file', $('#wxfile')[0].files[0]);
	formData.append('bizeCode',"pic");
	$("#save").attr("disabled","true");

	$.ajax({
		type : "POST",
		url : "/boss/imageUpload",  //上传
		data :formData,
		dataType : "json",
		cache: false,
		processData: false,
		contentType: false,
		success : function(data) {
			
			var _data = eval('(' + data + ')');
//			alert(_data.fileName);
			$("#fileWXName").val(_data.fileWXName);
			$("#picture1").attr("src",domainUrl+pic+_data.fileName);//及时换
			
			alert("上传成功");
			$("#save").removeAttr("disabled");
			document.getElementById("fileWXName").value = _data.fileName;
		}

	});
	
}

//上传banner
function UploadFile() {
		
	var formData = new FormData();
	
	formData.append('file', $('#file')[0].files[0]);
	formData.append('bizeCode',"pic");
	$("#save").attr("disabled","true");

	$.ajax({
		type : "POST",
		url : "/boss/imageUpload",  //上传
		data :formData,
		dataType : "json",
		cache: false,
		processData: false,
		contentType: false,
		success : function(data) {
//			alert("12132");
			var _data = eval('(' + data + ')');
			$("#fileName").val(_data.fileName);
			$("#picture").attr("src",domainUrl+pic+_data.fileName);//及时换
			
			alert("上传成功");
			$("#save").removeAttr("disabled");
			document.getElementById("fileName").value = _data.fileName;
		}

	});
	
}
	



//修改保存
	function submitBannerInfoTwo(){
		
		
    var bannerData=$("#form").serialize();
		$.ajax({
			type : "POST",
			url : "/web_mgt/w_other_barrage_app_banner_list/banner/update",  //修改
			dataType : "json",
			data :bannerData,

			success : function(data) {
				
		      alert("修改成功");
		      location = "../web_mgt/w_other_barrage_app_banner_list.html";
			},error: function(){	
				alert("修改成功");
				location = "../web_mgt/w_other_barrage_app_banner_list.html";
			}
			
		});
//		$("div").attr("disabled","false"); 
	}







*/