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
$(function() {

    _id = Request.jackpotId;
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
    //URL参数
    var id = Request.id;
    var tdUrl = "../reward/project_list/jackpot/getById";
    var tbData = {"id":_id};
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
            var start = new Date(data.limitedDate);
            var dateStrFrist = start.getFullYear() + '/' + (start.getMonth()+1) + '/' + start.getDate() + ' ' + start.getHours() + ':' + start.getMinutes() + ':' + start.getSeconds();
            var startOne = new Date(data.startDate);
            var dateStrOne = startOne.getFullYear() + '/' + (startOne.getMonth()+1) + '/' + startOne.getDate() + ' ' + startOne.getHours() + ':' + startOne.getMinutes() + ':' + startOne.getSeconds();
            var end = new Date(data.endDate);
            var dateStrEnd = end.getFullYear() + '/' + (end.getMonth()+1) + '/' + end.getDate() + ' ' + end.getHours() + ':' + end.getMinutes() + ':' + end.getSeconds();
            //把商品数据,显示在也页面上
            document.getElementById("jackpotId").value = data.id;
            document.getElementById("projectId").value = data.projectId;
            document.getElementById("name").value = data.name;
            document.getElementById("descName").value = data.descName;
            document.getElementById("number").value = data.number;
            $("#type").val(""+data.type+"");
            document.getElementById("remark").value = data.remark;
            document.getElementById("cost").value = data.cost;
            document.getElementById("value").value = data.value.amount;
            document.getElementById("descValue").value = data.descValue;
            document.getElementById("startDate").value = dateStrOne;
            document.getElementById("endDate").value = dateStrEnd;
            document.getElementById("limitedDate").value = dateStrFrist;
            document.getElementById("expiredDay").value = data.expiredDay;

            if(data.type == 'LOTTERY'){
                //抽奖券
                $("#addextend").removeAttr("disabled");
                $("#deleteextend").removeAttr("disabled");

                byId(data.id);// 抽奖券概率
                getId(data.id); //外链

                document.getElementById("tbo").style.display="";//显示
                document.getElementById("address").style.display="";//显示
            }if(data.type == 'VOUCHEROFFLINE'){
                //线下代金券
                getVoch(data.id);
                document.getElementById("tbootwo").style.display="";//显示
            }if(data.type == 'VOUCHER'){
                //系统内部使用代金券
                getVoch(data.id);
                document.getElementById("tbootwo").style.display="";//显示
            }if(data.type == 'VOUCHERH5'){
                //h5使用的代金券
                $("#addextend").attr("disabled","disabled");
                $("#deleteextend").attr("disabled","disabled");
                getVoch(data.id);
                document.getElementById("tbootwo").style.display="";//显示
            }

        }
    });
}

function getVoch(id){
    $.ajax({
        type: "POST",
        url:"../reward/project_list/jackpot/getVoch",
        data:{"poolId":id,"pageNo":"1","pageSize":100},
        error: function() {
        },
        success: function(data) {
            document.getElementById("hperLi").value = data.hperLink;
            //TODU 回显selected
            document.getElementById("seneCode").value = data.seneCode;
            document.getElementById("voucherInfoReqVoId").value = data.id;
            document.getElementById("useLimitMin").value = data.useLimitMin;
            document.getElementById("useLimitMax").value = data.useLimitMax;

        }
    });
}


$("#type").click(function (){
    if(document.getElementById("type").value == 'LOTTERY'){
        $("#addextend").removeAttr("disabled");
        $("#deleteextend").removeAttr("disabled");
        document.getElementById("address").style.display="";//显示
        document.getElementById("tbo").style.display="";//显示
        document.getElementById("tbootwo").style.display="none"; //隐藏
    }else if(document.getElementById("type").value == 'VOUCHEROFFLINE'){
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="";  //显示
    }else if(document.getElementById("type").value == 'VOUCHERH5'){
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="";  //显示
    }else if(document.getElementById("type").value == 'VOUCHER'){
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="";  //显示
    }else{
        $("#addextend").attr("disabled","disabled");
        $("#deleteextend").attr("disabled","disabled");
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="none"; //隐藏
    }
})

var trHtml;
var len = 0;
$("#addextend").click(function (){

    _lenTwo = _lenTwo + 1;
    var extend = $("#tbo");
    trHtml = '';
    trHtml += '<tr name="insert'+_lenTwo+'">';
    trHtml += '<td class="input-group-addon" width="20%"> <em>*</em>奖品码</td>';
    trHtml += '<td width="30%">';
    trHtml += '<input class="form-control" name="lotteryChangesReqVos['+_lenTwo+'].actCode" id="'+_lenTwo+'">';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+_lenTwo+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>最小中奖概率</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos['+_lenTwo+'].chMini" >';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+_lenTwo+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>最大中奖概率</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos['+_lenTwo+'].chMax">';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+_lenTwo+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>起始中奖位</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required, custom[onlyNumberSp]]" name="lotteryChangesReqVos['+_lenTwo+'].startLimit">';
    trHtml += '</td>';
    trHtml += '</tr>';
    extend.append(trHtml);
})

$("#deleteextend").click(function(){
    $("tr[name=insert"+_lenTwo+"]").remove();
    _lenTwo = _lenTwo - 1;
})





var  _lenTwo;
function byId(id){
    $.ajax({
        type: "POST",
        url:"../reward/project_list/jackpot/bylotteryInfoId",
        data:{"lotteryInfoId":id,"pageNo":"1","pageSize":100},
        error: function() {
        },
        success: function(data) {

            var _table = $('#table4');
            var len = -1;
            tableBodyHtml = '';
            _lenTwo = 0;
            $.each(data.list,function(k,v){
                _dalist = data.list.length;
                len = len +1;
                _lenTwo = len;
                var u_id = v.id,
                    u_actCode = v.actCode,
                    u_chMini = v.chMini,
                    u_chMax = v.chMax
                    u_startLimit = v.startLimit;

                tableBodyHtml += "<tr name='insert"+len+"'>";
                tableBodyHtml += "<td class='input-group-addon'> <em>*</em>抽奖券扩展信息</td>";
                tableBodyHtml += "</tr>";
                tableBodyHtml += "<tr name='insert"+len+"'>";
                tableBodyHtml += "<td class='input-group-addon'><em>*</em>奖品码</td>";
                tableBodyHtml += "<td>";
                tableBodyHtml += "<input class='form-control' name='lotteryChangesReqVos["+len+"].actCode' value="+u_actCode+">";
                tableBodyHtml += "</td>";
                tableBodyHtml += "</tr>";
                tableBodyHtml += "<tr name='insert"+len+"'>";
                tableBodyHtml += "<td class='input-group-addon' > <em>*</em>最小中奖概率</td>";
                tableBodyHtml += "<td>";
                tableBodyHtml += '<input class="form-control validate[required]" name="lotteryChangesReqVos['+len+'].chMini" id="chMini" value="' + u_chMini + '">';
                tableBodyHtml += "</td>";
                tableBodyHtml += "</tr>";
                tableBodyHtml += "<tr name='insert"+len+"'>";
                tableBodyHtml += "<td class='input-group-addon' > <em>*</em>最大中奖概率</td>";
                tableBodyHtml += "<td>";
                tableBodyHtml += '<input class="form-control validate[required]" name="lotteryChangesReqVos['+len+'].chMax" value="' + u_chMax + '">';
                tableBodyHtml += "</td>";
                tableBodyHtml += "</tr>";
                tableBodyHtml += "<tr name='insert"+len+"'>";
                tableBodyHtml += "<td class='input-group-addon' > <em>*</em>起始中奖位</td>";
                tableBodyHtml += "<td>";
                tableBodyHtml += '<input class="form-control validate[required, custom[onlyNumberSp]]" name="lotteryChangesReqVos['+len+'].startLimit" value="' + u_startLimit + '">';
                tableBodyHtml += "</td>";
                tableBodyHtml += "</tr>";
            });
            _table.find("tbody").html(tableBodyHtml);
            replaceFun(_table);
        },
        async:false             //false表示同步
    });
}

function getId(id){
    $.ajax({
        type: "POST",
        url:"../reward/project_list/jackpot/getId",
        data:{"poolId":id,"pageNo":"1","pageSize":100},
        error: function() {
        },
        success: function(data) {
            document.getElementById("hperLink").value = data.hperLink;
            document.getElementById("lotteryInfoReqVoId").value = data.id;
            // byId(data.id);
        }
    });
}



$('#jackpotSubmit').click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
//	var jackpotId = document.getElementById("jackpotId").value;
        var formdata = $('#form').serialize();
        $.ajax({
            type: "POST",
            url:"../reward/project_list/jackpot/update",
            data:formdata,
            error: function() {
                var projectId = document.getElementById("projectId").value;
                alert("修改失败");
                location = "../reward/project_list_jackpot_list.html?projectId="+projectId+"";
            },
            success: function(data) {
                var projectId = document.getElementById("projectId").value;
                alert("修改成功");
                location = "../reward/project_list_jackpot_list.html?projectId="+projectId+"";
            }
        });
    }
})
