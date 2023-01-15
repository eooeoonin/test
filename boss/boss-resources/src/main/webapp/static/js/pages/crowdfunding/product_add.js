
$(function() {
	
	 //时间段
	  var start = {
	    elem: "#startTime", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
	      end.min = datas;
	      end.start = datas
	    }
	  };
	  var end = {
	    elem: "#endTime", format: "YYYY/MM/DD 23:59:30", istime: true, istoday: false, choose: function (datas) {
	      start.max = datas
	    }
	  };
	  
	  laydate(start);
	  laydate(end);

	  
	  //查询项目信息
		$.ajax({
			type : "POST",
			url : "/crowdfunding/product/projectList",
			data : {},
			success : function(data) {
				if (data != null && data != "") {
					if (data.resCode == 0) {
						var str = "<option value=''>--请选择--</option>";
						var _list = data.data.businessObject.list;
						for(var key = 0; key < _list.length; key++){
							var projectId = _list[key].id;
							var projectName = _list[key].name;
							str += "<option value= '"+projectId+"'>" +projectName+"</option>";
						}
						$("#projectNameSelect").html(str);
					}else{
						bootbox.alert(data.msg);
					}
				}
			},
			async : false
		});

		//图片上传
		initFileInput("fileinput1", "/boss/imageUpload?bizeCode=crowdImages","1",false);
		initFileInput("fileinput2", "/boss/imageUpload?bizeCode=crowdImages","1",false);
		//图片预览
		$(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});  
		
	  
		//新增产品第4步增加详细图片
		var i = 1;
		$("#addInfoImg").bind("click",function(){
			var addTr = '<tr>'+
							'<td class="text-center">'+
								'<div class="file-box bln">'+
									'<div class="file">'+
										'<div class="image">'+
											'<a id="descFileView'+i+'" class="fancybox img-responsive validate[required]" data-errormessage-value-missing="产品详情图不能为空" href="../static/img/noimage.gif" title="产品详情图">'+
												'<img alt="image" src="../static/img/noimage.gif" /><input type="hidden" name="files['+(i-1)+'].name">'+
											'</a>'+
										'</div>'+
									'</div>'+
								'</div>'+
							'</td>'+
							'<td>'+
								'<form class="form-horizontal" enctype="multipart/form-data" method="post" id="descFileForm'+i+'"><input id="descFile'+i+'"  onchange="preview(this,\'descFileView'+i+'\');" name="file" multiple type="file" class="file-loading" /></form>'+
						    '</td>'+
						    '<td><a href="javascript:" onclick="trdel(\'descFileView'+ i +'\')">删除</a></td>'+
					   '</tr>';	
			
			$("#imgTables tbody").append(addTr);
			initFileInput("descFile"+i, "/boss/imageUpload?bizeCode=crowdImages","1",false);
			i++;	
		});
		
		
		$("#sub").click(function(){
			$.ajax({
				type : "POST",
				url : "/crowdfunding/product/add",
				data : $("#productForm").serialize(),
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							location = "/crowdfunding/product.html";
						}else{
							bootbox.alert(data.msg);
						}
					}
				},
				async : false
			});
			
		});
		
	});


function trdel(id){
	$("#"+id).parents("tr").remove();
}

function preview(inputFile, imgId) {
	  var count = imgId.substr(12, imgId.length);
	  var f=inputFile.value;
	  if(!/\.(gif|jpg|jpeg|png)$/.test(f))
	  {
	    alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
	    inputFile.value="";
	    inputFile.outerHTML+='';
	    document.getElementById(imgId).src="";
	    return false;
	  }else{
			file_size = inputFile.files[0].size;
			var size = (file_size / 1024).toFixed(2);
			if (size > 500) {
				alert("*上传图片大小不能超过500K");
				inputFile.value="";
				inputFile.outerHTML+='';
				document.getElementById(imgId).src="";
				return false;
			} else {
				if (inputFile.files.length > 0) {
					
					$('#descFile'+count).on('fileuploaded', function(event, data, previewId, index) {
					    var form = data.form, files = data.files, extra = data.extra,
					    response = data.response, reader = data.reader;
					    var result = eval('(' + response + ')');
					    var fileUrl = domainUrl + crowdImages +result.fileName;
					    var _cdImgId = $("#descFileView"+count);
					    _cdImgId.attr("href",fileUrl);
					    _cdImgId.find("img").attr("src",fileUrl);
					    _cdImgId.find("input").val(result.fileName);
					    $('#descFileView'+count).val(result.fileName);

					});
					
				}
			}
	  }
}


/***
 *功能说明：上传文件
 *参数说明：ctrlName  上传文本框ID  uploadUrl 后台上传接口  fmax 最大上传个数  fpreview 是否显示预览框
 *创建人：李波涛
 *时间：2016-08-18
 ***/
//初始化fileinput控件（第一次初始化）
var initFileInput = function(ctrlName, uploadUrl,fmax,fpreview) {
  var control = $('#' + ctrlName);
	  control.fileinput({
	      language: 'zh', //设置语言
	      allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
	      uploadUrl: uploadUrl, //上传的地址
	      maxFileCount: fmax,//表示允许同时上传的最大文件个数
	      enctype: 'multipart/form-data',//2进制传输数据
	      showUpload: true, //是否显示上传按钮
	      showRemove : false, //显示移除按钮
	      showCaption: true,//是否显示标题
	      showPreview: fpreview, //是否显示预览框
	      uploadAsync: true, //是否异步方式提交
	      dropZoneEnabled: fpreview,//是否显示拖拽区域
	      initialPreviewShowDelete:false,
	      initialPreview:[],
	      initialPreviewConfig:[],
	      maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
	      elErrorContainer: '#errorBlock' //错误信息显示地方
	    });
};

$('#fileinput1').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + crowdImages +result.fileName;
    var _cdImgId = $("#listViewImage");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#listViewImage").val(result.fileName);
  
});
$('#fileinput2').on('fileuploaded', function(event, data, previewId, index) {
    var form = data.form, files = data.files, extra = data.extra,
    response = data.response, reader = data.reader;
    var result = eval('(' + response + ')');
    var fileUrl = domainUrl + crowdImages +result.fileName;
    var _cdImgId = $("#bannerViewImage");
    _cdImgId.attr("href",fileUrl);
    _cdImgId.find("img").attr("src",fileUrl);
    _cdImgId.find("input").val(result.fileName);
    $("#bannerViewImage").val(result.fileName);
  
});



function next(step){
	$("#productForm").validationEngine('attach',{
		inlineValidation: false,
		maxErrorsPerField:1,
		autoHidePrompt: true,
		autoHideDelay: 2000
	});
	
	if (!$("#productForm").validationEngine('validate')) {
		$("#productForm").validationEngine('detach');
		 return false;
	}
	  
	var $pro_step1 = $(".pro_step1");
	var $pro_step2 = $(".pro_step2");
	var $pro_step3 = $(".pro_step3");
	var $pro_step4 = $(".pro_step4");
	if(step==1){
		$pro_step1.show();
		$pro_step2.hide();
		$pro_step3.hide();
		$pro_step4.hide();
	}
	if(step==2){
  if($(".pd-title").val() == ""){
      alert("请输入产品名称");
      return false;
  }
  if($(".pd-name").val() == ""){
      alert("请输入项目名称");
      return false;
  }
  if($(".pd-info").val() == ""){
      alert("请输入产品介绍");
      return false;
  }
  if($(".pd-stime").val() == ""){
      alert("请输入开始时间");
      return false;
  }
  if($(".pd-etime").val() == ""){
      alert("请输入结束时间");
      return false;
  }
		$pro_step1.hide();
		$pro_step2.show();
		$pro_step3.hide();
		$pro_step4.hide();
	}
	if(step==3){
  if($(".listvwimg").val() == ""){
      alert("请上传列表展示图");
      return false;
  }
		$pro_step1.hide();
		$pro_step2.hide();
		$pro_step3.show();
		$pro_step4.hide();
	}
	if(step==4){
  if($(".infovwimg").val() == ""){
      alert("请上传置业展示图");
      return false;
  }
		$pro_step1.hide();
		$pro_step2.hide();
		$pro_step3.hide();
		$pro_step4.show();
	}
}
