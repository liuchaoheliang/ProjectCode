<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/banner.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/imglist.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>

<script type="text/javascript">
			var xmlHttp;
			var flag = false;
			var hasResult=false;
			var requestTimes=0;
			var c=0;
			var t;
			var intervalTime=20;//初始间隔时间
			var shortIntervalTime=3;//最短的间隔时间
			var longIntervalTime=10;//公差，间隔时间成等差数列
			
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
				
			     var url = "queryPayPointsExchCurrecyResultByAjax.action?withDrawPointsVO.pointsExchCurrency.id=${withDrawPointsVO.pointsExchCurrency.id}";
			    // createXmlHttp();
			     xmlHttp.onreadystatechange = getVisitNumCallBack;
			     xmlHttp.open("GET",url,true)
			    xmlHttp.setRequestHeader("If-Modified-Since","0");  //禁止IE缓存，如果有缓存就不会再去连数据库了，那么数据达不到及时更新的效果，当然这句话只禁止当前xmlHttp对象的缓存(亲测)。
			
			     xmlHttp.send(null);
			     if(hasResult)
			     	return ;
			     else
			    	setTimeout("getVisitNum();",1000*intervalTime);   //定时触发请求
			}
			function getVisitNumCallBack(){
			    
			      if (xmlHttp.readyState == 4) {
			          if (xmlHttp.status == 0 || xmlHttp.status == 200) {
			          
			          		intervalTime=intervalTime-longIntervalTime;
			          		if(intervalTime<3)
			          			intervalTime=3;
			          			
			              if(xmlHttp.responseText!=null&&""!=xmlHttp.responseText){
			              	hasResult=true;
			              	document.getElementById("waitForResult").style.display="none";
			              	document.getElementById("payInfo").style.display="";
			              	
			              	var payResultVal_fromServer="";
			              	if(xmlHttp.responseText=="success"){
			              		payResultVal_fromServer="积分提现成功！";
			              		document.getElementById("succImg").style.display="";
			              	}
			              	if(xmlHttp.responseText=="fail"){
			              		payResultVal_fromServer="积分提现失败";
			              		document.getElementById("FailImg").style.display="";
			              	}
			              	if(xmlHttp.responseText=="notExists"){
			              		payResultVal_fromServer="积分提现交易不存在！";
			              		document.getElementById("FailImg").style.display="";
			              	}
			              	if(xmlHttp.responseText=="sysError"){
			              		payResultVal_fromServer="系统失败，请稍后再试！";
			              		document.getElementById("FailImg").style.display="";
			              	}
			              	
			              	
			              	document.getElementById("payResult").innerHTML="<div style='color:red;font-size:16px;margin-top:30px;'><b>"+payResultVal_fromServer+"    订单号为：${withDrawPointsVO.pointsExchCurrency.id?if_exists?c}</b></div>";//更新DOM
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
			
				var transPointsId="${withDrawPointsVO.pointsExchCurrency.id?if_exists}";
				if(transPointsId==""){
					 document.getElementById("payResult").innerHTML="积分提现交易号为空！";
					return ;
				}
			
			getVisitNum(); 
			
			} 
			
			</script>

</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
-->
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->


<div class="box1010 pt10 clear">
<!--内容开始-->
  <div class="result fl">
     <div id="waitForResult" class="successBox">
			<div class="succText">
					         	系统正在处理，请等待。。。。。。
			</div>
	</div>
    <!--跳转内容begin-->
    <div class="successBox" id="payInfo" style="display:none"> 
    <dl>
      <dt class="jumpSuccess"><B>购买成功</B></dt>
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="16%" rowspan="2"><img src="${base}/template/common/images/success.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="81%" height="24" align="left">提示信息：交易号会发送到您手机上，请到实体店消费。</td>
        </tr>
        <tr>
          <td height="24" align="left">如果您的浏览器没有自动跳转，<a href="#">请点击此链接。</a></td>
        </tr>
      </table>

    </dl>
    
    </div>   
<!--内容结束-->

  <div class="fl ml10">
    <div class="rightImg">
      <a href="#"><img src="${base}/template/web/images/right001.png"></a>
      <a href="#"><img src="${base}/template/web/images/right002.png"></a>
      <a href="#"><img src="${base}/template/web/images/right003.png"></a>
      <a href="#"><img src="${base}/template/web/images/right004.png"></a>
      <a href="#"><img src="${base}/template/web/images/right005.png"></a>
      <a href="#"><img src="${base}/template/web/images/right006.png"></a>
    </div>
    <div><img src="img/ad01.png"></div> 
  </div>                  
</div>
  
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->
<div class="foot mt10">
  <div class="footAdd">营商：珠海农商银行 上海方付通商务服务有限公司 电话：96138 沪ICP备:xxxxxxxxx号 地址：珠海市香洲兴业路223号</div>
</div>
<!--底部结束--> 
</body>
</html>
