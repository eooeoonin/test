/**
 * 
 */
var _pages;
$(function () {
	$("#table").hide();
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm =  $('#modal_form');
	_modalFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
 });
	
var _userId;
$("#change").click(function(){
	if (!_modalFm.validationEngine('validate')) {
	    return false;
	  }else{
		_userId = $("#investorUserCode").val();
		$("#table").show();
		var _table = $('#table');
		_table.bootstrapTable();
		var srhData = {"investorUserCode":_userId,"investorStatus":"SUCCESS","investType":"AUTO","pageNo":"1","pageSize":"10"};
		tableFun("../automatic_bidding/bidrecord/list", srhData);	
		myPage();
	 }
})


function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  if(data.total != 0){
        var _table = $('#table'),
          tableBodyHtml = '';
        
        _pages = data.pages;
        
        $.each(data.list, function (k, v) {
          //获取数据
        var u_investorUserCode = v.investorUserCode;
        	  u_investorUserName = v.investorUserName,
        	  u_investAmount = v.investAmount.amount,
              u_loanCode = v.loanCode,
              u_investTime = v.investTime,
              u_investorStatus = v.investorStatus;
              
        	  
        	  	if(u_investorStatus == "COMPLETED"){
        	  		u_investorStatus = "已回款";
        	  	}else{
        	  		u_investorStatus = "已出借";
        	  	}
        	  	
        	  
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + u_investorUserCode + '</td>';
          tableBodyHtml += '<td>' + u_investorUserName + '</td>>';
          tableBodyHtml += '<td>' + u_investAmount + '</td>';
          tableBodyHtml += '<td>' + u_loanCode + '</td>';
          tableBodyHtml += '<td>' + u_investTime + '</td>';
          tableBodyHtml += '<td>' + u_investorStatus + '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
        	  $("#tcdPagehide").show();
    	  }else{
        	  $("#tcdPagehide").hide();
        	  $("#table").hide();
        	  alert("此用户没有自动出借记录");
        }
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
									"investType":"AUTO",
									"investorUserCode":_userId,
									"investorStatus":"SUCCESS",
								};
			tableFun("../automatic_bidding/bidrecord/list", srhData);
		}
	});
};

