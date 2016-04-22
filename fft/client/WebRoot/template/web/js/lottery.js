/*
* Author:
* pengling@f-road.com.cn 
*/
//双色球


//选择红球
var arr = [],
abb = [],
add = []
var redball = document.getElementById('redbox');
var orLi = redball.getElementsByTagName('li')

for (var i = 0; i < orLi.length; i++) {
	orLi[i].odds = i;
	orLi[i].onclick = function() {
		for (var i = 0; i < orLi.length; i++) {
			if (this.className == '') {
				this.className = 'redclick';
			} else {
				this.className = '';
			}
		}

		var aRed = [];
		if (aRed.length == 0) {
			document.getElementById("rednumber").innerHTML = '0';
		}
		var aRed = getByClass(redball, 'redclick');
		for (var i = 0; i < aRed.length; i++) {
			if (aRed.length > 6) {

				this.className = '';
				document.getElementById("rednumber").innerHTML = aRed.length - 1
			}

			else {
				document.getElementById("rednumber").innerHTML = aRed.length
			}
		}
	}

}

//选择蓝球
var blueball = document.getElementById('bluebox');
var obLi = blueball.getElementsByTagName('li');
for (var k = 0; k < obLi.length; k++) {
	obLi[k].oindex = k;
	obLi[k].onclick = function() {
		for (var i = 0; i < obLi.length; i++) {

			obLi[i].className = '';

		}
		this.className = 'blueclick'
	}
}

//添加到投注
function newBall() {
	if (document.getElementById("rednumber").innerHTML == 0) {
		var message="您必须选择 <b>6</b> 个红球";
		document.getElementById("ball").innerHTML = ''
		
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:message,type : 8}	
			});
			
		return;
	}

	var aRed = getByClass(redball, 'redclick');
	for (var i = 0; i < aRed.length; i++) {
		if (aRed[i].className == 'redclick' && aRed.length == 6) {
			//arr[i] = parseInt(new Number(aRed[i].innerHTML));
			arr[i] = parseInt(aRed[i].innerHTML,10);
		} else {
			var message="您只选择了 <b>" + aRed.length + "</b> 个红球,必须选择 <b> 6 </b> 个红球";
			document.getElementById("ball").innerHTML = ''
				$.layer({
					title:['分分通提示您',true],
					time:3,
					area : ['auto','auto'],
					dialog : {msg:message,type : 8}	
				});
			return;
		}
	}

	arr.sort(function(n1, n2) {
		return n1 - n2;
	})

	var aBlue = getByClass(blueball, 'blueclick');
	for (var i = 0; i < aBlue.length; i++) {
		if (aBlue[i].className == 'blueclick' && aBlue.length == 1) {
			//abb[i] = parseInt(new Number(aBlue[i].innerHTML))
			abb[i] = parseInt(aBlue[i].innerHTML,10)
		}
	}

	var totlenum = 0

	totlenum = parseInt(arr.length) + parseInt(abb.length);

	if (totlenum == 7) {
         document.getElementById("choseRedBall").value = arr
		 document.getElementById("blueB").value = abb 
		 document.getElementById("ball").innerHTML = '<a href="javascript:void(0)" onclick="clearBall()">清空</a>'
		//document.getElementById("ball").innerHTML = '<u>红球：</u>' + '<u>' + arr + '</u>' + '<span>蓝球：</span>' + abb + '<a href="javascript:void(0)" onclick="clearBall()">清空</a>'
	} else if (totlenum == 6) {
		var message="您必须选择 <b>1</b> 个蓝球";
		document.getElementById("ball").innerHTML = ''
			
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:message,type : 8}	
			});
			
		return;
	} else if (totlenum == 1) {
		
		var message="您必须选择 <b>6</b> 个红球";
		
		document.getElementById("ball").innerHTML = ''
			
			$.layer({
				title:['分分通提示您',true],
				time:3,
				area : ['auto','auto'],
				dialog : {msg:message,type : 8}	
			});
		return;
	}

}

//随机红球
function redRandom() {
	var reds = new Array(6);
	var redPool = new Array(33);
	var boolean = new Array(33);
	for (var i = 0; i < orLi.length; i++) {
		orLi[i].className = '';
	}

	for (var i = 0; i < redPool.length; i++) {
		redPool[i] = i + 1;
	}
	for (var j = 0; j < boolean.length; j++) {
		boolean[j] = 0;
	}
	for (var a = 0; a < 6; a++) {
		var random = 0;
		do {
			random = getRandom(0, 32);
		} while ( boolean [ random ] == 1) boolean[random] = 1;
		reds[a] = redPool[random];
	}
	reds.sort(function(n1, n2) {
		return n1 - n2;
	});

	var ai = 0,
	bi = 0;
	while (ai < reds.length && bi < orLi.length) {
		if (reds[ai] < orLi[bi]) {
			ai++;
		} else if (reds[ai] > orLi[bi]) {
			bi++;
		} else
		/* they're equal */
		{
			add[ai] = reds[ai];
			ai++;
			bi++;
		}
	}

	for (var i = 0; i < orLi.length; i++) {
		for (var j = 0; j < add.length; j++) {

			if (add[j] == orLi[i].innerHTML || add[j] == orLi[i].innerText) {
				orLi[i].className = 'redclick';

			}

		}
	}

	var aRed = getByClass(redball, 'redclick');
	for (var i = 0; i < aRed.length; i++) {
		if (aRed.length = 6) {
			document.getElementById("rednumber").innerHTML = aRed.length
		}
	}

}

//随机蓝球
function blueRandom() {
	var blue = 0;
	blue = getRandom(1, 16);

	//alert(blue);
	for (var i = 0; i < obLi.length; i++) {
		if (blue == obLi[i].innerHTML || blue == obLi[i].innerText ) {
			obLi[i].className = 'blueclick';
		} else {
			obLi[i].className = '';
		}
	}

}

//随机方法
function getRandom(min, max) {
	var r = Math.random();
	return Math.floor(r * (max - min + 0.5) + min);
}

//重新选择
function newSelect() {
	for (var i = 0; i < orLi.length; i++) {
		orLi[i].className = '';
	}

	document.getElementById("rednumber").innerHTML = 0

}

//清空
function clearBall() {

	         document.getElementById("choseRedBall").value = ''
		 document.getElementById("blueB").value = '' 

}


