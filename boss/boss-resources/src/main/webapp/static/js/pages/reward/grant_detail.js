var _pages;
$(function() {
	var _table = $('#table');
	_table.bootstrapTable();
	var srhData = {
		"pageNo" : "1",
		"pageSize" : "10"
	};
	tableFun("/reward/grant_detail/getAllData", srhData);
	myPage();

});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				var a = data.data.total;
				if (a != 0) {
					var _table = $('#table'), tableBodyHtml = '';
					_pages = data.data.pages;
					var _data = data.data;
					$.each(_data.list, function(k, v) {
						// 获取数据
						var userId = v.userId,
						projectId = v.projectId,
						awardPoolId = v.awardPoolId,
						createdBy = v.createdBy,
						awardPoolNum = v.awardPoolNum,
						status = v.status;
						tableBodyHtml += '<tr>';
						tableBodyHtml += '<td>' + userId + '</td>';
						tableBodyHtml += '<td>' + projectId + '</td>';
						tableBodyHtml += '<td>' + awardPoolId + '</td>';
						tableBodyHtml += '<td>' + createdBy + '</td>';
						tableBodyHtml += '<td>' + awardPoolNum + '</td>';
						if(status == 1){
							tableBodyHtml += '<td>成功</td>';
							tableBodyHtml += '<td>——</td></tr>';
						}else{
							tableBodyHtml += '<td>失败</td>';
							tableBodyHtml += '<td><a onclick="regrant(\'' + v.id + '\',\'' + userId + '\',\'' + projectId + '\',\'' + awardPoolId + '\',\'' + awardPoolNum + '\')">重发</a></td></tr>';
						}
					});
					$("#tcdPagehide").show();
					_table.find("tbody").html(tableBodyHtml);
					replaceFun(_table);
				} else {
					var html = "";
					html += '<tr class="no-records-found">';
					html += '<td colspan="7">没有找到匹配的记录</td>';
					html += '</tr>';
					$("#table").find("tbody").html(html);
					$("#tcdPagehide").hide();
				}
			}else{
				bootbox.alert("接口异常");
			}

		},
		async : false
	});
}
$("#srhBtn").click(function() {
	var userId = $("#userId").val();
	var srhData;
	if (userId != null && userId != "") {
		srhData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"userId" : userId
		};
	} else {
		srhData = {
			"pageNo" : "1",
			"pageSize" : "10"
		};
	}
	tableFun("/reward/grant_detail/getAllData", srhData);
	myPage();
});

var myPage = function() {
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {};
			var userId = $("#userId").val();
			if (userId != null && userId != "") {
				srhData = {
					"pageNo" : p,
					"pageSize" : "10",
					"userId" : userId
				};
			} else {
				srhData = {
					"pageNo" : p,
					"pageSize" : "10"
				};
			}
			// 点击分页事件
			tableFun("/reward/grant_detail/getAllData", srhData);
		}
	});
};
function regrant(id,userId,projectId,awardPoolId,awardPoolNum) {
	tbData = {"id":id,"userId":userId,"projectId":projectId,"awardPoolId":awardPoolId,"awardPoolNum":awardPoolNum}
	$.ajax({
		type : "POST",
		url : "/reward/grant_detail/regrant",
		data : tbData,
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				bootbox.alert('操作成功',function(){
					location.href = "";
	        	});
			}else{
				bootbox.alert('操作失败',function(){
					location.href = "";
	        	});
			}
		}
	});
}