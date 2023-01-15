/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var amount1 = 0;
var amount2 = 0;
var amount3 = 0;
var amount4 = 0;
$(function () {
	var Request = {};
	Request = GetRequest();
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"})
  var start = {};
  if(undefined != Request.repayTime){
	  start = {
			    elem: "#beforeDate", format: "YYYY-MM-DD", istime: true, istoday: true,max:Request.repayTime, //最大日期
			  };
  }else{
	  start = {
			    elem: "#beforeDate", format: "YYYY-MM-DD", istime: true, istoday: true
			  };  
  }
  laydate(start);
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
	
	
  //借款人明细连接
  $("#toBorrowerDetail").attr("href","repayListBorrowerDetail.html?borrowId="+Request.borrowId);
	
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
	    	  $("#lastReleaseTime").text(responseMsg.lastReleaseTime.split(" ")[0]);
	    	  $("#lastRepayTime").text(responseMsg.lastRepayTime.split(" ")[0]);
	    	  $("#borrowFee").text(responseMsg.borrowFee.amount+"元");
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
	    		  
	    		  $("#protocolId").text(v.protocolId);
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
	    			  }
	    			  else
	    				  approveHtml += "<td class=\"input-group-addon\">审批人</td>";
	    			  approveHtml += "<td>"+v.editedBy+"</td>";
	    			  approveHtml += "<td class=\"input-group-addon\">审批时间</td>";
	    			  approveHtml += "<td>"+v.modifyTime+"</td>";
	    			  approveHtml += "</tr>";
	    			  
	    		  }
	    	  })
	    	  var length = $("#table").find("tr").length-1;
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
	    		  html += "<small>资产文件</small>";
	    		  html += "</div>";
	    		  html += "</div>";
	    		  html += "</div>";
	    	  })
	    	  $("#images").html(html);
	      }
	  });
  }
  
  //还款页签
  queryRepayDetail();  
  
  function queryRepayDetail(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/repayList/detail",
	      data:{
	    	  borrowId:Request.borrowId
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  //获取数据
	    	  $("#borrowName").text(responseMsg.borrowTitle);
	    	  $("#borrowerName").text(responseMsg.borrowUserName);
	    	  $("#borrowRate2").text(responseMsg.borrowRate+"%");
	    	  $("#investCycle").text(responseMsg.borrowDates);
	    	  $("#balance").text((responseMsg.balance.amount).toFixed(2)+"元");
	    	  $("#totoalBorrowAmount").text((responseMsg.borrowAmount.amount).toFixed(2)+"元");
	    	  $("#borrowedAmount").text((responseMsg.borrowedAmount.amount).toFixed(2)+ "元");
	    	  var html = "";
	    	  $.each(responseMsg.borrowRepayPlans, function (k, v) {
	    		  var principalAmount = parseFloat(v.principalAmount.amount);
	    		  var interestAmount = v.interestAmount.amount*1;
	    		  var penalty = v.penaltyAmount.amount;
	    		  var overduePenaltyAmount = v.overduePenaltyAmount.amount;
	    		  var borrowManagerFeeAmount = v.borrowManagerFeeAmount.amount;
	    		  var total = (principalAmount+interestAmount+overduePenaltyAmount+borrowManagerFeeAmount).toFixed(2);
	    		  html +="<tr>";
		          html +="<td>"+v.phase+"</a></td>";
		          html +="<td>"+principalAmount+"</td>";
		          html +="<td>"+interestAmount+"</td>";
		          html +="<td>"+principalAmount+"</td>";
		          html +="<td>"+overduePenaltyAmount+"</td>";
		          html +="<td>"+borrowManagerFeeAmount+"</td>";
		          html +="<td>"+total+"</td>";
		          html +="<td>"+v.interestEndDate.split(" ")[0]+"</td>";
		          
		          
		          if(v.status=="INIT"){
		        	  html +="<td>待还款</td>";
		          }else if(v.status=="OVERDUE"){
		        	  html +="<td>逾期中</td>";
		          }else{
		        	  html +="<td>已还款 </td>";
		          }
		          if("REPAY_COMMEN" == v.status || "REPAY_EARLY" == v.status || "REPAY_OVERDUE" == v.status){
		        	  html +="<td>&nbsp;</td>";
		          }else{
		        	  var newDate = v.interestEndDate.split(" ")[0];
			          var newDate2 = responseMsg.nowDate.split(" ")[0];
			          if(newDate == newDate2 && (v.status =="INIT"||v.status=="OVERDUE")){
			        	  html +="<td><a onclick=repay(this,'"+responseMsg.id+"',"+v.phase+",'COMMON')>还款</a></td>";
			          }
			          if(newDate > newDate2){
			        	/*  html +="<td><a href='repayList_earlyRepayDetail.html?borrowId="+responseMsg.id+"&phase="+v.phase+"&beforeDate="+beforeDate+"'>提前还款</a></td>";*/
			        	  html +="<td><a onclick=elaryRepayBySelectDate('"+responseMsg.id+"',"+v.phase+")>提前还款</a></td>";
			          }
			          if(newDate < newDate2){
			        	  html +="<td><a onclick=repay(this,'"+responseMsg.id+"',"+v.phase+",'OVERDUE')>逾期还款</a></td>"; 
			          }
		          }
		          html +="</tr>";
	    	  })
	    	  $("#table2").find("tbody").html(html);
	       }
	  });
}

  
  //格式化日期类型
  function   formatDate(now,flag)   {     
      var year=now.getFullYear();     
      var month=now.getMonth()+1;     
      var date=now.getDate();     
      var hour=now.getHours();     
      var minute=now.getMinutes();     
      var second=now.getSeconds();
      if(flag==1){
    	  return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
      }else{
    	  return   year+"-"+month+"-"+date;
      }
  } 
  
  
  
  $("#tab2").click(function(){
	  $("#tab-1").hide();
	  $("#tab-2").show();
	  $("#tab-3").hide();
	  $("#tab-4").hide();
	  $("#borrowId").val(Request.borrowId);
	  var tableBodyHtml = "";
	  $.ajax({
	      type: "POST",
	      url:"/borrow/repayList/loanList",
	      data:{
	    	  borrowId:Request.borrowId
	      },
	      async: false,
	      success: function(data) {
	    	var _table = $('#table3'),
	        tableBodyHtml = '';
	        $.each(data.data.list, function (k, v) {
	    	//获取数据
	          var d_loanCode = v.id,
	              d_loanTypeName = v.loanTypeName,
	              d_title = v.title,
	              d_yearRate=v.yearRate,
	              d_amount=v.amount.amount,
	              d_openTime=v.openTime,
	              d_interestStartDate=v.interestStartDate,
	              d_interestEndDate=v.interestEndDate,
	              d_borrowCycle=v.borrowCycle
	              ;
	          //输出HTML元素
	          tableBodyHtml += '<tr>';
	          tableBodyHtml += '<td>' + d_loanCode + '</td>';
	          tableBodyHtml += '<td>' + d_loanTypeName + '</td>';
	          tableBodyHtml += '<td>' + d_title + '</td>>';
	          tableBodyHtml += '<td>' + d_yearRate + '%</td>';
	          tableBodyHtml += '<td>' + d_borrowCycle + '</td>';
	          tableBodyHtml += '<td>' + d_amount + '</td>';
	          tableBodyHtml += '<td>' + d_openTime.split(" ")[0] + '</td>';
	          tableBodyHtml += '<td>' + d_interestStartDate.split(" ")[0] + '</td>';
	          tableBodyHtml += '<td>' + d_interestEndDate.split(" ")[0]  + '</td>';
	          tableBodyHtml += '</tr>';
	        });
	        _table.find("tbody").html(tableBodyHtml);
	      }
	  })
  })
  
  
  
  
  
  $("#tab3").click(function(){
	  $("#tab-1").hide();
	  $("#tab-2").hide();
	  $("#tab-4").hide();
	  $("#tab-3").show();
	  queryRepayDetail();

  })
  
   $("#tab4").click(function(){
	  $("#tab-1").hide();
	  $("#tab-2").hide();
	  $("#tab-3").hide();
	  $("#tab-4").show();
		  $.ajax({
		      type: "POST",
		      url:"/borrow/repayList/borrowerDetail",
		      data:{
		    	  "borrowId":Request.borrowId
		      },
		      async: false,
		      success: function(responseMsg) {
		    	  //获取数据
		    	  $("#borrowerName-tab4").text(responseMsg.borrowTitle);
		    	  $("#borrowAmount-tab4").text(responseMsg.borrowAmount.amount+"元");
		    	  $("#borrowDates-tab4").text(responseMsg.borrowDates);
		    	  var html = "";
		    	  $.each(responseMsg.borrowRepayPlans, function (k, v) {
		    		  var principalAmount = parseFloat(v.principalAmount.amount);
		    		  var interestAmount = v.interestAmount.amount*1;
		    		  var penalty = v.penaltyAmount.amount;
		    		  var overduePenaltyAmount = v.overduePenaltyAmount.amount;
		    		  var borrowManagerFeeAmount = v.borrowManagerFeeAmount.amount;
		    		  var total = (principalAmount+interestAmount+overduePenaltyAmount+borrowManagerFeeAmount).toFixed(2);
		    		  html +="<tr>";
			          html +="<td>"+v.phase+"</a></td>";
			          html +="<td>"+responseMsg.borrowTitle+"</td>";
			          html +="<td>"+v.interestEndDate.split(" ")[0]+"</td>";
			          html +="<td>"+responseMsg.borrowRate+"%</td>";
			          html +="<td>"+principalAmount+"</td>";
			          html +="<td>"+interestAmount+"</td>";
			          html +="<td>"+overduePenaltyAmount+"</td>";
			          html +="<td>"+borrowManagerFeeAmount+"</td>";
			          html +="<td>"+total+"</td>";
			          if(v.status=="INIT"){
			        	  html +="<td>待还款</td>";
			          }else if(v.status=="OVERDUE"){
			        	  html +="<td>逾期中</td>";
			          }else{
			        	  html +="<td>已还款 </td>";
			          }
			          html +="</tr>";
		    	  })
		    	  $("#table2-tab4").find("tbody").html(html);
		       }
		  });
	});
   
   
  $("#tab1").click(function(){
	  $("#tab-1").show();
	  $("#tab-2").hide();
	  $("#tab-3").hide();
	  $("#tab-4").hide();
	  queryBorrower();
  })
  
});

//还款
function repay(obj,borrowId,phase,repayType){	
	
	$.ajax({
	      type: "POST",
	      url:"/borrow/repayList/borrowList",
	      data:{'id':borrowId,'pageNo':1,'pageSize':10,'statusType':repayType},
	      async: false,
	      dataType: "json",
	      success: function(responseMsg) {
	    	  if(responseMsg.total!=0){
	    	  $.each(responseMsg.list, function (k, v) {
	        	  var beforeDate = $("#beforeDate").val();
//		          if(v.mark == "true"){
//		        		queryBorrowerData(borrowId,phase,repayType,'');
//		        		$("#myModal").modal("show");
//		          }else{
		      		bootbox.confirm("确定要还款吗?", function(result){
		      		if(result){
		      			  $.ajax({
		      			      type: "POST",
		      			      url:"/borrow/repayList/repay",
		      			      data:{
		      			    	  borrowId:borrowId,
		      			    	  phase:phase,
		      			    	  repayType:repayType
		      			      },
		      			      async: false,
		      			      success: function(responseMsg) {
		      			    	  if(responseMsg.resultCode=="C12000000"){
		      			    		  obj.innerText = "";
		      							bootbox.alert("还款成功", function(result){
		      								window.location.reload();
		      							});
		      			    	  }else{
		      			    		  bootbox.alert(responseMsg.resultCodeMsg);
		      			    	  }
		      			      }
		      			  });
		      		}
		      		});
//		          }
	    	  });
	      }
	      }
	  
	  });

}
function elaryRepayBySelectDate(id,phase){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/repayList/borrowList",
	      data:{'id':id,'pageNo':1,'pageSize':10,'statusType':'ADVANCE','phase':phase},
	      async: false,
	      dataType: "json",
	      success: function(responseMsg) {
	    	  if(responseMsg.total!=0){
	    	  $.each(responseMsg.list, function (k, v) {
	        	  var beforeDate = $("#beforeDate").val();
//		          if(v.mark == "true"){
//		        		queryBorrowerData(id,phase,'ADVANCE',beforeDate);
//		        		$("#myModal").modal("show");
//		          }else{
		        		if(undefined == beforeDate || beforeDate == "" || beforeDate == null){
		        			var d = new Date();
		        			beforeDate = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
		        		}
		        		location.href = "repayList_earlyRepayDetail.html?borrowId="+id+"&phase="+phase+"&beforeDate="+beforeDate;
//		          }
	    	  });
	      }
	      }
	  
	  });
}
function queryBorrowerData(id,phase,earlyRepay,beforeDate){
  $.ajax({
      type: "POST",
      url:"/borrow/repayList/borrowData",
      data:{'id':id,'phase':phase,'statusType':earlyRepay,'beforeDate':beforeDate},
      async: false,
      dataType: "json",
      success: function(data) {
    	  console.log(data);
    	  if(data.resCode == 0){
    		  amount1 = 0;
    		  amount2 = 0;
    		  amount3 = 0;
    		  amount4 = 0;
    		  var lastReleaseTime =  formatDate(data.data[0].lastReleaseTime,"yyyy-MM-dd");
    		  var lastRepayTime =  formatDate(data.data[0].lastRepayTime,"yyyy-MM-dd");
    		  var repayType = data.data[0].repayType;
    		  $("#borrowTitle1").text(data.data[0].borrowTitle);
    		  $("#borrowDate1").text(DateDiff(lastReleaseTime,lastRepayTime) + 1 +"天");
    		  $("#borrowRate1").text(data.data[0].borrowRate/100+"%");
    		  $("#lastReleaseTime1").text(lastReleaseTime);
    		  $("#lastRepayTime1").text(lastRepayTime);
    		  if(repayType == "MONTHLYPAYMENTDUE"){
    			  $("#repayType1").text("按月付息到期还本产品");
    		  }else if(repayType == "ONETIMEDEBT"){
    			  $("#repayType1").text("一次性还本付息产品");
    		  }else if(repayType == "MATCHING"){
    			  $("#repayType1").text("等额本息产品");
    		  }
    		  var html = "";
	    	  $.each(data.data[1], function (k, v) {
	    		var repayFee = 0;
	    		var addAmount = 0;
		          html +="<tr>";
		          html +="<td>"+v.phase+"</td>";
		          html +="<td id='id'>"+v.id+"</td>";
		          html +="<td id='ben'>"+v.principalAmount.amount+"</td>";
		          html +="<td id='li'>"+v.interestAmount.amount+"</td>";
		          if(v.repayFee == null ){
		        	  html +="<td width='200px'><input type='text' onblur='calculation("+v.phase+")' placeholder='输入手续费金额' class='form-control validate[required,custom[onlyNumberdouble]]'  data-errormessage-custom-error='手续费只能为数字或小数' data-errormessage-value-missing='手续费不能为空' id='am'></td>";
	    		  }else{
	    			  html +="<td width='200px'><input type='text' onblur='calculation("+v.phase+")' placeholder='输入手续费金额' class='form-control validate[required,custom[onlyNumberdouble]]'  data-errormessage-custom-error='手续费只能为数字或小数' data-errormessage-value-missing='手续费不能为空' value='"+v.repayFee.amount+"' id='am'></td>";
	    			  repayFee = v.repayFee.amount;
	    		  }
		          html +="<td>"+formatDate(v.interestEndDate,"yyyy-MM-dd")+"</td>";
		          addAmount = accAdd(repayFee,accAdd(v.principalAmount.amount,v.interestAmount.amount));
		          html +="<td id='heji'>"+addAmount+"</td>";
		          html +="</tr>";
		          amount1 = accAdd(v.principalAmount.amount,amount1);
		          amount2 = accAdd(v.interestAmount.amount,amount2) ;
		          amount3 = accAdd(amount3,repayFee);
		          amount4 = addAmount;
	    	  });
	    	  $("#amount1").text(amount1);
	    	  $("#amount2").text(amount2);
	    	  $("#amount3").text(amount3);
	    	  $("#amount4").text(amount4);
	    	  $("#tablequery").find("tbody").html(html);
	    	  
    	  }else{
    		  alert("系统错误");
    	  }
      }
  
  });
};

//计算天数差的函数，通用 
function DateDiff(sDate1, sDate2) { //sDate1和sDate2是2017-9-25格式 
var aDate, oDate1, oDate2, iDays
if (navigator.userAgent.indexOf('Firefox') >= 0){
	aDate = sDate1.split("-")
	oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]) //转换为9-25-2017格式 
	aDate = sDate2.split("-")
	oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0])
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数 
}else{
	aDate = sDate1.split("-")
	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) //转换为9-25-2017格式 
	aDate = sDate2.split("-")
	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数 
}
return iDays
}


//小数加法
function accAdd(arg1, arg2) {
var r1, r2, m, c;
try {
  r1 = arg1.toString().split(".")[1].length;
}
catch (e) {
  r1 = 0;
}
try {
  r2 = arg2.toString().split(".")[1].length;
}
catch (e) {
  r2 = 0;
}
c = Math.abs(r1 - r2);
m = Math.pow(10, Math.max(r1, r2));
if (c > 0) {
  var cm = Math.pow(10, c);
  if (r1 > r2) {
      arg1 = Number(arg1.toString().replace(".", ""));
      arg2 = Number(arg2.toString().replace(".", "")) * cm;
  } else {
      arg1 = Number(arg1.toString().replace(".", "")) * cm;
      arg2 = Number(arg2.toString().replace(".", ""));
  }
} else {
  arg1 = Number(arg1.toString().replace(".", ""));
  arg2 = Number(arg2.toString().replace(".", ""));
}
return (arg1 + arg2) / m;
}
$("#choose").click(function(){
/**
 * 模态窗口非空/数字校验
 */
_modalFm1 =  $('#vform');
_modalFm1.validationEngine('attach', {
  maxErrorsPerField:1,
  autoHidePrompt: true,
  autoHideDelay: 3000
});
if (!$("#vform").validationEngine('validate')) {
	return false;
};
var ids = [];
var amounts = [];
	ids[0]=$("#id").text();
	amounts[0]=$("#am").val();
 $.ajax({
      type: "POST",
      url:"/borrow/repayList/updateRepayPlan",
      data:{'ids':ids,'amounts':amounts},
      async: false,
      traditional:true,
      dataType: "json",
      success: function(responseMsg) {
    	  bootbox.alert("设置成功", function() {
      		window.location.reload();
});
      }
  
  });

});
function calculation(){
	amount3 = "0";
	amount3 = accAdd(amount3,$("#am").val());
	var he = accAdd(accAdd($("#ben").text(),$("#am").val()),$("#li").text());
	$("#heji").text(he)
	amount4 = accAdd(accAdd(amount1,amount2),amount3); 
	$("#amount3").text(amount3);
	$("#amount4").text(amount4);
};


function formatDate(date, format) {   
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };       
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
}  