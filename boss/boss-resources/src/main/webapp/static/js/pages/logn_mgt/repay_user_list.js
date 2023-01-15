var _pages;
var v_user_code;
$(function() {
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10"
	};
	tableFun("/borrow/repay_user_list/repayUserList", srhData);
	myPage();
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
					if(null!=data&&""!=data){
						var _table = $('#table'), tableBodyHtml = '';
						_pages = data.data.pages;
						$.each(data.data.list,function(k, v) {
							    var	d_borrowName = v.borrowName,//借款名称
							    d_id = v.id,//id
							    d_enterpriseName = v.enterpriseName,//企业名称
							    d_oaFlowCode = v.oaFlowCode,//oa编号
							    d_otherRepayUserName = v.otherRepayUserName;//三方代偿用户名称
							    
									tableBodyHtml += '<tr>';
									if("" != d_borrowName && null != d_borrowName){
										tableBodyHtml += '<td>' + d_borrowName+ '</td>';
									}else{
										tableBodyHtml += '<td>——</td>';
									}
									tableBodyHtml += '<td>' + d_enterpriseName+ '</td>';
									tableBodyHtml += '<td>' + d_oaFlowCode+ '</td>';
									tableBodyHtml += '<td>' + d_otherRepayUserName+ '</td>';
									tableBodyHtml += "<td><a href='repay_user_setting.html?borrowId=" + v.borrowId +"&delId=" + d_id +"'>查看</a></td>";
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
						$("#tcdPagehide").show();
					}else{
						 var html = ""; 
						 html +='<tr class="no-records-found">';
						 html +='<td colspan="9" align="center">没有找到匹配的记录</td>';
						 html += '</tr>';
			    		 $("#table").find("tbody").html(html);
			    		 $("#tcdPagehide").hide();
			    	}
				},error : function(){
		        	alert("获取还款对象列表失败");
		        	$("#tcdPagehide").hide();
		        },
				async : false
			});

}

var myPage = function(){
	var $tcdPage = $("#tcdPagehide");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {  
		  var v_borrowName = $("#borrowName").val(),
			v_oaFlowCode = $("#oaFlowCode").val(),
			v_otherRepayUserName = $("#otherRepayUserName").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "10",
			"borrowName" : v_borrowName,
			"oaFlowCode" : v_oaFlowCode,
			"otherRepayUserName" : v_otherRepayUserName
			};
		tableFun("/borrow/repay_user_list/repayUserList", pageData);		  
	  }
	});
};

var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
		 var v_borrowName = $("#borrowName").val(),
			v_oaFlowCode = $("#oaFlowCode").val(),
			v_otherRepayUserName = $("#otherRepayUserName").val();
			var pageData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"borrowName" : v_borrowName,
			"oaFlowCode" : v_oaFlowCode,
			"otherRepayUserName" : v_otherRepayUserName
			};
		tableFun("/borrow/repay_user_list/repayUserList",pageData);
		myPage();
});

