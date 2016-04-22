<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-返利积分首页</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/css/banner.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/exchangelist.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/seller.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/select3.css" rel="stylesheet" type="text/css" />
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


<div class="box1010 pt10">
<!--幻灯片开始-->
  <div class="banner fl">
    <div id="player" >
      <ul class="Limg">
       <#list advertList as advert>
	      	<#if advert_index gt 3>
	      		<#break>
	      	</#if>
        <li>
	        <#if advert.link != "">
	        	<a href="${advert.link}" <#if advert.isBlankTarge=='1'> target="_blank"</#if> >
	        </#if>
	        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(advert.images)}"/></a>
	    </li>
      	</#list>
      </ul>
      <div class="Nubbt"><span class="on">1</span><span >2</span><span >3</span><span >4</span></div>
      <script src="${base}/template/web/js/banner.js"></script>                   
    </div>
  </div>
<!--幻灯片结束-->  

<!--搜索开始-->
  <div class="search fl ml10">
  <dl>
    <dt><i>商家</i>搜索：</dt>
  </dl>
    <li><b>商圈：</b>
      <span class="fl" id="uboxstyle">
        <select name="selectTagDistrictBId" id="selectTagDistrictBId">
          <option value="">所有商圈</option>
	        <#list allTagDistrictBList as tagDistrictB>
	        	<option value="${tagDistrictB.id}">${tagDistrictB.tagValue}</option>
	        </#list>
        </select>
      </span>
    </li>

    <li><b>分类：</b>
      <span class="fl" id="uboxstyle">
        <select name="selectTagClassifyAId" id="selectTagClassifyAId">
          <option value="">所有分类</option>
          <#list allTagClassifyAList as tagClassifyA>
        	<option value="${tagClassifyA.id}">${tagClassifyA.tagValue}</option>
        </#list>
        </select>
      </span>
    </li>  
    
    <li><div class="searchGlass"></div><a href="javascript:searchMerchantIndex(2);"><img src="${base}/template/web/images/SchBtn.png"></a></li>    
  </div>
<!--搜索结束-->
</div>  

<div class="box1010 pt10 clear">
<form id="listForm" action="exchange_index.action" method="post">
<!--列表开始-->
<!--列表开始-->
  <div class="exchange fl">
  	<div class="list"  id="ashowlist">
  	 <#if pager?exists && pager.list?exists>
  	 <#list pager.list as goodsExchangeRack>  	 	
  	 <#if goodsExchangeRack.goods?exists && goodsExchangeRack.goods.goodsCategory?exists && goodsExchangeRack.goods.goodsCategory.name?exists> 
      	  <#if goodsExchangeRack.goods.goodsCategory.name=="彩票">
	      <a href="exchange_lottery_page.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}">
		      <li>
			        <dt class="shower">
				        <!-- 价格开始-->
				        <#if goodsExchangeRack.isCash=="1" >
					       	<p>
					      		<#assign cashPricing=goodsExchangeRack.cashPricing>
					    		<span>现金：<B>${cashPricing?if_exists}</B>元 </span>
					   		</p>
					    </#if>
				        
					    <#if goodsExchangeRack.isFftPoint=="1" >
					       	<p>
					       		<#assign fftpoints=goodsExchangeRack.fftPointPricing>
					    		<span>分分通积分：<B>${fftpoints?if_exists}</B>分 </span>
					    	</p>
					    </#if>
				       
				        <#if goodsExchangeRack.isBankPoint=="1" >
				        	<p>
				        		<#assign bankPoint=goodsExchangeRack.bankPointPricing>
				       			<span>银行积分：<B>${bankPoint?if_exists}</B>分 </span>
				       		</p>
				       </#if>
				      
				       <#if goodsExchangeRack.isFftpointCash=="1" >
				          		<p>
				        			<#assign fftpointsAndCash=goodsExchangeRack.fftpointCashPricing>
				        			<#assign fftpointsAndCashArray=fftpointsAndCash?split('|')>
				       				<span>分分通积分：<B>${fftpointsAndCashArray[0]?if_exists}</B>分 + <B>${fftpointsAndCashArray[1]?if_exists}</B>元</span>
				       			</p>
				       </#if>
				       
				       <#if goodsExchangeRack.isBankpointCash=="1" >
				          		<p>
				        			<#assign bankpointsAndCash=goodsExchangeRack.bankpointCashPricing>
				        			<#assign bankpointsAndCashArray=bankpointsAndCash?split('|')>
				        			<span>银行积分：<B>${bankpointsAndCashArray[0]?if_exists}</B>分 + <B>${bankpointsAndCashArray[1]?if_exists}</B>元</span>
				       			</p>
				       </#if>
				       
				       <#if goodsExchangeRack.goods.goodsCategory.name=="积分提现" && goodsExchangeRack.isCash=="1" >
				          		<p>
				        			<span>分分通积分：<B>1分 可以提现 </b> <B>${goodsExchangeRack.cashPricing?if_exists}</B>元</span>
				       			</p>
				       </#if>
			        <!-- 价格结束-->
			        </dt>		        
			        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${goodsExchangeRack.goods.sourceUrl?if_exists}">
	 				<div class="hotTitle">${goodsExchangeRack.goods.goodsName?if_exists}</div>
					<div class="boxInfor">${goodsExchangeRack.goods.remark?if_exists}</div>
			        <div class="btnImg"><img src="${base}/template/web/images/ljdh.png"></div>
		      </li>
	      </a>
	      </#if>
	    <#if goodsExchangeRack.goods.goodsCategory.name=="话费充值">
<a href="exchange_recharge_phone_page.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}&pager.cashPricing=#{goodsExchangeRack.cashPricing?number}">
	      <li>
		        <dt class="shower">
			        <!-- 价格开始-->
			        <#if goodsExchangeRack.isCash=="1" >
				       	<p>
				      		<#assign cashPricing=goodsExchangeRack.cashPricing>
				    		<span>现金：<B>${cashPricing?if_exists}</B>元 </span>
				   		</p>
				    </#if>
			        
				    <#if goodsExchangeRack.isFftPoint=="1" >
				       	<p>
				       		<#assign fftpoints=goodsExchangeRack.fftPointPricing>
				    		<span>分分通积分：<B>${fftpoints?if_exists}</B>分 </span>
				    	</p>
				    </#if>
			       
			        <#if goodsExchangeRack.isBankPoint=="1" >
			        	<p>
			        		<#assign bankPoint=goodsExchangeRack.bankPointPricing>
			       			<span>银行积分：<B>${bankPoint?if_exists}</B>分 </span>
			       		</p>
			       </#if>
			      
			       <#if goodsExchangeRack.isFftpointCash=="1" >
			          		<p>
			        			<#assign fftpointsAndCash=goodsExchangeRack.fftpointCashPricing>
			        			<#assign fftpointsAndCashArray=fftpointsAndCash?split('|')>
			       				<span>分分通积分：<B>${fftpointsAndCashArray[0]?if_exists}</B>分 + <B>${fftpointsAndCashArray[1]?if_exists}</B>元</span>
			       			</p>
			       </#if>
			       
			       <#if goodsExchangeRack.isBankpointCash=="1" >
			          		<p>
			        			<#assign bankpointsAndCash=goodsExchangeRack.bankpointCashPricing>
			        			<#assign bankpointsAndCashArray=bankpointsAndCash?split('|')>
			        			<span>银行积分：<B>${bankpointsAndCashArray[0]?if_exists}</B>分 + <B>${bankpointsAndCashArray[1]?if_exists}</B>元</span>
			       			</p>
			       </#if>
			       
			       <#if goodsExchangeRack.goods.goodsCategory.name=="积分提现" && goodsExchangeRack.isCash=="1" >
			          		<p>
			        			<span>分分通积分：<B>1分 可以提现 </b> <B>${goodsExchangeRack.cashPricing?if_exists}</B>元</span>
			       			</p>
			       </#if>
		        <!-- 价格结束-->
		        </dt>				
			    <#if goodsExchangeRack.goodsExchangeRackImages?exists>
 					 <#assign aa = goodsExchangeRack.goodsExchangeRackImages?size>
 					 <#if aa gt 0>
 					 	<#list goodsExchangeRack.goodsExchangeRackImages as image>
				          <#if image_index gt 0>
				          		<#break>
				          </#if>
						  <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${image.imagesUrl?if_exists}">
				     	</#list>
				     <#else>
				     	  <img src="${base}/template/common/images/moren.gif">		     	
				     </#if> 					 
			    </#if>
 				
 				<div class="hotTitle">${goodsExchangeRack.goods.goodsName?if_exists}</div>
				<div class="boxInfor">${goodsExchangeRack.goods.remark?if_exists}</div>
		        <div class="btnImg"><img src="${base}/template/web/images/ljdh.png"></div>
	      </li> 
     	</a>     
     	</#if>
     	
     	<#if goodsExchangeRack.goods.goodsCategory.name=="积分提现">
      	<a href="carry_index.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}">
	      <li>
		        <dt class="shower">
			        <!-- 价格开始-->
			        <#if goodsExchangeRack.isCash=="1" >
				       	<p>
				      		<#assign cashPricing=goodsExchangeRack.cashPricing>
				    		<span>现金：<B>${cashPricing?if_exists}</B>元 </span>
				   		</p>
				    </#if>
			        
				    <#if goodsExchangeRack.isFftPoint=="1" >
				       	<p>
				       		<#assign fftpoints=goodsExchangeRack.fftPointPricing>
				    		<span>分分通积分：<B>${fftpoints?if_exists}</B>分 </span>
				    	</p>
				    </#if>
			       
			        <#if goodsExchangeRack.isBankPoint=="1" >
			        	<p>
			        		<#assign bankPoint=goodsExchangeRack.bankPointPricing>
			       			<span>银行积分：<B>${bankPoint?if_exists}</B>分 </span>
			       		</p>
			       </#if>
			      
			       <#if goodsExchangeRack.isFftpointCash=="1" >
			          		<p>
			        			<#assign fftpointsAndCash=goodsExchangeRack.fftpointCashPricing>
			        			<#assign fftpointsAndCashArray=fftpointsAndCash?split('|')>
			       				<span>分分通积分：<B>${fftpointsAndCashArray[0]?if_exists}</B>分 + <B>${fftpointsAndCashArray[1]?if_exists}</B>元</span>
			       			</p>
			       </#if>
			       
			       <#if goodsExchangeRack.isBankpointCash=="1" >
			          		<p>
			        			<#assign bankpointsAndCash=goodsExchangeRack.bankpointCashPricing>
			        			<#assign bankpointsAndCashArray=bankpointsAndCash?split('|')>
			        			<span>银行积分：<B>${bankpointsAndCashArray[0]?if_exists}</B>分 + <B>${bankpointsAndCashArray[1]?if_exists}</B>元</span>
			       			</p>
			       </#if>
			       
			       <#if goodsExchangeRack.goods.goodsCategory.name=="积分提现" && goodsExchangeRack.isCash=="1" >
			          		<p>
			        			<span>分分通积分：<B>1分 可以提现 </b> <B>${goodsExchangeRack.cashPricing?if_exists}</B>元</span>
			       			</p>
			       </#if>
		        <!-- 价格结束-->
		        </dt>
		        
		        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${goodsExchangeRack.goods.sourceUrl?if_exists}">
 				<div class="hotTitle">${goodsExchangeRack.goods.goodsName?if_exists}</div>
				<div class="boxInfor">${goodsExchangeRack.goods.remark?if_exists}</div>
		        <div class="btnImg"><img src="${base}/template/web/images/ljdh.png"></div>
	      </li> 
     	</a>     
     	</#if>
     	
     	<#if goodsExchangeRack.goods.goodsCategory.name=="农特产品">
      	<a href="local_goods_exch_detail.action?pager.goodsCategoryId=#{goodsExchangeRack.goods.goodsCategory.id?if_exists}">
	      <li>
		        <dt class="shower">
			        <!-- 价格开始-->
			        <#if goodsExchangeRack.isCash=="1" >
				       	<p>
				      		<#assign cashPricing=goodsExchangeRack.cashPricing>
				    		<span>现金：<B>${cashPricing?if_exists}</B>元 </span>
				   		</p>
				    </#if>
			        
				    <#if goodsExchangeRack.isFftPoint=="1" >
				       	<p>
				       		<#assign fftpoints=goodsExchangeRack.fftPointPricing>
				    		<span>分分通积分：<B>${fftpoints?if_exists}</B>分 </span>
				    	</p>
				    </#if>
			       
			        <#if goodsExchangeRack.isBankPoint=="1" >
			        	<p>
			        		<#assign bankPoint=goodsExchangeRack.bankPointPricing>
			       			<span>银行积分：<B>${bankPoint?if_exists}</B>分 </span>
			       		</p>
			       </#if>
			      
			       <#if goodsExchangeRack.isFftpointCash=="1" >
			          		<p>
			        			<#assign fftpointsAndCash=goodsExchangeRack.fftpointCashPricing>
			        			<#assign fftpointsAndCashArray=fftpointsAndCash?split('|')>
			       				<span>分分通积分：<B>${fftpointsAndCashArray[0]?if_exists}</B>分 + <B>${fftpointsAndCashArray[1]?if_exists}</B>元</span>
			       			</p>
			       </#if>
			       
			       <#if goodsExchangeRack.isBankpointCash=="1" >
			          		<p>
			        			<#assign bankpointsAndCash=goodsExchangeRack.bankpointCashPricing>
			        			<#assign bankpointsAndCashArray=bankpointsAndCash?split('|')>
			        			<span>银行积分：<B>${bankpointsAndCashArray[0]?if_exists}</B>分 + <B>${bankpointsAndCashArray[1]?if_exists}</B>元</span>
			       			</p>
			       </#if>
			       
			       <#if goodsExchangeRack.goods.goodsCategory.name=="积分提现" && goodsExchangeRack.isCash=="1" >
			          		<p>
			        			<span>分分通积分：<B>1分 可以提现 </b> <B>${goodsExchangeRack.cashPricing?if_exists}</B>元</span>
			       			</p>
			       </#if>
		        <!-- 价格结束-->
		        </dt>
		        
		        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${goodsExchangeRack.goods.sourceUrl?if_exists}">
 				<div class="hotTitle">${goodsExchangeRack.goods.goodsName?if_exists}</div>
				<div class="boxInfor">${goodsExchangeRack.goods.remark?if_exists}</div>
		        <div class="btnImg"><img src="${base}/template/web/images/ljdh.png"></div>
	      </li>
     	</a>
     	</#if>
      	 			
      </#if>
      
 	</#list> 
 	</#if>  
    </div>    
    <div class="page">
       <#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       <#else>
       	 	没有找到你想要的商品!	
       </#if>     
    </div>
    <script src="${base}/template/web/js/pointshow.js"></script>                               
  </div> 
<!--列表结束-->

  <#include "/WEB-INF/templates/common/exch_right.ftl">  
</form>                   
</div>

<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>   
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
