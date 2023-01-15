/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}  
var Request = {};
Request = GetRequest();





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
	
	
	//URL参数
	var id = Request.id;



	
	var tdUrl = "/basedata_mgt/b_card_bin_list/CardBin/one";
	var tbData = {
		"id":id
	};
	tableFun(tdUrl,tbData);
	
});


//编辑
function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		
		success : function(data) {
			var _data = eval('(' + data + ')');
		//把商品数据,显示在也页面上
			document.getElementById("id").value = _data.id;
			document.getElementById("number").value = _data.number;
			document.getElementById("bankcode").value = _data.bankCode;
			
			document.getElementById("bankname").value = _data.bankName;
			document.getElementById("carddesc").value = _data.cardDesc;
			
			document.getElementById("cardtype").value = _data.cardType;
			document.getElementById("cardaccount").value = _data.cardAccount;
			
			document.getElementById("cardlength").value = _data.cardLength;
			document.getElementById("available").value = _data.available;
			
			
			
		}
	});
}

//修改
function submitCardBinTwo() {
	
	//判断是否为空
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }else{
	var formData=$("#form").serialize();
	
	  }

	
	$.ajax({
		type : "POST",
		url : "/basedata_mgt/b_card_bin_list/CardBin/edit",
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("修改成功");
	      location = "../basedata_mgt/b_card_bin_list.html";
		},error: function(){
			alert("修改成功");
			location = "../basedata_mgt/b_card_bin_list.html";
		}
			
	});
}

