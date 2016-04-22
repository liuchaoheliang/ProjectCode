<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户服务-意见与建议</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/common/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/common/css/dialog_common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/dialog.js"></script>
</head>
<body>
<!--
* Author:
* pengling@f-road.com.cn 
*/
--> 
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--导航开始-->
<#include "/WEB-INF/templates/common/menu.ftl">
<!--导航结束--> 

<!--广告栏开始-->
<div class="ad"><img src="${base}/template/member/img/123213.png"></div>
<!--广告栏结束-->

<div class="middleBox">
<!-- 会员个人信息菜单开始 -->
<#include "/WEB-INF/templates/common/user_manage_menu.ftl">
<!-- 会员个人信息菜单结束 -->
  <div class="rightBox" id="rightHeight">
  <form id="suggestionForm" action="add_suggestion.action" method="post">
	<input type="hidden" name="suggestion.userId" id="userId" value="${(user.userID)!""}"/>
    <input type="hidden" name="suggestion.userMobile" id="userMobile" value="${(user.mobilephone)!""}"/>
    <input type="hidden" name="suggestion.userEmail" id="userEmail" value="${(user.email)!""}"/>
    <input type="hidden" name="suggestion.userName" id="userName" value="${(user.username)!""}"/>
      <div class="inforBox">
        <dl style="display:none">
          <dt>用户名：</dt>
          <dd>
            <div> 
              <span>${(user.username)!""}</span> 
            </div>
          </dd>
        </dl>
        <dl style="display:none">
          <dt>联系方式：</dt>
          <dd>
            <div> 
              <span>${(user.mobilephone)!""}</span> 
            </div>
          </dd>
        </dl>
        <dl id="dl100">
          <dt>意见与建议：</dt>
          <dd>
            <div> 
              <span><textarea style="resize:none;" name="suggestion.content" id="content" cols="" rows="" class="textArea500">${(suggestion.content)!""}</textarea></span> 
            </div>
          </dd>
        </dl>
         <div class="ml150 mt10 fl">
         <a href="javascript:void(0);"><div id="saveSuggest" class="textBtn"><B>提交</B></div></a>   
         </div> 
         <div class="ml10 mt10 fl">
         <a href="${base}/suggestion_list.action?"><div class="textBtn"><B>返回</B></div></a>  
         </div>   
       </div>
      </form>  
  </div>
</div>
<script type="text/javascript" src="${base}/template/common/js/height.js"></script>
<!--底部开始-->
<#include "/WEB-INF/templates/common/footer.ftl">
<!--底部结束--> 
<script type="text/javascript"> 	
$("#saveSuggest").click(function(){	
	var content = $("#content").val();
	if(content == ''){
		$.layer({
			title:['分分通提示您',true],
			time:3,
			area : ['auto','auto'],
			dialog : {msg:'请填写意见建议',type : 8}	
		});
		return ;
	}
	$("#suggestionForm").submit();		
});
</script>
</body>
</html>
