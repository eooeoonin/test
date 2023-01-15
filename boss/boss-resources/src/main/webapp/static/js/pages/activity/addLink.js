var _pages;
var channelId;
var activityId = getUrlParam("activityId");
var acode = getUrlParam("acode");
$(function() {
	var srhData = {};
	$.ajax({
		type : "POST",
		url : "/activity/channelManage/getAllChannelType",
		data : {},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$.each(data.data, function(k, v) {
					$("#channelType").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
				});
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
	tableFun("/activity/channelManage/getAllData", srhData);
	chkFun();
});

$('#channelType').change(function(){
	var srhData = {};
	var channelType = $("#channelType option:selected")[0].value;
	if(channelType != null && channelType != ""){
		srhData = {'channelTypeId':channelType};
	}else{
		srhData = {};
	}
	tableFun("/activity/channelManage/getAllData", srhData);
	chkFun();
});  


function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			var a = data.data.length;
			if (a > 0) {
				var _table = $('#table'), tableBodyHtml = '';
				var _data = data.data;
				$.each(_data, function(k, v) {
					// 获取数据
					var id=v.id,code=v.code,name=v.name,channelType=v.channelType,modifyTime=v.modifyTime,editedBy=v.editedBy;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input  type="checkbox" name="ckbox" class="sub_ckbox" value="' + id + '"></label></td>';
					tableBodyHtml += '<td>' + code + '</td>';
					tableBodyHtml += '<td>' + name + '</td>';
					tableBodyHtml += '<td>' + channelType + '</td>';
					tableBodyHtml += '<td>' + modifyTime + '</td>';
					tableBodyHtml += '<td>' + editedBy + '</td>';
					tableBodyHtml += '</tr>';
				});
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="6" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
			}
		},
		async : false
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

/* 批量genggai  */   	
$("#payPast").click(function() {  
	// 判断是否至少选择一项 
    var checkedNum = $("input[name='ckbox']:checked").length;  
    if(checkedNum == 0) {  
        alert("请选择至少一项！");  
        return;  
    }
      
 // 批量选择   
    if(confirm("确定要创建所选渠道链接？")) {  
        var checkedList = new Array();  
        $("input[name='ckbox']:checked").each(function() {  
        	
            checkedList.push($(this).val()); 
           
        });  
        var tData = {
        		"ids":checkedList,
        		"activityId":activityId,
        		"linkUrl" : mktUrl
        };
        	$.ajax({  
                type: "POST",  
                url: "/activity/activityManage/createLink",  
                data: tData,
                dataType:"json",
                traditional:true,
                success: function(data) {  
                	bootbox.alert('创建成功',function(){
                		location.href = "/activity/activity_linkList.html?acode="+acode;
		        	});
                },error: function(){
                	bootbox.alert('创建失败',function(){
                		location.href = "/activity/activity_list.html";
		        	});
        		} 
            }); 
    }             
});

$("#lookList").click(function() {
	location.href = "/activity/activity_linkList.html?acode="+acode;
}); 
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};