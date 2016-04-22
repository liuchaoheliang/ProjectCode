<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-兑换结果</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
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
      <li class="colorRed step09">购买成功</li>
    </div>   
    <!--跳转内容begin-->
    <div class="successBox"> 
    <dl>
      <!-- <dt class="jumpFail"><B>购买失败</B></dt>-->
      <table border="0"  cellpadding="0" cellspacing="0">
        <tr>
          <td width="20%" rowspan="2"><img src="${base}/template/common/images/fail.png"/></td>
          <td width="3%" rowspan="2">&nbsp;</td>
          <td width="77%" height="24" align="left">${errorMsg!"购买失败"}</td>
        </tr>
  <!--     <tr>
          <td height="24" align="left">如果您的浏览器没有自动跳转，<a href="#">请点击此链接。</a></td>
        </tr> --> 
      </table>

    </dl>
    
    </div>   
    <!--跳转内容end-->       
  </div> 
<!--结束-->
                 
</div>
  
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
