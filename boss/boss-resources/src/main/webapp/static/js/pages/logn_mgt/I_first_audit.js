function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};
var zijiuer;
var _pages;
var _pages2;
var saveId;
var fileCount = -1;
var riskfiles = [];
var riskfilesname = [];
var borrowId =  getUrlParam("borrowId");
var borrowerType = getUrlParam("borrowerType");
$("#borrowId").val(borrowId);
if(borrowerType == "PERSON"){
	$(".qiye").hide();
	$(".person").show();
	$("#isPerson").html("身份证");
}
if(borrowerType == "ENTERPRISE"){
	$(".qiye").show();
	$(".person").hide();
}
var _bwId = borrowId;
//var ue = UE.getEditor('securityAssurance');
//var ue1 = UE.getEditor('riskControl');
var ue2 = UE.getEditor('projectText');
ue2.ready(function() {
    //设置编辑器的内容
	UE.getEditor('projectText').setDisabled('fullscreen');
});

/*$("#riskControl").val("（1）严格审核借款企业（人）和担保人的信用。（最高人民法院执行查询网、失信被执行网、<br>工商企业信用网、风险预警网、央行征信报告等）<br>（2）严格审核借款用途真实合理性。<br>（3）严格审核担保物价值，控制抵押率。");
$("#securityAssurance").val("资金安全：<br>新网银行存管：借贷交易中所有的资金流转环节都在新网银行完成，实现平台资金与用户资金的隔离。<br>风控安全：<br>1、项目充分论证，数据交叉验证，多层次内控体系，多层次还款保障措施，让每个项目安全放心。" +
"<br>2、资质审核严格，全方位覆盖所有风险点。<br>3、完善贷中，贷后管理，多种手段针对各种逾期情况的催收体系。<br>账户安全：<br>1、实名认证：所有账户交易前均需实名认证。<br>2、同卡进出：充值时所使用的银行卡，提现时原路返回。<br>3、数据保密：金融级安全技术，高强度加密，为用户数据安全提供更高保障，以及对用户数据安全的承诺。");*/
$("#borrowId").val(borrowId);
$(function () {

	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#firstAuditInfo');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
  var _table = $('#table');
  chkFun();
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
  laydate({elem: "#date1", format: "YYYY-MM-DD"});
  
  
  //普通上传
  initFileInput("fileinput", "/boss/imageUpload?bizeCode=pic","10",false);
  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
  
		$.ajax({
			type : "POST",
			url : "/borrow/firstReview_I_first_audit/audit",
			data:{"borrowId":borrowId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data = data.data[0];
					var _data1= data.data[1];
					var _data2 = data.data[2].businessObject;
					var _data3 = data.data[3];
					var _data4 = data.data[4];
					var _data5 = data.data[5];
					var _data6 = data.data[6];
					var _data8 = data.data[8];
					if(_data.guaranteeResVo.guaranteeUserName != null && _data.guaranteeResVo.guaranteeUserName != ""){
						$(".danbao").show();
					}else{
						$(".danbao").hide();
					}
					$("#location").val(_data.borrowerResVo.location);
					$("#legalPersonMobile").val(_data2.legalPersonMobile);
					$("#userProvince").val(_data2.userProvince);
					$("#userAddress").val(_data2.userAddress);
					if(_data8 != null ){
						$("#releaseUserId").val(_data8.releaseUserName);
						$("#userCertCode").val(_data8.releaseCertNo);
						zijiuer = _data8.userId;
					}
					$("#guarantyOwner").val(_data.guaranteeResVo.guarantyOwner);
					$("#guarantyAge").val(_data.guaranteeResVo.guarantyAge);
					$("#guarantyCode").val(_data.guaranteeResVo.guarantyCode);
					if(_data.riskGrade != null && _data.riskGrade != ""){
						$("#riskGrade").val(_data.riskGrade);
					}
					$("#sealImageId").val(_data5.id);
					
					$("#borrowerdebt").val(_data.borrowerResVo.debt);
					$("#borrowercredit").val(_data.borrowerResVo.credit);
					$("#borrowerotherPlatForm").val(_data.borrowerResVo.otherPlatForm);
					$("#borrowerlawsuit").val(_data.borrowerResVo.lawsuit);
					
					$("#guaranteeguaranteeCredit").val(_data.guaranteeResVo.guaranteeCredit);
					$("#guaranteeguaranteeLawsuit").val(_data.guaranteeResVo.guaranteeLawsuit);
					
					
					$("#idCard").val(_data.borrowerResVo.idCard);
					$("#age").val(_data.borrowerResVo.age);
					$("#phone").val(_data.borrowerResVo.phone);
					$("#bmarriage").val(_data.borrowerResVo.marriage);
					$("#birthplace").val(_data.borrowerResVo.birthplace);
					if(_data.borrowerResVo.workNature == "SELF"){
						$("#workNature").val("自雇");
					}else if(_data.borrowerResVo.workNature == "EMPLOY"){
						$("#workNature").val("受雇");
					}else if(_data.borrowerResVo.workNature == "FREEDOM"){
						$("#workNature").val("自由职业");
					}else{
						$("#workNature").val("——");
					}
					if(_data.borrowerResVo.incomeRange == "LOW"){
						$("#incomeRange").val("3-10万");
					}else if(_data.borrowerResVo.incomeRange == "MIDDLE"){
						$("#incomeRange").val("10-30万");
					}else if(_data.borrowerResVo.incomeRange == "HIGH"){
						$("#incomeRange").val("30-50万");
					}else if(_data.borrowerResVo.incomeRange == "HIGHER"){
						$("#incomeRange").val("50万以上");
					}else{
						$("#incomeRange").val("——");
					}
					if(null != _data.guaranteeResVo.guaranteeBirthplace && "" != _data.guaranteeResVo.guaranteeBirthplace){
						$("#guaranteeBirthplace").val(_data.guaranteeResVo.guaranteeBirthplace);
					}else{
						$("#guaranteeBirthplace").val("——");
					}
					if(_data.guaranteeResVo.guaranteeWorkNature == "SELF"){
						$("#guaranteeWorkNature").val("自雇");
					}else if(_data.guaranteeResVo.guaranteeWorkNature == "EMPLOY"){
						$("#guaranteeWorkNature").val("受雇");
					}else if(_data.guaranteeResVo.guaranteeWorkNature == "FREEDOM"){
						$("#guaranteeWorkNature").val("自由职业");
					}else{
						$("#guaranteeWorkNature").val("——");
					}
					if(_data.guaranteeResVo.guaranteeIncomeRange == "LOW"){
						$("#guaranteeIncomeRange").val("3-10万");
					}else if(_data.guaranteeResVo.guaranteeIncomeRange == "MIDDLE"){
						$("#guaranteeIncomeRange").val("10-30万");
					}else if(_data.guaranteeResVo.guaranteeIncomeRange == "HIGH"){
						$("#guaranteeIncomeRange").val("30-50万");
					}else if(_data.guaranteeResVo.guaranteeIncomeRange == "HIGHER"){
						$("#guaranteeIncomeRange").val("50万以上");
					}else{
						$("#guaranteeIncomeRange").val("——");
					}
					$("#oaFlowCode").val(_data.oaFlowCode);
					$("#guaranteePhone").val(_data.guaranteeResVo.guaranteePhone);
					$("#corporateContactsMobile").val(_data.borrowerResVo.phone);
					$("#borrowerName").val(_data.borrowUserName);//回显借款方名称
                    $("#borrowTitle").val(_data.borrowTitle);//回显借款方名称
					$("#registeredCapital").val(_data.borrowerResVo.registeredCapital);//回显注册资金
					$("#registerTime").val(_data.borrowerResVo.registerTime);//回显企业注册时间
					$("#enterpriseContact").val(_data.borrowerResVo.enterpriseContact);//回显企业联系人
					$("#enterpriseIntroduce").val(_data.borrowerResVo.enterpriseIntroduce);//回显企业简介
					$("#officeAddress").val(_data.borrowerResVo.officeAddress);//回显企业简介
					$("#industry").val(_data.borrowerResVo.industry);//回显所属行业
					$("#borrowPurpose").val(_data.borrowerResVo.borrowPurpose);//回显借款用途
					$("#repaySourse").val(_data.borrowerResVo.repaySourse);//回显还款来源
					$("#editBy").text(_data.editedBy);
					$("#guaranteeUserName").val(_data.guaranteeResVo.guaranteeUserName);//回显担保人名称
					$("#relationOfEnterprise").val(_data.guaranteeResVo.relationOfEnterprise);//回显与企业关系
					$("#guaranteeIdCard").val(_data.guaranteeResVo.guaranteeIdCard);//回显担保人身份证号
					$("#guaranteeContract").val(_data.guaranteeResVo.guaranteeContract);//回显担保联系人
					$("#guaranteeAge").val(_data.guaranteeResVo.guaranteeAge);//回显担保人年龄
					$("#guarantyAddress").val(_data.guaranteeResVo.guarantyAddress);//回显担保物地址
					$("#marriage").val(_data.guaranteeResVo.marriage);//回显担保婚姻
					$("#guarantyArea").val(Number(_data.guaranteeResVo.guarantyArea).toFixed(2));//回显担保物面积
					$("#guarantyCreateTime").val(_data.guaranteeResVo.guarantyCreateTime);//回显建成年代
					$("#applyAmount").val(_data.applyAmount.amount / 10000);//回显借款金额
					if(_data.termType == "MONTH"){
						$("#borrowTerm").val(_data.borrowTerm);//回显借款期限
					}else if(_data.termType == "DAY"){
						$("#borrowTerm").val(_data.borrowTerm);//回显借款期限
					}else{
						$("#borrowTerm").val("——");//回显借款期限
					}
					if(_data.repayType == "MONTHLYPAYMENTDUE"){
						$("#repayType").val("按月付息到期还本");//回显还款方式
					}else if(_data.repayType == "ONETIMEDEBT"){
						$("#repayType").val("一次性还本付息");//回显还款方式
					}else if(_data.repayType == "MATCHING"){
						$("#repayType").val("等额本息");//回显还款方式
					}else{
						$("#repayType").val("——");//回显还款方式
					}
					$("#borrowLocation").val(_data.borrowLocation);//回显借款发生地
					if(_data.borrowType != null && _data.borrowType != ""){
						$("#assetType").val(_data.borrowType);
					}else{
						$("#assetType").val("WU");
					}
					$("#date1").val(moment(_data.wantReleaseTime).format("YYYY-MM-DD"));
					$("#compRate").val(_data.borrowRate);
					if(_data.guarantyValue.amount == 0){
						$("#guarantyValue").val("");
					}else{
						$("#guarantyValue").val(_data.guarantyValue.amount);
					}
					if(_data.borrowAmount.amount == 0){
						$("#borrowAmount").val("");
					}else{
						$("#borrowAmount").val(_data.borrowAmount.amount / 10000);
					}
					$("#guarantyRate").val(_data.guarantyRate);
					
					if(_data.termType == "MONTH"){
						$("#approveTerm").val(_data.approveTerm);
					}else if(_data.termType == "DAY"){
						$("#approveTerm").val(_data.approveTerm);
					}else{
						$("#approveTerm").val("——");
					}
					var _data7 = data.data[7];
					if(_data7.length > 0){
						showGua(_data7);
					}
					 loadpro(_data1);
					_loadImages(_data2);
					_loadImages(_data5);
					_loadImages1(_data6);
					_showEditInfo(_data3);
					loadrisk(_data4);
				}else{
					bootbox.alert("取得借款人基础信息异常");
				}
			}
		},
		async : false
	});
  

		
$("#submitBtn").click(function() {
	var finalData = {};
	var formData = $("#firstAuditInfo").serializeObject();
		formData.riskfiles = riskfiles;
		formData.riskfilesname =riskfilesname;
		formData.zijiuer = zijiuer;
	var sealId = $("#sealImageId").val();
	if ("" == sealId ||null == sealId ){
		  bootbox.alert("借款人印模不能为空");
	  }else{
	if($("#approveResult").val() == "PASS_FIRST_CHECK"){
		  if (!$("#firstAuditInfo").validationEngine('validate')) {
			    return false;
			  }
		  finalData = formData;
	}else{
		if($("#auditRecommendations").val() == "" || $("#auditRecommendations").val() == null){
			alert("意见建议不能为空");
			return false;
		}
		finalData.approveResult = formData.approveResult;
		finalData.borrowId = formData.borrowId;
		finalData.auditRecommendations = formData.auditRecommendations;
	}

	  	var upBorrowAmount = $("#borrowAmount").val();
	  	var upapplyAmount = $("#applyAmount").val();
	  	
		if( parseFloat(upBorrowAmount) > parseFloat(upapplyAmount)){
			alert("批贷金额不能大于借款金额");
		} else {
		$.ajax({
			type : "POST",
			url : "/borrow/firstReview_I_first_audit/firstAssetAudit",
			data : finalData,
			dataType: "json",
			traditional:true,
			success : function(data) {
				if (data != null && data != "") {
					if (data == "success") {
						bootbox.alert("操作成功", function(){
							window.location.href = "/borrow/firstReview_list.html";
						});
					}else{
						bootbox.alert(data);
					}
				}
			},
			async : false,
			error : function(data){
				alert(data);
			}
		});
		}
		}
});


$("#saveBtn").click(function() {
	var finalData = {};
	var formData = $("#firstAuditInfo").serializeObject();
		formData.riskfiles = riskfiles;
		formData.riskfilesname =riskfilesname;
		formData.zijiuer = zijiuer;
	var sealId = $("#sealImageId").val();
	if ("" == sealId ||null == sealId ){
		  bootbox.alert("借款人印模不能为空");
	  }else{
	if($("#approveResult").val() == "PASS_FIRST_CHECK"){
		  if (!$("#firstAuditInfo").validationEngine('validate')) {
			    return false;
			  }
		  finalData = formData;
			finalData.isSave = "yes";
			finalData.saveId = saveId;
	}else{
		if($("#auditRecommendations").val() == "" || $("#auditRecommendations").val() == null){
			alert("意见建议不能为空");
			return false;
		}
		finalData.approveResult = formData.approveResult;
		finalData.borrowId = formData.borrowId;
		finalData.auditRecommendations = formData.auditRecommendations;
		finalData.isSave = "yes";
		finalData.saveId = saveId;
	}

	  	var upBorrowAmount = $("#borrowAmount").val();
	  	var upapplyAmount = $("#applyAmount").val();
	  	
		if( parseFloat(upBorrowAmount) > parseFloat(upapplyAmount)){
			alert("批贷金额不能大于借款金额");
		} else {
		$.ajax({
			type : "POST",
			url : "/borrow/firstReview_I_first_audit/firstAssetAudit",
			data : finalData,
			dataType: "json",
			traditional:true,
			success : function(data) {
				if (data != null && data != "") {
					if (data == "success") {
						bootbox.alert("操作成功", function(){
							window.location.href = "/borrow/firstReview_list.html";
						});
					}else{
						bootbox.alert(data);
					}
				}
			},
			async : false,
			error : function(data){
				alert(data);
			}
		});
		}
		}
});

});

function loadrisk(data){
	riskfiles = [];
	  $.each(data, function (k, v) {
		  fileCount++;
		  	var _fileImgs = $("#fileImgs");
		  	var fileUrl = domainUrl + pic +v.fileUrl;
		  	var fid = v.id;
		    var fileHtml = "";
		    fileHtml +='<div id="divImg'+ fileCount +'" style="margin-left:15px;">';
		    fileHtml +='   <a target="_blank" id="creditImgUrl'+ fileCount +'"  href="'+ fileUrl +'">';
		    fileHtml +='    <input type="hidden" id="riskfiles['+ fileCount +']"  value="'+ v.fileUrl +'">';
		    fileHtml +='    <input type="hidden" id="riskfilesname['+ fileCount +']"  value="'+ v.title +'">';
		    fileHtml +=v.title+'</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="float:left" id="delImg'+fileCount+'" onclick="delFile(\''+fid+'\',\''+fileCount+'\')">删除</a>';
		    fileHtml +='</div>';
		    _fileImgs.append(fileHtml);
		    riskfiles.push(v.fileUrl);
		    riskfilesname.push(v.title);
	  });

}

function loadpro(data){
	  var o = -1;
	  $.each(data, function (k, v) {
		  	o++;
		  	var _fileImgs = $("#profileImgs");
		  	var fileUrl = domainUrl + pic +v.fileUrl;
		    var fileHtml = "";
		    fileHtml +='<div style="margin-left:15px;">';
		    fileHtml +='   <a target="_blank" id="creditImgUrl'+ o +'" class="fancybox img-responsive" href="'+ fileUrl +'">';
		    fileHtml +='    <input type="hidden"  value="'+ v.fileUrl +'">';
		    fileHtml +=v.title+'</a>';
		    fileHtml +='</div>';
		    _fileImgs.append(fileHtml);
		    
	  });
	  

}

//回显图片
function _loadImages(data){
  
	if(null != data.businessLicenseFile && ''!= data.businessLicenseFile){
        var businessLicenseNofileUrl = domainUrl + pic + data.businessLicenseFile;
        var _cdImgIdbusinessLicenseNo = $("#businessLicenseNoShow");
        _cdImgIdbusinessLicenseNo.attr("href",businessLicenseNofileUrl);
        _cdImgIdbusinessLicenseNo.find("img").attr("src",businessLicenseNofileUrl);
        _cdImgIdbusinessLicenseNo.find("input").val(data.businessLicenseFile);
    }

    if (data.registerCertType == 'ONE') {
        $("#taxIdShow1").attr("style", "display: none;");
        $("#organizingCodeShow1").attr("style", "display: none;");
    } else {
        if (null != data.taxFile && '' != data.taxFile) {
            var taxIdfileUrl = domainUrl + pic + data.taxFile;
            var _cdImgIdtaxId = $("#taxIdShow");
            _cdImgIdtaxId.attr("href", taxIdfileUrl);
            _cdImgIdtaxId.find("img").attr("src", taxIdfileUrl);
            _cdImgIdtaxId.find("input").val(data.taxFile);
        }

        if (null != data.organizingCodeFile && '' != data.organizingCodeFile) {
            var organizingCodeFilefileUrl = domainUrl + pic + data.organizingCodeFile;
            var _cdImgIdorganizingCode = $("#organizingCodeShow");
            _cdImgIdorganizingCode.attr("href", organizingCodeFilefileUrl);
            _cdImgIdorganizingCode.find("img").attr("src", organizingCodeFilefileUrl);
            _cdImgIdorganizingCode.find("input").val(data.organizingCodeFile);
        }
    }
    
    if(null != data.fileUrl && ''!= data.fileUrl){
    	if(data.mark == "CREDIT"){
    		 var newfileUrl = domainUrl + pic + data.fileUrl;
    	        var _cdImgIdorganizingCode = $("#businessLicenseNo");
    	        _cdImgIdorganizingCode.attr("href",newfileUrl);
    	        _cdImgIdorganizingCode.find("img").attr("src",newfileUrl);
    	        _cdImgIdorganizingCode.find("input").val(data.fileUrl);
    	}
       
    }
    

    if(null != data.bankAccountFile && ''!= data.bankAccountFile){
        var bankopenfileUrl = domainUrl + pic + data.bankAccountFile;
        var _cdImgIdbankopen = $("#bankopenShow");
        _cdImgIdbankopen.attr("href",bankopenfileUrl);
        _cdImgIdbankopen.find("img").attr("src",bankopenfileUrl);
        _cdImgIdbankopen.find("input").val(data.bankAccountFile);
    }
    
	if(null != data.savePath && ''!= data.savePath){
		var moulagePicturefileUrl = domainUrl + data.savePath;
		var _cdImgMoulagePicture = $("#moulagePictureShow");
		_cdImgMoulagePicture.attr("href",moulagePicturefileUrl);
		_cdImgMoulagePicture.find("img").attr("src",moulagePicturefileUrl);
		_cdImgMoulagePicture.find("input").val(data.savePath);
	}
}

function _loadImages1(data){
    if (typeof(data) != "undefined"){
    	if(null != data.savePath && ''!= data.savePath){
			var moulagePicturefileUrl = domainUrl + data.savePath;
			var _cdImgMoulagePicture = $("#guaMoulagePictureShow");
			_cdImgMoulagePicture.attr("href",moulagePicturefileUrl);
			_cdImgMoulagePicture.find("img").attr("src",moulagePicturefileUrl);
			_cdImgMoulagePicture.find("input").val(data.savePath);
		}
    }
}

function _showEditInfo(data){
	var editInfoList = data;
	var str = "";
		str += "<tr>";
		str += "<td colspan='4'>审核历史</td>";
		str += "<tr/>";
//		str += "<td class='input-group-addon' ></td>";
		var style=[];
		var isTrue = true;
	for(var i=0; i<editInfoList.length; i++){
		if(editInfoList[i].extendType == "FIRST_CHECK" && editInfoList[i].action == "save"){
			$("#auditRecommendations").val(editInfoList[i].mark);
			saveId = editInfoList[i].id;
			continue;
		}
		if(editInfoList[i].extendType == "BUS_MANAGER" || editInfoList[i].extendType == "FIRST_CHECK" || editInfoList[i].extendType == "RECHECK" || editInfoList[i].extendType == "FINANCE" || editInfoList[i].extendType == "FINAL_CHECK"){
			if(isTrue){
				isTrue = false;
				style = "style='background-color:#ffad86;'";
			}else{
				isTrue = true;
				style = "style='background-color:#c2ff68;'";
			}
			str += "<tr>";
			str += "<td width='20%' class='input-group-addon' "+style+">审核环节</td>";
	        str += "<td width='30%' "+style+">"
	        str += "<span >" +editInfoList[i].borrowCode+"</span>";
	        str += "<td width='20%' class='input-group-addon' "+style+" >审核结果</td>";
	        str += "<td width='30%'  "+style+">"
	        str += "<span >" +editInfoList[i].status+"</span>";
			str += "<tr/>";
			str += "<tr>";
	        str += "<td class='input-group-addon'>审核建议</td>";
	        str += "<td colspan='3'>";
	        str += "<textarea type='text' readonly='readonly'  class='form-control' style='height:150px;resize:none'> "+ editInfoList[i].mark+"</textarea>";
	        str += "</td>";
	        str += "</tr>";
	        str += "<tr>";
			str += "<td class='input-group-addon'>操作人</td>";
	        str += "<td >"
	        str += "<span>" +editInfoList[i].editedBy+"</span>";
	        str += "<td class='input-group-addon'>操作时间</td>";
	        str += "<td >"
	        str += "<span>" +editInfoList[i].createTime+"</span>";
			str += "<tr/>";
		}
		if(editInfoList[i].extendType == "PROJECT"){
			$("#projectText").val(editInfoList[i].mark);
		}
		/*if(editInfoList[i].extendType == "RISK"){
			if(editInfoList[i].mark != null && editInfoList[i].mark != ""){
				$("#riskControl").val(editInfoList[i].mark);
			}else{
				$("#riskControl").val("（1）严格审核借款企业（人）和担保人的信用。（最高人民法院执行查询网、失信被执行网、<br>工商企业信用网、风险预警网、央行征信报告等）<br>（2）严格审核借款用途真实合理性。<br>（3）严格审核担保物价值，控制抵押率。");
			}
		}
		if(editInfoList[i].extendType == "SECURITY"){
			if(editInfoList[i].mark != null && editInfoList[i].mark != ""){
				$("#securityAssurance").val(editInfoList[i].mark);
			}else{
				$("#securityAssurance").val("资金安全：<br>新网银行存管：借贷交易中所有的资金流转环节都在新网银行完成，实现平台资金与用户资金的隔离。<br>风控安全：<br>1、项目充分论证，数据交叉验证，多层次内控体系，多层次还款保障措施，让每个项目安全放心。" +
						"<br>2、资质审核严格，全方位覆盖所有风险点。<br>3、完善贷中，贷后管理，多种手段针对各种逾期情况的催收体系。<br>账户安全：<br>1、实名认证：所有账户交易前均需实名认证。<br>2、同卡进出：充值时所使用的银行卡，提现时原路返回。<br>3、数据保密：金融级安全技术，高强度加密，为用户数据安全提供更高保障，以及对用户数据安全的承诺。");
			}
		}*/
	}
	$("#historyReview").append(str);
}




//初始化fileinput控件（第一次初始化）
var initFileInput = function(ctrlName, uploadUrl,fmax,fpreview) {
  var control = $('#' + ctrlName);
  $(document).on('ready', function () {
	  control.fileinput({
	      language: 'zh', //设置语言
	      allowedFileExtensions: ['jpg', 'png', 'gif','pdf'],//接收的文件后缀
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

/*//上传图片
$('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    $("#rikeUrl").val(result.fileName);
    var _cdImgId = $("#businessLicenseNo2");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
    $("#businessLicenseNo2").val(result.fileName);
    
});*/
$('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
	var _fileImgs = $("#fileImgs");
	fileCount++;
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var fileHtml = "";
    fileHtml +='<div id="divImg'+ fileCount +'" style="margin-left:15px;">';
    fileHtml +='   <a target="_blank" id="creditImgUrl'+ fileCount +'"  href="'+ fileUrl +'">';
    fileHtml +='    <input type="hidden" id="riskfiles['+ fileCount +']"  value="'+ result.fileName +'">';
    fileHtml +='    <input type="hidden" id="riskfilesname['+ fileCount +']"  value="'+ files[0].name +'">';
    fileHtml +=files[0].name+'</a>&nbsp;&nbsp;&nbsp;&nbsp;<a id="delImg'+fileCount+'" onclick="delFileNoUpLoad(\''+fileCount+'\')">删除</a>';
    fileHtml +='</div>';
    _fileImgs.append(fileHtml);
    riskfiles.push(result.fileName);
    riskfilesname.push(files[0].name);

});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数
};
$.fn.serializeObject = function()
{
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};
function delFile(fid,fileCount){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/borrow/firstReview_I_first_audit/delBorrowFile",
				data : {'id':fid},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('删除成功',function(){
							 var obj = document.getElementById("divImg"+fileCount);
							 var parent = obj.parentNode;
							 parent.removeChild(obj);//在这里通过父节点＋待删除节点对象来删除
							 riskfiles.splice(fileCount,1);
							 riskfilesname.splice(fileCount,1);	
			        	});
					} else {
						bootbox.alert("操作失败");
					}
					;
				}
			});
		} else {
			return;
		}
	});
};
function delFileNoUpLoad(fileCount){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			 var obj = document.getElementById("divImg"+fileCount);
			 var parent = obj.parentNode;
			 parent.removeChild(obj);//在这里通过父节点＋待删除节点对象来删除
			 riskfiles.splice(fileCount,1);		
			 riskfilesname.splice(fileCount,1);	
			 } else {
			return;
		}
	});
}
function showGua(v){
	var cijiGua = $("#cijiGua");
	var html = "";
	for(var i = 0 ; i < v.length ; i++){
		var fileUrl = domainUrl + v[i].editedBy;
		html +='<table class="table table-bordered" ><tr><td class="input-group-addon">担保人身份证号</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeIdCard+'"></td>';
		html +='<td class="input-group-addon">担保人名称</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeUserName+'"></td></tr>';
		html +='<tr><td class="input-group-addon">担保联系人</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeContract+'"></td>';
		html +='<td class="input-group-addon">担保人年龄</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeAge+'"></td></tr>';
		html +='<tr><td class="input-group-addon">担保人联系方式</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteePhone+'"></td></tr>';
		html +='<tr><td class="input-group-addon">担保人印模图片</td><td><div  style="margin-left:15px;"><a target="_blank"  href="'+ fileUrl +'"><img alt="image" style="width:25%" src="'+fileUrl+'" /></a></div></td></tr></table>';
		
	}
	cijiGua.html(html);
}

