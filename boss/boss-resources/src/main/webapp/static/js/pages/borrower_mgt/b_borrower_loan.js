function _getParamsObject() {
	var thisURL = document.URL;
	var getval = thisURL.split('?')[1];
	var paramsArray = getval.split("&");
	var paramsObject = {};
	for (var i = 0; i < paramsArray.length; i++) {
		var keyAndValue = paramsArray[i].split("=");
		paramsObject[keyAndValue[0]] = keyAndValue[1];
	}
	return paramsObject;
}

var _pages;
$(function () {
	
  var userCode = $("#userCode").val();
  var _table = $('#table');
  _table.bootstrapTable();
  var srhData = {"pageNo":"1","pageSize":"10", "borrowUserCode":userCode };
  tableFun("/borrower/borrowerlist/getLoanInfo", srhData);
  myPage();
});

function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  nextfun();
        var _table = $('#table'),
        tableBodyHtml = '';
        _pages= data.data.pages;
        var _data = data.data;
        $.each(_data.list, function (k, v) {
          //获取数据
          var borrowTitle = v.borrowTitle,       //借款名称
          		borrowRate=v.borrowRate,  //利率
          		investDate=v.investDate,  //出借周期
          		borrowAmount=v.borrowAmount.amount,  //借款金额
          		borrowedAmount=v.borrowedAmount.amount,  //已经借款金额
          		//remainAmount=borrowAmount - borrowedAmount,  //剩余金额
          		
          		remainAmount = v.nextPayAmount,
          		//d_data= moment(v.createTime).format("YYYY-MM-DD"),   // 
          		d_data = moment(v.nextPayDate).format("YYYY-MM-DD"),
          		status= formatStatus(v.status);    // 状态
          var liborrowRate = borrowRate+"%";
//          alert(liborrowRate);
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + borrowTitle + '</td>';
          tableBodyHtml += '<td>' + liborrowRate + '</td>';
          tableBodyHtml += '<td>' + investDate + '</td>';
          tableBodyHtml += '<td>' + borrowAmount + '</td>';
          tableBodyHtml += '<td>' + remainAmount + '</td>';
          tableBodyHtml += '<td>' + d_data + '</td>';
          tableBodyHtml += '<td>' + status + '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);

      },
	  async : false
    });
  }

function nextfun(){
	
	
	
	
}

function formatStatus(status){
	if("INIT" == status){
		return "初始";
	}else if("APPROVAL1"== status){
		return "初审";
	}else if("APPROVAL2"== status){
		return "复审";
	}else if("INUSE"== status){
		return "可用";
	}else if("SPLIT"== status){
		return "已拆分";
	}else if("LOCK"== status){
		return "锁定中";
	}else if("INVALID1"== status){
		return "初审废弃";
	}else if("INVALID2"== status){
		return "复审废弃";
	}else if("REFUSE1"== status){
		return "初审拒绝";
	}else if("REFUSE2"== status){
		return "复审拒绝";
	}else if("FINISH" ==  status){
		return "已完成";
	}else return "";
}
var myPage = function(){
    //分页
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
      pageCount: _pages,
      current: 1,
      backFn: function (p) {
    	  //点击分页事件
    	  var userCode = $("#userCode").val();
    	  var srhData = {"pageNo":p,"pageSize":"10" ,"borrowUserCode":userCode};
	  	  tableFun("/borrower/borrowerlist/getLoanInfo", srhData);
      }
    });
}