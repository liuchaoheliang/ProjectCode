<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户列表</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/merchant/css/merchant.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function(){
	$('#addUser').click(function (){
		window.location.href = "${base}/merchant/user/add.jhtml";
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
    <!--左侧导航开始-->
    <#include "/include/merchant/menu.ftl">
    <!--左侧导航结束--> 
    	<div class="rightBox" id="rightHeight"><div class="mt10 ml5">
     		<input class="subBtn " value="新增" type="button" id="addUser">
 	    </div>
            <div class="clear pt15">
        <table width="99%" border="0" cellspacing="0" cellpadding="0" class="tableA">
          <tr>
            <th width="13%">网点</th>
            <th width="17%">登录账号</th>
            <th width="6%">类型</th>
           
            <th width="8%">操作</th>
          </tr>
          <tr>
            <td>DDDDDDDDDD</td>
            <td>SDFDF34</td>
            <td>类别</td>
            <td class="unl"><a href="#"  >修改密码</a></td>
            
          </tr>
          <tr>
            <td>DDDDDDDDDD</td>
            <td>SDFDF34</td>
            <td>类别</td>
            <td class="unl"><a href="#"  >修改密码</a></td>
            
          </tr>
           <tr>
            <td>DDDDDDDDDD</td>
            <td>SDFDF34</td>
            <td>类别</td>
            <td class="unl"><a href="#"  >修改密码</a></td>
            
          </tr>
           <tr>
            <td>DDDDDDDDDD</td>
            <td>SDFDF34</td>
            <td>类别</td>
            <td class="unl"><a href="#"  >修改密码</a></td>
            
          </tr>
           <tr>
            <td>DDDDDDDDDD</td>
            <td>SDFDF34</td>
            <td>类别</td>
            <td class="unl"><a href="#"  >修改密码</a></td>
            
          </tr>
         
        </table>
      </div>
      <div class="page"><div class="yellow"><a href="#"> 上一页 </a><span class="current">1</span><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#"> 下一页</a><span class="number">共6页</span></div></div>
    </div>
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