/**
 * Created by fengwei on 2017/11/24.
 */

var activityId;
var activityName;
var _pages = 0;
$(function() {
    activityId = Request.id ;
    var srhData = {
        "pageNo" : "1",
        "pageSize" : "10",
        "activityId" : activityId
    };
    tableFun("/activity/activityManager/getAllRule", srhData);
    myPage();
    activityStatistics( activityId );
});
//活动统计
function activityStatistics( activityId ) {
    $.ajax({
        type : "POST",
        url : "/activity/activityManager/activityStatistics",
        data : {"activityId" : activityId },
        dataType : "json",
        success : function(data) {
            console.log( data );
            var personTime = $('#personTime');//参与人次
            var titleAmount = $('#titleAmount');//总金额
            personTime.html( data.data.personTime );
            titleAmount.html( data.data.titleAmount.amount );
        },
        async : false
    });
}

function tableFun(tdUrl, tbData) {
    $.ajax({
        type : "POST",
        url : tdUrl,
        data : tbData,
        dataType : "json",
        success : function(data) {
            console.log( data );
            var a = data.data[1].total;
            if (a != 0) {
                var _table = $('#table'), tableBodyHtml = '';
                _pages = data.data[1].pages;
                var _data = data.data[1];
                var t = data.data[0];
                activityName = t.name;
                for(var i = 0; i < _data.list.length ; i++ ) {
                    var v = _data.list[i];
                    var name = v.name;
                    var standardAmount = v.standardAmount;
                    var sunShines = v.sunShines;
                    var id = v.id;
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td class="activityId">' + activityId + '</td>';
                    tableBodyHtml += '<td>' + activityName + '</td>';
                    if (sunShines == 1) {
                        tableBodyHtml += '<td><input class="sunShines" type="checkbox" id="sunShines" name="sunShines" checked="checked"></td>';
                    } else {
                        tableBodyHtml += '<td><input class="sunShines" type="checkbox" id="sunShines" name="sunShines"></td>';
                    }
                    tableBodyHtml += '<td><input class="name" type="text" id="name" name="name" value="'+ name +'"></td>';
                    tableBodyHtml += '<td><input class="standardAmount" type="text" id="standardAmount" name="standardAmount" value="'+ standardAmount +'"></td>';
                    tableBodyHtml += '<td><input type="hidden" class="dId" name="id" value="'+ id +'"><a class="saveTr">设置中奖号</a>&nbsp;|<a class="delTr" >删除</a>&nbsp;|<a class="awardTr">兑换奖励</a>&nbsp;</td>';
                    tableBodyHtml += '</tr>';

                }
                $("#tcdPageCode").show();
                _table.find("tbody").html(tableBodyHtml);
                _table.find("tr").each(function () {
                    $(this).find(".delTr").click(function () {
                        $(this).parents("tr").remove();
                        var dId = $(this).parent().find(".dId").val();
                        del( dId );
                    });
                    $(this).find(".saveTr").click(function () {
                        var dId = $(this).parent().find(".dId").val();
                        var activityId =  $(this).parents("tr").find(".activityId").text();
                        var sunShines = 0 ;
                        if(  $(this).parents("tr").find(".sunShines").is(':checked') ){
                            sunShines = 1 ;
                        }
                        var standardAmount = $(this).parents("tr").find(".standardAmount").val();
                        var name = $(this).parents("tr").find(".name").val();
                        save( dId , activityId , standardAmount , name , sunShines );
                    });
                    $(this).find(".awardTr").click(function () {
                        var dId = $(this).parent().find(".dId").val();
                        var activityId =  $(this).parents("tr").find(".activityId").text();
                        var sunShines = 0 ;
                        if(  $(this).parents("tr").find(".sunShines").is(':checked') ){
                            sunShines = 1 ;
                        }
                        var standardAmount = $(this).parents("tr").find(".standardAmount").val();
                        var name = $(this).parents("tr").find(".name").val();
                        award( dId , activityId , standardAmount , name , sunShines );
                    });
                });
            } else {
                var html = "";
                html += '<tr class="no-records-found">';
                html += '<td colspan="12" style="text-align:center">没有找到匹配的记录</td>';
                html += '</tr>';
                $("#table").find("tbody").html(html);
                $("#tcdPageCode").hide();
            }
        },
        async : false
    });
};

var myPage = function() {
    // 分页
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount : _pages,
        current : 1,
        backFn : function(p) {
            // 点击分页事件
            var srhData = {};
            if($("#name").val() != "" && undefined != $("#name").val() && null != $("#name").val()){
                srhData = {
                    "pageNo" : p,
                    "pageSize" : "10",
                    "name":$("#name").val()
                };
            }else{
                srhData = {
                    "pageNo" : p,
                    "pageSize" : "10",
                };
            }
            tableFun("/activity/activityManager/getAllActivity", srhData);
        }
    });
};


$("#addBtn").click(function(){
    var _table = $('#table') ;
    var tableBodyHtml = _table.find("tbody").html();
    tableBodyHtml += '<tr>';
    tableBodyHtml += '<td class="activityId">'+ activityId + '</td>';
    tableBodyHtml += '<td>'+ activityName +'</td>';
    tableBodyHtml += '<td><input class="sunShines" type="checkbox" id="sunShines" name="sunShines"></td>';
    tableBodyHtml += '<td><input class="name" type="text" id="name" name="name" value=""></td>';
    tableBodyHtml += '<td><input class="standardAmount" type="text" id="standardAmount" name="standardAmount" value=""></td>';
    tableBodyHtml += '<td><input type="hidden" class="dId" name="id" value=""><a class="saveTr">设置中奖号</a>&nbsp;|<a class="delTr" >删除</a>&nbsp;|<a class="awardTr">兑换奖励</a>&nbsp;</td>';
    tableBodyHtml += '</tr>';
    _table.find("tbody").html(tableBodyHtml);
    _table.find("tr").each(function () {
        $(this).find(".delTr").click(function () {
            $(this).parents("tr").remove();
            var dId = $(this).parent().find(".dId").val();
            del( dId );
        });
        $(this).find(".saveTr").click(function () {
            var dId = $(this).parent().find(".dId").val();
            var activityId =  $(this).parents("tr").find(".activityId").text();
            var sunShines = 0 ;
            if(  $(this).parents("tr").find(".sunShines").is(':checked') ){
                sunShines = 1 ;
            }
            var standardAmount = $(this).parents("tr").find(".standardAmount").val();
            var name = $(this).parents("tr").find(".name").val();
            save( dId , activityId , standardAmount , name , sunShines );
        });
        $(this).find(".awardTr").click(function () {
            var dId = $(this).parent().find(".dId").val();
            var activityId =  $(this).parents("tr").find(".activityId").text();
            var sunShines = 0 ;
            if(  $(this).parents("tr").find(".sunShines").is(':checked') ){
                sunShines = 1 ;
            }
            var standardAmount = $(this).parents("tr").find(".standardAmount").val();
            var name = $(this).parents("tr").find(".name").val();
            award( dId , activityId , standardAmount , name , sunShines );
        });
    });
});

function del( id ) {
    if( id != '' ){ //页面删除
        bootbox.confirm("你确定要删除吗?", function (result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: "/activity/activityManager/delRule",
                    data: {'id': id},
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if (data.resCode == 0) {
                            bootbox.alert('成功', function () {
                                window.location.reload();
                            });
                        } else {
                            bootbox.alert("操作失败");
                        }
                        ;
                    }
                });
            } else {
                return;
            }
        });
    }
}

function save(  dId , activityId , standardAmount , name , sunShines ) {
    bootbox.confirm("你确定要保存吗?", function (result) {
        if (result) {
            $.ajax({
                type: "POST",
                url: "/activity/activityManager/saveRule",
                data: {'id': dId , 'activityId' : activityId , "standardAmount" : standardAmount , "name" : name , "sunShines" : sunShines },
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.resCode == 0) {
                        bootbox.alert('成功', function () {
                            window.location.reload();
                        });
                    } else {
                        bootbox.alert("操作失败");
                    }
                    ;
                }
            });
        } else {
            return;
        }
    });
}

function award(  dId , activityId , standardAmount , name  , sunShines ) {
    bootbox.confirm("你确定发放奖励吗?", function (result) {
        if (result) {
            $.ajax({
                type: "POST",
                url: "/activity/activityManager/award",
                data: {'id': dId , 'activityId' : activityId , "standardAmount" : standardAmount , "name" : name , "sunShines" : sunShines },
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.resCode == 0) {
                        bootbox.alert('成功', function () {
                            window.location.reload();
                        });
                    } else {
                        bootbox.alert("操作失败");
                    }
                    ;
                }
            });
        } else {
            return;
        }
    });
}

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