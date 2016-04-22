<!--密码强度--> 
$(function(){
$("#password").keydown(function () {
 
　　if ($(this).val() != "") {
 
　　　　var strongRegex =
new RegExp("^(?=.{14,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");

 
　　　　var mediumRegex =
new RegExp
("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
 
　　　　var enoughRegex = new RegExp("(?=.{6,}).*", "g");
 
　　　　if (false == enoughRegex.test($(this).val())) {
　　　　　　//密码小于六位的时候，密码强度图片都为灰色
　　　　　　$(".pw-letter span").eq(0).addClass('tsl');
      　  $(".pw-letter span").eq(1).removeClass('tsl');
	      $(".pw-letter span").eq(2).removeClass('tsl');
　　　　}
　　　　else if (strongRegex.test($(this).val())) {
　　　　　//强,密码为14位及以上并且字母、数字、特殊字符三项中有两项，强度是强
　　　　　 $(".pw-letter span").eq(2).addClass('tsl');
         $(".pw-letter span").eq(0).removeClass('tsl');
         $(".pw-letter span").eq(1).removeClass('tsl');
　　　　}
　　　　else if (mediumRegex.test($(this).val())) {
　　　　　　//中等,密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等
　　　　　 $(".pw-letter span").eq(1).addClass('tsl');
         $(".pw-letter span").eq(2).removeClass('tsl');
         $(".pw-letter span").eq(0).removeClass('tsl');
　　　　}
　　　　else {
　　　　　　//弱,如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的
　　　　　　$(".pw-letter span").eq(0).addClass('tsl');
      　  $(".pw-letter span").eq(1).removeClass('tsl');
	      $(".pw-letter span").eq(2).removeClass('tsl');
　　　　}
                 
　　}
 
});	
	})