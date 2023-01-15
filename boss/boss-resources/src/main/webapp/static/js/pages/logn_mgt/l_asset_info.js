/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

var amount1 = 0;
var amount2 = 0;
var amount3 = 0;
var amount4 = 0;
var count = 1;
var _borrowId,_borrowStatus;
$(function () {
	_modalFm1 =  $('#borrowForm');
	_modalFm1.validationEngine('attach', {
			  maxErrorsPerField:1,
			  autoHidePrompt: true,
			  autoHideDelay: 2000
			});
	  
	var Request = {};
	Request = GetRequest();
	_borrowId = Request.borrowId;
	_borrowStatus = Request.status;
	if(_borrowStatus === "INUSE") {
		$("#cancelBorrow").show();
		$("#cancelBorrowSpan").show();
	}
		
  queryBorrow("Y");
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"})

  $("#fileinput").fileinput({
      language: 'zh', //设置语言
      uploadUrl: "/borrow/l_asset_list/upload/"+_borrowId+"?bizeCode=pic", //上传的地址
      allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
      uploadAsync: true,//是否异步方式提交
      enctype: 'multipart/form-data',//2进制传输数据
      showUpload: true,//是否显示提交按钮
//      minImageWidth: 50, //图片的最小宽度
//      minImageHeight: 50,//图片的最小高度
//      maxImageWidth: 1000,//图片的最大宽度
//      maxImageHeight: 1000,//图片的最大高度
      maxFileCount: 10,//上传数量
      maxFilePreviewSize: 10240//上传文件大小
    }).on("fileuploaded", function(event, data, previewId, index) {
    	queryBorrowFile();
    });

  
  
  
  $("#tab2").click(function(){
	  $("#tab-1").hide();
	  $("#tab-3").hide();
	  $("#tab-2").show();
	  $("#borrowId").val(_borrowId);
	  var tableBodyHtml = "";
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_asset_list/loanList",
	      data:{
	    	  borrowId:_borrowId
	      },
	      async: false,
	      success: function(data) {
	    	var _table = $('#table3'),
	        tableBodyHtml = '';
	        $.each(data.data.list, function (k, v) {
	    	//获取数据
	          var d_loanCode = v.id,
	              d_loanTypeName = v.loanTypeName,
	              d_title = v.title,
	              d_yearRate=v.yearRate,
	              d_amount=v.amount.amount,
	              d_openTime=v.openTime,
	              d_interestStartDate=v.interestStartDate,
	              d_interestEndDate=v.interestEndDate,
	              d_borrowCycle=v.borrowCycle
	              ;
	          //输出HTML元素
	          tableBodyHtml += '<tr>';
	          tableBodyHtml += '<td>' + d_loanCode + '</td>';
	          tableBodyHtml += '<td>' + d_loanTypeName + '</td>';
	          tableBodyHtml += '<td>' + d_title + '</td>';
	          tableBodyHtml += '<td>' + d_yearRate + '%</td>';
	          tableBodyHtml += '<td>' + d_borrowCycle + '</td>';
	          tableBodyHtml += '<td>' + d_amount + '</td>';
	          tableBodyHtml += '<td>' + v.biddingAmount.amount + '</td>';
	          tableBodyHtml += '<td>' + d_openTime.split(" ")[0] + '</td>';
	          tableBodyHtml += '<td>' + d_interestStartDate.split(" ")[0] + '</td>';
	          tableBodyHtml += '<td>' + d_interestEndDate.split(" ")[0]  + '</td>';
	          tableBodyHtml += '</tr>';
	        });
	        _table.find("tbody").html(tableBodyHtml);
	      }
	  })
  })
  
  $("#tab1").click(function(){
	  $("#tab-3").hide();
	  $("#tab-1").show();
	  $("#tab-2").hide();
	  queryBorrow("N");
  })
  
});

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

	
	//格式化日期类型
	  function   formatDate(now)   {     
	      var year=now.getFullYear();     
	      var month=now.getMonth()+1;     
	      var date=now.getDate();     
	      var hour=now.getHours();     
	      var minute=now.getMinutes();     
	      var second=now.getSeconds();
	      return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
	      
	  }  
	  
//查询借款明细
function queryBorrow(flag){
	  /*var Request = {};
	  Request = GetRequest();*/
	  $.ajax({
	      type: "POST",
	      url:"/borrow/getBorrow",
	      data:{
	    	  borrowId:_borrowId
	      },
	      async: false,
	      success: function(responseMsg) {
	    	  var repayTypeCn = "";
	    	  switch(responseMsg.repayType) {
	    	  case "MONTHLYPAYMENTDUE":
	    		  repayTypeCn = "按月付息到期还本产品";
	    		  break;
	    	  case "ONETIMEDEBT": 
	    		  repayTypeCn = "一次性还本付息产品";
	    		  break;
	    	  case "MATCHING":
	    		  repayTypeCn = "等额本息产品";
	    		  break;
	    	  }
	    	  $("#borrowId").val(responseMsg.id);
	    	  $("#borrowIdEdit").val(responseMsg.id);
	    	  $("#borrowIdText").text(responseMsg.id);
	    	  $("#borrowUserName").text(responseMsg.borrowUserName);
	    	  $("#borrowTitle").text(responseMsg.borrowTitle);
	    	  $("#borrowType").text(responseMsg.borrowType);
	    	  $("#borrowRate").text(responseMsg.borrowRate+"%");
	    	  $("#borrowDates").text(responseMsg.borrowDates);
	    	  $("#borrowAmount").val(responseMsg.borrowAmount.amount);
	    	  $("#repayType").text(repayTypeCn);
	    	  $("#collectedAmount").text(responseMsg.collectedAmount.amount);	    	  
	    	  $("#remainAmount").text(responseMsg.remainAmount.amount+"元");
	    	  $("#lastReleaseTime").text(responseMsg.lastReleaseTime);
	    	  $("#lastRepayTime").text(responseMsg.lastRepayTime);
	    	  $("#borrowFee").text(responseMsg.borrowFee.amount+"元");
	    	  //借款用途，安全保障
	    	  //审批意见
	    	  var approveHtml = "";
	    	  $.each(responseMsg.borrowExtendList, function (k, v) {
	    		  if(v.extendType=="PURPOSE"){
	    			  $("#PURPOSE").text(v.mark);
	    		  }
	    		  if(v.extendType=="RISK"){
	    			  $("#RISK").text(v.mark);
	    		  }
	    		  //审批意见
	    		  if(v.extendType== 'FIRSTTRIAL' || v.extendType== 'REVIEW' || v.extendType== 'REFUSE' || v.extendType== 'INIT' ||v.extendType== 'EDIT'){
	    			  approveHtml += "<tr>";
	    			  if(v.extendType== 'INIT'){
	    				  approveHtml += "<td class=\"input-group-addon\">添加借款意见</td>";  
	    			  }
	    			  if(v.extendType== 'FIRSTTRIAL'){
	    				  approveHtml += "<td class=\"input-group-addon\">初审意见</td>";  
	    			  }
	    			  if(v.extendType== 'REVIEW'){
	    				  approveHtml += "<td class=\"input-group-addon\">复审意见</td>";  
	    			  }
	    			  if(v.extendType== 'REFUSE'){
	    				  approveHtml += "<td class=\"input-group-addon\">拒绝原因</td>";  
	    			  }
	    			  if(v.extendType== 'EDIT'){
	    				  approveHtml += "<td class=\"input-group-addon\">修改内容</td>";  
	    			  }
	    			  approveHtml += "<td colspan=\"3\">"+v.mark+"</td>";
	    			  approveHtml += "</tr>";
	    			  approveHtml += "<tr>";
	    			  if(v.extendType== 'INIT'){
	    				  approveHtml += "<td class=\"input-group-addon\">操作人</td>";
	    			  }
	    			  else
	    				  approveHtml += "<td class=\"input-group-addon\">审批人</td>";
	    			  approveHtml += "<td>"+v.editedBy+"</td>";
	    			  approveHtml += "<td class=\"input-group-addon\">审批时间</td>";
	    			  approveHtml += "<td>"+v.modifyTime+"</td>";
	    			  approveHtml += "</tr>";
	    			  
	    		  }
	    	  })
	    	  if(flag == "Y") {
	    		  var length = $("#table").find("tr").length-1;
		    	  $("#table tr:eq("+length+")").after(approveHtml)
	    	  }
	    	  
	    	  //文件
	    	  var html = "";
	    	  $.each(responseMsg.borrowFileList, function (k, v) {
	    		  html += "<div class=\"file-box\">";
	    		  html += "<div class=\"file\">";
	    		  html += "<span class=\"corner\"></span>";
	    		  html += "<div class=\"image\">";
	    		  html += "<a class=\"fancybox img-responsive\" href="+domainUrl+pic+v.fileUrl;
	    		  html += '  title="资产文件">';
	    		  html += "<img alt=\"image\" src="+domainUrl+pic+v.fileUrl+"></img>";
	    		  html += "</a>";
	    		  html += "</div>";
	    		  html += "<div class=\"file-name\">";
	    		  html += "<small>资产文件</small>";
	    		  html += '<div class="img-btns clear">';
	    		  html += '<div class="pull-right">';
	    		  html += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\''+v.id+'\')"><i class="fa fa-trash-o"></i> 删除 </a>';
	    		  html += '</div>';
	    		  html += '</div>';
	    		  html += '</div>';
	    		  html += '</div>';
	    		  html += '</div>';
	    	  })
	    	  $("#images").html(html);
	      }
	  });
}

function delFile(fileId){
    $.ajax({
        type: "POST",
        url: "/borrow/l_asset_list/deleteBorrowFile/"+fileId,
        success: function (data) {
            if (data != null && data != "") {
                $("#"+fileId).removeAttr();
                queryBorrowFile();
            }
        },
        async: false
    });
}


function queryBorrowFile(){
	$.ajax({
        type: "POST",
        url: "/borrow/l_asset_list/getBorrowFileList/"+_borrowId,
        success: function (data) {
            if (data != null && data != "") {
                html = '';
                $.each(data.data, function (k, v) {
      	    		  html += "<div class=\"file-box\">";
      	    		  html += "<div class=\"file\">";
      	    		  html += "<span class=\"corner\"></span>";
      	    		  html += "<div class=\"image\">";
      	    		  html += "<a class=\"fancybox img-responsive\" href="+domainUrl+pic+v.fileUrl;
      	    		  html += ' title="资产文件">';
      	    		  html += "<img alt=\"image\" src="+domainUrl+pic+v.fileUrl+"></img>";
      	    		  html += "</a>";
      	    		  html += "</div>";
      	    		  html += "<div class=\"file-name\">";
      	    		  html += "<small>资产文件</small>";
      	    		  html += '<div class="img-btns clear">';
      	    		  html += '<div class="pull-right">';
      	    		  html += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\''+v.id+'\')"><i class="fa fa-trash-o"></i> 删除 </a>';
      	    		  html += '</div>';
      	    		  html += '</div>';
      	    		  html += '</div>';
      	    		  html += '</div>';
      	    		  html += '</div>';
  	    	  
                    
                });
                $("#images").html(html);
            }
        },
        async: false
    });
}

//保存修改
$("#saveBorrow").click(function () {
	  if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
	  var borrowAmount = Number($("#borrowAmount").val()),
	  collectedAmount = Number($("#collectedAmount").text());
	  if(borrowAmount < collectedAmount) {
		  alert("借款金额不能小于实际已募金额");
		  return false;
	  }
		  
    $.ajax({
        type: "POST",
        url: "/borrow/l_asset_list/saveBorrow",
        data: $("#borrowForm").serialize(),
        success: function (data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
                    bootbox.alert("操作成功", function () {
                        window.location.href = "l_asset_list_view.html?borrowId="+ _borrowId + "&status=" + _borrowStatus;
                    });
                }
                else {
                    bootbox.alert(data.msg);
                }
            }
        },
        async: false
    });
});

$("#cancelBorrow").click(function(){
	bootbox.setLocale("zh_CN");  
	bootbox.confirm("确定要撤销资产吗?", function(result){			
		if(result) {
			$.ajax({
		        type: "POST",
		        url: "/borrow/l_asset_list/cancelBorrow",
		        data: $("#borrowForm").serialize(),
		        success: function (data) {
		            if (data != null && data != "") {
		                if (data.resCode == 0) {
		                    bootbox.alert("操作成功", function () {
		                        window.location.href = "l_asset_list.html" ;
		                    });
		                }
		                else {
		                    bootbox.alert(data.msg);
		                }
		            }
		        },
		        async: false
		    });
		}	
	});	
});


$("#tab3").click(function(){
	  $("#tab-1").hide();
	  $("#tab-2").hide();
	  $("#tab-3").show();
	  queryBorrowerData();
});
function queryBorrowerData(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_firstinstance_list/borrowData",
	      data:{'id':_borrowId,'phase':0,'statusType':'','beforeDate':''},
	      async: false,
	      dataType: "json",
	      success: function(data) {
	    	  console.log(data);
	    	  if(data.resCode == 0){
	    		  var lastReleaseTime = data.data[0].lastReleaseTime;
	    		  var lastRepayTime = data.data[0].lastRepayTime;
	    		  var repayType = data.data[0].repayType;
	    		  $("#borrowTitle").text(data.data[0].borrowTitle);
	    		  $("#borrowDate").text(DateDiff(lastReleaseTime,lastRepayTime) + 1 +"天");
	    		  $("#borrowRate").text(data.data[0].borrowRate/100+"%");
	    		  $("#lastReleaseTime").text(lastReleaseTime);
	    		  $("#lastRepayTime").text(lastRepayTime);
	    		  if(repayType == "MONTHLYPAYMENTDUE"){
	    			  $("#repayType").text("按月付息到期还本产品");
	    		  }else if(repayType == "ONETIMEDEBT"){
	    			  $("#repayType").text("一次性还本付息产品");
	    		  }else if(repayType == "MATCHING"){
	    			  $("#repayType").text("等额本息产品");
	    		  }
	    		  var html = "";
		    	  $.each(data.data[1], function (k, v) {
		    		count++;
		    		var repayFee = 0;
		    		var addAmount = 0;
			          html +="<tr>";
			          html +="<td>"+v.phase+"</td>";
			          html +="<td id='id"+v.phase+"'>"+v.id+"</td>";
			          html +="<td>"+v.principalAmount.amount+"</td>";
			          html +="<td>"+v.interestAmount.amount+"</td>";
			          if(v.repayFee == null ){
			        	  html +="<td width='200px'>0</td>";
		    		  }else{
		    			  html +="<td width='200px'>"+v.repayFee.amount+"</td>";
		    			  repayFee = v.repayFee.amount;
		    		  }
			          html +="<td>"+v.interestEndDate+"</td>";
			          addAmount = accAdd(repayFee,accAdd(v.principalAmount.amount,v.interestAmount.amount));
			          html +="<td>"+addAmount+"</td>";
			          html +="</tr>";
			          amount1 = accAdd(v.principalAmount.amount,amount1);
			          amount2 = accAdd(v.interestAmount.amount,amount2) ;
			          amount3 = accAdd(amount3,repayFee);
			          amount4 = addAmount;
		    	  });
		    	  $("#amount1").text(amount1);
		    	  $("#amount2").text(amount2);
		    	  $("#amount3").text(amount3);
		    	  $("#amount4").text(amount4);
		    	  $("#tablequery").find("tbody").html(html);
		    	  
	    	  }else{
	    		  alert("系统错误");
	    	  }
	      }
	  
	  });
};

//计算天数差的函数，通用 
function DateDiff(sDate1, sDate2) { //sDate1和sDate2是2017-9-25格式 
  var aDate, oDate1, oDate2, iDays
  if (navigator.userAgent.indexOf('Firefox') >= 0){
  	aDate = sDate1.split("-")
  	oDate1 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0]) //转换为9-25-2017格式 
  	aDate = sDate2.split("-")
  	oDate2 = new Date(aDate[1] + '/' + aDate[2] + '/' + aDate[0])
  	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数 
  }else{
  	aDate = sDate1.split("-")
  	oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) //转换为9-25-2017格式 
  	aDate = sDate2.split("-")
  	oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
  	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数 
  }
  return iDays
}


//小数加法
function accAdd(arg1, arg2) {
var r1, r2, m, c;
try {
    r1 = arg1.toString().split(".")[1].length;
}
catch (e) {
    r1 = 0;
}
try {
    r2 = arg2.toString().split(".")[1].length;
}
catch (e) {
    r2 = 0;
}
c = Math.abs(r1 - r2);
m = Math.pow(10, Math.max(r1, r2));
if (c > 0) {
    var cm = Math.pow(10, c);
    if (r1 > r2) {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", "")) * cm;
    } else {
        arg1 = Number(arg1.toString().replace(".", "")) * cm;
        arg2 = Number(arg2.toString().replace(".", ""));
    }
} else {
    arg1 = Number(arg1.toString().replace(".", ""));
    arg2 = Number(arg2.toString().replace(".", ""));
}
return (arg1 + arg2) / m;
}
$("#choose").click(function(){
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#vform');
	_modalFm1.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 3000
	});
	if (!$("#vform").validationEngine('validate')) {
		return false;
	};
	var ids = [];
	var amounts = [];
	for(j = 0;j < count - 1; j++){
		ids[j]=$("#id"+(j+1)).text();
		amounts[j]=$("#am"+(j+1)).val();
	}
	 $.ajax({
	      type: "POST",
	      url:"/borrow/l_firstinstance_list/updateRepayPlan",
	      data:{'ids':ids,'amounts':amounts},
	      async: false,
	      traditional:true,
	      dataType: "json",
	      success: function(responseMsg) {
	    	  
	      }
	  
	  });
	
});
function calculation(){
	amount3 = "0";
	for(i=1;i< count;i++){
		amount3 = accAdd(amount3,$("#am"+i).val());
	}
	amount4 = accAdd(accAdd(amount1,amount2),amount3); 
	 $("#amount3").text(amount3);
	 $("#amount4").text(amount4);
};