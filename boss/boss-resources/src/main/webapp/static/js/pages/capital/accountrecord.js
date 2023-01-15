/**
 * 
 */
//var _pages;
$(function() {
	var start = {
		elem : "#startDate",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			end.min = datas;
			end.start = datas;
		}
	};
	var end = {
		elem : "#endDate",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			start.max = datas;
		}
	};
	laydate(start);
	laydate(end);
	
	$.ajax({
		type : "POST",
		url : "../static/data/tradebiz.txt",
		dataType : "json",
		success : function(data) {
			var _select = $('#type');
			$.each(data, function(k, v) {
				_select.append('<option value='+v.accountType+'>' + v.accountTypeCn + '</option>');
			});
		},
		error: function(data){
			//alert(data);
		}
	});
});


function tableFun() {
	var form = "";
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	form = {
			"userId":$("#userId").val(),
			"registerMobile":$("#registerMobile").val(),
			"type":$("#type").val(),
			"pageNo":$("#currPage").val(),
			"pageSize":$("#pageSize").val()
			};
	if(startDate!= ""){
		form.startDate = startDate;
	}
	if(endDate!= ""){
		form.endDate = endDate;
	}
	$.ajax({
				type : "POST",
				url : "/capital/accountrecord/getTradeBizLog",
				data : form,
				dataType : "json",
				success : function(data) {
					if(data.resCode == 0){
						if(data.data.list.length > 0) {
							var _table = $('#table'), tableBodyHtml = '';
							$("#currPage").val(data.data.pageNum);
				    		$("#pageCount").val(data.data.pages);
							$.each(
									data.data.list,
									function(k, v) {
										var d_id = v.userId,
										d_registerMobile = v.registerMobile,
										d_time = v.tradeTime,
										d_type = v.type;
                                        d_amount = v.amount.amount;
										var InAndOut = handleInAndOut(d_type, d_amount);
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + d_id+ '</td>';
										tableBodyHtml += '<td>' + d_registerMobile+ '</td>';
										tableBodyHtml += '<td>' + d_time + '</td>';
										tableBodyHtml += '<td>' + InAndOut['type']+ '</td>';
										tableBodyHtml += '<td>' + InAndOut['d_in']+ '</td>';
										tableBodyHtml += '<td>' + InAndOut['d_out']+ '</td>';
										tableBodyHtml += '</tr>';
									});
							_table.find("tbody").html(tableBodyHtml);
                            replaceFun(_table);
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
				error:function(data){
					alert("出错了");
				},
				async : false
			});

	
}


var myPage = function(){

	  var $curP = $("#currPage"),
	  $pageC = $("#pageCount");
	  var curpage = parseInt($curP.val());
	  var pageCount = parseInt($pageC.val());
	  var $tcdPage = $(".tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: pageCount,
	    current: curpage,
	    backFn: function (p) {
	    	$curP.val(p);
	    	tableFun();
	    }
	  });
};


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	var userId = $("#userId").val();
	var registerMobile = $("#registerMobile").val();
	if(userId == "" && registerMobile == "") {
		alert("用户id或用户名不能为空");
		return false;
	}
	$("#currPage").val(1);
	tableFun();	
	myPage();
});


function handleInAndOut(type, amount) {
	var inAndOut ={};
	inAndOut['d_in'] = 0;
	inAndOut['d_out'] = 0;
	if("RECHARGE" == type){
        inAndOut['d_in'] = amount;
        inAndOut['type'] = "充值";
	}else if("WITHDRAW" == type){
        inAndOut['d_out'] = amount;
        inAndOut['type'] = "提现";
    }else if("ADD" == type){
        inAndOut['d_in'] = amount;
        inAndOut['type'] = "调账增加";
    }else if("SUB" == type){
        inAndOut['d_out'] = amount;
        inAndOut['type'] = "调账减少";
    }else if("RELEASE" == type){
        inAndOut['d_out'] = amount;
        inAndOut['type'] = "满标放款";
    }else if("INVITE_PROFIT" == type){
        inAndOut['d_in'] = amount;
        inAndOut['type'] = "推荐人收益";
    }else if("REPAY_PRINCIPAL" == type){
        inAndOut['d_in'] = amount;
        inAndOut['type'] = "到期回收本金";
    }else if("REPAY_INTEREST" == type){
        inAndOut['d_in'] = amount;
        inAndOut['type'] = "到期回收利息";
    }else if("REPAY_PENALTY" == type){
        inAndOut['d_in'] = amount;
        inAndOut['type'] = "到期回收罚息";
    }
	return inAndOut;
}



