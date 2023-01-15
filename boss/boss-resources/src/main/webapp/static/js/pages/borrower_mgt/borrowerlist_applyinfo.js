var threeNo;
var oneNO;
var rcType;
var staticUrl = domainUrl;
var _fileImgs = $("#fileImgs");
var isinit = _getParamsObject().isInit;
if(isinit != 'yes'){
	$(".jin").attr('readOnly',true);
	$(".jins").attr('disabled',true);
}else{
	$(".jin").attr('readOnly',false);
	$(".jins").attr('disabled',false);
}
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
	_modalFm1 =  $('#edit_borrowerForm');
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
  initFileInput("fileinput2", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput3", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput4", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput5", "/boss/imageUpload?bizeCode=pic","1",false);
  initFileInput("fileinput6", "/boss/imageUpload?bizeCode=pic","1",false);
  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

if( _getParamsObject().type=="edit"){
	var userId =  _getParamsObject().userId;
	$.ajax({
		type : "POST",
//		url : "/borrower/borrowerlist/getbaseInfo/" + userId,
		url : "/borrower/borrowerlist/get/" + userId,
		data : {"userId" : userId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
                    var businessObject = data.data;
                    getUserInfo(userId);
                    var id = businessObject.id;
                    var email = businessObject.email;  //邮箱
                    var enterpriseContact = businessObject.enterpriseContact;  //企业联系人
                    var corporateContactsMobile = businessObject.corporateContactsMobile;  //联系人手机号
                    var legalPersonName = businessObject.legalPersonName;  //法人姓名
                    var enterpriseName = businessObject.enterpriseName;  //企业名称
                    var registeredCapital = businessObject.registeredCapital;  //注册资金
                    var legalPersonCertNo = businessObject.legalPersonCertNo;  //法人身份证号
                    var legalPersonCertNoFile = businessObject.legalPersonCertNoFile;  //法人身份证复印件
                    var legalPersonCertNoFileName = businessObject.legalPersonCertNoFileName;
                    var businessLicenseNo = businessObject.businessLicenseNo; //营业执照号
                    var businessLicenseFile = businessObject.businessLicenseFile; //营业执照上传
                    var businessLicenseFileName = businessObject.businessLicenseFileName;
                    var taxId = businessObject.taxId; //税务登记证号
                    var taxFile = businessObject.taxFile; //税务登记证号
                    var taxFileName = businessObject.taxFileName;
                    var unifiedSocialCreditCode = businessObject.unifiedSocialCreditCode;
                    var organizingCode = businessObject.organizingCode; //组织机构代码证号
                    var organizingCodeFile = businessObject.organizingCodeFile; //组织机构代码证号
                    var organizingCodeFileName = businessObject.organizingCodeFileName;
                    var bankAccount = businessObject.bankAccount; //企业银行账号
                    var bankAccountFile = businessObject.bankAccountFile; //企业银行账号
                    var bankAccountFileName = businessObject.bankAccountFileName;
                    var bankAccountName = businessObject.bankAccountName; //企业银行账户名
                    var branchBank = businessObject.branchBank; //支行
                    var openingBankPermit = businessObject.openingBankPermit; //开户银行许可证号
                    var officeAddress = businessObject.officeAddress; //办公地址
                    var openAccountBank = businessObject.openAccountBank; //开户银行
                    var registerCertType = businessObject.registerCertType; //注册类型
                    rcType = registerCertType;
                    $("#bwId").val(id);
                    $("#email").val(email);
                    $("#enterpriseContact").val(enterpriseContact);
                    $("#corporateContactsMobile").val(corporateContactsMobile);
                    $("#legalPersonName").val(legalPersonName);
                    $("#enterpriseName").val(enterpriseName);
                    $("#legalPersonCertNo").val(legalPersonCertNo);
                    $("#registeredCapital").val(registeredCapital);
                    $("#legalPersonCertNoFile,.legalPersonCertNoFile").val(legalPersonCertNoFile);
                    $("#legalPersonCertNoFileName").val(legalPersonCertNoFileName);
                    $("#businessLicenseNo").val(businessLicenseNo);
                    $("#businessLicenseFile,.businessLicenseFile").val(businessLicenseFile);
                    $("#businessLicenseFileName").val(businessLicenseFileName);
                    $("#taxId").val(taxId);
                    $("#date1").val(moment(businessObject.establishedTime).format("YYYY-MM-DD"));//注册时间
                    $("#taxFile,.taxFile").val(taxFile);
                    $("#taxFileName").val(taxFileName);
                    $("#organizingCode").val(organizingCode);
                    $("#organizingCodeFile,.organizingCodeFile").val(organizingCodeFile);
                    $("#organizingCodeFileName").val(organizingCodeFileName);
                    $("#bankAccount").val(bankAccount);
                    $("#bankAccountFile,.bankAccountFile").val(bankAccountFile);
                    $("#bankAccountFileName").val(bankAccountFileName);
                    $("#bankAccountName").val(bankAccountName);
                    $("#unifiedSocialCreditCode").val(unifiedSocialCreditCode);
                    $("#branchBank").val(branchBank);
                    $("#openAccountBankSelect").val(openAccountBank);
                    $("#openingBankPermit").val(openingBankPermit);
                    $("#officeAddress").val(officeAddress);
                    //$("input[name='registerCertType']").val(registerCertType);
                    $("#"+registerCertType).iCheck('check');
                    if(legalPersonCertNoFile){
                        var fileUrl = staticUrl + pic +legalPersonCertNoFile;
                        var fileHtml = "";
                        fileHtml +='    <tr class="fl-item legalPersonCertNo">';
                        fileHtml +='    <td style="width:60%;">';
                        fileHtml += '<span class="fl-name">' + legalPersonCertNoFileName + '</span>';
                        fileHtml +='   </td>';
                        fileHtml +='    <td style="width:10%;"><a href="'+ fileUrl +'" target="_blank" class="fl-view">查看</a></td>';
                        fileHtml +='    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'legalPersonCertNo\')" class="fl-del">删除</a></td>';
                        fileHtml +='   </tr>';
                        _fileImgs.append(fileHtml);
                    }
                    if(businessLicenseFile){
                        var fileUrl2 = staticUrl + pic +businessLicenseFile;
                        var fileHtml = "";
                        fileHtml +='    <tr class="fl-item businessLicense">';
                        fileHtml +='    <td style="width:60%;">';
                        fileHtml += '<span class="fl-name">' + businessLicenseFileName + '</span>';
                        fileHtml +='   </td>';
                        fileHtml +='    <td style="width:10%;"><a href="'+ fileUrl2 +'" target="_blank" class="fl-view">查看</a></td>';
                        fileHtml +='    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'businessLicense\')" class="fl-del">删除</a></td>';
                        fileHtml +='   </tr>';
                        _fileImgs.append(fileHtml);
                    }
                    if(taxFile){
                        var fileUrl3 = staticUrl + pic +taxFile;
                        var fileHtml = "";
                        fileHtml +='    <tr class="fl-item tax">';
                        fileHtml +='    <td style="width:60%;">';
                        fileHtml += '<span class="fl-name">' + taxFileName + '</span>';
                        fileHtml +='   </td>';
                        fileHtml +='    <td style="width:10%;"><a href="'+ fileUrl3 +'" target="_blank" class="fl-view">查看</a></td>';
                        fileHtml +='    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'tax\')" class="fl-del">删除</a></td>';
                        fileHtml +='   </tr>';
                        _fileImgs.append(fileHtml);
                    }
                    if(organizingCodeFile){
                        var fileUrl4 = staticUrl + pic +organizingCodeFile;
                        var fileHtml = "";
                        fileHtml +='    <tr class="fl-item organizingCode">';
                        fileHtml +='    <td style="width:60%;">';
                        fileHtml += '<span class="fl-name">' + organizingCodeFileName + '</span>';
                        fileHtml +='   </td>';
                        fileHtml +='    <td style="width:10%;"><a href="'+ fileUrl4 +'" target="_blank" class="fl-view">查看</a></td>';
                        fileHtml +='    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'organizingCode\')" class="fl-del">删除</a></td>';
                        fileHtml +='   </tr>';
                        _fileImgs.append(fileHtml);
                    }
                    if(bankAccountFile){
                        var fileUrl5 = staticUrl + pic +bankAccountFile;
                        var fileHtml = "";
                        fileHtml +='    <tr class="fl-item bankAccount">';
                        fileHtml +='    <td style="width:60%;">';
                        fileHtml += '<span class="fl-name">' + bankAccountFileName + '</span>';
                        fileHtml +='   </td>';
                        fileHtml +='    <td style="width:10%;"><a href="'+ fileUrl5 +'" target="_blank" class="fl-view">查看</a></td>';
                        fileHtml +='    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'bankAccount\')" class="fl-del">删除</a></td>';
                        fileHtml +='   </tr>';
                        _fileImgs.append(fileHtml);
                    }
				}else{
					bootbox.alert("取得借款人基础信息异常");
				}
			}
		},
		async : false
	});
  
}

//选择三证合一
rgtShow();
$("input[name='registerCertType']").on('ifChecked', function(event){
    rgtShow();
});


$("#editSubmit").click(function() {
		$.ajax({
			type : "POST",
			url : "/borrower/borrowerlist/add",
			data : $("#edit_borrowerForm").serialize(),
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
if(_getParamsObject().isInit == "yse"){
	  $("input").removeAttr("readonly");
	  $("#openAccountBankSelect").removeAttr("disabled"); 
}


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
};





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
    response = eval('(' + response + ')');
    var fileUrl = staticUrl + pic +response.fileName;
    $("input[name='legalPersonCertNoFile']").val(response.fileName);
    $(".legalPersonCertNoFile").val(response.fileName);
    $("input[name='legalPersonCertNoFileName']").val(files[0].name);
    var fltm = $("#fileImgs .legalPersonCertNo");
    if(fltm.length > 0){
        fltm.find(".fl-name").html(files[0].name);
        fltm.find(".fl-view").attr("href",fileUrl);
    }else{
        var fileHtml = "";
        fileHtml +='    <tr class="fl-item legalPersonCertNo">';
        fileHtml +='    <td style="width:60%;">';
        fileHtml += '<span class="fl-name">' + files[0].name + '</span>';
        fileHtml +='   </td>';
        fileHtml +='    <td style="width:10%;"><a href="'+ fileUrl +'" target="_blank" class="fl-view">查看</a></td>';
        fileHtml +='    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'legalPersonCertNo\')" class="fl-del">删除</a></td>';
        fileHtml +='   </tr>';
        _fileImgs.append(fileHtml);
    }

});

$('#fileinput2').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    response = eval('(' + response + ')');
    var fileUrl = staticUrl + pic +response.fileName;
    $("input[name='businessLicenseFile']").val(response.fileName);
    $(".businessLicenseFile").val(response.fileName);
    $("input[name='businessLicenseFileName']").val(files[0].name);
    var fltm = $("#fileImgs .businessLicense");
    if(fltm.length > 0){
        fltm.find(".fl-name").html(files[0].name);
        fltm.find(".fl-view").attr("href",fileUrl);
    }else {
        var fileHtml = "";
        fileHtml += '    <tr class="fl-item businessLicense">';
        fileHtml += '    <td style="width:60%;">';
        fileHtml += '<span class="fl-name">' + files[0].name + '</span>';
        fileHtml += '   </td>';
        fileHtml += '    <td style="width:10%;"><a href="' + fileUrl + '" target="_blank" class="fl-view">查看</a></td>';
        fileHtml += '    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'businessLicense\')" class="fl-del">删除</a></td>';
        fileHtml += '   </tr>';
        _fileImgs.append(fileHtml);
    }
});
$('#fileinput3').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    response = eval('(' + response + ')');
    var fileUrl = staticUrl + pic +response.fileName;
    $("input[name='taxFile']").val(response.fileName);
    $(".taxFile").val(response.fileName);
    $("input[name='taxFileName']").val(files[0].name);
    var fltm = $("#fileImgs .tax");
    if(fltm.length > 0){
        fltm.find(".fl-name").html(files[0].name);
        fltm.find(".fl-view").attr("href",fileUrl);
    }else {
        var fileHtml = "";
        fileHtml += '    <tr class="fl-item tax">';
        fileHtml += '    <td style="width:60%;">';
        fileHtml += '<span class="fl-name">' + files[0].name + '</span>';
        fileHtml += '   </td>';
        fileHtml += '    <td style="width:10%;"><a href="' + fileUrl + '" target="_blank" class="fl-view">查看</a></td>';
        fileHtml += '    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'tax\')" class="fl-del">删除</a></td>';
        fileHtml += '   </tr>';
        _fileImgs.append(fileHtml);
    }
});
$('#fileinput4').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    response = eval('(' + response + ')');
    var fileUrl = staticUrl + pic +response.fileName;
    $("input[name='organizingCodeFile']").val(response.fileName);
    $(".organizingCodeFile").val(response.fileName);
    $("input[name='organizingCodeFileName']").val(files[0].name);
    var fltm = $("#fileImgs .organizingCode");
    if(fltm.length > 0){
        fltm.find(".fl-name").html(files[0].name);
        fltm.find(".fl-view").attr("href",fileUrl);
    }else {
        var fileHtml = "";
        fileHtml += '    <tr class="fl-item organizingCode">';
        fileHtml += '    <td style="width:60%;">';
        fileHtml += '<span class="fl-name">' + files[0].name + '</span>';
        fileHtml += '   </td>';
        fileHtml += '    <td style="width:10%;"><a href="' + fileUrl + '" target="_blank" class="fl-view">查看</a></td>';
        fileHtml += '    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'organizingCode\')" class="fl-del">删除</a></td>';
        fileHtml += '   </tr>';
        _fileImgs.append(fileHtml);
    }
});
$('#fileinput5').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    response = eval('(' + response + ')');
    var fileUrl = staticUrl + pic +response.fileName;
    $("input[name='bankAccountFile']").val(response.fileName);
    $(".bankAccountFile").val(response.fileName);
    $("input[name='bankAccountFileName']").val(files[0].name);
    var fltm = $("#fileImgs .bankAccount");
    if(fltm.length > 0){
        fltm.find(".fl-name").html(files[0].name);
        fltm.find(".fl-view").attr("href",fileUrl);
    }else {
        var fileHtml = "";
        fileHtml += '    <tr class="fl-item bankAccount">';
        fileHtml += '    <td style="width:60%;">';
        fileHtml += '<span class="fl-name">' + files[0].name + '</span>';
        fileHtml += '   </td>';
        fileHtml += '    <td style="width:10%;"><a href="' + fileUrl + '" target="_blank" class="fl-view">查看</a></td>';
        fileHtml += '    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'bankAccount\')" class="fl-del">删除</a></td>';
        fileHtml += '   </tr>';
        _fileImgs.append(fileHtml);
    }
});
$('#fileinput6').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
    response = eval('(' + response + ')');
    var fileUrl = staticUrl + pic +response.fileName;
    $("input[name='businessLicenseFile']").val(response.fileName);
    $(".businessLicenseFile").val(response.fileName);
    $("input[name='businessLicenseFileName']").val(files[0].name);
    var fltm = $("#fileImgs .businessLicense");
    if(fltm.length > 0){
        fltm.find(".fl-name").html(files[0].name);
        fltm.find(".fl-view").attr("href",fileUrl);
    }else {
        var fileHtml = "";
        fileHtml += '    <tr class="fl-item businessLicense">';
        fileHtml += '    <td style="width:60%;">';
        fileHtml += '<span class="fl-name">' + files[0].name + '</span>';
        fileHtml += '   </td>';
        fileHtml += '    <td style="width:10%;"><a href="' + fileUrl + '" target="_blank" class="fl-view">查看</a></td>';
        fileHtml += '    <td style="width:10%;"><a href="javascript:" onclick="flDel(\'businessLicense\')" class="fl-del">删除</a></td>';
        fileHtml += '   </tr>';
        _fileImgs.append(fileHtml);
    }
});
function getUserInfo(userId){
	$.ajax({
		type : "POST",
		url : "/borrower/borrowerlist/getUserInfo",
		data : {"userId":userId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var data1 = data.data[0];
					var data2 = data.data[1];
	                 $("#userId").val(data1.id);
					$("#legalPersonMobile").val(data1.registerMobile);
					if(data1.registerMobile != "" && data1.registerMobile != null){
						$("#userName").val(data1.registerMobile);
					}else{
						$("#userName").val('——');
					}
					if(data1.userType != "" && data1.userType != null){
						if(data1.userType == 'ENTERPRISE'){
							$("#userAtt").val('企业');	
						}else if(data1.userType == 'PERSON'){
							$("#userAtt").val('个人');	
						}else{
							$("#userAtt").val('——');	
						}
					}else{
						$("#userAtt").val('——');
					}
					if(data2.roleCode != "" && data2.roleCode != null){
						$("#userRole").val(data2.roleCode);
					}else{
						$("#userRole").val('——');
					}
					
				}
			}else{
					bootbox.alert(data.msg);
				}
		},
		async : false
	});
};
$("#borrowerSubmit").click(function() {
    if (!$("#addBorrowerForm").validationEngine('validate')) {
        return false;
    }
    var formData = $("#addBorrowerForm").serializeObject();
    rcType = $("input[name='registerCertType']:checked").val();
    formData.registerCertType = rcType;
    formData.userRole = $("#userRole").val();
    $.ajax({
        type : "POST",
        url: "/borrower/borrowerlist/add",
        dataType: "json",
        async : false,
        data : formData,
        success : function(data) {
            var resultCode = data.resCode;
            var resultCodeMsg = data.msg; //返回信息
            if (resultCode === 0) {
                bootbox.alert("操作成功", function(){
                	window.location.href = "/borrower/borrowerlist.html";
                });
            }else{
                bootbox.alert(resultCodeMsg);
            }
        }, error: function (data) {  //token过期处理
            tkFailFun(data);
        }
    });
});



//删除
var flDel = function(ele){
    $("."+ele).remove();
    $("."+ ele +"Fm").fileinput('reset');
    $("."+ ele +"File").val("");
    $("#"+ ele +"File").val("");
    $("#"+ ele +"FileName").val("");
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