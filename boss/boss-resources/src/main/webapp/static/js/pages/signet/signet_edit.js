function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};

$(function () {
	
	var userId = getUrlParam("userId");
	var requestId = getUrlParam("requestId");
	
//		$("#imageXml").attr("disabled","disabled");
//		$("#editSubmit").attr("style","display:none");
//		$("#addSubmit").attr("style","display:none");

	
	//取得印章信息
	$.ajax({
		type : "POST",
		url : "/signet/moulage_record/getSealInfo",
		data:{
			"requestId":requestId
		},
		success : function(data) {
			if (null != data.data.id && "" != data.data.id) {
				if (data.resCode == 0) {
					//回显
					var _data = data.data,
					d_id = data.data.id;
					$("#userId").val(_data.userId);
					$("#requestId").val(_data.requestId);
					$("#preRequestId").val(_data.sealImageRequestId);
					$("#userType").val(_data.userType);
					$("#userRole").val(_data.userRole);
					$("#userName").val(_data.userName);
					$("#certType").val(_data.certType);
					$("#certNo").val(_data.certNo);
					$("#bankMobile").val(_data.bankMobile);
					$("#sealImageCode").val(_data.sealImageCode);
					$("#sealXml").val(_data.sealXml);
					$("#businessCode").val(_data.businessCode);
					$("#operatorCode").val(_data.operatorCode);
					$("#channelCode").val(_data.channelCode);
					$("#sequenceId").val(_data.sequenceId);
					$("#savePath").val(_data.savePath);
					$("#investorId").val(_data.investorId);
					$("#investId").val(_data.investId);
					$("#sealCode").val(_data.sealCode);
					$("#sealType").val(_data.sealType);
					
					
				}else{
					bootbox.alert("取得印章信息异常");
					$("#editSubmit").attr("style","display:none");
				}
			}
		},error : function(){
			bootbox.alert("取得印章信息失败");
        },
		async : false
	});
  
	
	//重新生成印章
	$("#editSubmit").click(function() {
			var preRequestId = $("#preRequestId").val();
			$.ajax({
				type : "POST",
				url : "/signet/moulage_record/sealUpdate",
				data :{
					"userId" : userId,
					"sealImageRequestId":preRequestId,
					"sealType":"PERMANENT"
				},
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							bootbox.alert("印章更新成功", function(){
								window.location.reload();
							});
						}else{
							bootbox.alert("印章已存在，请不要重复申请");
						}
					}
				},
				async : false
			});
	});

});

//印模查看
$("#checkMoulagePic").click(function(){
	var userId = getUrlParam("userId");
	window.location.href = "../signet/moulage_edit.html?type1=check&userId="+userId;
});