<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>


<#include "/WEB-INF/merchant/base/include.ftl">


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
              		document.getElementById("waitForResult").style.display="none";
              	document.getElementById("payInfo").style.display="";
              document.getElementById("payResult").innerHTML="<h5>"+xmlHttp.responseText+"    积分交易号为：${orderVO.transPoints.id?if_exists?c}</h5>";//更新DOM
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


</head>
<body>
<div class="adminTop">
  <div class="adminTopMiddle">
    <div class="adminTopLogo"><a href="#"><img src="images/adminTopLogo.png"></a></div>
    <div class="adminTopWelcome"><img src="images/adminTopIcon.png">你好：<B>凌统</B>，欢迎使用珠海农商银行管理系统。</div>
    <div class="adminToday">今天是：2012年12月6日 星期二</div>
  </div>
  
  <div class="adminTopRight">
    <div class="adminTopMenu"><a href="#">管理主页</a> | <a href="#">安全退出</a></div>
  </div>  
  
  <div class="adminMenu">
    <ul>
    <img src="images/syt.png">
    <li><a href="#" class="cure">基本信息</a></li>
    <li><a href="#">首页管理</a></li>
    <li><a href="#">信息管理</a></li>
    <li><a href="#">交易管理</a></li>
    <li><a href="#">客诉处理</a></li>
    <li><a href="#">安全中心</a></li>
    </ul>
  </div>
</div>


<div class="adminRight">
  <div class="adminBorderTop">
    <div class="adminBorderTopLeft"></div>
    <div class="adminBorderTopTitle">
      <div class="adminBorderTopTitleLeft"></div>
      <div class="adminBorderTopTitleMiddle">收银台</div>
      <div class="adminBorderTopTitleRight"></div>
    </div>
    <div class="adminBorderTopRight"></div>
  </div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F7F8F9">
  <tr>
    <td class="adminBorderMiddleLeft"></td>
    <td>
     <div class="rebateBox">
         <div class="step">
           <li class="step01">输入信息</li>
           <li class="step02">生成订单</li>
           <li class="step03">收款结果</li>
           <li class="step04">赠送积分</li>
           <li class="step05">积分订单</li>
           <li class="step12">赠送成功</li>
         </div>
         
         <div id="waitForResult" class="discountFloat">
          	<div class="succText">
	         	系统正在赠送积分，请等待。。。。。。
	         </div>
		</div>
         <div id="payInfo" class="discountFloat" style="display:none">
         		
	         <div class="succImg"></div>
	         <div id="payResult" class="succText">
			</div>
			<div>
      			<h5> <a href="/getCashdeskInfo.action">返回到收银台</a></h5>
      		</div>
         </div>
                     
     </div>          
    </td>
    <td class="adminBorderMiddleRight"></td>
  </tr>
</table>
<div class="adminBorderBottom">
  <div class="adminBorderBottomLeft"></div>
  <div class="adminBorderBottomRight"></div>
</div>
</div>

<div><!--数据图表--></div>

<div class="adminFoot">
  <div class="adminCopy"><span class="adminFootLogo"></span>Copyright 2012 Froad All Right reserved.</div>
</div>
</body>
</html>
