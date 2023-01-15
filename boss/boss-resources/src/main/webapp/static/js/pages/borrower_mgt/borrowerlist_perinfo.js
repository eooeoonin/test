var isinit = _getParamsObject().isInit;
var userId = _getParamsObject().userId;
//if(isinit != 'yes'){
//	$(".jin").attr('readOnly',true);
//	$(".jins").attr('disabled',true);
//}else{
//	$(".jin").attr('readOnly',false);
//	$(".jins").attr('disabled',false);
//}
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
$.ajax({
	type : "POST",
	url : "/borrower/borroweradd/getBankInfo",
	data : {},
	success : function(data) {
		if (data != null && data != "") {
			if (data.resCode == 0) {
				var str = "<option value=''>--请选择--</option>";
				var _list = data.data.businessObject.list;
				for(var key = 0; key < _list.length; key++){
					var bankName = _list[key].bankName;
					var bankCode = _list[key].bankCode;
					str += "<option value= '"+bankCode+"'>" +bankName+"</option>";
				}
				$("#bankCode").html(str);
			}else{
				bootbox.alert(data.msg);
			}
		}
	},
	async : false
});


$("#borrowerSubmit").click(function() {
    if (!$("#addBorrowerForm").validationEngine('validate')) {
        return false;
    }
console.log($("#addBorrowerForm").serialize());
    $.ajax({
        type : "POST",
        url: "/borrower/borrowerlist/editPersonInfo",
        dataType: "json",
        async : false,
        data : $("#addBorrowerForm").serialize(),
        success : function(data) {
            var resultCode = data.resCode;
            var resultCodeMsg = data.msg; //返回信息
            if (resultCode === 0) {
                bootbox.alert("操作成功", function(){
                	window.location.href = "/borrower/borrowerlist.html";
                });
            }else{
                bootbox.alert(resultCodeMsg);
            }
        }, error: function (data) {  //token过期处理
            tkFailFun(data);
        }
    });
});

$(function () {
	$.ajax({
		type : "POST",
		url : "/borrower/borrowerlist/getPersonInfo",
		data : {'userId':userId},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					var data1 = data.data[0].businessObject;
					var data2 = data.data[1].businessObject;
					var data3 = data.data[2].businessObject;
					var data4 = data.data[3].businessObject;
				$("#id").val(data1.id);	
				$("#registerMobile").val(data1.registerMobile);	
				$("#email").val(data3.email);	
				$("#userRole").val(data2.roleCode);	
				if(data1.userType != "" && data1.userType != null){
					if(data1.userType == 'ENTERPRISE'){
						$("#userAtt").val('企业');	
					}else if(data1.userType == 'PERSON'){
						$("#userAtt").val('个人');	
					}else{
						$("#userAtt").val('——');	
					}
				}else{
					$("#userAtt").val('——');
				}
				$("#realName").val(data1.realName);	
				$("#bankMobile").val(data4.bankMobile);	
				$("#certNo").val(data1.certNo);	
				$("#bankCardNo").val(data4.bankCardNo);	
				$("#bid").val(data4.id);	
				$("#bankCode").val(data4.bankCode);	
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
	
});