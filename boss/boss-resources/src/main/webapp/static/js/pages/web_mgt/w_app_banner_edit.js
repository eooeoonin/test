/***
 *** 获取URL参数
 ***/
var data1;
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
	$("#link").blur(function () {
		var actVal = $("#extra1").val(); //获取活动号
		var lnkVal = $(this).val(); //获取链接
		if (lnkVal.indexOf("markets") > 0) {  //链接中存在markets参数
			if (actVal) {  //判断活动号是否存在
				if (lnkVal.indexOf("mCode") < 0) {  //链接中不存在mCode参数
					$(this).val(lnkVal + "?mCode=" + actVal);
				}
			} else {
				$(this).val("");
				alert("请先填写活动号！");
			}
		}
	});


	var link = $("#linkTr"),
		pageId = $("#pageId");
	link.show();
	pageId.hide();
	$("input[name='button']").click(function () {
		var radVal = $("input[name='button']:checked").val();
		if (radVal == 1) {
			link.show();
			pageId.hide();

		} else {
			link.hide();
			pageId.show();
		}
	});
	tableFun(tdUrl, tbData);

});


//编辑
function tableFun(tdUrl, tbData) {

	$.ajax({
		type: "POST",
		url: tdUrl,
		data: tbData,
		dataType: "json",
		success: function (data) {

			var _data = eval('(' + data + ')');

			data1 = _data;
//			if (_data.isShow == 1) {
//
//				document.getElementsByName("isShow")[0].checked = "checked";
//			} else {
//
//				document.getElementsByName("isShow")[1].checked = "checked";
//			}
			var imgUrl = _data.picture;
			var imgUrl1 = _data.wxPicture;
			document.getElementById("id").value = _data.id;
			document.getElementById("titile").value = _data.titile;
			document.getElementById("link").value = _data.link;

			document.getElementById("fileName").value = _data.picture;

			document.getElementById("body").value = _data.body;
			document.getElementById("wxBody").value = _data.wxBody;
			document.getElementById("wxTitle").value = _data.wxTitle;
			document.getElementById("fileWXName").value = _data.wxPicture;
			document.getElementById("innerPage").value = _data.innerPage;
			document.getElementById("extra1").value = _data.extra1;
			$("#picture").attr("src", domainUrl + pic + imgUrl);
			$("#picture1").attr("src", domainUrl + pic + imgUrl1);
			var ora = document.getElementsByName("button");
			var link = $("#linkTr"),
				pageId = $("#pageId");
			if (_data.link != "" && _data.link != null) {
				ora[0].checked = true;
				link.show();
				pageId.hide();
			} else {
				ora[1].checked = true;
				pageId.show();
				link.hide();
			}

		}
	});
}


//上传微信缩略图
function UploadWXFile() {
	var formData = new FormData();

	formData.append('file', $('#wxfile')[0].files[0]);
	formData.append('bizeCode', "pic");
	$("#save").attr("disabled", "true");
	var wxfileVal = $("#wxfile").val();
	if (!wxfileVal) {
		alert("请选择微信图");
		return false;
	}

	if ($('#wxfile')[0].files[0].size > imageUploadThumbSize * 1024) {
		alert(imageUploadNoticeMsg);
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
			$("#fileWXName").val(_data.fileWXName);
			$("#picture1").attr("src", domainUrl + pic + _data.fileName);//及时换

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


//修改保存
function submitBannerInfoTwo() {
	if (!_modalFm1.validationEngine('validate')) {
		return false;
	} else if (document.getElementById("fileName").value == ''){
		alert("banner图片不能为空！")
		return false;
	} else {
		var ora = document.getElementsByName("button");
		if (ora[0].checked == true) {
			document.getElementById("innerPage").value = "";
//			bannerData.innerPage="";
		} else {
			document.getElementById("link").value = "";
//			bannerData.link="";
		}
		id = data1.id;

		titile = document.getElementById("titile").value;
		picture = document.getElementById("fileName").value;
		body = document.getElementById("body").value;
		wxBody = document.getElementById("wxBody").value;
		wxTitle = document.getElementById("wxTitle").value;
		wxPicture = document.getElementById("fileWXName").value;
		innerPage = document.getElementById("innerPage").value;
		link = document.getElementById("link").value;
//		isShow = $('input[type="radio"][name="isShow"]:checked').val();
		extra1 = document.getElementById("extra1").value;


		$.ajax({
			type: "POST",
			url: "/web_mgt/w_app_banner_list/banner/update",  //修改
			dataType: "json",
			data: {
				"titile": titile,
				"picture": picture,
				"body": body,
				"wxBody": wxBody,
				"wxTitle": wxTitle,
				"wxPicture": wxPicture,
				"innerPage": innerPage,
				"link": link,
				"extra1": extra1,
//				"isShow": isShow,
				"id": id
			},

			success: function (data) {
				if (data.resultCode == "1") {
					alert("修改成功");
					location = "../web_mgt/w_app_banner_list.html";
				} else {
					alert("修改失败");
				}
			}, error: function () {
				alert("修改失败");
				location = "../web_mgt/w_app_banner_list.html";
			}

		});
//		$("div").attr("disabled","false");
	}
}
