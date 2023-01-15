
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

//	alert(id);

	
	var tdUrl = "/basedata_mgt/masstemplate/one";
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
			
		
		//把商品数据,显示在页面上
			
			
			document.getElementById("title").value = data.title;

			document.getElementById("content").value = data.content;
	
		}
	});
}
//修改保存
function submitBannerInfoTwo() {
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{

	document.getElementById("id").value=Request.id;
	var formData=$("#form").serialize();
	  }
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/masstemplate/update",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/masstemplate.html";
		},error: function(){
			alert("修改失败");
			location = "../basedata_mgt/masstemplate.html";
		}
			
	});
}

