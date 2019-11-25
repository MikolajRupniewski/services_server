package pl.rupniewski.service_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.properties.FileStorageProperties;
import pl.rupniewski.service_server.repository.AuthoritiesRepository;
import pl.rupniewski.service_server.repository.UsersRepository;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceServerApplication.class, args);
    }
}
