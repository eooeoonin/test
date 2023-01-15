/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

var _pages;var _data;
$(function (){
	 var _table = $('#table');
	  _table.bootstrapTable();
	  var srhData4 = {
				"pageNo" :"1",
				"pageSize" : "20"
		};
	tableFun("../basedata_mgt/selectuserid/SMSlist",srhData4);
	myPage2();	
});


var _Cbtn = $("#upperLevel");
_Cbtn.click(function () {
	window.location.href='../basedata_mgt/selectuserid.html';

});


var _Cbtn2 = $("#sendMsg");
_Cbtn2.click(function () {
	var srhData4 = {
			"content":$("#content").val()
			
	};
	tableFun6("../basedata_mgt/selectuserid/sndMsgById",srhData4);

});

  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/
	var sizedata;
	var a = "";
  function tableFun(tdUrl,b) {
	  
	  
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: b,
      dataType: "json",
      success: function (data) {
    	  sizedata = data.total;
    	  _data=data.total;
    	 
    	  var _table = $('#table'),
    	 
          tableBodyHtml = ''; 
    	if(data.total!==0){
    		document.getElementById("uid").value = data.total+"人";
    		
        _pages = data.pages;
        $.each(
        		data.list,
        	function (k, v) {
            var d_channel = v.channel,
            d_userType = v.userType,
            d_realName = v.realName,
          	d_registerMobile = v.registerMobile,
            d_createTime = v.createTime,
            d_authState = v.authState,
            d_id = v.id;
          if(d_userType=="PERSON"){
        	  d_userType="个人会员";
          }else if(d_userType=="ENTERPRISE"){
        	  d_userType="企业会员";
          }
          if(d_authState=="BANKCARD"){
        	  d_authState="是";
          }else{
        	  d_authState="否";
          }
            
            	tableBodyHtml += '<tr height="50">';
            	tableBodyHtml += '<td width=12%>' + d_channel + '</td>';
            	tableBodyHtml += '<td width=8%>' + d_userType + '</td>';
            	tableBodyHtml += '<td width="8%">' + d_realName + '</td>';
            	tableBodyHtml += '<td width="12%" >' + d_registerMobile + '</td>';
            	tableBodyHtml += '<td width="20%" >' + d_createTime + '</td>';
            	tableBodyHtml += '<td width="8%" >' + d_authState + '</td>';
            	tableBodyHtml += '<td width="26%" class="UserId" >' + d_id + '</td>'; 
            	tableBodyHtml += '<td width="6%" ><a name='+d_id+'  href="javascript:" style="margin-left:15px;" onclick="bankInfoDel(this)">删除</a></td>'; 
                tableBodyHtml += '</tr>';           
                
        });
        $("#checkall").removeAttr("checked");
        $("#tcdPagehide").show();
        _table.find("tbody").html(tableBodyHtml);
        $("#table").find(".items1").each(function(){
      	  $(this).change(function () {
          	if( $(this).is(':checked')){
          		a = "";
          		$(this).parent().parent().find("td").each(function(k,v){
          			if(k == 6){
          				a+= $(this).html();
          				alert(a);
          				var srhData3 = {
          						"id" :a
          				}
          				tableFun2("/basedata_mgt/selectuserid/insertone",srhData3);
          			}
          			
          		});
          	}else{
//          		alert(2);
          		
          		a = "";
          		$(this).parent().parent().find("td").each(function(k,v){
          			if(k == 6){
          				a+= $(this).html();
          				alert(a);
          				var srhData3 = {
          						"id" :a
          				}
          				tableFun4("/basedata_mgt/selectuserid/deleteone",srhData3);
          			}
          			
          		});
          	}
          });
        });
      }else{
    	  tableBodyHtml +='<tr class="no-records-found">';
 		  tableBodyHtml +='<td style="text-align:center; vertical-align:middle;" colspan="8">没有找到匹配的记录</td>';
 		  tableBodyHtml += '</tr>';
 		 _table.find("tbody").html(tableBodyHtml);
      }
      },
    async : false
    });
    
    
  
  }

  function tableFun6(a,b){
		
		 $.ajax({
	    type: "POST",
	    url: a,
	    data: b,
	    dataType: "json",
	    success: function (data) {
	    	
	    	if($("#content").val()!=null&& _data!=0 &&$("#content").val()!=""){
	   	  alert("发送成功");
	   	window.location.href='../basedata_mgt/selectuserid.html';
	    	 }
	   	else if(_data==0){
	   		alert("请选择用户");
	   		
	   	}else if($("#content").val()==""){
	   		alert("请编辑发送内容");
	   	}
	    }
	});
	 
	} 

function myPage2(){
	  if(sizedata!==0){
		var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(q) {
						  	
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "20"
					};
					tableFun("../basedata_mgt/selectuserid/SMSlist", srhData4);	
			}
		});
}else{
	$("#tcdPagehide").hide();
}
	  
}
function bankInfoDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/selectuserid/deleteone",
					data:{
						"id":id.name
					},
					dataType: "json",
					async:false,
					success : function(data) {
						
						
							
								bootbox.alert("删除成功", function(result){
									window.location.reload();
								});
							
						
					},
					async : false
				});
			}
		});
}
	




