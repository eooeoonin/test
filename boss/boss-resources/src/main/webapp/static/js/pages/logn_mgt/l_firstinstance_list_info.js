/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

$(function () {
	
  queryBorrower();
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"})
  operatorName = $.cookie("username");
  $('#approver').val(operatorName);
  $("#p").text(operatorName);
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
  
  //查询借款明细
  function queryBorrower(){
	  var Request = {};
	  Request = GetRequest();
	  $.ajax({
	      type: "POST",
	      url:"/borrow/getBorrow",
	      data:{
	    	  borrowId:Request.borrowId
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  
	    	  var repayTypeCn = "";
	    	  switch(responseMsg.repayType) {
	    	  case "MONTHLYPAYMENTDUE":
	    		  repayTypeCn = "按月付息到期还本产品";
	    		  break;
	    	  case "ONETIMEDEBT": 
	    		  repayTypeCn = "一次性还本付息产品";
	    		  break;
	    	  case "MATCHING":
	    		  repayTypeCn = "等额本息产品";
	    		  break;
	    	  }
	    	  
	    	  $("#borrowId").val(responseMsg.id);
	    	  $("#borrowIdText").text(responseMsg.id);
	    	  $("#borrowUserName").text(responseMsg.borrowUserName);
	    	  $("#borrowTitle").text(responseMsg.borrowTitle);
	    	  $("#borrowType").text(responseMsg.borrowType);
	    	  $("#borrowRate").text(responseMsg.borrowRate+"%");
	    	  $("#borrowDates").text(responseMsg.borrowDates);
	    	  $("#borrowAmount").text(responseMsg.borrowAmount.amount+"元");
	    	  $("#repayType").text(repayTypeCn);
	    	  $("#lastReleaseTime").text(responseMsg.lastReleaseTime);
	    	  $("#lastRepayTime").text(responseMsg.lastRepayTime);
	    	  $("#borrowFee").text(responseMsg.borrowFee.amount+"元");
	    	  $("#protocolId").text(responseMsg.protocolId);
	    	  //借款用途，安全保障
	    	  //审批意见
	    	  var approveHtml = "";
	    	  $.each(responseMsg.borrowExtendList, function (k, v) {
	    		  if(v.extendType=="PURPOSE"){
	    			  $("#PURPOSE").text(v.mark);
	    		  }
	    		  if(v.extendType=="RISK"){
	    			  $("#RISK").text(v.mark);
	    		  }
	    		  //审批意见
	    		  if(v.extendType== 'FIRSTTRIAL' || v.extendType== 'REVIEW' || v.extendType== 'REFUSE' || v.extendType== 'INIT' ||v.extendType== 'EDIT'){
	    			  approveHtml += "<tr>";
	    			  if(v.extendType== 'INIT'){
	    				  approveHtml += "<td class=\"input-group-addon\">添加借款意见</td>";  
	    			  }
	    			  if(v.extendType== 'FIRSTTRIAL'){
	    				  approveHtml += "<td class=\"input-group-addon\">初审意见</td>";  
	    			  }
	    			  if(v.extendType== 'REVIEW'){
	    				  approveHtml += "<td class=\"input-group-addon\">复审意见</td>";  
	    			  }
	    			  if(v.extendType== 'REFUSE'){
	    				  approveHtml += "<td class=\"input-group-addon\">拒绝原因</td>";  
	    			  }
	    			  if(v.extendType== 'EDIT'){
	    				  approveHtml += "<td class=\"input-group-addon\">修改内容</td>";  
	    			  }
	    			  approveHtml += "<td colspan=\"3\">"+v.mark+"</td>";
	    			  approveHtml += "</tr>";
	    			  approveHtml += "<tr>";
	    			  if(v.extendType== 'INIT'){
	    				  approveHtml += "<td class=\"input-group-addon\">操作人</td>";
	    				  approveHtml += "<td>"+v.editedBy+"</td>";
		    			  approveHtml += "<td class=\"input-group-addon\">操作时间</td>";
		    			  approveHtml += "<td>"+v.modifyTime+"</td>";
		    			  approveHtml += "</tr>";
	    			  }
	    			  else{
	    				  approveHtml += "<td class=\"input-group-addon\">审批人</td>";
		    			  approveHtml += "<td>"+v.editedBy+"</td>";
		    			  approveHtml += "<td class=\"input-group-addon\">审批时间</td>";
		    			  approveHtml += "<td>"+v.modifyTime+"</td>";
		    			  approveHtml += "</tr>";
	    			  }
	    				     			  
	    		  }
	    	  })
	    	  var length = $("#table").find("tr").length-3;
	    	  $("#table tr:eq("+length+")").after(approveHtml)
	    	  //文件
	    	  var html = "";
	    	  $.each(responseMsg.borrowFileList, function (k, v) {
	    		  html += "<div class=\"file-box\">";
	    		  html += "<div class=\"file\">";
	    		  html += "<span class=\"corner\"></span>";
	    		  html += "<div class=\"image\">";
	    		  html += "<a class=\"fancybox img-responsive\" href="+domainUrl+pic+v.fileUrl+">";
	    		  html += "<img alt=\"image\" src="+domainUrl+pic+v.fileUrl+"></img>";
	    		  html += "</a>";
	    		  html += "</div>";
	    		  html += "<div class=\"file-name\">";
	    		  html += "<small>图表展示、数据可视化是</small>";
	    		  html += "</div>";
	    		  html += "</div>";
	    		  html += "</div>";
	    	  })
	    	  $("#images").html(html);
	      }
	  });
  }
  
  //格式化日期类型
  function   formatDate(now)   {     
      var year=now.getFullYear();     
      var month=now.getMonth()+1;     
      var date=now.getDate();     
      var hour=now.getHours();     
      var minute=now.getMinutes();     
      var second=now.getSeconds();
      return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
      
  }  
});

var _form = $('#approveFrom'); //form表单ID
//表单元素验证
_form.validationEngine('attach', {
  maxErrorsPerField: 1,
  autoHidePrompt: true,
  autoHideDelay: 2000
});

//审批
function approve(approveResult,type){
	
	  if (!_form.validationEngine('validate')) {
	     return false;
	  }
	 
	  operatorName = $.cookie("username");
	  $('#editedBy').val(operatorName);
	  
	  $("#approveResult").val(approveResult);
	  $("#type").val(type);
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_firstinstance_list/approveBorrow",
	      data:$("#approveFrom").serialize(),
	      async: false,
	      success: function(responseMsg) {
	    	  if("success"==responseMsg){
	    		  alert("审核成功");
	    		  location = "../borrow/l_firstinstance_list.html";
	    	  }else{
	    		  alert("审核失败");
	    		  location = "../borrow/l_firstinstance_list.html";
	    	  }
	      }
	  });
}