$(function(){
	laydate({elem: "#clearDate", format: "YYYY/MM/DD"});
})

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