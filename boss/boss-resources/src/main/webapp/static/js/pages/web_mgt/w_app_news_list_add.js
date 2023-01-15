$(function() {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000,
		  promptPosition: 'topLeft'
		});
	var ue = UE.getEditor('content');
	initFileInput1("picture", "/boss/imageUpload?bizeCode=pic","1",false);
	initFileInput2("wxPicture", "/boss/imageUpload?bizeCode=pic","1",false);

	$(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
});


var initFileInput1 = function(ctrlName, uploadUrl,fmax,fpreview) {
	  var control = $('#' + ctrlName);
	  $(document).on('ready', function () {
		  control.fileinput({
		      language: 'zh',
		      allowedFileExtensions: ['jpg', 'png', 'gif'],
		      uploadUrl: uploadUrl,
		      maxFileCount: fmax,
		      enctype: 'multipart/form-data',
		      showUpload: true,
		      showRemove : false,
		      showCaption: true,
		      showPreview: fpreview,
		      uploadAsync: true,
		      dropZoneEnabled: fpreview,
		      initialPreviewShowDelete:false,
		      initialPreview:[],
		      initialPreviewConfig:[],
		      maxFileSize: 0,
		      elErrorContainer: '#errorBlock'
		  });
	 });
};
	

var initFileInput2 = function(ctrlName, uploadUrl,fmax,fpreview) {
	  var control = $('#' + ctrlName);
	  $(document).on('ready', function () {
		  control.fileinput({
		      language: 'zh',
		      allowedFileExtensions: ['jpg', 'png', 'gif'],
		      uploadUrl: uploadUrl,
		      maxFileCount: fmax,
		      enctype: 'multipart/form-data',
		      showUpload: true,
		      showRemove : false,
		      showCaption: true,
		      showPreview: fpreview,
		      uploadAsync: true,
		      dropZoneEnabled: fpreview,
		      initialPreviewShowDelete:false,
		      initialPreview:[],
		      initialPreviewConfig:[],
		      maxFileSize: imageUploadThumbSize,
		      initialCaption : imageUploadNoticeMsg,
		      msgSizeTooLarge : imageUploadNoticeMsg,
		      elErrorContainer: '#errorBlock'
		  });
	 });
};



$('#picture').on('fileuploaded', function(event, data, previewId, index) {
	    var form = data.form, files = data.files, extra = data.extra,
	    response = data.response, reader = data.reader;
	    var result = eval('(' + response + ')');
	    var fileUrl = domainUrl + pic + result.fileName;
	    var _cdImgId = $("#filethumbnail");
	    _cdImgId.attr("href",fileUrl);
	    _cdImgId.find("img").attr("src",fileUrl);
	    _cdImgId.find("input").val(result.fileName);
	    $("#fileName").val(result.fileName);
	    
});
	
$('#wxPicture').on('fileuploaded', function(event, data, previewId, index) {
	    var form = data.form, files = data.files, extra = data.extra,
	    response = data.response, reader = data.reader;
	    var result = eval('(' + response + ')');
	    var fileUrl = domainUrl + pic+ result.fileName;
	    var _cdImgId = $("#wxfilethumbnail");
	    _cdImgId.attr("href",fileUrl);
	    _cdImgId.find("img").attr("src",fileUrl);
	    _cdImgId.find("input").val(result.fileName);
	    $("#fileWXName").val(result.fileName);
	    
});

var addId;
$("#submitBtn").click(function(){
	
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
	
	var ue = UE.getEditor('content');
	var bodyContent = ue.getContent();
	if(bodyContent.length < 1){
		alert("新闻正文不能为空");
		return false;
	}
	
	if($("#businessLicenseFile").val() == ""){
		alert("新闻配图不能为空");
	return false;	  
	}
	
	if($("#taxFile").val() == ""){
		alert("微信分享缩略图不能为空");
	return false;	  
	}
	
	
	var newsData=$("#form").serialize();
	var url = "/web_mgt/w_app_news_list/addPublish?bizeCode="+html;
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data :newsData,
		success : function(data) {
			addId = data.id;
			bootbox.alert(data.result);
			if (data.result == "success") {
				bootbox.alert("新闻增加成功", function(){
					window.location.href= "/web_mgt/w_app_news_list.html";
				});
			}else{
				bootbox.alert("新闻增加失败");
			}
			
			
		}
	});
	 
});

$("#viewHtml").click(function(){
	//location.reload(true);
	window.location.href = domainUrl +html+ "/a_" + addId + ".html";;
	
});
