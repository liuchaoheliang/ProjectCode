<div class="box1010 pt10">
<!--幻灯片开始-->
  <div class="banner fl">

    	
        <div class="main_view">
            <div class="window">	
                <div class="image_reel"  id="showPic">
                   <!-- <a href="#"><img src="img/0001.png" alt="" /></a>
                    <a href="#"><img src="img/0002.png" alt="" /></a>
                    <a href="#"><img src="img/0003.png" alt="" /></a>
                    <a href="#"><img src="img/0004.png" alt="" /></a> -->
                </div>
            </div>
            <div class="paging">
                <a href="#" rel="1">1</a>
                <a href="#" rel="2">2</a>
                <a href="#" rel="3">3</a>
                <a href="#" rel="4">4</a>
            </div>
        </div>




<script type="text/javascript">

$(document).ready(function() {

	var advertImg=""; 
	$.ajax({
		url:"ajax_advert_list.action",
		type: "POST",
		data: {"advert.module": "2",
			   "advert.position" : "1"},
		dataType : "json",
		beforeSend: function(data) {
			$("#rebateAdvertDetail").html('<div id="advertLoading"><img src="${base}/template/common/images/ajax-loader.gif"></div>');
		}, 
 		success: function(data) {
 			var play;
 			//广告
 			$.each(data, function(i) { 			
 				if(i>3){
 					return false;
 				} 			
 							
 				var link=data[i].link;
 				var isblank=data[i].is_blank; 				
 				if(link==""){
 					advertImg +="<a href=\"#\" ><img src=\""+data[i].images+"\" /></a>";
 				}else{
 					if(isblank=="1"){
 						advertImg +="<a href=\""+data[i].link+"\" target=\"_blank\"><img src=\""+data[i].images+"\" /></a>";
 					}
 					else{
 						advertImg +="<a href=\""+data[i].link+"\" ><img src=\""+data[i].images+"\" /></a>";
 					}
 				}		
 				
 			})
 			
 			$("#showPic").html(advertImg);
 			//Set Default State of each portfolio piece
 			
			$(".paging").show();
			$(".paging a:first").addClass("active");
				
			//Get size of images, how many there are, then determin the size of the image reel.
			var imageWidth = $(".window").width();
			var imageSum = $(".image_reel img").size();
			var imageReelWidth = imageWidth * imageSum;
			
			//Adjust the image reel to its new size
			$(".image_reel").css({'width' : imageReelWidth});
			
			//Paging + Slider Function
			rotate = function(){	
				var triggerID = $active.attr("rel") - 1; //Get number of times to slide
				var image_reelPosition = triggerID * imageWidth; //Determines the distance the image reel needs to slide
		
				$(".paging a").removeClass('active'); //Remove all active class
				$active.addClass('active'); //Add active class (the $active is declared in the rotateSwitch function)
				
				//Slider Animation
				$(".image_reel").animate({ 
					left: -image_reelPosition
				}, 500 );
				
			}; 
			
			//Rotation + Timing Event
			rotateSwitch = function(){		
				play = setInterval(function(){ //Set timer - this will repeat itself every 3 seconds
					$active = $('.paging a.active').next();
					if ( $active.length === 0) { //If paging reaches the end...
						$active = $('.paging a:first'); //go back to first
					}
					rotate(); //Trigger the paging and slider function
				}, 3000); //Timer speed in milliseconds (3 seconds)
				
			};
			
			rotateSwitch();
			//On Hover
			$(".image_reel a").hover(function() {
				clearInterval(play); //Stop the rotation
			}, function() {
				rotateSwitch(); //Resume rotation
			});	
			
			//On Click
			$(".paging a").mouseover(function () {	
				$(".image_reel").stop(true,true);
				$active = $(this); //Activate the clicked paging
				//Reset Timer
				clearInterval(play); //Stop the rotation
				rotate(); //Trigger rotation immediately
				rotateSwitch(); // Resume rotation
				return false; //Prevent browser jump to link anchor
			});
 		}
 	});
	
	
});
</script>
  </div>
<!--幻灯片结束-->  