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
//        		        var d_platformProfit = v.platformProfit.amount,//平台收益
//        		          	d_penaltyAmount = v.penaltyAmount.amount;//罚息
        		      
    		          tableBodyHtml += '<tr>';
    		          tableBodyHtml += '<td>' + v.borrowId + '</td>';
    		          tableBodyHtml += '<td>' + v.borrowName + '</td>';
    		          if(v.repayTime){
    		        	  tableBodyHtml += '<td>' + v.repayTime + '</td>';
    		          }else{
    		        	  tableBodyHtml += '<td></td>';
    		          }
    		          tableBodyHtml += '<td>' + v.phase + '</td>';
    		          tableBodyHtml += '<td>' + v.principalAmount.amount + '</td>';
    		          tableBodyHtml += '<td>' + v.interestAmount.amount + '</td>';
    		          if (null == v.platformProfit) {
    		        	  tableBodyHtml += '<td>0</td>';
    		          } else {
    		        	  tableBodyHtml += '<td>' + v.platformProfit.amount + '</td>';
    		          } 
    		          if (null == v.penaltyAmount) {
    		        	  tableBodyHtml += '<td>0</td>';
    		          } else {
    		        	  tableBodyHtml += '<td>' + v.penaltyAmount.amount + '</td>';
    		          } 
    		          tableBodyHtml += '<td>' + status + '</td>';
    		          if(v.status == "SUCCESS")
    		        	  tableBodyHtml += '<td><a href="l_RepayRecordDetail.html?repayRecordId='+ v.id +'" style="margin-left:15px;">详情</a></td>';
//    		        	  +'&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" disabled="disabled" style="color:#888888">还款</a></td>';
    		          if(v.status == "INIT")
    		        	  tableBodyHtml += '<td><a href="l_RepayRecordDetail.html?repayRecordId='+ v.id +'" style="margin-left:15px;">详情</a></td>';
//    		        	  +'&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" disabled="disabled" style="color:#888888">还款</a></td>';
    		          if(v.status == "FAIL")
    		        	  tableBodyHtml += '<td><a href="l_RepayRecordDetail.html?repayRecordId='+ v.id +'" style="margin-left:15px;">详情</a></td>';
//    		        	  +'<a  name='+v.borrowId+' href="javascript:" style="margin-left:15px;" onclick=repay(this,"'+v.borrowRepayId+'","'+v.phase+'")>还款</a></td>';
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
    		              var srhData = {"pageNo":p,"pageSize":10};
    		              tableFun("/borrow/l_RepayRecord/getRepayRecord", srhData);
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
  tableFun("/borrow/l_RepayRecord/getRepayRecord", srhData);
});


var repay = function(obj,borrowRepayId,phase){
	var repayType = "COMMON";
	$.ajax({
	      type: "POST",
	      url: "/borrow/l_RepayRecord/repayType",
	      data: {"id":borrowRepayId},
	      success: function (data) {
	        if (data != null && data != "") {
	          if (data.resCode == 0) {
	        	  repayType = data.data;
	          }
	          else {
	            bootbox.alert(data.msg);
	          }
	        }
	      },
	      async: false
	 });
	
	$.ajax({
	      type: "POST",
	      url: "/borrow/l_RepayRecord/repay",
	      data: {"borrowId":obj.name,"phase":phase,"repayType":repayType},
	      success: function (data) {
	    	  
	    	  if (data.resultCode=="C12000000") {
		            bootbox.alert("确定", function () {
		            	window.location.reload();
		            });
		          }
		          else {
		            bootbox.alert(data.resultCodeMsg);
		          }
	      },
	      async: false
	 });
};