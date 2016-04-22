<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<#include "/WEB-INF/templates/common/admin_include.ftl">
<link href="${base}/template/common/css/pager.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>

<link href="${base}/template/common/css/ajax_detail.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/ajax_detail.js"></script>


</head>
<body>
<!--头部开始-->
<#include "/WEB-INF/templates/common/admin_top.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/admin_menu.ftl">
<!--导航结束-->


<div class="adminRight">
  <div class="adminBorderTop">
    <div class="adminBorderTopLeft"></div>
    <div class="adminBorderTopTitle">
      <div class="adminBorderTopTitleLeft"></div>
      <div class="adminBorderTopTitleMiddle">交易管理</div>
      <div class="adminBorderTopTitleRight"></div>
    </div>
    <div class="adminBorderTopRight"></div>
  </div>
   <form id="listForm" action="tran_points_list_for_seller.action?menu_flag=tran_points_list" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#F7F8F9">
  <tr>
    <td class="adminBorderMiddleLeft"></td>
    <td>
    <div class="adminBox">
       <div class="fromDiv">
                      订单号：
         <input name="pager.transaction.id" type="text"  class="loginText3" value="<#if pager.transaction?exists && pager.transaction.id?exists>${pager.transaction.id?c}</#if>"/>
         
          会员登录号：
       <input name="pager.user.username" type="text" class="loginText3" value="<#if pager.user?exists>${pager.user.username?if_exists}</#if>"/>
      	 开始时间：
       <input name="pager.beginTime" id="txtStartDate" type="text"   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'txtEndDate\',{d:-1});}'})" class="loginTextDate" value="${(pager.beginTime?split('|')[0])!""}" />
      	 结束时间：
       <input name="pager.endTime" id="txtEndDate" type="text"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'txtStartDate\',{d:0});}'})"  class="loginTextDate" value="${(pager.endTime?split('|')[0])!""}" />
       	状态：
       <select name="pager.state" id="fundsChannelId">
       				<option value="" />选择状态<br/>
					<option value="02" <#if  pager.state?exists && pager.state=="02">selected</#if>/>成功<br/>
					<option value="03" <#if  pager.state?exists && pager.state=="03">selected</#if>/>失败<br/>
					<option value="01"  <#if  pager.state?exists && pager.state=="01">selected</#if>/>处理中<br/>
		</select>
      
      
      
 		</div>
       <div id="searchButton" class="adSchBtn mt5 ml20"><span>搜索</span></div>

	</div> 
     
 <div class="adminBox">
     <div class="listTable">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tableA">
               <tr>
             <th width="10%">订单号</th>
          <th width="10%">会员登录号</th>
           <th width="10%">支付手机号</th>
          <th width="10%">产品</th>
          <th width="10%">消费金额（元）</th>
         
          <th width="10%">积分数</th>
          <th width="10%">完成时间</th>
          <th width="10%">状态</th>
          <th width="10%"><B>操作</B></th>
              </tr>  
        <#list pager.list as transpoints>
       
          <tr>
            <td><#if transpoints.transaction?exists>${transpoints.transaction.id?c}</#if></td>
         <td><#if transpoints.user?exists>${transpoints.user.username?if_exists}</#if></td>
          <td><#if transpoints.user?exists>${transpoints.user.mobilephone?if_exists}</#if></td>
          <td>
         <#if (transpoints.transaction?exists && transpoints.transaction.tranDetailList?exists && transpoints.transaction.tranDetailList?size !=0)>
	        	<#if (transpoints.transaction.tranDetailList[0].tranGoods?exists)>
	        	 	${transpoints.transaction.tranDetailList[0].tranGoods.transGoodsDisplay?if_exists}
	        	<#else>
	        		-
	        	</#if>
	        <#else>
	        	-
	        </#if>
	       </td>
          <td><#if transpoints.transaction?exists>${transpoints.transaction.currencyValueRealAll?if_exists}</#if></td>
       
         <!--  <td><#if transpoints.transaction?exists>${transpoints.transaction.currencyValueRealAll?if_exists}</#if></td> -->
             <td>
	             <#if transpoints.pointsValueRealAll?exists>
	             	${transpoints.pointsValueRealAll?number}
	             </#if>
             </td>
          <td>${transpoints.createTime?if_exists}</td>
          <td>
          <#if transpoints.state?exists && transpoints.state=="02">
	        	成功
	        <#elseif transpoints.state?exists && transpoints.state=="03">
	        	失败
	       <#else>
	        	处理中
	        </#if>
          </td>
	        
	        <td height="10">
	        <B>
	        <a class="ajaxlinkDetail" href="tran_points_detailInfo.action?menu_flag=tran_points_list&pager.id=${transpoints.id?c}"  title="查看详情">[详情]
	        </a>
	        </B>
	        </td>
      	</tr>
       </#list>
     
     	 <tr >
        <td colspan="8" height="30">
        <#if (pager.list?size > 0)>
         	<#include "/WEB-INF/templates/common/pager.ftl" />
       <#else>
         		没有找到任何记录!	
        </#if>
       </td>
        </tr>
     
      </table>       
       </div>  

              
     </div>               
     
          
    </td>
    <td class="adminBorderMiddleRight"></td>
  </tr>
</table>
</form>
<div class="adminBorderBottom">
  <div class="adminBorderBottomLeft"></div>
  <div class="adminBorderBottomRight"></div>
</div>
</div>

<div><!--数据图表--></div>
<!--底部-->
<#include "/WEB-INF/templates/common/admin_footer.ftl">
<!--底部-->
</body>
</html>
