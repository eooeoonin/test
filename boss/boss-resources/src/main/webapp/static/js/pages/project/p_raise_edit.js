/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
	var ue = UE.getEditor('desc');
//	var ue = UE.getEditor('purpose');
//	var ue = UE.getEditor('risk');

	_modalFm1 =  $('#loanForm');
	  _modalFm1.validationEngine('attach', {
			  maxErrorsPerField:1,
			  autoHidePrompt: true,
			  autoHideDelay: 2000
			});
    //查询项目文件
  $.ajax({
    type: "POST",
    url: "/project/p_raise_edit/getLoanFileList/"+Request.loanCode,
    success: function (data) {
      if (data != null && data != "") {
        tableBodyHtml = '';
        $.each(data.data, function (k, v) {
          //获取数据
          var d_sitUrl = v.fileUrl,
              d_id = v.id;
          //输出HTML元素
          tableBodyHtml += '<div class="file-box" id="'+d_id+'">';
          tableBodyHtml += '<div class="file">';
          tableBodyHtml += '<span class="corner"></span>';
          tableBodyHtml += '<div class="image">';
          tableBodyHtml += '<a class="fancybox img-responsive" href="'+domainUrl+loanOrigin+d_sitUrl+'"';
          tableBodyHtml += 'title="项目文件">';
          tableBodyHtml += '<img alt="image" src="'+domainUrl+loanOrigin+d_sitUrl+'"/>';
          tableBodyHtml += '</a>';
          tableBodyHtml += '</div>';
          tableBodyHtml += '<div class="file-name">';
          tableBodyHtml += '<small>项目文件</small>';
          tableBodyHtml += '<div class="img-btns clear">';
          tableBodyHtml += '<div class="pull-right">';
          tableBodyHtml += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\''+d_id+'\')"><i class="fa fa-trash-o"></i> 删除 </a>';
          tableBodyHtml += '</div>';
          tableBodyHtml += '</div>';
          tableBodyHtml += '</div>';
          tableBodyHtml += '</div>';
          tableBodyHtml += '</div>';
        });
        $("#loanFiles").html(tableBodyHtml);
      }
    },
    async: false
  });

  //上传文件
  $(document).on('ready', function () {
    $.ajax({
      type : "POST",
      url : "/project/p_raise_edit/showLoan/"+Request.loanCode,
      success : function(data) {
        if (data != null && data != "") {
          if (data.resCode == 0) {
            //获取数据
            var loanCode = data.data.loanCode,
                borrowTitle = data.data.borrowTitle,
                title = data.data.title,
                borrowTypeName = data.data.borrowTypeName,
                contractNo = data.data.contractNo,
                contractName = data.data.contractName,
                loanTypeName = data.data.loanTypeName,
            	floatRate = data.data.floatRate,
                investInvetCode = data.data.investInvetCode,
                yearRate=data.data.yearRate,
                borrowRate = data.data.borrowRate,
                borrowCycle = data.data.borrowCycle,
                amount=data.data.amount.amount,
                borrowAmount=data.data.borrowAmount.amount,
                borrowSurplusAmount=data.data.borrowSurplusAmount.amount,
                repayTypeName=data.data.repayTypeName,
                openTime=data.data.openTime,
                openEndTime=data.data.openEndTime,
                interestStartDate=data.data.interestStartDate,
                interestEndDate=data.data.interestEndDate,
                investGroupCode=data.data.investGroupCode,
                beginInvest=data.data.beginInvest.amount,
                stepInvest=data.data.stepInvest.amount,
                maxInvest=data.data.maxInvest.amount,
                useRedPacketName=data.data.useRedPacketName,
                desc=data.data.desc,
                addInterestCardFlag=data.data.addInterestCardFlag,
                cashCardFlag=data.data.cashCardFlag,
//                purpose=data.data.purpose,
//                risk=data.data.risk,
                operdesc=data.data.openedoperdesc,
                operUser=data.data.operUser,
                descId=data.data.descId,
                purposeId=data.data.purposeId,
                riskId=data.data.riskId,
                investGroupCodeName=data.data.investGroupCodeName,
                operdescId=data.data.openedoperdescId;
            redMoneyScale=data.data.redMoneyScale;
            repayDesc=data.data.repayDesc;
            $("#useRedPacket").val( useRedPacket?1:0 );
            $("#redMoneyScale").text( redMoneyScale );
            $("#redMoneyScale").val( redMoneyScale );
            if(addInterestCardFlag){
            	 $("#addInterestCardFlag").val(1);
                 $("#addInterestCardFlag").text("是");
            }else{
           	 $("#addInterestCardFlag").val(0);
             $("#addInterestCardFlag").text("否");
            }
            if(cashCardFlag){
           	 $("#cashCardFlag").val(1);
                $("#cashCardFlag").text("是");
           }else{
          	 $("#cashCardFlag").val(0);
            $("#cashCardFlag").text("否");
           }
//            if (useRedPacket == true) {
//           	 $("#redMoneyScale").removeAttr("disabled");
//        	    $("#redMoneyScale").val( redMoneyScale );
//     	  
//     	    } else {
//     	     
//       	    $("#redMoneyScale").attr("disabled", "disabled");
//       	      $("#redMoneyScale").val(redMoneyScale);
//     	    }
            $("#repayDesc").val( repayDesc );
            $("#loanCode").text( loanCode );
            $("#id").val( loanCode );
            $("#borrowTitle").text( borrowTitle );
            $("#title").text( title );
            $("#borrowTypeName").text( borrowTypeName );
            $("#contractName").text( contractName );
            $("#investInvetCode").text( investInvetCode );
            $("#yearRate").text( yearRate +"%");
            $("#borrowRate").text( borrowRate +"%");
            $("#borrowCycle").text( borrowCycle );
            $("#amount").text( amount +"元");
            $("#amountinput").val( amount );
            $("#borrowAmount").text( borrowAmount +"元");
            $("#borrowSurplusAmount").text( borrowSurplusAmount+"元" );
            $("#loanTypeName").text( loanTypeName );
            $("#floatRate").text(floatRate + "%");
            
            $("#repayTypeName").text( repayTypeName );
            $("#openTime").text( openTime );
//            $("#openEndTime").text( openEndTime );
            $("#interestStartDate").text( interestStartDate );
            $("#interestEndDate").text( interestEndDate );
            $("#beginInvest").text( beginInvest +"元");
            $("#stepInvest").text( stepInvest +"元");
            $("#maxInvest").text( maxInvest +"元");
            $("#desc").val( desc );
            $("#descId").val( descId );
//            $("#purpose").val( purpose );
            $("#purposeId").val( purposeId );
//            $("#risk").val( risk );
            $("#riskId").val( riskId );
            $("#operdesc").val( operdesc );
            $("#operdescId").val( operdescId );
            $("#operUser").text( operUser );
            $("#contractNo").val( contractNo );
            $("#loanType").val( "NOVICE" );
            $("#useRedPacket").text( useRedPacketName );
            $("#investGroupCodeName").text( investGroupCodeName );
            $("#editedBy").text( data.data.editedBy );
            $("#createTime").text( data.data.createTime );
            $("#autoInvestMaxAmount").val( data.data.autoInvestMaxAmount.amount+"元");
            
            var approveHtml = "";           
            if(data.data.initoperdescId != null) {
            	approveHtml += "<tr>";
            	approveHtml += "<td class=\"input-group-addon\">待开标操作内容</td>"; 
            	approveHtml += "<td colspan=\"3\">"+data.data.initoperdesc+"</td>";
    			approveHtml += "</tr>";
    			approveHtml += "<tr>";
            	approveHtml += "<td class=\"input-group-addon\">操作人</td>";
    			approveHtml += "<td>"+data.data.initoperdescEditBy+"</td>";
    			approveHtml += "<td class=\"input-group-addon\">操作时间</td>";
    			approveHtml += "<td>"+data.data.initoperdescTime+"</td>";
    			approveHtml += "</tr>";
            }
            if(data.data.openoperdescId != null) {
            	approveHtml += "<tr>";
            	approveHtml += "<td class=\"input-group-addon\">未开始操作内容</td>"; 
            	approveHtml += "<td colspan=\"3\">"+data.data.openoperdesc+"</td>";
    			approveHtml += "</tr>";
    			approveHtml += "<tr>";
            	approveHtml += "<td class=\"input-group-addon\">操作人</td>";
    			approveHtml += "<td>"+data.data.openoperdescEditBy+"</td>";
    			approveHtml += "<td class=\"input-group-addon\">操作时间</td>";
    			approveHtml += "<td>"+data.data.openoperdescTime+"</td>";
    			approveHtml += "</tr>";
            }
            var length = $("#table").find("tr").length-3;
	    	    $("#table tr:eq("+length+")").after(approveHtml);
            
          }
          else{
            bootbox.alert(data.msg);
          }
        }
      },
      async : false
    });

    $("#fileinput").fileinput({
      language: 'zh', //设置语言
      uploadUrl: "/project/p_raise_edit/uploadLoanFile/"+Request.loanCode, //上传的地址
      allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
      uploadAsync: true,//是否异步方式提交
      enctype: 'multipart/form-data',//2进制传输数据
      showUpload: true,//是否显示提交按钮
//      minImageWidth: 50, //图片的最小宽度
//      minImageHeight: 50,//图片的最小高度
//      maxImageWidth: 1000,//图片的最大宽度
//      maxImageHeight: 1000,//图片的最大高度
      maxFileCount: 10,//上传数量
      maxFilePreviewSize: 10240//上传文件大小
    }).on("fileuploaded", function(event, data, previewId, index) {
      $.ajax({
        type: "POST",
        url: "/project/p_raise_edit/getLoanFileList/"+Request.loanCode,
        success: function (data) {
          if (data != null && data != "") {
            tableBodyHtml = '';
            $.each(data.data, function (k, v) {
              //获取数据
              var d_sitUrl = v.fileUrl,
                  d_id = v.id;
              //输出HTML元素
              tableBodyHtml += '<div class="file-box" id="'+d_id+'">';
              tableBodyHtml += '<div class="file">';
              tableBodyHtml += '<span class="corner"></span>';
              tableBodyHtml += '<div class="image">';
              tableBodyHtml += '<a class="fancybox img-responsive" href="'+domainUrl+loanOrigin+d_sitUrl+'"';
              tableBodyHtml += 'title="项目文件">';
              tableBodyHtml += '<img alt="image" src="'+domainUrl+loanOrigin+d_sitUrl+'"/>';
              tableBodyHtml += '</a>';
              tableBodyHtml += '</div>';
              tableBodyHtml += '<div class="file-name">';
              tableBodyHtml += '<small>项目文件</small>';
              tableBodyHtml += '<div class="img-btns clear">';
              tableBodyHtml += '<div class="pull-right">';
              tableBodyHtml += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\''+d_id+'\')"><i class="fa fa-trash-o"></i> 删除 </a>';
              tableBodyHtml += '</div>';
              tableBodyHtml += '</div>';
              tableBodyHtml += '</div>';
              tableBodyHtml += '</div>';
              tableBodyHtml += '</div>';
            });
            $("#loanFiles").html(tableBodyHtml);
          }
        },
        async: false
      });
    });
  });


  //保存修改
  $("#saveLoan").click(function () {
	  if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }
	  var loanForma = $("#loanForm").serialize();
    $.ajax({
      type: "POST",
      url: "/project/p_raise_edit/saveLoan",
      data: $("#loanForm").serialize(),
      success: function (data) {
        if (data != null && data != "") {
          if (data.resCode == 0) {
            bootbox.alert("操作成功", function () {
              window.location.href = "p_raise_edit.html?loanCode="+ Request.loanCode ;
            });
          }
          else {
            bootbox.alert(data.msg);
          }
        }
      },
      async: false
    });
  });

  //流标
  $("#killLoan").click(function () {
	  if (!_modalFm1.validationEngine('validate')) {
		    return false;
		  }
	  bootbox.confirm("确定要流标吗?", function(result){
	  		if(result){
    $.ajax({
      type: "POST",
      url: "/project/p_raise_edit/killLoan",
      data: $("#loanForm").serialize(),
      success: function (data) {
        if (data != null && data != "") {
          if (data.resCode == 0) {
            bootbox.alert("操作成功", function () {
              window.location.href = "p_raise_list.html";
            });
          }
          else {
            bootbox.alert(data.msg);
          }
        }
      },
      async: false
    });
	  		}
      });
  });

  //资产图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
  $("#p_raise_edit").attr("href","p_raise_edit.html?loanCode="+Request.loanCode);
  $("#p_loan_association_r").attr("href","p_loan_association_r.html?loanCode="+Request.loanCode);
  $("#p_invest_user_r").attr("href","p_invest_user_r.html?loanCode="+Request.loanCode);
});

function delFile(fileId){
  alert( fileId );
  $.ajax({
    type: "POST",
    url: "/project/p_raise_edit/deleteLoanFile/"+fileId,
    success: function (data) {
      if (data != null && data != "") {
        $("#"+fileId).removeAttr();
        $.ajax({
          type: "POST",
          url: "/project/p_raise_edit/getLoanFileList/"+Request.loanCode,
          success: function (data) {
            if (data != null && data != "") {
              tableBodyHtml = '';
              $.each(data.data, function (k, v) {
                //获取数据
                var d_sitUrl = v.fileUrl,
                    d_id = v.id;
                //输出HTML元素
                tableBodyHtml += '<div class="file-box" id="'+d_id+'">';
                tableBodyHtml += '<div class="file">';
                tableBodyHtml += '<span class="corner"></span>';
                tableBodyHtml += '<div class="image">';
                tableBodyHtml += '<a class="fancybox img-responsive" href="'+domainUrl+loanOrigin+d_sitUrl+'"';
                tableBodyHtml += 'title="项目文件">';
                tableBodyHtml += '<img alt="image" src="'+domainUrl+loanOrigin+d_sitUrl+'"/>';
                tableBodyHtml += '</a>';
                tableBodyHtml += '</div>';
                tableBodyHtml += '<div class="file-name">';
                tableBodyHtml += '<small>项目文件</small>';
                tableBodyHtml += '<div class="img-btns clear">';
                tableBodyHtml += '<div class="pull-right">';
                tableBodyHtml += '<a href="javascript:" class="btn btn-default btn-xs" onclick="delFile(\''+d_id+'\')"><i class="fa fa-trash-o"></i> 删除 </a>';
                tableBodyHtml += '</div>';
                tableBodyHtml += '</div>';
                tableBodyHtml += '</div>';
                tableBodyHtml += '</div>';
                tableBodyHtml += '</div>';
              });
              $("#loanFiles").html(tableBodyHtml);
            }
          },
          async: false
        });
      }
    },
    async: false
  });
}

/***
 *** 获取URL参数
 ***/
function GetRequest() {
  var url = location.search; //获取url中"?"符后的字串
  var theRequest = {};
  if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("&");
    for (var i = 0; i < strs.length; i++) {
      theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
    }
  }
  return theRequest;
}
var Request = {};
Request = GetRequest();