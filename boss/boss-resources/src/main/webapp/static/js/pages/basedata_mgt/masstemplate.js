/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

var _pages;
$(function (){
	var srhData = {
			"pageNo":"1",
			"pageSize":"10"
	};
	tableFun("/basedata_mgt/masstemplate/page", srhData);
	myPage2();

});
  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/
	var sizedata;
	
  function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  sizedata = data.total;
    	if(data.total!==0){
        var _table = $('#table'),
        tableBodyHtml = '';
        _pages = data.pages;
        $.each(
        		data.list,
        	function (k, v) {
            var d_id = v.id,
            d_title = v.title,//标题
            d_content = v.content;//内容
          
            
            	tableBodyHtml += '<tr>';
            	tableBodyHtml += '<td >' + d_title + '</td>';
            	tableBodyHtml += '<td >' + d_content + '</td>';
            	 tableBodyHtml += '<td><a  href="masstemplate_edit.html?id='+d_id+'">编辑</a></td>';
            	tableBodyHtml += '</tr>';           
                
        });
        $("#tcdPagehide").show();
        _table.find("tbody").html(tableBodyHtml);
      }else{
    	  tableBodyHtml1 +='<tr class="no-records-found">';
 		  tableBodyHtml1 +='<td style="text-align:center; vertical-align:middle;" colspan="7">没有找到匹配的记录</td>';
 		  tableBodyHtml1 += '</tr>';
 		 _table1.find("tbody").html(tableBodyHtml1);
      }
      },
    async : false
    });
  }



function myPage2(){
	  if(sizedata!==0){
		var $tcdPage = $(".tcdPageCode");
		$tcdPage.createPage({
			pageCount : _pages,
			current : 1,
			backFn : function(q) {
						  	
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "10"
					};
					tableFun("/basedata_mgt/masstemplate/page", srhData4);	
			}
		});
}else{
	$("#tcdPagehide").hide();
}
	  
}
 
	

//新增按钮跳转页面
var _addmess = $("#addmessage");
_addmess.click(function(){
	location = "../basedata_mgt/masstemplate_add.html";	
});


