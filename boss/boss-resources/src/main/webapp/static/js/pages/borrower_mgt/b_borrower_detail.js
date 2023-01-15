/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
  chkFun();
  $(window).resize(function () {
    chkFun();
  });

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
  
  


  //图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});
});