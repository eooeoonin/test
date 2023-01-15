
$(function (){
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm1 =  $('#form1');
	_modalFm1.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	_modalFm2 =  $('#form2');
	_modalFm2.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	_modalFm3 =  $('#form3');
	_modalFm3.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	_modalFm4 =  $('#form4');
	_modalFm4.validationEngine('attach', {
		  maxErrorsPerField:1,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
	

});
$.ajax({
	type : "POST",
	url : "/permission/role/getMenusLogineds",
	data : {},
	success : function(data) {
		_showMenus(data.data);
	},
	async : false
});

//查询所有
var data111;
function _showMenus(data){
	
	data111 = data.length;
	var str = "";
	for(var i=0; i<data.length; i++){
		 	str += "<li value="+data[i].id+">";
		    str += "<a name="+data[i].id+" href='#' onclick='edit1(this)'>";
		    str += " <i class='fa fa-tachometer'></i>";
		    str += "<span class='nav-label' >"+data[i].text+"</span>";
		    
		    str += " <span class='fa arrow'></span>";
		    str += "</a>";
		    str += "<ul class='nav nav-second-level dis'>";
		var children =  data[i].children;
		for(var k=0; k<children.length; k++){
			str += "<li><a class='J_menuItem' href='#'name="+children[k].id+" onclick='edit2(this)'>"+children[k].text+"</a></li>";
		}
		str+= "</ul></li>";
	}
	
	var _menus = $("#side-menu1");
	_menus.append(str);
	
}
//输出菜单栏的折叠
var _uls = $("#side-menu1");
_uls.find("a").each(function(){
	$(this).click(function(){
		$(this).next().toggle();
		
	});
});

/**
 * 隐藏
 * 
 * */
$("#rank").hide();
$("#rank1").hide();
$("#rank2").hide();
$("#rank3").hide();
$("#rank4").hide();
//$("#delete1").hide();
//$("#delete2").hide();

//点击新增触发事件
var _inserInser1 = $("#inserInser");
_inserInser1.click(function(){
	$("#rank").show();
	$("#rank1").hide();
	$("#rank2").hide();
	$("#rank3").hide();
	$("#rank4").hide();
//	$("#delete1").hide();
//	$("#delete2").hide();
	var _style = $("#inserstyle");
	_style.change(function(){
		var v_style = $("#inserstyle").val();
		if(v_style=="1"){
			$("#rank1").show();
			$("#rank2").hide();
			$("#rank3").hide();
			$("#rank4").hide();
//			$("#delete1").hide();
//			$("#delete2").hide();
		}
		if(v_style=="2"){
			$("#rank2").show();
			$("#rank1").hide();
			$("#rank4").hide();
			$("#rank3").hide();
//			$("#delete1").hide();
//			$("#delete2").hide();
			selectFun();
		}	
	});
});
//插入一级菜单
$("#insertMenu1").click(function(){
	
	var v_style = $("#inserstyle").val(),
	v_text = $("#text1").val(),
	v_url = $("#url1").val(),
	v_parentId = "0",
	v_permissionFlag=$("#permissionFlag1").val();
	v_display = data111+4;
	tableFun1();
	
});
//插入menu表
function tableFun1() {
	if (!_modalFm1.validationEngine('validate')) {
	    return false;
	  }
	else {
	var v_style = $("#inserstyle").val(),
	v_text = $("#text1").val(),
	v_url = $("#url1").val(),
	v_parentId = "0",
	v_permissionFlag = $("#permissionFlag1").val(),
	v_display = data111+4;
	var tdUrl = "/permission/role/insertMenu";
	var tbData = {
			"style":v_style,
			"text": v_text,
			"url": v_url,
			"displayOrder" : v_display,
			"parentId" : v_parentId,
			"permissionFlag" : v_permissionFlag
	};
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {
			$("#menID").val(data);
			tableFun2();
			alert("添加成功");		
		},error:function(){
			alert("添加失败");
			window.location.reload();
		}
	});
	}
}
//数据校验

//插入resource表
function tableFun2() {
	var v_text = $("#text1").val(),
	v_id=$("#menID").val(),
	//menu id
	v_remark = $("#text1").val();
	var tdUrl = "/permission/role/insertResource";
	var tbData = {
			"text": v_text,
			"remark" : v_remark,
			"menuId" : v_id
	};
	if( document.getElementById("text1").value==""|| document.getElementById("url1").value==""||document.getElementById("permissionFlag1").value==""){
		alert("数据不合法！");
		return false;
	}
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data) {	
			 window.location.reload();
		},error:function(){
			alert("添加失败");
			window.location.reload();
		}
	});
}

//数据校验
//校验菜单名称
function checkMer1(){
	if(document.getElementById("text1").value.length !=""){	
		document.getElementById("label_text1").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#text1").val("").focus();
		document.getElementById("label_text1").innerHTML="<font color='red'>*菜单名称不能为空!</font>";
		 return false;
	}
}

//校验页面地址
function checkMer2(){
	
	
  // wordde =this.value;
	if(document.getElementById("url1").value.length !=""){
		document.getElementById("label_url1").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#url1").val("").focus();
		document.getElementById("label_url1").innerHTML="<font color='red'>*菜单名称不能为空!</font>";
		 return false;
	}
}
function zidong1(){
	var zidong1=document.getElementById('url1').value;//=this.value
	  var aaa = zidong1.split('.');
	  document.getElementById('permissionFlag1').value=aaa[0];
}
function zidong2(){
	var zidong1=document.getElementById('url2').value;//=this.value
	  var aaa = zidong1.split('.');
	  document.getElementById('permissionFlag2').value=aaa[0];
}
function zidong3(){
	var zidong1=document.getElementById('url3').value;//=this.value
	  var aaa = zidong1.split('.');
	  document.getElementById('permissionFlag3').value=aaa[0];
}
function zidong4(){
	var zidong1=document.getElementById('url4').value;//=this.value
	  var aaa = zidong1.split('.');
	  document.getElementById('permissionFlag4').value=aaa[0];
}

//校验权限标识
function checkMerquan1(){
	if(document.getElementById("permissionFlag1").value.length !=""){
		document.getElementById("label_permissionFlag1").innerHTML="<font color='green'></font>";		
		return true;
	}else{
		$("#permissionFlag1").val("").focus();
		document.getElementById("label_permissionFlag1").innerHTML="<font color='red'>*权限标识不能为空!</font>";
		 return false;
	}
}

//插入2级菜单
$("#insertMenu2").click(function(){
	tableFun3();
});
//选择下拉框列表用于插入二级菜单选择导航
function selectFun() {
	var ss="";
	$.ajax({
		type : "POST",
		url : "/permission/role/selectMenu",
		data : {},
		dataType : "json",
		success : function(data) {
			var _select = $('#style1');
			$.each(data,function(k,v) {
				var d_text = v.text,
					d_id = v.id;
				ss+='<option value='+d_id+' id="parentId">' + d_text + '</option>';
			});
			_select.html(ss);
		},
		async : false
	});
}

//插入menu表
function tableFun3() {
	//display 排序--thth
	if (!_modalFm2.validationEngine('validate')) {
	    return false;
	  }
	else {
	var v_style = $("#inserstyle").val(),//目录级别
	v_text = $("#text2").val(),//名称
	v_url = $("#url2").val(),//url
	v_permissionFlag = $("#permissionFlag2").val(),//权限标识
	v_parentId = $("#style1").val();//父级id
	var tdUrl1 = "/permission/role/insertMenu";
	var tbData1 = {
			"displayOrder" : thth,
			"style":v_style,
			"text": v_text,
			"url": v_url,
			"permissionFlag" :v_permissionFlag,
			"parentId" : v_parentId		
	};
	if( document.getElementById("text2").value==""|| document.getElementById("url2").value==""||document.getElementById("permissionFlag2").value==""){
		alert("数据不合法！");
		return false;
	}
	$.ajax({
		type : "POST",
		url : tdUrl1,
		data : tbData1,
		dataType : "json",
		success : function(data) {
			$("#menID2").val(data);
			tableFun4();
			alert("添加成功");	
		},error:function(){
			alert("添加失败");
			window.location.reload();
		}
	});
	}
}

//插入Resource表
function tableFun4(){
	var	v_text = $("#text2").val(),
	v_id=$("#menID2").val(),
	v_remark = $("#text2").val();
	var tdUrl = "/permission/role/insertResource";
	var tbData = {
			"text": v_text,
			"remark" : v_remark, 
			"menuId" : v_id
	};
	if( document.getElementById("text2").value==""|| document.getElementById("url2").value==""||document.getElementById("permissionFlag2").value==""){
		return false;
	}
	$.ajax({
		type : "POST",
		url : tdUrl,
		data : tbData,
		dataType : "json",
		success : function(data){		
			 window.location.reload();
		},error:function(){
			alert("添加 失败");
			window.location.reload();
		}
	});
}


//数据校验
//校验菜单名称
function checkMer3(){
	if(document.getElementById("text2").value.length !=""){
		document.getElementById("label_text2").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#text2").val("").focus();
		document.getElementById("label_text2").innerHTML="<font color='red'>*菜单名称不能为空!</font>";
		 return false;
	}
}

//校验页面地址
function checkMer4(){
	if(document.getElementById("url2").value.length !=""){
		document.getElementById("label_url2").innerHTML="<font color='green'></font>";
		var filename = $("#url2").val();
		var flag = false; //状态
	    var arr = "html"; 
	    var lastindex = filename.lastIndexOf(".");//取出上传文件的扩展名
	    var ext = filename.substr(lastindex+1);
	    var index = filename.indexOf("/");
	    if(ext == arr&&index == 0){
	    	 flag = true;
	    }
	    else{
	    	 flag = false;
	    }
	    if(flag)
	    {
	    	document.getElementById("label_url2").innerHTML="<font color='green'></font>";
	    }else
	    {
	    	 document.getElementById("label_url2").innerHTML="<font color='red'>*页面地址不合法!例（/aaa.html）</font>";	     
	    }
		return true;
	}else{
		$("#url2").val("").focus();
		document.getElementById("label_url2").innerHTML="<font color='red'>*页面地址不能为空!</font>";
		 return false;
	}
}

//校验权限标识
function checkMerquan2(){
	if(document.getElementById("permissionFlag2").value.length!=""){
		document.getElementById("label_permissionFlag2").innerHTML="<font color='green'></font>";	
		return true;
	}else{
		$("#permissionFlag2").val("").focus();
		document.getElementById("label_permissionFlag2").innerHTML="<font color='red'>*权限标识不能为空!</font>";
		 return false;
	}
}

var deleid;
var deleid2;
//根据id查询menu信息//用于编辑回显
function edit1(obj1){
	$("#rank3").show();
	$("#rank").hide();
	$("#rank1").hide();
	$("#rank2").hide();
	$("#rank4").hide();
//	$("#delete1").show();
//	$("#delete2").hide();
/*	var d_editid = $("#edit1").val();*/
	var d_editid = obj1.name;
	 var srhData = {
		 "id":d_editid
	}; 
	$.ajax({
		type : "POST",
		url : "/permission/role/selectMenuEdit",
		data : srhData,
		dataType : "json",
		success : function(data) {
			deleid = data.id;
			$("#menuid").val(data.id);
			$("#text3").val(data.text);
			$("#url3").val(data.url);
			$("#permissionFlag3").val(data.permissionFlag);
		},
		async : false
	});
}

//根据ID查询
var thth;
 function parentid(){
	 var d_parentId = $("#style1").val();
	 var srhData = {
	 		"parentId":d_parentId
	 	}; 
	 $.ajax({
			type : "POST",
			url : "/permission/role/selectMenuTwo",
			data : srhData,
			dataType : "json",
			success : function(data) {
				thth = data.length;
			},
			async : false
		});
 }
 
//修改保存menu表
 function submitedit3() {
	 if (!_modalFm3.validationEngine('validate')) {
		    return false;
		  }
		else {
 	var formData=$("#form3").serialize();
 	if( document.getElementById("text3").value==""|| document.getElementById("url3").value==""||document.getElementById("permissionFlag3").value==""){
 		alert("数据不合法！");
 		return false;
 	}
 	$.ajax({
 		type : "POST",
 		url : "/permission/role/OneMenuEdit",
 		data :formData,
 		dataType : "json",
 		success : function(data) {
 			submiteditResourceFun();   
 		},error: function(){
 			alert("修改失败");
 			window.location.reload();
 		}			
 	});
		}
 }
 
 //修改保存resource表
 function submiteditResourceFun(){
 	var v_id = $("#menuid").val(),
 		v_text = $("#text3").val();
 		v_remark = $("#text3").val();
 	var tdUrl = "/permission/role/OneResourceEdit";
 	var tbData = {
 			"menuId":v_id,
 			"text": v_text,
 			"remark" : v_remark
 	};
 	if( document.getElementById("text3").value==""|| document.getElementById("url3").value==""||document.getElementById("permissionFlag3").value==""){
 		alert("数据不合法！");
 		return false;
 	}
 	$.ajax({
 		type : "POST",
 		url : tdUrl,
 		data :tbData,
 		dataType : "json",
 		success : function(data) {
 	      alert("修改成功");
 	      window.location.reload();
 		},error: function(){
 			alert("修改失败");
 			window.location.reload();
 		}
 			
 	});
 } 
 //^123.*abc$
 
 //^/.*.html$
//数据校验

 function checkMer5(){
		if(document.getElementById("text3").value.length !=""){
			document.getElementById("label_text3").innerHTML="<font color='green'></font>";
			return true;
		}else{
			$("#text3").val("").focus();
			document.getElementById("label_text3").innerHTML="<font color='red'>*菜单名称不能为空!</font>";
			 return false;
		}
	}

 //校验页面地址
 function checkMer6(){
		if(document.getElementById("url3").value.length !=""){
			document.getElementById("label_url3").innerHTML="<font color='green'></font>";
			return true;
		}else{
			$("#url3").val("").focus();
			document.getElementById("label_url3").innerHTML="<font color='red'>*URL不能为空!</font>";
			 return false;
		}
	}
 
//校验权限标识
function checkMerquan3(){
	if(document.getElementById("permissionFlag3").value.length !=""){
		document.getElementById("label_permissionFlag3").innerHTML="<font color='green'></font>";		
		return true;
	}else{
		$("#permissionFlag3").val("").focus();
		document.getElementById("label_permissionFlag3").innerHTML="<font color='red'>*权限标识不能为空!</font>";
		return false;
	}
}


//二级菜单编辑回显
function edit2(obj2){
	$("#rank4").show();
	$("#rank1").hide();
	$("#rank2").hide();
	$("#rank3").hide();
//	$("#delete2").show();
//	$("#delete1").hide();
	var d_editid = obj2.name;
	
	 var srhData = {
		 "id":d_editid
	}; 
	$.ajax({
		type : "POST",
		url : "/permission/role/selectMenuEdit",
		data : srhData,
		dataType : "json",
		success : function(data) {
			deleid2 = data.id;
			$("#menuid2").val(data.id);
			$("#text4").val(data.text);
			$("#url4").val(data.url);	
			$("#permissionFlag4").val(data.permissionFlag);
		},
		async : false
	});	
}

//修改二级菜单保存menu表
function submitedit4() {
	if (!_modalFm4.validationEngine('validate')) {
	    return false;
	  }
	else {
	var formData=$("#form4").serialize();
	if( document.getElementById("text4").value==""|| document.getElementById("url4").value==""||document.getElementById("permissionFlag4").value==""){
		alert("数据不合法！");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "/permission/role/OneMenuEdit",
		data :formData,
		dataType : "json",
		success : function(data) {
			submiteditResourceFun2();
		},error: function(){
			alert("修改失败");
			window.location.reload();
		}	
	});
		}
}

//修改二级菜单保存resource表
function submiteditResourceFun2(){
	var v_id = $("#menuid2").val(),
	v_text = $("#text4").val();
	var tdUrl = "/permission/role/OneResourceEdit";
	var tbData = {
		"id":v_id,
		"text": v_text
	};
	if( document.getElementById("text4").value==""|| document.getElementById("url4").value==""||document.getElementById("permissionFlag4").value==""){
		alert("数据不合法！");
		return false;
	}
	$.ajax({
		type : "POST",
		url : tdUrl,
		data :tbData,
		dataType : "json",
		success : function(data) {
			alert("修改成功");
			window.location.reload();
		},error: function(){
			alert("修改失败");
			window.location.reload();
		}	
	});
	
}

//数据校验

function checkMer7(){
	if(document.getElementById("text4").value.length !=""){
		document.getElementById("label_text4").innerHTML="<font color='green'></font>";
		return true;
	}else{
		$("#text4").val("").focus();
		document.getElementById("label_text4").innerHTML="<font color='red'>*菜单名称不能为空!</font>";
		 return false;
	}
}

function checkMer8(){
	if(document.getElementById("url4").value.length !=""){
		document.getElementById("label_url4").innerHTML="<font color='green'></font>";
		var filename = $("#url4").val();
		var flag = false; //状态
	    var arr = "html"; 
	    var lastindex = filename.lastIndexOf(".");//取出上传文件的扩展名
	    var ext = filename.substr(lastindex+1);
	    var index = filename.indexOf("/");
	    if(ext == arr&&index == 0){
	    	 flag = true;
	    }
	    else{
	    	 flag = false;
	    }
	    if(flag)
	    {
	    	document.getElementById("label_url4").innerHTML="<font color='green'></font>";
	    }else
	    {
	    	 document.getElementById("label_url4").innerHTML="<font color='red'>*页面地址不合法!例（/aaa.html）</font>";	     
	    }
		return true;
	}else{
		$("#url4").val("").focus();
		document.getElementById("label_url4").innerHTML="<font color='red'>*页面地址不能为空!</font>";
		 return false;
	}
}

function checkMerquan4(){
	if(document.getElementById("permissionFlag4").value.length !=""){
		document.getElementById("label_permissionFlag4").innerHTML="<font color='green'></font>";	
		return true;
	}else{
		$("#label_permissionFlag4").val("").focus();
		document.getElementById("label_permissionFlag4").innerHTML="<font color='red'>*权限标识不能为空!</font>";
		 return false;
	}
}


//返回事件
function fanhui(){
	window.location.reload();
}




