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
    url : "/project/show/getInvestUserList/"+Request.loanCode,
    success : function(data) {
      var loanAmount = data.data.loanAmount.amount,
          biddingAmount = data.data.biddingAmount.amount,
          redmoneyAmount = data.data.redmoneyAmount.amount,
          cashAmount = data.data.cashAmount.amount,
          investCount = data.data.investCount,
          useCashAmount = data.data.useCashAmount.amount;
      $("#loanAmount").text(loanAmount+"元");
      $("#useCashAmount").text(useCashAmount+"元");
      $("#biddingAmount").text(biddingAmount+"元");
      $("#redmoneyAmount").text(redmoneyAmount+"元");
      $("#cashAmount").text(cashAmount+"元");
      $("#investCount").text(investCount);
      var _table = $('#table'),
          tableBodyHtml = '';
      $.each(data.data.investUserForms, function (k, v) {
        //获取数据
        var userPhone = v.userPhone,//出借人
            userName = v.userName,//姓名
            investTime = v.investTime,//出借时间
            userProperties=v.userProperties,//用户属性
            cashMoney=v.cashMoney.amount,//实付余额
            redMoneyAmount=v.redMoneyAmount.amount,//红包金额
            investAmount=v.investAmount.amount,//出借金额
            addInterestVal = v.addInterestVal;
        	var useCashVal=0;
            if(undefined != v.useCashVal.amount && null != v.useCashVal.amount){
                useCashVal = v.useCashVal.amount;
            }

        //输出HTML元素
        tableBodyHtml += '<tr>';
        tableBodyHtml += '<td>' + userPhone + '</td>';
        tableBodyHtml += '<td>' + userName + '</td>';
        tableBodyHtml += '<td>' + userProperties + '</td>>';
        tableBodyHtml += '<td>' + investTime + '</td>';
        tableBodyHtml += '<td>' + cashMoney + '</td>';
        tableBodyHtml += '<td>' + redMoneyAmount + '</td>';
        tableBodyHtml += '<td>' + investAmount + '</td>';
        tableBodyHtml += '<td>' + addInterestVal + '%</td>';
        tableBodyHtml += '<td>' + useCashVal + '元</td>';
        tableBodyHtml += '</tr>';
      });
      _table.find("tbody").html(tableBodyHtml);
    },
    async : false
  });

  $("#p_project_edit").attr("href","p_project_edit.html?loanCode="+Request.loanCode);
  $("#p_loan_association_p").attr("href","p_loan_association_p.html?loanCode="+Request.loanCode);
  $("#p_invest_user_p").attr("href","p_invest_user_p.html?loanCode="+Request.loanCode);
  $("#p_repayment_info").attr("href","p_repayment_info.html?loanCode="+Request.loanCode);
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


$("#exportExcel").click(function(){
	window.location.href="/project/p_invest_user_p/exportInvestUser/"+Request.loanCode;

})
