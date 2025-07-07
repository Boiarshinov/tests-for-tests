package dev.boiarshinov.tests4tests.p1;

import dev.boiarshinov.tests4tests.t4t.AuthorisationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Endpoint1Test {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @AuthorisationTest(endpoint = "GET /1/g1/{id}")
    void happyPath() throws Exception {
        mockMvc.perform(get("/1/g1/id1")
                        .header("X-ROLE", "admin"))
                .andExpect(status().isOk());
    }

    @Test
    @AuthorisationTest(endpoint = "GET /1/g1/{id}")
    void forbiddenAtWrongRole() throws Exception {
        mockMvc.perform(get("/1/g1/id1")
                        .header("X-ROLE", "user"))
                .andExpect(status().isForbidden());
    }
}
