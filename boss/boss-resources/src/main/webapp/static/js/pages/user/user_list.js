/**
 * 
 */
var _pages;
var _split;
$(function () {
	
	var _table = $('#table');
	_table.bootstrapTable();
	var srhData = {"pageNo":"1","pageSize":"10"};
	tableFun("../user/user_list/userSelectAll", srhData);	
	myPage();
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm =  $('#modal_form');
	_modalFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
 });
	


function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';
        
        _pages = data.pages;
        
        $.each(data.list, function (k, v) {
          //获取数据
        var u_id = v.id;
        	  u_realName = v.realName,
        	  u_userType = v.userType,
              u_group = v.group,
              u_registerMobile = v.registerMobile,
              u_nickName = v.nickName,
              u_gender = v.gender,
              u_certType = v.certType,
              u_certNo = v.certNo,
          	  u_authState = v.editedBy,
          	  u_userState = v.userState,
          	  u_grade = v.grade,
        	  u_inviteCode = v.inviteCode,
        	  u_referrer1 = v.referrer1,
        	  u_referrer2 = v.referrer2,
        	  u_agent = v.agent,
        	  u_deviceId = v.deviceId,
        	  u_from = v.from,
        	  u_remark = v.remark,
        	  u_egistrationId = v.registrationId,
        	  u_from = v.from;
        
          	  if(u_userType == 'PERSON'){
          		u_userType = '个人会员';
          	  }if(u_userType == 'ENTERPRISE'){
          		u_userType = '企业会员';
          	  }
          	  
          	 if(u_gender == 'UNKNOWN'){
          		u_gender = '未知';
           	  }if(u_gender == 'MAN'){
           		u_gender = '男';
           	  }if(u_gender == 'WOMAN'){
           		u_gender = '女';
           	  }
           	  
           	if(u_certType == 'ID'){
           		u_certType = '身份证';
           	  }if(u_certType == 'VISA'){
           		u_certType = '签证';
           	  }if(u_certType == null){
           		u_certType = '未知';
           	  }
           	  if(u_certType == 'Passport'){
           		u_certType = '护照';
           	  }if(u_certType == 'MilitaryCard'){
           		u_certType = '军人证';
           	  }if(u_certType == 'OnewayPermit'){
           		u_certType = '港澳通行证';
           	  }
          	  
           	if(u_authState == 'INIT'){
           		u_authState = '未认证';
           	  }if(u_authState == 'IDCARD'){
           		u_authState = '实名认证';
           	  }if(u_authState == 'BANKCARD'){
           		u_authState = '绑卡';
           	  }  
          	  

           	if(u_userState == 'NORMAL'){
           		u_userState = '正常';
           	  }if(u_userState == 'FROZEN'){
           		u_userState = '冻结';
           	  }
           	  
           	  
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + u_id + '</td>';
          tableBodyHtml += '<td><a href="user_list_user_detailedinformation.html?id='+u_id+'">' + u_realName + '</a></td>>';
          tableBodyHtml += '<td>' + u_userType + '</td>';
          tableBodyHtml += '<td>' + u_group + '</td>';
          tableBodyHtml += '<td>' + u_registerMobile + '</td>';
          tableBodyHtml += '<td>' + u_nickName + '</td>';
          tableBodyHtml += '<td>' + u_gender + '</td>';
          tableBodyHtml += '<td>' + u_certType + '</td>';
          tableBodyHtml += '<td>' + u_certNo + '</td>';
          tableBodyHtml += '<td>' + u_authState + '</td>';
          tableBodyHtml += '<td>' + u_userState + '</td>';
          tableBodyHtml += '<td>' + u_grade + '</td>';
          tableBodyHtml += '<td>' + u_inviteCode + '</td>';
          tableBodyHtml += '<td>' + u_referrer1 + '</td>';
          tableBodyHtml += '<td>' + u_referrer2 + '</td>';
          tableBodyHtml += '<td>' + u_agent + '</td>';
          tableBodyHtml += '<td>' + u_deviceId + '</td>';
          tableBodyHtml += '<td>' + u_from + '</td>';
          tableBodyHtml += '<td>' + u_remark + '</td>';
          tableBodyHtml += '<td width="120px">' +
			  '<a href="javaScript:" name="'+ u_id +'" title="'+u_realName+'" type="'+u_from+'"  onclick=redpacketsFun(this,"'+u_registerMobile+'","'+u_egistrationId+'") class=" btn btn-primary">奖励红包</a>' +
              '<a href="javaScript:" name="'+ u_id +'" title="'+u_realName+'" type="'+u_from+'"  onclick=changeUserGroupFun(this,"'+u_id+'") class="btn btn-primary">编辑分组</a>' +
              '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
        replaceFun(_table);
        
      },
    async : false
    });
  }
	

$("#sousuo").click(function(){
	var registerMobile = document.getElementById("registerMobile").value;
	var nickName = document.getElementById("nickName").value;
	var realName = document.getElementById("realName").value;
	var id = document.getElementById("id").value;
	if(registerMobile != null || realName != null){
		  var srhData = {
								"pageNo" : "1",
								"pageSize" : "10",
								"registerMobile" :registerMobile,
								"nickName" :nickName,
								"realName": realName,
								"id":id
							   };
			tableFun("../user/user_list/userSelectAll",srhData);
			myPage();
	}
});

   // 分页
  var myPage = function(){
  var $tcdPage = $(".tcdPageCode");
	$tcdPage.createPage({
		pageCount : _pages,
		current : 1,
		backFn : function(p) {
			var registerMobile = document.getElementById("registerMobile").value;
			var nickName = document.getElementById("nickName").value;
			var realName = document.getElementById("realName").value;
			var id = document.getElementById("id").value;
			var srhData = {
									"pageNo" : p,
									"pageSize" : "10",
									"registerMobile":registerMobile,
									"nickName":nickName,
									"realName":realName,
									"id":id
								};
			tableFun("../user/user_list/userSelectAll", srhData);
		}
	});
};


var redpacketsFun = function(obj, registerMobile,registrationId) {	
	$("#modal_id").val(obj.name);
	$("#modal_realName").val(obj.title);
	$("#modal_registerMobile").val(registerMobile);
	$("#modal_from").val(obj.type);
	$("#modal_egistrationId").val(registrationId);
	$('#modal_amount').val("");
	$('#modal_expireDay').val("");
	$('#myModal').modal('show');
};


var _modalSave = $("#modal_save");
_modalSave.click(function() {
			if (!_modalFm.validationEngine('validate')) {
			    return false;
			  }
			else {
				_modalSave.attr("disabled","disabled").addClass("btn-white");
				var userId = $("#modal_id").val();
					  amount = $('#modal_amount').val(),
					  expireDay = $('#modal_expireDay').val();
					  realName = $('#modal_realName').val();
				$.ajax({
					type : "POST",
					url : "../user/user_list/sendRedMoney",
					data : {"userId":userId,"amount":amount,"expireDay":expireDay},
					dataType : "json",
					success : function(data) {
						alert(data.result);
						$('#myModal').modal('hide');
						 _modalSave.removeAttr("disabled").removeClass("btn-white");
					},
					error : function() {
						alert("红包发送失败");
						$('#myModal').modal('hide');
					}
				});
			}	
		});


	function approve(){
		window.location.href="difference_trade_list.html?&type_select="+1+"";
	}


var changeUserGroupFun = function(obj, userId) {
    //将属于自己的
    $.ajax({
        type: "POST",
        url: "/user/user_list/getById",
        data: {"userId": userId},
        sync: false,
        success: function (data) {
            if (data != null && data != "") {
                var _groupStr = data.group;
                _split = _groupStr.split(",");
            }
        },
        async: false
    });

	loadUserGroups(userId);
    $('#userGroupModal').modal('show');
};

function loadUserGroups(userId) {
    //用户权限组列表
    $.ajax({
        type: "POST",
        url: "/user/user_list/getUserGroups",
		sync: false,
        success: function (data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
                	$("#userIdHidden").val(userId);
                    var _data = data.data.list;
                    tableBodyHtml = "";
                    $.each(_data, function (k, v) {
                        var flag = true;
                        //获取数据
                        var codeKey = v.codeKey,
                            codeValue = v.codeValue;
                        $.each(_split, function (k, v) {
                        	if(v != '' && v !=null && v != 'undefined'){
                                if(v == codeKey){
                                    tableBodyHtml += "<input checked style='margin-left: 20px;' type=\"checkbox\" title = " + codeKey + " name='userGroups' value= '"+ codeKey +"'>" + codeValue;
                                	flag = false;
                                }
							}
                        });
                        if(flag){
                            tableBodyHtml += "<input style='margin-left: 20px;' type=\"checkbox\" title = " + codeKey + " name='userGroups' value= '"+ codeKey +"'>" + codeValue;
                        }
                    });
					$("#userGroups").html(tableBodyHtml);

                }else bootbox.alert(data.msg);
            }
        }

    });





}

var _userGroup_modal_save = $("#userGroup_modal_save");
_userGroup_modal_save.click(function() {
    var checkedCount = $("input[name='userGroups']:checked").length;
	if(checkedCount == 0){
		bootbox.alert("至少选中一个组");
		return false;
	}

    _userGroup_modal_save.attr("disabled","disabled").addClass("btn-white");
    var userId = $("#userIdHidden").val();
    var _group = "";
    $("input[type=checkbox]:checked").each(function(){
        _group += $(this).val()+","
    });
    _group = _group.substr(0, _group.length-1);

    $.ajax({
        type : "POST",
        url : "../user/user_list/updateUserGroup",
        data : {"id":userId,"group":_group},
        dataType : "json",
        success : function(data) {
            if (data != null && data != "") {
                if (data.resCode == 0) {
					bootbox.alert(data.data,function(){
						 $('#userGroupModal').modal('hide');
		                    _userGroup_modal_save.removeAttr("disabled").removeClass("btn-white");
		                    $("#sousuo").click();
		                    });
                    
                }else {
                    $('#userGroupModal').modal('hide');
                    _userGroup_modal_save.removeAttr("disabled").removeClass("btn-white");
                    bootbox.alert(data.msg);
                }
            }

        },
        error : function() {
            alert("接口异常");
        }
    });

});