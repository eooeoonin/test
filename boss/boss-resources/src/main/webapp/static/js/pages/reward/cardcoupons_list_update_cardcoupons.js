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

var _id;var _proId;
$(function() {
    _id = Request.id;
    _proId = Request.projectId;
    //单个时间引用方式
    laydate({elem: "#endDate", format: "YYYY/MM/DD hh:mm:ss",istime: true});
    /**
     * 模态窗口非空/数字校验
     */
    _modalFm1 =  $('#formDp');
    _modalFm1.validationEngine('attach', {
        maxErrorsPerField:1,
        autoHidePrompt: true,
        autoHideDelay: 2000
    });
    //URL参数
    var id = Request.id;
    var tdUrl = "../reward/cardcoupons_list/getById";
    var tbData = {"id":_id};
    tableFun(tdUrl,tbData);
});


//编辑
function tableFun(tdUrl, tbData) {
    $.ajax({
        type : "POST",
        url : tdUrl,
        data : tbData,
        dataType : "json",
        success : function(data) {
            var start = new Date(data.endDate);
            var dateStrFrist = start.getFullYear() + '/' + (start.getMonth()+1) + '/' + start.getDate() + ' ' + start.getHours() + ':' + start.getMinutes() + ':' + start.getSeconds();
            //把商品数据,显示在也页面上
            document.getElementById("projectId").value = data.projectId;
            document.getElementById("cid").value = data.id;
            document.getElementById("aid").value = data.id;
            document.getElementById("cname").value = data.name;
            $("#type").val(""+data.type+"");
            document.getElementById("remark").value = data.remark;
            document.getElementById("value").value = data.value;
            document.getElementById("keyValue").value = data.keyValue;
            $("#payed").val(""+data.payed+"");
            document.getElementById("endDate").value = dateStrFrist;


            if(data.type == 'LOTTERY'){
                byId(data.id);
                getId(data.id);
                document.getElementById("tbo").style.display="";//显示
                document.getElementById("address").style.display="";//显示
            }if(data.type == 'VOUCHEROFFLINE'){
                getVoch(data.id);
                document.getElementById("tbootwo").style.display="";//显示
            }if(data.type == 'VOUCHER'){
                getVoch(data.id);
                document.getElementById("tbootwo").style.display="";//显示
            }if(data.type == 'VOUCHERH5'){
                getVoch(data.id);
                document.getElementById("tbootwo").style.display="";//显示
            }

            var _proId = data.projectId;
            _poolId = data.poolId;
            $.ajax({
                    type:"post",
                    data:{"projectId":_proId,"pageNo":"1","pageSize":"10"},
                    dataType:"json",
                    url:"../reward/project_list/jackpot/list",
                    success:function(data){
                        $.each(data.list,function(k,v){
                            var u_name = v.name;
                            u_id = v.id;
                            $("#poolId").append("<option value='"+u_id+"'>"+u_name+"</option>");
                            if(_poolId == u_id){
                                $("#poolId").val(""+u_id+"");
                            }
                        })
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(errorThrown);
                    },
                    async:false             //false表示同步
                }
            );

        }
    });
}


function getVoch(id){
    $.ajax({
        type: "POST",
        url:"../reward/cardcoupons_list/getVoch",
        data:{"id":id},
        error: function() {
        },
        success: function(data) {
            document.getElementById("voucherInfoId").value = data.id;
            document.getElementById("passWord").value = data.passWord;
            document.getElementById("hperLi").value = data.hperLink;
            document.getElementById("seneCode").value = data.seneCode;
        }
    });
}


var _dalist;
var _nameArray = new Array();
function byId(id){
    $.ajax({
        type: "POST",
        url:"../reward/cardcoupons_list/byId",
        data:{"id":id,"pageNo":"1","pageSize":100},
        error: function() {
        },
        success: function(data) {

            var _table = $('#table4');
            var len = 0;
            tableBodyHtml = '';
            $.each(data.list,function(k,v){
                _dalist = data.list.length;
                len = len +1;

                var u_id = v.id,
                    u_actCode = v.actCode,
                    u_chMini = v.chMini,
                    u_chMax = v.chMax;
                u_name = v.name;
                _nameArray[len] = u_name;

                tableBodyHtml += "<tr>";
                tableBodyHtml += "<td class='input-group-addon'> <em>*</em>抽奖券扩展信息</td>";
                tableBodyHtml += "<tr>";
                tableBodyHtml += "<tr>";
                tableBodyHtml += "<td class='input-group-addon'><em>*</em>奖品码</td>";
                tableBodyHtml += "<td>";
                tableBodyHtml += "<input class='form-control' name='lotteryChangesReqVo["+len+"].actCode' value="+u_actCode+">";
                tableBodyHtml += "</td>";
                tableBodyHtml += "</tr>";
                tableBodyHtml += "<tr>";
                tableBodyHtml += "<td class='input-group-addon' > <em>*</em>最小中奖概率</td>";
                tableBodyHtml += "<td>";
                tableBodyHtml += '<input class="form-control validate[required]" name="lotteryChangesReqVo['+len+'].chMini" id="chMini" value="' + u_chMini + '">';
                tableBodyHtml += '<input type="hidden" class="form-control validate[required]" name="lotteryChangesReqVo['+len+'].id"  value="' + u_id + '">';
                tableBodyHtml += "</td>";
                tableBodyHtml += "</tr>";
                tableBodyHtml += "<tr>";
                tableBodyHtml += "<td class='input-group-addon' > <em>*</em>最大中奖概率</td>";
                tableBodyHtml += "<td>";
                tableBodyHtml += '<input class="form-control validate[required]" name="lotteryChangesReqVo['+len+'].chMax" value="' + u_chMax + '">';
                tableBodyHtml += "</td>";
                tableBodyHtml += "</tr>";

            });
            _table.find("tbody").html(tableBodyHtml);
            replaceFun(_table);

            var _projeId = $("#projectId").val();
//              $.ajax({
//			        type:"post",
//			        data:{"projectId":_projeId,"pageNo":"1","pageSize":"10"},
//			        dataType:"json",
//			        url:"../reward/cardcoupons_list/list",
//			        success:function(data){
//			            $.each(data.list,function(k,v){
//			            	var u_names = v.name;
//			            		  u_ids = v.id;
//			            		  for (var i = 1; i <= _dalist; i++) {
//			            			  $("#select"+i+"").append("<option value='"+u_ids+"'>"+u_names+"</option>");
//			            			  $("#select"+i+"").val(""+_nameArray[i]+"");
//								}
//			            })
//			        },
//				      async:false        //false表示同步
//			    }
//			    );

        },
        async:false             //false表示同步
    });
}

function getId(id){
    $.ajax({
        type: "POST",
        url:"../reward/cardcoupons_list/getId",
        data:{"id":id},
        error: function() {
        },
        success: function(data) {
            document.getElementById("lotteryInfoId").value = data.id;
            document.getElementById("hperLink").value = data.hperLink;
        }
    });
}




$('#departmentSubmit').click(function(){
    if (!_modalFm1.validationEngine('validate')) {
        return false;
    }else{
        $("#type").removeAttr("disabled");
        var formdata = $('#formDp').serialize();
        $.ajax({
            type: "POST",
            url:"../reward/cardcoupons_list/update",
            data:formdata,
            error: function() {
                alert("修改失败");
                location = "../reward/cardcoupons_list.html";
            },
            success: function(data) {
                alert("修改成功");
                location = "../reward/cardcoupons_list.html";
            }
        });
    }
})

