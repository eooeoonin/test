var _pages;
$(function () {
    var _table = $('#table');
    _table.bootstrapTable();
    var srhData = {"pageNo":"1","pageSize":"10"};
    tableFun("../reward/reward_list/list", srhData);
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
                    var d_id = v.id;//id
                    	u_editedBy = v.editedBy,//最后编辑人
                    	u_createTime = v.createTime,//创建时间 发放时间
                    	u_modifyTime = v.modifyTime,//最后编辑时间
                    	u_awardProjectId = v.awardProjectId,//奖励项目ID
                    	u_awardProject = v.awardProject,//奖励项目
                    	u_awardAmount = v.awardAmount,//获奖人数
                    	u_createdBy = v.createdBy;//创建人 发放人
                    var	u_grantType = v.grantType;//发放类型 GROUP:群发,SINGLE:单发
                    	if("GROUP" == v.grantType){
                    		u_grantType = "奖励群发";
                    	}
                    	if("SINGLE" == v.grantType){
                    		u_grantType = "奖励单发";
                    	}

                        //输出HTML元素
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + u_createTime + '</td>';
                    tableBodyHtml += '<td>' + u_awardProject + '</td>';
                    tableBodyHtml += '<td>' + u_grantType + '</td>';
                    tableBodyHtml += '<td>' + u_awardAmount + '</td>';
                    tableBodyHtml += '<td>' + u_createdBy + '</td>';
                    tableBodyHtml += '</tr>';
                });
                _table.find("tbody").html(tableBodyHtml);

                replaceFun(_table);
            }else{
            	var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="5">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPagehide").hide();
            }
        },error:function(data){
            alert("查询奖励记录失败")
        },
        async : false
    });
}

$("#srhBtn").click(function(){
    var awardProject = $("#awardProject").val();
    var srhData = {'pageNo':'1','pageSize':'10','awardProject':awardProject};
    tableFun("../reward/reward_list/list", srhData);
    myPage();
});

//分页
var myPage = function(){
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount : _pages,
        current : 1,
        backFn : function(p) {
        	var srhData  = {};
        	if($("#awardProject").val() != "" && $("#awardProject").val() != null){
        			srhData = {
        				"pageNo" : p,
        				"pageSize" : "10",
        				'awardProject':$("#awardProject").val()
        		};
        	}else{
        		srhData = {
        				"pageNo" : p,
        				"pageSize" : "10"
        		};
        	}
            tableFun("../reward/reward_list/list", srhData);
        }
    });
};
