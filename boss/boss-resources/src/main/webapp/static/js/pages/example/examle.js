/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
var _pages;  //定义分页变量
$(function () {

  /***
   *功能说明：表格相关操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-29
   ***/
  var _table = $('#table');
  _table.bootstrapTable();   //美化表格
  chkFun();   //复选框操作
  $(window).resize(function () {  //实时变换窗口大小时复选框问题处理
    chkFun();
  });
  var srhData = {"pageNo":"1","pageSize":"10"};  //传递分页数据
  tableFun("../static/data/example.txt", srhData);  //加载表格数据
  myPage();  //分页


  /***
   *功能说明：查询操作
   *参数说明：
   *创建人：李波涛
   *时间：2016-08-01
   ***/
  var _srhBtn = $("#srhBtn"),
    _srhPrmtxt = $(".srh_prmtxt");
  _srhBtn.click(function () {    //查询按钮提交操作
    var srhId = $("#srhId").val(),
      srhName = $("#srhName").val();
    if(!srhId){
      _srhPrmtxt.html("名称不能为空");
      return false;
    }
    var srhData = {"id":srhId,"name":srhName};  //传递数据
    tableFun("../static/data/example.txt", srhData); //加载表格数据
    myPage();  //分页
  });

  /***
   *功能说明：时间插件
   *参数说明：elem 时间文本框ID  format时间格式
   *创建人：李波涛
   *时间：2016-07-15
   ***/

  //单个时间
  //laydate({elem: "#date1", format: "YYYY/MM/DD hh:mm:ss"});

  //时间段
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
  } catch (e) {}


});


  /***
   *功能说明：表格数据及分页  注： 为传递分页，需要同步提交
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/
  var tableFun = function(tdUrl, tbData) {
    $.ajax({
      type: "POST",
      url: tdUrl,
      data: tbData,
      async : false,  //同步提交
      dataType: "json",
      success: function (data) {
        var _table = $('#table'),
          tableBodyHtml = '';
        _pages = data.pages; //分页总数
        var _list = data.list;  //列表数据
        $.each(_list, function (k, v) {
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
      }
    });
  };



/***
 *功能说明：分页
 *参数说明：pageCount  总页数  current 默认当前页   srhData 需传递参数  tableFun  追加数据函数
 *创建人：李波涛
 *时间：2016-07-15
 ***/
var myPage = function(){
  var $tcdPage = $(".tcdPageCode");
  $tcdPage.createPage({
    pageCount: _pages,
    current: 1,
    backFn: function (p) {
      //点击分页事件
      var srhData = {"pageNo":p,"pageSize":"5"};
      tableFun("../static/data/example.txt", srhData);

    }
  });
};


  /***
   *功能说明：复选框、单选框美化
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
  var chkFun = function() {
    $(".i-checks").iCheck({
      checkboxClass: "icheckbox_square-green",
      radioClass: "iradio_square-green"
    });
    //全选、反选
    var _jCheckAll = $("#jCheckAll"),    //全选复选框ID
      _subCheck = $('input[type="checkbox"].sub_ckbox');  //要选择的全部复选框数组
    _jCheckAll.on('ifChecked', function () {
      _subCheck.iCheck('check');
    });
    _jCheckAll.on('ifUnchecked', function () {
      _subCheck.iCheck('uncheck');
    });
  };