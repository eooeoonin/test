/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages ;
var _currentPage;

var start = {
	    elem: "#endDateStart", format: "YYYY-MM-DD", istoday: true, choose: function (datas) {
	        end.min = datas;
	        end.start = datas
	    }
	};
	var end = {
	    elem: "#endDateEnd", format: "YYYY-MM-DD", istoday: true, choose: function (datas) {
	        start.max = datas
	    }
	};
	try {
	    laydate(start);
	    laydate(end);
	} catch (e) {
	}

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
              d_openTime=v.openTime,
              d_interestStartDate=v.interestStartDate,
              d_endTime=v.endTime,
              d_borrowCycle=v.borrowCycle;

          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_loanCode + '</td>';
          tableBodyHtml += '<td>' + d_loanTypeName + '</td>';
          tableBodyHtml += '<td><a href="p_project_info.html?loanCode='+d_loanCode+'">' + d_title + '</a></td>>';
          tableBodyHtml += '<td>' + d_yearRate + '%</td>';
          tableBodyHtml += '<td>' + d_borrowCycle + '</td>';
          tableBodyHtml += '<td>' + d_amount + '</td>';
          tableBodyHtml += '<td>' + d_openTime + '</td>';
          tableBodyHtml += '<td>' + d_interestStartDate + '</td>';
          tableBodyHtml += '<td>' + d_endTime + '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        $("#tcdPagehide").show();
        //复选框效果
        //chkFun();

        //分页
        var $tcdPage = $(".tcdPageCode");
        $tcdPage.createPage({
          pageCount:  _pages,
          current: _currentPage,
          backFn: function (p) {
        	  var srhData ={};
        	  if($("#endDateStart").val() != null && $("#endDateStart").val() != "" && $("#endDateEnd").val() != null && $("#endDateEnd").val() != ""){
        		  srhData = {"pageNo":p,"pageSize":"10","id":$("#id").val(),"title":$("#title").val(),"endDateStart":$("#endDateStart").val(),"endDateEnd":$("#endDateEnd").val()};
        	  }else{
        		  srhData = {"pageNo":p,"pageSize":"10","id":$("#id").val(),"title":$("#title").val()};
        	  }
            tableFun("/project/p_completed_list/list", srhData);
          }
        });
     
      }else{
    	  tableBodyHtml +='<tr class="no-records-found">';
    	  tableBodyHtml +='<td style="text-align:center; vertical-align:middle;" colspan="7">没有找到匹配的记录</td>';
    	  tableBodyHtml += '</tr>';
    	  _table.find("tbody").html(tableBodyHtml);
    	  $("#tcdPagehide").hide();
      }
      }
    });
  }

  var srhData = {"pageNo":1,"pageSize":"10"};
  tableFun("/project/p_completed_list/list", srhData);
  
  $("#srhBtn").click(function(){
	  var srhData ={};
	  if($("#endDateStart").val() != null && $("#endDateStart").val() != "" && $("#endDateEnd").val() != null && $("#endDateEnd").val() != ""){
		  srhData = {"pageNo":"1","pageSize":"10","id":$("#id").val(),"title":$("#title").val(),"endDateStart":$("#endDateStart").val(),"endDateEnd":$("#endDateEnd").val()};
	  }else{
		  srhData = {"pageNo":"1","pageSize":"10","id":$("#id").val(),"title":$("#title").val()};
	  }
	  tableFun("/project/p_completed_list/list", srhData);
  });
});