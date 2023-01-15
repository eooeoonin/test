/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  		url = decodeURIComponent(url);
  var theRequest = {};
  if (url.indexOf("?") !== -1) {
    var str = url.substr(1);
   var  strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
    }
  }
  return theRequest;
}  

var Request = {};
Request = GetRequest();

var _parentId = Request.parentId;

/**
 * 
 */
var _pages;

$(function () {
	
	var _table = $('#table');
	  _table.bootstrapTable();
	  
	var srhData1 = {"parentId":"0","levelNum":"1","pageNo":"1","pageSize":"10"};
	
	$.ajax({
        type:"post",
        data:{"levelNum":"1","pageNo":"1","pageSize":"1000"},
        dataType:"json",
        url:"../basedata_mgt/provincialcity_list/getSystemConfig",
        success:function(data){
            $.each(data.list,function(k,v){
            	var u_name = v.name,
            		  u_id= v.id;
                	  $("#parentId").append("<option value='"+u_id+"'>"+u_name+"</option>");
            })
        	$('#parentId').change();
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
    },
        async:false             //false表示同步
    }
        );
	
	
	if(_parentId){
		  $("#parentId").val(""+_parentId+"")
		  document.getElementById("parentId").value = _parentId;
		  test();
	}
	
});



$('#parentId').change(function(){  
	var sdata = {"parentId":$('#parentId').val(),"pageNo":"1","pageSize":"10"};
	tableFun("../basedata_mgt/provincialcity_list/getSystemConfig", sdata);	
	myPage2($('#parentId').val());
});


function myPage2(parentID){
		  var $tcdPage = $(".tcdPageCode");
			$tcdPage.createPage({
				pageCount : _pages,
				current : 1,
				backFn : function(q) {
					  	var srhData4 = {
								"pageNo" :q,
								"pageSize" : "10",
								"parentId":parentID,
						};
						tableFun("/basedata_mgt/provincialcity_list/getSystemConfig", srhData4);	
				}
			});
	}



/*	function goback(){
		window.location.href="../basedata_mgt/provincialcity_list.html";
	}*/

/*	function test(){
		var _parId = $("#parentId").val();
        srhData2 = {
			"pageNo" : "1",
			"pageSize" : "10",
			"parentId":_parId
			
	};
	tableFun("/basedata_mgt/provincialcity_list/getSystemConfig",srhData2);
	myPage3(_parId);
	}*/
/*
	
	function myPage3(_parId){
 		  var $tcdPage = $(".tcdPageCode");
 			$tcdPage.createPage({
 				pageCount : _pages,
 				current : 1,
 				backFn : function(q) {
 					  	var srhData5 = {
 								"pageNo" :q,
 								"pageSize" : "10",
 								"parentId":_parId
 						};
 						tableFun("/basedata_mgt/provincialcity_list/getSystemConfig", srhData5);	
 				}
 			});
 	}*/
	


	/**
	 * 
	 */
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
        var s_id = v.id,
              s_code = v.code,
              s_name = v.name,
              s_inCode = v.inCode,
              s_parentId = v.parentId,
              s_displayOrder = v.displayOrder,
              s_levelNum = v.levelNum,
              s_isLeaf = v.isLeaf,
              s_available = v.available;
        
        
          	  if(s_available == 1){
          		s_available = '可用';
          	  }if(s_available == 0){
          		s_available = '不可用';
          	  }

          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + s_code + '</td>';
          tableBodyHtml += '<td>' + s_name + '</td>';
          tableBodyHtml += '<td>' + s_inCode + '</td>';
          tableBodyHtml += '<td>' + s_parentId + '</td>';
          tableBodyHtml += '<td>' + s_displayOrder + '</td>';
          tableBodyHtml += '<td>' + s_levelNum + '</td>';
          tableBodyHtml += '<td>' + s_isLeaf + '</td>';
          tableBodyHtml += '<td>' + s_available + '</td>';
          tableBodyHtml += '<td><a href="../basedata_mgt/provincialcity_list_update_isystemconfig.html?id='+s_id+'" style="margin-left:15px;">编辑</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
        replaceFun(_table);
      },
    async : false,
    error : function(d){
    	console.log(d);
    }
    });
  }
