package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<List<Shop>> findByServices_nameContaining(String name);
    Optional<List<Shop>> findByUsers_usernameContaining(String username);
    Optional<Shop> findByUserId(Long id);
}
