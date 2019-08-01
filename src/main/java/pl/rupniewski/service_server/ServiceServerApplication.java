package pl.rupniewski.service_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.rupniewski.service_server.properties.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class ServiceServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ServiceServerApplication.class, args);
	}
}
