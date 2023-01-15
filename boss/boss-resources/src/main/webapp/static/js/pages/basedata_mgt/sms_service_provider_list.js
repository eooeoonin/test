/**
 * 
 */

$(function () {
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("/basedata_mgt/sms_service_provider_list/Agency/page", srhData);		

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
      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';
        
        $.each(data.list, function (k, v) {
          //获取数据
          var d_ID = v.id,
            d_name = v.name,
            d_channelCode = v.channelCode,
            d_price = v.price,
            d_stableLevel = v.stableLevel,
            d_speed = v.speed,
            d_maxPhones = v.maxPhones,
            d_level = v.level;
          
            if(v.open==1){
            	d_open = "开放";
            }else{
            	d_open = "关闭";
            }

          //输出HTML元素
          tableBodyHtml += '<tr heigth="53">';
          tableBodyHtml += '<td>' + d_name + '</a></td>>';
          tableBodyHtml += '<td>' + d_channelCode + '</td>';
          tableBodyHtml += '<td>' + d_price + '</td>';
          tableBodyHtml += '<td>' + d_stableLevel + '</td>';
          tableBodyHtml += '<td>' + d_speed + '</td>';
          tableBodyHtml += '<td>' + d_maxPhones + '</td>';
          tableBodyHtml += '<td>' + d_level + '</td>';
          tableBodyHtml += '<td>' + d_open + '</td>';
          tableBodyHtml += '<td><a  href="sms_service_provider_list_edit.html?id='+d_ID+'">编辑</a><a  name='+d_ID+'  href="javascript:" style="margin-left:15px;" onclick="cardDel(this)">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);

      }
    });
  }


	
});






function cardDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
		  if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/sms_service_provider_list/Agency/delete",
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
