<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分分通</title>
<#include "/WEB-INF/templates/common/include.ftl">

<link href="${base}/template/web/css/inforbox.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/web/css/explain.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
-->
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<div class="box1010 pt10 clear">
  
<!--充值开始-->
  <div class="put fl">
   <table>
  <tr>
    <td colspan="2" class="pt20 pl50"></td>
    </tr>
  <tr>
    <th>您的分分通积分余额为：</th>
    <td class="redFont">18000</td>
    </tr>
  <tr>
    <th>您的手机号为：</th>
    <td>138888888888</td>
  </tr>
  <tr>
    <th>提现积分：</th>  
    <td><input type="text" name="textfield2" id="textfield2" /></td>
  </tr>
  <tr>
    <th>银行卡帐号：</th>
      <td><select name="">
        <option>6222************2222</option>
      </select>
      （珠海农商银行）</td>
  </tr>
  <tr>
    <th>&nbsp;</th>   
    <td  class="grayFont f12">还未绑定银行账户？ <a href="#">立即绑定</a></td>
  </tr>
  <tr>
    <th>  
    <td  class="grayFont f12">&nbsp;</td>
  </tr>
   </table> 
    <a href="#" class="btn w70">确定</a>  
    
    <div class="explain">
      <li>1、手机号码用于接收短信通知，请认真填写。</li>
      <li>2、服务费费率为输入金额的1%，最低收取2元，最高不超过50元。</li>
      <li>3、交易号支付业务，请确保提供正确的支付宝交易号和相符的交易金额。</li>
    </div> 
  </div> 
<!--充值结束-->

  <div class="fl ml10">
    <div class="rightImg">
      <a href="#"><img src="${base}/template/web/images/right001.png"></a>
      <a href="#"><img src="${base}/template/web/images/right002.png"></a>
      <a href="#"><img src="${base}/template/web/images/right003.png"></a>
      <a href="#"><img src="${base}/template/web/images/right004.png"></a>
      <a href="#"><img src="${base}/template/web/images/right005.png"></a>
      <a href="#"><img src="${base}/template/web/images/right006.png"></a>
    </div>
    <div><img src="${base}/template/web/img/ad01.png"></div> 
  </div>                  
</div>

  
<!--底部开始-->  
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->  

</body>
</html>
