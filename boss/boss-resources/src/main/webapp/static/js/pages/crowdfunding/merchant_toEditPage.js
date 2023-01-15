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

$(function () {
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#editForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
 
  //普通上传
  initFileInput("fileinput", "/boss/imageUpload?bizeCode=crowdImages","1",false);
  initFileInput("fileinput2", "/boss/imageUpload?bizeCode=crowdImages","1",false);
  initFileInput("fileinput3", "/boss/imageUpload?bizeCode=crowdImages","1",false);
  initFileInput("fileinput4", "/boss/imageUpload?bizeCode=crowdImages","1",false);


  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
  
  $(document).on('ready', function () {
		$.ajax({
			type : "POST",
			url : "/crowdfunding/merchant/getById",
			data : {"companyId" : Request.companyId},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						var _data = data.data;
						$("#id").val(_data.id);
						$("#name").val(_data.name);
						$("#companyType").val(_data.companyType);
						$("#address").val(_data.address);
						$("#agent").val(_data.agent);
						$("#scope").val(_data.scope);
						$("#bcNumber").val(_data.bcNumber);
						$("#orgNumber").val(_data.orgNumber);
						$("#textNumber").val(_data.textNumber);
						_loadImages(_data);
					}else{
						bootbox.alert("取得商户异常");
					}
				}
			},
			async : false
		});
  	});

});

var initFileInput = function(ctrlName, uploadUrl,fmax,fpreview) {
  var control = $('#' + ctrlName);
  $(document).on('ready', function () {
	  control.fileinput({
	      language: 'zh', //设置语言
	      allowedFileExtensions: ['jpg', 'png', 'gif','jpeg'],//接收的文件后缀
	      uploadUrl: uploadUrl, //上传的地址
	      maxFileCount: fmax,//表示允许同时上传的最大文件个数
	      enctype: 'multipart/form-data',//2进制传输数据
	      showUpload: true, //是否显示上传按钮
	      showRemove : false, //显示移除按钮
	      showCaption: true,//是否显示标题
	      showPreview: fpreview, //是否显示预览框
	      uploadAsync: true, //是否异步方式提交
	      dropZoneEnabled: fpreview,//是否显示拖拽区域
	      initialPreviewShowDelete:false,
	      initialPreview:[],
	      initialPreviewConfig:[],
	      maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
	      elErrorContainer: '#errorBlock' //错误信息显示地方
	    });
  });
};

$('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + crowdImages +result.fileName;
    var _cdImgId = $("#busFile");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#busFile").val(result.fileName);
  
});

$('#fileinput2').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + crowdImages +result.fileName;
    var _cdImgId = $("#orgFile");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#orgFile").val(result.fileName);
});
$('#fileinput3').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + crowdImages +result.fileName;
    var _cdImgId = $("#taxFile");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#taxFile").val(result.fileName);
});
$('#fileinput4').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + crowdImages +result.fileName;
    var _cdImgId = $("#proFile");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#proFile").val(result.fileName);
});

//回显图片
function _loadImages(data){
	for(var i=0;i<data.files.length;i++) {
		if("BUSINESS_LICENSE" == data.files[i].type){
	    	var busFileUrl = domainUrl + crowdImages + data.files[i].path;
	        var _busFile = $("#busFile");
	        _busFile.attr("href",busFileUrl);
	        _busFile.find("img").attr("src",busFileUrl);
	        _busFile.find("input").val(data.files[i].path);
	    }
	    
	    if("ORG_NO" == data.files[i].type){
	        var orgFileUrl = domainUrl + crowdImages + data.files[i].path;
	        var _orgFile = $("#orgFile");
	        _orgFile.attr("href",orgFileUrl);
	        _orgFile.find("img").attr("src",orgFileUrl);
	        _orgFile.find("input").val(data.files[i].path);
	    }
	    
	    if("TAX_REG" == data.files[i].type){
	        var taxIdfileUrl = domainUrl + crowdImages + data.files[i].path;
	        var _cdImgIdtaxId = $("#taxFile");
	        _cdImgIdtaxId.attr("href",taxIdfileUrl);
	        _cdImgIdtaxId.find("img").attr("src",taxIdfileUrl);
	        _cdImgIdtaxId.find("input").val(data.files[i].taxFile);
	    }
	    
	    if("PROTOCOL" == data.files[i].type){
	    	var proFileUrl = domainUrl + crowdImages + data.files[i].path;
	        var _proFile = $("#proFile");
	        _proFile.attr("href",proFileUrl);
	        _proFile.find("img").attr("src",proFileUrl);
	        _proFile.find("input").val(data.files[i].proFile);
	    }
	}
    
}

$("#save").click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
  
	$.ajax({
		type : "POST",
		url : "/crowdfunding/merchant/edit",
		data : $("#editForm").serialize(),
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "/crowdfunding/merchant.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
});
