<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帮助中心</title>
<#include "/WEB-INF/templates/common/include.ftl">
    <link href="${base}/template/web/css/newhelp.css" rel="stylesheet" type="text/css"/>
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

<div class="box1010 pt10 clear">
  <div class="newhelp">
    <div class="helptitle">帮助中心</div>
    <div class="left">
      <dl>
      <div>
        <dt>账户帮助</dt>
        <dd>·<a href="#answer01">账户注册</a></dd>
        <dd>·<a href="#answer02">账户使用</a></dd>
        <dd>·<a href="#answer03">找回密码</a></dd>
        <dt>精品预售</dt>
        <dd>·<a href="#sell01">预售商品</a></dd>
        <dd>·<a href="#sell02">预售提货</a></dd>
        <dt>积分帮助</dt>
        <dd>·<a href="#answer04">分分通积分</a></dd>
        <dd>·<a href="#answer05">银行积分</a></dd>
        <dd>·<a href="#answer06">激活积分</a></dd>
        <dd>·<a href="#answer07">积分返利</a></dd>
        <dd>·<a href="#answer08">积分兑换</a></dd>
        <dd>·<a href="#answer09">积分提现</a></dd>
        <dt>团购帮助</dt>
        <dd>·<a href="#answer10">团购商品</a></dd>
        <dd>·<a href="#answer11">团购券使用</a></dd>
        <dt>其他功能</dt>
        <dd>·<a href="#zhifubao01">支付宝付款</a></dd>
        <dd class="longfont">·<a href="#answer12">手机银行卡认证</a></dd>
        <dd class="longfont">·<a href="#lianmeng01">手机银行联盟服务</a></dd>
        <dd>·<a href="#answer13">提现认证</a></dd>
        <dd>·<a href="#answer14">客户端下载</a></dd>
      </dl>
    </div>

    <div class="answer">
    <div class="titleTitle"><a name="answer01">账户帮助-账户注册</a></div>
      <dl>
      <dt>如何注册成为会员？</dt>
      </dl>

    <table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>答：进入分分通网站（<a href="http://www.fenfentong.com.cn" target="_blank">http://www.fenfentong.com.cn</a>），点击右上角的“注册”，按下述操作即可完成分分通会员的注册。</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new001.gif"></td>
  </tr>
    </table>

    <dl>
      <dt>分分通会员有什么权利？</dt>
    </dl>
     <table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>答：您在分分通商户的营业场所消费时，可以享受专有的积分返利优惠，或者更低的折扣优惠。</td>
  </tr>
  </table>

    <div class="titleTitle"><a name="answer02">账户帮助-账户使用</a></div>
    <dl>
      <dt>连续输错了5次密码，我该怎么办？</dt>
    </dl>
      <table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>答：如果连续输错了5次密码，账号在当天将被限制登录，如果您忘记了密码，请参考账户帮助-找回密码中<a href="#answer03">找回密码</a>的流程。</td>
  </tr>
  </table>

    <dl>
      <dt>我用手机注册的，能否改系统给我默认的用户名？ </dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：若使用手机号码注册，系统将默认创建一个用户名，该用户名无法修改。您可以使用该注册手机号方便地登录以及找回密码。</td>
  </tr>
  </table>

    <div class="titleTitle"><a name="answer03">账户帮助-找回密码</a></div>
    <dl>
      <dt>密码忘记了怎么办？</dt>
    </dl>
 <table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>答：可按下述步骤找回密码：</td>
  </tr>
  <tr>
    <td class="redFont">1.进入登录页面，点击“找回密码”；</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new002.gif"></td>
  </tr>
  <tr>
    <td class="redFont">2.输入注册时填写的手机号码</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new003.gif"></td>
  </tr>
  <tr>
    <td class="redFont">3.系统会将重置后的密码发送到填写的手机号码中</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new004.gif"></td>
  </tr>
  <tr>
    <td class="redFont">4.使用重置后的密码登录分分通，并及时修改密码。</td>
  </tr>
    </table>

    <div class="titleTitle"><a name="sell01">精品预售-预售商品</a></div>
     <dl>
      <dt>什么是精品预售？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：平台为您精选了一些即将上市的新商品或定制的特殊商品，平台在商品上市之前开放给平台用户购买，以便平台用户在新商品上市的第一时间就能拿到。而定制的特殊商品是平台归集一些对该商品有需求的用户，达到一定购买数量后，平台统一向供应商采购。</td>
  </tr>
  </table>

     <dl>
      <dt>精品预售的商品有什么特别？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：平台上的精品预售商品，均是平台为客户争取到更低的折扣、更高的品质以及更便利的购物体验的商品。</td>
  </tr>
  </table>

     <dl>
      <dt>什么是预售成团？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：平台预售的商品都有一个最低购买数量，当平台用户购买的总数量达到成团数量时，平台才会采购该预售商品，若平台用户的购买总数量未能达到成团数量时预售失败。</td>
  </tr>
  </table>

     <dl>
      <dt>预售成团的商品是否能继续购买？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：预售商品未截止预售时，您依然可以继续购买，若商品有最大预售数量时，达到最大预售数量之后就无法购买了。</td>
  </tr>
  </table>

     <dl>
      <dt>预售失败的款项平台会退回吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：若预售期结束后未达到最低成团数量，平台将自动退款退分，退款退分原路退回。</td>
  </tr>
  </table>

     <dl>
      <dt>预售的商品订单能够退款吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：预售的商品价格较正常销售价格有很大的优惠，客户支付后不能主动发起退款，只有预售失败的商品方可退款退分。</td>
  </tr>
  </table>

    <div class="titleTitle"><a name="sell02">精品预售-预售提货</a></div>
      <dl>
      <dt>什么是预售期、备货期和提货期？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：预售商品只在预定时间内开放购买，您可以点击商品查看具体的预售时间段。预售时间截止后，无法继续购买。备货期是指平台向商品供应商采购时间，提货期是预售商品已经到货，用户可根据提货验证信息前往提货点获得预售商品的时间。</td>
  </tr>
  </table>

      <dl>
      <dt>购买的预售商品成团后如何获得预售商品？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：预售商品详情页面会告知该商品的提货时间以及提货方式。平台同时也会以短信的形式将提货验证码发送给您，到达提货期后您凭借提货验证码在提货点即可获得预售商品。</td>
  </tr>
  </table>

      <dl>
      <dt>如何查看预售商品的提货点？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：在预售商品详情页面中会注明该商品的所有提货点，您可以根据自己的需要选择合适的提货点。预售成团后，平台也会以短信的形式将您选择的提货点信息发送至您的手机。</td>
  </tr>
  </table>

      <dl>
      <dt>我的实际提货点和订单中选的提货点不一致可以提货吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：可以。预售商品的提货验证码可以在任意一个提货点提货。</td>
  </tr>
  </table>

      <dl>
      <dt>预售的商品可以换货吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：在提货时您需要验明商品没有问题，若验货时发现商品质量影响使用，可当场要求换货，验货完成后的商品一般不予退换货。</td>
  </tr>
  </table>


    <div class="titleTitle"><a name="answer04">积分帮助-分分通积分</a></div>
    <dl>
      <dt>什么是分分通积分？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：去商家的店里消费后商家返还的积分或者分分通活动赠送的积分，都属于分分通积分。</td>
  </tr>
  </table>

    <dl>
      <dt>如何获得分分通积分？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：在分分通积分返利板块中查询返利合作商户，至商户的营业场所消费后，向商户提供您在分分通平台注册的手机号，商户会按照您的消费金额，赠送给您一定比例的分分通积分。</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new005.gif"></td>
  </tr>
  </table>

    <dl>
      <dt>分分通积分可以用来做什么？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：分分通积分可以用来兑换各种商品（如充值话费、兑换彩票、兑换商家的产品等），可以用来购买大部分商品时抵扣现金，若分分通积分达到一定的值，还可以提为现金。</td>
  </tr>
  </table>

    <dl>
      <dt>如何查询我的分分通积分？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：登录分分通后，您可以直接查看右上角的“分分通积分”与“银行积分”的值，也可以点击右侧“我的积分”查看，另外，您如果获得积分赠送或者进行了积分消费，将会有短信提醒积分值的变动情况。</td>
  </tr>
  </table>

    <dl>
      <dt>分分通积分与银行积分有何区别？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：分分通积分为用户在商家消费后获得的积分或者通过分分通活动获得的积分，而银行积分为珠海农商银行用户使用珠海农商银行的银行卡或手机银行卡产生的积分，这些积分可以用于在分分通网站兑换各种商品，不能提现。</td>
  </tr>
  </table>

    <dl>
      <dt>我可以同时使用分分通积分和银行积分吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：不能。分分通积分和银行积分是两种不同类型的积分，它们可用于兑换商品，购买商品时抵扣现金等，但银行积分只能用于消费，无法进行提现，因此分分通积分和银行积分无法同时使用。</td>
  </tr>
  </table>

    <div class="titleTitle"><a name="answer05">积分帮助-银行积分</a></div>
    <dl>
      <dt>什么是银行积分？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：银行积分，是指珠海农商银行用户使用珠海农商银行的银行卡刷卡消费或使用手机银行卡汇款、缴费等功能而获得的积分。银行积分包括珠海农商银行的银行卡积分和珠海农商银行手机银行卡积分两种。用户通过分分通上的“激活积分”功能，可将银行积分激活到分分通，变为可在分分通上消费的银行积分。</td>
  </tr>
  </table>

    <dl>
      <dt>如何获得银行积分？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：银行积分中的手机银行卡积分我们将为您自动激活到分分通，而银行卡积分需要您通过“激活积分”功能手动激活。当前只能通过激活积分的方式获得银行积分。</td>
  </tr>
  </table>

    <dl>
      <dt>银行积分可以用来做什么？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：银行积分可以用来兑换各种商品（如充值话费、兑换彩票、兑换商家的产品等），可以用来购买大部分商品时抵扣现金，银行积分无法提现。</td>
  </tr>
  </table>

    <dl>
      <dt>如何查询我的银行积分？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：登录分分通后，您可以直接查看右上角的“分分通积分”与“银行积分”的值，也可以点击右侧“我的积分”查看，另外，您如果获得积分赠送或者进行了积分消费，将会有短信提醒积分值的变动情况。</td>
  </tr>
  </table>

    <dl>
      <dt>我可以同时使用分分通积分和银行积分吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：不能。分分通积分和银行积分是两种不同类型的积分，它们可用于兑换商品，购买商品时抵扣现金等，但银行积分只能用于消费，无法进行提现，因此分分通积分和银行积分无法同时使用。</td>
  </tr>
  </table>


    <div class="titleTitle"><a name="answer06">积分帮助-激活积分</a></div>
    <dl>
      <dt>激活积分功能是什么？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：用户通过分分通上的“激活积分”功能，可将珠海农商银行的银行积分激活到分分通，变为可在分分通上消费的银行积分。</td>
  </tr>
  </table>

    <dl>
      <dt>为什么我有珠海农商行的积分，但却提示激活失败呢？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：·请确保您的户名及证件号码输入正确，若仍然提示激活积分失败，则是因为您的珠海农商银行积分不够最低激活标准，最少需要5000积分；</br>
·另外，银行积分不是实时获取的最新数据，本次可激活的积分为2013年度的积分数据，因此若您在这之后达到激活标准，仍然会提示激活失败，我们将会定期更新积分激活数据.</td>
  </tr>
  </table>

    <dl>
      <dt>为什么激活积分后我的积分值和原银行积分的积分值不同？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：我们会按照一定的比例（10000:8）将珠海农商银行的银行卡积分换算成分分通上的银行积分，因此您激活后的积分值会少于原银行积分的积分值；</br>
·若您的多个珠海农商银行账户均达到积分激活的标准，则激活积分时我们会将其全部激活，因此您激活后的积分值为所有符合条件账户的积分值总和。</td>
  </tr>
  </table>

    <dl>
      <dt>为什么我的其他类型的银行积分无法完成激活？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：本年度（2013年度）珠海农商银行的可激活的积分类型为珠海农商银行的银行卡积分，其他种类的银行积分暂时无法激活，请谅解”。</td>
  </tr>
  </table>

  <div class="titleTitle"><a name="answer07">积分帮助-积分返利</a></div>
    <dl>
      <dt>到分分通商户处消费可以获得积分吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：分分通积分返利商户一般全场赠送分分通积分，如若有不赠送的情况，我们会在商户介绍中特别说明。例如酒水，积分兑换及团购产品等不参与赠送积分。</td>
  </tr>
  </table>

    <dl>
      <dt>我到分分通商户处消费，为什么没有获得积分返还？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：若到分分通的直接优惠商户处消费，优惠是以直接打折的方式返还的；只有到分分通的积分返利商户处消费会有积分返还，若积分返利商户拒绝赠送积分，您可以拨打分分通客服热线0756-3827999进行投诉。</td>
  </tr>
  </table>


    <div class="titleTitle"><a name="answer08">积分帮助-积分兑换</a></div>

     <dl>
      <dt>什么是兑换商品送积分？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：平台推选出一些商品，在您使用银行积分或分分通积分兑换，或使用现金(包括手机银行卡和支付宝)支付时，平台会按订单金额的一定比例返还分分通积分给您。</td>
  </tr>
  </table>

     <dl>
      <dt>兑换商品送的积分什么时候能到账？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：您支付完成后，积分即时返还到您在分分通的账户中。</td>
  </tr>
  </table>


    <dl>
      <dt>积分都可以兑换什么类型的商品？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：积分兑换是分分通平台提供给广大客户的分分通积分和珠海农商银行积分的兑换渠道，您的积分可以兑换虚拟商品（如话费充值、彩票等），也可以兑换珠海当地相关合作商户提供的产品或者服务，兑换商家产品或商家服务后将获得兑换码，线下去该商家处出示兑换码即可享受到兑换的产品或服务。您可以使用积分进行以下商品的兑换。</td>
  </tr>
    <tr>
    <td><img src="${base}/template/web/images/new006.gif"></td>
  </tr>
  </table>


    <dl>
      <dt>如何使用积分兑换？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：选择积分兑换商品，下单之后，您可自由选择商户处积累的分分通积分或珠海农商银行积分进行兑换，如积分不够，还可选择积分+现金或纯现金的方式，现金支付方式为使用珠海农商银行的手机银行卡支付。没有手机银行卡的用户，可至珠海农商行营业网点申请办理。</td>
  </tr>
  </table>

    <dl>
      <dt>我收到兑换码如何使用？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：在支付完成后，系统会为您的手机发送兑换验证码，至商户处消费时报出此码，商户验证后即可。</td>
  </tr>
  </table>

    <div class="titleTitle"><a name="answer09">积分帮助-积分提现</a></div>
    <dl>
      <dt>如何将分分通积分提现？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：您需要将您的分分通账户绑定珠海农商银行的银行卡后才能提现成功；若分分通账户未绑定珠海农商银行的银行卡，请先在会员中心板块中的“提现认证”中绑定银行卡后再进行提现操作；若无珠海农商银行的银行卡账户，请先至珠海农商银行的营业网点开卡。
</td>
  </tr>
    <tr>
    <td>1.首先在个人中心板块中的“提现认证”中绑定珠海农商银行账户。</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new007.gif"></td>
  </tr>
  <tr>
    <td>2.进入积分提现功能，输入提现积分</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new008.gif"></td>
  </tr>
  <tr>
    <td>3.确认提现账户、金额及手续费等信息</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new009.gif"></td>
  </tr>
  <tr>
    <td>4.提现申请成功</td>
  </tr>
  <tr>
    <td><img src="${base}/template/web/images/new010.gif"></td>
  </tr>
  </table>

    <dl>
      <dt>银行积分是否可以提现？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：不能。银行积分可以用来兑换各种商品（如充值话费、兑换彩票、兑换商家的产品等），可以用来购买大部分商品时抵扣现金，银行积分无法提现。</td>
  </tr>
  </table>

    <dl>
      <dt>积分提现有什么要求吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：当分分通积分达到10积分后可以申请提现，每次提现的积分值需为10的整数倍，提现手续费按5%收取，最低1元，最高50元。</td>
  </tr>
  </table>

    <dl>
      <dt>申请积分提现后多久能到账？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：您申请积分提现后，一般一个工作日内可以到账，请及时检查账户余额变动。</td>
  </tr>
  </table>

    <div class="titleTitle"><a name="answer10">团购帮助-团购商品</a></div>
    <dl>
      <dt>怎么购买团购商品？有哪些支付方式？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：只需在团购截止时间之前点击"购买"按钮，根据提示下订单，付费购买时，您可按自身情况选择是否使用积分，积分可在分分通积分或珠海农商银行积分中选择一种使用，如您的积分不够，可用手机银行卡支付的方式进行现金补足。</td>
  </tr>
    <tr>
    <td>1.点击团购，选择您喜欢的团购商品，进去查看详情，点击立即抢购按钮</td>
  </tr>
    <tr>
    <td><img src="${base}/template/web/images/new011.gif"></td>
  </tr>
    <tr>
    <td>2.确认购买</td>
  </tr>
    <tr>
    <td><img src="${base}/template/web/images/new012.gif"></td>
  </tr>
  </table>

    <dl>
      <dt>团购能否退款？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：珠海农商银行分分通平台的团购商品均为优质商家的产品或服务，价格已十分优惠，且可用积分兑换的方式购买，因此团购产品售出之后不能退款，请您谅解。</td>
  </tr>
  </table>

    <dl>
      <dt>什么是现金支付？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>答：目前为止，分分通上的“现金”支付是指使用珠海农商银行的手机银行卡进行支付的方式。</td>
  </tr>
  </table>

  <div class="titleTitle"><a name="answer11">团购帮助-团购券使用</a></div>
    <dl>
      <dt>怎么使用团购券？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>团购成功后,分分通会将团购验证码发送至您的手机上，凭借此团购验证码即可在团购商户指定的场所消费，请消费前出示团购验证码，该验证码仅可使用一次。</td>
  </tr>
  </table>

    <dl>
      <dt>使用团购券时，能享受分分通平台的其他优惠吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>团购优惠不可与其分分通平台的其他优惠同时享受。</td>
  </tr>
  </table>

    <dl>
      <dt>我不小心将团购商品的短信删除了，还能重发吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>可以。进入会员中心，找到该笔团购订单，点击订单尾部代表“重发验证券短信”的小手机按钮，即可重新发送验证券到手机上。</td>
  </tr>
     <tr>
    <td><img src="${base}/template/web/images/new013.gif"></td>
  </tr>
  </table>

    <dl>
      <dt>我可以在分分通上查询我的团购券吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>可以。进入会员中心，找到该笔订单，点击查看“详情”，在订单详情中将会有兑换券信息。</td>
  </tr>
     <tr>
    <td><img src="${base}/template/web/images/new014.gif"></td>
  </tr>
  </table>

    <div class="titleTitle"><a name="zhifubao01">其他功能-支付宝付款</a></div>
    <dl>
      <dt>使用支付宝付款遇到问题怎么办？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>若在使用支付宝付款过程中遇到问题，可查询支付宝常见问题帮助页面。<a href="http://help.alipay.com/lab/question.htm" target="_blank">点击查询</a></td>
  </tr>
  </table>


   <div class="titleTitle"><a name="answer12">其他功能-手机银行卡认证</a></div>
    <dl>
      <dt>使用现金支付时，为何提示无法完成支付？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>当选择现金支付后，账单将推送到您的手机银行卡上，非手机银行卡用户将无法使用现金支付。</td>
  </tr>
  </table>

    <dl>
      <dt>为何需要进行手机银行卡认证？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>若您购物时选择现金支付，账单将推送到您的手机银行卡上，非手机银行卡用户将无法完成现金支付。因此若选择支付现金，请先进行手机银行卡认证。</td>
  </tr>
  </table>

    <dl>
      <dt>使用手机银行卡认证，为何提示认证失败？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>如果认证失败，表示您还未到珠海农商银行办理过手机银行卡，可到银行各营业网点办理。</td>
  </tr>
  </table>

    <dl>
      <dt>我没有手机银行卡，到哪里办理呢？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>您可到珠海农商银行各营业网点办理，<a href="http://www.zhnsb.com.cn/service/branches/" target="_blank">点击查看办理网点</a></td>
  </tr>
  </table>

    <dl>
      <dt>办理手机银行卡有什么好处？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>·无需上网，彻底避免网上银行的安全漏洞，更安全</br>
·不用跑银行，可随时随地自助办理转账、汇款、缴费、查询等业务</br>
·安全快捷的网购，网上购物，线下支付</br>
·随身携带，二十四小时随时随地办理银行业务</br>
·<a href="http://mobile.zhnsb.com.cn/html/sub-cptd.html" target="_blank">点击查看详细介绍</a></td>
  </tr>
  </table>

   <div class="titleTitle"><a name="lianmeng01">其他功能-手机银行联盟服务</a></div>
    <dl>
      <dt>为什么我点击了分分通首页的“机票”、“知名电商企业产品购买”和“购物返利”后进入了手机银行联盟网站？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>珠海农商银行分分通平台和手机银行联盟网站是有着深度合作关系的，您在珠海农商银行分分通的账户可以登录手机银行联盟网站，而且珠海农商银行的银行卡积分也可以在手机银行联盟网站上使用。</td>
  </tr>
  </table>

    <dl>
      <dt>为什么我在手机银行联盟网站上购买的机票在分分通平台上无法查询？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>进入分分通平台的会员中心后点击“机票订单”会自动跳转到手机银行联盟网站的订单页面，在这里可以查询到您的机票订单信息。</td>
  </tr>
  </table>

    <dl>
      <dt>分分通积分在手机银行联盟网站上可以使用吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>可以，分分通积分和手机银行联盟积分已经打通，您在分分通账户中显示的分分通积分余额同样也是手机银行联盟积分的余额，您在手机银行联盟网站上获得的积分也可以在分分通平台上使用，在两者任意一个平台消耗积分后在另一个平台中也会扣除相关积分。</td>
  </tr>
  </table>


    <div class="titleTitle"><a name="answer13">其他功能-提现认证</a></div>
    <dl>
      <dt>为何要进行提现认证？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>若将分分通积分提现，首先需要有一个珠海农商银行的银行账户，因此第一次进行积分提现操作时需要先进行提现认证。</td>
  </tr>
  </table>

    <dl>
      <dt>可以绑定其他银行的账户吗？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>分分通积分只支持提现到珠海农商银行，若您没有珠海农商银行的银行账户，可先到当地办理开户，<a href="http://www.zhnsb.com.cn/service/branches/" target="_blank">点击查看当地营业网点</a></td>
  </tr>
  </table>

    <div class="titleTitle"><a name="answer14">其他功能-客户端下载</a></div>
    <dl>
      <dt>分分通客户端如何下载？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td>分分通客户端已在各大应用市场发布，安卓用户可到各大安卓市场搜索“分分通”下载，苹果用户可到AppStore搜索“分分通”下载。
<a href="http://www.fenfentong.com.cn/app_download_index.action" target="_blank">点击进入下载页</a></td>
  </tr>
  </table>

      <dl>
      <dt>为何除了分分通客户端外，还有分分通商户版客户端？</dt>
    </dl>
    <table width="800" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:30px;">
    <tr>
    <td>分分通客户端是提供广大分分通用户使用的版本，分分通商户版为分分通商户专用版本，普通用户请下载分分通普通版客户端。</td>
  </tr>
  </table>

    </div>
  </div>
</div>

<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</div>
</body>
</html>
