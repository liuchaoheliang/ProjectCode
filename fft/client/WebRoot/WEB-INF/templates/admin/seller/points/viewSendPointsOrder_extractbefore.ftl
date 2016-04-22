<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<#include "/WEB-INF/merchant/base/include.ftl">

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
           <li class="step04">赠送积分</li>
           <li class="colorRed step11">积分订单</li>
           <li class="step06">赠送成功</li>
         </div>
         
         <div class="discountFloat">
         	<#if orderVO?exists>
		         <form action="/payTransPoints.action" method="post">
				      <dl>
				       <dt>积分订单号：</dt>
				       <dd>
				         <div>
				           ${orderVO.transPoints.id?if_exists?c}
				         </div>
				       </dd>
				     </dl>
				     <input type="hidden" name="orderVO.transPoints.id" value="${orderVO.transPoints.id?if_exists?c}" />
				      <dl>
				       <dt>时间：</dt>
				       <dd>
				         <div>
				           111
				         </div>
				       </dd>
				     </dl>          
				      <dl>
				       <dt>手机号：</dt>
				       <dd>
				         <div>
				         <span>${orderVO.buyerPhone?if_exists}</span>
				         </div>
				       </dd>
				     </dl>   
				     
				      <dl>
				       <dt>返利积分：</dt>
				       <dd>
				         <div>
				           <span>${orderVO.transPoints.pointsValueRealAll?if_exists}</span>
				         </div>       
				       </dd>
				     </dl>     
				
				      <dl>
				       <dt>积分的金额：</dt>
				       <dd>
				         <div>
				           <span>${orderVO.transPoints.sellPointCurreVal?if_exists}元</span>
				         </div>
				       </dd>
				     </dl>
				      <dl>
				       <dt>积分的手续费：</dt>
				       <dd>
				         <div>
				          <span>${orderVO.transPoints.sellFacPointCurreVal?if_exists}元</span>
				         </div>
				       </dd>
				     </dl>          
				    
				     <dl>
				       <dt>货币单位：</dt>
				       <dd>
				         <div>
				           <span>RMB</span>
				         </div>       
				       </dd>
				     </dl>            
				    
				     <dl>
				       <dt>卖家账户：</dt>
				       <dd>
				         <div>
				           <span>${orderVO.sellerAccountNumberOfCurrentUsed?if_exists}</span>
				         </div>       
				       </dd>
				     </dl> 
				      
				
				      <div class="textBtn"><input type="submit" value="赠送"/></div>
		     </form>
		    </#if>
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
