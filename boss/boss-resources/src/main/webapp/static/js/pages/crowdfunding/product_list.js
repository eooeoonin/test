var _pages;
  
  
$(function () {
  var _table = $('#table');
  _table.bootstrapTable();
  $(window).resize(function () {
  });
  
  var srhData = {"pageNo":"1","pageSize":"10"};
  tableFun("/crowdfunding/product/listWithPage", srhData);
  myPage();

});


function tableFun(tdUrl, tbData) {
	  $.ajax({
	    type: "POST",
	    url: tdUrl,
	    data: tbData,
	    dataType: "json",
	    success: function (data) {
	    	if (data.resCode == 0) {
	    		 var _data = data.data.businessObject;
	    		    if(_data != ""){
	    		    	  var _table = $('#table'),
	    		          tableBodyHtml = '';
	    		          //获取数据
	    		       _pages= _data.pages;
	    		       _list = _data.list
	    		      	for(var key = 0; key < _list.length; key++){
	    		      		var d_id = _list[key].id,
	    		      			title = _list[key].title,
	    		      			projectName = _list[key].projectName,
	    		      			description = _list[key].description,
	    		      			startTime = _list[key].startTime,
	    		      			endTime = _list[key].endTime,
	    		      			status = _list[key].status;

	    		            //输出HTML元素
	    		            tableBodyHtml += '<tr>';
	    		            tableBodyHtml += '<td><a href="product_info_view.html?productId='+ d_id +'">' + title + '</a></td>';
	    		            tableBodyHtml += '<td>'+ projectName + '</td>';
	    		            tableBodyHtml += '<td>' + description + '</td>';
	    		            tableBodyHtml += '<td>' + startTime + '</td>';
	    		            tableBodyHtml += '<td>' + endTime + '</td>';
	    		            tableBodyHtml += '<td><a href="product_add_sub.html?productId='+ d_id +'">添加权益</a>&nbsp; &nbsp; <a href="product_edit.html?type=edit&productId='+ d_id +'">编辑</a> &nbsp; &nbsp;';
	    		            if("1"== status){
	    		            	tableBodyHtml += '<a href="#" onclick= "unSale(\''+ d_id +'\')">下架<a>'
	    		            }
	    		            tableBodyHtml += '</td></tr>';
	    		      	}
	    		      	
	    		      _table.find("tbody").html(tableBodyHtml);
	    		    }
	    	}else{
	    		bootbox.alert(data.msg);
	    	}
	    },
	  async : false
	  });
	  
	}


$("#srhBtn").click(function(){
	var title = $("#searchTitle").val();
	var projectName = $("#searchProjectName").val();
	var srhData = {"pageNo":"1","pageSize":"10" ,"title":title, "projectName":projectName};
	tableFun("/crowdfunding/product/listWithPage", srhData);
	myPage();
});



var myPage = function(){
	  //分页
	  var $tcdPage = $(".tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: _pages,
	    current: 1,
	    backFn: function (p) {
	      //点击分页事件
	  	  var srhData = {"pageNo":p,"pageSize":"10"};
	  	  tableFun("/crowdfunding/product/listWithPage", srhData);
	  	  
	    }
	  });
	
}

//下架产品
function unSale(productId){
	  bootbox.confirm("确定要下架吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/crowdfunding/product/unSale",
					data : {
						"productId" : productId
					},
					success : function(data) {
						if (data != null && data != "") {
							if (data.resCode == 0) {
								bootbox.alert("产品下架成功", function(result){
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