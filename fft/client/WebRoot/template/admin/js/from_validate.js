//表单验证
$(function(){
		//如果是必填的，则加红星标识.
		$("form :text").each(function(){
			var $required = $("<strong class='red'> *</strong>"); //创建元素
			$(this).parent().append($required); //然后将它追加到文档中
		});
	
         //文本框失去焦点后
	    $('form :input').blur(function(){
			 var $parent = $(this).parent();
			 $parent.find(".formtips").remove();
			 //验证用户名
			 if( $(this).is('#mobile') ){				 
					if( this.value=="" || this.value.length != 11 ){
						$(this).addClass("errorText");
					    var errorMsg = '请输入11位手机号码';
                        $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
                        if($("#mobilecheckResult").length>0){
							$("#mobilecheckResult").val("false")
						}else{}
					}else{
						$(this).removeClass("errorText");
					    var okMsg = '输入成功';
					    $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					    if($("#mobilecheckResult").length>0){
							$("#mobilecheckResult").val("true")
						}else{}
					}
			 }
			 //验证物品名称
			 if( $(this).is('#name') ){
				if( this.value==""){
					  $(this).addClass("errorText");
                      var errorMsg = '请填写物品名称';
					  $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					  if($("#namecheckResult").length>0){
							$("#namecheckResult").val("false")
						}else{}
				}else{
					  $(this).removeClass("errorText");
                      var okMsg = '输入成功';
					  $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					  if($("#namecheckResult").length>0){
							$("#namecheckResult").val("true")
						}else{}
				}
			 }
			 //验证交易品数量
			if( $(this).is('#numeber') ){
				if( this.value=="" || (this.value!="" && !/^[0-9]*[1-9][0-9]*$/.test(this.value))){
					  $(this).addClass("errorText2");
                      var errorMsg = '请正确填写数量';
					  $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					  if($("#numebercheckResult").length>0){
							$("#numebercheckResult").val("false")
						}else{}
				}else{
					  $(this).removeClass("errorText2");
                      var okMsg = '输入成功';
					  $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					  if($("#numebercheckResult").length>0){
							$("#numebercheckResult").val("true")
						}else{}
				}
			 }

			 //验证价格
			if( $(this).is('#price') ){
				if( this.value=="" || (this.value!="" && ! /^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test(this.value))){
					  $(this).addClass("errorText2");
                      var errorMsg = '请输入正确的价格';
					  $parent.append('<span class="formtips onError">'+errorMsg+'</span>');
					  if($("#pricecheckResult").length>0){
							$("#pricecheckResult").val("false")
						}else{}
				}else{
					  $(this).removeClass("errorText2");
                      var okMsg = '输入成功';
					  $parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
					  if($("#pricecheckResult").length>0){
							$("#pricecheckResult").val("true")
						}else{}
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
				
				alert("请把信息填写正确"+numError);
		 });
 })