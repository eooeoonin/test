function _getParamsObject() {
	var thisURL = document.URL;
	var getval = thisURL.split('?')[1];
	var paramsArray = getval.split("&");
	var paramsObject = {};
	for (var i = 0; i < paramsArray.length; i++) {
		var keyAndValue = paramsArray[i].split("=");
		paramsObject[keyAndValue[0]] = keyAndValue[1];
	}
	return paramsObject;
}
var registerCertType;

$(function () {
	if( _getParamsObject().type=="show"){
	var borrowerId =  _getParamsObject().borrowerId;
	var userId =  _getParamsObject().userId;
	/**
	 * 用户角色
	 */
	    $.ajax({
	      type: "POST",
	      url: "/borrower/borrowerlist/getUserRole",
	      data: {"userId" : userId},
	      dataType: "json",
	      success: function (data) {
	    	  $("#userRole").val(data);
	    	  if(data =="GUARANTEE"){
	    		  $("#tab3Name").text("担保信息");
	    	  }
	      },
		  async : false
	    });
	
	
	$.ajax({
		type : "POST",
		url : "/borrower/borrowerlist/getbaseInfo/" + borrowerId,
		data : {"borrowerId" : borrowerId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data = data.data;
					$("#userCode").val(_data.userId);//企业用户id
					$("#recharge_userCode").val(_data.userId);//借款人充值隐藏域
					$("#legalPersonMobile").html(_data.legalPersonMobile);//法人手机号码
					$("#detail_legalPersonMobile").html(_data.legalPersonMobile);
					$("#recharge_legalPersonMobile").html(_data.legalPersonMobile);
					$("#recharge_mob").val(_data.legalPersonMobile);
					$("#detail_registeredCapital").text(_data.registeredCapital+"万元");//注册资金
					$("#legalPersonCertNo").html(_data.legalPersonCertNo);//法人身份证号
					$("#detail_legalPersonCertNo").html(_data.legalPersonCertNo);
					$("#recharge_legalPersonCertNo").html(_data.legalPersonCertNo);
					$("#email").html(_data.email);//邮箱
					$("#establishedTime").html(_data.establishedTime);//注册时间
					$("#detail_establishedTime").html(_data.establishedTime);
					$("input[type='radio'][name='registerCertType'][value="+registerCertType+"]").attr("checked", "checked");
					$("#enterpriseName").html(_data.enterpriseName);//企业名称
					$("#enterpriseName1").val(_data.enterpriseName);
					$("#detail_enterpriseName").html(_data.enterpriseName);
					$("#legalPersonName").html(_data.legalPersonName);//法人姓名
					$("#detail_legalPersonName").html(_data.legalPersonName);
					$("#recharge_legalPersonName").html(_data.legalPersonName);
					$("#businessLicenseNo").html(_data.businessLicenseNo);//营业执照编号
					$("#detail_businessLicenseNo").html(_data.businessLicenseNo);
					$("#taxId").html(_data.taxId);//税务登记号
					$("#detail_taxId").html(_data.taxId);
					$("#organizingCode").html(_data.organizingCode);//组织机构代码
					$("#detail_organizingCode").html(_data.organizingCode);
					$("#bankAccount").html(_data.bankAccount);//企业银行账号
					$("#detail_bankAccount").html(_data.bankAccount);
					$("#openAccountBank").html(_data.openAccountBank);//开户银行
					$("#branchBank").html(_data.branchBank);//支行名称
					$("#officeAddress").html(_data.officeAddress);//办公地址
					$("#detail_officeAddress").html(_data.officeAddress);
					$("#enterpriseIntroduce").html(_data.enterpriseIntroduce);//企业基本介绍
					$("#detail_enterpriseIntroduce").html(_data.enterpriseIntroduce);
					$("#enterpriseBusiness").html(_data.enterpriseBusiness);//企业经营范围
					$("#detail_enterpriseBusiness").html(_data.enterpriseBusiness);
					
					registerCertType= _data.registerCertType; //三证合一类型
					if("THREE"== registerCertType){
						$("#detail_registerCertType").html("三证");
					}
					if("ONE"== registerCertType){
						$("#detail_registerCertType").html("三证合一");
					}
					if(registerCertType=="ONE"){
						$("#taxIdShow1").hide();
						$("#organizingCodeShow1").hide();
						$("#detail_taxId").html("");
						$("#detail_organizingCode").html("");
					}
					
					//判断角色
					var userRole = $("#userRole").val();
					if (userRole == "GUARANTEE") {
						if(null != _data.userAccountInfoResVo && null != _data.userAccountInfoResVo.balance){
							$("#blance").html(_data.userAccountInfoResVo.balance.amount);//账户余额
						}
						if(null != _data.userAccountInfoResVo && null != _data.userAccountInfoResVo.freezeAmount){
							$("#freezeAmount").html(_data.userAccountInfoResVo.freezeAmount.amount);//冻结金额
						}
					} else {
						if(null != _data.userAccountInfoResVo && null != _data.userAccountInfoResVo.balance){
							$("#detail_blance").html(_data.userAccountInfoResVo.balance.amount);//账户余额
						}
	//					if(null != _data.userAccountInfoResVo && null != _data.userAccountInfoResVo.balance){
	//						$("#recharge_blance").html(_data.userAccountInfoResVo.balance.amount);
	//					}
						if(null != _data.userAccountInfoResVo && null != _data.userAccountInfoResVo.freezeAmount){
							$("#detail_freezeAmount").html(_data.userAccountInfoResVo.freezeAmount.amount);//冻结金额
						}
						if(null != _data.redMoney){
							$("#detail_redMoney").html(_data.redMoney.amount);//红包余额
						}
					}
					$("#recharge_operate").html($.cookie("username"));//操作人
					_loadImages(_data);
				}else{
					alert(data.msg);
				}
			}
		},
		async : false
	});
	}

});


function _loadImages(data){
	if(null != data.legalPersonCertNoFile && ''!= data.legalPersonCertNoFile){
	    var legalPersonIDcardfileUrl = domainUrl + pic + data.legalPersonCertNoFile;
	    var _cdImgIdlegalPersonIDcard = $("#legalPersonIDcardShow");
	    _cdImgIdlegalPersonIDcard.attr("href",legalPersonIDcardfileUrl);
	    _cdImgIdlegalPersonIDcard.find("img").attr("src",legalPersonIDcardfileUrl);
	    _cdImgIdlegalPersonIDcard.find("input").val(data.legalPersonCertNoFile);
	}

    if(null != data.businessLicenseFile && ''!= data.businessLicenseFile){
        var businessLicenseNofileUrl = domainUrl + pic + data.businessLicenseFile;
        var _cdImgIdbusinessLicenseNo = $("#businessLicenseNoShow");
        _cdImgIdbusinessLicenseNo.attr("href",businessLicenseNofileUrl);
        _cdImgIdbusinessLicenseNo.find("img").attr("src",businessLicenseNofileUrl);
        _cdImgIdbusinessLicenseNo.find("input").val(data.businessLicenseFile);
    }

    if(null != data.taxFile && ''!= data.taxFile){
        var taxIdfileUrl = domainUrl + pic + data.taxFile;
        var _cdImgIdtaxId = $("#taxIdShow");
        _cdImgIdtaxId.attr("href",taxIdfileUrl);
        _cdImgIdtaxId.find("img").attr("src",taxIdfileUrl);
        _cdImgIdtaxId.find("input").val(data.taxFile);
    }
    
    if(null != data.organizingCodeFile && ''!= data.organizingCodeFile){
        var organizingCodeFilefileUrl = domainUrl + pic + data.organizingCodeFile;
        var _cdImgIdorganizingCode = $("#organizingCodeShow");
        _cdImgIdorganizingCode.attr("href",organizingCodeFilefileUrl);
        _cdImgIdorganizingCode.find("img").attr("src",organizingCodeFilefileUrl);
        _cdImgIdorganizingCode.find("input").val(data.organizingCodeFile);
    }
    

    if(null != data.bankAccountFile && ''!= data.bankAccountFile){
        var bankopenfileUrl = domainUrl + pic + data.bankAccountFile;
        var _cdImgIdbankopen = $("#bankopenShow");
        _cdImgIdbankopen.attr("href",bankopenfileUrl);
        _cdImgIdbankopen.find("img").attr("src",bankopenfileUrl);
        _cdImgIdbankopen.find("input").val(data.bankAccountFile);
    }

}

//印模查看
$("#checkMoulageBtn").click(function(){
	 var userId =  _getParamsObject().userId;
	 window.location.href = "../signet/moulage_edit.html?userId="+userId;
	 
});
//印章记录
$("#checkSignetBtn").click(function(){
	var userId =  _getParamsObject().userId;
	window.location.href = "../signet/enterprise_signet_detail.html?userId="+userId;
	
});
