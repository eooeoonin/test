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



$(function() {
    //时间段
    var start = {
        elem: "#startTime", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
            end.min = datas;
            end.start = datas
        }
    };
    var end = {
        elem: "#endTime", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
            start.max = datas
        }
    };
    try {
        laydate(start);
        laydate(end);
    } catch (e) {}

    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#projectForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
    //URL参数
    var id = Request.projectId;
    document.getElementById("departmentId").value = id;
    var tdUrl = "../reward/project_list/getById";
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
            var dateStrFrist = start.getFullYear() + '/' + (start.getMonth()+1) + '/' + start.getDate() + ' ' + start.getHours() + ':' + start.getMinutes() + ':' + start.getSeconds();
            var end = new Date(data.endTime);
            var dateStrEnd = end.getFullYear() + '/' + (end.getMonth()+1) + '/' + end.getDate() + ' ' + end.getHours() + ':' + end.getMinutes() + ':' + end.getSeconds();

            //把商品数据,显示在也页面上
            document.getElementById("name").value = data.name;
            document.getElementById("description").value = data.description;
            document.getElementById("startTime").value = dateStrFrist;
            document.getElementById("endTime").value = dateStrEnd;
            document.getElementById("defaultAward").value = data.defaultAward;
        }
    });
}

$('#projectSubmit').click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{

        var formdata = $('#projectForm').serialize();
        $.ajax({
            type: "POST",
            url:"../reward/project_list/update",
            data:formdata,
            error: function() {
                alert("修改失败");
                location = "../reward/project_list.html";
            },
            success: function(data) {
                alert("修改成功");
                location = "../reward/project_list.html";
            }
        });
    }
})
