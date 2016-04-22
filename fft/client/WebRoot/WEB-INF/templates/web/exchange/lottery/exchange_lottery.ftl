<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">

<link href="${base}/template/web/css/table.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/lottery.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${base}/template/common/js/method.js"></script>
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>
<script type="text/javascript" src="${base}/template/common/js/form.js"></script>
<link href="${base}/template/common/css/select.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dropkick.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/drop.js"></script>
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<script type="text/javascript">
function checkAndSubmit(){
	var checkR=touzhu();
	if(!checkR){
		return ;
	}
	if(!submitCheck())	
		return ;
	var choseRedBall=	document.getElementById("choseRedBall").value;
	var blueB=	document.getElementById("blueB").value;
	document.getElementById("allBall").value=choseRedBall+"|"+blueB;
	exch.submit();
}

</script> 
<div class="miss" style="display:none" id="show">
	<div id="errorMsg"><h2>抱歉，数量有限，您最多只能购买 <b id="boxnumber"></b> 件</h2></div>
 
	<a href="#" onclick="closed()"></a>
</div>
<div class="box1010 pt10 clear"> 
<div style='color:red;'>${errorMsg?if_exists}</div>
	<#if pager?exists && pager.list?exists && pager.list?has_content >
		<#list pager.list as goodsExchangeRack>
			<#assign lottery=goodsExchangeRack>
		</#list>
<#if lottery?exists && transAddtionalInfoVo?exists && transAddtionalInfoVo.lottery?exists>
	<form id="exch" name="exch" method="post" action="exchange_lottery.action">
	
			 <#assign pointsIsUsable=false>
			        <#assign bankPointsValueMax=0>
			         <#assign fftPointsValueMax=0>
			          <#assign pointsIsUsable="false">
			        <#if lottery.isFftpointcashRatioPricing?exists && lottery.isFftpointcashRatioPricing=="1" && lottery.fftpointcashRatioPricing?exists >
			        	 <#assign pointsIsUsable="true">
			        	  <#assign fftpointsMinMax=lottery.fftpointcashRatioPricing?split('|')>
			        	   <#assign fftPointsValueMin=fftpointsMinMax[0]>
			        	 <#assign fftPointsValueMax=fftpointsMinMax[1]>
			        </#if>
			         <#if lottery.isBankpointcashRatioPricing?exists && lottery.isBankpointcashRatioPricing=="1" && lottery.bankpointcashRatioPricing?exists>
			        	 <#assign pointsIsUsable="true">
			        	 <#assign bankPointsValueMax=0>
			        	  <#assign bankPointsMinMax=lottery.bankpointcashRatioPricing?split('|')>
			        	  <#assign bankPointsValueMin=bankPointsMinMax[0]>
			        	 <#assign bankPointsValueMax=bankPointsMinMax[1]>
			        </#if>
			        <#assign fftPoints=0>
			        <#assign bankPoints="0">  
			        <#if Session?exists && Session.pointsTypePointsAccountMap?exists && Session.pointsTypePointsAccountMap?exists>
			        	<#if Session.pointsTypePointsAccountMap.FFTPlatform?exists && Session.pointsTypePointsAccountMap.FFTPlatform.points?exists>
			          		<#assign fftPoints=Session.pointsTypePointsAccountMap.FFTPlatform.points>
			        	</#if>
			        	
			            <#if Session.pointsTypePointsAccountMap.ZHBank?exists && Session.pointsTypePointsAccountMap.ZHBank.points?exists>
			               <#assign bankPoints=Session.pointsTypePointsAccountMap.ZHBank.points>
			            </#if>
			      </#if>
			     
			      <input type="hidden"    id="fftPoints1" value="${fftPoints?if_exists}" />
			      <input type="hidden"   id="bankPoints1" value="${bankPoints?if_exists}" />
			      
			       <input type="hidden"   id="bankPointsValueMin" value="${bankPointsValueMin?if_exists}" />
			          <input type="hidden"    id="fftPointsValueMin" value="${fftPointsValueMin?if_exists}" />
			      
			           <input type="hidden"   id="bankPointsValueMax" value="${bankPointsValueMax?if_exists}" />
			          <input type="hidden"    id="fftPointsValueMax" value="${fftPointsValueMax?if_exists}" />
			         <input type="hidden"   id="cashFftPointsRatio" value="${cashFftPointsRatio?if_exists}" />
			          <input type="hidden"    id="cashBankPointsRatio" value="${cashBankPointsRatio?if_exists}" />
			           <input type="hidden" name="transDetails.bankPointsValueRealAll" id="bankPointsValueRealAll" />
			          <input type="hidden" name="transDetails.fftPointsValueRealAll" id="fftPointsValueRealAll" />
			  <!--彩票开始-->
			  <div class="lottery fl">
			 <div class="win">
			   <div class="leftball"><img src="${base}/template/web/images/shuangseqiu.png"></div>
			   <div class="rightPeriods">
			     <dl> 
			     <input type="hidden" name="transDetails.goodsRackId" id="radio" value="#{lottery.id?if_exists}" />
			       <input type="hidden" name="transDetails.goodsNumber"  value="1" id="value">
			        <input type="hidden" name="pager.goodsCategoryId"  value="100001005">
			     <input type="hidden" name="transAddtionalInfoVo.lottery.period" id="textfield" value="${transAddtionalInfoVo.lottery.period?if_exists}" />
			     <input type="hidden" name="transAddtionalInfoVo.lottery.winAmount" id="textfield" value="${transAddtionalInfoVo.lottery.winAmount?if_exists}" />
			      <input type="hidden" name="transAddtionalInfoVo.lottery.numCount" id="textfield" value="${transAddtionalInfoVo.lottery.numCount?if_exists}" />
			       <input type="hidden" name="transAddtionalInfoVo.lottery.playType" id="textfield" value="${transAddtionalInfoVo.lottery.playType?if_exists}" />
			        <input type="hidden" name="transAddtionalInfoVo.lottery.lotteryNo" id="textfield" value="${transAddtionalInfoVo.lottery.lotteryNo?if_exists}" />
			       <dt>第  ${transAddtionalInfoVo.lottery.period?if_exists}  期</dt>
			       <dd>双色球大奖大、小奖多，最高奖金${transAddtionalInfoVo.lottery.winAmount?if_exists}万</dd>
			       <dd>每周二、四、日 21：30开奖</dd>
			       <dd>截止时间：2013-02-20 00:00:00 销售时间结束！</dd>
			     </dl>
			   </div>
			 </div>
			 
			 <div class="redbox">
			   <div id="redbox">
			   <a href="javascript:void(0)"><li>1</li></a>
			   <a href="javascript:void(0)"><li>2</li></a>
			   <a href="javascript:void(0)"><li>3</li></a>
			   <a href="javascript:void(0)"><li>4</li></a>
			   <a href="javascript:void(0)"><li>5</li></a>
			   <a href="javascript:void(0)"><li>6</li></a>
			   <a href="javascript:void(0)"><li>7</li></a>
			   <a href="javascript:void(0)"><li>8</li></a>
			   <a href="javascript:void(0)"><li>9</li></a>
			   <a href="javascript:void(0)"><li>10</li></a>
			   <a href="javascript:void(0)"><li>11</li></a>
			   <a href="javascript:void(0)"><li>12</li></a>
			   <a href="javascript:void(0)"><li>13</li></a>
			   <a href="javascript:void(0)"><li>14</li></a>
			   <a href="javascript:void(0)"><li>15</li></a>
			   <a href="javascript:void(0)"><li>16</li></a>
			   <a href="javascript:void(0)"><li>17</li></a>
			   <a href="javascript:void(0)"><li>18</li></a>
			   <a href="javascript:void(0)"><li>19</li></a>
			   <a href="javascript:void(0)"><li>20</li></a>
			   <a href="javascript:void(0)"><li>21</li></a>
			   <a href="javascript:void(0)"><li>22</li></a>
			   <a href="javascript:void(0)"><li>23</li></a>
			   <a href="javascript:void(0)"><li>24</li></a>
			   <a href="javascript:void(0)"><li>25</li></a>
			   <a href="javascript:void(0)"><li>26</li></a>
			   <a href="javascript:void(0)"><li>27</li></a>
			   <a href="javascript:void(0)"><li>28</li></a>
			   <a href="javascript:void(0)"><li>29</li></a>
			   <a href="javascript:void(0)"><li>30</li></a>
			   <a href="javascript:void(0)"><li>31</li></a>
			   <a href="javascript:void(0)"><li>32</li></a>
			   <a href="javascript:void(0)"><li>33</li></a>
			  </div>
			   <div class="select">
			     <div class="wenzi">注：红球必须选择6个</div>
			     <span>您选择了<b id="rednumber">0</b>个红球</span>
			     <a href="javascript:void(0)" onclick="newSelect()">重新选择</a>
			     <a href="javascript:void(0)" onclick="redRandom()"><img src="${base}/template/web/images/redball.png"></a>
			   </div>       
			 </div>
			 
			 <div class="bluebox">
			   <div id="bluebox">
			   <a href="javascript:void(0)"><li>1</li></a>
			   <a href="javascript:void(0)"><li>2</li></a>
			   <a href="javascript:void(0)"><li>3</li></a>
			   <a href="javascript:void(0)"><li>4</li></a>
			   <a href="javascript:void(0)"><li>5</li></a>
			   <a href="javascript:void(0)"><li>6</li></a>
			   <a href="javascript:void(0)"><li>7</li></a>
			   <a href="javascript:void(0)"><li>8</li></a>
			   <a href="javascript:void(0)"><li>9</li></a>
			   <a href="javascript:void(0)"><li>10</li></a>
			   <a href="javascript:void(0)"><li>11</li></a>
			   <a href="javascript:void(0)"><li>12</li></a>
			   <a href="javascript:void(0)"><li>13</li></a>
			   <a href="javascript:void(0)"><li>14</li></a>
			   <a href="javascript:void(0)"><li>15</li></a>
			   <a href="javascript:void(0)"><li>16</li></a> 
			   </div>
			   <div class="select">
			     <div class="wenzi">注：蓝球必须选择1个</div>
			     <a href="javascript:void(0)" onclick="blueRandom()"><img src="${base}/template/web/images/blueball.png"></a>
			   </div>                 
			 </div>
			 <div class="clear"></div>
			 <a href="javascript:void(0)" onclick="newBall()"><div class="touzhu"></div></a>
			 <div class="areawidth">
			   
			   
			   <div class="widthball">
			    <b class="redFont">
			    红球:</b><input type="text" name="transAddtionalInfoVo.lotteryContentRedBall" id="choseRedBall" class="inputRed">
			    蓝球:<input type="text" name="transAddtionalInfoVo.lotteryContentBlueBall" id="blueB" class="inputBlue"> 
			    <input type="hidden" name="transAddtionalInfoVo.lotteryContent" id="allBall"> 
			     <b id="ball"></b>
			   </div>
			   
			   
			 </div> 
			 
			 <!-- hidden elements      start   -->
			 <div style="display:none">
			      		<span>积分：</span> -¥<b id="score">0</b>
			      		<strong id="totleRMB">0</strong>
			      		<span id="price">${lottery.cashPricing?if_exists}</span>
			        	<span id="totleprice">${lottery.cashPricing?if_exists}</span>
			 </div>
			  <div style="display:none">
			  	<input type="radio" name="pointsType" value="bank"  id="card">银行积分：
				<input type="radio" name="pointsType" value="fft">分分通积分
			
			   		<dd>分分通积分+现金兑换：
			   		<b id="usableFftPointsMinShow">10</b>-<b id="usableFftPointsMaxShow">300</b>分+<b id="currencyMinUsedFftPointsShow">10</b>-<b id="currencyMaxUsedFftPointsShow">100</b>元</dd>
			   		<dd>银行积分+现金兑换：<b id="usableBankPointsMinShow">10</b>-<b id="usableBankPointsMaxShow">300</b>分+<b id="currencyMinUsedBankPointsShow">10</b>-<b id="currencyMaxUsedBankPointsShow">100</b>元</dd>
			
			</div>
			<!--  hidden elements     end -->
			  <div class="payselect">
			  <div class="heightmiddle">
			  <div class="selectfont">请选择支付方式：</div>
			  		<form action="#" method="post" class="example_form fl">
			         <select name="trans.payMethod" id="payMethod" class="existing_event"  tableindex="4" onchange=changePayMethod(this.value);>
				          <#if lottery.isCash=="1" >
			             	 <option  value="02">现金（珠海农商银行手机银行卡支付）</option>
			              </#if>
			               <#if lottery.isFftPoint=="1">
			             	<option  value="00" >分分通积分</option>
			              </#if>
			               <#if lottery.isBankPoint=="1" >
			             	  <option  value="01">银行积分</option>
			              </#if>
			              <#if lottery.isFftpointCash=="1" >
			             	<option  value="03">分分通积分+现金</option>
			              </#if>
			               <#if lottery.isBankpointCash=="1" >
			             	<option  value="04">银行积分+现金</option>
			              </#if>
			               <#if lottery.isFftpointcashRatioPricing=="1" >
			             	<option  value="05">分分通积分+现金（比例）</option>
			              </#if>
			               <#if lottery.isBankpointcashRatioPricing=="1" >
			             	<option  value="06">银行积分+现金（比例）</option>
			              </#if>
				     </select>
	</form>
				     </div>
				     <center>
			           <div class="validate mb5" id="formlist" style="display:none"> 
			           <strong>使用积分：
				        <input name="" type="text" class="loginText4" id="points" onchange="sure();">
				        <i>
				        	需使用<b id="pointsType"></b>积分：
				        	<b id="usableFftPointsMin" style="display:none"></b>
					        <b id="usableBankPointsMin" style="display:none"></b>
					       <b>-</b>
					        <b id="usableFftPointsMax" style="display:none"></b>
					        <b id="usableBankPointsMax" style="display:none"></b>
					        	分，您还需要支付现金
					        <b id="currencyMinUsedFftPoints" style="display:none"></b>
					        <b id="currencyMinUsedBankPoints" style="display:none"></b>
					       <b id="connectionSign" > - </b>
					        <b id="currencyMaxUsedFftPoints" style="display:none"></b>
					        <b id="currencyMaxUsedBankPoints" style="display:none"></b>
					       	元
					       	</i>
				       	</strong>
				        </div>
				        </center>             
			</div>
			
			<div class="w150 abtn pb30 pt30">
			 <a href="javascript:checkAndSubmit()" id="touzhu"><div class="textBtn"><B>立即投注</B></div></a>  
			   </div>     
			  </div>
			   <script src="${base}/template/common/js/price.js"></script>
			 <script src="${base}/template/web/js/lottery.js"></script> 
			 </form>
			
<#else>
		  <div class="lottery fl">
	      <div class="payselect">
		      <div class="heightmiddle">
		      		<div class="selectfont">因不能查询到 彩票信息，现在系统不能购买彩票!</div>
		      </div>         
		  </div>
	  	  </div>
		  
		 	     
</#if>
	 
	</#if>  
    <#include "/WEB-INF/templates/common/exch_right.ftl">   
</div>

 <!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</div>

<div id="showDialog" style="display:none">
<div class="dialog">
  <dl>
    <dt><b>分分通提示您：</b><a href="javascript:void(0)" onclick="closebox()"></a></dt>
    <dd id="message"></dd>
    <dd><a href="javascript:void(0)" onclick="closebox()">知道了，关闭</a></dd>
  </dl>
</div>
</div>
<div style="background-color: rgb(0, 0, 0); opacity: 0.7;filter:alpha(opacity=70); position: absolute; z-index: 999; left: 0px; top: 0px; width: 1349px; height:1150px; display:none;" id="pay_mask"></div>
</body>
</html>
