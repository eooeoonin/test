/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#rechargeForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
	
	

  //普通上传
  initFileInput("fileinput", "/boss/imageUpload?bizeCode=pic","1",false);
  var aa =$("#enterpriseName1").val();
  $("#recharge_companyName").val(aa);
  $('#rechargeSubmit').click(function() {
	  
	  if (!$("#rechargeForm").validationEngine('validate')) {
		    return false;
		  }
	  
	  $.ajax({
			type : "POST",
			url : "/borrower/borrowerlist/recharge",
			data : $("#rechargeForm").serialize(),
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("操作成功", function(){
							window.location.href = "/borrower/borrowerlist.html";
						});
					}else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});  
  });
});

$('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic + result.fileName;
    var _cdImgId = $("#payment_certificate");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    
});
$("#rcgLink").click(function(){
	$(".nav-tabs").find("li:nth-child(4)").addClass("active").siblings().removeClass("active");
});

/***
 *功能说明：上传文件
 *参数说明：ctrlName  上传文本框ID  uploadUrl 后台上传接口  fmax 最大上传个数  fpreview 是否显示预览框
 *创建人：李波涛
 *时间：2016-08-18
 ***/
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