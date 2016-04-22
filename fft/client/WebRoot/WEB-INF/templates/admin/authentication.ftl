<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>分分通-认证中心</title>
<#include "/WEB-INF/templates/common/include.ftl">
    <link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
-->
<!--头部开始 -->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束 -->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束-->

<div class="middleBox">
<!-- 商家管理菜单开始 -->
<#include "/WEB-INF/templates/common/merchant_manage_menu.ftl">
<!-- 商家管理菜单结束 -->
<div class="rightBox" id="rightHeight">
    <div class="inforBox">
        <div class="attest">
            <input type="radio" name="type" id="authRadioOne" value="1" checked="true"/>积分兑换认证
            <input type="radio" name="type" id="authRadioTwo" value="0"/>团购认证

        <#if merchant.activityId?exists && merchant.activityId=="1">
            <input type="radio" name="type" id="authRadioThree" value="2"/>快乐金秋
        </#if>

        <#if merchant.isSpringActivity?exists && merchant.isSpringActivity=="2">
            <input type="radio" name="type" id="authRadioFour" value="3"/>春节抽奖
        </#if>

        <#if merchant.isAffordPresellFlag?exists && merchant.isAffordPresellFlag=="1">
            <input type="radio" name="type" id="authRadioFive" value="5"/>精品预售认证
        </#if>
        </div>
        <dl>
            <dt>验证码：</dt>
            <dd>
                <div><span><input id="securitiesNo" name="securitiesNo" type="text" class="loginText2" value=""></span></div>
                <input type="hidden" name="merchantId" id="merchantId" value="${merchantId!""}"/>
            </dd>
        </dl>
        <div id="tranInfo" class="clear pt10" style="display:none">
        </div>

        <div class=" ml80 mt10">
            <a href="#">
                <div id="rezheng" class="textBtn"><B>验证</B></div>
            </a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束  -->
<script type="text/javascript">
    $('#authRadioOne').click(initData);
    $('#authRadioTwo').click(initData);
    $('#authRadioThree').click(initData);
    $('#authRadioFour').click(initData);
    function initData()
    {
        $('#securitiesNo').val("");
        $('#tranInfo').html("");
    }
    $('#rezheng').click(function ()
    {
        $('#tranInfo').html('');
        $('#tranInfo').hide();
        authentication();
    });
    function authentication()
    {

        var merchantId = $('#merchantId').val();
        var securitiesNo = $.trim($('#securitiesNo').val());
        var type = $("input[name='type']:checked").val();
        if (securitiesNo == '')
        {
            $.layer({
                title: ['分分通提示您', true],
                time: 3,
                area: ['auto', 'auto'],
                dialog: {msg: '请输入认证号码', type: 8}
            });
            return;
        }
        if (type == '0' || type == '1')
        {
            $.getJSON("groupExchange_authentication.action?authTicket.merchantId=" + merchantId + "&authTicket.type=" + type + "&authTicket.securitiesNo=" + securitiesNo, function (data)
            {
                if (data.reno == "0")
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: '认证成功', type: 9}
                    });
                } else
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: data.message, type: 9}
                    });
                    //TODO:
                }
                if (data.tranReno == "0")
                {
                    var html = '<table width="80%" border="0" cellspacing="0" cellpadding="0" class="tableA"><tr><th width="15%">商户</th><th width="12%">订单号</th><th width="23%">商品</th><th width="13%">分分通积分</th><th width="7%">银行</th><th width="9%">现金</th></tr>';
                    $.each(data.tranDetailsList, function (key, value)
                    {
                        html += '<tr>' +
                                '<td>' + data.merchant + '</td>' +
                                '<td>' + data.tranId + '</td>' +
                                '<td>' + value.goodsName + '</td>' +
                                '<td>' + data.pointsFFT + '</td>' +
                                '<td>' + data.pointsBank + '</td>' +
                                '<td>' + data.currency + '</td>' +
                                '</tr>' +
                                '<tr>' +
                                '<td colspan="7">' + value.goodsDesc + '</td>' +
                                '</tr>';
                    });
                    html += '</table>';
                    $('#tranInfo').html(html);
                    $('#tranInfo').show();
                }
            });
        } else if (type == '2')
        {
            $.getJSON("activity_authentication.action?securitiesNo=" + securitiesNo, function (data)
            {
                if (data.reno == "0")
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: data.message, type: 9}
                    });
                } else
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: data.message, type: 8}
                    });
                    //TODO:
                }
            });
        } else if (type == '3')
        {
            $.getJSON("springfestival_authentication.action?securitiesNo=" + securitiesNo, function (data)
            {
                if (data.reno == "0")
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: data.message, type: 9}
                    });
                } else
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: data.message, type: 8}
                    });
                    //TODO:
                }
            });
        }
        //精品预售认证，add by 侯国权

        else if ('5' == type)
        {
            $.getJSON("presell_authentication.action?authTicket.merchantId=" + merchantId + "&authTicket.type=" + type + "&authTicket.securitiesNo=" + securitiesNo, function (data)
            {
                if (data.reno == "0")
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: '认证成功', type: 9}
                    });
                } else
                {
                    $.layer({
                        title: ['分分通提示您', true],
                        time: 3,
                        area: ['auto', 'auto'],
                        dialog: {msg: data.message, type: 9}
                    });
                    //TODO:
                }
                if (data.tranReno == "0")
                {
                    var html = '<table width="80%" border="0" cellspacing="0" cellpadding="0" class="tableA"><tr><th width="12%">订单号</th><th width="23%">商品</th><th width="7%">数量</th><th width="13%">分分通积分</th><th width="7%">银行</th><th width="9%">现金</th></tr>';
                    html += '<tr>' +
                                 '<td>' + data.tranId + '</td>' +
                                 '<td>' + data.goodsName + '</td>' +
                                 '<td>' + data.goodsNumber + '</td>' +
                                 '<td>' + data.pointsFFT + '</td>' +
                                 '<td>' + data.pointsBank + '</td>' +
                                 '<td>' + data.currency + '</td>' +
                                 '</tr>' +
                                 '<tr>' +
                                 '<td  width="15%">商品描述</td>' + '<td align="left"  colspan="5">' + data.goodsDesc + '</td>' +
                                 '</tr>' +
                                 '<tr>' +
                                 '<td width="15%">提货点信息</td>' +'<td align="left" colspan="5">' + data.deliveryName + '</td>' +
                                 '</tr>'
                         ;
                    html += '</table>';
                    $('#tranInfo').html(html);
                    $('#tranInfo').show();
                }
            });
        }

        else
        {
            $.layer({
                title: ['分分通提示您', true],
                time: 3,
                area: ['auto', 'auto'],
                dialog: {msg: '请选择认证类型', type: 8}
            });
            return;
        }
        $('#securitiesNo').val("");
    }
</script>
</body>
</html>
