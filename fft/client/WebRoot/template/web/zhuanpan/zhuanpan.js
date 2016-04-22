/*
* Author:
* pengling@f-road.com.cn 
*/
//转盘抽奖验证
$(document).ready(function (){
	
	var check = [
	       	  /^[\u4e00-\u9fa5]+$/,             /*1姓名*/
	       	  /^1[3458][0-9]{9}$/,
	       	  /^[\s\S]+$/  //*3验证银行卡为16-19位数字*/
	       	  ];
	       	  
	       $(function(){
	       	var thisId = null;
	       	var checkIndex = 0;
	       	$("#formlist input").live('focus',function(){
	       		thisId = $(this).attr('id');
	       		$(this).addClass("focusText");
	       		 if($(this).val() == this.defaultValue){  
	       			  $(this).val("");  
	       			  $(this).css("color","#047ebe")         
	       		  } 

	       	}).live('blur',function(){
	       		if(thisId == 'userName'){
	       			checkIndex = 0;
	       		}else if(thisId == 'userbankCard'){
	       			checkIndex = 1;
	       		}else{
	       			checkIndex = 2;
	       		}
	       		
	       		$(this).removeClass("focusText");
	       		var spanright = $("<span><h2>输入成功</h2></span>");
	       		var spanwrong = $("<span><h3>输入错误</h3></span>");
	       		var spanerror = $("<span><h3>不能为空</h3></span>");
	       		$($(this).parent().find("span").remove())			
	       		 if ($(this).val() == '') {
	       			 $(this).val(this.defaultValue);
	       			 $(this).css("color","#999")
	       			 $(this).parent().append(spanerror)	
	       			 $(this).addClass("errorText")		 
	       		 }		 
	       		 else if(check[checkIndex].test(this.value)){
	       			$(this).parent().append(spanright)
	       			$(this).removeClass("errorText")
	       			 }		
	       		else{
	       			$(this).parent().append(spanwrong)
	       			$(this).addClass("errorText")
	       			}
	       	})
	       	
	       })

	       //最终提交验证
	       $(function(){
	         $("#send").click(function(){ 
	           var spanerror = $("<span><h3>不能为空</h3></span>");
	       	$("input[type='text']").each(function(){
	       		 if($(this).val() == this.defaultValue){
	       			 $(this).parent().find("span").remove()
	       			 $(this).parent().append(spanerror)	
	       			 $(this).addClass("errorText");	
	       			 return false;
	       			}
	       	   })
	       	 var subm = $('h3').length;  	
	       	  if(subm){
	       			return false;
	       		}  
	       		else{
	       						
	       		}
	       	})	
	       })
})
 