/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") !== -1) {
    var str = url.substr(1);
    var  strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}  

var Request = {};
Request = GetRequest();

/**
 * 
 */
$(function () {
  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：LSC
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();
  
  var type2 = Request.type;
  if(type2){
	var  srhData2 = {
				"pageNo" : "1",
				"pageSize" : "10",
				"type" : type2
				};
	  tableFun("/web_mgt/w_app_share_list/banner/page",srhData2);
  }

});
  /***
   *功能说明：表格数据
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：LSC
   *时间：2016-08-01
   ***/
   function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,

      
      dataType: "json",
      success: function (data) {
    	  var _data = eval('(' + data + ')');
        var _table = $('#table'),
          tableBodyHtml = '';
        $.each(_data, function (k, v) {
          //获取数据
          if(k>4) {
        	  return ;
          }
          var d_ID = v.id,
            
            d_titile = v.titile, //标题
            d_wxTitle = v.wxTitle,//微信标题
            d_wxPicture = v.wxPicture,//图片
            d_wxBody = v.wxBody;//内容
         

          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_titile + '</td>';
          tableBodyHtml += '<td>' + d_wxTitle + '</td>';
          tableBodyHtml += '<td>' + d_wxPicture + '</td>';
          tableBodyHtml += '<td>' + d_wxBody + '</td>';
          tableBodyHtml += '<td><a href="w_app_share_list_edit.html?id='+d_ID+'">编辑</a>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
        
      }
    });
  }
/**
 * 查询
 */
//$("#srhBtn").click(function(){
function srhBtnClick(){
	var type = $("#type").val(),
	 srhData = {
		"pageNo" : "1",
		"pageSize" : "10",
		"type" : type
		};
	tableFun("/web_mgt/w_app_share_list/banner/page", srhData);	
}

