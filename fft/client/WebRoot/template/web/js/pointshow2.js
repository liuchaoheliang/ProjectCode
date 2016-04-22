/*
* Author:
* pengling@f-road.com.cn 
*/
/*积分展示显示*/
var $listid = $(".list dt");

$listid.hide();

var $lista = $(".list a");

  $lista.hover(function(){
	 $(this).find("dt").stop(true,true).slideDown(); 
	},function(){
	 $(this).find("dt").slideUp(100);
	});


	
	
	
	
