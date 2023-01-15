$(function () {
    queryBorrowTable();

    /***
     *功能说明：表格相关操作
     *参数说明：
     *创建人：李波涛
     *时间：2016-07-29
     ***/
    var _table = $('#table');
    _table.bootstrapTable();

    //分页
    var $curP = $("#currPage"),
        $pageC = $("#pageCount");
    var curpage = parseInt($curP.val());
    var pageCount = parseInt($pageC.val());
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount: pageCount,
        current: curpage,
        backFn: function (p) {
            $curP.val(p);
            queryBorrowTable();
        }
    });
});


//查询借款人信息
function queryBorrowTable() {
    $.ajax({
        type: "POST",
        url: "/borrow/overdue/borrowList",
        data: $("#form").serialize(),
        async: false,
        dataType: "json",
        success: function (responseMsg) {
            var html = "";
            if (responseMsg.total != 0) {
                if (null != responseMsg) {
                    $("#currPage").val(responseMsg.pageNum);
                    $("#pageCount").val(responseMsg.pages);
                }
                $.each(responseMsg.list, function (k, v) {
                    html += "<tr>";
                    html += "<td>" + v.borrowUserCode + "</td>";
                    if (v.oaFlowCode != null && v.oaFlowCode != "") {
                        html += "<td>" + v.oaFlowCode + "</td>";
                    } else {
                        html += "<td>——</td>";
                    }
                    if (v.borrowTitle != null && v.borrowTitle != "") {
                        html += "<td>" + v.borrowTitle + "</a></td>"; //<a href='l_firstinstance_list_view.html?borrowId="+v.id+"'>
                    } else {
                        html += "<td>——</td>";
                    }
                    html += "<td>" + v.borrowUserName + "</td>";
                    if (v.borrowerType == "PERSON") {
                        html += "<td>个人借款</td>";
                    } else if (v.borrowerType == "ENTERPRISE") {
                        html += "<td>企业借款</td>";
                    } else {
                        html += "<td>——</td>";
                    }
                    if (v.applyAmount.amount != null && v.applyAmount.amount != "") {
                        html += "<td>" + v.applyAmount.amount / 10000 + "万元</td>";
                    } else {
                        html += "<td>——</td>";
                    }
                    if (v.termType == "MONTH") {
                        html += "<td>" + v.borrowTerm + "月</td>";
                    } else if (v.termType == "DAY") {
                        html += "<td>" + v.borrowTerm + "日</td>";
                    } else {
                        html += "<td>——</td>";
                    }
                    if (v.status == "SUBMIT" || v.status == "BACK_FIRST_CHECK") {
                        html += "<td><a href='review_I_asset_audit.html?borrowerType=" + v.borrowerType + "&borrowId=" + v.id + "'>审核</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href='review_I_asset_audit_detail.html?borrowerType=" + v.borrowerType + "&borrowId=" + v.id + "'>详情</a></td>";
                    }else if(v.status == "RELEASE" || v.status == "OVERDUE"){
                        html += "<td><a href='repayPlan_detail.html?borrowerType=" + v.borrowerType + "&borrowId=" + v.id + "'>详情</a></td>";
                    }else{
                        html += "<td><a href='review_I_asset_audit_detail.html?borrowerType=" + v.borrowerType + "&borrowId=" + v.id + "'>详情</a></td>";
                    }
                    html += "</tr>";
                });
                $("#tcdPagehide").show();
            } else {
                $("#tcdPagehide").hide();
            }
            $("#table").find("tbody").html(html);
        }

    });
}

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

function formatDate(date, format) {
    if (!date) return;
    if (!format) format = "yyyy-MM-dd";
    switch (typeof date) {
        case "string":
            date = new Date(date.replace(/-/, "/"));
            break;
        case "number":
            date = new Date(date);
            break;
    }
    if (!date instanceof Date) return;
    var dict = {
        "yyyy": date.getFullYear(),
        "M": date.getMonth() + 1,
        "d": date.getDate(),
        "H": date.getHours(),
        "m": date.getMinutes(),
        "s": date.getSeconds(),
        "MM": ("" + (date.getMonth() + 101)).substr(1),
        "dd": ("" + (date.getDate() + 100)).substr(1),
        "HH": ("" + (date.getHours() + 100)).substr(1),
        "mm": ("" + (date.getMinutes() + 100)).substr(1),
        "ss": ("" + (date.getSeconds() + 100)).substr(1)
    };
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
        return dict[arguments[0]];
    });
}

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
    $("#currPage").val(1);
    queryBorrowTable();
    var $curP = $("#currPage"),
        $pageC = $("#pageCount");
    var curpage = 1;
    var pageCount = parseInt($pageC.val());
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount: pageCount,
        current: curpage,
        backFn: function (p) {
            $curP.val(p);
            queryBorrowTable();
        }
    });
});
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