package com.nhnacademy.ink3batch.batch.client;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserClient {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Long> getTodayBirthdayUserIds() {
        ResponseEntity<List<Long>> response = restTemplate.exchange(
                "http://localhost:10260/shop/users/birthday",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}

