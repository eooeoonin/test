/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {

  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
  //出借用户列表
  $.ajax({
    type : "POST",
    url : "/loan/getInvestUserList/"+Request.loanCode,
    success : function(data) {
        var loanAmount = data.data.loanAmount.amount,
        biddingAmount = data.data.biddingAmount.amount,
        redmoneyAmount = data.data.redmoneyAmount.amount,
        cashAmount = data.data.cashAmount.amount,
        investCount = data.data.investCount;
        $("#loanAmount").text(loanAmount);
        $("#biddingAmount").text(biddingAmount);
        $("#redmoneyAmount").text(redmoneyAmount);
        $("#cashAmount").text(cashAmount);
        $("#investCount").text(investCount);
        var _table = $('#table'),
          tableBodyHtml = '';
      $.each(data.data.investUserForms, function (k, v) {
        //获取数据
        var investorUserCode = v.investorUserCode,//出借人
            investAmount = v.investAmount.amount,//出借金额
            investTime = v.investTime,//出借时间
            redMoneyAmount=v.redMoneyAmount.amount,//使用红包金额
            d_amount=v.amount.amount,
            d_openTime=v.openTime,
            d_interestStartDate=v.interestStartDate,
            d_interestEndDate=v.interestEndDate,
            d_borrowCycle=v.borrowCycle;
            //输出HTML元素
            tableBodyHtml += '<tr>';
            tableBodyHtml += '<td>' + d_loanCode + '</td>';
            tableBodyHtml += '<td>' + d_loanTypeName + '</td>';
            tableBodyHtml += '<td><a href="#">' + d_title + '</a></td>>';
            tableBodyHtml += '<td>' + d_yearRate + '%</td>';
            tableBodyHtml += '<td>' + d_borrowCycle + '</td>';
            tableBodyHtml += '<td>' + d_amount + '</td>';
            tableBodyHtml += '<td>' + d_openTime + '</td>';
            tableBodyHtml += '<td>' + d_interestStartDate + '</td>';
            tableBodyHtml += '<td>' + d_interestEndDate + '</td>';
            tableBodyHtml += '<td><a href="p_raise_edit.html?loanCode='+ d_loanCode +'" style="margin-left:15px;">编辑</a></td>';
            tableBodyHtml += '</tr>';
      });
      _table.find("tbody").html(tableBodyHtml);
    },
    async : false
  });

    $("#p_raise_edit").attr("href","p_raise_edit.html?loanCode="+Request.loanCode);
    $("#p_loan_association_r").attr("href","p_loan_association_r.html?loanCode="+Request.loanCode);
    $("#p_invest_user_r").attr("href","p_invest_user_r.html?loanCode="+Request.loanCode);
});

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