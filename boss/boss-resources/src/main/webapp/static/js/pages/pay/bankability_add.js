var _pages;
$(function() {
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
});


$("#addBtn").click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("form").serialize();
	$
	.ajax({
		type : "POST",
		url : "/pay/bankability/addBankAbility",
		data : formData,
		dataType : "json",
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("操作成功", function(){
						window.location.href = "../pay/bankability.html";
					});
				}else{
					bootbox.alert(data.msg);
				}
			}
		}
	});
	  }
});


$("#bankFun").click(function(){
	$
	.ajax({
		type : "POST",
		url : "/pay/bankability/getBankInfo",
		data : {
			"pageNo" : "1",
			"pageSize" : "10"
		},
		success : function(data) {
			var _table = $('#modaltable'), tableBodyHtml = '';
			_pages = data.pages;
			$.each(data.list,function(k,v){
				tableBodyHtml += '<tr class="parent1">';
				tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input type="radio" name="radio" class="sub_radbox"></label></td>';
				tableBodyHtml += '<td title="'+v.bankCode+'">' + v.bankCode+ '</td>';
				tableBodyHtml += '<td title="'+v.bankName+'">' + v.bankName+ '</td>';
				tableBodyHtml += '</tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
		},
		async : false
	});
	
	myPage();
	
	$('#myModal').modal('show');
	
});

var tableFun = function(url,tdata){
	$
	.ajax({
		type : "POST",
		url : url,
		data : tdata,
		success : function(data) {
			var _table = $('#modaltable'), tableBodyHtml = '';
			_pages = data.pages;
			$.each(data.list,function(k,v){
				tableBodyHtml += '<tr class="parent1">';
				tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input type="radio" name="radio" class="sub_radbox"></label></td>';
				tableBodyHtml += '<td title="'+v.bankCode+'">' + v.bankCode+ '</td>';
				tableBodyHtml += '<td title="'+v.bankName+'">' + v.bankName+ '</td>';
				tableBodyHtml += '</tr>';
			});
			_table.find("tbody").html(tableBodyHtml);
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
			var pageData = {
			"pageNo" : p,
			"pageSize" : "10"
			};
		tableFun("/pay/bankability/getBankInfo", pageData);		  
	  }
	});
};

$("#modal_save").click(function(){
	var radio=document.getElementsByName("radio");
    var bankCode= null,bankName = null;
   for(var i=0;i<radio.length;i++){
	   if(radio[i].checked==true) {
		   var obj= radio[i].parentNode.parentNode;
		   var bankCodeObj = obj.nextSibling;
		   var bankNameObj = bankCodeObj.nextSibling;
		   bankCode = bankCodeObj.title;
		   bankName = bankNameObj.title;
		   break;
	   	}
   	} 
   if(bankCode == null || bankName == null)
	   alert("请选择");
   $("#bankCode").val(bankCode);
   $("#bankName").val(bankName);
   $('#myModal').modal('hide');
});