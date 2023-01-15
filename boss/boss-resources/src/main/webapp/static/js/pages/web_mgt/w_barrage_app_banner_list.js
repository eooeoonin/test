/**
 * 
 */

$(function () {
	var srhData = {"pageNo":"1","pageSize":"5"};
	tableFun("/web_mgt/w_other_barrage_app_banner_list/banner/page", srhData);	

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
    	  var _data = eval('(' + data + ')');
        var _table = $('#table'),
          tableBodyHtml = '';
        $.each(_data, function (k, v) {
          //获取数据
          if(k>4) {
        	  return ;
          }
          var d_ID = v.id,
            
            d_titile = v.titile, //名称
            d_body = v.body; //内容
                             //修改时间
            d_isShow=v.isShow;//
            if(v.isShow==1){
            	d_isShow= "开启";
            }else{
            	d_isShow = "关闭";
            }

          //输出HTML元素
          tableBodyHtml += '<tr>';
//          tableBodyHtml += '<td>' + d_ID + '</td>';

          tableBodyHtml += '<td>' + d_titile + '</td>';
//          tableBodyHtml += '<td><a  href="'+ d_link + '">'+d_link+'</a></td>';
          tableBodyHtml += '<td>' + d_body + '</td>';
         
          tableBodyHtml += '<td>' + d_isShow + '</td>';
          tableBodyHtml += '<td><a href="w_other_barrage_app_banner_list_edit.html?id='+d_ID+'">编辑</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
      }
    });
  }

/**
 * 删除
 */

function qq11(id){
	
	

	var flag = confirm('确定要删除记录吗?');
	if(flag){		
		$.ajax({
			type : "POST",
			url : "/web_mgt/w_app_banner_list/banner/delete",
			dataType : "json",
			data:{
				"id":id.name
			},
						      
			success : function(data) {
				alert("删除成功");
				window.location.reload();
			},error: function(){
				alert("删除失败");
				window.location.reload();
			}
			
		});
	}
}
});
/**
 * 删除
 */

function qq11(id){
	
	var flag = confirm('确定要删除记录吗?');
	if(flag){		
		$.ajax({
			type : "POST",
			url : "/web_mgt/w_app_banner_list/banner/delete",
			dataType : "json",
			data:{
				"id":id.name
			},
						      
			success : function(data) {
				alert("删除成功");
				window.location.reload();
			},error: function(){
				alert("删除失败");
				window.location.reload();
			}
			
		});
	}
}