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

var intervalTime=30;//初始间隔时间
var shortIntervalTime=3;//最短的间隔时间
var longIntervalTime=10;//公差，间隔时间成等差数列
var threeIntT=5;//间隔3秒的次数
var stopQuery=false;

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


function refreshVisitNum(){
	createXMLHttpRequest(); 
	
     var url = "query_exchange_pay_result.action?trans.id=${trans.id}";
    // createXmlHttp();
     xmlHttp.onreadystatechange = getVisitNumCallBack;
     xmlHttp.open("GET",url,true)
    xmlHttp.setRequestHeader("If-Modified-Since","0");  //禁止IE缓存，如果有缓存就不会再去连数据库了，那么数据达不到及时更新的效果，当然这句话只禁止当前xmlHttp对象的缓存(亲测)。

     xmlHttp.send(null);
}




function getVisitNum(){
	createXMLHttpRequest(); 
	
     var url = "query_exchange_pay_result.action?trans.id=${trans.id}";
    // createXmlHttp();
     xmlHttp.onreadystatechange = getVisitNumCallBack;
     xmlHttp.open("GET",url,true)
    xmlHttp.setRequestHeader("If-Modified-Since","0");  //禁止IE缓存，如果有缓存就不会再去连数据库了，那么数据达不到及时更新的效果，当然这句话只禁止当前xmlHttp对象的缓存(亲测)。

     xmlHttp.send(null);
     if(hasResult)
     	return ;
      else if(!stopQuery)
    	setTimeout("getVisitNum();",1000*intervalTime);   //定时触发请求
    
}
function getVisitNumCallBack(){
    
      if (xmlHttp.readyState == 4) {
          if (xmlHttp.status == 0 || xmlHttp.status == 200) {
          		
          		intervalTime=intervalTime-longIntervalTime;
          		if(intervalTime<3)
          			intervalTime=3;
          		if(intervalTime==3)
          			threeIntT=threeIntT-1;
          		if(threeIntT<=0)
          			stopQuery=true;
          			
              if(xmlHttp.responseText!=null&&""!=xmlHttp.responseText){
              	hasResult=true;
              	document.getElementById("pay").style.display="none";
              	//document.getElementById("payInfo").style.display="";
              	var payResultVal_fromServer="";
              	if(xmlHttp.responseText=="success"){
              		//payResultVal_fromServer="支付成功！";
              		//var sendPointsObj=document.getElementById("sendPoints");
	              	//if((typeof sendPointsObj != "undefined" )&&(sendPointsObj!=null) ){
	              	//	sendPointsObj.style.display="";
	              	//}
	              	//document.getElementById("succImg").style.display="";
	              		document.getElementById("paySuccess").style.display="";
              	}
              	if(xmlHttp.responseText=="fail"){
              	//	payResultVal_fromServer="支付失败";
              	//	document.getElementById("FailImg").style.display="";
              	document.getElementById("payFail").style.display="";
              	}
              	if(xmlHttp.responseText=="notExists"){
              		//payResultVal_fromServer="交易不存在！";
              		//document.getElementById("FailImg").style.display="";
              		document.getElementById("payNotExists").style.display="";
              	}
              	if(xmlHttp.responseText=="sysError"){
              		//payResultVal_fromServer="系统失败，请稍后再试！";
              		//document.getElementById("FailImg").style.display="";
              		document.getElementById("sysError").style.display="";
              	}
              	//document.getElementById("payResult").innerHTML="<div style='color:red;font-size:20px;margin-top:30px;'><b>"+payResultVal_fromServer+"  订单号为：${trans.id?if_exists?c}</div>";//更新DOM
              	
              }
         }
     }
}


window.onload = function(){  //加载页面时自动运行

	var tranId="${trans.id?if_exists}";
	if(tranId==""){
		 document.getElementById("payResult").innerHTML="交易号为空！";
		return ;
	}

getVisitNum(); 

} 


function sendPointsSumbit()
{
sendPointsForm.submit();
}

</script>


</head>
<body>
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->


<div class="box1010 pt10 clear">
<!--充值开始-->
  <div class="result fl">
    <!--跳转内容begin-->
    
     <div class="stepgroup"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="colorRed step09">操作结果</li>
    </div> 
    
     <div class="messageBox" id="pay">
        <dl>
          <dt>处理中，请稍候……</dt>
           <dd class="grayFont f12">如果长时间没有响应,请点击<a href="#" onclick="javascript:refreshVisitNum()"  value="查询">查询</a>
        </dl>  
      </div>   
    
     <!--成功-->  
    <div class="successBox" id="paySuccess" style="display:none"> 
    <dl>
      <dt class="jumpSuccess"><B>操作成功</B></dt>
       <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/success.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="70%" height="24" align="left">提示信息：操作成功。</td>
        </tr>
        <!--
        <tr>
          <td height="24" align="left">如果您的浏览器没有自动跳转，<a href="#">请点击此链接。</a></td>
        </tr>  -->
      </table>
    </dl>    
    </div>   
    <!--成功-->
    
     <!--失败-->  
    <div class="successBox" id="payFail" style="display:none" > 
    <dl>
      <dt class="jumpFail"><B>操作失败</B></dt>
       <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="70%" height="24" align="left">提示信息：操作失败。</td>
        </tr>
        <!--
        <tr>
          <td height="24" align="left">如果您的浏览器没有自动跳转，
          <a href="#">请点击此链接。</a>
          </td>
        </tr>  -->
      </table>
    </dl>    
    </div>   
    <!--失败-->  
    
      <!--transaction does not exist-->  
    <div class="successBox" id="payNotExists" style="display:none"> 
    <dl>
      <dt class="jumpFail"><B>操作失败，交易号为：${trans.id?if_exists?c}</B></dt>
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
      <#--    <td width="70%" height="24" align="left">付款人手机号：${orderVO.tran.buyer.user.mobilephone?if_exists}</td>   -->
        </tr>
        <tr>
          <td height="24" align="left"><a href="#">返回收银台。</a></td>
        </tr>
      </table>

    </dl>    
    </div>   
    <!----transaction does not exist-->  
    
      <!--occur system error on paying-->  
    <div class="successBox" id="sysError" style="display:none"> 
    <dl>
      <dt class="jumpFail"><B>操作失败，交易号为：${trans.id?if_exists?c}</B></dt>
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
  <#--        <td width="70%" height="24" align="left">付款人手机号：${orderVO.tran.buyer.user.mobilephone?if_exists}</td>  -->
        </tr>
        <tr>
          <td height="24" align="left"><a href="#">返回收银台。</a></td>
        </tr>
      </table>

    </dl>    
    </div>   
     <!--occur system error on paying-->  
    
  </div>
</div>


  
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
