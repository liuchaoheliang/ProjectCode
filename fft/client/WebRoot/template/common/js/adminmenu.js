/*
* Author:
* pengling@f-road.com.cn 
*/
/*adminmenu*/
/*var oA=document.getElementById("admin_menu").getElementsByTagName('u'); 

  for (var i=0; i<oA.length; i++){  
    [i].index = i;
    oA[i].onclick = function()
	{
		for(var i=0; i<oA.length; i++)
			{
			oA[i].className = ''
					
			}
			this.className = 'admincut'
			this.getElementsByTagName('a')[0].style.color = '#FFF'
	}
  } */
  
  
$('#admin_menu u').click(function(){	
	$('#admin_menu u').each(function(){
	if($(this).attr("class")){
		 $(this).removeClass("admincut");
		 $(this).find("a").css("color","#333")
		}
	})
	
		$(this).addClass("admincut")
		$(this).find("a").css("color","#FFF")
}) 