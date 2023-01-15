/**
 * 
 */

var addErrorCode = function(){
	//var formData=$("#form").serialize();
	var 
	systemCode =  $("#systemCode").val().substring(0,3),
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
	editedBy =  $("#editedBy").val();
	direction = $("#direction").val();
	var formData = {
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
		url : "/basedata_mgt/errorcodemap_manage/addErrorCode",
		data : formData,
		dataType : "json",
		success : function(data) {
			if(data.resCode==0)
				alert("添加成功");
			else
				alert(data.msg);
			//location = "../basedata_mgt/errorcodemap_manage.html";
		}
	});
};

$(function() {
	
	
	var thisURL = document.URL;    
	var getval =thisURL.split('?')[1];  
	var systemCode= getval.split("=")[1].split("_",2)[0];
	var systemName= getval.split("=")[1].split("_",2)[1];
	showvalf();
	function  showvalf(){ 
	   $("#systemCode").val(systemCode);
	   $("#systemName").val(systemName);
	};
		
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


$("#addErrorCode").click(function(){
	if (!_errFm.validationEngine('validate')) {
	    return false;
	  }
	addErrorCode();
});

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



