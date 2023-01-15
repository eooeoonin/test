var _pages;
  
  
$(function () {
  var _table = $('#table');
  _table.bootstrapTable();
  $(window).resize(function () {
  });
  
  var srhData = {"pageNo":"1","pageSize":"10"};
  tableFun("/permission/department/departmentsWithPage", srhData);
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
	              departmentName = _data[key].departmentName,
	              departmentCode = _data[key].departmentCode,
	              departmentType = _data[key].departmentType;

	            //输出HTML元素
	            tableBodyHtml += '<tr>';
	            tableBodyHtml += '<td>' + departmentName + '</td>';
	            tableBodyHtml += '<td>'+ departmentCode + '</td>';
	            tableBodyHtml += '<td>' + departmentType + '</td>';
	            tableBodyHtml += '<td><a href="department_edit.html?type=edit&departmentId='+ d_id +'"> 编辑</a>  <a href="#" onclick= "deleteDepartment(\''+ d_id +'\')">删除</a></td>';
	            tableBodyHtml += '</tr>';
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
	  	  tableFun("/permission/department/departmentsWithPage", srhData);
	  	  
	    }
	  });
	
}

//删除
function deleteDepartment(departmentId){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "DELETE",
					url : "/permission/department/delete/"+ departmentId,
					data : {
						"departmentId" : departmentId
					},
					success : function(data) {
						if (data != null && data != "") {
							if (data.resCode == 0) {
								bootbox.alert("部门删除成功", function(result){
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