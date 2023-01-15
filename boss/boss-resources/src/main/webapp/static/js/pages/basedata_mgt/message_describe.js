
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
	//URL参数
	var id = Request.id;

	

	
	var tdUrl = "/basedata_mgt/message_system/message_compile";
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
		success : function(data){
			
			document.getElementById("id").value = data.id;
			document.getElementById("open").value = data.open;
			document.getElementById("name").value = data.subject;
			document.getElementById("miaoshu").value = data.content;
			document.getElementById("key").value = data.key;
			document.getElementById("type").value = data.type;
			document.getElementById("merchant").value = data.merchant;
		}
	});


}