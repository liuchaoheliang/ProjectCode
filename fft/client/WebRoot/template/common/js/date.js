
if (document.getElementById("zyrq").value ==""){
	
	document.getElementById("zyrq").value=new Date().getFullYear()+'-'+('0'+(new Date().getMonth()+1))+'-'+new Date().getDate();
	}


if (document.getElementById("syrq").value ==""){
	
	document.getElementById("syrq").value=new Date().getFullYear()+'-'+('0'+(new Date().getMonth()+1))+'-'+(new Date().getDate()-3);
	}


