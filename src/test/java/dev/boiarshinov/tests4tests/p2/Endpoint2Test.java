package dev.boiarshinov.tests4tests.p2;

import dev.boiarshinov.tests4tests.t4t.AuthorisationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Endpoint2Test {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ValueSource(strings = {"admin", "owner"})
    @AuthorisationTest(endpoint = "GET /2/g2")
    void happyPath(String role) throws Exception {
        mockMvc.perform(get("/2/g2")
                        .header("X-ROLE", role))
                .andExpect(status().isOk());
    }

    @Test
    @AuthorisationTest(endpoint = "GET /2/g2")
    void forbiddenAtWrongRole() throws Exception {
        mockMvc.perform(get("/2/g2")
                        .header("X-ROLE", "user"))
                .andExpect(status().isForbidden());
    }
}
