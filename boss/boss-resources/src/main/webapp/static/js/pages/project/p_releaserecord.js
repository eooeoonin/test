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
    	  if(data.resCode == 0) {
    		  var _table = $('#table'),
	          tableBodyHtml = '';
    		  if(data.data.total!=0){
    		        _pages= data.data.pages;
    		        _currentPage = data.data.pageNum;
    		       
    		        
    		        $.each(data.data.list, function (k, v) {
    		        	var status = "";
        		        switch(v.status) {
        		        case "SUCCESS":
        		        	status="成功";
        		        	break;
        		        case "FAIL":
        		        	status="失败";
        		        	break;
        		        case "INIT":
        		        	status="处理中";
        		        	break;
        		        default:
        		        	break;
        		        }
    		          tableBodyHtml += '<tr>';
    		          tableBodyHtml += '<td>' + v.loanId + '</td>';
    		          tableBodyHtml += '<td>' + v.loanName + '</td>';
    		          tableBodyHtml += '<td>' + v.amount.amount + '</td>';
    		          tableBodyHtml += '<td>' + status + '</td>';
    		          if(v.releaseTime){
    		        	  tableBodyHtml += '<td>' + v.releaseTime + '</td>';
    		          }else{
    		        	  tableBodyHtml += '<td></td>';
    		          }
    		          if(v.status == "SUCCESS")
    		        	  tableBodyHtml += '<td><a href="javascript:void(0);" disabled="disabled" style="color:#888888">放款</a></td>';
    		          if(v.status == "INIT")
    		        	  tableBodyHtml += '<td><a href="javascript:void(0);" disabled="disabled" style="color:#888888">放款</a></td>';
    		          if(v.status == "FAIL")
    		        	  tableBodyHtml += '<td><a  name='+v.loanId+' href="javascript:" style="margin-left:15px;" onclick="releaseLoan(this)">放款</a></td>';
    		          tableBodyHtml += '</tr>';
    		        });
    		        _table.find("tbody").html(tableBodyHtml);
    		        $("#tcdPagehide").show();
    		        var $tcdPage = $(".tcdPageCode");
    		          $tcdPage.createPage({
    		            pageCount: _pages,
    		            current: _currentPage,
    		            backFn: function (p) {
    		              //点击分页事件
    		            	var srhData = {"pageNo":p,"pageSize":"10","loanId":$("#id").val(),"loanName":$("#title").val()};
    		              tableFun("/project/p_releaserecord/getReleaseRecord", srhData);
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
    	  else{
    		  $("#tcdPagehide").hide();
    		  alert(data.msg);
    	  }
      	
      }
    });
  }

  var srhData = {"pageNo":1,"pageSize":10};
  tableFun("/project/p_releaserecord/getReleaseRecord", srhData);
  
  $("#srhBtn").click(function(){
	  var srhData = {"pageNo":"1","pageSize":"10","loanId":$("#id").val(),"loanName":$("#title").val()};
	  tableFun("/project/p_releaserecord/getReleaseRecord", srhData);
  });
});


var releaseLoan = function(obj){
	$.ajax({
	      type: "POST",
	      url: "/project/p_releaserecord/releaseRecordLoan",
	      data: {"loanId":obj.name},
	      success: function (data) {
	        if (data != null && data != "") {
	          if (data.resCode == 0) {
	            bootbox.alert("确定再次放款", function () {
	            	window.location.href = "p_releaserecord.html";
	            });
	          }
	          else {
	            bootbox.alert(data.msg);
	          }
	        }
	      },
	      async: false
	 });
};
