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
   
       <!--成功-->
    <div class="successBox" id="paySuccess" style="">

	 <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="jumpsuccess">支付成功</td>
          </tr>
        <tr>
          <td>提示信息：您已成功参与团购，兑换码将会发送到您的手机号码上，请及时到店消费。</td>
          </tr>
        <tr>
            <td><a href="group_index.action">返回团购列表</a></td>
        </tr>
      </table> 
    </div>   
    <!--成功-->

  </div>
</div>

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
