var _pages;
  
  
$(function () {
	
	  var srhData = {"pageNo":"1","pageSize":"10"};
	  tableFun("/basedata_mgt/vacation_list/list", srhData);
	  myPage();

  var _table = $('#table');
  _table.bootstrapTable();
  $(window).resize(function () {
  });
  

});


function tableFun(tdUrl, tbData) {
	  $.ajax({
	    type: "POST",
	    url: tdUrl,
	    data: tbData,

	    dataType: "json",
	    success: function (data) {
	    var _data = data.list;
	    _pages= data.pages;
	    if(_data != ""){
	    	  var _table = $('#table'),
	          tableBodyHtml = '';
	          //获取数据
	       
	      	for(var key = 0; key < _data.length; key++){
	      		var d_id = _data[key].id,
	              d_departmentName = _data[key].date,
	              d_departmentName=d_departmentName.substring(0,10);
	              d_isHodiday = _data[key].isHodiday,
	              d_remark = _data[key].remark;
	      		if(d_isHodiday=="Y"){
	      			d_isHodiday="是";
	      		}else if(d_isHodiday=="N"){
	      			d_isHodiday="否";
	      		}
	      		if(d_remark==null){
	      			d_remark=" ";
	      		}
	            //输出HTML元素
	            tableBodyHtml += '<tr>';
	            tableBodyHtml += '<td>' + d_departmentName + '</td>';
	            tableBodyHtml += '<td>'+ d_isHodiday + '</td>';
	            tableBodyHtml += '<td>' + d_remark + '</td>';
	            tableBodyHtml += '<td><a href="vacation_list_add.html?type=edit&departmentId='+ d_id +'"> 编辑</a>  <a href="#" onclick= "deleteDepartment(\''+ d_id +'\')">删除</a></td>';
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
	  	  tableFun("/basedata_mgt/vacation_list/list", srhData);
	  	  
	    }
	  });
	
}

//更新缓存
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	  $.ajax({
	      type: "POST",
	      url: "/basedata_mgt/vacation_list/redis",
	      data: "",
	      dataType: "json",
	      success: function (data) {
	    		if (data != null && data != "") {
					if(data.resultCode==1){
						bootbox.alert("初始化成功", function(){
							location = "vacation_list.html";
						});
				}else{
					bootbox.alert("初始化失败");
				}
					
				}
	      }
});
});
//删除
function deleteDepartment(departmentId){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/vacation_list/delete",
					data : {
						"id" : departmentId
					},
					success : function(data) {
						if (data != null && data != "") {
							
								bootbox.alert("删除成功", function(result){
									location = "../basedata_mgt/vacation_list.html";
									
								});
							
						}
					},
					async : false
				});
			}

		});
}