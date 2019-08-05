package pl.rupniewski.service_server;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.rupniewski.service_server.model.Authorities;
import pl.rupniewski.service_server.model.Users;
import pl.rupniewski.service_server.repository.AuthoritiesRepository;
import pl.rupniewski.service_server.repository.UsersRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersTests extends BaseTest {

    private final Users dummyUser = new Users("Maciej","Kowal","maciekkowal@gmail.com","556848526",
            "90-711","Lodz","Zachodnia",
            "14","16","maciek","maciek123",true);

    @Override
    @Before
    public void setUp() {
        super.setUp();
        resetDatabase();
    }
    @Test
    public void A_TestGetUsersList() throws Exception {
        String uri = "/users";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users[] usersList = super.mapFromJson(content, Users[].class);
        System.out.println(usersList.length);
        assertEquals(4, usersList.length);
    }

    @Test
    public void B_TestAddUser() throws Exception {
        String uri = "/users";
        String inputJson = super.mapToJson(dummyUser);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void E_TestGetUserByIdValid() throws Exception {
        List<Users> tempUsers = usersRepository.findAll();
        Collections.shuffle(tempUsers);
        String uri = "/users/" + tempUsers.get(1).getId();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users users = super.mapFromJson(content, Users.class);
        System.out.println(users);
        assertEquals(users.getId(), tempUsers.get(1).getId());
    }
    @Test
    public void F_TestGetUserByIdInvalid() throws Exception {
        String uri = "/users/" + Long.MAX_VALUE;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }
    @Test
    public void I_TestGetUserByCityValid() throws Exception {
        List<Users> tempUsers = usersRepository.findAll();
        Collections.shuffle(tempUsers);
        String uri = "/users?city=" + tempUsers.get(1).getCity();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users[] users = super.mapFromJson(content, Users[].class);
        System.out.println(users.length);
    }
    @Test
    public void J_TestGetUserByCityInvalid() throws Exception {

        String uri = "/users?city=" + "invalid_city";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users[] users = super.mapFromJson(content, Users[].class);
        System.out.println(users.length);
        assertEquals(users.length, 0);
    }
    @Test
    public void K_TestGetUserByZipCodeValid() throws Exception {
        List<Users> tempUsers = usersRepository.findAll();
        Collections.shuffle(tempUsers);
        String uri = "/users?zipCode=" + tempUsers.get(1).getZipCode();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users[] users = super.mapFromJson(content, Users[].class);
        System.out.println(users.length);
    }
    @Test
    public void L_TestGetUserByZipcodeInvalid() throws Exception {

        String uri = "/users?zipCode=" + "invalid_zipCode";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Users[] users = super.mapFromJson(content, Users[].class);
        System.out.println(users.length);
        assertEquals(users.length, 0);
    }
    private List<Users> getDummyUsers(){
        Users user1 = new Users("Mikolaj","Rupniewski","mikolajrupniewski@gmail.com",
                "570568484","97-200","Tomaszow Mazowiecki","Polna",
                "14","16","mikolaj","mikolaj123",true);
        Users user2 = new Users("Adam","Kowal","adamkowal@gmail.com","666555444",
                "97-200","Tomaszow Mazowiecki","Polna",
                "14","16","adam","adam123",true);
        Users user3 = new Users("Monika","Rybska","monikarybska@gmail.com","555444685",
                "97-200","Tomaszow Mazowiecki","Polna",
                "14","16","monika","monika123",true);
        Users user4 = new Users("Kaja","Rymka","kajarymka@gmail.com","556845954",
                "90-711","Lodz","Zachodnia",
                "14","16","kaja","kaja123",true);
        return Arrays.asList(user1,user2,user3, user4);
    }
    private List<Authorities>getDummyAuthorities(){
        Authorities authorities1 = new Authorities("mikolaj", "USER");
        Authorities authorities2 = new Authorities("adam", "USER");
        Authorities authorities3 = new Authorities("monika", "USER");
        Authorities authorities4 = new Authorities("kaja", "USER");
        return Arrays.asList(authorities1, authorities2, authorities3, authorities4);
    }
    private void resetDatabase(){
        usersRepository.deleteAll();
        usersRepository.saveAll(getDummyUsers());
        authoritiesRepository.deleteAll();
        authoritiesRepository.saveAll(getDummyAuthorities());
    }
}
