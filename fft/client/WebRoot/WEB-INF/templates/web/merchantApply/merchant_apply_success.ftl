<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/table.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/web/js/rightImage.js"></script>

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

<div class="result fl">  
<!--商户申请-->

<#if apply.id?exists && apply.id == 0>

	 <div class="failBox" > 
		 <table border="0" cellspacing="0" cellpadding="0" >
	        <tr>
	          <td class="jumpFail">提交失败</td>
	          </tr>
	        <tr>
	          <td>提示信息：${(message)!""}</td>
	          </tr>
	      </table>      
	    </div>   


<#elseif apply.id?exists && apply.id != 0>   
    <div class="successBox"> 
    
    	 <table border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td class="jumpsuccess">提交成功</td>
	          </tr>
	        <tr>
	          <td>申请ID为：${apply.id?c}</td>
	          </tr>
	      </table> 
    </div>
    
  </#if> 
    
    
    
    
    

   

<!--商户申请-->
</div>

  <div class="fl ml10">
    <div class="rightImg">
      <a href="exchange_local_list.action"><img src="${base}/template/web/images/right001.png"></a>
      <a href="exchange_bankPoint_list.action"><img src="${base}/template/web/images/right007.png" ></a>
      <a href="exchange_lottery_list.action"><img src="${base}/template/web/images/right005.png"></a>
      <a href="exchange_telephonefee_list.action"><img src="${base}/template/web/images/right002.png"></a>
      <!--<a href="chargeMoney_page.action"><img src="${base}/template/web/images/right003.png"></a>
      <a href="alipay_page.action"><img src="${base}/template/web/images/right004.png"></a>
      <a href="exchange_specialty_list.action"><img src="${base}/template/web/images/right006.png"></a>       -->
    </div>
  </div>                  
</div>

<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
</div>
</body>
</html>
