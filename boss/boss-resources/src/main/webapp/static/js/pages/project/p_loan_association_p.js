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

  $.ajax({
    type : "POST",
    url : "/project/show/showLoanBorrow/"+Request.loanCode,
    success : function(data) {
      var _table = $('#table'),
          tableBodyHtml = '';
      //获取数据
      var id = data.data.id,
          borrowTitle = data.data.borrowTitle,
          borrowType = data.data.borrowType,
          borrowRate = data.data.borrowRate,
          borrowCycle = data.data.borrowCycle,
          borrowAmount = data.data.borrowAmount.amount,
          borrowedAmount= data.data.borrowedAmount.amount,
          lastReleaseTime=data.data.lastReleaseTime;

      //输出HTML元素
      tableBodyHtml += '<tr>';
      tableBodyHtml += '<td>' + id + '</td>';
      tableBodyHtml += '<td>' + borrowTitle + '</td>';
      tableBodyHtml += '<td>'+borrowType+'</td>>';
      tableBodyHtml += '<td>' + borrowRate + '%</td>';
      tableBodyHtml += '<td>' + borrowCycle + '</td>';
      tableBodyHtml += '<td>' + borrowAmount + '</td>';
      tableBodyHtml += '<td>' + borrowedAmount + '</td>';
      tableBodyHtml += '<td>' + lastReleaseTime + '</td>';
      tableBodyHtml += '</tr>';
      _table.find("tbody").html(tableBodyHtml);
    },
    async : false
  });

  $("#p_repayment_edit").attr("href","p_project_edit.html?loanCode="+Request.loanCode);
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