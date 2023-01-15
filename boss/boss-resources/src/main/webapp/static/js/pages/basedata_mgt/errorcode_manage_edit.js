/**
 * 
 */


$(function() {
	
	
	/*
	 * 选择下拉框列表
	 * */
	selectFun("../static/data/error_c_code.txt", "");

	var thisURL = document.URL;    
	var getval =thisURL.split('?')[1];  
	var id= getval.split("=")[1];  
	showvalf();
	function  showvalf(){ 
	   document.getElementById('id').value= id;  
	};
	
	var srhData = {
			"id" : id
		};
	tableFun("/basedata_mgt/errorcode_manage/getErrorCodeById",srhData);
	
	_errFm =  $('#form');
	_errFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
});

function selectFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			var _select = $('#systemCode');
			$.each(data, function(k, v) {
				var d_systemCode = v.systemCode;
				_select.append('<option>' + d_systemCode + '</option>');
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
					//var code1 = data.code;
					if(data.systemCode != null)
						document.getElementById("systemCode").value = data.systemCode;
					if(data.systemName != null)
						document.getElementById("systemName").value = data.systemName;
					if(data.code != null)
						document.getElementById("code").value = data.code;
					if(data.message != null)
						document.getElementById("message").value = data.message;
					if(data.level != null)
						document.getElementById("level").value = data.level;
					if(data.result != null)
						document.getElementById("result").value = data.result;
					if(data.extend != null)
						document.getElementById("extend").value = data.extend;
					if(data.version != null)
						document.getElementById("version").value = data.version;
					if(data.status != null)
						document.getElementById("status").value = data.status;
					if(data.editedBy != null)
						document.getElementById("editedBy").value = data.editedBy;
					if(data.createTime != null) {
	                    /*var date = new Date(data.createTime);
	                    var dateStr = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
	                 */   document.getElementById("createTime").value = data.createTime;
					}
						
					if(data.modifyTime != null)
						 /*var date = new Date(data.modifyTime);
                    	 var dateStr = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
						*/document.getElementById("modifyTime").value = data.modifyTime;
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
	var 
	id = $("#id").val(),
	systemCode =  $("#systemCode").val(),
	systemName =  $("#systemName").val(),
	code =  $("#code").val(),
	message =  $("#message").val(),
	level =  $("#level").val(),
	result =  $("#result").val(),
	extend =  $("#extend").val(),
	version =  $("#version").val(),
	status =  $("#status").val(),
	editedBy =  $("#editedBy").val();

	var formData = {
			"id":id,
			"systemCode":systemCode,
			"systemName":systemName,
			"code":code,
			"message":message,
			"level":level,
			"result":result,
			"extend":extend,
			"version":version,
			"status":status,
			"editedBy":editedBy
	};
	$
	.ajax({
		type : "POST",
		url : "/basedata_mgt/errorcode_manage/updateErrorCode",
		data : formData,
		dataType : "json",
		success : function(data) {
			alert(data.result);
			location = "../basedata_mgt/errorcode_manage.html";
		}
	});
};

