var _pages;
var data_url = "/trade/stream/list";
var apply_url = "/trade/stream/apply";
$(function () {
    var _table = $('#table');
    _table.bootstrapTable(); // 美化表格
    renderTable(getDataList({"pageNo": 1, "pageSize": 10}));
    myPage();

    var applyBtn = $('#applyBtn');
    applyBtn.click(function () {
        $("#userId").val("");
        $("#startDate").val("");
        $("#endDate").val("");
    });

    var applySubmit = $('#applySubmit');
    applySubmit.click(function () {
        if (!$("#applyForm").validationEngine('validate')) {
            return false;
        }
        var sequenceIds = $("#sequenceIds").val();
        var userId = $("#userId").val();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var res = applyData({
        	sequenceIds: sequenceIds,
            userId: userId,
            startDate: startDate,
            endDate: endDate
        });
        var alert = document.querySelector("#applyResDiv");
        var resInfo = document.querySelector("#applyResDiv p");
        alert.classList.remove("alert", "hidden", "alert-success", "alert-danger");
        if (res.isSuccess) {
            alert.classList.add("alert", "alert-success");
            resInfo.innerHTML = "操作成功";
            setTimeout(function () {
                $("#applyModal").modal('hide');
                window.location.reload();
            }, 500);
        } else {
            alert.classList.add("alert", "alert-danger");
            resInfo.innerHTML = "操作失败";
        }
    });

    //时间段
    var start = {
        elem: "#startDate", format: "YYYY-MM-DD", istime: false, istoday: false, choose: function (datas) {
            end.min = datas;
            end.start = datas
        }
    };
    var end = {
        elem: "#endDate", format: "YYYY-MM-DD", istime: false, istoday: false, choose: function (datas) {
            start.max = datas
        }
    };
    try {
        laydate(start);
        laydate(end);
    } catch (e) {
    }

});

/**
 * 获取数据
 * @param url
 * @param pageData
 */
var getDataList = function (pageData) {
    var res = {};
    $.ajax({
        type: "POST",
        url: data_url,
        data: pageData,
        async: false,  //同步提交
        dataType: "json",
        success: function (data) {
            res = data;
        },
        error: function () {
            res = {};
        }
    });
    return res;
};

/**
 * 申请确认事件
 * @param data
 */
var applyData = function (data) {
    var res = {};
    $.ajax({
        type: "POST",
        url: apply_url,
        data: data,
        async: false,
        dataType: "json",
        success: function (data) {
            if (data.resCode === 0) {
                res = {isSuccess: true, data: data}
            } else {
                res = {isSuccess: false}
            }
        },
        error: function () {
            res = {isSuccess: false}
        }
    });
    return res;
};

/**
 * 渲染表格数据
 * @param data
 * @param pageData
 */
var renderTable = function (data) {
    var _table = $('#table'), tableBodyHtml = '';
    _pages = Math.ceil(data.total / data.pageSize); //分页总数
    var _list = data.list;  //列表数据
    $.each(_list, function (k, v) {
        //输出HTML元素
        tableBodyHtml += '<tr>';
        tableBodyHtml += '<td>' + v.requestId + '</td>';
        tableBodyHtml += '<td>' + v.userId + '</td>';
        tableBodyHtml += '<td>' + v.sequenceId + '</td>';
        tableBodyHtml += '<td>' + v.remark + '</td>>';
        tableBodyHtml += '<td>' + v.requestTime + '</td>>';
        tableBodyHtml += '<td>' + v.responseTime + '</td>>';
        var statusText = '';
        if (v.status === 'SUCCESS') {
            statusText = '成功';
        } else if (v.status === 'INIT') {
            statusText = '初始化';
        } else if (v.status === 'PROCESSING') {
            statusText = '处理中';
        }
        tableBodyHtml += '<td>' + statusText + '</td>';
        var downloadStr = '';
        if (v.status === 'SUCCESS') {
            downloadStr = '<a href="#" onclick="downloadFile(\'' + v.requestId + '\')">下载</a>';
        }
        tableBodyHtml += '<td>' + downloadStr + '</td>';
        tableBodyHtml += '</tr>';
    });
    _table.find("tbody").html(tableBodyHtml);
};

/**
 * 下载事件
 * @param requestId 请求 ID
 */
function downloadFile(requestId) {
    $.ajax({
        type: "POST",
        url: '/trade/stream/download',
        data: {'requestId': requestId},
        async: false,
        dataType: "json",
        success: function (data) {
            if (data.resCode === 0) {
                var fileUrl = data.data;
				alert("请求下载成功");
				window.open(fileUrl);
            } else {
                bootbox.alert(data.msg);
            }
        },
        error: function () {
            bootbox.alert("接口出现错误，请重试");
        }
    });
}

/**
 * 分页
 */
var myPage = function () {
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount: _pages,
        current: 1,
        backFn: function (p) {
            renderTable(getDataList({"pageNo": p, "pageSize": 10}));
        }
    });
};