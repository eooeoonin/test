/**
 * 
 */
var _pages;
$(function() {
	  //分页
	  tableFun("/pay/transactionlog/getLog");
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
	    	tableFun("/pay/transactionlog/getLog");
	    }
	  });
});
var start = {
		elem : "#before",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			end.min = datas;
			end.start = datas;
		}
	};
	var end = {
		elem : "#after",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			start.max = datas;
		}
	};
	try {
		  laydate(start);
		  laydate(end);
		} catch (e) {}

function tableFun(tdUrl) {
	var form = $("#form").serialize();
	var startTime = $("#before").val();
	var endTime = $("#after").val();
	if(startTime!= "" && endTime != "")
		form = $("#form").serialize();
	else
		form = {
			"bankMobile":$("#bankMobile").val(),
			"business":$("#business").val(),
			"orderStatus":$("#orderStatus").val(),
			"pageNo":$("#currPage").val(),
			"pageSize":$("#pageSize").val()
		};
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
									var d_business = "";
									switch(v.business){
										case "NetBank":
											d_business="网银业务";
											break;
										case "Quick":
											d_business="快捷业务";
											break;
										case "POS":
											d_business="POS刷卡";
											break;
										case "Withdraw":
											d_business="提现";
											break;
										case "Weixin":
											d_business="微信支付";
											break;
										case "RealName":
											d_business="实名认证";
											break;
										case "BindCard":
											d_business="绑卡认证";
											break;
										default:
											d_business = v.business;
										break;
									
									};
									var d_orderStatus = v.orderStatus;
									switch(v.orderStatus){
										case 0:
											d_orderStatus="未开始";
											break;
										case 2:
											d_orderStatus="处理中";
											break;
										case 1:
											d_orderStatus="交易成功";
											break;
										case -1:
											d_orderStatus="交易失败";
											break;
										case -2:
											d_orderStatus="已撤销";
											break;
										case -3:
											d_orderStatus="已过期";
											break;
										case -4:
											d_orderStatus="订单不存在";
											break;
										case -5:
											d_orderStatus="重复订单号";
											break;
										case -6:
											d_orderStatus="订单未支付";
											break;
										case -7:
											d_orderStatus="已退款";
											break;
										default:
											d_orderStatus = v.orderStatus;
										break;
									
									};
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + v.requestId+ '</td>';
									tableBodyHtml += '<td>' + d_business+ '</td>';
									tableBodyHtml += '<td>' + v.payerUsername+ '</td>';
									tableBodyHtml += '<td>' + v.amount.amount+ '</td>';
									tableBodyHtml += '<td>' + v.bankName+ '</td>';
									tableBodyHtml += '<td>' + v.bankCode+ '</td>';
									tableBodyHtml += '<td>' + v.bankMobile+ '</td>';
									tableBodyHtml += '<td>' + v.cardNo+ '</td>';
									tableBodyHtml += '<td>' + v.payeeUsername+ '</td>';
									tableBodyHtml += '<td>' + v.requestTime+ '</td>';
									tableBodyHtml += '<td>' + v.responseMessage+ '</td>';
									tableBodyHtml += '<td>' + d_orderStatus+ '</td>';
									tableBodyHtml += '<td><a href="transactionlogdetail.html?Id='+ v.id +'" style="margin-left:15px;">详情</a></td>';
									if(v.orderStatus == 2)
										tableBodyHtml += '<td><button class="btn btn-primary" type="button" id="'+v.requestId+'"onclick="operation(this)">' + '补单' + '</button></td>'; 
									else
										tableBodyHtml += '<td></td>'; 
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
						replaceFun(_table);
						
					}
					else
						alert(data.msg);
				},
				async : false
			});
}

function operation(obj){
	var updateid = obj.id;
	var tData = {
    		"requestId":updateid
    };
	$.ajax({  
        type: "POST",  
        url: "/pay/transactionlog/revise",  
        data: tData,
        dataType:"json",
        traditional:true,
        success: function(data) {
        	alert(data.resultCodeMsg);
        	window.location.reload() ;    	
        }
	});	
}


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	$("#currPage").val(1);
	tableFun("/pay/transactionlog/getLog");
	 
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
	    	tableFun("/pay/transactionlog/getLog");
	    }
	  });
});
