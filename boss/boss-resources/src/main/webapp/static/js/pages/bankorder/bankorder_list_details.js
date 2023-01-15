/***
 *** URL
 ***/
function GetRequest() {
    var url = location.search; //
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

var _transStatus;
$(function() {
    //URL
    var id = Request.id;
     _transStatus = Request.transStatus;

    var tdUrl = "../bankorder/bankorder_list/selectBankOrderById";
    var tbData = {"id":id};
    tableFun(tdUrl,tbData);

});



function historyGo(){
	window.location.href="../bankorder/bankorder_list.html?transStatus="+_transStatus+"";
}


function tableFun(tdUrl, tbData) {
    $.ajax({
        type : "POST",
        url : tdUrl,
        data : tbData,
        dataType : "json",
        success : function(data) {
            $("#createTime").text(data.createTime);
            $("#tradeTime").text(data.tradeTime);
            $("#indexOrderId").text(data.indexOrderId);
            $("#payAccountName").text(data.payAccountName);
            $("#payAccountNo").text(data.payAccountNo);
            $("#amount").text(data.amount.amount);
            $("#userId").text(data.userId);
            $("#username").text(data.username);
        }
    });
}

