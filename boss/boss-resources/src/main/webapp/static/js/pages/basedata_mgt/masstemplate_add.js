
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
	});


	

 


//保存
	function submitBannerInfoTwo(){
	
		
		//判断是否为空
		if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }else{
		var bannerData=$("#form").serialize();
		
		
		  }
	$.ajax({
			type : "POST",
			url : "/basedata_mgt/masstemplate/add",  //保存
			dataType : "json",
			data :bannerData,

			success : function(data) {
				
		      alert("保存成功");
		      location = "../basedata_mgt/masstemplate.html";
			},error: function(){	
				alert("保存失败");
				
			}
			
		});

	}
