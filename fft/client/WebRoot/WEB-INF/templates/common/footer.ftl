<script>





</script>

<!--积分出现-->
<div class="pointright" id="rightShow">
  <a href="${base}/member_main.action" id="pointright" style="display:none;"><div class="li01"></div></a>
  <a id="activePoint" style="display:none;" href="${base}/bankPoint_activate.action"><div class="li02"></div></a>
  <a id="hiddenli" href="javascript:void(0);" onclick="showInfo()" id="fenfeninfor"><div class="li05"></div></a>
  <a id="hiddenlis" href="javascript:void(0);" onclick="onclickshow()" id="yindao"><div class="li03"></div></a>
  <!--<a id="hiddenli" href="activitshow"><div class="li06"></div></a>-->
  
  <a href="javascript:void(0);"><div class="li04" style="display:none" id="goTopBtn"></div></a>
  <SCRIPT type=text/javascript>$(function(){
 	 goTopEx();
  })</SCRIPT>
</div> 
<script type="text/javascript">
function screenWidth(className, cutNumber){
	try{
	var offLeft = $("."+className).offset().left;
	}catch(e){
	
	}
	
	$(".pointright").css('right',offLeft-cutNumber)			
}

$(function(){
	screenWidth('indexMenu', '70');
	screenWidth('box1010', '90');
	screenWidth('middleBox', '90');
	screenWidth('down', '70');
	screenWidth('regeditLeft','90');
	screenWidth('loginBox .all' , '90');

})

$(window).resize(function(){
	screenWidth('indexMenu', '70');
	screenWidth('box1010', '90');
	screenWidth('middleBox', '90');
	screenWidth('down', '70');
	screenWidth('regeditLeft','90');
	screenWidth('loginBox .all' , '90');

});

</script>
<script>
var indexhref = document.location.href;
if((indexhref.split('/')[indexhref.split('/').length - 1]) != 'index.action'){
	$('#hiddenlis').hide();
}
function showInfo(){
	var htmlContext = '&nbsp;&nbsp;&nbsp;&nbsp;分分通平台是珠海农商银行为全市所有市民奉上的多行业生活积分平台，平台提供积分返利、积分兑换、积分提现、团购、直接优惠等多种优惠服务。合作商家范围涵盖日常生活多个行业。';
	htmlContext += '分分通平台中的积分采取一分兑一元的等价形式，支持以下双积分使用模式，是您生活中不可多得的优惠助手。';
htmlContext +='<P>（1）分分通积分：任何客户在平台合作商家处消费后都可向商户人员报出手机号码，相关商户会赠送您相应比例的分分通积分（请点击积分返利板块查看合作商户详情），此积分可在线进行积分兑换产品甚至提取现金；</p>';

htmlContext +='<p>（2）银行积分：珠海农商银行持卡客户可直接使用往年银行积分进行积分兑换或团购（信通卡或金海洋卡5000分起兑，手机银行卡1000分起兑），若分数不够还支持现金付款。</p>' ;
	$.layer({
		   title:['分分通平台介绍！',true],
		    area : [700,330],
		    dialog : {
		        msg:htmlContext,
		        btns : 1, 
		        type : 2,
		        btn : ['确定'],
		        yes : function(index){
		            layer.close(index);
		        }
		    }
		});
}
</script>   
<!--积分出现-->
<!--清除浮动-->
<div class="clear"></div>
<!--清除浮动-->

<!--底部开始-->
<div class="foot mt10">
  <div class="footAdd">
  运营商：珠海农商银行 上海方付通商务服务有限公司 电话：96138 沪ICP备:08021520号-4 地址：珠海市香洲兴业路223号
    <script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F44e0db3b2f87a6367a56b044fce61548' type='text/javascript'%3E%3C/script%3E"));
</script>
  </div>
</div>
<script type="text/javascript">
$(function(){
	$('.tableA').tableUI();
})
</script>
<!--底部结束-->
<script src="${base}/template/common/js/backtop.js"></script>
