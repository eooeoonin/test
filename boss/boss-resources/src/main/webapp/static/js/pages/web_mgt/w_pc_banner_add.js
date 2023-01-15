$(function () {


	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 = $('#form');
	_modalFm1.validationEngine('attach', {
		maxErrorsPerField: 1,
		autoHidePrompt: true,
		autoHideDelay: 2000
	});
	//URL参数
	var id = Request.id;
	var tdUrl = "/web_mgt/w_app_banner_list/banner/one";
	var tbData = {
		"id": id
	};


	//2017-03-08添加　　链接自动添加活动号
//	$("#link").blur(function () {
//		var actVal = $("#extra1").val(); //获取活动号
//		var lnkVal = $(this).val(); //获取链接
//		if (lnkVal.indexOf("markets") > 0) {  //链接中存在markets参数
//			if (actVal) {  //判断活动号是否存在
//				if (lnkVal.indexOf("mCode") < 0) {  //链接中不存在mCode参数
//					$(this).val(lnkVal + "?mCode=" + actVal);
//				}
//			} else {
//				$(this).val("");
//				alert("请先填写活动号！");
//			}
//		}
//	});


//	var link = $("#linkTr"),
//		pageId = $("#pageId");
//	link.show();
//	pageId.hide();
//	$("input[name='button']").click(function () {
//		var radVal = $("input[name='button']:checked").val();
//		if (radVal == 1) {
//			link.show();
//			pageId.hide();
//
//		} else {
//			link.hide();
//			pageId.show();
//		}
//	});
	tableFun(tdUrl, tbData);

});

//$(function(){
//	var ss = document.getElementById("file").src;
//	alert(ss);
//});



//添加
function submitBannerInfo() {
	if (!_modalFm1.validationEngine('validate')) {
		return false;
	} else if (document.getElementById("fileName").value == ''){
		alert("banner图片不能为空！")
		return false;
	} else {
//		var ora = document.getElementsByName("pcBannerLocation");
//		var type = '12';
//		if (ora[0].checked == true) {
//			type = '12';
////			bannerData.innerPage="";
//		} else {
//			type = '13';
////			bannerData.link="";
//		}

		titile = document.getElementById("titile").value;
		type = $('input[type="radio"][name="pcBannerLocation"]:checked').val();
		picture = document.getElementById("fileName").value;
		body = document.getElementById("body").value;
//		wxBody = document.getElementById("wxBody").value;
//		wxTitle = document.getElementById("wxTitle").value;
//		wxPicture = document.getElementById("fileWXName").value;
//		innerPage = document.getElementById("innerPage").value;
//		link = document.getElementById("link").value;
//		isShow = $('input[type="radio"][name="isShow"]:checked').val();
//		extra1 = document.getElementById("extra1").value;

		$.ajax({
			type: "POST",
			url: "/web_mgt/w_pc_banner_list/banner/add",  //修改
			dataType: "json",
			data: {
				"titile": titile,
				"picture": picture,
				"body": body,
//				"wxBody": wxBody,
//				"wxTitle": wxTitle,
//				"wxPicture": wxPicture,
//				"innerPage": innerPage,
//				"link": link,
				"type":type
//				"extra1": extra1
//				"isShow": isShow
			},

			success: function (data) {
				location = "../web_mgt/w_pc_banner_list.html";
//				if (data.resultCode == "1") {
//					alert("修改成功");
//				} else {
//					alert("修改失败");
//				}
			}, error: function () {
				alert("修改失败");
				location = "../web_mgt/w_pc_banner_list.html";
			}

		});
//		$("div").attr("disabled","false");
	}
}

//上传banner
function UploadFile() {

	var formData = new FormData();

	formData.append('file', $('#file')[0].files[0]);
	formData.append('bizeCode', "pic");
	$("#save").attr("disabled", "true");

	var fileVal = $("#file").val();
	if (!fileVal) {
		alert("请选择banner图");
		return false;
	}

	$.ajax({
		type: "POST",
		url: "/boss/imageUpload",  //上传
		data: formData,
		dataType: "json",
		cache: false,
		processData: false,
		contentType: false,
		success: function (data) {

			var _data = eval('(' + data + ')');
//			alert(_data.fileName);
			$("#fileName").val(_data.fileName);
			$("#picture").attr("src", domainUrl + pic + _data.fileName);//及时换

			alert("上传成功");
			$("#save").removeAttr("disabled");
			//document.getElementById("fileName").value = _data.fileName;
		}

	});

}

//上传微信缩略图
//function UploadWXFile() {
//
//	var formData = new FormData();
//
//	formData.append('file', $('#wxfile')[0].files[0]);
//	formData.append('bizeCode', "pic");
//	$("#save").attr("disabled", "true");
//	var wxfileVal = $("#wxfile").val();
//	if (!wxfileVal) {
//		alert("请选择微信图");
//		return false;
//	}
//
//	if ($('#wxfile')[0].files[0].size > imageUploadThumbSize * 1024) {
//		alert(imageUploadNoticeMsg);
//		return false;
//	}
//
//	$.ajax({
//		type: "POST",
//		url: "/boss/imageUpload",  //上传
//		data: formData,
//		dataType: "json",
//		cache: false,
//		processData: false,
//		contentType: false,
//		success: function (data) {
//
//			var _data = eval('(' + data + ')');
////			alert(_data.fileName);
//			$("#fileWXName").val(_data.fileWXName);
//			$("#picture1").attr("src", domainUrl + pic + _data.fileName);//及时换
//
//			alert("上传成功");
//			$("#save").removeAttr("disabled");
//			document.getElementById("fileWXName").value = _data.fileName;
//		}
//
//	});
//
//}

