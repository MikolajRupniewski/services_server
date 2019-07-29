package pl.rupniewski.service_server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository <Users, Long> {

    List<Users> findByCity(String city);
    List<Users> findByZipCode(String zipCode);
    Users findByUsername(String username);
}
