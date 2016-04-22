<script type="text/javascript">
var xmlHttp;
var flag = false;
var hasResult=false;
var requestTimes=0;
var c=0;
var t;

function createXMLHttpRequest()
{
  if(window.ActiveXObject)
  {
    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  else if(window.XMLHttpRequest)
  {
  xmlHttp=new XMLHttpRequest();
  }
}




function getOrderResultByAjax(requestUrl){
	createXMLHttpRequest(); 
	
    // var url = "queryPayResultByAjax.action?orderVO.tran.id=${orderVO.tran.id}";
    // createXmlHttp();
     xmlHttp.onreadystatechange = getOrderResultCallBack;
     xmlHttp.open("GET",requestUrl,true)
    xmlHttp.setRequestHeader("If-Modified-Since","0");  //禁止IE缓存，如果有缓存就不会再去连数据库了，那么数据达不到及时更新的效果，当然这句话只禁止当前xmlHttp对象的缓存(亲测)。

     xmlHttp.send(null);
     if(!hasResult)
    	setTimeout("getOrderResultByAjax(requestUrl);",1000*30);   //定时触发请求
}
function getOrderResultCallBack(){
    
      if (xmlHttp.readyState == 4) {
          if (xmlHttp.status == 0 || xmlHttp.status == 200) {
          		
              if(xmlHttp.responseText!=null&&""!=xmlHttp.responseText){
              	hasResult=true;
              document.getElementById("orderResult").innerHTML=xmlHttp.responseText;//更新DOM
              if("下单成功"!=xmlHttp.responseText){
              		var getPayResultUrl="queryPayResultByAjax.action?orderVO.trackNo=${orderVO.trackNo}";
              		getPayResultByAjax(getPayResultUrl);
              	}
              }
         }
     }
}


function getPayResultByAjax(requestUrl){
	createXMLHttpRequest(); 
	
     xmlHttp.onreadystatechange = getPayResultCallBack;
     xmlHttp.open("GET",requestUrl,true)
    xmlHttp.setRequestHeader("If-Modified-Since","0");  //禁止IE缓存，如果有缓存就不会再去连数据库了，那么数据达不到及时更新的效果，当然这句话只禁止当前xmlHttp对象的缓存(亲测)。

     xmlHttp.send(null);
     if(!hasResult)
    	setTimeout("getPayResultByAjax(requestUrl);",1000*30);   //定时触发请求
}
function getPayResultCallBack(){
    
      if (xmlHttp.readyState == 4) {
          if (xmlHttp.status == 0 || xmlHttp.status == 200) {
          		
              if(xmlHttp.responseText!=null&&""!=xmlHttp.responseText){
              	hasResult=true;
              document.getElementById("payResult").innerHTML=xmlHttp.responseText;//更新DOM
              
              }
         }
     }
}

 

function timedCount()
{
document.getElementById('txt').value=c
c=c+1
t=setTimeout("timedCount()",1000)
}

window.onload = function(){  //加载页面时自动运行

	var tranId="${orderVO.tran.id?if_exists}";
	if(tranId==""){
		 document.getElementById("payResult").innerHTML="交易号为空！";
		return ;
	}
var queryOrderResultByAjaxUrl="queryOrderResultByAjax.action?orderVO.trackNo=${orderVO.trackNo}";
getOrderResultByAjax(queryOrderResultByAjaxUrl); 

} 

</script>

<div>
系统正在下单，请等待。。。。。
</div>

<div id="orderResult">
</div>

<div id="payResult">
</div>

