/***
 *** 获取URL参数
 ***/
var _pname;
var _name;
var _pages = 0;
var count = 1;
var minCount = 1;
var requestUrl;
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

if(Request.type == 'edit'){
	 $("#titior")[0].textContent = "编辑行为";
	 requestUrl = '../reward/project_list/behavior/update';
    $.ajax({
        type : "POST",
        url : "../reward/project_list/behavior/getById",
        data : {id:Request.behaviorId},
        dataType : "json",
        success : function(data) {
            document.getElementById("id").value = data[0].id;
            document.getElementById("strategyId").value = data[0].strategyId;
            if(data[0].first){
                $("#actCode option[id='investFirst']").attr("selected", true);
            }else{
                $("#actCode").val(""+data[0].actCode+"");
            }
            $('#actCode').change();
            $("#single").val(""+data[0].single+"");
            $("#userGrade").val(""+data[0].userGrade+"");
            $("#beneficiary").val(data[0].beneficiary); 
            document.getElementById("min").value = data[0].min;
            document.getElementById("max").value = data[0].max;
            handleAssociationType(data[0]);
            if(data[1].length > 0){
            	$('#single').change();
            	for(i=0;i<data[1].length;i++){
            		if(i == 0){
            			  $("#shouyiren").val(data[1][i].beneficiary);
            			  $("#zuida").val(data[1][i].max+"");
            			  $("#zuixiao").val(data[1][i].min+"");
            		}else{
            			  firstAdd();
            			  $("#shouyiren"+i).val(data[1][i].beneficiary);
            			  $("#zuida"+i).val(data[1][i].max+"");
            			  $("#zuixiao"+i).val(data[1][i].min+"");
            		}
            	}
            }
        }
    });
}else{
	requestUrl = '../reward/project_list/behavior/insert';
}

$(function () {
	_pname = decodeURI(Request.pname);
	_name = decodeURI(Request.name);
	document.getElementById("strName").value = _name;
	document.getElementById("proName").value = _pname;
    document.getElementById("strategyId").value = Request.awardStrategyId;
    document.getElementById("projectId").value = Request.projectId;
    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#behaviorForm');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });

	$('#single').change();
    // 选择关联类型时
    $('#associationType').change(function(){
        var associationType = $("#associationType option:selected")[0].value;
        if(associationType != null && associationType != "") {
            if("ACTIVITY" == associationType){
                $("#_activity_show").show();
                $("#_channel_show").hide();
                $("#channelSelectedCode").val("");
            }else if("CHANNEL" == associationType){
                $("#_channel_show").show();
                $("#_activity_show").hide();
                $("#activitySelectedCode").val("");
            }
        }
    });

    $('#actCode').change(function(){
        var p = $(this).children('option:selected');//这就是selected的值
        if("investFirst" == p.attr("id")){
            $("#first").val("true");
        }else{
        	$("#first").val("false");
        }

    });


});

//选择活动弹层
$("#checkActivity").click(function () {
    var srhData = {
        "pageNo": "1",
        "pageSize": "10"
    };
    tableFunActivity("/activity/activityManager/getAllActivity", srhData);
    myPage();
    $("#activityModal").modal("show");
    chkFun();
});


//查询活动按钮
$("#activity_srhSmtBtn").click(function(){
    var srhData = {
        "pageNo" : "1",
        "pageSize" : "10",
        "name" : $("#name").val()
    };
    tableFunActivity("/activity/activityManager/getAllActivity", srhData);
    myPage();
    chkFun();

});

//选择渠道弹层
$("#checkChannel").click(function () {
    $("#channelType").empty();
    $('input:checkbox').removeAttr('checked');

    $.ajax({
        type : "POST",
        url : "/activity/channelManage/getAllChannelType",
        data : {},
        dataType : "json",
        success : function(data) {
            if(data.resCode == 0){
                $.each(data.data, function(k, v) {
                    $("#channelType").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
                });
            }else{
                bootbox.alert("数据加载异常");
            }
        },
        async : false
    });

    var srhData = {
        "pageNo": "1",
        "pageSize": "10"
    };
    tableFunChannel("/activity/channelManage/getAllData", srhData);
    $("#channelModal").modal("show");
    chkFun();
});


$('#channelType').change(function(){
    $('input:checkbox').removeAttr('checked');
    var srhData = {};
    var channelType = $("#channelType option:selected")[0].value;
    if(channelType != null && channelType != ""){
        srhData = {'channelTypeId':channelType};
    }else{
        srhData = {};
    }
    tableFunChannel("/activity/channelManage/getAllData", srhData);
    chkFun();
});

$('#single').change(function(){
	var p = $(this).children('option:selected').val();//这就是selected的值  
	if(p == "true"){
		$('#singleMax').hide();
		$('#ycben').show();
	}else{
		$('#ycben').hide();
		$('#singleMax').show();
	}
});








function chkFun() {
    $(".i-checks").iCheck({
        checkboxClass: "icheckbox_square-green",
        radioClass: "iradio_square-green"
    });

    var _jCheckAll = $("#jCheckAll"),
        _subCheck = $('input[type="checkbox"].sub_ckbox');
    _jCheckAll.on('ifChecked', function () {
        _subCheck.iCheck('check');
    });
    _jCheckAll.on('ifUnchecked', function () {
        _subCheck.iCheck('uncheck');
    });
};


function tableFunActivity(tdUrl, tbData) {
    $.ajax({
        type: "POST",
        url: tdUrl,
        data: tbData,
        dataType: "json",
        async: false,
        success: function (data) {
            var html = "";
            _pages = data.data[1].pages;
            var _data = data.data[1];
            var templateInfo = data.data[0];
            var _table = $('#activity_table'), tableBodyHtml = '';
            for(var i = 0; i < _data.list.length ; i++ ){
                var v = _data.list[i];
                var tdata = templateInfo[i];
                var id=v.id,code=v.code,name=v.name,desc=v.desc,modifyTime=v.modifyTime,editedBy=v.editedBy,startTime=v.startTime,endTime=v.endTime,tempName=tdata.name,tempType=tdata.type;
                tableBodyHtml += '<tr>';
                tableBodyHtml += '<td><label class=\"radio-inline i-checks\"><input type=\"radio\"  title="" =\"'+ v.name+ '\" value=\"'+ v.code+ '\" name=\"radio\" class=\"sub_radbox\"></label></td>';
                tableBodyHtml += '<td>' + name + '</td>';
                tableBodyHtml += '<td>' + code + '</td>';
                if(tempType == 0){
                    tableBodyHtml += '<td>礼包类</td>';
                }else if(tempType == 1){
                    tableBodyHtml += '<td>抽奖类</td>';
                }else if(tempType == 2){
                    tableBodyHtml += '<td>邀请减免类</td>';
                }else if(tempType == 3){
                    tableBodyHtml += '<td>集采类</td>';
                }else if(tempType == 4){
                    tableBodyHtml += '<td>扫码活动</td>';
                }
                tableBodyHtml += '<td>' + tempName + '</td>';
                tableBodyHtml += '<td>' + startTime + '</td>';
                tableBodyHtml += '<td>' + endTime + '</td>';
            }
            tableBodyHtml += '</tr>';
            $("#tcdPageCode").show();
            _table.find("tbody").html(tableBodyHtml);
            replaceFun(_table);

        }
    });
}


var myPage = function() {
    // 分页
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount : _pages,
        current : 1,
        backFn : function(p) {
            // 点击分页事件
            srhData = {
                "pageNo" : p,
                "pageSize" : "10",
            };
            tableFunActivity("/activity/activityManager/getAllActivity", srhData);
            chkFun();
        }
    });
};




function tableFunChannel(tdUrl, tbData) {
    $.ajax({
        type : "POST",
        url : tdUrl,
        data : tbData,
        dataType : "json",
        success : function(data) {
            var a = data.data.length;
            if (a > 0) {
                var _table = $('#channel_table'), tableBodyHtml = '';
                var _data = data.data;
                $.each(_data, function(k, v) {
                    // 获取数据
                    var id=v.id,code=v.code,name=v.name,channelType=v.channelType,modifyTime=v.modifyTime,editedBy=v.editedBy;
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input  type="checkbox" name="ckbox" class="sub_ckbox" title="' + name + '" value="'+ code + '"></label></td>';
                    tableBodyHtml += '<td>' + code + '</td>';
                    tableBodyHtml += '<td>' + name + '</td>';
                    tableBodyHtml += '<td>' + channelType + '</td>';
                });
                _table.find("tbody").html(tableBodyHtml);
                replaceFun(_table);
            } else {
                var html = "";
                html += '<tr class="no-records-found">';
                html += '<td colspan="4" style="text-align:center">没有找到匹配的记录</td>';
                html += '</tr>';
                $("#channel_table").find("tbody").html(html);
            }
        },
        async : false
    });
};


//选择选中的活动
$("#choose").click(function () {
    var radioChecked=$('input:radio[name="radio"]:checked').val();
    $("#activitySelected").html(radioChecked);
    $("#activitySelectedCode").val(radioChecked);
    $('#activityModal').modal('hide');
});

//选择选中的渠道
$("#chooseChannel").click(function() {
    // 判断是否至少选择一项
    var checkedNum = $("input[name='ckbox']:checked").length;
    if(checkedNum == 0) {
        alert("请选择至少一项！");
        return;
    }
    // 批量选择
    var checkedList = new Array();
    var checkedChannelName = new Array();
        $("input[name='ckbox']:checked").each(function() {
            checkedList.push($(this).val());
            checkedChannelName.push($(this).attr("title"));
        });
    $("#channelSelected").html(checkedList.toString());
    $("#channelSelectedCode").val(checkedList.toString());
    $('#channelModal').modal('hide');

});


// 保存行为
$("#behaviorSubmit").click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        $.ajax({
            type:"POST",
            url:requestUrl,
            data:$("#behaviorForm").serialize(),
            dataType: "json",
            success:function(data){
                var awardStrategyId = document.getElementById("strategyId").value;
                var projectId = document.getElementById("projectId").value;
                alert("操作成功");
                location = "../reward/project_list_behavior_list.html?awardStrategyId="+awardStrategyId+"&projectId="+projectId+"&projectName="+encodeURI(encodeURI(_pname))+"&awardStrategyName="+encodeURI(encodeURI(_name));
            },error:function(data){
                var awardStrategyId = document.getElementById("strategyId").value;
                var projectId = document.getElementById("projectId").value;
                alert("操作失败");	
                location = "../reward/project_list_behavior_list.html?awardStrategyId="+awardStrategyId+"&projectId="+projectId+"&projectName="+encodeURI(encodeURI(_pname))+"&awardStrategyName="+encodeURI(encodeURI(_name));
               
            }
        })
    }
});
function firstAdd(){
	 $("#xingwei tbody").append('<tr id="fmin'+count+'"><td class="input-group-addon"> <em>*</em>受益人</td><td><select class="form-control" name="behaviorInfo['+count+'].shouyiren" id="shouyiren'+count+'"> <option value="ONESELF">本人</option> <option value="INVITER1">一级邀请</option> <option value="INVITER2">二级邀请</option> </select> </td> <td class="input-group-addon"><em>*</em>单次最小值</td> <td> <input type="text"  class="form-control validate[required]" data-errormessage-value-missing="最小值不能为空" name="behaviorInfo['+count+'].zuixiao" id="zuixiao'+count+'"> </td> <td class="input-group-addon" width="20%"> <em>*</em>单次最大值</td> <td width="30%"> <input type="text"  class="form-control validate[required]" data-errormessage-value-missing="最大值不能为空" name="behaviorInfo['+count+'].zuida" id="zuida'+count+'"></td><td colspan="2" align="right"><button onclick="firstMin(\'fmin'+count+'\')" class="btn btn-primary subChanges" style="float: right;margin-right: 21%" type="button"> <i class="fa fa-minus"></i></button></td> </tr>');
	 count++;
	 minCount++;
};
function firstMin(fmin){
	if(minCount == 1){
		return;
	}
	$("#"+fmin).remove();
	minCount--;
};
function handleAssociationType(data) {
    if(data.activityCode != null && data.activityCode != "" && data.activityCode != undefined){
        $("#associationType").val("ACTIVITY");
        $("#_activity_show").show();
        $("#_channel_show").hide();
        $("#activitySelected").html(data.activityCode);
        $("#activitySelectedCode").val(data.activityCode);
    }else{
        $("#associationType").val("CHANNEL");
        $("#_channel_show").show();
        $("#_activity_show").hide();
        $("#channelSelected").html(data.channelCode);
        $("#channelSelectedCode").val(data.channelCode);
    }


}