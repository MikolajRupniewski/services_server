package pl.rupniewski.service_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.EnabledUsers;
import pl.rupniewski.service_server.model.Users;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import java.util.logging.Logger;


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
public class AuthenticateController extends BaseController {
    private final String SUBJECT_ENABLE_USER = "[Services] Verify account";
    private final String HEADER_ENABLE_USER = "Account's verification";
    private final String SUBJECT_RESET_USER_PASSWORD = "[Services] Reset password";
    private final String HEADER_RESET_USER_PASSWORD = "Account's new password";
    private final Logger LOGGER = Logger.getLogger(AuthenticateController.class.getName());

    @PostMapping(value = "/register")
    public Users addUser(@RequestBody Users users, HttpServletResponse response) {
        LOGGER.info("Starting registering new user");
        Users duplicate = usersRepository.findByUsername(users.getUsername());
        if (usersRepository.findByUsername(users.getUsername()) != null
                || usersRepository.findByEmail(users.getEmail()) != null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            LOGGER.warning("Email or username is already taken");
            return null;
        }
        users.setEnabled(false);
        usersRepository.save(users);
        response.setStatus(HttpServletResponse.SC_CREATED);
        LOGGER.info("User created");
        EnabledUsers enabledUsers = new EnabledUsers(users.getEmail());
        enabledUsersRepository.save(enabledUsers);
        authoritiesRepository.save(new Authorities(users.getUsername(), "USER"));
        Thread t1 = new Thread(() -> sendEmail(enabledUsers.getEmail(), SUBJECT_ENABLE_USER, HEADER_ENABLE_USER, getEmailBodyForEnableUser(enabledUsers)));
        t1.start();
        LOGGER.info("Confirmation email sent");
        return usersRepository.save(users);
    }

    @GetMapping(value = "/enable-user")
    public String enableUser(@RequestParam String email, @RequestParam String uuid, HttpServletResponse response) {
        EnabledUsers enabledUsers = enabledUsersRepository.findByEmail(email);
        if (enabledUsers != null) {
            if (enabledUsers.getUuid().equals(uuid)) {
                Users users = usersRepository.findByEmail(email);
                if (users.isEnabled()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return "User already verified";
                }
                users.setEnabled(true);
                usersRepository.save(users);
                response.setStatus(HttpServletResponse.SC_OK);
                return "Successfully verified user";
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "Bad verification code";
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "User not found";
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        usersRepository.delete(users);
        Authorities authorities = authoritiesRepository.findByUsername(users.getUsername());
        authoritiesRepository.delete(authorities);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password/{id}")
    public Users updateUserCredentials(@PathVariable Long id, @RequestBody String password) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        System.out.println(users.getUsername());
        System.out.println(password);
        users.setPassword(password);
        return usersRepository.save(users);
    }

    @GetMapping("/reset-password/{id}")
    public String resetUserPassword(@PathVariable Long id, String email) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        if (!users.getEmail().equals(email)) {
            LOGGER.warning("Wrong email address");
            return "Wrong email address";
        }
        String newPassword = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 8);
        users.setPassword(newPassword);
        Thread t1 = new Thread(() -> sendEmail(users.getEmail(), SUBJECT_RESET_USER_PASSWORD, HEADER_RESET_USER_PASSWORD, getEmailBodyForResetPasswod(newPassword)));
        t1.start();
        usersRepository.save(users);
        return "Email has been sent";
    }


    private void sendEmail(String addressee, String subject, String header, String body) {
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

    private String getEmailBodyForEnableUser(EnabledUsers enabledUsers) {
        String href = String.format("<a href='http://localhost:8080/authenticate/enable-user?email=%s&uuid=%s'>Confirm your email</a>", enabledUsers.getEmail(), enabledUsers.getUuid());
        return "To confirm your email please follow this link:" + href;
    }

    private String getEmailBodyForResetPasswod(String password) {
        return String.format("Your new password is: <b>%s</b>", password);
    }
}
