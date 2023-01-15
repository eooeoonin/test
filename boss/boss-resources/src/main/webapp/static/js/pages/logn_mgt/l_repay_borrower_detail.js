/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

$(function () {
	
  
	
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
	
	borrowerDetail();
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
  
  function borrowerDetail(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/repayList/borrowerDetail",
	      data:{
	    	  "borrowId":Request.borrowId
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  //获取数据
	    	  $("#borrowerName").text(responseMsg.borrowTitle);
	    	  $("#borrowAmount").text(responseMsg.borrowAmount.amount);
	    	  $("#borrowDates").text(responseMsg.borrowDates);
	    	  var html = "";
	    	  $.each(responseMsg.borrowRepayPlans, function (k, v) {
	    		  var principalAmount = parseFloat(v.principalAmount.amount);
	    		  var interestAmount = v.interestAmount.amount*1;
	    		  var penalty = v.penaltyAmount.amount;
	    		  var total = principalAmount+interestAmount+penalty;
	    		  html +="<tr>";
		          html +="<td>"+v.phase+"</a></td>";
		          html +="<td>"+responseMsg.borrowTitle+"</td>";
		          html +="<td>"+formatDate(new Date(v.repayTime),2)+"</td>";
		          html +="<td>"+principalAmount+"</td>";
		          html +="<td>"+responseMsg.borrowRate+"%</td>";
		          html +="<td>"+principalAmount+"</td>";
		          html +="<td>"+interestAmount+"</td>";
		          html +="<td>"+penalty+"</td>";
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
	    	  $("#table2").find("tbody").html(html);
	       }
	  });
  }
  
});
