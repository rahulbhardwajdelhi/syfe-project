package com.syfe.pfm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// quick check that login + transactions + report flow works
@SpringBootTest
@AutoConfigureMockMvc
class FinanceWorkflowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void typicalMonthOfSpendingAndReport() throws Exception {
        String email = "workflow." + System.currentTimeMillis() + "@example.com";
        MockHttpSession session = registerAndLogin(email);

        postJson(session, "/transactions", """
                {"amount":5000,"date":"2024-01-10","category":"Salary","description":"Pay"}
                """);
        postJson(session, "/transactions", """
                {"amount":1200,"date":"2024-01-12","category":"Rent","description":"Rent"}
                """);

        MvcResult goalResult = postJson(session, "/goals", """
                {"goalName":"Buffer","targetAmount":10000,"targetDate":"2027-06-01","startDate":"2024-01-01"}
                """);
        JsonNode goal = objectMapper.readTree(goalResult.getResponse().getContentAsString());
        assertEquals(0, goal.get("currentProgress").decimalValue().compareTo(new java.math.BigDecimal("3800.00")));

        MvcResult report = mockMvc.perform(get("/reports/monthly/2024/1").session(session))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode reportJson = objectMapper.readTree(report.getResponse().getContentAsString());
        assertEquals(0, reportJson.get("netSavings").decimalValue().compareTo(new java.math.BigDecimal("3800.00")));
        assertTrue(reportJson.get("totalIncome").get("Salary").asText().contains("5000"));
    }

    private MockHttpSession registerAndLogin(String email) throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"password123","fullName":"Test User","phoneNumber":"+1234567890"}
                                """.formatted(email)))
                .andExpect(status().isCreated());

        MvcResult login = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"password123"}
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andReturn();

        return (MockHttpSession) login.getRequest().getSession();
    }

    private MvcResult postJson(MockHttpSession session, String path, String body) throws Exception {
        return mockMvc.perform(post(path)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}
