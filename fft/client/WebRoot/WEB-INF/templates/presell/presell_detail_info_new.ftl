<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>精品预售商品详细信息</title>
<#include "/WEB-INF/templates/common/include.ftl">
    <link href="${base}/template/common/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/common/css/public.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/booking.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/alsolook.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/hot.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/presell/css/latelook.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/template/common/js/list.js"></script>
    <link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/borderUI.js"></script>
    <script type="text/javascript" src="${base}/template/presell/js/presell_goods.js"></script>
    <script type="text/javascript" src="${base}/template/presell/js/presell_goods_big_map.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css"/>
    <script type="text/javascript" src="${base}/template/presell/js/timerdownfordetail.js"></script>


    <style type="text/css">

        .showmapWindow .dialogContent {
            margin  : 0;
            padding : 5px;
        }

    </style>


    <script type="text/javascript">
        $().ready(function ()
        {
            // 添加精品预售商品浏览记录
            $.addPresellGoodsHistory("presell_goods_detail_new.action?goodsPresellRack.id=${goodsPresellRack.id?c}", "${(goodsPresellRack.seoTitle)!""}", "${(goodsPresellRack.groupPrice)!""}", "${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsPresellRack.goods.sourceUrl)!""}");
        })

        function checkAndSubmit()
        {
            var value = $("#value").val();

            var regex = /^[1-9][0-9]*$/g;
            if (value != '0' && !regex.test(value))
            {
                showMessage("请输入正确的商品数量，商品数量只能为数字！");
                $('#goodsNumber').val("1");
                return;
            }
            $("#goodsNumber").val(value);
            presellBuy.submit();
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
<div class="miss" id="show" style="display: none">
    <h2 id="warmTitle"></h2>
    <a href="#" onclick="closed()"></a>
</div>
<div class="box1010 pt10 clear">
    <div class="fl">
        <form id="presellBuy" name="presellBuy" method="post" action="trans_presell.action">
            <input type="hidden" name="tempTran.goodsRackId" id="radio" value="#{goodsPresellRack.id?if_exists}"/>
            <input type="hidden" name="tempTran.goodsNumber" id="goodsNumber" value="1">
            <input type="hidden" id="perNumber" value="${perNumber!""}"/>
            <input type="hidden" id="perminNumber" value="${perminNumber!""}"/>
            <!--团购展示开始-->
            <div class="show">
                <dl>
                    <dt>${(goodsPresellRack.seoTitle)!""}</dt>
                    <dd title="${(goodsPresellRack.seoKeyword)!""}">${(goodsPresellRack.seoKeyword)!""}</dd>
                </dl>
                <div class="inforMiddle">
                    <div class="leftInfor">
                        <div class="leftFirst">￥${(goodsPresellRack.groupPrice)!""}</div>


                    <#list goodsStatusMap?keys as deliverKey>
                        <#if goodsPresellRack.id==deliverKey>
                            <#if goodsStatusMap.get(deliverKey)=='0'>
                            <#--暂未开始-->
                                <div class="imgShow" id="imgShowDiv1">
                                    <a href="javascript:void(0)"><img src="${base}/template/presell/images/zwks.gif"></a>
                                </div>
                                <div class="imgShow" id="imgShowDiv2" style="display: none">
                                    <a href="javascript:checkAndSubmit()"><img src="${base}/template/presell/images/lj-booking.gif"></a>
                                </div>
                                <div class="imgShow" id="imgShowDiv3" style="display: none">
                                    <a href="javascript:void(0)"><img src="${base}/template/presell/images/ysjs.gif"></a>
                                </div>
                            <#elseif goodsStatusMap.get(deliverKey)=='1'>
                            <#--预售中-->
                                <div class="imgShow" id="imgShowDiv2">
                                    <a href="javascript:checkAndSubmit()"><img src="${base}/template/presell/images/lj-booking.gif"></a>
                                </div>
                                <div class="imgShow" id="imgShowDiv3" style="display: none">
                                    <a href="javascript:void(0)"><img src="${base}/template/presell/images/ysjs.gif"></a>
                                </div>
                            <#else>
                            <#--预售结束-->
                                <div class="imgShow">
                                    <a href="javascript:void(0)"><img src="${base}/template/presell/images/ysjs.gif"></a>
                                </div>
                            </#if>
                        </#if>
                    </#list>


                        <div class="mebuy">
                            我要买：
                            <a href="javascript:void(0)" onclick="subNum()">-</a>
                            <input type="text" class="loginText5" id="value" value="1" maxlength="3" onkeyup="numberChange();" onchange="numberChange();" class="loginText5">
                            <a href="javascript:void(0)" onclick="addNum()">+</a>
                        </div>
                        <div class="buyed">已预订<B> ${(goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum)!"0"}</B></div>
                        <div class="over">成团数: ${(goodsPresellRack.clusteringNum)!""}  &nbsp;&nbsp;
                        <#if goodsPresellRack.clusteringNum?exists && goodsPresellRack.trueBuyerNum?exists &&goodsPresellRack.virtualBuyerNum?exists&&goodsPresellRack.clusteringNum gt (goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum)>
                                          还差<b>${(goodsPresellRack.clusteringNum-(goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum))}</b>件成团
                        <#else>
                            <span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#339900;">√ 已成团</span>
                        </#if>
                        </div>
                        <div class="mobile"><img src="${base}/template/presell/images/icon_01.gif"></div>
                        <div class="date">预售期：${(goodsPresellRack.beginTime?date("yyyy-MM-dd"))!""}至${(goodsPresellRack.endTime?date("yyyy-MM-dd"))!""}</div>
                        <div class="date">提货期：${(goodsPresellRack.deliveryStartTime?date("yyyy-MM-dd"))!""}至${(goodsPresellRack.deliveryEndTime?date("yyyy-MM-dd"))!""}</div>

                    </div>
                    <div class="pic">
                    <#list goodsStatusMap?keys as statusKey>
                        <#if goodsPresellRack.id==statusKey>
                            <input type="hidden" id="goodsStatus" value="${goodsStatusMap.get(statusKey)}"/>
                            <#if goodsStatusMap.get(statusKey)=='0'>
                                <#list leftTimeMap?keys as leftTimesKey>
                                    <#if goodsPresellRack.id==leftTimesKey>
                                        <div id="startDiv">
                                            <div class="countdown">
                                                <font>本商品预售将于&nbsp;</font>

                                                <p class="lxftime" endtime="${leftToStartMap.get(leftTimesKey)}" id="timercontainer"></p>
                                            <#--<p class="lxftime" endtime="00:00:00:10" id="timercontainer"></p>-->
                                                <font>&nbsp;后开始</font>
                                            </div>
                                        </div>
                                        <div id="sellDiv" style="display: none">
                                            <div class="countdown">
                                                <font>预售剩余时间&nbsp;</font>

                                            <#--<p class="lxftime" endtime="00:00:00:10" id="timercontainer1"></p>-->
                                                <p class="lxftime" endtime="${leftTimeMap.get(leftTimesKey)}" id="timercontainer1"></p>
                                            </div>
                                        </div>
                                    </#if>
                                </#list>
                            <#elseif goodsStatusMap.get(statusKey)=='1'>
                                <div>
                                    <div class="countdown">
                                        <font>预售剩余时间</font>
                                        <#list leftTimeMap?keys as leftTimesKey>
                                            <#if goodsPresellRack.id==leftTimesKey>
                                                <p class="lxftime" endtime="${leftTimeMap.get(leftTimesKey)}" id="timercontainer1"></p>
                                            </#if>
                                        </#list>
                                    </div>
                                </div>
                            <#elseif goodsStatusMap.get(statusKey)=='2'>
                                <#if goodsPresellRack.clusteringNum?exists && goodsPresellRack.trueBuyerNum?exists&&goodsPresellRack.virtualBuyerNum?exists&&goodsPresellRack.clusteringNum gt (goodsPresellRack.trueBuyerNum+goodsPresellRack.virtualBuyerNum)>
                                    <div class="leftDiscount redFont f20">
                                        预售已结束，此商品未成团
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
                                <div class="leftDiscount redFont f20">
                                    预售已结束，提货已完成
                                </div>
                            </#if>
                        </#if>
                    </#list>

                    <#if (goodsPresellRack.goodsPresellRackImages?size>0)>
                        <#assign ger=goodsPresellRack.goodsPresellRackImages[0]>
                        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(ger.imagesUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
                    <#else>
                        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsPresellRack.goods.sourceUrl)!""}" onError="this.src='${base}/template/common/images/moren.gif'">
                    </#if>

                    </div>
                </div>
            </div>
        </form>
        <script type="text/javascript" src="../common/js/menu.js"></script>
        <script type="text/javascript" src="js/points.js"></script>
        <!--团购展示结束-->

        <!--团购详情开始-->
        <div class="details mt5">
            <div class="main">
                <h2>商品详情</h2>
            ${(goodsPresellRack.seoDescription)!""}
                <div class="tips">
                    <p></p>
                </div>
                <div class="blk detail">
                    <div class="term">
                        <h5>购买须知</h5>
                    ${(goodsPresellRack.buyKnow)!""}
                    </div>
                    <!--项目介绍-->
                    <p class="standard-bar">商品介绍</p>
                    <ul class="list">
                        <li>${(goodsPresellRack.goodsRackDetail)!""}</li>
                    </ul>
                <#--<img src="../web/img/0002.png">-->
                </div>
            </div>
            <div class="side">
                <!--地图开始-->
                <div class="mt10">
                    <div id="map_container" style="width:204px;height:240px;border:1px solid #c7c7c7;"></div>
                    <a href="javascript:void(0);" onclick="javascript:showmapwindow('${(goodsPresellRack.goods.merchant.id?c)!""}','${goodsPresellRack.id?if_exists}');"><img src="${base}/template/buy/images/mapbottom.gif"></a>
                </div>
                <!--地图结束-->

                <h5>自提点信息</h5>

                <div id="storeListDiv">
                </div>
            </div>
        </div>
        <!--团购详情结束-->
    </div>
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

    var map = new BMap.Map("map_container");
    var point = new BMap.Point(116.404, 39.915);
    map.centerAndZoom(point, 12);
    var point;
    var points = [];

    function createInfoWindow(fullName, address, telephone)
    {

        var opts = {offset: new BMap.Size(-5, -5), title: '<span style="font-size:14px;color:#0A8021">' + fullName + '</span>'};
        var infoWindow = new BMap.InfoWindow("<div style='line-height:1.8em;font-size:12px;'><b>地址:</b>" + address + "</br><b>电话:</b>" + telephone + "</br><b></div>", opts);  // 创建信息窗口对象

        return infoWindow;
    }

    function openMyWin(fullName, address, telephone, coordinate)
    {

        var opts = {offset: new BMap.Size(5, -5), title: '<span style="font-size:14px;color:#0A8021">' + fullName + '</span>'};
        var infoWindow = new BMap.InfoWindow("<div style='line-height:1.8em;font-size:12px;'><b>地址:</b>" + address + "</br><b>电话:</b>" + telephone + "</br><b></div>", opts);  // 创建信息窗口对象

        var p0 = coordinate.split(",")[0];
        var p1 = coordinate.split(",")[1];
        var point = new BMap.Point(p0, p1);

        map.openInfoWindow(infoWindow, point);

    }
    function addNum()
    {
        var number = $('#value').val();
        if (number >= 999)
        {
            return;
        }
        number++;
        $('#value').val(number);
        //        pointCheck()

    }

    function subNum()
    {
        var number = $('#value').val();
        if (number > 1)
        {
            number--;
            $('#value').val(number);
        }
        //        pointCheck()

    }
    function pointCheck()
    {
        //
        var number = $.trim($('#value').val());
        if (number == '')
        {
            $('#warmTitle').html('商品数量不能为空');
            $('#show').show();
            return false;
        }
        number = parseInt(number);
        if (perminNumber != '' && perminNumber != '0' && number < perminNumber2)
        {
            $('#warmTitle').html('此商品每笔交易最低购买数为 ' + perminNumber + '件');
            $('#show').show();
            return false;
        } else if (perNumber != '' && perNumber != '0' && number > perNumber2)
        {
            $('#warmTitle').html('此商品每笔交易最多限购 ' + perNumber2 + '件');
            $('#show').show();
            return false;
        } else if (perNumber != '' && perNumber != '0' && number == perNumber2)
        {
            $('#warmTitle').html('此商品每笔交易最多限购 ' + perNumber2 + '件');
            $('#show').show();
        } else if (perNumber != '' && perNumber != '0' && number == perminNumber2)
        {
            $('#warmTitle').html('此商品每笔交易最少限购 ' + perminNumber2 + '件');
            $('#show').show();
        } else
        {
            $('#show').hide();
        }
        return true;
    }

    function initWarmTitle()
    {
        $("#show").show();
        timeoutfn = setTimeout(function ()
        {
            $("#warmTitle").html("");
        }, 11500);
    }
    function closed()
    {
        document.getElementById("show").style.display = "none";
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
        }
    }
</script>
<script type="text/javascript">
    var goodsStatus = $("#goodsStatus").val();

    if ("0" == goodsStatus)
    {
        $.leftToStart({
            showId: "timercontainer"
        });
    }
    else if ("1" == goodsStatus)
    {
        $.zxyTimerDown({
            showId: "timercontainer1"
        });
    }

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

    baseMapInit('${(goodsPresellRack.goods.merchant.id?c)!""}', '${goodsPresellRack.id?if_exists}');
</script>