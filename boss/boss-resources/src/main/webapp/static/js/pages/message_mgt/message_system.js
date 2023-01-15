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


  //分页
  var $tcdPage = $(".tcdPageCode");
  $tcdPage.createPage({
    pageCount: 10,
    current: 1,
    backFn: function (p) {
      //点击分页事件
      console.log(p);
    }
  });

  /***
   *功能说明：表格数据及分页
   *参数说明：tdUrl  -ajax接口地址  tbData －提交后台数据
   *创建人：李波涛
   *时间：2016-08-01
   ***/

});