/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */

$(function () {
  //资产图片预览
  $(".fancybox").fancybox({openEffect: "none", closeEffect: "none"});

  //撤销资产-模态窗口
  $("#undoAssetsBtn").click(function () {
    $('#myModal').modal('show')
  });
});