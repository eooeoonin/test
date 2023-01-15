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
	var thisURL = document.URL;    
	var getval =thisURL.split('?')[1];  
	var id= getval.split("=")[1];  
	showvalf();
	function  showvalf(){ 
	   document.getElementById('id').value= id;  
	   document.getElementById('ids').value= id;  
	};
	var srhData = {
			"id" : id
		};
	tableFun("/pay/bankability/getBankAbilityById",srhData);

});
var banknmber;
function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {	
					var aa= data.available;
					var str1="true";
					if(aa == true){
						$("#open").attr("checked",true);
					}else{
						$("#close1").attr("checked",true);
					}
					banknmber = data.bankCode;
					
					if(data.channel != null)
						$("#channel").val(data.channel);
					if(data.business != null)
						$("#business").val(data.business);
					if(data.merchant!=null)
						$("#merchant").val(data.merchant);
					if(data.bankName != null)
						$("#bankName").val(data.bankName);
					if(data.bankCode != null)
						$("#bankCode").val(data.bankCode);
					if(data.optimalRangeMin != null)
						$("#optimalRangeMin").val(data.optimalRangeMin);
					if(data.optimalRangeMax != null)
						$("#optimalRangeMax").val(data.optimalRangeMax);
//					if(data.available != null)
//						$("#available").val(data.available);
					if(data.singleQuota != null)
						$("#singleQuota").val(data.singleQuota);
					if(data.dayQuota!= null)
						$("#dayQuota").val(data.dayQuota);
					if(data.monthQuota != null)
						$("#monthQuota").val(data.monthQuota);	
					if(data.quotaDesc != null)
						$("#quotaDesc").val(data.quotaDesc);
					if(data.remark != null)
						$("#remark").val(data.remark);
					if(data.bankCode != null)
						$("#rank").val(data.rank);
				}
			});
};

$("#editBtn").click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
//	if (!$("#form").validationEngine('validate')) {
//	    return false;
//	  }
	var formData=$("form").serialize();
	$
	.ajax({
		type : "POST",
		url : "/pay/bankability/updateBankAbility",
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
				if(banknmber==v.bankCode){
					$("#open1").attr("checked",true);
				}
				tableBodyHtml += '<tr class="parent1">';
				tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input type="radio" id="open1" name="radio"></label></td>';//class="sub_radbox"
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
	//banknmber
	
});

var tableFun1 = function(url,tdata){
	$
	.ajax({
		type : "POST",
		url : url,
		data : tdata,
		success : function(data) {
			var _table = $('#modaltable'), tableBodyHtml = '';
			_pages = data.pages;
			$.each(data.list,function(k,v){
				if(banknmber==v.bankCode){
					$("#open1").attr("checked",true);
				}
				tableBodyHtml += '<tr class="parent1">';
				tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input type="radio" id="open1" name="radio"></label></td>';//class="sub_radbox"
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
		tableFun1("/pay/bankability/getBankInfo", pageData);		  
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