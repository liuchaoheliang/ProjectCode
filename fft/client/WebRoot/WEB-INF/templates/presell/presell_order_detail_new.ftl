<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>分分通</title>
    <link href="${base}/template/common/css/base.css" rel="stylesheet" type="text/css" />
    <link href="${base}/template/common/css/public.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${base}/template/common/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/base.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/search.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/jquery.ajaxfileupload.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/jquery.jqDnR.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/jquery.jqModal.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${base}/template/common/js/tableUI.js"></script>
    <link href="${base}/template/buy/css/confirm.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/template/common/js/list.js"></script>
    <script type="text/javascript" src="${base}/template/common/layer/layer.min.js"></script>
    <script type="text/javascript">
        function checkAndSubmit()
        {
            var senda = $("#twoClick");
            senda.attr("href", "javascript:void(0);");
            senda.find("div").attr("class", "gryBtn");
            senda.find('B').html("请等待...");
            var payMethod =${(trans.payMethod)!""};
            if (payMethod == "07" || payMethod == "08" || payMethod == "09")
            {
                showLayer();
                var winName='newwin'+new Date().getTime();
                document.getElementById("exch").target = winName;
                window.open("", winName);
            }
            document.getElementById("exch").submit();
        }
        function validate()
        {
            window.parent.frames.location.href = "${base}/presell_index.action";
        }

        function showLayer()
        {
            //需要的部分开始
            var i = $.layer({
                title: '分分通提示您',
                area: ['auto', 'auto'],
                dialog: {
                    btns: 2,
                    btn: ['已完成付款', '付款遇到问题'],
                    msg: '<div style="color:#087da3">付款完成前请不要关闭此窗口。完成付款后根据您的情况点击下面按钮。</div>',
                    type: -1,
                    yes: function (index)
                    {
                        layer.close(i);
                        var transId= $("#transId").val();
                        self.location="presell_pay_alipaycheck.action?trans.id="+transId;
                    },
                    no: function (index)
                    {
                        layer.close(i);
                        self.location="help_center.action#zhifubao01";
                    }
                }
            });
            //需要的部分结束
        }
    </script>

</head>
<body>
<!--
/* Author:
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
    <form id="exch" name="exch" method="post" action="presell_pay_new.action">
        <input type="hidden" name="trans.id" id="transId" value="${(trans.id)!""}"/>
        <input type="hidden" name="trans.payMethod" id="payMethod" value="${(trans.payMethod)!""}"/>
        <!--付款确认开始-->
        <div class="confirm">
            <div class="stepgroup"></div>
            <div class="step">
                <li class="step01">输入信息</li>
                <li class="colorRed step08">生成订单</li>
                <li class="step03">购买成功</li>
            </div>
            <div class="clear"></div>
            <div class="conborder">
                <dl>
                    <dt>商品名称：</dt>
                    <dd>
                        <div><span>
                        <#if trans.transDetailsList?exists>
                        	        	<#if trans.transDetailsList[0]?exists>
                            <#if trans.transDetailsList[0].goods?exists>
                            ${trans.transDetailsList[0].goods.goodsName!""}
                            </#if>
                        </#if>
                        	        </#if>
                        </span></div>
                    </dd>
                </dl>
                <dl>
                    <dt>商品数量：</dt>
                    <dd>
                        <div><span>
                        <#if trans.transDetailsList?exists>
                        	        	<#if trans.transDetailsList[0]?exists>
                        ${trans.transDetailsList[0].goodsNumber!""}
                        </#if>
                        	        </#if>
                        </span></div>
                    </dd>
                </dl>
                <dl>
                    <dt>单价：</dt>
                    <dd>
                        <div><span>
                        ${(goodsPresellRack.groupPrice)!""}
                            元</span></div>
                    </dd>
                </dl>
                <dl>
                    <dt>商品总价：</dt>
                    <dd>
                        <div><span>
                        <#if trans.transDetailsList?exists>
                        ${trans.costpriceTotal!""}
                        </#if>
                            元</span></div>
                    </dd>
                </dl>
                <dl>
                    <dt>自提点：</dt>
                    <dd>
                        <div><span>
                        ${delivery.name!""}( ${delivery.address!""} 电话： ${delivery.telephone!""})
                        </span></div>
                    </dd>
                </dl>
                <dl>
                    <dt>支付方式：</dt>
                    <dd>
                        <div><span>
                        <#if trans?exists && trans.payMethod?exists>
                            <#if trans.payMethod="00">
                                <span>分分通积分</span>
                            </#if>
                            <#if trans.payMethod="01">
                                <span>银行积分</span>
                            </#if>
                            <#if trans.payMethod="02">
                                <span>现金（珠海农商银行手机银行卡支付）</span>
                            </#if>
                            <#if trans.payMethod="03">
                                <span>分分通积分+现金</span>
                            </#if>
                            <#if trans.payMethod="04">
                                <span>银行积分+现金</span>
                            </#if>
                            <#if trans.payMethod="05">
                                <span>分分通积分+现金（比例）</span>
                            </#if>
                            <#if trans.payMethod="06">
                                <span>银行积分+现金（比例）</span>
                            </#if>
                            <#if trans.payMethod="07">
                                <span>分分通积分+支付宝（比例）</span>
                            </#if>
                            <#if trans.payMethod="08">
                                <span>银行积分+支付宝 （比例）</span>
                            </#if>
                            <#if trans.payMethod="09">
                                <span>支付宝</span>
                            </#if>
                        </#if>
                        </span></div>
                    </dd>
                </dl>
                <dl>
                    <dt>支付金额：</dt>
                    <dd>
                        <div><span>
                        <#assign hasOne=0>
                        <#if trans?exists>
                        <span>
                            <#if trans.fftPointsValueRealAll?exists && trans.fftPointsValueRealAll ?number gt 0.0>
                                <#assign hasOne=1>
                            ${(trans.fftPointsValueRealAll)!""} 分分通积分 </span>
                            </#if>
                            <#if trans.bankPointsValueRealAll?exists && trans.bankPointsValueRealAll ?number gt 0.0>
                                <#assign hasOne=1>
                            ${(trans.bankPointsValueRealAll)!""} 银行积分
                            </#if>
                            <#if trans.currencyValueRealAll?exists && trans.currencyValueRealAll?number gt 0.0>
                                <#if hasOne != 0>
                                                                 +
                                </#if>
                            ${(trans.currencyValueRealAll)!""} 元
                            </#if>
                        </span>
                        </#if>
                            </span></div>
                    </dd>
                </dl>
            </div>
            <div class="conbtn fr mr50 mt10">
            
            <a href="javascript:checkAndSubmit()" class="fr" id="twoClick">
                <div class="textBtn"><B>确认订单，去付款</B></div>
            </a>               
            </div>
        </div>
    </form>
</div>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束-->
</body>
</html>
