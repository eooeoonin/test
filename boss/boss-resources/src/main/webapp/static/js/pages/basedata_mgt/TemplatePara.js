/**
 * xiaxingxing1
 */
var _pages;
$(function (){
	var srhData = {
			"pageIndex":"1",
			"pageSize":"10"
	};
	tableFun("/basedata_mgt/TemplatePara/templateParaForList", srhData);
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
				tableFun("/basedata_mgt/TemplatePara/templateParaForList",srhData2);
				myPage2();	
	});
	
	//清空
	var _qingBtn = $("#qingBtn");
	_qingBtn.click(function(){
		  location = "../basedata_mgt/TemplatePara.html";		  
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
    	  sizedata = data.total;
    	  if(null!= data.data && data.total!==0 ){
    		  console.log(data);
    		 var _table = $('#table'),
              tableBodyHtml = '';
    		 
    		if(data.data.total > 10){
    			 _pages = parseInt(data.data.total / 10)  + 1; 
    			 //debugger;
    		 }else{
    			 _pages=1;
    		 }
    		
    		  
    		  var _data = data.data.list;
    		     $.each(_data, function (k, v) {
    		    	 var T_id = v.id,
    		    	 name = v.name,
    		    	 code = v.code,
    		    	 content = v.content,
    		    	 createTime =  v.createTime
    		    	 
    		          //输出HTML元素
    		          tableBodyHtml += '<tr><td>' + name + '</td>';
    		          tableBodyHtml += '<td>' + code + '</td>';
    		          tableBodyHtml += '<td>' + content + '</td>';
    		          tableBodyHtml += '<td>' + createTime + '</td>';
    		          tableBodyHtml += '<td><a href="../basedata_mgt/TemplatePara_insert_edit.html?id='+T_id+'">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a  id="'+T_id+'"   onclick="cardDel(\''+T_id+'\')">删除</a></td>';
    		          tableBodyHtml += '</tr>';
    		        });
    		     $("#tcdPagehide").show(); 
    		     _table.find("tbody").html(tableBodyHtml);
    		       replaceFun(_table);
    	  }else{
    		  $("#tcdPagehide").hide(); 
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
					tableFun("/basedata_mgt/TemplatePara/templateParaForList", srhData3);	
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
					url : "/basedata_mgt/TemplatePara/templateParaDelete",
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

  
  
  
  
  
  
  
  
  
  
  
  
  
  

//新增按钮跳转页面
var _addmess = $("#addTemplate");
_addmess.click(function(){
	location = "../basedata_mgt/TemplatePara_insert.html";	
});