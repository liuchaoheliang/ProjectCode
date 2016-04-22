<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-兑换结果</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/adminexplain.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
var xmlHttp;
var queryTimes=0;
var totalTimes=5;//总次数
var intervalTime=3000;//间隔时间

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


function queryPayResult(){
	createXMLHttpRequest(); 
	
     var url = "queryPayResult.action?transId=${(trans.id?c)?if_exists}";
     xmlHttp.open("GET",url,true)
     xmlHttp.setRequestHeader("If-Modified-Since","0");//禁止IE缓存
     xmlHttp.onreadystatechange=displayResult;
     xmlHttp.send(null);
}


function displayResult(){
    
      if (xmlHttp.readyState == 4) {
          if (xmlHttp.status == 200) {
			var payResult=xmlHttp.responseText;	
          	document.getElementById("pay").style.display="none";
          	document.getElementById("doing").style.display="none";
          	if(payResult=="success"){
            	document.getElementById("paySuccess").style.display="";
          	}else if(payResult=="fail"){
          		document.getElementById("payFail").style.display="";
          	}else if(payResult=="doing"){
          		document.getElementById("doing").style.display="";
				if(queryTimes<totalTimes){
					setTimeout("queryPayResult()",intervalTime);
					queryTimes++;
				}
				return;
          	}else if(payResult=="notExist"){
          		document.getElementById("payNotExists").style.display="";
          	}
         }
     }
}

window.onload = function(){ //加载页面时运行
	var result="${payResult!''}";
	if(result=="waiting"){
		document.getElementById("pay").style.display="";
	}else if(result=="success"){
		document.getElementById("paySuccess").style.display="";
	}else if(result=="fail"){
		document.getElementById("payFail").style.display="";
	}else if(result=="error"){
		document.getElementById("error").style.display="";
	}else{
		document.getElementById("error").style.display="";
	}
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
  
<!--开始-->
  <div class="result fl">
    <div class="stepgroup"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="colorRed step09">兑换完成</li>
    </div>
    
   <!--等待付款-->
   <div class="messageBox" id="pay" style="display:none">
        <dl>
          <dt>等待付款……</dt>
          <dd class="grayFont f12">如果支付完成，请点击<a href="javascript:queryPayResult()" value="已完成支付">已完成支付</a>
        </dl>  
    </div>
    <!--等待付款-->
      
    <!--处理中-->  
    <div class="messageBox" id="doing" style="display:none">
        <dl>
          <dt>处理中，请稍候……</dt>
           <dd class="grayFont f12">如果长时间没有响应，请点击<a href="javascript:queryPayResult()" value="查询">查询</a>
        </dl>  
     </div>  
     <!--处理中-->     
    
     <!--成功-->  
    <div class="successBox" id="paySuccess" style="display:none"> 
    	 <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="jumpsuccess">支付成功</td>
          </tr>
        <tr>
          <td>	          	<#if trans??>
	          		<#if trans.virtualType??>
		          		<#if trans.virtualType=="100001006">
		          			提示信息：支付成功，我们将尽快为您充值。
		          		<#elseif trans.virtualType=="100001005">
		          			提示信息：支付成功，彩票信息会尽快发送到您的手机上。
		          		<#else>
		          			提示信息：支付成功，
                            <#if trans.presentPointsValue??&& trans.presentPointsValue?number gt 0>
                            您已获得 ${trans.presentPointsValue!"0"} 个分分通积分，
                            </#if>
                            消费券会发送到您手机上，请到实体店消费。
		          		</#if>
	          		</#if>
	          	<#else>
	          		该页面已过期。
	          	</#if></td>
          </tr>
        <!--<tr>
          <td><a href="group_index.action">返回</a></td>
        </tr>-->
      </table> 
    
    
    <!--
	    <dl>
	       <table border="0"  cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="15%" rowspan="2"><img src="${base}/template/common/images/success.png"/></td>
	          <td width="3%" rowspan="2">&nbsp;</td>
	          <td width="82%" height="24" align="left">
	          	<#if trans??>
	          		<#if trans.virtualType??>
		          		<#if trans.virtualType=="100001006">
		          			支付成功，我们将尽快为您充值。
		          		<#elseif trans.virtualType=="100001005">
		          			支付成功，彩票信息会尽快发送到您的手机上。
		          		<#else>
		          			支付成功，消费券会发送到您手机上，请到实体店消费。
		          		</#if>
	          		</#if>
	          	<#else>
	          		该页面已过期。
	          	</#if>
	          </td>
	        </tr>
	      </table>
	    </dl> 
	   -->   
    </div>   
    <!--成功-->
    
    <!--失败-->  
    <div class="failBox" id="payFail" style="display:none" > 
	<!--
       <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="20%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="77%" height="24" align="left">支付失败，请稍后重试。</td>
        </tr>
      </table> 
    </dl> -->
 	 <table border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td class="jumpFail">支付失败</td>
          </tr>
        <tr>
          <td>提示信息：支付失败，请稍后重试。</td>
          </tr>
      </table>       
       
    </div>   
    <!--失败-->  
    
    <!--交易不存在-->  
    <div class="failBox" id="payNotExists" style="display:none"> 
	    <dl>
	      <table border="0"  cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="20%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
	          <td width="3%" rowspan="2">&nbsp;</td>
	          <td width="77%" height="24" align="left">交易不存在或该页面已过期。</td>
	        </tr>
	      </table>
	    </dl>    
    </div>
    <!--交易不存在-->  
    
    <!--错误-->  
    
    <div class="failBox" id="error" style="display:none"> 
     	 <table border="0" cellspacing="0" cellpadding="0" >
	        <tr>
	          <td class="jumpFail">支付失败</td>
	          </tr>
	        <tr>
	          <td>提示信息：${errorMsg!"页面已过期"}</td>
	          </tr>
	      </table>   
        
    
	    <!--<dl>
	      <table border="0"  cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="20%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
	          <td width="3%" rowspan="2">&nbsp;</td>
	          <td width="77%" height="24" align="left">
	          ${errorMsg!"页面已过期"}
	          </td>
	        </tr>
	      </table>
	    </dl>    -->
    </div>   
    <!--错误-->  
    
    <#if errorMsg??>
	    <div class="adminexplain">
	     <dl>
	      <dt>为何无法完成支付？</dt>
	      <dd>当选择现金支付后，账单将推送到您的手机银行卡上，非手机银行卡用户将无法使用现金支付</dd>
	      <dt>我该怎么做？</dt>
	      <dd>若您已在珠海农商银行办理手机银行卡，请进入<a href="${base}/mobile_bank_index.action">手机银行卡认证</a><br>若您没有手机银行卡，可到珠海农商银行各网点办理，<a href="http://www.zhnsb.com.cn/service/branches/" target="_blank">点击查看网点</a></dd>
	     </dl>
	    </div>
    </#if>
    
  </div> 
<!--结束-->
                 
</div>
  
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
