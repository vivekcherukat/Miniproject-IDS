package miniproject.alert;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import miniproject.SystemManager;

public class AlertHandlerEmail {
	static ArrayList<AlertItem> alertList = new ArrayList<AlertItem>();

	
	public static void mailAlert(Queue<AlertItem> alertQueue) {
		// loadAlertList(alertQueue);
		// Recipient
		String to = SystemManager.configuration.getEmailTo();

		// Sender
		// TODO Load these details from settings
		String from = SystemManager.configuration.getEmailfrom();
		final String username = SystemManager.configuration.getUsername();
		final String password = SystemManager.configuration.getPassword();

		// Alert Details
		int alertID;
		String alertSource = new String();
		String alertImagePath = new String();
		String alertDateTime = new String();
		AlertItem buffer = new AlertItem();

		String host = SystemManager.configuration.getHostName();
		String port = SystemManager.configuration.getPort();
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.ssl.trust", host);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			message.setSubject("***Intruder Alert***");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Intrusion Details");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			DataSource source = null;

			while (alertQueue.isEmpty() == false) {
				buffer = alertQueue.remove();
				System.out.println("Removed Item from alertQueue");
				alertID = buffer.alertId;
				alertSource = buffer.alertSource;
				alertImagePath = buffer.alertImagePath;
				alertDateTime = buffer.alertDateTime;
				messageBodyPart = new MimeBodyPart();
				messageBodyPart.setText("Intrusion Alert! ID:" + alertID
						+ " Source:" + alertSource + " Time:" + alertDateTime);
				multipart.addBodyPart(messageBodyPart);
				messageBodyPart = new MimeBodyPart();
				source = new FileDataSource(alertImagePath);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(alertImagePath);
				multipart.addBodyPart(messageBodyPart);

			}

			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Email Handler: Message Sent Successfully!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
