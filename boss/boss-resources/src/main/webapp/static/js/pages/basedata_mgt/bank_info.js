 /**
 * 
 */

$(function () {
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("/basedata_mgt/b_bank_info_list/BankInfo/page", srhData);	

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
          var d_ID = v.id,
            
            d_bankCode = v.bankCode,
            d_bankName = v.bankName;
        
            if(v.available==1){
            	d_available = "是";
            }else{
            	d_available = "否";
            }

          //输出HTML元素
          tableBodyHtml += '<tr>';
//          tableBodyHtml += '<td>' + d_ID + '</td>';

          tableBodyHtml += '<td>' + d_bankCode + '</td>';
          tableBodyHtml += '<td>' + d_bankName + '</td>';
          tableBodyHtml += '<td>' + d_available + '</td>';
          tableBodyHtml += '<td><a  href="b_bank_info_list_edit.html?id='+d_ID+'">编辑</a><a name='+d_ID+'  href="javascript:" style="margin-left:15px;" onclick="bankInfoDel(this)">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
      }
    });
  }

//	function getPageCount() {
//		var pageCount=1;
//		var srhData = "";
//		$.ajax({
//			type : "POST",
//			url : "/boss/CardBin/list",
//			data : srhData,
//			async:false,
//			datatype : "json",
//			success : function(data) {
//				pageCount = Math.ceil(data/10);
//			
//			}
//		});
//		return pageCount;
//	}
//	alert(getPageCount());


	/**
	 * 分页
	 */

   var $tcdPage = $(".tcdPageCode");
   $tcdPage.createPage({
     pageCount: 2,
     current: 1,
     backFn: function (p) {

    	 var srhData = {"pageNo":p,"pageSize":"10"};
     tableFun("/basedata_mgt/b_bank_info_list/BankInfo/page",srhData);
		     }
	});
});


/**
 * 删除
 */

//function bankInfoDel(id){
//	
////	alert(id.name);
//	var flag = confirm('确定要删除记录吗?');
//	if(flag){		
//		$.ajax({
//			type : "POST",
//			url : "/basedata_mgt/b_bank_info_list/BankInfo/delete",
//			dataType : "json",
//			data:{
//				"id":id.name
//			},
//						      
//			success : function(data) {
//				alert("删除成功");
//				window.location.reload();
//			},error: function(){
//				alert("删除失败");
//				window.location.reload();
//			}
//			
//		});
//	}
//	
//}



function bankInfoDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/b_bank_info_list/BankInfo/delete",
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
