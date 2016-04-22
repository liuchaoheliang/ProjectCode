<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付结果</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/shop/css/pay.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<script type="text/javascript">
$(document).ready(function (){
	var url = $('.return_url').attr('href');
	setTimeout(function (){
		window.location.href = url;
	},5000);
});
</script>
<#-- 头部 结束-->
<#if payResult.code == 'success'><#-- 支付成功 -->
<div class="main" >
  <div class="confirm" >
    <div class="stepgroup " style="background-position:center bottom;"></div>
    <div class="result"> 
      <div class="successBox">
          <table  border="0"  cellpadding="0" cellspacing="0">
            <tr>
              <td  class="jumpsuccess">${payResult.msg}</td>
            </tr>
            <tr>
              <td >如果您的浏览器没有自动跳转，<a class="return_url" href="${base}${payResult.url}">请点击此链接。</a></td>
            </tr>
          </table>
      </div>
    </div>
  </div>
</div>
<#elseif payResult.code == 'error'><#-- 支付失败 -->
<div class="main" >
  <div class="confirm" >
   <div class="stepgroup " style="background-position:center bottom;"></div>
    <div class="result"> 
      <div class="failBox">
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="jumpFail">购买失败</td>
          </tr>
          <tr>
            <td>提示信息：支付失败，${payResult.msg}</td>
          </tr>
          <tr>
            <td>如果您的浏览器没有自动跳转，<a class="return_url" href="${base}${payResult.url}/">返回首页</a></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
<#else><#-- 支付宝|网银未收到付款 -->
<div class="main" >
<div class="confirm" >
    <div class="stepgroup " style="background-position:center bottom;"></div>
    <div class="result"> 
      <div class="waitBox">
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="jumpwait">等待支付</td>
          </tr>
          <tr>
            <td>${payResult.msg}</td>
          </tr>
          <tr>
            <td>如果您的浏览器没有自动跳转，<a class="return_url" href="${base}${payResult.url}">请点击此链接。</a></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</#if>
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->
</div>
<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>