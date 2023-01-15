/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages ;
var _currentPage;

var Request = {};
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
$(function () {
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
  Request = GetRequest();
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
      	if(data.data.total!=0){
        _pages= data.data.pages;
        _currentPage = data.data.pageNum;
        var _table = $('#table'),
            tableBodyHtml = '';
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
          tableBodyHtml += '<td>' + v.investUserId + '</td>';
          tableBodyHtml += '<td>' + v.investUserName + '</td>';
          tableBodyHtml += '<td>' + v.investAmount.amount + '</td>';
          if(v.killTime){
        	  tableBodyHtml += '<td>' + v.killTime + '</td>';
          }else{
        	  tableBodyHtml += '<td></td>';
          }
          tableBodyHtml += '<td>' + status + '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        $("#tcdPagehide").show();
        //复选框效果
        //chkFun();

        //分页
        var $tcdPage = $(".tcdPageCode");
          $tcdPage.createPage({
            pageCount: _pages,
            current: _currentPage,
            backFn: function (p) {
              //点击分页事件
              var srhData = {"pageNo":p,"pageSize":"10","loanId":Request.loanId};
              tableFun("/project/p_killrecord/getKillRecordDetail", srhData);
            }
        });
      
      }else{
    	  $("#tcdPagehide").hide();
      }
      }
    });
  }

  var srhData = {"pageNo":1,"pageSize":"10","loanId":Request.loanId};
  tableFun("/project/p_killrecord/getKillRecordDetail", srhData);
});
