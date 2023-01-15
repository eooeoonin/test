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

  //分页
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
    	queryBorrower();
    }
  });

//查询借款人信息
  function queryBorrow(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_refuse_list/borrowList",
	      data:$("#form").serialize(),
	      async: false,
	      dataType: "json",
	      success: function(responseMsg){
	    	  
	    	  if(responseMsg.total!=0){
	    	  if(null!=responseMsg){
	    		  $("#currPage").val(responseMsg.pageNum);
	    		  $("#pageCount").val(responseMsg.pages);
	    	  }
	    	  var html = "";
	    	  $.each(responseMsg.list, function (k, v) {
		          html +="<tr>";
		          html +="<td>"+v.id+"</td>";
		          html +="<td><a href='l_refuse_list_view.html?borrowId="+v.id+"'>"+v.borrowTitle+"</a></td>";
		          html +="<td>"+v.borrowUserName+"</td>";
		          html +="<td>企业借款</td>";
		          html +="<td>"+v.borrowRate+"%</td>";
		          html +="<td>"+v.borrowDates+"</td>";
		          html +="<td>"+v.borrowAmount.amount+"</td>";
		          html +="<td>"+v.createTime+"</td>";
		         
	      		  if(v.status=="REFUSE1"){
	      			 html +="<td>初审</td>";
	      		  }else{
	      			 html +="<td>复审</td>";
	      		  }
		          html +="<td>"+v.modifyTime+"</td>";
		          html +="<td>"+v.protocolId+"</td>";
		          html +="<td><a href='l_refuse_list_edit.html?borrowId="+v.id+"'>编辑</a></td>";
		          html +="</tr>";
	    	  });
	    	  $("#table").find("tbody").html(html);
	    	  $("#tcdPagehide").show();
	    	  }else{
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

});