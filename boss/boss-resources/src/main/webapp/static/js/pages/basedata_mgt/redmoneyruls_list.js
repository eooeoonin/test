/**
 * 
 */
var _pages;

$(function () {
	
	var srhData1 = {
			"pageNo":"1",
			"pageSize":"10"
			};
	
	tableFun("/basedata_mgt/redmoneyrules/redmoneyRulelist", srhData1);	
	myPage2();
	
	
	var _table = $('#table');
	  _table.bootstrapTable();
	  
	  
	  
	  var _srhBtn = $("#srhBtn");
	  _srhBtn.click(function () {				  
			  var srhData2 = {
						"pageNo" : "1",
						"pageSize" : "10"
						
				};
				tableFun("/basedata_mgt/redmoneyrules/redmoneyRulelist",srhData2);
				myPage2();
	  });
	  
	   	function myPage2(){
	   		  var $tcdPage = $(".tcdPageCode");
	   			$tcdPage.createPage({
	   				pageCount : _pages,
	   				current : 1,
	   				backFn : function(q) {	  	
	   					  	var srhData4 = {
	   								"pageNo" :q,
	   								"pageSize" : "10"
	   						};
	   						tableFun("/basedata_mgt/redmoneyrules/redmoneyRulelist", srhData4);	
	   				}
	   			});
	   	}
	  
 
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
        	var d_id = v.id,
				 d_merchantCode1 = v.merchantCode,//商户号
				 d_merchantName1 = v.merchantName,//商户名称
				  d_createTime1 = v.createTime,//创建时间
				  d_modifyTime1 = v.modifyTime,//更新时间  
				  d_type1 = v.type,//红包类型
								//random_invest   出借红包 （分享红包）
								//rated_ register 注册额定红包
								//rated_invite1   邀请额定红包
								//rated_invite2   邀请额定红包
								//rated_system    补偿额定红包
				  d_isuse1 = v.isuse,//是否可用
				  					//是否可用 1-可用，0-不可用
				  d_amount1 = v.amount.amount+'元',//金额
									  //1、分享红包此金额为起送金额
							          //2、定额红包 此金额为红包金额
				  d_useRange1 = v.useRange,//使用范围
											//#1#,债权转让项目使用 
											//#2#,优选理财使用 
											//#3#,散标项目使用
				  d_pushWay1 = v.pushWay,//推送方式
				  						//推送方式,#1#,站内信 #2#,邮件 #3#,短信
				  d_isPush1 = v.isPush,	//是否推送
				  d_minAmount1 = v.minAmount.amount+'元',//红包最小值
				  d_maxAmount1 = v.maxAmount.amount+'元',//红包最大值
				  d_validDate1 = v.validDate+'天',//有效期
				  d_ratio1 = v.ratio,//手气红包折算比例
				  d_redmoneyConut1 = v.redmoneyConut,//手气红包数量
				  d_formula1 = v.formula,//红包公式
				  d_available1 = v.available,//是否可用
				  d_mark1 = v.mark,//备注
				  d_editedBy1 = v.editedBy;//修改人\
        	var redavailable;
        	if(d_available1==true){
        		redavailable='是';      		
        	}else if(d_available1==false){
        		redavailable='否';
        	}
        	var redisPush;
        	if(d_isPush1==true){
        		redisPush='是';      		
        	}else if(d_isPush1==false){
        		redisPush='否';
        	}
//        	var useRange;
//        	if(d_useRange1=='1'){
//        		useRange='债权转让项目使用 ';
//        	}else if(d_useRange1=='2'){
//        		useRange='优选理财使用 ';
//        	}else if(d_useRange1=='3'){
//        		useRange='散标项目使用';
//        	}
        	var redpushWay;
        	if(d_pushWay1=='1'){
        		redpushWay ='站内信 ';
        	}else if(d_pushWay1=='2'){
        		redpushWay ='邮件 ';
        	}else if(d_pushWay1=='3'){
        		redpushWay ='短信 ';
        	}
        	var redstatus;
        	if(d_isuse1==true){
        		redstatus='是';      		
        	}else if(d_isuse1==false){
        		redstatus='否';
        	}
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + d_merchantName1 + '</td>';
          tableBodyHtml += '<td>' + d_createTime1 + '</td>';
          tableBodyHtml += '<td>' + d_type1 + '</td>';
          
          tableBodyHtml += '<td>' + d_mark1 + '</td>';
          tableBodyHtml += '<td>' + redstatus + '</td>';
          tableBodyHtml += '<td>' + d_amount1 + '</td>';
//          tableBodyHtml += '<td>' + redpushWay+ '</td>';
//          tableBodyHtml += '<td>' + redisPush + '</td>';
          tableBodyHtml += '<td>' + d_validDate1 + '</td>';

          tableBodyHtml += '<td>' + d_editedBy1 + '</td>';
          tableBodyHtml += '<td><a href="../basedata_mgt/redmoneyrules_update.html?id='+d_id+'" style="margin-left:10px;">编辑</a><a  name='+d_id+' href="javascript:" style="margin-left:10px;" onclick="deleteIsystemConfig(this)">删除</a></td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);
      },
    async : false
    });
  }	
		

});	 
function deleteIsystemConfig(sid){
	  bootbox.confirm("确定要删除吗?", function(result){
			if(result){
				$.ajax({
					type : "POST",
					url : "/basedata_mgt/redmoneyrules/redmoneyRuledelete",
					data : {
						"id" : sid.name 
						},
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