var eventCode =  getUrlParam("eventCode");
var mappingId;
$(function() {
	/**
	 * 模态窗口非空/数字校验
	 */
	
	_modalFm =  $('#modal_form1');
	_modalFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	tableFun("/basedata_mgt/eventMapping/getAllMapping",  {"eventCode":eventCode});
	
	
	$.ajax({
		type : "POST",	
		url : "/basedata_mgt/eventMapping/getAllPara",
		data : {pageIndex:0,pageSize:0},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$.each(data.data, function(k, v) {
					$("#tmpleCode").append("<option value='" + v.code + "'>" + v.code + "    :        "+v.name +"</option>"); 
					$("#ectype").append("<option value='" + v.code + "'>" + v.code + "    :        "+v.name +"</option>"); 
				});
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
	
	
	$.ajax({
		type : "POST",	
		url : "/basedata_mgt/event/eventlist",
		data : {pageNo:1,pageSize:1000},
		dataType : "json",
		success : function(data) {
			console.log(data);
			if(data.resCode == 0){
				$.each(data.data.businessObject.list, function(k, v) {
					if(eventCode == v.code) {
						$("#subject").append("<option selected  value='" + v.code + "'>" + v.name +"</option>");
						$("#queryName").append("<td selected  value='" + v.code + "'>" + v.name +"</td>");
					} else {						
						$("#subject").append("<option value='" + v.code + "'>" + v.name +"</option>"); 
					}
					
				});
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
	
});



function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			console.log(data);
			
			var _data = data.data.list;
			console.log(_data);
			if (_data != "") {
				var _table = $('#table'), tableBodyHtml = '';
				$.each(_data, function(k, v) {
					var id=v.id,
					eventProp=v.eventProp,
					avariableCode=v.avariableCode,
					createTime=v.createTime,
					modifyTime=v.modifyTime,
					editedBy=v.editedBy;
					tableBodyHtml += '<tr>';
					tableBodyHtml += '<td>' + eventProp + '</td>';
					tableBodyHtml += '<td>' + avariableCode + '</td>';
					tableBodyHtml += '<td>' + createTime + '</td>';
					tableBodyHtml += '<td><a onclick="edit(\''+id+'\')">编辑</a>&nbsp;|&nbsp;<a onclick="del(\''+id+'\')">删除</a></td>';
					tableBodyHtml += '</tr>';
				});
				$("#tcdPageCode").show();
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
			} else {
				var html = "";
				html += '<tr class="no-records-found">';
				html += '<td colspan="7" style="text-align:center">没有找到匹配的记录</td>';
				html += '</tr>';
				$("#table").find("tbody").html(html);
				$("#tcdPageCode").hide();
			}
		},
		async : false
	});
};

$("#srhQuery").click(function() {
	var eventProp1 = $("#subject").val();
	$("#queryName").text($("#subject option:selected").text());
	var srhData2 = {
			"eventCode":eventProp1
	};
		tableFun("/basedata_mgt/eventMapping/getAllMapping",srhData2);
});



$("#addMapping").click(function() {
	$('#MappingModal').modal('show');
});




$("#subTran").click(function() {
	if (!$("#modal_form1").validationEngine('validate')) {
		return false;
	};	
	var avariableCode = $("#tmpleCode option:selected")[0].value;
	var eventProp = $("#MappingProperty").val();
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/eventMapping/addMapping",
		data : {'eventCode':eventCode,'avariableCode':avariableCode,'eventProp':eventProp},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#MappingProperty")[0].value = "";
				$('#MappingModal').modal('hide');
				bootbox.alert('保存成功',function(){
	        		window.location.reload();
	        	});
			}else{
				bootbox.alert("操作失败");
			}
		},
		async : false
	});
});



$("#subMappingModal").click(function() {
	var mappingTypes = $("#ectype option:selected")[0].value;
	var eventProp = $("#mappingAttribute").val();
	var srhData = {
			'id':mappingId,'eventProp':eventProp,'avariableCode':mappingTypes
		};
		$.ajax({
			type : "POST",
			url : "/basedata_mgt/eventMapping/editMapping",
			data : srhData,
			dataType : "json",
			success : function(data) {
				if(data.resCode == 0){
					$('#editEventModal').modal('hide');
					bootbox.alert('修改成功',function(){
		        		window.location.reload();
		        	});
				}else{
					bootbox.alert("数据加载异常");
				}
			},
			async : false
		});
});


function edit(id){
	mappingId = id;
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/eventMapping/getById",
		data : {'id':id},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#mappingAttribute")[0].value = data.data.eventProp;
				$("#ectype").val(data.data.avariableCode);
				$('#editEventModal').modal('show');
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
};



function del(id){
	bootbox.confirm("你确定要删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/basedata_mgt/eventMapping/delMapping",
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

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};