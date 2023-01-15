/**
 *
 */
var _pages;
$(function () {
    var _table = $('#table');
    _table.bootstrapTable();
    var srhData = {"pageNo":"1","pageSize":"10"};
    tableFun("../reward/project_list/list", srhData);
    myPage();
    /**
     * 模态窗口非空/数字校验
     */
    _modalFm =  $('#modal_form');
    _modalFm.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
});



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
                        u_description = v.description,
                        u_startTime = v.startTime,
                        u_endTime = v.endTime,
                        u_defaultAward = v.defaultAward,

                        //输出HTML元素
                        tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td><a href="project_list_jackpot_list.html?projectId='+u_id+'&projectName='+encodeURI(encodeURI(u_name))+'">' + u_name + '</a></td>';
                    tableBodyHtml += '<td>' + u_description + '</td>';
                    tableBodyHtml += '<td>' + u_startTime + '</td>';
                    tableBodyHtml += '<td>' + u_endTime + '</td>';
                    tableBodyHtml += '<td>' + u_defaultAward + '</td>';
                    // tableBodyHtml += '<td><a href="project_list_update_project.html?projectId='+u_id+'">编辑</a>&nbsp;&nbsp;<a href="project_list_jackpot_list.html?projectId='+u_id+'&projectName='+encodeURI(encodeURI(u_name))+'">奖池管理</a>&nbsp;&nbsp;</td>';
                    tableBodyHtml += '<td><a href="project_list_update_project.html?projectId='+u_id+'">编辑</a>&nbsp;&nbsp;</td>';
                    tableBodyHtml += '</tr>';
                });
                _table.find("tbody").html(tableBodyHtml);

                replaceFun(_table);
            }else{
                $("#tcdPagehide").hide();
            }
        },error:function(data){
            alert("调用奖励接口异常")
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
                "pageNo" : p,
                "pageSize" : "10",
            };
            tableFun("../reward/project_list/list", srhData);
        }
    });
};