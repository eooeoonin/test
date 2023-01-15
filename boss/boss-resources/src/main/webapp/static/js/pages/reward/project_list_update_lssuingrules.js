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
var _pname;
var _name;
var Request = {};
Request = GetRequest();


$(function() {
    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#lssuingrulesForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
    //URL参数
    var id = Request.lssuingrulesId;
    var _proId = Request.projectId;
	_pname = decodeURI(Request.pname);
	_name = decodeURI(Request.name);
    document.getElementById("proName").value = _pname;
    document.getElementById("strName").value = _name;
    document.getElementById("awardStrategyId").value = Request.awardStrategyId;
    document.getElementById("projectId").value = Request.projectId;

    var tdUrl = "../reward/project_list/lssuingrules/getById";
    var tbData = {"id":id};
    tableFun(tdUrl,tbData);

    $.ajax({
            type:"post",
            data:{"projectId":_proId,"pageNo":"1","pageSize":"100"},
            dataType:"json",
            url:"../reward/project_list/jackpot/list",
            success:function(data){
                $.each(data.list,function(k,v){
                    var u_descName = v.descName;
                    u_id = v.id;
                    $("#awardPoolId").append("<option value='"+u_id+"'>"+u_descName+"</option>");
                })
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert(errorThrown);
            },
            async:false             //false表示同步
        }
    );

});




//编辑
function tableFun(tdUrl, tbData) {
    $.ajax({
        type : "POST",
        url : tdUrl,
        data : tbData,
        dataType : "json",
        success : function(data) {
            //把商品数据,显示在也页面上
            document.getElementById("number").value = data.number;
            $("#payType").val(""+data.payType+"");
            document.getElementById("payStep").value = data.payStep;
            $("#awardPoolId").val(""+data.awardPoolId+"");
            document.getElementById("id").value = data.id;
            document.getElementById("awardStrategyId").value = data.awardStrategyId;
        }
    });
}

$('#lssuingrulesSubmit').click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{

        var formdata = $('#lssuingrulesForm').serialize();
        $.ajax({
            type: "POST",
            url:"../reward/project_list/lssuingrules/update",
            data:formdata,
            error: function() {
                var awardStrategyId = document.getElementById("awardStrategyId").value;
                var projectId = document.getElementById("projectId").value;
                alert("修改失败");
                location = "../reward/project_list_Issuingrules_list.html?awardStrategyId="+awardStrategyId+"&projectId="+projectId+"&projectName="+encodeURI(encodeURI(_pname))+"&awardStrategyName="+encodeURI(encodeURI(_name));
            },
            success: function(data) {
                var awardStrategyId = document.getElementById("awardStrategyId").value;
                var projectId = document.getElementById("projectId").value;
                alert("修改成功");
                location = "../reward/project_list_Issuingrules_list.html?awardStrategyId="+awardStrategyId+"&projectId="+projectId+"&projectName="+encodeURI(encodeURI(_pname))+"&awardStrategyName="+encodeURI(encodeURI(_name));
            }
        });
    }
})
