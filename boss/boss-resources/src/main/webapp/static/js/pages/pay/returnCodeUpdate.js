

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
	/**
	 * 数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	//URL参数
	var id = Request.id;
	var tdUrl = "/pay/returnCode/seletByIdReturnCode";
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
			$("#id1").val(data.id);
			$("#channelCode").val(data.channelCode);
			$("#channelName").val(data.channelName);
			$("#business").val(data.business);
			$("#transaction").val(data.transaction);
			$("#outerCode").val(data.outerCode);
			$("#outerInfo").val(data.outerInfo);
			$("#innerCode").val(data.innerCode);
			$("#innerInfo").val(data.innerInfo);
			$("#result").val(data.result);
			
			if(data.useState == 1){
				$("#open1").attr("checked",true);
			}else if(data.useState == 0){
				$("#wait1").attr("checked",true);
			}else if(data.useState == -1){
				$("#close2").attr("checked",true);
			}
			$("#editname").html(data.editedBy);
		}
	});


}	


});
function submitedit1(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
		  var formData=$("#form").serialize();
		  var tdUrl = "/pay/returnCode/updateReturnCode";
		$.ajax({    
                type: "POST",
                url:tdUrl,
                data:formData,
                dataType : "json",
                success: function(data) {
                	  alert("修改成功");
                	  location = "../pay/returnCodelist.html";
                },error: function(data){
        			alert("修改失败");
        		}
		 });
	  }
}