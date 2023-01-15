/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

$(function () {
	
  queryBorrower();
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"})
  
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
	      url:"/borrow/repayList/earlyRepayDetail",
	      data:{
	    	  borrowId:Request.borrowId,
	    	  phase:Request.phase,
	    	  actualInterestEndDate:Request.beforeDate
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  $("#borrowName").text(responseMsg.borrowTitle);			//借款名称
	    	  $("#phase").text(responseMsg.nextPhase);					//当前期数
	    	  //文件
	    	  var html = "";
	    	  $.each(responseMsg.borrowRepayPlans, function (k, v) {
	    		  var days = DateDiff(responseMsg.remainingDays,Request.beforeDate);
	    		  var principalAmount = v.principalAmount.amount*1;
	    		  var interestAmount = v.interestAmount.amount*1;
	    		  var penalty = v.penaltyAmount.amount*1;
	    		  var total = accAdd(penalty,accAdd(principalAmount,interestAmount));
	    		  $("#remainingDays").text(days+"天"); //剩余天数
	    		  html +="<tr>";
	    		  html +="<td>"+Request.beforeDate+"</td>";	//还款日期
		          html +="<td>"+responseMsg.borrowTitle+"</td>";		//借款名称
		          html +="<td>"+responseMsg.borrowRate+"%</td>";		//借款利率
		          html +="<td>"+responseMsg.investCycle+"</td>";		//借款周期
		          html +="<td>"+responseMsg.borrowAmount.amount+"</td>";//项目总额
		          html +="<td>"+interestAmount+"</td>";					//还息
		          html +="<td>"+principalAmount+"</td>";				//还本
		          html +="<td>"+penalty+"</td>";						//罚息
		          html +="<td>"+total+"</td>";
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
  
  $("#earlyPay").click(function(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/repayList/repay",
	      data:{
	    	  borrowId:Request.borrowId,
	    	  phase:Request.phase,
	    	  repayType:"ADVANCE",
	    	  actualInterestEndDate:Request.beforeDate
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  if(responseMsg.resultCode=="C12000000"){
	    		  alert("还款成功");
	    		  window.location.href = "repayList_repayDetail.html?borrowId=" + Request.borrowId;
	    	  }else{
	    		  alert(responseMsg.resultCodeMsg);
	    	  }
	      },error : function(data){
	    	  console.log(data);
	      }
	  });
  })
  
  //还款
  function repay(borrowId,phase,repayType){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/repayList/repay",
	      data:{
	    	  borrowId:borrowId,
	    	  phase:phase,
	    	  repayType:repayType,
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  if(responseMsg.resultCode=="C12000000"){
	    		  alert("还款成功");
	    	  }else{
	    		  alert(responseMsg.resultCodeMsg);
	    	  }
	      }
	  });
  }
});

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
 
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
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


function accSub(arg1, arg2) {
    var r1, r2, m, n;
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
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
 