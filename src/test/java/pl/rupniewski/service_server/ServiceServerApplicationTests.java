package pl.rupniewski.service_server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.repository.UsersRepository;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceServerApplicationTests {

    @Autowired
    private WebApplicationContext webContext;
    @MockBean
    private UsersRepository usersRepository;
    private MockMvc mockMvc;

    private Users user1 = new Users("Mikolaj","Rupniewski","570568484",
            "97-200","Tomaszow Mazowiecki","Polna",
            "14","16","mikolaj","mikolaj123",true);
    private Users user2 = new Users("Adam","Kowal","666555444",
            "97-200","Tomaszow Mazowiecki","Polna",
            "14","16","adam","adam123",true);
    private Users user3 = new Users("Monika","Rybska","555444685",
            "97-200","Tomaszow Mazowiecki","Polna",
            "14","16","monika","monika123",true);

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    @WithMockUser(username="michal",roles={"USER","ADMIN"})
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldReadUser() throws Exception {
        mockMvc.perform(get("/users")
                .with(user("michal").password("michal123"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf().asHeader()))
                .andExpect(status().is(200));

    }

}
