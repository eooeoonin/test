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
var _proId;var id;var _message;var _msgKey;var tdUrl;var tdUrl1;var tbData1;
$(function() {
    //URL参数
    id = Request.jackpotId;
    _proId = Request.projectId;
    tdUrl = "../reward/project_list/jackpot/insertNews";

    //URL参数
    tdUrl1 = "../reward/project_list/jackpot/listNews";
    tbData1 = {"poolId":id,"pageSize":"10","pageNo":"1"};
    tableFun(tdUrl1,tbData1);
});


function tableFun(tdUrl, tbData) {
    $.ajax({
        type : "POST",
        url : tdUrl1,
        data : tbData1,
        dataType : "json",
        success : function(data) {

            $.each(data.list, function (k, v) {
                //获取数据
                var u_msgKey = v.msgKey,
                    u_msgType = v.msgType

                if(document.getElementById("SEND_INNER_MESSAGE").value == u_msgType){
                    document.getElementById("msgType1").value = u_msgKey;
                    document.getElementById("SEND_INNER_MESSAGE").checked = true;
                }
                if(document.getElementById("SEND_SMS").value == u_msgType){
                    document.getElementById("msgType2").value = u_msgKey;
                    document.getElementById("SEND_SMS").checked = true;
                }
                if(document.getElementById("SEND_PUSH").value == u_msgType){
                    document.getElementById("msgType3").value = u_msgKey;
                    document.getElementById("SEND_PUSH").checked = true;
                }

            });
        }
    });
}
var _data;
$("#strategSu").click(function(){

    $.ajax({
        type : "POST",
        url : tdUrl1,
        data : tbData1,
        dataType : "json",
        success : function(data) {
            _data = data;
            if(data.list.length != 0){
                $.each(data.list, function (k, v) {
                    //获取数据
                    var u_id = v.id,
                        u_msgKey = v.msgKey,
                        u_msgType = v.msgType

                    $.ajax({
                        type : "POST",
                        url : "../reward/project_list/jackpot/deleteNews",
                        data : {"id":u_id},
                        dataType : "json",
                        success : function(data) {
                            if((_data.list.length - 1)  == k){
                                insertNews();
                            }
                        }
                    });
                });
            }else{
                insertNews();
            }
        }
    });

});

function insertNews(){
    if(document.getElementById("SEND_INNER_MESSAGE").checked){
        _msgKey = document.getElementById("msgType1").value;
        _message = document.getElementById("SEND_INNER_MESSAGE").value;
        var tbData = {"poolId":id,
            "msgKey":_msgKey,
            "msgType":_message
        };
        $.ajax({
            type:"POST",
            url:tdUrl,
            data:tbData,
            dataType: "json",
            success:function(data){
                if(document.getElementById("SEND_SMS").checked){
                    sendsms();
                }else{
                    alert("添加成功")
                    location = "../reward/project_list_jackpot_list.html?projectId="+_proId+"";
                }
            },error:function(){
                alert("添加失败")
                location = "../reward/project_list_jackpot_list.html?projectId="+_proId+"";
            }
        })
    }else if(document.getElementById("SEND_SMS").checked){
        sendsms();
    }else if(document.getElementById("SEND_PUSH").checked){
        sendpush();
    }
}

function sendsms(){
    _msgKey = document.getElementById("msgType2").value;
    _message = document.getElementById("SEND_SMS").value;
    var tbData = {"poolId":id,
        "msgKey":_msgKey,
        "msgType":_message
    };
    $.ajax({
        type:"POST",
        url:tdUrl,
        data:tbData,
        dataType: "json",
        success:function(data){
            if(document.getElementById("SEND_PUSH").checked){
                sendpush();
            }else{
                alert("添加成功")
                location = "../reward/project_list_jackpot_list.html?projectId="+_proId+"";
            }
        },error:function(){
            alert("添加失败")
            location = "../reward/project_list_jackpot_list.html?projectId="+_proId+"";
        }
    })
}

function sendpush(){
    _msgKey = document.getElementById("msgType3").value;
    _message = document.getElementById("SEND_PUSH").value;
    var tbData = {"poolId":id,
        "msgKey":_msgKey,
        "msgType":_message
    };
    $.ajax({
        type:"POST",
        url:tdUrl,
        data:tbData,
        dataType: "json",
        success:function(data){
            alert("添加成功")
            location = "../reward/project_list_jackpot_list.html?projectId="+_proId+"";
        },error:function(){
            alert("添加失败")
            location = "../reward/project_list_jackpot_list.html?projectId="+_proId+"";
        }
    })
}
	
