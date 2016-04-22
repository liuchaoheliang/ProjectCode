/**
 * @author  zhaoxiaoyao@f-road.com.cn
 * @desc 	封装ajax控件用于适用本平台简化相关操作
 * @use 	url : "", //请求具体地址
 * 			type : "post", //请求类型默认post
 * 			dataType : "json", //默认类型json
 * 			datas : {}, //参数传递支持表单序列化
 * 			errorMsg : msgInfo.ajaxDefaultErrorMsg, //请求异常时（指HTTP异常）可以传入相关自定义描述
 * 			mustLogin : false, 	//访问该ajax是否必须登录
 * 			lockScreen : false, //是否在访问该ajax时进行锁屏防止其他操作
 * 			useRandom : false,	//是否启用random随机带参，防止浏览器可能不发送请求
 * 			useErrorMsg : true,//发生HTTP请求异常时是否提示errorMsg 默认提示
 * 			successFn : function(data) { //请求成功的回调函数
 * 			}
 * @warn	1、使用该ajax时请不要在url中直接填写参数，统一使用datas变量携带参数进入后台
 * 			例如datas:{'param1':value1,'param2':value2}
 * 			2、如果使用默认值则在调用ajax时无须再指定该属性值
 * 			3、如果mustLogin设置为true时，当前ajax请求在请求后台时将进行登录验证，如果不是处于登录状态将跳转至登录页面
 **/
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('(4(a){a.S=4(b){b=r.R({m:"",n:"E",7:"C",L:{},O:W.T,F:q,H:q,K:q,I:14,Q:4(j){}},b||{});5 c=b.m;c=b.K?c+\'?u=\'+v.D():c;5 d=b.n;5 e=b.7;5 f=b.L;5 g=b.O;5 h=b.F;5 i=b.H;6(i){5 j=\'<l 12="o" M="16-J: 11;Z: 10;z-15:13;U:w%;Y:w%;V:x;X:x;y: 0.3;17:1h(y=1g);"></l>\';a(4(){a(\'1l\').1k(j)})};6(h){a.s({m:p.1f+\'?u=\'+v.D(),n:\'E\',7:\'C\',P:4(j){6(!j.1i){8.9.k=p.A+"?B="+8.9.k;t}},N:4(){8.9.k=p.A+"?B="+8.9.k;t}})};a.s({m:c,1b:f,n:d,7:e,1a:4(){},P:4(j){a(\'#o\').G();b.Q(j)},N:4(){6(b.I){a(\'#o\').G();1j.1c(\'<l M="J:1d;1e-18: 19;">\'+g+\'</l>\',2,-1)}}})}})(r);',62,84,'||||function|var|if|dataType|window|location|||||||||||href|div|url|type|ajaxWait|settings|false|jQuery|ajax|return|rand|Math|100|0px|opacity||lackLoginPowerRedirectUrl|redirectUrl|json|random|post|mustLogin|remove|lockScreen|useErrorMsg|color|useRandom|datas|style|error|errorMsg|success|successFn|extend|Ajax|ajaxDefaultErrorMsg|height|top|msgInfo|left|width|position|fixed|black|id|50|true|index|background|filter|size|15px|beforeSend|data|msg|red|font|checkLoginAjaxUrl|30|alpha|flag|layer|append|body'.split('|'),0,{}))