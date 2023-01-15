var _pages = 0;
$(function() {
	var srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
		};
		tableFun1("/borrow/whitelist/getWhitelist", srhData);
		myPage1();
	
/*	$("#addBtn").click(function() {
		var srhData = {
			"pageNo" : "1",
			"pageSize" : "10"
		};
		tableFun2("/project/p_add_pro/borrowList", srhData);
		myPage2();
		$("#myModal").modal("show");
	});*/

});

$("#srhBtn").click(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"oaFlowCode":$("#oaFlowCode").val(),
		"borrowName":$("#borrowName").val()
	};
	tableFun1("/borrow/whitelist/getWhitelist", srhData);
	myPage1();
});

function tableFun1(tdUrl, tbData){
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
				$.each(_data.list, function(k, v) {
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + v.borrowName + '</td>';
					tableBodyHtml += '<td>' + v.borrowUserName + '</td>';
					tableBodyHtml += '<td>' + v.oaFlowCode + '</td>';
					tableBodyHtml += '<td>' + v.borrowRate/100 + '%</td>';
					tableBodyHtml += '<td>' + v.borrowCycle + '</td>';
					tableBodyHtml += '<td>' + v.borrowAmount.amount + '</td>';
					tableBodyHtml += '<td>' + v.currentPhase +'/'+ v.totalPhase + '</td>';
					tableBodyHtml += '<td>' + v.borrowStatus + '</td>';
					tableBodyHtml += "<td><a href='whitelistDetail.html?borrowCode="+v.borrowCode+"&nextPhase="+v.currentPhase+"&totalPhase="+v.totalPhase+"'>查看</a></td>";
					tableBodyHtml += '</tr>';
				});
				$("#tcdPageCode").show();
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="9" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPageCode").hide();
			}
		},
		async : false
	});
}

var myPage1 = function() {
	// 分页
	var $tcdPage = $("#tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var srhData = {};
			if($("#oaFlowCode").val() == "" && $("#borrowName").val() == "" ){
				srhData = {
						"pageNo" : p,
						"pageSize" : "10"
					};
			}else{
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"oaFlowCode":$("#oaFlowCode").val(),
					"borrowName":$("#borrowName").val()
				};

			}
			tableFun1("/borrow/whitelist/getWhitelist", srhData);
		}
	});
};


function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/borrow/whitelist/delWhitelist",
				data : {'id':id},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('删除成功',function(){
			        		window.location.reload();
			        	});
					} else {
						bootbox.alert("操作失败");
					}
					;
				}
			});
		} else {
			return;
		}
	});
};






function openDetail(borrowCode){
	location.href = "/borrow/whitelistDetail.html?borrowCode="+v.borrowCode+"&nextPhase="+v.nextPhase+"&totalPhase="+v.totalPhase;
}








/*var myPage2 = function() {
	// 分页
	var $tcdPage = $("#tcdPageCode2");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var srhData = {};
			if($("#name").val() != "" && undefined != $("#name").val() && null != $("#name").val()){
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"name":$("#name").val()
				};
			}else{
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
				};
			}
			tableFun("/activity/channelManage/getAll", srhData);
		}
	});
};

function tableFun2(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				// data:$("#borrowerQueryForm").serialize(),
				data : tbData,
				dataType : "json",
				async : false,
				success : function(data) {
					// $("#currPage").val(data.pageNum);
					// $("#pageCount").val(data.pages);
					var html = "";
					_pages = data.pages;
					$
							.each(
									data.list,
									function(k, v) {
										html += "<tr>";
										html += "<td><label class=\"radio-inline i-checks\"><input type=\"radio\" name=\"radio\" class=\"sub_radbox\"></label></td>";
										html += "<td>" + v.borrowTitle
												+ "</td>";
										html += "<td>" + v.borrowUserName
												+ "<input type='hidden' id='"
												+ v.id + "cc' value='"
												+ v.borrowUserCode + "'/></td>";
										// html += '<td><a
										// href="../signet/moulage_edit.html?type1=check&userId='+v.borrowerCode+'">'+v.borrowUserName+'</a></td>';
										html += "<td>" + v.borrowRate
												+ "%</td>";
										 html += "<td>" + v.borrowCycle + "</td>"; 
										// html += "<td>" + v.balance.amount +
										// "</td>";
										html += "<td>" + v.borrowAmount.amount
												+ "</td>";
										html += "<td>" + v.createTime + "</td>";
										 html += "<td>" + v.reviewDate + "</td>"; 
										html += "<td>" + v.borrowType + "</td>";
										html += "<td>" + v.repayTypeName
												+ "</td>";
										html += "<td>"
												+ v.id
												+ "<input type='hidden' id='"
												+ v.id
												+ "tt' value='"
												+ v.termType
												+ "'/><input type='hidden' id='"
												+ v.id + "bt' value='"
												+ v.approveTerm + "'/></td>";
										html += "<td>"
												+ v.repayType
												+ "<input type='hidden' id='"
												+ v.id
												+ "gn' value='"
												+ v.guaranteeUserName
												+ "'/><input type='hidden' id='"
												+ v.id + "gc' value='"
												+ v.guaranteeUserCard
												+ "'/></td>";
										html += "<td>"
												+ v.guaranteeUserId
												+ "<input type='hidden' id='"
												+ v.id
												+ "p' value='"
												+ v.projectMessage
												+ "'/><input type='hidden' id='"
												+ v.id
												+ "s' value='"
												+ v.saveMessage
												+ "'/><input type='hidden' id='"
												+ v.id + "r' value='"
												+ v.risktMessage + "'/><input type='hidden' id='"+v.id+"want' value='"+v.wantReleaseTime+"'></td>";

										// if(v.lastRepayTime != null &&
										// v.lastRepayTime != "" ){
										// html += "<td type='hidden'>" +
										// v.lastRepayTime + "</td>";
										// }else{
										// html += "<td type='hidden'>——</td>";
										// }
										// if(v.lastReleaseTime != null &&
										// v.lastReleaseTime != "" ){
										// html += "<td style='display: none;'>"
										// + v.lastReleaseTime + "</td>";
										// }else{
										// html += "<td type='hidden'>——</td>";
										// }
										 html += "<td>"+v.releaseUserId+"</td>";
										 html += "<td>"+v.releaseUserCard+"</td>";
										html += "</tr>";
									});
					$("#table").find("tbody").html(html);
					replaceFun($("#table"));
				},
				error : function(da) {
					console.log(da.responseText);
					alert(da.responseText);
				}
			});
}*/