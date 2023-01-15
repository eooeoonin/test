/***
 *** 获取URL参数
 ***/
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
	//URL参数
	var id = Request.id;
	
	document.getElementById("userid").value = Request.id;
	
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
          	  
          	 if(data.gender == 0){
          		data.gender = '未知';
           	  }if(data.gender == 1){
           		data.gender = '男';
           	  }if(data.gender == 2){
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
           	  
          	  
           	if(data.authState == 'U'){
           		data.authState = '未认证';
           	  }if(data.authState == 'C'){
           		data.authState = '公安部什么认证';
           	  }if(data.authState == 'B'){
           		data.authState = '银行卡认证';
           	  }  
          	  

           	if(data.userState == 'Normal'){
           		data.userState = '正常';
           	  }if(data.userState == 'Frozen'){
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
		},error:function(){
			
		}
	})
	
});

	
		//用户扩展页面
		function userextend(){
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
			var id = document.getElementById("userid").value;
			$.ajax({
				type:'POST',
				url:'../user/user_list/selectBindBackById',
				data:{"userId":id},
				dataType:'json',
				success:function(data){
					
					 if(data.cardType == 'debitCard'){
			          		data.cardType = '借记卡';
			        }if(data.cardType == 'creditCard'){
			           		data.cardType = '信用卡';
			        }
			        
			        if(data.certType == 'idCard'){
		           		data.certType = '身份证';
	           	   }if(data.certType == 'visa'){
	           		    data.certType = '签证';
	           	   }if(data.certType == 'passport'){
	           		   data.certType = '护照';
	           	   }if(data.certType == 'militaryCard'){
	           		   data.certType = '军人证';
	           	   }if(data.certType == 'onewayPermit'){
	           		   data.certType = '港澳通行证';
	           	   }
		        
			        if(data.bindState == 0){
		          		data.bindState = '未绑定';
			        }if(data.bindState == 1){
			           		data.bindState = '已绑定';
			        }if(data.bindState == 2){
			           		data.bindState = '绑定中';
			        }if(data.bindState == -1){
			           		data.bindState = '绑定失败';
			        }if(data.bindState == -2){
			           		data.bindState = '解绑';
			        }
					
					document.getElementById("bankCode").value = data.bankCode;
					document.getElementById("bankName").value = data.bankName;
					document.getElementById("bankMobile").value = data.bankMobile;
					document.getElementById("bankCardNo").value = data.bankCardNo;
					document.getElementById("cardType").value = data.cardType;
					document.getElementById("realName").value = data.realName;
					document.getElementById("certType").value = data.certType;
					document.getElementById("certNo").value = data.certNo;
					document.getElementById("merchant").value = data.merchant;
					document.getElementById("bindState").value = data.bindState;
					document.getElementById("code").value = data.code;
					document.getElementById("message").value = data.message;
				},error:function(){
					
				}
			
			})
		}
