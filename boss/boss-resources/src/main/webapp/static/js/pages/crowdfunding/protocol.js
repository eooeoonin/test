/**
 * 
 */
var _pages;
$(function() {
	  //分页
	  tableFun("/crowdfunding/protocol/protocolList");
	  var $curP = $("#currPage"),
	  $pageC = $("#pageCount");
	  var curpage = parseInt($curP.val());
	  var pageCount = parseInt($pageC.val());
	  var $tcdPage = $(".tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: pageCount,
	    current: curpage,
	    backFn: function (p) {
	    	$curP.val(p);
	    	tableFun("/crowdfunding/protocol/protocolList");
	    }
	  });
});

function tableFun(tdUrl) {
	var form = $("#form").serialize();
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : form,
				dataType : "json",
				success : function(data) {
					if(data.resCode == 0) {
						var _table = $('#table'), tableBodyHtml = '';
						$("#currPage").val(data.data.pageNum);
			    		$("#pageCount").val(data.data.pages);
						$.each(
								data.data,
								function(k, v) {
									var d_type;
									switch(v.type){
									case "MICRO_CROWD":
										d_type = "微筹";
										break;
									case "MUST_CROWD":
										d_type = "定筹";
										break;
									case "DOUBLE_CROWD":
										d_type = "倍筹";
										break;
									default:
										d_type = v.type;
									    break;
									}
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + v.name + '</td>';
									tableBodyHtml += '<td>' + d_type + '</td>';
									tableBodyHtml += '<td>' + v.createTime + '</td>';
									tableBodyHtml += '<td><a href="protocol_toEditPage.html?protocolId=' + v.id + '" style="margin-left:15px;">编辑</a>'+
									'<a href="#" style="margin-left:15px;" onclick="deletePro(\''+ v.id +'\')">删除</a></td>';
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
					}
					else
						alert(data.msg);
				},
				async : false
			});
}


var _addButton = $("#addButton");
_addButton.click(function(){
	window.location.href = "protocol_toAddPage.html";
});

function deletePro(pid){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/crowdfunding/protocol/protocolDelete",
					data : {"protocolId" : pid},
					dataType: "json",
					async:false,
					success : function(data) {
						
						if (data != null && data != "") {
							if (data.msg == "success") {
								bootbox.alert("协议删除成功", function(result){
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
