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
     <div class="discountBox">
         <div class="step">
           <li class="step01">输入信息</li>
           <li class="colorRed step08">生成订单</li>
           <li class="step03">成功收款</li>
         </div>
         
         <div class="discountFloat">
         
        <#if orderVO?exists>
         <form action="/pay_transaction.action" method="post">
      <dl>
       <dt>订单号：</dt>
       <dd>
         <div>
         #{orderVO.tran.id?if_exists}
         	
         </div>
       </dd>
     </dl>
     <input type="hidden" name="orderVO.tran.id" value=#{orderVO.tran.id?if_exists} />
     <input type="hidden" name="orderVO.trackNo" value=${orderVO.tran.trackNo?if_exists} />
      <dl>
       <dt>时间：</dt>
       <dd>
         <div>
           <span>${orderVO.tran.createTime?if_exists}</span>
         </div>
       </dd>
     </dl>          
      <dl>
       <dt>状态：</dt>
       <dd>
         <div>
           <h2>待付款</h2>
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
     
     
     <#if orderVO.allTranDetails?has_content>
	     <#list orderVO.allTranDetails as tranDetail>
		      <dl>
		       <dt>交易品名称：</dt>
		       <dd>
		         <div>
		           <span>${tranDetail.tranGoods.transGoodsDisplay?if_exists}</span>
		         </div>
		       </dd>
		     </dl>
		      <dl>
		       <dt>交易品数量：</dt>
		       <dd>
		         <div>
		           <h2>${tranDetail.tranGoods.transGoodsAmount?if_exists}</h2>
		         </div>
		       </dd>
		     </dl>          
		     <dl>
		       <dt>原价：</dt>
		       <dd>
		         <div>
		           <span>${tranDetail.currencyValue?if_exists}元</span>
		         </div>       
		       </dd>
		     </dl>  
	    </#list> 
     </#if>
     
         
     <dl>
       <dt>优惠方式：</dt>
       <dd>
         <div>
           <span>全款9折</span>
         </div>       
       </dd>
     </dl>   
     <dl>
       <dt>支付方式：</dt>
       <dd>
         <div>
           <span>现金（珠海农商银行手机银行卡支付）</span>
         </div>       
       </dd>
     </dl> 
     <dl>
       <dt>实收款：</dt>
       <dd>
         <div>
           <span>${orderVO.tran.currencyValueRealAll?if_exists}元</span>
         </div>       
       </dd>
     </dl>  
     <dl>
       <dt>卖家收款账户：</dt>
       <dd>
         <div>
           <span>${orderVO.sellerAccountNumberOfCurrentUsed?if_exists}</span>
         </div>       
       </dd>
     </dl>       

      <div class="textBtn">
      <input type="submit" value="去支付"/>
      </div>
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
