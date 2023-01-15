function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};

var userId = getUrlParam("userId");
var requestId = getUrlParam("requestId");
var type1 = getUrlParam("type1");

$(function () {

	var srhData = {
			"userId":userId
		};
	tableFun("/signet/moulage_record/getUserInfo", srhData);
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#moulageEditForm');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
	
  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
	
	if(type1 == "check"){
		$("#imageXml").attr("disabled","disabled");
		$("#editSubmit").attr("style","display:none");
		$("#addSubmit").attr("style","display:none");
	}
  //取得印模信息,回显图片
	$.ajax({
		type : "POST",
		url : "/signet/moulage_record/getSealImageInfo",
		data:{
			"userId":userId
		},
		success : function(data) {
			if(data.resCode == 0){
				if ( null != data.data.id && "" != data.data.id) {
					//回显
					var _data = data.data,
					d_id = _data.id;
					$("#userId").val(_data.userId);
					$("#requestId").val(_data.requestId);
					$("#preRequestId").val(_data.requestId);
					$("#userType").val(_data.userType);
					$("#userRole").val(_data.userRole);
					$("#userName").val(_data.userName);
					$("#certType").val(_data.certType);
					$("#certNo").val(_data.certNo);
					$("#bankMobile").val(_data.bankMobile);
					$("#sealImageCode").val(_data.sealImageCode);
					$("#imageXml").val(_data.imageXml);
					$("#businessCode").val(_data.businessCode);
					$("#operatorCode").val(_data.operatorCode);
					$("#channelCode").val(_data.channelCode);
					$("#sequenceId").val(_data.sequenceId);
					$("#savePath").val(_data.savePath);
					
					_loadImages(_data);
					if("SUCCESS" == _data.transStatus ){
						$("#addSubmit").attr("style","display:none");
					}else{
						$("#addSubmit").attr("style","display:none");
						$("#editSubmit").attr("style","display:none");
						$("#imageXml").attr("disabled","disabled");
					}
					
					if("PERSON" == $("#userType").val()){
						$("#invalidateSubmit").attr("style","display:none");
					}
					var userName1 = $("#userName").val();
					var userName2 = $("#userName2").val();
					var certNo1 = $("#certNo").val();
					var certNo2 = $("#certNo2").val();
					
					if(!(userName1 != userName2 || certNo1 != certNo2)){
						$("#invalidateSubmit").attr("style","display:none");
					}
			}else{
				bootbox.alert("印模图片信息不存在");
				var _data = data.data;
				$("#userId").val(_data.userId);
				$("#userType").val(_data.userType);
				$("#userName").val(_data.userName);
				$("#certNo").val(_data.certNo);
				$("#bankMobile").val(_data.bankMobile);
				
				$("#imageXml").attr("disabled","disabled");
				$("#editSubmit").attr("style","display:none");
				$("#invalidateSubmit").attr("style","display:none");
			}
			}else{
				bootbox.alert(data.msg);
			}
		},error : function(){
			bootbox.alert("取得印模信息异常");
    		$("#editSubmit").attr("style","display:none");
    		$("#addSubmit").attr("style","display:none");
    		$("#invalidateSubmit").attr("style","display:none");
        },
		async : false
	});
  
	//生成印模图片
	$("#addSubmit").click(function() {
		if (!$("#moulageEditForm").validationEngine('validate')) {
			return false;
		}
		$.ajax({
			type : "POST",
			url : "/signet/moulage_record/sealImageGenerate",
			data : $("#moulageEditForm").serialize(),
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("图片生成成功", function(){
							window.location.reload();
						});
					}else{
						bootbox.alert(data);
					}
				}
			},
			async : false
		});
	});
	
	//重新生成印模图片
	$("#editSubmit").click(function() {
		  if (!$("#moulageEditForm").validationEngine('validate')) {
			    return false;
			  }
			$.ajax({
				type : "POST",
				url : "/signet/moulage_record/sealImageUpdate",
				data : $("#moulageEditForm").serialize(),
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							bootbox.alert("图片更新成功", function(){
								window.location.reload();
							});
						}else{
							bootbox.alert(data.msg);
						}
					}
				},
				async : false
			});
	});
	
	//使印模失效
	$("#invalidateSubmit").click(function() {
		bootbox.confirm("企业信息已变更，印模失效?", function(result){
		if(result){
			var requestId = $("#requestId").val();
			$.ajax({
				type : "POST",
				url : "/signet/moulage_record/invalidateMoulage",
				data : {
					"userId":userId,
					"requestId":requestId
				},
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							bootbox.alert("印模已失效", function(){
								window.location.reload();
							});
						}else{
							bootbox.alert(data.msg);
						}
					}
				},
				async : false
			});
		   }
		});
	});

});

//回显图片
function _loadImages(data){
    if (typeof(data) != "undefined"){
		if(null != data.savePath && ''!= data.savePath){
			var moulagePicturefileUrl = domainUrl + data.savePath;
			var _cdImgMoulagePicture = $("#moulagePictureShow");
			_cdImgMoulagePicture.attr("href",moulagePicturefileUrl);
			_cdImgMoulagePicture.find("img").attr("src",moulagePicturefileUrl);
			_cdImgMoulagePicture.find("input").val(data.savePath);
		}
    }
}
//查看最新个人/企业信息
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#userName2").val(data.data.userName);
				$("#certNo2").val(data.data.certNo);
				$("#userType2").val(data.data.userType);
			}
		},
		async : false
	});
}