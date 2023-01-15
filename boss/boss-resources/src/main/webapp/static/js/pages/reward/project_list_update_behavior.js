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
var _pages = 0;
var Request = {};
Request = GetRequest();

$(function() {
    document.getElementById("awardStrategyId").value = Request.awardStrategyId;
    document.getElementById("projectId").value = Request.projectId;

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
    _modalFm1 =  $('#behaviorForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
    //URL参数
    var id = Request.behaviorId;
    var tdUrl = "../reward/project_list/behavior/getById";
    var tbData = {"id":id};
    tableFun(tdUrl,tbData);


    // 选择关联类型时
    $('#associationType').change(function(){
        var associationType = $("#associationType option:selected")[0].value;
        if(associationType != null && associationType != "") {
            if("ACTIVITY" == associationType){
                $("#_activity_show").show();
                $("#_channel_show").hide();
                $("#channelSelectedCode").val("");
            }else if("CHANNEL" == associationType){
                $("#_channel_show").show();
                $("#_activity_show").hide();
                $("#activitySelectedCode").val("");
            }
        }
    });


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
            document.getElementById("id").value = data.id;
            document.getElementById("awardStrategyId").value = data.strategyId;
            $("#actCode").val(""+data.actCode+"");
            $("#first").val(""+data.first+"");
            $("#single").val(""+data.single+"");
            $("#userGrade").val(""+data.userGrade+"");
            document.getElementById("min").value = data.min;
            document.getElementById("max").value = data.max;
            handleAssociationType(data);
        }
    });
}

function handleAssociationType(data) {
    if(data.activityCode != null && data.activityCode != "" && data.activityCode != undefined){
        $("#associationType").val("ACTIVITY");
        $("#_activity_show").show();
        $("#_channel_show").hide();
        $("#activitySelected").html(data.activityCode);
        $("#activitySelectedCode").val(data.activityCode);
    }else{
        $("#associationType").val("CHANNEL");
        $("#_channel_show").show();
        $("#_activity_show").hide();
        $("#channelSelected").html(data.channelCode);
        $("#channelSelectedCode").val(data.channelCode);
    }


}


$('#behaviorSubmit').click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        var formdata = $('#behaviorForm').serialize();
        $.ajax({
            type: "POST",
            url:"../reward/project_list/behavior/update",
            data:formdata,
            error: function() {
                var awardStrategyId = document.getElementById("awardStrategyId").value;
                var projectId = document.getElementById("projectId").value;
                alert("修改失败");
                location = "../reward/project_list_behavior_list.html?awardStrategyId="+awardStrategyId+"&projectId="+projectId+"";
            },
            success: function(data) {
                var awardStrategyId = document.getElementById("awardStrategyId").value;
                var projectId = document.getElementById("projectId").value;
                alert("修改成功");
                location = "../reward/project_list_behavior_list.html?awardStrategyId="+awardStrategyId+"&projectId="+projectId+"";
            }
        });
    }
})







//选择活动弹层
$("#checkActivity").click(function () {
    var srhData = {
        "pageNo": "1",
        "pageSize": "10"
    };
    tableFunActivity("/activity/activityManager/getAllActivity", srhData);
    myPage();
    $("#activityModal").modal("show");
    chkFun();
});


//查询活动按钮
$("#activity_srhSmtBtn").click(function(){
    var srhData = {
        "pageNo" : "1",
        "pageSize" : "10",
        "name" : $("#name").val()
    };
    tableFunActivity("/activity/activityManager/getAllActivity", srhData);
    myPage();
    chkFun();

});


var myPage = function() {
    // 分页
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount : _pages,
        current : 1,
        backFn : function(p) {
            // 点击分页事件
            srhData = {
                "pageNo" : p,
                "pageSize" : "10",
            };
            tableFunActivity("/activity/activityManager/getAllActivity", srhData);
            chkFun();
        }
    });
};

function chkFun() {
    $(".i-checks").iCheck({
        checkboxClass: "icheckbox_square-green",
        radioClass: "iradio_square-green"
    });

    var _jCheckAll = $("#jCheckAll"),
        _subCheck = $('input[type="checkbox"].sub_ckbox');
    _jCheckAll.on('ifChecked', function () {
        _subCheck.iCheck('check');
    });
    _jCheckAll.on('ifUnchecked', function () {
        _subCheck.iCheck('uncheck');
    });
};


function tableFunActivity(tdUrl, tbData) {
    $.ajax({
        type: "POST",
        url: tdUrl,
        data: tbData,
        dataType: "json",
        async: false,
        success: function (data) {
            var html = "";
            _pages = data.data[1].pages;
            var _data = data.data[1];
            var templateInfo = data.data[0];
            var _table = $('#activity_table'), tableBodyHtml = '';
            for(var i = 0; i < _data.list.length ; i++ ){
                var v = _data.list[i];
                var tdata = templateInfo[i];
                var id=v.id,code=v.code,name=v.name,desc=v.desc,modifyTime=v.modifyTime,editedBy=v.editedBy,startTime=v.startTime,endTime=v.endTime,tempName=tdata.name,tempType=tdata.type;
                tableBodyHtml += '<tr>';
                tableBodyHtml += '<td><label class=\"radio-inline i-checks\"><input type=\"radio\"  title="" =\"'+ v.name+ '\" value=\"'+ v.code+ '\" name=\"radio\" class=\"sub_radbox\"></label></td>';
                tableBodyHtml += '<td>' + name + '</td>';
                tableBodyHtml += '<td>' + code + '</td>';
                if(tempType == 0){
                    tableBodyHtml += '<td>礼包类</td>';
                }else if(tempType == 1){
                    tableBodyHtml += '<td>抽奖类</td>';
                }else if(tempType == 2){
                    tableBodyHtml += '<td>邀请减免类</td>';
                }else if(tempType == 3){
                    tableBodyHtml += '<td>集采类</td>';
                }else if(tempType == 4){
                    tableBodyHtml += '<td>扫码活动</td>';
                }
                tableBodyHtml += '<td>' + tempName + '</td>';
                tableBodyHtml += '<td>' + startTime + '</td>';
                tableBodyHtml += '<td>' + endTime + '</td>';
            }
            tableBodyHtml += '</tr>';
            $("#tcdPageCode").show();
            _table.find("tbody").html(tableBodyHtml);
            replaceFun(_table);

        }
    });
}


//选择渠道弹层
$("#checkChannel").click(function () {
    $("#channelType").empty();
    $('input:checkbox').removeAttr('checked');

    $.ajax({
        type : "POST",
        url : "/activity/channelManage/getAllChannelType",
        data : {},
        dataType : "json",
        success : function(data) {
            if(data.resCode == 0){
                $.each(data.data, function(k, v) {
                    $("#channelType").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
                });
            }else{
                bootbox.alert("数据加载异常");
            }
        },
        async : false
    });

    var srhData = {
        "pageNo": "1",
        "pageSize": "10"
    };
    tableFunChannel("/activity/channelManage/getAllData", srhData);
    $("#channelModal").modal("show");
    chkFun();
});


function tableFunChannel(tdUrl, tbData) {
    $.ajax({
        type : "POST",
        url : tdUrl,
        data : tbData,
        dataType : "json",
        success : function(data) {
            var a = data.data.length;
            if (a > 0) {
                var _table = $('#channel_table'), tableBodyHtml = '';
                var _data = data.data;
                $.each(_data, function(k, v) {
                    // 获取数据
                    var id=v.id,code=v.code,name=v.name,channelType=v.channelType,modifyTime=v.modifyTime,editedBy=v.editedBy;
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input  type="checkbox" name="ckbox" class="sub_ckbox" title="' + name + '" value="'+ code + '"></label></td>';
                    tableBodyHtml += '<td>' + code + '</td>';
                    tableBodyHtml += '<td>' + name + '</td>';
                    tableBodyHtml += '<td>' + channelType + '</td>';
                });
                _table.find("tbody").html(tableBodyHtml);
                replaceFun(_table);
            } else {
                var html = "";
                html += '<tr class="no-records-found">';
                html += '<td colspan="4" style="text-align:center">没有找到匹配的记录</td>';
                html += '</tr>';
                $("#channel_table").find("tbody").html(html);
            }
        },
        async : false
    });
};




$('#channelType').change(function(){
    $('input:checkbox').removeAttr('checked');
    var srhData = {};
    var channelType = $("#channelType option:selected")[0].value;
    if(channelType != null && channelType != ""){
        srhData = {'channelTypeId':channelType};
    }else{
        srhData = {};
    }
    tableFunChannel("/activity/channelManage/getAllData", srhData);
    chkFun();
});



//选择选中的活动
$("#choose").click(function () {
    var radioChecked=$('input:radio[name="radio"]:checked').val();
    $("#activitySelected").html(radioChecked);
    $("#activitySelectedCode").val(radioChecked);
    $('#activityModal').modal('hide');
});

//选择选中的渠道
$("#chooseChannel").click(function() {
    // 判断是否至少选择一项
    var checkedNum = $("input[name='ckbox']:checked").length;
    if(checkedNum == 0) {
        alert("请选择至少一项！");
        return;
    }
    // 批量选择
    var checkedList = new Array();
    var checkedChannelName = new Array();
    $("input[name='ckbox']:checked").each(function() {
        checkedList.push($(this).val());
        checkedChannelName.push($(this).attr("title"));
    });
    $("#channelSelected").html(checkedList.toString());
    $("#channelSelectedCode").val(checkedList.toString());
    $('#channelModal').modal('hide');

});
















