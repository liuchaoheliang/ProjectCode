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
						<th>通知号码：</th>
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
					
						<#if (transDetail.goodsExchangeRack?exists)>
							<#if transDetail.goodsExchangeRack.goods?exists && transDetail.goodsExchangeRack.goods.goodsCategoryId?exists  && transDetail.goodsExchangeRack.goods.goodsCategoryId == "100001005">
							<!-- 彩票 PC端  -->
								<#if (trans.transGoodsAttrList?exists && trans.transGoodsAttrList?size !=0)>
								<#list trans.transGoodsAttrList as tranGoodsAttribute>
				        	 	
								    <#if tranGoodsAttribute.element?exists && tranGoodsAttribute.element != "">
							    		<#if tranGoodsAttribute.goodsRackAttribute?exists && tranGoodsAttribute.goodsRackAttribute != "">
							    			<#if tranGoodsAttribute.goodsRackAttribute.name?exists && tranGoodsAttribute.goodsRackAttribute.name != "彩票编码" && tranGoodsAttribute.goodsRackAttribute.name != "玩法" && tranGoodsAttribute.goodsRackAttribute.name != "投注倍数" && tranGoodsAttribute.goodsRackAttribute.name != "单复和合胆">
								    			<tr>
								    			<th>
													${(tranGoodsAttribute.goodsRackAttribute.name!"-")}：
												</th>
										     	<td> 
									        	 	${(tranGoodsAttribute.element!"-")}
										     	</td>
										     	</tr>
											</#if>
					        			</#if>
							        </#if>    
								
								</#list>
								</#if>  
								
								
							   <!-- 彩票状态开始 -->
							   <#if trans.state?exists && trans.state!="04">
						       <#if trans.payList?exists && trans.payList?size != 0>
						        	<tr>
						        		<th>彩票状态：</th>
						        		<td>
							        	<#list trans.payList as payList>
							        		<#if payList.typeDetail?exists && payList.typeDetail=="100001005">
							        		<!--属于彩票pay记录信息-->
							        			<#if payList.state?exists>
							        				<#if payList.state == "4101">
							        					出票中
							        				<#elseif payList.state == "4102" || payList.state == "4104">
							        					出票失败
							        				<#elseif payList.state == "4103">
							        					出票成功
						        					<#elseif payList.state == "4105">
						        						已中奖派奖中
						        					<#elseif payList.state == "4106">
						        						已派奖
						        					<#elseif payList.state == "1001">
						        						等待支付
						        					</#if>
							        			</#if>
							        			 <#break>
							        		</#if>
							        	</#list>
							        	</td>	
						        	   </tr> 	
						        </#if>
						        </#if>
				       		 <!--彩票状态结束-->
								
				        	</#if>
				        </#if>
				        
				        
				       
				        
				        
				        
				        <#if (transDetail.clientGoodsExchangeRack?exists) >
				        	<#if transDetail.clientGoodsExchangeRack.goods?exists && transDetail.clientGoodsExchangeRack.goods.goodsCategoryId?exists  && transDetail.clientGoodsExchangeRack.goods.goodsCategoryId == "100001005">
							<!-- 彩票  手机端  -->
								<#if (trans.transGoodsAttrList?exists && trans.transGoodsAttrList?size !=0)>
								<#list trans.transGoodsAttrList as tranGoodsAttribute>
				        	 	
								    <#if tranGoodsAttribute.element?exists && tranGoodsAttribute.element != "">
							    		<#if tranGoodsAttribute.goodsRackAttribute?exists && tranGoodsAttribute.goodsRackAttribute != "">
							    			<#if tranGoodsAttribute.goodsRackAttribute.name?exists && tranGoodsAttribute.goodsRackAttribute.name != "彩票编码" && tranGoodsAttribute.goodsRackAttribute.name != "玩法" && tranGoodsAttribute.goodsRackAttribute.name != "投注倍数" && tranGoodsAttribute.goodsRackAttribute.name != "单复和合胆">
								    			</tr> 
								    			<th>
													${(tranGoodsAttribute.goodsRackAttribute.name!"-")}：
												</th>
										     	<td> 
									        	 	${(tranGoodsAttribute.element!"-")}
										     	</td>
										     	</tr> 
											</#if>
					        			</#if>
							        </#if>    
								
								</#list>
								</#if>  
								
								<!-- 彩票状态开始 -->
								 <#if trans.state?exists && trans.state!="04">
					       <#if trans.payList?exists && trans.payList?size != 0>
					        	<tr>
					        		<th>彩票状态：</th>
					        		<td>
						        	<#list trans.payList as payList>
						        		<#if payList.typeDetail?exists && payList.typeDetail=="100001005">
						        		<!--属于彩票pay记录信息-->
						        			<#if payList.state?exists>
						        				<#if payList.state == "4101">
						        					出票中
						        				<#elseif payList.state == "4102" || payList.state == "4104">
						        					出票失败
						        				<#elseif payList.state == "4103">
						        					出票成功
					        					<#elseif payList.state == "4105">
					        						已中奖派奖中
					        					<#elseif payList.state == "4106">
					        						已派奖
					        					<#elseif payList.state == "1001">
					        						等待支付					        								        	
					        					</#if>
						        			</#if>
						        			 <#break>
						        		</#if>
						        	</#list>
						        	</td>	
					        	</tr>			        	
					        </#if>
					        </#if>
				        <!--彩票状态结束-->
								
								
				        	</#if>
				        	</#if>
				        
				        
				        
				        
				        <#if (transDetail.goodsExchangeRack?exists)>
				        	<#if transDetail.goodsExchangeRack.goods?exists && transDetail.goodsExchangeRack.goods.goodsCategoryId?exists && transDetail.goodsExchangeRack.goods.goodsCategoryId=="100001006">
				        	<!-- 话费  -->
				        	 	<#if (trans.transGoodsAttrList?exists && trans.transGoodsAttrList?size !=0)>
								<#list trans.transGoodsAttrList as tranGoodsAttribute>
				        	 	<tr>
							      	<#if (tranGoodsAttribute.element?exists)>
										<#if (tranGoodsAttribute.goodsRackAttribute?exists)>
											<th>
							        	 	${(tranGoodsAttribute.goodsRackAttribute.name!"-")}：
											</th>
							        	</#if>
								     	<td> 
							        	 	${(tranGoodsAttribute.element!"-")}
								     	</td> 
						        	</#if>    
								</tr> 
								</#list>
								</#if>
				        	</#if>
				        </#if> 
				         <#if (transDetail.clientGoodsExchangeRack?exists)>
				        	<#if transDetail.clientGoodsExchangeRack.goods?exists && transDetail.clientGoodsExchangeRack.goods.goodsCategoryId?exists && transDetail.clientGoodsExchangeRack.goods.goodsCategoryId=="100001006">
				        	<!-- 话费  -->
				        	 	<#if (trans.transGoodsAttrList?exists && trans.transGoodsAttrList?size !=0)>
								<#list trans.transGoodsAttrList as tranGoodsAttribute>
				        	 	<tr>
							      	<#if (tranGoodsAttribute.element?exists)>
										<#if (tranGoodsAttribute.goodsRackAttribute?exists)>
											<th>
							        	 	${(tranGoodsAttribute.goodsRackAttribute.name!"-")}：
											</th>
							        	</#if>
								     	<td> 
							        	 	${(tranGoodsAttribute.element!"-")}
								     	</td> 
						        	</#if>    
								</tr> 
								</#list>
								</#if>
				        	</#if>
				        </#if> 
					</#list>
					</#if>    
				<!--    <div class="textBtn">  <a class="windowClose ajaxLinkWindowClose" href="javascript:void(0)"><B>返回</B></a></div>  -->
		  	</table>
		   </div> 
		<div class="windowBottom"></div>
</div>