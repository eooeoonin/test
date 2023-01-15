/**
 * 
 */

$(function () {
	var srhData = {"pageNo":"1","pageSize":"5"};
	tableFun("/crowdfunding/crowdfunding_banner_list/banner/page", srhData);	

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
            d_name = v.name, //名称
            d_url = v.url; //链接
          	d_orders = v.orders;//排序            
            d_innerGo=v.innerGo;//APP内跳  
            d_itemId=v.itemId;//项目ID  
            d_homeShow=v.homeShow;//首页显示
            if(d_innerGo==0){ 
            	d_innerGo="否";
            }else{
            	d_innerGo="是";
            }
            if(d_homeShow==1){
            	d_homeShow= "是";
            }else{
            	d_homeShow = "否";
            }
          
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_name + '</td>';
          tableBodyHtml += '<td>' + d_url + '</td>';
          tableBodyHtml += '<td>' + d_orders + '</td>';
          tableBodyHtml += '<td>' + d_innerGo + '</td>';
          tableBodyHtml += '<td>' + d_itemId + '</td>';
          tableBodyHtml += '<td>' + d_homeShow + '</td>';
          tableBodyHtml += '<td><a href="crowdfunding_banner_list_edit.html?id='+d_ID+'">编辑</a><a name='+d_ID+' href="javascript:" style="margin-left:15px;" onclick="qq11(this)">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
      }
    });
  }
});
/**
 * 删除
 */


function qq11(id){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/crowdfunding/crowdfunding_banner_list/banner/delete",
					data:{
						"id":id.name
					},
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

