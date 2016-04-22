<div class="left" id="leftHeight">
    <ul class="main" id="admin_menu">
      <li  id="checkStand"><u><a class="first dt04" href="${base}/cashdesk_info.action" title="收银台" >收银台</a></u></li>
      <li id="baseInfo"><u><a class="first dt05" href="${base}/home.action" title="基本信息" >基本信息</a></u></li>
      <li><a class="first dt06" title="商家管理">商家管理</a>
        <ul class="mainfirst" id="busyMeg">
          <li><u><a href="${base}/message_manage.action" title="商家介绍">商家介绍</a></u></li>
          <li><u><a href="${base}/merchant_product_list.action" title="商家产品">商家产品</a></u></li>
          <li><u><a href="${base}/merchant_photo_list.action" title="商家相册">商家相册</a></u></li>
          <li><u><a href="${base}/merchant_preferential_list.action" title="商家优惠">商家优惠</a></u></li>
        </ul>
      </li>
      <li> <a class="first dt07" title="交易管理">交易管理</a>
	      <ul class="mainfirst" id="transMeg">		
	          <li><u><a href="${base}/query_trans.action" title="积分返利">积分返利</a></u></li>
	          <li><u><a href="${base}/query_exchange_trans.action" title="积分兑换">积分兑换</a></u></li>
	          <li><u><a href="${base}/query_group_trans.action" title="团购">团购</a></u></li>
              <li><u><a href="${base}/query_presell_trans.action" title="精品预售">精品预售</a></u></li>
	        <#if merchant.activityId?exists && merchant.activityId=="1">
				<li><u><a href="${base}/query_activity_trans.action" title="活动交易查询">活动交易查询</a></u></li>			
			</#if>
	        </ul>  
      </li>
      <#if merchantRole=="-1" || merchantRole=="1">
      <script type="text/javascript" src="${base}/template/common/js/selectLinkFinance.js"></script>   
      <li> <a class="first dt07" title="财务管理">财务管理</a>
	      <ul class="mainfirst" id="finance">		
	          <li><u><a href="${base}/query_trans_finance.action" title="积分返利">积分返利</a></u></li>
	          <li><u><a href="${base}/query_exchange_trans_finance.action" title="积分兑换">积分兑换</a></u></li>
	          <li><u><a href="${base}/query_group_trans_finance.action" title="团购">团购</a></u></li>
              <li><u><a href="${base}/query_presell_trans_finance.action" title="精品预售">精品预售</a></u></li>
	       <!-- <#if merchant.activityId?exists && merchant.activityId=="1">
				<li><u><a href="${base}/query_activity_trans.action" title="活动交易查询">活动交易查询</a></u></li>			
			</#if> -->
	        </ul>  
      </li>
      <#else>
      	  <script type="text/javascript" src="${base}/template/common/js/selectLinkAdmin.js"></script>   
      </#if>
      <!--
      <li> <a class="first dt08" href="#" title="积分查询">积分查询</a></li>
      -->
      <li id="service"><u><a class="first dt09" href="${base}/merchant_complaint_list.action" title="客诉处理" >客诉处理</a></u></li>
      <#if sellerList??>
      	<#assign isDisplay=false>
      	<#list sellerList as seller>
		    <#if seller.sellerType?exists && isDisplay==false && (seller.sellerType == "01" || seller.sellerType == "02" || seller.sellerType == "08") >
		       <#assign isDisplay=true>
		       <li id="approveCenter" ><u><a class="first dt10" href="${base}/toAuthentication.action"  title="积分及团购预售认证中心">认证中心</a></u></li>
		    </#if>
	    </#list>
      </#if>
      <li> <a class="first dt02" title="安全中心">安全中心</a>
      	<ul class="mainfirst" id="softCenter">
          <li><u><a href="${base}/toChangPasswordMerchant.action" title="修改密码">修改密码</a></u></li>
          <!--<li id="addClerkLi"><a href="${base}/to_add_clerk.action" title="添加"><u>添加</u></a></li> -->
          <li id="myClerkLi"><u><a href="${base}/merchant_clerk_list.action" title="我的操作员">我的操作员</a></u></li>
          <li id="myTreasurerLi"><u><a href="${base}/merchant_treasurer_list.action" title="我的财务员">我的财务员</a></u></li>
        </ul>
      </li>
    </ul>
  </div>
  <script type="text/javascript" src="${base}/template/common/js/adminmenu.js"></script>   
