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
  elem: "#availableTimeBefore", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: true, choose: function (datas) {
    end.min = datas;
    end.start = datas;
  }
};
var end = {
  elem: "#availableTimeAfter", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: true, choose: function (datas) {
    start.max = datas;
  }
};
var createTime = {
	elem: "#createTime", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: true, choose: function (datas) {
		start.max = datas;
	}
};
laydate(start);
laydate(end);
laydate(createTime);

_modalFm1 =  $('#form');
_modalFm1.validationEngine('attach', {
	maxErrorsPerField:1,
	autoHidePrompt: true,
	autoHideDelay: 2000
});

$('input[type="radio"][name="platform"]').click(function () {
	if ($('input[type="radio"][name="platform"]:checked').val() == 'PC') {
		$("#isRollTopTr").hide();
		$("#availableTimeTr").show();
	} else {
		$("input[name='isRollTop'][value='0']").prop("checked", true);  
		$("#isRollTopTr").show();
		$("#availableTimeBefore").val('');
		$("#availableTimeAfter").val('');
		$("#availableTimeTr").hide();
	}
});

$('input[type="radio"][name="isRollTop"]').click(function () {
	if ($('input[type="radio"][name="isRollTop"]:checked').val() == '1') {
		$("#availableTimeTr").show();
	} else {
		$("#availableTimeBefore").val('');
		$("#availableTimeAfter").val('');
		$("#availableTimeTr").hide();
	}
});

});



$(function() {
	//URL参数
	var id = Request.id;

//	alert(id);

	
	var tdUrl = "/basedata_mgt/pc_notice_list/notice/one";
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
			 var availableTimeBefore = _data.availableTimeBefore;
			 var availableTimeAfter = _data.availableTimeAfter;
			 var createTime = _data.createTime;
			 if (_data.platform == 'PC') {
			 		$("input[name='platform'][value='PC']").prop("checked", true); 
			 		$("#isRollTopTr").hide();
			 	} else {
			 		$("input[name='platform'][value='PCLIST']").prop("checked", true);
			 		if (_data.isRollTop == '1') {
			 			$("input[name='isRollTop'][value='1']").prop("checked", true);
			 		} else {
			 			$("input[name='isRollTop'][value='0']").prop("checked", true);
			 			availableTimeBefore = '';
			 			availableTimeAfter = '';
			 			$("#availableTimeTr").hide();
			 		}
			 	}
			document.getElementById("id").value = _data.id;
			document.getElementById("title").value = _data.title;
//			document.getElementById("body").value = _data.body;
			var ue = UE.getEditor('body', {toolbars: [
			    ['fullscreen', 'source', 'undo', 'redo'],
			    ['bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
			]});
			$("#body").val(_data.body);
			
		if(availableTimeAfter != null && availableTimeAfter != '') {
            var date = new Date(_data.availableTimeAfter);
            var availableTimeAfter = date.getFullYear() + '/' +( date.getMonth()+1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
		}
		if(availableTimeBefore != null && availableTimeBefore != '') {
            var date = new Date(_data.availableTimeBefore);
            var availableTimeBefore = date.getFullYear() + '/' + ( date.getMonth()+1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
		}
		if(createTime != null && createTime != '') {
			var date = new Date(_data.createTime);
			var createTime = date.getFullYear() + '/' + ( date.getMonth()+1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
		}
			
			document.getElementById("availableTimeAfter").value = availableTimeAfter;
			document.getElementById("availableTimeBefore").value = availableTimeBefore;
			document.getElementById("createTime").value = createTime;
			
			
			
		}
	});
}

//修改
function submitNoticTwo() {
	if (!$("#form").validationEngine('validate')) {
		return false;
	};
	if( $("#availableTimeBefore").val() == ""){
		$("#availableTimeBefore").val(new Date());
	}
	if( $("#availableTimeAfter").val() == ""){
		$("#availableTimeAfter").val(new Date());
	}	
	if( $("#createTime").val() == ""){
		$("#createTime").val(new Date());
	}	
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
		url : "/basedata_mgt/pc_notice_list/notice/update",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "pc_notice_list.html";
		},error: function(){
			alert("修改失败");
			location = "pc_notice_list.html";
		}
			
	});
}