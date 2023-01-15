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


var _pages;var _id;var _pid;var _name;var _pname;
$(function () {
    var _table = $('#table');
    _table.bootstrapTable();
    _id = Request.awardStrategyId;
    _pid = Request.projectId;
    _pname = decodeURI(Request.projectName);
    _name = decodeURI(Request.awardStrategyName);
    var srhData = {"strategyId":_id,"pageNo":"1","pageSize":"10"};
    tableFun("../reward/project_list/behavior/list", srhData);
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
                    var u_id = v.id;
                    u_actCode = v.actCode,   //行为分类---不确定
                        u_first = v.first,
                        u_userGrade = v.userGrade,
                        u_min = v.min,
                        u_max = v.max,
                        u_beneficiary = v.beneficiary,
                        single = v.single;
                    
                    if(u_beneficiary == "ONESELF"){
                    	u_beneficiary = "本人";
                    }else if(u_beneficiary == "INVITER1"){
                    	u_beneficiary = "1级邀请";
                    }else if(u_beneficiary == "INVITER2"){
                    	u_beneficiary = "2级邀请";
                    }else{
                    	u_beneficiary = "——";
                    }
                    if(single){
                    	single = "单次";
                    }else{
                    	single = "<a href='project_list_behavior_list_song.html?id="+u_id+"'>累计</a>";
                    }
                    if(u_actCode == 'REGISTER'){
                        u_actCode = '注册';
                    }if(u_actCode == 'LOGIN'){
                        u_actCode = '登录';
                    }if(u_actCode == 'REAL_NAME'){
                        u_actCode = '实名';
                    }if(u_actCode == 'BANK_CARD'){
                        u_actCode = '绑卡';
                    }if(u_actCode == 'INVITE_BANK_CARD'){
                        u_actCode = '邀请绑卡';
                    }if(u_actCode == 'RECHARGE'){
                        u_actCode = '充值';
                    }if(u_actCode == 'WITHDRAW'){
                        u_actCode = '提现';
                    }if(u_actCode == 'INVEST'){
                        u_actCode = '出借';
                    }if(u_actCode == 'FIRST_INVEST'){
                        u_actCode = '首次出借';
                    }if(u_actCode == 'INVITE'){
                        u_actCode = '邀请';
                    }if(u_actCode == 'INVITE1'){
                        u_actCode = '一级邀请';
                    }if(u_actCode == 'INVITE2'){
                        u_actCode = '二级邀请';
                    }if(u_actCode == 'INVITE3'){
                        u_actCode = '三级邀请';
                    }if(u_actCode == 'BEINVITED'){
                        u_actCode = '受邀请红包';
                    }if(u_actCode == 'FEEDBACK'){
                        u_actCode = '反馈';
                    }if(u_actCode == 'WINNING'){
                        u_actCode = '中奖';
                    }if(u_actCode == 'REPAY'){
                        u_actCode = '回款';
                    }

                    if(u_first == true){
                        u_first = '是';
                    }if(u_first == false){
                        u_first = '否';
                    }

                    if(u_userGrade == '1'){
                        u_userGrade = '普通会员';
                    }if(u_userGrade == '2'){
                        u_userGrade = 'VIP';
                    }if(u_userGrade == '3'){
                        u_userGrade = 'SVIP';
                    }




                    //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + _pname + '</td>';
                    tableBodyHtml += '<td>' + _name + '</td>';
                    tableBodyHtml += '<td>' + u_actCode + '</td>';
                    tableBodyHtml += '<td>' + u_first + '</td>';
                    tableBodyHtml += '<td>' + u_userGrade + '</td>';
                    tableBodyHtml += '<td>' + u_beneficiary + '</td>';
                    tableBodyHtml += '<td>' + single + '</td>';
                    tableBodyHtml += '<td>' + u_min + '</td>';
                    tableBodyHtml += '<td>' + u_max + '</td>';
                    tableBodyHtml += '<td><a href="project_list_insert_behavior.html?type=edit&behaviorId='+u_id+'&projectId='+_pid+'&pname='+encodeURI(encodeURI(_pname))+'&name='+encodeURI(encodeURI(_name))+'">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a onclick="del(\''+u_id+'\')">删除</a>';
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
                "strategyId":_id
            };
            tableFun("../reward/project_list/behavior/list", srhData);
        }
    });
};

function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "../reward/project_list/behavior/del",
				data : {'id':id},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.businessObject == 1) {
						bootbox.alert('删除成功',function(){
			        		window.location.reload();
			        	});
					} else {
						bootbox.alert("操作失败");
					}
					;
				}
			});
		} else {
			return;
		}
	});
};

function returnStr(){
	  window.location.href="project_list_strategy_list.html?projectId="+_pid+"&projectName="+encodeURI(encodeURI(_pname));
}

function addbehavior(){
    window.location.href = "project_list_insert_behavior.html?type=add&awardStrategyId="+_id+"&projectId="+_pid+"&pname="+encodeURI(encodeURI(_pname))+"&name="+encodeURI(encodeURI(_name));
}
