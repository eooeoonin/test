var _pages;var  _settleStatus;
$(function() {
	
	var _type = $("#type").val();
	      _settleStatus = $("#settleStatus").val();
	
	var pageData = {
			"pageNo" : "1",
			"pageSize" : "10",
			"type":_type,
			"settleStatus":_settleStatus,
	};
	tableFun("../crowdfunding/settlement/list",pageData);
	myPage();
});


function tableFun(tdUrl, tbData) {
	$.ajax({
				type : "POST",
				url : tdUrl,
				data : tbData,
				dataType : "json",
				success : function(data) {
							var _table = $('#table'),
								  tableBodyHtml = '';
								  _pages = data.result.pages;
							$.each(data.result.list,function(k, v) {
										var d_id = v.id,
											  d_projectName = v.projectName;
											  d_subProductName = v.subProductName,
											  d_userId = v.userId,
											  d_name = v.name,
											  d_phone = v.phone,
											  d_idcard = v.idcard,
											  d_orderingTime = v.orderingTime,
											  d_amount = v.amount.amount,
											  d_buyRate = v.buyRate,
											  d_noBuyRate = v.noBuyRate,
											  d_isBuyHouse1 = v.isBuyHouse,
											  d_isBuyHouse2 = v.isBuyHouse,
											  d_payTime = v.payTime;
											  if(_settleStatus == '0'){
												  d_interest = v.interest;
											  }if(_settleStatus == '1'){
												  d_interest = v.interest.amount;
											  }
											  
											  
											  if(d_isBuyHouse1 == '1'){
												  d_isBuyHouse1 = '是';
												  d_isBuyHouse2 = '';
											  }if(d_isBuyHouse1 == '0'){
												  d_isBuyHouse1 = '否';
												  d_isBuyHouse2 = '';
											  }
											  
											  
											  tableBodyHtml += '<tr>';
											  if(_settleStatus == '0'){
												  tableBodyHtml += '<td><input type=\"checkbox\" id=\"'+d_id+'"\ name="orderIds" value=\"'+d_id+'"\ class=\"table-mailbox\"></td>';
											  }
											  tableBodyHtml += '<td>' + d_projectName + '</td>';
											  tableBodyHtml += '<td>' + d_subProductName + '</td>';
											  tableBodyHtml += '<td>' + d_userId + '</td>';
											  tableBodyHtml += '<td>' + d_name + '</td>';
											  tableBodyHtml += '<td>' + d_phone + '</td>';
											  tableBodyHtml += '<td>' + d_idcard + '</td>';
											  tableBodyHtml += '<td>' + d_orderingTime + '</td>';
											  tableBodyHtml += '<td>' + d_amount + '</td>';
											  if(d_isBuyHouse1 == '否'){
												  if(_settleStatus == '0'){
													  tableBodyHtml += '<td>' + d_noBuyRate + '%</td>';
												  }else{
													  tableBodyHtml += '<td>' + d_noBuyRate + '</td>';
												  }
											  }if(d_isBuyHouse1 == '是'){
												  if(_settleStatus == '0'){
													  tableBodyHtml += '<td>' + d_buyRate + '%</td>';
												  }else{
													  tableBodyHtml += '<td>' + d_buyRate + '</td>';
												  }
											  }
											  tableBodyHtml += '<td>' + d_isBuyHouse1 + '</td>';
											  tableBodyHtml += '<td>' + d_isBuyHouse2 + '</td>';
											  tableBodyHtml += '<td>' + d_interest + '</td>';
											  if(_settleStatus == '0'){
												  $("#payTime").show();
												  $("#update").show();
												  $("#choice").show();
											  	  tableBodyHtml += '<td>' + d_payTime + '</td>';
												  tableBodyHtml += '<td><a href="javascript:" title="'+d_id+'" onclick="update(this.title)" >编辑</a></td>';
											  }if(_settleStatus == '1'){
												  $("#payTime").hide();
												  $("#update").hide();
												  $("#choice").hide();
											  }
											  tableBodyHtml += '</tr>';
									});
								_table.find("tbody").html(tableBodyHtml);
								replaceFun(_table);
				},
				async : false
			});

}


	var myPage = function(){
	var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
	  pageCount: _pages,
	  current: 1,
	  backFn: function (p) {
		  	var  _name2 = $("#name").val();
			var  _phone2 = $("#phone").val();
			var  _idcard2 = $("#idcard").val();
			var  _subProductName2 = $("#subProductName").val();
				   _settleStatus = $("#settleStatus").val();
			var _type2 = $("#type").val();
			var pageData2 = {
									"pageNo" : p,
									"pageSize" : "10",
									"name":_name2,
									"phone":_phone2,
									"idcard":_idcard2,
									"subProductName":_subProductName2,
									"type":_type2,
									"settleStatus":_settleStatus,
									};
		tableFun("../crowdfunding/settlement/list", pageData2);		  
	  }
	});
};


var _query = $("#query");
_query.click(function() {
		var sett = $("#settleStatus").val();
				if(sett == '1'){
					$("#settle").hide();
				}if(sett == '0'){
					$("#settle").show();
				}
	
				var  _name1 = $("#name").val();
				var  _phone1 = $("#phone").val();
				var  _idcard1 = $("#idcard").val();
				var  _subProductName1 = $("#subProductName").val();
					   _settleStatus = $("#settleStatus").val();
				var _type1 = $("#type").val();
				var pageData1 = {
											"pageNo" : "1",
											"pageSize" : "10",
											"name":_name1,
											"phone":_phone1,
											"idcard":_idcard1,
											"subProductName":_subProductName1,
											"type":_type1,
											"settleStatus":_settleStatus,
									  };
				tableFun("../crowdfunding/settlement/list",pageData1);
				myPage();
		});

$("#srhBtn").click(function(){
	var _type = $("#type").val();
	var _settleSta = $("#settleStatus").val();
	window.location.href="../crowdfunding/settlement/export?type="+_type+"&settleStatus="+_settleSta+"";

})


	//修改购房状态
	function update(id){
		$.ajax({
	        type: "POST",
	        url: "../crowdfunding/settlement/updateStatus",
	        data: {"orderId": id},
	        success: function(data) {
	        	 if("00000"==data){
	           		 alert("是否购房修改成功");
	           		location = location;
	           	 }else{
	           		 alert("是否购房修改失败");
	           		location = location;
	           	 }
	       	 },error : function(data){
	       		alert(data);
	       		location = location;
	       	 }
	    });
	}
	
	
	//判断是否选择了记录
	function validateIsSelect(allobj,items){
	    if(items.length){
		    for(var i=0;i<items.length;i++){
		    	if(items[i].checked){
		    		return "true";
		    	} 
		    }
	   }else{
		    if(items.checked) return "true";
	   }
	   return "false";
	}
	
	//清算
	$("#settle").click(function(){
			var form = $("#modal_form").get(0);
			
			if(typeof(form.orderIds) == "undefined") {
			alert("没有清算记录!");
			return false;
		}
			var result = validateIsSelect(form.chkall, form.orderIds);
			if("true"==result){
				$.ajax({
		            type: "POST",
		            url: "../crowdfunding/settlement/settle",
		            data: $("#modal_form").serialize(),
		            success: function(data) {
		            	data = eval('('+data+')');
		           	 if("00000"==data.resultCode){
		           		var dataObject = data.businessObject;
		           		for(var key in dataObject)
		           		{
		           			$("#"+key).text(dataObject[key]);
		           		}
		           		 alert("清算成功");
		           		 location = location;
		           	 }else{
		           		 alert("清算失败");
		           		 location = location;
		           	 }
		            }
		        }); 
		}else if("false"==result){
			alert("请选择要操作的记录");
		}else if("repeat"==result){
			alert("不能重复清算");
		}
		})
	
	
	

