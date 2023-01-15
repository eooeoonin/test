/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var redMoneyScale1;
$(function () {
    $(".i-checks").iCheck({
        checkboxClass: "icheckbox_square-green"
    });
    var ue = UE.getEditor('desc');
//    var ue = UE.getEditor('purpose');
//    var ue = UE.getEditor('risk');

    /***
     *功能说明：时间插件
     *参数说明：
     *创建人：李波涛
     *时间：2016-08-01
     ***/
    laydate({elem: "#openTime", format: "YYYY-MM-DD hh:mm", istime: true});
    laydate({elem: "#interestStartDate", format: "YYYY-MM-DD"});


    _modalFm1 = $('#loanForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField: 1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
    //查询合同列表
    $.ajax({
        type: "POST",
        url: "/project/p_start_edit/getContractList",
        success: function (data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
                    tableBodyHtml = '';
                    $.each(data.data, function (k, v) {
                        //获取数据
                        var d_name = v.name,
                            d_id = v.id;
                        //输出HTML元素
                        tableBodyHtml += '<option value="' + d_id + '">' + d_name + '</option>';
                    });
                    $("#contractNo").html(tableBodyHtml);
                }
                else {
                    bootbox.alert(data.msg);
                }
            }
        },
        async: false
    });

    //查询项目类型
    $.ajax({
        type: "POST",
        url: "/project/p_start_edit/getLoanTypeList",
        success: function (data) {
            if (data != null && data != "") {
                tableBodyHtml = '';
                $.each(data, function (k, v) {
                    //获取数据
                    var d_id = k,
                        d_name = v;
                    //输出HTML元素
                    tableBodyHtml += '<option value="' + d_id + '">' + d_name + '</option>';
                });
                $("#loanType").html(tableBodyHtml);
            }
        },
        async: false
    });


    //用户权限组列表
    $.ajax({
        type: "POST",
        url: "/project/p_start_edit/getInvestGroupCode",
        success: function (data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
                    var _data = data.data.list;
                    tableBodyHtml = '';
                    $.each(_data, function (k, v) {
                        //获取数据
                        var codeKey = v.codeKey,
                            codeValue = v.codeValue;
                        //输出HTML元素
                        tableBodyHtml += '<option value="' + codeKey + '">' + codeValue + '</option>';
                    });
                    $("#investGroupCode").html(tableBodyHtml);

                } else bootbox.alert(data.msg);
            }
        },
        async: false
    });

/*    //查询项目文件
    $.ajax({
        type: "POST",
        url: "/project/p_start_edit/getLoanFileList/" + Request.loanCode,
        success: function (data) {
            if (data != null && data != "") {
                tableBodyHtml = '';
                $.each(data.data, function (k, v) {
                    //获取数据
                    var d_sitUrl = v.fileUrl,
                        d_id = v.id;
                    //输出HTML元素
                    tableBodyHtml += '<div class="file-box" id="' + d_id + '">';
                    tableBodyHtml += '<div class="file">';
                    tableBodyHtml += '<span class="corner"></span>';
                    tableBodyHtml += '<div class="image">';
                    tableBodyHtml += '<a class="fancybox img-responsive" href="' + domainUrl + loanOrigin + d_sitUrl + '"';
                    tableBodyHtml += 'title="项目文件">';
                    tableBodyHtml += '<img alt="image" src="' + domainUrl + loanOrigin + d_sitUrl + '"/>';
                    tableBodyHtml += '</a>';
                    tableBodyHtml += '</div>';
                    tableBodyHtml += '<div class="file-name">';
                    tableBodyHtml += '<small>项目文件</small>';
                    tableBodyHtml += '<div class="img-btns clear">';
                    tableBodyHtml += '<div class="pull-right">';
                    tableBodyHtml += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\'' + d_id + '\')"><i class="fa fa-trash-o"></i> 删除 </a>';
                    tableBodyHtml += '</div>';
                    tableBodyHtml += '</div>';
                    tableBodyHtml += '</div>';
                    tableBodyHtml += '</div>';
                    tableBodyHtml += '</div>';
                });
                $("#loanFiles").html(tableBodyHtml);
            }
        },
        async: false
    });*/

    //项目详情
    $(document).on('ready', function () {
        $.ajax({
            type: "POST",
            url: "/project/p_start_edit/showLoan/" + Request.loanCode,
            success: function (data) {
                if (data != null && data != "") {
                    if (data.resCode == 0) {
                        //获取数据
                        var loanCode = data.data.loanCode,
                            borrowTitle = data.data.borrowTitle,
                            title = data.data.title,
                            borrowTypeName = data.data.borrowTypeName,
                            contractNo = data.data.contractNo,
                            loanType = data.data.loanType,
                            floatRate = data.data.floatRate,
                            investInvetCode = data.data.investInvetCode,
                            yearRate = data.data.yearRate,
                            borrowRate = data.data.borrowRate,
                            borrowCycle = data.data.borrowCycle,
                            //项目总额
                            amount = data.data.amount.amount,
                            borrowAmount = data.data.borrowAmount.amount,
                            //剩余额度
                            borrowSurplusAmount = data.data.borrowSurplusAmount.amount,
                            repayTypeName = data.data.repayTypeName,
                            openTime = data.data.openTime,
                            earlyRepayment = data.data.earlyRepayment,
                            interestStartDate = data.data.interestStartDate,
                            interestEndDate = data.data.interestEndDate,
                            investGroupCode = data.data.investGroupCode,
                            beginInvest = data.data.beginInvest.amount,
                            stepInvest = data.data.stepInvest.amount,
                            maxInvest = data.data.maxInvest.amount,
                            useRedPacket = data.data.useRedPacket,
                            addInterestCardFlag = data.data.addInterestCardFlag,
                            cashCardFlag = data.data.cashCardFlag,
                            desc = data.data.desc,
//                            purpose = data.data.purpose,
//                            risk = data.data.risk,
                            operdesc = data.data.openoperdesc,
                            operUser = data.data.operUser,
                            descId = data.data.descId
                        purposeId = data.data.purposeId
                        riskId = data.data.riskId
                        operdescId = data.data.openoperdescId;
                        repayDesc = data.data.repayDesc;
                        redMoneyScale = data.data.redMoneyScale;
                        redMoneyScale1 = redMoneyScale;

                        if (useRedPacket == true) {

                            $("#useRedPacket").iCheck('check').val("1");
                            $("#redMoneyScale").removeAttr("disabled");
                            $("#redMoneyScale").val(redMoneyScale);

                        } else {
                            $("#useRedPacket").iCheck('uncheck').val("0");
                            $("#redMoneyScale").attr("disabled", "disabled");
                            $("#redMoneyScale").val(redMoneyScale);
                        }
                        

                        if (addInterestCardFlag == true) {

                            $("#addInterestCardFlag").iCheck('check').val("1");

                        } else {
                            $("#addInterestCardFlag").iCheck('uncheck').val("0");
                        }

                        if (cashCardFlag == true) {

                            $("#cashCardFlag").iCheck('check').val("1");

                        } else {
                            $("#cashCardFlag").iCheck('uncheck').val("0");
                        }
                        

                        if (earlyRepayment == true) {
                            $("#earlyRepayment").iCheck('check').val("1");
                        } else {
                            $("#earlyRepayment").iCheck('uncheck').val("0");
                        }


                        $("#repayDesc").val(repayDesc);
                        $("#loanCode").text(loanCode);
                        $("#id").val(loanCode);
                        $("#borrowTitle").text(borrowTitle);
                        $("#title").val(title);
                        $("#borrowTypeName").text(borrowTypeName);
                        $("#floatRate").val(floatRate);

                        $("#yearRate").val(yearRate);
                        $("#borrowRate").text(borrowRate + "%");
                        $("#borrowCycle").text(borrowCycle);
                        //项目总额
                        $("#amount").val(amount);
                        $("#borrowAmount").text(borrowAmount + "元");
                        //剩余额度
                        $("#borrowSurplusAmount").text(borrowSurplusAmount + "元");
                        $("#repayTypeName").text(repayTypeName);
//            $("#openTime").val( moment(openTime).format("YYYY-MM-DD"));
                        $("#openTime").val(moment(openTime).format("YYYY-MM-DD HH:mm"));

                        $("#interestStartDate").val(moment(interestStartDate).format("YYYY-MM-DD"));
                        var _interestEndDate = moment(interestEndDate).format("YYYY-MM-DD");
                        $("#interestEndDate").text(_interestEndDate);
                        $("#interestEndDateTransfer").val(_interestEndDate);
                        $("#beginInvest").val(beginInvest);
                        $("#stepInvest").val(stepInvest);
                        $("#maxInvest").val(maxInvest);
                        $("#desc").val(desc);
                        $("#descId").val(descId);
//                        $("#purpose").val(purpose);
                        $("#purposeId").val(purposeId);
//                        $("#risk").val(risk);
                        $("#riskId").val(riskId);
                        $("#operdesc").val(operdesc);
                        $("#operdescId").val(operdescId);
                        $("#operUser").text(operUser);
                        $("#contractNo").val(contractNo);
                        $("#loanType").val(loanType);
                        $("#editedBy").text(data.data.editedBy);
                        $("#createTime").text(data.data.createTime);
                        $("#autoInvestMaxAmount").val(data.data.autoInvestMaxAmount.amount);
                        if (loanType == 'ADDINTEREST') {
                            $("#floatRate").removeAttr("disabled");
                        } else {
                            $("#floatRate").attr("disabled", "disabled");
                            $("#floatRate").val(0);
                        }

                        if (loanType == 'VIP') {
                            $("#investInvetCode").removeAttr("disabled");
                        } else {
                            $("#investInvetCode").attr("disabled", "disabled");
                            $("#investInvetCode").val("");
                        }
                        $("#investInvetCode").val(investInvetCode);
                        $("#useRedPacket").val(useRedPacket ? 1 : 0);
                        $("#addInterestCardFlag").val(addInterestCardFlag ? 1 : 0);
                        $("#cashCardFlag").val(cashCardFlag ? 1 : 0);
                        $("#investGroupCode").val(investGroupCode);

                        var approveHtml = "";
                        if (data.data.initoperdescId != null) {
                            approveHtml += "<tr>";
                            approveHtml += "<td class=\"input-group-addon\">待开标操作内容</td>";
                            approveHtml += "<td colspan=\"3\">" + data.data.initoperdesc + "</td>";
                            approveHtml += "</tr>";
                            approveHtml += "<tr>";
                            approveHtml += "<td class=\"input-group-addon\">操作人</td>";
                            approveHtml += "<td>" + data.data.initoperdescEditBy + "</td>";
                            approveHtml += "<td class=\"input-group-addon\">操作时间</td>";
                            approveHtml += "<td>" + data.data.initoperdescTime + "</td>";
                            approveHtml += "</tr>";
                        }
                        var length = $("#table").find("tr").length - 3;
                        $("#table tr:eq(" + length + ")").after(approveHtml);
                    }
                    else {
                        bootbox.alert(data.msg);
                    }
                }
            },
            async: false
        });

        $("#loanType").change(function () {
            var loanType = $("#loanType").val();
            if (loanType == 'ADDINTEREST') {
                $("#floatRate").removeAttr("disabled");
            } else {
                $("#floatRate").attr("disabled", "disabled");
                $("#floatRate").val(0);
            }

            if (loanType == 'VIP') {
                $("#investInvetCode").removeAttr("disabled");
            } else {
                $("#investInvetCode").attr("disabled", "disabled");
                $("#investInvetCode").val("");
            }
        });



/*        $("#fileinput").fileinput({
            language: 'zh', //设置语言
            uploadUrl: "/project/p_start_edit/uploadLoanFile/" + Request.loanCode, //上传的地址
            allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
            uploadAsync: true,//是否异步方式提交
            enctype: 'multipart/form-data',//2进制传输数据
            showUpload: true,//是否显示提交按钮
//      minImageWidth: 50, //图片的最小宽度
//      minImageHeight: 50,//图片的最小高度
//      maxImageWidth: 1000,//图片的最大宽度
//      maxImageHeight: 1000,//图片的最大高度
            maxFileCount: 10,//上传数量
            maxFilePreviewSize: 10240//上传文件大小
        }).on("fileuploaded", function (event, data, previewId, index) {
            $.ajax({
                type: "POST",
                url: "/project/p_start_edit/getLoanFileList/" + Request.loanCode,
                success: function (data) {
                    if (data != null && data != "") {
                        tableBodyHtml = '';
                        $.each(data.data, function (k, v) {
                            //获取数据
                            var d_sitUrl = v.fileUrl,
                                d_id = v.id;
                            //输出HTML元素
                            tableBodyHtml += '<div class="file-box" id="' + d_id + '">';
                            tableBodyHtml += '<div class="file">';
                            tableBodyHtml += '<span class="corner"></span>';
                            tableBodyHtml += '<div class="image">';
                            tableBodyHtml += '<a class="fancybox img-responsive" href="' + domainUrl + loanOrigin + d_sitUrl + '"';
                            tableBodyHtml += 'title="项目文件">';
                            tableBodyHtml += '<img alt="image" src="' + domainUrl + loanOrigin + d_sitUrl + '"/>';
                            tableBodyHtml += '</a>';
                            tableBodyHtml += '</div>';
                            tableBodyHtml += '<div class="file-name">';
                            tableBodyHtml += '<small>项目文件</small>';
                            tableBodyHtml += '<div class="img-btns clear">';
                            tableBodyHtml += '<div class="pull-right">';
                            tableBodyHtml += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\'' + d_id + '\')"><i class="fa fa-trash-o"></i> 删除 </a>';
                            tableBodyHtml += '</div>';
                            tableBodyHtml += '</div>';
                            tableBodyHtml += '</div>';
                            tableBodyHtml += '</div>';
                            tableBodyHtml += '</div>';
                        });
                        $("#loanFiles").html(tableBodyHtml);
                    }
                },
                async: false
            });
        });*/
    });

    //打勾事件
    $("#useRedPacket").on('ifUnchecked', function () {
        $("#useRedPacket").val(0);
        $("#redMoneyScale").attr("disabled", "disabled");
        $("#redMoneyScale").val(0);
    });

    $("#useRedPacket").on('ifChecked', function () {
        $("#useRedPacket").val(1);
        $("#redMoneyScale").removeAttr("disabled");
        $("#redMoneyScale").val(redMoneyScale1);
    });
    $("#addInterestCardFlag").on('ifUnchecked', function () {
        $("#addInterestCardFlag").val(0);
    });

    $("#addInterestCardFlag").on('ifChecked', function () {
        $("#addInterestCardFlag").val(1);
    });
    
    $("#cashCardFlag").on('ifUnchecked', function () {
        $("#cashCardFlag").val(0);
    });

    $("#cashCardFlag").on('ifChecked', function () {
        $("#cashCardFlag").val(1);
    });

    //保存修改
    $("#saveLoan").click(function () {
        if (!_modalFm1.validationEngine('validate')) {
            return false;
        }

        var para = $("#loanForm").serialize() + "&useRedPacket=" + $("#useRedPacket").prop("checked") +
            "&earlyRepayment=" + $("#earlyRepayment").prop("checked") + 
            "&addInterestCardFlag=" + $("#addInterestCardFlag").prop("checked") +
            "&cashCardFlag=" + $("#cashCardFlag").prop("checked");
        $.ajax({
            type: "POST",
            url: "/project/p_start_edit/saveLoan",
            data: para,
            success: function (data) {
                if (data.resCode == 0) {
                    bootbox.alert("操作成功", function () {
                        window.location.href = "p_start_edit.html?loanCode=" + Request.loanCode;
                    });
                }
                else {
                    bootbox.alert(data.msg, function () {
                        window.location.href = "p_start_edit.html?loanCode=" + Request.loanCode;
                    });
                }
            },
            async: false
        });
    });

    //开标
    $("#openLoan").click(function () {
        if (!_modalFm1.validationEngine('validate')) {
            return false;
        }
        bootbox.confirm("确定要开标吗?", function (result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: "/project/p_start_edit/openLoan/" + Request.loanCode,
                    data: $("#loanForm").serialize(),
                    success: function (data) {
                        if (data != null && data != "") {
                            if (data.resCode == 0) {
                                bootbox.alert("操作成功", function () {
                                    window.location.href = "p_wait_list.html";
                                });
                            }
                            else {
                                bootbox.alert(data.msg);
                            }
                        }
                    },
                    async: false
                });
            }
        });
    });

    //流标
    $("#killLoan").click(function () {
        bootbox.confirm("确定要流标吗?", function (result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: "/project/p_start_edit/killLoan",
                    data: $("#loanForm").serialize(),
                    success: function (data) {
                        if (data != null && data != "") {
                            if (data.resCode == 0) {
                                bootbox.alert("操作成功", function () {
                                    window.location.href = "p_start_list.html";
                                });
                            }
                            else {
                                bootbox.alert(data.msg);
                            }
                        }
                    },
                    async: false
                });
            }
        });
    });

    //资产图片预览
    $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

});

/*function delFile(fileId) {
    alert(fileId);
    $.ajax({
        type: "POST",
        url: "/project/p_start_edit/deleteLoanFile/" + fileId,
        success: function (data) {
            if (data != null && data != "") {
                $("#" + fileId).removeAttr();
                $.ajax({
                    type: "POST",
                    url: "/project/p_start_edit/getLoanFileList/" + Request.loanCode,
                    success: function (data) {
                        if (data != null && data != "") {
                            tableBodyHtml = '';
                            $.each(data.data, function (k, v) {
                                //获取数据
                                var d_sitUrl = v.fileUrl,
                                    d_id = v.id;
                                //输出HTML元素
                                tableBodyHtml += '<div class="file-box" id="' + d_id + '">';
                                tableBodyHtml += '<div class="file">';
                                tableBodyHtml += '<span class="corner"></span>';
                                tableBodyHtml += '<div class="image">';
                                tableBodyHtml += '<a class="fancybox img-responsive" href="' + domainUrl + loanOrigin + d_sitUrl + '"';
                                tableBodyHtml += 'title="项目文件">';
                                tableBodyHtml += '<img alt="image" src="' + domainUrl + loanOrigin + d_sitUrl + '"/>';
                                tableBodyHtml += '</a>';
                                tableBodyHtml += '</div>';
                                tableBodyHtml += '<div class="file-name">';
                                tableBodyHtml += '<small>项目文件</small>';
                                tableBodyHtml += '<div class="img-btns clear">';
                                tableBodyHtml += '<div class="pull-right">';
                                tableBodyHtml += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\'' + d_id + '\')"><i class="fa fa-trash-o"></i> 删除 </a>';
                                tableBodyHtml += '</div>';
                                tableBodyHtml += '</div>';
                                tableBodyHtml += '</div>';
                                tableBodyHtml += '</div>';
                                tableBodyHtml += '</div>';
                            });
                            $("#loanFiles").html(tableBodyHtml);
                        }
                    },
                    async: false
                });
            }
        },
        async: false
    });
}*/

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