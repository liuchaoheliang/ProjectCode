<#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"]>
<div class="headtop">
  <div class="headtopmain">
	<#if login_identification?exists ><#-- 用户已登录 -->
	  	<div class="headname fl ml10">
	    	<p>
	    		<b>您好：${login_identification.loginID}</b><span class="points">
	    		您的积分：<b>${login_identification.froadPoints!'0'}</b>分 </span>
	    	</p>   
	    </div>   
	    <div class="headphone fl ml10">客服电话：966866</div>
	    <div class="headlist fr mr20">
			<a href="${base}/sso/core/logout.jhtml">安全退出</a><span></span>
			<#--<a href="${base}/common/logout.jhtml">安全退出</a><span></span>-->
	    </div> 
    <#else><#-- elseif商户登录 -->
    	<@shiro.guest><#-- 未登录 -->
    		<#--sso-->
    		<#include "/include/system/sso/sso_ajax.ftl">
    		
    		<div class="headphone fl ml10">客服电话：966866</div>
    		<div class="headlist fr mr20">
			    <a href="${base}/shop/login/index.jhtml">用户登录</a><span>|</span>
			    <a href="${base}/shop/register/index.jhtml">注册</a><span>|</span>
			    <a href="${base}/merchant/login/index.jhtml">银行登录</a>
	    	</div> 
    	</@shiro.guest>
    	<#--商户已登录-->
    	<@shiro.user>
    		<div class="headname fl ml10">
	    		<p>
	    			您好：
	    			<b><@shiro.principal /></b>
	    		</p>   
	    	</div>  
    		<div class="headphone fl ml10">客服电话：966866</div>
    		
    		<div class="headlist fr mr20">
				<a href="${base}/merchant/login/logout.jhtml">安全退出</a><span></span>
	    	</div> 
    	</@shiro.user>
   	</#if>
  </div>
</div>
<div class="navBox">
  <div class="logo">
    <a href="${base}"><img src="${base}/resources/common/images/logo.png"/></a>
  </div> 
  <div class="nav">
    <ul>
      <li>
        <a href="${base}">首页</a>
      </li> 
      <li><a href="http://app1.o2obill.com:8080/o2obill_website/store.html" target="_blank">积分商城</a></li>
      <#--<li><a href="http://app1.o2obill.com:8080/o2obill_website/rebate.jhtml" target="_blank">购物返利</a></li>-->
      <li>	
      	<#if login_identification?exists >
	      	<a class="center" href="${base}/member/index.jhtml">个人中心</a>
       <#else>
       		<@shiro.guest>
    		<a class="center" href="${base}/member/index.jhtml">个人中心</a>
    	</@shiro.guest>
    	
    	<#--登录用户-->
    	<@shiro.user>
    		<a class="center" href="${base}/merchant/index.jhtml">个人中心</a>
    	</@shiro.user>
       </#if>
      </li> 
      <li>
        <a href="${base}/shop/helper/index.jhtml">用户指引</a>
      </li> 
    </ul> 
    <div class="bg"></div>
  </div>
</div>