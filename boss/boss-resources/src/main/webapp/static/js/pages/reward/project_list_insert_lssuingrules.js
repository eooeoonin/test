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
var _pname;
var _name;
var _id;var _proId;
$(function () {
    _id = Request.awardStrategyId;
    _proId = Request.projectId;
	_pname = decodeURI(Request.pname);
	_name = decodeURI(Request.name);
    document.getElementById("proName").value = _pname;
    document.getElementById("strName").value = _name;
    document.getElementById("awardStrategyId").value = Request.awardStrategyId;
    document.getElementById("projectId").value = Request.projectId;
    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#lssuingrulesForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });

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


$("#lssuingrulesSubmit").click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        $.ajax({
            type:"POST",
            url:"../reward/project_list/lssuingrules/insert",
            data:$("#lssuingrulesForm").serialize(),
            dataType: "json",
            success:function(data){
                alert("添加成功")
                location = "../reward/project_list_Issuingrules_list.html?awardStrategyId="+_id+"&projectId="+_proId+"&projectName="+encodeURI(encodeURI(_pname))+"&awardStrategyName="+encodeURI(encodeURI(_name));
            },error:function(){
                alert("添加失败")
                location = "../reward/project_list_Issuingrules_list.html?awardStrategyId="+_id+"&projectId="+_proId+"&projectName="+encodeURI(encodeURI(_pname))+"&awardStrategyName="+encodeURI(encodeURI(_name));
            }
        })
    }
});