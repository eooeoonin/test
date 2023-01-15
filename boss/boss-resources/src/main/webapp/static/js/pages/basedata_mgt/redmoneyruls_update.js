

/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  		url = decodeURIComponent(url);
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
    }
  }
  return theRequest;
}  

var Request = {};
Request = GetRequest();


$(function() {
	

//	 laydate({elem: "#createTime1", format: "YYYY-MM-DD hh:mm:ss",istime: true});
//	  laydate({elem: "#modifyTime1", format: "YYYY-MM-DD hh:mm:ss",istime: true});
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	//URL参数
	var id = Request.id;
	var tdUrl = "/basedata_mgt/redmoneyrules/redmoneyRuleselect";
	var tbData = {
		"id":id
	};
	tableFun(tdUrl,tbData);

//编辑回显
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			$("#id2").val(data.id);
			$("#merchantCode1").val(data.merchantCode);
			$("#merchantName1").val(data.merchantName);
			
			//$("#modifyTime1").val(data.modifyTime);
			
//			var myDate = new Date();
//			var year = myDate.getFullYear();
//			var month = myDate.getMonth()+1;
//			var day = myDate.getDate();
//			var hour = myDate.getHours();
//			var min = myDate.getMinutes();     //获取当前分钟数(0-59)
//			var sec = myDate.getSeconds();     //获取当前秒数(0-59)
//			var aa = year+'-'+month+'-'+day+' '+hour+':'+min+':'+sec;
//			
//			if(data.createTime!=null){
//				$("#createTime1").val(data.createTime);
//			}else {
//				$("#createTime1").val(new Date(aa));
//			}
//			if(data.modifyTime!=null){
//				$("#modifyTime1").val(data.modifyTime);
//			}else {
//				$("#modifyTime1").val(new Date(aa));
//			}
			
			//$("#yin1").hide;
			$("#type1").val(data.type);
			if(data.type=="random_invest"){
				$(".oicd").show();
			}else{
				$(".oicd").hide();
			}
			if(data.isuse == 1){
				$("#open").attr("checked",true);
			}else if(data.isuse == 0){
				$("#close").attr("checked",true);
			}
			$("#amount1").val(data.amount.amount);
			$("#useRange1").val(data.useRange);
			$("#pushWay1").val(data.pushWay);
			if(data.isPush == 1){
				$("#open1").attr("checked",true);
			}else if(data.isPush == 0){
				$("#close1").attr("checked",true);
			}
			$("#minAmount1").val(data.minAmount.amount);
			$("#maxAmount1").val(data.maxAmount.amount);
			$("#validDate1").val(data.validDate);
			$("#ratio1").val(data.ratio);
			$("#redmoneyConut1").val(data.redmoneyConut);
			//$("#formula1").val(data.formula);
			$("#mark1").val(data.mark);
			$("#editedBy1").text(data.editedBy);
		}
	});


}	


});
function submitedit() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
		  var formData=$("#form").serialize();
		  var tdUrl = "/basedata_mgt/redmoneyrules/redrileedit";
		$.ajax({    
                type: "POST",
                url:tdUrl,
                data:formData,
                dataType : "json",
                success: function(data) {
                	  alert("修改成功");
                	  location = "../basedata_mgt/redmoneyrules_list.html";
                },error: function(data){
        			alert("修改失败");
        			//location = "../basedata_mgt/redmoneyrules_list.html";
        		}
		 });
	  }
}