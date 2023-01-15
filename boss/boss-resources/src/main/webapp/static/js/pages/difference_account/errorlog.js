/**
 * 
 */
$(function() {
	  //分页
	  tableFun("/difference_account/errorlog/errorlog");
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
	    	tableFun("/difference_account/errorlog/errorlog");
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
					var _table = $('#table'), tableBodyHtml = '';
						$("#currPage").val(data.pageNum);
			    		$("#pageCount").val(data.pages);
						$.each(
								data.list,
								function(k, v) {
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td>' + v.id+ '</td>';
								    tableBodyHtml += '<td>' + v.content+ '</td>';
									tableBodyHtml += '<td>' + v.createTime+ '</td>';
									tableBodyHtml += '</tr>';
								});
						_table.find("tbody").html(tableBodyHtml);
					
				},
				async : false
			});
}


