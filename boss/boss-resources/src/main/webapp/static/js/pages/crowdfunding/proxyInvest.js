/**
 * 
 */
$(function() {
	
	 var start = {
			elem : "#beginTime",
			format : "YYYY/MM/DD hh:mm:ss",
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas;
				end.start = datas;
			}
		};
	  var end = {
			elem : "#endTime",
			format : "YYYY/MM/DD hh:mm:ss",
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas;
			}
		};
	  laydate(start);
	  laydate(end);
	  
	  tableFun("/crowdfunding/proxyInvest/orderList");
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
	    	tableFun("/crowdfunding/proxyInvest/orderList");
	    }
	  });
});


function tableFun(tdUrl) {
	tableHead();
	var form = $("#form").serialize();
	var startTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	if(startTime!= "" && endTime != "") {
		form = {
				"phone":$("#phone").val(),
				"subProductName":$("#subProductName").val(),
				"status":$("#status").val(),
				"beginTime":$("#beginTime").val(),
				"endTime":$("#endTime").val(),
				"pageNo":$("#currPage").val(),
				"pageSize":$("#pageSize").val()
			};
	}
	else
		form = $("#form").serialize();		
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : form,
				dataType : "json",
				success : function(data) {
					if(data.resCode == 0) {
						var _table = $('#table'), tableBodyHtml = '';
						$("#currPage").val(data.data.pageNum);
			    		$("#pageCount").val(data.data.pages);
						$.each(
								data.data.list,
								function(k, v) {
									var d_status,
									status=$("#status").val();
									
									switch(v.status){
									case "1":
										d_status = "待支付";
										break;
									case "2":
										d_status = "支付中";
										break;
									case "3":
										d_status = "已生效";
										break;
									case "-4":
										d_status = "已退款";
										break;
									case "-1":
										d_status = "已失效";
										break;
									case "-2":
										d_status = "用户已取消";
										break;
									case "-3":
										d_status = "申请退款";
										break;
									case "4":
										d_status = "已结算";
										break;
									default:
										d_status = v.status;
									    break;
									}
									tableBodyHtml += '<tr>';
									
									if(status == "0") {
										tableBodyHtml += '<td>' + v.userId + '</td>';
										tableBodyHtml += '<td>' + d_status + '</td>'; 
									}
									tableBodyHtml += '<td>' + v.orderingTime + '</td>';
                                    if(status == "0") {
                                    	if(v.status == "-4"){
                                            tableBodyHtml += '<td>' + v.refundTime + '</td>';
                                        }else if(v.status == "4"){
                                            tableBodyHtml += '<td>' + v.settleTime + '</td>';
                                        }else{
                                            tableBodyHtml += '<td>-</td>';
										}
                                    }
									if(status == "3") {
										tableBodyHtml += '<td>' + v.payTime + '</td>';
									}
									if(status == "-4") {
										tableBodyHtml += '<td>' + v.refundTime + '</td>';
									}
                                    if(status == "4") {
                                        tableBodyHtml += '<td>' + v.settleTime + '</td>';
                                    }
									tableBodyHtml += '<td>' + v.phone + '</td>';
									tableBodyHtml += '<td>' + v.name + '</td>';
									tableBodyHtml += '<td>' + v.idcard + '</td>';
									tableBodyHtml += '<td>' + v.amount.amount.toFixed(2) + '</td>';
									if(status == "2") {
										tableBodyHtml += '<td>' + v.alreadyPayAmount.amount.toFixed(2) + '</td>';
									}
									tableBodyHtml += '<td>' + v.subProductName + '</td>';
									if(status == "0") {
										if(v.status == "1"  || v.status == "2" )
											tableBodyHtml += '<td><a href="#" style="margin-left:15px;" onclick="buy(\''+ v.id +'\')">购买</a></td>';//购买
										else if(v.status == "-1")
											tableBodyHtml += '<td><a href="#" style="margin-left:15px;" onclick="effect(\''+ v.id +'\')">激活</a></td>';//激活
										else
											tableBodyHtml += '<td></td>';
									}
									if(status != "0") {
										if(v.status == "1"  || v.status == "2" )
											tableBodyHtml += '<td><a href="#" style="margin-left:15px;" onclick="buy(\''+ v.id +'\')">购买</a></td>';//购买
										else if(v.status == "-1")
											tableBodyHtml += '<td><a href="#" style="margin-left:15px;" onclick="effect(\''+ v.id +'\')">激活</a></td>';//激活
										else if(v.status != "4")
											tableBodyHtml += '<td></td>';
									}
									
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
					}
					else
						alert(data.msg);
				},
				async : false
			});
}

function tableHead() {
	var tableHeadHtml ="",
	status=$("#status").val(),
	_table = $('#table');
	tableHeadHtml += '<tr>';		
	if(status == "0") {
		tableHeadHtml += '<th>用户ID</th>';
		tableHeadHtml += '<th>状态</th>';
	}
	tableHeadHtml += '<th>购买时间</th>';
    if(status == "0") {
        tableHeadHtml += '<th>清算时间</th>';
    }
	if(status == "3") {
		tableHeadHtml += '<th>完成时间</th>';
	}
	if(status == "-4") {
		tableHeadHtml += '<th>退款时间</th>';
	}
    if(status == "4") {
        tableHeadHtml += '<th>清算时间</th>';
    }
	tableHeadHtml += '<th>用户名</th>';
	tableHeadHtml += '<th>姓名</th>';
	tableHeadHtml += '<th>身份证号</th>';
	tableHeadHtml += '<th>金额(元)</th>';
	if(status == "2") {
		tableHeadHtml += '<th>已支付金额(元)</th>';
	}
	tableHeadHtml += '<th>权益</th>';
	if(status == "1"  || status == "-1" || status == "2" || status == "0") {
		tableHeadHtml += '<th>操作</th>';
	}
	tableHeadHtml += '</tr>';	
	_table.find("thead").html(tableHeadHtml);	
}

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	$("#currPage").val(1);
	tableFun("/crowdfunding/proxyInvest/orderList");
	 
	  var $curP = $("#currPage"),
	  $pageC = $("#pageCount");
	  var curpage = 1;
	  var pageCount = parseInt($pageC.val());
	  var $tcdPage = $(".tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: pageCount,
	    current: curpage,
	    backFn: function (p) {
	    	$curP.val(p);
	    	tableFun("/crowdfunding/proxyInvest/orderList");
	    }
	  });
});


function effect(orderId){
	  $.ajax({
        type: "POST",
        url: "/crowdfunding/proxyInvest/effectiveOrder",
        data:{
      	  orderId:orderId
        },
        success: function(resultObj) {
           if(resultObj.msg=="success"){
          	   alert("激活成功");
          	   window.location.reload();
           }else{
        	   alert(resultObj.msg);                	 
           }
        }
    });
}


function buy(orderId){
	  $.ajax({
        type: "POST",
        url: "/crowdfunding/proxyInvest/buy",
        data:{
      	  orderId:orderId
        },
        success: function(resultObj) {
           if(resultObj.msg=="success"){
          	 	alert("购买成功");
          	 	window.location.reload();
           }else{
				 alert(resultObj.msg);               	 
           }
        }
    });
}

$("#export").click(function(){
	var form;
	var startTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	if(startTime== "" || endTime == "")
		form = {
			"beginTime":"",
			"endTime":"",
			"phone":$("#phone").val(),
			"subProductName":$("#subProductName").val(),
			"status":$("#status").val()
		};
	else
		form = {
			"beginTime":Date.parse($("#beginTime").val()).toString(),
			"endTime":Date.parse($("#endTime").val()).toString(),
			"phone":$("#phone").val(),
			"subProductName":$("#subProductName").val(),
			"status":$("#status").val()
		};
	window.location.href="/crowdfunding/proxyInvest/export/"+JSON.stringify(form);

});
$("#export2").click(function(){
	var beginTime = $("#beginTime").val(),
	endTime = $("#endTime").val(),
	phone = $("#phone").val(),
	subProductName = $("#subProductName").val(),
	status = $("#status").val();
	if(beginTime != "" && endTime != "")
		window.location.href="/crowdfunding/proxyInvest/export2?phone="+phone+"&status="+status+"&subProductName="+subProductName+"&beginTime="+beginTime+"&endTime="+endTime;
	else
		window.location.href="/crowdfunding/proxyInvest/export2?phone="+phone+"&status="+status+"&subProductName="+subProductName;
});
