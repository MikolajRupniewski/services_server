package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    Optional<List<Chat>> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}
