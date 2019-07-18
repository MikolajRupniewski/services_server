package pl.rupniewski.service_server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.repository.AuthoritiesRepository;
import pl.rupniewski.service_server.repository.UsersRepository;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

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

    @PostMapping(value = "")
    public Users addUser(@RequestBody Users users) {
        authoritiesRepository.save(new Authorities(users.getUsername(), "ADMIN"));
        return usersRepository.save(users);
    }

    @PutMapping("/{id}")
    public Users updateUser(@RequestBody Users users, @PathVariable Long id) {
        Users updatedUser = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User","id", id));
        updatedUser.setFirstName(users.getFirstName());
        updatedUser.setLastName(users.getLastName());
        updatedUser.setPhoneNumber(users.getPhoneNumber());
        updatedUser.setZipCode(users.getZipCode());
        updatedUser.setCity(users.getCity());
        updatedUser.setStreetName(users.getStreetName());
        updatedUser.setHouseNumber(users.getHouseNumber());
        if(users.getApartmentNumber() != null) {
            updatedUser.setApartmentNumber(users.getApartmentNumber());
        }
        updatedUser.setUsername(users.getUsername());
        updatedUser.setPassword(users.getPassword());
        updatedUser.setEnabled(users.isEnabled());
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Users users = usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User","id",id));
        usersRepository.delete(users);
        return ResponseEntity.ok().build();
    }
}
