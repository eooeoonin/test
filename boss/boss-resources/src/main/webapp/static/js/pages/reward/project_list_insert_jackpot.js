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

var _id;
$(function () {

    _id = Request.projectId;
    document.getElementById("projectId").value = Request.projectId;
    //单个时间引用方式
    laydate({elem: "#limitedDate", format: "YYYY/MM/DD hh:mm:ss" , istime: true});
    //时间段
    var start = {
        elem: "#startDate", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
            end.min = datas;
            end.start = datas
        }
    };
    var end = {
        elem: "#endDate", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
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
    _modalFm1 =  $('#form');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });

    len = len + 1;
    var extend = $("#tbo");
    trHtml = '';
    trHtml += '<tr name="insert'+len+'">';
    trHtml += '<td class="input-group-addon" width="20%"> <em>*</em>奖品码</td>';
    trHtml += '<td width="30%">';
    trHtml += '<input class="form-control" name="lotteryChangesReqVos['+len+'].actCode" id="'+len+'">';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+len+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>最小中奖概率</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos['+len+'].chMini" >';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+len+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>最大中奖概率</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos['+len+'].chMax">';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+len+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>起始中奖位</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required, custom[onlyNumberSp]]" name="lotteryChangesReqVos['+len+'].startLimit">';
    trHtml += '</td>';
    trHtml += '</tr>';
    extend.append(trHtml);
});
$("#tab1_jackpotSubmit").click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        $.ajax({
            type:"POST",
            url:"../reward/project_list/jackpot/insert",
            data:$("#form").serialize(),
            dataType: "json",
            success:function(data){
                if(data==1){
                    alert("添加成功");
                    location = "../reward/project_list_jackpot_list.html?projectId="+_id+"";
                }else{
                    alert("接口调用失败");
                }

            },error:function(){
                alert("添加失败")
                location = "../reward/project_list_jackpot_list.html?projectId="+_id+"";
            }
        })
    }
});