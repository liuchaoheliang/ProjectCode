/*
* Author:
* pengling@f-road.com.cn 
*/
/*tab切换*/
var tabThree = function(e) {
	return document.getElementById(e);
	}
function showRightDiv(parm)
{
tabThree('divTab01').style.display='none';
tabThree('divTab02').style.display='none';
tabThree('divTab03').style.display='none';
tabThree(parm).style.display = '';
for(var i in tabThree('dtMenu').getElementsByTagName('a')){
tabThree('dtMenu').getElementsByTagName('a')[i].className = 'playout';
}
}
