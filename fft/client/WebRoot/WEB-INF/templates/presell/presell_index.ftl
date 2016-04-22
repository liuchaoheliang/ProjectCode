<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>预售商品列表</title>
<#include "/WEB-INF/templates/common/include.ftl">
    <link href="${base}/template/common/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/common/css/public.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/booking.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/hot.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/latelook.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/alsolook.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/template/common/js/list.js"></script>
    <link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/borderUI.js"></script>
    <script type="text/javascript" src="${base}/template/presell/js/presell_goods.js"></script>
    <script type="text/javascript" src="${base}/template/presell/js/timerdown.js"></script>

</head>
<body>
<input type="text" id="notfind" style="display:none;" value="${notfind}"/>
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
<script type="text/javascript">
    $(function ()
    {
        $('.list').borderUI();
    })
</script>
<div class="box1010 pt10 clear">
    <!--团购开始-->
    <form id="listForm" action="presell_index.action" method="post">
        <div class="group fl">
        <div class="list">

        <#list pager.list as goodsPresellRack>
            <#if goodsPresellRack_index gt 11 >
                <#break>
            </#if>
            <li>
                <div class="icon"><img src="${base}/template/presell/images/icon_booking.png"></div>
                <div class="imgShow">
                    <a href="presell_goods_detail_new.action?goodsPresellRack.id=${goodsPresellRack.id?c}">
                    <#--<img src="${base}/template/presell/img/001.jpg" height="209">-->
                        <#if (goodsPresellRack.goodsPresellRackImages?size>0)>
                            <#assign ger=goodsPresellRack.goodsPresellRackImages[0]>
                            <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(ger.imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
                        <#else>
                            <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsPresellRack.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
                        </#if>
                    </a>
                </div>
                <div class="bookingtext">
                    <dl>
                        <dt title="${(goodsPresellRack.seoTitle)!""}">
                                <a href="presell_goods_detail_new.action?goodsPresellRack.id=${goodsPresellRack.id?c}">
                                    ${goodsPresellRack.seoTitle}
                                </a>
                            </dt>
                        <dd title="${(goodsPresellRack.seoKeyword)!""}">${(goodsPresellRack.seoKeyword)!""}</dd>
                    </dl>
                    <div class="groupPrice">
                        <div class="firstPrice">￥${(goodsPresellRack.groupPrice)!""}</div>
                        <a href="presell_goods_detail_new.action?goodsPresellRack.id=${goodsPresellRack.id?c}"><img src="${base}/template/presell/images/look.png"></a>
                    </div>
                    <div class="bookingnum">成团数：<b>${(goodsPresellRack.clusteringNum)!""} </b>&nbsp;
                                            已预订<font class="redFont"> ${(goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum)!"0"}</font>件 &nbsp;


                        <#if goodsPresellRack.clusteringNum?exists && goodsPresellRack.trueBuyerNum?exists&&goodsPresellRack.virtualBuyerNum?exists&&goodsPresellRack.clusteringNum gt (goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum)>
                                            还差 <font class="redFont">${(goodsPresellRack.clusteringNum- (goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum))}</font>件成团
                        <#else>
                            <font class="redFont"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#339900;">√ 已成团</span></font>
                        </#if>

                    </div>
                <div class="discount">
                    <div class="leftDiscount">
                        <P>预售期：${(goodsPresellRack.beginTime?date("yyyy-MM-dd"))!""}至${(goodsPresellRack.endTime?date("yyyy-MM-dd"))!""}</P>

                        <P>提货期：${(goodsPresellRack.deliveryStartTime?date("yyyy-MM-dd"))!""}至${(goodsPresellRack.deliveryEndTime?date("yyyy-MM-dd"))!""}</P>
                    </div>
                    <#list goodsStatusMap?keys as statusKey>
                        <#if goodsPresellRack.id==statusKey>
                            <#if goodsStatusMap.get(statusKey)=='0'>
                                <div class="leftDiscount">
                                    <P>预售暂未开始</p>
                                </div>
                                <div class="leftDiscount">
                                    <P>提货暂未开始</p>
                                </div>
                            <#elseif goodsStatusMap.get(statusKey)=='1'>
                                <div>
                                    <div class="countdown">
                                        <font>预售剩余时间：</font>
                                        <#list leftTimeMap?keys as leftTimesKey>
                                            <#if goodsPresellRack.id==leftTimesKey>
                                                <p class="lxftime" endtime="${leftTimeMap.get(leftTimesKey)}" id="timercontainer${goodsPresellRack_index}"></p>
                                            </#if>
                                        </#list>
                                    </div>
                                    <div class="leftDiscount">
                                        <P>提货暂未开始</p>
                                    </div>
                                </div>
                            <#elseif goodsStatusMap.get(statusKey)=='2'>
                                <#if goodsPresellRack.clusteringNum?exists && goodsPresellRack.trueBuyerNum?exists&&goodsPresellRack.virtualBuyerNum?exists&&goodsPresellRack.clusteringNum gt (goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum)>
                                <div class="leftDiscount redFont f20">
                                    预售已结束
                                </div>
                                <#else>
                                    <div class="leftDiscount redFont f20">
                                        预售已结束，正在备货
                                    </div>
                                </#if>
                            <#elseif goodsStatusMap.get(statusKey)=='3'>
                                <div class="leftDiscount redFont f20">
                                    预售已结束，正在提货中
                                </div>
                            <#elseif goodsStatusMap.get(statusKey)=='4'>
                                <#if goodsPresellRack.clusteringNum?exists && goodsPresellRack.trueBuyerNum?exists&&goodsPresellRack.virtualBuyerNum?exists&&goodsPresellRack.clusteringNum gt (goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum)>
                                    <div class="leftDiscount redFont f20">
                                        预售已结束
                                    </div>
                                <#else>
                                    <div class="leftDiscount redFont f20">
                                        预售已结束，提货已完成
                                    </div>
                                </#if>
                            </#if>
                        </#if>
                    </#list>
                </div>
        </div>
            </li>
        </#list>
        </div>
        <div class="page">
        <#if (pager.list?size > 0)>
            <#include "/WEB-INF/templates/common/pager.ftl" />
        <#else>
            目前没有上架的精品预售商品!
        </#if>
        </div>
</div>
</form>
<!--团购结束-->

<div class="fl ml10">
    <!--右侧排行开始-->
    <div class="hot">
        <dl>
            <dt><B>历史预售</B></dt>
            <div id="presellGoodsCloseListDetail"></div>
        </dl>
    </div>
    <!--右侧排行结束-->

    <!--最近浏览开始-->
    <div class="late mt10">
        <dl>
            <dt><B>最近浏览</B></dt>
            <div id="presellGoodsHistoryListDetail"></div>
        </dl>
    </div>
    <!--最近浏览结束-->
</div>

</div>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
<script type="text/javascript">
    $(document).ready(function ()
    {

        if ($("#notfind").val() == 'notfind')
        {
            $.layer({
                title: ['分分通提示您', true],
                time: 3,
                area: ['auto', 'auto'],
                dialog: {msg: '抱歉此商品已下架', type: 8}
            });
        }
    });
</script>
<script type="text/javascript">
    $().ready(function ()
    {
        //热卖排行

        $.ajax({
            url: "presell_goods_close_list.action",
            dataType: "json",
            beforeSend: function (data)
            {
                $("#presellGoodsCloseListDetail").html('<div id="loading"><img src="${base}/template/common/images/ajax-loader.gif"></div>');
            },
            success: function (data)
            {
                var presellGoodsCloseListDetail = "";
                $.each(data, function (j)
                {
                    var num = j + 1;
                    presellGoodsCloseListDetail += '<dd>'
                            + '<div class="title">';
                    if (1 == num)
                    {
                        presellGoodsCloseListDetail += '<i class="redbg">1</i>';
                    }
                    else
                    {
                        presellGoodsCloseListDetail += '<i class="bluebg">' + num + '</i>';
                    }
                    presellGoodsCloseListDetail += '<a href="presell_goods_detail_new.action?goodsPresellRack.id=' + data[j].id + '">' + data[j].title + '</a></div>'
                            + '<div class="imgInfor">'
                            + '<a href="presell_goods_detail_new.action?goodsPresellRack.id=' + data[j].id + '"><img src=' + data[j].img + '></a>'
                            + '<div class="inforPrice">'
                            + '<B>￥' + data[j].groupPrice + '</B>'
                            + '<P>已卖出' + data[j].marketTotalNumber + '份</P>'
                            + '</div>'
                            + '</div>'
                            + '</dd>';
                });
                $("#presellGoodsCloseListDetail").html(presellGoodsCloseListDetail);
            }
        });
    });
</script>
<script type="text/javascript">
    $.zxyTimerDown({
        showId: "timercontainer0"
    })
    $.zxyTimerDown({
        showId: "timercontainer1"
    })
    $.zxyTimerDown({
        showId: "timercontainer2"
    })
    $.zxyTimerDown({
        showId: "timercontainer3"
    })
    $.zxyTimerDown({
        showId: "timercontainer4"
    })
</script>