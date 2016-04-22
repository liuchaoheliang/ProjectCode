/*
 * validation by heqingjun
 * Version 1.0
 * */
(function($){
	$.Datatype={
			"*":/^[^\s]+$/,//字符
			"u":/^[a-zA-Z0-9_-]{4,20}$/,//用户名 可由4至20 位英文+数字
			//"*6-16":/^[^\s]{6,16}$/, //字符范围
			"n":/^\d+$/,//数字
			"p":/^[1-9]\d*$/,//金额
			"s":/^[\u4E00-\u9FA5]+$/, //中文
			//"s6-18":/^[\u4E00-\u9FA5\uf900-\ufa2d\w]{6,18}$/,//中文范围
			"m":/^13[0-9]{9}$|15[0-9]{9}$|18[0-9]{9}$|14[0-9]{9}$/,//手机号码
			"e":/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, //mail
			"repeat":'',
			"definition":'',
			"noVallidate":''
	};
	$.Tipmsg={//默认提示文字;
			w:function(name){ return "请输入msg！".replace("msg",name);},
			r:"通过信息验证！",
			c:"正在检测信息…",
			s:"请填入信息！",
			v:"所填信息没有经过验证，请稍后…",
			p:"正在提交数据…",
			err:"提交数据出错，请检查地址是否正确！"
	};
	$.fn.validation = function(options){
		var setting = {
			imgPath:'images/',
			showDiv:'.Validform_checktip',
			dateType:'dataType',//校验类型
			dateName:'dataName',//校验数据名称
			errMsg:'errMsg',//错误
			ignore:'ignore', //可以忽略
			blurTrigger:true,
			selectFunc:undefined,
			definitionFunc:undefined,
			ajaxValidate:undefined,
			submitCallback:undefined,
			ajaxCallback:undefined
		};
		if(options){
			$.extend(setting,options);
		}
		var func ={
		binding:function(dot){
			$(dot).find(":input:not(':submit')").each(function(){
				if(setting.blurTrigger)
				$(this).blur(function(){ $(this).attr(setting.dateType)=="noVallidate"?"":func.validate.call(this,$(this)); });
			});
			$(dot).unbind("submit").submit(function(){
				var flag = true;
				$(this).find(":input:not(':submit'):not('button'):visible").each(function(){
					if($(this).attr(setting.dateType)=="noVallidate") return;
					if(!func.validate.call(this,$(this))) {
						flag = false;
					}
				});
				if(flag){
					if(setting.submitCallback){
						try {
							if(!setting.submitCallback.call(this)){
								flag = false;
							}
						} catch (e) {
							flag = false;
							alert("Please initialization submitCallback");
						}
					}
					if(flag && setting.ajaxCallback){
						flag = false;
						$.getJSON($(this).attr("action"),$(this).serialize(), function(data){setting.ajaxCallback.call(dot,data)});
					}
				}
				return flag;
			});
		},validate:function(dot){
			func.showMsg(dot,$.Tipmsg.c,1);
			var flag = false;
			var ignore = dot.attr(setting.ignore);
			var val = dot.val();
			if(ignore == 'ignore' &&  val == ""){
				func.showMsg(dot,"",4);
				return true;
			} else if($.trim(val) == "") {
				func.showMsg(dot,$.Tipmsg.w(dot.attr(setting.dateName)),3);
			} else {
				try {
					var type = dot.attr(setting.dateType);
					if(type){
						var typeArray = type.split("|");
						switch (typeArray[0]) {
						case "repeat":
							if(val == $(typeArray[1]).val()){
								flag = true; 
							}
							break;
						case "definition":
							var json;
							if(typeArray.length == 2){
								var functionstr = typeArray[1],param=undefined;
								if(typeArray[1].search(/\([^\s]*\)/) != -1 ){
									functionstr = typeArray[1].replace(/\([^\s]*\)/,"");
									param = typeArray[1].match(/\([^\s]*\)/).toString();
									param = param.slice(1,param.length-1);
								} else {
									functionstr = typeArray[1];
								}
								eval("var a = " +functionstr);
								json = a.call(this,param);
							} else {
								json=setting.definitionFunc.call(this,dot);
							}
							flag = json.flag; 
							if(json.Msg){
								dot.attr(setting.errMsg,json.Msg);
							}
							break;
						case "u"://用户名
							var reg = $.Datatype[typeArray[0]];
							if(reg.test(val) && !/^[0-9]{4,20}$|^[_-]{4,20}$/.test(val)) {
								flag = true; 
							} 
							break;
						case "null":
							flag = true; 
							break;
						default:
							var reg = $.Datatype[typeArray[0]];
							if(/^({\d+,\d+})|({\d+})$/.test(typeArray[1])){ 
								reg = (RegExp)(reg.source.replace("+",typeArray[1]));
							}
							if(reg.test(val)) {
								flag = true; 
							} 
							break;
						}
						
						
						
						if(flag) {
							if(typeArray[2]&&typeArray[2] == "ajaxValidate"){
								var json = setting.ajaxValidate.call(this,dot);
								flag = json.flag; 
								if(!flag){
									func.showMsg(dot,json.Msg?json.Msg:dot.attr(setting.errMsg),3);
								} else {
									func.showMsg(dot,$.Tipmsg.r,2);
								}
							} else {
								func.showMsg(dot,$.Tipmsg.r,2);
							}
						} else {
							func.showMsg(dot,dot.attr(setting.errMsg),3);
						}
					} else {
						flag = true;
						func.showMsg(dot,"",4);
					}
				} catch (e) {
					flag = false;
				}
			}
			return flag;
		},matching:function(type){
			switch (type) {
			case "text":
				
				break;
			case "password":
				
				break;
			case "select-one":
				
				break;
			}
		},showMsg:function(dot,msg,status){ 
			if(setting.selectFunc){
				obj = setting.selectFunc($(dot));
			} else {
				obj = dot.nextAll(setting.showDiv).first();
			}
			$.showMsg(obj,msg,status);
		}};
		
		$.extend({
			showMsg:function(dot,msg,status){ 
				dot.html(msg);
				switch(status){
				case 1:
					dot.removeClass("Validform_right Validform_wrong").addClass("Validform_checktip Validform_loading");//checking;
					break;
				case 2:
					dot.removeClass("Validform_wrong Validform_loading").addClass("Validform_checktip Validform_right");//passed;
					break;
				case 4:
					dot.removeClass("Validform_right Validform_wrong Validform_loading").addClass("Validform_checktip");//for ignore;
					break;
				default:
					dot.removeClass("Validform_right Validform_loading").addClass("Validform_checktip Validform_wrong");//wrong;
				}
			}
		});
		$("head:eq(0)").append('<style type="text/css">'+setting.showDiv+'{ margin-left:8px; line-height:20px; height:20px; overflow:hidden; color:#999; font-size:12px;}'+
				'.Validform_right{ color:#71b83d; padding-left:20px; background:url('+setting.imgPath+'reg4.gif) no-repeat left center; }'+
				'.Validform_wrong{ color:red; padding-left:20px; white-space:nowrap; background:url('+setting.imgPath+'reg3.gif) no-repeat left center; }'+
				'.Validform_loading{ padding-left:20px; background:url('+setting.imgPath+'onLoad.gif) no-repeat left center; }'+
				'.Validform_error{ background-color:#ffe7e7; }</style>');
		
		func.binding(this);
	};
})(jQuery);

		


		