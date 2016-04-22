<div class="left" id="leftHeight">
    <ul class="main" id="admin_menu">
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
      <li> <a class="first dt02" title="安全中心">安全中心</a>
      	<ul class="mainfirst" id="softCenter">
          <li><u><a href="${base}/toChangPasswordMerchant.action" title="修改密码">修改密码</a></u></li>
        </ul>
      </li>
    </ul>
  </div>
  <script type="text/javascript" src="${base}/template/common/js/adminmenu.js"></script>   
