<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<#include "/WEB-INF/merchant/base/include.ftl">

<#include "/WEB-INF/merchant/base/extInclude.ftl">

<script type="text/javascript" src="${base}/template/common/js/seller/cashdeskgrid/cashdeskgrid.js"></script>

</head>
<body>
<div class="adminTop">
  <div class="adminTopMiddle">
    <div class="adminTopLogo"><a href="#"><img src="${base}/template/common/images/adminTopLogo.png"></a></div>
    <div class="adminTopWelcome"><img src="${base}/template/common/images/adminTopIcon.png">你好：<B>凌统</B>，欢迎使用珠海农商银行管理系统。</div>
    <div class="adminToday">今天是：2012年12月6日 星期二</div>
  </div>
  
  <div class="adminTopRight">
    <div class="adminTopMenu"><a href="#">管理主页</a> | <a href="#">安全退出</a></div>
  </div>  
  
  <div class="adminMenu">
    <ul>
    <img src="images/syt.png">
    <li><a href="#" class="cure">基本信息</a></li>
    <li><a href="#">首页管理</a></li>
    <li><a href="#">信息管理</a></li>
    <li><a href="#">交易管理</a></li>
    <li><a href="#">客诉处理</a></li>
    <li><a href="#">安全中心</a></li>
    </ul>
  </div>
</div>


<div class="adminRight">
  <div class="adminBorderTop">
    <div class="adminBorderTopLeft"></div>
    <div class="adminBorderTopTitle">
      <div class="adminBorderTopTitleLeft"></div>
      <div class="adminBorderTopTitleMiddle">收银台</div>
      <div class="adminBorderTopTitleRight"></div>
    </div>
    <div class="adminBorderTopRight"></div>
  </div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F7F8F9">
  <tr>
    <td class="adminBorderMiddleLeft"></td>
    <td>
     <div class="adminBox">
       <div class="cashierLeft">
       <div>
       		<#assign sendPoints=false>
       		<#if cashdesk?exists && cashdesk.sellerDepositChannelList?exists>
       			<#if cashdesk.sellerDepositChannelList?has_content>
		       		<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
		       			<#if sellerDepositChannel.sellerRule.sellerRuleType=="00">
		       				<#assign sendPoints=true>
		       			</#if>
						收款渠道：${sellerDepositChannel.fundsChannel.channelFullName?if_exists}<br>
						渠道规则：${sellerDepositChannel.sellerRule.ruleDesc?if_exists}<br>
					</#list>
				</#if>	
			</#if>	
			
			该渠道的所有账户信息：</br>
			 <#if cashdesk.sellerAccountList?has_content>
					<#list cashdesk.sellerAccountList as sellerAccount>
						<span>账户名：${sellerAccount.accountName?if_exists}</span>  
						<span>账户号 ：${sellerAccount.accountNumber?if_exists}</span> <br/>
					</#list>
			</#if>
       </div>
       <a href="/add_goods_page.action" class="BL" title="下单" target="_blank" ><img src="${base}/template/admin/images/shoukuan.png"></a>
       
       <#if sendPoints>
       <a href="/sendPointsPage.action" class="BL" title="下单" target="_blank" > <img src="${base}/template/admin/images/zsjf.png"></a>
       </#if>
       
       <div id="testG"></div>
    <div class="zuijin">最近10笔交易</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tableA">
              <th width="21%" height="25"><B>订单号</B></th>
              <th width="18%"><B>手机号</B></th>
              <th width="20%"><B>交易时间</B></th>
              <th width="20%"><B>交易金额</B></th>
              <th width="21%"><B>优惠方式</B></th>
              </tr>    
      <tr>
        <td height="20"><B>1423552525235</B></td>
        <td width="18%"><b>343453453455345</b></td>
        <td width="20%"><b>2012-12-07 15:22:22</b></td>
        <td width="20%"><B>1204</B></td>
        <td width="21%"><B>返利积分6%</B></td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      </table>       
       </div>
       <div class="cashierRight"></div>         
       
       
     </div>          
    </td>
    <td class="adminBorderMiddleRight"></td>
  </tr>
</table>
<div class="adminBorderBottom">
  <div class="adminBorderBottomLeft"></div>
  <div class="adminBorderBottomRight"></div>
</div>
</div>

<div><!--数据图表--></div>

<div class="adminFoot">
  <div class="adminCopy"><span class="adminFootLogo"></span>Copyright 2012 Froad All Right reserved.</div>
</div>
</body>
</html>
