<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>春节活动--抽奖</title>
<#include "/WEB-INF/templates/common/include.ftl">
<link href="${base}/template/web/zhuanpan/zhuanpan.css" rel="stylesheet" type="text/css" />
<!--<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js" ></script>-->
<script type="text/javascript" src="${base}/template/web/zhuanpan/zhuanpan.js" ></script>
<!--<script>
$(document).ready(function (){
	$areaId = $("#areaId");
	$areaId.lSelect({
		url: "http://192.168.2.151:8080/o2obill-website/common/area.jhtml"
	});
})	
</script>-->
</head>
<style>
.chunjie3{display:none;}
</style>
<body>
<!--头部开始-->
<#include "/WEB-INF/templates/common/header.ftl">
<!--头部结束-->

<!--春节活动开始-->
<div class="chunjie">

	<div class="bg01"></div>
    <div class="bg02"></div>
    <div class="bg03"></div>
    <div class="bg04"></div>
    <div class="bg05">
      <div id="flashContent"></div>
                    <script>						
                            flashHoler = "flashContent"     //加载flash的容器ID
                            autoPlay = "yes"                //初始化时是否自动旋转，"yes" or "no"
                            setPointer = "no" 				//初始化时是否设置鼠标指针，"yes" or "no"
                            url_bg = "${base}/template/web/zhuanpan/bg.png";              //转盘背景图片
                            url_prize = "";        //奖品图片
                            url_pointer = "${base}/template/web/zhuanpan/pointer.png";    //指针图片
                            url_btnStart = "${base}/template/web/zhuanpan/btnStart.swf";  //开始按钮图片，可以是图片或flash
                            url_btnOver = "${base}/template/web/zhuanpan/btnOver.png";    //鼠标经过图片
                            url_btnWait = "${base}/template/web/zhuanpan/btnWait.png";    //旋转过程中按钮图片
                            url_btnSuc = "${base}/template/web/zhuanpan/btnSuc.png";      //旋转结束后按钮图片
                            W_bg = "510";                   //背景图片尺寸
                            W_btn = "124";                  //按钮图片尺寸
                            W_pointer = "150";              //指针图片宽度
                            H_pointer = "255";              //指针图片高度
                            totleNum = 6;                  //奖品个数
                            turns = 3;                      //旋转圈数
                            stopNum = 6;                    //中奖奖品                            
                        </script>
                        <script src="${base}/template/web/zhuanpan/Flash.js" type="text/javascript"></script>
                <div class="resultelist"> 
	                 <div>
	                 <ul>
	                 	<#if lotteryWinners?exists && (lotteryWinners.size() > 0)>
	                 		<#list lotteryWinners as winner>
		                 		<!--<li> 133＊***5678  一等奖 gucci经典包包 </li> -->
		                 		<li>${winner.winnerDesc!''}</li>
		                	</#list>
		                 <#else>
		                 	<li>暂无中奖名单</li>
		                 </#if>
	                 </ul>
	                </div>
                </div>
			 <script type="text/javascript">
                  $(document).ready(function () {                                 
              	 	loadFlash(1);  				    
				    window.choujiang = function(){	
				    	$.ajax({
				    		url:'begin_lottery',
				    		dataType:'json',
				    		type:'post',
				    		success:function (jsonObj){
				    			successFn(jsonObj);
				    		},
				    		error:function (){
				    			loadFlash(1); 
				    			$.layer({
									title:'分分通提示您',
									area : ['auto','auto'],
									dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">抱歉，系统繁忙请稍后再试！</div>',type : 8}							
								});	
				    		}
				    	});					    	
				    }
				    var successFn = function(data){
				    	if(data.result){
				    		normal(data);
				    	}else{
				    		abnormity(data);				    		
				    	}
				    }					    
				    var RandomResult = null;					
					var normal = function (data){			
						if(data.bankPoints != undefined){
							document.getElementById("bankPoints").innerHTML = data.bankPoints;
							document.getElementById("fftPoints").innerHTML = data.fftPoints;	
						}											
						RandomResult = data;
						var stopNum = 6;
						if(data.isGot){
							stopNum = data.stopNum;
						}						
						var swf = document['flashvars'] ? document['flashvars'] : window['flashvars']; 				    	
					    	setTimeout(function (){				    		
					    		swf.setStopNum(stopNum);				    	
					    },1500);
					}
					var abnormity = function (data){
						var swf = document['flashvars'] ? document['flashvars'] : window['flashvars']; 				    	
					    	setTimeout(function (){				    		
					    		swf.setStopNum(6);				    	
					    },2500);
						if(data.code == 'to_login'){
							window.location.href='toLogin.action';
						}else if(data.code == "info"){
							loadFlash(1);
							$.layer({
								title:'分分通提示您',
								time : 2,
								area : ['auto','auto'],
								dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 8}								
							});
						}else{
							loadFlash(1);
							$.layer({
								title:'分分通提示您',
								time : 2,
								area : ['auto','auto'],
								dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 8},
								end : function (){									
										window.location.href='index.action';
								}
							});
						}
					}
					    
				    window.flashOver = function(num){   
				    				    	
				    	if(num == 6){	
				    		RandomResult = null;			    	
				    		$.layer({
								title:'分分通提示您',
								area : ['auto','auto'],
								dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">抱歉，您没有中奖，再试一次吧！</div>',type : 8},
								end : mfr	
							});								
				    	}else{
				    		$.layer({
								title:'分分通提示您',
								area : ['auto','auto'],	
								dialog : {btns: 1,btn : ['领奖'],msg:'<div style="color:#333; font:14px/20px Verdana">'+RandomResult.msg+'</div>',type : 9},
								end : function () {												
									if(num == 1){
										var htmlElement ='<div class="validate" id="formlist" ><p>*请填写收货信息，我们将按您提供的地址，以快递或邮寄的形式将奖品寄送出。</p><form name="formregedit" action="#" method="post"><dl><dt>手机号码：</dt><dd><div><strong><input type="text" class="loginText" id="userbankCard" value="请输入您的手机号码" maxlength="11"  /><u>*</u></strong></div></dd></dl><dl><dt>姓名：</dt><dd><div><strong><input type="text" class="loginText" id="userName" value="请输入您的中文姓名" maxlength="4" /><u>*</u></strong></div>';
						                    htmlElement += '</dd></dl><dl><dt>收货地址：</dt><dd><div><strong><input type="text" class="loginText" id="userIdCard" value="请输入您的收货地址" maxlength="18" /><u>*</u></strong>';
						                    htmlElement += '</div></dd></dl>';
						                    htmlElement += '</form></div>';
										$.layer({
											title:'请输入个人信息',
											area : ['530','auto'],
											end:function (){
												loadFlash(1);
											},
											dialog : {
														msg : htmlElement,
														btns: 1,
														type: 9,
														btn : ['确定','取消'],
														yes : function(index){
																var spanerror = $("<span><h3>不能为空</h3></span>");
																$("#formlist input[type='text']").each(function(){
																	 if($(this).val() == this.defaultValue){
																		$(this).parent().find("span").remove()
																		$(this).parent().append(spanerror)	
																		$(this).addClass("errorText");	
																		return false;
																	}
																});
																var subm = $('h3').length;  																
																if(subm){
																	return;
																}																												
																$.ajax({
																	url : 'aldo_parisot_prize.action',
																	type : 'post',
																	dataType : 'json',
																	data : {'prizeJson':'[{"prizeLevel":"t","uname":"'+$("#userName").val()+'","idcard":"'+$("#userIdCard").val()+'","phone":"'+$("#userbankCard").val()+'","winnerid":"'+RandomResult.winnerid+'"}]'},
																	success : function (data){
																		layer.close(index);
																		loadFlash(1);
																		$.layer({
																			title:'分分通提示您',
																			area : ['auto','auto'],
																			dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 9}
																		});	
																	},
																	error : function (){
																		loadFlash(1);
																	}
																});
														},
														no: function (){
															loadFlash(1);
														}
													}
										});
									}else{
										$.ajax({
											url : 'aldo_parisot_prize.action',
											type : 'post',
											dataType : 'json',
											data : {'prizeJson':'[{"prizeLevel":"'+(num-1)+'","winnerid":"'+RandomResult.winnerid+'"}]'},
											success : function (data){
												loadFlash(1);
												$.layer({
														title:'分分通提示您',
														area : ['auto','auto'],
														dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 9}
													});												
											},
											error : function (){
												loadFlash(1);
											}
										});
									
									}									
																
									
								}
							});				    		
				    		
				    	}				    	
				    	function mfr(){
				        	loadFlash(6);
				        }
				    }                  
                      $.fn.textSlider = function (settings) {
                          settings = jQuery.extend({
                              speed: "normal",
                              line: 2,
                              timer: 1000
                          }, settings);
                          return this.each(function () {
                              $.fn.textSlider.scllor($(this), settings);
                          });
                      };
                      $.fn.textSlider.scllor = function ($this, settings) {
                          var ul = $("ul:eq(0)", $this);
                          var timerID;
                          var li = ul.children();
                          var _btnUp = $(".up:eq(0)", $this)
                          var _btnDown = $(".down:eq(0)", $this)
                          var liHight = $(li[0]).height();
                          var upHeight = 0 - settings.line * liHight; 
                          var scrollUp = function () {
                              _btnUp.unbind("click", scrollUp);
                              ul.animate({ marginTop: upHeight }, settings.speed, function () {
                                  for (i = 0; i < settings.line; i++) {
                                      ul.find("li:first").appendTo(ul);
                                  }
                                  ul.css({ marginTop: 0 });
                                  _btnUp.bind("click", scrollUp); 
                              });
                          };
                          var scrollDown = function () {
                              _btnDown.unbind("click", scrollDown);
                              ul.css({ marginTop: upHeight });
                              for (i = 0; i < settings.line; i++) {
                                  ul.find("li:last").prependTo(ul);
                              }
                              ul.animate({ marginTop: 0 }, settings.speed, function () {
                                  _btnDown.bind("click", scrollDown); 
                              });
                          };
                          var autoPlay = function () {
                              timerID = window.setInterval(scrollUp, settings.timer);
                          };
                          var autoStop = function () {
                              window.clearInterval(timerID);
                          };
                          ul.hover(autoStop, autoPlay).mouseout();
                          _btnUp.css("cursor", "pointer").click(scrollUp);
                          _btnUp.hover(autoStop, autoPlay);
                          _btnDown.css("cursor", "pointer").click(scrollDown);
                          _btnDown.hover(autoStop, autoPlay)
                      };
                      $(".resultelist").textSlider({ line: 5, speed: 500, timer: 3000 });
                  });
              </script>      
    </div>
    <div class="bg06"></div>
    <div class="bg07"></div>
    <!--<div class="bg08">
      <input name="" type="text" class="souinput"><input name="" type="button" value="搜索" class="soubutton">
    </div>-->
</div>
<!--春节活动结束-->

<!--商家展示开始-->
<div class="sellerBox">
	<div class="showBox"> 
		<!--循环开始-->  
		  <#if merchant.list?exists && (merchant.list.size() > 0)>
			<#list merchant.list as merchant>
		 	 <div class="show">
		  <a href="merchant_info.action?id=${merchant.id?c}" target="_blank">
		   	<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}${merchant.mstoreLogoUrl!""}" onError="this.src='${base}/template/common/images/moren.gif'" >
		   </a>
		    <div class="leftBox">
		      <dl>
		        <dt>${merchant.mstoreShortName!''}</dt>
		        <dd>行业：餐饮行业</dd>
		        <dd>返利积分：<b>${(merchant.pointRate)!'0'}%</b></dd>
		      </dl>
		    </div>
		    
		    <div class="rightBox">
		        <div class="mingcheng">
		          	<#if merchant.storeList?exists && (merchant.storeList.size() > 0)>
			          	<#list merchant.storeList as store>
			            <li>${store.fullName!''}</li> 
			            </#list> 
			        <#else>
			        	<li>${merchant.mstoreShortName!''}</li>
			        </#if>     
		        </div>
		        
		       <div class="dizhi">
		          	<#if merchant.storeList?exists && (merchant.storeList.size() > 0)>
			          	<#list merchant.storeList as store>
			            <li>地址：${store.address!''}</li> 
			            </#list> 
			        <#else>
			        	<li>地址：${merchant.mstoreAddress!''}</li>
			        </#if>        
		        </div>
		            
		    </div></div>
		    	</#list>
	<#else>
		暂无数据商户信息
	</#if>    
		  </div>
		 <!--循环结束-->  
</div>	
 
   
<#include "/WEB-INF/templates/common/footer.ftl">
<!--头部结束-->
</body>
</html>
