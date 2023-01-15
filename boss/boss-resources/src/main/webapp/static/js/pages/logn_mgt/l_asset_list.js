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

  queryBorrow();
  myPage();
  /***
   *功能说明：复选框、单选框美化
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green"
  });
   
});


$("#srhBtn").click(function(){
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

/***
 *功能说明：表格数据及分页
 *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
 *创建人：李波涛
 *时间：2016-08-01
 ***/
//查询借款人信息
function queryBorrow(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_asset_list/borrowList",
	      data:$("#form").serialize(),
	      async: false,
	      dataType: "json",
	      success: function(responseMsg) {
	    	  //alert(responseMsg.total);
	    	 if(responseMsg.total!=0){
	    	  if(null!=responseMsg){
	    		  $("#currPage").val(responseMsg.pageNum);
	    		  $("#pageCount").val(responseMsg.pages);
	    	  }
	    	  var html = "";
	    	  $.each(responseMsg.list, function (k, v) {
	    		  var surplusAmount = v.borrowAmount.amount - v.borrowedAmount.amount;
	    		  if(surplusAmount < 0)
	    			  surplusAmount = 0;
		          html +="<tr>";
		          html +="<td>"+v.id+"</td>";
		          html +="<td><a href='l_asset_list_view.html?borrowId="+v.id+"&status="+v.status+"'>"+v.borrowTitle+"</a></td>";
		          html +="<td>"+v.borrowUserName+"</td>";
		          html +="<td>"+v.borrowRate+"%</td>";
		          html +="<td>"+v.borrowDates+"</td>";
		          html +="<td>"+surplusAmount+"</td>";
		          html +="<td>"+v.borrowAmount.amount+"</td>";
		          html +="<td>"+v.borrowType+"</td>";
		          html +="<td>"+v.protocolId+"</td>";
		          html +="<td>"+v.lastReleaseTime+"</td>";
		          if("INUSE"==v.status){
		        	  html +="<td>可用</td>";
		          }
		          if("SPLIT"==v.status){
		        	  html +="<td>已拆分</td>";
		          }
		          if("LOCK"==v.status){
		        	  html +="<td>锁定中</td>";
		          }
		          html +="</tr>";
	    	  });
	    	  $("#table").find("tbody").html(html);
	    	  $("#tcdPagehide").show();
	      }else{
	    	  var html = "";
	    	  html +='<tr class="no-records-found">';
	    	  html +='<td colspan="10">没有找到匹配的记录</td>';
	    	  html += '</tr>';
	    	  $("#table").find("tbody").html(html);
	    	  $("#tcdPagehide").hide();
	      }
	      }
	  });
}

//格式化日期类型
function formatDate(now)   {     
    var year=now.getFullYear();     
    var month=now.getMonth()+1;     
    var date=now.getDate();     
    var hour=now.getHours();     
    var minute=now.getMinutes();     
    var second=now.getSeconds();
    return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
    
}