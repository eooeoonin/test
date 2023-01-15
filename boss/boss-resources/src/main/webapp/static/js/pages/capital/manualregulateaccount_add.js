var _pages; 
$(function() {
//	getSystemUserId();
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"registerMobile" : "",
			"realName" : ""
		};
	tableFun("/capital/manualregulateaccount/getUserInfo", pageData);
	myPage();
	
	_modalFm =  $('#modal_form');
	_modalFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
});


/*
 * 获取systemUserId
 * */
//function getSystemUserId() {
//	$.ajax({
//		type : "POST",
//		url : "/capital/manualregulateaccount/getSystemUserId",
//		dataType : "json",
//		success : function(data) {
//			//系统用户ID
//			document.getElementById("systemUserId").value = data.result;
//		},
//		async : false
//	});
//}

/*
 * 查询
 */
function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					var _table = $('#table'), tableBodyHtml = '';
					_pages = data.pages;
					$.each(data.list,function(k, v) {
						var d_id = v.id, d_userType = v.userType, d_realName = v.realName, d_registerMobile = v.registerMobile, d_certType = v.certType, d_certNo = v.certNo, d_createTime = v.createTime;
						var userTypeCn = "", certTypeCn = "";
										// d_userType="person";
							            switch (d_userType) {
										case "PERSON":
											userTypeCn = "个人会员";
											break;
										case "ENTERPRISE":
											userTypeCn = "企业会员";
											break;
										case "ADMIN":
											userTypeCn = "系统管理员";
											break;
										default:
										}
										switch (d_certType) {
										case "ID":
											certTypeCn = "身份证";
											break;
										case "VISA":
											certTypeCn = "签证";
											break;
										case "Passport":
											certTypeCn = "护照";
											break;
										case "MilitaryCard":
											certTypeCn = "军人证";
											break;
										case "OnewayPermit":
											certTypeCn = "港澳通行证";
											break;
										default:
										}
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + userTypeCn
												+ '</td>';
										tableBodyHtml += '<td>' + d_realName
												+ '</td>';
										tableBodyHtml += '<td>'
												+ d_registerMobile + '</td>';
										tableBodyHtml += '<td>' + certTypeCn
												+ '</td>';
										tableBodyHtml += '<td>' + d_certNo
												+ '</td>';
										tableBodyHtml += '<td>' + d_createTime
												+ '</td>';
										tableBodyHtml += '<td><a href="javaScript:" name="'
												+ d_id
												+ '"onclick="regaccFun(this.name)" class="btn btn-primary">手工转帐</a></td>';
										tableBodyHtml += '</tr>';
									});
					_table.find("tbody").html(tableBodyHtml);
				},
				error:function(data){
					alert("取得用户信息异常");
				},
				async : false
			});
		}

// 分页
var myPage = function() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var v_registerMobile = $("#registerMobile").val(), v_realName = $(
					"#realName").val(), v_userType = $("#userType").val();
			var pageData = {
				"pageNo" : p,
				"pageSize" : "10",
				"registerMobile" : v_registerMobile,
				"realName" : v_realName,
				"userType" : "PERSON"
			};
			tableFun("/capital/manualregulateaccount/getUserInfo", pageData);
		}
	});
};

/*
 * select查询操作
 */
var _srhBtn = $("#srhBtn");
_srhBtn.click(function() {
	var v_registerMobile = $("#registerMobile").val(), v_realName = $("#realName").val();
	var v_userId = $("#userId").val();
	var pageData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"registerMobile" : v_registerMobile,
		"realName" : v_realName,
		"id" : v_userId
	};
	tableFun("/capital/manualregulateaccount/getUserInfo", pageData);
	myPage();
});

var regaccFun = function(obj) {
	$("#modal_id").val(obj);
	//$("#modal_registerMobile").val(registerMobile);
	$('#myModal').modal('show');
};

var _modalSave = $("#modal_save");
	  _modalSave.click(function() {
			if (!_modalFm.validationEngine('validate')) {
			    return false;
			}else{
				_modalSave.attr("disabled","disabled").addClass("btn-white");
				var userId = $("#modal_id").val(), 
				registerMobile = $('#modal_registerMobile').val(),
				adjustmentType = $('#modal_adjustmentType  option:selected').val(),
				adjustmentAmount = $('#modal_adjustmentAmount').val(),
				mark = $('#modal_mark').val();
				operatorName = $.cookie("username");
				systemId = $("#systemUserId").val();
				var tData = {
					"userId" : userId,
					"mobile" : registerMobile,
					"adjustmentType" : adjustmentType,
					"amount" : adjustmentAmount,
					"mark" : mark
				};
				$.ajax({
					type : "POST",
					url : "/capital/manualregulateaccount/adjustUserAccount",
					data : tData,
					dataType : "json",
					async : false,
					success : function(data) {
						if(data.resCode == 0){
							alert(data.msg);
							$('#myModal').modal('hide');
							_modalSave.removeAttr("disabled").removeClass("btn-white");
							$('#modal_form')[0].reset();
						}else{
							alert(data.msg);
							$('#myModal').modal('hide');
							_modalSave.removeAttr("disabled").removeClass("btn-white");
							$('#modal_form')[0].reset();
						}
					},
					error : function() {
						alert("转账失败");
						$('#myModal').modal('hide');
						$('#modal_form')[0].reset();
					}
				});
			}		
		});