/**
 * 
 */
var _pages;
$(function() {
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "10"				
	};
	tableFun("/pay/bankability/listBankAbility",pageData);
	myPage();
});


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
								d_merchant = v.merchant,
								d_channel = v.channel,
								d_business = v.business,
								d_bankName = v.bankName,
								d_bankCode = v.bankCode,
								d_cardType = v.cardType,
								d_optimalRangeMin = v.optimalRangeMin;
								d_optimalRangeMax = v.optimalRangeMax,
								d_available = v.available,
								d_singleQuota = v.singleQuota;
								d_dayQuota = v.dayQuota,
								d_monthQuota = v.monthQuota,
								d_quotaDesc = v.quotaDesc;
								d_rank = v.rank;
								d_remark = v.remark;

								var d_ids;
								if(d_id.length > 6)
									d_ids = d_id.substring(0,6) + "...";
								else
									d_ids = d_id;
								var d_merchant_cn;
								switch(d_merchant){
								case "P2P":
									d_merchant_cn = "出借";
									break;
								case "CROWD":
									d_merchant_cn = "众筹";
									break;
								default:
									d_merchant_cn = d_merchant;
									break;
								}
								
								var d_business_cn;
								switch(d_business){
								case "NetBank":
									d_business_cn="网银业务";
									break;
								case "Quick":
									d_business_cn = "快捷业务";
									break;
								case "POS":
									d_business_cn = "POS刷卡";
									break;
								case "Withdraw":
									d_business_cn = "提现";
									break;
								
								case "BindCard":
									d_business_cn = "绑卡认证";
									break;
								default:
									d_business_cn = d_business;
									break;
								}
								
								var d_cardType_cn;
								switch(d_cardType){
								case "DebitCard":
									d_cardType_cn = "借记";
									break;
								case "CreditCard":
									d_cardType_cn = "信用";
									break;
								case "HybridCard":
									d_cardType_cn = "混合";
									break;
								case "Company":
									d_cardType_cn = "企业";
									break;	
								default:
									d_cardType_cn = d_cardType;
									break;
								}

								tableBodyHtml += '<tr>';
								tableBodyHtml += '<td>' + d_ids+ '</td>';
								tableBodyHtml += '<td>' + d_merchant_cn+ '</td>';
								tableBodyHtml += '<td>' + d_channel+ '</td>';
								tableBodyHtml += '<td>' + d_business_cn+ '</td>';
								tableBodyHtml += '<td>' + d_bankName+ '</td>';
								tableBodyHtml += '<td>' + d_bankCode+ '</td>';
								tableBodyHtml += '<td>' + d_cardType_cn+ '</td>';
								tableBodyHtml += '<td>' + d_optimalRangeMin+ '</td>';
								tableBodyHtml += '<td>' + d_optimalRangeMax+ '</td>';
								tableBodyHtml += '<td>' + d_available+ '</td>';
								tableBodyHtml += '<td>' + d_singleQuota+ '</td>';
								tableBodyHtml += '<td>' + d_dayQuota+ '</td>';
								tableBodyHtml += '<td>' + d_monthQuota+ '</td>';
								tableBodyHtml += '<td>' + d_quotaDesc+ '</td>';
								tableBodyHtml += '<td>' + d_rank+ '</td>';
								tableBodyHtml += '<td>' + d_remark+ '</td>';
								tableBodyHtml += '<td><a href="bankability_edit.html?id='+ d_id + '" style="margin-left:15px;">编辑</a>'
								+'<a  name='+d_id+' href="javascript:" style="margin-left:15px;" onclick="errorDel(this)">删除</a></td>';
								tableBodyHtml += '</tr>';
							});
					_table.find("tbody").html(tableBodyHtml);

				},
				error:function(data){
					alert("取得银行卡能力异常");
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
		  var v_merchant= $("#merchant").val(),
			v_channel = $("#channel").val(),
			v_cardType = $("#cardType").val();
			v_business = $("#business").val(),
			v_bankName = $("#bankName").val();
			v_bankCode = $("#bankCode").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "10",
			"merchant" : v_merchant,
			"channel" : v_channel,
			"cardType" : v_cardType,
			"business" : v_business,
			"bankName" : v_bankName,
			"bankCode" : v_bankCode
			};
		tableFun("/pay/bankability/listBankAbility", pageData);		  
	  }
	});
};


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
		var v_merchant= $("#merchant").val(),
		v_channel = $("#channel").val(),
		v_cardType = $("#cardType").val();
		v_business = $("#business").val(),
		v_bankName = $("#bankName").val();
		v_bankCode = $("#bankCode").val();
		var srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"merchant" : v_merchant,
		"channel" : v_channel,
		"cardType" : v_cardType,
		"business" : v_business,
		"bankName" : v_bankName,
		"bankCode" : v_bankCode
		};
		tableFun("/pay/bankability/listBankAbility",srhData);
		myPage();
	
});


var _addBtn = $("#addButton");
_addBtn.click(function(){
	window.location.href="../pay/bankability_add.html";
});

function errorDel(id){	
	var flag = confirm('确定要删除记录吗?');
	if(flag){		
		$.ajax({
			type : "POST",
			url : "/pay/bankability/delBankAbility",
			dataType : "json",
			data:{
				"id":id.name
			},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						bootbox.alert("操作成功", function(){
							window.location.reload();
						});
					}else{
						bootbox.alert(data.msg);
						window.location.reload();
					}
				}
			}	
		});
	}
}