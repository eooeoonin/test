/**
 * 
 */
var _pages;
$(function () {
	
	var srhData = {"pageNo":"1","pageSize":"10"};
	
	tableFun("../basedata_mgt/iprotol_list/list", srhData);	
	myPage();
	var _table = $('#table');
	  _table.bootstrapTable();

})
	/**
	 * 
	 */
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
        var p_id = v.id,
              p_protocolId = v.protocolId,
              p_type = transferType(v.type),
              p_name = v.name,
             
              p_protocolDesc = v.protocolDesc,
          	  p_available = v.available;
          	 
        
          	  if(p_available == 1){
          		p_available = '可用';
          	  }if(p_available == 0){
          		p_available = '不可用';
          	  }

          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + p_protocolId + '</a></td>>';
          tableBodyHtml += '<td>' + p_type + '</td>';
          tableBodyHtml += '<td>' + p_name + '</td>';
          
          tableBodyHtml += '<td>' + p_protocolDesc + '</td>';
          tableBodyHtml += '<td>' + p_available + '</td>';
          tableBodyHtml += '<td><a href="../basedata_mgt/iprotol_list_update_iportol.html?id='+p_id+'" style="margin-left:15px;">编辑</a><a  title='+p_id+' href="#" style="margin-left:15px;" onclick="deleteiportol(\''+ p_id +'\')">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
      },
    async : false
    });
  }
   
   
   
	   //分页
	   //try {
	var myPage = function(){
	   var $tcdPage = $(".tcdPageCode");
	   $tcdPage.createPage({
	         pageCount:_pages,
	         current: 1,
	         backFn: function (p) {
	       //点击分页事件
	   	  var srhData = {
	 				"pageNo" : p,
	 				"pageSize" : "10"
	 			};
	   	  tableFun("../basedata_mgt/iprotol_list/list", srhData);
	     }
	   });  
}

	function deleteiportol(pid){
		  bootbox.confirm("确定要删除吗?", function(result){
				if(result){
					$.ajax({
						type : "POST",
						url : "../basedata_mgt/iprotol_list/delete",
						data : {"pid" : pid},
						dataType: "json",
						async:false,
						success : function(data) {
							
							if (data != null && data != "") {
								if (data == 1) {
									bootbox.alert("协议删除成功", function(result){
										window.location.reload();
									});
								} else {
									bootbox.alert(data.msg);
								}
							}
						},
						async : false
					});
				}
			});
	}

	function transferType(type){
		var typeName = "";
		switch (type){
		case "p2p": typeName = "出借";break;
		case "crowd": typeName = "众筹";break;
		case "other": typeName = "其它";break;
		default : typeName = "";
		}
		return typeName;
	}