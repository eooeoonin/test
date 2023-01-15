/***
 *** 获取URL参数
 ***/
function GetRequest() {
    var url = decodeURI(location.search); //获取url中"?"符后的字串
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

var _proId;
$(function () {
    document.getElementById("projectId").value = Request.projectId;
    document.getElementById("name").value = Request.name;
    _proId = Request.projectId;
    //单个时间引用方式
    laydate({elem: "#endDate", format: "YYYY/MM/DD hh:mm:ss"});
    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#cardcouponsForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });

    $.ajax({
            type:"post",
            data:{"projectId":_proId,"pageNo":"1","pageSize":"10"},
            dataType:"json",
            url:"../reward/project_list/jackpot/list",
            success:function(data){
                $.each(data.list,function(k,v){
                    var u_name = v.name;
                    u_id = v.id;
                    $("#poolId").append("<option value='"+u_id+"'>"+u_name+"</option>");
                })
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                alert(errorThrown);
            },
            async:false             //false表示同步
        }
    );


});

$("#cardcouponsSubmit").click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        $.ajax({
            type:"POST",
            url:"../reward/cardcoupons_list/insert",
            data:$("#cardcouponsForm").serialize(),
            dataType: "json",
            success:function(data){
                alert("添加成功")
                location = "../reward/cardcoupons_list.html";
            },error:function(){
                alert("添加失败")
                location = "../reward/cardcoupons_list.html";
            }
        })
    }
});




var pp;
function changetype(){
    if(document.getElementById("type").value == 'LOTTERY'){
        $("#addextend").removeAttr("disabled");
        $("#deleteextend").removeAttr("disabled");
        document.getElementById("address").style.display="";//显示
        document.getElementById("tbo").style.display="";//显示
        document.getElementById("tbootwo").style.display="none"; //隐藏

//				$.ajax({
//			        type:"post",
//			        data:{"projectId":_proId,"pageNo":"1","pageSize":"10"},
//			        dataType:"json",
//			        url:"../reward/cardcoupons_list/list",
//			        success:function(data){
//			            $.each(data.list,function(k,v){
//			            	var u_name = v.name;
//			            		  u_id = v.id;
//			                $("#lotteryChangesReqVo").append("<option value='"+u_id+"'>"+u_name+"</option>");
//			            })
//			        },
//			        error : function(XMLHttpRequest, textStatus, errorThrown) {
//			            alert(errorThrown);
//			    },
//			      async:false             //false表示同步
//			    }
//			    );

    }else if(document.getElementById("type").value == 'VOUCHEROFFLINE'){
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="";  //显示
    }else if(document.getElementById("type").value == 'VOUCHERH5'){
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="";  //显示
    }else if(document.getElementById("type").value == 'VOUCHER'){
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="";  //显示
    }else{
        $("#addextend").attr("disabled","disabled");
        $("#deleteextend").attr("disabled","disabled");
        document.getElementById("address").style.display="none";
        document.getElementById("tbo").style.display="none";//隐藏
        document.getElementById("tbootwo").style.display="none"; //隐藏
    }
}


var trHtml;
var len = 0;
$("#addextend").click(function (){

    len = len + 1;
    var extend = $("#tbo");
    trHtml = '';
    trHtml += '<tr name="insert'+len+'">';
    trHtml += '<td class="input-group-addon" width="20%"> <em>*</em>名称</td>';
    trHtml += '<td width="30%">';
    trHtml += '<input class="form-control" name="lotteryChangesReqVos['+len+'].actCode">';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+len+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>最小中奖概率</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos['+len+'].chMini" >';
    trHtml += '</td>';
    trHtml += '</tr>';
    trHtml += '<tr name="insert'+len+'">';
    trHtml += '<td class="input-group-addon"> <em>*</em>最大中奖概率</td>';
    trHtml += '<td>';
    trHtml += '<input type="text" class="form-control validate[required]" name="lotteryChangesReqVos['+len+'].chMax">';
    trHtml += '</td>';
    trHtml += '</tr>';
    extend.append(trHtml);

    $("#"+len+"").append($("#lotteryChangesReqVo").html());
//			 $.ajax({
//			        type:"post",
//			        data:{"projectId":_proId,"pageNo":"1","pageSize":"10"},
//			        dataType:"json",
//			        url:"../reward/cardcoupons_list/list",
//			        success:function(data){
//			            $.each(data.list,function(k,v){
//			            	var u_name = v.name;
//			            		  u_id = v.id;
//			                $("#"+len+"").append("<option value='"+u_id+"'>"+u_name+"</option>");
//			            })
//			        },
//			        error : function(XMLHttpRequest, textStatus, errorThrown) {
//			            alert(errorThrown);
//			    },
//			      async:false             //false表示同步
//			    }
//			    );
})

$("#deleteextend").click(function(){
    $("tr[name=insert"+len+"]").remove();
    len = len - 1;
})