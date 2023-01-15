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
var ue = UE.getEditor('projectDetails');
var borrowId = getUrlParam("borrowId");
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
$(function () {

	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#assetAuditInfo');
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
  initFileInput("fileinput", "/boss/imageUpload?bizeCode=pic","1",false);
  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

	$.ajax({
		type : "POST",
		url : "/borrow/review_I_asset_audit/audit",
		data:{"borrowId":borrowId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data = data.data[0];
					var _data1= data.data[1];
					var _data2 = data.data[2].businessObject;
					var _data3 = data.data[3];
					var _data5 = data.data[5];
					var _data6 = data.data[6];
					var _data8 = data.data[8];
					if(_data8 != null ){
						$("#releaseUserId").val(_data8.releaseUserName);
						$("#userCertCode").val(_data8.releaseCertNo);
						zijiuer = _data8.userId;
					}
					if(_data.guaranteeResVo.guaranteeUserName != null && _data.guaranteeResVo.guaranteeUserName != ""){
						$(".danbao").show();
					}else{
						$(".danbao").hide();
					}
					$("#location").val(_data.borrowerResVo.location);
					$("#legalPersonMobile").val(_data2.legalPersonMobile);
					$("#userProvince").val(_data2.userProvince);
					$("#userAddress").val(_data2.userAddress);
					$("#sealImageId").val(_data5.id);
					$("#idCard").val(_data.borrowerResVo.idCard);
					$("#borrower.debt").val(_data.borrowerResVo.debt);
					$("#borrower.credit").val(_data.borrowerResVo.credit);
					$("#borrower.otherPlatForm").val(_data.borrowerResVo.otherPlatForm);
					$("#borrower.lawsuit").val(_data.borrowerResVo.lawsuit);
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
                    // $("#borrowTitle").val(_data.borrowTitle);//回显借款方名称
					$("#registeredCapital").val(_data.borrowerResVo.registeredCapital);//回显注册资金
					$("#registerTime").val(_data.borrowerResVo.registerTime);//回显企业注册时间
					$("#enterpriseContact").val(_data.borrowerResVo.enterpriseContact);//回显企业联系人
					$("#enterpriseIntroduce").val(_data.borrowerResVo.enterpriseIntroduce);//回显企业简介
					$("#officeAddress").val(_data.borrowerResVo.officeAddress);//回显企业简介
					$("#industry").val(_data.borrowerResVo.industry);//回显所属行业
					$("#industry1").val(_data.borrowerResVo.industry);//回显所属行业
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
						$("#repayType").val("按月付息到期还本产品");//回显还款方式
						$("#repayType").val("按月付息到期还本");//回显还款方式
					}else if(_data.repayType == "ONETIMEDEBT"){
						$("#repayType").val("一次性还本付息产品");//回显还款方式
						$("#repayType").val("一次性还本付息");//回显还款方式
					}else if(_data.repayType == "MATCHING"){
						$("#repayType").val("等额本息产品");//回显还款方式
						$("#repayType").val("等额本息");//回显还款方式
					}else{
						$("#repayType").val("——");//回显还款方式
					}
					$("#borrowLocation").val(_data.borrowLocation);//回显借款发生地
					
					$("#borrowTitle").val(_data.borrowTitle);
					$("#borrowType").val(_data.borrowType);
					$("#date1").val(_data.wantReleaseTime);
					$("#borrowRate").val(_data.borrowRate);
					$("#projectDetails").val(_data.projectDetails);
					var _data7 = data.data[7];
					if(_data7.length > 0){
						showGua(_data7);
					}
					loadpro(_data1);
					_loadImages(_data2);
					_loadImages(_data5);
					_loadImages1(_data6);
					_showEditInfo(_data3);
				}else{
					bootbox.alert("取得借款人基础信息异常");
				}
			}
		},
		async : false
	});
  

$("#submitBtn").click(function() {
	
	
	if($("#approveResult").val() == "PASS_BUS_MANAGER"){
		  if (!$("#assetAuditInfo").validationEngine('validate')) {
			    return false;
			  }
	}else{
		if($("#auditRecommendations").val() == "" || $("#auditRecommendations").val() == null){
			alert("意见建议不能为空");
			return false;
		}
	}
	
	var formData = $("#assetAuditInfo").serializeObject();
	formData.zijiuer = zijiuer;
	
		$.ajax({
			type : "POST",
			url : "/borrow/review_I_asset_audit/assetAudit",
			data : formData,
			success : function(data) {
				if (data != null && data != "") {
					if (data == "success") {
						bootbox.alert("操作成功", function(){
							window.location.href = "/borrow/review_list.html";
						});
					}else{
						bootbox.alert(data);
					}
				}
			},
			async : false
		});
	  
});

});

//回显图片
function _loadImages(data){
    if (typeof(data) != "undefined"){
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
		}



		if(null != data.fileUrl && ''!= data.fileUrl){
			if(data.mark == "CREDIT"){
				 var newfileUrl = domainUrl + pic + data.fileUrl;
					var _cdImgIdorganizingCode = $("#businessLicenseNo");
					_cdImgIdorganizingCode.attr("href",newfileUrl);
					_cdImgIdorganizingCode.find("img").attr("src",newfileUrl);
					_cdImgIdorganizingCode.find("input").val(data.fileUrl);
			}
			if(data.mark == "CREDIT_RISK"){
			 var newfileUrl = domainUrl + pic + data.fileUrl;
				var _cdImgIdorganizingCode = $("#businessLicenseNo1");
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

//上传图片
$('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#businessLicenseNo2");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
    
});
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
//			ue.setContent(editInfoList[i].mark);
			$("#projectDetails").val(editInfoList[i].mark);
		}
		if(editInfoList[i].extendType == "RISK"){
			$("#safetytext").val(editInfoList[i].mark);
		}
		if(editInfoList[i].extendType == "SECURITY"){
			$("#safetytext2").val(editInfoList[i].mark);
		}
		
	}
	$("#historyReview").append(str);
}
function loadrisk(data){
	  var o = -1;
	  $.each(data, function (k, v) {
		  	o++;
		  	var _fileImgs = $("#fileImgs");
		  	var fileUrl = domainUrl + pic +v.fileUrl;
		    var fileHtml = "";
		    fileHtml +='<div class="file-box">';
		    fileHtml +='    <div class="file">';
		    fileHtml +='   <span class="corner"></span><div class="image">';
		    fileHtml +='   <a id="creditImgUrl'+ o +'" class="fancybox img-responsive" href="'+ fileUrl +'" title="资信及征信查看">';
		    fileHtml +='    <img alt="image" src="'+ fileUrl +'"/>';
		    fileHtml +='    <input type="hidden" id="riskfiles['+ o +']"  value="'+ v.fileUrl +'">';
		    fileHtml +='    </a>';
		    fileHtml +='    </div>';  
		    fileHtml +='   <div class="file-name"><small>资信及征信查看</small></div>';
		    fileHtml +='   </div>';
		    fileHtml +='    </div>';
		    _fileImgs.append(fileHtml);
	  });

}

function loadpro(data){
	  var o = -1;
	  $.each(data, function (k, v) {
		  	o++;
		  	var _fileImgs = $("#profileImgs");
		  	var fileUrl = domainUrl + pic +v.fileUrl;
		    var fileHtml = "";
		    fileHtml +='<div class="file-box">';
		    fileHtml +='    <div class="file">';
		    fileHtml +='   <span class="corner"></span><div class="image">';
		    fileHtml +='   <a id="creditImgUrl'+ o +'" class="fancybox img-responsive" href="'+ fileUrl +'" title="资信及征信查看">';
		    fileHtml +='    <img alt="image" src="'+ fileUrl +'"/>';
		    fileHtml +='    <input type="hidden"  value="'+ v.fileUrl +'">';
		    fileHtml +='    </a>';
		    fileHtml +='    </div>';  
		    fileHtml +='   <div class="file-name"><small>资信及征信查看</small></div>';
		    fileHtml +='   </div>';
		    fileHtml +='    </div>';
		    _fileImgs.append(fileHtml);
	  });

}
function showGua(v){
	var cijiGua = $("#cijiGua");
	var html = "";
	for(var i = 0 ; i < v.length ; i++){
		html +='<table class="table table-bordered" ><tr><td class="input-group-addon">担保人身份证号</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeIdCard+'"></td>';
		html +='<td class="input-group-addon">担保人名称</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeUserName+'"></td></tr>';
		html +='<tr><td class="input-group-addon">担保联系人</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeContract+'"></td>';
		html +='<td class="input-group-addon">担保人年龄</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteeAge+'"></td></tr>';
		html +='<tr><td class="input-group-addon">担保人联系方式</td><td>';
		html +='<input type="text"  class="form-control" readonly="readonly" value="'+v[i].guaranteePhone+'"></td></tr></table>';
	}
	cijiGua.html(html);
}


$("#changeBtn").click(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10"
	};
	tableFun2("/borrow/l_add_loan/borrowerList", srhData);
	// tableFun2("/project/p_add_pro/borrowUserList", srhData);
	$("#myModal2").modal("show");
	myPage2();
});


function tableFun2(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				async : false,
				success : function(data) {
					var html = "";
					if (data.list != null) {
						_pages2 = data.pages;
						$
								.each(
										data.list,
										function(k, v) {
											html += "<tr>";
											html += "<td><label class='radio-inline i-checks'><input style='margin-left:10px' type='radio' name='radio' class='sub_radbox'></label></td>";
											if (v.roleCode == "PERSON") {
												html += "<td>"
														+ v.legalPersonName
														+ "</td>";
											} else {
												html += "<td>"
														+ v.enterpriseName
														+ "</td>";
											}
											var v_userType = null;
											if (v.userType == "PERSON") {
												v_userType = "个人";
											} else if (v.userType == "ENTERPRISE") {
												v_userType = "企业";
											} else {
												v_userType = v.userType;
											}
											html += "<td>" + v_userType + ""
													+ v.roleName + "</td>";
											html += "<td>" + v.legalPersonName
													+ "</td>";
											html += "<td>" + v.userId + "</td>";
											if(v.userCertCode != null && v.userCertCode != ""){
												html += "<td>" + v.userCertCode + "</td>";
											}else{
												html += "<td>——</td>";
											}
											html += "</tr>";
										});
						$("#table2").find("tbody").html(html);
					} else {
						var _table = $('#table2'), tableBodyHtml = '';
						tableBodyHtml += '<tr class="no-records-found">';
						tableBodyHtml += '<td colspan="9" align="center">没有找到匹配的记录</td>';
						tableBodyHtml += '</tr>';
						_table.find("tbody").html(tableBodyHtml);
						$("#tcdPageCode2").hide();
					}
				},
				error : function(data) {
					alert("出错了");
				}
			});
}

//分页2
function myPage2() {
	var $tcdPage = $("#tcdPageCode2");
	$tcdPage.createPage({
		pageCount : _pages2,
		current : 1,
		backFn : function(p) {
			var legalPersonName = $("#legalPersonName").val();
			var srhData4 = {
				"pageNo" : p,
				"pageSize" : "10",
				"legalPersonName" : legalPersonName
			};

			tableFun2("/borrow/l_add_loan/borrowerList", srhData4);
			// tableFun2("/project/p_add_pro/borrowUserList", srhData4);
		}
	});
}
// 担保人查询条件查询
$("#srhSmtBtn2").click(function() {
	var legalPersonName = $("#legalPersonName").val();
	var srhData2 = {
		"pageNo" : "1",
		"pageSize" : "10",
		"legalPersonName" : legalPersonName
	};
	tableFun2("/borrow/l_add_loan/borrowerList", srhData2);
	// tableFun2("/project/p_add_pro/borrowUserList", srhData2);
	myPage2();
});

// 选择选中的担保人
$("#choose2").click(function() {
	var obj = $("#table2").find("input[type='radio']:checked");
	var releaseUserId = obj.parents("tr").find("td:nth-child(2)").text();// 找到位置
	$("#releaseUserId").val(releaseUserId);
	zijiuer = obj.parents("tr").find("td:nth-child(5)").text();
	$("#userCertCode").val(obj.parents("tr").find("td:nth-child(6)").text());
	$('#myModal2').modal('hide');
});

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