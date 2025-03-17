package com.luxoft.springsecurity;

import com.luxoft.spingsecurity.Lab1Exercise;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Lab1Exercise.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(RestTemplateConfig.class)
public class SecurityConfigIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void testSecureEndpoint() throws Exception {
        String url = "https://localhost:8090/user";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        System.out.println(response.getBody());
    }
}

