package com.froad.util.mail;

import org.apache.log4j.Logger;

import com.froad.CB.common.MailCommand;
import com.froad.CB.po.user.LoginManager;

public class MailUtil {
	public static Logger logger = Logger.getLogger(MailUtil.class);
//	private static final String mailServerHost="mail.f-road.com.cn";
//	private static MailSenderInfo mailInfo=new MailSenderInfo();
//	static{
//		mailInfo.setMailServerHost(mailServerHost);
//		mailInfo.setMailServerPort(mailServerPort);
//		mailInfo.setValidate(true);
//		mailInfo.setUserName("goujunwei@f-road.com.cn");
//		mailInfo.setPassword("goujw");// 您的邮箱密码
//		mailInfo.setFromAddress("goujunwei@f-road.com.cn");
//	}
	
	/**
	 * <p>description:初始化邮箱设置
	 * <p>author 勾君伟 goujunwei@f-road.com.cn 
	 * @time:2011-3-16 下午01:42:10
	 * @version 1.0
	 */
	public static MailSenderInfo initMail(MailSenderInfo mailInfo){
		mailInfo.setMailServerHost(MailCommand.MailServerHost);
		mailInfo.setMailServerPort(MailCommand.MailServerPort);
		mailInfo.setValidate(true);
		mailInfo.setUserName(MailCommand.USER_NAME);
		mailInfo.setPassword(MailCommand.PASSWORD);// 您的邮箱密码
		mailInfo.setFromAddress(MailCommand.FROMADDRESS);
		return mailInfo;
	}
	
	
	/**
	  * 方法描述：发送邮件(带抄送)
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-29 下午3:16:43
	  */
	public static boolean sendMailUtil(String subject,String content,String toAddress,String[] ccAddress){
		logger.info("向 "+toAddress+" 发送邮件  主题：" + subject + "   内容：" + content);
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo = initMail(mailInfo);
		mailInfo.setToAddress(toAddress);
		mailInfo.setCcAddress(ccAddress);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		return sms.sendTextMail(mailInfo);
	}
	/**
	  * 方法描述：发送邮件(不带抄送)
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-29 下午3:16:43
	  */
	public static boolean sendMail(String subject,String content,String toAddress){
		return sendMailUtil(subject, content, toAddress,null);
	}
	
	/**
	  * 方法描述：发送HTML邮件
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: liqiaopeng@f-road.com.cn
	  * @update:
	  * @time: 2012-08-16 下午3:16:43
	  */
	public static boolean sendMailForHtml(String subject,String content,String toAddress,String[] ccAddress){
		logger.info("向 "+toAddress+" 发送邮件  主题：" + subject + "   内容：" + content);
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo = initMail(mailInfo);
		mailInfo.setToAddress(toAddress);
		mailInfo.setCcAddress(ccAddress);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		return SimpleMailSender.sendHtmlMail(mailInfo);
	}
	

	public static void main(String[] args) {
		System.out.println("=================");
		String[] str = {"heqingjun@f-road.com.cn","35115291@qq.com"};
		String[] str2 = {"rikouhou2008@163.com","276163411@qq.com"};
		//sendMailUtil("你好","测试","liqiaopeng@froad.com.cn",str);
		sendMailForHtml("主题","积分错误报告:<table border='1' width='100%'><thead><tr><th>积分账号</th><th>积分机构</th><th>积分</th><th>冻结积分</th></tr></thead><tr><td>111</td><td>fangfutong</td><td>1000</td><td><font color='red'>10</font></td></tr></table>","liqiaopeng@f-road.com.cn",str2);
	}
	/**
	 * <p>
	 * description:想用户邮箱发送激活超链接
	 * 
	 * @param args
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-15 下午03:06:28
	 * @version 1.0
	 */
	public static LoginManager send(LoginManager loginManager) {
		// TODO Auto-generated method stub

		String ToEmailAddress=loginManager.getToEmailAddress();
		if(ToEmailAddress.equals("")||ToEmailAddress==null){
			loginManager.setRespCode("1");
			loginManager.setRespMsg("用户邮箱为空，无法发送激活信息!");
			return loginManager;
		}
		StringBuffer content=new StringBuffer();
		
		MailSenderInfo mailInfo = new MailSenderInfo();
		//初始化邮件设置
		mailInfo=initMail(mailInfo);
		
		mailInfo.setToAddress(loginManager.getToEmailAddress());
		mailInfo.setSubject("方付通商城用户激活链接");
		content.append("亲爱的方付通商城用户：\n");
		content.append("您的用户名是："+loginManager.getUsername()+"\n");
		content.append("请点击下面链接激活帐户，完成注册!\n\n");
		content.append(loginManager.getActivateURL());
		content.append("\n如果该链接无法点击，请直接拷贝以上网址到浏览器地址栏中访问。\n");
		content.append("上海方付通商务服务有限公司  www.f-road.com.cn");
		mailInfo.setContent(content.toString());
		
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
//		sms.sendHtmlMail(mailInfo);// 发送html格式
		loginManager.setRespCode("0");
		loginManager.setRespMsg("激活链接已发送用户邮箱!");
		return loginManager;
	}
	
	/**
	 * <p>description:用户通过邮箱找回密码
	 * @param loginManager
	 * @return
	 * <p>author 勾君伟 goujunwei@f-road.com.cn 
	 * @time:2011-3-16 下午02:02:32
	 * @version 1.0
	 */
	public static LoginManager initPassword(LoginManager loginManager) {
		// TODO Auto-generated method stub

		String ToEmailAddress=loginManager.getToEmailAddress();
		if(ToEmailAddress==null||ToEmailAddress.equals("")){
			loginManager.setRespCode("1");
			loginManager.setRespMsg("用户邮箱为空，无法发送新密码信息!");
			return loginManager;
		}
		StringBuffer content=new StringBuffer();
		
		MailSenderInfo mailInfo = new MailSenderInfo();
		//初始化邮件设置
		mailInfo=initMail(mailInfo);
		
		mailInfo.setToAddress(loginManager.getToEmailAddress());
		mailInfo.setSubject("珠海农商行平台用户密码重置链接");
		content.append("亲爱的会员：\n");
		content.append("您的用户名是："+loginManager.getUsername()+"\n");
		content.append("请使用下面的临时密码登录系统，然后重置密码!\n\n");
		content.append("临时密码："+loginManager.getPassword()+"\n\n");
		content.append("上海方付通商务服务有限公司");
		mailInfo.setContent(content.toString());
		
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
//		sms.sendHtmlMail(mailInfo);// 发送html格式
		loginManager.setRespCode("0");
		loginManager.setRespMsg("新临时密码已发送用户邮箱，提示用户及时登录并重置密码!");
		return loginManager;
	}
}
