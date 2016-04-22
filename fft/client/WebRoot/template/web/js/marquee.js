/*
* Author:
* pengling@f-road.com.cn 
*/

//图片滚动

window.onload = function(){

	var oMarquee = document.getElementById('marquee');
	var oUl = oMarquee.getElementsByTagName('ul')[0];
	var aLi = oUl.getElementsByTagName('li');	
	var speed = -2;
	
	oUl.innerHTML = oUl.innerHTML + oUl.innerHTML;
	//alert(oUl.innerHTML)	
	oUl.style.width = 215 * aLi.length + 'px';
	 function move(){
		if(oUl.offsetLeft < -oUl.offsetWidth/2){
			
		oUl.style.left = '0';
		}	
		if(oUl.offsetLeft > 0){
			
		oUl.style.left = -oUl.offsetWidth/2 + 'px';
		}
		
	oUl.style.left = oUl.offsetLeft +speed +'px'}
	var timer = setInterval(move ,30)

     oMarquee.onmouseover = function(){
		 clearInterval(timer)
		 }
     		 
   oMarquee.onmouseout = function(){
	 timer =  setInterval(move ,30);
	   };
	   
	document.getElementById('left').onmouseover=function ()
	{
		speed=-2;
	};
	document.getElementById('right').onmouseover=function ()
	{
		speed=2;
	};   
};