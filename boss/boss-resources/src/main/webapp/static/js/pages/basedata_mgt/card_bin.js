/**
 * 
 */

$(function () {
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("/basedata_mgt/b_card_bin_list/CardBin/page", srhData);	

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
        var _data = eval('(' + data + ')');
        $.each(_data, function (k, v) {
          //获取数据
          var d_ID = v.id,
            d_number = v.number,
            d_bankCode = v.bankCode,
            d_bankName = v.bankName,
            
            d_cardDesc = v.cardDesc,
            d_cardType = v.cardType,
            d_cardAccount = v.cardAccount,
            d_cardLength = v.cardLength;
          
            if(v.available==1){
            	d_available = "是";
            }else{
            	d_available = "否";
            }

          //输出HTML元素
          tableBodyHtml += '<tr heigth="53">';
//          tableBodyHtml += '<td>' + d_ID + '</td>';
          tableBodyHtml += '<td>' + d_number + '</a></td>>';
          tableBodyHtml += '<td>' + d_bankCode + '</td>';
          tableBodyHtml += '<td>' + d_bankName + '</td>';
          tableBodyHtml += '<td>' + d_cardDesc + '</td>';
          tableBodyHtml += '<td>' + d_cardType + '</td>';
          tableBodyHtml += '<td>' + d_cardAccount + '</td>';
          tableBodyHtml += '<td>' + d_cardLength + '</td>';
          tableBodyHtml += '<td>' + d_available + '</td>';
          tableBodyHtml += '<td><a  href="b_card_bin_list_edit.html?id='+d_ID+'">编辑</a><a  href="javascript:" style="margin-left:15px;" onclick="cardDel('+d_ID+')">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);

      }
    });
  }

	function getPageCount() {
		var pageCount=1;
		var srhData = "";
		$.ajax({
			type : "POST",
			url : "/basedata_mgt/b_card_bin_list/CardBin/list",
			data : srhData,
			async:false,
			datatype : "json",
			success : function(data) {
				pageCount = Math.ceil(data/10);	 
//				alert(data);
//				pageCount = data;
			
			}
		});
		return pageCount;
	}
	//alert(getPageCount());


	/**
	 * 分页
	 */

   var $tcdPage = $(".tcdPageCode");
   $tcdPage.createPage({
     pageCount: getPageCount(),
     current: 1,
     backFn: function (p) {

    	 var srhData = {"pageNo":p,"pageSize":"10"};
     tableFun("/basedata_mgt/b_card_bin_list/CardBin/page",srhData);
		     }
	});
});


/**
 * 删除
 */

//function cardDel(id){
//	
//	
//	var flag = confirm('确定要删除记录吗?');
//	if(flag){		
//		$.ajax({
//			type : "GET",
//			url : "/basedata_mgt/b_card_bin_list/CardBin/delete",
//			dataType : "json",
//			data:{
//				"id":id
//			},
//						      
//			success : function(data) {
//				alert("删除成功");
//				window.location.reload();
//			},error: function(){
//				alert("删除");
//				window.location.reload();
//			}
//			
//		});
//	}
//	
//}



function cardDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/b_card_bin_list/CardBin/delete",
					data : {"id" : id },
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
