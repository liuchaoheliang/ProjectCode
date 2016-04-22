/*
* Author:
* pengling@f-road.com.cn 
*/
/*tab切换*/
var oDiv = document.getElementById('tab');
var oLink = oDiv.getElementsByTagName('li');
var eUl = oDiv.getElementsByTagName('ul');
for(var i =0; i<oLink.length; i++)
{
  oLink[i].odds = i;
  oLink[i].onclick = function()
  
  {
	  for(var i=0; i<oLink.length; i++)
	  
	  {
		oLink[i].className = '' 
		eUl[i].style.display = 'none'
		 
	  }
	  this.className = 'playRcut'; 
	  eUl[this.odds].style.display = 'block'
	  }	
}

