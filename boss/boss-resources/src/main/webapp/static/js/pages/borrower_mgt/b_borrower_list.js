var _pages;

$(function () {
  var _table = $('#table');
  _table.bootstrapTable();
  var srhData = {"pageNo":"1","pageSize":"10"};
  tableFun("/borrower/borrowerlist/borrowersWithPage", srhData);
  myPage();
  });

var start = {
	    elem: "#createTimeStart", format: "YYYY-MM-DD", istoday: true, choose: function (datas) {
	        end.min = datas;
	        end.start = datas
	    }
	};
	var end = {
	    elem: "#createTimeEnd", format: "YYYY-MM-DD",  istoday: true, choose: function (datas) {
	        start.max = datas
	    }
	};
	try {
	    laydate(start);
	    laydate(end);
	} catch (e) {
	}
function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      dataType: "json",
      success: function (data) {
    	var a = data.data.businessObject.total;
    	if(a!=0){
        var _table = $('#table'),
        tableBodyHtml = '';
        _pages= data.data.businessObject.pages;
        var _data = data.data.businessObject;
        
        $.each(_data.list, function (k, v) {
        	
          //获取数据
        	var d_id = v.id, 
        	roleCode = v.roleCode,
        	userId = v.userId,
            d_legal=v.userName,  //企业名称
            d_data= moment(v.createTime).format("YYYY-MM-DD");  // 用户注册时间
        	var d_approvalStatus = "";
            if(v.approvalStatus == 'DEACTIVATED'){
                d_approvalStatus = '未激活';
            }if(v.approvalStatus == 'FROZEN'){
                d_approvalStatus = '冻结';
            }if(v.approvalStatus == 'INIT' || null == v.approvalStatus){
        		d_approvalStatus = '初始未开户';
        	}if(v.approvalStatus == 'AUDIT'){
        		d_approvalStatus = '审核中';
        	}if(v.approvalStatus == 'AUDIT_REFUSED'){
        		d_approvalStatus = '审核拒绝';
        	}if(v.approvalStatus == 'NORMAL' || v.approvalStatus == 'AUDIT_PASSED'){
        		d_approvalStatus = '审核通过';
        	}if(v.approvalStatus == 'AUDIT_BACK'){
        		d_approvalStatus = '审核回退';
        	}
        	var isInit;
        	if(d_approvalStatus == "初始未开户"){
        		isInit = "yes";
        	}else{
        		isInit = "no";
        	}
        	
          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td>' + userId + '</td>';
          tableBodyHtml += '<td>' + v.registerName + '</td>';
          tableBodyHtml += '<td><a href="../signet/moulage_edit.html?userId='+userId+'">' + d_legal + '</a></td>';
          if(v.userType ==  "ENTERPRISE"){
              tableBodyHtml += '<td>企业</td>';
          }else{
        	  tableBodyHtml += '<td>个人</td>';
          }
          if(roleCode == "BORROW"){
        	  tableBodyHtml += '<td>借款人</td>';
          }else{
        	  tableBodyHtml += '<td>担保人</td>';
          }
          tableBodyHtml += '<td>' + d_data + '</td>';
          tableBodyHtml += '<td>' + d_approvalStatus + '</td>';
          tableBodyHtml += '<td>' + v.approvalMessage + '</td>';
          if(v.userType ==  "ENTERPRISE"){
        	  tableBodyHtml += '<td><a href="borrowerlist_applyinfo.html?type=edit&userId='+ userId +'&isInit='+isInit+'">查看</a></td>';
          }else{
        	  tableBodyHtml += '<td><a href="borrowerlist_perinfo.html?type=edit&userId='+ userId +'&isInit='+isInit+'">查看</a></td>';
          }
          tableBodyHtml += '</tr>';
        	
        });
        $("#tcdPagehide").show();
        _table.find("tbody").html(tableBodyHtml);
        replaceFun(_table);
    	}else{
    		 var _table = $('#table'),
    	     tableBodyHtml = '';
    		 tableBodyHtml +='<tr class="no-records-found">';
    		 tableBodyHtml +='<td colspan="5">没有找到匹配的记录</td>';
    		 tableBodyHtml += '</tr>';
    		 _table.find("tbody").html(tableBodyHtml);
    		 $("#tcdPagehide").hide();
    	}
      },
	  async : false
    });
  }

$("#srhBtn").click(function(){
	var userId = $("#userId").val();
	var userName = $("#userName").val();
	var approvalStatus = $("#approvalStatus").val();
	var roleCode = $("#roleCode").val();
	var createTimeStart;
	var createTimeEnd;
	var srhData = {};
	if($("#createTimeStart").val() != null && $("#createTimeStart").val() != "" && $("#createTimeEnd").val() != null && $("#createTimeEnd").val() != ""){
		createTimeStart = $("#createTimeStart").val();
		createTimeEnd = $("#createTimeEnd").val();
		srhData = {"pageNo":"1","pageSize":"10" ,"userId":userId, "userName":userName,'approvalStatus':approvalStatus,'createTimeStart':createTimeStart,'createTimeEnd':createTimeEnd,'roleCode':roleCode};
	}else{
		srhData = {"pageNo":"1","pageSize":"10" ,"userId":userId, "userName":userName,'approvalStatus':approvalStatus,'roleCode':roleCode};
	}
	tableFun("/borrower/borrowerlist/borrowersWithPage", srhData);
	myPage();
});

var myPage = function(){
    //分页
    var $tcdPage = $(".tcdPageCode");
    $tcdPage.createPage({
      pageCount: _pages,
      current: 1,
      backFn: function (p) {
    	  //点击分页事件
    	  	var userId = $("#userId").val();
    		var userName = $("#userName").val();
    		var approvalStatus = $("#approvalStatus").val();
    		var roleCode = $("#roleCode").val();
    		var createTimeStart;
    		var createTimeEnd;
    		var srhData = {};
    		if($("#createTimeStart").val() != null && $("#createTimeStart").val() != "" && $("#createTimeEnd").val() != null && $("#createTimeEnd").val() != ""){
    			createTimeStart = $("#createTimeStart").val();
    			createTimeEnd = $("#createTimeEnd").val();
    			srhData = {"pageNo":p,"pageSize":"10" ,"userId":userId, "userName":userName,'approvalStatus':approvalStatus,'createTimeStart':createTimeStart,'createTimeEnd':createTimeEnd,'roleCode':roleCode};
    		}else{
    			srhData = {"pageNo":p,"pageSize":"10" ,"userId":userId, "userName":userName,'approvalStatus':approvalStatus,'roleCode':roleCode};
    		}
	  	  tableFun("/borrower/borrowerlist/borrowersWithPage", srhData);
      }
    });
}

