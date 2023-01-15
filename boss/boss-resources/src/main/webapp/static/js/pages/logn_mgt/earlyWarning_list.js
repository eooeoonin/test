var _pages;
var channelId;
var _table = $('#table');
_table.bootstrapTable();
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
	};
	tableFun("/borrow/earlyWarning/repayWarningList", srhData);
	myPage();
});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(responseMsg) {
            var html = "";
            if (responseMsg.resCode == 0) {
            	_pages = responseMsg.data.businessObject.pages;
                $.each(responseMsg.data.businessObject.list, function (k, v) {
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
                    if (v.repayAmount.amount != null && v.repayAmount.amount != "") {
                        html += "<td>" + v.repayAmount.amount + "元</td>";
                    } else {
                        html += "<td>——</td>";
                    }
                    html += "<td>"+v.phase+"</td>";
                    html += "<td>"+v.interestEndDate.substr(0, 10)+"</td>";
                    if (v.interestEndDate != null && v.interestEndDate != "") {
                    	var a = getNowFormatDate();
                        html += "<td>" + DateDiff(v.interestEndDate.substr(0, 10),a.substr(0, 10)) + "天</td>";
                    } else {
                        html += "<td>——</td>";
                    }
                    html += "<td><a href='repayPlan_detail.html?borrowerType=" + v.borrowerType + "&borrowId=" + v.borrowCode + "'>详情</a></td>";
                    html += "</tr>";
                });
                $("#tcdPagehide").show();
            } else {
                $("#tcdPagehide").hide();
            }
            $("#table").find("tbody").html(html);
		},
		async : false
	});
};

var myPage = function() {
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var borrowTitle = $("#borrowTitle").val();
			var borrowUserCode = $("#borrowUserCode").val();
			var oaFlowCode = $("#oaFlowCode").val();
			var srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"borrowTitle":borrowTitle,
					"borrowUserCode":borrowUserCode,
					"oaFlowCode":oaFlowCode
				};
			tableFun("/borrow/earlyWarning/repayWarningList", srhData);
		}
	});
};
$("#srhBtn").click(function() {
	var borrowTitle = $("#borrowTitle").val();
	var borrowUserCode = $("#borrowUserCode").val();
	var oaFlowCode = $("#oaFlowCode").val();
	var srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"borrowTitle":borrowTitle,
			"borrowUserCode":borrowUserCode,
			"oaFlowCode":oaFlowCode
		};
		tableFun("/borrow/earlyWarning/repayWarningList", srhData);
		myPage();
});
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