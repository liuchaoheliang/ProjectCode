/*商户收藏和分享*/

	
	//收藏
	 function CollectionMerchant(id){
		 $.ajax({
				url:'merchant_collect_add.action',
				type: "POST",
				dataType: "json",
				data:{"merchantId": id},
				success: function(data) {					
					var message="";
					if (data.status == "success") {
						message=data.message;
						count = $("#shareCount").html();
						count = eval(count.split('：')[1]) + 1;
						$("#shareCount").html('收藏人数：'+count);
						$("#yishoucang").css("display","block");
						$("#weishoucang").css("display","none");
					}
					else if(data.status == "warn"){
						message=data.message;
					}
					else{
						message=data.message;
					}
					
					
//					$.dialog({
//						title:"分分通",
//						type: data.status, 
//						content: message, 
//						autoCloseTime:3000 
//						});
					if (data.status == "success") {
						$.layer({
							title:['分分通提示您',true],
							time:3,
							area : ['auto','auto'],
							dialog : {msg:message,type : 9}	
						});
					}else{
						$.layer({
							title:['分分通提示您',true],
							time:3,
							area : ['auto','auto'],
							dialog : {msg:message,type : 8}	
						});
					}
					
					}	
			});
		 return false;
	 }
	
	//分享
	 function ShowShareMerchant(id){
		 //
		 var shareWindowHtml='<div id="shareWindowForm"> '
								+'<table> '
									+'<tr> '
									+'<th>手机号码: </th> '
									+'<td> <input type="text" id="shareWindowMobile" name="shareMerchant.shareMobile" class="formText" maxlength="11" /><input type="hidden" id="shareWindowMerchantId" name="shareMerchant.merchantId" value="'+id+'" /> '
									+'</td> '
									+'</tr>'
									+'<tr> '
									+'<th>&nbsp;</th> '
									+'<td><div id="myMsg"></div>'
									+'</td> '
									+'</tr>'
								+'</table>' 
							+'</div>';
		 
		 
//		 $.dialog( {
//				title : "分享商户",
//				content : shareWindowHtml,
//				ok : "确  定",
//				cancel : "取  消",
//				id : "shareWindow",
//				className : "shareWindow",
//				width : 420,
//				okCallback : shareMerchant,
//				modal : true
//			});
		 $.layer({
			   title:['分享',true],
			    area : ['auto','auto'],
			    dialog : {
			        msg:shareWindowHtml,
			        btns : 2, 
			        type : 9,
			        btn : ['确定','取消'],
			        yes : function(index){
			 			shareMerchant(index);
			            //layer.msg('您选择了重要。',2,1);
			        },
			        no : function(index){
			        	layer.close(index);
			        }
			    }
			});
		 var $shareWindow = $("#shareWindow");
		 var $shareWindowForm = $("#shareWindowForm");
		 var $shareWindowMobile = $("#shareWindowMobile");
		 var $shareWindowMerchantId = $("#shareWindowMerchantId");
		 var $myMsg = $("#myMsg");
		 
		 function shareMerchant(index){
			 
			 if ($.trim($shareWindowMobile.val()) == "") {
				 $shareWindowMobile.focus();
				 $myMsg.text('手机号码不能为空！');
				 return false;
			 }
			 else{
				 $myMsg.text('');
			 }
			 
			 var reg=/^1[3|4|5|8][0-9]\d{4,8}$/;
			if(!reg.test($shareWindowMobile.val())){
				$shareWindowMobile.focus();
				$myMsg.text('手机号码格式不对！');
				return false;
			 }
			 else{
				 $myMsg.text('');
			 }
			 
			 $.ajax({
					 url: "member_share_merchant.action",
					 data: {"shareMerchant.merchantId": $shareWindowMerchantId.val(),
				 			"shareMerchant.shareMobile":	$shareWindowMobile.val()},
					 type: "POST",
					 dataType: "json",
					 cache: false,
					 beforeSend: function() {
				 		$shareWindow.find(":button").attr("disabled", true);
					 },
					 success: function(data) {
						$myMsg.text(data.message);
						if (data.status == "success") {
							setTimeout(function() {
								layer.close(index);
								//$.closeDialog("shareWindow");
							}, 2000);
						}
						else{
							setTimeout(function() {
								//$.closeDialog("shareWindow");
								layer.close(index);
							}, 2000);
						}
					 },
					 complete: function() {
						 $shareWindow.find(":button").attr("disabled", false);
					 } 
				 });
			 return false;
		 }
		 
		 return false; 
	 }

	 
	 //完整地图
	 function showmapwindow(merchantId){
		 
		var point;
		var points=[];
		 
		var bigmapHtml="<div id='bigmap' style='width: 710px; height: 350px; '></div>";
		 
		 $.layer({
				type: 1,
				title: ['完整地图',true],
				border : [5, 0.5, '#666', true],
				area: ['auto','auto'],
				closeBtn : [0 , true],
				page: {
				html: bigmapHtml
				}
			});
		 
		//地图
		var big_map = new BMap.Map('bigmap');
		big_map.enableScrollWheelZoom();                     //启用滚轮放大缩小
		big_map.addControl(new BMap.NavigationControl());	//添加了鱼骨 
		point = new BMap.Point(116.404, 39.915);
		big_map.centerAndZoom(point, 14);
		 
		 //查找商户分店地址
		 $.ajax({
				url:"ajax_merchant_store_list.action",
				type: "POST",
				data: {"id": merchantId},
				dataType : "json",
				success: function(data) {
					
					$.each(data.storeList, function(i, item) { 	
						
						if(item.coordinate == null || item.coordinate == ""){
							return true;
						}
						
						//放入数组
						var p0 = item.coordinate.split(",")[0];
			    		var p1 = item.coordinate.split(",")[1];
			    		point = new BMap.Point(p0,p1);
			    		points.push(point);
			    		
			    		//创建标注
			    		var marker = new BMap.Marker(point);
			    		
			    		//将标注添加到地图中
			    		big_map.addOverlay(marker);
			    		
			    		(function(){
			    			var _iw = createInfoWindow(item.fullName,item.address,item.telephone);
			    			var _marker = marker;
			    			
			    			//点击
			    			_marker.addEventListener("click",function(e){
			    				_iw.open(_marker);
			    			});
						
			    		})()
							
					});
					
					 big_map.setViewport(points);         //调整地图的最佳视野为显示标注数组point
				}
		 });
		
		
		
		//创建窗口
	    function createInfoWindow(fullName,address,telephone){
	    	
	    	 var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
	         '地址：'+address+'<br>电话：'+telephone+'</div>';
			 
			//创建检索信息窗口对象
			 var searchInfoWindow = new BMapLib.SearchInfoWindow(big_map, content, {
			        title  : fullName,      //标题
			        panel  : "panel",         //检索结果面板
			        enableAutoPan : true,     //自动平移
			        searchTypes   :[
			            BMAPLIB_TAB_SEARCH,   //周边检索
			            BMAPLIB_TAB_TO_HERE,  //到这里去
			            BMAPLIB_TAB_FROM_HERE //从这里出发
			        ]
			    });
			 
		    return searchInfoWindow;   		
		}
	 }
	 