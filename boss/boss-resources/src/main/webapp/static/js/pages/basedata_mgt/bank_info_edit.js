
/***
 *** 获取URL参数
 ***/
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
	//URL参数
	var id = Request.id;
	var tdUrl = "/basedata_mgt/b_bank_info_list/BankInfo/one";
	var tbData = {
		"id":id
	};
	tableFun(tdUrl,tbData);
	
});


//编辑
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
		//把商品数据,显示在也页面上
			 var _data = eval('(' + data + ')');
			document.getElementById("id").value = _data.id;

			document.getElementById("bankcode").value = _data.bankCode;
			
			document.getElementById("bankname").value = _data.bankName;

			document.getElementById("available").value = _data.available;
			
			
			
			
		}
	});
	
}
	
//修改
function submitBankInfoTwo() {
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	  }

	$.ajax({
		type : "POST",
		url : "/basedata_mgt/b_bank_info_list/BankInfo/edit",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/b_bank_info_list.html";
		},error: function(){
			alert("修改成功");
			location = "../basedata_mgt/b_bank_info_list.html";
		}
			
	});
}


//上传微信缩略图
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
			
			var _data = eval('(' + data + ')');
//			alert(_data.fileName);
			$("#fileName").val(_data.fileName);
			$("#picture").attr("src",domainUrl+pic+_data.fileName);//及时换
			
			alert("上传成功");
			$("#save").removeAttr("disabled");
			//document.getElementById("fileName").value = _data.fileName;
		}

	});
	
}
	



//修改保存
	function submitBannerInfoTwo(){
		
		
    var bannerData=$("#form").serialize();
		$.ajax({
			type : "POST",
			url : "/web_mgt/w_app_banner_list/banner/update",  //修改
			dataType : "json",
			data :bannerData,

			success : function(data) {
				
		      alert("修改成功");
		      location = "../web_mgt/w_app_banner_list.html";
			},error: function(){	
				alert("修改");
				location = "../web_mgt/w_app_banner_list.html";
			}
			
		});
//		$("div").attr("disabled","false"); 
	}








