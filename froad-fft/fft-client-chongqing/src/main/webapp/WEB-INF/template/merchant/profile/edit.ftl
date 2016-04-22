<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>修改密码</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/merchant/css/merchant.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/common/css/validate.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/js/jquery.validate.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#commentForm").validate({
		rules: {			
			old_password: {
				required: true,
				minlength: 6,
				pattern:/^[^\u4e00-\u9fa5]{0,}$/
			},
			new_password: {
				required: true,
				minlength: 6,
				pattern:/^[^\u4e00-\u9fa5]{0,}$/
			},
			require_password: {
				required: true,
				minlength: 6,
				pattern:/^[^\u4e00-\u9fa5]{0,}$/,
				equalTo: "#new_password"
			}
		},
		
		messages: {
			old_password: {
				required: '请输入您的密码',
				minlength: '密码长度为6-16位',
				pattern: '密码不能为特殊字符'
			},
			new_password: {
				required: '请输入您的新密码',
				minlength: '密码长度为6-16位',
				pattern: '密码不能为特殊字符'
			},			
			require_password: {
				required: '请再次输入您的新密码',
				minlength: '密码长度为6-16位',
				pattern: '密码不能为特殊字符',
				equalTo: '两次密码输入不一致'
			}


		},	
		 
		errorElement: "em",
		focusInvalid:false, 		
		focusCleanup:true,
		onkeyup:false,
		validClass: "success" ,
		 
		success: function(span) {					
			span.addClass("success").text("验证通过!");
			span.prev().addClass("right")
		},	
		
	   errorPlacement:function(error,element) {		   	   
	   		error.appendTo(element.parent());		
	   }	

	 });	 
	 
	 
	 //修改密码
	 $("#update").click(function (){
	 	var flag=$('#commentForm').valid();	
		if(flag){	
		 	$.Ajax({
				url : '${base}/merchant/profile/update_password.jhtml',
				datas : $('#commentForm').serialize(),
				lockScreen : true,
				successFn: function (data){
					if(data.flag){
						layer.msg(data.msg, 3, -1);
						setTimeout(function (){
								window.location.href ="${base}/merchant/login/logout.jhtml";
							},2000);
					}else{
						layer.msg(data.msg, 3, -1);
					}
				}
			});	
		}else{
			return ;
		}
	 }); 
  });
</script> 
<style>
	.modifypsw dd>div{
		width:500px;
	}
</style>
</head>
<body>
<#-- 头部 开始-->
<#include "/include/common/header.ftl">
<#-- 头部 结束-->

<div class="main">
  <div class="middleBox" > 
    <!--左侧导航开始-->
    <#include "/include/merchant/menu.ftl">
    <!--左侧导航结束-->
  <div class="rightBox" id="rightHeight">
      <div class="clear pt5 mt35 mb30">
  	 <form id="commentForm" action="${base}/merchant/profile/update_password.jhtml" method="post" onsubmit='javascript:return false;'>
           <h2 class="pl50">提示：<font class="f13 grayFont">您正在修改“${user.username}”用户的密码</font></h2>
        <div class="inforBox validate psw" id="formlist">
       <dl>
       	<dt>原密码：</dt>
          <dd>
	        <div>            
	         <strong>
	       		<input name="old_password" type="password"  class="loginText" maxlength="16"/>
	       	   <u>*</u></strong>
	       	</div>
          </dd>
        </dl>
        <dl>
        <dt>新密码：</dt>
          <dd>       
	       	<div> <strong>
	       		<input name="new_password" id="new_password" type="password"  class="loginText" maxlength="16"/>
	       		 <u>*</u></strong>
	       	</div>
       	   </dd>
       </dl>
       
        <dl>  
         <dt>确认密码：</dt>  
          <dd>   
	       	<div> <strong> 
	       	 	    <input name="require_password" id="require_password" type="password"  class="loginText" maxlength="16"/>
	       		  <u>*</u></strong>
	        </div>
		  </dd>
       </dl>      
        <div class="ml150 mt5">
       <#--<font class="redFont">密码修改成功，下次登录时需使用新密码，请牢记!</font><br />--> 
        <input class="subBtn ml50" value="确定修改" type="submit" id="update" onsubmit='javascript:return false;'>
        </div>
      </div>
      </div>
     </from> 
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