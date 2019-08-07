package pl.rupniewski.service_server.controller;


import org.springframework.web.bind.annotation.*;
import pl.rupniewski.service_server.exception.ResourceNotFundException;
import pl.rupniewski.service_server.model.Users;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")

public class UsersController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(UsersController.class.getName());

    @GetMapping(value = "")
    public List<Users> getAllUsers() {
        LOGGER.info("Receiving all users");
        return usersRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Users getOneUser(@PathVariable Long id) {
        LOGGER.info("Receiving user for id=" + id);
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFundException("User", "id", id));
    }

    @GetMapping(value = "", params = {"city"})
    public List<Users> getUsersByCity(String city) {
        LOGGER.info("Receiving user for city=" + city);
        return usersRepository.findByCity(city);
    }

    @GetMapping(value = "", params = {"zipCode"})
    public List<Users> getUsersByZipCode(String zipCode) {
        LOGGER.info("Receiving user for zipCode=" + zipCode);
        return usersRepository.findByZipCode(zipCode);
    }

    @PutMapping("/{id}")
    public Users updateUserData(@RequestBody Users users, @PathVariable Long id) {
        LOGGER.info("Updating user's data for id=" + id);
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
