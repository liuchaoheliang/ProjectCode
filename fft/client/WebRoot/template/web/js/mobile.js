//表单验证
$(function(){
		//如果是必填的，则加红星标识.
		$("#mobile1").each(function(){
			var $required = $("<strong class='redFont'> *</strong>"); //创建元素
			$(this).parent().append($required); //然后将它追加到文档中
		});
		$("#mobile2").each(function(){
			var $required = $("<strong class='redFont'> *</strong>"); //创建元素
			$(this).parent().append($required); //然后将它追加到文档中
		});
		
		var wText = [
		'请输入11位手机号码',
		'两次输入的手机号码不一样',
		]
		
		var rText = '输入成功'
	
         //文本框失去焦点后
	    $('form :input').blur(function(){
			 var $parent = $(this).parent();
			 $parent.find(".formtips").remove();
			 
			 //手机号1
			 if( $(this).is('#mobile1') ){				 
					if( this.value=="" || this.value.length != 11 ){
						$(this).addClass("errorText");
					    var errorMsg = wText[0];
                        $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					}else{
						$(this).removeClass("errorText");
					    var okMsg = rText;
					    $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					}
			 }
			 
            //手机号2
			 if( $(this).is('#mobile2') ){				 
					if( this.value=="" || this.value.length != 11 ){
						$(this).addClass("errorText");
					    var errorMsg = wText[0];
                        $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					}else{
						$(this).removeClass("errorText");
					    var okMsg = rText;
					    $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					}
			 }
			 			 
	 
			 
		}).keyup(function(){
		   $(this).triggerHandler("blur");
		}).focus(function(){
	  	   $(this).triggerHandler("blur");
		});//end blur
		
		//提交，最终验证。
		 $('#send').click(function(){
				$("form :input.required").trigger('blur');
				var numError = $('form .onError').length;
				if(numError){
					return false;
				} 
				alert("请把信息填写正确");
		 });
 })