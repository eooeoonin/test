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
	tableFun("/basedata_mgt/message/messageSelectAll", srhData);
	myPage2();
	/*
	 * 选择下拉框列表
	 * */
	selectFun("../static/data/message_select_type.txt", "");
	function selectFun(tdUrl, tbData) {
		$.ajax({
			type : "POST",
			url : tdUrl,
			data : tbData,
			dataType : "json",
			success : function(data) {
				var _select = $('#type');
				$.each(data, function(k, v) {
					var d_type = v.type;
					_select.append('<option>' + d_type + '</option>');
				});
				}
		});
	}
	var _srhBtn = $("#srhBtn");
	  _srhBtn.click(function () {
		  var v_type = $("#type").val();
		  var v_subject = $("#subject").val();
			  var srhData2 = {
						"pageNo" : "1",
						"pageSize" : "10",
						"type" :v_type,
						"subject" : v_subject
				};
				tableFun("/basedata_mgt/message/messageSelectAll",srhData2);
				myPage2();	
	  });
	  var _qingBtn = $("#qingBtn");
	  _qingBtn.click(function(){
		  location = "../basedata_mgt/message.html";		  
	  });
});
  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/
	var sizedata;
	
  function tableFun(tdUrl, tbData, tbPage) {
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
            d_name = v.subject,
            d_miaoshu = v.content,
          	d_duanxin = v.open,
            d_type = v.type,
            d_key = v.key;
            if(d_duanxin===1){
            	d_duanxin='开启';
            }else{
            	d_duanxin='关闭';
            }
            // SMS PUSH EMAIL INNER_MESSAGE
            var duanxin1;
            var zhannei1;
            var email1;
            var push11;
            if(d_type==="SMS"){
            	duanxin1=d_duanxin;
                zhannei1="——";
                email1="——";
                push11="——";
            }else if(d_type==="PUSH"){
            	duanxin1="——";
                zhannei1="——";
                email1="——";
                push11=d_duanxin;
            }else if(d_type==="EMAIL"){
            	duanxin1="——";
                zhannei1="——";
                email1=d_duanxin;
                push11="——";
            }else if(d_type==="INNER_MESSAGE"){
            	duanxin1="——";
                zhannei1=d_duanxin;
                email1="——";
                push11="——";
            }
            
            	tableBodyHtml += '<tr height="50">';
            	tableBodyHtml += '<td width=12%>' + d_name + '</td>';
            	tableBodyHtml += '<td width=6%>' + d_key + '</td>';
            	tableBodyHtml += '<td width="52%">' + d_miaoshu + '</td>';
            	tableBodyHtml += '<td width="6%" style="text-align:center; vertical-align:middle;">' + duanxin1 + '</td>';
            	tableBodyHtml += '<td width="6%" style="text-align:center; vertical-align:middle;">' + zhannei1 + '</td>';
            	tableBodyHtml += '<td width="6%" style="text-align:center; vertical-align:middle;">' + email1 + '</td>';
            	tableBodyHtml += '<td width="6%" style="text-align:center; vertical-align:middle;">' + push11 + '</td>'; 
                if(d_type === "SMS"){
              		tableBodyHtml += '<td width="6%" style="text-align:center"><a href="message_compile.html?id=' + d_id +'" class="msgEdit">编辑</a></td>'; 
                }else if(d_type === "INNER_MESSAGE"){
              		tableBodyHtml += '<td width="6%" style="text-align:center"><a href="message_center.html?id=' + d_id +'" class="msgEdit">编辑</a></td>'; 
                }else if(d_type === "EMAIL"){
              		tableBodyHtml += '<td width="6%" style="text-align:center"><a href="message_email.html?id=' + d_id +'" class="msgEdit">编辑</a></td>'; 
                }else if(d_type === "PUSH"){
              		tableBodyHtml += '<td width="6%" style="text-align:center"><a href="message_push.html?id=' + d_id +'" class="msgEdit">编辑</a></td>'; 
                }          	 
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
				  var	v_type = $("#type").val(),
					v_subject = $("#subject").val();				  	
				  	var srhData4 = {
							"pageNo" :q,
							"pageSize" : "10",	
							"type" : v_type,
							"subject" : v_subject
					};
					tableFun("/basedata_mgt/message/messageSelectAll", srhData4);	
			}
		});
}else{
	$("#tcdPagehide").hide();
}
	  
}
 
	

//新增按钮跳转页面
var _addmess = $("#addmessage");
_addmess.click(function(){
	location = "../basedata_mgt/message_insert.html";	
});


