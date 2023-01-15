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


/**
 *
 */
var _pages;var _id;var _name;
$(function () {
    _id = Request.projectId;
    _name = decodeURI(Request.projectName);
    var _table = $('#table');
    _table.bootstrapTable();
    var tdU = "../reward/project_list/getById";
    var tbD = {"id":_id};
    tablePro(tdU,tbD);
    var srhData = {"projectId":_id,"pageNo":"1","pageSize":"10"};
    tableFun("../reward/project_list/strateg/list", srhData);
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
                        u_multi = v.multi,
                        u_startTime = v.startTime,
                        u_endTime = v.endTime;
                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + _name + '</td>';
                    tableBodyHtml += '<td>' + u_name + '</td>';
                    tableBodyHtml += '<td>' + u_multi + '</td>';
                    tableBodyHtml += '<td><a href="project_list_Issuingrules_list.html?awardStrategyId='+u_id+'&projectId='+_id+'&projectName='+encodeURI(encodeURI(_name))+'&awardStrategyName='+encodeURI(encodeURI(u_name))+'">发放管理</a>&nbsp;&nbsp;<a href="project_list_behavior_list.html?awardStrategyId='+u_id+'&projectId='+_id+'&projectName='+encodeURI(encodeURI(_name))+'&awardStrategyName='+encodeURI(encodeURI(u_name))+'">行为管理</a>&nbsp;&nbsp;<a href="project_list_update_strategy.html?id='+u_id+'&projectId='+_id+'&projectName='+encodeURI(encodeURI(_name))+'">编辑</a></td>';
                    tableBodyHtml += '</tr>';
                });
                _table.find("tbody").html(tableBodyHtml);

                replaceFun(_table);
            }else{
                $("#tcdPagehide").hide();
            }
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
            tableFun("../reward/project_list/strateg/list", srhData);
        }
    });
};


function returnJac(){
    window.location.href="project_list_jackpot_list.html?projectId="+_id+"&projectId="+_id+"&projectName="+encodeURI(encodeURI(_name));
}
function strategy(){
    window.location.href="project_list_strategy_list.html?projectId="+_id+"&projectId="+_id+"&projectName="+encodeURI(encodeURI(_name));
}


function addstrategy(){
    window.location.href="project_list_insert_strategy.html?projectId="+_id+"&projectId="+_id+"&projectName="+encodeURI(encodeURI(_name));
}

