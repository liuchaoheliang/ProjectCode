/*
* Author:
* pengling@f-road.com.cn 
*/

/* 搜索文本框效果 */
$(function(){
	$("#searchWord").focus(function(){
		  if($(this).val() ==this.defaultValue){  
			  $(this).val("");           
		  } 
	}).blur(function(){
		 if ($(this).val() == '') {
			$(this).val(this.defaultValue);
		 }
	})
})