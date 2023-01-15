

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

var _id;var _levelNum;
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
	var tdUrl = "../basedata_mgt/provincialcity_list/selectProvincialcity";
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
			document.getElementById("code").value = data.code;
			document.getElementById("name").value = data.name;
			document.getElementById("inCode").value = data.inCode;
			document.getElementById("parentId").value = data.parentId;
			_id = data.parentId;
			document.getElementById("displayOrder").value = data.displayOrder;
			document.getElementById("levelNum").value = data.levelNum;
			_levelNum = data.levelNum;
			document.getElementById("isLeaf").value = data.isLeaf;
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
                url:"../basedata_mgt/provincialcity_list/updateProvincialcity",
                data:$('#form').serialize(),
                error: function() {
                    alert("修改失败");
                    location = "../basedata_mgt/provincialcity_list.html?parentId="+_id+"";
                },
                success: function(data) {
                	  alert("修改成功");
                	  if(_levelNum == 1){
                		  location = "../basedata_mgt/provincialcity_list.html";
                	  }else{
                		  location = "../basedata_mgt/provincialcity_list.html?parentId="+_id+"";
                	  }
                }
		 });
	  }
})
