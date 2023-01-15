$(function () {

    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 = $('#withdrawForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField: 1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });

    loadPlatformAccounts();



    $("#accountsSelect").change(function(){
        var platformAccountId =$("#accountsSelect").val();
        if(platformAccountId != ""){
            $.ajax({
                type : "GET",
                url : "/platformManager/account/getPlatformAccount",
                data : {userId : platformAccountId},
                success : function(data) {
                    if (data.resCode == 0) {
                        $("#beforeAmount").html((data.data.balance.amount).toFixed(2));
                    }else {
                        bootbox.alert(data.msg);
                        $("#accountsSelect").val('');
                    }
                },
                error : function() {
                    $("#accountsSelect").val('');
                },
                async : false
            });

        }

    });

    $("#withdrawAmount").blur(function(){
        if(validateAmount()){
            $("#afterAmount").html(($("#beforeAmount").html()*1-$("#withdrawAmount").val()*1).toFixed(2));
        }
    });

});
var loadPlatformAccounts = function(){

    $.ajax({
        type : "GET",
        url : "/platformManager/account/getPlatformAccounts",
        data : {},
        success : function(data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
                    var str = "<option value=''>--请选择--</option>";
                    for(var key = 0; key < data.data.length; key++){
                        var id = data.data[key].id;
                        var enterpriseName = data.data[key].enterpriseName;
                        str += "<option value= '"+id+"'>" +enterpriseName+"</option>";
                    }
                    $("#accountsSelect").html(str);
                }
            }
        },
        async : false
    });

}


$("#withdrawSubmit").click(function () {

    if(validateAmount()){
        var withdrawAmount = $("#withdrawAmount").val();
        var platformAccountId =$("#accountsSelect").val();
        $.ajax({
            type: "POST",
            url: "/platformManager/withdraw",
            data: {"userId": platformAccountId, "amount": withdrawAmount},
            success: function (data) {
                if (data.resCode == 0) {
                    withdrawRedirect(data.data);
                } else {
                    bootbox.alert(data.msg);
                }
            },
            async: false
        });
    }

});



function withdrawRedirect(data){
    var htmlStr = "<form id='withdrawSubmitForm' method='"+ data.sendMethod + "' action='" + data.sendUrl +"'>";
    htmlStr +="<input name='serviceName' value='" + data.sendForm.serviceName+"'>";
    htmlStr +="<input name='platformNo' value='" + data.sendForm.platformNo+"'>";
    htmlStr +="<input name='userDevice' value='" + data.sendForm.userDevice+"'>";
    htmlStr +="<input name='keySerial' value='" + data.sendForm.keySerial+"'>";
    htmlStr +="<input name='sign' value='" + data.sendForm.sign+"'>";
    htmlStr +="<input name='reqData' value='" + data.sendForm.reqData+"'>";
    htmlStr +="</from>";
    $("#withdrawFormDiv").html(htmlStr);
    $("#withdrawSubmitForm").submit();
}

function validateAmount(){
    var flag = true;
    if (!$("#withdrawForm").validationEngine('validate')) {
        flag  =false;
    }
    if(($("#beforeAmount").html()-$("#withdrawAmount").val())< 0){
        $("#withdrawAmount").attr("class","form-control validate[required,custom[onlyNumberdouble]],max["+$("#beforeAmount").html()+"]");
        flag = false;
    }


    return flag;
}