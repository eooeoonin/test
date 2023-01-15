var i=1;

function _getParamsObject() {
	var thisURL = document.URL;
	var getval = thisURL.split('?')[1];
	var paramsArray = getval.split("&");
	var paramsObject = {};
	for (var i = 0; i < paramsArray.length; i++) {
		var keyAndValue = paramsArray[i].split("=");
		paramsObject[keyAndValue[0]] = keyAndValue[1];
	}
	return paramsObject;
}

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
		
		
		if( _getParamsObject().type=="edit"){
			var productId =  _getParamsObject().productId;
			$.ajax({
				type : "POST",
				url : "/crowdfunding/product/getProduct",
				data : {"productId" : productId},
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							var _data = data.data.businessObject;
							$("#edit_productId").val(_data.id);//编辑时回显productId
							$("#title").val(_data.title);//产品名称
							$("#description").val(_data.description);//产品介绍
							$("#startTime").val(moment(_data.startTime).format("YYYY/MM/DD HH:mm:ss"));//开始时间
							$("#endTime").val(moment(_data.endTime).format("YYYY/MM/DD HH:mm:ss"));//结束时间

							showProjectNameSelect(_data.estateId);//项目名称回显
							
							_loadImages(_data.listImagePath, _data.productBannerPath, _data.productDesPath);
							
						}else{
							bootbox.alert("取得产品详细信息异常");
						}
					}
				},
				async : false
			});
		  
		}
		
		
		
	});


function showProjectNameSelect(estateId){
	
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
						if(projectId == estateId){
							str += "<option value= '"+projectId+"' selected = 'selected'>" +projectName+"</option>";
						}else{
							str += "<option value= '"+projectId+"'>" +projectName+"</option>";
						}
					}
					$("#projectNameSelect").html(str);
				}else{
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
	
}

function _loadImages(listImagePath, productBannerPath, desPathList){
	if(null != listImagePath && ''!= listImagePath){
    	var listViewImageUrl = domainUrl + crowdImages + listImagePath;
        var _cdListViewImage = $("#listViewImage");
        _cdListViewImage.attr("href",listViewImageUrl);
        _cdListViewImage.find("img").attr("src",listViewImageUrl);
        _cdListViewImage.find("input").val(listImagePath);
    }
	if(null != productBannerPath && ''!= productBannerPath){
		var bannerViewImageUrl = domainUrl + crowdImages + productBannerPath;
		var _cdBannerViewImage = $("#bannerViewImage");
		_cdBannerViewImage.attr("href",bannerViewImageUrl);
		_cdBannerViewImage.find("img").attr("src",bannerViewImageUrl);
		_cdBannerViewImage.find("input").val(productBannerPath);
	}
	var desPathListArray = desPathList;
		for(var k=1; k<= desPathListArray.length; k++){
			var value = desPathListArray[k-1];
			if(null != value && ''!= value){
				var addTr = '<tr>'+
				'<td class="text-center">'+
					'<div class="file-box bln">'+
						'<div class="file">'+
							'<div class="image">'+
								'<a id="descFileView'+k+'" class="fancybox img-responsive validate[required]" data-errormessage-value-missing="产品详情图不能为空" href="../static/img/noimage.gif" title="产品详情图">'+
									'<img alt="image" src="../static/img/noimage.gif" /><input type="hidden" name="files['+(k-1)+'].name">'+
								'</a>'+
							'</div>'+
						'</div>'+
					'</div>'+
				'</td>'+
				'<td>'+
					'<form class="form-horizontal" enctype="multipart/form-data" method="post" id="descFileForm'+k+'"><input id="descFile'+k+'"  onchange="preview(this,\'descFileView'+k+'\');" name="file" multiple type="file" class="file-loading" /></form>'+
			    '</td>'+
			    '<td><a href="javascript:" onclick="trdel(\'descFileView'+ k +'\')">删除</a></td>'+
		   '</tr>';	
	
			$("#imgTables tbody").append(addTr);
			initFileInput("descFile"+k, "/boss/imageUpload?bizeCode=crowdImages","1",false);
	
			var fileUrl = domainUrl + crowdImages + value;
			var _cdImgId = $("#descFileView"+k);
			_cdImgId.attr("href",fileUrl);
			_cdImgId.find("img").attr("src",fileUrl);
			_cdImgId.find("input").val(value);
			$('#descFileView'+k).val(value);
			i=desPathListArray.length+1;
		}
	}
}
	

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

