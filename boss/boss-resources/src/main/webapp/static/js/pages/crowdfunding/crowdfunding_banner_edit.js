
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


var data1;
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
	var tdUrl = "/crowdfunding/crowdfunding_banner_list/banner/one";
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
			data1=data;
			var imgUrl = data.uploadFile.path;
			document.getElementById("id").value = data.id;
			document.getElementById("name").value =data.name;
			document.getElementById("url").value = data.url;
			document.getElementById("fileName").value = data.uploadFile.path;
			document.getElementById("homeShow").value = data.homeShow; 
			document.getElementById("innerGo").value = data.innerGo;  
			document.getElementById("itemId").value = data.itemId;  
			document.getElementById("orders").value = data.orders;
			$("#picture").attr("src",domainUrl+crowdImages+imgUrl);
			
		
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
	



//修改保存
	function submitBannerInfoTwo(){
		if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }else{
	
		id= data1.id;
		var bannerData=$("#form").serialize();

    
		$.ajax({
			type : "POST",
			url : "/crowdfunding/crowdfunding_banner_list/banner/update",  //修改
			dataType : "json",
			data :bannerData,

			success : function(data) {
				
		      alert("修改成功");
		      location = "../crowdfunding/crowdfunding_banner_list.html";
				
			},error: function(){	
				alert("修改失败");
				location = "../crowdfunding/crowdfunding_banner_list.html";
			}
			
		});

		  }
	}
