<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>退换货认证</title>
<link rel="icon" href="${base}/favicon.ico" type="image/x-icon" />
<#include "/include/sources.ftl">
<link href="${base}/resources/merchant/css/merchant.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/common/css/validate.css" rel="stylesheet" type="text/css" />
<script src="${base}/resources/common/js/jquery.validate.js"></script>
<script type="text/javascript">

//根据券号获取认证券信息
function securitiesNoDetail (securitiesNo){
	//获取券信息
		$.Ajax({
			url : settings.baseUrl + '/merchant/presell/securitiesNo_detail.jhtml',
			datas : {'securitiesNo':securitiesNo},
			lockScreen : false,
			successFn : function (data){
				if(data.flag){
					//alert(data.data);
					$("#security_no").val(data.data.securityNo);
					$("#user_name").html(data.data.userName);
					$("#delivery_phone").html(data.data.phone);
					$("#delivery_name").html(data.data.deliveryName);
					$("#product_name").html(data.data.productName);
					$("#number").html(data.data.number);
					$("#create_time").html(data.data.createTime);
					$("#is_consume").html(data.data.isConsume);
					$("#pay_method").html(data.data.payMethodShow);
					$("#result_no").hide();
					$("#result_ok").show();
				}else{
					$("#result_ok").hide();
					$("#result_no").show();
					$("#result_no").html("<span><dd class='redFont'>"+data.msg+"</dd></span>");
				}
			}
		});
}


//
$(document).ready(function(){


	//查找
	$("#find").click(function(){
		var securitiesNo=$("#securitiesNo").val();
		if(securitiesNo==""){
			$("#result_ok").hide();
			$("#result_no").show();
			$("#result_no").html("<span><dd class='redFont'>请输入16位有效提货券，不区分大小写</dd></span>");
			return;
		}
		securitiesNoDetail(securitiesNo);
	});
	
	
	$("#result_no").hide();
	$("#result_ok").hide();
	$("#show").hide();
	$("#reason_result").hide();
	
	//认证按钮
	$("#require").click(function(){
	
		var securityNo= $("#security_no").val();
		var type = $("input[name='type']:checked").val();	
		var quantity=$("#quantity").val();	
		var reason = $("#select").val();
		//如果原因选择其他理由
		if(reason==00){
			reason=$('#reason').val();
		}
		if(reason==""){
			layer.msg("请输入退换货理由", 2, -1);
			return ;
		}
		var reg= /^[1-9][0-9]*$/;
		
		if(quantity==""){
			layer.msg("请输入退换货数量", 2, -1);
			return;
		}
		if(!reg.test(quantity)){
			layer.msg("输入退换货数量有误，请重新输入", 2, -1);
			return;
		}
		
		if(type==undefined){
			layer.msg("请选择退换货类型", 2, -1);
			return ;
		}	
		
		//认证券信息
		$.Ajax({
			url : settings.baseUrl + '/merchant/return_sale/returnSale_Authentic.jhtml',
			datas : {'securitiesNo':securityNo,'type':type,'quantity':quantity,'reason':reason},
			lockScreen : true,
			successFn : function (data){
				if(data.flag){					
					layer.msg(data.msg, 3, -1);
					setTimeout(function(){
						securitiesNoDetail(securityNo)
					},2000);
				}else{
					layer.msg(data.msg, 4, -1);
				}
			}
		});
		
	});
	
	//原因选择
	$('#select').change(function() {
		var reason = $("#select").val();
		if(reason==00){
			$("#reason_result").show();
		}else{
			$("#reason_result").hide();
		}
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
    
    <div class="rightBox" id="rightHeight"><div class="fromDiv">
    	<span >
        	请输入券号：<input id="securitiesNo" name="securitiesNo" type="text" class="loginText"  value=""/>
        </span>  
        <input class="subBtn " value="查询" type="button" id="find"> 
    </div>
    <div class="tit">
    	
     	<div class="ordersearch" style="padding-left:65px;">    
     				
	      	<div id="result_ok">
	      		<input type="hidden" id="security_no"  />
				<span><label>输入退换货的数量：</label><dd><input type="text" id="quantity" name="quantity" class="loginText" size=10/></dd></span>
	      		<span><label>类型：</label><dd>
	      			<input type="radio" name="type" value="0"/> 退货       
	      			<input type="radio" name="type" value="1"/> 换货    
	      		</dd></span>
	      		<span><label>请选择退换货原因：</label><dd>
	      			<select name="reason" id="select" >
			      		 <option >包装完整，不影响二次销售，支持退换货</option>      		 
			      		 <option >外包装已打开，内包装未打开，质量问题，支持退换货</option>
			      		 <option >内包装打开，质量问题，支持退换货</option>
			      		 <option value="00">其他理由</option>			      		 
			        </select><br/>
	      		</dd></span>
	      		<div id="reason_result" style="padding-left:21px;">
	      			<textarea rows="4" cols="52" id="reason" name="reason" style="resize: none;"></textarea>
	      		</div>
	      			   
	      		<span><label>提货人：</label><dd id="user_name"></dd></span>
	      		<span><label>提货人手机号：</label><dd id="delivery_phone"></dd></span>
	      		<span><label>提货点：</label><dd class="redFont" id="delivery_name"></dd></span>
				<span  class="mt15"><label>商品：</label><dd id="product_name"></dd></span>
				<span><label>数量：</label><dd id="number"></dd></span>
				<span><label>支付方式：</label><dd id="pay_method"></dd></span>
				<span><label>是否已消费：</label><dd id="is_consume"></dd></span>
				<span><label>下单时间：</label><dd><font class="mr20" id="create_time"></font></dd></span>
				<span class="ml15">
					<input class="subBtn " value="确认" type="button" id="require">
				</span>
			</div>
			<div id="result_no" style="padding-left:7px;">
				
			</div>
		</div> 
  </div>
  <#--
  <div class="tit"><h2 class="fb fl w120">当前网点库存</h2>
      <div class="miss" id="show" style="display：block;">
        <dl><dd><u>重庆农商行沙坪坝分行</u>网点的<u>牛奶梦工厂</u>商品数量少于<u>30</u>个，请及时补货！</dd>
         <dd><u>重庆农商行沙坪坝分行</u>网点的<u>牛奶梦工厂</u>商品数量少于<u>30</u>个，请及时补货！</dd><dd><u>重庆农商行沙坪坝分行</u>网点的<u>牛奶梦工厂</u>商品数量少于<u>30</u>个，请及时补货！</dd><dd><u>重庆农商行沙坪坝分行</u>网点的<u>牛奶梦工厂</u>商品数量少于<u>30</u>个，请及时补货！</dd></dl>
        <a href="#" onclick="closed()"></a> </div>
  	  </div>
      
      <div class="ordersearchtable">
        <table  border="0" cellspacing="0" cellpadding="0" class="tableA">
          <tr>
            <th width="60%">商品名</th>
            <th width="40%">数量</th>
           
          </tr>
          <tr>
            <td>小米小米</td>
            <td>330</td>
           
          </tr>
         <tr>
            <td>小米小米</td>
            <td>330</td>
           
          </tr>
            <tr>
            <td>小米小米</td>
            <td>330</td>
           
          </tr>
            <tr>
            <td>小米小米</td>
            <td>330</td>
           
          </tr>
            <tr>
            <td>小米小米</td>
            <td>330</td>
           
          </tr>
           
        </table>
      </div>
      <div class="page"><div class="yellow"><a href="#"> 上一页 </a><span class="current">1</span><a href="#">2</a><a href="#">3</a><a href="#">4</a><a href="#">5</a><a href="#">6</a><a href="#"> 下一页</a><span class="number">共6页</span></div></div>
    </div>-->
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