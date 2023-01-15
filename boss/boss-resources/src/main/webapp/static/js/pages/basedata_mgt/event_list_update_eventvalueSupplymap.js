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

	var tdUrl = "../basedata_mgt/event_list/selectEventValueSupplyMapById";
	var tbData = {"id":id};
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
			document.getElementById("eventMapId").value = data.eventMapId;
			document.getElementById("name").value = data.name;
			document.getElementById("value").value = data.value;
		}
	});


}	






$('#updateLoan').click(function(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formdata = $('#form').serialize();
		$.ajax({
                type: "POST",
                url:"../basedata_mgt/event_list/updateEventValueSupplyMap",
                data:formdata,
                error: function() {
                    alert("修改失败");
                    var empValueId = document.getElementById("eventMapId").value;
              	    aa(eventMapId.value,1);
              	    var empId = eventMapId.value;
              	    location = "../basedata_mgt/event_list.html?eventMapId="+empId+"";
                },
                success: function(data) {
                	  alert("修改成功");
                	  var empValueId = document.getElementById("eventMapId").value;
                	  var empId = eventMapId.value;
                	  location = "../basedata_mgt/event_list.html?eventMapId="+empId+"";
                }
		 });
	  }
})
