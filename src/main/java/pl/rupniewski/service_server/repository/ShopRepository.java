package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
