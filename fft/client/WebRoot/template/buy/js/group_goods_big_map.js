
	 
	 //完整地图
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
	 