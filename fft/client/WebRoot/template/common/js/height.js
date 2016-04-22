try{
	var leftHeight = document.getElementById('leftHeight');
	var rightHeight = document.getElementById('rightHeight');
    var number = leftHeight.offsetHeight - rightHeight.offsetHeight ;	
	if(number>=0){
	rightHeight.style.height = rightHeight.offsetHeight + number + 'px';
	}
	}
    catch(e) {
	var middleHeight = document.getElementById('middleHeight');
	if(middleHeight.offsetHeight < window.innerHeight){		
	middleHeight.style.height = window.innerHeight - 285 + 'px'	
	}
}	


