/*
* Author:
* pengling@f-road.com.cn 
*/
//首页图片导航效果

var menu = document.getElementById("indexmenu");
var oA = menu.getElementsByTagName('a');

for(i=0; i<oA.length; i++){
	 oA[i].index = i
	 oA[i].onmouseover= function ()
	{
		for(i=0; i<oA.length; i++){
		oA[i].style.filter = 'alpha(opacity:30)';
		oA[i].style.opacity= 0.3;
			
	};
	
	 this.style.filter = 'alpha(opacity:100)';
	 this.style.opacity= 1;
	
  }
  
  	 oA[i].onmouseout=function ()
	{
       for(i=0; i<oA.length; i++){
		oA[i].style.filter = 'alpha(opacity:100)';
		oA[i].style.opacity= 1;
	};
	 this.style.filter = 'alpha(opacity:50)';
	 this.style.opacity= 0.5;
	
	};		
		
}



