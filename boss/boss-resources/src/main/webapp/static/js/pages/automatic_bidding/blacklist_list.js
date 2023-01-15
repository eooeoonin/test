/**
 * 
 */
var _pages;
$(function () {
	
	var _table = $('#table');
	_table.bootstrapTable();
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("../automatic_bidding/blacklist_list/list", srhData);	
	myPage();
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm2 =  $('#modal_form2');
	_modalFm2.validationEngine('attach', {
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
    	 
        var _table = $('#table'),
          tableBodyHtml = '';
        if(data.length!=0){
        _pages = data[0].pages;
        }else{
        	_pages=0;
        }
        $.each(data, function (k, v) {
        	
          //获取数据
        var u_registerMobile = v.registerMobile;
        	  u_blackCreateTime = v.blackCreateTime,
        	
        
           	  
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + u_blackCreateTime + '</td>>';
          tableBodyHtml += '<td>' + u_registerMobile + '</td>';
          tableBodyHtml += '</tr>';
        
        });
        _table.find("tbody").html(tableBodyHtml);
        
        replaceFun(_table);
        	  $("#tcdPagehide").show();
    	
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
									
								};
			tableFun("../automatic_bidding/blacklist_list/list", srhData);
		}
	});
};
//添加模态窗口
var regaccFun2 = function(obj) {
	$("#modal_id").val(obj);
	$('#myModal1').modal('show');
	
};
var _modalSave2 = $("#modal_save2");
	  _modalSave2.click(function() {
			if (!_modalFm2.validationEngine('validate')) {
			    return false;
			}else{
				_modalSave2.attr("disabled","disabled").addClass("btn-white");
				var mobile = $("#mobile").val();
				var tData = {
						"mobile":mobile,
				};
				$.ajax({
					type : "POST",
					url : "/automatic_bidding/blacklist_list/addBlacklist",
					data : tData,
					dataType : "json",
					async : false,
					success : function(data) {
//						if(){
						alert(data);
//						}
						$('#myModal').modal('hide');
						_modalSave2.removeAttr("disabled").removeClass("btn-white");
						$('#modal_form2')[0].reset();
						window.location.href="../automatic_bidding/blacklist_list.html"; 
					},
					error : function() {
						alert("添加失败");
						$('#myModal').modal('hide');
						$('#modal_form2')[0].reset();
					}
				});
			}		
		});
