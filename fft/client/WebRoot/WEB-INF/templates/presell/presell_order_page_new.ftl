<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>分分通预售精品订单页</title>
<#include "/WEB-INF/templates/common/include.ftl">
    <link href="${base}/template/buy/css/pay.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/common/css/select2.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/pay.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/template/common/js/headsearch.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/closeDialog.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/form.js"></script>

</head>
<body>

<script type="text/javascript">
    function selectDeliveryInfo(deliveryIndex)
    {
        var key = "#deliverySelect" + deliveryIndex;
        var deliveryValue = $(key).val();
        $("#deliveryId").val(deliveryValue);

        $("li[litype='deliverySelect']").removeClass("select");
        $(key).addClass("select");
    }

    function selectPayPointType(type)
    {
        $("#payPointType").val(type);
        $("#inputPoints").removeAttr("disabled");
    }

    function selectPayCashType(type)
    {
        var key = "#payCashTypeSId" + type;
        $(key).attr("checked", "checked");
        $("#payCashType").val(type);
    }
    function showMessage(message)
    {
        $.layer({
            title: ['分分通提示您', true],
            area: ['auto', 'auto'],
            dialog: {msg: message, type: 9}
        });
    }
</script>
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

<div class="miss" id="show">
    <h2 id="warmTitle"></h2>
    <a href="#" onclick="closed()"></a>
</div>


<div class="box1010 pt10 clear">
    <!--付款详情开始-->
    <form id="presellBuy" name="presellBuy" method="post" action="presell_order_new.action" onSubmit="return checkAndSubmit()">
        <input type="hidden" id="submitTimes" value="0"/>
        <input type="hidden" name="tempTran.clientType" id="clientType" value="100"/>
        <input type="hidden" name="tempTran.goodsRackId" id="radio" value="${(tempTran.goodsRackId)!""}"/>
        <input type="hidden" name="pager.groupPrice" id="radio" value="${(pager.groupPrice)!""}"/>
        <input type="hidden" name="pager.cashPricing" id="cashPricing" value="${(pager.cashPricing)!""}"/>
        <input type="hidden" name="tempTran.goodsName" id="radio" value="${(pager.goods.goodsName)!""}"/>
        <input type="hidden" name="pager.goods.goodsName" id="radio" value="${(pager.goods.goodsName)!""}"/>
        <input type="hidden" id="cash" value="${cash!"0"}"/>
        <input type="hidden" id="fftPointPricing" value="${fftPointPricing!"0"}"/>
        <input type="hidden" id="bankPointPricing" value="${bankPointPricing!"0"}"/>
        <input type="hidden" id="cashFftPointPricing" value="${cashFftPointPricing!"0"}"/>
        <input type="hidden" id="cashFftPointPricingCash" value="${cashFftPointPricingCash!"0"}"/>
        <input type="hidden" id="cashBankPointPricing" value="${cashBankPointPricing!"0"}"/>
        <input type="hidden" id="cashBankPointPricingCash" value="${cashBankPointPricingCash!"0"}"/>
        <input type="hidden" name="tempTran.bankPointsValueRealAll" id="bankPointsValueRealAll"/>
        <input type="hidden" name="tempTran.fftPointsValueRealAll" id="fftPointsValueRealAll"/>
        <input type="hidden" id="fftPoints1" value="${fftPoint!""}"/>
        <input type="hidden" id="bankPoints1" value="${bankPoint!""}"/>
        <input type="hidden" id="bankPointsValueMin" value="${bankPointValueMin!""}"/>
        <input type="hidden" id="fftPointsValueMin" value="${fftPointValueMin!""}"/>
        <input type="hidden" id="bankPointsValueMax" value="${bankPointValueMax!""}"/>
        <input type="hidden" id="fftPointsValueMax" value="${fftPointValueMax!""}"/>
        <input type="hidden" id="cashFftPointsRatio" value="${cashFftPointsRatio!""}"/>
        <input type="hidden" id="cashBankPointsRatio" value="${cashBankPointsRatio!""}"/>
        <input type="hidden" name="delivery.id" id="deliveryId" value=""/>
        <input type="hidden" name="payPointType" id="payPointType" value=""/>
        <input type="hidden" name="payCashType" id="payCashType" value=""/>


        <div class="paytable validate">
            <div class="stepgroup"></div>
            <div class="table-section mt5">
                <div class="step" style="height:30px;">
                    <li class="colorRed step07">输入信息</li>
                    <li class="step02">生成订单</li>
                    <li class="step03">购买成功</li>
                </div>

                <table cellspacing="0">
                    <tbody>
                    <tr>
                        <td valign="top" align="right">自提点：</td>
                        <td align="left">
              <span class="fl" id="uboxstyle">
              <#list deliveryList as deliverfy>
                  <li litype="deliverySelect" id="deliverySelect${(deliverfy_index?c)}" value="${(deliverfy.id?c)}"
                          ><a href="javascript:void(0);" onclick="javascript:selectDeliveryInfo('${(deliverfy_index?c)!""}');">${(deliverfy.name)}<br/>
                      <font>（营业时间：${(deliverfy.businessTime)}）</font></a>
                  </li>
              </#list>

              </span>
                        </td>
                    </tr>
                    <tr>
                        <td width="50" align="right">商品名称：</td>
                        <td width="610" class="desc">${pager.goods.goodsName?if_exists}</td>
                    </tr>
                    <tr>
                        <td align="right">数量：</td>
                        <td align="left" class="quantity">
                            <a href="javascript:void(0)" onclick="subNum();">-</a>
                            <input class="loginText5" name="tempTran.goodsNumber" maxlength="3" value="${tempTran.goodsNumber}" type="text" id="value" Onchange="numberChange();" onkeyup="numberChange();"/>
                            <a href="javascript:void(0)" onclick="addNum();">+</a>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">单价：</td>
                        <td align="left" class="quantity redFont">
                        ${cash!"0"}元
                        </td>
                    </tr>
                    <tr>
                        <td nowrap="nowrap" valign="top" align="right">支付方式：</td>
                        <td align="left">
                            <div class="useponits">
                            <#if (fftPoint?exists&&fftPoint !="0" ) || (bankPoint?exists &&bankPoint !="0") >
                                使用积分：
                                <#if (pager.isFftPoint?exists&&pager.isFftPoint !="0" )  >
                                    <input name="payPointTypeS" type="radio" onclick="selectPayPointType('1');" value="1"/>分分通积分<font class="redFont">（${(fftPoint)!"0"}分）</font>
                                </#if>
                                <#if (pager.isBankPoint?exists&&pager.isBankPoint !="0"  )  >
                                    <input name="payPointTypeS" type="radio" onclick="selectPayPointType('2');" value="2"/>银行积分<font class="redFont">（${(bankPoint)!"0"}分）</font>
                                </#if>
                                我要使用<input name="payPointValue" id="inputPoints" type="text" style="width:50px" value="0" onkeyup="pointValueChange();"/>分
                            <#else>
                                使用积分：<span style="color: red">无可用积分</span>
                                <input  name="payPointValue" id="inputPoints" type="hidden" style="width:50px" value="0" />
                            </#if>

                            </div>

                            <div id="showpoints" class="usecash">
                                使用现金：<input name="payCashTypeS" id="payCashTypeSId1" type="radio" value="1" onclick="selectPayCashType('1');"/>
                                <img src="${base}/template/presell/images/pay.gif" width="147" height="43" border="1" vspace="bottom" onclick="selectPayCashType('1');"/>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input name="payCashTypeS" type="radio" id="payCashTypeSId2" onclick="selectPayCashType('2');"/>
                                <img src="${base}/template/presell/images/pay1.gif" width="147" height="43" border="1" vspace="bottom" onclick="selectPayCashType('2');"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">总价：</td>
                        <td align="left">
                            <font class="redFont f20 fl" id="totalValueText">0元</font>
                            <span style="float:right;line-height:20px;">积分抵扣：
                                <font class="redFont f20 " id="pointValueShow">0</font>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                支付现金：<font class="redFont f20 " id="chashValueShow">0元</font></span>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div class="confirm">
                    <a href="javascript:checkAndSubmit();" class="fr mt10 mr15">
                        <div class="textBtn"><B>确认无误，购买</B></div>
                    </a>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="../common/js/select.js"></script>
        <script src="../common/js/price.js"></script>

    </form>
    <!--付款详情结束-->
</div>
<script type="text/javascript" src="${base}/template/common/js/select.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
<script type="text/javascript">
var flagVaule = 0;
var cashPricing = $('#cashPricing').val();
var cash = $('#cash').val();
var fftPoints = $('#fftPoints1').val();
var bankPoint = $('#bankPoints1').val();
var bankPointValueMin = $('#bankPointsValueMin').val();
var bankPointValueMax = $('#bankPointsValueMax').val();
var fftPointsValueMin = $('#fftPointsValueMin').val();
var fftPointsValueMax = $('#fftPointsValueMax').val();
var cashFftPointsRatio = $('#cashFftPointsRatio').val();
var cashBankPointsRatio = $('#cashBankPointsRatio').val();
var perNumber = $('#perNumber').val();
var perminNumber = $('#perminNumber').val();

var cashPricing2 = parseFloat(cashPricing);
var fftPoints2 = parseFloat(fftPoints);
var bankPoint2 = parseFloat(bankPoint);
var bankPointValueMin2 = parseFloat(bankPointValueMin);
var bankPointValueMax2 = parseFloat(bankPointValueMax);
var fftPointsValueMin2 = parseFloat(fftPointsValueMin);
var fftPointsValueMax2 = parseFloat(fftPointsValueMax);
var cashFftPointsRatio2 = parseFloat(cashFftPointsRatio);
var cashBankPointsRatio2 = parseFloat(cashBankPointsRatio);
var perNumber2 = parseInt(perNumber);
var perminNumber2 = parseInt(perminNumber);

var timeoutfn;
function initWarmTitle()
{
    //    $("#show").show();
    //    timeoutfn = setTimeout(function ()
    //    {
    //        $("#warmTitle").html("");
    //    }, 11500);
    showMessage();
}

//将相应的数据进行处理不产生小数
function disposeNum()
{
    $("#usablePointsMin").html(Math.ceil($("#usablePointsMin").html()));
    $("#usablePointsMax").html(Math.floor($("#usablePointsMax").html()));
    if (Math.ceil($("#usablePointsMin").html()) > Math.floor($("#usablePointsMax").html()))
    {
        $("#usablePointsMin").html('0');
        $("#usablePointsMax").html('0');
    }
    payShowChange();
}
function numberChange()
{
    var goodsNum = $("#value").val();
    if ("" != goodsNum)
    {

        if (!/^[0-9]*[1-9][0-9]*$/.test(goodsNum))
        {
            $("#value").val('1');
            showMessage("请正确输入商品数量！")
            return;
        }
        if ('0' == goodsNum)
        {
            $("#value").val('1');
            showMessage("请正确输入商品数量！")
            return;
        }
        payShowChange();
    }

}
//积分值改变检查
function pointValueChange()
{
    var payPointType = $("#payPointType").val();
    if ("" == payPointType || null == payPointType)
    {
        $("#inputPoints").val("0");
        showMessage("请选择支付方式！")
        return;
    }
    payShowChange();
}

//针对总价显示与应付显示的检查与修改
function payShowChange()
{
    var goodsCount = $("#value").val();
    var price = $("#cash").val();
    var totalValue = goodsCount * price;
    $("#totalValueText").html(Math.round(totalValue * Math.pow(10, 2)) / Math.pow(10, 2) + "元");

    //需要对积分情况进行检查
    var flag = pointValueCheck();
    if (flag)
    {
        //应付款项
        var pointValue = $("#inputPoints").val();
        var showValue = totalValue - pointValue;

        $("#chashValueShow").html(Math.round(showValue * Math.pow(10, 2)) / Math.pow(10, 2) + "元");
        //积分抵扣显示
        $("#pointValueShow").html(Math.round(pointValue * Math.pow(10, 2)) / Math.pow(10, 2) + "元");

        if (showValue == 0)
        {
            $("#payCashTypeSId1").attr("disabled", "disabled");
            $("#payCashTypeSId2").attr("disabled", "disabled");
            $("#payCashTypeSId1").removeAttr("checked");
            $("#payCashTypeSId2").removeAttr("checked");
        }
        else
        {
            $("#payCashTypeSId1").removeAttr("disabled", "disabled");
            $("#payCashTypeSId2").removeAttr("disabled", "disabled");
        }
    }
}

function checkAndSubmit()
{
    var payPointType = $("#payPointType").val();
    var payCashType = $("#payCashType").val();

    var goodsNum = document.getElementById("value").value;
    if (goodsNum == '' || goodsNum == '0' || goodsNum == null)
    {
        showMessage("商品数量不能为空！")
        return;
    }

    if ((payPointType == "" || payPointType == undefined) && (payCashType == "") || payCashType == undefined)
    {
        showMessage("请选择支付方式！")
        return;
    }
    else
    {
        if (!/^[0-9]*[1-9][0-9]*$/.test(goodsNum))
            return;
        if (pointCheck())
        {
            disposeNum();
            var senda = $("#twoClick");
            senda.attr("href", "javascript:void(0);");
            senda.find("div").attr("class", "gryBtn");
            senda.find('B').html("请等待...");
            presellBuy.submit();
        } else
        {
            //            $("#warmTitle").html("请输入正确的积分数");
            //            initWarmTitle();
            //alert("输入正确的积分数,或者换其他支付方式");
            disposeNum();
            return;
        }
    }
}

function addNum()
{
    var number = $('#value').val();
    if (number >= perNumber2 || number >= 999)
    {
        return;
    }

    number++;
    $('#value').val(number);
    payShowChange();
}

function subNum()
{
    var number = $('#value').val();
    if (number > 1)
    {
        number--;
        $('#value').val(number);
    }
    payShowChange();
}

function pointCheck()
{
    //积分支付方式选择
    var pointMethod = $('#payMethod').val();
    var number = $.trim($('#value').val());
    if (number == '' || number == '0' || number == null)
    {
        showMessage("商品数量不能为空！")
        return false;
    }
    number = parseInt(number);
    if (perminNumber != '' && perminNumber != '0' && number < perminNumber2)
    {
        showMessage('此商品每笔交易最低购买数为 ' + perminNumber + '件')
        //        $('#warmTitle').html('此商品每笔交易最低购买数为 ' + perminNumber + '件');
        //        $('#show').show();
        return false;
    } else if (perNumber != '' && perNumber != '0' && number > perNumber2)
    {
        showMessage('此商品每笔交易最多限购 ' + perNumber2 + '件')
        //        $('#warmTitle').html('此商品每笔交易最多限购 ' + perNumber2 + '件');
        //        $('#show').show();
        return false;
    } else if (perNumber != '' && perNumber != '0' && number == perNumber2)
    {
        showMessage('此商品每笔交易最多限购 ' + perNumber2 + '件')
        //        $('#warmTitle').html('此商品每笔交易最多限购 ' + perNumber2 + '件');
        //        $('#show').show();
    } else if (perNumber != '' && perNumber != '0' && number == perminNumber2)
    {
        showMessage('此商品每笔交易最少限购 ' + perminNumber2 + '件')
        //        $('#warmTitle').html('此商品每笔交易最少限购 ' + perminNumber2 + '件');
        //        $('#show').show();
    } else
    {
        $('#show').hide();

    }
    var goodsCount = $("#value").val();
    var price = $("#cash").val();
    var totalValue = goodsCount * price;

    //应付款项
    var pointValue = $("#inputPoints").val();
    var showValue = totalValue - pointValue;
    //对总价以及现金的检查
    if (showValue > 0 && ( undefined == $("#payCashType").val() || "" == $("#payCashType").val()))
    {
        showMessage('您所输入的积分不足以支付商品金额，请选择现金支付方式！')
        //        $('#warmTitle').html('您所输入的积分不足以支付商品金额，请选择现金支付方式！');
        //        $('#show').show();
        return false;
    }
    pointValueCheck();
    return true;
}
$("#initSelect").attr("selected", "true");//控制下拉框默认选择

function pointValueCheck()
{
    var goodsCount = $("#value").val();
    var price = $("#cash").val();
    var totalValue = goodsCount * price;
    var flag = true;
    //两个积分的检查
    var inputPoints = $('#inputPoints').val();
    if (inputPoints == null || inputPoints == '' || inputPoints == 'undefined')
    {
        inputPoints = '0';
    }
    var checkInputPoint = inputPoints;
    var firstPoint = checkInputPoint.indexOf('.');
    var lastPoint = checkInputPoint.lastIndexOf('.');
    var pointLenght = checkInputPoint.length;

    if (firstPoint == lastPoint)
    {
        checkInputPoint = checkInputPoint.replace('.', '');
    }

    //积分格式检查
    var regex = /^[0-9]+(.[0-9]{1,2})?$/;
    //确保是小数
    if (inputPoints != '0' && !regex.test(inputPoints) && !regex.test(checkInputPoint))
    {
        showMessage("请输入正确的积分数")
        //        $("#warmTitle").html("请输入正确的积分数");
        //        initWarmTitle();
        //alert("请输入整数积分");
        $('#inputPoints').val("");
        return false;
    }
    //确保是两位小数
    if (firstPoint == lastPoint && firstPoint > 0)
    {
        lastPoint = lastPoint + 3;
        if (lastPoint < pointLenght)
        {
            checkInputPoint = inputPoints.substr(0, pointLenght - 1);
            $('#inputPoints').val(checkInputPoint);
        }
    }

    var payPointType = $("#payPointType").val();
    if (null != payPointType && undefined != payPointType && "" != payPointType)
    {
        var fftPoint = ${(fftPoint)};
        var bankPoint = ${(bankPoint)};

        //分分通积
        if ("1" == payPointType)
        {
            flag = true;

            if (inputPoints > fftPoint)
            {
                showMessage("您所输入的积分不能大于您的分分通积分")
                //                $('#warmTitle').html('您所输入的积分不能大于您的分分通积分！');
                //                $('#show').show();
                flag = false;
            }
            if (inputPoints > totalValue)
            {
                showMessage("您所输入的积分不能大于商品总价")
                //                $('#warmTitle').html('您所输入的积分不能大于商品总价！');
                //                $('#show').show();
                flag = false;
            }

            if (!flag)
            {
                $("#inputPoints").val(Math.min(totalValue, fftPoint));
                var pointValue = $("#inputPoints").val();
                var showValue = totalValue - pointValue;
                $("#chashValueShow").html(showValue + "元");
                //积分抵扣显示
                $("#pointValueShow").html(pointValue + "元");
            }
            return flag;
        }
        else if ("2" == payPointType)
        {
            if (inputPoints > bankPoint)
            {
                showMessage("您所输入的积分不能大于您的银行积分!")
                //                $('#warmTitle').html('您所输入的积分不能大于您的银行积分！');
                //                $('#show').show();
                return false;
            }

            if (inputPoints > totalValue)
            {
                showMessage("您所输入的积分不能大于商品总价!")
                //                $('#warmTitle').html('您所输入的积分不能大于商品总价！');
                //                $('#show').show();
                flag = false;
            }
            if (!flag)
            {
                $("#inputPoints").val(Math.min(totalValue, bankPoint));
                var pointValue = $("#inputPoints").val();
                var showValue = totalValue - pointValue;
                $("#chashValueShow").html(showValue + "元");
                //积分抵扣显示
                $("#pointValueShow").html(pointValue + "元");
            }
            return flag;
        }
    }
    return flag;
}
</script>

<script type="text/javascript">
    selectDeliveryInfo(0);
    payShowChange();
    closed();
</script>
</body>
</html>
