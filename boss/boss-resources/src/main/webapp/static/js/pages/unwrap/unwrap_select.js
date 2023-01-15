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

$(function(){
	//URL参数
	var id = Request.id;
	var tdUrl = "/unwrap/UnBankselect";
	var tbData = {
		"id":id
		
	};
	tableFun(tdUrl,tbData);
	
});
var remarkvalue;
var userinfoid;
var phone;
var xingname;
var certNo;
var bank;
var banknumber;
var yuer;
var editname;
var lognmoney;
//编辑
function tableFun(tdUrl, tbData) {
	
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
	    async : false,
		dataType : "json",
		success : function(data){
			userinfoid = data.userId;
			remarkvalue = data.remark;
			$("#id1").val(data.id);
			//加载另外两个函数获取详细信息
			//获取用户信息
			userSelectid();
			//获取用余额
			userSelectyuer();
			//取得出借金额
			userSelectloanid();
			
		var _table = $('#table'), tableBodyHtml = '';
       // $.each(function (k, v) {
        	//0=申请中、1=解绑成功、2=解绑处理中、-1=解绑失败、 -2=解绑拒绝
            var d_id = data.id,
            d_userId = data.userId,
            d_certPhoto1 = domainUrl + pic + data.certPhoto1,
            d_certPhoto2 = domainUrl + pic + data.certPhoto2,
            d_remark = data.remark,
            d_status = data.status,
          	d_editedBy = data.editedBy,
            d_createTime = data.createTime,
            d_unbindResult = data.unbindResult,
            d_modifyTime= data.modifyTime;
            var d_zuang;
            if(d_unbindResult=='Init'){
            	d_zuang='申请中';
            }else if(d_unbindResult=='Success'){
            	d_zuang='已成功';
            }else if(d_unbindResult=='Processing'){
            	d_zuang='解绑处理中';
            }else if(d_unbindResult=='Failure'){
            	d_zuang='解绑失败';
            }else if(d_unbindResult=='Refuse'){
            	d_zuang='已拒绝';
            }
            	tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td  width="10%">手机号</td>';	 
            	tableBodyHtml += '<td>'+phone+'</td>';
            	tableBodyHtml += '<td  width="10%">状态</td>';	 
            	tableBodyHtml += '<td>'+d_zuang+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">姓名</td>';	 
            	tableBodyHtml += '<td colspan="3">'+xingname+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">身份证号</td>';	 
            	tableBodyHtml += '<td colspan="3">'+certNo+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">银行</td>';	 
            	tableBodyHtml += '<td colspan="3">'+bank+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">银行卡号</td>';	 
            	tableBodyHtml += '<td colspan="3">'+banknumber+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">账户余额</td>';	 
            	tableBodyHtml += '<td colspan="3">'+yuer+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">出借金额</td>';	 
            	tableBodyHtml += '<td colspan="3">'+lognmoney+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">申请时间</td>';	 
            	tableBodyHtml += '<td colspan="3">'+d_createTime+'</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">手持身份证正面</td>';	 
            	tableBodyHtml += '<td width="40%"><p><img src="'+ d_certPhoto1 +'"/></p></td>';
            	tableBodyHtml += '<td width="10%">手持身份证反</td>';	 
            	tableBodyHtml += '<td width="40%"><p><img src="'+ d_certPhoto2 +'"/></p></td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">操作理由</td>';	 
            	tableBodyHtml += '<td colspan="3">'+d_remark+'</td>';
            	tableBodyHtml += '</tr>';
            	tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">操作人</td>';
            	tableBodyHtml += '<td colspan="3">' + editname + '</td>';
          		tableBodyHtml += '</tr>';
          		tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td width="10%">操作时间</td>';
            	tableBodyHtml += '<td colspan="3">' + d_modifyTime + '</td>';
          		tableBodyHtml += '</tr>'
          		
        //});
        _table.find("tbody").html(tableBodyHtml);
      }
	
	});
	
}

//取得出借金额
function userSelectloanid(){
	var formData={
			"investorUserCode" : userinfoid
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/userSelectloanid",
		data :formData,
		async : false,
		dataType : "json",
		success : function(data1) {
			lognmoney = data1.investAmount.cent;
			
		}	
		
	});
}
//根据userid查询余额和用户信息
//余额userSelectyuer
//获取当前用户角色
function userSelectid() {
	getUnBankStaffName();
	var formData={
			"id" : userinfoid//=========================================		
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/userSelectid",
		data :formData,
		async : false,
		dataType : "json",
		success : function(data) {
			phone =data.bankMobile;
			xingname =data.realName;
			certNo = data.certNo;
			bank = data.bankName;
			banknumber =data.bankCardNo;
		}	
	});  
}
function userSelectyuer() {
	var formData={
			"userId" : userinfoid//=============================================================
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/userSelectyuer",
		data :formData,
		async : false,
		dataType : "json",
		success : function(data) {
			yuer = data.balance.cent;
		}
			
	});
	  
}
function getUnBankStaffName(){
	$.ajax({
		type : "POST",
		url : "/unwrap/getUnBankStaffName",
		data :"",
		async : false,
		dataType : "json",
		success : function(data) {
			editname = data;
		}
			
	});
	
}


//通过
function submitedit1() {
	var id = $("#id1").val();
	var formData={
			"id" : id
			
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/successUnBank",
//		data :formData,
		data :formData,
		dataType : "json",
		success : function(data) {
	      alert("通过成功");
	      editremark();
	     
		},error: function(){
			alert("通过失败");
			//location = "../unwrap/unwrap_list.html";
		}
			
	});
	  
}
//拒绝
function submitedit2() {
	var id = $("#id1").val();
	var formData={
			"id" : id
			
	};
	$.ajax({
		type : "POST",
		url : "/unwrap/refuseUnBank",
		data :formData,
		dataType : "json",
		success : function(data){
	      alert("拒绝通过成功");
	      editremark();
	      
		},error: function(){
			alert("拒绝通过失败");
			//location = "../unwrap/unwrap_list.html";
		}
			
	});	  
}
//内容保存

function editremark() {
	var formData=$("#form").serialize();
	var formData;
	$.ajax({
		type : "POST",
		url : "/unwrap/UnBankupdata",
		data :formData,
		dataType : "json",
		success : function(data){
	      editremark();
	      location = "../unwrap/unwrap_list.html";
		},error: function(){
			//location = "../unwrap/unwrap_list.html";
		}	
	});	  
}