/**
 * 
 */
var _pages;
$(function () {
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("/basedata_mgt/sms_channel_list/channel/page", srhData);		
	myPage();
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：LSC
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
  $(window).resize(function () {
  });
});
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
      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';
        _pages= data.pages;
        $.each(data.list, function (k, v) {
          //获取数据
          var d_ID = v.id,
            d_merchant = v.merchant,
            d_username = v.username,
            d_url = v.url,
            d_type = v.type,
            d_sign = v.sign;
            
            d_channelCode = v.channelCode
          
            if(v.open==1){
            	d_open = "开放";
            }else{
            	d_open = "关闭";
            }
            
            if(v.smsType==null){
            	d_smsType = "  ";
            }else{
            	d_smsType= v.smsType;
            }
            
            if(v.smsType==null){
            	d_channelCode = "  ";
            }else{
            	 d_channelCode = v.channelCode;
            }
          //输出HTML元素
          tableBodyHtml += '<tr heigth="53">';
          tableBodyHtml += '<td>' + d_merchant + '</a></td>>';
          tableBodyHtml += '<td>' + d_username + '</td>';
//          tableBodyHtml += '<td>' + d_url + '</td>';
          tableBodyHtml += '<td>' + d_open + '</td>';
          tableBodyHtml += '<td>' + d_type + '</td>';
          tableBodyHtml += '<td>' + d_sign + '</td>';
          tableBodyHtml += '<td>' + d_smsType + '</td>';
          tableBodyHtml += '<td>' + d_channelCode + '</td>';
          tableBodyHtml += '<td><a  href="sms_channel_list_edit.html?id='+d_ID+'">编辑</a><a  name='+d_ID+'  href="javascript:" style="margin-left:15px;" onclick="cardDel(this)">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);

      },
	    async : false
    });
  }


	


var myPage = function(){
	  //分页
	  var $tcdPage = $(".tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: _pages,
	    current: 1,
	    backFn: function (p) {
	      //点击分页事件
	  	  var srhData = {"pageNo":p,"pageSize":"10"};
	  	  tableFun("/basedata_mgt/sms_channel_list/channel/page", srhData);
	  	  
	    }
	  });
	
}




function cardDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
		 
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/sms_channel_list/channel/delete",
					data : {"id" : id.name},
					dataType: "json",
					async:false,
					success : function(data) {
						
						if (data != null && data != "") {
							
								bootbox.alert("删除成功", function(result){
									window.location.reload();
								});
							
						}
					},
					async : false
				});
			}
		});
}
