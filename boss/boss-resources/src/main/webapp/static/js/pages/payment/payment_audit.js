/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages;
var _atStatus;
var _pageSize = 50;
var checkAmount = 0;
var checkNum = 0;
var _totalAmount;
$(function () {
    chkFun();
    srhBtn1();
});


//状态修改事件
function auditchan() {
    srhBtn1();
}

//查询事件
function srhBtn1() {
	checkAmount = 0,
	checkNum = 0;
	$("#checkTotalAmount").html(checkAmount);
	$("#checkTotalRow").html(checkNum);
    _atStatus = $("#auditStatus").find("option:selected").val();

    var srhData = getQueryData(1);

    if (_atStatus == 0) {
        tableFun("/payment/payment_audit/queryAuditData", srhData);
        myPage();
    }
    else if (_atStatus == 1) {
        tableFun("/payment/payment_audit/queryCallBackErrorData", srhData);
        myPage();
    }
    else if (_atStatus == 2) {
        tableFun("/payment/payment_audit/querySuccessHistroyData", srhData);
        myPage();
    }
    else if (_atStatus == 3) {
        tableFun("/payment/payment_audit/queryDefeatedHistroyData", srhData);
        myPage();
    }
    else if (_atStatus == 4) {
        tableFun("/payment/payment_audit/queryAuditedData", srhData);
        myPage();
    }
}

function getQueryData(p) {
    var v_tradeDateQueryAfter = $("#after").val(),
        v_tradeDateQueryBefore = $("#before").val(),
        v_selectmobile = $("#selectmobile").val(),
        v_selectuserid = $("#selectuserid").val(),
        v_loanTitle =  $("#loanTitle").val(),
        v_selectaccountType = $("#selectaccountType").find("option:selected").val(),
        bizCode = $("#bizCode").find("option:selected").val();

    if (v_selectmobile == "")
        v_selectmobile = null;
    if (v_selectuserid == "")
        v_selectuserid = null;
    if (v_selectaccountType == "")
        v_selectaccountType = null;

    var srhData;

    if (v_tradeDateQueryBefore != "" && v_tradeDateQueryAfter == "") {
        srhData = {
            "pageNo": p,
            "pageSize": _pageSize,
            "tradeDateQueryBefore": v_tradeDateQueryBefore,
            "mobile": v_selectmobile,
            "userCode": v_selectuserid,
            "accountType": v_selectaccountType,
            "bizCode": bizCode
        };
    }
    else if (v_tradeDateQueryBefore == "" && v_tradeDateQueryAfter != "") {
        srhData = {
            "pageNo": p,
            "pageSize": _pageSize,
            "tradeDateQueryAfter": v_tradeDateQueryAfter,
            "mobile": v_selectmobile,
            "userCode": v_selectuserid,
            "accountType": v_selectaccountType,
            "bizCode": bizCode
        };
    }
    else if (v_tradeDateQueryBefore != "" && v_tradeDateQueryAfter != "") {
        srhData = {
            "pageNo": p,
            "pageSize": _pageSize,
            "tradeDateQueryBefore": v_tradeDateQueryBefore,
            "tradeDateQueryAfter": v_tradeDateQueryAfter,
            "mobile": v_selectmobile,
            "userCode": v_selectuserid,
            "accountType": v_selectaccountType,
            "bizCode": bizCode
        };
    }
    else {
        srhData = {
            "pageNo": p,
            "pageSize": _pageSize,
            "mobile": v_selectmobile,
            "userCode": v_selectuserid,
            "accountType": v_selectaccountType,
            "bizCode": bizCode
        };
    }
    if(v_loanTitle != null && v_loanTitle != ""){
    	srhData.loanTitle = v_loanTitle;
    }
    return srhData;
}

$("#bt").hide();
/***
 *功能说明：表格数据及分页
 *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
 *创建人：李波涛
 *时间：2016-08-01
 ***/
function tableFun(tdUrl, tbData) {

    $.ajax({
        type: "POST",
        url: tdUrl,
        data: tbData,
        //async : false,
        dataType: "json",
        success: function (data) {
            var _table = $('#table'), tableBodyHtml = '';
            _totalAmount = 0;
            _pages = data.pages;
            $("#totalRow").html(data.size);
            $.each(data.list, function (k, v) {

                //获取数据
                var d_id = v.id,
                    d_tradeDate = v.tradeDate,
                    d_tradeId = v.tradeId,
                    d_userCode = v.userCode,
                    d_amount = v.amount.amount,//转账金额
                    d_fee = v.fee.amount,//手续费
                    d_auditStatus = v.auditStatus,
                    d_createTime = v.createTime,
                    d_modifyTime = v.modifyTime,
                    d_tradeId = v.tradeId,
                    d_mobile = v.mobile,
                    d_origin = v.origin,
                    d_Comment = v.comment,
                    d_errorMsg = v.errorMsg,
                    d_errorCode = v.errorCode,
                    d_accountType = v.accountType,//账户类型：1=对私、2=对公	非空
                    d_bankCode = v.bankCode,//开户行名称
                    d_bankNo = v.bankNo,//联行号
                    d_accountNo = v.accountNo,//银行账号
                    d_accountName = v.accountName,//银行账户名称
                    // d_orderId = v.orderId,
                    d_loanTitle = v.loanTitle,
                    d_branchBank = v.branchBank;
                	var realAmount = accSub(d_amount,d_fee);
                var d_acctype;
                if (d_accountType == 1) {
                    d_acctype = '对私';
                } else if (d_accountType == 2) {
                    d_acctype = '对公';
                }
                var dateStr = d_tradeDate;
                if (_atStatus == 0) {
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input type="checkbox" name="ckbox" class="sub_ckbox" value="' + d_id + '"></label></td>';
                    tableBodyHtml += '<td>' + dateStr + '</td>';
                    tableBodyHtml += '<td>' + d_tradeId + '</td>';
                    tableBodyHtml += '<td>' + v.orderid + '</td>';
                    tableBodyHtml += '<td>' + d_userCode + '</td>';
                    tableBodyHtml += '<td>' + d_loanTitle + '</td>';
                    tableBodyHtml += '<td>' + d_mobile + '</td>';
                    tableBodyHtml += '<td>' + d_accountName + '</td>';
                    tableBodyHtml += '<td id=\''+d_id+'count\'>' + d_amount + '</td>';
                    tableBodyHtml += '<td>' + realAmount + '</td>';
                    tableBodyHtml += '<td>' + d_fee + '</td>';
                    tableBodyHtml += '<td>' + d_bankCode + '</td>';
                    tableBodyHtml += '<td>' + d_branchBank + '</td>';
                    tableBodyHtml += '<td>' + d_accountNo + '</td>';
                    tableBodyHtml += '<td>' + d_acctype + '</td>';
                    tableBodyHtml += '</tr>';
                    _totalAmount = accAdd(_totalAmount, d_amount)
                }
                if (_atStatus == 1) {
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + dateStr + '</td>';
                    tableBodyHtml += '<td>' + d_tradeId + '</td>';
                    tableBodyHtml += '<td>' + v.orderid + '</td>';
                    tableBodyHtml += '<td>' + d_userCode + '</td>';

                    if (v.auditOper == 3) {
                        tableBodyHtml += '<td>取消</td>';
                    } else if (v.auditOper == 1) {
                        tableBodyHtml += '<td>提现</td>';
                    } else {
                        tableBodyHtml += '<td>' + v.auditOper + '</td>';
                    }

                    tableBodyHtml += '<td>' + d_loanTitle + '</td>';
                    tableBodyHtml += '<td>' + d_mobile + '</td>';
                    tableBodyHtml += '<td>' + d_accountName + '</td>';
                    tableBodyHtml += '<td>' + d_amount + '</td>';
                    tableBodyHtml += '<td>' + realAmount + '</td>';
                    tableBodyHtml += '<td>' + d_fee + '</td>';
                    tableBodyHtml += '<td>' + d_bankCode + '</td>';
                    tableBodyHtml += '<td>' + d_branchBank + '</td>';
                    tableBodyHtml += '<td>' + d_accountNo + '</td>';
                    tableBodyHtml += '<td>' + d_acctype + '</td>';
                    tableBodyHtml += '<td>' + d_Comment + '</td>';
                    tableBodyHtml += '<td>' + d_errorMsg + '</td>';
                    tableBodyHtml += '<td><button class="btn btn-primary"  type="button" id="' + d_id + '" onclick="operation(this,\''+d_errorCode+'\',\''+d_errorMsg+'\')">' + '操作' + '</button></td>';
                    tableBodyHtml += '</tr>';
                    _totalAmount = accAdd(_totalAmount, d_amount);

                }
                if (_atStatus == 2) {
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + dateStr + '</td>';
                    tableBodyHtml += '<td>' + d_tradeId + '</td>';
                    tableBodyHtml += '<td>' + v.orderId + '</td>';
                    tableBodyHtml += '<td>' + d_userCode + '</td>';
                    tableBodyHtml += '<td>' + d_loanTitle + '</td>';
                    tableBodyHtml += '<td>' + d_mobile + '</td>';
                    tableBodyHtml += '<td>' + d_accountName + '</td>';
                    tableBodyHtml += '<td>' + d_amount + '</td>';
                    tableBodyHtml += '<td>' + realAmount + '</td>';
                    tableBodyHtml += '<td>' + d_fee + '</td>';
                    tableBodyHtml += '<td>' + d_bankCode + '</td>';
                    tableBodyHtml += '<td>' + d_branchBank + '</td>';
                    tableBodyHtml += '<td>' + d_accountNo + '</td>';
                    tableBodyHtml += '<td>' + d_acctype + '</td>';
                    tableBodyHtml += '</tr>';
                    _totalAmount = accAdd(_totalAmount, d_amount);

                }
                if (_atStatus == 3) {
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + dateStr + '</td>';
                    tableBodyHtml += '<td>' + d_tradeId + '</td>';
                    tableBodyHtml += '<td>' + v.orderId + '</td>';
                    tableBodyHtml += '<td>' + d_userCode + '</td>';
                    tableBodyHtml += '<td>' + d_loanTitle + '</td>';
                    tableBodyHtml += '<td>' + d_mobile + '</td>';
                    tableBodyHtml += '<td>' + d_accountName + '</td>';
                    tableBodyHtml += '<td>' + d_amount + '</td>';
                    tableBodyHtml += '<td>' + realAmount + '</td>';
                    tableBodyHtml += '<td>' + d_fee + '</td>';
                    tableBodyHtml += '<td>' + d_bankCode + '</td>';
                    tableBodyHtml += '<td>' + d_branchBank + '</td>';
                    tableBodyHtml += '<td>' + d_accountNo + '</td>';
                    tableBodyHtml += '<td>' + d_acctype + '</td>';
                    tableBodyHtml += '<td>' + d_Comment + '</td>';
                    tableBodyHtml += '<td>' + d_errorMsg + '</td>';
                    tableBodyHtml += '</tr>';
                    _totalAmount = accAdd(_totalAmount, d_amount);

                }
                if (_atStatus == 4) {
                    tableBodyHtml += '<tr>';
                    tableBodyHtml += '<td>' + dateStr + '</td>';
                    tableBodyHtml += '<td>' + d_tradeId + '</td>';
                    tableBodyHtml += '<td>' + v.orderid + '</td>';
                    tableBodyHtml += '<td>' + d_userCode + '</td>';
                    tableBodyHtml += '<td>' + d_loanTitle + '</td>';
                    tableBodyHtml += '<td>' + d_mobile + '</td>';
                    tableBodyHtml += '<td>' + d_accountName + '</td>';
                    tableBodyHtml += '<td>' + d_amount + '</td>';
                    tableBodyHtml += '<td>' + realAmount + '</td>';
                    tableBodyHtml += '<td>' + d_fee + '</td>';
                    tableBodyHtml += '<td>' + d_bankCode + '</td>';
                    tableBodyHtml += '<td>' + d_branchBank + '</td>';
                    tableBodyHtml += '<td>' + d_accountNo + '</td>';
                    tableBodyHtml += '<td>' + d_acctype + '</td>';
                    tableBodyHtml += '</tr>';
                    _totalAmount = accAdd(_totalAmount, d_amount);
                }
            });

            $("#totalAmount").html(_totalAmount);

            if (_atStatus == 0) {
                if (data.list != "") {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<tr height="35.56">';
                    tableHeadHtml += '<th width="30" ><label class="checkbox-inline i-checks"> <input type="checkbox"  id="jCheckAll"></label></th>';
                    tableHeadHtml += '<th>交易时间</th>';
                    tableHeadHtml += '<th>交易流水号</th>';
                    tableHeadHtml += '<th>外部流水号</th>';
                    tableHeadHtml += '<th>用户ID</th>';
                    tableHeadHtml += '<th>标的名称</th>';
                    tableHeadHtml += '<th>手机号</th>';
                    tableHeadHtml += '<th>账户姓名</th>';
                    tableHeadHtml += '<th>提现金额</th>';
                    tableHeadHtml += '<th>到账金额</th>';
                    tableHeadHtml += '<th>手续费</th>';
                    tableHeadHtml += '<th>总行名称</th>';
                    tableHeadHtml += '<th>支行名称</th>';//账户类型：1=对私、2=对公	非空
                    tableHeadHtml += '<th>银行账号</th>';//开户行名称
                    tableHeadHtml += '<th>账户类型</th>';//联行号
                    tableHeadHtml += '</tr>';
                    $("#bt").show();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                } else {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<table id="table" class="table table-bordered table-hover">';
                    tableHeadHtml += '<thead>';
                    tableHeadHtml += '<tr></tr>';
                    tableHeadHtml += '</thead>';
                    tableHeadHtml += '<tbody>';
                    tableHeadHtml += '<tr class="no-records-found">';
                    tableHeadHtml += '<td style="text-align: center;" colspan="0">没有找到匹配的记录</td></tr>';
                    tableHeadHtml += '</tbody>';
                    tableHeadHtml += '</table>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                }
            }
            if (_atStatus == 1) {
                if (data.list != "") {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<tr height="35.56">';
                    tableHeadHtml += '<th>交易时间</th>';
                    tableHeadHtml += '<th>交易流水号</th>';
                    tableHeadHtml += '<th>外部流水号</th>';
                    tableHeadHtml += '<th>用户ID</th>';
                    tableHeadHtml += '<th>操作类型</th>';
                    tableHeadHtml += '<th>标的名称</th>';
                    tableHeadHtml += '<th>手机号</th>';
                    tableHeadHtml += '<th>账户姓名</th>';
                    tableHeadHtml += '<th>提现金额</th>';
                    tableHeadHtml += '<th>到账金额</th>';
                    tableHeadHtml += '<th>手续费</th>';
                    tableHeadHtml += '<th>总行名称</th>';
                    tableHeadHtml += '<th>支行名称</th>';//账户类型：1=对私、2=对公	非空
                    tableHeadHtml += '<th>银行账号</th>';//开户行名称
                    tableHeadHtml += '<th>账户类型</th>';//联行号
                    tableHeadHtml += '<th>提现说明</th>';
                    tableHeadHtml += '<th>支付错误信息</th>';
                    tableHeadHtml += '<th>操作</th>';

                    tableHeadHtml += '</tr>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                } else {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<table id="table" class="table table-bordered table-hover">';
                    tableHeadHtml += '<thead>';
                    tableHeadHtml += '<tr></tr>';
                    tableHeadHtml += '</thead>';
                    tableHeadHtml += '<tbody>';
                    tableHeadHtml += '<tr class="no-records-found">';
                    tableHeadHtml += '<td style="text-align: center;" colspan="0">没有找到匹配的记录</td></tr>';
                    tableHeadHtml += '</tbody>';
                    tableHeadHtml += '</table>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                }
            }
            if (_atStatus == 2) {
                if (data.list != "") {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<tr height="35.56">';
                    tableHeadHtml += '<th>交易时间</th>';
                    tableHeadHtml += '<th>交易流水号</th>';
                    tableHeadHtml += '<th>外部流水号</th>';
                    tableHeadHtml += '<th>用户ID</th>';
                    tableHeadHtml += '<th>标的名称</th>';
                    tableHeadHtml += '<th>手机号</th>';
                    tableHeadHtml += '<th>账户姓名</th>';
                    tableHeadHtml += '<th>提现金额</th>';
                    tableHeadHtml += '<th>到账金额</th>';
                    tableHeadHtml += '<th>手续费</th>';
                    tableHeadHtml += '<th>总行名称</th>';
                    tableHeadHtml += '<th>支行名称</th>';//账户类型：1=对私、2=对公	非空
                    tableHeadHtml += '<th>银行账号</th>';//开户行名称
                    tableHeadHtml += '<th>账户类型</th>';//联行号
                    tableHeadHtml += '</tr>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                } else {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<table id="table" class="table table-bordered table-hover">';
                    tableHeadHtml += '<thead>';
                    tableHeadHtml += '<tr></tr>';
                    tableHeadHtml += '</thead>';
                    tableHeadHtml += '<tbody>';
                    tableHeadHtml += '<tr class="no-records-found">';
                    tableHeadHtml += '<td style="text-align: center;" colspan="0">没有找到匹配的记录</td></tr>';
                    tableHeadHtml += '</tbody>';
                    tableHeadHtml += '</table>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                }
            }
            if (_atStatus == 3) {
                if (data.list != "") {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<tr height="35.56">';
                    tableHeadHtml += '<th>交易时间</th>';
                    tableHeadHtml += '<th>交易流水号</th>';
                    tableHeadHtml += '<th>外部流水号</th>';
                    tableHeadHtml += '<th>用户ID</th>';
                    tableHeadHtml += '<th>标的名称</th>';
                    tableHeadHtml += '<th>手机号</th>';
                    tableHeadHtml += '<th>账户姓名</th>';
                    tableHeadHtml += '<th>提现金额</th>';
                    tableHeadHtml += '<th>到账金额</th>';
                    tableHeadHtml += '<th>手续费</th>';
                    tableHeadHtml += '<th>总行名称</th>';
                    tableHeadHtml += '<th>支行名称</th>';//账户类型：1=对私、2=对公	非空
                    tableHeadHtml += '<th>银行账号</th>';//开户行名称
                    tableHeadHtml += '<th>账户类型</th>';//联行号
                    tableHeadHtml += '<th>提现说明</th>';
                    tableHeadHtml += '<th>支付错误信息</th>';
                    tableHeadHtml += '</tr>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                } else {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<table id="table" class="table table-bordered table-hover">';
                    tableHeadHtml += '<thead>';
                    tableHeadHtml += '<tr></tr>';
                    tableHeadHtml += '</thead>';
                    tableHeadHtml += '<tbody>';
                    tableHeadHtml += '<tr class="no-records-found">';
                    tableHeadHtml += '<td style="text-align: center;" colspan="0">没有找到匹配的记录</td></tr>';
                    tableHeadHtml += '</tbody>';
                    tableHeadHtml += '</table>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                }
            }
            if (_atStatus == 4) {
                if (data.list != "") {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<tr height="35.56">';
                    tableHeadHtml += '<th>交易时间</th>';
                    tableHeadHtml += '<th>交易流水号</th>';
                    tableHeadHtml += '<th>外部流水号</th>';
                    tableHeadHtml += '<th>用户ID</th>';
                    tableHeadHtml += '<th>标的名称</th>';
                    tableHeadHtml += '<th>手机号</th>';
                    tableHeadHtml += '<th>账户姓名</th>';
                    tableHeadHtml += '<th>提现金额</th>';
                    tableHeadHtml += '<th>到账金额</th>';
                    tableHeadHtml += '<th>手续费</th>';
                    tableHeadHtml += '<th>总行名称</th>';
                    tableHeadHtml += '<th>支行名称</th>';//账户类型：1=对私、2=对公	非空
                    tableHeadHtml += '<th>银行账号</th>';//开户行名称
                    tableHeadHtml += '<th>账户类型</th>';//联行号
                    tableHeadHtml += '</tr>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                } else {
                    var tableHeadHtml = "";
                    tableHeadHtml += '<table id="table" class="table table-bordered table-hover">';
                    tableHeadHtml += '<thead>';
                    tableHeadHtml += '<tr></tr>';
                    tableHeadHtml += '</thead>';
                    tableHeadHtml += '<tbody>';
                    tableHeadHtml += '<tr class="no-records-found">';
                    tableHeadHtml += '<td style="text-align: center;" colspan="0">没有找到匹配的记录</td></tr>';
                    tableHeadHtml += '</tbody>';
                    tableHeadHtml += '</table>';
                    $("#bt").hide();
                    _table.find("thead").html(tableHeadHtml);
                    _table.find("tbody").html(tableBodyHtml);
                }
            }

            chkFun();
            replaceFun(_table);
        },
        async: false,
        error: function (XMLHttpRequest) {
            alert("Error: " + XMLHttpRequest.responseText);
        }

    });
}


//分页
var myPage = function () {
    //分页
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
        pageCount: _pages,
        current: 1,
        backFn: function (p) {
        	checkAmount = 0,
        	checkNum = 0;
    		$("#checkTotalAmount").html(checkAmount);
    		$("#checkTotalRow").html(checkNum);
            var srhData = getQueryData(p);

            if (_atStatus == 0) {
                tableFun("/payment/payment_audit/queryAuditData", srhData);
            }
            else if (_atStatus == 1) {
                tableFun("/payment/payment_audit/queryCallBackErrorData", srhData);
            }
            else if (_atStatus == 2) {
                tableFun("/payment/payment_audit/querySuccessHistroyData", srhData);
            }
            else if (_atStatus == 3) {
                tableFun("/payment/payment_audit/queryDefeatedHistroyData", srhData);
            }else if(_atStatus == 4){
            	tableFun("/payment/payment_audit/queryAuditedData", srhData);
            }
        }
    });
}
/***
 *功能说明：时间插件
 *参数说明：
 *创建人：李波涛
 *时间：2016-07-15
 ***/
var start = {
    elem: "#before", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: true, choose: function (datas) {
        end.min = datas;
        end.start = datas
    }
};
var end = {
    elem: "#after", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: true, choose: function (datas) {
        start.max = datas
    }
};
try {
    laydate(start);
    laydate(end);
} catch (e) {
}


/***
 *功能说明：复选框、单选框美化
 *参数说明：
 *创建人：李波涛
 *时间：2016-07-15
 ***/
function chkFun() {
    $(".i-checks").iCheck({
        checkboxClass: "icheckbox_square-green",
        radioClass: "iradio_square-green"
    });

    var _jCheckAll = $("#jCheckAll"),
        _subCheck = $('input[type="checkbox"].sub_ckbox');
    _jCheckAll.on('ifChecked', function () {
        _subCheck.iCheck('check');
    });
    _jCheckAll.on('ifUnchecked', function () {
        _subCheck.iCheck('uncheck');
    });
    $('.i-checks').on('ifChecked', function(event){
    	if($(event.target).val() == "on"){
    		$("#checkTotalAmount").html(_totalAmount);
    	}else if($(event.target).val() == "option1"){
    		
    	}else if($(event.target).val() == "option2"){
    		
    	}else{
    		checkNum++;
    		var id = "#"+ $(event.target).val() + "count";
    		checkAmount = accAdd(checkAmount,$(id).html());
    		$("#checkTotalAmount").html(checkAmount);
    		$("#checkTotalRow").html(checkNum);
    		
    	}
    });  
    $('.i-checks').on('ifUnchecked', function(event){    
    	if($(event.target).val() == "on"){
    		$("#checkTotalAmount").html(0);
    	}else if($(event.target).val() == "option1"){
    		
    	}else if($(event.target).val() == "option2"){
    		
    	}else{
    		checkNum--;
    		var id = "#"+ $(event.target).val() + "count";
    		checkAmount = accSub(checkAmount,$(id).html());
    		$("#checkTotalAmount").html(checkAmount);
    		$("#checkTotalRow").html(checkNum);
    	}
    }); 
}

/* 批量genggai  */
$("#payPast").click(function () {
    // 判断是否至少选择一项
    var checkedNum = $("input[name='ckbox']:checked").length;
    if (checkedNum == 0) {
        alert("请选择至少一项！");
        return;
    }

    // 批量选择
    if (confirm("确定要通过所选项目？")) {
        var checkedList = new Array();
        $("input[name='ckbox']:checked").each(function () {

            checkedList.push($(this).val());

        });
        console.log(checkedList);
        var tData = {
            "ids": checkedList,
            "bizCode": $("#bizCode option:selected")[0].value
        }

        $.ajax({
            type: "POST",
            url: "/payment/payment_audit/auditData",
            data: tData,
            dataType: "json",
            traditional: true,
            success: function (data) {
            	checkAmount = 0,
            	checkNum = 0;
        		$("#checkTotalAmount").html(checkAmount);
        		$("#checkTotalRow").html(checkNum);
                var srhData = {
                    "pageNo": "1",
                    "pageSize": "50",
                    "bizCode": $("#bizCode").find("option:selected").val()

                };
                tableFun("/payment/payment_audit/queryAuditData", srhData);

            }, error: function () {
            	checkAmount = 0,
            	checkNum = 0;
        		$("#checkTotalAmount").html(checkAmount);
        		$("#checkTotalRow").html(checkNum);
                var srhData = {
                    "pageNo": "1",
                    "pageSize": "50",
                    "bizCode": $("#bizCode").find("option:selected").val()

                };
                tableFun("/payment/payment_audit/queryAuditData", srhData);
            }
        });
    }
});
$("#refuse").click(function () {

    var checkedNum = $("input[name='ckbox']:checked").length;
    if (checkedNum == 0) {
        alert("请选择至少一项！");
        return;
    }

    if (confirm("确定要拒绝所选项目？")) {
        var checkedList = new Array();
        $("input[name='ckbox']:checked").each(function () {
            checkedList.push($(this).val());
        });
        console.log(checkedList);
        var tData = {
            "ids": checkedList,
            "bizCode": $("#bizCode option:selected")[0].value
        }

        $.ajax({
            type: "POST",
            url: "/payment/payment_audit/refuseData",
            data: tData,
            dataType: "json",
            traditional: true,
            success: function (data) {
            	checkAmount = 0,
            	checkNum = 0;
        		$("#checkTotalAmount").html(checkAmount);
        		$("#checkTotalRow").html(checkNum);
                var srhData = {
                    "pageNo": "1",
                    "pageSize": "50",
                    "bizCode": $("#bizCode").find("option:selected").val()

                };
                tableFun("/payment/payment_audit/queryAuditData", srhData);
            }, error: function () {
            	checkAmount = 0,
            	checkNum = 0;
        		$("#checkTotalAmount").html(checkAmount);
        		$("#checkTotalRow").html(checkNum);
                var srhData = {
                    "pageNo": "1",
                    "pageSize": "50",
                    "bizCode": $("#bizCode").find("option:selected").val()

                };
                tableFun("/payment/payment_audit/queryAuditData", srhData);
            }
        });
    }
});
var updateid;
var errorCode;
var errorMsg;
function operation(obj,errorCode1,errorMsg1) {
    var bizCode = $("#bizCode").find("option:selected").val();
    if (bizCode == 102) {
        $('#myGroup').hide();
    } else {
        $('#myGroup').show();
    }
     $("#jsWinTrade").css("display", "block");
    updateid = obj.id;
    errorCode =  errorCode1;
    errorMsg = errorMsg1;
    $('#myModal').modal('show');
}
$("#trade_btn").click(function () {
    $("#jsWinTrade").css("display", "none");
    var tData = {
        "id": updateid,
        "bizCode": $("#bizCode").find("option:selected").val(),
        "errorCode" : errorCode,
        "errorMsg":errorMsg
    };
    if ($("#rad input[name='group1']:checked").val() == "option1") {
        $.ajax({
            type: "POST",
            url: "/payment/payment_audit/backSuccessData",
            data: tData,
            dataType: "json",
            traditional: true,
            success: function (data) {
                if (data == 'success') {
                    var srhData = {
                        "pageNo": "1",
                        "pageSize": "50",
                        "bizCode": $("#bizCode").find("option:selected").val()
                    };
                    tableFun("/payment/payment_audit/queryCallBackErrorData", srhData);
                    $('#myModal').modal('hide');
                    bootbox.alert("操作成功");
                } else {
                    var srhData = {
                        "pageNo": "1",
                        "pageSize": "50",
                        "bizCode": $("#bizCode").find("option:selected").val()
                    };
                    tableFun("/payment/payment_audit/queryCallBackErrorData", srhData);
                    $('#myModal').modal('hide');
                    bootbox.alert("系统错误，请重新操作");
                }
            }, error: function () {
                var srhData = {
                    "pageNo": "1",
                    "pageSize": "50",
                    "bizCode": $("#bizCode").find("option:selected").val()
                };
                tableFun("/payment/payment_audit/queryCallBackErrorData", srhData);
                $('#myModal').modal('hide');
                bootbox.alert("系统错误，请重新操作");
            }
        });
    }
    if ($("#rad input[name='group1']:checked").val() == "option2") {
        $.ajax({
            type: "POST",
            url: "/payment/payment_audit/backrefuseData",
            data: tData,
            dataType: "json",
            traditional: true,
            success: function (data) {
                var srhData = {
                    "pageNo": "1",
                    "pageSize": "50",
                    "bizCode": $("#bizCode").find("option:selected").val()
                };
                tableFun("/payment/payment_audit/queryCallBackErrorData", srhData);
                $('#myModal').modal('hide');
            }, error: function () {
                var srhData = {
                    "pageNo": "1",
                    "pageSize": "50",
                    "bizCode": $("#bizCode").find("option:selected").val()
                };
                tableFun("/payment/payment_audit/queryCallBackErrorData", srhData);
                $('#myModal').modal('hide');
            }
        });
    }
});
var _qingBtn = $("#qingBtn");
_qingBtn.click(function () {
    window.location.reload();
});

function close(type) {
    if (type == 1) {
        $("#jsWinRecharge").hide();
    }
    if (type == 2) {
        $("#jsWinCards").hide();
    }
    if (type == 3) {
        $("#redMoneyDiv").hide();
    }
    if (type == 4) {
        $("#jsWinTrade").hide();
    }
}

//小数加法
function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}


function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
 