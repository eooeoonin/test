/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
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
	

	$("#addLoan").click(function(){
		if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }else{
			$.ajax({
				type:"POST",
				url:"../basedata_mgt/event_list/insertEventMap",
				data:$("#form").serialize(),
			    dataType: "json",
				success:function(data){
					alert("添加成功")
					location = "../basedata_mgt/event_list.html";
				},error:function(){
					alert("添加失败")
					location = "../basedata_mgt/event_list.html";
				}
			})
		  }
		});