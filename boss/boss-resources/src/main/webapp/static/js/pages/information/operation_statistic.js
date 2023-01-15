var _pageSize = 15;
var _pages;
$(function () {
	var srhData = {
			"pageNo" : "1",
			"pageSize" : _pageSize,
		};
		tableFun("/information/operation_statistic_list", srhData);
		myPage();
		
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：LSC
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			var a = data.data.total;
			if (a != 0) {
				var _table = $('#table'), tableBodyHtml = '';
				_pages = data.data.pages;
				var _data = data.data;
				for(var i = 0; i < _data.list.length ; i++ ){
					var v = _data.list[i];
					var id=v.id,
						historyTop1amount=v.historyTop1amount.amount,
						historyAmount=v.historyAmount.amount,
						historyTrade=v.historyTrade,
						historyLender=v.historyLender,
						currentLender=v.currentLender,
						borrowBalance=v.borrowBalance.amount,
						currentBorrowTrade=v.currentBorrowTrade,
						currentBorrowerNum=v.currentBorrowerNum,
						historyBorrowerNum=v.historyBorrowerNum,
						historyTop10amount=v.historyTop10amount.amount,
						historyAllamount=v.historyAllamount.amount,
						mark=v.mark,
						infoMonth=v.infoMonth;
						updateTime=v.modifyTime;
//					alert(JSON.stringify(borrowBalance));
					
//					  <th>统计月份</th>
//	                  <th>累计借贷金额</th>
//	 			      <th>累计借贷笔数</th>
//	 			      <th>借贷余额</th>
//	 			      <th>借贷余额笔数</th>
//	                  <th>累计出借人数量</th>
//	                  <th>累计借款人数量</th>
//	                  <th>当期出借人数量</th>
//	                  <th>当期借款人数量</th>
//	                  <th>前十大借款人待还金额占比%</th>
//	                  <th>最大单一借款人待还金额占比%</th>
					
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + infoMonth + '</td>';
					tableBodyHtml += '<td>' + (historyAmount/10000).toFixed(2) + '</td>';
					tableBodyHtml += '<td>' + historyTrade + '</td>';
					tableBodyHtml += '<td>' + (borrowBalance/10000).toFixed(2) + '</td>';
					tableBodyHtml += '<td>' + currentBorrowTrade + '</td>';
					tableBodyHtml += '<td>' + historyLender + '</td>';
					tableBodyHtml += '<td>' + historyBorrowerNum + '</td>';
					tableBodyHtml += '<td>' + currentLender + '</td>';
					tableBodyHtml += '<td>' + currentBorrowerNum + '</td>';
					if (historyAllamount == 0) {
						tableBodyHtml += '<td>0.00%</td>';
						tableBodyHtml += '<td>0.00%</td>';
					} else {
						tableBodyHtml += '<td>' + (historyTop10amount/historyAllamount * 100).toFixed(2) + "%" + '</td>';
						tableBodyHtml += '<td>' + (historyTop1amount/historyAllamount * 100).toFixed(2) + "%" +'</td>';
					}
					if (v.mark == 0 || v.mark == '') {
						tableBodyHtml += '<td>' + updateTime + '</td>';
						tableBodyHtml += '<td>已发布</td>';
					} else {
						tableBodyHtml += '<td id="'+ v.id +'_u"></td>';
						tableBodyHtml += '<td id="'+ v.id +'"><a href="javascript:" onclick="changeStatusFun(\''+ v.id  +'\')" style="margin-left:5px;" class="changeStatus">发布</a></td>';
					}
					tableBodyHtml += '</tr>';
				}
				$("#tcdPageCode").show();
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="12" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPageCode").hide();
			}
		},
		async : false
	});
};

var changeStatusFun = function(_id){
	var changeText = "";
	var changeText1 = $("#"+_id).html();
	$.ajax({
		type : "POST",
		url : "/information/operation_statistic_changeStatus",
		data : {
			"id" : _id
		},
		dataType: "json",
		async:false,
		success : function(data) {
			if(data.flag > 0){
				alert("操作成功！");
				changeText = "已发布";
				$("#"+_id).html(changeText);
				$("#"+_id+"_u").html(data.updateTime);
			} else {
				alert("操作失败！");
			}
		},
		async : false
	});
};

var myPage = function() {
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			srhData = {
				"pageNo" : p,
				"pageSize" : _pageSize,
			};
			tableFun("/information/operation_statistic_list", srhData);
		}
	});
};

$("#exportExcel").click(function(){
	window.location.href="/information/operation_statistic_export";
})
