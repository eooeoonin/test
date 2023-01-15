/**
 * @author libotao
 * @date 2016/7/15
 * @version 1.0.0
 */
$(function () {
	
	  var d = new Date();
	  var dateStrFrist = d.getFullYear() + '/' + (d.getMonth()+1) + '/' + d.getDate() + ' ' + "0" + ':' + "0" + ':' + "0"; 
	  var dateStrLast = d.getFullYear() + '/' + (d.getMonth()+1) + '/' + d.getDate() + ' ' + "23" + ':' + "59" + ':' + "59"; 
	    
	  $.ajax({
			type : "POST",
			url : "../main/borrowAccount",
			data : {
					   "startTime":dateStrFrist,
					   "endTime":dateStrLast
					  },
			dataType: "json",
			success : function(data) {
				document.getElementById("borrowingPen").innerHTML = data.count;
				document.getElementById("totalborrowings").innerHTML = data.moneyCount.amount;
			},error : function(data){
			}
	  });
	  
	  
	  $.ajax({
			type : "POST",
			url : "../main/countRegisterUser",
			success : function(data) {
				document.getElementById("addLoginNumber").innerHTML = data;
			},error : function(data){
			}
	  });
	  
	  $.ajax({
			type : "POST",
			url : "../main/countLoginUser",
			success : function(data) {
				document.getElementById("loginNumber").innerHTML = data;
			},error : function(data){
			}
	  });
	  
	  $.ajax({
			type : "POST",
			url : "../main/countTodayWithdraw",
			success : function(data) {
				document.getElementById("numberofPresentpen").innerHTML = data;
			},error : function(data){
			}
	  });
	  
	  var date1 = new Date();
      var times1 = date1.getFullYear()+"/"+(date1.getMonth()+1)+"/"+date1.getDate();
      var date2 = new Date(date1);
      		date2.setDate(date1.getDate()-10);
      var times2 = date2.getFullYear()+"/"+(date2.getMonth()+1)+"/"+date2.getDate();
	  var arrDate = new Array();
	  var arrAccount = new Array();
	  var arrInvestCount = new Array();
	  $.ajax({
			type : "POST",
			data:{"startTime":times2,"endTime":times1},
			url : "../main/investorStatistics",
			success : function(data) {
				for (var int = 0; int < data.length; int++) {
					var date = new Date(data[int].date);
					arrDate[int] = (date.getMonth()+1)+"-"+date.getDate();
					arrAccount[int] = data[int].totalInvest.cent/10000;
					arrInvestCount[int] = data[int].investCount;
				}
			},error : function(data){
			},
			 async : false
	  });
	  
	  var arrBorowData = new Array();
	  var arrBorowCountMoney = new Array();
	  var arrBorowCount = new Array();
	  $.ajax({
			type : "POST",
			data:{"startTime":times2,"endTime":times1},
			url : "../main/borrowCountList",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var date = new Date(data[i].date);
					arrBorowData[i] = (date.getMonth()+1)+"-"+date.getDate();
					arrBorowCountMoney[i] = data[i].moneyCount.cent/10000;
					arrBorowCount[i] = data[i].count;
				}
			},error : function(data){
			},
			 async : false
	  });
	
  /***
   *功能说明：数据图表插件
   *参数说明：
   *创建人：李波涛
   *时间：2016-07-08
   ***/
  require.config({
    packages:[
      {
        name: 'echarts',
        location: 'static/js/plugins/echars/1.4.1/src',
        main: 'echarts'
      },
      {
        name: 'zrender',
        location: 'static/js/plugins/zrender/1.1.2/src',
        main: 'zrender'
      }
    ]});
  var option = {
    tooltip : {
      trigger: 'axis'
    },
    toolbox: {
      show : true,
      feature : {
        //mark : {show: true},
        dataView : {show: false, readOnly: false},
        magicType: {show: true, type: ['line', 'bar']},
        restore : {show: false},
        saveAsImage : {show: true}
      }
    },
    calculable : true,
    legend: {
      data:['借款笔数','借款金额']
    },
    xAxis : [
      {
        type : 'category',
        data : arrBorowData

      }
    ],
    yAxis : [
      {
        type : 'value',
        name : '笔数',
        axisLabel : {
          formatter: '{value} 笔'
        },
        splitArea : {show : true}
      },
      {
        type : 'value',
        name : '金额',
        axisLabel : {
          formatter: '{value} 万元'
        },
        splitLine : {show : false}
      }
    ],
    series : [

      {
        name:'借款笔数',
        type:'bar',
        data:arrBorowCount
      },
      {
        name:'借款金额',
        type:'line',
        yAxisIndex: 1,
        data:arrBorowCountMoney
      }
    ]
  };
  var option2 = {
    tooltip : {
      trigger: 'axis'
    },
    toolbox: {
      show : true,
      feature : {
        //mark : {show: true},
        dataView : {show: false, readOnly: false},
        magicType: {show: true, type: ['line', 'bar']},
        restore : {show: false},
        saveAsImage : {show: true}
      }
    },
    calculable : true,
    legend: {
      data:['出借笔数','出借金额']
    },
    xAxis : [
      {
        type : 'category',
        data : arrDate
      }
    ],
    yAxis : [
      {
        type : 'value',
        name : '笔数',
        axisLabel : {
          formatter: '{value} 笔'
        },
        splitArea : {show : true}
      },
      {
        type : 'value',
        name : '金额',
        axisLabel : {
          formatter: '{value} 万元'
        },
        splitLine : {show : false}
      }
    ],
    series : [

      {
        name:'出借笔数',
        type:'bar',
	        data:arrInvestCount
      },
      {
        name:'出借金额',
        type:'line',
        yAxisIndex: 1,
        data:arrAccount
      }
    ]
  };
  require(
    [
      'echarts',
      'echarts/chart/line',
      'echarts/chart/bar'
    ],
    function(ec) {
      var jkChart = ec.init(document.getElementById('jk-bar-chart'));
      var tzChart = ec.init(document.getElementById('tz-bar-chart'));
      jkChart.setOption(option);
      tzChart.setOption(option2);
    }
  );
  
});