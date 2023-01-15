

/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  		url = decodeURIComponent(url);
  var theRequest = {};
  if (url.indexOf("?") !== -1) {
    var str = url.substr(1);
   var  strs = str.split("&");
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
	var tdUrl = "../basedata_mgt/operatesystemconfig_list/selectIsystemConfig";
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
			document.getElementById("codeKey").value = data.codeKey;
			document.getElementById("codeValue").value = data.codeValue;
			document.getElementById("codeDesc").value = data.codeDesc;
			document.getElementById("systemCode").value = data.systemCode;
			//第一个单选框，默认选中
			if(data.available==1){
				$("#open").attr("checked",true);
			}else if(data.available==0){
				$("#close1").attr("checked",true);
			}
		
		}
	});


}	






$('#updateLoan').click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
		  $.ajax({
                cache: true,
                type: "POST",
                url:"../basedata_mgt/operatesystemconfig_list/updateIsystemConfig",
                data:$('#form').serialize(),
                async: false,
                error: function() {
                    alert("修改失败");
                    location = "../basedata_mgt/operatesystemconfig_list.html";
                },
                success: function(data) {
                	  alert("修改成功");
                	  location = "../basedata_mgt/operatesystemconfig_list.html";
                }
		 });
	  }
})
