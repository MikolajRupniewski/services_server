package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
