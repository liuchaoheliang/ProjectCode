<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>分分通</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon"/>
<#include "/WEB-INF/templates/common/include.ftl">

    <link href="${base}/template/common/css/pointright.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/web/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/web/css/exchangelist.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/template/web/css/banner.css" rel="stylesheet" type="text/css"/>


    <script type="text/javascript" src="${base}/template/common/js/headlist.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/headsearch.js"></script>
    <script type="text/javascript" src="${base}/template/web/js/index.js"></script>
    <script type="text/javascript" src="${base}/template/web/js/coin-slider.min.js"></script>
    <script src="${base}/template/web/js/roll.js"></script>


</head>
<script type="text/javascript">
    $(function ()
    {
        $("#ashowlist .arrow1").hide();
        $("#ashowlist .arrow2").hide();
        $("#ashowlistB .arrow1").hide();
        $("#ashowlistB .arrow2").hide();

        $("#ashowlist").hover(function ()
                {
                    $("#ashowlist .arrow1").stop(true, true).fadeIn(100);
                    $("#ashowlist .arrow2").stop(true, true).fadeIn(100);
                }
                , function ()
                {
                    $("#ashowlist .arrow1").stop(true, true).fadeOut(1000);
                    $("#ashowlist .arrow2").stop(true, true).fadeOut(1000);
                });

        $("#ashowlistB").hover(function ()
                {
                    $("#ashowlistB .arrow1").stop(true, true).fadeIn(100);
                    $("#ashowlistB .arrow2").stop(true, true).fadeIn(100);
                }
                , function ()
                {
                    $("#ashowlistB .arrow1").stop(true, true).fadeOut(1000);
                    $("#ashowlistB .arrow2").stop(true, true).fadeOut(1000);
                })
    })
</script>


<body style="background:#f6f7fb">

<!--
* Author:
* pengling@f-road.com.cn
*/
-->

<script type="text/javascript">
    function screenWidth(className, cutNumber)
    {
        try
        {
            var offLeft = $("." + className).offset().left;
        }

        catch (e)
        {
        }
        $(".pointright").css('right', offLeft - cutNumber)

    }

    $(window).resize(function ()
    {
        screenWidth("indexMenu", 70);
        screenWidth("middleBox", 90);
        screenWidth("box1010", 90);
        screenWidth("down", 70)
    });
</script>
<script type="text/javascript">
    $(function ()
    {
        var timer = setTimeout(function ()
        {
            $(".chunjie").slideUp(1000);
            $(".chunjie2").slideDown(1000);
        }, 3000);

        $(".chunjie").click(function ()
        {
            $(this).slideUp(1000);
            $(".chunjie2").slideDown(1000);
        })

    })
</script>

<!--弹出层-->
<div id="showDialog" style="display:none">
    <div class="newinfor" id="newinfor"><a href="#" onclick="showDialogClose()"><img
            src="${base}/template/web/images/newinfor3.png"></a></div>
    <div style="background-color: rgb(0, 0, 0); opacity: 0.9;filter:alpha(opacity=90); position: absolute; z-index: 999; left: 0px; top: 0px; display:none; cursor:pointer;"
         id="pay_mask"></div>
</div>
<!--弹出层-->

<!--春节活动开始-->
<div class="chunjie" style="display:block">
    <p><a href="spring_festival_index.action"><img src="${base}/template/web/images/201401.jpg"></a></p>
</div>
<div class="chunjie2" style="display:none">
    <p><a href="spring_festival_index.action"><img src="${base}/template/web/images/201402.jpg"></a></p>
</div>
<!--春节活动结束-->
<style>
    .chunjie3 {
        display : none;
    }

    body {
        background : #fff
    }

    a {
        margin  : 0px;
        border  : 0px;
        padding : 0px;
    }
</style>
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">

<!--导航结束-->
<div class="main">
<div id="lunhuanback">
    <p style='background-image:url(${base}/template/web/img/0251d.jpg); opacity: 1;filter:alpha(opacity=100);'><a
            href="#"
            target="_blank"> </a>
    </p>
</div>
<div class="newbanner">
<div class="board">
    <dt>公告栏</dt>
<#list announcementList as announcement>
    <dd><a href="announcement_info.action?id=${(announcement.id?c)!""}">${(announcement.title)!""}</a></dd>
</#list>
</div>
<script type="text/javascript">
    $().ready(function ()
    {
        //广告
        $.ajax({
            url: "ajax_advert_list.action?rad=" + Math.random(),
            type: "get",
            data: {
                "advert.module": "0",

                "advert.position": "1"
            },
            dataType: "json",
            beforeSend: function (data)
            {

            },
            success: function (data)
            {
                var elementCounts = 0;
                var indexChangeAdvertHtml = "";
                //广告
                $.each(data, function (i)
                {

                    //if(i>5){
                    //	return false;
                    //}

                    var advertImg = "";

                    var link = data[i].link;
                    var isblank = data[i].is_blank;

                    if (link == "")
                    {
                        advertImg = '<a href="#"><img src=' + data[i].images + ' alt="" width="770" height="247"/></a>';
                        elementCounts += 1;
                    }
                    else
                    {
                        if (isblank == "1")
                        {
                            advertImg = '<a href=' + data[i].link + '  target="_blank"><img width="770" height="247" src=' + data[i].images + '  /></a>';
                        }
                        else
                        {
                            advertImg = '<a href=' + data[i].link + '><img width="770" height="247" src=' + data[i].images + ' /></a>';
                        }
                        elementCounts += 1;
                    }
                    indexChangeAdvertHtml += advertImg;
                });

                $('#image_reel').empty();
                $('#image_reel').html(indexChangeAdvertHtml);
                var paging = $('#paginga');
                var pagingEle = '';
                for (var i = 1; i <= elementCounts; i++)
                {
                    pagingEle += '<a href="javascript:void(0);" rel="' + i + '">' + i + '</a>';
                }
                paging.empty();
                paging.html(pagingEle);
            }
        });

    });

</script>


<div class="ad">
    <div class="main_view">
        <div class="window">
            <div class="image_reel" id="showPic">
                <!-- <a href="#"><img src="img/0001.png" alt="" /></a>
                 <a href="#"><img src="img/0002.png" alt="" /></a>
                 <a href="#"><img src="img/0003.png" alt="" /></a>
                 <a href="#"><img src="img/0004.png" alt="" /></a> -->
            </div>
        </div>
        <div class="paging" id="paginga">
            <a href="#" rel="1">1</a>
            <a href="#" rel="2">2</a>
            <a href="#" rel="3">3</a>
            <a href="#" rel="4">4</a>
        </div>
    </div>

    <script type="text/javascript">

        $(document).ready(function ()
        {

            var advertImg = "";
            $.ajax({
                url: "ajax_advert_list.action?aaa=121",
                type: "get",
                data: {
                    "advert.module": "0",

                    "advert.position": "1"
                },
                dataType: "json",
                beforeSend: function (data)
                {
                    $("#rebateAdvertDetail").html('<div id="advertLoading"><img src="${base}/template/common/images/ajax-loader.gif"></div>');
                },
                success: function (data)
                {
                    var play;
                    //广告
                    $.each(data, function (i)
                    {
                        if (i > 3)
                        {
                            return false;
                        }

                        var link = data[i].link;
                        var isblank = data[i].is_blank;
                        if (link == "")
                        {
                            advertImg += "<a href=\"#\" ><img src=\"" + data[i].images + "\" /></a>";
                        } else
                        {
                            if (isblank == "1")
                            {
                                advertImg += "<a href=\"" + data[i].link + "\" target=\"_blank\"><img src=\"" + data[i].images + "\" /></a>";
                            }
                            else
                            {
                                advertImg += "<a href=\"" + data[i].link + "\" ><img src=\"" + data[i].images + "\" /></a>";
                            }
                        }

                    })

                    $("#showPic").html(advertImg);
                    //Set Default State of each portfolio piece

                    $(".paging").show();
                    $(".paging a:first").addClass("active");

                    //Get size of images, how many there are, then determin the size of the image reel.
                    var imageWidth = $(".window").width();
                    var imageSum = $(".image_reel img").size();
                    var imageReelWidth = imageWidth * imageSum;

                    //Adjust the image reel to its new size
                    $(".image_reel").css({'width': imageReelWidth});

                    //Paging + Slider Function
                    rotate = function ()
                    {
                        var triggerID = $active.attr("rel") - 1; //Get number of times to slide
                        var image_reelPosition = triggerID * imageWidth; //Determines the distance the image reel needs to slide

                        $(".paging a").removeClass('active'); //Remove all active class
                        $active.addClass('active'); //Add active class (the $active is declared in the rotateSwitch function)

                        //Slider Animation
                        $(".image_reel").animate({
                            left: -image_reelPosition
                        }, 500);

                    };

                    //Rotation + Timing Event
                    rotateSwitch = function ()
                    {
                        play = setInterval(function ()
                        { //Set timer - this will repeat itself every 3 seconds
                            $active = $('.paging a.active').next();
                            if ($active.length === 0)
                            { //If paging reaches the end...
                                $active = $('.paging a:first'); //go back to first
                            }
                            rotate(); //Trigger the paging and slider function
                        }, 3000); //Timer speed in milliseconds (3 seconds)
                    };
                    rotateSwitch();
                    //On Hover
                    $(".image_reel a").hover(function ()
                    {
                        clearInterval(play); //Stop the rotation
                    }, function ()
                    {
                        rotateSwitch(); //Resume rotation
                    });

                    //On Click
                    $(".paging a").mouseover(function ()
                    {
                        $(".image_reel").stop(true, true);
                        $active = $(this); //Activate the clicked paging
                        //Reset Timer
                        clearInterval(play); //Stop the rotation
                        rotate(); //Trigger rotation immediately
                        rotateSwitch(); // Resume rotation
                        return false; //Prevent browser jump to link anchor
                    });

                }
            });

        });
    </script>

</div>

<!--首页自定义-->
<#include "/WEB-INF/templates/common/index_change.ftl">
<!--首页自定义-->
<div class="bestservice">
    <a href="exchange_telephonefee_list.action"><img src="${base}/template/web/images/charge.gif" /></a><a href="exchange_lottery_info.action?id=100001002"><img
        src="${base}/template/web/images/fftlottery.gif" ></a><a href="lead_to_airticket.action" target="_blank"
                                                                                       style="margin:0"><img
        src="${base}/template/web/images/flights.gif" style="margin:0"></a>
</div>
</div>
<div class="indexMenu">
    <div class="col04">
        <a href="rebate_index.action" class="menuBox"><img src="${base}/template/web/images/men0312a.png" width="240"/></a>
        <a href="new_exchange_index.action" class="menuBox"><img src="${base}/template/web/images/men0312b.png" width="240"/></a>
        <a href="lead_to_o2omall.action" target="_blank" class="menuBox"><img src="${base}/template/web/images/men0312c.png" width="240"/></a>
        <a href="lead_to_o2ofanli.action" target="_blank" class="menuBox"><img src="${base}/template/web/images/men0312d.png" width="240"/></a>
    </div>
</div>
<div class="newbanner" id="ashowlist">
    <div class="points">
        <div class="tit">
            <dt>积分兑换</dt>
            <dd>积分支付、现金支付灵活选择，更有精品兑换送积分</dd>
            <span><a href="new_exchange_index.action">更多<img src="${base}/template/web/images/more_arrow.png" width="8" height="12"/></a></span>
        </div>
    </div>
    <div class="arrow1"><a href="javascript:void(0)"><img src="${base}/template/web/images/shqm_left_pic.gif" width="18"
                                                          height="26"/></a></div>
    <div class="exchange">
        <div class="list">
            <ul>
            <#list goodsExchangeRackList as goodsExchangeRack>
                <#if goodsExchangeRack_index gt 7 >
                    <#break>
                </#if>
                <#if goodsExchangeRack.goodsCategoryId=="100001009">
                <a href="exchange_local_info.action?id=${goodsExchangeRack.id?c}">
                </#if>
                <#if goodsExchangeRack.goodsCategoryId=="100001011">
                <a href="exchange_bankPoint_info.action?id=${goodsExchangeRack.id?c}">
                </#if>
                <#if goodsExchangeRack.goodsCategoryId=="100001006">
                <a href="exchange_telephonefee_info.action?id=${goodsExchangeRack.id?c}">
                </#if>
                <#if goodsExchangeRack.goodsCategoryId=="100001005">
                <a href="exchange_lottery_info.action?id=${goodsExchangeRack.id?c}">
                </#if>
                <#if goodsExchangeRack.goodsCategoryId=="100001003">
                <a href="exchange_specialty_info.action?id=${goodsExchangeRack.id?c}">
                </#if>
                <#if goodsExchangeRack.goodsCategoryId=="100001007">
                <a href="#">
                </#if>
                <li>
                    <dl>
                    <dt class="shower">
                        <#if goodsExchangeRack.isCash=='1'>
                            <p>现金：<b>${(goodsExchangeRack.cashPricing)!"0"}</b>元</p>
                        </#if>
                        <#if goodsExchangeRack.isFftPoint=='1'>
                            <p>分分通积分：<b>${(goodsExchangeRack.fftPointPricing)!"0"}</b>分</p>
                        </#if>
                        <#if goodsExchangeRack.isBankPoint=='1'>
                            <p>银行积分：<b>${(goodsExchangeRack.bankPointPricing)!"0"}</b>分</p>
                        </#if>
                        <#if goodsExchangeRack.isFftpointCash=='1'>
                            <#assign fftpointsAndCashArray=goodsExchangeRack.fftpointCashPricing?split('|')>
                            <p>分分通积分：<b>${fftpointsAndCashArray[0]}</b>分+现金<b>${fftpointsAndCashArray[1]}</b>元</p>
                        </#if>
                        <#if goodsExchangeRack.isBankpointCash=='1'>
                            <#assign bankpointsAndCashArray=goodsExchangeRack.bankpointCashPricing?split('|')>
                            <p>银行积分：<b>${bankpointsAndCashArray[0]}</b>分+现金<b>${bankpointsAndCashArray[1]}</b>元</p>
                        </#if>
                        </dt>
                    </dl>
                    <#if goodsExchangeRack.goodsCategoryId?exists && goodsExchangeRack.goodsCategoryId = '100001011'>
                        <img src="${base}/template/web/images/zhuhai.png"></#if>
                    <#if (goodsExchangeRack.goodsExchangeRackImages?size>0)>
                        <#assign ger=goodsExchangeRack.goodsExchangeRackImages[0]>
                        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(ger.imagesUrl)!""}"
                             onError="this.src='${base}/template/common/images/moren.gif'">
                        <!--
                   			        	<#list goodsExchangeRack.goodsExchangeRackImages as goodsExchangeRackImages>
                   			        		<#if goodsExchangeRackImages_index gt 0>
                   			        			<#break>
                   			        			<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsExchangeRackImages.imagesUrl)!""}"">
                   			        		</#if>
                   			        	</#list>
                   			        	-->
                    <#else>
                        <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${goodsExchangeRack.goods.sourceUrl?if_exists}"
                             onError="this.src='${base}/template/common/images/moren.gif'">
                    </#if>

                    <div class="hotTitle">${(goodsExchangeRack.goodsRackName)!""}</div>
                    <div class="boxInfor">${(goodsExchangeRack.goodsRackDesc)!""}</div>
                    <div class="btnImg">
	                <#if goodsExchangeRack.goodsCategoryId=="100001009">
	                	<img src="${base}/template/web/images/btnexchange.png" onClick="window.location='exchange_local_info.action?id=${goodsExchangeRack.id?c}' ">
	                </#if>
	                <#if goodsExchangeRack.goodsCategoryId=="100001011">
	                	<img src="${base}/template/web/images/btnexchange.png" onClick="window.location='exchange_bankPoint_info.action?id=${goodsExchangeRack.id?c}' ">
	                </#if>
	                <#if goodsExchangeRack.goodsCategoryId=="100001006">
	                	<img src="${base}/template/web/images/btnexchange.png" onClick="window.location='exchange_telephonefee_info.action?id=${goodsExchangeRack.id?c}' ">
	                </#if>
	                <#if goodsExchangeRack.goodsCategoryId=="100001005">
	                	<img src="${base}/template/web/images/btnexchange.png" onClick="window.location='exchange_lottery_info.action?id=${goodsExchangeRack.id?c}' ">
	                </#if>
	                <#if goodsExchangeRack.goodsCategoryId=="100001003">
	                	<img src="${base}/template/web/images/btnexchange.png" onClick="window.location='exchange_specialty_info.action?id=${goodsExchangeRack.id?c}' ">
	                </#if>
	                <#if goodsExchangeRack.goodsCategoryId=="100001007">
	               		<a href="#"></a>
	                </#if>
                    <#if goodsExchangeRack.isPresentPoints==1>
                        <span class="icon_points"></span>
                    </#if>                      
                   </div>
                </li>
            </a>
            </#list>
            </ul>
        </div>

    </div>
    <div class="arrow2"><a href="javascript:void(0);"><img src="${base}/template/web/images/shqm_right_pic.gif"
                                                           width="18" height="26"/></a>
    </div>
</div>

<div class="newbanner tg" id="ashowlistB">
    <div class="points">
        <div class="tit">
            <dt class="bluefont">团购产品</dt>
            <dd>本地产品，线上购买，线下提货</dd>
            <span><a href="group_index.action">更多<img src="${base}/template/web/images/more_arrow.png" width="8" height="12"/></a></span>
        </div>
    </div>
    <div class="arrow1"><a href="javascript:void(0)"><img src="${base}/template/web/images/shqm_left_pic.gif" width="18"
                                                          height="26"/></a>
    </div>
    <div class="exchange" style="height:330px;">
        <div class="list" id="ashowlist" style="height:340px" >
            <ul>
            <#list goodsGroupRackList as goodsGroupRack>
                <#if goodsGroupRack_index gt 7 >
                    <#break>
                </#if>

                <a href="group_goods_detail_new.action?goodsGroupRack.id=${goodsGroupRack.id?c}">
                    <li>
                        <#if (goodsGroupRack.goodsGroupRackImages?size>0)>
                            <#assign ger=goodsGroupRack.goodsGroupRackImages[0]>
                            <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(ger.imagesUrl)!""}"
                                 onError="this.src='${base}/template/common/images/moren.gif'">
                        <#else>
                            <img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${(goodsGroupRack.goods.sourceUrl)!""}"
                                 onError="this.src='${base}/template/common/images/moren.gif'">
                        </#if>

                        <div class="hotTitle">${(goodsGroupRack.seoTitle)!""}</div>
                        <div class="boxInfor">${(goodsGroupRack.seoKeyword)!""}</div>
                        <div class="price"><font>¥${(goodsGroupRack.groupPrice)!""}</font> 原价：<font
                                class="oldprice">¥${(goodsGroupRack.goods.price)!""}</font></div>
                        <div class="date">有效期：${(goodsGroupRack.beginTime?date("yyyy-MM-dd"))!""}
                                          至${(goodsGroupRack.endTime?date("yyyy-MM-dd"))!""}</div>
                        <div class="btnImg">
                        <img src="${base}/template/web/images/btnsale.png" onClick="window.location='group_goods_detail_new.action?goodsGroupRack.id=${goodsGroupRack.id?c}' ">
                        <span>有<font>${(goodsGroupRack.marketTotalNumber)!""}</font>人已购买</span>
                        </div>
                    </li>
                </a>
            </#list>
            </ul>
        </div>
    </div>
    <div class="arrow2"><a href="javascript:void(0)"><img src="${base}/template/web/images/shqm_right_pic.gif" width="18" height="26"/></a>
    </div>
</div>


<script type="text/javascript" src="${base}/template/web/js/newinfor.js"></script>
<script src="${base}/template/web/js/pointshow2.js"></script>

<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->
</div>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
