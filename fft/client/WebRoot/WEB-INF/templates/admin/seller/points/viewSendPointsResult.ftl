<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收银台-积分赠送结果</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="${base}/template/common/js/height.js"></script> -->
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>

<script type="text/javascript">
var xmlHttp;
var queryTimes=0;
var totalTimes=60;//总次数
var intervalTime=3000;//间隔时间
var firstQueryTime=20*1000;//初次查询的开始时间

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
	if(result=="doing"){
		document.getElementById("doing").style.display="";
		setTimeout("queryPayResult()",firstQueryTime);
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
<!-- 商家管理菜单结束 -->
<div class="givebg">
  <div class="money" id="rightHeight">
  <div class="stepgive"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="step09 colorRed">赠送结果</li>
    </div>    
    
    <!--处理中-->  
    <div class="messageBox" id="doing" style="display:none">
        <dl>
          <dt>正在赠送积分，请稍候……</dt>
           <dd class="grayFont f12">如果长时间没有响应，请点击<a href="javascript:queryPayResult()" value="查询">查询</a>
          <dd><a href="javascript:window.close();">关闭</a></dd>
        </dl>  
     </div>  
     <!--处理中--> 
    
    <!--成功-->  
    <div class="successBox" id="paySuccess" style="display:none"> 
    <dl>
      <dt class="jumpSuccess"><B>恭喜您，交易号为：${(trans.id?c)?if_exists}</B></dt>
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/success.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="70%" height="24" align="left">积分赠送成功！<!-- 付款人手机号：<#if trans?exists >${trans.phone?if_exists}</#if> --></td>  
        </tr>
        <tr>
          <td height="24" align="left"><a href="javascript:window.close();">关闭</a></td>
        </tr>
      </table>
    </dl>    
    </div>   
    <!--成功-->
    
    <!--失败-->  
    <div class="successBox" id="payFail" style="display:none"> 
    <dl>
      <dt class="jumpFail"><B>${errorMessage!"积分赠送失败，"}交易号为：${(trans.id?c)?if_exists}</B></dt>
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
         <td width="70%" height="24" align="left">请稍候再试！<!-- 付款人手机号：<#if trans?exists>${trans.phone?if_exists}</#if> --></td>  
        </tr>
        <tr>
          <td height="24" align="left"><a href="cashdesk_info.action">返回收银台</a></td>
        </tr>
      </table>

    </dl>    
    </div>   
    <!--失败-->  
    
    <!--交易不存在-->
    <div class="messageBox" id="payNotExists" style="display:none"> 
	    <dl>
	      <table border="0"  cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
	          <td width="3%" rowspan="2">&nbsp;</td>
	          <td width="70%" height="24" align="left">交易不存在或该页面已过期。</td>
	        </tr>
	      </table>
	    </dl>    
    </div>
    <!--交易不存在-->  
    
    <!--错误-->
    <div class="messageBox" id="error" style="display:none"> 
	    <dl>
	      <table border="0"  cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
	          <td width="3%" rowspan="2">&nbsp;</td>
	          <td width="70%" height="24" align="left">${errorMessage!"页面已过期"}</td>
	        </tr>
	      </table>
	    </dl>    
    </div>   
    <!--错误-->  
    
  </div>
   </div>
</body>
</html>
