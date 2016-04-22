<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>我的预售</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/member/css/admin.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/plugin/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(function (){
	$('.details').click(function (){
		var transId = $(this).attr('transId');
		$.Ajax({
			url : '${base}/member/orderlist/presell/detail.jhtml',
			dataType : 'html',
			datas : {'transId' : transId},
			lockScreen : true,
			successFn : function (data){
				 var i= $.layer({
					offset : ['120px' , '50%'],
					area : ['570px' , '320px'],
					type : 1,
					title : "查看详情",
					page : {html :data}
				
				});	
				$('#closed').bind('click', function(){
					layer.close(i);
				 });	
				
			}
		});
	});
	$("select[name='payState']").val('${payState}');
});
</script>
<script>
	$(document).ready(function(){
		$(".return").click(function(){
		var transId = $(this).attr('transId');
		$.Ajax({
			url : '${base}/member/orderlist/refunds/return.jhtml',
			dataType : 'html',
			datas : {'transId' : transId},
			type:'GET',
			lockScreen : true,
			successFn : function (data){
				 var i= $.layer({
					offset : ['120px' , '50%'],
					area : ['470px' , '230px'],
					type : 1,
					title : "退款申请",
					page : {html :data}
				
				});	
				$('#closed').bind('click', function(){
					layer.close(i);
				 });	
				
			}
		});
	});
	});
</script>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->
<div class="main">
	<div class="middleBox"> 
	<#-- 公共菜单  开始 -->
	<#include "/include/member/menu.ftl">
	<#-- 公共菜单  结束 -->
    <div class="rightBox" id="rightHeight">
    	<form id="listForm" action="${base}/member/orderlist/presell/list.jhtml" method="post">
	      	<div class="fromDiv"> 
	      		<span>开始时间：
	        		<input  readonly="true" name="pageFilterDto.startTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(page.pageFilterDto.startTime?string('yyyy-MM-dd'))!}" />
	        	</span> 
	        	<span>结束时间：
	        		<input  readonly="true" name="pageFilterDto.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})" class="loginTextDate" value="${(page.pageFilterDto.endTime?string('yyyy-MM-dd'))!}" />
	        	</span> 
	        	<span>状态：
		        	<select name="payState" id="select">
		          		<option value="">全部</option>
		          		<option value="10,20">待付款</option>
		          		<option value="30">已付款</option>
		          		<option value="40">退款中</option>
		          		<option value="50">已退款</option>
		        	</select>
	        	</span> 
	      		<input class="subBtn " value="查询" type="submit">
	      	</div>
	      	<div class="clear pt15">
	        	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
	          		<tr>
	            		<th width="10%">商品</th>
		            	<th width="10%">订单号</th>
		            	<th width="10%">购买时间</th>
		            	<th width="6%">单价（元）</th>
		            	<th width="6%">积分</th>
		            	<#--<th width="6%">银行积分</th>-->
		            	<th width="6%">现金（元）</th>
		           		<th width="15%">支付方式</th>
		            	<th width="11%">状态</th>
		            	<th width="11%">操作</th>
		         	</tr>
					<#list page.resultsContent as trans>
			          	<tr>
			          		<td>${trans.transDetailDto[0].productName}</td>
			            	<td>${trans.sn}</td>
			            	<td>${(trans.createTime?string("yyyy-MM-dd"))!"-"}</td>
			            	<#--<td>${currency(trans.totalPrice!"0",false)}</td>-->
			            	<td>${currency(trans.transDetailDto[0].single!"0",false)}</td>
			            	<td>${currency(trans.fftPoints!"0",false)}</td>
			            	<#--<td>${currency(trans.bankPoints!"0",false)}</td>-->
			            	<td>${currency(trans.realPrice!"0",false)}</td>
			            	<td>${trans.payMethod.describe}</td>
			            	<td>
			            		<#if trans.payState==10||trans.payState==20>
			            			待付款
			            		<#elseif trans.payState==30>
			            			已付款
			            		<#elseif trans.payState==40>
			            			退款中
			            		<#elseif trans.payState==50>
			            			已退款
			            		</#if>
			            	</td>
			            	<td>
			            	<a href="javascript:void(0);" class="details" transId="${trans.id?c}">查看详情</a></br>
			            	<!--支付状态:已付款,成团状态:(未成团、成团失败),申请退款记录：为空>>>可申请退款-->
			            	<#if trans.payState==30&&trans.transDetailDto[0].productPresellDto.clusterState!=1&&trans.refundsDto==null>
			            		<a href="javascript:;" class="return" transId="${trans.id?c}">取消订单</a>
			            	</#if>
			            	<#if trans.refundsDto!=null>
			            		<#if trans.refundsDto.state.code==1>
			            			<span>${trans.refundsDto.state.describe}</span>
			            		</#if>
			            		<#--<#if trans.refundsDto.state.code==3>
			            			<span style="cursor:pointer;" onmouseover="layer.tips('${trans.refundsDto.remark}',this,{maxWidth:200,closeBtn:[0,true]});">${trans.refundsDto.state.describe}</span>
			            		<#else>
			            			<span>${trans.refundsDto.state.describe}</span>
			            		</#if>-->
			            	</#if>
			            	</td>
			          	</tr>
		        	</#list> 
	       	   </table>
	      </div>
	      <#if (page.resultsContent?size > 0)>
        	<#include "/include/pager.ftl" />
      	  <#else>
      		<div class="nopr">
      			<img src="${base}/resources/member/images/nopr.png"/>
      			<a href="${base}/">
      				<img src="${base}/resources/member/images/backico.png" width="90" height="29" />
      			</a>
      		</div>
      	  </#if>
    </form>
  </div>
  <!--清除浮动-->
  <div class="clear"></div>
  <!--清除浮动--> 
</div>
<#-- 底部 -->
<#include "/include/common/footer.ftl">
<#-- 底部 -->
</body>
</html>