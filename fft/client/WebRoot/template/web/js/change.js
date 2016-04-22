/*
* Author:
* pengling@f-road.com.cn 
*/

/*换一批*/

var dlchange = document.getElementById("indexChangeMerchantDetail");
var changebtn = document.getElementById("changebtn");
changebtn.onclick =  function(){
	var odl = dlchange.children[0];
	var od2 = dlchange.children[1];
	dlchange.appendChild(odl);
	dlchange.appendChild(od2);
}
