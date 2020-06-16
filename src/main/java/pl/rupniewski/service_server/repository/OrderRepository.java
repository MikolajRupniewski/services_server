package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
