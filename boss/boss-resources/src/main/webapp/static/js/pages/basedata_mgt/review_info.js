 /**
 * 
 */
var _page;
$(function () {
	var srhData = {"pageNo":"1","pageSize":"5"};
	tableFun("/basedata_mgt/review_info_list/ireview/page", srhData);
	myPage();

	
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：LSC
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();


  /***
   *功能说明：表格数据
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：LSC
   *时间：2016-08-01
   ***/
});



function tableFun(tdUrl, tbData) {
	   
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      
      
      dataType: "json",
      success: function (data) {
    	  _page=data.pages;
    	  
//    	  var _data = eval('(' + data + ')');
        var _table = $('#table'),
          tableBodyHtml = '';
        $.each(data.list, function (k, v) {
          //获取数据
          var d_ID = v.id,
            
            d_title = v.title,
            d_body = v.body;
            d_time = v.createTime;


          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_title + '</td>';
          tableBodyHtml += '<td>' + d_body + '</td>';
          tableBodyHtml += '<td>' + d_time + '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
      },
	async : false//同步加载
    });
  }

/**
 * 分页
 */

var myPage = function(){
var $tcdPage = $(".tcdPageCode");

$tcdPage.createPage({

 pageCount:  _page,
 current: 1,
 backFn: function (p) {

	 var srhData = {"pageNo":p,"pageSize":"5"};
 tableFun("/basedata_mgt/review_info_list/ireview/page",srhData);
	     }
});
};
	

