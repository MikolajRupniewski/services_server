package pl.rupniewski.service_server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.rupniewski.service_server.repository.AuthoritiesRepository;
import pl.rupniewski.service_server.repository.EnabledUsersRepository;
import pl.rupniewski.service_server.repository.UsersRepository;

import java.io.IOException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceServerApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public abstract class BaseTest {
    MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    AuthoritiesRepository authoritiesRepository;
    @Autowired
    EnabledUsersRepository enabledUsersRepository;

    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

}
