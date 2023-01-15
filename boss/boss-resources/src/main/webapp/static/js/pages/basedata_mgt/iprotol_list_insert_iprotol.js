/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
	
	var ue = UE.getEditor('miaoshu');
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
  /***
   *功能说明：复选框、单选框美化
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green"
  });

  
		
});
		//protocolId校验
		function test8(){
			 var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
		       if (document.getElementById("protocolId").value.length <= 20) {
		       	document.getElementById("label_id8").innerHTML="<font color='green'></font>";
		       } else {
		       	$("#protocolId").val("").focus();
		       	document.getElementById("label_id8").innerHTML="<font color='red'>20个数字以内!</font>";
		       }
		}

		//type	
		function test9(){
			 var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
               if (document.getElementById("type").value.length <= 20) {
               	document.getElementById("label_id9").innerHTML="<font color='green'></font>";
               } else {
               	$("#type").val("").focus();
               	document.getElementById("label_id9").innerHTML="<font color='red'>20个数字以内!</font>";
               }
		}
		//name
		function test10(){
			 var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
               if (document.getElementById("name").value.length <= 20) {
               	document.getElementById("label_id10").innerHTML="<font color='green'></font>";
               } else {
               	$("#name").val("").focus();
               	document.getElementById("label_id10").innerHTML="<font color='red'>20个数字以内!</font>";
               }
		}
		//template
		function test11(){
			 var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
               if (document.getElementById("template").value.length <= 20) {
               	document.getElementById("label_id11").innerHTML="<font color='green'></font>";
               } else {
               	$("#template").val("").focus();
               	document.getElementById("label_id11").innerHTML="<font color='red'>20个数字以内!</font>";
               }
		}

		//protocolDesc
		function test12(){
			 var myReg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
               if (document.getElementById("protocolDesc").value.length <= 20) {
               	document.getElementById("label_id12").innerHTML="<font color='green'></font>";
               } else {
               	$("#protocolDesc").val("").focus();
               	document.getElementById("label_id12").innerHTML="<font color='red'>20个数字以内!</font>";
               }
		}

		
		
		
	$("#addLoan").click(function(){
		if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }else{
			$.ajax({
				type:"POST",
				url:"../basedata_mgt/iprotol_list/insert",
				data:$("#form").serialize(),
			    dataType: "json",
				success:function(data){
					alert("添加成功")
					location = "../basedata_mgt/iprotol_list.html";
				},error:function(){
					alert("添加失败")
					location = "../basedata_mgt/iprotol_list.html";
				}
			})
		  }
		});