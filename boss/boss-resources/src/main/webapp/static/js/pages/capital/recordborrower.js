/**
 * 
 */
var _pages;
$(function() {

	var pageData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"userType" : "ENTERPRISE"
				
	};
	tableFun("/capital/recordborrower/getAdjustmentRecord",pageData);
	myPage();
});


$("#addButton").click(function () {
	window.location.href = "manualregulateaccount_add.html"; 
});

//if(data.data.list != ""){}
function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					var _table = $('#table'), tableBodyHtml = '';
					_pages = data.pages;
					$.each(
							data.list,
							function(k, v) {
								var d_id = v.id,
								d_usercode = v.userCode,
								d_userType = v.userType,//用户类型
								d_registerMobile = v.mobile,//手机号
								d_realName = v.realName,//真实姓名
								d_adjustmentTradeTime = v.adjustmentTradeTime,
								d_adjustmentType = v.adjustmentType,//调账类型
								d_adjustmentAmount = v.adjustmentAmount.amount,
								d_operatorName = v.operatorName,//操作人
								d_companyName = v.companyName,
								d_mark = v.mark;
								var userTypeCn = "";
								if("PERSON" == d_userType)
									userTypeCn = "个人会员";
								else if("ENTERPRISE" == d_userType)
									userTypeCn = "企业会员";
								var d_adjustmentTypeCn = "";
								if("ADD" == d_adjustmentType)
									d_adjustmentTypeCn = "手工充值";
								else
									d_adjustmentTypeCn = "手工减帐";
								tableBodyHtml += '<tr>';
								tableBodyHtml += '<td>' + d_id+ '</td>';
								tableBodyHtml += '<td>' + userTypeCn+ '</td>';
								tableBodyHtml += '<td>' + d_registerMobile+ '</td>';
								tableBodyHtml += '<td>' + d_realName+ '</td>';
								
								tableBodyHtml += '<td>' + d_adjustmentTradeTime+ '</td>';
								tableBodyHtml += '<td>' + d_adjustmentTypeCn+ '</td>';
								tableBodyHtml += '<td>' + d_adjustmentAmount+ '</td>';
								tableBodyHtml += '<td>' + d_operatorName+ '</td>';
								tableBodyHtml += '<td><a href="recordborrower_info.html?id=' + d_id +'" class="msgEdit">查看</a></td>';
								
								tableBodyHtml += '</tr>';
							});
					_table.find("tbody").html(tableBodyHtml);

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
		  //var pageData=$("#form").serialize();
		  var v_registerMobile = $("#registerMobile").val(),
			v_realName = $("#realName").val(),
//			v_adjustmentType = $("#adjustmentType").val(),	
			v_start = $("#before").val(),
		    v_end = $("#after").val(),
			v_operatorName = $("#operatorName").val();
		  if(v_start==""&&v_end==""){
			  var pageData = {
						"pageNo" : p,
						"pageSize" : "10",
						"userType" : "ENTERPRISE",
						"mobile" : v_registerMobile,
						"realName" : v_realName,
						"operatorName" : v_operatorName
						};
			}else{
				var pageData = {
						"pageNo" : p,
						"pageSize" : "10",
						"userType" : "ENTERPRISE",
						"mobile" : v_registerMobile,
						"realName" : v_realName,
						"startTime" : v_start,
						"endTime": v_end,
						"operatorName" : v_operatorName
						};
			}
		  	
		  
			
		tableFun("/capital/recordborrower/getAdjustmentRecord", pageData);		  
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
	var v_registerMobile = $("#registerMobile").val(),
		v_realName = $("#realName").val(),
//		v_adjustmentType = $("#adjustmentType").val(),
		v_start = $("#before").val(),
	    v_end = $("#after").val(),
		v_operatorName = $("#operatorName").val();
		if(v_start==""&&v_end==""){
			var srhData = {
					"pageNo" : "1",
					"pageSize" : "10",
					"userType" :"ENTERPRISE",
					"mobile" : v_registerMobile,
					"realName" : v_realName,
					"operatorName" : v_operatorName
					};	
		}else{
			var srhData = {
					"pageNo" : "1",
					"pageSize" : "10",
					"userType" :"ENTERPRISE",
					"mobile" : v_registerMobile,
					"realName" : v_realName,
					"startTime" : v_start,
					"endTime": v_end,
					"operatorName" : v_operatorName
					};
		}
		
		tableFun("/capital/recordborrower/getAdjustmentRecord",srhData);
		myPage();
	
});



/***
 *功能说明：时间插件
 *参数说明：
 *创建人：李波涛
 *时间：2016-07-15
 ***/
var start = {
elem: "#before", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
  end.min = datas;
  end.start = datas
}
};
var end = {
elem: "#after", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
  start.max = datas
}
};
try {
laydate(start);
laydate(end);
} catch (e) {}
