/*$("#unlock").click(function(){
	$.ajax({
		type:'POST',
		url:'../user/user_list/selectAuthUserBuPhone',
		data:$('#unlockform').serialize(),
		dataType : "json",
		success:function(data){
			if(data == null){
				alert("手机号不存在");
			}
			if(data != null){
				alert("解绑成功");
			}
		},error : function(){
			alert("解绑失败");
		}
	})
 })*/
 
 
 
 function test(){
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	    if(!myreg.test($("#bankMobile").val())){ 
	        alert('请输入有效的手机号码！'); 
	        $("#bankMobile").val("").focus();   
	        return false; 
	    } else{
	    	$.ajax({
	    		type:'POST',
	    		url:'../user/user_list/selectAuthUserBuPhone',
	    		data:$('#unlockform').serialize(),
	    		dataType : "json",
	    		success:function(data){
	    			if(data == null){
	    				alert("手机号不存在");
	    			}
	    			if(data != null){
	    				alert("解绑成功");
	    			}
	    		},error : function(){
	    			alert("解绑失败");
	    		}
	    	})
	    }
}