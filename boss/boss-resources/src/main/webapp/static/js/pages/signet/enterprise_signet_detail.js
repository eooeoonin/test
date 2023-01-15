/**
 * 印章记录
 */
function _getParamsObject() {
	var thisURL = document.URL;
	var getval = thisURL.split('?')[1];
	var paramsArray = getval.split("&");
	var paramsObject = {};
	for (var i = 0; i < paramsArray.length; i++) {
		var keyAndValue = paramsArray[i].split("=");
		paramsObject[keyAndValue[0]] = keyAndValue[1];
	}
	return paramsObject;
}

var userId = _getParamsObject().userId;
var _pages;
$(function() {
	var srhData1 = {
		"pageNo" : "1",
		"pageSize" : "20",
		"userId" : userId
	};
	tableFun("/signet/enterprise_signet_record/getEnterpriseSealRecord", srhData1);
	myPage();
	var _table = $('#table');
	_table.bootstrapTable();
});

function tableFun(tdUrl, tbData) {
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			if ("" != data.data.list) {
				var _table = $('#table'), tableBodyHtml = '';
				_pages = data.data.pages;
				$.each(
					data.data.list,function(k, v) {
					/**
					 * 获取数据信息
					 */
						var d_id = v.id, // id
						d_requestId = v.requestId, // 其它系统请求id
						d_requestTime = v.requestTime, // 其它系统请求时间
						d_userId = v.userId, // 用户id
						d_userType = v.userType, // 用户类型：PERSON=个人、ENTERPRISE=企业
						d_investorId = v.investorId, // 出借用户编号
						d_investId = v.investId, // 出借id
						d_userName = v.userName, // 姓名或者企业名称
						d_certType = v.certType, // 证件类型
						d_certNo = v.certNo, // 证件号码或者统一企业信用代码
						d_bankMobile = v.bankMobile, // 银行手机号或者企业法人手机号
//								d_sealImageCode = v.sealImageCode, // 印模编码
//								d_sealCode = v.sealCode, // 印章编码
//								d_sealPassword = v.sealPassword, // 印章密码
						d_businessCode = v.businessCode, // 业务码
						d_operatorCode = v.operatorCode, // 操作员编码或者机构号
						d_channelCode = v.channelCode, // 渠道编码
						d_savePath = v.savePath, // 合同pdf保存路径
						d_actionStatus = v.actionStatus, // 操作状态
						d_transStatus = v.transStatus, // 业务状态
						d_sealType = v.sealType, // 印章类型
						d_sealExpiredTime = v.sealExpiredTime, // 印章过期时间
						d_responseCode = v.responseCode, // 响应编码
						d_responseMessage = v.responseMessage, // 响应信息
						d_responseTime = v.responseTime; // 响应时间
	
						// 输出HTML元素
						tableBodyHtml += '<tr>';
						tableBodyHtml += '<td>'+ d_requestId + '</td>';
						tableBodyHtml += '<td>'+ d_requestTime + '</td>';
						tableBodyHtml += '<td>'+ d_userId + '</td>';
						tableBodyHtml += '<td>'+ d_userName + '</td>';
						tableBodyHtml += '<td>'+ d_responseCode + '</td>';
						tableBodyHtml += '<td>'+ d_responseMessage + '</td>';
						if ("PERMANENT"==d_sealType) {
							tableBodyHtml += '<td>长期</td>';
						} else if ("TEMPORARY"==d_sealType){
							tableBodyHtml += '<td>场景</td>';
						} else {
							tableBodyHtml += '<td>——</td>';
						}
						tableBodyHtml += '<td>'+ d_sealExpiredTime + '</td>';
						tableBodyHtml += '<td>'+ d_transStatus + '</td>';
						if ("FAIL" == d_transStatus){
							tableBodyHtml += '<td>——</td>'
						}else if("PROCESSING" == d_transStatus){
							tableBodyHtml += '<td><button class="btn btn-primary" type="button" onclick="revise(\''+d_requestId+'\',\''+d_userId+'\')">补单</button></td>'; //操作
						}else{
							tableBodyHtml += '<td><a href="../signet/signet_edit.html?type1=edit&requestId='+d_requestId+'&userId='+d_userId+'">印章查看</a></td>'
						}
						tableBodyHtml += '</tr>';
					});
				_table.find("tbody").html(tableBodyHtml);
				replaceFun(_table);
				$("#tcdPageCode").show();
			} else {
				var html = ""; 
				 html +='<tr class="no-records-found">';
				 html +='<td colspan="9" align="center">没有找到匹配的记录</td>';
				 html += '</tr>';
	    		 $("#table").find("tbody").html(html);
	    		 $("#tcdPageCode").hide();
			}
		},error : function(){
        	alert("获取企业印章记录失败");
        	$("#tcdPageCode").hide();
        },
		async : false
	});
}
function myPage() {
	var $tcdPage = $("#tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(q) {
			var _userId = $("#userId").val();
			var srhData2 = {
				"pageNo" : q,
				"pageSize" : "20",
				"userId" : _userId
			};
			tableFun("/signet/enterprise_signet_record/getEnterpriseSealRecord", srhData2);
		}
	});
}

//生成印章
var _addBtn = $("#addBtn");
_addBtn.click(function() {
	$.ajax({
		type : "POST",
		url : "/signet/moulage_record/autoMakeSeal",
		data : {
			"userId" : userId,
			"sealType":"PERMANENT"
		},
		dataType : "json",
		success : function(data) {
			if (data.resCode == 0) {
				bootbox.alert("印章生成成功", function(){
					window.location.reload();
				});
			}else{
				bootbox.alert(data.msg);
			}
		}
	});
});
//补单
function revise(requestId,userId)
{
    $.ajax({
            type : "POST",
            url : "/signet/moulage_record/sealRevise",
            data : 
            {
            	"requestId" : requestId,
            	"userId":userId,
            	"sealType":"PERMANENT"
            },
            dataType : "json",
            success : function(data) {
                if (data != null && data != "") {
                    if (data.resCode == 0) {
                    	alert(data.msg);
                    	document.location.reload();
                    }else{
                        alert(data.msg);
                        document.location.reload();
                    }
                }
            },error : function(data){
	        	alert(data.msg);
	        },
	        async : false
        });
}