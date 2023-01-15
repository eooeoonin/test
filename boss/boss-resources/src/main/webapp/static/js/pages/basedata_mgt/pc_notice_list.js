var _pageSize = 15;
var _pages;
$(function () {
	var start = {
		elem : "#startTime",
		format : "YYYY/MM/DD hh:mm",
		istime : true,
		istoday : true,
		choose : function(datas) {
			end.min = datas;
			end.start = datas;
		}
	};
	var end = {
		elem : "#endTime",
		format : "YYYY/MM/DD hh:mm",
		istime : true,
		istoday : true,
		choose : function(datas) {
			start.max = datas;
		}
	};
	laydate(start);
	laydate(end);
	
	var srhData = {"pageNo":"1","pageSize":_pageSize};
	tableFun("/basedata_mgt/pc_notice_list/notice/page", srhData);	
	myPage();
	
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：LSC
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
});

$("#srhBtn").click(function() {  
	var srhData = {
			"pageNo" : "1",
			"pageSize" : _pageSize,
			"platform" : $("#platform_sel").val(), 
			"availableTimeBefore" : $("#startTime").val(), 
			"availableTimeAfter" : $("#endTime").val(), 
			"available" : $("#available_sel").val() 
	};
	tableFun("/basedata_mgt/pc_notice_list/notice/page", srhData);
	myPage();
});

/***
 *功能说明：表格数据
 *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
 *创建人：LSC
 *时间：2016-08-01
 ***/
 function tableFun(tdUrl, tbData) {
	   $.ajax({
    type: "POST",
    url: tdUrl,
    data: tbData,
    async : false,
    dataType: "json",
    success: function (data) {
  	_pages = data.pages;
      var _table = $('#table'), tableBodyHtml = '';
//      var _data = eval('(' + data + ')');
      $.each(data.list, function (k, v) {
        //获取数据
        var d_ID = v.id,
        	d_editedBy=v.editedBy,
	        d_title = v.title,
	        d_body = v.body;
        	d_availableTimeBefore = v.availableTimeBefore;
        	d_availableTimeAfter = v.availableTimeAfter;
        
         var d_platform = v.platform;
         if (d_platform == 'PC') {
        	 d_platform = 'PC 弹窗公告';
         } else {
        	 if (v.isRollTop == '0') {
        		 d_platform = 'PC 列表公告';
        		 d_availableTimeBefore = '-';
        		 d_availableTimeAfter = '-';
        	 } else {
        		 d_platform = 'PC 列表公告【置顶滚动】'; 
        	 }
         }
//          var date = new Date(d_availableTimeBefore);
//          var d_availableTimeBefore = date.getFullYear() + '-' + ( date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();  
//          
//          var date = new Date(d_availableTimeAfter);
//          var d_availableTimeAfter = date.getFullYear() + '-' +( date.getMonth()+1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();  
        //输出HTML元素
        tableBodyHtml += '<tr>';
        tableBodyHtml += '<td>' + d_title+ '</td>';
        tableBodyHtml += '<td>' + d_body + '</td>';
        tableBodyHtml += '<td>' + d_platform + '</td>';
        tableBodyHtml += '<td>' +d_availableTimeBefore+ '</td>';
        tableBodyHtml += '<td>' +d_availableTimeAfter+ '</td>';
        tableBodyHtml += '<td>' +d_editedBy+ '</td>';
        tableBodyHtml += '<td class="w_available">' + (v.available == '1' ? '开启' : '关闭') + '</td>>';
        tableBodyHtml += '<td><a  href="pc_notice_list_edit.html?id='+d_ID+'">编辑</a><a title='+v.id +' href="#" style="margin-left:5px;" onclick="deleteiportol(\''+ v.id  +'\')">删除</a>' + 
			'<a href="javascript:" id="'+ v.id +'"  onclick="changeStatusFun(\''+ v.id  +'\')" style="margin-left:5px;" class="changeStatus">' + (v.available == 0 ? "开启" : "关闭") + '</a></td>';
        tableBodyHtml += '</tr>';
      });
      _table.find("tbody").html(tableBodyHtml);
    }
  });
}

var changeStatusFun = function(_id){
	var changeText = "";
	var changeText1 = $("#"+_id).html();
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/pc_notice_list/notice/changeStatus",
		data : {
			"id" : _id
		},
		dataType: "json",
		async:false,
		success : function(data) {
			if(data.flag > 0){
				alert("操作成功！");
				if(changeText1 == "开启"){
					changeText = "关闭";
				}else{
					changeText = "开启";
				}
				$("#"+_id).html(changeText);
				$("#"+_id).parent().parent().find(".w_available").html(changeText1);
			} else {
				alert("操作失败！");
			}
		},
		async : false
	});
};
function deleteiportol(pid){
	bootbox.confirm("确定要删除吗?", function(result){
		if(result){
			$.ajax({
				type : "POST",
				url : "/basedata_mgt/pc_notice_list/notice/delete",
				data : {
					"id" : pid
				},
				dataType: "json",
				async:false,
				success : function(data) {

					if (data != null && data != "") {
						if (data == 1) {
							bootbox.alert("删除成功", function(result){
								var pageData = {
										"pageNo" : 1,
										"pageSize" : _pageSize,
										"platform" : $("#platform_sel").val(), 
										"availableTimeBefore" : $("#startTime").val(), 
										"availableTimeAfter" : $("#endTime").val(), 
										"available" : $("#available_sel").val() 
								};
								tableFun("/basedata_mgt/pc_notice_list/notice/page", pageData);
							});
						} else {
							bootbox.alert("操作失败");
						}
					}
				},
				async : false
			});
		}
	});
}

var myPage = function(){
	//分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount: _pages,
		current: 1,
		backFn: function (p) {
			var pageData = {
					"pageNo" : p,
					"pageSize" : _pageSize,
					"platform" : $("#platform_sel").val(), 
					"availableTimeBefore" : $("#startTime").val(), 
					"availableTimeAfter" : $("#endTime").val(), 
					"available" : $("#available_sel").val() 
			};
			tableFun("/basedata_mgt/pc_notice_list/notice/page", pageData);
		}
	});
}
