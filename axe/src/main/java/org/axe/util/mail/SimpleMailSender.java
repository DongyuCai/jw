package org.axe.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 简单邮件（不带附件的邮件）发送器
 * @author 原作者以无法查实
 */
public final class SimpleMailSender {
	/*public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("axe_caidongyu@163.com");
		mailInfo.setPassword("NiveaLlwifUAUUi1");// 您的邮箱密码
		mailInfo.setFromAddress("axe_caidongyu@163.com");
		mailInfo.setToAddress("1157656909@qq.com");
		mailInfo.setSubject("系统日志");
		mailInfo.setContent("<a>这是一封系统日志邮件</a>");
		// 这个类主要来发送邮件
//		SimpleMailSender.sendTextMail(mailInfo);// 发送文体格式
		boolean success = SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
		LogUtil.log(success);
	}*/

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 */
	public static void sendTextMail(MailSenderInfo mailSendInfo,String ...toAddress)throws Exception {
		Message mailMessage = buildMailMessage(mailSendInfo, toAddress);
		// 设置邮件消息的主要内容
		String mailContent = mailSendInfo.getContent();
		mailMessage.setText(mailContent);
		// 发送邮件
		Transport.send(mailMessage);
	}

	/**
	 * 以HTML格式发送邮件
	 * 
	 * @param mailSendInfo
	 *            待发送的邮件信息
	 */
	public static void sendHtmlMail(MailSenderInfo mailSendInfo,String ...toAddress)throws Exception {
		Message mailMessage = buildMailMessage(mailSendInfo, toAddress);
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(mailSendInfo.getContent(), "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
	}
	
	private static Message buildMailMessage(MailSenderInfo mailSendInfo,String ...toAddress)throws Exception{
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailSendInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailSendInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailSendInfo.getUserName(), mailSendInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailSendInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address[] toAry = new Address[toAddress.length];
		for(int i=0;i<toAry.length;i++){
			toAry[i] = new InternetAddress(toAddress[i]);
		}
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipients(Message.RecipientType.TO, toAry);
		// 设置邮件消息的主题
		mailMessage.setSubject(mailSendInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		return mailMessage;
	}
}