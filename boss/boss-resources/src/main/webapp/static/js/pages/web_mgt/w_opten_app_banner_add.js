/***
 *** 获取URL参数
 ***/
var _isShow;
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}  
var Request = {};
Request = GetRequest();


$(function() {
	
	/**
	 * 模态窗口非空/数字校验
	 */
	
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	
	
	
	
	var link = $("#linkTr"),
	pageId = $("#pageId");
	link.show();
	pageId.hide();
	$("input[name='button']").click(function(){
		var radVal = $("input[name='button']:checked").val();
		if(radVal == 1){
			link.show();
			pageId.hide();
		}else{
			link.hide();
			pageId.show();
		}
	});
	
});





//上传微信缩略图
function UploadWXFile() {
	
	var formData = new FormData();
	
	formData.append('file', $('#wxfile')[0].files[0]);
	formData.append('bizeCode',"pic");
	$("#save").attr("disabled","true");
	var wxfileVal = $("#wxfile").val();
	if(!wxfileVal){
		alert("请选择微信图");
		return false;
	}
	if($('#wxfile')[0].files[0].size > imageUploadThumbSize * 1024){
		alert(imageUploadNoticeMsg);
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
	var fileVal = $("#file").val();
	if(!fileVal){
		alert("请选择开屏广告图");
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
		
		
		
		if(Number(document.getElementById("extra1").value)>Number(document.getElementById("extra2").value)){
			alert("最大概率不能小于最小概率");
			return false;
		}
		if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }else{
		var ora=document.getElementsByName("button");
		if(ora[0].checked==true){
			document.getElementById("innerPage").value="";
//			bannerData.innerPage="";
		}else{
			document.getElementById("link").value="";
//			bannerData.link="";
		}
		
		var bannerData=$("#form").serialize();
		
		  
		$.ajax({
			type : "POST",
			url : "/web_mgt/w_other_opten_app_banner_list/banner/add",  //保存
			dataType : "json",
			data :bannerData,

			success : function(data) {
				
		      alert("保存成功");
		      location = "../web_mgt/w_other_opten_app_banner_list.html";
			},error: function(){	
				alert("保存失败");
				location = "../web_mgt/w_other_opten_app_banner_list.html";
			}
			
		});
		  }
//		$("div").attr("disabled","false"); 
	}








