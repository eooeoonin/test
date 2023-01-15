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
	      url:"/borrow/repayList/userDetail",
	      data:{
	    	  loanId:Request.loanId,
	    	  phase:Request.phase
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  $("#loanName").text(responseMsg.title);
	    	  $("#loanRate").text(responseMsg.yearRate+"%");
	    	  $("#remainingDays").text(responseMsg.remainingDays+"天");
	    	  //文件
	    	  var html = "";
	    	  $.each(responseMsg.interestPlans, function (k, v) {
	    		  var principalAmount = v.principalAmount.amount*1;//本金
	    		  var redMoneyAmount = v.redMoneyAmount.amount*1;//红包
	    		  var interestAmount = v.interestAmount.amount*1;//利息
	    		  var penalty = v.penalty.amount*1;//提前还款罚息
	    		  var interest = interestAmount+penalty;//总收益  利息+罚息
	    		  var total = principalAmount + interestAmount + penalty;//总金额
	    		  var realPay = principalAmount - redMoneyAmount;//实际支付金额
	    		  html +="<tr>";
	    		  html +="<td>"+v.userCode+"</a></td>";
	    		  html +="<td>"+v.phone+"</a></td>";
	    		  html +="<td>"+v.userName+"</a></td>";
		          html +="<td>"+v.phase+"</a></td>";
		          html +="<td>"+realPay+"</td>";
		          html +="<td>"+redMoneyAmount+"</td>";
		          html +="<td>"+principalAmount+"</td>";
		          html +="<td>0</td>";
		          html +="<td>"+principalAmount+"</td>";
		          html +="<td>"+interest+"</td>";
		          html +="<td>"+total+"</td>";
		          if(v.status=="INIT"){
		        	  html +="<td>待还款</td>";
		          }
		          if(v.status=="OVERDUE"){
		        	  html +="<td>逾期中</td>";
		          }
		          if(v.status=="FINISH"){
		        	  html +="<td>已还款 </td>";
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
  
  $("#export").click(function(){
	  $("#loanId").val(Request.loanId);
	  $("#phase").val(Request.phase);
	  $("#form").attr("action","/borrow/export");
	  $("#form").submit();
  })
  
});
