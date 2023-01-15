/**
 * 
 */
var _pages;
$(function() {

	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10"
	};

	tableFun("../platformManager/account/list", srhData);
	myPage();
	var _table = $('#table');
	_table.bootstrapTable();

	document.getElementById('extend').style.display = 'none';
})
/**
 * 
 */
function tableFun(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					var _table = $('#table'), tableBodyHtml = '';
					_pages = data.pages;
					$
							.each(
									data.list,
									function(k, v) {
										// 获取数据
										var p_id = v.id, p_userId = v.userId, p_bankAccountName = v.legalPersonName, p_authState = v.authState, p_availablebalance = (v.balance.amount - v.freezeAmount.amount)
												.toFixed(2), p_freezeAmount = v.freezeAmount.amount, p_balance = v.balance.amount, p_branchBank = v.branchBank, p_bankAccount = v.bankAccount

										if (p_authState == 'INIT') {
											p_authStatetwo = '未开通';
										} else {
											p_authStatetwo = '开通';
										}

										// 输出HTML元素
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + p_userId
												+ '</td>';
										tableBodyHtml += '<td>'
												+ p_bankAccountName + '</td>';
										tableBodyHtml += '<td>'
												+ p_authStatetwo + '</td>';
										tableBodyHtml += '<td>'
												+ p_availablebalance + '</td>';
										tableBodyHtml += '<td>'
												+ p_freezeAmount + '</td>';
										tableBodyHtml += '<td>' + p_balance
												+ '</td>';
										tableBodyHtml += '<td>' + p_branchBank
												+ '</td>';
										tableBodyHtml += '<td>' + p_bankAccount
												+ '</td>';
										if (p_authState == 'BANKCARD' && p_bankAccountName != "分润账户" && p_bankAccountName != "垫资账户") {
											tableBodyHtml += '<td><a  title='
													+ p_id
													+ ' href="#" style="margin-left:15px;" onclick="companyUnbind(\''
													+ p_userId
													+ '\')">解绑</a></td>';
										} else if (p_authState == 'IDCARD' && p_bankAccountName != "分润账户" && p_bankAccountName != "垫资账户") {
											tableBodyHtml += '<td><a  title='
													+ p_id
													+ ' href="#" style="margin-left:15px;" onclick="companybind(\''
													+ p_id + '\')">绑卡</a></td>';
										}else{
											tableBodyHtml += '<td>' + ''
											+ '</td>';
										}
										tableBodyHtml += '</tr>';
									});
					_table.find("tbody").html(tableBodyHtml);
					replaceFun(_table);
				},
				async : false,
				error : function(data) {
					bootbox.alert("接口请求失败");
				}
			});
}

// 分页
// try {
var myPage = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var srhData = {
				"pageNo" : p,
				"pageSize" : "10"
			};
			tableFun("../platformManager/account/list", srhData);
		}
	});
}

$("#accountrecharge").click(function() {
	window.location.href = "../platformManager/account_recharge.html";
})
$("#accountWithdraw").click(function() {
	window.location.href = "../../../../platformManager/withdraw_info.html";
})

function companyUnbind(id) {
	$
			.ajax({
				type : "POST",
				url : "../platformManager/account/companyUnbind",
				data : {
					"userId" : id
				},
				dataType : "json",
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							var _sendUrl = data.data.sendUrl;
							var _data = data.data.sendForm;
							document.all("extend").setAttribute("action",
									_sendUrl);
							document.getElementById("keySerial").value = _data.keySerial;
							document.getElementById("platformNo").value = _data.platformNo;
							document.getElementById("reqData").value = _data.reqData;
							document.getElementById("serviceName").value = _data.serviceName;
							document.getElementById("sign").value = _data.sign;
							document.getElementById("extend").submit();
						} else {
							alert(data.msg);
						}
					} else {
						alert("接口请求失败");
					}
				},
				error : function(data) {
					alert(data);
				}
			});
}

function companybind(id) {
	$
			.ajax({
				type : "POST",
				url : "/platformManager/account/open",
				data : {
					'id' : id
				},
				dataType : "json",
				success : function(data) {
					if (data.data != null && data.data != "") {
						var v = data.data.businessObject;
						var sendUrl = v.sendUrl, sendMethod = v.sendMethod, keySerial = v.sendForm.keySerial, platformNo = v.sendForm.platformNo, reqData = v.sendForm.reqData, serviceName = v.sendForm.serviceName, sign = v.sendForm.sign, userDevice = v.sendForm.userDevice;
						var formHtml = "";
						formHtml += "<form id='custodyForm' method='"
								+ sendMethod + "' action='" + sendUrl + "'>";
						formHtml += "<input type='hidden' name='keySerial' value='"
								+ keySerial + "' />";
						formHtml += "<input type='hidden' name='platformNo' value='"
								+ platformNo + "' />";
						formHtml += "<input type='hidden' name='reqData' value='"
								+ reqData + "' />";
						formHtml += "<input type='hidden' name='serviceName' value='"
								+ serviceName + "' />";
						formHtml += "<input type='hidden' name='sign' value='"
								+ sign + "' />";
						formHtml += "<input type='hidden' name='userDevice' value='"
								+ userDevice + "' />";
						formHtml += "</form>";
						$("body").append(formHtml);
						$("#custodyForm").submit(); // 提交
					} else {
						bootbox.alert(data.msg);
					}
				},
				async : false
			});
}
