/**
 * @author libotao
 * @date 2016/8/22
 * @version 1.0.0
 */


	var _myDate = new Date();
	var _myDate_For_First = _myDate.getFullYear() +'-' + (_myDate.getMonth()+1)+ '-' +_myDate.getDate();
	
//表格ID
var _table = $('#tabled');

//项目类型-数组
var projectTypeArr ;

$.ajax({
  type: "POST",
  url: "/project/p_batch_pro/getLoanTypeList",
  success: function (data) {
    if (data != null && data != "") {
      projectTypeArr = data;
    }
  },
  async: false
});

//提前还款-数组
var prepaymentArr = [{
  "payId":"1",
  "paymentTxt":"是"
},{
  "payId":"0",
  "paymentTxt":"否"
}];

//购买权限-数组
var userPurviewArr ;

//用户权限组列表
$.ajax({
  type: "POST",
  url: "/project/p_batch_pro/getInvestGroupCode",
  success: function (data) {
    if (data != null && data != "") {

        if (data.resCode == 0) {
            var _data = data.data.list;
            userPurviewArr = _data ;
        }else bootbox.alert(data.msg);
    }
  },
  async: false
});

$(function () {
  //表格美化
  _table.bootstrapTable();

	/**
	 *校验
	 */
	_modalFm1 =  $('#batchProject');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	
  //添加按钮操作
  $("#addPro").click(function () {
    batchFun();
  });
 
  //var moneyyue = new Array();
  
  
  //运用设置到所有按钮操作
  $("#setBatch").click(function () {
	//获取剩余额度的值
	    var aaa = borrowSurplusAmount.textContent;
	    var yue;
    //列元素
    var project_type = ".project_type", //项目类型
      project_rate = ".project_rate", //项目利率
      float_rate = ".float_rate", //浮动利率
      invest_password = ".invest_password", //出借密码
      project_total = ".project_total", //项目总额
      pre_payment = ".pre_payment", //提前还款
      start_time = ".start_time", //开始募集时间
      /*end_time = ".end_time", //结束募集时间*/
      value_date = ".value_date", //起息日
      settle_date = ".settle_date", //最后结息日
      user_purview = ".user_purview" //购买权限
      ;

    //获取设置值
    var _trLast = _table.find("tbody tr:last");
    var s_project_type = _trLast.find(project_type).val(),//项目类型
      s_project_rate = _trLast.find(project_rate).val(),//项目利率
      s_float_rate = _trLast.find(float_rate).val(),//浮动利率
      s_invest_password = _trLast.find(invest_password).val(),//出借密码
      s_project_total = _trLast.find(project_total).val(),//项目总额
      s_pre_payment = _trLast.find(pre_payment).val(),//提前还款
      s_start_time = _trLast.find(start_time).val(),//开始募集时间
      /*s_end_time = _trLast.find(end_time).val(),//结束募集时间*/
      s_value_date = _trLast.find(value_date).val(),//起息日
      s_settle_date = _trLast.find(settle_date).val(),//最后结息日
      s_user_purview = _trLast.find(user_purview).val() //购买权限
      ;
    
    yue = s_project_total;
    //项目总额限制
    //var s_project_total;
    if(aaa-yue*len>yue){
    	batchFun();  //批量添加行
    }else{
    	alert("剩余额度不足");
    }
    
    
    //输入设置值
    var _trLastNew = _table.find("tbody tr:last");
    _trLastNew.find(project_type).val(s_project_type); //项目类型
    _trLastNew.find(project_rate).val(s_project_rate); //项目利率
    _trLastNew.find(float_rate).val(s_float_rate);//浮动利率  
    _trLastNew.find(invest_password).val(s_invest_password); //出借密码
    _trLastNew.find(project_total).val(s_project_total); //项目总额
    _trLastNew.find(pre_payment).val(s_pre_payment); //提前还款
    _trLastNew.find(start_time).val(s_start_time); //开始募集时间
    /*_trLastNew.find(end_time).val(s_end_time); //结束募集时间*/
    _trLastNew.find(value_date).val(s_value_date); //起息日
    _trLastNew.find(settle_date).val(s_settle_date); //最后结息日
    _trLastNew.find(user_purview).val(s_user_purview); //购买权限

    //获取项目类型选中文本内容
    var sProjectTypeT = _trLastNew.find(".project_type option:selected").text();
    if(sProjectTypeT == "出借加密"){  //项目类型为出借加密时，出借密码可输入
      _trLastNew.find(invest_password).attr("disabled",false);
    }else{
      _trLastNew.find(invest_password).attr("disabled",true);
    }
    if(sProjectTypeT == "内部加息"){
  	  //$(this).parents("tr").find(".invest_neibu").attr("disabled",false);
  	  _trLastNew.find(float_rate).attr("disabled",false);
    }else{
      //$(this).parents("tr").find(".invest_neibu").attr("disabled",true);
      _trLastNew.find(float_rate).attr("disabled",true);
    }

  });

  //批量创建操作
  $("#batchAdd").click(function(){
	  
	  var star1 = $("#").val();
	  if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }
	 var _tdLength =  _table.find("tbody tr td").length;
	 if(_tdLength <= 1){
		 alert("请至少添加一个项目");
		 return false;
	 }
	 if(aaa > _interestEndDateTransfer){
		 alert("起息日不能大于结息日");
		return false;
	 }
	  
	  
    var aa = $("#batchProject").serialize();
    $.ajax({
      type : "POST",
      url : "/project/p_batch_pro/add",
      data : $("#batchProject").serialize(),
      success : function(data) {
        if (data != null && data != "") {
          if (data.resCode == 0) {
            bootbox.alert("操作成功", function(){
              window.location.href = "p_wait_list.html";
            });
          }
          else{
            bootbox.alert(data.msg);
          }
        }
      },
      error:function(data){
    	  alert("出错了,请刷新页面重新创建项目");
      },
      async : false
    });
  });

});


/***
 *功能说明：批量操作
 *参数说明：
 *创建人：李波涛
 *时间：2016-08-22
 ***/
var len = 0;
var _interestEndDateTransfer;
var batchFun = function () {
  //获取项目ID
  // var proId = "1234567890";
  var batchCount = $("#batchCount").val();
  len = len + 1;
  
  $("#batchCount").val( len);
   _interestEndDateTransfer = $("#interestEndDateTransfer_up").val();
  //HTML内容
  var trHtml = '';
  trHtml += '<tr>';
  trHtml += '<td><input type="text" class="form-control validate[required] " name="list['+ batchCount +'].title" title="" data-errormessage-value-missing="项目名称不能为空" ></td>';  //项目ID
  trHtml += '<td><select class="form-control project_type" title="" name="list['+ batchCount +'].loanType"></select></td>'; //项目类型
  trHtml += '<td><input type="text" name="list['+ batchCount +'].yearRate" title="" class="form-control validate[required,custom[number]] project_rate" data-errormessage-value-missing="项目利率不能为空" data-errormessage-custom-error="请输入数字"></td>'; //项目利率
  //trHtml += '<td><input type="text" name="list['+ batchCount +'].floatRate" title="" class="form-control validate[required,custom[number]] float_rate" data-errormessage-value-missing="加息利率不能为空" data-errormessage-custom-error="请输入数字"></td>';  //浮动利率
  
  trHtml += '<td><input type="text" name="list['+ batchCount +'].floatRate" title="" class="invest_neibu form-control validate[required,custom[number]] float_rate" disabled="true" data-errormessage-value-missing="加息利率不能为空" data-errormessage-custom-error="请输入数字"></td>';  //加息利率
  
  trHtml += '<td><input type="text" name="list['+ batchCount +'].investInvetCode" title="" class="invest_password form-control validate[required]" disabled="true" data-errormessage-value-missing="出借加密不能为空"></td>';  //出借密码
  
  trHtml += '<td><input type="text" name="list['+ batchCount +'].amount" title="" class="form-control validate[required,custom[number]] project_total" data-errormessage-value-missing="项目总额不能为空" data-errormessage-custom-error="请输入数字"></td>'; //项目总额
  trHtml += '<td><select class="form-control pre_payment" title="" name="list['+ batchCount +'].earlyRepayment"></select></td>'; //提前还款
  trHtml += '<td><input class="laydate-icon layer-date start_time form-control validate[required]" name="list['+ batchCount +'].openTime" title="" onclick="laydate({format: \'YYYY-MM-DD\'})" data-errormessage-value-missing="开始募集时间不能为空" value="'+_myDate_For_First+'"></td>'; //开始募集时间
  trHtml += '<td><input class="laydate-icon layer-date value_date form-control validate[required]" id="interestStartDate" name="list['+ batchCount +'].interestStartDate" title="" onclick="laydate({format: \'YYYY-MM-DD\',istoday: false,choose: function(datas){selct(datas);}})" data-errormessage-value-missing="起息日不能为空" value="'+_myDate_For_First+'"></td>';  //起息日
  trHtml += '<td><p class="form-control-static" id="interestEndDate">'+_interestEndDateTransfer+'</p><input type="hidden" name="list['+ batchCount +'].interestEndDate" value="'+_interestEndDateTransfer+'"/></td>';  //最后结息日
  trHtml += '<td><select class="form-control user_purview" title="" name="list['+ batchCount +'].investGroupCode"></select></td>'; //购买权限
  trHtml += '<td><a href="javascript:" class="trDel" onclick="delTr(this)">删除</a></td>'; //删除操作
  trHtml += '</tr>';

  //HTML显示
  if (_table.find(".no-records-found").length > 0) { //无记录时处理
    _table.find("tbody").html(trHtml);
  } else {  //prepend 在前追加 append 在后追加
    _table.find("tbody").append(trHtml);
  }

  //添加项目类型选项
  var project_type = $(".project_type");
  $.each(projectTypeArr, function (k, v) {
		 project_type.last().append('<option value="'+ k +'">'+ v +'</option>');
  });

  //添提前还款选项
  var prepayment = $(".pre_payment");
  $.each(prepaymentArr, function (k, v) {
    prepayment.last().append('<option value="'+ v.payId +'">'+ v.paymentTxt +'</option>');
  });

  //添加购买权限选项
  var user_purview = $(".user_purview");
  $.each(userPurviewArr, function (k, v) {
    user_purview.last().append('<option value="'+ v.codeKey +'">'+ v.codeValue +'</option>');
  });

  //获取项目类型选中内容
  _table.find("tbody tr").each(function () {
    $(this).find(".project_type").change(function () {
      //获取项目类型选中文本内容
      var projectTypeT = $(this).find("option:selected").text();
      if(projectTypeT == "出借加密"){  //项目类型为出借加密时，出借密码可输入
        $(this).parents("tr").find(".invest_password").attr("disabled",false);
      }else{
        $(this).parents("tr").find(".invest_password").attr("disabled",true);
      }
      if(projectTypeT == "内部加息"){
    	  $(this).parents("tr").find(".invest_neibu").attr("disabled",false);
      }else{
        $(this).parents("tr").find(".invest_neibu").attr("disabled",true);
      }
    });
  });

  //表单元素验证
  var _form = $('#batchProject');  //form表单ID
  _form.validationEngine('attach', {
    maxErrorsPerField: 1,
    autoHidePrompt: true,
    autoHideDelay: 2000
  });
};
var aaa;
function selct(datas){
	 aaa= datas;
	if(datas>_interestEndDateTransfer){
		alert("起息日不能大于结息日");
		//$("#interestStartDate").val("");
	}
	
}
/***
 *功能说明：删除操作
 *参数说明：
 *创建人：李波涛
 *时间：2016-08-22
 ***/
var delTr = function (ele) {
  $(ele).parents("tr").remove();
  //后台操作内容
  len = len-1;
  $("#batchCount").val( len);

};