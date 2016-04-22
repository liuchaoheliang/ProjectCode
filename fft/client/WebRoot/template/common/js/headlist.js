/*
* Author:
* pengling@f-road.com.cn 
*/
/*头部下拉框*/
$(document).ready(function(){
$(".arrowmember").live("mouseover",function(){
	$(".showmember").css("display", "block");
	$(this).css("border-color", "#ccc");	
	})
	
$(".arrowmember").live("mouseout",function(){
	$(".showmember").css("display", "none");
	$(this).css("border-color", "#FFF")	
	})
	
$(".showmember").live("mouseover",function(){
	$(this).css("display", "block")	
	$(".arrowmember").css("border-color", "#ccc");
	})
	
$(".showmember").live("mouseout",function(){
	$(this).css("display", "none")
	$(".arrowmember").css("border-color", "#FFF");	
	})
});