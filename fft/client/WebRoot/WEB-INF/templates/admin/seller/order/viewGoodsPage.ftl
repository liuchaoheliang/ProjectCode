<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收银台-手机银行收款确认支付</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/My97DatePicker/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="${base}/template/common/js/height.js"></script> -->
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/list.js"></script>

<script type="text/javascript">


function checkAndSubmit(){
	var senda = $("#senda");
	senda.attr("href","javascript:void(0);");
	senda.find("div").attr("class","gryBtn");	
	senda.find('B').html("请等待...");
	pay_transaction_form.submit();

//	var mobilecheckResult=document.getElementById('mobilecheckResult').value;
//	var namecheckResult=document.getElementById('namecheckResult').value;
//	var numebercheckResult=document.getElementById('numebercheckResult').value;
//	var pricecheckResult=document.getElementById('pricecheckResult').value;
//	var checkResult=false;
//	if(mobilecheckResult=="true" && namecheckResult=="true" && numebercheckResult=="true" && pricecheckResult=="true" ){
//		checkResult=true;
//	}
//	if(checkResult)
//		create_order_form.submit();
//	else
//		alert("请把信息填写正确!");
}



</script>

</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 
<!--头部开始-->
<!-- 商家管理菜单结束 -->
  <div class="moneybg">
  <div class="money" id="rightHeight">
  <div class="stepadmin"></div>
    <div class="step">
      <li class="step01">输入信息</li>
      <li class="step08 colorRed">生成订单</li>
      <li class="step03">成功收款</li>
    </div>  
   <form id="pay_transaction_form" action="pay_transaction.action" method="get">
    <div class="inforBox" id="admin">
        <dl>
          <dt>订单号：</dt>
          <dd>
            <div> ${trans.id?if_exists?c} </div>
          </dd>
        </dl>
         <input type="hidden" name="trans.id"  value="${trans.id?if_exists?c}" />    
        <dl>
          <dt>时间：</dt>
          <dd>
            <div> ${trans.createTime?if_exists} </div>
          </dd>
        </dl>        
        <dl>
          <dt>支付方式：</dt>
          <dd>
            <div> 
            	现金
            </div>
          </dd>
        </dl>
        <dl>
          <dt>手机号：</dt>
          <dd>
            <div>${buyerPhone?if_exists}</div>
          </dd>
        </dl>
        <dl>
          <dt>交易品名称：</dt>
          <dd>
            <div>${tranDetail.goods.goodsName?if_exists}</div>
          </dd>
        </dl>        
        <dl>
          <dt>数量：</dt>
          <dd>
            <div><span class="redFont">${tranDetail.goodsNumber?if_exists}</div>
          </dd>
        </dl>
        <dl>
          <dt>返利积分：</dt>
          <dd>
            <div> ${trans.fftPointsValueRealAll?if_exists} </div>
          </dd>
        </dl>  
        <dl>
          <dt>实收款：</dt>
          <dd>
            <div> ${trans.currencyValueRealAll?if_exists}元 </div>
          </dd>
        </dl>                    
        <dl>
          <dt>收款账户：</dt>
          <dd>
            <div>
             		<#assign userAccountNoShow="">
					<#if seller.sellerChannelList[0].accountNumber?exists>
						<#assign userAccountNo=seller.sellerChannelList[0].accountNumber>
				      	<#assign userAccountNoBefore=userAccountNo?substring(0,4)>
				      	<#assign userAccountNoAfter=userAccountNo?substring(userAccountNo?length-4)>
				      	<#assign userAccountNoShow=userAccountNoBefore+"*******"+userAccountNoAfter>
			      	</#if>
					${seller.sellerChannelList[0].accountName?if_exists}(${userAccountNoShow?if_exists})
             </div>
          </dd>
        </dl>
                
        <div class="ml145 mt5"> 
          <a href="javascript:checkAndSubmit()" id ="senda" >
          <div class="textBtn"><B>支付</B></div>
          </a> 
        </div> 
      <!--  <div class="fhuixiugai"><a href="#">返回修改</a></div>         -->      
    </div>
</form>
  </div>
  </div>
</body>
</html>
