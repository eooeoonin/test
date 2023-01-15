var id = getUrlParam("id");
var url;

$(function () {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#addProForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
	  //普通上传
	  initFileInput("fileinput", "/boss/imageUpload?bizeCode=pic","1",false);
	//图片预览
	  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
	  /***
	   *功能说明：上传文件
	   *参数说明：ctrlName  上传文本框ID  uploadUrl 后台上传接口  fmax 最大上传个数  fpreview 是否显示预览框
	   *创建人：李波涛
	   *时间：2016-08-18
	   ***/
	  if(id != null && id != undefined && id != ""){
		  url = "/sign/config/edit";
			$.ajax({
				type : "POST",
				url : "/sign/config/getById",
				data : {'id':id},
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							$("#name").val(data.data.name);
							$("#additional").val(data.data.additional);
							$("#desc").val(data.data.desc);
							if(data.data.linkType == "link"){
								$("#r1").prop("checked",true);
								$('input:radio[name="link_type"]').change();
								$("#linkAddress1").val(data.data.linkAddress);
							}else{
								$("#r2").prop("checked",true);
								$('input:radio[name="link_type"]').change();
								$("#linkAddress2").val(data.data.linkAddress);
							}
							_loadImages(data.data);
						}else{
							bootbox.alert(data.msg);
						}
					}
				},
				async : false
			});
	}else{
		  url = "/sign/config/add";
	}
	  
	 
});
//初始化fileinput控件（第一次初始化）
var initFileInput = function(ctrlName, uploadUrl,fmax,fpreview) {
  var control = $('#' + ctrlName);
  $(document).on('ready', function () {
	  control.fileinput({
	      language: 'zh', //设置语言
	      allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
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
	    var fileUrl = domainUrl + pic +result.fileName;
	    var _cdImgId = $("#img");
	    _cdImgId.attr("href",fileUrl);
	    _cdImgId.find("img").attr("src",fileUrl);
	    _cdImgId.find("input").val(result.fileName);
	    $("#img").val(result.fileName);
	  
	});

$('input:radio[name="link_type"]').change(function(){  
    if($(this).is(":checked")){  
    	if($(this).val() == "link"){
    		$("#lian").show();
    		$("#ye").hide();
    	}else{
    		$("#lian").hide();
    		$("#ye").show();
    	}
     }
 });

$("#addSubmit").click(function(){
	  if (!$("#addProForm").validationEngine('validate')) {
		    return false;
	  }
	  var name = $("#name").val();
	  var additional = $("#additional").val();
	  var desc = $("#desc").val();
	  var img = $("#imgIn").val();
	  var linkType = $("input[name='link_type']:checked").val();
	  var linkAddress;
	  if(linkType == "link"){
		  linkAddress = $("#linkAddress1").val();
	  }else{
		  linkAddress = $("#linkAddress2").val();
	  }
	  var addData;
	  if(id != null && id != undefined && id != ""){
		  addData = {
				  	'id':id,
					'name':name,
					'additional':additional,
					'desc':desc,
					'img':img,
					'linkType':linkType,
					'linkAddress':linkAddress
			  }
	  }else{
		  addData = {
					'name':name,
					'additional':additional,
					'desc':desc,
					'img':img,
					'linkType':linkType,
					'linkAddress':linkAddress
			  }
	  }
	  
		$.ajax({
			type : "POST",
			url : url,
			data : addData,
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("操作成功", function(){
							window.location.href = "/sign/config_list.html";
						});
					}else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};

//回显图片
function _loadImages(data){
  
    if(null != data.img && ''!= data.img){
    	var fileUrl = domainUrl + pic + data.img;
        var _Img = $("#img");
        _Img.attr("href",fileUrl);
        _Img.find("img").attr("src",fileUrl);
        _Img.find("input").val(data.img);
    } 
}