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


var _pages;var _id;var _name;
$(function () {
    var _table = $('#table');
    _table.bootstrapTable();
    _id = Request.projectId;
    _name = decodeURI(Request.projectName);
    var tdU = "../reward/project_list/getById";
    var tbD = {"id":_id};
    tablePro(tdU,tbD);
    var srhData = {"projectId":_id,"pageNo":"1","pageSize":"10"};
    tableFun("../reward/project_list/jackpot/list", srhData);
    myPage();
});

//编辑
function tablePro(tdUrl, tbData) {
$.ajax({
    type : "POST",
    url : tdUrl,
    data : tbData,
    dataType : "json",
    success : function(data) {
        var _table = $('#table_pro'),
        tableBodyHtml = '';
        var u_id = data.id;
        	u_name = data.name,
            u_description = data.description,
            u_startTime = data.startTime,
            u_endTime = data.endTime,
            u_defaultAward = data.defaultAward;
            tableBodyHtml += '<tr>';
            tableBodyHtml += '<td>' + u_name + '</td>';
            tableBodyHtml += '<td>' + u_description + '</td>';
            tableBodyHtml += '<td>' + u_startTime + '</td>';
            tableBodyHtml += '<td>' + u_endTime + '</td>';
            tableBodyHtml += '<td>' + u_defaultAward + '</td>';
            tableBodyHtml += '<td><a href="project_list_update_project.html?projectId='+u_id+'">编辑</a></td>';
            tableBodyHtml += '</tr>';
            _table.find("tbody").html(tableBodyHtml);
            replaceFun(_table);
    }
});
}


function tableFun(tdUrl, tbData) {
    $.ajax({
        type: "POST",
        url: tdUrl,
        data: tbData,
        dataType: "json",
        success: function (data) {
            if(data.total!=0){
                var _table = $('#table'),
                    tableBodyHtml = '';

                _pages = data.pages;

                $.each(data.list, function (k, v) {
                    //获取数据
                    var u_id = v.id;
                    u_name = v.name,
                        u_descName = v.descName,
                        u_number = v.number,
                        u_type = v.type,
                        u_remark = v.remark,
                        u_value = v.value.amount,
                        u_descValue = v.descValue,
                        u_cost = v.cost.amount,
                        u_payNumber = v.payNumber,
                        u_startDate = v.startDate,
                        u_endDate = v.endDate,
                        u_limitedDate = v.limitedDate,
                        u_expiredDay = v.expiredDay;

                    var	_startDa = new Date(u_startDate);
                    _myDate = new Date();

                    if(u_type == "THANKS"){
                        u_type = "谢谢参与";
                    }if(u_type == 'PHYSICAL'){
                        u_type = '实物券';
                    }if(u_type == "REDMONEY"){
                        u_type = "红包";
                    }if(u_type == 'SHAREREDMONEY'){
                        u_type = '分享红包';
                    }if(u_type == "EXPERIENCE"){
                        u_type = "体验金";
                    }if(u_type == "LOTTERY"){
                        u_type = "抽奖券";
                    }if(u_type == "CASH"){
                        u_type = "现金";
                    }if(u_type == "POINT"){
                        u_type = "积分";
                    }if(u_type == "VOUCHEROFFLINE"){
                        u_type = "线下代金券";
                    }if(u_type == "VOUCHER"){
                        u_type = "系统内部使用代金券";
                    }if(u_type == "VOUCHERH5"){
                        u_type = "h5使用的代金券";
                    }if(u_type == "VOUCHERPWD"){
                        u_type = "卡密代金券";
                    }

                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + _name + '</td>';
                    tableBodyHtml += '<td>' + u_name + '</td>';
                    tableBodyHtml += '<td>' + u_descName + '</td>';
                    tableBodyHtml += '<td>' + u_type + '</td>';
                    tableBodyHtml += '<td>' + u_number + '</td>';
                    tableBodyHtml += '<td>' + u_value + '</td>';
                    tableBodyHtml += '<td>' + u_descValue + '</td>';
                    tableBodyHtml += '<td>' + u_cost + '</td>';
                    tableBodyHtml += '<td>' + u_payNumber + '</td>';
                    tableBodyHtml += '<td>' + u_limitedDate + '</td>';
                    tableBodyHtml += '<td>' + u_expiredDay + '</td>';
                    tableBodyHtml += '<td><a  name='+u_id+'  href="javascript:" style="margin-left:15px;" onclick="deleteJackpot(this)">删除</a>&nbsp;&nbsp;<a href="project_list_award_add.html?projectId='+_id +'&type=edit&jackpotId='+u_id+'">编辑</a> </td>';
                    // tableBodyHtml += '<td><a href="project_list_update_jackpot.html?jackpotId='+u_id+'">编辑</a> <a  name='+u_id+'  href="javascript:" style="margin-left:15px;" onclick="deleteJackpot(this)">删除</a>&nbsp;&nbsp;<a href="project_list_news_list.html?jackpotId='+u_id+'&projectId='+_id+'">消息</a></td>';
                    tableBodyHtml += '</tr>';
                });
                _table.find("tbody").html(tableBodyHtml);

                replaceFun(_table);
            }else{
                $("#tcdPagehide").hide();
            }
        },error:function(data){
            alert(data)
        },
        async : false
    });
}


// 分页
var myPage = function(){
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount : _pages,
        current : 1,
        backFn : function(p) {
            var srhData = {
                "projectId":_id,
                "pageNo" : p,
                "pageSize" : "10",
            };
            tableFun("../reward/project_list/jackpot/list", srhData);
        }
    });
};

function addjackpot(){
    window.location.href="project_list_award_add.html?type=add&projectId="+_id+"";
}


function returnHistory(){
    window.location.href="../reward/project_list.html";
}


function deleteJackpot(jackpotId){
    if(confirm("确认要删除吗?")){
        $.ajax({
            type : "POST",
            url : "../reward/project_list/jackpot/deleteJackpot",
            data : {"poolId" : jackpotId.name},
            dataType: "json",
            async:false,
            success : function(data) {
                if (data != null && data != "") {
                    alert("删除成功")
                    window.location.reload();
                } else {
                    alert(data.msg);
                }
            },
            async : false
        });
    }
}
	
	
function strategy(){
	 window.location.href="project_list_strategy_list.html?projectId="+_id+"&projectName="+encodeURI(encodeURI(_name));
}
function jackpot(){
	 window.location.href="project_list_jackpot_list.html?projectId="+_id+"&projectName="+encodeURI(encodeURI(_name));
}
	

