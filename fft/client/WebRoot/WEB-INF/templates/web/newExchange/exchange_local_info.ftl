<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>分分通-本地商品</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css"/>
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
<link href="${base}/template/web/css/points.css" rel="stylesheet" type="text/css"/>
<link href="${base}/template/web/css/rightimg.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>

<!--
* Author:
* pengling@f-road.com.cn 
*/
-->

<script type="text/javascript">
$(document).ready(function ()
{

    var selectType = "notratio"

    var buyNumber = $("#number");
    //var stockNumber = $("#stock");//库存

    //输入数量
    $("#number").keyup(function ()
    {

        var re = /^[1-9]\d*$/;
        if (!re.test($(this).val()))
        {
            $(this).val(1);
        }

        //if(parseFloat(buyNumber.val())>parseFloat(stockNumber.text())){
        //	$(this).val(stockNumber.text());
        //	$("#show").css("display","block");
        //	$("#boxnumber").text(stockNumber.text());
        //}
    });

    //增加点击
    $("#addNumber").click(function ()
    {
        buyNumber.val(parseFloat(buyNumber.val()) + 1);
        selectEvent();
    });

    //减少点击
    $("#minusNumber").click(function ()
    {
        if (parseFloat(buyNumber.val()) > 1)
        {
            buyNumber.val(parseFloat(buyNumber.val()) - 1);
            selectEvent();
        }
    });

    $("#number").keyup(selectEvent);
    function selectEvent()
    {
        var selectPayMethod = $("#selectPayMethod");
        var showPayMethod = $("#showPayMethod");
        var showPayMethodHtml = "";

		//alert("================="+selectPayMethod.val())
		//支付宝支付
		if(selectPayMethod.val() == "09"){
			selectType = "notratio";
            var cash_value = $("#cash_value");
            var numA = parseFloat(cash_value.text());
            var numB = parseFloat($("#number").val());
            var totle = numA * numB;
            var strTotle = totle.toFixed(1);
            showPayMethodHtml = '<div class="userpoints">商品总价：<b>' + strTotle + '</b>元</div>';
		}

        //现金
        if (selectPayMethod.val() == "02")
        {
            selectType = "notratio";
            var cash_value = $("#cash_value");
            var numA = parseFloat(cash_value.text());
            var numB = parseFloat($("#number").val());
            var totle = numA * numB;
            var strTotle = totle.toFixed(1);
            showPayMethodHtml = '<div class="userpoints">商品总价：<b>' + strTotle + '</b>元</div>';
        }

        //分分通积分
        if (selectPayMethod.val() == "00")
        {
            selectType = "notratio";
            var fftpoint_value = $("#fftpoint_value");
            var numA = parseFloat(fftpoint_value.text());
            var numB = parseFloat($("#number").val());
            var totle = numA * numB;
            var strTotle = totle.toFixed(1) + "";
            showPayMethodHtml = '<div class="userpoints">商品总价：<b>' + strTotle + '</b>分</div>';
        }

        //银行积分
        if (selectPayMethod.val() == "01")
        {
            selectType = "notratio";
            var bankpoint_value = $("#bankpoint_value");
            var numA = parseFloat(bankpoint_value.text());
            var numB = parseFloat($("#number").val());
            var totle = numA * numB;
            var strTotle = totle.toFixed(1) + "";
            showPayMethodHtml = '<div class="userpoints">商品总价：<b>' + strTotle + '</b>分</div>';
        }

        //分分通积分+现金
        if (selectPayMethod.val() == "03")
        {
            selectType = "notratio";
            var fftpoint_fix_value = $("#fftpoint_fix_value");
            var fftcash_fix_value = $("#fftcash_fix_value");
            showPayMethodHtml = '<div class="userpoints">商品总价：<b>' + (parseFloat(fftpoint_fix_value.text()) * $("#number").val()).toFixed(1) + '</b>分 + <b>' + (parseFloat(fftcash_fix_value.text()) * parseFloat($("#number").val())).toFixed(1) + '</b>元</div> ';
        }

        //银行积分+现金
        if (selectPayMethod.val() == "04")
        {
            selectType = "notratio";
            var bankpint_fix_value = $("#bankpint_fix_value");
            var bankcash_fix_value = $("#bankcash_fix_value");
            showPayMethodHtml = '<div class="userpoints">商品总价：<b>' + (parseFloat(bankpint_fix_value.text()) * parseFloat($("#number").val())).toFixed(1) + '</b>分 + <b>' + (parseFloat(bankcash_fix_value.text()) * parseFloat($("#number").val())).toFixed(1) + '</b>元</div> ';
        }

        //分分通积分+现金(比例)
        if (selectPayMethod.val() == "05")
        {
            selectType = "ratio";
            showPayMethodHtml = '<div class="meinput validate" id="formlist"><strong>使用积分：<input type="text" class="loginText4" id="points" value=""/><i>可使用积分：<span id="default_fftpoint">1080-2100</span> 分，您还需要支付现金<span id="default_fftpointCash">200</span> 元</i><span id="myMessage"></span></strong></div>';
            showPayMethod.html(showPayMethodHtml);

            var fftpointsRatio_value = $("#fftpointsRatio_value").text();
            var fftpointsCashRatio_value = $("#fftpointsCashRatio_value").text();
            var fftpointsRatio = fftpointsRatio_value.split("-");
            var fftpointsCashRatio = fftpointsCashRatio_value.split("-");
            var default_fftpoint = $("#default_fftpoint");
            var default_fftpointCash = $("#default_fftpointCash");
            var total_cash = parseFloat(fftpointsRatio[0]) + parseFloat(fftpointsCashRatio[0]);
            var myMessage = $("#myMessage");
            if (Math.ceil(parseFloat(fftpointsRatio_value.split("-")[0]) * parseFloat($("#number").val())) > Math.floor(parseFloat(fftpointsRatio_value.split("-")[1]) * parseFloat($("#number").val())))
            {
                default_fftpoint.text("0-0");//使用范围
            } else
            {
                default_fftpoint.text(Math.ceil(parseFloat(fftpointsRatio_value.split("-")[0]) * parseFloat($("#number").val())) + "-" + Math.floor(parseFloat(fftpointsRatio_value.split("-")[1]) * parseFloat($("#number").val())));//使用范围
            }
            default_fftpointCash.text((parseFloat(fftpointsCashRatio[0]) * parseFloat($("#number").val())).toFixed(2));
            $("#usePointRaioValue").val(fftpointsRatio[0]);

            var allMo = parseFloat(fftpointsRatio_value.split("-")[0]) + parseFloat(fftpointsCashRatio[0]);
            $("#points").focus(function ()
            {
                $(this).attr("className", "focusText4");
                myMessage.html("<h4>请输入兑换积分</h4>");
            });

            $("#points").bind("keyup", function ()
            {
                var points_value = $(this).val();

                if ($.trim(points_value) == "")
                {
                    //$(this).val(fftpointsRatio[0]);
                    return;
                }

                var regexp_value = /^[1-9]\d*$/;

                if (!regexp_value.test(points_value))
                {
                    //$(this).attr("className","errorText4");
                    myMessage.html("<h3>请正确输入积分</h3>");
                    return;
                }

                if (parseFloat(points_value) < Math.ceil(parseFloat(fftpointsRatio_value.split("-")[0]) * parseFloat($("#number").val())) || parseFloat(points_value) > Math.floor(parseFloat(fftpointsRatio_value.split("-")[1]) * parseFloat($("#number").val())))
                {
                    $(this).attr("className", "errorText4");
                    myMessage.html("<h3>请正确输入积分范围</h3>");
                    return;
                }
                else
                {
                    $(this).attr("className", "loginText4");
                    myMessage.html("<h2>输入成功</h2>");
                    $("#totalPoints").val($("#points").val());
                    $("#usePointRaioValue").val(points_value);
                    default_fftpointCash.text((allMo * parseFloat($("#number").val()).toFixed(2) - parseFloat(points_value)).toFixed(2));
                    $("#totalAmount").val((allMo * parseFloat($("#number").val()) - parseFloat(points_value)));
                }
            });
            return;
        }

        //银行积分+现金(比例)
        if (selectPayMethod.val() == "06")
        {
            selectType = "ratio";
            showPayMethodHtml = '<div class="meinput validate" id="formlist"> <strong>商品使用积分：<input type="text" class="loginText4" id="points"><i>可使用积分：<span id="default_bankpoint">1080-2100</span>分，您还需要支付现金<span id="default_bankpointCash">200</span>元</i><span id="myMessage"></span></strong></div>';
            showPayMethod.html(showPayMethodHtml);

            var bankpointsRatio_value = $("#bankpointsRatio_value").text();
            var bankpointsCashRatio_value = $("#bankpointsCashRatio_value").text();
            var bankpointsRatio = bankpointsRatio_value.split("-");
            var bankpointsCashRatio = bankpointsCashRatio_value.split("-");
            var default_bankpoint = $("#default_bankpoint");

            var default_bankpointCash = $("#default_bankpointCash");
            var total_cash = parseFloat(bankpointsRatio[0]) + parseFloat(bankpointsCashRatio[0]);
            var myMessage = $("#myMessage");

            //$("#points").val(bankpointsRatio[0]);//积分值
            if (Math.ceil(parseFloat(bankpointsRatio_value.split("-")[0]) * parseFloat($("#number").val())) > Math.floor(parseFloat(bankpointsRatio_value.split("-")[1]) * parseFloat($("#number").val())))
            {
                default_bankpoint.text("0-0");
            } else
            {
                default_bankpoint.text(Math.ceil(parseFloat(bankpointsRatio_value.split("-")[0]) * parseFloat($("#number").val())) + "-" + Math.floor(parseFloat(bankpointsRatio_value.split("-")[1]) * parseFloat($("#number").val())));//使用范围
            }
            //(parseFloat(bankpointsCashRatio[0])*parseFloat($("#number").val())).toFixed(2)
            default_bankpointCash.text((parseFloat(bankpointsCashRatio[0]) * parseFloat($("#number").val())).toFixed(2));
            var allMo = parseFloat(bankpointsRatio_value.split("-")[0]) + parseFloat(bankpointsCashRatio[0]);
            $("#usePointRaioValue").val(bankpointsRatio[0]);

            $("#points").focus(function ()
            {
                $(this).attr("className", "focusText4");
                myMessage.html("<h4>请输入兑换积分</h4>");
            });

            $("#points").bind("keyup", function ()
            {
                var points_value = $(this).val();

                if ($.trim(points_value) == "")
                {
                    //$(this).val(bankpointsRatio[0]);
                    return;
                }

                var regexp_value = /^[1-9]\d*$/;

                if (!regexp_value.test($("#points").val()))
                {
                    //$(this).attr("className","errorText4");
                    myMessage.html("<h3>请正确输入积分</h3>");
                    return;
                }

                if (parseFloat(points_value) < Math.ceil(parseFloat(bankpointsRatio_value.split("-")[0]) * parseFloat($("#number").val())) || parseFloat(points_value) > Math.floor(parseFloat(bankpointsRatio_value.split("-")[1]) * parseFloat($("#number").val())))
                {
                    //$(this).attr("className","errorText4");
                    myMessage.html("<h3>请正确输入积分范围</h3>");
                    return;
                }
                else
                {
                    $(this).attr("className", "loginText4");
                    myMessage.html("<h2>输入成功</h2>");
                    $("#totalPoints").val($("#points").val());
                    $("#usePointRaioValue").val(points_value);
                    default_bankpointCash.text(((allMo * parseFloat($("#number").val())).toFixed(2) - (parseFloat(points_value)).toFixed(2)).toFixed(2));
                    $("#totalAmount").val((allMo * parseFloat($("#number").val())).toFixed(2) - (parseFloat(points_value)).toFixed(2));
                }
            });
            return;
        }
        showPayMethod.html(showPayMethodHtml);
    }

    //选择支付方式
    $("#selectPayMethod").change(selectEvent);

    //立即兑换
    $('a.exchangeBut').click(function ()
    {

        var formObject = document.getElementById('formlist');
        if (formObject != null)
        {
            var subm = formObject.getElementsByTagName('h3').length;
            if (subm)
            {
                return;
            }
        }
        //selectEvent();
        if (selectType == 'ratio')
        {
            var regexp_value = /^[1-9]\d*$/;
            if (!regexp_value.test($("#points").val()))
            {
                var myMessage = $("#myMessage");
                //$(this).attr("className","errorText4");
                myMessage.html("<h3>请正确输入积分</h3>");
                return;
            }
        }

        var goodsExchangeRackId = $("#goodsExchangeRackId");
        var selectPayMethod = $("#selectPayMethod");

        if ($.trim(selectPayMethod.val()) == "")
        {
            $.layer({
                title: ['分分通提示您', true],
                time: 3,
                area: ['auto', 'auto'],
                dialog: {msg: '请选择支付方式', type: 8}
            });

            return false;
        }
        else
        {
            $('#exchangeSpecialtyViewForm').submit()
        }

        return false;
    });

})

</script>


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

<div class="miss" style="display:none" id="show">
    <h2>抱歉，数量有限，您最多只能购买 <b id="boxnumber"></b> 件</h2>
    <a href="#" onclick="closed()"></a>
</div>

<div class="box1010 pt10 clear">
    <!--内容开始-->
    <form id="exchangeSpecialtyViewForm" method="post" action="exchange_local_view.action" >
        <input id="totalAmount" type="text" style="display:none;" value="" name="exchangeTempBean.totalAmount"/>
        <input id="totalPoints" type="text" style="display:none;" value="" name="exchangeTempBean.totalPoints"/>
        <input id="goodsExchangeRackId" name="exchangeTempBean.goodsRackId" value="${(goodsExchangeRack.id?c)!""}" type="hidden">
        <input name="exchangeTempBean.goodsCategoryId" value="${(goodsExchangeRack.goodsCategoryId)!""}" type="hidden">
        <input id="usePointRaioValue" name="exchangeTempBean.usePointRaioValue" type="hidden">

        <div class="points fl">
            <div class="leffShow">
                <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsExchangeRack.goods.sourceUrl)!""}"">

            </div>
            <div class="rightInfor">
                <dl>
                    <dt>${(goodsExchangeRack.goodsRackName)!""}</dt>

                <#if goodsExchangeRack?exists>
                    <#assign cash=goodsExchangeRack.cashPricing>
                    <#assign cashPricing=cash?number>
                    <#if goodsExchangeRack?exists && goodsExchangeRack.isCash=="1" >
                        <dd>现金兑换：<b id="cash_value">${goodsExchangeRack.cashPricing!"0"}</b>元</dd>
                    </#if>

                    <#if goodsExchangeRack?exists && goodsExchangeRack.isFftPoint=="1" >
                        <dd>分分通积分兑换：<b id="fftpoint_value">${goodsExchangeRack.fftPointPricing!"0"}</b>分</dd>
                    </#if>

                    <#if goodsExchangeRack?exists && goodsExchangeRack.isBankPoint=="1" >
                        <dd>银行积分兑换：<b id="bankpoint_value">${goodsExchangeRack.bankPointPricing!"0"}</b>分</dd>
                    </#if>

                    <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointCash=="1" >
                        <#assign fftpointsAndCashArray=goodsExchangeRack.fftpointCashPricing?split('|')>
                        <dd>分分通积分+现金兑换：<b id="fftpoint_fix_value">${fftpointsAndCashArray[0]}</b>分+<b id="fftcash_fix_value">${fftpointsAndCashArray[1]}</b>元</dd>
                    </#if>

                    <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointCash=="1" >
                        <#assign bankpointsAndCashArray=goodsExchangeRack.bankpointCashPricing?split('|')>
                        <dd>银行积分+现金兑换：<b id="bankpint_fix_value">${bankpointsAndCashArray[0]}</b>分+<b id="bankcash_fix_value">${bankpointsAndCashArray[1]}</b>元</dd>
                    </#if>

                    <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointcashRatioPricing=="1" >
                        <#assign fftpointsAndCashRatioArray=goodsExchangeRack.fftpointcashRatioPricing?split('|')>
                        <#assign fftpointsRatioMin=fftpointsAndCashRatioArray[0]>
                        <#assign fftpointsRatioMinPercent=fftpointsRatioMin?number>
                        <#assign fftpointsRatioMinPercentDivision=fftpointsRatioMinPercent/100>
                        <#assign fftpointsRatioMax=fftpointsAndCashRatioArray[1]>
                        <#assign fftpointsRatioMaxPercent=fftpointsRatioMax?number>
                        <#assign fftpointsRatioMaxPercentDivision=fftpointsRatioMaxPercent/100>
                        <#assign fftpointsMin=cashPricing*fftpointsRatioMinPercentDivision>
                        <#assign fftpointsMax=cashPricing*fftpointsRatioMaxPercentDivision>
                        <dd>分分通积分+现金兑换：<b id="fftpointsRatio_value">${fftpointsMin}-${fftpointsMax}</b>分+<b id="fftpointsCashRatio_value">${cashPricing-fftpointsMin}-${cashPricing-fftpointsMax}</b>元
                        </dd>
                    </#if>

                    <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointcashRatioPricing=="1" >
                        <#assign bankpointsAndCashRatioArray=goodsExchangeRack.bankpointcashRatioPricing?split('|')>
                        <#assign bankpointsRatioMin=bankpointsAndCashRatioArray[0]>
                        <#assign bankpointsRatioMinPercent=bankpointsRatioMin?number>
                        <#assign bankpointsRatioMinPercentDivision=bankpointsRatioMinPercent/100>
                        <#assign bankpointsRatioMax=bankpointsAndCashRatioArray[1]>
                        <#assign bankpointsRatioMaxPercent=bankpointsRatioMax?number>
                        <#assign bankpointsRatioMaxPercentDivision=bankpointsRatioMaxPercent/100>
                        <#assign bankpointsMin=cashPricing*bankpointsRatioMinPercentDivision>
                        <#assign bankpointsMax=cashPricing*bankpointsRatioMaxPercentDivision>
                        <dd>银行积分+现金兑换：<b id="bankpointsRatio_value">${bankpointsMin}-${bankpointsMax}</b>分+<b id="bankpointsCashRatio_value">${cashPricing-bankpointsMin}
                            -${cashPricing-bankpointsMax}</b>元
                        </dd>
                    </#if>
                </#if>
                </dl>
            </div>

            <div class="buyBox">
                <div class="mebuy">
                    商品数量：
                    <a href="javascript:void(0)" id="minusNumber">-</a>
                    <input name="exchangeTempBean.buyNumber" type="text" class="loginText5" id="number" value="1" maxlength="2" class="loginText5">
                    <a href="javascript:void(0)" id="addNumber">+</a>
                </div>

                <div class="meSelect">
                    <div class="fl mt9">支付方式：</div>
           <span class="fl" id="uboxstyle">  
            <select id="selectPayMethod" name="exchangeTempBean.payMethod">
                
                 <option value="09" selected="selected">现金（支付宝支付）</option>
            <#if goodsExchangeRack?exists && goodsExchangeRack.isCash=="1" >
                <option value="02">现金（珠海农商银行手机银行卡支付）</option>
            </#if>
            <#if goodsExchangeRack?exists && goodsExchangeRack.isFftPoint=="1">
                <option value="00">分分通积分</option>
            </#if>
            <#if goodsExchangeRack?exists && goodsExchangeRack.isBankPoint=="1" >
                <option value="01">银行积分</option>
            </#if>
            <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointCash=="1" >
                <option value="03">分分通积分+现金</option>
            </#if>
            <#if goodsExchangeRack?exists && goodsExchangeRack?exists && goodsExchangeRack.isBankpointCash=="1" >
                <option value="04">银行积分+现金</option>
            </#if>
            <#if goodsExchangeRack?exists && goodsExchangeRack.isFftpointcashRatioPricing=="1" >
                <option value="05">分分通积分+现金（比例）</option>
            </#if>
            <#if goodsExchangeRack?exists && goodsExchangeRack.isBankpointcashRatioPricing=="1" >
                <option value="06">银行积分+现金（比例）</option>
            </#if>
            </select>
            </span>
                </div>

                <div id="showPayMethod"><div class="userpoints">商品总价：<b>${cashPricing}</b>元</div></div>
                <a href="javascript:void(0);" class="exchangeBut"><img src="${base}/template/web/images/dhbtn.png"></a>
            <#if goodsExchangeRack.isPresentPoints==1 && pointRule.pointValue !='0'>
                <span class="exchangetext">支付完成后可获得${pointRule.pointValue}个分分通积分</span>
            </#if>
            </div>
            <script type="text/javascript" src="${base}/template/common/js/select.js"></script>
            <div class="contantInr"> ${(goodsExchangeRack.goodsRackDetail)!""}</div>
        </div>
        <!--内容结束-->
    </form>
<#include "/WEB-INF/templates/common/exch_right.ftl">
</div>
<#if !goodsExchangeRack?exists>
<script type="text/javascript">
    $(document).ready(function ()
    {
        $.layer({
            title: ['分分通提示您', true],
            area: ['auto', 'auto'],
            dialog: {msg: '参数异常！', type: 3}
        });
    });
</script>
</#if>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>