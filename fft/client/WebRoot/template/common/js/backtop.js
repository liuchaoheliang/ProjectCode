/*
* Author:
* pengling@f-road.com.cn 
*/

function goTopEx(){
        var obj=document.getElementById("goTopBtn");
        function getScrollTop(){
			if(window.navigator.userAgent.indexOf("Chrome")!= -1){
				return document.body.scrollTop;
				}
                return document.documentElement.scrollTop;
				
            }
        function setScrollTop(value){
			if(window.navigator.userAgent.indexOf("Chrome")!= -1){
				document.body.scrollTop = value;
				}
                document.documentElement.scrollTop=value;
            }    
        window.onscroll=function(){getScrollTop()>0?obj.style.display="":obj.style.display="none";}

        obj.onclick=function(){
            var goTop=setInterval(scrollMove,10);
            function scrollMove(){
                    setScrollTop(getScrollTop()/1.1);
                    if(getScrollTop()<1)clearInterval(goTop);
                }
        }
    }