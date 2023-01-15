/**
 *
 */

$(function () {
    var srhData = {"pageNo": "1", "pageSize": "5"};
    tableFun("/web_mgt/w_app_properties_list/banner/page", srhData);

    /***
     *功能说明：表格相关操作
     *参数说明：
     *创建人：LSC
     *时间：2016-07-29
     ***/
    var _table = $('#table');
    _table.bootstrapTable();


    /***
     *功能说明：表格数据
     *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
     *创建人：LSC
     *时间：2016-08-01
     ***/
    function tableFun(tdUrl, tbData) {
        $.ajax({
            type: "POST",
            url: tdUrl,
            data: tbData,


            dataType: "json",
            success: function (data) {
                var _data = eval('(' + data + ')');
                var _table = $('#table'),
                    tableBodyHtml = '';
                $.each(_data, function (k, v) {
                    //获取数据
                    if (k > 4) {
                        return;
                    }
                    var d_ID = v.id,

                        d_titile = v.titile, //名称
                        d_link = v.link, //链接
                        d_picture = v.picture; //图片名

                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + d_titile + '</td>';
                    tableBodyHtml += '<td>' + d_link + '</td>';
                    tableBodyHtml += '<td>' + v.weight + '</td>';
                    tableBodyHtml += '<td>' + d_picture + '</td>';
                    tableBodyHtml += '<td><a href="w_app_properties_list_edit.html?id=' + d_ID + '">编辑</a></td>';
                    tableBodyHtml += '</tr>';
                });
                _table.find("tbody").html(tableBodyHtml);

            }
        });
    }

    /**
     * 删除
     */

    function qq11(id) {


        var flag = confirm('确定要删除记录吗?');
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/web_mgt/w_app_banner_list/banner/delete",
                dataType: "json",
                data: {
                    "id": id.name
                },

                success: function (data) {
                    alert("删除成功");
                    window.location.reload();
                }, error: function () {
                    alert("删除失败");
                    window.location.reload();
                }

            });
        }
    }
});
/**
 * 删除
 */

function qq11(id) {

    var flag = confirm('确定要删除记录吗?');
    if (flag) {
        $.ajax({
            type: "POST",
            url: "/web_mgt/w_app_banner_list/banner/delete",
            dataType: "json",
            data: {
                "id": id.name
            },

            success: function (data) {
                alert("删除成功");
                window.location.reload();
            }, error: function () {
                alert("删除失败");
                window.location.reload();
            }

        });
    }
}