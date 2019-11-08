package it.objectmethod.secretaryquiz.service;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.secretaryquiz.domain.User;
import it.objectmethod.secretaryquiz.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	public boolean checkSearchByUniqueEmail(User user) {
		boolean error = false;
		if (user != null) {
			User userSearch = userRepository.findByEmail(user.getEmail());
			if (userSearch != null) {
				error = true;
			}
		}
		return error;
	}

	public static void sendEmailToUser(User user) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.starttls.enable", "true");

		String myEmail = "matteo.milano@objectmethod.it";
		String password = "Matteomilano12$";

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(myEmail, password);
			}
		});
		Message message = prepareMessage(session, myEmail, user.getEmail(), user);
		Transport.send(message);
	}

	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, User user) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Email da ObjcetMethod");

			String nameUser = "<h1>" + user.getName() + ",</h1>" + " ";
			
						String htmlCode = "<h1>la sua iscrizione e' andata buon fine!</h1><br>\r\n" + 
								"        <h3>Objcet Method, le comunica che puo' sostenere il test al seguente link: </h3><br>\r\n" + 
								"        <a href=\"http://localhost:4200/login\"> <h1 style=\"color: crimson; text-align: center;\">Inizia il\r\n" + 
								"            Test!</h1></a><br><br><br>\r\n" + 
								"        Cordiali Saluti, Object Method srl.\r\n" + 
								"        <br><br><br><br><br><br>" + 
					"        <br><br><br><br><br><br>";
			String content = nameUser + htmlCode;
			message.setContent(content, "text/html");
			return message;
		} catch (Exception e) {
			System.out.println("Errore durante l'invio della richiesta" + e);
		}
		return null;
	}
}
