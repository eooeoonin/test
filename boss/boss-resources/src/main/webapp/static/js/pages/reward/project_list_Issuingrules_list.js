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



var _pages;var _id;var _proId;var _name;var _pname;
$(function () {
    var _table = $('#table');
    _table.bootstrapTable();
    _id = Request.awardStrategyId;
    _proId = Request.projectId;
    _pname = decodeURI(Request.projectName);
    _name = decodeURI(Request.awardStrategyName);
    var srhData = {"awardStrategyId":_id,"pageNo":"1","pageSize":"10"};
    tableFun("../reward/project_list/lssuingrules/list", srhData);
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
                    u_number = v.number,
                        u_payType = v.payType,
                        u_payStep = v.payStep,
                        u_awardPoolId = v.awardPoolId;

                    if(u_payType == "1"){
                        u_payType = '年';
                    }if(u_payType == "2"){
                        u_payType = '月';
                    }if(u_payType == "6"){
                        u_payType = '日';
                    }

                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + _pname + '</td>';
                    tableBodyHtml += '<td>' + _name + '</td>';
                    tableBodyHtml += '<td>' + u_number + '</td>';
                    tableBodyHtml += '<td>' + u_payType + '</td>';
                    tableBodyHtml += '<td>' + u_payStep + '</td>';
                    tableBodyHtml += '<td>' + u_awardPoolId + '</td>';
                    tableBodyHtml += '<td><a href="project_list_update_lssuingrules.html?lssuingrulesId='+u_id+'&projectId='+_proId+'&pname='+encodeURI(encodeURI(_pname))+'&name='+encodeURI(encodeURI(_name))+'">编辑</a>';
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
                "pageNo" : p,
                "pageSize" : "10",
                "awardStrategyId":_id
            };
            tableFun("../reward/project_list/lssuingrules/list", srhData);
        }
    });
};

function returnStr(){
	  window.location.href="project_list_strategy_list.html?projectId="+_proId+"&projectName="+encodeURI(encodeURI(_pname));
}

function addlssuingrules(){
    window.location.href="project_list_insert_lssuingrules.html?awardStrategyId="+_id+"&projectId="+_proId+"&pname="+encodeURI(encodeURI(_pname))+"&name="+encodeURI(encodeURI(_name));
}
