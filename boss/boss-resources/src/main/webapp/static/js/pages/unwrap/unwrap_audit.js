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
	var tdUrl = "/unwrap/UnBankselect";
	var tbData = {
		"id":id
		
	};
	tableFun(tdUrl,tbData);
	chkFun();
	  $(window).resize(function () {
	    chkFun();
	  });

	  /***
	   *功能说明：复选框、单选框美化
	   *参数说明：
	   *创建人：李波涛
	   *时间：2016-07-15
	   ***/
	  function chkFun() {
	    $(".i-checks").iCheck({
	      checkboxClass: "icheckbox_square-green",
	      radioClass: "iradio_square-green"
	    });
	    //全选、反选
	    var _jCheckAll = $("#jCheckAll"),
	      _subCheck = $('input[type="checkbox"].sub_ckbox');
	    _jCheckAll.on('ifChecked', function () {
	      _subCheck.iCheck('check');
	    });
	    _jCheckAll.on('ifUnchecked', function () {
	      _subCheck.iCheck('uncheck');
	    });
	  }
	  
	  


	  //图片预览
	  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

});







var remarkvalue;
var userinfoid;
var phone;
var xingname;
var certNo;
var bank;
var banknumber;
var yuer;
var editname;
var lognmoney;
//编辑
function tableFun(tdUrl, tbData) {
	
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
	    async : false,
		dataType : "json",
		success : function(data){
			_loadImages(data);
			userinfoid = data.userId;
			remarkvalue = data.remark;
			$("#id1").val(data.id);
			$("#userId1").val(data.userId);
			
			//加载另外函数获取详细信息
			//获取用户信息
			userSelectid();
			//获取用余额
			userSelectyuer();
			//取得出借金额
			userSelectloanid();
			//手机号
			$("#mobile").html(data.registerMobile);
			//姓名
			$("#xingname").html(xingname);
			//身份证号
			$("#certNo").html(certNo);
			//银行
			$("#bank").html(bank);
			//银行卡号
			$("#banknumber").html(banknumber);
			//账户余额
			$("#yuer").html(yuer);
			//出借金额
			$("#lognmoney").html(lognmoney);
			//申请时间
			$("#createTime").html(data.createTime);
//			//手持身份证正面
//			$("#banknumber").html(data.certPhoto1);
//			//手持身份证反面
//			$("#banknumber").html(data.certPhoto2);
			//操作理由
			
			//操作人
			$("#editedBy").html(data.editedBy);
      }
	
	});
	$("#remark1").val(remarkvalue);
}


function _loadImages(data){
    var legalPersonIDcardfileUrl = domainUrl + pic + data.certPhoto1;
    var _cdImgIdlegalPersonIDcard = $("#legalPersonIDcardShow");
    _cdImgIdlegalPersonIDcard.attr("href",legalPersonIDcardfileUrl);
    _cdImgIdlegalPersonIDcard.find("img").attr("src",legalPersonIDcardfileUrl);
    _cdImgIdlegalPersonIDcard.find("input").val(data.certPhoto1);
    
    var businessLicenseNofileUrl = domainUrl + pic + data.certPhoto2;
    var _cdImgIdbusinessLicenseNo = $("#businessLicenseNoShow");
    _cdImgIdbusinessLicenseNo.attr("href",businessLicenseNofileUrl);
    _cdImgIdbusinessLicenseNo.find("img").attr("src",businessLicenseNofileUrl);
    _cdImgIdbusinessLicenseNo.find("input").val(data.certPhoto2);

}
//userSelectloanid

//取得出借金额
function userSelectloanid(){
	var formData={
			"investorUserCode" : userinfoid
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/userSelectloanid",
		data :formData,
		async : false,
		dataType : "json",
		success : function(data1) {
			lognmoney = data1.totalInvest.amount+'元';
			
		}	
		
	});
}

//获取当前用户角色信息
function userSelectid() {
	
	var formData={
			"userId" : userinfoid//=========================================		
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/userSelectid",
		data :formData,
		async : false,
		dataType : "json",
		success : function(data) {
			phone =data.bankMobile;
			xingname =data.realName;
			certNo = data.certNo;
			bank = data.bankName;
			banknumber =data.bankCardNo;
		}	
	});  
//	getUnBankStaffName();
}
//根据userid查询余额
function userSelectyuer() {
	var formData={
			"userId" : userinfoid//=============================================================
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/userSelectyuer",
		data :formData,
		async : false,
		dataType : "json",
		success : function(data) {
				yuer = data.amount+'元';
		}
			
	});
	
}
function getUnBankStaffName(){
	$.ajax({
		type : "POST",
		url : "/unwrap/getUnBankStaffName",
		data :"",
		async : false,
		dataType : "json",
		success : function(data) {
			editname =data;
		}
			
	});
	
}


//通过
function submitedit1() {
	var formData=$("#form").serialize();	
	$.ajax({
		type : "POST",
		url : "/unwrap/successUnBank",
		data :formData,
		dataType : "json",
		success : function(data) {
			var aa = data.resultCodeMsg;
			alert(aa);
			location = "../unwrap/unwrap_list.html";
		}
			
//	      alert("通过成功");
//		},error: function(){
//			alert("通过失败");
//			location = "../unwrap/unwrap_list.html";
//		}
			
	});
	  
}
//拒绝
function submitedit2() {
	var formData=$("#form").serialize();
	$.ajax({
		type : "POST",
		url : "/unwrap/refuseUnBank",
		data :formData,
		dataType : "json",
		success : function(data){
			var aa = data.resultCodeMsg;
			alert(aa);
			location = "../unwrap/unwrap_list.html";
		}
//		},error: function(){
//			alert("拒绝通过失败");
//			location = "../unwrap/unwrap_list.html";
//		}
			
	});	  
}
////内容保存
//
//function editremark() {
//	var formData=$("#form").serialize();
//	var formData;
//	$.ajax({
//		type : "POST",
//		url : "/unwrap/UnBankupdata",
//		data :formData,
//		dataType : "json",
//		success : function(data){
//	      editremark();
//	      location = "../unwrap/unwrap_list.html";
//		},error: function(){
//			//location = "../unwrap/unwrap_list.html";
//		}	
//	});	  
//}
