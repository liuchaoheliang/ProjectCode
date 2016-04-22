/*
* Author:
* pengling@f-road.com.cn 
*/
/*积分展示显示*/
var ashow = document.getElementById("ashowlist").getElementsByTagName('dt');
    for (var i=0; i<ashow.length; i++){
	  ashow[i].style.display = "none";
 }	
 

var alink = document.getElementById("ashowlist").getElementsByTagName('a')
for(i=0; i<alink.length; i++){
    alink[i].onmouseover = function(){			
    for (var i=0; i<ashow.length; i++){
	   this.children[0].children[0].children[0].style.display = "block";
       }	
     
	}
}


for(i=0; i<alink.length; i++){
    alink[i].onmouseout = function(){
	   for (var i=0; i<ashow.length; i++){
	   this.children[0].children[0].children[0].style.display = "none";
           }				

	}
}

	

	
	
	
	
