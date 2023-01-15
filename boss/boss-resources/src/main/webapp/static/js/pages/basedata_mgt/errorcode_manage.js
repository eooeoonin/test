/**
 * error
 */
var _pages;
$(function() {

	var pageData = {
		"pageNo" : "1",
		"pageSize" : "10"
	};
	tableFun("/basedata_mgt/errorcode_manage/getErrorCodeBySelected", pageData);
	myPage();

	/*
	 * 选择下拉框列表
	 * */
	selectFun("../static/data/error_c_code.txt", "");
	function selectFun(tdUrl, tbData) {
		$.ajax({
			type : "POST",
			url : tdUrl,
			data : tbData,
			dataType : "json",
			success : function(data) {
				var _select = $('#systemCode');
				$.each(data, function(k, v) {
					var d_system = v.systemCode + "_" + v.systemName;
					_select.append('<option>' + d_system + '</option>');
				});
			}		
		});
	}
});



/*
 * 查询表格数据
 * */
function tableFun(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,

				dataType : "json",
				success : function(data) {
					var _table = $('#table'), tableBodyHtml = '';
					_pages = data.pages;
					$
							.each(
									data.list,
									function(k, v) {
										// 获取数据
										var d_id = v.id, 
										d_systemCode = v.systemCode, 
										d_systemName = v.systemName, 
										d_code = v.code, 
										d_message = v.message, 
										d_level = v.level,
										d_result = v.result,
										d_createTime = v.createTime,
										d_modifyTime = v.modifyTime;
										var ids=d_id.substring(0,6)+"...";
										// 输出HTML元素
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + ids+ '</td>';
										tableBodyHtml += '<td>' + d_systemCode + '</a></td>>';
										tableBodyHtml += '<td>' + d_systemName + '</a></td>>';
										tableBodyHtml += '<td>' + d_code + '</td>';
										tableBodyHtml += '<td>' + d_message + '</td>';
										tableBodyHtml += '<td>' + d_level + '</td>';
										tableBodyHtml += '<td>' + d_result + '</td>';
										tableBodyHtml += '<td>' + d_createTime + '</td>';
										tableBodyHtml += '<td>' + d_modifyTime + '</td>';
										tableBodyHtml += '<td><a href="errorcode_manage_edit.html?id='+ d_id + '" style="margin-left:15px;">编辑</a>'
										+'<a  name='+d_id+' href="javascript:" style="margin-left:15px;" onclick="errorDel(this)">删除</a></td>';
										tableBodyHtml += '</tr>';
									});
					_table.find("tbody").html(tableBodyHtml);

				},
				async : false
			});

}

// 分页
var myPage = function(){
	//分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {
		  var v_systemCode = $("#systemCode").val().split("_",2)[0],
			v_sourceCode = $("#sourceCode").val(),
			v_targetCode = $("#targetCode").val();
		var pageData = {"pageNo" : p,
				"pageSize" : "10",
				"systemCode" : v_systemCode,
				"sourceCode" :v_sourceCode,
				"targetCode": v_targetCode};
		tableFun("/basedata_mgt/errorcode_manage/getErrorCodeBySelected", pageData);		  
	  }
	});
}


/*
 * 添加
 * */
$("#addButton").click(function () {
    
	var systemCode = $("#systemCode").val();
	if(systemCode===""||systemCode===null||systemCode==="_")
		alert("请先选择系统编码！");
	else
		window.location.href = "errorcode_manage_add.html?systemCode=" + systemCode;
  
  });

/*
 * select查询操作
 * */
var _srhBtn = $("#srhBtn");
_srhBtn.click(function() {
	var v_systemCode = $("#systemCode").val().split("_",2)[0],
	v_code = $("#code").val();
	var pageData = {
			
			"pageNo" : "1",
			"pageSize" : "10",
			"systemCode" : v_systemCode,
			"code" :v_code
		
	};
	tableFun("/basedata_mgt/errorcode_manage/getErrorCodeBySelected",pageData);
	myPage();
});

function errorDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/errorcode_manage/errorCodeDelete",
					data:{
						"id":id.name
					},
					dataType: "json",
					async:false,
					success : function(data) {
						if (data !== null && data !== "") {
								bootbox.alert("删除成功", function(result){
									window.location.reload();
								});
						}
					}
				});
			}
		});
}