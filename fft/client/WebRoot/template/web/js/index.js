/*
* Author:
* pengling@f-road.com.cn 
*/
//首页淡入淡出
$(function(){	
	
	var $selectImg =  $(".indexMenu img");
	var srcOld = ["men01.png","men02.png","men03.png","men04.png","men05.png","men06.png"];
	var srcNew = ["men10.png","men11.png","men12.png","men13.png","men14.png","men15.png"];
	
	$selectImg.hover(function(){		
		var thissrc = $(this).attr("src");		
		for(var i = 0 ; i < srcOld.length; i ++){			
			if(thissrc.indexOf(srcOld[i]) != -1){
				index = i;
				$(this).attr('src',thissrc.replace(srcOld[i],srcNew[i]));
				break;
			}
		}	
		$selectImg.not($(this)).stop(true,true).animate({
			filter:'Alpha(opacity:'+50+')',		
			opacity: '0.5'
		},500);
    },function(){
		var thissrc = $(this).attr("src");		
		for(var i = 0 ; i < srcOld.length; i ++){			
			if(thissrc.indexOf(srcNew[i]) != -1){
				index = i;
				$(this).attr('src',thissrc.replace(srcNew[i],srcOld[i]));
				break;
			}
		}
		$selectImg.not($(this)).stop(true,true).animate({
			filter:'Alpha(opacity:'+100+')'	,	
			opacity: '1'
		},500);
	});	
});
