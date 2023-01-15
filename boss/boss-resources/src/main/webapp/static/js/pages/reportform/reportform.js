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
	
	var startTime = $("#before").val();
	var endTime = $("#after").val();
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : {
					"startTime":startTime,
					"endTime":endTime
				},
				dataType : "json",
				success : function(data) {
					 var _table = $('#table'),
					 tableBodyHtml = '';
					 tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + data.addRegisteredUsers+ '</td>';
									tableBodyHtml += '<td>' + data.cumulativeRegisteredUsers+ '</td>';
									tableBodyHtml += '<td>' + data.addBindUsers+ '</td>';
									tableBodyHtml += '<td>' + data.cumulativeBindUsers+ '</td>';
									tableBodyHtml += '<td>' + data.rechargeAmount.amount+ '</td>';//充值金额
									tableBodyHtml += '<td>' + data.withdrawAmount.amount+ '</td>';
									tableBodyHtml += '<td>' + data.investAmount.amount+ '</td>';//出借金额
									tableBodyHtml += '<td>' + data.investCount+ '</td>';
									tableBodyHtml += '<td>' + data.investNumber+ '</td>';
									tableBodyHtml += '<td>' + data.maxInvestAmount.amount+ '</td>';//单笔最大
									tableBodyHtml += '<td>' + data.averageInvestAmount.amount+ '</td>';//平均出借金额
									tableBodyHtml += '<td>' + data.maxPersonInvestAmount.amount+ '</td>';//单人最大出借金额
									
									tableBodyHtml += '<td>' + data.averagePersonInvestAmount.amount+ '</td>';//平均每人出借金额
									tableBodyHtml += '<td>' + data.totalRedMoneyAmount.amount+ '</td>';//红包使用金额
									tableBodyHtml += '<td>' + data.repayAmount.amount+ '</td>';//回款金额
									tableBodyHtml += '<td>' + data.averageUserCost.amount+ '</td>';//平均用户注册成本 
									tableBodyHtml += '<td>' +data.averageInvestCost.amount+ '</td>';//平均用户出借成本
									
									tableBodyHtml += '</tr>';
							
						_table.find("tbody").html(tableBodyHtml);
					
				},
				async : false
			});
}




var _srhBtn = $("#change");
_srhBtn.click(function () {
	tableFun("/reportform/reportform/select");

});
