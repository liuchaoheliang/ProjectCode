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
<link href="${base}/template/common/css/adminexplain.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>


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
    <div class="stepgroup"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="step02">生成订单</li>
      <li class="colorRed step09">操作结果</li>
    </div> 
   <!--跳转内容begin
    <div class="failBox" id="pay"> 
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="17%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="80%" height="24" align="left"></td>
        </tr>
        <tr>
          <td height="24" align="left"><a href="${base}/group_index.action">返回团购列表页面</a></td>
        </tr>
      </table>    
    </div>   -->
    
    
    <!--失败-->  
    <div class="failBox" id="pay"> 
	 <table border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td class="jumpFail">支付失败</td>
          </tr>
        <tr>
          <td>${errorMsg?if_exists}</td>
          </tr>
        <tr>
          <td><a href="presell_index.action">返回精品预售列表</a></td>
        </tr>
      </table>      
    </div>   
    <!--失败-->      
    
      
	
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
 </div> 
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
