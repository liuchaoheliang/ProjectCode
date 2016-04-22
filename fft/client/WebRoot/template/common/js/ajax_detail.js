/***
 *	HongHaoSoft Product JavaScript
 *
 *	http://www.honghaosoft.com
 *
 *	Copyright (c) 2008 HongHaoSoft. All rights reserved.
 **/

$( function() {

	$.ajaxlinkDetail = function(u) {
		var ret = '';
    	$.ajax({
        	url: u,
        	async:false,
        	dataType: "html",
        	success: function(data) {
    			$("body").prepend(data);
				$("#ajaxLinkWindow").jqm({
					modal: true,// 是否开启模态窗口
					overlay: 30,// 屏蔽层透明度
					closeClass: "ajaxLinkWindowClose",// 关闭按钮
					trigger: false,
					onHide: function(hash) {
		    			hash.o.remove();
		    			hash.w.fadeOut();
		    			$("#ajaxLinkWindow").remove();
		    		},
		    		onShow: function(hash){
		    			hash.w.fadeIn();
			    	}
				}).jqDrag();
				
				$("#ajaxLinkWindow").jqmShow();
				
        	}
    	});
	};
	
	$('a.ajaxlinkDetail').click(function() {
		if ($(this).attr('no') == 'yes')
			return false;
		var link = $(this).attr('href');
		
		if(link.indexOf('?')<0){
			link = link+"?timestamp=" + (new Date()).valueOf();
		}else{
			link = link+"&timestamp=" + (new Date()).valueOf();
		}
		$.ajaxlinkDetail(link);
		return false;
    });
	
});