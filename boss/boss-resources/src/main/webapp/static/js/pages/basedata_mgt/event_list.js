var _pages,_pages2,_emID,_pages3;


/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}  

var Request = {};
Request = GetRequest();


$(function () {
	
	var _table = $('#table1');
	  _table.bootstrapTable();
	  
	  var _table2 = $('#table2');
	  _table2.bootstrapTable();  
	
	  var _table3 = $('#table3');
	  _table3.bootstrapTable(); 
	var srhData1 = {"pageNo":"1","pageSize":"10"};
	tableFun2("../basedata_mgt/event_list/selectEventMapList", srhData1);
	myPage1();
	

	   var eventMapId22 =Request.eventMapId;
	   	if(eventMapId22){
		   $("#aid").val(eventMapId22);
		   var srhData2 = {"eventMapId":eventMapId22,"pageNo":"1","pageSize":"10"};
		   selectEventValueMap(srhData2);
		   myPage2();
		   
		   selectEventValueSupplyMap(srhData2);
		   myPage3();
	   	}
	
});

	
function trclick(mapid){
	   $(this).css("background","#ddd");
	   var tr_tit = mapid.title;
	   $("#aid").val(tr_tit);
	   var srhData2 = {"eventMapId":tr_tit,"pageNo":"1","pageSize":"10"};
	   selectEventValueMap(srhData2);
	   myPage2();
	   
	   selectEventValueSupplyMap(srhData2);
	   myPage3();
	   }


	/**
	 * 父表
	 */
   function tableFun2(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	 
        var _table = $('#table1'),
          tableBodyHtml = '';
        
        _pages = data.pages;
        
        $.each(data.list, function (k, v) {
          //获取数据
        var e_id = v.id,
              e_receiveTopic = v.receiveTopic,
              e_receiveTag = v.receiveTag,
              e_sendTopic = v.sendTopic,
              e_sendTag = v.sendTag;
        

        
          //输出HTML元素
          tableBodyHtml += '<tr onclick="trclick(this)" title="'+e_id+'">';
          tableBodyHtml += '<td>' + e_receiveTopic + '</a></td>>';
          tableBodyHtml += '<td>' + e_receiveTag + '</td>';
          tableBodyHtml += '<td>' + e_sendTopic + '</td>';
          tableBodyHtml += '<td>' + e_sendTag + '</td>';
          tableBodyHtml += '<td><a href="../basedata_mgt/event_list_update_eventmap.html?id='+e_id+'" style="margin-left:15px;">编辑</a><a href="../basedata_mgt/event_list_insert_eventvaluemap.html?id='+e_id+'" style="margin-left:15px;">录入</a><a  name='+e_id+'  href="javascript:" style="margin-left:15px;" onclick="deleteEventMap(this)">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
      },
    async : false
    });
  }
   
   
   
   
   /**
    * 单笔查询点击事件
    */
   function aa(eid,pageNo,pageSize){
	  var srhData2 = {"eventMapId":eid,"pageNo":pageNo,"pageSize":pageSize};
	   selectEventValueMap(srhData2);
	   myPage2();
	   
	   selectEventValueSupplyMap(srhData2);
	   myPage3();
   }
   /**
    * 字表1展示
    * @param tbData2
    */
   
   function selectEventValueMap(tbData2){
	   $.ajax({
			type : "POST",
			url : "../basedata_mgt/event_list/selectValueMapList",
			data : tbData2,
			dataType: "json",
			success : function(data) {
				var _table2 = $('#table2'),
		          tableBodyHtml = '';
		        
				_pages2 = data.pages;
		        
		        $.each(data.list, function (k, v) {
		          //获取数据
		        var ev_id = v.id,
		        	  ev_eventMapId = v.eventMapId;
		              ev_inputName = v.inputName,
		              ev_outputName = v.outputName;
		              
		              
		          //输出HTML元素
		          tableBodyHtml += '<tr>';
		          tableBodyHtml += '<td>' + ev_inputName + '</a></td>>';
		          tableBodyHtml += '<td>' + ev_outputName + '</td>';
		          tableBodyHtml += '<td><a href="../basedata_mgt/event_list_update_eventvaluemap.html?id='+ev_id+'" style="margin-left:15px;">编辑</a><a  name='+ev_id+' title="'+ev_eventMapId+'" href="javascript:" style="margin-left:15px;" onclick="deleteEventValueMap(this)">删除</a></td>';
		          tableBodyHtml += '</tr>';
		        });
		        _table2.find("tbody").html(tableBodyHtml);
		      },
		      async : false
		});
   }
   
   /**
    * 字表2展示
    * @param tbData2
    */

   function selectEventValueSupplyMap(tbData2){
	   $.ajax({
			type : "POST",
			url : "../basedata_mgt/event_list/selectValueMapSupplyList",
			data : tbData2,
			dataType: "json",
			success : function(data) {
				var _table3 = $('#table3'),
		          tableBodyHtml = '';
		        
				_pages3 = data.pages;
		        
		        $.each(data.list, function (k, v) {
		          //获取数据
		        var ev_id = v.id,
		        	  ev_eventMapId = v.eventMapId;
		              ev_name = v.name,
		              ev_value = v.value;
		              
		              
		          //输出HTML元素
		          tableBodyHtml += '<tr>';
		          tableBodyHtml += '<td>' + ev_name + '</a></td>>';
		          tableBodyHtml += '<td>' + ev_value + '</td>';
		          tableBodyHtml += '<td><a href="../basedata_mgt/event_list_update_eventvalueSupplymap.html?id='+ev_id+'" style="margin-left:15px;">编辑</a><a  name='+ev_id+' title="'+ev_eventMapId+'" href="javascript:" style="margin-left:15px;" onclick="deleteEventValueSupplyMap(this)">删除</a></td>';
		          tableBodyHtml += '</tr>';
		        });
		        _table3.find("tbody").html(tableBodyHtml);
		      },
		      async : false
		});
   }
   // 分页
   function myPage1(){
   var $tcdPage = $("#tcdPageCode1");
  	$tcdPage.createPage({
  		pageCount : _pages,
  		current : 1,
  		backFn : function(q) {
  			var srhData1 = {
  									"pageNo" : q,
  									"pageSize" : "10"
  								};
  			tableFun2("../basedata_mgt/event_list/selectEventMapList", srhData1);
  		}
  	})
  };
   
   // 字表1分页
function myPage2(){
   var $tcdPage2 = $("#tcdPageCode2");
 	$tcdPage2.createPage({
 		pageCount : _pages2,	
 		current : 1,
 		backFn : function(p) {
 			var _id = $("#aid").val();
 			var srhData2 = {"eventMapId":_id,"pageNo":p,"pageSize":"10"};
 			selectEventValueMap(srhData2);
 		}
 	})
 };
 
 
 // 字表2分页
 function myPage3(){
    var $tcdPage3 = $("#tcdPageCode3");
  	$tcdPage3.createPage({
  		pageCount : _pages3,	
  		current : 1,
  		backFn : function(p) {
  			var _id = $("#aid").val();
  			var srhData3 = {"eventMapId":_id,"pageNo":p,"pageSize":"10"};
  			selectEventValueSupplyMap(srhData3);
  		}
  	})
  };
 

// function deleteEventMap(eid){
//		if(confirm("确认要删除吗?")){
//			$.ajax({
//				type : "POST",
//				url : "../basedata_mgt/event_list/deleteEventMap",
//				data : {"eid" : eid.name},
//				dataType: "json",
//				success : function(data) {
//					alert("删除成功")
//					location = location;
//				},error : function(data){
//					alert("删除失败")
//					location = location;
//				}
//			});
//		}
//	}
 
	
	function deleteEventMap(eid){
		  bootbox.confirm("确定要删除吗?", function(result){
				if(result){
					$.ajax({
						type : "POST",
						url : "../basedata_mgt/event_list/deleteEventMap",
						data : {"eid" : eid.name},
						dataType: "json",
						async:false,
						success : function(data) {
							
							if (data != null && data != "") {
								if (data == 1) {
									bootbox.alert("删除成功", function(result){
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
 
 
 


//		function deleteEventValueMap(evid){
//			
//			if(confirm("确认要删除吗?")){
//				$.ajax({
//					type : "POST",
//					url : "../basedata_mgt/event_list/deleteEventValueMap",
//					data : {"id" : evid.name},
//					dataType: "json",
//					success : function(data) {
//						alert("删除成功")
//						var eventMapId = evid.title;
//		              	  location = "../basedata_mgt/event_list.html?eventMapId="+eventMapId+"";
//					},error : function(data){
//						alert("删除失败")
//						location = location;
//					}
//				});
//			}
//		}
		
		
		function deleteEventValueMap(evid){
			  bootbox.confirm("确定要删除吗?", function(result){
					if(result){
						$.ajax({
							type : "POST",
							url : "../basedata_mgt/event_list/deleteEventValueMap",
							data : {"id" : evid.name},
							dataType: "json",
							async:false,
							success : function(data) {
								
								if (data != null && data != "") {
									if (data == 1) {
										bootbox.alert("删除成功", function(result){
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
		
		
		
		
		

//		function deleteEventValueSupplyMap(evid){
//			
//			if(confirm("确认要删除吗?")){
//				$.ajax({
//					type : "POST",
//					url : "../basedata_mgt/event_list/deleteEventValueSupplyMap",
//					data : {"id" : evid.name},
//					dataType: "json",
//					success : function(data) {
//						alert("删除成功")
//						var eventMapId = evid.title;
//		              	  location = "../basedata_mgt/event_list.html?eventMapId="+eventMapId+"";
//					},error : function(data){
//						alert("删除失败")
//						location = location;
//					}
//				});
//			}
//		}
		
		
		
		
		function deleteEventValueSupplyMap(evid){
			  bootbox.confirm("确定要删除吗?", function(result){
					if(result){
						$.ajax({
							type : "POST",
							url : "../basedata_mgt/event_list/deleteEventValueSupplyMap",
							data : {"id" : evid.name},
							dataType: "json",
							async:false,
							success : function(data) {
								
								if (data != null && data != "") {
									if (data == 1) {
										bootbox.alert("删除成功", function(result){
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
