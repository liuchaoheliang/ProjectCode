<div class="fl ml10">
	<div class="seller" id="tab">
	  <ol>
	    <li class="playRcut"><img src="${base}/template/web/images/HotS.png">热门商家</li>
	    <li><img src="${base}/template/web/images/NewS.png">最新商家</li>
	  </ol>
	
	  <ul id="hotMerchants">
	  </ul>   
	
	  <ul id="newMerchants" style="display:none">
	  </ul>      
	  <div id="loading"><img src="${base}/template/common/images/ajax-loader.gif"></div>             
	</div>  
	<script type="text/javascript" src="${base}/template/common/js/tab.js"></script>  
 </div>
 
<script type="text/javascript"> 
	//热门商家
	function getRankMerchantList(){
		$.getJSON("${base}/findRankMerchantList.action",function(data){
			var $divR02 = $("#hotMerchants");
			var html='';
			if(data.reno == "0"){
				$.each(data.rankMerchantList,function(key,value){
				var tag = (value.companyTagDistrictB.tagValue == undefined) ? "" : value.companyTagDistrictB.tagValue;
			      html+='<a href="merchant_info.action?id='+value.id+'" target="_blank">'+
						'<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}'+value.companyLogoUrl+'">'+
						'<dl>'+
						'<dt>'+value.companyFullName+'</dt>'+
						'<dd>'+'<u>'+value.companyTagDistrictA.tagValue+'</u>'+
						'<u>'+tag+'</u>'+
						'<br/><u>'+value.companyTagClassifyA.tagValue+'</u>'+					
						'</dl>'
						'</a>';					
				});
				$divR02.html(html);
				$('#loading').hide();
			}else{
				$divR02.html('');
			}
		});
	}
	//最新商家
	function getNewMerchantList(){
		$.getJSON("${base}/findNewMerchantList.action",function(data){
			var $newMerchantDiv = $("#newMerchants");
			var html='';
			if(data.reno == "0"){
				$.each(data.newMerchantList,function(key,value){
				var tag = (value.companyTagDistrictB.tagValue == undefined) ? "" : value.companyTagDistrictB.tagValue;
					html+='<a  href="merchant_info.action?id='+value.id+'"  target="_blank">'+
						'<img src="${stack.findValue("@com.froad.util.Command@IMAGE_SERVER_URL")}'+value.companyLogoUrl+'">'+
						'<dl>'+
						'<dt>'+value.companyFullName+'</dt>'+
						'<dd>'+'<u>'+value.companyTagDistrictA.tagValue+'</u>'+
						'<u>'+tag+'</u>'+
						'<br/><u>'+value.companyTagClassifyA.tagValue+'</u>'+						
						'</dd>'+
						'</dl>'+
						'</a>';		
				});
				$newMerchantDiv.html(html);
				$('#loading').hide();
			}else{
				$newMerchantDiv.html('');
			}
		});
	}
	$(window).load(function(){
		getRankMerchantList();
		getNewMerchantList();		
	});
</script>   