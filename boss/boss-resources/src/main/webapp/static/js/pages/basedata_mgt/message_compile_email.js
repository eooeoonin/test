
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
var Request = {};
Request = GetRequest();

$(function() {

	  /***
	   *功能说明：编辑器
	   *参数说明：
	   *创建人：
	   *时间：
	   ***/
/*	var ue = UE.getEditor('content');*/
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
	var tdUrl = "/basedata_mgt/message/message_compile";
	var tbData = {
		"id":id
		
	};
	tableFun(tdUrl,tbData);
	
});


//编辑
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		async : false,
		dataType : "json",
		success : function(data){
			$("#key").val(data.key);
			$("#id").val(data.id);
			//$("#open").val(data.open);
			if(data.open == 1){
				$("#open").attr("checked",true);
			}else if(data.open == 0){
				$("#close").attr("checked",true);
			}
			$("#name").val(data.subject);
			$("#content").val(data.content);
			$("#type").val(data.type);
			$("#merchant1").val(data.merchant);
			$("#subject").val(data.subject);
			
		}
	});
	 

}
function fanhui(){
	location = "../basedata_mgt/message.html";
}
//修改
function submitedit() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/message/message/edit",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/message.html";
		},error: function(){
			alert("修改失败");
			location = "../basedata_mgt/message.html";
		}
			
	});
	  }
}

