<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户列表</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/merchant/css/merchant.css" rel="stylesheet" type="text/css" />
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
    
    
     <div class="rightBox" id="rightHeight"><div class="tit m15 mt10">
     	 <h2 class="fb fl">新增用户：</h2>
      	
 	 </div>
      <div class="modifypsw">
      	<span><label>用户类型：</label>
	        <select name="select" id="select">
	        	<option>分店管理员</option>
	        	<option>分店操作员</option>
	        	<option>商户账务员</option>
	        	<option>分店账务员</option>
	        	<option>商户出纳员</option>
	        </select>
        </span><br />

        <span><label>网点：</label>
	        <select name="select" id="select">
	       		<option>重庆市</option>
	        </select> 
	        <select name="select" id="select">
	        	<option>永川区</option>
	        	<option>万州区</option>
	        	<option>北碚区</option>
	        	<option>合川区</option>
	        	<option>江北区</option>
	        </select>
        </span>
		<br />
	<div class="fl" id="uboxstyle">
                <li ><a href="#">重庆农村商业银行 </a> </li>
                <li><a href="#">重庆农村商业银行 </a> </li>
                <li><a href="#">重庆农村商业银行 </a> </li>
                <li class="current1"><a href="#">重庆农村商业银行 </a> </li>
                <li><a href="#">重庆农村商业银行 </a> </li>
                <li><a href="#">重庆农村商业银行 </a> </li>
                <li><a href="#">重庆农村商业银行 </a> </li>
                <li><a href="#">重庆农村商业银行 </a> </li>
              </div><br />

        <input class="subBtn " value="新增" type="button" id="addUser">

<div class="newuser"><span>新增用户成功！</span>
<span><label>用户名：</label>dsfasdfasd</span>
<span><label>默认密码：</label>3454523</span></div></div>
      
      
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