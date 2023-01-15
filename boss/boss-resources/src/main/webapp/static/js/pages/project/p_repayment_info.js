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
    type: "POST",
    url: "/project/show/getRepaymentInfo/"+Request.loanCode,
    success: function (data) {
      if (data.data != null && data.data != "") {
        //项目名称
        var loanTitle = data.data.loanTitle;
        //关联借款
        var borrowTile = data.data.borrowTile;
        //借款企业
        var enterpriseUserName = data.data.enterpriseUserName;
        //账户余额
        var enterpriseUserBalance = data.data.enterpriseUserBalance.amount;
        //借款利率
        var borrowRate = data.data.borrowRate+"%";
        //项目利率
        var loanRate = data.data.loanRate+"%";
        $("#loanTitle").text(loanTitle);
        $("#borrowTile").text(borrowTile);
        $("#enterpriseUserName").text(enterpriseUserName);
        $("#enterpriseUserBalance").text(enterpriseUserBalance+"元");
        $("#borrowRate").text(borrowRate);
        $("#loanRate").text(loanRate);
        var _table = $('#table'),
            tableBodyHtml = '';
        $.each(data.data.interestPlansResVos, function (k, v) {
          //获取数据
          var d_loanCode = v.loanCode,
              d_loanTypeName = v.loanTypeName,
              d_title = v.title,
              d_yearRate=v.yearRate,
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
          tableBodyHtml += '<td><a href="p_repayment_edit.html?loanCode='+ d_loanCode +'" style="margin-left:15px;">还款</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
      }
      else
    	  alert(data.msg);
    },
    async: false
  });
  
  
  //查询项目还款记录
  $.ajax({
    type: "POST",
    url: "/project/show/repayList/loanDetail/"+Request.loanCode,
    success: function (responseMsg) {
      if (responseMsg != null && responseMsg != "") {
    	  var _table = $('#table'), html = "";
        $.each(responseMsg.loanRepayRecordResVo, function (k, v) {
        	  var repayPrincipalAmount = v.repayPrincipalAmount.amount*1;
  		  	  var repayInterestAmount = v.repayInterestAmount.amount*1;
  		  	  var platformProfit = v.platformProfit.amount*1;
  		  	  var total = accAdd(repayPrincipalAmount , repayInterestAmount);
  		  	  var total2 = accAdd(accAdd(repayPrincipalAmount,repayInterestAmount),platformProfit);
  		  	  var basePhase = v.phase;
  		  	  var status = "已还款";
  		  	  switch(v.status){
  		  	  case "REPAY_COMMEN":
  		  		  status = "已还款";
  		  		  break;
  		  	  case "REPAY_EARLY":
  		  		  status = "已提前还款";
  		  		  break;
  		  	  case "REPAY_OVERDUE":
  		  		  status = "逾期已还款";
  		  		  break;
  		  	  case "KILL":
		  		  status = "流标";
		  		  break; 
  		  	  default:
		  		  status = v.status;
		  		  break; 	  		
  		  	  }
	  		  html +="<tr>";
	          html +="<td>"+basePhase+"</td>";
	          html +="<td>"+total2+"</td>";
	          html +="<td>"+total+"</td>";
	          html +="<td>"+platformProfit+"</td>";
	          html +="<td>"+v.modifyTime+"</td>";
//	          html +="<td>"+status+"</td>";
	          html +="</tr>";
        });
        _table.find("tbody").html(html);
      }
    },
    async: false
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


$("#detail").click(function(){
	location = "p_repayment_info_detail.html?loanCode=" + Request.loanCode;
});



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


function accSub(arg1, arg2) {
  var r1, r2, m, n;
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
  m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
  n = (r1 >= r2) ? r1 : r2;
  return ((arg1 * m - arg2 * m) / m).toFixed(n);
}