/**
 * 
 */

var _pages;

$(function() {
	var _select = $('#systemCode');
	selectFun("../static/data/error_c_code.txt", "",_select);

	var thisURL = document.URL;    
	var getval =thisURL.split('?')[1];  
	var id= getval.split("=")[1];  
	showvalf();
	function  showvalf(){ 
	   document.getElementById('ID').value= id;  
	};
	
	var srhData = {
			"id" : id
		};
	tableFun("/basedata_mgt/errorcodemap_manage/getErrorCodeById",srhData);
	
	_errFm =  $('#form');
	_errFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});

});

function selectFun(tdUrl, tbData, _select) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			//var _select = $('#systemCode');
			$.each(data, function(k, v) {
				var d_system = v.systemCode;
				_select.append('<option>' + d_system + '</option>');
			});
		}
	});
}

function tableFun(tdUrl, tbData) {
	$
			.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
					if(data.systemCode != null)
						document.getElementById("systemCode").value = data.systemCode;
					if(data.systemName != null)
						document.getElementById("systemName").value = data.systemName;
					if(data.sourceCode != null)
						document.getElementById("sourceCode").value = data.sourceCode;
					if(data.sourceInfo != null)
						document.getElementById("sourceInfo").value = data.sourceInfo;
					if(data.targetCode != null)
						document.getElementById("targetCode").value = data.targetCode;
					if(data.targetInfo != null)
						document.getElementById("targetInfo").value = data.targetInfo;
					if(data.level != null)
						document.getElementById("level").value = data.level;
					if(data.result != null)
						document.getElementById("result").value = data.result;
					if(data.extend != null)
						document.getElementById("extend").value = data.extend;
					if(data.version != null)
						document.getElementById("version").value = data.version;
					if(data.deleted != null)
						document.getElementById("deleted").value = data.deleted;
					if(data.status != null)
						document.getElementById("status").value = data.status;
					if(data.direction != null)
						document.getElementById("direction").value = data.direction;
					if(data.editedBy != null)
						document.getElementById("editedBy").value = data.editedBy;
					if(data.createTime != null) {
	                    document.getElementById("createTime").value = data.createTime;
					}
						
					if(data.modifyTime != null)
						document.getElementById("modifyTime").value = data.modifyTime;
				}
			});
};

$("#editErrorCode").click(function(){
	if (!_errFm.validationEngine('validate')) {
	    return false;
	  }
	editErrorCode();
});
var editErrorCode = function(){
	//var formData=$("#form").serialize();
	var id = $("#ID").val(),
	systemCode =  $("#systemCode").val(),
	systemName =  $("#systemName").val(),
	sourceCode =  $("#sourceCode").val(),
	sourceInfo =  $("#sourceInfo").val(),
	targetCode =  $("#targetCode").val(),
	targetInfo =  $("#targetInfo").val(),
	level =  $("#level").val(),
	result =  $("#result").val(),
	extend =  $("#extend").val(),
	version =  $("#version").val(),
	deleted =  $("#deleted").val(),
	status =  $("#status").val(),
	editedBy =  $("#editedBy").val(),
	direction = $("#direction").val();
	var formData = {
			"id":id,
			"systemCode":systemCode,
			"systemName":systemName,
			"sourceCode":sourceCode,
			"sourceInfo":sourceInfo,
			"targetCode":targetCode,
			"targetInfo":targetInfo,
			"level":level,
			"result":result,
			"extend":extend,
			"version":version,
			"status":status,
			"editedBy":editedBy,
			"direction":direction
	};
	$
	.ajax({
		type : "POST",
		url : "/basedata_mgt/errorcodemap_manage/updateErrorCode",
		data : formData,
		dataType : "json",
		success : function(data) {
			if(data.result != null && data.result != ""){
				alert(data.result);
			}else {
				alert("保存成功")
			}
			location = "../basedata_mgt/errorcodemap_manage.html";
		}
	});
};

var bankFun = function(name){
	$('#modalType').val(name);	
	var _select = $('#modalSystemCode');
	selectFun("../static/data/error_c_code.txt", "",_select);
	thData = {
		"pageNo":"1",
		"pageSize":"10"
	};
	tableFunModal("/basedata_mgt/errorcodemap_manage/getErrorCode",thData);
	myPageModal();
	$('#myModal').modal('show');
	
};

function tableFunModal(tdUrl, tbData) {
	$
	.ajax({
		type : "POST",
		data : tbData,
		url : tdUrl,
		dataType : "json",
		success : function(data) {
			var _table2 = $('#modaltable'), tableBodyHtml = '';
			_pages = data.pages;
			$.each(data.list,function(k,v){
				var d_level = "",d_result = "",d_extend = "";
				if(v.level != null)
					d_level = v.level;
				if(v.result != null)
					d_result = v.result;
				if(v.extend != null)
					d_extend = v.extend;
				tableBodyHtml += '<tr>';
				tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input type="radio" name="radio" class="sub_radbox"></label></td>';
				tableBodyHtml += '<td title="'+v.systemCode+'">' + v.systemCode+ '</td>';
				tableBodyHtml += '<td title="'+v.systemName+'">' + v.systemName+ '</td>';
				tableBodyHtml += '<td title="'+v.code+'">' + v.code+ '</td>';
				tableBodyHtml += '<td title="'+v.message+'">' + v.message+ '</td>';
				tableBodyHtml += '<td title="'+d_level+'">' + d_level+ '</td>';
				tableBodyHtml += '<td title="'+d_result+'">' + d_result+ '</td>';
				tableBodyHtml += '<td title="'+d_extend+'">' + d_extend+ '</td>';
				tableBodyHtml += '</tr>';
			});
			_table2.find("tbody").html(tableBodyHtml);
		},
		async : false
	});
}

var myPageModal = function(){
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {
		  var systemCode = $('#modalSystemCode').val(),
		  modalCode = $("#modalCode").val();
			var pageData = {
			"pageNo" : p,
			"pageSize" : "10",
			"systemCode":systemCode,
			"code":modalCode
			};
		tableFunModal("/basedata_mgt/errorcodemap_manage/getErrorCode", pageData);		  
	  }
	});
};


$("#modalsrhBtn").click(function(){
	var systemCode = $('#modalSystemCode').val(),
	  modalCode = $("#modalCode").val();
		var pageData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"systemCode":systemCode,
		"code":modalCode
		};
	tableFunModal("/basedata_mgt/errorcodemap_manage/getErrorCode", pageData);		
	myPageModal();
});

$("#modal_save").click(function(){
	var modalType = $('#modalType').val();
	var radio=document.getElementsByName("radio");
    var code= null,message = null,level=null,result=null,extend = null;
   for(var i=0;i<radio.length;i++){
	   if(radio[i].checked==true) {
		   var obj= radio[i].parentNode.parentNode;
		   var codeObj = obj.nextSibling.nextSibling.nextSibling;
		   var messageObj = codeObj.nextSibling,
		   levelObj = messageObj.nextSibling,
		   resultObj = levelObj.nextSibling,
		   extendObj = resultObj.nextSibling;
		   code = codeObj.title;
		   message = messageObj.title;
		   level = levelObj.title;
		   result = resultObj.title;
		   extend = extendObj.title;
		   break;
	   	}
   	} 
   if(code == null){
	   alert("请选择");
	   return false;
   }

   if(modalType == "bsourceCode") {
	   $("#sourceCode").val(code);
	   $("#level").val(level);
	   $("#result").val(result);
	   $("#extend").val(extend);
	   $("#sourceInfo").val(message);
	   $("#targetInfo").val(message); 
   }   
   else if(modalType == "btargetCode")
	   $("#targetCode").val(code);
   /*if(message != null && message != ""){
	   $("#sourceInfo").val(message);
	   $("#targetInfo").val(message); 
   }
   if(level != null && level != "")
	   $("#level").val(level);
   if(result != null && result != "")
	   $("#result").val(result);
   if(extend != null && extend != "")
	   $("#extend").val(extend);*/
   $('#myModal').modal('hide');
});


