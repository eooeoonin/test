

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
	var tdUrl = "/gateway/change_code/seletByIdChangeCode";
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
			$("#id").val(data.id);
			$("#channel").val(data.channel);
			$("#business").val(data.business);
			$("#codeName").val(data.codeName);
			$("#direction").val(data.direction);
			$("#outerCode").val(data.outerCode);
			$("#outerInfo").val(data.outerInfo);
			$("#innerCode").val(data.innerCode);
			$("#innerInfo").val(data.innerInfo);
			
			if(data.available){
				$("#usable").attr("checked",true);
			}else{
				$("#disabled").attr("checked",true);
			}
			$("#editedBy").html(data.editedBy);
		}
	});


}	


});
function submitedit2(){
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
		  var formData=$("#form").serialize();
		  var tdUrl = "/gateway/change_code/updateChangeCode";
		$.ajax({    
                type: "POST",
                url:tdUrl,
                data:formData,
                dataType : "json",
                success: function(data) {
                	  alert("修改成功");
                	  location = "../gateway/change_code.html";
                },error: function(data){
        			alert("修改失败");
        		}
		 });
	  }
}