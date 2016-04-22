/*
* Author:
* pengling@f-road.com.cn 
*/
//双色球

var redball = $('#redbox a');
    redball.click(function(){
		
		alert($(this).html())
	    if ($(this).className == ''){
			$(this).className = 'redclick';		
			}
			
			else{
				$(this).className = '';
				}
	  
	  
	  });