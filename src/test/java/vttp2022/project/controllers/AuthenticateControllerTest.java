package vttp2022.project.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogout() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
            .get("/authenticate/logout")
            .sessionAttr("username", "testUsername"))
            .andExpect(status().isOk());
    }

    @Test
    void testLogin() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
            .post("/authenticate")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content("username=testUsername&password=testPassword")).andDo(print())
            .andExpect(status().isFound());

        mockMvc.perform(MockMvcRequestBuilders
            .post("/adduser")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content("username=testUsername&password=testPassword"))
            .andExpect(status().isFound());

        mockMvc.perform(MockMvcRequestBuilders
            .post("/authenticate")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content("username=testUsername&password=testPassword"))
            .andExpect(status().isFound());

        mockMvc.perform(MockMvcRequestBuilders
            .get("/deleteuser")
            .sessionAttr("username", "testUsername"))
            .andExpect(status().isNotAcceptable());
    }
    

}
