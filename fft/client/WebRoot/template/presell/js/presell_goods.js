/***
* FQ
 **/

$().ready(function() {

	// 添加团购商品浏览记录
	var maxPresellGoodsHistoryListCount = 6; // 最大预售商品浏览记录数
	
	/**
	 * linkUrl      点击链接
	 * name			名称
	 * groupPrice   团购价
	 * imageUrl     图片路径
	 */
	$.addPresellGoodsHistory = function(linkUrl,name,groupPrice,imageUrl) {
		var presellGoodsHistory = {
			linkUrl:linkUrl,
			name: name,
			groupPrice: groupPrice,
			imageUrl:imageUrl
		};
		var presellGoodsHistoryArray = new Array();
		var presellGoodsHistoryListCookie = $.cookie("presellGoodsHistoryList");
		if(presellGoodsHistoryListCookie) {
			presellGoodsHistoryArray = eval(presellGoodsHistoryListCookie);
		}
		var presellGoodsHistoryListHtml = "";
		for (var i in presellGoodsHistoryArray) {
			presellGoodsHistoryListHtml += '<dd> <a href="'+presellGoodsHistoryArray[i].linkUrl+'"><img src="'+presellGoodsHistoryArray[i].imageUrl+'" /></a><div class="inforPrice"> <b>￥'+presellGoodsHistoryArray[i].groupPrice+'</b><p>'+presellGoodsHistoryArray[i].name+'</p></div></dd>';
		}
		for (var i in presellGoodsHistoryArray) {
			if(presellGoodsHistoryArray[i].linkUrl == linkUrl) {
				return;
			}
		}
		
		//超过记录数 移除最前一个元素
		if(presellGoodsHistoryArray.length >= maxPresellGoodsHistoryListCount) {
			presellGoodsHistoryArray.shift();
		}
		
		//将一个新元素添加到数组结尾
		presellGoodsHistoryArray.push(presellGoodsHistory);

		var list = new Array();
		
		for(var i = 0 ; i < presellGoodsHistoryArray.length; i ++){
			list[presellGoodsHistoryArray.length - i -1] = presellGoodsHistoryArray[i];
		}
		
		presellGoodsHistoryArray = list;
		var newpresellGoodsHistoryCookieString = "";
		for (var i in presellGoodsHistoryArray) {
			newpresellGoodsHistoryCookieString += ',{linkUrl: "' + presellGoodsHistoryArray[i].linkUrl + '", name: "' + presellGoodsHistoryArray[i].name + '",groupPrice: "' + presellGoodsHistoryArray[i].groupPrice + '",  imageUrl: "' + presellGoodsHistoryArray[i].imageUrl + '"}'
		}
		newpresellGoodsHistoryCookieString = "[" + newpresellGoodsHistoryCookieString.substring(1, newpresellGoodsHistoryCookieString.length) + "]";
		$.cookie("presellGoodsHistoryList", newpresellGoodsHistoryCookieString, {path: "/"});
	}
	
	//预售商品浏览记录列表
	var presellGoodsHistoryArray = new Array();
	var presellGoodsHistoryListCookie = $.cookie("presellGoodsHistoryList");
	if(presellGoodsHistoryListCookie) {
        presellGoodsHistoryArray = eval(presellGoodsHistoryListCookie);
	}
	var presellGoodsHistoryListHtml = "";
	for (var i in presellGoodsHistoryArray) {
        presellGoodsHistoryListHtml += '<dd> <a href="'+presellGoodsHistoryArray[i].linkUrl+'"><img src="'+presellGoodsHistoryArray[i].imageUrl+'" /><div class="inforPrice"> <b>￥'+presellGoodsHistoryArray[i].groupPrice+'</b><p>'+presellGoodsHistoryArray[i].name+'</p></div></a></dd>';
	}
	$("#presellGoodsHistoryListDetail").html(presellGoodsHistoryListHtml);

		
});