/*
* Author:
* pengling@f-road.com.cn 
*/
$(function(){
	$(".rightImg a").hover(function(){
	$(this).find("img").css({
		"border":"1px solid #088eec",
	})	
	$(this).siblings().stop(true,true).animate({
		filter:'Alpha(opacity:'+30+')',		
		opacity: '0.3'
		},500);
		},function(){
			
	$(this).find("img").css({
		"border":"1px solid #ececec",
	})		
		$(this).siblings().stop(true,true).animate({
		filter:'Alpha(opacity:'+100+')'	,	
		opacity: '1'
		},500);
	})
})
