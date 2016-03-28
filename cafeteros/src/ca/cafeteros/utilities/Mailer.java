package ca.cafeteros.utilities;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import java.util.Base64;
import java.util.Properties;
import java.io.UnsupportedEncodingException;

public class Mailer {
	
	private String smtpServer;
	private InternetAddress to;
	private InternetAddress from;
	private String username;
	private String password;
	private String port;
	private String subject;
	private String content;
	private Session session;
	private Properties properties;
	private Message message;
	
	private Logger log;
	
	public static void main(String[] args){
		
		Logger log = new Logger();
		Mailer testEmail = new Mailer("ubuntucloud.cafeterosclub.ca", 587, log);
		testEmail.setUsername("myself");
		testEmail.setFrom("myself@cafeterosclub.ca");
		testEmail.setTo("davidrcuervo@gmail.com");
		testEmail.setSubject("This is a test email from java");
		testEmail.setContent("<h2>This is actual message embedded in HTML tags.</h2>");
		
		try{
			String pass_base64 = Base64.getEncoder().encodeToString("www.myself.com".getBytes("utf-8"));
			//log.info("Encoded password is: " + pass_base64);
			testEmail.setPassword(pass_base64);
			testEmail.send();
		}catch(UnsupportedEncodingException ex){
			log.error(ex.getMessage());
		}
		
	}
	
	public Mailer(String host, int port){
		log = new Logger();
		buildDefaults(host, port);
	}
	
	public Mailer(String host, int port, Logger log){
		this.log = log;
		buildDefaults(host, port);
	}
	
	private void buildDefaults(String host, int port){
		this.properties = new Properties();
		to = new InternetAddress();
		from = new InternetAddress();
		this.setSmtpServer(host).setPort(port).setSession();
	}
	
	private Mailer setPort(int port){
		log.info("Setting port of smtp server. port: " + port);
		
		this.port = Integer.toString(port);
		properties.put("mail.smtp.port", this.port);
		
		return this;
	}
	
	private Mailer setSmtpServer(String host){
		log.info("Setting smtp server. SMTP Server: " + host);
		
		smtpServer = host;
		properties.put("mail.smtp.host", smtpServer);
		
		return this;
	}
	
	private void setSession(){
		log.info("Setting session for send emails. smtpServer " + this.smtpServer);
		
		properties.put("mail.smtp.auth", "true");
		this.session = Session.getInstance(properties,
				new javax.mail.Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication(username, password);
					}
		});
	}
	
	public Mailer setTo(String to){
		log.info("Setting destination address. to: " + to);
		
		try{
			this.to = new InternetAddress(to);
			this.to.validate();
		
		}catch(AddressException ex){
			log.error("Error while setting destination email adddress. Exception error: " + ex.getMessage());
			this.to = new InternetAddress();
		}
		
		return this;
	}
	
	public Mailer setFrom(String from){
		log.info("Setting sender email address. from: " + from);
		
		try{
			this.from = new InternetAddress(from);
			this.from.validate();
		}catch(AddressException ex){
			log.error("Error while setting sender email adddress. Exception error: " + ex.getMessage());
			this.from = new InternetAddress();
		}
		
		return this;
	}
	
	public Mailer setPassword(String password){
		log.info("Setting password for mail sender account. password: ******");
		
		byte[] password_base64 = Base64.getDecoder().decode(password);
		
		try{
			this.password = new String(password_base64, "utf-8");
		}catch (UnsupportedEncodingException ex){
			this.password = "";
			log.error("Encoded password is not valid.");
		}
		
		return this;
	}
	
	public Mailer setUsername(String username){
		log.info("Setting username for mail sender account. username: " + username);
		
		this.username = username;
		
		return this;
	}
	
	public Mailer setSubject(String subject){
		log.info("Setting subject for email. subject: " + subject);
		
		if(subject == null || subject.isEmpty()){
			log.error("Subject for email is empty or not valid");
			this.subject = "";
		}else{
			this.subject = subject;
		}
		
		return this;
	}
	
	public Mailer setContent(String content){
		log.info("Setting content for email. content: " + content);
		
		if(content == null || content.isEmpty()){
			log.error("Content email is empty or not valid");
			this.content = "";
		}else{
			this.content = content;
		}
		
		return this;
	}
	
	public void send() throws RuntimeException{
		log.info("Sending email.");
		
		if(to == null || subject == null || content == null || subject.isEmpty() || content.isEmpty()){
			log.error("The parameters to send email are not correct. Verify subject, content and/or destination address");
			reset();
			throw new RuntimeException("subect, content, or/and destination address are null");
		}
		
		try{
			message = new MimeMessage(session);
			message.setFrom(from);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to.toString()));
			message.setSubject(subject);
			message.setContent(content, "text/html");
			Transport.send(message);
			log.info("Email has been sent succesfully");
			reset();
		}catch (MessagingException ex){
			log.error("The email did not send. Error message: " + ex.getMessage());
			reset();
			throw new RuntimeException(ex);
		}
	}
	
	private void reset(){
		log.info("Resetting email parameters");
		
		this.to = null;
		this.subject = null;
		this.content = null;
	}
}
