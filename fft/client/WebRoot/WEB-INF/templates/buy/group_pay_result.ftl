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
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->


<div class="box1010 pt10 clear">

  <div class="result fl">
    <!--跳转内容begin-->
    
     <div class="stepgroup"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="colorRed step09">购买完成</li>
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
          <td>提示信息：消费券会发送到您手机上，请到实体店消费。</td>
          </tr>
        <tr>
          <td><a href="group_index.action">返回团购列表</a></td>
        </tr>
      </table> 
    </div>   
    <!--成功-->
    
    <!--失败-->  
    <div class="failBox" id="payFail" style="display:none" > 
	 <table border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td class="jumpFail">支付失败</td>
          </tr>
        <tr>
          <td>提示信息：支付失败，请稍后重试。</td>
          </tr>
        <tr>
          <td><a href="group_index.action">返回团购列表</a></td>
        </tr>
      </table>      
    </div>   
    <!--失败-->  
    
    <!--交易不存在-->  
    <div class="failBox" id="payNotExists" style="display:none"> 
	      <table border="0"  cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
	          <td width="3%" rowspan="2">&nbsp;</td>
	          <td width="70%" height="24" align="left">交易不存在或该页面已过期。</td>
	        </tr>
	      </table> 
    </div>
    <!--交易不存在-->  
    
    <!--错误-->  
    <div class="failBox" id="error" style="display:none"> 
	      <table border="0"  cellpadding="0" cellspacing="0">
	        <tr>
	          <td width="27%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
	          <td width="3%" rowspan="2">&nbsp;</td>
	          <td width="70%" height="24" align="left">${errorMsg!"页面已过期"}</td>
	        </tr>
	      </table>  
    </div>   
    <!--错误-->  
    
  </div>
</div>

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
