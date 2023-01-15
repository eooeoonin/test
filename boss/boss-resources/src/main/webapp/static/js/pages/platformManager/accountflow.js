var _pages;
$(function () {
	var start = {
			elem : "#before",
			format : "YYYY/MM/DD",
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas;
				end.start = datas;
			}
		};
		var end = {
			elem : "#after",
			format : "YYYY/MM/DD ",
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas;
			}
		};
		try {
			  laydate(start);
			  laydate(end);
			} catch (e) {}
			
});


function tableFun(tdUrl,tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					 var _table = $('#table'),
			          tableBodyHtml = '';
			        _pages = data.pages;
			      
			        
			        $.each(data.list, function (k, v) {
			        	
			        	  if(v.transAccount=="sys_generate_001"){
					        	v.transAccount="代偿金账户";
					        }else if(v.transAccount=="sys_generate_002"){
					        	v.transAccount="营销款账户";
					        }else if(v.transAccount=="sys_generate_005"){
					        	v.transAccount="派息账户";
					        }else if(v.transAccount=="sys_generate_006"){
					        	v.transAccount="代充值账户";
					        }else{
					        	v.transAccount="--";
					        }
					        
					        
					        if(v.otherAccount=="sys_generate_001"){
					        	v.otherAccount="代偿金账户";
					        }else if(v.otherAccount=="sys_generate_002"){
					        	v.otherAccount="营销款账户";
					        }else if(v.otherAccount=="sys_generate_005"){
					        	v.otherAccount="派息账户";
					        }else if(v.otherAccount=="sys_generate_006"){
					        	v.otherAccount="代充值账户";
					        }else{
					        	v.otherAccount="--";
					        }
					        
					        if(v.subTransCode=="ADD_RECHARGE"){
					        	v.subTransCode="充值";
					        }else if(v.subTransCode=="UNFREEZE_SUB_WITHDRAW"){
					        	v.v.subTransCode="提现";
					        }else if(v.subTransCode=="TRANSFER_SYSTEM"){
					        	v.subTransCode="转账";
					        }
			       	 tableBodyHtml += '<tr>';
						tableBodyHtml += '<td>' + v.transAccount+ '</td>';
						tableBodyHtml += '<td>' + v.subTransCode+ '</td>';
						tableBodyHtml += '<td>' + v.otherAccount+ '</td>';
						tableBodyHtml += '<td>' + v.outOrderNo+ '</td>';
						tableBodyHtml += '<td>' + v.transAmount.amount+ '</td>';//充值金额
						tableBodyHtml += '<td>' + v.tradeTime+ '</td>';
						tableBodyHtml += '</tr>';
				        });
			        _table.find("tbody").html(tableBodyHtml);
			        replaceFun(_table);
			      },error:function(data){
			    	 alert(data)
			      },
			    async : false
			    });
					
}			
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				

var myPage = function(){
	var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(p) {
				var startTime = $("#before").val();
				var endTime = $("#after").val();
				var transAccount = $("#transAccount").val();
				var subTransCode = $("#subTransCode").val();
				var otherAccount = $("#otherAccount").val();
				var outOrderNo = $("#outOrderNo").val();
				var srhData = {"startTime":startTime,
										"endTime":endTime,
										"transAccount":transAccount,
										"subTransCode":subTransCode,
										"otherAccount":otherAccount,
										"outOrderNo":outOrderNo,
										"pageNo" : p,
										"pageSize" : "10"
									};
  tableFun("/platformManager/accountflow/account",srhData);
		     }
	});
};



var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	$("#selected").validationEngine('attach',{
		inlineValidation: false,
		maxErrorsPerField:1,
		autoHidePrompt: true,
		autoHideDelay: 2000
	});
	if (!$("#selected").validationEngine('validate')) {
		$("#selected").validationEngine('detach');
		 return false;
	} 
	var startTime = $("#before").val();
	var endTime = $("#after").val();
	var transAccount = $("#transAccount").val();
	var subTransCode = $("#subTransCode").val();
	var otherAccount = $("#otherAccount").val();
	var outOrderNo = $("#outOrderNo").val();
	var srhData = {"startTime":startTime,
							"endTime":endTime,
							"transAccount":transAccount,
							"subTransCode":subTransCode,
							"otherAccount":otherAccount,
							"outOrderNo":outOrderNo,
							"pageNo" : "1",
							"pageSize" : "10"
						};
	  tableFun("/platformManager/accountflow/account",srhData);
	  myPage();
});
