<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重庆农村商业银行-预售抽奖</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/common/css/bonus.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/common/js/rolling.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//加载可抽奖次数
	loadLotteryTimes();
	//加载中奖名单
	loadWinners();
});



</script>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<div class="bonusbg">
  <div class="bonusarea">  
<div class="table">
<table style="position:absolute; z-index:1">
<tr>
    <td background="${base}/resources/common/images/bonus/bonus4.png"></td>
    <td background="${base}/resources/common/images/bonus/bonus2.png"></td>
    <td background="${base}/resources/common/images/bonus/bonus3.png"></td>
    <td background="${base}/resources/common/images/bonus/bonus4.png"></td>
</tr>
<tr>
    <td background="${base}/resources/common/images/bonus/bonus3.png"></td>
    <td></td>
    <td></td>
    <td background="${base}/resources/common/images/bonus/bonus5.png"></td>
</tr>
<tr>
  <td background="${base}/resources/common/images/bonus/bonus3.png"></td>
  <td background="${base}/resources/common/images/bonus/bonusno.png"></td>
  <td background="${base}/resources/common/images/bonus/bonus5.png"></td>
  <td background="${base}/resources/common/images/bonus/bonus4.png"></td>
</tr>	
</table>

<table id="tb" style="position:absolute; z-index:2">
<tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
  <td></td>
  <td></td>
  <td></td>
  <td></td>
</tr>
</table>
<a href="javascript:;" id="lotteryRandom" onclick="StartGame()"></a>
</div>
  </div>
  <div class="bonuspoints">
  <p>您当前可用积分:<span>${login_identification.froadPoints!'0'}</span>分</p>
  <p>剩余抽奖次数：<span id="times"></span>次</p>
  </div>
  <div class="bonustext">
    <dt>活动内容:</dt>
    <li> 1.每个账号每2积分可抽一次奖</li>
    <li> 2.活动时间为2014年5月20日-5月30日</li>
    <li> 3.实体奖品在用户中奖后7个工作日内发一次货</li>
  </div>
  <div class="bonustext">
    <dt>中奖名单:</dt>
    <div class="namelist" id="scrollDiv">
     <ul id="winners">
	
     </ul>
    </div>
  </div>
  <div class="bonusproducts"><img src="${base}/resources/common/images/jiangpin.jpg"></div>
</div>
<#include "/include/common/footer.ftl">
<script type="text/javascript" src="${base}/resources/common/js/luck.js"></script>
</body>
</html>
<script>
 
</script>