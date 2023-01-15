var _pages = 0;
$(function() {
	var srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
		};
		tableFun("/activity/activityManager/getAllActivity", srhData);
		myPage();

});
$("#addBtn").click(function(){
	location.href = "/activity/activity_add.html";
});


function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			var a = data.data[1].total;
			if (a != 0) {
				var _table = $('#table'), tableBodyHtml = '';
				_pages = data.data[1].pages;
				var _data = data.data[1];
				var t = data.data[0];
/*				$.each(_data.list, function(k, v) {
					// 获取数据
					var id=v.id,code=v.code,name=v.name,desc=v.desc,modifyTime=v.modifyTime,editedBy=v.editedBy;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + id + '</td>';
					tableBodyHtml += '<td>' + code + '</td>';
					tableBodyHtml += '<td>' + name + '</td>';
					tableBodyHtml += '<td>' + channelType + '</td>';
					tableBodyHtml += '<td>' + modifyTime + '</td>';
					tableBodyHtml += '<td>' + editedBy + '</td>';
					tableBodyHtml += '<td><a onclick="edit(\''+id+'\')">编辑</a>|<a onclick="del(\''+id+'\')">删除</a></td>';
				});*/
				for(var i = 0; i < _data.list.length ; i++ ){
					var v = _data.list[i];
					var tdata = t[i];
					var id=v.id,code=v.code,name=v.name,desc=v.desc,modifyTime=v.modifyTime,editedBy=v.editedBy,startTime=v.startTime,endTime=v.endTime,tempName=tdata.name,tempType=tdata.type;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + id + '</td>';
					tableBodyHtml += '<td>' + name + '</td>';
					tableBodyHtml += '<td>' + desc + '</td>';
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
                    }else if(tempType == 5){
                        tableBodyHtml += '<td>砸蛋活动</td>';
                    }else{
						tableBodyHtml += '<td>'+tempType+'</td>';
					}
					tableBodyHtml += '<td>' + tempName + '</td>';
					tableBodyHtml += '<td>' + modifyTime + '</td>';
					tableBodyHtml += '<td>' + editedBy + '</td>';
					tableBodyHtml += '<td>' + startTime + '</td>';
					tableBodyHtml += '<td>' + endTime + '</td>';
					if(v.status == 0) {
                        if (tempType == 3) {
                            tableBodyHtml += '<td><a onclick="edit(\'' + id + '\')">编辑</a>&nbsp;|<a onclick="lookforLink(\'' + id + '\',\'' + code + '\')">编辑链接</a>&nbsp;|<a onclick="forJ(\'' + id + '\')">创建奖励</a>&nbsp;|<a onclick="upline(\'' + id + '\')">上线</a>&nbsp;|<a onclick="del(\'' + id + '\')">删除</a>|<a onclick="award(\'' + id + '\')">奖励</a></td>';
                        }else if(tempType == 5){
                        	 tableBodyHtml += '<td><a onclick="edit(\'' + id + '\')">编辑</a>&nbsp;|<a onclick="lookforLink(\'' + id + '\',\'' + code + '\')">编辑链接</a>&nbsp;|<a onclick="forJ(\'' + id + '\')">创建奖励</a>&nbsp;|<a onclick="upline(\'' + id + '\')">上线</a>&nbsp;|<a onclick="del(\'' + id + '\')">删除</a>|<a onclick="rule(\'' + id + '\',\'' + code + '\')">规则</a></td>';
                        } else {
							tableBodyHtml += '<td><a onclick="edit(\'' + id + '\')">编辑</a>&nbsp;|<a onclick="lookforLink(\'' + id + '\',\'' + code + '\')">编辑链接</a>&nbsp;|<a onclick="forJ(\'' + id + '\')">创建奖励</a>&nbsp;|<a onclick="upline(\'' + id + '\')">上线</a>&nbsp;|<a onclick="del(\'' + id + '\')">删除</a></td>';
						}
					}else{
                        if (tempType == 3) {
                            tableBodyHtml += '<td><a onclick="edit(\''+id+'\')">编辑</a>&nbsp;|<a onclick="lookforLink(\''+id+'\',\''+code+'\')">编辑链接</a>&nbsp;|<a onclick="forJ(\''+id+'\')">创建奖励</a>&nbsp;|<a onclick="downline(\''+id+'\')">下线</a>|<a onclick="award(\'' + id + '\')">奖励</a>&nbsp;</td>';
                        }else if(tempType == 5){
                        	tableBodyHtml += '<td><a onclick="edit(\''+id+'\')">编辑</a>&nbsp;|<a onclick="lookforLink(\''+id+'\',\''+code+'\')">编辑链接</a>&nbsp;|<a onclick="forJ(\''+id+'\')">创建奖励</a>&nbsp;|<a onclick="downline(\''+id+'\')">下线</a>|<a onclick="rule(\'' + id + '\',\'' + code + '\')">规则</a>&nbsp;</td>';
                        }else{
                        	tableBodyHtml += '<td><a onclick="edit(\''+id+'\')">编辑</a>&nbsp;|<a onclick="lookforLink(\''+id+'\',\''+code+'\')">编辑链接</a>&nbsp;|<a onclick="forJ(\''+id+'\')">创建奖励</a>&nbsp;|<a onclick="downline(\''+id+'\')">下线</a>&nbsp;</td>';
                        }
					}
					tableBodyHtml += '</tr>';
				}
				$("#tcdPageCode").show();
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="12" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPageCode").hide();
			}
		},
		async : false
	});
};

var myPage = function() {
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var srhData = {};
			if($("#name").val() != "" && undefined != $("#name").val() && null != $("#name").val()){
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"name":$("#name").val()
				};
			}else{
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
				};
			}
			tableFun("/activity/activityManager/getAllActivity", srhData);
		}
	});
};

$("#srhBtn").click(function(){
	var srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"name" : $("#name").val()
		};
		tableFun("/activity/activityManager/getAllActivity", srhData);
		myPage();

});
function edit(id){
	location.href = "/activity/activity_add.html?id="+id;
}
function upline(id){
	bootbox.confirm("你确定要上线吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/activity/activityManager/updateStatusActivity",
				data : {'id':id,'status':1},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('成功',function(){
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
}
function downline(id){
	bootbox.confirm("你确定要下线吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/activity/activityManager/updateStatusActivity",
				data : {'id':id,'status':0},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('成功',function(){
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
}
function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/activity/activityManager/delActivity",
				data : {'id':id},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
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
}

function award(id) {
    location.href = "/activity/activity_rule.html?id="+id;
}
function rule(id ,code) {
    location.href = "/activity/activity_signRule.html?id="+id+"&code="+code;
}
function forJ(id){
	location.href = "/reward/project_list.html?id="+id;
};
function lookforLink(id,code){
	location.href = "/activity/activity_addLink.html?activityId="+id+"&acode="+code;
}