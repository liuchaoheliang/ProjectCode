/*
 * 删除左右两端的空格
 */
function Trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

/*
 * 定义数组
 */
function GetSide(m, n) {
	// 初始化数组
	var arr = [];
	for (var i = 0; i < m; i++) {
		arr.push([]);
		for (var j = 0; j < n; j++) {
			arr[i][j] = i * n + j;
		}
	}
	// 获取数组最外圈
	var resultArr = [];
	var tempX = 0, tempY = 0, direction = "Along", count = 0;
	while (tempX >= 0 && tempX < n && tempY >= 0 && tempY < m && count < m * n) {
		count++;
		resultArr.push([ tempY, tempX ]);
		if (direction == "Along") {
			if (tempX == n - 1)
				tempY++;
			else
				tempX++;
			if (tempX == n - 1 && tempY == m - 1)
				direction = "Inverse";
		} else {
			if (tempX == 0)
				tempY--;
			else
				tempX--;
			if (tempX == 0 && tempY == 0)
				break;
		}
	}
	return resultArr;
}

var index = 0, // 当前亮区位置
prevIndex = 0, // 前一位置
Speed = 100, // 初始速度
Time = 2, // 定义对象
arr = GetSide(3, 4), // 初始化数组
EndIndex = 0, // 决定在哪一格变慢
tb = document.getElementById("tb"), // 获取tb对象
cycle = 0, // 转动圈数
EndCycle = 0, // 计算圈数
flag = false, // 结束转动标志
quick = 0; // 加速

var stopNum,//转动停止在哪一个上
desc="",//描述
winnerid="";//中奖者ID

function StartGame() {
	//防止重复点击
	 $('#lotteryRandom').removeAttr("onclick");
	
	//先校验是否符合抽奖
	$.Ajax({
		url : settings.baseUrl + '/shop/activity/o2oraffle/lotteryRandom.jhtml',
		datas : {},
		lockScreen : false,
		mustLogin :true,
		useErrorMsg : false,
		successFn : function (data){
			if(data.flag){
				//开始跑动
				cycle = 0;
				flag = false;
				EndIndex = Math.floor(Math.random() * 10);
				EndCycle = 1;
				Time = setInterval(Star, Speed);
				//停止位置
				stopNum=data.data.stopNum;
				desc=data.msg;
				//只有中奖，winnerid才有值
				winnerid=data.data.winnerid;
				
			}else{
				//抽奖前提条件未通过
				$.layer({
					title:'重庆农村商业银行提示您',
					area : ['auto','auto'],
					dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 8},
					end : function(){
						$('#lotteryRandom').attr("onclick","StartGame()");
					}
				});
				return;
			}
		}
	});
	
}

function Star(num) {
	// 跑马灯变速
	if (flag == false) {
		// 走五格开始加速
		if (quick == 4) {
			clearInterval(Time);
			Speed = 50;
			Time = setInterval(Star, Speed);
		}
		// 跑N圈减速
		if (cycle == EndCycle + 1 && index == EndIndex) {
			clearInterval(Time);
			Speed = 300;
			flag = true; // 触发结束
			Time = setInterval(Star, Speed);
		}
	}

	if (index >= arr.length) {
		index = 0;
		cycle++;
	}

	tb.rows[arr[index][0]].cells[arr[index][1]].style.background = "url("
			+ settings.baseUrl + "/resources/common/images/bonus/cutbonus.png)";
	if (index > 0)
		prevIndex = index - 1;
	else {
		prevIndex = arr.length - 1;
	}
	tb.rows[arr[prevIndex][0]].cells[arr[prevIndex][1]].style.background = "none";
	index++;
	quick++;
	
	// 结束转动并选中号码
	if (flag == true && index == stopNum) {
		quick = 0;
		clearInterval(Time);
		if(desc!=""){
			$.layer({
				title:'重庆农村商业银行提示您',
				area : ['auto','auto'],
				dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+desc+'</div>',type : 9},
				end: function(){
					//如果中奖信息ID不为空，则调用派奖
					if(winnerid != "" && winnerid != null){
						aldoParisotPrize(winnerid);
					}else{
						location.reload();
					}
				}
			});
		}
	}
}


/**
 * 抽中奖之后派奖
 */
function aldoParisotPrize(winnerId){
	$.Ajax({
		url : settings.baseUrl + '/shop/activity/o2oraffle/aldoParisotPrize.jhtml',
		datas : {winnerid:winnerId},
		lockScreen : false,
		mustLogin :true,
		useErrorMsg : false,
		successFn : function (data){
			if(data.flag){
				$.layer({
					title:'重庆农村商业银行提示您',
					area : ['auto','auto'],
					dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 9},
					end : function(){location.reload();}
				});
			}else{
				$.layer({
					title:'重庆农村商业银行提示您',
					area : ['auto','auto'],
					dialog : {msg:'<div style="color:#333; font:14px/20px Verdana">'+data.msg+'</div>',type : 8},
					end : function(){location.reload();}
				});
			}
		}
	});
}


/**
 * 加载中奖名单
 */
function loadWinners(){
	$.Ajax({
		url : settings.baseUrl + '/shop/activity/o2oraffle/loadWinners.jhtml',
		datas : {},
		lockScreen : false,
		mustLogin :false,
		useErrorMsg : false,
		successFn : function (data){
			var HTML="";
			if(data.flag){
				//中奖名单
				var winners=data.data;
				if(winners.length > 0 ){
					$(winners).each(function(){ 
						HTML+="<li>"+this.winnerDesc+"</li>"
					});
				}else{
					HTML="<li>暂无获奖名单</li>";
				}
			}
			$("#winners").html(HTML);
		}
	});
}


//加载可抽奖次数
function loadLotteryTimes(){
	$.Ajax({
		url : settings.baseUrl + '/shop/activity/o2oraffle/loadLotteryTimes.jhtml',
		datas : {},
		lockScreen : false,
		mustLogin :false,
		useErrorMsg : false,
		successFn : function (data){
			if(data.flag){
				//抽奖次数
				var times=data.data;
				$("#times").html(times);
			}
		}
	});
}


/*
 * window.onload=function(){ Time = setInterval(Star,Speed); }
 */
