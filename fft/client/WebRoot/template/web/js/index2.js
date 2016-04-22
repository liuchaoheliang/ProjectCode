/*
* Author:
* pengling@f-road.com.cn 
*/
//首页淡入淡出
$(function(){
	var $selectImg =  $(".indexMenu img");
    $selectImg.hover(function(){
	var thissrc = $(this).attr("src");	
		switch (thissrc) {
			case "images/men01.png":
			$(this).attr("src","images/men10.png");
			break;
			
			case "images/men02.png":
			$(this).attr("src","images/men11.png");
			break;
			
			case "images/men03.png":
			$(this).attr("src","images/men12.png");
			break;

			case "images/men04.png":
			$(this).attr("src","images/men13.png");
			break;	
			
			case "images/men05.png":
			$(this).attr("src","images/men14.png");
			break;
			
			case "images/men06.png":
			$(this).attr("src","images/men15.png");
			break;														
						
			}
		$selectImg.not($(this)).stop(true,true).animate({
		filter:'Alpha(opacity:'+50+')',		
		opacity: '0.5'
		},500);
		},function(){
		var thissrc = $(this).attr("src");	
		switch (thissrc) {
			case "images/men10.png":
			$(this).attr("src","images/men01.png");
			break;
			
			case "images/men11.png":
			$(this).attr("src","images/men02.png");
			break;
			
			case "images/men12.png":
			$(this).attr("src","images/men03.png");
			break;	
			
			case "images/men13.png":
			$(this).attr("src","images/men04.png");
			break;
			
			case "images/men14.png":
			$(this).attr("src","images/men05.png");
			break;															
			
			case "images/men15.png":
			$(this).attr("src","images/men06.png");
			break;		
			}	
				
		$selectImg.not($(this)).stop(true,true).animate({
		filter:'Alpha(opacity:'+100+')'	,	
		opacity: '1'
		},500);
	})
});
