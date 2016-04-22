/**
*@author larry
*/
$(document).ready(function(){
	$("ul.mainfirst").find("u").each(function(i,e){
		if($(e).html()==document.title){
			$(e).parent().parent().addClass("current");
		}else{
			$(e).parent().parent().removeClass("current");
		}
	});
});