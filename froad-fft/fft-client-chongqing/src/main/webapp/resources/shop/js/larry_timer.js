/**
*@author 赵荆州
*@date 2014-04-10
*@参数说明:
*type：预售还是团购，可选值('prsell','group'),默认为预售
*必须保证元素，具有pk属性，pk为后台获取数据的唯一标识。
*/
(function($){
	$.fn.larryTimer=function(options){
		//基本参数
		var defaults = {
			type:"prsell"
		};
		var options = $.extend({},defaults,options);
		   var str = '<div class="leftDiscount"><p>';
	       var str1 = '<a href="'+settings.baseUrl+'/shop/helper/index.jhtml?now='+new Date().getTime()+'#delivery">查看提货点</a></p></div>';
	       var str2 = "预售已结束,提货已开始";
	       var str3 = "预售已结束,等待提货";
	       var str4 = "预售已结束,提货已完成";
	       var str5 = "预售已结束";
	       //预售结束图片
	       var finish = '<a href="javascript:;"><img src="'+settings.baseUrl+'/resources/common/images/finish.png"></a>';
	       var start  = '<a href="javascript:;"><img src="'+settings.baseUrl+'/resources/common/images/zwks.png"></a>';
	       var buy  = '<a href="javascript:;"><img src="'+settings.baseUrl+'/resources/common/images/buy.png"></a>';
	       var buyNow = '<a href="'+settings.baseUrl+'/member/pay/presell/create_order.jhtml?productId=${productId}" class="buyNow"><img src="'+settings.baseUrl+'/resources/common/images/buy.png"></a>';
			return this.each(function(){
					  var timer = $(this);
				      var pk = $(timer).attr('pk');//主键唯一标示
				      var url = settings.baseUrl+"/shop/utils/get_time_down.jhtml";
				      var params = {"productId":pk,"type":options.type};
				      $.ajax({
				    	  url:url,
				    	  type:'POST',
				    	  data:params,
				    	  success:function(data){
				    		  	var downTimeStr = data.msg;
						        var dateArray = downTimeStr.split(':');
						        var day = parseInt(dateArray[0], 10);
						        var hour = parseInt(dateArray[1], 10);
						        var minute = parseInt(dateArray[2], 10);
						        var secound = parseInt(dateArray[3], 10);
						        var isStart = parseInt(dateArray[4], 10);
						        var startDelivery = parseInt(dateArray[5], 10);
						        var strDay, strHour, strMinute, strSecound;
						        var interval = null;
						        var changeDIV = $(timer).parent();
						        if(isStart==0){//未到预售时间
						        	$(timer).prev().html("预售开始倒计时：");
						        	//changeDIV.next().find("a:eq(0)").remove();
						        	changeDIV.next().find("a:eq(0)").before(start);//预售未开始图片
						        }else if(isStart==1){
						        	var temp = buyNow;
						        	temp=temp.replace("${productId}", pk);
						        	changeDIV.next().find("a:eq(0)").before(temp);//预售中
						        }
						        interval = setInterval(function () {
						            strDay = day < 10 ? '0' + day : day;
						            strHour = hour < 10 ? '0' + hour : hour;
						            strMinute = minute < 10 ? '0' + minute : minute;
						            strSecound = secound < 10 ? '0' + secound : secound;
						            $(timer).html("<span>" + strDay + "</span><em>天</em><span>" + strHour + "</span><em>时</em><span>" + strMinute + "</span><em>分</em><span>" + strSecound + "</span><em>秒</em>");
						            secound -= 1;
						            if (secound < 0) {
						                secound = 59;
						                minute -= 1;
						                if (minute < 0) {
						                    minute = 59;
						                    hour -= 1;
						                    if (hour < 0) {
						                        hour = 23;
						                        day -= 1;
						                        if (day < 0&&isStart==1||isStart==2) {//时间结束，并且是预售期结束
						                            clearInterval(interval);
						                            changeDIV.empty();
						                            var deliveryInfo;
						                            //预售时间结束，判断提货状态
						                            if(startDelivery==0){//未开始提货
						                            	deliveryInfo = str+str3;
						                        	}else if(startDelivery==1){//开始提货
						                        		deliveryInfo = str+str2+str1;
						                        	}else if(startDelivery==2){//提货已完成
						                        		deliveryInfo = str+str4;
						                        	}else if(startDelivery==3){
						                        		deliveryInfo = str+str5;
						                        	}
						                            if(changeDIV.next().find("a").length>1){
						                            	changeDIV.next().find("a:eq(0)").remove();
						                            }
						                            changeDIV.next().find("a:eq(0)").before(finish);//预售结束图片
						                            changeDIV.before(deliveryInfo);
						                            changeDIV.remove();
						                            return;
						                        }else if(day < 0&&isStart==0){//时间结束，预售期开始
						                        	$(timer).prev().html("预售剩余时间：");
						                        	clearInterval(interval);
						                        	changeDIV.empty();
										        	changeDIV.next().find("a:eq(0)").remove();
										        	buyNow=buyNow.replace("${productId}", pk);
										        	changeDIV.next().find("a:eq(0)").before(buyNow);//预售中
						                        	$(timer).larryTimer();
						                        }
						                    }
						                }
						            }
						        }, 1000);
				    	  },
				    	  error:function(){
				    	  },
				    	  dataType:"json"
				      });
			});
	};
})(jQuery);