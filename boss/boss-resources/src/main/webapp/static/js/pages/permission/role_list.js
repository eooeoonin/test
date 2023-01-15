var _pages;
  
  
$(function () {
  var _table = $('#table');
  _table.bootstrapTable();
  $(window).resize(function () {
  });
  
  var srhData = {"pageNo":"1","pageSize":"10"};
  tableFun("/permission/role/rolesWithPage", srhData);
  myPage();

});


function tableFun(tdUrl, tbData) {
	  $.ajax({
	    type: "POST",
	    url: tdUrl,
	    data: tbData,

	    dataType: "json",
	    success: function (data) {
	    var _data = data.data.list;
	    if(_data != ""){
	    	var _table = $('#table'),
	          tableBodyHtml = '';
	          //获取数据
	       _pages= data.data.pages;
	      	for(var key = 0; key < _data.length; key++){
	      		var d_id = _data[key].id,
	      		roleName = _data[key].roleName,
	      		remark = _data[key].remark;

	            //输出HTML元素
	            tableBodyHtml += '<tr>';
	            tableBodyHtml += '<td>' + roleName + '</td>';
	            tableBodyHtml += '<td>'+ remark + '</td><td>';
	            if("1" != d_id){
	            	 tableBodyHtml += '<a href="role_edit.html?type=edit&roleId='+ d_id +'"> 编辑</a>  <a href="#" onclick= "deleteRole(\''+ d_id +'\')">删除</a>';
	            }
	            tableBodyHtml += '</td></tr>';
	      	}
	      	
	      _table.find("tbody").html(tableBodyHtml);
	    }
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
	  	  tableFun("/permission/role/rolesWithPage", srhData);
	  	  
	    }
	  });
	
}

//删除
function deleteRole(roleId){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "DELETE",
					url : "/permission/role/delete/"+ roleId,
					data : {
						"roleId" : roleId
					},
					success : function(data) {
						if (data != null && data != "") {
							if (data.resCode == 0) {
								bootbox.alert("角色删除成功", function(result){
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