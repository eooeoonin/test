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
	$("#id").val(id);



	
	var tdUrl = "/basedata_mgt/TemplatePara/templateById";
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
		dataType : "json",
		
		success : function(data) {
			$("#name").val(data.data.name);
			$("#code").val(data.data.code);
			$("#content").val(data.data.content);
		}
	});
}

//修改
function submitedit() {
	
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	  }

	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/TemplatePara/templateEdit",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/TemplatePara.html";
		}
			
	});
}

