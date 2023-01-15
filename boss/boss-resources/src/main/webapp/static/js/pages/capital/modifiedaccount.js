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
	_modalFm =  $('#modal_form');
	_modalFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	//$("#userCode").val(systemUserId);
	//getSystemUserId();
	var v_user_code = $("#userId").val(),
	v_transCode = $("#subTransCode").val(),
    v_subTransCode = $("#subTransCode").val();
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"userId":v_user_code,
			"transCode":v_transCode,
			"subTransCode":v_subTransCode,
	};
	tableFun("/capital/modifiedaccount/getSystemAccountLog",pageData);
	myPage();
	
	  $("#userCode").change(function () {
		    var userCode = $("#userCode").val();
		    if (userCode != 'SYS_GENERATE_000') {
		    	$("#subTransCode").attr("disabled", "disabled");
		        $("#subTransCode").val("TRANSFER_SYSTEM_ADD");
		    } else {
		    	$("#subTransCode").removeAttr("disabled");
		    }
	 });
	  
	  $("#modal_userCode").change(function () {
		    var userCode = $("#modal_userCode").val();
		    getBalance();
//		    if (userCode != 'SYS_GENERATE_000') {
//		    	$("#modal_subTransCode").attr("disabled", "disabled");
//		        $("#modal_subTransCode").val("TRANSFER_SYSTEM_ADD");
//		    } else {
//		    	$("#modal_subTransCode").removeAttr("disabled");
//		    	getBalance();
//		    }
	 });
});


function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if(data.resCode == 0){
						if(data.data.list.length > 0) {
							var _table = $('#table'), tableBodyHtml = '';
							_pages = data.data.pages;
							$.each(
									data.data.list,
									function(k, v) {
										var d_id = v.userId,
										d_realName = "";
										if(d_id == "SYS_GENERATE_000")
											d_realName = "系统户";
										if(d_id == "system_0002")
											d_realName = "客户发红包账户";
										if(d_id == "system_0003")
											d_realName = "运营发红包账户";
										if(d_id == "system_0005")
											d_realName = "系统券营销账户";
										if(d_id == "system_0006")
											d_realName = "系统手续费账户";
										var d_time = v.modifyTime,
										d_subTransCode = v.subTransCodeCn;
										d_direction = v.actionType,
										d_in = 0,
										d_out = 0;
										if(d_direction == "IN")
											d_in = v.transAmount.amount;
										if(d_direction == "OUT")
											d_out = v.transAmount.amount;
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + d_id+ '</td>';
										tableBodyHtml += '<td>' + d_realName+ '</td>';
										tableBodyHtml += '<td>' + d_time + '</td>';
										tableBodyHtml += '<td>' + d_subTransCode+ '</td>';
										tableBodyHtml += '<td>' + d_in+ '</td>';
										tableBodyHtml += '<td>' + d_out+ '</td>';
										tableBodyHtml += '</tr>';
									});
							_table.find("tbody").html(tableBodyHtml);
							$("#tcdPageCode").show();
							
						}
						else{
							var _table = $('#table'),
				    	     tableBodyHtml = '';
				    		 tableBodyHtml +='<tr class="no-records-found">';
				    		 tableBodyHtml +='<td colspan="9" align="center">没有找到匹配的记录</td>';
				    		 tableBodyHtml += '</tr>';
				    		 _table.find("tbody").html(tableBodyHtml);
				    		 $("#tcdPageCode").hide();
						}
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
		  var v_userCode = $("#userId").val(),
		    v_transCode = $("#subTransCode").val(),
		    v_subTransCode = $("#subTransCode").val(),
		    v_startTime = $("#start").val(),
			v_endTime = $("#end").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "10",
			"userId" : v_userCode,
			"transCode":v_transCode,
			"subTransCode":v_subTransCode
			};
			if(v_startTime!= ""){
				pageData.startTime = v_startTime;
			}
			if(v_endTime!= ""){
				pageData.endTime = v_endTime;
			}
		tableFun("/capital/modifiedaccount/getSystemAccountLog", pageData);		  
	  }
	});
};


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	
	var v_userCode = $("#userId").val(),
	v_transCode = $("#subTransCode").val(),
    v_subTransCode = $("#subTransCode").val(),
	v_startTime = $("#start").val();
	v_endTime = $("#end").val();
	if(v_startTime != null && v_endTime == null) {
		alert("请输入结束时间");
		return false;
	}
	if(v_startTime == null && v_endTime != null) {
		alert("请输入开始时间");
		return false;
	}	
	if(v_userCode != "SYS_GENERATE_000") {
		v_transCode = "TRANSFER_SYSTEM_ADD";
		v_subTransCode = "TRANSFER_SYSTEM_ADD";
	}
		
	var srhData = {
	"pageNo" : "1",
	"pageSize" : "10",
	"userId" : v_userCode,
	"transCode":v_transCode,
	"subTransCode":v_subTransCode
	};
		if(v_startTime!= ""){
			srhData.startTime = v_startTime;
		}
		if(v_endTime!= ""){
			srhData.endTime = v_endTime;
		}
		
		tableFun("/capital/modifiedaccount/getSystemAccountLog",srhData);
		myPage();
	
});


$("#addButton").click(function () {
	//$("#modal_id").val($("#userCode").val());
	document.getElementById("modal_subTransCode").selectedIndex = 0;  
	$("#modal_transAmount").val("");
	$.ajax({
		type : "POST",
		url : "/capital/modifiedaccount/getBalance",
		data : {"userId":$("#modal_userCode").val()},
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.resCode == 0){
				$('#modal_balance').val(data.data.balance.amount);
				$('#myModal').modal('show');
			}
			else{
				alert(data.msg);
				$('#myModal').modal('show');
			}
		},
		error : function() {
			alert("查询余额出错");
			$('#myModal').modal('show');
		}
	});
	
});

function getBalance() {
	$.ajax({
		type : "POST",
		url : "/capital/modifiedaccount/getBalance",
		data : {"userId":$("#modal_userCode").val()},
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.resCode == 0){
				$('#modal_balance').val(data.data.balance.amount);
				$('#myModal').modal('show');
			}
			else{
				alert(data.msg);
				$('#myModal').modal('show');
			}
		},
		error : function() {
			alert("查询余额出错");
			$('#myModal').modal('show');
		}
	});
}

var _modalSave = $("#modal_save");
_modalSave
		.click(function() {
			if (!_modalFm.validationEngine('validate')) {
			    return false;
			  }
			else{
				_modalSave.attr("disabled","disabled").addClass("btn-white");
				var modal_userCode = $("#modal_userCode").val();
				$("#modal_transCode").val($("#modal_subTransCode").val());
				var tData,url;
//				if(modal_userCode == "SYS_GENERATE_000") {
					url = "/capital/modifiedaccount/modifiedaccount";
					tData = $("#modal_form").serialize();
//				}else {
//					userIdIn = $('#modal_userCode').val(),
//					transAmount = $('#modal_transAmount').val(),	
//					url = "/capital/modifiedaccount/transfer";
//					tData = {
//							"userIdIn" : userIdIn,
//							"transCode" : "TRANSFER_SYSTEM_ADD",
//							"transAmount":transAmount
//						};
//				}
				
				$.ajax({
					type : "POST",
					url : url,
					data : tData,
					dataType : "json",
					async : false,
					success : function(data) {
						alert(data.result);
						$('#myModal').modal('hide');
						_modalSave.removeAttr("disabled").removeClass("btn-white");
						window.location.reload();
					},
					error : function() {
						alert("调账失败");
						$('#myModal').modal('hide');
						window.location.reload();
					}
				});
			}		
		});

