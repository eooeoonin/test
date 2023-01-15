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
//  laydate({elem: "#repayTime", format: "YYYY-MM-DD",istime: true});
  var _table = $('#table');
  _table.bootstrapTable();
  queryBorrow();
  myPage();
});
  //分页
var myPage = function(){
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
    	queryBorrow();
    }
  });
};

  //查询借款人信息
  function queryBorrow(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/repayOption/repayOptionlist",
	      data:$("#form").serialize(),
	      async: false,
	      dataType: "json",
	      success: function(responseMsg) {
	    	  var html = "";
	    	  if(responseMsg.total!=0){
	    	  if(null!=responseMsg){
	    		  $("#currPage").val(responseMsg.pageNum);
	    		  $("#pageCount").val(responseMsg.pages);
	    	  }
	    	  
	    	  $.each(responseMsg.list, function (k, v) {
		          html +="<tr>";
		          html +="<td>"+v.borrowTitle+"</td>";
		          html +="<td>"+v.borrowUserName+"</td>";
		          html +="<td>"+v.borrowRate+"%</td>";
		          html +="<td>"+v.borrowDates+"</td>";
		          html +="<td>"+v.borrowAmount.amount+"</td>";
		          html +="<td>"+v.nextPhase+"/"+v.totalPhase+"</td>";
		          html +="<td>"+v.nextReapyAmount.amount+"</td>";
		          if(v.oaFlowCode == null || v.oaFlowCode == ""){
		        	  v.oaFlowCode = "——";
		          }
		          if(v.nextRepayTime != null){
		        	  html +="<td>"+v.nextRepayTime.split(" ")[0]+"</td>";
		          	  html +="<td>"+v.oaFlowCode+"</td>";
		          	  html +="<td>"+v.editedBy+"</td>";
		          	  html +="<td><a href='repayOption_detail.html?borrowId="+v.id+"&nextPhase="+v.nextPhase+"&totalPhase="+v.totalPhase+"'>查看</a></td>";
		          }else{
		        	  html +="<td>"+v.nextRepayTime+"</td>";
		        	  html +="<td>"+v.oaFlowCode+"</td>";
		        	  html +="<td>"+v.editedBy+"</td>";
		          	  html +="<td><a href='repayOption_detail.html?borrowId="+v.id+"&nextPhase="+v.nextPhase+"&totalPhase="+v.totalPhase+"'>查看</a></td>";
		          }
		          html +="</tr>";
	    	  });
	    	  $("#table").find("tbody").html(html);
	    	  $("#tcdPagehide").show();
	      }else{
	    	  html +='<tr class="no-records-found">';
	    	  html +='<td style="text-align:center; vertical-align:middle;" colspan="11">没有找到匹配的记录</td>';
	    	  html += '</tr>';
	    	  $("#table").find("tbody").html(html);
		 	  $("#tcdPagehide").hide();
	      }
	    	  
	      },error:function(data){
	    	  alert("出错了");
	      }
	  });
  }
  
  var _srhBtn = $("#srhBtn");
  _srhBtn.click(function () {
  	$("#currPage").val(1);
  	queryBorrow();
  	 
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
  	    	queryBorrow();
  	    }
  	  });
  });
