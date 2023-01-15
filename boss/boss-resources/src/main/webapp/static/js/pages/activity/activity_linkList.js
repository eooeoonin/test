var acode = getUrlParam("acode");
var _pages;
$(function() {
	var srhData = {
			"pageNo" : "1",
			"pageSize" : "30",
			"aActivityCode":acode
	};
	tableFun("/activity/activityManager/getAllLink", srhData);
	chkFun();
	myPage();
});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			var a = data.data.total;
			if (a != 0) {
				var _table = $('#table'), tableBodyHtml = '';
				_pages = data.data.pages;
				var _data = data.data;
				$.each(_data.list, function(k, v) {
					// 获取数据
					var channelCode = v.channelCode,channelName=v.channelName,aactivityCode=v.aactivityCode,activityName=v.activityName,linkName=v.linkName;
					var weisrc = mktUrl + aactivityCode + "/static/qrcode/"+channelCode+".png";
					var id =v.id;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input  type="checkbox" name="ckbox" class="sub_ckbox" value="' + id + '"></label></td>';
					tableBodyHtml += '<td>' + channelCode + '</td>';
					tableBodyHtml += '<td>' + channelName + '</td>';
					tableBodyHtml += '<td>' + aactivityCode + '</td>';
					tableBodyHtml += '<td>' + activityName + '</td>';
					tableBodyHtml += '<td>' + linkName + '</td>';
					tableBodyHtml += '<td><img src="'+weisrc+'" width="100px"></td>';
					tableBodyHtml += '<td><a href="'+weisrc+'" download>下载二维码</a>&nbsp;|<a onclick="del(\''+id+'\')">删除</a></td>';
					tableBodyHtml += '</tr>';
				});
				$("#tcdPageCode").show();
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="12" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPageCode").hide();
			}
		},
		async : false
	});
};

var myPage = function() {
	// 分页
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			// 点击分页事件
			var srhData = {
					"pageNo" : p,
					"pageSize" : "30",
					"aActivityCode":acode,
					"channelCode":$("#channelCode").val()
			};
			tableFun("/activity/activityManager/getAllLink", srhData);
			chkFun();
		}
	});
};
$("#srhBtn").click(function() {  
	var channelCode = $("#channelCode").val();
	var srhData = {
			"pageNo" : "1",
			"pageSize" : "30",
			"aActivityCode":acode,
			"channelCode":channelCode
	};
	tableFun("/activity/activityManager/getAllLink", srhData);
	chkFun();
	myPage();
});

/* 批量genggai  */   	
$("#pDownload").click(function() {  
	// 判断是否至少选择一项 
    var checkedNum = $("input[name='ckbox']:checked").length;  
    if(checkedNum == 0) {  
        alert("请选择至少一项！");  
        return;  
    }
      
 // 批量选择   

        var checkedList = new Array();  
        $("input[name='ckbox']:checked").each(function() {  
        	
            checkedList.push($(this).val()); 
           
        });  
        var tData = {
        		'ids':checkedList
        };
/*        	$.ajax({
                type: "POST",  
                url: "/activity/activityManager/qrCode/download",
                data: tData,
                dataType:"json",
                traditional:true,
                success: function(data) {  
                	
                },error: function(){
                	bootbox.alert('下载失败',function(){
                		location.href = "/activity/activity_list.html";
		        	});
        		} 
            });*/
    window.location.href = "/activity/activityManager/qrCode/download?ids=" + checkedList.toString();


});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};
function _downLoad(src){
	window.location.href = src;
};
function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/activity/activityManager/delLink",
				data : {'id':id},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('删除成功',function(){
			        		window.location.reload();
			        	});
					} else {
						bootbox.alert("操作失败");
					}
					;
				}
			});
		} else {
			return;
		}
	});
};
function chkFun() {
    $(".i-checks").iCheck({
        checkboxClass: "icheckbox_square-green",
        radioClass: "iradio_square-green"
    });

    var _jCheckAll = $("#jCheckAll"),
        _subCheck = $('input[type="checkbox"].sub_ckbox');
    _jCheckAll.on('ifChecked', function () {
        _subCheck.iCheck('check');


    });
    _jCheckAll.on('ifUnchecked', function () {
        _subCheck.iCheck('uncheck');
    });
};