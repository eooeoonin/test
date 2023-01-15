var _pages;
$(function (){
	var srhData = {
			"pageNo":"1",
			"pageSize":"10"
		};
	tableFun("/unwrap/unwrapSelectAll", srhData);
	myPage();
	var _srhBtn = $("#srhBtn");
	_srhBtn.click(function () {
		
		  var v_namecode = $("#realName").val(),
			v_unbindResult = $("#unbindResult1").val(),
			v_subject = $("#registerMobile").val();	
		  if(v_namecode==""&&v_unbindResult==""&&v_subject==""){
			  var srhData2 = {
						"pageNo" : "1",
						"pageSize" : "10"
				};
//			  tableFun("/unwrap/unwrapSelectAll",srhData2);
//				myPage2();
			}else{
				var srhData2 = {
						"pageNo" : "1",
						"pageSize" : "10",
						"realName" : v_namecode,
						"unbindResult" :v_unbindResult,
						"registerMobile": v_subject
				};
				
			}
		  tableFun("/unwrap/unwrapSelectAll",srhData2);
			myPage2();
				
	});

	  
	
  
});

var _qingBtn = $("#qingBtn");
_qingBtn.click(function(){
	  location = "../unwrap/unwrap_list.html";		  
});
  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：
   *时间：2016-08-01
   ***/
  function tableFun(tdUrl, tbData,tbPage) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
        var _table = $('#table'), tableBodyHtml = '';
        _pages = data.pages;
        $.each(
        		data.list, 
        	function (k, v) {
        	//0=申请中、1=解绑成功、2=解绑处理中、-1=解绑失败、 -2=解绑拒绝
            var d_id = v.id,
            d_userId = v.userId,
            d_certPhoto1 = v.certPhoto1,
            d_certPhoto2 = v.certPhoto2,
            d_unbindResult = v.unbindResult,
            d_remark = v.remark,
            d_status = v.status,
          	d_editedBy = v.editedBy,
            d_createTime = v.createTime,
            d_registerMobile = v.registerMobile,
            d_realName = v.realName,
            d_modifyTime= v.modifyTime;
            
            var d = new Date(d_createTime);
            var dateStr = d.getFullYear() + '-' + (d.getMonth()+1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes();      	 
        
            
            
            var d_zuang;
            if(d_unbindResult=='INIT'){
            	d_zuang='申请中';
            }else if(d_unbindResult=='SUCCESS'){
            	d_zuang='已成功';
            }else if(d_unbindResult=='PROCESSING'){
            	d_zuang='解绑处理中';
            }else if(d_unbindResult=='FAILURE'){
            	d_zuang='解绑失败';
            }else if(d_unbindResult=='REFUSE'){
            	d_zuang='已拒绝';
            }
            	tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td>' + d_createTime + '</td>';
            	tableBodyHtml += '<td>' + d_registerMobile + '</td>';
            	tableBodyHtml += '<td>' + d_realName + '</td>';
//            	tableBodyHtml += '<td>' + d_userId + '</td>';
            	tableBodyHtml += '<td>' + d_zuang + '</td>';	
                if(d_zuang=='申请中' || d_zuang=='解绑失败'){
              		tableBodyHtml += '<td><a href="unwrap_audit.html?id=' + d_id +'" class="msgEdit">查看</a></td>'; 
                }else if(d_zuang=='已成功'||d_zuang=='已拒绝'){
              		tableBodyHtml += '<td><a href="unwrap_processed.html?id=' + d_id +'" class="msgEdit">查看</a></td>'; 
                }else{
                	tableBodyHtml += '<td></td>'; 
                }    	 
          		tableBodyHtml += '</tr>';           
                var msgEdit = $('.msgEdit');
        });
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
      },
    async : false
    });
  }
  
function myPage(){
	  var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(p) {				  	
				  	var srhData3 = {
							"pageNo" :p,
							"pageSize" : "10"
					};
					tableFun("/unwrap/unwrapSelectAll", srhData3);	
			}
		});
  }


function myPage2(){
	  var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(q) {
				 var v_namecode = $("#realName").val(),
					v_unbindResult = $("#unbindResult1").val(),
					v_subject = $("#registerMobile").val();
				 	if(v_namecode==""&&v_unbindResult==""&&v_subject==""){
				 		 var srhData4 = {
									"pageNo" : q,
									"pageSize" : "10"
							};
				 	}else{
				 		 var srhData4 = {
									"pageNo" : q,
									"pageSize" : "10",
									"realName" : v_namecode,
									"unbindResult" :v_unbindResult,
									"registerMobile": v_subject
							};
				 		
				 	}
					 				  	
				  
					tableFun("/unwrap/unwrapSelectAll", srhData4);	
			}
		});
}


	



