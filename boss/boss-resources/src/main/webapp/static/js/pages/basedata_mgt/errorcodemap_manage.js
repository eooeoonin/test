/**
 * 
 */
var _pages;
$(function() {

	var pageData = {
		"pageNo" : "1",
		"pageSize" : "10"
	};
	tableFun("/basedata_mgt/errorcodemap_manage/getErrorCodeBySelected", pageData);
	myPage();

	/*
	 * 选择下拉框列表
	 * */

	$.ajax({
		type : "POST",
		url : "../static/data/error_c_code.txt",
		dataType : "json",
		success : function(data) {
			var _select = $('#systemCode');
			$.each(data, function(k, v) {
				var d_system = v.systemCode +"_" +  v.systemName;
				_select.append('<option>' + d_system + '</option>');
			});
		}		
	});

	
});



/*
 * 查询表格数据
 * */
function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,

				dataType : "json",
				success : function(data) {
					var _table = $('#table'), tableBodyHtml = '';
					_pages = data.pages;
					$.each(
									data.list,
									function(k, v) {
										// 获取数据
										var d_id = v.id, 
							
										d_systemCode = v.systemCode, 
										d_systemName = v.systemName, 
										d_sourceCode = v.sourceCode, 
										d_sourceInfo = v.sourceInfo, 
										d_targetCode = v.targetCode, 
										d_targetInfo = v.targetInfo,
										d_level = v.level,
										d_result = v.result,
										d_direction = v.direction,
										d_createTime = v.createTime,
										d_modifyTime = v.modifyTime;
										var ids=d_id.substring(0,6)+"...";
										// 输出HTML元素
										switch(v.direction){
										case "ToIn":
											d_direction = "转内码";
											break;
										case "ToOut":
											d_direction = "转外码";
											break;
										default:
											d_direction = "";
									
										}
										
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + ids+ '</td>';
										tableBodyHtml += '<td>' + d_systemCode + '</a></td>>';
										tableBodyHtml += '<td>' + d_systemName + '</a></td>>';
										tableBodyHtml += '<td>' + d_sourceCode + '</td>';
										tableBodyHtml += '<td>' + d_sourceInfo + '</td>';
										tableBodyHtml += '<td>' + d_targetCode + '</td>';
										tableBodyHtml += '<td>' + d_targetInfo + '</td>';
										tableBodyHtml += '<td>' + d_level + '</td>';
										tableBodyHtml += '<td>' + d_result + '</td>';
										tableBodyHtml += '<td>' + d_direction + '</td>';
										tableBodyHtml += '<td>' + d_createTime + '</td>';
										tableBodyHtml += '<td>' + d_modifyTime + '</td>';
										tableBodyHtml += '<td><a href="errorcodemap_manage_edit.html?id='+ d_id + '" style="margin-left:15px;">编辑</a>'
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
		tableFun("/basedata_mgt/errorcodemap_manage/getErrorCodeBySelected", pageData);		  
	  }
	});
};


/*
 * 添加
 * */
$("#addBtn").click(function () {
	var systemCode = $("#systemCode").val();
	if(systemCode===""||systemCode===null||systemCode==="_")
		alert("请先选择系统编码！");
	else
		window.location.href = "errorcodemap_manage_add.html?systemCode=" + systemCode;
  });

/*
 * select查询操作
 * */
var _srhBtn = $("#srhBtn");
_srhBtn.click(function() {
	var v_systemCode = $("#systemCode").val().split("_",2)[0],
	v_sourceCode = $("#sourceCode").val(),
	v_targetCode = $("#targetCode").val();
	var pageData = {
			
			"pageNo" : "1",
			"pageSize" : "10",
			"systemCode" : v_systemCode,
			"sourceCode" :v_sourceCode,
			"targetCode": v_targetCode
			
	};
	tableFun("/basedata_mgt/errorcodemap_manage/getErrorCodeBySelected",pageData);
	myPage();
});


function errorDel(id){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/errorcodemap_manage/errorCodeDelete",
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