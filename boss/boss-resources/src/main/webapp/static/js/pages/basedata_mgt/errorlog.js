/**
 * 
 */
$(function() {
	  //分页
	  tableFun("/basedata_mgt/errorlog/errorLogList");
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
	    	tableFun("/basedata_mgt/errorlog/errorLogList");
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
								data.data.list,
								function(k, v) {
									var d_type = "";
									switch(v.type){
										case "1":
											d_type="整个分片错误";
											break;
										case "2":
											d_type="单个分片中某一分页出错";
											break;
										default:
											d_type = v.type;
										break;
									
									};
									
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + v.id+ '</td>';
									tableBodyHtml += '<td>' + d_type+ '</td>';
									tableBodyHtml += '<td>' + v.errorCode+ '</td>';
									tableBodyHtml += '<td>' + v.errorMsg+ '</td>';
									tableBodyHtml += '<td>' + v.content+ '</td>';
									tableBodyHtml += '<td>' + v.shardingParameter+ '</td>';
									tableBodyHtml += '<td>' + v.pageNum+ '</td>';
									tableBodyHtml += '<td>' + v.status+ '</td>';
									tableBodyHtml += '<td>' + v.createTime+ '</td>';
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
						replaceFun(_table);
					}
					else
						alert(data.msg);
				},
				async : false
			});
}


