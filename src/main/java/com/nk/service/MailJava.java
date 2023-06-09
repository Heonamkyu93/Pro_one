package com.nk.service;

import javax.mail.Authenticator;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailJava {

	
	public void sendMail(String pemail, int random) {
		Properties pro = new Properties();
			pro.put("mail.smtp.host", "smtp.gmail.com");
		//	pro.put("mail.smtp.host", "smtp.naver.com");
			pro.put("mail.smtp.port", "587");
			pro.put("mail.smtp.auth", "true");
			pro.put("mail.smtp.starttls.enable", "true");
			pro.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			Session session = Session.getInstance(pro, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("projectproonetest@gmail.com","xzskxnirhmvvlpmh");
				}
			});
			
			String receiver = pemail; // 메일 받을 주소
			String title = "가입인증 메일입니다";
			String content = "<h2 style='color:blue'>안녕하세요</h2>"+"<h3>인증번호입니다.</h3><br>"+"<h4>"+random+"<h4>";
			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress("projectproonetest@gmail.com","관리자","utf-8"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
				message.setSubject(title);
				message.setContent(content,"text/html; charset=utf-8");
				Transport.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	
	public void sendMailFindId(String pemail,String peid ) {
		Properties pro = new Properties();
			pro.put("mail.smtp.host", "smtp.gmail.com");
		//	pro.put("mail.smtp.host", "smtp.naver.com");
			pro.put("mail.smtp.port", "587");
			pro.put("mail.smtp.auth", "true");
			pro.put("mail.smtp.starttls.enable", "true");
			pro.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			Session session = Session.getInstance(pro, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("projectproonetest@gmail.com","xzskxnirhmvvlpmh");
				}
			});
			
			String receiver = pemail; // 메일 받을 주소
			String title = "아이디 찾기 메일입니다";
			String content = "<h2 style='color:blue'>안녕하세요</h2>"+"<h3>아이디 입니다.</h3><br>"+"<h4>"+peid+"<h4>";
			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress("projectproonetest@gmail.com","관리자","utf-8"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
				message.setSubject(title);
				message.setContent(content,"text/html; charset=utf-8");
				Transport.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	public void sendMailFindPwd(String inPwd ,String email ) {
		Properties pro = new Properties();
			pro.put("mail.smtp.host", "smtp.gmail.com");
		//	pro.put("mail.smtp.host", "smtp.naver.com");
			pro.put("mail.smtp.port", "587");
			pro.put("mail.smtp.auth", "true");
			pro.put("mail.smtp.starttls.enable", "true");
			pro.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			Session session = Session.getInstance(pro, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("projectproonetest@gmail.com","xzskxnirhmvvlpmh");
				}
			});
			
			String receiver = email; // 메일 받을 주소
			String title = "비밀번호 찾기 메일입니다";
			String content = "<h2 style='color:blue'>안녕하세요</h2>"+"<h3>임시 비밀번호 입니다.</h3><br>"+"<h4>"+inPwd+"<h4>";
			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress("projectproonetest@gmail.com","관리자","utf-8"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
				message.setSubject(title);
				message.setContent(content,"text/html; charset=utf-8");
				Transport.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	
}


/*
 * public void sendMail(String pemail, int random) { Properties pro = new
 * Properties(); pro.put("mail.smtp.host", "smtp.gmail.com"); //
 * pro.put("mail.smtp.host", "smtp.naver.com"); pro.put("mail.smtp.port",
 * "587"); pro.put("mail.smtp.auth", "true");
 * pro.put("mail.smtp.starttls.enable", "true"); pro.put("mail.smtp.ssl.trust",
 * "smtp.gmail.com"); Session session = Session.getInstance(pro, new
 * Authenticator() {
 * 
 * @Override protected PasswordAuthentication getPasswordAuthentication() {
 * return new
 * PasswordAuthentication("projectproonetest@gmail.com","clufsitgbcubitql"); }
 * });
 * 
 * String receiver = "heonamkyu93@naver.com"; // 메일 받을 주소 String title =
 * "가입인증 메일입니다"; String content = "<h2 style='color:blue'>안녕하세요</h2>";
 * //"+"<h3>인증번호입니다.</h3><br>"+"<h4>"+random+"<h4>"; Message message = new
 * MimeMessage(session); try { message.setFrom(new
 * InternetAddress("projectproonetest@gmail.com","관리자","utf-8"));
 * message.addRecipient(Message.RecipientType.TO, new
 * InternetAddress(receiver)); message.setSubject(title);
 * message.setContent(content,"text/html; charset=utf-8");
 * Transport.send(message); } catch (Exception e) { e.printStackTrace(); }
 * 
 * }
 */