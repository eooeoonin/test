$(function (){
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	
	

});
//添加
function submitBannerInfo() {
	
	var formData = new FormData();
	formData.append('file', $('#file')[0].files[0]);
	formData.append('name',$('#name').val());
	formData.append('url',$('#url').val());
	formData.append('homeShow',$('#homeShow').val());
	formData.append('bizeCode',"crowdImages");
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
			document.getElementById("fileName").value = _data.fileName;
			//判断是否为空
			if (!_modalFm1.validationEngine('validate')) {
			    return false;
			  }else{
			var bannerData=$("#form").serialize();
			
			  }
			
			
			
			$.ajax({
				type : "POST",
				url : "/crowdfunding/crowdfunding_banner_list/banner/add",  //添加
				dataType : "json",
				data :bannerData,
//				cache: false,
//				async:false,
				success : function(data) {
					
			      alert("添加成功");
			      location = "../crowdfunding/crowdfunding_banner_list.html";
				},error: function(){
					alert("添加失败");
				location = "../crowdfunding/crowdfunding_banner_list.html";
				}
					
			});
		}
			
	});
	
}


//上传banner
function UploadFile() {
		
	var formData = new FormData();
	
	formData.append('file', $('#file')[0].files[0]);
	formData.append('bizeCode',"crowdImages");
	$("#save").attr("disabled","true");
	
	var fileVal = $("#file").val();
	if(!fileVal){
		alert("请选择banner图");
		return false;
	}

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
			$("#fileName").val(_data.fileName);
			$("#picture").attr("src",domainUrl+crowdImages+_data.fileName);//及时换
			
			alert("上传成功");
			$("#save").removeAttr("disabled");
			//document.getElementById("fileName").value = _data.fileName;
		}

	});
	
}
