package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
