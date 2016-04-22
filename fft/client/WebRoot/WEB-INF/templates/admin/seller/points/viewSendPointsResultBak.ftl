
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




function getVisitNum(){
	createXMLHttpRequest(); 
	
     var url = "queryPayPointResultByAjax.action?orderVO.transPoints.id=${orderVO.transPoints.id}";
    // createXmlHttp();
     xmlHttp.onreadystatechange = getVisitNumCallBack;
     xmlHttp.open("GET",url,true)
    xmlHttp.setRequestHeader("If-Modified-Since","0");  //禁止IE缓存，如果有缓存就不会再去连数据库了，那么数据达不到及时更新的效果，当然这句话只禁止当前xmlHttp对象的缓存(亲测)。

     xmlHttp.send(null);
     if(hasResult)
     	return ;
     else
    	setTimeout("getVisitNum();",1000*30);   //定时触发请求
}
function getVisitNumCallBack(){
    
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

	var transPointsId="${orderVO.transPoints.id?if_exists}";
	if(transPointsId==""){
		 document.getElementById("payResult").innerHTML="赠送积分交易号为空！";
		return ;
	}

getVisitNum(); 

invokeF();
} 

</script>
系统正在支付，请等待。。。。。。

<div id="payResult">
</div>

<form>
<input type="button" value="开始计时！" onClick="timedCount()">
<input type="text" id="txt">
</form>