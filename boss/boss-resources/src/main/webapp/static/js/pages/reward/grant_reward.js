var count = 1;
var selectdata;
$(function() {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#addProForm');
	_modalFm1.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 3000
	});
	$.ajax({
		type : "POST",
		url : "/reward/grant_reward/getAllProject",
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$.each(data.data, function(k, v) {
					var myDate = new Date();
					var myDate1 = new Date(v.endTime);
					if(myDate1 >= myDate){
						$("#project").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
					}
				});
			}else{
			}
		},
		async : false
	});
		
});

$('#project').change(function(){ 
	var p = $(this).children('option:selected').val();//这就是selected的值
	while(count > 1){
		firstMin();
	}
	if(p == 0){
		$("#jackpot").html("");
		$("#jackpot").append("<option value='0'>无</option>"); // 为Select追加一个Option(下拉项)
		return;
	}else{
		$.ajax({
			type : "POST",
			url : "/reward/grant_reward/getAllJackpot",
			data : {"id":p},
			dataType : "json",
			success : function(data) {
				if(data.resCode == 0){
					$("#jackpot").html("");
					$("#jackpot").append("<option value='0'>无</option>"); // 为Select追加一个Option(下拉项)
					selectdata = data.data;
					$.each(data.data, function(k, v) {
						if(v.type != "THANKS" && v.type != "REDMONEY" && v.type != "SHAREREDMONEY"){
							var myDate = new Date();
							var myDate1 = new Date(v.limitedDate);
							if(myDate1 >= myDate){
								$("#jackpot").append("<option value='" + v.id + "'>" + v.descName + "</option>"); // 为Select追加一个Option(下拉项)
							}
						}
					});
				}else{
				}
			},
			async : false
		});
	}
	
});

$('#grantType').change(function(){ 
	var p = $(this).children('option:selected').val();//这就是selected的值
	if(p == 1){
		$('#batch').show();
		$('#single').hide();
		$('#batchUser').show();
	}else{
		$('#batch').hide();
		$('#single').show();
		$('#batchUser').hide();
		$('#batchTag').hide();
	}
});

$('#batchType').change(function(){ 
	var p = $(this).children('option:selected').val();//这就是selected的值
	if(p == 1){
		$('#batchUser').hide();
		$('#batchTag').show();
	}else{
		$('#batchTag').hide();
		$('#batchUser').show();
		
	}
});

$("#addSubmit").click(function() {
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#addProForm');
	_modalFm1.validationEngine('attach', {
	  maxErrorsPerField:1,
	  autoHidePrompt: true,
	  autoHideDelay: 3000
	});
	if (!$("#addProForm").validationEngine('validate')) {
		return false;
	};
	var formdata = {};
	var userIds = [];
	var TAG;
	var grantType;
	if($('#grantType').val() == 0){
		userIds[0] = $('#userId').val();
		grantType = "SINGLE";
	}else{
		grantType = "GROUP";
		if($('#batchType').val() == 0){
			userIds = $('#bauserId').val().split(";");
		}else{
			TAG = $('#TAG').val();
		}
	}
	var projectId = $('#project').val();
	var awardProject = $('#project').find("option:selected").text();
	var jackpotId=[];
	var jackpotnum=[];
	var c = 0;
	for(var i = 0; i < count ; i++){
		if(i == 0){
			jackpotId[0] = $("#jackpot option:selected")[0].value;
			jackpotnum[0] = $("#jackpotNum").val()*1;
		}else{
			jackpotId[i] = $("#jackpot"+i+" option:selected")[0].value;
			jackpotnum[i] = $("#jackpotNum"+i).val()*1;
		}
	}
	formdata.TAG = TAG;
	formdata.userIds = userIds;
	formdata.projectId = projectId;
	formdata.jackpotId = jackpotId;
	formdata.jackpotnum = jackpotnum;
	formdata.awardProject = awardProject;
	formdata.grantType = grantType;
	$.ajax({
		type : "POST",
		url : "/reward/grant_reward/grantsAward",
		data : formdata,
		dataType:"json",
        traditional:true,
		success : function(data) {
			if(data.resCode == 0){
				bootbox.alert('操作成功',function(){
					location.href = "";
	        	});
			}else{
				bootbox.alert('操作失败',function(){
					location.href = "";
	        	});
			}
		},
		async : false
	});
});


function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
};




function firstAdd(){
	 $("#modaltable tbody").append('<tr><td><select id="jackpot'+count+'" class="form-control"><option value="0">无</option></select></td><td><input type="text"  id="jackpotNum'+count+'" style="width: 55px"  type="text" class="form-control validate[required]" data-errormessage-value-missing="奖品个数不能为空" /></td></tr>');
	 var jackpot = "#jackpot"+count
	 count++;
	 $.each(selectdata, function(k, v) {
		 if(v.type != "THANKS" && v.type != "REDMONEY" && v.type != "SHAREREDMONEY"){
			 $(jackpot).append("<option value='" + v.id + "'>" + v.descName + "</option>"); // 为Select追加一个Option(下拉项)
		 	}
		 });
};
function firstMin(){
	if(count == 1){
		return;
	}
	$("#modaltable tbody tr:last").remove();
	count--;
};
