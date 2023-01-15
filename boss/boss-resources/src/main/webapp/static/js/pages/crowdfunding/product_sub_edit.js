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
	 //时间段
	  var start = {
	    elem: "#startTime", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
	      end.min = datas;
	      end.start = datas
	    }
	  };
	  var end = {
	    elem: "#endTime", format: "YYYY/MM/DD 23:59:59", istime: true, istoday: false, choose: function (datas) {
	      start.max = datas
	    }
	  };
	  
	  laydate(start);
	  laydate(end);
	  
	  var paramsObject = _getParamsObject();
	var subProductId =  paramsObject.subProductId;
	$("#productId").val(paramsObject.productId);
	 
	  
	//编辑器
	var ue = UE.getEditor('content');
	
	  //查询权益信息
	$.ajax({
		type : "POST",
		url : "/crowdfunding/product/getSubProduct",
		data : {"subProductId" : subProductId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var _data = data.data;
					$("#id").val(_data.id);//编辑时回显id
					$("#title").val(_data.title);//权益名称
					$("#typeSelect").val(_data.type);//权益类型
					$("#trait").val(_data.trait);//权益简介
					$("#price").val(_data.price.amount);//　金额
					$("#maxActivityNo").val(_data.maxActivityNo);//　最大购买份数
					$("#userCount").val(_data.userCount);//　单用户购买份数
					$("#splitAmount").val(_data.splitAmount.amount);//　拆分付款最小金额
					$("#content").val(_data.description);	//编辑器内容
					$("#startTime").val(moment(_data.startTime).format("YYYY/MM/DD HH:mm:ss"));//开始时间
					$("#endTime").val(moment(_data.endTime).format("YYYY/MM/DD HH:mm:ss"));//结束时间
					$("#isrefundSelect").val(_data.isrefund);
					$("#issplitSelect").val(_data.issplit);
					_protocolSelected(_data.protocolId);
					if("0"== _data.editFlag){
					   $("#typeSelect").attr("disabled", "disabled");
					   $("#startTime").attr("disabled", "disabled");
					   $("#endTime").attr("readonly", "readonly");
					   $("#price").attr("disabled", "disabled");
					   $("#maxActivityNo").attr("disabled", "disabled");
					   $("#userCount").attr("disabled", "disabled");
					   $("#isrefundSelect").attr("disabled", "disabled");
					   $("#issplitSelect").attr("disabled", "disabled");
					   $("#protocolNameSelect").attr("disabled", "disabled");
					   $("#splitAmount").attr("disabled", "disabled");
					}
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
						location = "/crowdfunding/product_info_view.html?productId=" + $("#productId").val();
					}else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});
		
		
	})
	
});


//选中当前协议
function _protocolSelected(_protocolId){
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
						if(_protocolId == protocolId){
							str += "<option value= '"+protocolId+"' selected='selected'>" +protocolName+"</option>";
						}else
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
}
