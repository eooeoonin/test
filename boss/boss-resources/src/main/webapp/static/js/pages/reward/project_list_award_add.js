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
var u_name;
var lotteryChangeCounts = 0; //编辑时回显示的抽奖券概率的条数

$(function () {
    var tdU = "../reward/project_list/getById";
    var tbD = {"id": Request.projectId};
    tablePro(tdU, tbD);

    laydate({elem: "#tab1_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab2_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab3_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab4_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab5_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab6_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab7_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab8_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab9_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab10_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    laydate({elem: "#tab11_limitedDate", format: "YYYY/MM/DD hh:mm:ss", istime: true});
    //时间段


    $(".tabSubmit").bind("click", function () {
        var flag = $(this).attr('title');
        var formName = "#" + flag + "_form";
        _modalFm = $(formName);
        var _id = Request.projectId;
        var _type = Request.type;

        $(".validateForm").validationEngine('attach', {
            inlineValidation: false,
            maxErrorsPerField: 1,
            autoHidePrompt: true,
            autoHideDelay: 2000
        });

        if (!$(".validateForm").validationEngine('validate')) {
            $(".validateForm").validationEngine('detach');
            return false;
        }

        if(!validateMsgSelected(flag)){
            return false;
        }

        if ("add" == _type) {
            $(".projectIdHidden").val(_id);
            //卡密代金券新增时要上传文件，这里表单提交时单独处理
            if ("tab11" == flag) {
                var fileName = document.getElementById("myfile").value;
                if (fileName.length != 0) {
                    var reg = ".*\\.(txt)";
                    var r = fileName.match(reg);
                    if (r == null) {
                        bootbox.alert("上传的附件格式不对，请重新上传");
                        return false;
                    }
                }

                var file = new FormData($('#uploadForm')[0]);
                file.append("projectId", _id);
                file.append("type", "VOUCHERPWD");
                file.append("remark", $("#tab11_remark").val());
                file.append("name", $("#tab11_name").val());
                file.append("descValue", $("#tab11_descValue").val());
                file.append("descName", $("#tab11_descName").val());
                file.append("number", $("#tab11_number").val());
                file.append("expiredDay", $("#tab11_expiredDay").val());
                file.append("value", $("#tab11_value").val());
                file.append("cost", $("#tab11_cost").val());
                file.append("limitedDate", $("#tab11_limitedDate").val());
                file.append("voucherInfoReqVo.hperLink", $("#tab11_link").val());
                file.append("poolMsgReqVos[0].msgType", "SEND_INNER_MESSAGE");
                file.append("poolMsgReqVos[0].msgKey", $("#tab11_msgKey1").val());
                file.append("poolMsgReqVos[1].msgType", "SEND_SMS");
                file.append("poolMsgReqVos[1].msgKey", $("#tab11_msgKey2").val());
                file.append("poolMsgReqVos[2].msgType", "SEND_PUSH");
                file.append("poolMsgReqVos[2].msgKey", $("#tab11_msgKey3").val());
                $.ajax({
                    type: "POST",
                    url: "../reward/project_list/jackpot/insert",
                    data: file,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function (data) {
                        if (data != null && data != "") {
                            if (data.resCode == 0) {
                                bootbox.alert("操作成功");
                                location = "../reward/project_list_jackpot_list.html?projectId=" + _id + "&projectName=" + encodeURI(encodeURI(u_name));
                            } else {
                                bootbox.alert(data.msg);
                            }
                        }
                    }, error: function () {
                        bootbox.alert("操作失败")
                        location = "../reward/project_list_jackpot_list.html?projectId=" + _id + "&projectName=" + encodeURI(encodeURI(u_name));
                    }
                })
            } else {
                $.ajax({
                    type: "POST",
                    url: "../reward/project_list/jackpot/insert",
                    data: _modalFm.serialize(),
                    dataType: "json",
                    success: function (data) {
                        if (data != null && data != "") {
                            if (data.resCode == 0) {
                                bootbox.alert("操作成功");
                                location = "../reward/project_list_jackpot_list.html?projectId=" + _id + "&projectName=" + encodeURI(encodeURI(u_name));
                            } else {
                                bootbox.alert(data.msg);
                            }
                        }
                    }, error: function () {
                        bootbox.alert("操作失败")
                        location = "../reward/project_list_jackpot_list.html?projectId=" + _id + "&projectName=" + encodeURI(encodeURI(u_name));
                    }
                })
            }

        } else if ("edit" == _type) {
            $.ajax({
                type: "POST",
                url: "../reward/project_list/jackpot/update",
                data: _modalFm.serialize(),
                dataType: "json",
                success: function (data) {
                    if (data == 1) {
                        bootbox.alert("操作成功")
                        location = "../reward/project_list_jackpot_list.html?projectId=" + _id + "&projectName=" + encodeURI(encodeURI(u_name));
                    }
                }, error: function () {
                    bootbox.alert("操作失败")
                    location = "../reward/project_list_jackpot_list.html?projectId=" + _id + "&projectName=" + encodeURI(encodeURI(u_name));
                }
            })
        }

    });


    var jackpotId = Request.jackpotId;
    if (null != jackpotId && "" != jackpotId && undefined != jackpotId) {
        $.ajax({
            type: "POST",
            url: "/reward/project_list/jackpot/getById",
            data: {"id": jackpotId},
            dataType: "json",
            success: function (data) {
                var awardData = data[0];
                var msgData = data[1];
                //alert(awardData.type);
                var flag = getTabCount(awardData.type, awardData.voucherInfoReqVo.seneCode);

                var tabN = flag.substring(3);
                $('.nav-tabs a').eq(tabN).tab('show');
                //移除其它页签，使其不可点
                $("#active" + flag).siblings().find("a").removeAttr("data-toggle");

                if ("tab11" == flag) {
                    $("#h5File").remove();
                }
                //修改按钮字体
                $("#" + flag + "_jackpotSubmit").html("修改");

                var show_remark = "#" + flag + "_remark";   //价值描述
                var show_descValue = "#" + flag + "_descValue";   //价值描述
                var show_name = "#" + flag + "_name";             //名称
                var show_descName = "#" + flag + "_descName";     //奖品描述
                var show_value = "#" + flag + "_value";           //价值
                var show_cost = "#" + flag + "_cost";             //成本
                var show_number = "#" + flag + "_number";         //奖品个数
                var show_limitedDate = "#" + flag + "_limitedDate";     //全局过期时间
                var show_expiredDay = "#" + flag + "_expiredDay";       //过期天数

                var show_link = "#" + flag + "_link";  //抽奖券外链地址
                var show_voucher_interest_Min = "#" + flag + "_voucher_interest_useLimitMin";  //加息券最小出借额
                var show_voucher_interest_Max = "#" + flag + "_voucher_interest_useLimitMax";  //加息券最大出借额
                var show_voucher_useLimitDesc = "#" + flag + "_voucher_interest_useLimitDesc";  //加息券最大出借额
                var show_voucher_interest_Day_Min = "#" + flag + "_voucher_interest_useDayLimitMin";  //加息券最小出借额
                var show_voucher_interest_Day_Max = "#" + flag + "_voucher_interest_useDayLimitMax";  //加息券最大出借额

                $(show_remark).val(awardData.remark);
                $(show_descValue).val(awardData.descValue);
                $(show_name).val(awardData.name);
                $(show_descName).val(awardData.descName);
                $(show_value).val(awardData.value.amount);
                $(show_cost).val(awardData.cost.amount);
                $(show_number).val(awardData.number);
                $(show_limitedDate).val(moment(awardData.limitedDate).format("YYYY/MM/DD hh:mm:ss"));
                $(show_expiredDay).val(awardData.expiredDay);
                if (null != awardData.lotteryInfoReqVo && "" != awardData.lotteryInfoReqVo && undefined != awardData.lotteryInfoReqVo) {
                    $(show_link).val(awardData.lotteryInfoReqVo.hperLink);
                }
                if (null != awardData.voucherInfoReqVo && "" != awardData.voucherInfoReqVo && undefined != awardData.voucherInfoReqVo ) {
                    $(show_voucher_interest_Min).val(awardData.voucherInfoReqVo.useLimitMin);   //加息券最小出借额
                    $(show_voucher_interest_Max).val(awardData.voucherInfoReqVo.useLimitMax);   //加息券最大出借额
                    $(show_voucher_useLimitDesc).val(awardData.voucherInfoReqVo.useLimitDesc);   //出借券使用描述
                    $(show_voucher_interest_Day_Min).val(awardData.voucherInfoReqVo.useDayLimitMin);   //加息券最小出借额
                    $(show_voucher_interest_Day_Max).val(awardData.voucherInfoReqVo.useDayLimitMax);   //加息券最大出借额
                    if("tab8" == flag || "tab9" == flag){
                        $(show_link).val(awardData.voucherInfoReqVo.hperLink);                      //H5使用代金券外链地址
                    }
                }

                showMsg(msgData, flag);
                //显示抽奖券概率
                showLotteryChanges(awardData.id);

                //将id与projectId 设置成隐藏域，保存时提交到后台
                $(".idHidden").val(awardData.id);
                $(".projectIdHidden").val(awardData.projectId);

            }, error: function () {
                bootbox.alert("获取奖品信息失败")
            }
        })

    }

    tab3TableFun();

});

//获得项目信息
function tablePro(tdUrl, tbData) {

    $.ajax({
        type: "POST",
        url: tdUrl,
        data: tbData,
        dataType: "json",
        success: function (data) {
            var u_id = data.id;
            u_name = data.name,
                u_description = data.description,
                u_startTime = data.startTime,
                u_endTime = data.endTime,
                u_defaultAward = data.defaultAward;
            if (null == u_defaultAward || "" == u_defaultAward || undefined == u_defaultAward) {
                u_defaultAward = "无";
            }

            $(".showProjectClass").html("项目名称: &nbsp;" + u_name + "<span style='margin-left: 100px'>开始时间: &nbsp;" + u_startTime + "</span><span style='margin-left: 100px'>结束时间: &nbsp;" + u_endTime + "</span><span style='margin-left: 100px'>补充奖励: &nbsp;" + u_defaultAward + "</span>");
        }
    });
}


var tab3TableFun = function () {
    $("#tab3Table").find(".subChanges").each(function () {
        $(this).click(function () {
            var i = $("#tab3Table").find(".lotteryChanges").length;
            if (i <= 1) {
                return false;
            }
            $(this).parent().parent().remove();
        })

    });
};


function getTabCount(type, seneCode) {
    var result_tab = "";
    if ("REDMONEY" == type) {
        result_tab = "tab1";
    } else if ("SHAREREDMONEY" == type) {
        result_tab = "tab2";
    } else if ("LOTTERY" == type) {
        result_tab = "tab3";
    } else if ("VOUCHER" == type) {
        if ("WITHDRAW" == seneCode) {
            result_tab = "tab4";
        } else if ("INTEREST" == seneCode) {
            result_tab = "tab5";
        } else if ("INVEST" == seneCode) {
            result_tab = "tab6";
        } else if (null == seneCode || "" == seneCode && undefined == seneCode) {
            result_tab = "tab9";
        }
    } else if ("PHYSICAL" == type) {
        result_tab = "tab7";
    } else if ("VOUCHERH5" == type) {
        result_tab = "tab8";
    } else if ("VOUCHEROFFLINE" == type) {
        result_tab = "tab10";
    } else if ("VOUCHERPWD" == type) {
        result_tab = "tab11";
    }
    return result_tab;
}


function validateMsgSelected(flag) {
    var validateResult = true;
    var _selectedInnerType = "#" + flag + "_SEND_INNER_MESSAGE";
    var _selectedSmsType = "#" + flag + "_SEND_SMS";
    var _selectedPushType = "#" + flag + "_SEND_PUSH";
    if ($(_selectedInnerType).prop("checked") || $("#" + flag + "_msgKey1").val().length > 0) {
        if(!($(_selectedInnerType).prop("checked") & $("#" + flag + "_msgKey1").val().length > 0)){
            bootbox.alert("请填写正确的站内信");
            validateResult =  false;
        }

    }
    if ($(_selectedSmsType).prop("checked") || $("#" + flag + "_msgKey2").val().length > 0) {
        if (!($(_selectedSmsType).prop("checked") & $("#" + flag + "_msgKey2").val().length > 0)){
            bootbox.alert("请填写正确的短信");
            validateResult =  false;
        }
    }
    if ($(_selectedPushType).prop("checked") || $("#" + flag + "_msgKey3").val().length > 0) {
        if (!($(_selectedPushType).prop("checked") & $("#" + flag + "_msgKey3").val().length > 0)) {
            bootbox.alert("请填写正确的push");
            validateResult =  false;
        }
    }
    return validateResult;

}


function showMsg(data, flag) {
    var show_innerType = "#" + flag + "_SEND_INNER_MESSAGE";     //站内信息type
    var show_innerKey = "#" + flag + "_msgKey1";                //站内信息填写的值
    var show_smsType = "#" + flag + "_SEND_SMS";               //短信
    var show_smsKey = "#" + flag + "_msgKey2";               //短信
    var show_pushType = "#" + flag + "_SEND_PUSH";            //push
    var show_pushKey = "#" + flag + "_msgKey3";            //push

    $.each(data.list, function (k, v) {
        //获取数据
        var u_msgKey = v.msgKey,
            u_msgType = v.msgType;

        if ($(show_innerType).val() == u_msgType) {
            $(show_innerKey).val(u_msgKey);
            $(show_innerType).attr("checked", "true");
        }
        if ($(show_smsType).val() == u_msgType) {
            $(show_smsKey).val(u_msgKey);
            $(show_smsType).attr("checked", "true");
        }
        if ($(show_pushType).val() == u_msgType) {
            $(show_pushKey).val(u_msgKey);
            $(show_pushType).attr("checked", "true");
        }
    });

}


$("#addChanges").click(function () {
    // var i =	$("#tab3Table").find(".lotteryChanges").length;
    lotteryChangeCounts++;
    var html = "";
    html = '<tr class="lotteryChanges" >' +
        '<td class="input-group-addon" width="10%"> <em>*</em>奖品码</td>' +
        '<td width="15%">' +
        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + lotteryChangeCounts + '].actCode" id="actCode' + lotteryChangeCounts + '">' +
        '</td>' +
        '<td class="input-group-addon" width="10%"> <em>*</em>最小中奖概率</td>' +
        '<td width="15%">' +
        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + lotteryChangeCounts + '].chMini" id="chMini' + lotteryChangeCounts + '">' +
        '</td>' +
        '<td class="input-group-addon" width="10%"> <em>*</em>最大中奖概率</td>' +
        '<td width="15%">' +
        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + lotteryChangeCounts + '].chMax" id="chMax' + lotteryChangeCounts + '">' +
        '</td>' +
        '<td class="input-group-addon" width="10%"> <em>*</em>起始中奖位</td>' +
        '<td width="15%">' +
        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + lotteryChangeCounts + '].startLimit" id="startLimit' + lotteryChangeCounts + '">' +
        '<td colspan="2" align="right"><button class="btn btn-primary subChanges" type="button"> <i class="fa fa-minus"></i></button></td>' +
        '</tr>';
    $("#tab3Table").append(html);

    tab3TableFun();
});


function showLotteryChanges(poolId) {
    $.ajax({
        type: "POST",
        url: "../reward/project_list/jackpot/bylotteryInfoId",
        data: {"poolId": poolId, "pageNo": "1", "pageSize": 100},
        error: function () {
        },
        success: function (data) {
            var html = '';
            lotteryChangeCounts = data.list.length;
            $.each(data.list, function (i, v) {
                if (i == 0) {
                    $("#actCode0").val(v.actCode);
                    $("#chMini0").val(v.chMini);
                    $("#chMax0").val(v.chMax);
                    $("#startLimit0").val(v.startLimit);
                } else {
                    html += '<tr class="lotteryChanges" >' +
                        '<td class="input-group-addon" width="10%"> <em>*</em>奖品码</td>' +
                        '<td width="15%">' +
                        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + i + '].actCode" value=' + v.actCode + ' id="actCode' + i + '">' +
                        '</td>' +
                        '<td class="input-group-addon" width="10%"> <em>*</em>最小中奖概率</td>' +
                        '<td width="15%">' +
                        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + i + '].chMini" value=' + v.chMini + ' id="chMini' + i + '">' +
                        '</td>' +
                        '<td class="input-group-addon" width="10%"> <em>*</em>最大中奖概率</td>' +
                        '<td width="15%">' +
                        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + i + '].chMax" value=' + v.chMax + ' id="chMax' + i + '">' +
                        '</td>' +
                        '<td class="input-group-addon" width="10%"> <em>*</em>起始中奖位</td>' +
                        '<td width="15%">' +
                        '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos[' + i + '].startLimit" value=' + v.startLimit + ' id="startLimit' + i + '">' +
                        '<td colspan="2" align="right"><button class="btn btn-primary subChanges" type="button"> <i class="fa fa-minus"></i></button></td>' +
                        '</tr>';
                }
            });
            $("#tab3Table").append(html);
            tab3TableFun();
        },
        async: false             //false表示同步
    });
}




