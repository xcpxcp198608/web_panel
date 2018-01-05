package com.wiatec.panel.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

public class EmailMaster {

	private static final String SEND_ADDRESS = "bactivation@legacy.direct";
	private static final String USERNAME = "bactivation@legacy.direct";
	private static final String PASSWORD ="Ihatespam123#";

	private String emailSubject = "WELCOME TO LD";
	private String emailContent = "";

	private Properties properties;
	private Session session;
	private MimeMessage message;
	private Transport transport = null;

	private MimeMultipart msgMultipart = null;
	private MimeBodyPart attachmentPart = null;

	public EmailMaster() {
		properties = new Properties();
		properties.setProperty("mail.smtp.host","smtp.office365.com") ;
		properties.setProperty("mail.smtp.port","587") ;
		properties.put("mail.smtp.starttls.enable","true");
		properties.setProperty("mail.smtp.socketFactory.port","587") ;
		properties.setProperty("mail.smtp.auth","true") ;
		session = Session.getDefaultInstance(properties, null);
		message = new MimeMessage(session);
		msgMultipart = new MimeMultipart("mixed");
	}

	public void setEmailSubject(String subject){
		this.emailSubject = subject;
	}

	public void setEmailContent(String basePath, String userName ,String token){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Dear "+userName+":<br/>");
		stringBuilder.append("<br/>");
		stringBuilder.append("Legacy Direct"+"<br/>");
		stringBuilder.append("<a href='" + basePath + "/activate/"+token+"'>" +
				"ACTIVATE MY ACCOUNT</a>"+"<br/><br/>");
		this.emailContent = stringBuilder.toString();
	}

	public void setResetPasswordContent(String basePath, String userName ,String token){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Dear "+userName+":<br/>");
		stringBuilder.append("<br/>");
		stringBuilder.append("Legacy Direct"+"<br/>");
		stringBuilder.append("You have requested to reset your password.  " +
				"Please click the link below to finalize the process."+"<br/><br/>");
		stringBuilder.append("<a href='" + basePath + "/reset/"+token+"'>" +
				"RESET PASSWORD</a>"+"<br/><br/>");
		stringBuilder.append("Legacy Direct"+"<br/>");
		this.emailContent = stringBuilder.toString();
	}

	public void setInvoiceContent(String userName){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Dear "+userName+":<br/>");
		this.emailContent = stringBuilder.toString();
	}

	public boolean sendMessage(String receiverAddress){
		if(receiverAddress == null || receiverAddress.equals("")){
			System.out.println("receiverAddress error");
			return false;
		}
		try {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverAddress));
			message.setFrom(new InternetAddress(SEND_ADDRESS));
			message.setSubject(emailSubject);
			message.setContent(msgMultipart);

			MimeBodyPart content = new MimeBodyPart();
			content.setContent(emailContent,"text/html ; charset=utf-8");
			msgMultipart.addBodyPart(content);
			if(attachmentPart != null){
				msgMultipart.addBodyPart(attachmentPart);
			}
			message.setSentDate(new Date());
			message.saveChanges();
			transport = session.getTransport("smtp");
			transport.connect((String)properties.get("mail.smtp.host"), USERNAME ,PASSWORD);
			transport.sendMessage(message , message.getRecipients(MimeMessage.RecipientType.TO));
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(transport != null){
					transport.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public void addAttachment(String path){
		try {
			this.attachmentPart = getAttachedBodyPart(path);
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static MimeBodyPart getAttachedBodyPart(String filePath) throws MessagingException,
			UnsupportedEncodingException {
		MimeBodyPart attached = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(filePath);
		attached.setDataHandler(new DataHandler(fds));
		String[] f = filePath.split("/");
		String fileName = f[f.length - 1];
		attached.setFileName(fileName);
		return attached;
	}

	public static void main (String [] args){
		EmailMaster emailMaster = new EmailMaster();
		emailMaster.setInvoiceContent("sdf");
		emailMaster.addAttachment("/Users/xuchengpeng/IdeaProjects/panel/build/libs/exploded/panel-1.0-SNAPSHOT.war/15150514323162018-01-04 15:37:12.pdf");
		emailMaster.sendMessage("patrickxu@wiatec.com");
	}


}
