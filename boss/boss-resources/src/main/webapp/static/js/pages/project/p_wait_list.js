/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages ;
var _currentPage;
$(function () {
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/

  function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  var _table = $('#table'),
    	  tableBodyHtml = '';
    	  //alert(data.data.total);
    	if(data.data.total!=0){
        _pages= data.data.pages;
        _currentPage = data.data.pageNum;
        
        $.each(data.data.list, function (k, v) {
          //获取数据
          var d_loanCode = v.loanCode,
              d_loanTypeName = v.loanTypeName,
              d_title = v.title,
              d_yearRate=v.yearRate,
              d_amount=v.amount.amount,
              d_interestStartDate=v.interestStartDate,
              d_interestEndDate=v.interestEndDate,
              d_borrowCycle=v.borrowCycle;
          
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_loanCode + '</td>';
          tableBodyHtml += '<td>' + d_loanTypeName + '</td>';
          tableBodyHtml += '<td><a href="p_project_info.html?loanCode='+d_loanCode+'">' + d_title + '</a></td>>';
          tableBodyHtml += '<td>' + d_yearRate + '%</td>';
          tableBodyHtml += '<td>' + d_borrowCycle + '</td>';
          tableBodyHtml += '<td>' + d_amount + '</td>';
          tableBodyHtml += '<td>' + d_interestStartDate + '</td>';
          tableBodyHtml += '<td>' + d_interestEndDate + '</td>';
          tableBodyHtml += '<td><a href="p_wait_edit.html?loanCode='+ d_loanCode +'" style="margin-left:15px;">编辑</a></td>';
          tableBodyHtml += '</tr>';
         
        });
        _table.find("tbody").html(tableBodyHtml);
      
        $("#tcdPagehide").show();
        var $tcdPage = $(".tcdPageCode");
        $tcdPage.createPage({
          pageCount:  _pages,
          current: _currentPage,
          backFn: function (p) {
            var srhData = {"pageNo":p,"pageSize":"10","id":$("#id").val(),"title":$("#title").val()};
            tableFun("/project/p_wait_list/list", srhData);
          }
        });
     
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
  /**
   * 分页
   */

  var srhData = {"pageNo":"1","pageSize":"10"};
  tableFun("/project/p_wait_list/list", srhData);
  
  
  $("#srhBtn").click(function(){
	  var srhData = {"pageNo":"1","pageSize":"10","id":$("#id").val(),"title":$("#title").val()};
	  tableFun("/project/p_wait_list/list", srhData);
  });
});


