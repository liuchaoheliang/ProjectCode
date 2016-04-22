package com.froad.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;


/**
 * 类描述：邮件发送  带抄送
	 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2012 
 * @author: 何庆均 heqingjun@f-road.com.cn
 * @update:
 * @time: 2012-7-17 下午6:00:14 
 */
public class SimpleMailSender {
private static Logger logger = Logger.getLogger(SimpleMailSender.class);
/**
 * 以文本格式发送邮件
 * 
 * @param mailInfo
 *            待发送的邮件的信息
 */
public boolean sendTextMail(MailSenderInfo mailInfo) {
	// 判断是否需要身份认证
	MyAuthenticator authenticator = null;
	Properties pro = mailInfo.getProperties();
	if (mailInfo.isValidate()) {
		// 如果需要身份认证，则创建一个密码验证器
		authenticator = new MyAuthenticator(mailInfo.getUserName(),
				mailInfo.getPassword());
	}
	// 根据邮件会话属性和密码验证器构造一个发送邮件的session
	Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
	try {
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		
		//设置抄送者
		if(mailInfo.getCcAddress() != null && mailInfo.getCcAddress().length > 0){
			Address[] address = new Address[mailInfo.getCcAddress().length];
			for (int i = 0; i < address.length; i++) {
				address[i] = new InternetAddress(mailInfo.getCcAddress()[i]);
			}
					
			mailMessage.setRecipients(Message.RecipientType.CC, address);
		}
		
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// 设置邮件消息的主要内容
		String mailContent = mailInfo.getContent();
		mailMessage.setText(mailContent);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	} catch (MessagingException ex) {
		logger.error("异常", ex);
	}
	return false;
}

/**
 * 以HTML格式发送邮件
 * 
 * @param mailInfo
 *            待发送的邮件信息
 */
public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
	// 判断是否需要身份认证
	MyAuthenticator authenticator = null;
	Properties pro = mailInfo.getProperties();
	// 如果需要身份认证，则创建一个密码验证器
	if (mailInfo.isValidate()) {
		authenticator = new MyAuthenticator(mailInfo.getUserName(),
				mailInfo.getPassword());
	}
	// 根据邮件会话属性和密码验证器构造一个发送邮件的session
	Session sendMailSession = Session
			.getDefaultInstance(pro, authenticator);
	try {
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(mailInfo.getToAddress());
		
		
		//设置抄送者
		if(mailInfo.getCcAddress() != null && mailInfo.getCcAddress().length > 0){
			Address[] address = new Address[mailInfo.getCcAddress().length];
			for (int i = 0; i < address.length; i++) {
				address[i] = new InternetAddress(mailInfo.getCcAddress()[i]);
			}
					
			mailMessage.setRecipients(Message.RecipientType.CC, address);
		}
		
		
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		//设置邮件的等级
		mailMessage.setHeader("X-Priority","1");
		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	} catch (MessagingException ex) {
		logger.error("异常", ex);
	}
	return false;
}
}

