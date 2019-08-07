package pl.rupniewski.service_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.EnabledUsers;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.service.email.ConfirmationEmail;
import pl.rupniewski.service_server.service.email.ResetPasswordEmail;

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
        Thread t1 = new Thread(() -> ConfirmationEmail.sendEmail(enabledUsers));
        t1.start();
        LOGGER.info("Confirmation email sent");
        return usersRepository.save(users);
    }

    @GetMapping(value = "/enable-user")
    public String enableUser(@RequestParam String email, @RequestParam String uuid, HttpServletResponse response) {
        LOGGER.info("Enabling user...");
        EnabledUsers enabledUsers = enabledUsersRepository.findByEmail(email);
        if (enabledUsers != null) {
            if (enabledUsers.getUuid().equals(uuid)) {
                Users users = usersRepository.findByEmail(email);
                if (users.isEnabled()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    LOGGER.info("User already verifed");
                    return "User already verified";
                }
                users.setEnabled(true);
                usersRepository.save(users);
                response.setStatus(HttpServletResponse.SC_OK);
                LOGGER.info("User verified");
                return "Successfully verified user";
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                LOGGER.warning("Bad verification code");
                return "Bad verification code";
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.warning("User not found");
            return "User not found";
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        LOGGER.info("Deleting user...");
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        usersRepository.delete(users);
        Authorities authorities = authoritiesRepository.findByUsername(users.getUsername());
        authoritiesRepository.delete(authorities);
        LOGGER.info("User deleted");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password/{id}")
    public Users updateUserCredentials(@PathVariable Long id, @RequestBody String newPassword, @RequestBody String oldPassword) {
        LOGGER.info("Updating user's password");
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        if(!users.getPassword().equals(Users.hashPassword(oldPassword))) {
            LOGGER.warning("Password does not match");
            return null;
        }
        users.setPassword(newPassword);
        LOGGER.info("User's password updated");
        return usersRepository.save(users);
    }

    @GetMapping("/reset-password/{id}")
    public String resetUserPassword(@PathVariable Long id, String email) {
        LOGGER.info("Resetting user's password");
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        if (!users.getEmail().equals(email)) {
            LOGGER.warning("Wrong email address");
            return "Wrong email address";
        }
        String newPassword = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 8);
        users.setPassword(newPassword);
        Thread t1 = new Thread(() -> ResetPasswordEmail.sendEmail(users, newPassword));
        t1.start();
        usersRepository.save(users);
        return "Email has been sent";
    }



}
