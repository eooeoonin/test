/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */


$(function () {
	

	
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
	
  	
  $(document).on('ready', function () {
	  var Request = {};
	  Request = GetRequest();
    $("#fileinput").fileinput({
      language: 'zh', //设置语言
      uploadUrl: "/borrow/l_add_loan/upload/"+Request.borrowId+"?bizeCode=pic", //上传的地址
      allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
      uploadAsync: true,//是否异步方式提交
      enctype: 'multipart/form-data',//2进制传输数据
      showUpload: true,//是否显示提交按钮
      //minImageWidth: 50, //图片的最小宽度
      //minImageHeight: 50,//图片的最小高度
      //maxImageWidth: 1000,//图片的最大宽度
      //maxImageHeight: 1000,//图片的最大高度
      maxFileCount: 10,//上传数量
      maxFilePreviewSize: 10240//上传文件大小
    });
    
  });
  
  
  $('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
	  if( index==0){
		  alert("图片上传成功");}
  });
});

$("#loanFileBtn").click(function() {
		$("#loanFileBtn").attr("disabled","disabled"); 
		location = "../borrow/l_add_loan.html";
	});