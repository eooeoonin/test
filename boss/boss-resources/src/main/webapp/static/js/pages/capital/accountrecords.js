/**
 * 
 */
var _pages;
$(function() {
	var v_starData1 = $("#before").val();
	var v_endData1 = $("#after").val();

	var pageData1 = {
			"pageNo" : "1",
			"pageSize" : "10",
			"transStatus" : "PROCESSING"
//				,
//			"beginDate" : v_starData,
//			"endDate" : v_endData
			
		
	};
	tableFun("/capital/accountquery/getRecharge",pageData1);
	myPage();
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
									d_rechargeAmount = v.amount.cent,
									d_rechargeTime = v.applyTime,
									d_userCode = v.userId,
									d_rechargeType = v.payType,
									d_rechargeStatus = v.transStatus;
									
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
										d_rechargeStatus = v.paymentStatus;
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
									tableBodyHtml += '<td>' + d_registerMobile+ '</td>';
									/*tableBodyHtml += '<td>' + d_realName+ '</td>';*/
									tableBodyHtml += '<td>' + d_rechargeAmount+ '</td>';
									tableBodyHtml += '<td>' + d_rechargeTime+ '</td>';
									tableBodyHtml += '<td>' + d_id+ '</td>';
									tableBodyHtml += '<td>' + d_rechargeType+ '</td>';
									tableBodyHtml += '<td>' + d_rechargeStatus+ '</td>';
									tableBodyHtml += '<td><button class="btn btn-primary" type="button" id="'+d_id+'"onclick="operation(this)">' + '补单' + '</button></td>';  		
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
var updateid;
function operation(obj){
	updateid = obj.id;
	var tData = {
    		"requestId":updateid
    }
	$.ajax({  
        type: "POST",  
        url: "/capital/accountquery/replacement",  
        data: tData,
        dataType:"json",
        traditional:true,
        success: function(data) {
        	alert(data.resultCodeMsg);
        	window.location.reload();
        	
        }
	
	
	
	});
	
}

var myPage = function(){
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {
		  var v_registerMobile1 = $("#registerMobile1").val();
		  var v_starData1 = $("#before").val();
			var v_endData1 = $("#after").val();
			if(v_starData1==""||v_endData1==""){
				var pageData1 = {
						"pageNo" : p,
						"pageSize" : "10",
						"transStatus" : "PROCESSING",
						"registerMobile" : v_registerMobile1
						};
				
			}else{
				var pageData1 = {
						"pageNo" : p,
						"pageSize" : "10",
						"transStatus" : "PROCESSING",
						"registerMobile" : v_registerMobile1,
						"beginDate" : v_starData1,
						"endDate" : v_endData1
						};
			}
			
			//}
		tableFun("/capital/accountquery/getRecharge", pageData1);		  
	  }
	});
};


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
		var a_registerMobile2 = $("#registerMobile1").val();
		
		var a_starData2 = $("#before").val();
		var a_endData2 = $("#after").val();
		if(a_starData2==""||a_endData2==""){
			var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "10",
					"transStatus" : "PROCESSING",
					"registerMobile" : a_registerMobile2
					};	
		}else{
			var srhData2 = {
					"pageNo" : "1",
					"pageSize" : "10",
					"transStatus" : "PROCESSING",
					"registerMobile" : a_registerMobile2,
					"beginDate" : a_starData2,
					"endDate" : a_endData2
					};
		}
		
		tableFun("/capital/accountquery/getRecharge",srhData2);
		myPage();
	
});
