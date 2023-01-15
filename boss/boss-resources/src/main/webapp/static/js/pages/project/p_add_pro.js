/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages;
var _pages2;
var interestStartDate;
var borrowTerm;
var termType;
var btermType;
$(function() {

	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green"
	});

	var ue = UE.getEditor('desc');
//	var ue1 = UE.getEditor('purpose');
//	var ue2 = UE.getEditor('risk');
	/**
	 * 校验
	 */
	_modalFm1 = $('#loanForm');
	_modalFm1.validationEngine('attach', {
		maxErrorsPerField : 1,
		autoHidePrompt : true,
		autoHideDelay : 2000
	});

	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var day = myDate.getDate();
	var hour = myDate.getHours();
	var bb = year + '-' + month + '-' + day + ' ' + myDate.getHours() + ':'
			+ myDate.getMinutes() + ':' + myDate.getSeconds();
	var aa = year + '-' + month + '-' + day;
	$("#openTime").val(bb);
	// $("#interestStartDate").val(aa);

	$("#redMoneyScale").attr("disabled", "disabled");
	$("#redMoneyScale").val(0);
	/***************************************************************************
	 * 功能说明：表格相关操作 参数说明： 创建人：李波涛 时间：2016-07-29
	 **************************************************************************/
	var start = {
		elem : "#openTime",
		format : "YYYY-MM-DD hh:mm",
		istime : true,
		choose: function (datas) {
			datas  = datas.substring(0,10);
			var day1 = datas.replace(/\s+/g, "");
			var d1 = new Date(day1);
			d1.setDate(d1.getDate() + 20);
			end.max = formatDate(d1, "yyyy-MM-dd");
			end.min = datas; //开始日选好后，重置结束日的最小日期
	        end.start = datas //将结束日的初始值设定为开始日
	    }

	}
	var end = {
			elem : "#interestStartDate",
			istoday : false,
			format : "YYYY-MM-DD ",
			choose : function(datas) {
				if ($("#interestStartDate").val() != null
						&& $("#interestEndDate").val() != null) {
					var day1 = $("#interestStartDate").val().replace(/\s+/g, "");
					var d1 = new Date(day1);
					var d2 = new Date(day1);
					if (borrowTerm != "" && termType != "") {
						if (termType == "m") {
							d1.setMonth(d1.getMonth() + parseInt(borrowTerm));
							d1.setDate(d1.getDate() - 1);
							var diff = d1 - d2;
							$("#borrowCycle").text(
									Math.round(diff / (1000 * 60 * 60 * 24)) + 1
											+ "天(" + borrowTerm + "月)");
							$("#interestEndDate").val(formatDate(d1, "yyyy-MM-dd"));
						} else if (termType == "d") {
							d1.setDate(d1.getDate() + parseInt(borrowTerm));
							d1.setDate(d1.getDate() - 1);
							$("#interestEndDate").val(getLocalTime(d1));
							$("#borrowCycle").text(borrowTerm + "日");
							$("#interestEndDate").val(formatDate(d1, "yyyy-MM-dd"));
						}
					}
				}
			}
		};
	
	
	laydate(start);
	laydate(end);

	operatorName = $.cookie("username");
	$('#editedBy').val(operatorName);
	$("#p").text(operatorName);

	/***************************************************************************
	 * 功能说明：表格相关操作 参数说明： 创建人：李波涛 时间：2016-07-29
	 **************************************************************************/
	var _table = $('#table');
	_table.bootstrapTable();

	$("#useRedPacket").iCheck('uncheck').val("0");
	$("#earlyRepayment").iCheck('uncheck').val("0");
	$("#addInterestCardFlag").iCheck('uncheck').val("0");
	$("#cashCardFlag").iCheck('uncheck').val("0");

	// 下一步提交
	$("#addPro").click(function() {
		window.location.href = "p_pro_file.html";
	});

	$("input[name='available']").on('ifChecked', function(event) {
		var a = $("input[name='available']:checked").val();
		if (a == 0) {
			$("#changeBtn").attr("disabled", "disabled");
		} else {
			$("#changeBtn").attr("disabled", false);
		}
	});

	// 图片预览
	$(".fancybox").fancybox({
		openEffect : "none",
		closeEffect : "none"
	});

	$("#changeBtn").click(function() {
		var srhData = {
			"pageNo" : "1",
			"pageSize" : "10"
		};
		tableFun2("/borrow/l_add_loan/borrowerList", srhData);
		// tableFun2("/project/p_add_pro/borrowUserList", srhData);
		$("#myModal2").modal("show");
		myPage2();
	});

	// 借款人查询条件查询
	$("#srhSmtBtn").click(
			function() {
				var v_tIdSrh = $("#tIdSrh").val(), v_tNameSrh = $("#tNameSrh")
						.val(), v_srhName = $("#srhName").find(
						"option:selected").val();
				var srhData = {
					"pageNo" : "1",
					"pageSize" : "10",
					"borrowType" : v_srhName,
					"borrowUserName" : v_tNameSrh,
					"borrowTitle" : v_tIdSrh
				// borrowUserCode
				// borrowUserName
				};
				tableFun("/project/p_add_pro/borrowList", srhData);
				myPage();
			});

	// 担保人查询条件查询
	$("#srhSmtBtn2").click(function() {
		var v_enterpriseName = $("#enterpriseName").val();
		var srhData2 = {
			"pageNo" : "1",
			"pageSize" : "10",
			"enterpriseName" : v_enterpriseName
		};
		tableFun2("/borrow/l_add_loan/borrowerList", srhData2);
		// tableFun2("/project/p_add_pro/borrowUserList", srhData2);
		myPage2();
	});

	$("#loanType").change(function() {
		var loanType = $("#loanType").val();
		if (loanType == 'VIP') {
			$("#investInvetCode").removeAttr("disabled");
		} else {
			$("#investInvetCode").attr("disabled", "disabled");
			$("#investInvetCode").val("");
		}
	});

	$("#loanType").change(function() {
		var loanType = $("#loanType").val();
		if (loanType == 'ADDINTEREST') {
			$("#floatRate").removeAttr("disabled");
			$("#floatRate").val("");
		} else {
			$("#floatRate").attr("disabled", "disabled");
			$("#floatRate").val("");
		}
	});

	$("#earlyRepayment").change(function() {
		var earlyRepayment = $("#earlyRepayment").val();
		if (earlyRepayment == '0') {
			$("#repayDesc").attr("disabled", "disabled");
			$("#repayDesc").val("");

		} else {
			$("#repayDesc").removeAttr("disabled");
			$("#repayDesc").val("");
		}
	});

	// $("#amount").change(function () {
	// var amount = $("#amount").val();
	// var borrowSurplusAmount = $("#borrowSurplusAmount").text();
	// if (parseFloat(amount) > parseFloat(borrowSurplusAmount)) {
	// alert("金额错误");
	// $("#amount").val("");
	// }
	// });

	$("#yearRate").change(function() {
		var yearRate = $("#yearRate").val();
		var borrowRate = $("#borrowRate").text();
		if (parseFloat(yearRate) > parseFloat(borrowRate)) {
			alert("利率错误");
			$("#yearRate").val("");
		}
	})

	// 打勾事件
	$("#useRedPacket").on('ifUnchecked', function() {
		$("#useRedPacket").val(0);
		$("#redMoneyScale").attr("disabled", "disabled");
		$("#redMoneyScale").val(0);
	});

	$("#useRedPacket").on('ifChecked', function() {
		$("#useRedPacket").val(1);
		$("#redMoneyScale").removeAttr("disabled");

	});
	// 打勾事件
	$("#addInterestCardFlag").on('ifUnchecked', function() {
		$("#addInterestCardFlag").val(0);
	});

	$("#addInterestCardFlag").on('ifChecked', function() {
		$("#addInterestCardFlag").val(1);

	});
	// 打勾事件
	$("#cashCardFlag").on('ifUnchecked', function() {
		$("#cashCardFlag").val(0);
	});

	$("#cashCardFlag").on('ifChecked', function() {
		$("#cashCardFlag").val(1);
	});
	$("#addLoan")
			.click(
					function() {
						bootbox.confirm("你确定要创建吗?", function(result) {
							if(result){
								var sealId = $("#sealImageId").val();
								if ("" == sealId ||null == sealId ){
									  bootbox.alert("借款人印模不能为空");
								  }else{
								if ($("#earlyRepayment").prop("checked") == true) {
									var rr = $("#repayDesc").val()
									if (rr == "" || rr == null) {
										alert("提前还款描述需要填写!");
										return false;
									}
								}
								var time1 = $("#openTime").val();
								var serial = $("#loanForm").serialize();

								var starinsert = $("#interestStartDate").val();
								var endinsert = $("#interestEndDate").val();
								var a1 = starinsert.split("-");
								var b1 = endinsert.split("-");

								var S_Date = new Date(Date.parse(a1));
								var E_Date = new Date(Date.parse(b1));

								if (S_Date > E_Date) {
									alert("起息日不能大于结息日");
									return false;
								}
								var validate_maxInvest = $("#maxInvest").val();

								var validate_amount = $("#amount").val();
								if (parseInt(validate_maxInvest) > parseInt(validate_amount)) {
									alert("最大可投金额不能大于项目总额");
									return false;
								}

								if (!_modalFm1.validationEngine('validate')) {
									return false;
								}
								if ($("#borrowerCode").val() == ""
										|| $("#borrowerCode").val() == null) {
									alert("请选择资产");
									return false;
								}
								var para = $("#loanForm").serialize()
										+ "&useRedPacket="
										+ $("#useRedPacket").prop("checked")
										+ "&earlyRepayment="
										+ $("#earlyRepayment").prop("checked")
										+ "&addInterestCardFlag="
										+ $("#addInterestCardFlag").prop("checked")
										+ "&cashCardFlag="
										+ $("#cashCardFlag").prop("checked")
										+ "&termType="
										+ btermType
										+ "&borrowTerm="
										+borrowTerm;
								$
										.ajax({
											type : "POST",
											url : "/project/p_add_pro/add",
											data : para,
											success : function(data) {
												if (data != null && data != "") {
													if (data.resCode == 0) {
														bootbox
																.alert(
																		"操作成功",
																		function() {
																			/*window.location.href = "p_add_pro_file.html?loanCode="
																					+ data.data.id;*/
																			window.location.reload();
																		});
													} else {
														bootbox.alert(data.msg);
													}
												}
											},
											error : function(data) {
												alert("出错了");
											},
											async : false
										});
								  }
							}
						});
					});

	// 查询合同列表
	$.ajax({
		type : "POST",
		url : "/project/p_add_pro/getContractList",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					tableBodyHtml = '';
					$.each(data.data, function(k, v) {
						// 获取数据
						var d_name = v.name, d_id = v.id;

						// 输出HTML元素
						tableBodyHtml += '<option value="' + d_id + '">'
								+ d_name + '</option>';
					});
					$("#contractNo").html(tableBodyHtml);
				} else {
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});

	// 项目类型列表
	$.ajax({
		type : "POST",
		url : "/project/p_add_pro/getLoanTypeList",
		success : function(data) {
			if (data != null && data != "") {
				tableBodyHtml = '';
				$.each(data, function(k, v) {
					// 获取数据
					var d_id = k, d_name = v;
					// 输出HTML元素
					tableBodyHtml += '<option value="' + d_id + '">' + d_name
							+ '</option>';
				});
				$("#loanType").html(tableBodyHtml);
			}
		},
		async : false
	});

	// 用户权限组列表
	$.ajax({
		type : "POST",
		url : "/project/p_add_pro/getInvestGroupCode",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data = data.data.list;
					tableBodyHtml = '';
					$.each(_data, function(k, v) {
						// 获取数据
						var codeKey = v.codeKey, codeValue = v.codeValue;
						// 输出HTML元素
						if (codeValue == '普通用户') {
							tableBodyHtml += '<option selected value="'
									+ codeKey + '">' + codeValue + '</option>';
						} else {
							tableBodyHtml += '<option value="' + codeKey + '">'
									+ codeValue + '</option>';
						}
					});
					$("#investGroupCode").html(tableBodyHtml);

				} else
					bootbox.alert(data.msg);
			}
		},
		async : false
	});

	// 选择选中的借款人
	$("#choose")
			.click(
					function() {
						var obj = $("#table").find(
								"input[type='radio']:checked");
						var borrowName = obj.parents("tr").find(
								"td:nth-child(2)").text();
						var borrowerCode = obj.parents("tr").find(
								"td:nth-child(9)").text();
						var borrowUserName =  obj.parents("tr").find(
						"td:nth-child(3)").text();
						var borrowRate = obj.parents("tr").find(
								"td:nth-child(4)").text();
						// var borrowCycle =
						// obj.parents("tr").find("td:nth-child(5)").text();
						var borrowAmount = obj.parents("tr").find(
								"td:nth-child(5)").text();
						var borrowType = obj.parents("tr").find(
								"td:nth-child(7)").text();
						// var borrowSurplusAmount =
						// obj.parents("tr").find("td:nth-child(6)").text();
						var repayTypeName = obj.parents("tr").find(
								"td:nth-child(10)").text();
						var repayType = obj.parents("tr").find(
								"td:nth-child(8)").text();
						
						var releaseUserId = obj.parents("tr").find(
						"td:nth-child(12)").text();
						var releaseUserCard = obj.parents("tr").find(
						"td:nth-child(13)").text();
						// var interestEndDate =
						// obj.parents("tr").find("td:nth-child(14)").text();
						// var lastReleaseTime =
						// obj.parents("tr").find("td:nth-child(15)").text();
						var guaranteeUserId = obj.parents("tr").find(
								"td:nth-child(11)").text();

						ue.setContent($("#" + borrowerCode + "p").val());
//						ue1.setContent($("#" + borrowerCode + "s").val());
//						ue2.setContent($("#" + borrowerCode + "r").val());
						btermType = $("#" + borrowerCode + "tt").val();
						$("#wantReleaseTime").text($("#" + borrowerCode + "want").val());
						if ($("#" + borrowerCode + "tt").val() == "MONTH") {
							borrowTerm = $("#" + borrowerCode + "bt").val();
							termType = "m";
						} else if ($("#" + borrowerCode + "tt").val() == "DAY") {
							borrowTerm = $("#" + borrowerCode + "bt").val();
							termType = "d";
						} else {
							borrowTerm = "";
							termType = "";
						}

						$("#isCheck").val(releaseUserCard);
						$("#releaseUserId").val(releaseUserId);
						$("#releaseUserId2").val(releaseUserId);
						$("#borrowName").val(borrowName);
						$("#borrowerCode").val(borrowerCode);
						$("#borrowRate").text(borrowRate);
						$("#amount").val(borrowAmount);
						// $("#yearRate").val(borrowRate);
						// $("#borrowCycle").text(borrowCycle);
						$("#borrowAmount").text(borrowAmount + "元");
						// $("#borrowSurplusAmount").text(borrowSurplusAmount +
						// "元");
						$("#repayTypeName").text(repayType);
						$("#borrowType").text(borrowType);
						$("#borrowType").val(borrowType);
						$("#repayType").val(repayTypeName);
						_interestEndDate = moment(interestEndDate).format(
								"YYYY-MM-DD");
						// lastReleaseTime =
						// moment(lastReleaseTime).format("YYYY-MM-DD");
						$("#interestEndDate").val(_interestEndDate);
						// $("#interestEndDateTransfer").html(_interestEndDate);
						// $("#interestStartDate").val(lastReleaseTime);
						/*
						 * $("#myModal").hide(); $(".modal-backdrop").remove();
						 */
						$('#myModal').modal('hide');

						var srhData = {
							"borrowId" : borrowerCode
						};
						tableFun3("/signet/moulage/picture", srhData);

						// here
						// var borrowUserCode = $("#"+borrowerCode+"cc").val();
						// $("#borrowUserCode").val(borrowUserCode);
						//       
						// if (borrowUserCode != "" && borrowUserCode != "null")
						// {
						// $("#checkMoulagePic").removeAttr("disabled");
						// }else{
						// $("#checkMoulagePic").attr("disabled","disabled");
						// }
						// if (guaranteeUserId == "" || guaranteeUserId ==
						// "null"){
						// $("#checkSignetPic").attr("disabled","disabled");
						// }else{
						// $("#checkSignetPic").removeAttr("disabled");
						// }

					});

	// 选择选中的担保人
	$("#choose2").click(function() {
		var obj = $("#table2").find("input[type='radio']:checked");
		var releaseUserId = obj.parents("tr").find("td:nth-child(5)").text();// 找到位置
		$("#releaseUserId").val(releaseUserId);
		$("#releaseUserId2").val(releaseUserId);

		$('#myModal2').modal('hide');
	});
});

function tableFun(tdUrl, tbData) {
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
										/* html += "<td>" + v.borrowCycle + "</td>"; */
										// html += "<td>" + v.balance.amount +
										// "</td>";
										html += "<td>" + v.borrowAmount.amount
												+ "</td>";
										html += "<td>" + v.createTime + "</td>";
										/* html += "<td>" + v.reviewDate + "</td>"; */
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
}
function tableFun2(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				async : false,
				success : function(data) {
					var html = "";
					if (data.list != null) {
						_pages2 = data.pages;
						$
								.each(
										data.list,
										function(k, v) {
											html += "<tr>";
											html += "<td><label class='radio-inline i-checks'><input style='margin-left:10px' type='radio' name='radio' class='sub_radbox'></label></td>";
											if (v.roleCode == "PERSON") {
												html += "<td>"
														+ v.legalPersonName
														+ "</td>";
											} else {
												html += "<td>"
														+ v.enterpriseName
														+ "</td>";
											}
											var v_userType = null;
											if (v.userType == "PERSON") {
												v_userType = "个人";
											} else if (v.userType == "ENTERPRISE") {
												v_userType = "企业";
											} else {
												v_userType = v.userType;
											}
											html += "<td>" + v_userType + ""
													+ v.roleName + "</td>";
											html += "<td>" + v.legalPersonName
													+ "</td>";
											html += "<td>" + v.userId + "</td>";
											html += "</tr>";
										});
						$("#table2").find("tbody").html(html);
					} else {
						var _table = $('#table2'), tableBodyHtml = '';
						tableBodyHtml += '<tr class="no-records-found">';
						tableBodyHtml += '<td colspan="9" align="center">没有找到匹配的记录</td>';
						tableBodyHtml += '</tr>';
						_table.find("tbody").html(tableBodyHtml);
						$("#tcdPageCode2").hide();
					}
				},
				error : function(data) {
					alert("出错了");
				}
			});
}
// 印模图片
function tableFun3(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		async : false,
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data0 = data.data[0];
					var _data1 = data.data[1];
					if("" == _data0.id || null == _data0.id){
						alert("借款人印模不存在");
						window.location.reload();
					}else{
						$("#sealImageId").val(_data0.id);
						_loadImages(_data0);
						_loadImages1(_data1);
					}
				} else {
					alert("查询印模图片错误:"+data.msg);
				}
			}
		},
		async : false,
		error : function(data) {
			alert("查询印模图片错误:"+data.msg);
		}
	});
}
// 分页
function myPage() {
	var $tcdPage = $("#tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var v_tIdSrh = $("#tIdSrh").val(), v_tNameSrh = $("#tNameSrh")
					.val(), v_srhName = $("#srhName").find("option:selected")
					.val();
			var srhData3 = {
				"pageNo" : p,
				"pageSize" : "10"
			};
			// $("#currPage").val(p);

			/*
			 * , "borrowType": v_srhName, "borrowUserName": v_tNameSrh,
			 * "borrowTitle": v_tIdSrh
			 */

			tableFun("/project/p_add_pro/borrowList", srhData3);
		}
	});
}
// 分页2
function myPage2() {
	var $tcdPage = $("#tcdPageCode2");
	$tcdPage.createPage({
		pageCount : _pages2,
		current : 1,
		backFn : function(p) {
			var v_enterpriseName = $("#enterpriseName").val();
			var srhData4 = {
				"pageNo" : p,
				"pageSize" : "10",
				"enterpriseName" : v_enterpriseName
			};

			tableFun2("/borrow/l_add_loan/borrowerList", srhData4);
			// tableFun2("/project/p_add_pro/borrowUserList", srhData4);
		}
	});
}

$("#queryBorrow").click(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10"
	};
	tableFun("/project/p_add_pro/borrowList", srhData);
	myPage();
	$("#myModal").modal("show");
});

// 计算天数差的函数，通用
function DateDiff(sDate1, sDate2) { // sDate1和sDate2是2017-9-25格式
	var aDate, oDate1, oDate2, iDays
	if (navigator.userAgent.indexOf('Firefox') >= 0) {
		aDate = sDate1.split("-")
		oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]) // 转换为9-25-2017格式
		aDate = sDate2.split("-")
		oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0])
		iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) // 把相差的毫秒数转换为天数
	} else {
		aDate = sDate1.split("-")
		oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) // 转换为9-25-2017格式
		aDate = sDate2.split("-")
		oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
		iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) // 把相差的毫秒数转换为天数
	}
	return iDays
}

function getLocalTime(nS) {
	return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,
			' ');
}
function formatDate(date, format) {
	if (!date)
		return;
	if (!format)
		format = "yyyy-MM-dd";
	switch (typeof date) {
	case "string":
		date = new Date(date.replace(/-/, "/"));
		break;
	case "number":
		date = new Date(date);
		break;
	}
	if (!date instanceof Date)
		return;
	var dict = {
		"yyyy" : date.getFullYear(),
		"M" : date.getMonth() + 1,
		"d" : date.getDate(),
		"H" : date.getHours(),
		"m" : date.getMinutes(),
		"s" : date.getSeconds(),
		"MM" : ("" + (date.getMonth() + 101)).substr(1),
		"dd" : ("" + (date.getDate() + 100)).substr(1),
		"HH" : ("" + (date.getHours() + 100)).substr(1),
		"mm" : ("" + (date.getMinutes() + 100)).substr(1),
		"ss" : ("" + (date.getSeconds() + 100)).substr(1)
	};
	return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
		return dict[arguments[0]];
	});
}

Date.prototype.toLocaleString = function() {
	return this.getFullYear() + "/" + (this.getMonth() + 1) + "/"
			+ this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes()
			+ ":" + this.getSeconds();
};

function _loadImages(data) {
	if (typeof (data) != "undefined") {
		if (null != data.savePath && '' != data.savePath) {
			var moulagePicturefileUrl = domainUrl + data.savePath;
			var _cdImgMoulagePicture = $("#moulagePictureShow");
			_cdImgMoulagePicture.attr("href", moulagePicturefileUrl);
			_cdImgMoulagePicture.find("img").attr("src", moulagePicturefileUrl);
			_cdImgMoulagePicture.find("input").val(data.savePath);
		}
	}
}

function _loadImages1(data) {
	if (typeof (data) != "undefined") {
		if (null != data.savePath && '' != data.savePath) {
			var moulagePicturefileUrl = domainUrl + data.savePath;
			var _cdImgMoulagePicture = $("#guaMoulagePictureShow");
			_cdImgMoulagePicture.attr("href", moulagePicturefileUrl);
			_cdImgMoulagePicture.find("img").attr("src", moulagePicturefileUrl);
			_cdImgMoulagePicture.find("input").val(data.savePath);
		}
	}
}

// // 借款人印模查看
// $("#checkMoulagePic")
// .click(
// function() {
// var userId = $("#borrowUserCode").val();
// window.location.href = "../signet/moulage_edit.html?type1=check&userId="
// + userId;
//
// });
// // 担保人印模查看
// $("#checkSignetPic")
// .click(
// function() {
// var userId2 = $("#releaseUserId2").val();
// window.location.href = "../signet/moulage_edit.html?type1=check&userId="
// + userId2;
//
// });
