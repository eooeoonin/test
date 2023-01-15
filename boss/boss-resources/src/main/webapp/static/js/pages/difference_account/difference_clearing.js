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

var day;
$(function(){
	var _table = $('#tab_1');
		  _table.bootstrapTable();
		  
		  laydate({elem: "#clearDate", format: "YYYY/MM/DD",
			  	       choose: function(datas){ //选择日期完毕的回调
			  	    	 var day = $("#clearDate").val();
			  	    	 	   $("#clearDate").val(day);
			  			 var srhData = {"day":day};
			  			  	  tableFun("../difference_account/clearpart/clearPartList", srhData);	
			  	       }
		  			});
		  
		  var date1 = new Date();
	  	  var times1 = date1.getFullYear()+"/"+(date1.getMonth()+1)+"/"+(date1.getDate()-1);
	  	  
	  	  var _year = date1.getFullYear();
	  	  var _mouth = date1.getMonth();
	  	  var _daytime = date1.getDate()-1;
	  	  
	  	  var nowday = Request.clearDate;
	  	  if(nowday != "" && nowday != null){
	  		$("#clearDate").val(nowday);
	  	  }else{
	  		  if(_daytime == 0){
	  			 getLastDay(_year,_mouth);
	  		  }else{
	  			$("#clearDate").val(times1);
	  		  }
	  	  }
		  day = $("#clearDate").val();
		  
	var srhData = {"day":day};
		  tableFun("../difference_account/clearpart/clearPartList", srhData);	
})

	function getLastDay(year,month) {   
		   var new_year = year;  //取当前的年份   
		   var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）   
		 //如果当前大于12月，则年份转到下一年
		   if(month>12)  {  	  
		   new_month -=12;    //月份减   
		   new_year++;      //年份增   
		   }   
		   
		   if(new_month == 0){
			   var new_date = new Date(new_year,new_month,1);
			   var _timesday = new_year-1 +"/"+ '12' +"/"+ new Date(new_date.getTime()-1000*60*60*24).getDate();
			   $("#clearDate").val(_timesday);
		   }else{
			   var new_date = new Date(new_year,new_month,1);        //取当年当月中的第一天   
			   var _timesday = new_year +"/"+ new_month +"/"+ new Date(new_date.getTime()-1000*60*60*24).getDate();
			   $("#clearDate").val(_timesday);
		   }
	} 


function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';
        if(data.code == "SUCCESS"){
        	 $.each(data.data, function (k, v) {
   	          //获取数据
   	        	 var u_id = v.id, 
   	        	 	  u_bizCode = v.bizCode;
   		        	  u_bizValue1 = v.bizValue,
   		        	  u_status = v.status
   		              
   		        	  u_bizValue =u_bizValue1.substr(0,4) + "-" + u_bizValue1.substr(4,2) + "-" + u_bizValue1.substr(6,2);
   		        	  
   		        	  
   		        	  if(u_bizCode == 'INVESTOR'){
   		        		  u_bizCode2 = '出借成功、出借失败';
   		        	  }if(u_bizCode == 'KILLRECORD'){
   		        		  u_bizCode2 = '流标';
   		        	  }if(u_bizCode == 'RELEASE'){
   		        		  u_bizCode2 = '放款';
   		        	  }if(u_bizCode == 'REPAY'){
   		        		  u_bizCode2 = '还款';
   		        	  }if(u_bizCode == 'PAYRECORDPROCESS'){
   		        		  u_bizCode2 = '提现申请';
   		        	  }if(u_bizCode == 'PAYRECORD'){
   		        		  u_bizCode2 = '提现成功、失败，充值';
   		        	  }if(u_bizCode == 'ADJUST'){
   		        		  u_bizCode2 = '手动调账';
   		        	  }if(u_bizCode == 'INVITE'){
   		        		  u_bizCode2 = '邀请收益发放';
   		        	  }if(u_bizCode == 'FREEZE'){
   		        		  u_bizCode2 = '资金冻结';
   		        	  }if(u_bizCode == 'REDMONEYFREEZE'){
   		        		  u_bizCode2 = '红包冻结';
   		        	  }if(u_bizCode == 'ACCOUNT'){
   		        		  u_bizCode2 = '资金交易';
   		        	  }if(u_bizCode == 'REDMONEYACCOUNT'){
   		        		  u_bizCode2 = '红包交易';
   		        	  }if(u_bizCode == 'USER_INFO'){
   		        		  u_bizCode2 = '用户信息';
   		        	  }if(u_bizCode == 'SSO_USER_INFO'){
   		        		  u_bizCode2 = 'SSO用户信息';
   		        	  }if(u_bizCode == 'REAL_NAME_AUTH'){
   		        		  u_bizCode2 = '实名认证信息';
   		        	  }if(u_bizCode == 'BIND_BANK_CARD'){
   		        		  u_bizCode2 = '绑定银行卡信息';
   		        	  }
   		        	  
   		        	  
   		        	  if(u_status == 'PRE_DO'){
   		        		  u_status2 = '待处理';
   		        	  } if(u_status == 'DOING'){
   		        		  u_status2 = '处理中';
   		        	  } if(u_status == 'DONE'){
   		        		  u_status2 = '处理成功';
   		        	  } if(u_status == 'FAILURE'){
   		        		  u_status2 = '处理失败';
   		        	  }
   		        	  
   	          //输出HTML元素
   	          tableBodyHtml += '<tr>';
   	          tableBodyHtml += '<td>' + "<label class=\"radio-inline i-checks\"><input type=\"checkbox\" id=\""+u_bizCode+"\" name=\"bizCodeSets\" value=\""+u_bizCode+"\" class=\"sub_radbox\"></label>" + '</td>';
   	          tableBodyHtml += '<td><a name="'+u_bizCode+'" onclick="deail(this)">' + u_bizCode2 + '</a></td>';
   	          tableBodyHtml += '<td>' + u_bizValue + '</td>';
   	          if(u_status == 'FAILURE'){
   	        	  tableBodyHtml += '<td style="color:red">' + u_status2 + '</td>';
   	          }else if(u_status == 'PRE_DO'){
   	        	  tableBodyHtml += '<td style="color:blue">' + u_status2 + '</td>';
   	          }else if(u_status == 'DOING'){
   	        	  tableBodyHtml += '<td style="color:green">' + u_status2 + '</td>';
   	          }else{
   	        	  tableBodyHtml += '<td>' + u_status2 + '</td>';
   	          }
   	          if(u_status == 'FAILURE'){
   	        	  tableBodyHtml += '<td><a href="#" style="margin-left:15px;" name="'+u_bizCode+'" onclick="updateStatusForPreDoByFailure(this)">重置</a></td>';
   	          }else{
   	        	  tableBodyHtml += '<td> ' + "" + '</td>';
   	          }
   	          tableBodyHtml += '</tr>';
   	        });
        }
       
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
      },error:function(data){
    	  alert(data)
      },
    async : false
    });
  }

	$("#refreshall").click(function(){
		location = location; 
		var day1 = $("#clearDate").val();
	 	   window.location.href="../difference_account/difference_clearing.html?clearDate="+day1+"";
	})



//判断是否选择了记录
function validateIsSelect(allobj,items){
    if(items.length){
	    for(var i=0;i<items.length;i++){
	    	if(items[i].checked){
	    		return "true";
	    	} 
	    }
   }else{
	    if(items.checked) return "true";
   }
   return "false";
}

	function search(){
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
      }
	
	function deail(bizCode){
		deailShow(bizCode);
		$('#myModal').modal('show');
	}
	
	
	function deailShow(bizCode){
		var day2 = $("#clearDate").val();
		$.ajax({
		      type: "POST",
		      url: "../difference_account/clearpart/listForDetailByBizCode",
		      data:{"bizCode":bizCode.name,"day":day2},
		      dataType: "json",
		      success: function (data) {
		        var _table = $('#table2'),
		          tableBodyHtml = '';
		        
		        $.each(data.data, function (k, v) {
			          //获取数据
			        	 var u_id = v.id, 
			        	 	  u_bizCode = v.bizCode;
				        	  u_bizValue = v.bizValue,
				        	  u_status = v.status,
				        	  u_pageNo = v.pageNo,
				        	  u_note = v.note,
				        	  u_createTime = v.createTime,
				        	  u_modifyTime = v.modifyTime
				              
				        	  u_bizValue =u_bizValue1.substr(0,4) + "-" + u_bizValue1.substr(4,2) + "-" + u_bizValue1.substr(6,2);
				        	  
				        	  if(u_bizCode == 'INVESTOR'){
				        		  u_bizCode2 = '出借成功、出借失败';
				        	  }if(u_bizCode == 'KILLRECORD'){
				        		  u_bizCode2 = '流标';
				        	  }if(u_bizCode == 'RELEASE'){
				        		  u_bizCode2 = '放款';
				        	  }if(u_bizCode == 'REPAY'){
				        		  u_bizCode2 = '还款';
				        	  }if(u_bizCode == 'PAYRECORDPROCESS'){
				        		  u_bizCode2 = '提现申请';
				        	  }if(u_bizCode == 'PAYRECORD'){
				        		  u_bizCode2 = '提现成功、失败，充值';
				        	  }if(u_bizCode == 'ADJUST'){
				        		  u_bizCode2 = '手动调账';
				        	  }if(u_bizCode == 'INVITE'){
				        		  u_bizCode2 = '邀请收益发放';
				        	  }if(u_bizCode == 'FREEZE'){
				        		  u_bizCode2 = '资金冻结';
				        	  }if(u_bizCode == 'REDMONEYFREEZE'){
				        		  u_bizCode2 = '红包冻结';
				        	  }if(u_bizCode == 'ACCOUNT'){
				        		  u_bizCode2 = '资金交易';
				        	  }if(u_bizCode == 'REDMONEYACCOUNT'){
				        		  u_bizCode2 = '红包交易';
				        	  }
				        	  
				        	  
				        	  if(u_status == 'PRE_DO'){
				        		  u_status2 = '待处理';
				        	  } if(u_status == 'DOING'){
				        		  u_status2 = '处理中';
				        	  } if(u_status == 'DONE'){
				        		  u_status2 = '处理成功';
				        	  } if(u_status == 'FAILURE'){
				        		  u_status2 = '处理失败';
				        	  }
				        	  
				        	  
			          //输出HTML元素
			          tableBodyHtml += '<tr>';
			          tableBodyHtml += '<td>' + k +'</td>';
			          tableBodyHtml += '<td>' + u_bizCode2 + '</td>';
			          tableBodyHtml += '<td>' + u_bizValue + '</td>';
			          if(u_status == 'FAILURE'){
			        	  tableBodyHtml += '<td style="color:red">' + u_status2 + '</td>';
			          }else if(u_status == 'PRE_DO'){
			        	  tableBodyHtml += '<td style="color:blue">' + u_status2 + '</td>';
			          }else if(u_status == 'DOING'){
			        	  tableBodyHtml += '<td style="color:green">' + u_status2 + '</td>';
			          }else{
			        	  tableBodyHtml += '<td>' + u_status2 + '</td>';
			          }
			          tableBodyHtml += '<td>' + u_pageNo + '</td>';
			          tableBodyHtml += '<td>' + u_note + '</td>';
			          tableBodyHtml += '<td>' + u_createTime + '</td>';
			          tableBodyHtml += '<td>' + u_modifyTime + '</td>';
			          tableBodyHtml += '</tr>';
			        });
		        _table.find("tbody").html(tableBodyHtml);
		        replaceFun(_table);
		      },error:function(data){
		    	  alert(data)
		      },
		    async : false
		    });
	}
	
	//重置
	function updateStatusForPreDoByFailure(bizCode){
		var day3 = $("#clearDate").val();
		var date1 = new Date();
	  	var times1= date1.getFullYear()+"/"+(date1.getMonth()+1)+"/"+(date1.getDate());
//	  	  	if(new Date(day3).getTime() < new Date(times1).getTime()){
				$.ajax({
		            type: "POST",
		            dataType: "json",
		            url: "../difference_account/clearpart/updateStatusForPreDoByFailure",
		            data: {"bizCode":bizCode.name,"day":day3},
		            success: function(data) {
		            	alert("重置成功");
		            	window.location.href="../difference_account/difference_clearing.html?clearDate="+day3+"";
		            },error:function(data){
		            	alert("重置失败");
		            	window.location.href="../difference_account/difference_clearing.html?clearDate="+day3+"";
		            }
		        });
//	  	  	}else{
//	  	  		alert("选择时间要小于当前时间");
//	  	  	}	
	}
	
	
	
		$("#synchronization").click(function(){
			var day4 = $("#clearDate").val();
			var obj=document.getElementsByName('bizCodeSets');
			var date2 = new Date();
  	  	  	var times2= date2.getFullYear()+"/"+(date2.getMonth()+1)+"/"+(date2.getDate());
//  	  	  		if(new Date(day4).getTime() < new Date(times2).getTime()){
					if(obj.length != 0){
						var chk_value = new Array();
						$('input[name="bizCodeSets"]:checked').each(function(){
							chk_value.push($(this).val());
						});
						if(chk_value.length==0){
							$.ajax({
					            type: "POST",
					            dataType: "json",
					            url: "../difference_account/clearpart/sync",
					            data: {"day":day4},
					            success: function(data) {
					            	alert("同步成功，稍等片刻刷新本页面");
					            	var srhData = {"day":day4};
					      		  		  tableFun("../difference_account/clearpart/clearPartList", srhData);
					      		  		  window.location.href="../difference_account/difference_clearing.html?clearDate="+day4+"";
					            },error:function(data){
					            	alert("同步失败");
					            	window.location.href="../difference_account/difference_clearing.html?clearDate="+day4+"";
					            }
					        });
						}else{
							$.ajax({
					            type: "POST",
					            dataType: "json",
					            traditional:true,
					            url: "../difference_account/clearpart/sync",
					            data: {"bizCodeSets":chk_value,"day":day4},
					            success: function(data) {
					            	 alert("同步成功，稍等片刻刷新本页面");
					            	 window.location.href="../difference_account/difference_clearing.html?clearDate="+day4+"";
					            },error:function(data){
					            	 alert("同步失败");
					            	 window.location.href="../difference_account/difference_clearing.html?clearDate="+day4+"";
					            }
					        });
						}
						
					}else{
						$.ajax({
				            type: "POST",
				            dataType: "json",
				            url: "../difference_account/clearpart/sync",
				            data: {"day":day4},
				            success: function(data) {
				            	alert("同步成功，稍等片刻刷新本页面");
				            	var srhData = {"day":day4};
				      		  		  tableFun("../difference_account/clearpart/clearPartList", srhData);
				      		  		  window.location.href="../difference_account/difference_clearing.html?clearDate="+day4+"";
				            },error:function(data){
				            	alert("同步失败");
				            	window.location.href="../difference_account/difference_clearing.html?clearDate="+day4+"";
				            }
				        });
					}
//				}else{
//					alert("选择时间要小于当前时间");
//				}
			
			})
			
			
			
			$("#eliminate").click(function(){
			var form = $("#modal_form").get(0);
			if(typeof(form.bizCodeSets) == "undefined") {
			alert("没有需要处理的数据!");
			return false;
			}
		  bootbox.confirm("确定清除数据吗?", function(result){
				if(result){
					var day5 = $("#clearDate").val();
					var result = validateIsSelect(form.chkall, form.bizCodeSets);
					if("true"==result){
							$.ajax({
					            type: "POST",
					            url: "../difference_account/clearpart/remove",
					            data: $("#modal_form").serialize() + "&day=" + day5,
					            success: function(data) {
					           		 alert("清除成功");
					           		 window.location.href="../difference_account/difference_clearing.html?clearDate="+day5+"";
					            },error:function(data){
					            	alert("清除失败");
					            	 window.location.href="../difference_account/difference_clearing.html?clearDate="+day5+"";
					            }
					        });
					}else if("false"==result){
						$.ajax({
				            type: "POST",
				            url: "../difference_account/clearpart/remove",
				            data: {"day":day5},
				            success: function(data) {
				           		 alert("清除成功");
				           		 location = location;
				            },error:function(data){
				            	alert("清除失败");
				            	location = location;
				            }
				        });
					}
				}
			});
		})
