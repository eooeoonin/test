/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

var _pages;
$(function (){
	laydate({elem: "#registerStartTime", format: "YYYY/MM/DD"});
	laydate({elem: "#registerEndTime", format: "YYYY/MM/DD"});
//	var srhData = {
//			"pageNo":"1",
//			"pageSize":"16"
//	};
	tableFun6("/basedata_mgt/selectuserid/delete");
//	myPage2();
});

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	$("#selected").validationEngine('attach',{
		inlineValidation: false,
		maxErrorsPerField:1,
		autoHidePrompt: true,
		autoHideDelay: 2000
	});
	if (!$("#selected").validationEngine('validate')) {
		$("#selected").validationEngine('detach');
		 return false;
	} 
	

	  
		  var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "16",
				 
					"channel" :$("#channel").val(),
					"projectCode" :$("#projectCode").val(),
					"userType" :$("#userType").val(),
					"realName" :$("#realName").val(),
					"registerMobile" :$("#registerMobile").val(),
					"id" :$("#id").val(),
					"registerStartTime" :$("#registerStartTime").val(),
					"registerEndTime" :$("#registerEndTime").val(),
					"isBankStates" :$("#isBankStates").val()
			};
			tableFun("/basedata_mgt/selectuserid/select",srhData2);
			myPage2();	
});


var _Cbtn = $("#modal_save2");
_Cbtn.click(function () {
	window.location.href='../basedata_mgt/selectuserid_sms.html';
//	window.location.href = "../basedata_mgt/event_list.html";
});

  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/
	var sizedata;
	var a = "";
  function tableFun(tdUrl, tbData, tbPage) {
	  
	  
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  sizedata = data.total;
    	  var _table = $('#table'),
          tableBodyHtml = ''; 
    	if(data.total!==0){
       
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
            	tableBodyHtml += '<td width="6%" ><input class="items1" type="checkbox" name="items" ></td>'; 
                     	 
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

  
  
  $("#checkall").change(function () {
	  	a = "";
	  	var checkboxs=$("input[name='items']") ;
    	 for (var i=0;i<checkboxs.length;i++) {
    	  var e=checkboxs[i];
    	  e.checked=!e.checked;
    	 }
    	 if($(this).is(':checked')){
	    	 $("#table").find("tbody tr").each(function(k,v){
	    		 $(this).find(".UserId").each(function(){
	    			 a += $(this).html() +",";
	    		 });
	    		 
	    	 });
	    	 var srhData3 = {
						"id" :a
				}
				tableFun3("/basedata_mgt/selectuserid/insertall",srhData3);
    	 } else {
    		 $("#table").find("tbody tr").each(function(k,v){
	    		 $(this).find(".UserId").each(function(){
	    			 a += $(this).html() +",";
	    		 });
	    		 
	    	 });
	    	 var srhData3 = {
						"id" :a
				}
				tableFun5("/basedata_mgt/selectuserid/deleteall",srhData3);
	  	}
    	
    	
    });

  
  
  
  
  
 $("#Cbtn").click(function () {
	 $("#myModal2").show();
	 
 });
 
 
 $("#exit1").click(function () {
	 $("#myModal2").hide();
	 
 });
  

function tableFun2(a,b){
	
	 $.ajax({
     type: "POST",
     url: a,
     data: b,
     dataType: "json",
     success: function (data) {
    	  
     }
 });
  
} 


function tableFun3(a,b){
	
	 $.ajax({
     type: "POST",
     url: a,
     data: b,
     dataType: "json",
     success: function (data) {
    	  
     }
 });
  
} 
  

function tableFun4(a,b){
	
	 $.ajax({
    type: "POST",
    url: a,
    data: b,
    dataType: "json",
    success: function (data) {
   	  
    }
});
 
} 


function tableFun5(a,b){
	
	 $.ajax({
   type: "POST",
   url: a,
   data: b,
   dataType: "json",
   success: function (data) {
  	  
   }
});

} 


function tableFun6(a){
	
	 $.ajax({
  type: "POST",
  url: a,
  data: {},
  dataType: "json",
  success: function (data) {
 	  
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
							"pageSize" : "16",
							"channel" :$("#channel").val(),
							"projectCode" :$("#projectCode").val(),
							"userType" :$("#userType").val(),
							"realName" :$("#realName").val(),
							"registerMobile" :$("#registerMobile").val(),
							"id" :$("#id").val(),
							"registerStartTime" :$("#registerStartTime").val(),
							"registerEndTime" :$("#registerEndTime").val(),
							"isBankStates" :$("#isBankStates").val()
					};
					tableFun("/basedata_mgt/selectuserid/select", srhData4);	
			}
		});
}else{
	$("#tcdPagehide").hide();
}
	  
}
 
	




