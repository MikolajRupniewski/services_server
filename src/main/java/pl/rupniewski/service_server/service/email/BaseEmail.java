package pl.rupniewski.service_server.service.email;

import pl.rupniewski.service_server.model.EnabledUsers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

abstract class BaseEmail {
    private final static Logger LOGGER = Logger.getLogger(BaseEmail.class.getName());
    static void sendEmail(String addressee, String subject, String header, String body) {
        final String username = "services.wfis@gmail.com";
        final String password = "Karconko123,";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("services.wfis@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(addressee)
            );
            message.setSubject(subject);
            message.setContent(
                    "<img width='100%' height='100%' src='https://securesense.ca/wp-content/uploads/2016/07/managed-services-banner.jpg'>" +
                            "<h1 style='text-align: center;'>" + header + "</h1>" +
                            "<br>" +
                            body,
                    "text/html");
            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            LOGGER.warning("Email was not sent: " + e.getMessage());
        }
    }

}
