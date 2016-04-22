<div class="indexChange">
  <div class="leftChange">
	<div id='coin-slider'></div>
  </div>
</div>
<script type="text/javascript">
$().ready(function() {
 	//广告
 	$.ajax({
		url:"ajax_advert_list.action?a="+ Math.random(),
		type: "get",
		data: {
			   "advert.module": "0",
			  
			   "advert.position" : "2"
			   },
		dataType : "json",
		beforeSend: function(data) {
			$("#coin-slider").html('<div id="loading"><img src="${base}/template/common/images/ajax-loader.gif"></div>');
		},
 		success: function(data) {
 			var indexChangeAdvertHtml="";
 			//广告
 			$.each(data, function(i) {

 				if(i>5){
 					return false;
 				}

 				var advertImg="";

 				var link=data[i].link;
 				var isblank=data[i].is_blank;

 				if(link==""){
 					advertImg='<a href="#"><img src='+data[i].images+' height="420" width="472"  /><span>'+data[i].name+'</span></a>';
 				}
 				else{
 					if(isblank=="1"){
 						advertImg='<a href='+data[i].link+'  target="_blank"><img  style="display:block;"  height="233" width="472" src='+data[i].images+'  /><span>'+data[i].name+'</span></a>';
 					}
 					else{
 						advertImg='<a href='+data[i].link+'><img  height="233"  style="display:block;" width="472"  src='+data[i].images+' /><span>'+data[i].name+'</span></a>';
 					}
 				}
 			indexChangeAdvertHtml += advertImg;
 			});

 			$("#coin-slider").html(indexChangeAdvertHtml);

 			$('#coin-slider').coinslider({
				width:590,
				height:235,
				navigation: true,
				delay: 15000
			 });

 		}
 	});
});
</script> 