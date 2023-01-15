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



	
	var tdUrl = "/basedata_mgt/sms_service_provider_list/Agency/ById";
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
		
		//把商品数据,显示在也页面上
			document.getElementById("id").value = data.id;
			document.getElementById("name").value = data.name;
			document.getElementById("channelCode").value = data.channelCode;
			
			document.getElementById("price").value = data.price;
			document.getElementById("stableLevel").value = data.stableLevel;
			
			document.getElementById("speed").value = data.speed;
			document.getElementById("maxPhones").value = data.maxPhones;
			
			document.getElementById("level").value = data.level;
			document.getElementById("open").value = data.open;
			
			
			
		}
	});
}

//修改
function submitCardBinTwo() {
	
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	  }

	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/sms_service_provider_list/Agency/edit",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/sms_service_provider_list.html";
		}
			
	});
}
