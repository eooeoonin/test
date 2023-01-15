/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}  

var Request = {};
Request = GetRequest();


var _pages;var _cleardate;var _inBusType;var _inStatus;
$(function(){
	var _table = $('#tab_1');
		  _table.bootstrapTable();
		  
		  laydate({elem: "#clearDate", format: "YYYY/MM/DD"});
		  
		  _cleardate = Request.clearDate;
		  _type_select = Request.type_select;
		  _inStatus = Request.inStatus;
		  
		  $("#clearDate").val(_cleardate);
		  $("#type_select").val(_type_select);
		  $("#inStatus").val(_inStatus);
		  
		  if(_inStatus == '1'){
			  _inStatus2 = '0,2,3';
		  }if(_inStatus == '2'){
			  _inStatus2 = '4,5';
		  }
		  
	var srhData = {"pageNo":"1",
						   "pageSize":"20",
						   "clearDate":_cleardate,
						   "inBusType":"INVITE_PROFIT",
						   "inStatus":_inStatus2
						  };
		  tableFun("../difference_account/difference/tradeRecordList", srhData);	
		  myPage();
})


function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
//    	  if(data.data.total!=0){
        var _table = $('#tab_17'),
          tableBodyHtml = '';
        
        _pages = data.data.pages;
        
        $.each(data.data.list, function (k, v) {
	          //获取数据
	        	 var u_id = v.id;
	        	  u_outOrderNo = v.outOrderNo,
	        	  u_userId = v.userId,
	              u_cashAmount = v.cashAmount.amount,
	              u_tradeTime = v.tradeTime,
	              u_checkCount = v.checkCount,
	              u_status = v.status
	              
	           
	              if(u_status == '2'){
	            	  if(u_checkCount == '1'){
		           		  _checkCount = '';
		           	  }if(u_checkCount == '2'){
		           		    _checkCount = '出借人现金入账错账';
		           	  }if(u_checkCount == '3'){
		           		    _checkCount = '出借人现金入账单边账';
		           	  }
	              }if(u_status == '3'){
	        		  if(u_checkCount == '1'){
		           		  _checkCount = '';
		           	  }if(u_checkCount == '3'){
		           		    _checkCount = '出借人现金入账单边账';
		           	  } 
	        	  }
	        	  
	          //输出HTML元素
	          tableBodyHtml += '<tr>';
	          tableBodyHtml += '<td>' + "<label class=\"radio-inline i-checks\"><input type=\"checkbox\" id=\""+u_id+"\" name=\"id\" value=\""+u_id+"\" class=\"sub_radbox\"></label>" + '</td>';
	          tableBodyHtml += '<td>' + u_outOrderNo + '</td>';
	          tableBodyHtml += '<td>' + u_userId + '</td>';
	          tableBodyHtml += '<td>' + u_cashAmount + '</td>';
	          tableBodyHtml += '<td>' + u_tradeTime + '</td>';
	          if(u_checkCount == "" || u_checkCount == null){
	        	  tableBodyHtml += '<td>' + "" + '</td>';
	          }else{
	        	  tableBodyHtml += '<td>' + _checkCount + '</td>';
	          }
	          if(u_checkCount == "2"){
	          tableBodyHtml += '<td>' + "<a href=\"difference_account_list.html?id="+u_id+"\">查看账务</a>" + '</td>';
	          }else{
	        	  tableBodyHtml += '<td>' + "" + '</td>';
	          }
	          tableBodyHtml += '</tr>';
	        });
        _table.find("tbody").html(tableBodyHtml);
        
        replaceFun(_table);
//      }else{
//	    	$("#tcdPagehide").hide();
//	    }
      },error:function(data){
    	 alert(data)
      },
    async : false
    });
  }


// 分页
var myPage = function(){
var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
									"pageNo" : p,
									"pageSize" : "20",
									"clearDate":_cleardate,
									"inBusType":"INVITE_PROFIT",
									"inStatus":_inStatus2
								};
			tableFun("../difference_account/difference/tradeRecordList", srhData);
		}
	});
};


$("#checkother").click(function(){
	 var form = $("#modal_form").get(0);
		if(typeof(form.id) == "undefined") {
		alert("没有处理数据记录!");
		return false;
		}
		var result = validateIsSelect(form.chkall, form.id);
		var _clearDate = $("#clearDate").val();
		if("true"==result){
				$.ajax({
		            type: "POST",
		            url: "../difference_account/difference/again",
		            data: $("#modal_form").serialize() + "&tradeCheckDate=" + _clearDate,
		            success: function(data) {
		            	if(data == 'SEND_OK'){
		            		alert("处理成功");
			           		location = location;
		            	}else{
		            	 alert("处理失败");
		           		 location = location;
		            	}
		            },error:function(data){
		            	alert(data);
		            }
		        });
		}else if("false"==result){
			alert("请选择要处理的数据");
		}
})


//判断是否选择了记录
function validateIsSelect(allobj,items){
    if(items.length){
	    for(var i=0;i<items.length;i++){
	    	if(items[i].checked){
	    		return "true";
	    	} 
	    }
   }else{
	    if(items.checked) return "true";
   }
   return "false";
}

	$("#handle").click(function(){
		var form = $("#modal_form").get(0);
		if(typeof(form.id) == "undefined") {
		alert("没有处理数据记录!");
		return false;
		}
		var result = validateIsSelect(form.chkall, form.id);
		if("true"==result){
			$.ajax({
	            type: "POST",
	            url: "../difference_account/difference/handedTrade",
	            data: $("#modal_form").serialize(),
	            success: function(data) {
	           		 alert(data.data);
	           		 location = location;
	            },error:function(data){
	            	alert(data.data);
	            }
	        }); 
		}else if("false"==result){
			alert("请选择要处理的数据");
		}
	});

	$("#inStatus").change(function(){
		search();
	});
	
	function search(){
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
		var _type_select = document.getElementById("type_select").value;
		var _clearDate = document.getElementById("clearDate").value;
		var _inStatus = document.getElementById("inStatus").value;
		if(_type_select == 0){
			window.location.href="difference_summary.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
		}if(_type_select == 1){
			if(_inStatus == 1){
				window.location.href="difference_recharge_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}if(_inStatus == 2){
				window.location.href="difference_recharge_handle_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}
		}if(_type_select == 2){
			if(_inStatus == 1){//未处理
				window.location.href="difference_accountingwater_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}if(_inStatus == 2){//已处理
				window.location.href="difference_accountingwater_handle_list.html?clearDate="+_clearDate+"&inStatus="+_inStatus+"&type_select="+_type_select+"";
			}
		}
	};


	//充值
	function recharge(){
		window.location.href="difference_recharge_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	//提现
	function withdraw(){
		window.location.href="difference_withdraw_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	//出借
	function invest(){
		window.location.href="difference_invest_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	//流标
	function killloan(){
		window.location.href="difference_killloan_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	//放款
	function release(){
		window.location.href="difference_release_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	//还款
	function repay(){
		window.location.href="difference_repay_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	//邀请收益
	function inviteprofit(){
		window.location.href="difference_inviteprofit_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	//手动调账
	function adjustment(){
		window.location.href="difference_adjustment_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=1";
	}
	
