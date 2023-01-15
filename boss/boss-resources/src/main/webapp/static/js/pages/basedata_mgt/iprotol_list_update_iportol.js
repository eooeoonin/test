
/*******************************************************************************
 * ** 获取URL参数
 ******************************************************************************/
function GetRequest() {
	var url = location.search; // 获取url中"?"符后的字串
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
	_modalFm1 = $('#form');
	_modalFm1.validationEngine('attach', {
		maxErrorsPerField : 1,
		autoHidePrompt : true,
		autoHideDelay : 2000
	});
	// URL参数
	var id = Request.id;
	// alert(id);
	var tdUrl = "../basedata_mgt/iprotol_list/getById";
	var tbData = {
		"id" : id
	};
	tableFun(tdUrl, tbData);

});

// 编辑
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			// alert(data)
			// 把商品数据,显示在也页面上
			document.getElementById("id").value = data.id;
			document.getElementById("protocolId").value = data.protocolId;
			document.getElementById("type").value = data.type;
			document.getElementById("name").value = data.name;

			var ue = UE.getEditor('miaoshu');
			$("#miaoshu").val(data.template);
			document.getElementById("protocolDesc").value = data.protocolDesc;

			// 第一个单选框，默认选中
			if (data.available == 1) {
				document.getElementsByName("available")[0].checked = "checked"
			}
			if (data.available == 0) {
				document.getElementsByName("available")[1].checked = "checked"
			}

		}
	});

}

$('#update').click(function() {
	if (!_modalFm1.validationEngine('validate')) {
		return false;
	} else {
		var formdata = $('#form').serialize();
		// alert(formdata)
		$.ajax({
			type : "POST",
			url : "../basedata_mgt/iprotol_list/update",
			data : formdata,
			error : function() {
				alert("修改失败");
				location = "../basedata_mgt/iprotol_list.html";
			},
			success : function(data) {
				alert("修改成功");
				location = "../basedata_mgt/iprotol_list.html";
			}
		});
	}
})
// 测试生成testGeneratePDF
$('#testGeneratePDF').click(function() {
	if (!_modalFm1.validationEngine('validate')) {
		return false;
	} else {
		var requestId = $('#id').val();
		$.ajax({
			type : "POST",
			url : "../signet/moulage_record/testGeneratePDF",
			data : {
				"requestId":requestId
			},
			success : function(data) {
				if(data.resCode == 0){
					var moulagePicturefileUrl = domainUrl +'/protocol' + data.data.savePath;
					alert("测试生成协议PDF成功");
					window.open(moulagePicturefileUrl);
				}else{
					alert("测试生成协议PDF失败");
					window.location.reload();
				}
			},
			error : function() {
				alert("测试生成协议PDF失败");
				window.location.reload();
			}
		});
	}
})