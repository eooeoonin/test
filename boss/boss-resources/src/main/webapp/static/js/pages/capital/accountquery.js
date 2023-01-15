/**
 * 
 */
var _pages;
$(function() {
	var start = {
		elem : "#start",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			end.min = datas;
			end.start = datas;
		}
	};
	var end = {
		elem : "#end",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			start.max = datas;
		}
	};
	laydate(start);
	laydate(end);
	
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"flowDirection" : "1" 
	};
	tableFun("/capital/accountquery/getRecharge",pageData);
	myPage();
});


function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if(data.resCode == 0) {
						var _table = $('#table'), tableBodyHtml = '';
						_pages = data.data.pages;
						$.each(
								data.data.list,
								function(k, v) {
									var d_id = v.id,
									d_registerMobile = v.registerMobile,
									d_realName = v.realName,
									d_rechargeAmount = v.amount.amount,
									d_rechargeTime = v.applyTime,
									d_userCode = v.userId,
									d_rechargeType = v.payType,
									d_rechargeStatus = v.transStatus,
									mark = v.mark,
									message = v.message;
									if(message == null || message == ""){
										message = "——"
									}
									if(mark == null || mark == ""){
										mark = "——"
									}
									switch(v.transStatus){
										case "INIT":
											d_rechargeStatus="初始";
											break;
										case "PROCESSING":
											d_rechargeStatus="处理中";
											break;
										case "SUCCESS":
											d_rechargeStatus="成功";
											break;
										case "FAIL":
											d_rechargeStatus="失败";
											break;
										default:
										d_rechargeStatus = v.transStatus;
										break;
									
									};
									switch(v.payType){
									case "QUICK":
										d_rechargeType="快捷";
										break;
									case "NETBANK":
										d_rechargeType="网银";
										break;
									case "WITHDRAW":
										d_rechargeType="提现";
										break;
									default:
									d_rechargeType = v.payType;
									break;
								
								};
							

									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_userCode+ '</td>';
/*									tableBodyHtml += '<td>' + mark+ '</td>';*/
									tableBodyHtml += '<td>' + d_registerMobile+ '</td>';
									/*tableBodyHtml += '<td>' + d_realName+ '</td>';*/
									tableBodyHtml += '<td>' + d_rechargeAmount+ '</td>';
									tableBodyHtml += '<td>' + d_rechargeTime+ '</td>';
									tableBodyHtml += '<td>' + d_id+ '</td>';
									tableBodyHtml += '<td>' + d_rechargeType+ '</td>';
									tableBodyHtml += '<td>' + message+ '</td>';
									tableBodyHtml += '<td>' + d_rechargeStatus+ '</td>';
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


var myPage = function(){
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {
		  var v_rechargeStatus = $("#paymentStatus").val(),
			v_registerMobile = $("#registerMobile").val();
		  	v_startTime = $("#start").val(),
		  	v_userId = $("#userId").val(),
			v_endTime = $("#end").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "10",
			"TransStatus" : v_rechargeStatus,
			"registerMobile" : v_registerMobile,
			"userId":v_userId,
			"flowDirection" : "1" 
			};
			if(v_startTime!= ""){
				pageData.beginDate = v_startTime;
			}
			if(v_endTime!= ""){
				pageData.endDate = v_endTime;
			}
		tableFun("/capital/accountquery/getRecharge", pageData);		  
	  }
	});
};


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
		var v_rechargeStatus = $("#paymentStatus").val(),
		v_registerMobile = $("#registerMobile").val(),
		v_startTime = $("#start").val(),
		v_userId = $("#userId").val(),
		v_endTime = $("#end").val();
		
		var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"TransStatus" : v_rechargeStatus,
		"registerMobile" : v_registerMobile,
		"userId":v_userId,
		"flowDirection" : "1" 
		};
		if(v_startTime!= ""){
			srhData.beginDate = v_startTime;
		}
		if(v_endTime!= ""){
			srhData.endDate = v_endTime;
		}
		
		
		tableFun("/capital/accountquery/getRecharge",srhData);
		myPage();
	
});
