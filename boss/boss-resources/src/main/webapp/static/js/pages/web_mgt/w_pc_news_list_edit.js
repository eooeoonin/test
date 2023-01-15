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
	
	
	initFileInput1("picture", "/boss/imageUpload?bizeCode=pic","1",false);
	initFileInput2("wxPicture", "/boss/imageUpload?bizeCode=pic","1",false);
	
	var id = GetRequest().id;
	//var newsUrl = domainUrl + html+"/a_" + id + ".html";
	var tdUrl = "/web_mgt/w_pc_news_list/getNewsById?bizeCode="+html;
	var tbData = {
		"id":GetRequest().id
	};
	tableFun(tdUrl,tbData);
	
	$(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
	
	//单个时间引用方式
	laydate({elem: "#extra1", format: "YYYY-MM-DD hh:mm", istime: true});
	
	_form =  $('#form');
	_form.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
});


function GetRequest() {
	  var url = location.search;
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
	

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			document.getElementById("id").value = data.id;
			document.getElementById("titile").value = data.titile;
			document.getElementById("preview").value = data.preview;
            var ue = UE.getEditor('content');
			$("#content").val(data.content);
			document.getElementById("fileName").value = data.picture;
			document.getElementById("extra1").value = data.extra1;
			document.getElementById("extra2").value = data.extra2;
			
			if(data.picture != "" && data.picture != null) {
				var fileUrl = domainUrl + pic + data.picture;
			    var _cdImgId = $("#filethumbnail");
			    _cdImgId.attr("href",fileUrl);
			    _cdImgId.find("img").attr("src",fileUrl);
			    _cdImgId.find("input").val(data.picture);
			}
			
			if(data.wxPicture != "" && data.wxPicture != null) {
				var wxfileUrl = domainUrl + pic + data.wxPicture;
			    var _wxcdImgId = $("#wxfilethumbnail");
			    _wxcdImgId.attr("href",wxfileUrl);
			    _wxcdImgId.find("img").attr("src",wxfileUrl);
			    _wxcdImgId.find("input").val(data.wxPicture);
			}
		}
	});
}

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
	    var fileUrl = domainUrl+pic +result.fileName;
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
	    var fileUrl = domainUrl+pic +result.fileName;
	    var _cdImgId = $("#wxfilethumbnail");
	    _cdImgId.attr("href",fileUrl);
	    _cdImgId.find("img").attr("src",fileUrl);
	    _cdImgId.find("input").val(result.fileName);
	    $("#fileWXName").val(result.fileName);
	    
});

$("#submitBtn").click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
		  
		if($("#businessLicenseFile").val() == ""){
			alert("新闻配图不能为空");
		return false;	  
		}
		
		if($("#taxFile").val() == ""){
			alert("微信分享缩略图不能为空");
		return false;	  
		}
		
		var newsData=$("#form").serialize();
		var url = "/web_mgt/w_pc_news_list/publish?bizCode=" + html;
		$.ajax({
			type : "POST",
			url : url,
			dataType : "json",
			data :newsData,
			success : function(data) {
				if("success" == data.result) {
					
					alert("发布成功");
					window.location.href = "../web_mgt/w_pc_news_list.html";
				}
				else
					alert(data.result);
			      
			}
		});
	}	
});

$("#viewHtml").click(function(){
	var id = $("#id").val();
	window.location.href = domainUrl + html+"/pc_" + id + ".html";
});
