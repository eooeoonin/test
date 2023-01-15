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
  chkFun();
  $(window).resize(function () {
    chkFun();
  });
  tableFun("../static/data/example.txt", "");


  /***
   *功能说明：查询操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-08-01
   ***/
  var _srhBtn = $("#srhBtn"),
    _srhPrmtxt = $(".srh_prmtxt");
  _srhBtn.click(function () {
    var srhId = $("#srhId").val(),
      srhName = $("#srhName").val();
    if(!srhId){
      _srhPrmtxt.html("名称不能为空");
      return false;
    }
    var srhData = {"id":srhId,"name":srhName};
    tableFun("../static/data/example2.txt", srhData);
  });




  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/
  function tableFun(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,

      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';
        $.each(data, function (k, v) {
          //获取数据
          var d_id = v.id,
            d_name = v.name,
            d_price = v.price;

          //输出HTML元素
          tableBodyHtml += '<tr>';
          tableBodyHtml += '<td><label class="checkbox-inline i-checks"><input type="checkbox" name="ckbox" class="sub_ckbox"></label></td>';
          tableBodyHtml += '<td>' + d_id + '</td>';
          tableBodyHtml += '<td><a href="#">' + d_name + '</a></td>>';
          tableBodyHtml += '<td>' + d_price + '</td>';
          tableBodyHtml += '</tr>';
        });
        _table.find("tbody").html(tableBodyHtml);

        //复选框效果
        chkFun();

        //分页
        //try {
        var $tcdPage = $(".tcdPageCode");
        $tcdPage.createPage({
          pageCount: 10,
          current: 1,
          backFn: function (p) {
            //点击分页事件
          }
        });
        /*} catch (e) {
         }*/
      }
    });
  }



  /***
   *功能说明：复选框、单选框美化
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
  function chkFun() {
    $(".i-checks").iCheck({
      checkboxClass: "icheckbox_square-green",
      radioClass: "iradio_square-green"
    });
    //全选、反选
    var _jCheckAll = $("#jCheckAll"),
      _subCheck = $('input[type="checkbox"].sub_ckbox');
    _jCheckAll.on('ifChecked', function () {
      _subCheck.iCheck('check');
    });
    _jCheckAll.on('ifUnchecked', function () {
      _subCheck.iCheck('uncheck');
    });
  }


  /***
   *功能说明：时间插件
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
  var start = {
    elem: "#start", format: "YYYY/MM/DD", istime: false, istoday: false, choose: function (datas) {
      end.min = datas;
      end.start = datas
    }
  };
  var end = {
    elem: "#end", format: "YYYY/MM/DD", istime: false, istoday: false, choose: function (datas) {
      start.max = datas
    }
  };
  try {
    laydate(start);
    laydate(end);
  } catch (e) {
  }

});