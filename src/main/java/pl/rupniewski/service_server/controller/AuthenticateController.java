package pl.rupniewski.service_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.EnabledUsers;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.repository.AuthoritiesRepository;
import pl.rupniewski.service_server.repository.EnabledUsersRepository;
import pl.rupniewski.service_server.repository.UsersRepository;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;


/*
{
	"firstName": "Mikolaj",
	"lastName": "Rupniewski",
	"email": "rupniewskimikolaj@gmail.com",
	"phoneNumber": "570568484",
	"zipCode": "97-200",
	"city": "Tomaszow Mazowiecki",
	"streetName": "Polna",
	"houseNumber": "14",
	"apartmentNumber": "16",
	"username": "karconko",
	"password": "$2y$12$gWjd2DF.UE3QrovBbvW6GuHfGGA17JWKkE/5A8j6sGQy7uo7WF76K",
	"enabled": false
}
 */
@RestController
@RequestMapping(value = "/authenticate")
public class AuthenticateController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private EnabledUsersRepository enabledUsersRepository;

    @PostMapping(value = "/register")
    public Users addUser(@RequestBody Users users, HttpServletResponse response) {
        Users duplicate = usersRepository.findByUsername(users.getUsername());
        if(duplicate != null){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }
        try {
            users.setEnabled(false);
            usersRepository.save(users);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        EnabledUsers enabledUsers = new EnabledUsers(users.getEmail());
        enabledUsersRepository.save(enabledUsers);
        authoritiesRepository.save(new Authorities(users.getUsername(), "USER"));
        sendEmail(enabledUsers);
        return usersRepository.save(users);
    }

    @GetMapping(value = "/enableUser")
    public String enableUser(@RequestParam String email, @RequestParam String uuid, HttpServletResponse response) {
        EnabledUsers enabledUsers = enabledUsersRepository.findByEmail(email);
        if(enabledUsers != null) {
            if(enabledUsers.getUuid().equals(uuid)){
                Users users = usersRepository.findByEmail(email);
                if(users.isEnabled()){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return "User already verified";
                }
                users.setEnabled(true);
                usersRepository.save(users);
                response.setStatus(HttpServletResponse.SC_OK);
                return "Successfully verified user";
            }
            else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "Bad verification code";
            }
        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "User not found";
        }
    }


    private boolean sendEmail(EnabledUsers enabledUsers) {
        final String username = "rupniewskimikolaj@gmail.com";
        final String password = "angelika123";

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
                                "services.wfis@gmail.com", "Karconko123,");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("services.wfis@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("angelapiet001@gmail.com")
            );
            message.setSubject("Mikolaj's service");
            message.setContent(
                    "<img width='100%' height='100%' src='https://securesense.ca/wp-content/uploads/2016/07/managed-services-banner.jpg'>" +
                            "<h1 style='text-align: center;'>Confirm your Email</h1>" +
                            "<br>" +
                            "To confirm your email please paste this code into application: " + enabledUsers.getUuid(),

                    "text/html");
            Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
