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
  laydate({elem: "#openTime", format: "YYYY-MM-DD hh:mm",istime: true});

  tableFun();
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
    	tableFun();
    }
  });
  

});

function tableFun() {
    $.ajax({
      type: "POST",
	  url: "/project/p_history_list/list",
	  data: $("#form").serialize(),
	  dataType: "json",
	  success: function (data) {
		  if(data.resCode == 0) {
			  var _table = $('#table'),
		      tableBodyHtml = '';
		  	  if(data.data.total!=0){
				  	$("#currPage").val(data.data.pageNum);
					$("#pageCount").val(data.data.pages);
				    
				    $.each(data.data.list, function (k, v) {
				      //获取数据
				      var d_loanCode = v.loanCode,
				          d_loanTypeName = v.loanTypeName,
				          d_title = v.title,
				          d_yearRate=v.yearRate,
				          d_amount=v.amount.amount,
				          d_openTime=v.openTime,
				          d_interestEndDate=v.interestEndDate,
				          d_borrowCycle=v.borrowCycle,
				          d_status = v.status;
				      var d_redMoneyAmount,d_profit;
				      if(v.redMoneyAmount == null)
				    	  d_redMoneyAmount = 0;
				      else
				    	  d_redMoneyAmount = v.redMoneyAmount.amount;
				      if(v.profit == null)
				    	  d_profit = 0;
				      else
				    	  d_profit = v.profit.amount;
				
				      //输出HTML元素
				      tableBodyHtml += '<tr>';
				      tableBodyHtml += '<td>' + d_loanCode + '</td>';
				      tableBodyHtml += '<td>' + d_loanTypeName + '</td>';
				      tableBodyHtml += '<td><a href="p_project_info.html?loanCode='+d_loanCode+'">' + d_title + '</a></td>>';
				      tableBodyHtml += '<td>' + d_yearRate + '%</td>';
				      tableBodyHtml += '<td>' + d_borrowCycle + '</td>';
				      tableBodyHtml += '<td>' + d_amount + '</td>';
				      tableBodyHtml += '<td>' + v.biddingAmount.amount + '</td>';
				      
				      if(v.status == "已完成") {
				    	  tableBodyHtml += '<td>' + d_redMoneyAmount + '</td>';
					      tableBodyHtml += '<td>' + d_profit + '</td>';
				      }
				      else {
				    	  tableBodyHtml += '<td>' + '——' + '</td>';
					      tableBodyHtml += '<td>' + '——' + '</td>';
				      }
				    	  
				      tableBodyHtml += '<td>' + d_openTime + '</td>';
				      tableBodyHtml += '<td>' + d_interestEndDate + '</td>';
				      tableBodyHtml += '<td>' + d_status + '</td>';
				      tableBodyHtml += '</tr>';
				    });
				    _table.find("tbody").html(tableBodyHtml);
				    $("#tcdPagehide").show();
				 }
		  	  else{
		  		tableBodyHtml +='<tr class="no-records-found">';
		  		tableBodyHtml +='<td style="text-align:center; vertical-align:middle;" colspan="7">没有找到匹配的记录</td>';
		  		tableBodyHtml += '</tr>';
		 		_table.find("tbody").html(tableBodyHtml);
		 		$("#tcdPagehide").hide();
		  	  }
		  }
		  else{
			  alert(data.msg);
		  }
		  
	    },
	    async : false
  });
}
	 
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
  	$("#currPage").val(1);
  	tableFun();
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
	    	tableFun();
	    }
	});
});