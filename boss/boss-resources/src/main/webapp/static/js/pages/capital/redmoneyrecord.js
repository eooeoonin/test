/**
 * 
 */
//var _pages;
$(function() {
	var start = {
		elem : "#start",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			end.min = datas;
			end.start = datas;
		}
	};
	var end = {
		elem : "#end",
		format : "YYYY/MM/DD hh:mm:ss",
		istime : true,
		istoday : false,
		choose : function(datas) {
			start.max = datas;
		}
	};
	laydate(start);
	laydate(end);
	
	$.ajax({
		type : "POST",
		url : "../static/data/redmoneytype.txt",
		dataType : "json",
		success : function(data) {
			var _select = $('#subTransCode');
			$.each(data, function(k, v) {
				_select.append('<option value='+v.accountType+'>' + v.accountTypeCn + '</option>');
			});
		},
		error: function(data){
			//alert(data);
		}
	});
});


function tableFun() {
	// var form =$("#form").serialize();
	
	$.ajax({
				type : "POST",
				url : "/capital/redmoneyrecord/getRedMoneyLog",
				data : $("#form").serialize(),
				dataType : "json",
				success : function(data) {
					if(data.resCode == 0){
						if(data.data.list.length > 0) {
							var _table = $('#table'), tableBodyHtml = '';
							$("#currPage").val(data.data.pageNum);
				    		$("#pageCount").val(data.data.pages);
							//_pages = data.data.pages;
							$.each(
									data.data.list,
									function(k, v) {
										var d_id = v.userId,
										d_registerMobile = v.registerMobile,
										d_time = v.modifyTime;
                                        var inAndOutType = handlerInAndOutType(v.source);
                                        var d_in=0, d_out=0;
                                        if(inAndOutType['positiveSymbol'] == '+'){
                                            d_in = v.amount.amount;
                                        }
                                        if(inAndOutType['positiveSymbol'] == '-'){
                                        	d_out = v.amount.amount;
										}
										tableBodyHtml += '<tr>';
										tableBodyHtml += '<td>' + d_id+ '</td>';
										tableBodyHtml += '<td>' + d_registerMobile+ '</td>';
										tableBodyHtml += '<td>' + d_time + '</td>';
										tableBodyHtml += '<td>' + inAndOutType['source']+ '</td>';
										tableBodyHtml += '<td>' + d_in+ '</td>';
										tableBodyHtml += '<td>' + d_out+ '</td>';
										tableBodyHtml += '</tr>';
									});
							_table.find("tbody").html(tableBodyHtml);
                            replaceFun(_table);
                            $("#tcdPageCode").show();
							
						}
						else{
							var _table = $('#table'),
				    	     tableBodyHtml = '';
				    		 tableBodyHtml +='<tr class="no-records-found">';
				    		 tableBodyHtml +='<td colspan="9" align="center">???????????????????????????</td>';
				    		 tableBodyHtml += '</tr>';
				    		 _table.find("tbody").html(tableBodyHtml);
                            $("#tcdPageCode").hide();
						}
					}
					else
						alert(data.msg);

				},
				error:function(data){
					alert("?????????");
				},
				async : false
			});

	
}


var myPage = function(){
	  var $curP = $("#currPage"),
	  $pageC = $("#pageCount");
	  var curpage = parseInt($curP.val());
	  var pageCount = parseInt($pageC.val());
	  var $tcdPage = $(".tcdPageCode");
	  $tcdPage.createPage({
	    pageCount: pageCount,
	    current: curpage,
	    backFn: function (p) {
	    	$curP.val(p);
	    	tableFun();
	    }
	  });
};


var _srhBtn = $("#srhBtn");
_srhBtn.click(function () {
	var startTime = $("#start").val();
	var endTime = $("#end").val();
	if(startTime == "" || endTime == "") {
		alert("???????????????????????????????????????");
		return false;
	}
    var userId = $("#userId").val();
    var registerMobile = $("#registerMobile").val();
    if(userId == "" && registerMobile == "") {
        alert("??????id????????????????????????");
        return false;
    }

	tableFun();	
	myPage();
});


//???????????????????????? ??????????????? ?????? + -
function handlerInAndOutType(source) {
    var _inAndOut ={};
    //??????????????????????????????????????????
    if("expire".toUpperCase() == source.toUpperCase() || "consume".toUpperCase() == source.toUpperCase()){
        _inAndOut['type'] = '??????';
        _inAndOut['positiveSymbol'] = '-';
    }else {
        _inAndOut['type'] = '??????';
        _inAndOut['positiveSymbol'] = '+';
    }
    if("invest".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '????????????';
    }else if("binvest".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '????????????';
    }else if("register".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '????????????';
	}else if("system".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '??????????????????';
    }else if("invite1".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '??????????????????';
    }else if("invite2".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '??????????????????';
    }else if("invite3".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '??????????????????';
    }else if("lottery".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '????????????';
    }else if("rollback".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '????????????';
    }else if("beInvited".toUpperCase() == source.toUpperCase() || "BE_INVITED" == source.toUpperCase()){
        _inAndOut['source'] = '???????????????';
    }else if("consume".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '????????????';
    }else if("expire".toUpperCase() == source.toUpperCase()){
        _inAndOut['source'] = '????????????';
    }else{
        _inAndOut['source'] = '????????????';
	}
	return _inAndOut;

}

