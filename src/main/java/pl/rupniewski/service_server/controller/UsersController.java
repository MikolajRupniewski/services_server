package pl.rupniewski.service_server.controller;


import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Users;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UsersController extends BaseController {

    @GetMapping(value = "")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Users getOneUser(@PathVariable Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
    }

    @GetMapping(value = "", params = {"city"})
    public List<Users> getUsersByCity(String city) {
        return usersRepository.findByCity(city);
    }

    @GetMapping(value = "", params = {"zipCode"})
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
