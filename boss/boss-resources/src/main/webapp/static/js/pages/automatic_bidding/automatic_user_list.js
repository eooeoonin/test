/**
 * 
 */
var _pages;
$(function () {
	
	var _table = $('#table');
	_table.bootstrapTable();
	var srhData = {"pageNo":"1","pageSize":"10","status":"ON"};
	tableFun("../automatic_bidding/automatic_user_list/list", srhData);	
	myPage();
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
        	if(v.status == "ON"){
          //获取数据
        var u_userId = v.userId;
        	  u_userName = v.userName,
        	  u_expectEndTime = v.expectEndTime,
        	  u_investTermMin = v.investTermMin,
        	  u_investTermMax = v.investTermMax,
              u_modifyTime = v.modifyTime;
        
           	  
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + u_userId + '</td>';
          tableBodyHtml += '<td>' + u_userName + '</td>>';
          tableBodyHtml += '<td>' + u_expectEndTime + '</td>';
          if(u_investTermMax == '9999'){
        	  tableBodyHtml += '<td>300天以上</td>';
          }else{
        	  tableBodyHtml += '<td>' + u_investTermMin + '-' + u_investTermMax + '天</td>';
          }
          tableBodyHtml += '<td>' + u_modifyTime + '</td>';
          tableBodyHtml += '</tr>';
        	}
        });
        _table.find("tbody").html(tableBodyHtml);
        
        replaceFun(_table);
        	  $("#tcdPagehide").show();
    	  }else{
        	  $("#tcdPagehide").hide();
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
									"status":"ON",
								};
			tableFun("../automatic_bidding/automatic_user_list/list", srhData);
		}
	});
};

