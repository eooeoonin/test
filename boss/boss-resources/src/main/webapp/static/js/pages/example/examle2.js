/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
  /***
   *功能说明：复选框、单选框美化
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
  $(".i-checks").iCheck({
    checkboxClass: "icheckbox_square-green",
    radioClass: "iradio_square-green"
  });

  /***
   *功能说明：时间插件
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-15
   ***/
  //单个时间引用方式
  laydate({elem: "#date1", format: "YYYY/MM/DD hh:mm:ss"});

  //时间段引用方式
  var start = {
    elem: "#start", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
      end.min = datas;
      end.start = datas
    }
  };
  var end = {
    elem: "#end", format: "YYYY/MM/DD hh:mm:ss", istime: true, istoday: false, choose: function (datas) {
      start.max = datas
    }
  };
  laydate(start);
  laydate(end);



  /***
   *功能说明：表单验证
   *参数说明：
   *创建人：李波涛
   *时间：2016-08-17
   ***/
  var _form = $('#form'),  //form表单ID
    _submitBtn = $("#submitBtn");  //提交表单按钮ID
  //表单元素验证
  _form.validationEngine('attach', {
    maxErrorsPerField: 1,
    autoHidePrompt: true,
    autoHideDelay: 2000
  });


  //表单提交
  _submitBtn.click(function () {
    //表单元素验证
    if (!_form.validationEngine('validate')) {
     return false;
     }
    //后台提交
    var formData = _form.serialize();
    //console.log(formData);
    $.ajax({
      url: '/upload',
      type: 'POST',
      data: formData,
      contentType: false,// 告诉jQuery不要去设置Content-Type请求头
      processData: false,// 告诉jQuery不要去处理发送的数据
      success: function (data) {
        if (data.result == 'success') {
          alert("提交成功");
        } else {
          alert("提交失败");
        }
      }
    });
  });

});
