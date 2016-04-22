/***
* FQ
 **/

$().ready(function() {

	// 添加团购商品浏览记录
	var maxGroupGoodsHistoryListCount = 6; // 最大团购商品浏览记录数
	
	/**
	 * linkUrl      点击链接
	 * name			名称
	 * groupPrice   团购价
	 * imageUrl     图片路径
	 */
	$.addGroupGoodsHistory = function(linkUrl,name,groupPrice,imageUrl) {
		var groupGoodsHistory = {
			linkUrl:linkUrl,
			name: name,
			groupPrice: groupPrice,
			imageUrl:imageUrl
		};
		var groupGoodsHistoryArray = new Array();
		var groupGoodsHistoryListCookie = $.cookie("groupGoodsHistoryList");
		if(groupGoodsHistoryListCookie) {
			groupGoodsHistoryArray = eval(groupGoodsHistoryListCookie);
		}
		var groupGoodsHistoryListHtml = "";
		for (var i in groupGoodsHistoryArray) {
			groupGoodsHistoryListHtml += '<dd> <a href="'+groupGoodsHistoryArray[i].linkUrl+'"><img src="'+groupGoodsHistoryArray[i].imageUrl+'" /></a><div class="inforPrice"> <b>￥'+groupGoodsHistoryArray[i].groupPrice+'</b><p>'+groupGoodsHistoryArray[i].name+'</p></div></dd>';
		}
		for (var i in groupGoodsHistoryArray) {
			if(groupGoodsHistoryArray[i].linkUrl == linkUrl) {
				return;
			}
		}
		
		//超过记录数 移除最前一个元素
		if(groupGoodsHistoryArray.length >= maxGroupGoodsHistoryListCount) {
			groupGoodsHistoryArray.shift();
		}
		
		//将一个新元素添加到数组结尾
		groupGoodsHistoryArray.push(groupGoodsHistory);

		var list = new Array();
		
		for(var i = 0 ; i < groupGoodsHistoryArray.length; i ++){
			list[groupGoodsHistoryArray.length - i -1] = groupGoodsHistoryArray[i];
		}
		
		groupGoodsHistoryArray = list;
		var newGroupGoodsHistoryCookieString = "";
		for (var i in groupGoodsHistoryArray) {
			newGroupGoodsHistoryCookieString += ',{linkUrl: "' + groupGoodsHistoryArray[i].linkUrl + '", name: "' + groupGoodsHistoryArray[i].name + '",groupPrice: "' + groupGoodsHistoryArray[i].groupPrice + '",  imageUrl: "' + groupGoodsHistoryArray[i].imageUrl + '"}'
		}
		newGroupGoodsHistoryCookieString = "[" + newGroupGoodsHistoryCookieString.substring(1, newGroupGoodsHistoryCookieString.length) + "]";
		$.cookie("groupGoodsHistoryList", newGroupGoodsHistoryCookieString, {path: "/"});
	}
	
	// 团购商品浏览记录列表
	var groupGoodsHistoryArray = new Array();
	var groupGoodsHistoryListCookie = $.cookie("groupGoodsHistoryList");
	if(groupGoodsHistoryListCookie) {
		groupGoodsHistoryArray = eval(groupGoodsHistoryListCookie);
	}
	var groupGoodsHistoryListHtml = "";
	for (var i in groupGoodsHistoryArray) {
		groupGoodsHistoryListHtml += '<dd> <a href="'+groupGoodsHistoryArray[i].linkUrl+'"><img src="'+groupGoodsHistoryArray[i].imageUrl+'" /></a><div class="inforPrice"> <b>￥'+groupGoodsHistoryArray[i].groupPrice+'</b><p>'+groupGoodsHistoryArray[i].name+'</p></div></dd>';
	}
	$("#groupGoodsHistoryListDetail").html(groupGoodsHistoryListHtml);

		
});