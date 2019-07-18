package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
}
