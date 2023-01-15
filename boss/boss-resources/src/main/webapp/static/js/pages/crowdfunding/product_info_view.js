
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
	    elem: "#interestTime", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
	      end.min = datas;
	      end.start = datas
	    }
	  };
	  var end = {
	    elem: "#settleTime", format: "YYYY/MM/DD 23:59:59", istime: true, istoday: false, choose: function (datas) {
	      start.max = datas
	    }
	  };
	  
	  laydate(start);
	  laydate(end);
	  
	  
	  
		//图片预览
		$(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});  

			var productId =  _getParamsObject().productId;
			$("#view_productId").val(productId);
			$.ajax({
				type : "POST",
				url : "/crowdfunding/product/getProduct",
				data : {"productId" : productId},
				success : function(data) {
					if (data != null && data != "") {
						if (data.resCode == 0) {
							var _data = data.data.businessObject;
							$("#title").html(_data.title);//产品名称
							$("#projectNameSelect").html(_data.projectName);//项目名称
							$("#description").html(_data.description);//产品介绍
							$("#startTime").html(moment(_data.startTime).format("YYYY-MM-DD HH:mm:ss"));//开始时间
							$("#endTime").html(moment(_data.endTime).format("YYYY-MM-DD HH:mm:ss"));//结束时间
							_loadImages(_data.listImagePath, _data.productBannerPath, _data.productDesPath);
							_showProductSubs(_data.subProjectInfos);
						}else{
							bootbox.alert("取得产品详细信息异常");
						}
					}
				},
				async : false
			});
		  	
		
		
	});


//回显图片
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
				'</td>'
		   '</tr>';	
	
			$("#imgTables tbody").append(addTr);
			var fileUrl = domainUrl + crowdImages + value;
			var _cdImgId = $("#descFileView"+k);
			_cdImgId.attr("href",fileUrl);
			_cdImgId.find("img").attr("src",fileUrl);
			_cdImgId.find("input").val(value);
			$('#descFileView'+k).val(value);
		}
	}
}

//显示产品权益相关信息
function _showProductSubs(subProjectInfos){
	var tableBodyHtml = '';
	var productId = _getParamsObject().productId;
	for(var k=0; k< subProjectInfos.length; k++){
		tableBodyHtml += '<tr><td width="">' + subProjectInfos[k].title + '&nbsp; <a href="#" onclick= "subProjectUnSale(\''+ subProjectInfos[k].id +'\')">下架</a> &nbsp;';
		if(subProjectInfos[k].type == "MUST_CROWD" && subProjectInfos[k].settle == "NEED"){
			tableBodyHtml +='<a href="javaScript:" onclick="showModal(\''+ subProjectInfos[k].id+'\',\''+subProjectInfos[k].interestTime+'\',\''+subProjectInfos[k].settleTime+'\',\''+subProjectInfos[k].buyRate+'\',\''+subProjectInfos[k].noBuyRate+'\')">设置</a>';
		}
		tableBodyHtml +='&nbsp; <a href="product_sub_edit.html?subProductId='+ subProjectInfos[k].id + '&productId='+productId+'">编辑</a></td></tr>';
	}
	$("#productSubs").find("tbody").html(tableBodyHtml);
	
}


//下架权益
function subProjectUnSale(subProjectId){
	  bootbox.confirm("确定要下架该权益吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/crowdfunding/product/subProjectUnSale",
					data : {
						"subProjectId" : subProjectId
					},
					success : function(data) {
						if (data != null && data != "") {
							if (data.resCode == 0) {
								bootbox.alert("权益下架成功", function(result){
									location = "/crowdfunding/product_info_view.html?productId=" + $("#view_productId").val();
								});
							} else {
								bootbox.alert(data.msg);
							}
						}
					},
					async : false
				});
			}

		});
}


function showModal(subProjectId,interestTime, settleTime, buyRate, noBuyRate){
	$("#subProjectId").val(subProjectId);
	$("#interestTime").val(interestTime);
	$("#settleTime").val(settleTime);
	$("#buyRate").val(buyRate);
	$("#noBuyRate").val(noBuyRate);
	
	$('#myModal').modal('show');
}

function settle(){
	var subProjectId = $("#subProjectId").val();
//	var interestTime = $("#interestTime").val();
	var interestTime = moment($("#interestTime").val()).format("YYYY/MM/DD HH:mm:ss")
	var settleTime = moment($("#settleTime").val()).format("YYYY/MM/DD HH:mm:ss")
	var buyRate = $("#buyRate").val();
	var noBuyRate = $("#noBuyRate").val();
	
	$.ajax({
		type : "POST",
		url : "/crowdfunding/product/setSettle",
		data : {"id":subProjectId,"interestTime":interestTime,"settleTime":settleTime,"buyRate":buyRate,"noBuyRate":noBuyRate},
		success : function(data) {
			if (data != null && data != "") {
				if (data.resCode == 0) {
					bootbox.alert("设置权益成功", function(result){
						location = "/crowdfunding/product_info_view.html?productId=" + $("#view_productId").val();
					});
				} else {
					bootbox.alert(data.msg);
				}
			}
		},
		async : false
	});
	
}

