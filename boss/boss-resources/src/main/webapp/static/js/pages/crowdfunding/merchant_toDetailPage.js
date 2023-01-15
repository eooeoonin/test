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
						
						var d_companyType;
						
						switch(_data.companyType){
						case "STATE_OWNED":
							d_companyType = "国有企业";
							break;
						case "PRI_OWNED":
							d_companyType = "民营企业";
							break;
						case "FOR_OWNED":
							d_companyType = "外资企业";
							break;
						case "JOIN_OWNED":
							d_companyType = "合资企业";
							break;
						case "INDIVIDUAL":
							d_companyType = "个体企业";
							break;
						case "OTHER":
							d_companyType = "其他";
							break;
						default:
							d_companyType = _data.companyType;
						    break;
						}
						
						$("#id").val(_data.id);
						$("#name").text(_data.name);
						$("#companyType").text(d_companyType);
						$("#address").text(_data.address);
						$("#agent").text(_data.agent);
						$("#scope").text(_data.scope);
						$("#bcNumber").text(_data.bcNumber);
						$("#orgNumber").text(_data.orgNumber);
						$("#textNumber").text(_data.textNumber);
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
