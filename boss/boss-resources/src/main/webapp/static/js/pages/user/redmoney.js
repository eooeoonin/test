/**
 * 
 */
var _pages;
$(function () {
	
	var srhData = {"pageNo":"1","pageSize":"10"};
	
	tableFun("/user/historyredpackets/sendRedMoney/list", srhData);	
	myPage();
	var _table = $('#table');
	  _table.bootstrapTable();
})

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
        
        _pages = data.pages;
        
        $.each(data.list, function (k, v) {
          //获取数据
          var d_ID = v.id,
          d_userId = v.userId,
          d_createTime = v.createTime,
          	d_realName = v.realName,
            d_account = v.accountMoney.amount,
            d_times = v.times;
            if (v.type == "0"){
            	d_type = "成功";
            } else {
            	d_type = "失败";
            }
            if (v.status == "0"){
            	d_status = "单发";
            } else {
            	d_status = "群发";
            }

          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_realName + '</td>>';
          tableBodyHtml += '<td>' + d_userId + '</td>>';
          tableBodyHtml += '<td>' + d_account + '元</td>';
          tableBodyHtml += '<td>' + d_times+  '天</td>';
          tableBodyHtml += '<td>' + d_status+  '</td>';
          tableBodyHtml += '<td>' + d_type + '</td>';
          tableBodyHtml += '<td>' + d_createTime+ '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
        replaceFun(_table);
      },
    async : false
    });
  }

$("#sousuo").click(function(){

	var type = document.getElementById("type").value;
	if(type=="成功"){
		type="0";
	}else if(type=="失败"){
		type="1"
	}
	var userId = document.getElementById("userId").value;
	if(type != null || id != null){
		  var srhData = {
								"pageNo" : "1",
								"pageSize" : "10",
								"type": type,
								"userId":userId
							   };
			tableFun("../user/historyredpackets/sendRedMoney/list",srhData);
			myPage();
	}
});

var myPage = function(){

	   var $tcdPage = $(".tcdPageCode");
	   
	   
	   $tcdPage.createPage({
	         pageCount:_pages,
	         current: 1,
	         
	         backFn: function (p) {
	        		var type = document.getElementById("type").value;
	        		if(type=="成功"){
	        			type="0";
	        		}else if(type=="失败"){
	        			type="1"
	        		}
	        		var userId = document.getElementById("userId").value;
	       //点击分页事件
	   	  var srhData = {
	 				"pageNo" : p,
	 				"pageSize" : "10",
	 				"type": type,
					"userId":userId	
	 			};
	   	  tableFun("../user/historyredpackets/sendRedMoney/list", srhData);
	     }
	   });  
}
