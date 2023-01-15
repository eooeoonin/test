var amount1 = 0;
var amount2 = 0;
var amount3 = 0;
var amount4 = 0;
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); // 匹配目标参数
    if (r != null)
        return unescape(r[2]);
    return null; // 返回参数值
};
//var ue = UE.getEditor('safetytext2');
//var ue1 = UE.getEditor('safetytext');
var ue2 = UE.getEditor('projectText');
//ue.ready(function() {
//    //设置编辑器的内容
//	UE.getEditor('safetytext2').setDisabled('fullscreen');
//});
//ue1.ready(function() {
//    //设置编辑器的内容
//	UE.getEditor('safetytext').setDisabled('fullscreen');
//});
ue2.ready(function() {
    //设置编辑器的内容
	UE.getEditor('projectText').setDisabled('fullscreen');
});
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
$("#tab-1").show();
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
    initFileInput("fileinput", "/boss/imageUpload?bizeCode=pic","1",false);
    //图片预览
    $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

    $.ajax({
        type : "POST",
        url : "/borrow/repayPlan_detail/audit",
        data : {"borrowId" : borrowId},
        success : function(data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
                    var _data = data.data[0];
                    var _data1= data.data[1];
                    var _data2 = data.data[2].businessObject;
                    var _data3 = data.data[3];
                    var _data4 = data.data[4];

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
					if(_data.riskGrade != null && _data.riskGrade != ""){
						$("#riskGrade").val(_data.riskGrade);
					}
					$("#borrowerdebt").val(_data.borrowerResVo.debt);
					$("#borrowercredit").val(_data.borrowerResVo.credit);
					$("#borrowerotherPlatForm").val(_data.borrowerResVo.otherPlatForm);
					$("#borrowerlawsuit").val(_data.borrowerResVo.lawsuit);
					
					$("#guaranteeguaranteeCredit").val(_data.guaranteeResVo.guaranteeCredit);
					$("#guaranteeguaranteeLawsuit").val(_data.guaranteeResVo.guaranteeLawsuit);
					
					$("#guarantyOwner").val(_data.guaranteeResVo.guarantyOwner);
					$("#guarantyAge").val(_data.guaranteeResVo.guarantyAge);
					$("#guarantyCode").val(_data.guaranteeResVo.guarantyCode);
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
                    $("#guarantyValue").val(_data.guarantyValue.amount);
                    $("#guarantyRate").val(_data.guarantyRate);
                    $("#borrowedAmount").val(_data.borrowAmount.amount / 10000);
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
                    _showEditInfo(_data3);
                    loadrisk(_data4);

                    /***
                     *功能说明：打印报告
                     ***/
                    var _printIcon = $("#printIcon");
                    _printIcon.unbind("click");
                    _printIcon.bind("click",function () {
                        var _reportWarp = $("#reportWarp");
                        html2canvas(_reportWarp,{

                            allowTaint: false,
                            taintTest: false,
                            useCORS: true,
                            width: _reportWarp.outerWidth(),
                            height: _reportWarp.outerHeight()+180,
                            background:"#fff",
                            onrendered: function(canvas) {
                                //document.body.appendChild(canvas);
                            }
                        }).then(function(canvas) {
                            var url =canvas.toDataURL("image/*");
                            var imgDiv = '<img src="'+ url +'" id="ptImg" width="672" />';
                            $("body").append(imgDiv);
                            $("#ptImg").print().remove();
                        });
                    });

                }else{
                    bootbox.alert("取得借款人基础信息异常");
                }
            }
        },
        async : false
    });


});

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
        if(editInfoList[i].extendType == "BUS_MANAGER" || editInfoList[i].extendType == "FIRST_CHECK" || editInfoList[i].extendType == "RECHECK" || editInfoList[i].extendType == "FINANCE" || editInfoList[i].extendType == "FINAL_CHECK"){
            if(isTrue){
                isTrue = false;
                style = "style='background-color:#ffad86;'";
            }else{
                isTrue = true;
                style = "style='background-color:#c2ff68;'";
            }
            str += "<tr>";
            str += "<td class='input-group-addon' "+style+">审核环节</td>";
            str += "<td width='65%' "+style+">"
            str += "<span >" +editInfoList[i].borrowCode+"</span>";
            str += "<td class='input-group-addon' "+style+" >审核结果</td>";
            str += "<td  "+style+">"
            str += "<span >" +editInfoList[i].status+"</span>";
            str += "<tr/>";
            str += "<tr>";
            str += "<td class='input-group-addon'>审核建议</td>";
            str += "<td colspan='3'>";
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
            str += "<textarea type='text' readonly='readonly'  class='form-control' style='height:100px;resize:none'> "+ editInfoList[i].mark+"</textarea>";
            str += "</td>";
            str += "</tr>";
            str += "<tr>";
            str += "<td class='input-group-addon'>操作人</td>";
            str += "<td >"
            str += "<span>" +editInfoList[i].editedBy+"</span>";
            str += "<td class='input-group-addon'>操作时间</td>";
            str += "<td>"
            str += "<span>" +editInfoList[i].createTime+"</span>";
            str += "<tr/>";
        }
        if(editInfoList[i].extendType == "PROJECT"){
            $("#projectText").html(editInfoList[i].mark);
        }
//        if(editInfoList[i].extendType == "RISK"){
//            $("#safetytext").html(editInfoList[i].mark);
//        }
//        if(editInfoList[i].extendType == "SECURITY"){
//            $("#safetytext2").html(editInfoList[i].mark);
//        }

    }
    $("#historyReview").append(str);
}

$("#addLoan").click(function(){
    if (!$("#firstAuditInfo").validationEngine('validate')) {
        return false;
    }
    var mark = $('#enterpriseIntroduce1').val();
    var reviewCode = $('#finalCheck').val();
    var editBy = $('#editBy').text();
    $.ajax({
        type : "POST",
        url : "/borrow/repayPlan_detail/finalReview",
        data : {'id':borrowId,'mark':mark,'reviewCode':reviewCode,'editBy':editBy,'borrowExtendType':'FINAL_CHECK'},
        success : function(data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
                    bootbox.alert("操作成功", function(){
                        location.href = "/borrow/finalReview_list.html";
                    });
                }else{
                    bootbox.alert(data.msg);
                }
            }
        },
        async : false
    });
});

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
function loadrisk(data){
    var o = -1;
    $.each(data, function (k, v) {
        o++;
        var _fileImgs = $("#fileImgs");
        var fileUrl = domainUrl + pic +v.fileUrl;
        var fileHtml = "";
        fileHtml +='<div style="margin-left:15px;">';
        fileHtml +='   <a target="_blank" id="creditImgUrl'+ o +'" class="fancybox img-responsive" href="'+ fileUrl +'">';
        fileHtml +='    <input type="hidden" id="riskfiles['+ o +']"  value="'+ v.fileUrl +'">';
        fileHtml +=v.title+'</a>';
        fileHtml +='</div>';
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
        fileHtml +='<div style="margin-left:15px;">';
        fileHtml +='   <a target="_blank" id="creditImgUrl'+ o +'" class="fancybox img-responsive" href="'+ fileUrl +'">';
        fileHtml +='    <input type="hidden"  value="'+ v.fileUrl +'">';
        fileHtml +=v.title+'</a>';
        fileHtml +='</div>';
        _fileImgs.append(fileHtml);
    });

}
$("#tab1").click(function(){
    $("#tab-1").show();
    $("#tab-2").hide();

})
$("#tab2").click(function(){
    amount1 = 0;
    amount2 = 0;
    amount3 = 0;
    amount4 = 0;
    $("#tab-1").hide();
    $("#tab-2").show();
    $.ajax({
        type: "POST",
        url:"/borrow/repayList/borrowerDetail",
        data:{
            "borrowId":borrowId
        },
        async: false,
        success: function(responseMsg) {
            //获取数据
            var html = "";
            $.each(responseMsg.borrowRepayPlans, function (k, v) {
                var principalAmount = parseFloat(v.principalAmount.amount);
                var interestAmount = v.interestAmount.amount*1;
                var overduePenaltyAmount = v.overduePenaltyAmount.amount;
                var borrowManagerFeeAmount = v.borrowManagerFeeAmount.amount;
                var penalty = accAdd(overduePenaltyAmount,borrowManagerFeeAmount);
                var total = accAdd(accAdd(principalAmount,interestAmount),penalty);
                amount1 = accAdd(amount1,principalAmount);
                amount2 = accAdd(amount2,interestAmount);
                amount3 = accAdd(amount3,penalty);
                amount4 = accAdd(amount4,total);
                html +="<tr>";
                html +="<td>"+v.phase+"</a></td>";
                html +="<td>"+principalAmount.toFixed(2)+"</td>";
                html +="<td>"+interestAmount.toFixed(2)+"</td>";
                html +="<td>"+overduePenaltyAmount.toFixed(2)+"</td>";
                html +="<td>"+borrowManagerFeeAmount.toFixed(2)+"</td>";
                html +="<td>"+(v.repayTime+"").substr(0, 10)+"</td>";
                html +="<td>"+(v.interestEndDate+"").substr(0, 10)+"</td>";
                if(v.status=="INIT"){
                    html +="<td>待还款</td>";
                    html +="<td>——</td>";
                }else if(v.status=="OVERDUE"){
                    var a = getNowFormatDate();
                    html +="<td>逾期中</td>";
                    html +="<td>"+DateDiff(v.interestEndDate.substr(0, 10),a.substr(0, 10))+"天</td>";
                }else{
                    html +="<td>已还款 </td>";
                    if((v.repayTime+"").substr(0, 10) > v.interestEndDate.substr(0, 10)){
                        html +="<td>"+DateDiff(v.interestEndDate.substr(0, 10),(v.repayTime+"").substr(0, 10))+"天</td>";
                    }else{
                        html +="<td>——</td>";
                    }
                }
                html +="<td>"+total.toFixed(2)+"</td>";
                html +="</tr>";
            })
            $("#amount1").text(amount1.toFixed(2));
            $("#amount2").text(amount2.toFixed(2));
            $("#amount3").text(amount3.toFixed(2));
            $("#amount4").text(amount4.toFixed(2));
            $("#table2-tab4").find("tbody").html(html);
            replaceFun($("#table2-tab4"));
        }
    });

})
//计算天数差的函数，通用 
function DateDiff(sDate1, sDate2) { //sDate1和sDate2是2017-9-25格式 
    var aDate, oDate1, oDate2, iDays
    if (navigator.userAgent.indexOf('Firefox') >= 0) {
        aDate = sDate1.split("-")
        oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]) //转换为9-25-2017格式
        aDate = sDate2.split("-")
        oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0])
        iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数
    } else {
        aDate = sDate1.split("-")
        oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) //转换为9-25-2017格式
        aDate = sDate2.split("-")
        oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
        iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数
    }
    return iDays
}
//小数加法
function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
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