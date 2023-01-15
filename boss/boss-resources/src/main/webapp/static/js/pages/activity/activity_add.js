var activityId="";
var acode="";
var isJian = false;
var isChou = false;
var isJicai = false;
var isScan = false;
var editId = getUrlParam("id");
var tempUseId = "";
var isEdit = false;
var ue = UE.getEditor('body');

$.ajax({
	type : "POST",
	url : "/activity/activityManager/getAllProject",
	dataType : "json",
	success : function(data) {
		if(data.resCode == 0){
			$.each(data.data, function(k, v) {
				$("#projectChou").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
			});
		}else{
		}
	},
	async : false
});


$(function() {

	/***************************************************************************
	 * 功能说明：时间插件 参数说明： 创建人：李波涛 时间：2016-08-01
	 **************************************************************************/
	 //时间段
	  var start = {
	    elem: "#startTime", format: "YYYY-MM-DD hh:mm:ss", istime: true,  choose: function (datas) {
	      end.min = datas;
	      end.start = datas
	    }
	  };
	  var end = {
	    elem: "#endTime", format: "YYYY-MM-DD hh:mm:ss", istime: true , choose: function (datas) {
	      start.max = datas
	    }
	  };

	  var award = {
	    elem: "#awardTime", format: "YYYY-MM-DD hh:mm:ss", istime: true, choose: function (datas) {
	      start.max = datas
	    }
	  };



	  $('#tempType').change();
	  laydate(start);
	  laydate(end);
	  laydate(award);

	
	if(undefined != editId && null != editId && "" != editId ){
		$("#tptype").hide();
		$("#tpselect").hide();
		$.ajax({
			type : "POST",
			url : "/activity/activityManager/getActivityById",
			data : {'id':editId},
			dataType : "json",
			success : function(data) {
				if(data.resCode == 0){
					 $('#name').val(data.data[0].name);
					 $('#startTime').val(data.data[0].startTime);
					 $('#endTime').val(data.data[0].endTime);
					 $('#desc').val(data.data[0].desc);
					 $('#tempType').val(data.data[1].type);
					 tempUseId = data.data[1].id;
					 $('#body').val(data.data[0].body);
					 $('#projectChou').val(data.data[0].rule1);
					 $('#rule1').val(data.data[0].rule1);
					 $('#rule2').val(data.data[0].rule2);
					 $('#rule3').val(data.data[0].rule3);
					 $('#wxName').val(data.data[0].wxName);
					 $('#wxDesc').val(data.data[0].wxDesc);

					 $('#standardAmount').val(data.data[0].standardAmount);
					 $('#conversionRatio').val(data.data[0].conversionRatio);
					 $('#totalAmount').val(data.data[0].totalAmount);
					 $('#startNum').val(data.data[0].startNum);
					 $('#endNum').val(data.data[0].endNum);
					 $('#awardTime').val(data.data[0].awardTime);
					 $('#awardValue').val(data.data[0].awardValue);
					 $('#tempType').change();
					 $("input[name='pc_is_show'][value='" + data.data[2].PC + "']").prop("checked", true);
					 $('#fileWXName').val(data.data[0].wxPic);
					if(data.data[0].wxPic != "" && data.data[0].wxPic != null) {
						var wxfileUrl = domainUrl + pic + data.data[0].wxPic;
					    var _wxcdImgId = $("#wxfilethumbnail");
					    _wxcdImgId.attr("href",wxfileUrl);
					    _wxcdImgId.find("img").attr("src",wxfileUrl);
					    _wxcdImgId.find("input").val(data.data[0].wxPic);
					}
				}else{
					bootbox.alert("数据加载异常");
				}
			},
			async : false
		});
	}
	
	initFileInput2("wxPicture", "/boss/imageUpload?bizeCode=pic","1",false);
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

	var url="";
	if(undefined != editId && null != editId && "" != editId ){
		url="/activity/activityManager/editActivity";
		isEdit = true;
	}else{
		url="/activity/activityManager/addActivity";
		isEdit = false;
	}
	if(!isEdit || (undefined != document.getElementById("file").value && document.getElementById("file").value != "" && null != document.getElementById("file").value) ){
		  if (/.*\img.zip$/.test(document.getElementById("file").value)) {  
		        ;  
		        } else {  
		            alert('请选择img.zip文件!');
		            return false;
		        }  
	}
	if($("#fileWXName").val() == ""){
		alert("微信分享缩略图不能为空");
		return false;	  
	}
	$("#addSubmit").attr("disabled","disabled").addClass("btn-white");
  
	var file = new FormData($('#uploadForm')[0]);
	file.append('wxPic', $('#fileWXName').val());
	file.append('pcIsShow', $('input[type="radio"][name="pc_is_show"]:checked').val());
	if(isJian){
		var templateId = $("#temp option:selected")[0].value;
		var name = $("#name").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var desc = $("#desc").val();
		var body = ue.getContent();
		var rule1 = $("#rule1").val();
		var rule2 = $("#rule2").val();
		var rule3 = $("#rule3").val();
		var wxName = $("#wxName").val();
		var wxDesc = $("#wxDesc").val();
		if(isEdit){
			file.append('id',editId);
		}
		file.append('templateId',templateId);
		file.append('name',name);
		file.append('activityStartTime',startTime);
		file.append('activityEndTime',endTime);
		file.append('desc',desc);
		file.append('body',body);
		file.append('rule1',rule1);
		file.append('rule2',rule2);
		file.append('rule3',rule3);
		file.append('wxName',wxName);
		file.append('wxDesc',wxDesc);
	}else if(isChou){
		var templateId = $("#temp option:selected")[0].value;
		var name = $("#name").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var desc = $("#desc").val();
		var body = ue.getContent();
		var rule1 = $("#projectChou option:selected")[0].value;
		var wxName = $("#wxName").val();
		var wxDesc = $("#wxDesc").val();
		if(isEdit){
			file.append('id',editId);
		}
		file.append('templateId',templateId);
		file.append('name',name);
		file.append('activityStartTime',startTime);
		file.append('activityEndTime',endTime);
		file.append('desc',desc);
		file.append('body',body);
		file.append('rule1',rule1);
		file.append('wxName',wxName);
		file.append('wxDesc',wxDesc);
	}else if(isJicai){
        var templateId = $("#temp option:selected")[0].value;
        var name = $("#name").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var awardTime = $("#awardTime").val();
        var desc = $("#desc").val();
        var body = ue.getContent();
        var rule1 = $("#projectChou option:selected")[0].value;
        var wxName = $("#wxName").val();
        var wxDesc = $("#wxDesc").val();
        var standardAmount = $("#standardAmount").val();
        var conversionRatio = $("#conversionRatio").val();
        var totalAmount = $("#totalAmount").val();
        var startNum = $("#startNum").val();
        var endNum = $("#endNum").val();
        if(isEdit){
            file.append('id',editId);
        }
        file.append('templateId',templateId);
        file.append('name',name);
        file.append('activityStartTime',startTime);
        file.append('activityEndTime',endTime);
        file.append('desc',desc);
        file.append('body',body);
        file.append('rule1',rule1);
        file.append('wxName',wxName);
        file.append('wxDesc',wxDesc);
        file.append('standardAmount',standardAmount);
        file.append('conversionRatio',conversionRatio);
        file.append('totalAmount',totalAmount);
        file.append('startNum',startNum);
        file.append('endNum',endNum);
        file.append('awardTime',awardTime);
    }else if(isScan){
        var templateId = $("#temp option:selected")[0].value;
        var name = $("#name").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var desc = $("#desc").val();
        var body = ue.getContent();
        var wxName = $("#wxName").val();
        var wxDesc = $("#wxDesc").val();
        var awardValue = $("#awardValue").val();
        var rule1 = $("#projectChou option:selected")[0].value;
        if(isEdit){
            file.append('id',editId);
        }
        file.append('templateId',templateId);
        file.append('name',name);
        file.append('activityStartTime',startTime);
        file.append('activityEndTime',endTime);
        file.append('desc',desc);
        file.append('body',body);
        file.append('rule1',rule1);
        file.append('wxName',wxName);
        file.append('wxDesc',wxDesc);
        file.append('awardValue',awardValue);
	}else{
		var templateId = $("#temp option:selected")[0].value;
		var name = $("#name").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var desc = $("#desc").val();
		var body = ue.getContent();
		var wxName = $("#wxName").val();
		var wxDesc = $("#wxDesc").val();
		if(isEdit){
			file.append('id',editId);
		}
		file.append('templateId',templateId);
		file.append('name',name);
		file.append('activityStartTime',startTime);
		file.append('activityEndTime',endTime);
		file.append('desc',desc);
		file.append('body',body);
		file.append('wxName',wxName);
		file.append('wxDesc',wxDesc);
	}
	$.ajax({
		type : "POST",
		url : url,
		data : file,
		cache: false,
        processData: false,
        contentType: false,
		success : function(data) {
			if(data.resCode == 0){
				activityId = data.data.id;
				acode = data.data.code;
				bootbox.alert('保存成功',function(){
					location.href = "/activity/activity_addLink.html?activityId="+activityId+"&acode="+acode;
	        	});
			}else{
				bootbox.alert('操作失败',function(){
					location.href = "/activity/activity_list.html";
	        	});
			}
		},
		async : false
	});
});

$('#tempType').change(function(){  
	var p = $(this).children('option:selected').val();//这就是selected的值
    if( p ==4 ){
        isScan = true;
        $('#scan').show();
        $('#choujian').show();
    }else{
        isScan =false;
        if(!isChou && !isScan && !isJicai) {
            $('#choujian').hide();
        }
        $('#scan').hide();
    }



	if( p ==3 ){
        isJicai = true;
        $('#choujian').show();
        $('#jicai0').show();
        $('#jicai1').show();
        $('#jicai2').show();
        $('#jicai3').show();
        $('#jicai4').show();
        $('#jicai5').show();
	}else{
        isJicai =false;
        if(!isChou && !isScan && !isJicai ) {
            $('#choujian').hide();
        }
        $('#jicai0').hide();
        $('#jicai1').hide();
        $('#jicai2').hide();
        $('#jicai3').hide();
        $('#jicai4').hide();
        $('#jicai5').hide();
	}

	if(p == 2){
		isJian = true;
		$('#jian0').show();
		$('#jian1').show();
		$('#jian2').show();
		$('#jian3').show();
	}else{
		isJian = false;
		$('#jian0').hide();
		$('#jian1').hide();
		$('#jian2').hide();
		$('#jian3').hide();
	}
	if(p == 1){
		isChou = true;
		$('#choujian').show();
	}else{
		isChou = false;
		if(!isChou && !isScan && !isJicai)
			$('#choujian').hide();
	}
	$("#temp").html("");
	$.ajax({
		type : "POST",
		url : "/activity/template/getAlltemplateByType",
		data : {'type':p},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$.each(data.data, function(k, v) {
					$("#temp").append("<option value='" + v.id + "'>" + v.name + "</option>"); // 为Select追加一个Option(下拉项)
				});
			}else{
			}
			if("" != tempUseId){
				 $('#temp').val(tempUseId);
				 tempUseId = "";
			}
			  $('#temp').change();
		},
		async : false
	});
	});

$('#temp').change(function(){  
	var p = $(this).children('option:selected').val();//这就是selected的值  
	$.ajax({
		type : "POST",
		url : "/activity/template/getTemplateById",
		data : {'id':p},
		dataType : "json",
		success : function(data) {
			if(data.resCode == 0){
				$("#carOl").html("");
				$("#carDiv").html("");
				var t = data.data;
				var path = t.fileName;
				for(i = 0; i < t.imgNum; i++){
					if(i == 0){
						$("#carOl").append('<li data-target="#myCarousel" data-slide-to="'+i+'" class="active"></li>');
						$("#carDiv").append('<div class="item active"><img width="55%" style="margin: 0 auto;" src="'+mktUrl+path+'/exp/pic'+(i+1)+'.jpg" alt="'+i+'"> </div>');
					}else{
						$("#carOl").append('<li data-target="#myCarousel" data-slide-to="'+i+'"></li>');
						$("#carDiv").append('<div class="item"><img width="55%" style="margin: 0 auto;" src="'+mktUrl+path+'/exp/pic'+(i+1)+'.jpg" alt="'+i+'"> </div>');
					}
				}
			}else{
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
function _download(){
	var templateId = $("#temp").val();
    window.location.href = "/activity/activityManager/template/download?templateId=" + templateId;
}

$('#wxPicture').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + pic+ result.fileName;
    var _cdImgId = $("#wxfilethumbnail");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#fileWXName").val(result.fileName);
});

var initFileInput2 = function(ctrlName, uploadUrl,fmax,fpreview) {
	  var control = $('#' + ctrlName);
	  $(document).on('ready', function () {
		  control.fileinput({
		      language: 'zh',
		      allowedFileExtensions: ['jpg', 'png', 'gif'],
		      uploadUrl: uploadUrl,
		      maxFileCount: fmax,
		      enctype: 'multipart/form-data',
		      showUpload: true,
		      showRemove : false,
		      showCaption: true,
		      showPreview: fpreview,
		      uploadAsync: true,
		      dropZoneEnabled: fpreview,
		      initialPreviewShowDelete:false,
		      initialPreview:[],
		      initialPreviewConfig:[],
		      maxFileSize: 0,
		      elErrorContainer: '#errorBlock'
		  });
	 });
};