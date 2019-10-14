package pl.rupniewski.service_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rupniewski.service_server.model.GeoLocation;

public interface GeoLocationRepository extends JpaRepository<GeoLocation, Long> {
}
