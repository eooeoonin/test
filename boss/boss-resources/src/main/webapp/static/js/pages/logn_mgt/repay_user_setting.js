function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};
var borrowId =  getUrlParam("borrowId");
var delId = getUrlParam("delId");
$(function () {
	$.ajax({
		type : "POST",
		url : "/borrow/repay_user_list/getDetail",
		data : {"borrowId" : borrowId ,"id":delId},
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
				$("#currentPhase").val(_data4.currPhase +'/'+_data4.totalPhase);
				$("#lastReleaseTime").val(_data.lastReleaseTime);
				$("#lastRepayTime").val(_data.lastRepayTime);
				$("#guaranteeUserName").val(_data1.guaranteeUserName);
				$("#guaranteeIdCard").val(_data1.guaranteeIdCard);
				$("#guaranteeUserName2").val(_data2.guaranteeUserName);
				$("#guaranteeIdCard2").val(_data2.guaranteeIdCard);
				$("#enterpriseName").val(_data3.otherRepayUserName);
				$("#certId").val(_data3.certId);
				$("#mark").val(_data3.mark);
				$("#editedBy").val(_data3.editedBy);
				$("#createTime").val(_data3.createTime);
			
			}else{
				bootbox.alert("数据加载异常");
			}
		},
		async : false
	});
});	




$("#delBtn").click(function() {
	var v_mark = $("#mark1").val();
	if (null != v_mark && "" != v_mark) {
		bootbox.confirm("确定要从还款对象删除吗?", function(result) {
			if (result) {
				$.ajax({
					type : "POST",
					url : "/borrow/repay_user_list/delRepayUser",
					data : {
						'id' : delId,
						'mark' : v_mark
					},
					dataType : "json",
					async : false,
					success : function(data) {
						if (data.resCode == 0) {
							bootbox.alert('删除成功', function() {
								location.href = "/borrow/repay_user_list.html";
							});
						} else {
							bootbox.alert(data.msg);
						}
						;
					}
				});
			} else {
				return;
			}
		});
	} else {
		bootbox.alert("备注不能为空!");
	}
	
});
