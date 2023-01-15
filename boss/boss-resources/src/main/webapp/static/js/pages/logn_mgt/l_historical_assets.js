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
  //tableFun("../static/data/example.txt", "");
  laydate({elem: "#modify", format: "YYYY-MM-DD"});
  pageData = $("#form").serialize();
  queryBorrow("/borrow/l_historical_assets/borrowList",pageData);
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
//    	$curP.val(p);
    	  var modify = document.getElementById("modify").value;
    	  var borrowUserName = document.getElementById("borrowUserName").value;
    	  var pageData = {
    			  	"pageNo" : p,
    				"pageSize" : "10",
    				"modifyTime" : modify,
    				"borrowUserName":borrowUserName,
    				"statusType":"invalid"
    			};
    	queryBorrow("/borrow/l_historical_assets/borrowList",pageData);
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
  function queryBorrow(tdUrl, tbData){
	  $.ajax({
	      type: "POST",
	      url:tdUrl,
	      data:tbData,
	      async: false,
		  dataType: "json",
	      success: function(responseMsg) {
	    	  if(null!=responseMsg){
	    		  $("#currPage").val(responseMsg.pageNum);
	    		  $("#pageCount").val(responseMsg.pages);
	    	  }
	    	  var html = "";
	    	  $.each(responseMsg.list, function (k, v) {
		          html +="<tr>";
		          html +="<td>"+v.id+"</td>";
		          html +="<td>"+v.borrowTitle+"</td>";
		          html +="<td>"+v.borrowUserName+"</td>";
		          html +="<td>"+v.borrowRate+"%</td>";
		          html +="<td>"+v.borrowDates+"</td>";
		          html +="<td>"+v.borrowAmount.amount+"</td>";
		          html +="<td>"+v.createTime+"</td>";
		          html +="<td>"+v.modifyTime+"</td>";
		          html +="<td>"+v.protocolId+"</td>";
		          if(v.status=="INVALID1"){
	      			 html +="<td>初审</td>";
	      		  }else if(v.status == "INVALID2"){
	      			  html += "<td>复审</td>";
	      		  }else if(v.status == "INVALID3"){
	      			  html += "<td>可用资产</td>";
	      		  }else{
	      			 html +="<td>已完成</td>";
	      		  }
		          
		          html +="</tr>";
	    	  });
	    	  $("#table").find("tbody").html(html);
	      },
		async : false
	  });
  }
  
  
  $("#sousuo").click(function(){
		var modify = document.getElementById("modify").value;
		var borrowUserName = document.getElementById("borrowUserName").value;
		var srhData = {
	    						"pageNo" : "1",
								"pageSize" : "10",
								"borrowUserName" :borrowUserName,
								"modifyTime": modify,
							    "statusType":"invalid"
							 };
		queryBorrow("../borrow/l_historical_assets/borrowList",srhData);
		myPage();
	});
  
  
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