/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages ;
$(function () {

  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();

  var data = {
		  pageNo:"1",
		  pageSize:"10"
	  };
  getDetail(data);
  page();

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

function getDetail(data) {
	//出借人还款记录
	  $.ajax({
	    type: "POST",
	    url: "/project/show/repayList/userDetail/"+Request.loanCode,
	    data:data,
	    success: function (responseMsg) {
	      if (responseMsg != null && responseMsg != "") {
	    	  _pages = responseMsg.interestPlansPages.pages;
	    	  $("#title").text(responseMsg.title);
	    	  $("#yearRate").text(responseMsg.yearRate+"%");
	    	  $("#remainingDays").text(responseMsg.remainingDays+"天");
	    	  var _table = $('#table'), html = "";
	    	  if(_pages==0) {
	    		 tableBodyHtml = '';
	    		 tableBodyHtml +='<tr class="no-records-found">';
	    		 tableBodyHtml +='<td colspan="9" align="center">没有找到匹配的记录</td>';
	    		 tableBodyHtml += '</tr>';
	    		 _table.find("tbody").html(tableBodyHtml);
	    		 $("#tcdPageCode").hide();
	    	  }
	    		
	    	  else {
	    		  $.each(responseMsg.interestPlansPages.list, function (k, v) {
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
		  		  	  default:
		  		  		  status = v.status;
				  		  break; 
		  		  	  }
		  		  	  var actualAmount = v.amount.amount*1 - v.redMoneyAmount.amount*1;
		  		  	  var total = v.penalty.amount*1 + v.principalAmount.amount*1 + v.interestAmount.amount*1;
			  		  html +="<tr>";
			          html +="<td>"+v.userCode+"</td>";
			          html +="<td>"+v.phone+"</td>";
			          html +="<td>"+v.modifyTime+"</td>";
			          html +="<td>"+v.userName+"</td>";
			          html +="<td>"+v.phase+"</td>";
			          html +="<td>"+actualAmount+"</td>";//实付金额
			          html +="<td>"+v.redMoneyAmount.amount+"</td>";
			          html +="<td>"+v.amount.amount+"</td>";//出借金额
			          html +="<td>"+(v.penalty.amount).toFixed(2)+"</td>";//贴息
			          html +="<td>"+v.principalAmount.amount+"</td>";//本金
			          html +="<td>"+(v.interestAmount.amount).toFixed(2)+"</td>";//实际利息
			          html +="<td>"+total.toFixed(2)+"</td>";//合计
			          html +="<td>"+status+"</td>";
			          html +="</tr>";
		        });
		        _table.find("tbody").html(html);
		        $("#tcdPageCode").show();
	    	  }
	       
	      }
	      else {
	    	  alert("查询失败");
	    	  $("#tcdPageCode").hide();
	      }
	    	  
	    },
	    async: false
	  });
}

function page(){
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
      pageCount:  _pages,
      current: 1,
      backFn: function (p) {
        //点击分页事件
        var srhData = {"pageNo":p,"pageSize":"10"};
        getDetail(srhData);
      }
    });
}

$("#exportExcel").click(function(){
	window.location.href="/project/p_repayment_info/export/"+Request.loanCode;

})
