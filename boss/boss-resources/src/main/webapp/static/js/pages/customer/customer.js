var _pages;

function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	var a = data.data.businessObject.total;
    	if(a!=0){
        var _table = $('#table'),
        tableBodyHtml = '';
        _pages= data.data.businessObject.pages;
        var _data = data.data.businessObject;
        
        $.each(_data.list, function (k, v) {
          // 获取数据
        	var registerMobile = v.registerMobile,
        	nickName = v.nickName,
        	userId = v.id;
          // 输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + userId + '</td>';
          tableBodyHtml += '<td>' + registerMobile + '</td>';
          tableBodyHtml += '<td>' + nickName + '</td>';
          tableBodyHtml += '<td><a href="../customer/customer_user_detailedinformation.html?id='+userId+'">查看详情</a></td>';
          tableBodyHtml += '</tr>';
        	
        });
        $("#tcdPagehide").show();
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
    	}else{
    		 var _table = $('#table'),
    	     tableBodyHtml = '';
    		 tableBodyHtml +='<tr class="no-records-found">';
    		 tableBodyHtml +='<td colspan="5">没有找到匹配的记录</td>';
    		 tableBodyHtml += '</tr>';
    		 _table.find("tbody").html(tableBodyHtml);
    		 $("#tcdPagehide").hide();
    	}
      },
	  async : false
    });
  }

$("#srhBtn").click(function(){
	var registerMobile = $("#registerMobile").val();
	var nickName = $("#nickName").val();
	var userId = $("#userId").val();
	var srhData = {"pageNo":"1","pageSize":"10" ,"registerMobile":registerMobile, "nickName":nickName, "id":userId};
	tableFun("/user/user_list/userSelectByPhone", srhData);
	myPage();
});

var myPage = function(){
    // 分页
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
      pageCount: _pages,
      current: 1,
      backFn: function (p) {
    	  // 点击分页事件
    	  	var registerMobile = $("#registerMobile").val();
    		var nickName = $("#nickName").val();
    		var userId = $("#userId").val();
    		var srhData = {"pageNo":p,"pageSize":"10" ,"registerMobile":registerMobile, "nickName":nickName, "id":userId};
	  	  tableFun("/user/user_list/userSelectByPhone", srhData);
      }
    });
}
