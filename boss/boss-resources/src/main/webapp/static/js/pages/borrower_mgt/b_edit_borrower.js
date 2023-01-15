var threeNo;
var oneNO;
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
  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

if( _getParamsObject().type=="edit"){
	var borrowerId =  _getParamsObject().borrowerId;
	$.ajax({
		type : "POST",
//		url : "/borrower/borrowerlist/getbaseInfo/" + borrowerId,
		url : "/borrower/borrowerlist/get/" + borrowerId,
		data : {"borrowerId" : borrowerId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data = data.data;
	$("#enterpriseContact").val(_data.enterpriseContact);//编辑时回显企业联系人
					$("#corporateContactsMobile").val(_data.corporateContactsMobile);//编辑时回显企业联系人手机号
					$("#edit_borrowerId").val(_data.id);//编辑时回显borrowerId
					$("#legalPersonMobile").val(_data.legalPersonMobile);//法人手机号码
					$("#registeredCapital").val(_data.registeredCapital);//注册资金
					$("#legalPersonCertNo").val(_data.legalPersonCertNo);//法人身份证号
					$("#email").val(_data.email);//邮箱
					var registerCertType= _data.registerCertType; //三证合一类型
	$("#unifiedSocialCreditCode").val(_data.unifiedSocialCreditCode);//企业统一社会信用代码
					$("#date1").val(moment(_data.establishedTime).format("YYYY-MM-DD"));//注册时间
					$("input[type='radio'][name='registerCertType'][value="+registerCertType+"]").attr("checked", "checked").parent().addClass("checked");
					$("#enterpriseName").val(_data.enterpriseName);//企业名称
					$("#legalPersonName").val(_data.legalPersonName);//法人姓名
					$("#businessLicenseNo").val(_data.businessLicenseNo);//营业执照编号
					$("#taxId").val(_data.taxId);//税务登记号
					$("#organizingCode").val(_data.organizingCode);//组织机构代码
					$("#bankAccount").val(_data.bankAccount);//企业银行账号
					$("#bankAccountName").val(_data.bankAccountName);//企业银行账户名
					$("#bankNo").val(_data.bankNo);//联行号
	$("#institutionalCreditCode").val(_data.institutionalCreditCode);//编辑时回显机构信用代码
					$("#openingBankPermit").val(_data.openingBankPermit);//编辑时回显开户银行许可证号
					showOpenAccountBank(_data.openAccountBank);//开户银行回显
					$("#branchBank").val(_data.branchBank);//支行名称
					$("#officeAddress").val(_data.officeAddress);//办公地址
					$("#enterpriseIntroduce").val(_data.enterpriseIntroduce);//企业基本介绍
					$("#enterpriseBusiness").val(_data.enterpriseBusiness);//企业经营范围
					_loadImages(_data);
					_showEditInfo(_data);
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
	  if (!$("#edit_borrowerForm").validationEngine('validate')) {
		    return false;
		  }
	  
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
 if($("#unifiedSocialCreditCode")[0].value == "" || $("#unifiedSocialCreditCode")[0].value == null || $("#unifiedSocialCreditCode")[0].value == undefined){
			  $("#unifiedSocialCreditCode").removeAttr("readonly"); 
		  }else{
//			  $("#unifiedSocialCreditCode").attr("readonly","readonly");
		  }
		  $(".txid").hide();//将税务登记证号一整行隐藏、将用于回显的税务登记证图片隐藏
		  $(".oicd").hide();//将组织机构代码证号一整行隐藏、将用于回显的组织机构代码证图片隐藏
  $("#oneNo").show();
		  $("#threeNo").hide();
	  }else{
if($("#businessLicenseNo")[0].value == "" || $("#businessLicenseNo")[0].value == null || $("#businessLicenseNo")[0].value == undefined){
			  $("#businessLicenseNo").removeAttr("readonly"); 
		  }else{
//			  $("#unifiedSocialCreditCode").attr("readonly","readonly");
		  }
		  $(".txid").show();
		  $(".oicd").show();
 $("#oneNo").hide();
		  $("#threeNo").show();
	  }
	  
}



//回显图片
function _loadImages(data){
  
    if(null != data.legalPersonCertNoFile && ''!= data.legalPersonCertNoFile){
    	var legalPersonIDcardfileUrl = domainUrl + pic + data.legalPersonCertNoFile;
        var _cdImgIdlegalPersonIDcard = $("#legalPersonIDcardShow");
        _cdImgIdlegalPersonIDcard.attr("href",legalPersonIDcardfileUrl);
        _cdImgIdlegalPersonIDcard.find("img").attr("src",legalPersonIDcardfileUrl);
        _cdImgIdlegalPersonIDcard.find("input").val(data.legalPersonCertNoFile);
   document.getElementById("uploadForm1").style.display="none";
    }else{
    	$("#legalPersonCertNo").removeAttr("readonly"); 
    }
    
    if(null != data.businessLicenseFile && ''!= data.businessLicenseFile){
        var businessLicenseNofileUrl = domainUrl + pic + data.businessLicenseFile;
        var _cdImgIdbusinessLicenseNo = $("#businessLicenseNoShow");
        _cdImgIdbusinessLicenseNo.attr("href",businessLicenseNofileUrl);
        _cdImgIdbusinessLicenseNo.find("img").attr("src",businessLicenseNofileUrl);
        _cdImgIdbusinessLicenseNo.find("input").val(data.businessLicenseFile);
  document.getElementById("uploadForm2").style.display="none"; 
        document.getElementById("uploadForm22").style.display="none"; 
    }else{
    	$("#businessLicenseNo").removeAttr("readonly"); 
    }
    
    if(null != data.taxFile && ''!= data.taxFile){
        var taxIdfileUrl = domainUrl + pic + data.taxFile;
        var _cdImgIdtaxId = $("#taxIdShow");
        _cdImgIdtaxId.attr("href",taxIdfileUrl);
        _cdImgIdtaxId.find("img").attr("src",taxIdfileUrl);
        _cdImgIdtaxId.find("input").val(data.taxFile);
       document.getElementById("uploadForm3").style.display="none"; 
    }else{
    	$("#taxId").removeAttr("readonly"); 
    
    }
    
    if(null != data.organizingCodeFile && ''!= data.organizingCodeFile){
    	var organizingCodeFilefileUrl = domainUrl + pic + data.organizingCodeFile;
        var _cdImgIdorganizingCode = $("#organizingCodeShow");
        _cdImgIdorganizingCode.attr("href",organizingCodeFilefileUrl);
        _cdImgIdorganizingCode.find("img").attr("src",organizingCodeFilefileUrl);
        _cdImgIdorganizingCode.find("input").val(data.organizingCodeFile);
 document.getElementById("uploadForm4").style.display="none"; 
    }else{
    	$("#organizingCode").removeAttr("readonly"); 
    }
    
    if(null != data.bankAccountFile && ''!= data.bankAccountFile){
    	var bankopenfileUrl = domainUrl + pic + data.bankAccountFile;
        var _cdImgIdbankopen = $("#bankopenShow");
        _cdImgIdbankopen.attr("href",bankopenfileUrl);
        _cdImgIdbankopen.find("img").attr("src",bankopenfileUrl);
        _cdImgIdbankopen.find("input").val(data.bankAccountFile);
        document.getElementById("uploadForm5").style.display="none"; 
    }else{
    	$("#bankAccount").removeAttr("readonly"); 
    }
    
}

function _showEditInfo(data){
	var editInfoList = data.logs;
	var str = "";
	var strtwo = "";
	for(var i=0; i<editInfoList.length; i++){
		str += "<tr>";
        str += "<td class='input-group-addon'>操作内容</td>";
        str += "<td colspan='1'>";
        str += "<textarea type='text' readonly='readonly' class='form-control' style='height:100px;'> "+ editInfoList[i].content+"</textarea>";
        str += "</td>";
        str += "<td class='input-group-addon'>操作人</td>";
        str += "<td colspan='1'>"
        str += "<span>" +editInfoList[i].editedBy+"</span>";
        str += "</td></tr>";
	}
	$("#borrower_edit_body").append(str);
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


$('#fileinput').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#legalPersonIDcardShow");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
    
});

$('#fileinput2').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
        response = data.response, reader = data.reader;
	    var result = eval('(' + response + ')');
	        var fileUrl = domainUrl + pic +result.fileName;
		    var _cdImgId = $("#businessLicenseNoShow");
		        _cdImgId.attr("href",fileUrl);
			    _cdImgId.find("img").attr("src",fileUrl);
			        _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
			
			});
$('#fileinput6').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#businessLicenseNoShow");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
	
});
$('#fileinput3').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#taxIdShow");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
	
});
$('#fileinput4').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#organizingCodeShow");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
	
});
$('#fileinput5').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic +result.fileName;
    var _cdImgId = $("#bankopenShow");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);//点击上传图片按钮后，将隐藏域设置值，用于将图片url传递给后台
	
});





function showOpenAccountBank(openAccoutBank){
	$.ajax({
		type : "POST",
		url : "/borrower/borrowerlist/getBankInfo",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var str = "<option value=''>--请选择--</option>";
					var _list = data.data.businessObject.list;
					for(var key = 0; key < _list.length; key++){
						var bankName = _list[key].bankName;
						var bankCode = _list[key].bankCode;
						if(bankCode == openAccoutBank){
							str += "<option value= '"+bankCode+"' selected = 'selected'>" +bankName+"</option>";
						}else{
							str += "<option value= '"+bankCode+"'>" +bankName+"</option>";
						}
						
					}
					$("#openAccountBankSelect").html(str);
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
}