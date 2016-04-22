<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收银台-手机银行收款结果</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="${base}/template/common/js/height.js"></script> -->
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>

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
     var url = "queryCollectResult.action?transId=${(trans.id?c)?if_exists}";
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
          	document.getElementById("paySuccess").style.display="none";
          	if(payResult=="collect_success"){
            	document.getElementById("paySuccess").style.display="";
            	document.getElementById("successTip").innerHTML = "收款成功，正在赠送积分，请稍等……";
            	document.getElementById("successLink").innerHTML="<a href='javascript:queryPayResult();'>查询赠送结果</a>";
          	}else if(payResult=="collect_fail"){
          		document.getElementById("payFail").style.display="";
          		document.getElementById("failTip").innerHTML="收款失败，交易号为：${(trans.id?c)?if_exists}";
          		document.getElementById("failLink").innerHTML="";
          	}else if(payResult=="collect_doing"){
          		document.getElementById("doing").style.display="";
          		document.getElementById("doingTip").innerHTML="收款处理中，请稍等……";
				if(queryTimes<totalTimes){
					setTimeout("queryPayResult()",intervalTime);
					queryTimes++;
				}
				return;
          	}else if(payResult=="deduct_success"){
            	document.getElementById("paySuccess").style.display="";
            	document.getElementById("successTip").innerHTML = "收款成功，正在赠送积分，请稍等……";
            	document.getElementById("successLink").innerHTML="<a href='javascript:queryPayResult();'>查询赠送结果</a>";
          	}else if(payResult=="deduct_fail"){
          		document.getElementById("payFail").style.display="";
          		document.getElementById("failTip").innerHTML="收款成功，积分增送失败，请重新增送积分。";
          		document.getElementById("failLink").innerHTML="<a href='send_points_page.action'>重新赠送积分</a>";
          	}else if(payResult=="deduct_doing"){
          		document.getElementById("doing").style.display="";
          		document.getElementById("doingTip").innerHTML="收款成功，正在赠送积分，请稍等……";
				if(queryTimes<totalTimes){
					setTimeout("queryPayResult()",intervalTime);
					queryTimes++;
				}
				return;
          	}else if(payResult=="present_success"){
            	document.getElementById("paySuccess").style.display="";
            	document.getElementById("successTip").innerHTML = "收款成功，积分增送成功。";
            	document.getElementById("successLink").innerHTML="<a href='javascript:window.close();'>关闭</a>";
          	}else if(payResult=="present_fail"){
          		document.getElementById("payFail").style.display="";
          		//收款成功，积分增送失败，请联系分分通客服。
          		document.getElementById("failTip").innerHTML="收款成功，正在赠送积分……";
          		document.getElementById("failLink").innerHTML="";
          	}else if(payResult=="present_doing"){
          		document.getElementById("doing").style.display="";
          		document.getElementById("doingTip").innerHTML="收款成功，正在赠送积分，请稍等……";
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
<div class="moneybg">
<div class="money" id="rightHeight">
   <div class="stepadmin"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="step09 colorRed">收款结果</li>
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
          <dt id="doingTip"></dt>
           <dd class="grayFont f12">如果长时间没有响应，请点击<a href="javascript:queryPayResult()" value="查询">查询收款结果</a>
        </dl>  
     </div>  
     <!--处理中-->
    
     <!--成功-->
    <div class="successBox" id="paySuccess" style="display:none"> 
    <dl>
      <dt class="jumpSuccess"><B id="successTip"></B></dt>
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/success.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="70%" height="24" align="left"><b>付款人手机号：${(trans.phone)?if_exists}</b></td>  
        </tr>
        <tr>
          <td height="24" align="left" id="successLink"><a href="javascript:window.close();">关闭</a></td>
        </tr>
      </table>
    </dl>    
    </div>   
    <!--成功-->
    
     <!--失败-->
    <div class="successBox" id="payFail" style="display:none"> 
    <dl>
      <dt class="jumpFail"><B id="failTip">${errorMessage!"收款失败，"}交易号为：${(trans.id?c)?if_exists}</B></dt>
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="70%" height="24" align="left"><b>付款人手机号：${(trans.phone)?if_exists}</b></td>  
        </tr>
        <tr>
          <td height="24" align="left" id="failLink"><a href="javascript:window.close();">关闭</a></td>
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
