/**
*@author 赵荆州
*@date 2014-04-10
*@参数说明:
*必须保证元素，具有pk属性，pk为后台获取数据的唯一标识。
*/
(function($){
	$.fn.larryPresell=function(options){
		//基本参数
		var defaults = {
		};
		var options = $.extend({},defaults,options);
			return this.each(function(){
					 var presell = $(this);
				      var pk = $(presell).attr('pk');//主键唯一标示
				      var url = settings.baseUrl+"/shop/utils/get_presell_info.jhtml";
				      var params = {"productId":pk};
				      $.ajax({
				    	  url:url,
				    	  type:'POST',
				    	  data:params,
				    	  success:function(data){
				    		  	var presellStr = data.msg;
						        var presellArray = presellStr.split(':');
						        var buyer = parseInt(presellArray[0]);//已预订
						        var short = parseInt(presellArray[1]);//还差
						        $(presell).html(buyer);
						        if(short<=0){
						        	short = "√已成团";
						        	 $(presell).next().html(short);
						        	 $(presell).next().addClass("redFont");
						        	 return;
						        }
						        $(presell).next().find("font").html(short);
				    	  },
				    	  error:function(){
				    	  },
				    	  dataType:"json"
				      });
			});
	};
})(jQuery);