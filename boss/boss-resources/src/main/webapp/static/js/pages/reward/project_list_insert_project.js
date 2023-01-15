
$(function() {
    _modalFm1 =  $('#form');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });

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

});


$("#projectSubmit").click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        var data = $("#form").serialize();
        $.ajax({
            type:"POST",
            url:"../reward/project_list/insert",
            data:data,
            dataType: "json",
            success:function(data){
                alert("添加成功")
                location = "../reward/project_list.html";
            },error:function(){
                alert("添加失败")
                location = "../reward/project_list.html";
            }
        })
    }
});
