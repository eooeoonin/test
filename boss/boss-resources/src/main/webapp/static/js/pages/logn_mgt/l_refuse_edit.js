/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */


$(function () {
  queryBorrower();
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
	var start = {
    elem: "#date1", format: "YYYY-MM-DD", istime: true, istoday: false, choose: function (datas) {
      end.min = datas;
      end.start = datas
      var data2 = $("#date2").val();
      if(data2){
    	  //判断显示天还是月
    	  var day1 = data2.split("-")[2];
    	  var day2 = datas.split("-")[2];
    	  if(data2 == datas){
    		  var months = getMonths(datas,data2) + 1;
    		  $("#days").text(months+"天");
    		  return;
    	  }if(day1==day2){//正好相差整月
    		  var months = getMonths(data2,datas);
    		  $("#days").text(months+"月");
    		  return;
    	  }if(parserDate(data2).getTime()-parserDate(datas).getTime() > 0){
    		  var date3=parserDate(data2).getTime()-parserDate(datas).getTime()//时间差的毫秒数
        	  //计算出相差天数
        	  var days=Math.floor(date3/(24*3600*1000));
        	  $("#days").text(days+1+"天");
    	  }if(parserDate(data2).getTime()-parserDate(datas).getTime() < 0){
    		  var date3=parserDate(data2).getTime()-parserDate(datas).getTime()//时间差的毫秒数
        	  //计算出相差天数
        	  var days=Math.floor(date3/(24*3600*1000));
        	  $("#days").text(days+"天");
    	  }
    	  //计算出相差天数
//    	  var date3=parserDate(data2).getTime()-parserDate(datas).getTime()//时间差的毫秒数
//    	  var days=Math.floor(date3/(24*3600*1000))+1;
//    	  $("#days").text(days+"天");
      }
    }
  };
  var end = {
	elem: "#date2", format: "YYYY-MM-DD", istime: true, istoday: false, choose: function (datas) {
      start.max = datas
      var data1 = $("#date1").val();
      if(data1){
    	  var day1 = data1.split("-")[2];
    	  var day2 = datas.split("-")[2];
    	  if(data1 == datas){
    		  var months = getMonths(datas,data1) + 1;
    		  $("#days").text(months+"天");
    		  return;
    	  }if(day1==day2){//正好相差整月
    		  var months = getMonths(datas,data1);
    		  $("#days").text(months+"月");
    		  return;
    	  }if(parserDate(datas).getTime()-parserDate(data1).getTime() > 0){
    		  var date3=parserDate(datas).getTime()-parserDate(data1).getTime()//时间差的毫秒数
        	  //计算出相差天数
        	  var days=Math.floor(date3/(24*3600*1000));
        	  $("#days").text(days+1+"天");
    	  }if(parserDate(datas).getTime()-parserDate(data1).getTime() < 0){
    		  var date3=parserDate(datas).getTime()-parserDate(data1).getTime()//时间差的毫秒数
        	  //计算出相差天数
        	  var days=Math.floor(date3/(24*3600*1000));
        	  $("#days").text(days+"天");
    	  }
    	  
    	  
//    	  var date3=parserDate(datas).getTime()-parserDate(data1).getTime()//时间差的毫秒数
//    	  //计算出相差天数
//    	  var days=Math.floor(date3/(24*3600*1000));
//    	  $("#days").text(days+1+"天");
      }
    }
  };
  laydate(start);
  laydate(end);
  
  operatorName = $.cookie("username");
  $('#approver').val(operatorName);
  $("#p").text(operatorName);
	  
  //计算两个日期相差月数
  function getMonths(date2,date1){
	    //用-分成数组
	    date1 = date1.split("-");
	    date2 = date2.split("-");
	    //获取年,月数
	    var year1 = parseInt(date1[0]) , 
	        month1 = parseInt(date1[1]) , 
	        year2 = parseInt(date2[0]) , 
	        month2 = parseInt(date2[1]) , 
	        //通过年,月差计算月份差
	        months = (year2 - year1) * 12 + (month2-month1);
	    return months;    
  }
  //日期类型转换
  var parserDate = function (date) {  
      var t = Date.parse(date);  
      if (!isNaN(t)) {  
          return new Date(Date.parse(date.replace(/-/g, "/")));  
      } else {  
          return new Date();  
      }  
  };

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

              var d_repayType;
              switch(responseMsg.repayType) {
              case "ONETIMEDEBT":
            	  d_repayType = "一次性还本付息";
            	  break;
              case "MATCHING":
            	  d_repayType = "等额本息";
            	  break;
              case "MONTHLYPAYMENTDUE":
            	  d_repayType = "按月付息到期还本";
            	  break;
              default:
            	  d_repayType = responseMsg.repayType;
              break;
              }
	    	  
	    	  $("#borrowIdInput").val(responseMsg.id);
	    	  
	    	  $("#borrowId").text(responseMsg.id);
	    	  $("#borrowUserName").text(responseMsg.borrowUserName);
	    	  $("#borrowUserCode").val(responseMsg.borrowUserCode);
	    	  $("#borrowTitle").val(responseMsg.borrowTitle);
	    	  if(responseMsg.borrowType == "普通借款"){
	    		  $("#borrowType").find("option[value='COMMON']").attr("selected",true);
	    	  }else{
	    		  $("#borrowType").find("option[value='SUPPLYCHAIN']").attr("selected",true);
	    	  }
	    	  $("#borrowRate").val(responseMsg.borrowRate);
	    	  $("#days").text(responseMsg.borrowDates);
	    	  $("#borrowAmount").val(responseMsg.borrowAmount.amount);
	    	 /* $("#repayType").find("option[value='"+responseMsg.repayType+"']").attr("selected",true);*/
	    	  $("#repayType").text(d_repayType);
	    	  $("#date1").val(responseMsg.lastReleaseTime.split(" ")[0]);
	    	  $("#date2").val(responseMsg.lastRepayTime.split(" ")[0]);
	    	  $("#borrowFee").val(responseMsg.borrowFee.amount);
	    	  $("#status").val(responseMsg.status);
	    	  $("#protocolId").val(responseMsg.protocolId);
	    	  
	    	  //借款用途，安全保障
	    	  //审批意见
	    	  var approveHtml = "";
	    	  $.each(responseMsg.borrowExtendList, function (k, v) {
	    		  if(v.extendType=="PURPOSE"){
	    			  $("#purpose").text(v.mark);
	    			  $("#purposeId").val(v.id);
	    		  }
	    		  if(v.extendType=="RISK"){
	    			  $("#risk").text(v.mark);
	    			  $("#riskId").val(v.id);
	    		  }
	    		  //审批意见
	    		  if(v.extendType== 'FIRSTTRIAL' || v.extendType== 'REVIEW' || v.extendType== 'REFUSE' || v.extendType== 'INIT'||v.extendType== 'EDIT'){
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
	    				  approveHtml += "<td class=\"input-group-addon\">拒绝编辑内容</td>";  
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
	    	  $("#table tr:eq("+length+")").after(approveHtml);
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
  
  //格式化日期类型
  function   formatDate2(now)   {     
      var year=now.getFullYear();     
      var month=now.getMonth()+1;     
      var date=now.getDate();     
      var hour=now.getHours();     
      var minute=now.getMinutes();     
      var second=now.getSeconds();
      return   year+"-"+month+"-"+date;
  }
});

var _form = $('#borrowFrom'),  //form表单ID
_submitBtn = $("#addLoan");  //提交表单按钮ID
//表单元素验证
_form.validationEngine('attach', {
  maxErrorsPerField: 1,
  autoHidePrompt: true,
  autoHideDelay: 2000
});

function reApprove(flag){
	
	if (!_form.validationEngine('validate')) {
	     return false;
	}
	operatorName = $.cookie("username");
	$('#approver').val(operatorName);
	$("#approveResult").val(flag);
	var formData = $("#borrowFrom").serialize();
	$.ajax({
	      type: "POST",
	      url:"/borrow/l_refuse_list/updateBorrow",
	      data:$("#borrowFrom").serialize(),
	      async: false,
	      success: function(responseMsg) {
	    	  if("success"==responseMsg){
	    		  alert("操作成功");
	    		  location="../borrow/l_refuse_list.html";
	    	  }else{
	    		  alert("操作失败");
	    		  location="../borrow/l_refuse_list.html";
	    	  }
	    		  
	      },
	      error:function(){
	    	  alert("出错了");
	      }
	});
}