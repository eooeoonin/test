/**
 * 
 */
var _pages;
$(function() {
	  //分页
	  tableFun("/crowdfunding/merchant/merchantList");
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
	    	tableFun("/crowdfunding/merchant/merchantList");
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
									var d_companyType;
									
									switch(v.companyType){
									case "STATE_OWNED":
										d_companyType = "国有企业";
										break;
									case "PRI_OWNED":
										d_companyType = "民营企业";
										break;
									case "FOR_OWNED":
										d_companyType = "外资企业";
										break;
									case "JOIN_OWNED":
										d_companyType = "合资企业";
										break;
									case "INDIVIDUAL":
										d_companyType = "个体企业";
										break;
									case "OTHER":
										d_companyType = "其他";
										break;
									default:
										d_companyType = v.companyType;
									    break;
									}
									var url = "merchant_projectAdd.html?companyId=" + v.id + "&companyName="+ v.name ;
									url = encodeURI(url);
									tableBodyHtml += '<tr>';
									tableBodyHtml += '<td><a href="merchant_toDetailPage.html?companyId=' + v.id + '">' + v.name + '</td>';
									tableBodyHtml += '<td>' + d_companyType + '</td>';
									tableBodyHtml += '<td>' + v.agent + '</td>';
									tableBodyHtml += '<td><a href='+url+' style="margin-left:15px;">添加项目</a>'
									+'<a href="merchant_projectList.html?companyId=' + v.id + '" style="margin-left:15px;">项目列表</a>'
									+'<a href="merchant_toEditPage.html?companyId=' + v.id + '" style="margin-left:15px;">编辑</a></td>';
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


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	$("#currPage").val(1);
	tableFun("/crowdfunding/merchant/merchantList");
	 
	  var $curP = $("#currPage"),
	  $pageC = $("#pageCount");
	  var curpage = 1;
	  var pageCount = parseInt($pageC.val());
	  var $tcdPage = $(".tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: pageCount,
	    current: curpage,
	    backFn: function (p) {
	    	$curP.val(p);
	    	tableFun("/crowdfunding/merchant/merchantList");
	    }
	  });
});

var _addButton = $("#addButton");
_addButton.click(function(){
	window.location.href = "merchant_toAddPage.html";
});
