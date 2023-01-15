/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages2;
$(function () {
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth()+1;
	var day = myDate.getDate();
	var hour = myDate.getHours();
	var aa = year+'-'+month+'-'+day;
	$("#date1").val(aa);
	$("#date2").val(aa);
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var start = {
    elem: "#date1", format: "YYYY-MM-DD", istime: true, istoday: false, choose: function (datas) {
      end.min = datas;
      end.start = datas
      var data2 = $("#date2").val();
      if(data2){
    	  if(datas == data2){
    		  $("#days").text("1天");
    		  return;
    	  }
    	  
    	  //判断显示天还是月
    	  var day1 = data2.split("-")[2];
    	  var day2 = datas.split("-")[2];
//    	  if(day1==day2){//正好相差整月
//    		  var months = getMonths(data2,datas);
//    		  $("#days").text(months+"月");
//    		  return;
//    	  }
    	  //计算出相差天数
    	  var date3=parserDate(data2).getTime()-parserDate(datas).getTime();//时间差的毫秒数
    	  var days=Math.floor(date3/(24*3600*1000))+1;
    	  $("#days").text(days+"天");
      }
    }
  };
  var end = {
    elem: "#date2", format: "YYYY-MM-DD", istime: true, istoday: false, choose: function (datas) {
      start.max = datas
      var data1 = $("#date1").val();
      if(data1){
    	  if(datas == data1){
    		  $("#days").text("1天");
    		  return;
    	  }
    	  var day1 = data1.split("-")[2];
    	  var day2 = datas.split("-")[2];
    	
//    	  if(day1==day2){//正好相差整月
//    		  var months = getMonths(datas,data1);
//    		  $("#days").text(months+"月");
//    		  return;
//    	  }
    	  var date3=parserDate(datas).getTime()-parserDate(data1).getTime();//时间差的毫秒数
    	  //计算出相差天数
    	  var days=Math.floor(date3/(24*3600*1000))+1;
    	  $("#days").text(days+"天");
      }
    }
  };
  laydate(start);
  laydate(end);
  
  operatorName = $.cookie("username");
  $('#approver').val(operatorName);
  $("#p").text(operatorName);
  
  //计算两个日期相差月数
  function getMonths(date2,date1){
	    //用-分成数组
	    date1 = date1.split("-");
	    date2 = date2.split("-");
	    //获取年,月数
	    var year1 = parseInt(date1[0]) , 
	        month1 = parseInt(date1[1]) , 
	        year2 = parseInt(date2[0]) , 
	        month2 = parseInt(date2[1]) , 
	        //通过年,月差计算月份差
	        months = (year2 - year1) * 12 + (month2-month1);
	    return months;    
  }
  //日期类型转换
  var parserDate = function (date) {  
      var t = Date.parse(date);  
      if (!isNaN(t)) {  
          return new Date(Date.parse(date.replace(/-/g, "/")));  
      } else {  
          return new Date();  
      }  
  };
  
  //格式化日期类型
  function   formatDate(now)   {     
      var year=now.getFullYear();     
      var month=now.getMonth()+1;     
      var date=now.getDate();     
      var hour=now.getHours();     
      var minute=now.getMinutes();     
      var second=now.getSeconds();
      return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;
      
  }     
 
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
  

  //单选框操作
  $(".i-checks").iCheck({
    radioClass: "iradio_square-green"
  });

  
  var $curP = $("#currPage"),
	  $pageC = $("#pageCount");
  //查询借款人信息
  function queryBorrower(){
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_add_loan/borrowerList",
	      data:$("#borrowerQueryForm").serialize(),
	      dataType:"json",
	      async: false,
	      success: function(responseMsg) {
	    	  if(null!=responseMsg){
	    		  $("#currPage").val(responseMsg.pageNum);
	    		  $("#pageCount").val(responseMsg.pages);
	    	  }
	    	  var html = "";
	    	  $.each(responseMsg.list, function (k, v) {
		          html +=  "<tr>";
		          html +=  "<td><label class=\"radio-inline i-checks\"><input type=\"radio\" name=\"radio\" class=\"sub_radbox\"></label></td>";
		          html +="<td>"+v.userId+"</td>";
		          html +="<td>"+v.legalPersonMobile+"</td>";
		          var v_userType = null;
		          if (v.userType == "PERSON") {
		        	  v_userType = "个人";
		          } else if(v.userType == "ENTERPRISE") {
		        	  v_userType = "企业";
		          } else {
		        	  v_userType = v.userType;
		          }
		          html +="<td>"+v_userType+""+v.roleName+"</td>";
		          if (v.roleCode =="PERSON"){
		        	  html +="<td>"+v.legalPersonName+"</td>";
		          } else {
		        	  html +="<td>"+v.enterpriseName+"</td>";
		          }
		          html +="<td>"+v.legalPersonName+"</td>";
		          html +="<td>"+v.createTime+"</td>";
		          html +="</tr>";
	    	  });
	    	  $("#table").find("tbody").html(html);
	      },error:function(data){
	    	  alert("出错了");
	      }
	  });
  }
  
  //选择选中的借款人
  $("#choose").click(function(){
	  var obj = $("#table").find("input[type='radio']:checked");
	  var userId = obj.parents("tr").find("td:nth-child(2)").text();
	  var enterpriseName = obj.parents("tr").find("td:nth-child(5)").text();
	  //var legalPersonName = obj.parents("tr").find("td:nth-child(6)").text();
	  $("#borrowerName").val(enterpriseName);
	  $("#borrowerId").val(userId);
	  $("#borrowName").val(enterpriseName);
	/*  $("#myModal").hide();
	  $(".modal-backdrop").remove();*/
	  $('#myModal').modal('hide');
	  
  });

  
  $("#searchBtn").click(function(){
	  queryBorrower();
	  myPage();
	  $("#myModal").modal("show");
  });
  
  //借款人查询
  $("#srhSmtBtn").click(function(){
	  queryBorrower();
	  myPage();
  });
  
  //分页
var myPage = function(){
	  var curpage = parseInt($curP.val());
	  var pageCount = parseInt($pageC.val());
	  var $tcdPage = $("#tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: pageCount,
	    current: curpage,
	    backFn: function (p) {
	    	$curP.val(p);
	    	queryBorrower();
	    }
	  });
}

  
  var _form = $('#borrowFrom'),  //form表单ID
  _submitBtn = $("#addLoan");  //提交表单按钮ID
  //表单元素验证
  _form.validationEngine('attach', {
    maxErrorsPerField: 1,
    autoHidePrompt: true,
    autoHideDelay: 2000
  });

  //下一步提交
  $("#addLoan").click(function () {
	  if (!_form.validationEngine('validate')) {
	     return false;
	  }
	  if($("#borrowerId").val() == "" || $("#borrowerId").val() == null){
		  alert("请选择借款方");
		  return false;
	  }
		  
	  operatorName = $.cookie("username");
	  $('#approver').val(operatorName);
	  $.ajax({
	      type: "POST",
	      url:"/borrow/l_add_loan/addBorrow",
	      data:$("#borrowFrom").serialize(),
	      success: function(data) {
	    	  if(data != null && data != ""){
	    		  if (data.resCode == 0) {
	    			  $("#addLoan").attr("disabled","disabled"); 
	    			  var responseMsg = data.data;
		    		  window.location.href = "l_add_loan_file.html?borrowId="+responseMsg;
		    		  $("#borrowId").val(responseMsg);
	    		  }else{
		    		  alert(data.msg);
		    	  }
	    	  }
	      },
		  error:function(data){
			  alert("出错了");
		  }
	  });
  });

//禁用按钮
  $("input[name='available']").on('ifChecked', function(event){  
  	var a = $("input[name='available']:checked").val();
	if (a == 0){
		$("#changeBtn").attr("disabled","disabled"); 
	}else{
		$("#changeBtn").attr("disabled",false); 
	}
  });  
    
  //选择担保按钮
  $("#changeBtn").click(function () {
  	var srhData = {
  			"pageNo": "1",
  			"pageSize": "10",
  			"roleCode": "GUARANTEE"
  	};
  	tableFun2("/borrow/l_add_loan/borrowerList", srhData);
//  	tableFun2("/project/p_add_pro/borrowUserList", srhData);
  	$("#myModal2").modal("show");
  	myPage2();
  });
    
  //担保人查询条件查询
  $("#srhSmtBtn2").click(function () {
  	var v_enterpriseName = $("#enterpriseName2").val();
  	var srhData2 = {
  			"pageNo": "1",
  			"pageSize": "10",
  			"roleCode" : "GUARANTEE",
  			"enterpriseName" : v_enterpriseName
  	};
  	tableFun2("/borrow/l_add_loan/borrowerList", srhData2);
//  	tableFun2("/project/p_add_pro/borrowUserList", srhData2);
  	myPage2();
  });

  //选择选中的担保人
  $("#choose2").click(function () {
  	var obj = $("#table2").find("input[type='radio']:checked");
  	var guaranteeUserId = obj.parents("tr").find("td:nth-child(5)").text();
  	$("#guaranteeUserId").val(guaranteeUserId);
  	$("#guaranteeUserId2").val(guaranteeUserId);

  	$('#myModal2').modal('hide');
  });
});

function tableFun2(tdUrl, tbData) {
	$.ajax({
		type: "POST",
		url: tdUrl,
		data: tbData,
		dataType: "json",
		async: false,
		success: function (data) {
			var html = "";
			if (data.list != null) {
				_pages2 = data.pages;
				$.each(data.list, function (k, v) {
					html += "<tr>";
					html += "<td><label class='radio-inline i-checks'><input style='margin-left:10px' type='radio' name='radio' class='sub_radbox'></label></td>";
					if (v.roleCode =="PERSON"){
			        	  html +="<td>"+v.legalPersonName+"</td>";
			          } else {
			        	  html +="<td>"+v.enterpriseName+"</td>";
			          }
					var v_userType = null;
			          if (v.userType == "PERSON") {
			        	  v_userType = "个人";
			          } else if(v.userType == "ENTERPRISE") {
			        	  v_userType = "企业";
			          } else {
			        	  v_userType = v.userType;
			          }
			        html +="<td>"+v_userType+"</td>";
					html +="<td>"+v.legalPersonName+"</td>";
					html += "<td>" + v.userId+ "</td>";
					html += "</tr>";
				});
				$("#table2").find("tbody").html(html);
			} else {
				 var _table = $('#table2'),
	    	     tableBodyHtml = '';
	    		 tableBodyHtml +='<tr class="no-records-found">';
	    		 tableBodyHtml +='<td colspan="9" align="center">没有找到匹配的记录</td>';
	    		 tableBodyHtml += '</tr>';
	    		 _table.find("tbody").html(tableBodyHtml);
	    		 $("#tcdPageCode2").hide();
			}
		},error :function (data){
			alert("出错了");
		}
	});
}

//分页2
function myPage2() {
	var $tcdPage = $("#tcdPageCode2");
	$tcdPage.createPage({
		pageCount: _pages2,
		current: 1,
		backFn: function (p) {
			var v_enterpriseName = $("#enterpriseName").val();
			var srhData4 = {
					"pageNo": p,
					"pageSize": "10",
					"enterpriseName" : v_enterpriseName
			};
			
			tableFun2("/borrow/l_add_loan/borrowerList", srhData4);
//			tableFun2("/project/p_add_pro/borrowUserList", srhData4);
		}
	});
}