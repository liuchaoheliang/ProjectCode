<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<#include "/WEB-INF/merchant/base/include.ftl">
<script type="text/javascript">



function countFactorageAndPay(points){
	if(points==""){
		$.layer({
			title:['分分通提示您',true],
				time:3,
			area : ['auto','auto'],
			dialog : {msg:'积分返利不能为空',type : 8}	
		});
		return ;
	}
	
	var pointsFactorageRatio=document.getElementById('pointsFactorageRatio').value;
	var payPointsRatio=document.getElementById('payPointsRatio').value;//纯购买积分金额
	var pointsFactorage=points*pointsFactorageRatio;
	var payPoints=points*payPointsRatio;
	
	document.getElementById('pointsFactorageCurrency').innerHTML=pointsFactorage;
	document.getElementById('payPointsCurrency').innerHTML=payPoints;

}
</script>
</head>
<body>
<div class="adminTop">
  <div class="adminTopMiddle">
    <div class="adminTopLogo"><a href="#"><img src="images/adminTopLogo.png"></a></div>
    <div class="adminTopWelcome"><img src="images/adminTopIcon.png">你好：<B>凌统</B>，欢迎使用珠海农商银行管理系统。</div>
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
       <div class="rebateBox">
         <div class="step">
           <li class="step01">输入信息</li>
           <li class="step02">生成订单</li>
           <li class="step03">成功收款</li>
           <li class="colorRed step10">赠送积分</li>
           <li class="step05">积分订单</li>
           <li class="step06">赠送成功</li>
         </div>
         
         <div class="discountFloat">
         <#if errorMessage?exists>${errorMessage?if_exists}</br></#if>
<form action="/createOrderOfSendPoints.action" method="post">
      
      <dl>
       <dt>交易号：</dt>
       <dd>
         <div>
           <#if orderVO?exists>#{orderVO.tran.id?if_exists}</#if>
         </div>
       </dd>
     </dl>
       <input type="hidden" name="orderVO.tran.id" value="<#if orderVO?exists>#{orderVO.tran.id?if_exists}</#if>" />
     <dl>
       <dt>手机号：</dt>
       <dd>
         <div>
         <input type="text" name="orderVO.buyerPhone" value="<#if orderVO?exists>${orderVO.buyerPhone?if_exists}</#if>" />
         </div>
       </dd>
     </dl>
      
     <dl>
       <dt>返利积分：</dt>
       <dd>
         <div>
           <input type="text" name="orderVO.points" value="" onBlur="countFactorageAndPay(this.value)"/>
         </div>       
       </dd>
     </dl> 
     
     
     <dl>
       <dt>赠送积分的金额：</dt>
       <dd>
         <div id="payPointsCurrency">
          
         </div>       
       </dd>
     </dl>  
      <dl>
       <dt>赠送积分的手续费：</dt>
       <dd>
         <div id="pointsFactorageCurrency">
         
         </div>       
       </dd>
     </dl>  
     <dl>
       <dt>卖家账户：</dt>
       <dd>
         <div>
         
         <select name="orderVO.sellerAccountIdOfCurrentUsed">
           <#if cashdesk.sellerAccountList?has_content>
				<#list cashdesk.sellerAccountList as sellerAccount>
					<option value="#{sellerAccount.id}" />${sellerAccount.accountNumber}<br/>
				</#list>
			</#if>
		</select>
         </div>       
       </dd>
     </dl>   
     
     
       <#assign points_factorage_ratio=1>
       <#assign pay_points_ratio=1>
       		<#list cashdesk.sellerDepositChannelList as sellerDepositChannel>
       			<#if sellerDepositChannel.sellerRule.sellerRuleDetails?exists&&sellerDepositChannel.sellerRule.sellerRuleDetails?has_content>
       				<#list sellerDepositChannel.sellerRule.sellerRuleDetails as sellerRuleDetail>
       					<#if sellerRuleDetail.ruleType=="002">
       						 <#assign points_factorage_ratio=sellerRuleDetail.formula?number>
       					</#if>
       					<#if sellerRuleDetail.ruleType=="001">
       						 <#assign pay_points_ratio=sellerRuleDetail.formula?number>
       					</#if>
       				</#list>
       			</#if>
			</#list>
			 <input type="hidden" id="pointsFactorageRatio" value="${points_factorage_ratio}"/>
			 <input type="hidden" id="payPointsRatio" value="${pay_points_ratio}"/>
         

      <div class="textBtn"><input type="submit" value="确定"/></div>
     </form>
      </div>
                     
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
