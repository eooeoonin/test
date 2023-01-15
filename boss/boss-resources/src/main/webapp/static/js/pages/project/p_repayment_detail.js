/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {

  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();

/*  //查询项目文件
  $.ajax({
    type: "POST",
    url: "/loan/getLoanFileList/"+Request.loanCode,
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
          tableBodyHtml += '<a class="fancybox img-responsive" href="'+domainUrl+loan+d_sitUrl+'"';
          tableBodyHtml += 'title="图表展示、数据可视化是">';
          tableBodyHtml += '<img alt="image" src="'+domainUrl+loan+d_sitUrl+'"/>';
          tableBodyHtml += '</a>';
          tableBodyHtml += '</div>';
          tableBodyHtml += '<div class="file-name">';
          tableBodyHtml += '<small>图表展示、数据可视化是</small>';
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
  });*/

  $("#p_repayment_edit").attr("href","p_repayment_edit.html?loanCode="+Request.loanCode);
  $("#p_loan_association_p").attr("href","p_loan_association_p.html?loanCode="+Request.loanCode);
  $("#p_invest_user_p").attr("href","p_invest_user_p.html?loanCode="+Request.loanCode);
  $("#p_repayment_info").attr("href","p_repayment_info.html?loanCode="+Request.loanCode);
});

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