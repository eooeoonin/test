/**
 * 
 */
var _pages;

$(function () {
	
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"10",
			"unit":"0"	
	};
	
	tableFun("../basedata_mgt/isystemconfig_list/getSystemConfig", srhData1);	
	myPage2();
	
	
	var _table = $('#table');
	  _table.bootstrapTable();
	  
	  
	  
	  var _srhBtn = $("#srhBtn");
	  _srhBtn.click(function () {
		  var systemCode = $("#systemCode1").val();
				  
			  var srhData2 = {
						"pageNo" : "1",
						"pageSize" : "10",
						"systemCode" : systemCode,
						"unit":"0"
						
				};
				tableFun("/basedata_mgt/isystemconfig_list/getSystemConfig",srhData2);
				myPage2();
	  });
	  
	   	function myPage2(){
	   		
	   		  var $tcdPage = $(".tcdPageCode");
	   			$tcdPage.createPage({
	   				pageCount : _pages,
	   				current : 1,
	   				backFn : function(q) {
	   					var systemCode1 = $("#systemCode1").val();		  	
	   					  	var srhData4 = {
	   								"pageNo" :q,
	   								"pageSize" : "10",
	   								"systemCode":systemCode1,
	   								"unit":"0"
	   						};
	   						tableFun("/basedata_mgt/isystemconfig_list/getSystemConfig", srhData4);	
	   				}
	   			});
	   	}
	  
});	  
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
              s_classCode = v.classCode,
              s_classDesc = v.classDesc,
              s_codeKey = v.codeKey,
              s_codeValue = v.codeValue,
              s_codeDesc = v.codeDesc,
              s_unit = v.unit,
              s_systemCode = v.systemCode,
              s_systemDesc = v.systemDesc,
          	  s_available = v.available,
          	  s_createTime = v.createTime,
          	  s_modifyTime = v.modifyTime;
        
          	  if(s_available == 1){
          		s_available = '可用';
          	  }if(s_available == 0){
          		s_available = '不可用';
          	  }

          //输出HTML元素
          tableBodyHtml += '<tr>';
         /* tableBodyHtml += '<td>' + s_classCode + '</a></td>>';*/
         /* tableBodyHtml += '<td>' + s_classDesc + '</td>';*/
          tableBodyHtml += '<td>' + s_codeKey + '</td>';
          tableBodyHtml += '<td>' + s_codeValue + '</td>';
          tableBodyHtml += '<td>' + s_codeDesc + '</td>';
         /* tableBodyHtml += '<td>' + s_unit + '</td>';*/
          tableBodyHtml += '<td>' + s_systemCode + '</td>';
          /*tableBodyHtml += '<td>' + s_systemDesc + '</td>';*/
          tableBodyHtml += '<td>' + s_available + '</td>';
//          tableBodyHtml += '<td>' +  + '</td>';
          tableBodyHtml += '<td><a href="../basedata_mgt/isystemconfig_list_update_isystemconfig.html?id='+s_id+'&codekey='+s_codeKey+'&systemCode='+s_systemCode+'" style="margin-left:15px;">编辑</a><a  name='+s_id+' href="javascript:" style="margin-left:15px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
          replaceFun(_table);

      },
    async : false
    });
  }

		
		function deleteIsystemConfig(sid){
			  bootbox.confirm("确定要删除吗?", function(result){
					if(result){
						$.ajax({
							type : "POST",
							url : "../basedata_mgt/isystemconfig_list/deleteIsystemConfig",
							data : {"sid" : sid.name },
							dataType: "json",
							async:false,
							success : function(data) {
								
								if (data != null && data != "") {
									if (data == 1) {
										bootbox.alert("删除成功", function(result){
											window.location.reload();
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

		