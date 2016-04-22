<div id="ajaxLinkWindow" class="ajaxLinkWindow">
    <div class="windowTop">
        <div class="windowTitle">详细信息</div>
        <a class="windowClose ajaxLinkWindowClose" href="javascript:void(0)" hidefocus="true"></a>
    </div>
    <div class="windowMiddle">
        <table>
            <tr>
                <th>订单号：</th>
                <td>
                <#if (trans.id?exists)>
					     	${trans.id?c}
					     </#if>
                </td>
                <th>手机号码：</th>
                <td>
                ${(trans.phone)!"-"}
                </td>
            </tr>
        <#if (trans.transDetailsList?exists && trans.transDetailsList?size !=0)>
            <#list trans.transDetailsList as transDetail>
                <tr>
                    <th>商品名称：</th>
                    <td>
                    ${(transDetail.goods.goodsName)!"-"}
                    </td>
                    <th>商品数量：</th>
                    <td>
                        <#if (trans.state?exists && trans.state != '02' && transDetail.goodsNumber?exists)>
                        ${transDetail.goodsNumber?if_exists}
                        <#else>
                            1
                        </#if>
                    </td>
                </tr>
                <tr>
                    <th>实付金额：</th>
                    <td>
                        <#if (trans?exists && trans.currencyValueRealAll?exists)>
                        ${trans.currencyValueRealAll?if_exists}
                        <#else>
                            -
                        </#if>
                    </td>
                    <#if securitiesNo?exists>
                        <th>消费券：</th>
                        <td>
                        ${(securitiesNo)!"-"}
                        </td>
                    </#if>
                </tr>
                <tr>
                    <th>银行积分：</th>
                    <td>
                        <#if (trans?exists && trans.bankPointsValueRealAll?exists)>
                        ${trans.bankPointsValueRealAll?if_exists}
                        <#else>
                            -
                        </#if>
                    </td>
                    <th>分分通积分：</th>
                    <td>
                        <#if (trans?exists && trans.fftPointsValueRealAll?exists)>
                        ${trans.fftPointsValueRealAll?if_exists}
                        <#else>
                            -
                        </#if>
                    </td>
                </tr>
                <!--			<tr>
						<th>会员登录号：</th>
					     <td>      <#if trans.user?exists>${trans.user.username?if_exists}</#if> </td> 
					     <th>支付手机号：</th>
					     <td>      <#if trans.user?exists>${trans.user.mobilephone?if_exists}</#if> </td> 
					</tr>
	-->
                <tr>
                    <th>状态：</th>
                    <td>
                    ${(trans.remark)!"-"}
                    </td>
                    <#if trans.isConsume?exists && trans.isConsume == "1">
                        <th>消费时间：</th>
                        <td>
                            <#if trans.consumeTime?exists && trans.consumeTime != "">
                            ${(trans.consumeTime)!"暂未消费"}
                            <#else>
                                --
                            </#if>
                        </td>
                    <#else>
                        <th>完成时间：</th>
                        <td>
                        ${(trans.updateTime)!"-"}
                        </td>
                    </#if>
                </tr>

                <tr>
                    <th>自提点：</th>
                    <td colspan="2">${(trans.respCode)}</td>
                </tr>
                <tr>
                	<th>营业时间：</th>
                    <td colspan="1">${(trans.respMsg)}</td>
                    <th>电话：</th>
                    <td colspan="1">${(trans.beName)}</td>
                </tr>
            </#list>
        </#if>
                <!--    <div class="textBtn">  <a class="windowClose ajaxLinkWindowClose" href="javascript:void(0)"><B>返回</B></a></div>  -->
        </table>
    </div>
    <div class="windowBottom"></div>
</div>