var _pages;
$(function () {
	$("#table").hide();
	/**
	 * 模态窗口非空/数字校验
	 */
	_modalFm =  $('#modal_form');
	_modalFm.validationEngine('attach', {
		  maxErrorsPerField:1,
		  focusFirstField:false,
		  autoHidePrompt: true,
		  autoHideDelay: 2000
		});
	
		$("#startTime").focus(function () {
			var myDate = new Date();
			var year = myDate.getFullYear();
			var month = myDate.getMonth()+1;
			var day = myDate.getDate();
			var hour = myDate.getHours();
			var aa = year+'/'+month+'/'+day;
			$(this).val(aa);
		});
		$("#endTime").focus(function () {
			var myDate = new Date();
			var year = myDate.getFullYear();
			var month = myDate.getMonth()+1;
			var day = myDate.getDate()+10;
			var hour = myDate.getHours();
			var bb = year+'/'+month+'/'+day;
			$(this).val(bb);
		});
	
	  //时间段引用方式
	  var start = {
		  elem: "#startTime", format: "YYYY/MM/DD", istime: false, istoday: true, choose: function (datas) {
	      end.min = datas;
	      end.start = datas
	    }
	  };
	  
	  var end = {
	    elem: "#endTime", format: "YYYY/MM/DD", istime: false, istoday: true, choose: function (datas) {
	      start.max = datas
	    }
	  };
	  laydate(start);
	  laydate(end);
 });
	
$("#change").click(function(){
	if (!_modalFm.validationEngine('validate')) {
	    return false;
	  }else{
		 var _startDa = $("#startTime").val();
		 var _endDa = $("#endTime").val();
		$("#table").show();
		var _table = $('#table');
		_table.bootstrapTable();
		var srhData = {"startTime":_startDa,"endTime":_endDa};
		tableFun("../automatic_bidding/fundstatistics/userInvestStatistics", srhData);	
	  }
})


function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	  if(data.length != 0){
		        var _table = $('#table'),
		          tableBodyHtml = '';
		        
		        $.each(data, function (k, v) {
		          //获取数据
		        var u_investTermMin = v.investTermMin;
		        	  u_investTermMax = v.investTermMax;
		        	  u_totalAmount = v.totalAmount.amount;
		           	  
		          //输出HTML元素
		          tableBodyHtml += '<tr>';
		          if(u_investTermMax == '9999'){
		        	  tableBodyHtml += '<td>300天以上</td>';
		          }else{
		        	  tableBodyHtml += '<td>' + u_investTermMin + '-' + u_investTermMax + '天</td>';
		          }
		          tableBodyHtml += '<td>' + u_totalAmount + '元</td>';
		          tableBodyHtml += '</tr>';
		        });
		        _table.find("tbody").html(tableBodyHtml);
		        replaceFun(_table);
    	  }else{
        	   $("#table").hide();
         	   alert("此时间段没有出借");
        }
      },error:function(data){
    	  alert("查询失败");
      },
      	async : false
    });
  }
