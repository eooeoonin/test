/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var amount1 = 0;
var amount2 = 0;
var amount3 = 0;
var amount4 = 0;
var count = 1;
$(function () {
	 queryBorrow();
  
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
  
 

  //分页
  var $curP = $("#currPage"),
  $pageC = $("#pageCount");
  var curpage = parseInt($curP.val());
  var pageCount = parseInt($pageC.val());
  var $tcdPage = $(".tcdPageCode");
  $tcdPage.createPage({
    pageCount: pageCount,
    current: curpage,
    backFn: function (p) {
    	$curP.val(p);
    	queryBorrow();
    }
  });
  
//查询借款人信息
  function queryBorrow(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_firstinstance_list/borrowList",
	      data:$("#form").serialize(),
	      async: false,
	      dataType: "json",
	      success: function(responseMsg) {
	    	  if(responseMsg.total!=0){
	    	  if(null!=responseMsg){
	    		  $("#currPage").val(responseMsg.pageNum);
	    		  $("#pageCount").val(responseMsg.pages);
	    	  }
	    	  var html = "";
	    	  $.each(responseMsg.list, function (k, v) {
		          html +="<tr>";
		          html +="<td>"+v.id+"</td>";
		          html +="<td><a href='l_firstinstance_list_view.html?borrowId="+v.id+"'>"+v.borrowTitle+"</a></td>";
		          html +="<td>"+v.borrowUserName+"</td>";
		          html +="<td>企业借款</td>";
		          html +="<td>"+v.borrowRate+"%</td>";
		          html +="<td>"+v.borrowDates+"</td>";
		          html +="<td>"+v.borrowAmount.amount+"</td>";
		          html +="<td>"+v.createTime+"</td>";
		          html +="<td>"+v.borrowType+"</td>";
		          html +="<td>"+v.protocolId+"</td>";
//		          if(v.mark == "true"){
//		        	  html +="<td><a onclick='setUpdate(\""+v.id+"\")'>还款计划</a></td>";
//		          }else{
		        	  html +="<td><a href='l_firstinstance_list_info.html?borrowId="+v.id+"'>审核</a>";
//		          }
		          html +="</tr>";
	    	  });
	    	  $("#table").find("tbody").html(html);
	    	  $("#tcdPagehide").show();
	      }else{
	    	  $("#tcdPagehide").hide();
	      }
	      }
	  
	  });
  }
  
  //格式化日期类型
  function formatDate(now)   {
      var year=now.getFullYear();     
      var month=now.getMonth()+1;     
      var date=now.getDate();     
      var hour=now.getHours();     
      var minute=now.getMinutes();     
      var second=now.getSeconds();
      return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
      
  }

});
function setUpdate(id){
		queryBorrowerData(id);
	  $("#myModal").modal("show");
};
function queryBorrowerData(id){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_firstinstance_list/borrowData",
	      data:{'id':id,'phase':0,'statusType':'','beforeDate':''},
	      async: false,
	      dataType: "json",
	      success: function(data) {
	    	  console.log(data);
	    	  if(data.resCode == 0){
	    		  count = 1;
	    		  amount1 = 0;
	    		  amount2 = 0;
	    		  amount3 = 0;
	    		  amount4 = 0;
	    		  var lastReleaseTime =  formatDate(data.data[0].lastReleaseTime,"yyyy-MM-dd");
	    		  var lastRepayTime =  formatDate(data.data[0].lastRepayTime,"yyyy-MM-dd");
	    		  var repayType = data.data[0].repayType;
	    		  $("#borrowTitle").text(data.data[0].borrowTitle);
	    		  $("#borrowDate").text(DateDiff(lastReleaseTime,lastRepayTime) + 1 +"天");
	    		  $("#borrowRate").text(data.data[0].borrowRate/100+"%");
	    		  $("#lastReleaseTime").text(lastReleaseTime);
	    		  $("#lastRepayTime").text(lastRepayTime);
	    		  if(repayType == "MONTHLYPAYMENTDUE"){
	    			  $("#repayType").text("按月付息到期还本产品");
	    		  }else if(repayType == "ONETIMEDEBT"){
	    			  $("#repayType").text("一次性还本付息产品");
	    		  }else if(repayType == "MATCHING"){
	    			  $("#repayType").text("等额本息产品");
	    		  }
	    		  var html = "";
		    	  $.each(data.data[1], function (k, v) {
		    		count++;
		    		var repayFee = 0;
		    		var addAmount = 0;
			          html +="<tr>";
			          html +="<td>"+v.phase+"</td>";
			          html +="<td id='id"+v.phase+"'>"+v.id+"</td>";
			          html +="<td id='ben"+v.phase+"'>"+v.principalAmount.amount+"</td>";
			          html +="<td id='li"+v.phase+"'>"+v.interestAmount.amount+"</td>";
			          if(v.repayFee == null ){
			        	  html +="<td width='200px'><input type='text' onblur='calculation()' placeholder='输入手续费金额' class='form-control validate[required,custom[onlyNumberdouble]]'  data-errormessage-custom-error='手续费只能为数字或小数' data-errormessage-value-missing='手续费不能为空' id='am"+v.phase+"'></td>";
		    		  }else{
		    			  html +="<td width='200px'><input type='text' onblur='calculation()' placeholder='输入手续费金额' class='form-control validate[required,custom[onlyNumberdouble]]'  data-errormessage-custom-error='手续费只能为数字或小数' data-errormessage-value-missing='手续费不能为空' value='"+v.repayFee.amount+"' id='am"+v.phase+"'></td>";
		    			  repayFee = v.repayFee.amount;
		    		  }
			          html +="<td>"+formatDate(v.interestEndDate,"yyyy-MM-dd")+"</td>";
			          addAmount = accAdd(repayFee,accAdd(v.principalAmount.amount,v.interestAmount.amount));
			          html +="<td id='heji"+v.phase+"'>"+addAmount+"</td>";
			          html +="</tr>";
			          amount1 = accAdd(v.principalAmount.amount,amount1);
			          amount2 = accAdd(v.interestAmount.amount,amount2) ;
			          amount3 = accAdd(amount3,repayFee);
			          amount4 = accAdd(amount4,addAmount);
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
	for(j = 0;j < count - 1; j++){
		ids[j]=$("#id"+(j+1)).text();
		amounts[j]=$("#am"+(j+1)).val();
	}
	 $.ajax({
	      type: "POST",
	      url:"/borrow/l_firstinstance_list/updateRepayPlan",
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
	for(i=1;i< count;i++){
		amount3 = accAdd(amount3,$("#am"+i).val());
		var he = accAdd(accAdd($("#ben"+i).text(),$("#am"+i).val()),$("#li"+i).text());
		$("#heji"+i).text(he)
	}
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