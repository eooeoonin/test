function _getParamsObject() {
	var thisURL = document.URL;
	var getval = thisURL.split('?')[1];
	var paramsArray = getval.split("&");
	var paramsObject = {};
	for (var i = 0; i < paramsArray.length; i++) {
		var keyAndValue = paramsArray[i].split("=");
		paramsObject[keyAndValue[0]] = keyAndValue[1];
	}
	return paramsObject;
}

$(function() {
	
	var productId =  _getParamsObject().productId;
	$("#productId").val(productId);
	 //时间段
	  var start = {
	    elem: "#startTime", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
	      end.min = datas;
	      end.start = datas
	    }
	  };
	  var end = {
	    elem: "#endTime", format: "YYYY/MM/DD 23:59:30", istime: true, istoday: false, choose: function (datas) {
	      start.max = datas
	    }
	  };
	  
	  laydate(start);
	  laydate(end);
	  
	//编辑器
	var ue = UE.getEditor('content');
	
	  //查询协议信息
	$.ajax({
		type : "POST",
		url : "/crowdfunding/product/protocolList",
		data : {},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var str = "";
					var _list = data.data.businessObject;
					for(var key = 0; key < _list.length; key++){
						var protocolId = _list[key].id;
						var protocolName = _list[key].name;
						str += "<option value= '"+protocolId+"'>" +protocolName+"</option>";
					}
					$("#protocolNameSelect").html(str);
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
	
	
	$("#sub").click(function(){
		$("#subproductForm").validationEngine('attach',{
			inlineValidation: false,
			maxErrorsPerField:1,
			autoHidePrompt: true,
			autoHideDelay: 2000
		});
		
		if (!$("#subproductForm").validationEngine('validate')) {
			$("#subproductForm").validationEngine('detach');
			 return false;
		}
		  
		var maxActivityNo = $("#maxActivityNo").val()*1;
		var userCount = $("#userCount").val()*1;
		
		if(userCount>maxActivityNo){
			alert("用户最大购买份数不能超过总份数");
			return;
		}
		if (ue && ue.getContent() == "") {
			alert("内容不能为空");
			return false;
		}
		if(ue.getContentLength(true) > 5000){
			alert("内容长度不能超过5000");
			return false;
		}
		
		
		$.ajax({
			type : "POST",
			url : "/crowdfunding/product/addSub",
			data : $("#subproductForm").serialize(),
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						location = "/crowdfunding/product.html";
					}else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});
		
		
	})
	
	
});
