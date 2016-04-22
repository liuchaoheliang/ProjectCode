/*
* Author:
* pengling@f-road.com.cn 
*/

(function($){
	$.fn.tableUI = function(options){
		var defaults = {
			tableodd: 'tableoddcolor',
			tableeven:'tableevencolor',
			tablehover:'tablehovercolor',
			tableclick:'tableclickcolor'
		};	
					
		var options = $.extend({},defaults, options);
		this.each(function(){
			var thisTable=$(this);
			//添加奇偶行颜色
			thisTable.find('tr:odd').addClass(options.tableodd);
			thisTable.find('tr:even').addClass(options.tableeven);	

			//添加hover颜色			
			thisTable.find('tr').hover(function(){
				$(this).addClass(options.tablehover);				
				},function(){				
				$(this).removeClass(options.tablehover)
				});		
			//添加点击颜色					
			thisTable.find('tr').bind('click', function(){	
				$(this).toggleClass(options.tableclick);			
				});	
					
			});

		}		
})(jQuery);