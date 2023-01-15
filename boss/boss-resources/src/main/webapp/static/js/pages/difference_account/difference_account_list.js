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


var _pages;var _id;
$(function(){
	var _table = $('#table');
		  _table.bootstrapTable();
		  
		  _id = Request.id;
		  
	var srhData = {
						   "id":_id
						  };
		  tableFun("../difference_account/difference/showAccountRecords", srhData);	
})


function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  if(data.data.length!=0){
        var _table = $('#table'),
          tableBodyHtml = '';
        
        $.each(data.data, function (k, v) {
	          //获取数据
	        	 var u_id = v.id;
	        	  u_userId = v.userId,
	              u_outOrderNo = v.outOrderNo,
	              u_transBalanceBefore = v.transBalanceBefore.amount,
	              u_amount = v.amount.amount,
	              u_transBalanceAfter = v.transBalanceAfter.amount,
	              u_checkCount = v.checkCount,
	              u_actionType = v.actionType,
	              u_tradeTime = v.tradeTime,
	              u_status = v.status
	              
	              if(u_checkCount == '0'){
	            	  u_checkCount = '初始状态'; 
	              }if(u_checkCount == '1'){
	            	  u_checkCount = '匹配'; 
	              }if(u_checkCount == '2'){
	            	  u_checkCount = '错账'; 
	              }if(u_checkCount == '3'){
	            	  u_checkCount = '单边账'; 
	              }if(u_checkCount == '4'){
	            	  u_checkCount = '错账处理过，已标平'; 
	              }if(u_checkCount == '5'){
	            	  u_checkCount = '单边处理过，已标平'; 
	              }
	              
	              
	              if(u_actionType == 'IN'){
	            	  u_actionType = '注入'; 
	              }if(u_actionType == 'OUT'){
	            	  u_actionType = '流出'; 
	              }
	              
	        	  
	          //输出HTML元素
	          tableBodyHtml += '<tr>';
	          tableBodyHtml += '<td>' + u_userId + '</td>';
	          tableBodyHtml += '<td>' + u_outOrderNo + '</td>';
	          tableBodyHtml += '<td>' + u_transBalanceBefore + '</td>';
	          tableBodyHtml += '<td>' + u_amount + '</td>';
	          tableBodyHtml += '<td>' + u_transBalanceAfter + '</td>';
	          tableBodyHtml += '<td>' + u_checkCount + '</td>';
	          tableBodyHtml += '<td>' + u_actionType + '</td>';
	          tableBodyHtml += '<td>' + u_tradeTime + '</td>';
	          tableBodyHtml += '</tr>';
	        });
        _table.find("tbody").html(tableBodyHtml);
        
        replaceFun(_table);
      }else{
	    	$("#tcdPagehide").hide();
	    }
      },error:function(data){
    	 alert(data)
      },
    async : false
    });
  }
	