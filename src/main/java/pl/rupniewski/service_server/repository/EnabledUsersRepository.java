package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.EnabledUsers;

public interface EnabledUsersRepository extends JpaRepository<EnabledUsers, Long> {
    EnabledUsers findByEmail(String email);
}
