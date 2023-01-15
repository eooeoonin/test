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

$(function(){
	//URL参数
	var id = Request.id;
	var tdUrl = "/capital/recordborrower/getAdjustmentRecordinfo";
	var tbData = {
		"id":id
		
	};
	tableFun(tdUrl,tbData);

	  //图片预览
	  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
});
var editid;
var editname;
//编辑
function tableFun(tdUrl, tbData) {
	
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
	    async : false,
		dataType : "json",
		success : function(data){	
			editid =data.userId;
			slectfaren();
		var _table = $('#table'),
		tableBodyHtml = '';
		_loadImages(data);
		var d_adjustmentType = data.adjustmentType,
		d_paymentCertificate = data.paymentCertificate,
		d_realName = data.realName;
		d_userType = data.userType;//用户类型
		//alert(d_paymentCertificate);
		var d_adjustmentTypeCn = "";
		var userTypeCn = "";
		if("PERSON" == d_userType)
			userTypeCn = "个人会员";
		else if("ENTERPRISE" == d_userType)
			userTypeCn = "企业会员";
		if("ADD" == d_adjustmentType){
			d_adjustmentTypeCn = "手工加帐";
		}
		else{
			d_adjustmentTypeCn = "手工减帐";
		}
		$("#id1").html(data.id);
		$("#adjustmentTypeCn").html(d_adjustmentTypeCn);
		$("#operatorName").html(data.operatorName);
		$("#adjustmentTradeTime").html(data.adjustmentTradeTime);
		$("#realName1").html(d_realName);
		$("#userTypeCn").html(userTypeCn);
		$("#realName2").html(editname);
		$("#mobile").html(data.mobile);
		$("#adjustmentAmount").html(data.adjustmentAmount.amount);
		$("#mark").html(data.mark);

      }
	
	});
	
}
function slectfaren(){
	var tbData = {
			"userId":editid
		};
	$.ajax({
		type : "POST",
		url : "/capital/recordborrower/getfarenRecord",
		data :tbData,
		async : false,
		dataType : "json",
		success : function(data) {
			editname = data.legalPersonName;
		}
			
	});
	
	
}
function getUnBankStaffName(){
	$.ajax({
		type : "POST",
		url : "/capital/recordborrower/getUnBankStaffName",
		data :"",
		async : false,
		dataType : "json",
		success : function(data) {
			editname = data;
		}
			
	});
	
}
function _loadImages(data){
var legalPersonIDcardfileUrl = domainUrl + pic + data.paymentCertificate;

  var _cdImgIdlegalPersonIDcard = $("#paymentCertificate");
  _cdImgIdlegalPersonIDcard.attr("href",legalPersonIDcardfileUrl);
  _cdImgIdlegalPersonIDcard.find("img").attr("src",legalPersonIDcardfileUrl);
  _cdImgIdlegalPersonIDcard.find("input").val(data.legalPersonCertNoFile);
}