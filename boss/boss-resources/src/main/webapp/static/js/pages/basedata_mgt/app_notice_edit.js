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

$(function () {
/***
 *功能说明：时间插件
 *参数说明：
 *创建人：李波涛
 *时间：2016-07-15
 ***/
//单个时间引用方式


//时间段引用方式availableTimeBefore
var start = {
  elem: "#availableTimeBefore", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
    end.min = datas;
    end.start = datas;
  }
};
var end = {
  elem: "#availableTimeAfter", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
    start.max = datas;
  }
};
laydate(start);
laydate(end);

_modalFm1 =  $('#form');
_modalFm1.validationEngine('attach', {
	maxErrorsPerField:1,
	autoHidePrompt: true,
	autoHideDelay: 2000
});

});



$(function() {
	//URL参数
	var id = Request.id;

//	alert(id);

	
	var tdUrl = "/basedata_mgt/app_notice_list/notice/one";
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
			 var _data = eval('(' + data + ')');
			 	if (_data.platform == 'APP') {
			 		document.getElementsByName("platform")[0].checked="checked";
			 	} else if (_data.platform == 'H5') {
			 		document.getElementsByName("platform")[1].checked="checked";
			 	}
//				if(_data.available==1){
//					document.getElementsByName("available")[0].checked="checked";
//				}else{
//					document.getElementsByName("available")[1].checked="checked";
//				}
			document.getElementById("id").value = _data.id;
			document.getElementById("title").value = _data.title;
//			document.getElementById("body").value = _data.body;
			var ue = UE.getEditor('body', {toolbars: [
			    ['fullscreen', 'source', 'undo', 'redo'],
			    ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
			]});
			$("#body").val(_data.body);
			
			if(_data.availableTimeAfter != null) {
            var date = new Date(_data.availableTimeAfter);
            var availableTimeAfter = date.getFullYear() + '/' +( date.getMonth()+1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
		}
		if(_data.availableTimeBefore != null) {
            var date = new Date(_data.availableTimeBefore);
            var availableTimeBefore = date.getFullYear() + '/' + ( date.getMonth()+1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
		}
			
			document.getElementById("availableTimeAfter").value = availableTimeAfter;
			document.getElementById("availableTimeBefore").value = availableTimeBefore;
			
			
			
		}
	});
}

//修改
function submitNoticTwo() {
	if (!$("#form").validationEngine('validate')) {
		return false;
	};
	
	var formData=$("#form").serialize();

//	if( document.getElementById("availableTimeAfter").value==""|| document.getElementById("availableTimeBefore").value==""){
//		alert("有效时间不能为空!!!请注意!");
//		return false;
//	}
//	alert(document.getElementById("availableTimeAfter").value);
	var ue = UE.getEditor('body');
	var bodyContent = ue.getContent();
	if(bodyContent.length < 1){
		alert("公告正文不能为空");
		return false;
	}
	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/app_notice_list/notice/update",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "app_notice_list.html";
		},error: function(){
			alert("修改失败");
			location = "app_notice_list.html";
		}
			
	});
}