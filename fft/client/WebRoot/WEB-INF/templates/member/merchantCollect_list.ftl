<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通查询-我的收藏</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/point.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/member/css/imglist.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/member/js/scroll.js"></script>
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

<!--广告栏开始-->
<div class="ad"><img src="${base}/template/member/img/123213.png"></div>
<!--广告栏结束-->

<div class="middleBox">
<!--会员个人信息菜单开始-->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!--会员个人信息菜单结束-->
  <div class="rightBox imgList" id="rightHeight"> 
  
  <#if (memberCollectPager.list?size < 1)>  
    <!--无收藏开始-->
    <div class="point"><span>您暂时还没有收藏商家，<B>我们为您推荐了一些商家：</B></span></div>
    <div>
      <dl>
        <dd class="linkBlack">
          <#list newMerchantPager.list as merchant>
    			<li>
    				<a href="merchant_info.action?id=${(merchant.id)?c}" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(merchant.mstoreLogoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>

    				<div> <a href="merchant_info.action?id=${(merchant.id)?c}"  target="_blank">${(merchant.mstoreShortName?html)!""}</a></div>
    				<div class="fanli"><#if merchant?exists && merchant.preferentialType=="1">折扣优惠 ${(merchant.preferentialRate)!""}&nbsp;折<#else>积分返利 ${(merchant.pointRate)!""}&nbsp;%</#if></div>
    			</li>
    		</#list>
        </dd>
      </dl>
    </div>
    <!--无收藏结束--> 
    <#else>
    <!-- 有收藏开始 -->
    <div id="menuHeight" style="height:405px;cursor:pointer;overflow:hidden; clear:both">
      <dl>
        <dd class="linkBlack">
        <#list memberCollectPager.list as memberCollect>
    		<li>
				<a href="merchant_info.action?id=${(memberCollect.merchantId)!""}" target="_blank"><img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(memberCollect.merchant.mstoreLogoUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'"></a>
				<div>
					<a href="merchant_info.action?id=${(memberCollect.merchantId)!""}"  target="_blank">${(memberCollect.merchant.mstoreShortName?html)!""}</a></div>
				<div class="fanli"><a href="member_favorite_delete.action?id=${memberCollect.id?c}&merchantId=${(memberCollect.merchantId)!""}" class="ajaxlink" title="取消收藏" style="color:red;">取消收藏</a> </div>
			</li>
		</#list>
        </dd>
      </dl>
    </div>
     	<#if (memberCollectPager.list?size>10)>
	   		<div class="jiazai" id="menu">加载更多</div>   
	   </#if>
    <!-- 有收藏结束 --> 
   </#if>  
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
