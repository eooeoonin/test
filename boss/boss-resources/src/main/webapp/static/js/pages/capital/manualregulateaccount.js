var _pages;
var v_user_code;
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
	
//	getSystemUserId();
	
	
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "10"
				
	};
	tableFun("/capital/manualregulateaccount/getAdjustmentRecord",pageData);
	myPage();
});


$("#addButton").click(function () {
	window.location.href = "manualregulateaccount_add.html";
});

/*
 * 获取systemUserId
 * */
/*function getSystemUserId() {
	$.ajax({
		type : "POST",
		url : "/capital/manualregulateaccount/getSystemUserId",
		dataType : "json",
		success : function(data) {
			v_user_code = data.result;
		},error:function(data){
			console.log(data);
		},
		async : false
	});
}*/



function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if(data.list !== ""){
						var _table = $('#table'), tableBodyHtml = '';
						_pages = data.pages;
						$.each(data.list,function(k, v) {
							  var d_id = v.id,
									d_userType = v.userType,//用户类型
									d_registerMobile = v.mobile,//手机号
									d_realName = v.realName,//真实姓名
									d_adjustmentTradeTime = v.tradeTime,
									d_adjustmentType = v.adjustmentType,//调账类型
									d_adjustmentAmount = v.amount.amount,
									d_operatorName = v.editedBy,//操作人
									d_result = v.accountStatus,//结果状态   INIT("INIT", "初始"),
								    //PROCESSING("PROCESSING", "处理中"),
								    //SUCCESS("SUCCESS", "成功"),
								    //FAIL("FAIL", "失败"),;
									d_mark = v.mark;
							  		var resu;
//							  		switch(d_result){
//									case "INIT":
//										resu="初始";
//										break;
//									case "PROCESSING":
//										resu="处理中";
//										break;
//									case "SUCCESS":
//										resu="成功";
//										break;
//									case "FAIL":
//										resu="失败";
//										break;
//									default:
//										resu="未知";
//									break;
//						    		};
									var userTypeCn = "";
									if("PERSON" === d_userType)
										userTypeCn = "个人会员";
									else if("ENTERPRISE" === d_userType)
										userTypeCn = "企业会员";
									var d_adjustmentTypeCn = "";
									if("ADD" === d_adjustmentType)
										d_adjustmentTypeCn = "手工加帐";
									else
										d_adjustmentTypeCn = "手工减帐";
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + d_id+ '</td>';
//									tableBodyHtml += '<td>' + v_user_code + '</td>';
									tableBodyHtml += '<td>' + userTypeCn+ '</td>';
									tableBodyHtml += '<td>' + d_registerMobile+ '</td>';
									tableBodyHtml += '<td>' + d_realName+ '</td>';
									tableBodyHtml += '<td>' + d_adjustmentTradeTime+ '</td>';
									tableBodyHtml += '<td>' + d_adjustmentTypeCn+ '</td>';
									tableBodyHtml += '<td>' + d_adjustmentAmount+ '</td>';
									tableBodyHtml += '<td>' + d_operatorName+ '</td>';
									tableBodyHtml += '<td>' + d_result + '</td>';
//									tableBodyHtml += '<td width="20%">' + d_mark+ '</td>';
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
						replaceFun(_table);
					}else{
			    		
			    		 tableBodyHtml +='<tr class="no-records-found">';
			    		 tableBodyHtml +='<td colspan="9" align="center">没有找到匹配的记录</td>';
			    		 tableBodyHtml += '</tr>';
			    		 _table.find("tbody").html(tableBodyHtml);
			    	}
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
		  var v_userType = $("#userType").val(),
			v_registerMobile = $("#registerMobile").val(),
			v_realName = $("#realName").val(),
			v_adjustmentType = $("#adjustmentType").val(),				
			v_operatorName = $("#operatorName").val(),
		    v_startTime = $("#start").val(),
			v_endTime = $("#end").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "10",
			"userType" : v_userType,
			"registerMobile" : v_registerMobile,
			"realName" : v_realName,
			"adjustmentType" : v_adjustmentType,
			"editedBy" : v_operatorName
			};
			if(v_startTime!== ""){
				pageData.startTime = v_startTime;
			}
			if(v_endTime!== ""){
				pageData.endTime = v_endTime;
			}
		tableFun("/capital/manualregulateaccount/getAdjustmentRecord", pageData);		  
	  }
	});
};

/**
 * 点击查询触发条件
 * d_userType = v.userType,//用户类型
 * d_registerMobile = v.registerMobile,//手机号
 * d_realName = v.realName,//真实姓名
 * d_adjustmentType = v.adjustmentType,//调账类型				
 * d_operatorName = v.operatorName,//操作人
 *  
 * 开始时间
 * 结束时间
 * 
 * 
 * */
var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	var v_userType = $("#userType").val(),
		v_registerMobile = $("#registerMobile").val(),
		v_realName = $("#realName").val(),
		v_adjustmentType = $("#adjustmentType").val(),				
		v_operatorName = $("#operatorName").val();
		v_startTime = $("#start").val();
		v_endTime = $("#end").val();
		var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"userType" : v_userType,
		"mobile" : v_registerMobile,
		"realName" : v_realName,
		"adjustmentType" : v_adjustmentType,
		"editedBy" : v_operatorName
		};
		if(v_startTime!== ""){
			srhData.startTime = v_startTime;
		}
		if(v_endTime!== ""){
			srhData.endTime = v_endTime;
		}
		
		tableFun("/capital/manualregulateaccount/getAdjustmentRecord",srhData);
		myPage();
	
});




