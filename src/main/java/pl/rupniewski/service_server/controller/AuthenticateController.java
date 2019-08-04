package pl.rupniewski.service_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.EnabledUsers;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.repository.AuthoritiesRepository;
import pl.rupniewski.service_server.repository.EnabledUsersRepository;
import pl.rupniewski.service_server.repository.UsersRepository;

import javax.servlet.http.HttpServletResponse;

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
        try {
            EnabledUsers enabledUsers = new EnabledUsers(users.getEmail());
            enabledUsersRepository.save(enabledUsers);
            users.setEnabled(false);
            usersRepository.save(users);
            authoritiesRepository.save(new Authorities(users.getUsername(), "USER"));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        return usersRepository.save(users);
    }
    @GetMapping(value = "/enableUser")
    public String enableUser(@RequestParam String email, @RequestParam String uuid) {
        //EnabledUsers enabledUsers = enabledUsersRepository.findByEmail(email);
        //System.out.println(enabledUsers.getUuid());
        System.out.println(email);
        System.out.println(uuid);
        return "elo";
    }
}
