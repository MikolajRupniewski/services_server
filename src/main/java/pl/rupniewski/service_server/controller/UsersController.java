package pl.rupniewski.service_server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.EnabledUsers;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.repository.AuthoritiesRepository;
import pl.rupniewski.service_server.repository.EnabledUsersRepository;
import pl.rupniewski.service_server.repository.ServiceRepository;
import pl.rupniewski.service_server.repository.UsersRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")

public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EnabledUsersRepository enabledUsersRepository;

    @GetMapping(value = "")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Users getOneUser(@PathVariable Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
    }

    @GetMapping(value = "", params = "city")
    public List<Users> getUsersByCity(String city) {
        return usersRepository.findByCity(city);
    }

    @GetMapping(value = "", params = "zipCode")
    public List<Users> getUsersByZipCode(String zipCode) {
        return usersRepository.findByZipCode(zipCode);
    }

    @PutMapping("/{id}")
    public Users updateUserData(@RequestBody Users users, @PathVariable Long id) {
        Users updatedUser = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
        updatedUser.setFirstName(users.getFirstName());
        updatedUser.setLastName(users.getLastName());
        updatedUser.setPhoneNumber(users.getPhoneNumber());
        updatedUser.setZipCode(users.getZipCode());
        updatedUser.setCity(users.getCity());
        updatedUser.setStreetName(users.getStreetName());
        updatedUser.setHouseNumber(users.getHouseNumber());
        if (users.getApartmentNumber() != null) {
            updatedUser.setApartmentNumber(users.getApartmentNumber());
        }
        updatedUser.setEnabled(users.isEnabled());
        return updatedUser;
    }
}
