package vttp2022.project.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class ProtectedControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testProtectedView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/protected/bookmarks")
            .sessionAttr("username", "testUsername"))
            .andExpect(status().isOk());  
    }

    
}
