/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

$(function () {
	
	$.ajax({
		type : "POST",
		url : "/borrower/borroweradd/getBankInfo",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var str = "<option value=''>--请选择--</option>";
					var _list = data.data.businessObject.list;
					for(var key = 0; key < _list.length; key++){
						var bankName = _list[key].bankName;
						var bankCode = _list[key].bankCode;
						str += "<option value= '"+bankCode+"'>" +bankName+"</option>";
					}
					$("#openAccountBankSelect").html(str);
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#addBorrowerForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth()+1;
	var day = myDate.getDate();
	var hour = myDate.getHours();
	var aa = year+'-'+month+'-'+day;
	$("#date1").val(aa);
  /***
   *功能说明：复选框、单选框美化
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
    $(".i-checks").iCheck({
      checkboxClass: "icheckbox_square-green",
      radioClass: "iradio_square-green"
    });

  /***
   *功能说明：时间插件
   *参数说明：
   *创建人：李波涛
   *时间：2016-08-01
   ***/
  laydate({elem: "#date1", format: "YYYY-MM-DD"});
  
  //普通上传
  initFileInput("fileinput", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput2", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput3", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput4", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput5", "/boss/imageUpload?bizeCode=pic","1",false);
 initFileInput("fileinput6", "/boss/imageUpload?bizeCode=pic","1",false);

  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

  //选择三证合一 
  rgtShow();
  $("input[name='registerCertType']").on('ifChecked', function(event){  
	  rgtShow();  
  });
});

var rgtShow = function(){
	  var rgtCk = $("input[name='registerCertType']:checked").val(); 
	  if(rgtCk == "ONE"){
		  $(".txid").hide();
		  $(".oicd").hide();
 $("#oneNo").show();
		  $("#threeNo").hide();
	  }else{
		  $(".txid").show();
		  $(".oicd").show();
		  $("#oneNo").hide();
		  $("#threeNo").show();
	  }
}


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



$('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#legalPersonIDcard");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#legalPersonIDcard").val(result.fileName);
  
});

$('#fileinput2').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#businessLicenseNo");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#businessLicenseNo").val(result.fileName);
});
$('#fileinput3').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#taxId");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#taxId").val(result.fileName);
});
$('#fileinput4').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#organizingCode");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#organizingCode").val(result.fileName);
});
$('#fileinput5').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#bankopen");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#bankopen").val(result.fileName);
});
$('#fileinput6').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#businessLicenseNo");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#businessLicenseNo").val(result.fileName);
});
 

$("#borrowerSubmit").click(function() {
	  if (!$("#addBorrowerForm").validationEngine('validate')) {
		    return false;
		  }
	  
		$.ajax({
			type : "POST",
			url : "/borrower/borroweradd/add",
			data : $("#addBorrowerForm").serialize(),
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("操作成功", function(){
							window.location.href = "/borrower/borroweradd.html";
						});
					}else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});
});
