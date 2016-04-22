/*
* Author:
* pengling@f-road.com.cn 
*/

(function($) {
	$.fn.borderUI = function(options) {
		this.each(function() {
			var thisBorder = $(this);
			thisBorder.find("li").hover(function() {
				$(this).css({
					"border-color": "#206fff",
					"background-color": "#ddf4ff"
				})
			},
			function() {
				$(this).css({
					"border-color": "#ddd",
					"background-color": "#f3f5f7"
				})
			})

		});

	}
})(jQuery);