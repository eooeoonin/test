 /**
 * 
 */
var _size;
$(function () {
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("/basedata_mgt/innermessage_list/innermessage/page", srhData);	

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
   function tableFun(tdUrl, tbData) {
	   
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      async:false, 
      dataType: "json",
      success: function (data) {
    	  _size=data.lastPage;
//    	  var _data = eval('(' + data + ')');
        var _table = $('#table'),
          tableBodyHtml = '';
        $.each(data.list, function (k, v) {
          //获取数据
        	var d_ID = v.id,
        	d_createTime = v.createTime,
        	 d_title = v.title,
        	 d_merchant = v.merchant,
            d_content = v.content,
            d_userId = v.userId,
            d_bankName = v.bankName;
        	if(v.type=="NOTIC"){
            	d_type = "公告";
            }else if(v.type=="ACTIVITY"){
            	d_type = "活动信息";
            }else if(v.type="SPCINNERTEMPLATE"){
            	d_type = "特殊站内信";
            }
            if(v.status=="0"){
            	d_status = "未读";
            }else if(v.status=="1"){
            	d_status = "已读";
            }
            

          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_title + '</td>';
          tableBodyHtml += '<td>' + d_type + '</td>';
          tableBodyHtml += '<td>' + d_content + '</td>';
          tableBodyHtml += '<td>' + d_createTime + '</td>';
          
          tableBodyHtml += '<td>' + d_userId + '</td>';
          tableBodyHtml += '<td>' + d_status + '</td>';
          
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
      }
    });
  }



	/**
	 * 分页
	 */

   var $tcdPage = $(".tcdPageCode");
   $tcdPage.createPage({
     pageCount: _size,
     current: 1,
     backFn: function (p) {

    	 var srhData = {"pageNo":p,"pageSize":"10"};
     tableFun("/basedata_mgt/innermessage_list/innermessage/page",srhData);
		     }
	});
});

