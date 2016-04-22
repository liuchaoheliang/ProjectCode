<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通-彩票</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/web/css/table.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/lottery.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${base}/template/common/js/method.js"></script>


<script type="text/javascript">
$(document).ready( function() {

	var myPeriod = $("#myPeriod");
	var myEndTime = $("#myEndTime");
	var period = $("#period");
	
	$.ajax({
			url: "ajax_lottery_period.action",
			dataType: "json",
			beforeSend: function() {
				 myPeriod.html('<b style="font-size:18px; color:#0C0">  彩票期号查询中...  </b>');
			},
			success: function(data) {
				myPeriod.text("");
				myEndTime.text("");
				if(data.status == "success"){
					myPeriod.text(data.period);
					myEndTime.text(data.endtime);
					period.val(data.period);
				}
				else{
					myPeriod.html('<b style="font-size:18px; color:#0C0"> '+data.message+' </b>');
				}
			}
	});		
	

	$("#selectPayMethod").change(function(){ 
		var selectPayMethod = $("#selectPayMethod");
		var showPayMethod = $("#showPayMethod");
		var showPayMethodHtml = "";
		
		//支付宝
		if(selectPayMethod.val()=="09"){
			var cash_value = $("#cash_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+cash_value.text()+'</b>元</div>';
		}
		
		//现金
		if(selectPayMethod.val()=="02"){
			var cash_value = $("#cash_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+cash_value.text()+'</b>元</div>';
		}
		
		//分分通积分
		if(selectPayMethod.val()=="00"){
			var fftpoint_value = $("#fftpoint_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+fftpoint_value.text()+'</b>分</div>';
		}
		
		//银行积分
		if(selectPayMethod.val()=="01"){
			var bankpoint_value = $("#bankpoint_value");
			showPayMethodHtml = '<div class="userpoints">商品单价：<b>'+bankpoint_value.text()+'</b>分</div>';
		}
		
		//分分通积分+现金
		if(selectPayMethod.val()=="03"){
			var fftpoint_fix_value = $("#fftpoint_fix_value");
			var fftcash_fix_value = $("#fftcash_fix_value");
			showPayMethodHtml='<div class="userpoints">商品单价：<b>'+fftpoint_fix_value.text()+'</b>分 + <b>'+fftcash_fix_value.text()+'</b>元</div> ';
		} 
		
		//银行积分+现金
		if(selectPayMethod.val()=="04"){
			var bankpint_fix_value = $("#bankpint_fix_value");
			var bankcash_fix_value = $("#bankcash_fix_value");
			showPayMethodHtml='<div class="userpoints">商品单价：<b>'+bankpint_fix_value.text()+'</b>分 + <b>'+bankcash_fix_value.text()+'</b>元</div> ';
		} 
		
		//分分通积分+现金(比例)
		if(selectPayMethod.val()=="05"){
			showPayMethodHtml='<div class="meinput validate" id="formlist"> <strong>商品单价：<input type="text" class="loginText4" id="points"><i>可使用积分：<span id="default_fftpoint">1080-2100</span> 分，您还需要支付现金<span id="default_fftpointCash">200</span> 元</i><span id="myMessage"></span></strong></div>';
			showPayMethod.html(showPayMethodHtml);
			
			var fftpointsRatio_value = $("#fftpointsRatio_value").text();
			var fftpointsCashRatio_value = $("#fftpointsCashRatio_value").text();
			var fftpointsRatio = fftpointsRatio_value.split("-");
			var fftpointsCashRatio = fftpointsCashRatio_value.split("-");
			var default_fftpoint = $("#default_fftpoint");
			var default_fftpointCash = $("#default_fftpointCash");
			var total_cash=parseInt(fftpointsRatio[0])+parseInt(fftpointsCashRatio[0]);
			var myMessage = $("#myMessage");
			
			$("#points").val(fftpointsRatio[0]);//积分值
			default_fftpoint.text(fftpointsRatio_value);//使用范围
			default_fftpointCash.text(fftpointsCashRatio[0]);
			$("#usePointRaioValue").val(fftpointsRatio[0]);
			
			$("#points").focus(function(){
  				$(this).attr("className","focusText4");
  				myMessage.html("<h4>请输入兑换积分</h4>");
			});
			
			
			$("#points").bind("blur", function(){
				var points_value=$(this).val();
				
				if($.trim(points_value) == ""){
					$(this).val(fftpointsRatio[0]);
					return;
				}
				
				var regexp_value=/^[1-9]\d*$/;
				
				if(!regexp_value.test(points_value)){
					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分</h3>");
  					return;
				}
				
  				if(parseInt(points_value)<parseInt(fftpointsRatio[0]) || parseInt(points_value)>parseInt(fftpointsRatio[1])){
  					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分范围</h3>");
  					return;
  				}
  				else{
  					$(this).attr("className","loginText4");
  					myMessage.html("<h2>输入成功</h2>");
  					$("#usePointRaioValue").val(points_value);
  					default_fftpointCash.text(total_cash-parseInt(points_value));
  				}
			}); 
			return ;
		} 
		
		//银行积分+现金(比例)
		if(selectPayMethod.val()=="06"){
			showPayMethodHtml='<div class="meinput validate" id="formlist"> <strong>商品单价：<input type="text" class="loginText4" id="points"><i>可使用积分：<span id="default_bankpoint">1080-2100</span>分，您还需要支付现金<span id="default_bankpointCash">200</span>元</i><span id="myMessage"></span></strong></div>';
			showPayMethod.html(showPayMethodHtml);
			
			var bankpointsRatio_value = $("#bankpointsRatio_value").text();
			var bankpointsCashRatio_value = $("#bankpointsCashRatio_value").text();
			var bankpointsRatio = bankpointsRatio_value.split("-");
			var bankpointsCashRatio = bankpointsCashRatio_value.split("-");
			var default_bankpoint = $("#default_bankpoint");
			var default_bankpointCash = $("#default_bankpointCash");
			var total_cash=parseInt(bankpointsRatio[0])+parseInt(bankpointsCashRatio[0]);
			var myMessage = $("#myMessage");
			
			$("#points").val(bankpointsRatio[0]);//积分值
			default_bankpoint.text(bankpointsRatio_value);//使用范围
			default_bankpointCash.text(bankpointsCashRatio[0]);
			$("#usePointRaioValue").val(bankpointsRatio[0]);
			
			$("#points").focus(function(){
  				$(this).attr("className","focusText4");
  				myMessage.html("<h4>请输入兑换积分</h4>");
			});
			
			$("#points").bind("blur", function(){
				var points_value=$(this).val();
				
				if($.trim(points_value) == ""){
					$(this).val(bankpointsRatio[0]);
					return;
				}
				
				var regexp_value=/^[1-9]\d*$/;
				
				if(!regexp_value.test(points_value)){
					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分</h3>");
  					return;
				}
				
  				if(parseInt(points_value)<parseInt(bankpointsRatio[0]) || parseInt(points_value)>parseInt(bankpointsRatio[1])){
  					$(this).attr("className","errorText4");
  					myMessage.html("<h3>请正确输入积分范围</h3>");
  					return;
  				}
  				else{
  					$(this).attr("className","loginText4");
  					myMessage.html("<h2>输入成功</h2>");
  					$("#usePointRaioValue").val(points_value);
  					default_bankpointCash.text(total_cash-parseInt(points_value));
  				}
			}); 
			return ;
		} 
		showPayMethod.html(showPayMethodHtml);
	});
	
	
	//立即兑换
	$('a.exchangeBut').click(function() {
	
		var formObject = document.getElementById('formlist');
		if(formObject!=null){
			var subm = formObject.getElementsByTagName('h3').length;
			if(subm){
				return;
			}
		}
		
		var selectPayMethod = $("#selectPayMethod");
		var choseRedBall = $("#choseRedBall");
		var blueB = $("#blueB");
		var lotteryValue = $("#lotteryValue");
		var period = $("#period");
		
		if($.trim(period.val()) == ""){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'彩票期号不存在',type : 8}	
		});
					
			return false;	
		}
		else if($.trim(selectPayMethod.val()) == ""){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请选择支付方式',type : 8}	
		});
					
			return false;	
		}
		else if($.trim(choseRedBall.val()) == "" || $.trim(blueB.val()) == ""){
			$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'投注号码不能为空',type : 8}	
		});
					
			return false;	
		}else{
			$.ajax({
				url: "ajax_lottery_checkDate.action",
				dataType: "json",
				success: function(data) {
					if(data.res=="no"){
						$.layer({
							title:['分分通提示您',true],
							time:3,
							area : ['auto','auto'],
							dialog : {msg:'当前销售期已停止投注',type : 8}
						});
					}else{
						lotteryValue.val(choseRedBall.val()+"|"+blueB.val());
						var senda = $("#senda");
						senda.attr("href","javascript:void(0);");
						senda.find("div").attr("class","gryBtn");	
						senda.find('B').html("请等待...");
						$('#exchangeLotteryViewForm').submit();
					}
				}
			});	
		}
		return false;
    });
	
})

</script>


</head>
<body>

<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<div class="box1010 pt10 clear"> 
  <!--彩票开始-->
<form id="exchangeLotteryViewForm" method="post" action="exchange_lottery_view.action">  
  <input id="goodsExchangeRackId"  name="exchangeTempBean.goodsRackId" value="${(goodsExchangeRack.id?c)!""}" type="hidden">
	<input name="exchangeTempBean.goodsCategoryId" value="${(goodsExchangeRack.goodsCategoryId)!""}" type="hidden">
	<input id="usePointRaioValue" name="exchangeTempBean.usePointRaioValue" type="hidden">
	<input id="period" name="exchangeTempBean.period" type="hidden">
	<input id="lotteryValue" name="exchangeTempBean.lotteryValue" type="hidden">
	
  <div class="lottery fl">
     <div class="win">
       <div class="leftball"><img src="${base}/template/web/images/shuangseqiu.png"></div>
       <div class="rightPeriods">
         <dl>
	           <dt>第 <span id="myPeriod"></span> 期</dt>
	           <dd>双色球大奖大、小奖多，最高奖金1000万</dd>
	           <dd>每周二、四、日 21：30开奖</dd>
	           <dd>截止时间：<span id="myEndTime"></span> 销售时间结束！</dd>
         </dl>
       </div>
       
     </div>
     
     <div class="redbox">
       <div id="redbox">
       <a href="javascript:void(0)"><li>01</li></a>
       <a href="javascript:void(0)"><li>02</li></a>
       <a href="javascript:void(0)"><li>03</li></a>
       <a href="javascript:void(0)"><li>04</li></a>
       <a href="javascript:void(0)"><li>05</li></a>
       <a href="javascript:void(0)"><li>06</li></a>
       <a href="javascript:void(0)"><li>07</li></a>
       <a href="javascript:void(0)"><li>08</li></a>
       <a href="javascript:void(0)"><li>09</li></a>
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
       <a href="javascript:void(0)"><li>01</li></a>
       <a href="javascript:void(0)"><li>02</li></a>
       <a href="javascript:void(0)"><li>03</li></a>
       <a href="javascript:void(0)"><li>04</li></a>
       <a href="javascript:void(0)"><li>05</li></a>
       <a href="javascript:void(0)"><li>06</li></a>
       <a href="javascript:void(0)"><li>07</li></a>
       <a href="javascript:void(0)"><li>08</li></a>
       <a href="javascript:void(0)"><li>09</li></a>
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
	    <b class="redFont">红球:</b><input type="text" name="" id="choseRedBall" class="inputRed" readonly="readonly">
        <b class="blueFont"> 蓝球:</b><input type="text" name="" id="blueB" class="inputBlue" readonly="readonly"> 
         <b id="ball"></b>
       </div>
     </div> 
    <div class="payselect">
     <div class="heightmiddle">
      <div class="selectfont">请选择支付方式：</div>
      <span class="fl" id="uboxstyle"> 
        <select id="selectPayMethod" name="exchangeTempBean.payMethod">
            	<!--	<option  value="">选择支付方式</option>-->
            		<option  value="09" selected="selected">现金（支付宝支付）</option>
             	  <#if goodsExchangeRack?exists && goodsExchangeRack.isCash=="1" >
	             	 <option  value="02">现金（珠海农商银行手机银行卡支付）</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isFftPoint=="1">
	             	<option  value="00" >分分通积分</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isBankPoint=="1" >
	             	  <option  value="01">银行积分</option>
	              </#if>
	              <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointCash=="1" >
	             	<option  value="03">现金+手机银行卡</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointCash=="1" >
	             	<option  value="04">银行积分+现金</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointcashRatioPricing=="1" >
	             	<option  value="05">分分通积分+现金（比例）</option>
	              </#if>
	               <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointcashRatioPricing=="1" >
	             	<option  value="06">银行积分+现金（比例）</option>
	              </#if>
         </select>
         </span>
        </div>
        <center>
<div id="formlist" class="validate mb5"><div id="showPayMethod"></div></div>
  </center>
   <script type="text/javascript" src="${base}/template/common/js/select.js"></script>  
</div>
 <div class="w150 abtn pb30 pt20" id ="senda"  >
     <a href="javascript:void(0);" class="exchangeBut"><div class="textBtn"><B>立即投注</B></div></a>  
   </div>     
  </div>
 <script src="${base}/template/web/js/lottery.js"></script> 
  
<#include "/WEB-INF/templates/common/exch_right.ftl"> 

</form>  
</div>


<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</div>
<#if !goodsExchangeRack?exists>
<script type="text/javascript">
$(document).ready(function (){
	$.layer({
			title:['分分通提示您',true],
			area : ['auto','auto'],
			dialog : {msg:'参数异常！',type : 3}	
		});
});
</script>
</#if>
</body>
</html>
<div id="myDiv" style="display:none">
<#assign cash=goodsExchangeRack.cashPricing>
       <#assign cashPricing=cash?number>
       <#if goodsExchangeRack.isCash=="1" >
       		<dd>现金兑换：<b id="cash_value">${goodsExchangeRack.cashPricing!"0"}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isFftPoint=="1" >
       		<dd>分分通积分兑换：<b id="fftpoint_value">${goodsExchangeRack.fftPointPricing!"0"}</b>分</dd>
       </#if>
       
       <#if goodsExchangeRack.isBankPoint=="1" >
       		<dd>银行积分兑换：<b id="bankpoint_value">${goodsExchangeRack.bankPointPricing!"0"}</b>分</dd>
       </#if>
       
       <#if goodsExchangeRack.isFftpointCash=="1" >
       		<#assign fftpointsAndCashArray=goodsExchangeRack.fftpointCashPricing?split('|')>
       		<dd>分分通积分+现金兑换：<b id="fftpoint_fix_value">${fftpointsAndCashArray[0]}</b>分+<b id="fftcash_fix_value">${fftpointsAndCashArray[1]}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isBankpointCash=="1" >
       		<#assign bankpointsAndCashArray=goodsExchangeRack.bankpointCashPricing?split('|')>
       		<dd>银行积分+现金兑换：<b id="bankpint_fix_value">${bankpointsAndCashArray[0]}</b>分+<b id="bankcash_fix_value">${bankpointsAndCashArray[1]}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isFftpointcashRatioPricing=="1" >
       		<#assign fftpointsAndCashRatioArray=goodsExchangeRack.fftpointcashRatioPricing?split('|')>
       		<#assign fftpointsRatioMin=fftpointsAndCashRatioArray[0]>
       		<#assign fftpointsRatioMinPercent=fftpointsRatioMin?number>
       		<#assign fftpointsRatioMinPercentDivision=fftpointsRatioMinPercent/100>
       		<#assign fftpointsRatioMax=fftpointsAndCashRatioArray[1]>
       		<#assign fftpointsRatioMaxPercent=fftpointsRatioMax?number>
       		<#assign fftpointsRatioMaxPercentDivision=fftpointsRatioMaxPercent/100>
       		<#assign fftpointsMin=cashPricing*fftpointsRatioMinPercentDivision>
       		<#assign fftpointsMax=cashPricing*fftpointsRatioMaxPercentDivision>
       		<dd>分分通积分+现金兑换：<b id="fftpointsRatio_value">${fftpointsMin}-${fftpointsMax}</b>分+<b id="fftpointsCashRatio_value">${cashPricing-fftpointsMin}-${cashPricing-fftpointsMax}</b>元</dd>
       </#if>
       
       <#if goodsExchangeRack.isBankpointcashRatioPricing=="1" >
       		<#assign bankpointsAndCashRatioArray=goodsExchangeRack.bankpointcashRatioPricing?split('|')>
       		<#assign bankpointsRatioMin=bankpointsAndCashRatioArray[0]>
       		<#assign bankpointsRatioMinPercent=bankpointsRatioMin?number>
       		<#assign bankpointsRatioMinPercentDivision=bankpointsRatioMinPercent/100>
       		<#assign bankpointsRatioMax=bankpointsAndCashRatioArray[1]>
       		<#assign bankpointsRatioMaxPercent=bankpointsRatioMax?number>
       		<#assign bankpointsRatioMaxPercentDivision=bankpointsRatioMaxPercent/100>
       		<#assign bankpointsMin=cashPricing*bankpointsRatioMinPercentDivision>
       		<#assign bankpointsMax=cashPricing*bankpointsRatioMaxPercentDivision>
       		<dd>银行积分+现金兑换：<b id="bankpointsRatio_value">${bankpointsMin}-${bankpointsMax}</b>分+<b id="bankpointsCashRatio_value">${cashPricing-bankpointsMin}-${cashPricing-bankpointsMax}</b>元</dd>
       </#if>
</div>