/*******************************************************************************
 * ** 获取URL参数
 ******************************************************************************/
var _pages;
var _userId;
var _inviteFriends;
var _bonusAmount;
var registmoble;
function GetRequest() {
	var url = location.search; // 获取url中"?"符后的字串
	url = decodeURIComponent(url);
	var theRequest = {};
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
		}
	}
	return theRequest;
}

var Request = {};
Request = GetRequest();

$(function() {
	$("#tcdPageCodehide").hide();
	$("#inviteMoney").hide();
	$("#LoanInvestorMoney").hide();
	$("#Accountname").hide();
	basic();// 基本信息
	account();// 账户信息
	bankCard();// 银行卡信息
	weChat();// 微信信息
	selectuserRiskService();

	_modalFm = $('#modal_form');
	_modalFm.validationEngine('attach', {
		maxErrorsPerField : 1,
		autoHidePrompt : true,
		autoHideDelay : 2000
	});
	_modalFm2 = $('#modal_form2');
	_modalFm2.validationEngine('attach', {
		maxErrorsPerField : 1,
		autoHidePrompt : true,
		autoHideDelay : 2000
	});
	_modalFm3 = $('#modal_form3');
	_modalFm3.validationEngine('attach', {
		maxErrorsPerField : 1,
		autoHidePrompt : true,
		autoHideDelay : 2000
	});

});
function platform() {
	$("#tcdPageCodehide").hide();
	$("#inviteMoney").hide();
	$("#LoanInvestorMoney").hide();
	$("#Accountname").hide();
	basic();// 基本信息
	account();// 账户信息
	bankCard();// 银行卡信息
	weChat();// 微信信息
	selectuserRiskService();
}

function selectuserRiskService() {
	var id = Request.id;
	document.getElementById("userid").value = Request.id;
	_userId = Request.id;
	$
			.ajax({
				type : 'POST',
				url : '../user/user_list/selectuserRiskService',
				data : {
					"userId" : id
				},
				dataType : "json",
				success : function(data) {
					document.getElementById("riskScore").value = data.data.riskScore;
					if (data.data.riskScore == null) {
						document.getElementById("riskScore").value = "未评估";
						document.getElementById("riskRating").value = "未评估";
					} else if (data.data.riskScore >= 20
							&& data.data.riskScore <= 40) {
						document.getElementById("riskRating").value = "保守型";
					} else if (data.data.riskScore >= 41
							&& data.data.riskScore <= 60) {
						document.getElementById("riskRating").value = "稳健型";
					} else if (data.data.riskScore >= 61
							&& data.data.riskScore <= 100) {
						document.getElementById("riskRating").value = "积极型";
					}
				},
				error : function() {
				}
			})
}

/**
 * 基本信息
 */
function basic() {

	// URL参数
	var id = Request.id;
	document.getElementById("userid").value = Request.id;
	_userId = Request.id;
	$
			.ajax({
				type : 'POST',
				url : '../user/user_list/selectUserById',
				data : {
					"userId" : id,
					"isCustomer":1
				},
				dataType : "json",
				success : function(data) {

					if (data.userType == 'PERSON') {
						data.userType = '个人会员';
					}
					if (data.userType == 'ENTERPRISE') {
						data.userType = '企业会员';
					}

					if (data.gender == 'UNKNOWN') {
						data.gender = '未知';
					}
					if (data.gender == 'MAN') {
						data.gender = '男';
					}
					if (data.gender == 'WOMAN') {
						data.gender = '女';
					}

					if (data.certType == 'ID') {
						data.certType = '身份证';
					}
					if (data.certType == 'VISA') {
						data.certType = '签证';
					}
					if (data.certType == 'Passport') {
						data.certType = '护照';
					}
					if (data.certType == 'MilitaryCard') {
						data.certType = '军人证';
					}
					if (data.certType == 'OnewayPermit') {
						data.certType = '港澳通行证';
					}

					registmoble = data.registerMobile;
					document.getElementById("realName").value = data.realName;
					document.getElementById("gender").value = data.gender;
					document.getElementById("registerMobile").value = data.registerMobile;
					document.getElementById("userType").value = data.userType;
					document.getElementById("group").value = data.group;
					document.getElementById("certType").value = data.certType;
					document.getElementById("certNo").value = data.certNo;
					document.getElementById("grade").value = data.grade;
					document.getElementById("agent").value = data.agent;
					document.getElementById("from").value = data.from;
					document.getElementById("inviteCode").value = data.inviteCode;
					document.getElementById("referrer1").value = data.referrer1;
					document.getElementById("referrer2").value = data.referrer2;
					document.getElementById("deviceId").value = data.deviceId;
					document.getElementById("createTime").value = data.createTime;
					document.getElementById("lastLoginTime").value = data.lastLoginTime;
					document.getElementById("nickName").value = data.nickName;

					document.getElementById("nickName").value = data.nickName;
					document.getElementById("mobile").value = data.registerMobile;
					// document.getElementById("country").value = data.country;
					// document.getElementById("provinces").value =
					// data.provinces;
					// document.getElementById("citys").value = data.citys;
				},
				error : function() {

				}
			})
}
/**
 * 账户信息
 */
function account() {
	// URL参数
	var id = Request.id;
	document.getElementById("userid").value = Request.id;
	_userId = Request.id;
	$
			.ajax({
				type : 'POST',
				url : '../user/user_list/selectAccountInfo',
				data : {
					"userId" : id
				},
				dataType : 'json',
				success : function(data) {
					if (data.resCode == 0) {
						if (data.data.balance != null)
							$("#balance").val(data.data.balance.amount + "元");
						if (data.data.freezeAmount != null)
							$("#freezeAmount").val(
									data.data.freezeAmount.amount + "元");
						if (data.data.redMoneyBalance != null)
							$("#redMoneyBalance").val(
									data.data.redMoneyBalance.amount + "元");
					} else {
						alert(data.msg);
					}
				}
			})
}
/**
 * 银行卡信息
 */
function bankCard() {
	var id = document.getElementById("userid").value;
	$.ajax({
		type : 'POST',
		url : '../user/user_list/selectBindBackById',
		data : {
			"userId" : id
		},
		dataType : 'json',
		success : function(data) {
			if (data.bindState == 'UNBIND') {
				document.getElementById("bankName").value = "";
				document.getElementById("bankMobile").value = "";
				document.getElementById("bankCardNo").value = "";
				document.getElementById("realNames").value = data.realName;
				document.getElementById("bindTime").value = "";
			} else {
				document.getElementById("bankName").value = data.bankName;
				document.getElementById("bankMobile").value = data.bankMobile;
				document.getElementById("bankCardNo").value = data.bankCardNo;
				document.getElementById("realNames").value = data.realName;
				document.getElementById("bindTime").value = data.bindTime;
			}
		},
		error : function() {
		}
	})
}

/**
 * 微信信息
 */
function weChat() {
	var id = document.getElementById("userid").value;
	$.ajax({
		type : 'POST',
		url : '../user/user_list/selectWxUserById',
		data : {
			"userId" : id
		},
		dataType : 'json',
		success : function(data) {
			document.getElementById("mobile").value = data.mobile;
			document.getElementById("country").value = data.country;
			document.getElementById("provinces").value = data.province;
			document.getElementById("citys").value = data.city;
			document.getElementById("nickname").value = data.nickname;

		},
		error : function() {
		}
	})

}

/**
 * 邀请好友
 */
function inviteFriends() {

	$("#tcdPageCodehide").show();
	$("#inviteMoney").show();
	$("#LoanInvestorMoney").hide();
	$("#Accountname").hide();
	var srhData3 = {
		"referrer1" : _userId,
		"inviter" : _userId,
		"pageNo" : "1",
		"pageSize" : "10"
	};
	lncitePeopliList("../user/user_list/lnvitePeopleList", srhData3); // 邀请好友列表
	myPage2();

	var srhData1 = {
		"referrer1" : _userId
	};
	selectReferrerByUserId("../user/user_list/selectReferrerByUserId", srhData1); // 邀请人数

	var srhData2 = {
		"inviter" : _userId
	};
	selectInviterByUserId("../user/user_list/selectInviterByUserId", srhData2); // 奖励金额
}

function lncitePeopliList(tdUrl, tdData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tdData,
		dataType : "json",
		success : function(data) {
			var _table = $('#tab_2'), tableBodyHtml = '';

			_pages = data.pages;

			$.each(data.list, function(k, v) {
				// 获取数据
				if (v.registerMobile != null) {
					var t_registerMobile = v.registerMobile
				} else {
					v.registerMobile = ""
				}

				if (v.inviteAmount.amount != null) {
					var t_inviteAmount = v.inviteAmount.amount
				} else {
					v.inviteAmount.amount = 0
				}

				if (v.createTime != null) {
					var t_createTime = v.createTime
				} else {
					v.createTime = ""
				}

				if (v.nickName != null) {
					var t_nickName = v.nickName
				} else {
					v.nickName = ""
				}

				// 输出HTML元素
				tableBodyHtml += '<tr>';
				tableBodyHtml += '<td>' + t_registerMobile + '</td>';
				tableBodyHtml += '<td>' + t_nickName + '</td>';
				tableBodyHtml += '<td>' + t_inviteAmount + '</td>';
				tableBodyHtml += '<td>' + t_createTime + '</td>';

				tableBodyHtml += '</tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
		},
		async : false
	});
}

// 邀请好友分页
var myPage2 = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
				"pageNo" : p,
				"pageSize" : "10",
				"referrer1" : _userId,
				"inviter" : _userId
			};
			lncitePeopliList("../user/user_list/lnvitePeopleList", srhData);
		}
	});
};
/**
 * 奖励金额
 */
function selectInviterByUserId(tdUrl, tdData) {
	$.ajax({
		type : 'POST',
		url : tdUrl,
		data : tdData,
		dataType : 'json',
		success : function(data) {
			document.getElementById("rewardMoney").value = data.amount + "元";
		},
		error : function(data) {
			alert("出错了1");
		}
	})
}

/**
 * 邀请人数
 * 
 * @param tdUrl
 * @param tdData
 */
function selectReferrerByUserId(tdUrl, tdData) {
	$.ajax({
		type : 'POST',
		url : tdUrl,
		data : tdData,
		dataType : 'json',
		success : function(data) {
			document.getElementById("invitePeopleAcount").value = data;
		},
		error : function(data) {
			alert("出错了2");
		}
	})
}

/**
 * 出借记录
 */

function call() {

	$("#tcdPageCodehide").show();
	$("#LoanInvestorMoney").show();
	$("#inviteMoney").hide();
	$("#Accountname").hide();
	var srhData = {
		"investorUserCode" : _userId,
		"pageNo" : "1",
		"pageSize" : "10"
	};
	userLoanInvestor("../user/user_list/selectLoanInvestor", srhData);
	myPage();
	var srhData2 = {
		"userId" : _userId
	};
	selectInvestorTotalAmountByUserId(
			"../user/user_list/selectInvestorTotalAmountByUserId", srhData2); // 出借总金额

}

/**
 * 出借总金额
 */
function selectInvestorTotalAmountByUserId(tdUrl, tdData) {
	$
			.ajax({
				type : 'POST',
				url : tdUrl,
				data : tdData,
				dataType : 'json',
				success : function(data) {
					document.getElementById("totalMoney").value = data.totalInvest.amount
							+ "元";
				},
				error : function(data) {
					alert("出错了");
				}
			})
}

// 用户出借页面
function userLoanInvestor(tdUrl, tdData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tdData,
				dataType : "json",
				success : function(data) {
					var _table = $('#tab_5'), tableBodyHtml = '';

					_pages = data.pages;

					$
							.each(
									data.list,
									function(k, v) {
										// 获取数据
										var t_loanCode = v.loanCode, t_title = v.title, t_yearRate = v.yearRate, t_projectCycle = v.projectCycle, t_investTime = v.investTime, t_redMoneyAmount = v.redMoneyAmount.amount, t_ActualPay = v.actualPay.amount, t_investAmount = v.investAmount.amount, t_status = v.status;
										t_investorStatus = v.investorStatus;
										if (t_investorStatus == "FAIL") {
											t_investorStatus = "失败";
										}
										if (t_investorStatus == "SUCCESS") {
											t_investorStatus = "成功";
										}

										if (t_status == 'INIT') {
											t_status = '初始状态';
										}
										if (t_status == 'OPEN') {
											t_status = '开标状态但未到出借时间';
										}
										if (t_status == 'OPENED') {
											t_status = '开标状态可以出借';
										}
										if (t_status == 'FULL') {
											t_status = '满标';
										}
										if (t_status == 'DISBURSE') {
											t_status = '已放款';
										}
										if (t_status == 'STOP') {
											t_status = '停止募集';
										}
										if (t_status == 'COMPLETED') {
											t_status = '已完成';
										}
										if (t_status == 'KILLED') {
											t_status = '已流标';
										}

										// 输出HTML元素
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + t_title
												+ '</td>';
										tableBodyHtml += '<td>' + t_yearRate
												+ '%</td>';
										tableBodyHtml += '<td>'
												+ t_projectCycle + '天</td>';
										tableBodyHtml += '<td>' + t_investTime
												+ '</td>';
										tableBodyHtml += '<td>'
												+ t_redMoneyAmount + '</td>';
										tableBodyHtml += '<td>' + t_ActualPay
												+ '</td>';
										tableBodyHtml += '<td>'
												+ t_investAmount + '</td>';
										tableBodyHtml += '<td>'
												+ t_investorStatus + '</td>';// 出借状态
										tableBodyHtml += '<td>' + t_status
												+ '</td>';
										tableBodyHtml += '</tr>';

									});

					_table.find("tbody").html(tableBodyHtml);
				},
				async : false
			});
}

// 用户出借 分页
var myPage = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
				"pageNo" : p,
				"pageSize" : "10",
				"investorUserCode" : _userId
			};
			userLoanInvestor("../user/user_list/selectLoanInvestor", srhData);
		}
	});
};

/**
 * 红包记录页面
 */

function redmoneyrecord() {

	$("#tcdPageCodehide").show();
	$("#LoanInvestorMoney").hide();
	$("#inviteMoney").hide();
	$("#Accountname").hide();
	var srhData = {
		"userId" : _userId,
		"pageNo" : "1",
		"pageSize" : "10"
	};
	redmoneyrecord2("../capital/redmoneyrecord/getPersonalRedMoneyLog", srhData);
	myPage3();

}

function redmoneyrecord2(tdUrl, tdata) {

	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tdata,
		dataType : "json",
		success : function(data) {
			var _table = $('#tab_6'), tableBodyHtml = '';

			_pages = data.data.pages;

			$.each(data.data.list, function(k, v) {
				var _inAndOutType = handlerInAndOutType(v.source);
				tableBodyHtml += '<tr>';
				tableBodyHtml += '<td>' + v.createTime + '</td>';// 时间
				tableBodyHtml += '<td>' + _inAndOutType['positiveSymbol']
						+ v.amount.amount + '</td>';// 金额
				tableBodyHtml += '<td>' + _inAndOutType['type'] + '</td>';// 收入类型

				tableBodyHtml += '</tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
		},
		async : false
	});

}

// 红包记录分页
var myPage3 = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
				"pageNo" : p,
				"pageSize" : "10",
				"userId" : _userId
			};
			redmoneyrecord2("../capital/redmoneyrecord/getRedMoneyLog",
					srhData);
		}
	});
};

/**
 * 提现页面
 */

function withdrawals() {

	$("#tcdPageCodehide").show();
	$("#LoanInvestorMoney").hide();
	$("#inviteMoney").hide();
	$("#Accountname").show();
	var srhData = {
		"payeeId" : _userId,
		"flowDirction" : "-1",
		"pageNo" : "1",
		"pageSize" : "10"
	};
	withdrawals2("../pay/transactionlog/getLog2", srhData);
	myPage4();
}

function withdrawals2(tdUrl, tdata) {

	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tdata,
		dataType : "json",
		success : function(data) {
			var _table = $('#tab_4'), tableBodyHtml = '';
			_pages = data.pages;
			$.each(data.list, function(k, v) {
				t_requestTime = v.requestTime;// 申请时间
				t_amount = v.amount.amount;// 金额
				t_sequenceId = v.sequenceId;// 外部订单号
				t_orderStatus = v.orderStatus;// 状态
				t_responseMessage = v.responseMessage;// 失败原因
				t_realName = v.payeeUsername;// 账户名(收款方userName)
				t_bankName = v.bankName;// 银行名称
				t_cardType = v.cardType;// 账户类型：1=对私DebitCard、2=对公Company
				t_cardNo = v.cardNo;// 银行卡号
				if (v.cardType == "Company") {
					t_cardType = "企业";
				} else {
					t_cardType = "个人";
				}
				document.getElementById("accountRealName").value = t_realName;// 真实姓名
				document.getElementById("accountBankName").value = t_bankName;// 支行名称
				document.getElementById("accountType").value = t_cardType;// 个人类型
				document.getElementById("cardNo").value = t_cardNo;// 银行卡号
				switch (v.orderStatus) {
				case 0:
					t_orderStatus = "未开始";
					break;
				case 2:
					t_orderStatus = "处理中";
					break;
				case 1:
					t_orderStatus = "交易成功";
					break;
				case -1:
					t_orderStatus = "交易失败";
					break;
				case -2:
					t_orderStatus = "已撤销";
					break;
				case -3:
					t_orderStatus = "已过期";
					break;
				case -4:
					t_orderStatus = "订单不存在";
					break;
				case -5:
					t_orderStatus = "重复订单号";
					break;
				case -6:
					t_orderStatus = "订单未支付";
					break;
				case -7:
					t_orderStatus = "已退款";
					break;
				default:
					d_orderStatus = v.orderStatus;
					break;
				}
				;
				if (v.orderStatus == "-1") {
					t_responseMessage = v.responseMessage;
				} else {
					t_responseMessage = "";
				}
				tableBodyHtml += '<tr>';

				tableBodyHtml += '<td>' + t_requestTime + '</td>';//

				tableBodyHtml += '<td>' + t_amount + '</td>';//
				tableBodyHtml += '<td>' + t_sequenceId + '</td>';//
				tableBodyHtml += '<td>' + t_orderStatus + '</td>';
				tableBodyHtml += '<td>' + t_responseMessage + '</td>';
				tableBodyHtml += '</tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
		},
		async : false
	});

}
// 提现记录分页
var myPage4 = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
				"pageNo" : p,
				"pageSize" : "10",
				"payeeId" : _userId,
				"flowDirction" : "-1"
			};
			withdrawals2("../pay/transactionlog/getLog2", srhData);
		}
	});
};

/**
 * 充值记录页面
 */

function rechargeRecord() {

	$("#tcdPageCodehide").show();
	$("#LoanInvestorMoney").hide();
	$("#inviteMoney").hide();
	$("#Accountname").hide();
	var srhData = {
		"payerId" : _userId,
		"flowDirction" : "1",
		"pageNo" : "1",
		"pageSize" : "10"
	};
	rechargeRecord2("../pay/transactionlog/getLog2", srhData);
	myPage5();
}

function rechargeRecord2(tdUrl, tdata) {

	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tdata,
		dataType : "json",
		success : function(data) {
			var _table = $('#tab_3'), tableBodyHtml = '';
			_pages = data.pages;
			$.each(data.list, function(k, v) {
				t_requestTime = v.requestTime;// 充值时间
				t_amount = v.amount.amount;// 金额
				t_sequenceId = v.sequenceId;// 外部订单号
				t_payType = v.business;// 支付类型
				t_orderStatus = v.orderStatus;// 状态
				t_requestId = v.requestId;// 外部支付请求id

				if (v.business == "Quick") {
					t_payType = "快捷";
				} else if (v.business == "NetBank") {
					t_payType = "网银";
				}
				;
				switch (v.orderStatus) {
				case 0:
					t_orderStatus = "未开始";
					break;
				case 2:
					t_orderStatus = "处理中";
					break;
				case 1:
					t_orderStatus = "交易成功";
					break;
				case -1:
					t_orderStatus = "交易失败";
					break;
				case -2:
					t_orderStatus = "已撤销";
					break;
				case -3:
					t_orderStatus = "已过期";
					break;
				case -4:
					t_orderStatus = "订单不存在";
					break;
				case -5:
					t_orderStatus = "重复订单号";
					break;
				case -6:
					t_orderStatus = "订单未支付";
					break;
				case -7:
					t_orderStatus = "已退款";
					break;
				default:
					d_orderStatus = v.orderStatus;
					break;

				}
				;
				tableBodyHtml += '<tr>';
				tableBodyHtml += '<td>' + t_requestTime + '</td>';//
				tableBodyHtml += '<td>' + t_amount + '</td>';//
				tableBodyHtml += '<td>' + t_sequenceId + '</td>';//
				tableBodyHtml += '<td>' + t_payType + '</td>';
				tableBodyHtml += '<td>' + t_orderStatus + '</td>';
				if (v.orderStatus == 2)
					tableBodyHtml += '<td><button  type="button" id="'
							+ t_requestId + '"onclick="operation(this)">'
							+ '补单' + '</button></td>';
				else
					tableBodyHtml += '<td></td>';
				tableBodyHtml += '</tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
		},
		async : false
	});

}

// 充值记录分页
var myPage5 = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
				"pageNo" : p,
				"pageSize" : "10",
				"payerId" : _userId,
				"flowDirction" : "1"
			};
			rechargeRecord2("../pay/transactionlog/getLog2", srhData);
		}
	});
};
// 更换手机号模态窗口
var regaccFun = function(obj) {
	$("#modal_id").val(obj);

	$('#myModal').modal('show');
	document.getElementById("modal_orginMobile").value = registmoble;// 原手机号
};

var _modalSave = $("#modal_save");
_modalSave
		.click(function() {
			if (!_modalFm.validationEngine('validate')) {
				return false;
			} else {
				_modalSave.attr("disabled", "disabled").addClass("btn-white");

				orginMobile = registmoble, newMobile = $('#modal_newMobile')
						.val(), newMobile2 = $('#modal_newMobile2').val();
				var tData = {
					"orginMobile" : orginMobile,
					"newMobile" : newMobile,
					"newMobile2" : newMobile2
				};
				$
						.ajax({
							type : "POST",
							url : "/user/userunlock/changeMobile",
							data : tData,
							dataType : "json",
							async : false,
							success : function(data) {
								alert(data.result);
								$('#myModal').modal('hide');
								_modalSave.removeAttr("disabled").removeClass(
										"btn-white");
								$('#modal_form')[0].reset();

								window.location.href = "../customer/customer_user_detailedinformation.html?id="
										+ _userId + "";
							},
							error : function() {
								alert("更改失败");
								$('#myModal').modal('hide');
								$('#modal_form')[0].reset();
							}
						});
			}
		});

// 解锁手机号模态窗口
var regaccFun2 = function(obj) {
	$("#modal_id").val(obj);
	$('#myModal2').modal('show');
	document.getElementById("modal_orginMobile2").value = registmoble;// 原手机号
};
var _modalSave2 = $("#modal_save2");
_modalSave2
		.click(function() {
			if (!_modalFm2.validationEngine('validate')) {
				return false;
			} else {
				_modalSave2.attr("disabled", "disabled").addClass("btn-white");
				modalNewMobile2 = registmoble;
				var tData = {
					"mobile" : modalNewMobile2,
				};
				$
						.ajax({
							type : "POST",
							url : "/user/userunlock/smsUnBindCard",
							data : tData,
							dataType : "json",
							async : false,
							success : function(data) {
								alert(data.result);
								$('#myModal').modal('hide');
								_modalSave.removeAttr("disabled").removeClass(
										"btn-white");
								$('#modal_form2')[0].reset();
								window.location.href = "../customer/customer_user_detailedinformation.html?id="
										+ _userId + "";
							},
							error : function() {
								alert("解锁失败");
								$('#myModal').modal('hide');
								$('#modal_form2')[0].reset();
							}
						});
			}
		});

// 补单click事件
function operation(obj) {
	var updateid = obj.id;
	var tData = {
		"requestId" : updateid
	};
	$.ajax({
		type : "POST",
		url : "/pay/transactionlog/revise",
		data : tData,
		dataType : "json",
		traditional : true,
		success : function(data) {
			alert(data.resultCodeMsg);
			window.location.reload();
		}
	});
}

// 解绑身份事件 按钮删除
function regaccFun3(obj) {
	$('#myModal3').modal('show');
}

var _modalSave3 = $("#modal_save3");
_modalSave3
		.click(function() {
			if (!_modalFm3.validationEngine('validate')) {
				return false;
			} else {
				var certNo = $("#cer").val();
				var tData = {
					"userId" : _userId,
					"certNo" : certNo
				};
				$
						.ajax({
							type : "POST",
							url : "/user/user_list/cancelRealNameAuth",
							data : tData,
							dataType : "json",
							async : false,
							success : function(data) {
								if (data.msg == 'success') {
									alert("解绑成功");
								} else {
									alert(data.msg);
								}
								$('#myModal3').modal('hide');
								$('#modal_form3')[0].reset();
								window.location.href = "../customer/customer_user_detailedinformation.html?id="
										+ _userId + "";
							},
							error : function() {
								alert("解锁失败");
								$('#myModal3').modal('hide');
								$('#modal_form3')[0].reset();
							}
						});
			}
		});

// 处理红包收支类型 支出与收入 金额 + -
function handlerInAndOutType(source) {
	var _inAndOut = {};
	// 过期和使用为支出，其它为收入
	if ("expire".toUpperCase() == source.toUpperCase()
			|| "consume".toUpperCase() == source.toUpperCase()) {
		_inAndOut['type'] = '支出';
		_inAndOut['positiveSymbol'] = '-';
	} else {
		_inAndOut['type'] = '收入';
		_inAndOut['positiveSymbol'] = '+';
	}
	return _inAndOut;

}

/**
 * 绑定记录页面
 */
function bindRecord() {
	$("#tcdPageCodehide").show();
	$("#LoanInvestorMoney").hide();
	$("#inviteMoney").hide();
	$("#Accountname").hide();
	var srhData = {
		"userId" : _userId,
		"pageNo" : "1",
		"pageSize" : "10"
	};
	tableBindRecord("/user/user_list/queryBindLog", srhData);
	myPage7();
}

/**
 * 绑定记录页面
 */
function tableBindRecord(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	var a = data.data.businessObject.total;
    	if(a!=0){
        var _table = $('#tab_7'),
        tableBodyHtml = '';
        _pages= data.data.businessObject.pages;
        var _data = data.data.businessObject;
        
        $.each(_data.list, function (k, v) {
          // 获取数据
        	var remark = v.remark,
        	sourceTag = v.sourceTag,
        	optTime = v.optTime;
        	var optType = v.optType;
        	var optTypeTxt = "";
        	if(v.optType == 'BINDPHONE'){
        		optTypeTxt = '绑定手机';
        	}else if(v.optType == 'WXUNBIND'){
        		optTypeTxt = '绑定微信';
        	}else if(v.optType == 'UNBINDPHONE'){
        		optTypeTxt = '解绑手机';
        	}else if(v.optType == 'WXBIND'){
        		optTypeTxt = '解绑微信';
        	}else if(v.optType == 'REGISTER'){
        		optTypeTxt = '注册';
        	}
        	;
          // 输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + remark + '</td>';
          tableBodyHtml += '<td>' + optTypeTxt + '</td>';
          tableBodyHtml += '<td>' + '否' + '</td>';
          tableBodyHtml += '<td>' + sourceTag + '</td>';
          tableBodyHtml += '<td>' + optTime + '</td>';
          tableBodyHtml += '</tr>';
        });
        $("#tcdPagehide").show();
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
    	}else{
    		 var _table = $('#table'),
    	     tableBodyHtml = '';
    		 tableBodyHtml +='<tr class="no-records-found">';
    		 tableBodyHtml +='<td colspan="5">没有找到匹配的记录</td>';
    		 tableBodyHtml += '</tr>';
    		 _table.find("tbody").html(tableBodyHtml);
    		 $("#tcdPagehide").hide();
    	}
      },
	  async : false
    });
  }

/**
 * 绑定记录页面 分页
 */
var myPage7 = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
				"pageNo" : p,
				"pageSize" : "10",
				"userId" : _userId,
			};
			tableBindRecord("/user/user_list/queryBindLog", srhData);
		}
	});
};