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

var _id;var _name;
$(function () {
    _id = Request.projectId;
    _name = decodeURI(Request.projectName);
    document.getElementById("proName").value = _name;
    document.getElementById("projectId").value = Request.projectId;
    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#strategForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
});

$("#strategSubmit").click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        $.ajax({
            type:"POST",
            url:"../reward/project_list/strateg/insert",
            data:$("#strategForm").serialize(),
            dataType: "json",
            success:function(data){
                alert("添加成功")
                location = "../reward/project_list_strategy_list.html?projectId="+_id+"&projectName="+encodeURI(encodeURI(_name));
            },error:function(){
                alert("添加失败")
                location = "../reward/project_list_strategy_list.html?projectId="+_id+"&projectName="+encodeURI(encodeURI(_name));
            }
        })
    }
});