/**
 * 
 */
var _pages;
$(function () {
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("/user/user_list/bigRedMoneyList", srhData);	
	myPage5();
});
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
      async: false , 
      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';
        _pages=data.pages;
        var a = data.total;
		if (a != 0) {
        $.each(data.list, function (k, v) {
          //获取数据
         
           d_recieveTime = v.recieveTime,//时间
           d_userId = v.userId,//id
            d_amount = v.amount.amount,//金额
            d_redmoneynum = v.redmoneynum,
            d_redmoneysurplus = v.redmoneysurplus,
            d_amountsurplus = v.amountsurplus.amount;
         

          //输出HTML元素
          tableBodyHtml += '<tr heigth="53">';
          tableBodyHtml += '<td>' + d_recieveTime + '</a></td>>';
          tableBodyHtml += '<td>' + d_userId + '</td>';
          tableBodyHtml += '<td>' + d_amount + '</td>';
        
          tableBodyHtml += '<td>' + d_redmoneynum + '</td>';
          tableBodyHtml += '<td>' + d_redmoneysurplus + '</td>';
          tableBodyHtml += '<td>' + d_amountsurplus + '</td>';
          
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
		}else{
			var html = "";
			html += '<tr class="no-records-found">';
			html += '<td colspan="7" style="text-align:center">没有找到匹配的记录</td>';
			html += '</tr>';
			$("#table").find("tbody").html(html);
			$(".tcdPageCode").hide();
		}

      },
      error:function(){
    	  var html = "";
			html += '<tr class="no-records-found">';
			html += '<td colspan="7" style="text-align:center">没有找到匹配的记录</td>';
			html += '</tr>';
			$("#table").find("tbody").html(html);
			$(".tcdPageCode").hide();
      }
    
    });
  }

	 //充值记录分页
	  var myPage5 = function(){
		  var $tcdPage = $(".tcdPageCode");
			$tcdPage.createPage({
				pageCount : _pages,
				current : 1,
				backFn : function(p) {
					var userId = document.getElementById("userId").value;
					var srhData = {
											"pageNo" : p,
											"pageSize" : "10",
											"userId":userId
										};
					tableFun("/user/user_list/bigRedMoneyList", srhData);
				}
			});
		}
	  
	  
	  $("#sousuo").click(function(){
			var userId = document.getElementById("userId").value;
			if(userId != null){
				  var srhData = {
										"pageNo" : "1",
										"pageSize" : "10",
										"userId" :userId
									   };
				  	tableFun("../user/user_list/bigRedMoneyList",srhData);
				  	myPage5();
			}
		});
	  

