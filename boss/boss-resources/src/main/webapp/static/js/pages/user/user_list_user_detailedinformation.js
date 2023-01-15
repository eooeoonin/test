/***
 *** 获取URL参数
 ***/
var _pages;var _userId;var _inviteFriends;var _bonusAmount;
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  		url = decodeURIComponent(url);
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
    }
  }
  return theRequest;
} 

var Request = {};
Request = GetRequest();


$(function() {
	$("#inviteMoney").hide();
	$("#LoanInvestorMoney").hide();
	//URL参数
	var id = Request.id;
	document.getElementById("userid").value = Request.id;
	_userId = Request.id;
	$.ajax({
		type:'POST',
		url:'../user/user_list/selectUserById',
		data:{"userId":id},
		dataType : "json",
		success:function(data){
			
			if(data.userType == 'PERSON'){
				data.userType = '个人会员';
          	  }if(data.userType == 'ENTERPRISE'){
          		data.userType = '企业会员';
          	  }
          	  
          	 if(data.gender == 'UNKNOWN'){
          		data.gender = '未知';
           	  }if(data.gender == 'MAN'){
           		data.gender = '男';
           	  }if(data.gender == 'WOMAN'){
           		data.gender = '女';
           	  }

           	  
           	if(data.certType == 'ID'){
           		data.certType = '身份证';
           	  }if(data.certType == 'VISA'){
           		data.certType = '签证';
           	  }if(data.certType == 'Passport'){
           		data.certType = '护照';
           	  }if(data.certType == 'MilitaryCard'){
           		data.certType = '军人证';
           	  }if(data.certType == 'OnewayPermit'){
           		data.certType = '港澳通行证';
           	  }
           	
           	if(data.editedBy == 'INIT'){
           		data.editedBy = '未认证';
           	  }if(data.editedBy == 'IDCARD'){
           		data.editedBy = '实名认证';
           	  }if(data.editedBy == 'BANKCARD'){
           		data.editedBy = '绑卡';
           	  }  
          	  

           	if(data.userState == 'NORMAL'){
           		data.userState = '正常';
           	  }if(data.userState == 'FROZEN'){
           		data.userState = '冻结';
           	  }
			
			
			document.getElementById("realName").value = data.realName;
			document.getElementById("gender").value = data.gender;
			document.getElementById("registerMobile").value = data.registerMobile;
			document.getElementById("userType").value = data.userType;
			document.getElementById("group").value = data.group;
			document.getElementById("certType").value = data.certType;
			document.getElementById("certNo").value = data.certNo;
			document.getElementById("authState").value = data.editedBy;
			document.getElementById("userState").value = data.userState;
			document.getElementById("grade").value = data.grade;
			document.getElementById("agent").value = data.agent;
			document.getElementById("from").value = data.from;
			document.getElementById("inviteCode").value = data.inviteCode;
			document.getElementById("referrer1").value = data.referrer1;
			document.getElementById("referrer2").value = data.referrer2;
			document.getElementById("deviceId").value = data.deviceId;
			document.getElementById("remark").value = data.remark;
			document.getElementById("createTime").value = data.createTime;
			document.getElementById("lastLoginTime").value = data.lastLoginTime;
			if(data.userType == "企业会员"){
				$("#checkBtn").attr("style","display:none");
			}
		},error:function(){
			
		}
	})
	
});
function platform(){
	$("#userIdHide").show();
	$("#LoanInvestorMoney").hide();
	$("#inviteMoney").hide();
	//URL参数
	var id = Request.id;
	document.getElementById("userid").value = Request.id;
	_userId = Request.id;
	$.ajax({
		type:'POST',
		url:'../user/user_list/selectUserById',
		data:{"userId":id},
		dataType : "json",
		success:function(data){
			
			if(data.userType == 'PERSON'){
				data.userType = '个人会员';
          	  }if(data.userType == 'ENTERPRISE'){
          		data.userType = '企业会员';
          	  }
          	  
          	 if(data.gender == 'UNKNOWN'){
          		data.gender = '未知';
           	  }if(data.gender == 'MAN'){
           		data.gender = '男';
           	  }if(data.gender == 'WOMAN'){
           		data.gender = '女';
           	  }

           	  
           	if(data.certType == 'ID'){
           		data.certType = '身份证';
           	  }if(data.certType == 'VISA'){
           		data.certType = '签证';
           	  }if(data.certType == 'Passport'){
           		data.certType = '护照';
           	  }if(data.certType == 'MilitaryCard'){
           		data.certType = '军人证';
           	  }if(data.certType == 'OnewayPermit'){
           		data.certType = '港澳通行证';
           	  }
           	
           	if(data.authState == 'INIT'){
           		data.authState = '未认证';
           	  }if(data.authState == 'IDCARD'){
           		data.authState = '实名认证';
           	  }if(data.authState == 'BANKCARD'){
           		data.authState = '绑卡';
           	  }  
          	  

           	if(data.userState == 'NORMAL'){
           		data.userState = '正常';
           	  }if(data.userState == 'FROZEN'){
           		data.userState = '冻结';
           	  }
			
			
			document.getElementById("realName").value = data.realName;
			document.getElementById("gender").value = data.gender;
			document.getElementById("registerMobile").value = data.registerMobile;
			document.getElementById("userType").value = data.userType;
			document.getElementById("group").value = data.group;
			document.getElementById("certType").value = data.certType;
			document.getElementById("certNo").value = data.certNo;
			document.getElementById("authState").value = data.authState;
			document.getElementById("userState").value = data.userState;
			document.getElementById("grade").value = data.grade;
			document.getElementById("agent").value = data.agent;
			document.getElementById("from").value = data.from;
			document.getElementById("inviteCode").value = data.inviteCode;
			document.getElementById("referrer1").value = data.referrer1;
			document.getElementById("referrer2").value = data.referrer2;
			document.getElementById("deviceId").value = data.deviceId;
			document.getElementById("remark").value = data.remark;
			document.getElementById("createTime").value = data.createTime;
			document.getElementById("lastLoginTime").value = data.lastLoginTime;
		},error:function(){
			
		}  
	})
}
/**
 * 地址信息
 */
function receiptAddress(){
	$("#userIdHide").hide();
	$("#LoanInvestorMoney").hide();
	$("#inviteMoney").hide();
	$("#tcdPageCodehide").show()
	var srhData = {"userId":_userId,"pageNo":"1","pageSize":"10"};
	selectReceiptAddress("../user/user_list/selectReceiptAddress", srhData);	
	myPage3();
}

//用户地址页面
function selectReceiptAddress(tdUrl,tdData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tdData,
      dataType: "json",
      success: function (data) {
        var _table = $('#tab_10'),
          tableBodyHtml = '';
        
        _pages = data.pages;
        
        $.each(data.list,function(k,v){
	          //获取数据
        var t_userName = v.userName,
        	  t_userPhone = v.userPhone,
        	  t_address = v.address,
        	  t_isdefault = v.isdefault;
        
        if(t_isdefault == true){
    	  tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + t_userName + '</td>';
          tableBodyHtml += '<td>' + t_userPhone + '</td>';
          tableBodyHtml += '<td>' + t_address + '&nbsp;&nbsp;&nbsp;&nbsp;(默认地址)</td>';
          tableBodyHtml += '</tr>';
        }else{	  
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + t_userName + '</td>';
          tableBodyHtml += '<td>' + t_userPhone + '</td>';
          tableBodyHtml += '<td>' + t_address + '</td>';
          tableBodyHtml += '</tr>';
        }
        
        });
        _table.find("tbody").html(tableBodyHtml);
      },
    async : false
    });
  }

  // 分页
  var myPage3 = function(){
  var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
									"pageNo" : p,
									"pageSize" : "10",
									"userId":_userId
								};
			userLoanInvestor("../user/user_list/selectReceiptAddress", srhData);
		}
	});
};


/**
 * 卡券信息
 */
function cardCoupons(){
	$("#userIdHide").hide();
	$("#LoanInvestorMoney").hide();
	$("#inviteMoney").hide();
	$("#tcdPageCodehide").show()
	var srhData = {"userId":_userId,"pageNo":"1","pageSize":"10"};
	selectCardCoupons("../user/user_list/selectReceiptAward", srhData);	
	myPage5();
}

//用户卡券页面
function selectCardCoupons(tdUrl,tdData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tdData,
      dataType: "json",
      success: function (data) {
        var _table = $('#tab_9'),
          tableBodyHtml = '';
        
        _pages = data.pages;
        
        $.each(data.list,function(k,v){
	          //获取数据
        var t_name = v.name,
        	  t_id = v.id,
        	  t_type = v.type,
        	  t_startDate = v.startDate,
              t_endDate = v.endDate,
              t_status = v.status;
        
        if(t_status == 'INIT'){
        	t_status = '未使用';
       	 }if(t_status == 'USED'){
       		t_status = '已使用';
       	 }if(t_status == 'EXPIRED'){
       		t_status = '已过期';
       	 }
       	 
       	 if(t_type == "THANKS"){
   	  		 t_type = "谢谢参与";
     	 }if(t_type == 'PHYSICAL'){
     		 t_type = '实物券';
     	 }if(t_type == "REDMONEY"){
  	  		 t_type = "红包";
    	 }if(t_type == 'SHAREREDMONEY'){
    		 t_type = '分享红包';
    	 }if(t_type == "EXPERIENCE"){
    		 t_type = "体验金";
    	 }if(t_type == "LOTTERY"){
    		 t_type = "抽奖券";
    	 }if(t_type == "CASH"){
    		 t_type = "现金";
    	 }if(t_type == "POINT"){
    		 t_type = "积分";
    	 }if(t_type == "VOUCHEROFFLINE"){
    		 t_type = "线下代金券";
    	 }if(t_type == "VOUCHER"){
    		 t_type = "系统内部使用代金券";
    	 }if(t_type == "VOUCHERH5"){
    		 t_type = "h5使用的代金券";
    	 }
       	 
        	 
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + t_name + '</td>';
          tableBodyHtml += '<td>' + t_id + '</td>';
          tableBodyHtml += '<td>' + t_type + '</td>';
          tableBodyHtml += '<td>' + v.descValue + '</td>';
          tableBodyHtml += '<td>' + t_startDate + '</td>';
          tableBodyHtml += '<td>' + t_endDate + '</td>';
          tableBodyHtml += '<td>' + t_status + '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
      },
    async : false
    });
  }

  // 分页
  var myPage5 = function(){
  var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var srhData = {
									"pageNo" : p,
									"pageSize" : "10",
									"userId":_userId
								};
			selectCardCoupons("../user/user_list/selectReceiptAward", srhData);
		}
	});
};




	
	


	/**
	 * 邀请好友
	 */
	function inviteFriends(){
		$("#userIdHide").hide();
		$("#tcdPageCodehide").show(); 
		$("#inviteMoney").show();
		$("#LoanInvestorMoney").hide();
		var srhData3 = {"referrer1":_userId,"inviter":_userId,"pageNo":"1","pageSize":"10"};
		lncitePeopliList("../user/user_list/lnvitePeopleList", srhData3);	//邀请好友列表
		myPage2();
		
		var srhData1 = {"referrer1":_userId};
		selectReferrerByUserId("../user/user_list/selectReferrerByUserId", srhData1);	//邀请人数
		
		var srhData2 = {"inviter":_userId};
		selectInviterByUserId("../user/user_list/selectInviterByUserId", srhData2);	//奖励金额
	}
	
	function lncitePeopliList(tdUrl,tdData){
		$.ajax({
		      type: "POST",
		      url: tdUrl,
		      data: tdData,
		      dataType: "json",
		      success: function (data) {
		        var _table = $('#tab_6'),
		          tableBodyHtml = '';
		        
		        _pages = data.pages;
		        
		        $.each(data.list,function(k,v){
		          //获取数据
		        	 if(v.registerMobile != null){
		        		  var t_registerMobile = v.registerMobile
		        	 }else{
		        		 v.registerMobile = ""
		        	 }
		        	 
		        	 if(v.inviteAmount.amount != null){
		        		  var t_inviteAmount = v.inviteAmount.amount
		        	 }else{
		        		 v.inviteAmount.amount = 0
		        	 }
		        	 
		        	 if(v.lnvitePeopleStatus != null){
		        		  var t_lnvitePeopleStatus = v.lnvitePeopleStatus
		        	 }else{
		        		 v.lnvitePeopleStatus = ""
		        	 }
		        	 
		        	 if(t_lnvitePeopleStatus == 'INIT'){
		        		 t_lnvitePeopleStatus = '未认证';
		        	 }if(t_lnvitePeopleStatus == 'IDCARD'){
		        		 t_lnvitePeopleStatus = '已实名';
		        	 }if(t_lnvitePeopleStatus == 'BANKCARD'){
		        		 t_lnvitePeopleStatus = '已绑卡';
		        	 }
		        	 
		          	 
		          //输出HTML元素
		          tableBodyHtml += '<tr>';
		          tableBodyHtml += '<td>' + t_registerMobile + '</td>';
		          tableBodyHtml += '<td>' + t_inviteAmount + '</td>';
		          tableBodyHtml += '<td>' + t_lnvitePeopleStatus + '</td>';
		          tableBodyHtml += '</tr>';
		        });
		        _table.find("tbody").html(tableBodyHtml);
		      },
		    async : false
		    });
	}
	
	// 分页
	  var myPage2 = function(){
	  var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(p) {
				var srhData = {
										"pageNo" : p,
										"pageSize" : "10",
										"referrer1":_userId,
										"inviter":_userId
									};
				lncitePeopliList("../user/user_list/lnvitePeopleList", srhData);
			}
		});
	};
	/**
	 * 奖励金额
	 */
	function selectInviterByUserId(tdUrl,tdData){
		$.ajax({
			type:'POST',
			url:tdUrl,
			data:tdData,
			dataType:'json',
			success:function(data){
				document.getElementById("rewardMoney").value = data.amount;
			},error:function(data){
				alert("出错了1");
			}
		})
	}
	
	/**
	 * 邀请人数
	 * @param tdUrl
	 * @param tdData
	 */
	function selectReferrerByUserId(tdUrl,tdData){
		$.ajax({
			type:'POST',
			url:tdUrl,
			data:tdData,
			dataType:'json',
			success:function(data){
				document.getElementById("invitePeopleAcount").value = data;
			},error:function(data){
				alert("出错了2");
			}
		})
	}

	/**
	 * 出借记录
	 */
	
	function call(){
		$("#userIdHide").hide();
		$("#tcdPageCodehide").show();
		$("#inviteMoney").hide();
		$("#LoanInvestorMoney").show();
		var srhData = {"investorUserCode":_userId,"pageNo":"1","pageSize":"10"};
		userLoanInvestor("../user/user_list/selectLoanInvestor", srhData);	
		myPage7();
		var srhData2 = {"userId":_userId};
		selectInvestorTotalAmountByUserId("../user/user_list/selectInvestorTotalAmountByUserId", srhData2);	//出借总金额
		
	}
	
	/**
	 * 出借总金额
	 */
	function selectInvestorTotalAmountByUserId(tdUrl,tdData){
		$.ajax({
			type:'POST',
			url:tdUrl,
			data:tdData,
			dataType:'json',
			success:function(data){
				document.getElementById("totalMoney").value = data.totalInvest.amount+"元";
			},error:function(data){
				alert("出错了");
			}
		})
	}

		//用户出借页面
		function userLoanInvestor(tdUrl,tdData) {
		    $.ajax({
		      type: "POST",
		      url: tdUrl,
		      data: tdData,
		      dataType: "json",
		      success: function (data) {
		        var _table = $('#tab_7'),
		          tableBodyHtml = '';
		        
		        _pages = data.pages;
		        
		        $.each(data.list,function(k,v){
 		          //获取数据
		        var t_loanCode = v.loanCode,
		        	  t_title = v.title,
		        	  t_yearRate = v.yearRate,
		        	  t_projectCycle = v.projectCycle,
		              t_investTime = v.investTime,
		              t_redMoneyAmount = v.redMoneyAmount.amount,
		              t_ActualPay = v.actualPay.amount,
		              t_investAmount = v.investAmount.amount,
		              t_status = v.status;
		        t_investorStatus = v.investorStatus;
	        	  if(t_investorStatus=="FAIL"){
	        		  t_investorStatus="失败";
	        	  }if(t_investorStatus=="SUCCESS"){
	        		  t_investorStatus="成功";
	        	  }
		       
		        	   if(t_status == 'INIT'){
		        		 t_status = '初始状态';
		        	 }if(t_status == 'OPEN'){
		        		 t_status = '开标状态但未到出借时间';
		        	 }if(t_status == 'OPENED'){
		        		 t_status = '开标状态可以出借';
		        	 }if(t_status == 'FULL'){
		        		 t_status = '满标';
		        	 }if(t_status == 'DISBURSE'){
		        		 t_status = '已放款';
		        	 }if(t_status == 'STOP'){
		        		 t_status = '停止募集';
		        	 }if(t_status == 'COMPLETED'){
		        		 t_status = '已完成';
		        	 }if(t_status == 'KILLED'){
		        		 t_status = '已流标';
		        	 }
		          	 
		          //输出HTML元素
		          tableBodyHtml += '<tr>';
		          tableBodyHtml += '<td>' + t_title + '</td>';
		          tableBodyHtml += '<td>' + t_yearRate + '%</td>';
		          tableBodyHtml += '<td>' + t_projectCycle + '天</td>';
		          tableBodyHtml += '<td>' + t_investTime + '</td>';
		          tableBodyHtml += '<td>' + t_redMoneyAmount + '</td>';
		          tableBodyHtml += '<td>' + t_ActualPay+"元" + '</td>';
		          tableBodyHtml += '<td>' + t_investAmount+"元" + '</td>';
		          tableBodyHtml += '<td>' + t_investorStatus + '</td>';//出借状态
		          tableBodyHtml += '<td>' + t_status + '</td>';
		          tableBodyHtml += '</tr>';
			      
		        });
		        
		        _table.find("tbody").html(tableBodyHtml);
		      },
		    async : false
		    });
		  }

		  // 分页
		  var myPage7 = function(){
		  var $tcdPage = $(".tcdPageCode");
			$tcdPage.createPage({
				pageCount : _pages,
				current : 1,
				backFn : function(p) {
					var srhData = {
											"pageNo" : p,
											"pageSize" : "10",
											"investorUserCode":_userId
										};
					userLoanInvestor("../user/user_list/selectLoanInvestor", srhData);
				}
			});
		};
		
		//用户真实信息页面
		/*function userrealname(){
			$("#userIdHide").hide();
			 $("#tcdPageCodehide").hide();
				$("#inviteMoney").hide();
				var id = document.getElementById("userid").value;
				$.ajax({
					type:'POST',
					url:'../user/user_list/selectUserRealNameById',
					data:{
							 "userId":id
							 //"authResult":"Default"
							},
					dataType:'json',
					success:function(data){
						document.getElementById("realName1").value = data.realName;
						document.getElementById("certType1").value = data.certType;
						document.getElementById("certNo1").value = data.certNo;
						document.getElementById("authResult1").value = data.authResult;
						document.getElementById("code1").value = data.code;
						document.getElementById("message1").value = data.message;
					},error:function(){
						alert("失败")
					},error:function(data){
						alert("出错了");
					}
				})
		}*/



		//微信用户页面
	   function userwx(){
		   $("#userIdHide").hide();
		   $("#tcdPageCodehide").hide();
		   $("#inviteMoney").hide();
		   $("#LoanInvestorMoney").hide();
			var id = document.getElementById("userid").value;
			$.ajax({
				type:'POST',
				url:'../user/user_list/selectWxUserById',
				data:{"userId":id},
				dataType:'json',
				success:function(data){
					
					if(data.sex == 'UNKNOWN'){
		          		data.sex = '未知';
		           	  }if(data.sex == 'MAN'){
		           		data.sex = '男';
		           	  }if(data.sex == 'WOMAN'){
		           		data.sex = '女';
		           	  }
		           	  
					document.getElementById("mobile").value = data.mobile;
					document.getElementById("country").value = data.country;
					document.getElementById("provinces").value = data.province;
					document.getElementById("citys").value = data.city;
					document.getElementById("headimgurl").value = data.headimgurl;
					document.getElementById("privilege").value = data.privilege;
					document.getElementById("nickname").value = data.nickname;
					document.getElementById("sex").value = data.sex;
				},error:function(){
				}
			})
	   }

	
		//用户扩展页面
		function userextend(){
			$("#userIdHide").hide();
			 $("#tcdPageCodehide").hide();
			$("#inviteMoney").hide();
			$("#LoanInvestorMoney").hide();
			var id = document.getElementById("userid").value;
			$.ajax({
				type:'POST',
				url:'../user/user_list/selectUserExtendInfoById',
				data:{"userId":id},
				dataType:'json',
				success:function(data){
					document.getElementById("email").value = data.email;
					document.getElementById("zipCode").value = data.zipCode;
					document.getElementById("province").value = data.province;
					document.getElementById("city").value = data.city;
					document.getElementById("address").value = data.address;
					document.getElementById("phone").value = data.phone;
					document.getElementById("job").value = data.job;
					document.getElementById("remark").value = data.remark;
				},error:function(){
				}
			})
		}
		
		//绑卡信息页面
		function userbindback(){
			$("#userIdHide").hide();
			 $("#tcdPageCodehide").hide();
			$("#inviteMoney").hide();
			$("#LoanInvestorMoney").hide();
			var id = document.getElementById("userid").value;
			$.ajax({
				type:'POST',
				url:'../user/user_list/selectBindBackById',
				data:{"userId":id},
				dataType:'json',
				success:function(data){
					
					 if(data.cardType == 'DEBITCARD'){
			          		data.cardType = '借记卡';
			        }if(data.cardType == 'CREDITCARD'){
			           		data.cardType = '信用卡';
			        }
			        
			        if(data.certType == 'ID'){
		           		data.certType = '身份证';
	           	   }if(data.certType == 'VISA'){
	           		    data.certType = '签证';
	           	   }if(data.certType == 'PT'){
	           		   data.certType = '护照';
	           	   }if(data.certType == 'MC'){
	           		   data.certType = '军人证';
	           	   }if(data.certType == 'OP'){
	           		   data.certType = '港澳通行证';
	           	   }
		        
			        if(data.bindState == "INIT"){
		          		data.bindState = '未绑定';
			        }if(data.bindState == "BIND"){
			           		data.bindState = '已绑定';
			        }if(data.bindState == 'BINDAPPLY'){
			           		data.bindState = '绑定申请中';
			        }if(data.bindState == 'BINDFAILURE'){
			           		data.bindState = '绑卡失败';
			        }if(data.bindState == 'UNBIND'){
			           		data.bindState = '解绑';
			        }if(data.bindState == 'UNBINDAPPLY'){
			           		data.bindState = '解绑申请中';
			        }if(data.bindState == 'UNBINDFAILURE'){
			           		data.bindState = '解绑失败';
			        }
			        if(data.bindState == '解绑'){
					document.getElementById("bankCode").value = "";
					document.getElementById("bankName").value = "";
					document.getElementById("bankMobile").value = "";
					document.getElementById("bankCardNo").value = "";
					document.getElementById("cardType").value = "";
					document.getElementById("realNames").value = data.realName;
					document.getElementById("certTypes").value = data.certType;
					document.getElementById("certNos").value = data.certNo;
					document.getElementById("merchant").value = data.merchant;
					document.getElementById("bindState").value = data.bindState;
					document.getElementById("code").value = data.code;
					document.getElementById("message").value = data.message;
			        }else{
			        	document.getElementById("bankCode").value = data.bankCode;
						document.getElementById("bankName").value = data.bankName;
						document.getElementById("bankMobile").value = data.bankMobile;
						document.getElementById("bankCardNo").value = data.bankCardNo;
						document.getElementById("cardType").value = data.cardType;
						document.getElementById("realNames").value = data.realName;
						document.getElementById("certTypes").value = data.certType;
						document.getElementById("certNos").value = data.certNo;
						document.getElementById("merchant").value = data.merchant;
						document.getElementById("bindState").value = data.bindState;
						document.getElementById("code").value = data.code;
						document.getElementById("message").value = data.message;
			        }
				},error:function(){
				}
			})
		}
	function accountInfo() {
		$("#userIdHide").hide();
		 $("#tcdPageCodehide").hide();
		$("#inviteMoney").hide();
		$("#LoanInvestorMoney").hide();
		var id = document.getElementById("userid").value;	
		$.ajax({
			type:'POST',
			url:'../user/user_list/selectAccountInfo',
			data:{"userId":id},
			dataType:'json',
			success:function(data){
				
				if(data.resCode == 0) {
					if(data.data.balance != null)
						$("#balance").val(data.data.balance.amount + "元");
					if(data.data.freezeAmount != null)
						$("#freezeAmount").val(data.data.freezeAmount.amount + "元");
					if(data.data.redMoneyBalance != null)
						$("#redMoneyBalance").val(data.data.redMoneyBalance.amount + "元");
				}
				else
					alert(data.msg);                                                                      
			},error:function(){
				alert("出错了");
			}
		})
	}
	
//印模查看
$("#checkBtn").click(function(){
	_userId = Request.id;
	 window.location.href = "../signet/moulage_edit.html?userId="+_userId;
	 
});