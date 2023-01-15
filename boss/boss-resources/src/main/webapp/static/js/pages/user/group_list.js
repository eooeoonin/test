var _pages;


$(function () {
    var _table = $('#table');
    _table.bootstrapTable();
    $(window).resize(function () {
    });

    tableFun("/user/group/list");

});


function tableFun(tdUrl) {
    $.ajax({
        type: "POST",
        url: tdUrl,
        data: {},

        dataType: "json",
        success: function (data) {
            if (data.resCode == 0) {
                var _data = data.data.list;
                var _table = $('#table'),
                    tableBodyHtml = '';
                for (var key = 0; key < _data.length; key++) {
                    var d_id = _data[key].id,
                        classDesc = _data[key].classDesc,
                        codeKey = _data[key].codeKey,
                        codeValue = _data[key].codeValue;
                        var available="";
                    if("1" == _data[key].available){
                        available = "可用";
                    }else if("0" == _data[key].available){
                        available = "不可用";
                    }
                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + classDesc + '</td>';
                    tableBodyHtml += '<td>' + codeKey + '</td>';
                    tableBodyHtml += '<td>' + codeValue + '</td>';
                    tableBodyHtml += '<td>' + available + '</td>';
                    tableBodyHtml += '<td><a href="group_edit.html?type=edit&systemConfigId=' + d_id + '"> 编辑</a></td>';
                    tableBodyHtml += '</tr>';
                }

                _table.find("tbody").html(tableBodyHtml);

            } else bootbox.alert(data.msg);
        },
        error: function () {
            bootbox.alert("接口异常");
        },
        async: false
    });

}
