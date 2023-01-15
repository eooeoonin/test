/**
 * xiaxingxing1
 */
var _pages;
$(function (){
	var srhData = {
			"pageIndex":"1",
			"pageSize":"10"
	};
	tableFun("/basedata_mgt/sendMsgTask/sendMsgTaskForList", srhData);
	myPage2();
	
	//查询
	var _srhBtn = $("#srhBtn");
	_srhBtn.click(function () {
		  var v_subject = $("#subject").val();
			  var srhData2 = {
						"pageIndex" : "1",
						"pageSize" : "10",
						"name" : v_subject
				};
				tableFun("/basedata_mgt/sendMsgTask/sendMsgTaskForList",srhData2);
				myPage2();	
	});
	
	//清空
	var _qingBtn = $("#qingBtn");
	_qingBtn.click(function(){
		  location = "../basedata_mgt/sendMsgTask.html";		  
	});
});

	//列表
	var sizedata;
	
  function tableFun(tdUrl, tbData, tbPage) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  if(data.resCode == 0){
    		  sizedata = data.total;
    		  console.log(data);
    		 var _table = $('#table'),
              tableBodyHtml = '';
    		 
    		if(data.data.total > 10){
    			 _pages = data.data.total / 10  + 1; 
    		 }else{
    			 _pages=1;
    		 }
    		
    		  
    		  var _data = data.data.list;
    		     $.each(_data, function (k, v) {
    		    	 var T_id = v.id,
    		    	 name = v.name,
    		    	 sendType = v.sendType,
    		    	 groupId = v.groupId,
    		    	 createTime =  v.createTime,
    		    	 status =  v.status
    		    	 
    		          //输出HTML元素
    		          tableBodyHtml += '<tr><td>' + name + '</td>';
    		          tableBodyHtml += '<td>' + sendType + '</td>';
    		          tableBodyHtml += '<td>' + groupId + '</td>';
    		          tableBodyHtml += '<td>' + createTime + '</td>';
    		          if(status=="DEFAULT"){
    		        	  tableBodyHtml += '<td>默认</td>';
    		          }else if(status=="REPEATABLE"){
    		        	  tableBodyHtml += '<td>重发</td>';
    		          }else if(status=="UNDO"){
    		        	  tableBodyHtml += '<td>撤销</td>';
    		          }else if(status=="FINISH"){
    		        	  tableBodyHtml += '<td>完成</td>';
    		          }else{
    		        	  tableBodyHtml += '<td>状态错误</td>'; 
    		          }
    		          
    		          if(status=="DEFAULT" || status=="REPEATABLE"){
    		        	  tableBodyHtml += '<td><a href="../basedata_mgt/sendMsgTask_check.html?id='+T_id+'">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardChe(\''+T_id+'\',\''+v.status+'\')">撤销</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardDel(\''+T_id+'\')">删除</a></td>'
    		          }else if(status=="UNDO"){
    		        	  tableBodyHtml += '<td><a href="../basedata_mgt/sendMsgTask_check.html?id='+T_id+'">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../basedata_mgt/sendMsgTask_edit.html?id='+T_id+'">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardRepeat(\''+T_id+'\',\''+v.status+'\')">重发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardDel(\''+T_id+'\')">删除</a></td>';
    		          }else if(status == "FINISH"){
    		        	  tableBodyHtml += '<td><a href="../basedata_mgt/sendMsgTask_check.html?id='+T_id+'">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardDel(\''+T_id+'\')">删除</a></td>'
    		          }else{
    		        	  tableBodyHtml += '<td><a href="../basedata_mgt/sendMsgTask_check.html?id='+T_id+'">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../basedata_mgt/sendMsgTask_edit.html?id='+T_id+'">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardRepeat(\''+T_id+'\',\''+v.status+'\')">重发</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardChe(\''+T_id+'\',\''+v.status+'\')">撤销</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardDel(\''+T_id+'\')">删除</a></td>';
    		          }
    		          tableBodyHtml += '</tr>';
    		        });
    		     $("#tcdPagehide").show(); 
    		     _table.find("tbody").html(tableBodyHtml);
    		       replaceFun(_table);
    	  }else{
    		  alert("查询异常");
    	  }
    	console.log(data);
      },
    async : false,
    });
  }
    	  
  
  function myPage2(){
	  if(sizedata!== 0){
		var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(q) {
					v_subject = $("#subject").val();				  	
				  	var srhData3 = {
							"pageIndex" :q,
							"pageSize" : "10",	
							"subject" : v_subject
					};
					tableFun("/basedata_mgt/sendMsgTask/sendMsgTaskForList", srhData3);	
			}
		});
}else{
	$("#tcdPagehide").hide();
}
	  
}
  
  function cardDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
		  if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/sendMsgTask/sendMsgTaskDelete",
					data : {"id" : id},
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
  
  function cardChe(id,b){
	  bootbox.confirm("确定要撤销吗?", function(result){
		  if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/sendMsgTask/sendMsgTaskcardChe",
					data : {"id" : id,"status":"UNDO"},
					async:false,
					success : function(data) {
						
						if (data.resCode == 0) {
							
								bootbox.alert("撤销成功", function(result){
									window.location.reload();
								});
							
						}else{
							bootbox.alert("撤销失败", function(result){
								window.location.reload();
							});
						}
					},
					async : false,
					error :function(e){
						console.log(e);
					}
				});
			}
		});
	  
	  
	  
}
  
  
  function cardRepeat(id,b){
	  bootbox.confirm("确定要重发吗?", function(result){
		  if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/sendMsgTask/sendMsgTaskcardChe",
					data : {"id" : id,"status":"REPEATABLE"},
					async:false,
					success : function(data) {
						
						if (data.resCode == 0) {
							
								bootbox.alert("重发成功", function(result){
									window.location.reload();
								});
							
						}else{
							bootbox.alert("重发失败", function(result){
								window.location.reload();
							});
						}
					},
					async : false,
					error :function(e){
						console.log(e);
					}
				});
			}
		});
	  
	  
	  
}
//新增按钮跳转页面
var _addmess = $("#addSendMsgTask");
_addmess.click(function(){
	location = "../basedata_mgt/sendMsgTask_insert.html";	
});












