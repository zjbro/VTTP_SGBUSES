package vttp2022.project.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class BusArrivalControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testGetBusTiming() throws Exception {
        String busStopCode = "44259";
        mockMvc.perform(MockMvcRequestBuilders
            .get("/busStop")
            .queryParam("busStopCode", busStopCode))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
            .get("/busStop")
            .queryParam("busStopCode", "00000"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testAddUserAndBookmark() throws Exception{      
        String username = "testUsername";       
        mockMvc.perform(MockMvcRequestBuilders
            .post("/adduser")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content("username=testUsername&password=testPassword"))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
            .post("/adduser")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content("username=testUsername&password=testPassword"))
            .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders
            .post("/addBookmark")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content("busStopCode=44259&description=")
            .sessionAttr("username", username))
            .andExpect(status().isOk());
            mockMvc.perform(MockMvcRequestBuilders
            .post("/addBookmark")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .content("busStopCode=44259&description=")
            .sessionAttr("username", username))
            .andExpect(status().isNotAcceptable());          
        mockMvc.perform(MockMvcRequestBuilders
            .get("/deleteBookmark/44259")
            .sessionAttr("username", username))
            .andExpect(status().isOk());
    }

    @Test
    void testLoginPage() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
            .get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    void testregisterPage() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
            .get("/register"))
            .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception{
        String username = "testUsername";
        mockMvc.perform(MockMvcRequestBuilders
            .get("/deleteuser")
            .sessionAttr("username", username))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
            .get("/deleteuser")
            .sessionAttr("username", username))
            .andExpect(status().isNotAcceptable());
    }

    @Test
    void testBookmarkPage() throws Exception{
        String username = "testUsername";
        mockMvc.perform(MockMvcRequestBuilders
            .get("/protected/bookmarks")
            .sessionAttr("username", username))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
            .get("/protected/bookmarks"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testGoBackPage() throws Exception{
        String username = "testUsername";
        mockMvc.perform(MockMvcRequestBuilders
            .get("/goback")
            .sessionAttr("username", username))
            .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
            .get("/goback"))
            .andExpect(status().isOk());
    }

}
