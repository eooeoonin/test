/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages;
var ueDesc;
var uePurpose;
var ueRisk;
$(function () {

    $(".i-checks").iCheck({
        checkboxClass: "icheckbox_square-green",
        radioClass: "iradio_square-green"
    });

    // $("#useRedPacket").iCheck('uncheck').val("0");

    $("#redMoneyScale").attr("disabled", "disabled");
    $("#redMoneyScale").val(0);

    var ueDesc1 = UE.getEditor('desc');
    var uePurpose1 = UE.getEditor('purpose');
    var ueRisk1 = UE.getEditor('risk');
    /**
     *校验
     */
    _modalFm1 = $('#form1');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField: 1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });

    operatorName = $.cookie("username");
    $('#editedBy').val(operatorName);
    $("#p").text(operatorName);
    $('#editedByV').val(operatorName);

    /***
     *功能说明：表格相关操作
     *参数说明：
     *创建人：李波涛
     *时间：2016-07-29
     ***/
    var _table = $('#table');
    _table.bootstrapTable();
    $("#listTable").css("display", "none");

    $("#queryBorrow").click(function () {
        var srhData = {"pageNo": 1, "pageSize": "10"};
        tableFun("/project/p_batch_pro/borrowList", srhData);
        $("#myModal").modal("show");

        myPage();
    });

    //借款人查询
    $("#srhSmtBtn").click(function () {
        var srhData = {
            "pageNo": 1,
            "pageSize": "10"
        };
        tableFun("/project/p_batch_pro/borrowList", srhData);
        myPage();
    });

    //查询合同列表
    $.ajax({
        type: "POST",
        url: "/project/p_batch_pro/getContractList",
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

    //选择选中的借款人
    $("#choose").click(function () {
        var obj = $("#table").find("input[type='radio']:checked");
        var borrowName = obj.parents("tr").find("td:nth-child(2)").text();
        var borrowerCode = obj.parents("tr").find("td:nth-child(12)").text();
        var borrowRate = obj.parents("tr").find("td:nth-child(4)").text();
        var borrowCycle = obj.parents("tr").find("td:nth-child(5)").text();
        var borrowAmount = obj.parents("tr").find("td:nth-child(7)").text();
        var borrowType = obj.parents("tr").find("td:nth-child(10)").text();
        var borrowSurplusAmount = obj.parents("tr").find("td:nth-child(6)").text();
        var repayTypeName = obj.parents("tr").find("td:nth-child(11)").text();
        var repayType = obj.parents("tr").find("td:nth-child(13)").text();
        var interestEndDate = obj.parents("tr").find("td:nth-child(14)").text();
        $("#borrowName").val(borrowName);
        $("#borrowName1").text(borrowName);
        $("#borrowerCode").val(borrowerCode);
        $("#borrowRate").text(borrowRate);
        $("#borrowCycle").text(borrowCycle);
        $("#borrowAmount").text(borrowAmount);
        $("#borrowSurplusAmount").text(borrowSurplusAmount);
        $("#repayTypeName").text(repayTypeName);
        $("#borrowType").text(borrowType);
        $("#borrowType").val(borrowType);
        $("#repayType").val(repayType);
        $("#interestEndDateTransfer_up").val(interestEndDate);

        /*$("#myModal").hide();
         $(".modal-backdrop").remove();*/
        $('#myModal').modal('hide');
    });


    //下一步提交
    $("#addBatch").click(function () {

        if (!_modalFm1.validationEngine('validate')) {
            return false;
        }

        if ($("#borrowerCode").val() == "" || $("#borrowerCode").val() == null) {
            alert("请选择资产");
            return false;
        }
        var ue = UE.getEditor('desc');
        var bodyContent = ue.getContent();
        var ue2 = UE.getEditor('purpose');
        var bodyContent2 = ue2.getContent()
        var ue3 = UE.getEditor('risk');
        var bodyContent3 = ue3.getContent()
        $("#listTable").css("display", "");
        $("#formTable").css("display", "none");
        $("#redMoneyScaleV").val($("#redMoneyScale").val());//红包
        $("#repayDescV").val($("#repayDesc").val());//提前还款描述
        $("#borrowerCodeV").val($("#borrowerCode").val());
        $("#contractNoV").val($("#contractNo").val());
        $("#repayTypeV").val($("#repayType").val());
        $("#beginInvestV").val($("#beginInvest").val());
        $("#stepInvestV").val($("#stepInvest").val());
        //$("#maxInvestV").val($("#maxInvest").val());
        $("#useRedPacketV").val($("#useRedPacket").prop("checked"));
        $("#addInterestCardFlagV").val($("#addInterestCardFlag").prop("checked"));
        $("#cashCardFlagV").val($("#cashCardFlag").prop("checked"));
        $("#descV").val(bodyContent);
        $("#purposeV").val(bodyContent2);
        $("#riskV").val(bodyContent3);
        $("#autoInvestMaxAmountV").val($("#autoInvestMaxAmount").val());
        $("#maxInvestV").val($("#maxInvest").val())

    });

    $("#goBack").click(function () {
        $("#listTable").css("display", "none");
        $("#formTable").css("display", "");
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

    });
    //打勾事件
    $("#addInterestCardFlag").on('ifUnchecked', function () {
        $("#addInterestCardFlag").val(0);
    });

    $("#addInterestCardFlag").on('ifChecked', function () {
        $("#addInterestCardFlag").val(1);
    });
    //打勾事件
    $("#cashCardFlag").on('ifUnchecked', function () {
        $("#cashCardFlag").val(0);
    });

    $("#cashCardFlag").on('ifChecked', function () {
        $("#cashCardFlag").val(1);
    });
});



function tableFun(tdUrl, tbData) {
    $.ajax({
        type: "POST",
        url: tdUrl,
        data: $("#borrowerQueryForm").serialize(),
        dataType: "json",
        async: false,
        success: function (data) {
            $("#currPage").val(data.pageNum);
            $("#pageCount").val(data.pages);

            var html = "";
            _pages = data.pages;
            $.each(data.list, function (k, v) {
                html += "<tr>";
                html += "<td><label class=\"radio-inline i-checks\"><input type=\"radio\" name=\"radio\" class=\"sub_radbox\"></label></td>";
                html += "<td>" + v.borrowTitle + "</td>";
                html += "<td>" + v.borrowUserName + "</td>";
                html += "<td>" + v.borrowRate + "</td>";
                html += "<td>" + v.borrowCycle + "</td>";
                html += "<td>" + v.balance.amount + "</td>";
                html += "<td>" + v.borrowAmount.amount + "</td>";
                html += "<td>" + v.createTime + "</td>";
                html += "<td>" + v.reviewDate + "</td>";
                html += "<td>" + v.borrowType + "</td>";
                html += "<td>" + v.repayTypeName + "</td>";
                html += "<td type='hidden'>" + v.id + "</td>";
                html += "<td type='hidden'>" + v.repayType + "</td>";
                html += "<td type='hidden'>" + v.lastRepayTime + "</td>";
                html += "</tr>";

            });
            $("#table").find("tbody").html(html);
        }
    });
}


//分页
function myPage() {
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount: _pages,
        current: 1,
        backFn: function (p) {
            var srhData3 = {
                "pageNo": p,
                "pageSize": "10"
            };
            $("#currPage").val(p);

            tableFun("/project/p_add_pro/borrowList", srhData3);
        }
    });
}


