/*
* Author:
* pengling@f-road.com.cn 
*/

//数组删除重数字方法
Array.prototype.del = function() {
	var a = {},
	c = [],
	l = this.length;
	for (var i = 0; i < l; i++) {
		var b = this[i];
		var d = (typeof b) + b;
		if (a[d] === undefined) {
			c.push(b);
			a[d] = 1;
		}
	}
	return c;
}
//getClass方法
function getByClass(oParent, sClass) {
	var aResult = [];
	var aEle = oParent.getElementsByTagName('*');

	for (var i = 0; i < aEle.length; i++) {
		if (aEle[i].className == sClass) {
			aResult.push(aEle[i]);
		}
	}
	return aResult;
}