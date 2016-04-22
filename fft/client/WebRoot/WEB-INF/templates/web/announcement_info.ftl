<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-公告详情</title>
<#include "/WEB-INF/templates/common/include.ftl">

<link href="${base}/template/web/css/detail.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
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
  <div class="detail fl">
  <dl>
    <dt><B>${(announcement.title)!""}</B></dt>
    <div class="date">发布时间：${(announcement.createTime?date("yyyy-MM-dd"))!""}  </div>
    <dd>
      <div class="contantInr">
      	${(announcement.content)!""}
      </div>       
    </dd> 
   </dl>                                    
  </div> 
<!--内容结束-->

<!-- 热门商户和最新商户开始  -->
<#include "/WEB-INF/templates/common/hotAndNew_merchant.ftl">
<!-- 热门商户和最新商户结束  -->                    
</div>

<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
</body>
</html>
