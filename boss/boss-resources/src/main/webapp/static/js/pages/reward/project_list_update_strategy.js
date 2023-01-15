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

var id;var _name;var _id;
$(function() {

    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#strategForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
    //URL参数
    id = Request.id;
    _name = decodeURI(Request.projectName);
    _id = Request.projectId;
    document.getElementById("proName").value = _name;
    var tdUrl = "../reward/project_list/strateg/getById";
    var tbData = {"id":id};
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
            var start = new Date(data.startTime);
            var dateStrFrist = start.getFullYear() + '/' + (start.getMonth()+1) + '/' + start.getDate()+ ' ' +start.getHours() + ':' + start.getMinutes() + ':' + start.getSeconds();
            var end = new Date(data.endTime);
            var dateStrEnd = end.getFullYear() + '/' + (end.getMonth()+1) + '/' + end.getDate()+ ' ' +end.getHours() + ':' + end.getMinutes() + ':' + end.getSeconds();
            //把商品数据,显示在也页面上
            document.getElementById("strategId").value = data.id;
            document.getElementById("projectId").value = data.projectId;
            document.getElementById("name").value = data.name;
            $("#multi").val(""+data.multi+"");
            document.getElementById("startTime").value = dateStrFrist;
            document.getElementById("endTime").value = dateStrEnd;
        }
    });


}

$('#strategSubmit').click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        var formdata = $('#strategForm').serialize();
        $.ajax({
            type: "POST",
            url:"../reward/project_list/strateg/update",
            data:formdata,
            error: function() {
                var projectId = document.getElementById("projectId").value;
                alert("修改失败");
                location = "../reward/project_list_strategy_list.html?projectId="+projectId+"&projectName="+encodeURI(encodeURI(_name));
            },
            success: function(data) {
                var projectId = document.getElementById("projectId").value;
                alert("修改成功");
                location = "../reward/project_list_strategy_list.html?projectId="+projectId+"&projectName="+encodeURI(encodeURI(_name));
            }
        });
    }
})
