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
			  _inStatus2 = '0';
		  }if(_inStatus == '2'){
			  _inStatus2 = '4,5';
		  }
		  
			var srhData = {"pageNo":"1",
					   "pageSize":"10",
					   "clearDate":_cleardate,
					   "inBizType" :"FREEZE_REDMONEY,UNFREEZE_REDMONEY",
					   "inCheckCount":_inStatus2
					  };
		tableFun("../difference_account/difference/listForBoss",srhData);	
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
        var _table = $('#tab_15'),
          tableBodyHtml = '';
        
        _pages = data.pages;
        
        $.each(data.list, function (k, v) {
        	  //获取数据
        	 var date1 = new Date(v.modifyTime);
    	  	  var u_modifyTime = date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();
         	 u_userId = v.userId,//账户ID
       	  	 u_outOrderNo= v.outOrderNo,//外部订单号
       	  	 u_amount= v.amount.amount,//金额
       	  u_actionType = v.actionType//交易类型
          
       	if(u_actionType=="IN"){
    	    u_actionType="流入";
      }else if(u_actionType=="OUT"){
	        u_actionType="流出";
      }else if(u_actionType=="F"){
	        u_actionType="冻结";
      }else if(u_actionType=="U"){
	        u_actionType="解冻";
      }
        	  u_subTransCode= v.subTransCode//冻结类型
              if(u_subTransCode=="10001"){
             	 u_subTransCode="转帐冻结解冻";
             }else if(u_subTransCode=="10002"){
             	u_subTransCode="充值冻结解冻";
             }else if(u_subTransCode=="10003"){
             	u_subTransCode="提现冻结解冻";
             }else if(u_subTransCode=="10004"){
             	u_subTransCode="普通冻结解冻";
             }else if(u_subTransCode=="10005"){
             	u_subTransCode="出借冻结解冻";
             }
	        	  
	          //输出HTML元素
	          tableBodyHtml += '<tr>';
	          tableBodyHtml += '<td>' + u_modifyTime + '</td>';
	          tableBodyHtml += '<td>' + u_userId + '</td>';
	          tableBodyHtml += '<td>' + u_outOrderNo + '</td>';
	          tableBodyHtml += '<td>' + u_actionType + '</td>';
	          tableBodyHtml += '<td>' + u_amount + '</td>';
	          tableBodyHtml += '<td>' + u_subTransCode + '</td>';
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
									"pageSize" : "10",
									"clearDate":_cleardate,
									"inBizType" :"FREEZE_REDMONEY,UNFREEZE_REDMONEY",
									"inCheckCount":_inStatus2
								};
			tableFun("../difference_account/difference/listForBoss",srhData);	
		}
	});
};


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

//账务流水
function recharge1(){
	window.location.href="difference_accountingwater_handle_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=2";
}
//账务冻结
function withdraw1(){
	window.location.href="difference_accountingfreeze_handle_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=2";
}
//红包流水
function invest1(){
	window.location.href="difference_redwater_handle_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=2";
}
//红包冻结
function killloan1(){
	window.location.href="difference_redfreeze_handle_list.html?clearDate="+_cleardate+"&inStatus="+_inStatus+"&type_select=2";
}
