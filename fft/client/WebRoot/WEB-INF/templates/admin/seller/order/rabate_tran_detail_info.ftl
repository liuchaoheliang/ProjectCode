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
					     	<#if (transDetail.goodsNumber?exists)>
				        	 	${transDetail.goodsNumber?if_exists}
				        	<#else>
					        	-
				        	</#if>				        	 	    
					     </td> 
					</tr>   
					 <tr>
					     <th>实付金额：</th>
					     <td>
				        	 	${transDetail.currencyValueReal?if_exists}    
					     </td> 
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
					      	<#if trans.state?exists && trans.state=="02">
				        		成功
					        <#elseif trans.state?exists && trans.state=="03">
					        	失败
					       	<#elseif trans.state?exists && trans.state=="01">
					        	处理中
					       	<#elseif trans.state?exists && trans.state=="04">
					        	关闭
					        <#else>
					        	-
					        </#if>    
					     </td> 
					     <th>完成时间：</th>
					     <td>
						    ${(trans.updateTime)!"-"}				     
					     </td> 
					</tr>   
					</#list>
					</#if>    
				<!--    <div class="textBtn">  <a class="windowClose ajaxLinkWindowClose" href="javascript:void(0)"><B>返回</B></a></div>  -->
		  	</table>
		   </div> 
		<div class="windowBottom"></div>
</div>