/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
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
  /**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
		
});
	

/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") !== -1) {
    var str = url.substr(1);
    var strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}  

	var Request = {};
	Request = GetRequest();
	
	var valuemapid;
	var empValueId;
	var inputName;
	var outputName;
	var name;
	var value;
	
	$("#addvaluemap").click(function(){
		valuemapid = Request.id;
		empValueId = document.getElementById("eventMapId").value;
		inputName = document.getElementById("inputName").value;
		outputName = document.getElementById("outputName").value;
		name = document.getElementById("name").value;
		value = document.getElementById("value").value;
		 tableFun();
		 tableFun2();
	});
	

		function tableFun() {
			if (!_modalFm1.validationEngine('validate')) {
			    return false;
			  }else{
			var tdUrl = "../basedata_mgt/event_list/insertEventValueMap";
			var tbData = {
							 "eventMapId":valuemapid,
							 "empValueId":empValueId,
							 "inputName":inputName,
							 "outputName":outputName
		};
		if(inputName!==""||outputName!==""){
			$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function() {
					  alert("添加成功")
					  var eventMapId = tbData.eventMapId;
	              	  window.location.href = "../basedata_mgt/event_list.html?eventMapId="+eventMapId+"";
				},error:function(){
					alert("添加失败")
					window.location.href = "../basedata_mgt/event_list.html";
				}
			});
		}
		
		}
	}	
			
		
		function tableFun2() {
			var tdUrl = "../basedata_mgt/event_list/insertEventValueSupplyMapp";
			var tbData = {
								 "eventMapId":valuemapid,
								 "empValueId":empValueId,
								 "name":name,
								 "value":value,
								 "inputName":inputName,
								 "outputName":outputName
			};
			if((name!==""&&value!=="")&(inputName!==""&&outputName!=="")){			
			$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function() {
					  var eventMapId = tbData.eventMapId; 
					  window.location.href = "../basedata_mgt/event_list.html?eventMapId="+eventMapId+"";
				},error:function(){
					  window.location.href = "../basedata_mgt/event_list.html";
				}
			});
			}	
		}
		
		