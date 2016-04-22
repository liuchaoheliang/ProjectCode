







<div class="chunjie3" >
  <p><a href="spring_festival_index.action"><img src="${base}/template/web/zhuanpan/201402.jpg"></a></p>
</div>
<!--春节活动结束-->

<script type="text/javascript">
  //谷歌
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-40362091-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<div class="head100">
  <div class="head">
    <div class="logo">
    	<a href="index.action">    
    		<img src="${base}/template/common/images/logo.png">
    	</a>
    </div>
    <div class="fl"><img src="${base}/template/common/images/telcus2.png"></div>
    <div class="rightList">    	        
      <div class="leftmember">
        <b id="name" title=""></b> 
        
        <div class="divmember">  
          <div class="arrowmember" id="hygl" style="display:none">    
            <a>会员中心</a><img src="${base}/template/common/images/arrowup.gif">
          </div> 
          <div class="arrowmember" id="shgl" style="display:none">    
            <a>会员中心</a><img src="${base}/template/common/images/arrowup.gif">
          </div>
          
          <div class="showmember" style="display:none;">
            <li id="mXgmm" style="display:none"><a href="${base}/member_main.action">会员管理</a></li>
            <li id="sXgmm" style="display:none"><a href="${base}/home.action">商户管理</a></li>
            <li id="jhjf"><a href="${base}/bankPoint_activate.action">积分激活</a></li>
            <li id="loginout" style="display:none"><a id="loginoutUrl" href="<#if location_login_name?exists>${base}/toLoginOut.action<#else>${base}/loginOut.action</#if>">安全退出</a></li>
          </div>  
        </div> 
        
        <i id="memeberlogin"><a href="${base}/toLogin.action" id="memeberlogin">登录</a><span>|</span></i>
        <i id="regedit"><a href="${base}/toResgiter.action" id="regedit">注册</a><span>|</span></i>
        <a href="${base}/merchant_apply_index.action">商户合作申请</a><span>|</span>
        <a href="${base}/help_center.action">用户帮助</a><span>|</span>
        <a href="${base}/app_download_index.action">手机版下载</a>
      </div>
    
    
 <!--     <ul>
		<li id="hello" style="display:none"><h5>您好：</h5><b id="name"></b><li>
        <li id="memeberlogin"><a href="${base}/toLogin.action">登录</a><span>|</span></li>
        <li id="loginout"  style="display:none"><a href="${base}/j_spring_security_logout">安全退出</a><span>|</span></li>
        <li id="regedit"><a href="${base}/toResgiter.action">注册</a><span>|</span></li> -->
  <!--     <li id="busylogin"><a href="${base}/toLogin.action">商户登陆</a><span>|</span></li> --> 
  <!--      <li id="hygl"  style="display:none"><a href="${base}/member_main.action">会员管理</a><span>|</span></li> --> 
  <!--      <li id="shgl"  style="display:none"><a href="${base}/home.action">商户管理</a><span>|</span></li> -->
  <!--       <li id="jhjf"><a href="${base}/bankPoint_activate.action">激活积分</a><span>|</span></li>
        <li id="apply"><a href="${base}/merchant_apply_index.action">商户合作申请</a><span>|</span></li>
        <li><a href="${base}/help_center.action">用户帮助</a><span>|</span></li>
        <li><a href="${base}/app_download_index.action">手机版下载</a></li>
      </ul>
      -->
      <dl>
        <dd  id="jifenshow" style="display:none">您的分分通积分为：<B id="fftPoints"></B> 分,银行积分：<B id="bankPoints"></B> 分</dd>
      </dl>
      <form id="searchForm" name="searchForm" method="post" action="">
      	<input type="hidden" name="pager.tagValue" id="tagValue" value=""/>
	      <input type="hidden" name="pager.merchantPriority" id="merchantPriority" value=""/>
	      <input type="hidden" name="pager.preferentialType" id="preferentialType" value=""/>
	      <input type="hidden" name="pager.tagClassifyAId" id="tagClassifyAId" value=""/>
	      <input type="hidden" name="pager.tagDistrictBId" id="tagDistrictBId" value=""/>
	   </form>
    </div>
  </div>
</div>
<link href="${base}/template/common/css/pointright.css" rel="stylesheet" type="text/css" />
<script src="${base}/template/common/js/pointright.js"></script>
<script type="text/javascript">
$(window).load(function(){
var O2OBILL = O2OBILL||{};
O2OBILL.ajax = function(o){
	if(!parent||!parent.document.getElementById('_x_x_x_x_x')){
		//alert(o.url+'&b='+ Math.random());
		$('<iframe src="'+o.url+'&b='+ Math.random()+'" id="_x_x_x_x_x" width="0" height="0"></iframe>').appendTo(document.body);
		$('#_x_x_x_x_x').bind('load',function(){
			//alert(1);
			$.ajax({
			    url:o.url,
			    global: false,
			    dataType:o.dataType||'html',
			    method:o.method||'POST',
			    success:function(res){
			       if(typeof o.success == 'function'){
			    	   o.success(res);
			       }
			    },
			    error: function(XMLHttpRequest, textStatus, errorThrown) { 
			    	if(typeof o.unloginFn == 'function'){
				    	   o.unloginFn(XMLHttpRequest, textStatus, errorThrown);
				    }
			    }
			});
		});
	}
}


O2OBILL.ajax({
	url:'queryUserInfo.action?a=' + Math.random(),
	unloginFn:function(a,b,c){
		$('#registerBut').show();
		$('#jhjfbtn').show();
	},
    dataType:'html',
    method:'POST',
    success:function(res){
	    var anticache = new Date();
		$.getJSON("${base}/queryMerchant.action?anticache="+anticache.getTime(),function(data){
			if(data.reno == '0'){
				if(data.isMerchant == '0'){
					$('#loginoutUrl').attr('href','${base}/toLoginOut.action');
					document.getElementById("jifenshow").style.display = 'block';
					document.getElementById("pointright").style.display = 'block';
					document.getElementById("memeberlogin").style.display = 'none';
					document.getElementById("loginout").style.display = 'block';
					document.getElementById("regedit").style.display = 'none';
					document.getElementById("hygl").style.display = 'block';
					document.getElementById("mXgmm").style.display = 'block';
					document.getElementById("name").innerHTML = data.name;
					$("#name").attr('title',data.titleName);
					document.getElementById("bankPoints").innerHTML = data.bankPoints;
					document.getElementById("fftPoints").innerHTML = data.fftPoints;
					$("#bankRightPoint").html(data.bankPoints);
					$("#fftRightPoint").html(data.fftPoints);
					$("#uName").html(data.titleName);
					$('#jhjfbtn').show();
					$('#activePoint').show();
					$('#registerBut').hide();
					if(data.firstLogin == '0'){
						onclickshow();
					}
					if(window.location.href.indexOf('toLogin.action') != -1){
						
						window.location.href = 'index.action';
					}
				}else if(data.isMerchant == '1'){
					 document.getElementById("jifenshow").style.display = 'none';
					 document.getElementById("pointright").style.display = 'none';
					 document.getElementById("memeberlogin").style.display = 'none';
					 document.getElementById("loginout").style.display = 'block';
					 document.getElementById("sXgmm").style.display = 'block';
					 document.getElementById("regedit").style.display = 'none';
					 document.getElementById("jhjf").style.display = 'none';
					 document.getElementById("shgl").style.display = 'block';
					 document.getElementById("name").innerHTML = data.name;
					 document.getElementById("jhjfbtn").style.display = 'none';
					 document.getElementById("registerBut").style.display = 'none';	
					 $("#name").attr('title',data.titleName);
					 if(data.beCode != null && data.beCode != 'undefined' && data.beCode != "1000"){
					 	$('#addClerkLi').hide();
					 	$('#myClerkLi').hide();
					 	$('#myTreasurerLi').hide();
					 }
					 
				}
			}else{
				$('#registerBut').show();
				$('#jhjfbtn').show();
			}
		});  	    
    }
});








	
});	
</script> 
<script  type="text/javascript" src="${base}/template/common/js/headlist.js"></script>