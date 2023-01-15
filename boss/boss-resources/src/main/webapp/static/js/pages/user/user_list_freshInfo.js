var JSESSIONID = $.cookie("JSESSIONID");
/**
 * 会员中心消费记录
 */
function freshOrderRecord(){
    var srhData = {"userId":_userId,"pageNo":"1","pageSize":"10"};
    var url = "../user/user_list/selectFreshOrderRecord";
    selectFreshOrderRecord(url, srhData);
    myPage(url);
}

function freshAppraiseRecord(){
    var srhData = {"userId":_userId,"pageNo":"1","pageSize":"10"};
    var url = "../user/user_list/selectFreshAppraiseRecord";
    selectFreshAppraiseRecord(url, srhData);
    myPage(url);
}


//生鲜主订单消费记录
function selectFreshOrderRecord(tdUrl,tdData) {
    $.ajax({
        type: "POST",
        url: tdUrl,
        data: tdData,
        dataType: "json",
        success: function (data) {
            var _table = $('#tab_11'),
                tableBodyHtml = '',
                _data = data.data;
            _pages = _data.pages;
            if(_pages > 0){
                $.each(_data.list,function(k,v){
                    //获取数据
                    var t_orderTime = v.orderTime,
                        t_id = v.id,
                        t_recieverPhone = v.recieverPhone,
                        t_cabinetName = v.cabinetName,
                        t_orderStatus = showOrderStatus(v.orderStatus);

                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + t_orderTime + '</td>';
                    tableBodyHtml += '<td>' + t_id + '</td>';
                    tableBodyHtml += '<td>' + t_recieverPhone + '</td>';
                    tableBodyHtml += '<td>' + t_cabinetName + '</td>';
                    tableBodyHtml += '<td>' + t_orderStatus + '</td>';
                    tableBodyHtml += '</tr>';

                });
                _table.find("tbody").html(tableBodyHtml);
                replaceFun(_table);
            }else {
                var html = "";
                html += '<tr class="no-records-found">';
                html += '<td colspan="5" style="text-align:center" >没有找到匹配的记录</td>';
                html += '</tr>';
                _table.find("tbody").html(html);
                $("#tcdPageCodehide").hide();
            }

        },
        async : false
    });
}


//生鲜评价记录
function selectFreshAppraiseRecord(tdUrl,tdData) {
    $.ajax({
        type: "POST",
        url: tdUrl,
        data: tdData,
        dataType: "json",
        success: function (data) {
            var _table = $('#tab_12'),
                tableBodyHtml = '',
                _data = data.data;
            _pages = _data.pages;
            if(_pages > 0){
                $.each(_data.list,function(k,v){
                    //获取数据
                    var t_mainOrderCode = v.mainOrderCode,
                        t_createTime = v.createTime;

                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + t_createTime + '</td>';
                    tableBodyHtml += '<td>' + t_mainOrderCode + '</td>';
                    tableBodyHtml += '<td><a href="'+goodsOrderUrl+'/order/order.html?JSESSIONID='+JSESSIONID+'&id='+ t_mainOrderCode+'">查看</a></td></tr>';

                });
                _table.find("tbody").html(tableBodyHtml);
                replaceFun(_table);
            }else {
                var html = "";
                html += '<tr class="no-records-found">';
                html += '<td colspan="3" style="text-align:center" >没有找到匹配的记录</td>';
                html += '</tr>';
                _table.find("tbody").html(html);
                $("#tcdPageCodehide").hide();
            }

        },
        async : false
    });
}


// 分页
var myPage = function(url){
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount : _pages,
        current : 1,
        backFn : function(p) {
            var srhData = {
                "pageNo" : p,
                "pageSize" : "10",
                "userId":_userId
            };
            selectFreshOrderRecord(url, srhData);
        }
    });
};

function showOrderStatus(str) {
    var result = "";
    if(null == str || "" == str || undefined == str){
        result = "——";
    }else if("UNPAY" == str.toUpperCase()){
        result = "待支付";
    }else if("PAYING" == str.toUpperCase()){
        result = "支付中";
    }else if("PAYED" == str.toUpperCase()){
        result = "已支付";
    }else if("OVERPAY" == str.toUpperCase()){
        result = "超时未支付";
    }else if("ORDERCONFIRM" == str.toUpperCase()){
        result = "订单供应商确认";
    }else if("DELIVERY" == str.toUpperCase()){
        result = "订单配送中";
    }else if("INSIDECABINET" == str.toUpperCase()){
        result = "已入柜";
    }else if("OUTSIDECABINET" == str.toUpperCase()){
        result = "已取出";
    }else if("OVERPICKUP" == str.toUpperCase()){
        result = "超时未取";
    }else if("FINISH" == str.toUpperCase()){
        result = "正常完成";
    }else if("TIMEOUTFINISH" == str.toUpperCase()){
        result = "超时完成";
    }else if("INDEMNITYAPPLY" == str.toUpperCase()){
        result = "赔偿申请";
    }else if("INDEMNITYCONFIRM" == str.toUpperCase()){
        result = "赔偿确认";
    }else if("INDEMNITYREFUSE" == str.toUpperCase()){
        result = "赔偿拒绝";
    }
    return result;
}