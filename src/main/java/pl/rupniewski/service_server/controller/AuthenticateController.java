package pl.rupniewski.service_server.controller;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.EnabledUsers;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.service.email.ConfirmationEmail;
import pl.rupniewski.service_server.service.email.ResetPasswordEmail;

import javax.servlet.http.HttpServletResponse;
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
@CrossOrigin
@RequestMapping(value = "/authenticate")
public class AuthenticateController extends BaseController {
    private final Logger LOGGER = Logger.getLogger(AuthenticateController.class.getName());

    @PostMapping(value = "/register")
    public Users addUser(@RequestBody Users users, HttpServletResponse response) {
        LOGGER.info("Starting registering new user");
        System.out.println(usersRepository.findByUsername(users.getUsername()));
        System.out.println(usersRepository.findByEmail(users.getEmail()));
        if (usersRepository.findByUsername(users.getUsername()) != null
                || usersRepository.findByEmail(users.getEmail()) != null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            LOGGER.warning("Email or username is already taken");
            LOGGER.warning(users.toString());
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
        response.setStatus(HttpServletResponse.SC_CREATED);
        return usersRepository.save(users);
    }
//    @GetMapping(value = "/dummy")
//    public Users addDummyUser() {
//        final Users dummyUser = new Users("Maciej", "Kowal", "rupniewskimikolaj@gmail.com", "556848526",
//                "90-711", "Lodz", "Zachodnia",
//                "14", "16", "maciek", "maciek123", false);
//        return usersRepository.save(dummyUser);
//    }

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
    public Users updateUserCredentials(@PathVariable Long id, @RequestParam String newPassword, @RequestParam String oldPassword) {
        LOGGER.info("Updating user's password");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        if (!bCryptPasswordEncoder.matches(oldPassword, users.getPassword())) {
            LOGGER.warning("Password does not match");
            return null;
        }
        users.setPassword(newPassword);
        LOGGER.info("User's password updated");
        return usersRepository.save(users);
    }
    @PutMapping("/update-email/{id}")
    public Users updateUserEmail(@PathVariable Long id, @RequestParam String newEmail, @RequestParam String oldEmail) {
        LOGGER.info("Updating user's email");
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        if(!users.getEmail().equals(oldEmail)) {
            LOGGER.warning("Email does not match");
            return null;
        }
        users.setEmail(newEmail);
        LOGGER.info("User's email updated");
        return usersRepository.save(users);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetUserPassword(@RequestParam String email) {
        LOGGER.info("Resetting user's password");
        Users users = usersRepository.findByEmail(email);
        if (users == null) {
            LOGGER.warning("Wrong email address");
            return ResponseEntity.notFound().build();
        }
        String newPassword = Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 8);
        users.setPassword(newPassword);
        Thread t1 = new Thread(() -> ResetPasswordEmail.sendEmail(users, newPassword));
        t1.start();
        usersRepository.save(users);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestParam String username, @RequestParam String password){
        Users users = usersRepository.findByUsername(username);
        if (users == null) {
            return ResponseEntity.badRequest().build();
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(password, users.getPassword())) {
            if(users.isEnabled()) {
                Authorities auth = authoritiesRepository.findByUsername(users.getUsername());
                LOGGER.info("Role: " + auth.getAuthority());
                if(auth.getAuthority().equals("ROLE_USER")) {
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.accepted().build();
                }

            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/get-user-by-username")
    public Users geUserByUsername(@RequestParam String username) {
        return usersRepository.findByUsername(username);
    }

}
