package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
