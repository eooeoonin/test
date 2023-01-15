/**
 * 银行错误码展示页
 */
var _pages;
$(function() {
	var srhData1 = {
		"pageNo" : "1",
		"pageSize" : "20"
	};
	tableFun("/gateway/return_code/listReturnCode", srhData1);
	myPage2();
	var _table = $('#table');
	_table.bootstrapTable();
});
/**
 * 
 */
function tableFun(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if (data.list !== "") {
						var _table = $('#table'), tableBodyHtml = '';
						_pages = data.pages;
						$
								.each(
										data.list,
										function(k, v) {
											/**
											 * 获取数据信息
											 */
											var d_id = v.id, // id
											d_channel = v.channel, // 通道编码
											d_business = v.business, // 业务类型
											d_outerCode = v.outerCode, // 外部编码
											d_outerInfo = v.outerInfo, // 外部编码说明
											d_innerCode = v.innerCode, // 内部编码
											d_innerInfo = v.innerInfo, // 内部编码说明
											d_result = v.result, // 结果：FAIL=失败, PROCESSING=处理中、 SUCCESS=成功
											d_useState = v.useState, // 使用状态：1=启用  0=未知，待配置 -1=不启用
											d_extend = v.extend, // 扩展信息
											d_status = v.status, // 状态预留
											d_editedBy = v.editedBy, // 最后编辑人
											d_createTime = v.createTime, // 创建时间
											d_modifyTime = v.modifyTime;// 最后编辑时间
											var useState1;
											if (d_useState == 1) {
												useState1 = "启用";
											} else if (d_useState == 0) {
												useState1 = "待配置";
											} else if (d_useState == -1) {
												useState1 = "不启用";
											}

											// 输出HTML元素
											tableBodyHtml += '<tr>';
											tableBodyHtml += '<td>'
													+ d_createTime + '</td>';
											tableBodyHtml += '<td>' + d_channel
													+ '</td>';
											tableBodyHtml += '<td>'
													+ d_business + '</td>';
											tableBodyHtml += '<td>'
													+ d_outerCode + '</td>';
											tableBodyHtml += '<td>'
													+ d_outerInfo + '</td>';
											tableBodyHtml += '<td>'
													+ d_innerCode + '</td>';
											tableBodyHtml += '<td>'
													+ d_innerInfo + '</td>';
											tableBodyHtml += '<td>' + d_result
													+ '</td>';
											tableBodyHtml += '<td>' + useState1
													+ '</td>';
											tableBodyHtml += '<td>'
													+ d_editedBy + '</td>';
//											tableBodyHtml += '<td><a href="/gateway/return_code_update.html?id='+ d_id+ '"style="margin-left:10px;">编辑&nbsp;</a><a name='+d_id+'href="javascript:"style="margin-left:10px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
											tableBodyHtml += '<td><a href="../gateway/return_code_update.html?id='+d_id+'">编辑</a><a  name='+d_id+' href="javascript:"  onclick="deleteIsystemConfig(this)">删除</a></td>'
											tableBodyHtml += '</tr>';
										});
						_table.find("tbody").html(tableBodyHtml);
						replaceFun(_table);
						$("#tcdPageCode").show();
					} else {
						tableBodyHtml += '<tr class="no-records-found">';
						tableBodyHtml += '<td colspan="9" align="center">没有找到匹配的记录</td>';
						tableBodyHtml += '</tr>';
						_table.find("tbody").html(tableBodyHtml);
						$("#tcdPageCode").hide();
					}
				},
				async : false
			});
}
function myPage2() {
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(q) {
			var _channel = $("#channel").val();
			var _business = $("#business").val();
			var _outerCode = $("#outerCode").val();
			var srhData4 = {
				"pageNo" : q,
				"pageSize" : "20",
				"channel" : _channel,
				"business" : _business,
				"outerCode" : _outerCode
			};
			tableFun("/gateway/return_code/listReturnCode", srhData4);
		}
	});
}
var _insertBtn = $("#insertBtn");
_insertBtn.click(function() {
	location = "../gateway/return_code_insert.html";
});
var _srhBtn = $("#srhBtn");
_srhBtn.click(function() {
	var _channel = $("#channel").val();
	var _business = $("#business").val();
	var _outerCode = $("#outerCode").val();
	var srhData2 = {
		"pageNo" : "1",
		"pageSize" : "20",
		"channel" : _channel,
		"business" : _business,
		"outerCode" : _outerCode
	};
	tableFun("/gateway/return_code/listReturnCode", srhData2);
	myPage2();
});

function deleteIsystemConfig(sid) {
	bootbox.confirm("确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/gateway/return_code/deleteReturnCode",
				data : {
					"id" : sid.name
				},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data != null && data != "") {
						if (data == 1) {
							bootbox.alert("删除成功", function(result) {
								window.location.reload();
							});
						} else {
							bootbox.alert(data.msg);
						}
					}
				},
				async : false
			});
		}
	});
}