/*K V*/
/**
 * @author zhaoxiaoyao@f-road.com.cn
 * @desc 静态配置信息总集
 */
var settings = 
{
	baseUrl : '${base}',
	checkLoginAjaxUrl : '${base}/common/check_user_login.jhtml',
	lackLoginPowerRedirectUrl : '${base}/shop/login/index.jhtml',
	loginDefaultReturnUrl : '${base}/',
	countDownTimer : 60
};

var msgInfo = 
{
	ajaxDefaultErrorMsg : '哎呀，服务器开小差了，请稍后重试！',
	operateSuccessMsg : '操作成功！',
	operateFailureMsg : '对不起，系统繁忙，请稍后再试！',
	sendSMSSuccess : '短信验证码发送成功！'
};

var regexp = 
{
	phoneRE : /^1[3458][0-9]{9}$/
};
