/**
 * 
 */
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}
var Request = {};
Request = GetRequest();

$(function() {
	  //分页
	
	  $("#companyId").val(Request.companyId);
	  tableFun("/crowdfunding/merchant/projectList");
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
	    	tableFun("/crowdfunding/merchant/projectList");
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
						if(data.data.total!=0){
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
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + v.name + '</td>';
										tableBodyHtml += '<td>' + v.description + '</td>';
										tableBodyHtml += '<td>' + v.proLocationInfo.city + '</td>';
										tableBodyHtml += '<td>' + v.proLocationInfo.localCode + '</td>';
										tableBodyHtml += '<td>' + v.estateUser.username + '</td>';
										tableBodyHtml += '<td><a href="merchant_projectEdit.html?projectId=' + v.id + '" style="margin-left:15px;">编辑</a></td>';
										tableBodyHtml += '</tr>';
									});
							_table.find("tbody").html(tableBodyHtml);
							$("#tcdPagehide").show();
						}
						else{
							tableBodyHtml +='<tr class="no-records-found">';
					  		tableBodyHtml +='<td style="text-align:center; vertical-align:middle;" colspan="7">没有找到匹配的记录</td>';
					  		tableBodyHtml += '</tr>';
					 		_table.find("tbody").html(tableBodyHtml);
					 		$("#tcdPagehide").hide();
						}
					}
					else
						alert(data.msg);
				},
				async : false
			});
}

$("#returnBtn").click(function(){
	window.location.href="/crowdfunding/merchant.html";
});
