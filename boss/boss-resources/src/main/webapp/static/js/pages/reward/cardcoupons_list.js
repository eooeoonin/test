/**
 *
 */
var _pages;var proId;
$(function () {

    var _table = $('#table');
    _table.bootstrapTable();
    var srhData = {"pageNo":"1","pageSize":"10"};
    tableFun("../reward/cardcoupons_list/list", srhData);
    myPage();
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
                    var u_id = v.id,
                    	u_userId = v.userId,
                        u_name = v.name,
                        u_type = v.type,
                        u_projectId = v.projectId,
                        u_userPhone = v.userPhone,
                        u_createTime =v.createTime,
                        u_endDate = v.endDate,
                        u_value = v.value,
                        u_awardStatus = v.status;
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
                    }
                    if(u_awardStatus == 'INIT'){
                        u_awardStatus = '未使用';
                    }if(u_awardStatus == 'USED'){
                        u_awardStatus = '已使用';
                    }if(u_awardStatus == 'EXPIRED'){
                        u_awardStatus = '已过期';
                    }

                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + u_userId + '</td>';
                    tableBodyHtml += '<td>' + u_id + '</td>';
                    tableBodyHtml += '<td>' + u_name + '</td>';
                    tableBodyHtml += '<td>' + u_type + '</td>';
                    tableBodyHtml += '<td>' + u_projectId + '</td>';
                    tableBodyHtml += '<td>' + u_userPhone + '</td>';
                    tableBodyHtml += '<td>' + u_createTime + '</td>';
                    tableBodyHtml += '<td>' + u_endDate + '</td>';
                    tableBodyHtml += '<td>' + u_value.amount + '</td>';
                    tableBodyHtml += '<td>' + u_awardStatus + '</td>';
                    tableBodyHtml += '</tr>';
                });
                _table.find("tbody").html(tableBodyHtml);

                replaceFun(_table);
                $("#tcdPagehide").show();
            }else{
                var html = "";
                html +='<tr class="no-records-found">';
                html +='<td colspan="10">没有找到匹配的记录</td>';
                html += '</tr>';
                $("#table").find("tbody").html(html);
                $("#tcdPagehide").hide();
            }

        },
        async : false
    });
}


$("#srhBtn").click(function(){
    var userPhone = $("#userPhone").val();
    var nickName = $("#nickName").val();
    var projectId = $("#projectId").val();
    var status = $('#status').val();
    var srhData = {'pageNo':'1','pageSize':'10','userPhone':userPhone,'projectId':projectId,'status':status,'nickName':nickName};
    tableFun("../reward/cardcoupons_list/list", srhData);
    myPage();
});




// 分页
var myPage = function(){
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount : _pages,
        current : 1,
        backFn : function(p) {
            var userPhone = $("#userPhone").val();
            var nickName = $("#nickName").val();
            var projectId = $("#projectId").val();
            var status = $('#status').val();
            var srhData = {'pageNo':p,'pageSize':'10','userPhone':userPhone,'projectId':projectId,'status':status,'nickName':nickName};
            tableFun("../reward/cardcoupons_list/list", srhData);
        }
    });
};


