/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function() {
	
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});

	/***************************************************************************
	 * 功能说明：复选框、单选框美化 参数说明： 创建人：李波涛 时间：2016-07-15
	 **************************************************************************/
	$(".i-checks").iCheck({
		checkboxClass : "icheckbox_square-green",
		radioClass : "iradio_square-green"
	});

	/**
	 * 功能说明：时间插件 参数说明： 创建人：李波涛 时间：2016-07-15
	 */
	var start = {
		elem : "#createTime",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			end.min = datas;
			end.start = datas
		}
	};
	var end = {
		elem : "#modifyTime",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			start.max = datas
		}
	};
	laydate(start);
	laydate(end);

	// 加载时直接定位光标
	document.getElementById('codeKey').focus();
	
	
	
});

// 校验
// codeKey
function searchquest() {
	if (document.getElementById("codeKey").value.length == 0) {
		document.getElementById("codeKey").focus();
		document.getElementById("label_id").innerHTML = "<font color='red'>不能为空!</font>";
	} else if (document.getElementById("codeKey").value.length != 0) {
		document.getElementById("label_id").innerHTML = "<font color='green'></font>";
	} 
}

//systemCode
function test6(){
	if (document.getElementById("systemCode").value.length == 0) {
		document.getElementById("systemCode").focus();
		document.getElementById("label_id6").innerHTML = "<font color='red'>不能为空!</font>";
	} else if (document.getElementById("systemCode").value.length != 0) {
		document.getElementById("label_id6").innerHTML = "<font color='green'></font>";
	} 
}

// classCode
/*function test1() {
	var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (document.getElementById("classCode").value.length <= 20) {
		document.getElementById("label_id1").innerHTML = "<font color='green'></font>";
	} else {
		$("#classCode").val("").focus();
		document.getElementById("label_id1").innerHTML = "<font color='red'>20个数字以内!</font>";
	}
}*/
/* classDesc */
/*function test2() {
	var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (document.getElementById("classDesc").value.length <= 20) {
		document.getElementById("label_id2").innerHTML = "<font color='green'></font>";
	} else {
		$("#classDesc").val("").focus();
		document.getElementById("label_id2").innerHTML = "<font color='red'>20个数字以内!</font>";
	}
}*/
// codeValue编码值
function test3() {
	var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (document.getElementById("codeValue").value.length <= 20) {
		document.getElementById("label_id3").innerHTML = "<font color='green'></font>";
	} else {
		$("#codeValue").val("").focus();
		document.getElementById("label_id3").innerHTML = "<font color='red'>20个数字以内!</font>";
	}
}
// codeDesc的校验编码描述
function test4() {
	var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (document.getElementById("codeDesc").value.length <= 20) {
		document.getElementById("label_id4").innerHTML = "<font color='green'></font>";
	} else {
		$("#codeDesc").val("").focus();
		document.getElementById("label_id4").innerHTML = "<font color='red'>20个数字以内!</font>";
	}
}
/* unit */
/*function test5() {
	var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (document.getElementById("unit").value.length <= 20) {
		document.getElementById("label_id5").innerHTML = "<font color='green'></font>";
	} else {
		$("#unit").val("").focus();
		document.getElementById("label_id5").innerHTML = "<font color='red'>20个数字以内!</font>";
	}
}*/
/* systemCode */
/*function test6() {
	var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (document.getElementById("systemCode").value.length <= 20) {
		document.getElementById("label_id6").innerHTML = "<font color='green'></font>";
	} else {
		$("#systemCode").val("").focus();
		document.getElementById("label_id6").innerHTML = "<font color='red'>20个数字以内!</font>";
	}
}*/
/* systemDesc */
/*function test7() {
	var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
	if (document.getElementById("systemDesc").value.length <= 20) {
		document.getElementById("label_id7").innerHTML = "<font color='green'></font>";
	} else {
		$("#systemDesc").val("").focus();
		document.getElementById("label_id7").innerHTML = "<font color='red'>20个数字以内!</font>";
	}
}*/

$("#addLoan")
		.click(
				function() {
					$("#unit").val("0");
					if (!_modalFm1.validationEngine('validate')) {
					    return false;
					  }else{
					$.ajax({
								type : "POST",
								url : "../basedata_mgt/isystemconfig_list/insertSystemConfig",
								data : $("#form").serialize(),
								dataType : "json",
								success : function(data) {
										alert("添加成功")
										location = "../basedata_mgt/isystemconfig_list.html";
								},
								error : function() {
									alert("添加")
									location = "../basedata_mgt/isystemconfig_list.html";
								}
							})
					  }
				});