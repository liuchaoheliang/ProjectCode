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
    <div class="stepfen"></div>
     <div class="step">
      <li class="step01">输入积分</li>
      <li class="step02">核对信息</li>
      <li class="colorRed step09">申请结果</li>
    </div>     
   <!--成功-->  
    <div class="successBox" id="paySuccess" > 
    <dl>
      <dt class="jumpSuccess"><B>申请提现成功</B></dt>
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
