function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};
var borrowCode =  getUrlParam("borrowCode");
var nextPhase =  getUrlParam("nextPhase");
var totalPhase =  getUrlParam("totalPhase");
var earlyId;
$(function () {
	$.ajax({
		type : "POST",
		url : "/borrow/whitelist/getDetail",
		data : {"borrowId" : borrowCode},
		success : function(data) {
			if(data.resCode == 0){
				var _data = data.data[0];
				var _data1= data.data[1];
				var _data2 = data.data[2];
				var _data3 = data.data[3];
				var _data4 = data.data[4];
				$("#borrowTitle").val(_data.borrowTitle);
				$("#borrowUserName").val(_data.borrowUserName);
				$("#borrowRate").val(_data.borrowRate/100 + "%");
				if(_data.termType == "MONTH"){
					$("#borrowTerm").val(_data.borrowTerm);//回显借款期限
				}else if(_data.termType == "DAY"){
					$("#borrowTerm").val(_data.borrowTerm);//回显借款期限
				}else{
					$("#borrowTerm").val("——");//回显借款期限
				}
				$("#borrowAmount").val(_data.borrowAmount.amount);
				$("#currentPhase").val(nextPhase +'/'+totalPhase);
				$("#lastReleaseTime").val(_data.lastReleaseTime);
				$("#lastRepayTime").val(_data.lastRepayTime);
				$("#guaranteeUserName").val(_data1.guaranteeUserName);
				$("#guaranteeIdCard").val(_data1.guaranteeIdCard);
				$("#guaranteeUserName2").val(_data2.guaranteeUserName);
				$("#guaranteeIdCard2").val(_data2.guaranteeIdCard);
				$("#mark").val(_data3.mark);
				$("#editedBy").val(_data3.editedBy);
				$("#createTime").val(_data3.createTime);
				earlyId = _data3.id;
			
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
});	


$("#delBtn").click(function() {
	bootbox.confirm("确定要从提前还款白名单删除吗?", function(result) {
		if (result) {
			$.ajax({
				type : "POST",
				url : "/borrow/whitelist/delWhitelist",
				data : {'id':earlyId},
				dataType : "json",
				async : false,
				success : function(data) {
					if (data.resCode == 0) {
						bootbox.alert('删除成功',function(){
							location.href = "/borrow/whitelist_list.html";
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
});
