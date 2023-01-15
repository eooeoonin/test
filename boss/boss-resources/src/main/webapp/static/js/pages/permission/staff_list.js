var _pages;
  
  
$(function () {
  var _table = $('#table');
  _table.bootstrapTable();
  $(window).resize(function () {
  });
  
  var srhData = {"pageNo":"1","pageSize":"10"};
  tableFun("/permission/staff/staffsWithPage", srhData);
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
	              staffName = _data[key].staffName,
	              departmentId = _data[key].departmentId,
	              departmentName = _data[key].departmentName,
	              roleId = _data[key].roleId,
	              roleName = _data[key].roleName,
	              email = _data[key].email,
	              mobile = _data[key].mobile;
	      		  status = _data[key].status;

	            //输出HTML元素
	            tableBodyHtml += '<tr>';
	            tableBodyHtml += '<td>' + staffName + '</td>';
	            tableBodyHtml += '<td>'+ departmentName + '</td>';
	            tableBodyHtml += '<td>' + roleName + '</td>';
	            tableBodyHtml += '<td>' + email + '</td>';
	            tableBodyHtml += '<td>' + mobile + '</td>';
	            if (status == "INIT") { 
	            	tableBodyHtml += '<td>未激活</td><td>';
	            } else if (status == "ACTIVATED"){
	            	tableBodyHtml += '<td>已激活</td><td>';
	            } else if (status == "DISABLED"){
                    tableBodyHtml += '<td>已停用</td><td>';
                }  else {
	            	tableBodyHtml += '<td>——</td><td>';
	            }
	            if("1" != roleId){
	            	if("INIT" == status){
	            		tableBodyHtml += '<a href="staff_edit.html?type=edit&staffId='+ d_id +'"> 编辑</a>  &nbsp; <a href="#" onclick= "deleteStaff(\''+ d_id +'\')">停用</a> &nbsp; <a href="staff_edit_password.html?type=editPass&staffId='+ d_id +'">修改密码</a> &nbsp; <a href="#" onclick= "sendEmail(\''+ staffName +'\')">发送激活邮件</a>';
	            	} else if (status == "ACTIVATED") {
                        tableBodyHtml += '<a href="staff_edit.html?type=edit&staffId='+ d_id +'"> 编辑</a>  &nbsp; <a href="#" onclick= "deleteStaff(\''+ d_id +'\')">停用</a> &nbsp; <a href="staff_edit_password.html?type=editPass&staffId='+ d_id +'">修改密码</a>';
                    }else if (status == "DISABLED") {
                        tableBodyHtml += '';
                    }
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
	  	  tableFun("/permission/staff/staffsWithPage", srhData);
	  	  
	    }
	  });
	
}

//删除
function deleteStaff(staffId){
	  bootbox.confirm("确定要停用吗?", function(result){
			if(result){
				$.ajax({
					type : "DELETE",
					url : "/permission/staff/delete/"+ staffId,
					data : {
						"staffId" : staffId
					},
					success : function(data) {
						if (data != null && data != "") {
							if (data.resCode == 0) {
								bootbox.alert("员工停用成功", function(result){
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
//发送激活邮件
function sendEmail(staffName){
	bootbox.confirm("确定要发送邮件吗?", function(result){
		if(result){
			$.ajax({
				type : "POST",
				url : "/permission/staff/sendEmail",
				data : {
					"staffName" : staffName
				},
				success : function(data) {
					if (data != null && data != "") {
						if (data == "success") {
							bootbox.alert("邮件发送成功", function(result){
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