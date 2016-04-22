/*
* Author:
* pengling@f-road.com.cn 
*/
//导航点击效果
var aLinks=document.getElementById("menuAClick").getElementsByTagName('a'); 

  for (var i=0; i<aLinks.length; i++){  
    [i].index = i;
    aLinks[i].onclick = function()
	{
		for(var i=0; i<aLinks.length; i++)
			{
			aLinks[i].className = ''
					
			}
			this.className = 'menuClick'	
	}
  } 




