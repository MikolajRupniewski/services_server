package pl.rupniewski.service_server;


import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.rupniewski.service_server.model.Users;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

class AuthenticateControllerTest extends BaseTest {

    @Test
    public void C_TestUpdateUser() throws Exception {
        List<Users> tempUsers = usersRepository.findAll();
        String uri = "/users/" + tempUsers.get(1).getId();
        System.out.println(uri);
        Users userToUpdate = tempUsers.get(1);
        userToUpdate.setUsername("kajanowa");
        String inputJson = super.mapToJson(userToUpdate);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void D_TestDeleteUser() throws Exception {
        List<Users> tempUsers = usersRepository.findAll();
        Collections.shuffle(tempUsers);
        String uri = "/users/" + tempUsers.get(1).getId();
        System.out.println(uri);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void G_TestUpdateUserByIdInvalid() throws Exception {
        List<Users> tempUsers = usersRepository.findAll();
        Users userToUpdate = tempUsers.get(1);
        userToUpdate.setUsername("kajanowa");
        String inputJson = super.mapToJson(userToUpdate);
        String uri = "/users/" + Long.MAX_VALUE;
        System.out.println(uri);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void H_TestDeleteInvalidUser() throws Exception {
        String uri = "/users/" + Long.MAX_VALUE;
        System.out.println(uri);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

}