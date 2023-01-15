//检查
var data_;var redMoneyAcount_;var modal_expireDay;
function test(){
	
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});

	 if (!_modalFm1.validationEngine('validate')) {
			    return false;
			  }
	
				var array = $("#userIds").val().trim().replace(/[\n\r]/gi,"").replace(/[ ]/g,"").split(";");
				var nums = [ ];
				for (var i = 0; i < array.length; i++) {
					nums.push(parseInt(array[i]));
				}
				if(nums.length > 500){
					alert("用户不能超过500");
				}else{
					$('#myModal').modal('show');
					var tData = $("#userIds").val().trim().replace(/[\n\r]/gi,"").replace(/[ ]/g,"");
					$.ajax({
						type : "POST",
						url : "../user/redpackets/getCanSendUserIds",//查询正确的能发的userId
						data : {"userId":tData},
						dataType : "json",
						success : function(data) {
							data_ = data;
							document.getElementById("modal_amount").value = data.length; //人数
							
							var titile = $("#titile").val();
							redMoneyAcount_ =  $("#redMoneyAcount").val();
							redMoneyAcount_ = titile;
							document.getElementById("redMoneyAcount").value = redMoneyAcount_* data.length;//总金额
							
							var times = $("#times").val();
							modal_expireDay =  $("#modal_expireDay").val();
							modal_expireDay = times;
							document.getElementById("modal_expireDay").value = modal_expireDay;//红包有效期
						},
						error : function() { 
							alert("检查失败");
						}
					});
				}
		};
 

//发红包
var _modalSave = $("#modal_save");
_modalSave.click(function() {
    var tData = data_;
    var _userIds = new Array();
    for(var p in tData){
        _userIds.push(tData[p].id);
    }
    $.ajax({
        type : "POST",
        url : "../user/redpackets/sendRedMoneyWithMsg",
        data: {"userIds":_userIds,
            	"amount":redMoneyAcount_,
            	"smsContent":$("#smsContent").val(),
            	"expireDay":modal_expireDay
        		},
        dataType : "json",
        success : function(data) {
            if(data.resCode == 0){
				console.log(data.data);
                $('#myModal').modal('hide');
                alert("发送完毕");
            }else alert(data.msg);

            $('#myModal').modal('hide');
        },
        error : function() {
            $('#myModal').modal('hide');
        },
        async : false
    });


});
