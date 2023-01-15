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

  $.ajax({
    type: "POST",
    url: "/project/p_pro_revenue/show",
    success: function (data) {
      if (data.data != null && data.data != "") {
        var borrowCount = data.data.borrowCount ,
            historyCount = data.data.historyCount ,
            openCount = data.data.openCount ,
            disburseCount = data.data.disburseCount ,
            initCount = data.data.initCount ,
            waitCount = data.data.waitCount ,
            completedCount = data.data.completedCount;
      /*      allAmount = data.data.allAmount.amount ,
            monthAmount = data.data.monthAmount.amount ,
            futureAmount = data.data.futureAmount.amount ;*/
        $("#borrowCount").text( borrowCount );
        $("#historyCount").text( historyCount );
        $("#openCount").text( openCount );
        $("#disburseCount").text( disburseCount );
        $("#initCount").text( initCount );
        $("#waitCount").text( waitCount );
        $("#completedCount").text( completedCount );
/*        $("#allAmount").text( allAmount );
        $("#monthAmount").text( monthAmount );
        $("#futureAmount").text( futureAmount );*/
      }
    },
    async: false
  });


});