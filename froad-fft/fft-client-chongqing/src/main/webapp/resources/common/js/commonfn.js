/**
 * @author zhaoxiaoyao@f-road.com.cn
 * @desc 公共常用方法
 */
// 添加Cookie
function addCookie(name, value, options) {
	if (arguments.length > 1 && name != null) {
		if (options == null) {
			options = {};
		}
		if (value == null) {
			options.expires = -1;
		}
		if (typeof options.expires == "number") {
			var time = options.expires;
			var expires = options.expires = new Date();
			expires.setTime(expires.getTime() + time * 1000);
		}
		document.cookie = encodeURIComponent(String(name))
				+ "="
				+ encodeURIComponent(String(value))
				+ (options.expires ? "; expires="
						+ options.expires.toUTCString() : "")
				+ (options.path ? "; path=" + options.path : "")
				+ (options.domain ? "; domain=" + options.domain : ""),
				(options.secure ? "; secure" : "");
	}
}
// 获取Cookie
function getCookie(name) {
	if (name != null) {
		var value = new RegExp("(?:^|; )" + encodeURIComponent(String(name))
				+ "=([^;]*)").exec(document.cookie);
		return value ? decodeURIComponent(value[1]) : null;
	}
}
// 移除Cookie
function removeCookie(name, options) {
	addCookie(name, null, options);
}
// 关闭通用提示信息
function closed(){
	$("#show").slideUp(500);
}
function goBackPage(){
	 history.go(-1);
}
/**
 * 四舍五入保留两位小数
 * @param x
 * @returns {Number}
 */
function toDecimal(x) {
	var f = parseFloat(x);
	if (isNaN(f)) {
		return;
	}
	f = Math.round(x*100)/100;
	return f;
} 
