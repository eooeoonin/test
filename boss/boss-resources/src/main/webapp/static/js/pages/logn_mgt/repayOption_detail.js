function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};
var changeType = 0;
var borrowCode =  getUrlParam("borrowId");
var nextPhase =  getUrlParam("nextPhase");
var totalPhase =  getUrlParam("totalPhase");
var oaFlowCode;
var userId;
$(function () {
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#borrowerInfo');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 3000
		});
	
	$.ajax({
		type : "POST",
		url : "/borrow/repayOption/getDetail",
		data : {"borrowId" : borrowCode},
		success : function(data) {
			if(data.resCode == 0){
				var _data = data.data[0];
				var _data1= data.data[1];
				var _data2 = data.data[2];
				oaFlowCode = _data.oaFlowCode;
				$("#borrowTitle").val(_data.borrowTitle);
				$("#borrowUserName").val(_data.borrowUserName);
				$("#borrowRate").val(_data.borrowRate/100 + "%");
				if(_data.termType == "MONTH"){
					$("#borrowTerm").val(_data.borrowTerm);//回显借款期限
				}else if(_data.termType == "DAY"){
					$("#borrowTerm").val(_data.borrowTerm);//回显借款期限
				}else{
					$("#borrowTerm").val("——");//回显借款期限
				}
				$("#borrowAmount").val(_data.borrowAmount.amount);
				$("#currentPhase").val(nextPhase +'/'+totalPhase);
				$("#lastReleaseTime").val(_data.lastReleaseTime);
				$("#lastRepayTime").val(_data.lastRepayTime);
				$("#guaranteeUserName").val(_data1.guaranteeUserName);
				$("#guaranteeIdCard").val(_data1.guaranteeIdCard);
				$("#guaranteeUserName2").val(_data2.guaranteeUserName);
				$("#guaranteeIdCard2").val(_data2.guaranteeIdCard);
			
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
});	
$("#changeBtn").click(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"userType":"GUARANTEE"
		
	};
	tableFun2("/borrow/repayOption/borrowerList", srhData);
	// tableFun2("/project/p_add_pro/borrowUserList", srhData);
	$("#myModal2").modal("show");
	myPage2();
});


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
											if(v.userCertCode != null && v.userCertCode != ""){
												html += "<td>" + v.userCertCode + "</td>";
											}else{
												html += "<td>——</td>";
											}
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

//分页2
function myPage2() {
	var $tcdPage = $("#tcdPageCode2");
	$tcdPage.createPage({
		pageCount : _pages2,
		current : 1,
		backFn : function(p) {
			var legalPersonName = $("#legalPersonName").val();
			var srhData4 = {
				"pageNo" : p,
				"pageSize" : "10",
				"legalPersonName" : legalPersonName,
				"userType":"GUARANTEE"
			};

			tableFun2("/borrow/repayOption/borrowerList", srhData4);
			// tableFun2("/project/p_add_pro/borrowUserList", srhData4);
		}
	});
}
// 担保人查询条件查询
$("#srhSmtBtn2").click(function() {
	var legalPersonName = $("#legalPersonName").val();
	var srhData2 = {
		"pageNo" : "1",
		"pageSize" : "10",
		"legalPersonName" : legalPersonName,
		"userType":"GUARANTEE"
	};
	tableFun2("/borrow/repayOption/borrowerList", srhData2);
	myPage2();
});

// 选择选中的担保人
$("#choose2").click(function() {
	var obj = $("#table2").find("input[type='radio']:checked");
	var releaseUserId = obj.parents("tr").find("td:nth-child(2)").text();// 找到位置
	userId = obj.parents("tr").find("td:nth-child(5)").text();// 找到位置
	$("#releaseUserId").val(releaseUserId);
	$("#userCertCode").val(obj.parents("tr").find("td:nth-child(6)").text());
	$('#myModal2').modal('hide');
});


$("#subBtn").click(function() {
	
	  if (!$("#borrowerInfo").validationEngine('validate')) {
		    return false;
		  }
	
	if(changeType == 0){
		window.history.back(-1); 
	}else if(changeType == 1){
		$.ajax({
			type : "POST",
			url : "/borrow/repayOption/addOption",
			data : {"changeType" : changeType,"borrowCode":borrowCode,"borrowName":$("#borrowTitle").val(),"oaFlowCode":oaFlowCode,"mark":$("#mark").val()},
			success : function(data) {
				if(data.resCode == 0){
					bootbox.confirm("操作成功", function(result) {
						location.href = "/borrow/repayOption_list.html";
					});
				}else{
					bootbox.alert("操作失败");
				}
			},
			async : false
		});
	}else if(changeType == 2){
		$.ajax({
			type : "POST",
			url : "/borrow/repayOption/addOption",
			data : {"changeType" : changeType,"borrowId":borrowCode,"borrowName":$("#borrowTitle").val(),"otherRepayUserId":userId,"otherRepayUserName":$("#releaseUserId").val(),"oaFlowCode":oaFlowCode,"certId":$("#userCertCode").val(),"mark":$("#mark").val()},
			success : function(data) {
				if(data.resCode == 0){
					bootbox.confirm("操作成功", function(result) {
						location.href = "/borrow/repayOption_list.html";
					});
				}else{
					bootbox.alert("操作失败");
				}
			},
			async : false
		});
	}
});

$('#changeType').change(function(){  
	var p = $(this).children('option:selected').val();//这就是selected的值  
	changeType = p;
	if(p == 1){
		$("#changeBtn").attr("disabled", "disabled");
	}else if(p == 2){
		$("#changeBtn").attr("disabled", false);
	}
});